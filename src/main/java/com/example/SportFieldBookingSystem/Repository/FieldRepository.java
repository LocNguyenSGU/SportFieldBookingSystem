package com.example.SportFieldBookingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SportFieldBookingSystem.Entity.Field;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, Integer> {

}
