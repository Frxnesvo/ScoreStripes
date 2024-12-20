package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dto.LeagueDto;
import com.example.springbootapp.data.dto.LeagueRequestDto;
import com.example.springbootapp.data.dao.LeagueDao;
import com.example.springbootapp.data.entities.League;
import com.example.springbootapp.service.interfaces.AwsS3Service;
import com.example.springbootapp.service.interfaces.LeagueService;
import com.example.springbootapp.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeagueServiceImpl implements LeagueService {

    private final AwsS3Service awsS3Service;
    private final LeagueDao leagueDao;
    private final ModelMapper modelMapper;

    @Override
    public LeagueDto createLeague(LeagueRequestDto leagueRequestDto) {
        if(leagueDao.existsByName(leagueRequestDto.getName())){
            throw new RuntimeException("League already exists");
        }
        League league = new League();
        league.setName(leagueRequestDto.getName());
        String url = awsS3Service.uploadFile(leagueRequestDto.getPic(), Constants.LEAGUE_FOLDER, leagueRequestDto.getName());
        league.setPicUrl(url);
        leagueDao.save(league);
        return modelMapper.map(league, LeagueDto.class);
    }

    @Override
    public List<String> getLeagueNames() {
        return leagueDao.getAllNames();
    }


    @Override
    public List<LeagueDto> getLeagues() {
        List<League> leagues = leagueDao.findAll();
        return leagues.stream()
                .map(league -> modelMapper.map(league, LeagueDto.class))
                .toList();
    }

}
