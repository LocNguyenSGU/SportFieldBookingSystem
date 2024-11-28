package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.Entity.FieldImage;
import com.example.SportFieldBookingSystem.Service.FieldImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/field")
public class FieldImageController {
    @Autowired
    private FieldImageService fieldImageService;

    @PostMapping("/{fieldId}/images")
    public ResponseEntity<?> uploadImages(
            @PathVariable int fieldId,
            @RequestParam("files") MultipartFile[] files) {
        System.out.println("chay toi uploadImages controller");

        List<String> imageUrls = fieldImageService.uploadImagesToField(fieldId, files);
        return ResponseEntity.ok(Map.of(
                "message", "Images uploaded successfully",
                "imageUrls", imageUrls
        ));
    }

}
