package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.FieldImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldImageRepository extends JpaRepository<FieldImage, Integer> {
}
