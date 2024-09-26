package com.example.SportFieldBookingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldImage extends JpaRepository<FieldImage, Integer> {
}
