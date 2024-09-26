package com.example.SportFieldBookingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SportFieldBookingSystem.Entity.FieldMaintenance;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldMaintenanceRepository extends JpaRepository<FieldMaintenance, Integer> {
}
