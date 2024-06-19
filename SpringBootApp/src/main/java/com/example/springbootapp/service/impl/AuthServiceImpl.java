package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.AdminDao;
import com.example.springbootapp.data.dao.CustomerDao;
import com.example.springbootapp.data.dao.UserDao;
import com.example.springbootapp.data.dto.*;
import com.example.springbootapp.data.entities.*;
import com.example.springbootapp.data.entities.Enums.Role;
import com.example.springbootapp.data.entities.Enums.WishlistVisibility;
import com.example.springbootapp.exceptions.InvalidTokenException;
import com.example.springbootapp.exceptions.NoAccountException;
import com.example.springbootapp.exceptions.UserAlreadyExistsException;
import com.example.springbootapp.exceptions.VerificationException;
import com.example.springbootapp.service.interfaces.AuthService;
import com.example.springbootapp.service.interfaces.AwsS3Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.auth.oauth2.TokenVerifier;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.springbootapp.handler.JwtHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ModelMapper modelMapper;    //TODO: DA VEDERE COME RIFATTORIZZARE LE 2 REGISTER

    @Value("${google.clientId}")
    private String googleClientId;

    private final UserDao userDao;
    private final AdminDao adminDao;
    private final CustomerDao customerDao;
    private final JwtHandler jwtHandler;
    private final AwsS3Service awsS3Service;


    @Override
    public AuthResponseDto login(String idToken) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();{
        }
        try {
            GoogleIdToken googleIdToken = verifier.verify(idToken);
            if (googleIdToken == null) {
                throw new VerificationException("Invalid token");
            }
            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            System.out.println(payload.getEmail());
            System.out.println(payload.toPrettyString());
            Optional<User> user = userDao.findByEmail(payload.getEmail());
            if (user.isPresent()) {
                String jwtToken = jwtHandler.generateJwtToken(user.get());
                AuthResponseDto response = modelMapper.map(user.get(), AuthResponseDto.class);
                response.setJwt(jwtToken);
                return response;
            }
            else {
                throw new NoAccountException("No account found");
            }
        }
        catch (GeneralSecurityException | IOException | VerificationException e) {
            throw new VerificationException("Invalid token");
        }
    }

    @Override
    public String registerAdmin(AdminRegisterDto adminRegisterDto) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();
        try {
            GoogleIdToken googleIdToken = verifier.verify(adminRegisterDto.getIdToken());
            if (googleIdToken == null) {
                throw new VerificationException("Invalid token");
            }
            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            if (userDao.existsByEmail(payload.getEmail())) {
                throw new UserAlreadyExistsException("User already exists");
            }
            if(userDao.existsByUsername(adminRegisterDto.getUsername())){
                throw new UserAlreadyExistsException("Username already exists");
            }
            Admin admin = new Admin();
            admin.setUsername(adminRegisterDto.getUsername());
            admin.setFirstName(payload.get("given_name").toString());
            admin.setLastName(payload.get("family_name").toString());
            admin.setEmail(payload.getEmail());
            admin.setBirthDate(adminRegisterDto.getBirthDate());
            admin.setGender(adminRegisterDto.getGender());
            admin.setRole(Role.ADMIN);
            if(adminRegisterDto.getProfilePic()==null) {
                byte [] defaultPic = downloadImageFromUrl(payload.get("picture").toString());
                String url = awsS3Service.uploadFile(defaultPic, "users", adminRegisterDto.getUsername());
                admin.setProfilePicUrl(url);
            }
            else {
                String url = awsS3Service.uploadFile(adminRegisterDto.getProfilePic(), "users", adminRegisterDto.getUsername());
                admin.setProfilePicUrl(url);
            }
            adminDao.save(admin);
            return  "Admin registered successfully";
        }
        catch (GeneralSecurityException | IOException e) {
            throw new VerificationException("Invalid token");
        }
    }

    @Override
    @Transactional
    public String registerCustomer(CustomerRegisterDto customerRegisterDto) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();
        try {
            GoogleIdToken googleIdToken = verifier.verify(customerRegisterDto.getIdToken());
            if (googleIdToken == null) {
                throw new InvalidTokenException("Invalid token");
            }
            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            if (userDao.existsByEmail(payload.getEmail())) {
                throw new UserAlreadyExistsException("User already exists");
            }
            if (userDao.existsByUsername(customerRegisterDto.getUsername())) {
                throw new UserAlreadyExistsException("Username already exists");
            }
            Customer customer = new Customer();
            customer.setUsername(customerRegisterDto.getUsername());
            customer.setFirstName(payload.get("given_name").toString());
            customer.setLastName(payload.get("family_name").toString());
            customer.setEmail(payload.getEmail());
            customer.setBirthDate(customerRegisterDto.getBirthDate());
            customer.setGender(customerRegisterDto.getGender());
            customer.setRole(Role.CUSTOMER);
            if (customerRegisterDto.getProfilePic() == null) {
                byte[] defaultPic = downloadImageFromUrl(payload.get("picture").toString());
                String url = awsS3Service.uploadFile(defaultPic, "users", customerRegisterDto.getUsername());
                customer.setProfilePicUrl(url);
            } else {
                String url = awsS3Service.uploadFile(customerRegisterDto.getProfilePic(), "users", customerRegisterDto.getUsername());
                customer.setProfilePicUrl(url);
            }
            customer.setFavouriteTeam(customerRegisterDto.getFavoriteTeam());
            customer.setCart(new Cart());
            Wishlist wishlist = new Wishlist();
            //wishlist.setOwner(customer);
            wishlist.setVisibility(WishlistVisibility.PRIVATE);
            customer.setWishlist(wishlist);
            Address address = modelMapper.map(customerRegisterDto.getAddress(), Address.class);
            address.setCustomer(customer);
            customer.getAddresses().add(address);
            customerDao.save(customer);
            return "Customer registered successfully";
        } catch (GeneralSecurityException | IOException e) {
            throw new VerificationException("Invalid token");
        }
    }

    private byte[] downloadImageFromUrl(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        try (InputStream inputStream = connection.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
}
