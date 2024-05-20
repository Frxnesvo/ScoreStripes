package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.ClubDto;
import com.example.springbootapp.data.dto.ClubRequestDto;
import com.example.springbootapp.exceptions.RequestValidationException;
import com.example.springbootapp.service.interfaces.ClubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clubs")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ClubDto> createClub(@Valid @ModelAttribute ClubRequestDto clubRequestDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new RequestValidationException("Input validation failed");
        }
        ClubDto createdClub = clubService.createClub(clubRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdClub);
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getClubNames(){
        List<String> clubNames = clubService.getClubNames();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clubNames);
    }

    @GetMapping()
    public ResponseEntity<List<ClubDto>> getClubs(@RequestParam(required = false) String leagueName) {
        List<ClubDto> clubs = clubService.getClubs(leagueName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clubs);
    }
}
