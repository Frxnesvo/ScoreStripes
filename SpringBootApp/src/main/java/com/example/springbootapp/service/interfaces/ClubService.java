package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.Data.DTO.ClubDto;
import com.example.springbootapp.Data.DTO.ClubRequestDto;

public interface ClubService {
    ClubDto createClub(ClubRequestDto clubRequestDto);
}
