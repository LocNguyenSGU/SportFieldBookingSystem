package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.FieldType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldTypeRepository extends JpaRepository<FieldType, Integer> {
    @Query("SELECT f FROM FieldType f WHERE LOWER(f.fieldTypeName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<FieldType> searchByFieldTypeName(@Param("keyword") String keyword, Pageable pageable);
}
