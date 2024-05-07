package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.Data.DTO.LeagueDto;
import com.example.springbootapp.Data.DTO.LeagueRequestDto;

public interface LeagueService {
    LeagueDto createLeague(LeagueRequestDto leagueRequestDto);
}
