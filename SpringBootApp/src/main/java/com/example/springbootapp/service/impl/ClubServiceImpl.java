package com.example.springbootapp.service.impl;

import com.example.springbootapp.Data.DTO.ClubDto;
import com.example.springbootapp.Data.DTO.ClubRequestDto;
import com.example.springbootapp.Data.Dao.ClubDao;
import com.example.springbootapp.Data.Entities.Club;
import com.example.springbootapp.service.interfaces.AwsS3Service;
import com.example.springbootapp.service.interfaces.ClubService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final AwsS3Service awsS3Service;
    private final ClubDao clubDao;
    private final ModelMapper modelMapper;

    @Override
    public ClubDto createClub(ClubRequestDto clubRequestDto) {
        if(clubDao.existsByName(clubRequestDto.getName())){
            throw new RuntimeException("Club already exists");   //TODO: DA CAMBIARE IN CUSTOM EXCEPTION
        }
        Club club = new Club();
        club.setName(clubRequestDto.getName());
        String url = awsS3Service.uploadFile(clubRequestDto.getPic(), "clubs", clubRequestDto.getName());   //FIXME: NON MI PIACE PASSARE IL FOLDER COSI'
        club.setPicUrl(url);
        clubDao.save(club);
        return modelMapper.map(club, ClubDto.class);
    }

    @Override
    public List<String> getClubNames() {
        return clubDao.getAllNames();
    }
}
