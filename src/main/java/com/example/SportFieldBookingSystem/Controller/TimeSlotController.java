package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.FieldImageDTO.FieldImageResponseDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotCreateDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotResponseDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.TimeSlot;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/timeSlot")
public class TimeSlotController {
    private final TimeSlotService timeSlotService;

    @Autowired
    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @PostMapping()
    public ResponseEntity<?> createTimeSlot(@RequestBody TimeSlotCreateDTO timeSlotCreateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            TimeSlotResponseDTO timeSlot = timeSlotService.createTimeSlot(timeSlotCreateDTO);
            if (timeSlot != null) {
                responseData.setMessage("TimeSlot created successfully");
                responseData.setData(timeSlot);
                return ResponseEntity.ok(responseData);
            }
            responseData.setMessage("TimeSlot creation failed");
            responseData.setData(null);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("TimeSlot creation failed");
            responseData.setData(e);
            return ResponseEntity.ok(responseData);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTimeSlot(@RequestBody TimeSlotUpdateDTO timeSlotUpdateDTO, @PathVariable int id) {
        ResponseData responseData = new ResponseData();
        try {
            TimeSlotResponseDTO timeSlot = timeSlotService.updateTimeSlot(id, timeSlotUpdateDTO);
            if (timeSlot != null) {
                responseData.setMessage("TimeSlot updated successfully");
                responseData.setData(timeSlot);
                return ResponseEntity.ok(responseData);
            }
            responseData.setMessage("TimeSlot update failed");
            responseData.setData(null);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("TimeSlot update failed");
            responseData.setData(e);
            return ResponseEntity.ok(responseData);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllTimeSlot()
    {
        ResponseData responseData = new ResponseData();
        try {
            List<TimeSlotResponseDTO> timeSlots = timeSlotService.getAllTimeSlots();
            if (timeSlots != null) {
                responseData.setMessage("TimeSlots found");
                responseData.setData(timeSlots);
                return ResponseEntity.ok(responseData);
            }
            responseData.setMessage("TimeSlots not found");
            responseData.setData(null);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("TimeSlots not found");
            responseData.setData(e);
            return ResponseEntity.ok(responseData);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTimeSlot(@PathVariable int id) {
        ResponseData responseData = new ResponseData();
        try {
            TimeSlotResponseDTO timeSlot = timeSlotService.getTimeSlot(id);
            if (timeSlot != null) {
                responseData.setMessage("TimeSlot found");
                responseData.setData(timeSlot);
                return ResponseEntity.ok(responseData);
            }
            responseData.setMessage("TimeSlot not found");
            responseData.setData(null);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("TimeSlot not found");
            responseData.setData(e);
            return ResponseEntity.ok(responseData);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTimeSlot(@PathVariable int id) {
        ResponseData responseData = new ResponseData();
        try {
            timeSlotService.deleteTimeSlot(id);
            responseData.setMessage("TimeSlot deleted successfully");
            responseData.setData(null);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("TimeSlot deletion failed");
            responseData.setData(e);
            return ResponseEntity.ok(responseData);
        }
    }
}
