package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CustomerAuthResponseDto extends AuthResponseDto{
    private String favouriteTeam;
    private AddressDto address;


    @Override
    public String toString() {
        return "CustomerAuthResponseDto{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePicUrl='" + profilePicUrl + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", favouriteTeam='" + favouriteTeam + '\'' +
                ", address=" + address;
    }
}
