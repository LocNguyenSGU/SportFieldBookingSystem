package com.example.SportFieldBookingSystem.DTO.FieldDTO;

import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
public class FieldTimeSlotDTO {
    String name;
    List<TimeSlotDTO> listTimeSlot;
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FieldTimeSlotDTO{")
                .append("name='").append(name).append('\'')
                .append(", listTimeSlot=").append(listTimeSlot != null ? listTimeSlot.toString() : "[]")
                .append('}');
        return sb.toString();
    }
}
