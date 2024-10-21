package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.FieldImageDTO.FieldImageCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldImageDTO.FieldImageResponseDTO;
import com.example.SportFieldBookingSystem.DTO.FieldImageDTO.FieldImageUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.FieldImage;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Repository.FieldImageRepository;
import com.example.SportFieldBookingSystem.Service.FieldImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.URISyntax;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class FieldImageController {
    private final FieldImageService fieldImageService;

    @Autowired
    public FieldImageController(FieldImageService fieldImageService) {
        this.fieldImageService = fieldImageService;
    }

    @PostMapping
    public ResponseEntity<?> createFieldImage(@RequestBody FieldImageCreateDTO fieldImage) throws URISyntaxException {
        FieldImageResponseDTO saveDTO = fieldImageService.createFieldImage(fieldImage);
        return ResponseEntity(new URI("/api/images" + saveDTO.getFieldImageId())).body(saveDTO);
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<?> updateFieldImage(@PathVariable int id, @RequestBody FieldImageUpdateDTO fieldImage) {
        ResponseData responseData = new ResponseData();
        try {
            FieldImageResponseDTO fieldImageResponseDTO = fieldImageService.updateFieldImage(id, fieldImage);
            if (fieldImageResponseDTO != null) {
                responseData.setData(fieldImageResponseDTO);
                responseData.setMessage("FieldImage updated successfully");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            }
            responseData.setMessage("FieldImage update failed");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            responseData.setMessage(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllFieldImage() {
        ResponseData responseData = new ResponseData();
        try {
            List<FieldImageResponseDTO> fieldImageResponseDTOList = fieldImageService.getAllFieldImages();
            if (fieldImageResponseDTOList != null) {
                responseData.setData(fieldImageResponseDTOList);
                responseData.setMessage("All field image retrieved successfully");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            }
            responseData.setMessage("Failded to get All field image");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            responseData.setMessage(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFieldImage(@PathVariable int id) {
        ResponseData responseData = new ResponseData();
        try {
            FieldImageResponseDTO fieldImageResponseDTO = fieldImageService.getFieldImageById(id);
            if (fieldImageResponseDTO != null) {
                responseData.setData(fieldImageResponseDTO);
                responseData.setMessage("FieldImage retrieved successfully");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            }
            responseData.setMessage("Failded to get FieldImage by id");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            responseData.setMessage(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFieldImage(@PathVariable int id) {
        ResponseData responseData = new ResponseData();
        try {
            fieldImageService.deleteFieldImage(id);
            responseData.setMessage("FieldImage deleted successfully");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setMessage(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
