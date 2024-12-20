package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.FieldTimeRuleDTO.FieldTimeRuleCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldTimeRuleDTO.FieldTimeRuleDTO;
import com.example.SportFieldBookingSystem.Service.FieldTimeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/fieldTimeRules")
public class FieldTimeRuleController {
    private final FieldTimeRuleService fieldTimeRuleService;

    @Autowired
    public FieldTimeRuleController(FieldTimeRuleService fieldTimeRuleService) {
        this.fieldTimeRuleService = fieldTimeRuleService;
    }

    @PostMapping
    public ResponseEntity<?> createFieldTimeRule(@RequestBody FieldTimeRuleCreateDTO fieldTimeRuleCreateDTO) throws URISyntaxException {
        if(fieldTimeRuleService.isTimeSlotConflict(fieldTimeRuleCreateDTO)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi thời gian trống của sân bị trùng khi tạo");
        }
        FieldTimeRuleDTO result = fieldTimeRuleService.createFieldTimeRule(fieldTimeRuleCreateDTO);
        return ResponseEntity.created(new URI("/api/fieldTimeRules" + result.getField().getFieldId())).body(result);
    }

    @GetMapping
    public List<FieldTimeRuleDTO> getAllFieldTimeRules() {
        return fieldTimeRuleService.getAllFieldTimeRule();
    }

    @DeleteMapping("/{id}")
    public boolean deleteFieldTimeRule(@PathVariable int id) {
        return fieldTimeRuleService.deleteFieldTimeRule(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFieldTimeRule(@PathVariable int id, @RequestBody FieldTimeRuleDTO fieldTimeRuleDTO) {
        FieldTimeRuleDTO rs = fieldTimeRuleService.updateFieldTimeRule(id, fieldTimeRuleDTO);
        return ResponseEntity.ok(rs);
    }

    @GetMapping("/getByFieldId")
    public ResponseEntity<?> getFieldTimeRulesByFieldId(
            @RequestParam int fieldId,
            @RequestParam int page,
            @RequestParam int pageSize) {
        Page<FieldTimeRuleDTO> fieldTimeRules = fieldTimeRuleService.getFieldTimeRuleByFieldId(fieldId, page, pageSize);
        return ResponseEntity.ok(fieldTimeRules);
    }

    @GetMapping("/checkBeforeDelete")
    public ResponseEntity<?> checkBeforeDelete(@RequestParam int id) {
        boolean isBooked = fieldTimeRuleService.isTimeSlotBooked(id);
        if (isBooked) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Tồn tại 1 time slot đã được book");
        }
        return ResponseEntity.ok("Time slot is available for deletion.");
    }

}
