package com.example.SportFieldBookingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SportFieldBookingSystem.Entity.FieldFacility;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldFacilityRepository extends JpaRepository<FieldFacility, Integer> {
}
