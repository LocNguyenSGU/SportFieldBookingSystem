package com.example.SportFieldBookingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SportFieldBookingSystem.Entity.Field;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Integer> {
    @Query("SELECT f " +
            "FROM Field f " +
            "JOIN FETCH f.fieldType ft " +
            "WHERE (:loai IS NULL OR ft.fieldTypeId = :loai) " +
            "AND (:ten IS NULL OR LOWER(f.fieldName) LIKE LOWER(CONCAT('%', :ten, '%'))) " +
            "AND (:diaChi IS NULL OR LOWER(f.fieldAddress) LIKE LOWER(CONCAT('%', :diaChi, '%')))")
    List<Field> findFieldsByCriteria(
            @Param("loai") Integer loai,
            @Param("ten") String ten,
            @Param("diaChi") String diaChi
    );

}
