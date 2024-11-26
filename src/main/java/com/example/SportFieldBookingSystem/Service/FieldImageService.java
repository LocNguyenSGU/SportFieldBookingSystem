package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.FieldImageDTO.FieldImageCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldImageDTO.FieldImageResponseDTO;
import com.example.SportFieldBookingSystem.DTO.FieldImageDTO.FieldImageUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FieldImageService {
//    // Create FieldImage
//    FieldImageResponseDTO createFieldImage(FieldImageCreateDTO fieldImageCreateDTO);
//    // Find fieldimage by its id
//    FieldImageResponseDTO getFieldImageById(int id);
//    // Get list FieldImage
//    List<FieldImageResponseDTO> getAllFieldImages();
//    // Update FieldImage
//    FieldImageResponseDTO updateFieldImage(int id, FieldImageUpdateDTO
//                                                   fieldImageUpdateDTO);
//    void deleteFieldImage(int id);

    List<String> uploadImagesToField(int fieldId, MultipartFile[] files);

}
