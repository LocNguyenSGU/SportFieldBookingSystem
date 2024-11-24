package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.FieldFacilityDTO.FieldFacilityResponseDTO;
import com.example.SportFieldBookingSystem.DTO.FieldImageDTO.FieldImageCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldImageDTO.FieldImageResponseDTO;
import com.example.SportFieldBookingSystem.DTO.FieldImageDTO.FieldImageUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.FieldImage;
import com.example.SportFieldBookingSystem.Repository.FieldImageRepository;
import com.example.SportFieldBookingSystem.Service.FieldImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FieldImageServiceImpl implements FieldImageService {
    private final FieldImageRepository fieldImageRepository;

    @Autowired
    public FieldImageServiceImpl(FieldImageRepository fieldImageRepository) {
        this.fieldImageRepository = fieldImageRepository;
    }

    // Create
    public FieldImageResponseDTO createFieldImage(FieldImageCreateDTO fieldImageCreateDTO) {
        if (fieldImageCreateDTO == null) {
            throw new IllegalArgumentException("fieldImageCreateDTO cannot be null");
        }
        try {
            FieldImage fieldImage = new FieldImage();
            fieldImage.setImageUrl(fieldImageCreateDTO.getImageUrl());

            FieldImage fieldImgSave = fieldImageRepository.save(fieldImage);

            FieldImageResponseDTO fieldImageResponseDTO = new FieldImageResponseDTO();
            fieldImageResponseDTO.setFieldImageURL(fieldImgSave.getImageUrl());
            return fieldImageResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error creating field image", e);
        }
    }


    public FieldImageResponseDTO updateFieldImage(int id, FieldImageUpdateDTO fieldImageUpdateDTO) {
        try {
            FieldImage fieldImage = fieldImageRepository.findById(id).get();

            fieldImage.setImageUrl(fieldImageUpdateDTO.getImageUrl());
            FieldImage fieldImgSave = fieldImageRepository.save(fieldImage);

            FieldImageResponseDTO fieldImageResponseDTO = new FieldImageResponseDTO();
            fieldImageResponseDTO.setFieldImageURL(fieldImgSave.getImageUrl());
            return fieldImageResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error updating field image", e);
        }
    }

    public FieldImageResponseDTO getFieldImageById(int id) {
        try {
            FieldImage fieldImage = fieldImageRepository.findById(id).get();

            FieldImageResponseDTO fieldImageResponseDTO = new FieldImageResponseDTO();
            fieldImageResponseDTO.setFieldImageURL(fieldImage.getImageUrl());
            return fieldImageResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting field image", e);
        }
    }

    public List<FieldImageResponseDTO> getAllFieldImages() {
        List<FieldImageResponseDTO> fieldImageResponseDTOList = new ArrayList<>();
        try {
            List<FieldImage> fieldImageList = fieldImageRepository.findAll();
            for (FieldImage fieldImage : fieldImageList) {
                FieldImageResponseDTO fieldImageResponseDTO = new FieldImageResponseDTO();
                fieldImageResponseDTO.setFieldImageURL(fieldImage.getImageUrl());
                fieldImageResponseDTOList.add(fieldImageResponseDTO);
            }
            return fieldImageResponseDTOList;
        } catch (Exception e) {
            throw new RuntimeException("Error getting all field image", e);
        }
    }

    public void deleteFieldImage(int id) {
        try {
            FieldImage fieldImage = fieldImageRepository.findById(id).get();

            fieldImageRepository.delete(fieldImage);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting field image", e);
        }
    }

}
