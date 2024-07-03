package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dto.ClubDto;
import com.example.springbootapp.data.dto.ClubRequestDto;
import com.example.springbootapp.data.dao.ClubDao;
import com.example.springbootapp.data.dao.LeagueDao;
import com.example.springbootapp.data.entities.Club;
import com.example.springbootapp.data.entities.League;
import com.example.springbootapp.data.specification.ClubSpecification;
import com.example.springbootapp.service.interfaces.AwsS3Service;
import com.example.springbootapp.service.interfaces.ClubService;
import com.example.springbootapp.utils.Constants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final AwsS3Service awsS3Service;
    private final ClubDao clubDao;
    private final LeagueDao leagueDao;
    private final ModelMapper modelMapper;

    @Override
    public ClubDto createClub(ClubRequestDto clubRequestDto) {
        if(clubDao.existsByName(clubRequestDto.getName())){
            throw new RuntimeException("Club already exists");   //TODO: DA CAMBIARE IN CUSTOM EXCEPTION
        }
        Club club = new Club();
        club.setName(clubRequestDto.getName());
        String url = awsS3Service.uploadFile(clubRequestDto.getPic(), Constants.CLUB_FOLDER, clubRequestDto.getName());
        club.setPicUrl(url);
        League league = leagueDao.findByName(clubRequestDto.getLeagueName());
        if(league == null){
            throw new EntityNotFoundException("League not found");
        }
        club.setLeague(league);
        clubDao.save(club);
        return modelMapper.map(club, ClubDto.class);
    }

    @Override
    public List<String> getClubNames() {
        return clubDao.getAllNames();
    }

    @Override
    public List<ClubDto> getClubs(String leagueName) {
        Specification<Club> spec = Specification.where(null);
        if(leagueName != null){
            spec = spec.and(ClubSpecification.leagueNameEquals(leagueName));
        }
        List<Club> clubs = clubDao.findAll(spec);
        return clubs.stream()
                .map(club -> modelMapper.map(club, ClubDto.class))
                .toList();
    }
}
