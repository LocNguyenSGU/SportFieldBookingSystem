package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.FieldImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldImageRepository extends JpaRepository<FieldImage, Integer> {
    // Tìm FieldImage dựa trên URL và Field ID
    @Query(value = "SELECT * FROM field_image WHERE image_url = :imageUrl AND field_id = :fieldId", nativeQuery = true)
    Optional<FieldImage> findFieldImageNative(@Param("imageUrl") String imageUrl, @Param("fieldId") int fieldId);
}
