package com.example.SportFieldBookingSystem.DTO.TimeSlotDTO;

import com.example.SportFieldBookingSystem.Enum.TimeSlotEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class TimeSlotDTO {
    private int id;
    private Date date;
    private Time startTime;
    private Time endTime;
    private TimeSlotEnum status;
    @Override
    public String toString() {
        return "TimeSlotDTO{" +
                "id=" + id +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }
}
