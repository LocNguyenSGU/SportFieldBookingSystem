package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.BookingDTO.Event;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotRequestDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotResponseDTO;
import com.example.SportFieldBookingSystem.Enum.TimeSlotEnum;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class BookingWebSocketController {
    private final static Map<Integer, Integer> pendingTimeSlots = new ConcurrentHashMap<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ModelMapper modelMapper;

    // Người dùng chọn Time Slot
    @MessageMapping("/selectTimeSlot")
    @SendTo("/topic/timeSlots")
    public TimeSlotResponseDTO selectTimeSlot(TimeSlotRequestDTO timeSlot) {
        if (pendingTimeSlots.containsKey(timeSlot.getId())) {
            // Nếu slot đã được chọn, gửi thông báo lỗi
            messagingTemplate.convertAndSendToUser(
                    String.valueOf(timeSlot.getUserId()),
                    "/topic/errors",
                    "Time slot này đã được chọn!"
            );
            return null;
        }

        // Thêm vào danh sách pending
        pendingTimeSlots.put(timeSlot.getId(), timeSlot.getUserId());
        timeSlot.setStatus(TimeSlotEnum.PENDING);

        return modelMapper.map(timeSlot, TimeSlotResponseDTO.class);
    }

    // Người dùng hủy Time Slot
    @MessageMapping("/deselectTimeSlot")
    public void deselectTimeSlot(TimeSlotRequestDTO timeSlot) {
        pendingTimeSlots.remove(timeSlot.getId());
        timeSlot.setStatus(TimeSlotEnum.AVAILABLE);
        messagingTemplate.convertAndSend("/topic/timeSlots", timeSlot);
    }
}

