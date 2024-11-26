package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.FieldMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldMaintenanceRepository extends JpaRepository<FieldMaintenance, Integer> {
}
