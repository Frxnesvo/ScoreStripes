package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.Data.DTO.ClubDto;
import com.example.springbootapp.Data.DTO.ClubRequestDto;

import java.util.List;

public interface ClubService {
    ClubDto createClub(ClubRequestDto clubRequestDto);
    List<String> getClubNames();
}
