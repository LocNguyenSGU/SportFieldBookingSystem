package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.InvalidToken.InvalidTokenDTO;
import com.example.SportFieldBookingSystem.Entity.InvalidToken;
import com.example.SportFieldBookingSystem.Repository.InvalidTokenRepository;
import com.example.SportFieldBookingSystem.Service.InvalidTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@EnableScheduling
@Service
public class InvalidTokenServiceImpl implements InvalidTokenService {
    @Autowired
    private InvalidTokenRepository invalidTokenRepository;

    @Override
    public boolean existsByIdToken(String idToken) {
        return invalidTokenRepository.existsByIdToken(idToken);
    }

    @Override
    public void saveInvalidTokenIntoDatabase(InvalidTokenDTO invalidTokenDTO) {
        InvalidToken invalidToken = new InvalidToken();
        invalidToken.setIdToken(invalidTokenDTO.getIdToken());
        invalidToken.setExpiryDate(invalidTokenDTO.getExpiryDate());
        invalidTokenRepository.save(invalidToken);
    }

    @Scheduled(cron = "0 0 0 * * ?") // chay nua dem moi ngay
//    @Scheduled(fixedRate = 86400000) // Chạy mỗi 24 giờ
    @Transactional
    public void cleanupExpiredTokens() {
        invalidTokenRepository.deleteByExpiryDateBefore(new Date()); // Xóa các token hết hạn
        System.out.println("Deleted expired tokens count");
    }
}
