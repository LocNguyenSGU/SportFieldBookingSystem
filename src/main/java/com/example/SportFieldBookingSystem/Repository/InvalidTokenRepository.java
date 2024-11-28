package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {
    boolean existsByIdToken(String idToken);
    void deleteByExpiryDateBefore(Date date);
}
