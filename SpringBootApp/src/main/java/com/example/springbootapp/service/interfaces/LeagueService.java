package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.LeagueDto;
import com.example.springbootapp.data.dto.LeagueRequestDto;

import java.util.List;

public interface LeagueService {
    LeagueDto createLeague(LeagueRequestDto leagueRequestDto);
    List<String> getLeagueNames();
}
