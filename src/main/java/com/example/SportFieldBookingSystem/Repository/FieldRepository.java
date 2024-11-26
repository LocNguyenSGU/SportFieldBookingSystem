package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Enum.FieldEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Query("SELECT COUNT(f) FROM Field f")
    long countTotalFields();

    @Query("SELECT COUNT(f) FROM Field f WHERE f.status = :status")
    long countFieldsByStatus(@Param("status") FieldEnum status);
    @Query("SELECT f FROM Field f " +
            "JOIN f.fieldType ft " +
            "WHERE (:fieldName IS NULL OR f.fieldName LIKE %:fieldName%) " +
            "AND (COALESCE(:fieldTypeId, ft.fieldTypeId) = ft.fieldTypeId) " +
            "AND (:minCapacity IS NULL OR f.capacity >= :minCapacity) " +
            "AND (:maxCapacity IS NULL OR f.capacity <= :maxCapacity)")
    Page<Field> findFields(
            @Param("fieldName") String fieldName,
            @Param("fieldTypeId") Integer fieldTypeId,
            @Param("minCapacity") Integer minCapacity,
            @Param("maxCapacity") Integer maxCapacity,
            Pageable pageable
    );
}
