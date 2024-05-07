package com.example.springbootapp.controller;

import com.example.springbootapp.Data.DTO.ClubDto;
import com.example.springbootapp.Data.DTO.ClubRequestDto;
import com.example.springbootapp.Data.DTO.LeagueDto;
import com.example.springbootapp.Data.DTO.LeagueRequestDto;
import com.example.springbootapp.service.interfaces.ClubService;
import com.example.springbootapp.service.interfaces.LeagueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leagues")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ClubDto> createClub(@Valid @ModelAttribute ClubRequestDto clubRequestDto){
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
}
