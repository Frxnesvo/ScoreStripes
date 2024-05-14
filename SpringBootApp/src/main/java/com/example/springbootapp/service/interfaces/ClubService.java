package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.ClubDto;
import com.example.springbootapp.data.dto.ClubRequestDto;

import java.util.List;

public interface ClubService {
    ClubDto createClub(ClubRequestDto clubRequestDto);
    List<String> getClubNames();
}
