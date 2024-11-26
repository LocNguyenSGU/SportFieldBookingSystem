package com.example.SportFieldBookingSystem.Controller;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldTimeSlotDTO;
import com.example.SportFieldBookingSystem.Entity.Field;
import com.example.SportFieldBookingSystem.Repository.FieldRepository;
import com.example.SportFieldBookingSystem.Service.AIservice;
import com.example.SportFieldBookingSystem.Service.Impl.AIserviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatBotController {

    @Autowired
    private AIserviceImpl aiService;
    @Autowired
    private FieldRepository rp;

    @GetMapping("/prompt")
    public String getResponse(String prompt) {
        return aiService.callApi(prompt);

    }

    @GetMapping("/getField")
    public List<FieldTimeSlotDTO> getField() {
        return aiService.getAllFields();

    }
}