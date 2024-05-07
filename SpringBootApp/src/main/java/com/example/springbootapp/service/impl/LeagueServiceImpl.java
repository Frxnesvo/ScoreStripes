package com.example.springbootapp.service.impl;

import com.example.springbootapp.Data.DTO.LeagueDto;
import com.example.springbootapp.Data.DTO.LeagueRequestDto;
import com.example.springbootapp.Data.Dao.LeagueDao;
import com.example.springbootapp.Data.Entities.League;
import com.example.springbootapp.service.interfaces.AwsS3Service;
import com.example.springbootapp.service.interfaces.LeagueService;
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
            throw new RuntimeException("League already exists");   //TODO: DA CAMBIARE IN CUSTOM EXCEPTION
        }
        League league = new League();
        league.setName(leagueRequestDto.getName());
        String url = awsS3Service.uploadFile(leagueRequestDto.getPic(), "leagues", leagueRequestDto.getName());   //FIXME: NON MI PIACE PASSARE IL FOLDER COSI'
        league.setPicUrl(url);
        leagueDao.save(league);
        return modelMapper.map(league, LeagueDto.class);
    }

    @Override
    public List<String> getLeagueNames() {
        return leagueDao.getAllNames();
    }
}
