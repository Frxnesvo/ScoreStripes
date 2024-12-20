package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.LeagueDto;
import com.example.springbootapp.data.dto.LeagueRequestDto;
import com.example.springbootapp.exceptions.RequestValidationException;
import com.example.springbootapp.utils.ExceptionUtils;
import com.example.springbootapp.security.RateLimited;
import com.example.springbootapp.service.interfaces.LeagueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RateLimited(permitsPerSecond = 10)
@RestController
@RequestMapping("/api/v1/leagues")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LeagueController {

    private final LeagueService leagueService;
    private final ExceptionUtils exceptionUtils;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<LeagueDto> createLeague(@Valid @ModelAttribute LeagueRequestDto leagueRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new RequestValidationException(exceptionUtils.createErrorMessage(bindingResult));
        }
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

    @GetMapping()
    public ResponseEntity<List<LeagueDto>> getLeagues(){
        List<LeagueDto> leagues = leagueService.getLeagues();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(leagues);
    }
}
