package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.LeagueDto;
import com.example.springbootapp.data.dto.LeagueRequestDto;
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
public class LeagueController {

    private final LeagueService leagueService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<LeagueDto> createLeague(@Valid @ModelAttribute LeagueRequestDto leagueRequestDto){
        LeagueDto createdLeague = leagueService.createLeague(leagueRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdLeague);
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getLeagueNames(){
        List<String> leagueNames = leagueService.getLeagueNames();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(leagueNames);
    }
}
