package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.Data.DTO.LeagueDto;
import com.example.springbootapp.Data.DTO.LeagueRequestDto;

import java.util.List;

public interface LeagueService {
    LeagueDto createLeague(LeagueRequestDto leagueRequestDto);
    List<String> getLeagueNames();
}
