package com.example.SportFieldBookingSystem.Service;


import com.example.SportFieldBookingSystem.DTO.InvalidToken.InvalidTokenDTO;

public interface InvalidTokenService {
    boolean existsByIdToken(String idToken);
    void saveInvalidTokenIntoDatabase(InvalidTokenDTO invalidTokenDTO);
}
