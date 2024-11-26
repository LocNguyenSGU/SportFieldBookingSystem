package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldTimeSlotDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotDTO;
import com.example.SportFieldBookingSystem.Entity.Field;
import com.example.SportFieldBookingSystem.Entity.TimeSlot;
import com.example.SportFieldBookingSystem.Repository.FieldRepository;
import com.example.SportFieldBookingSystem.Service.AIservice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AIserviceImpl {
    @Autowired
    private FieldRepository fieldRepository;

    private RestTemplate restTemplate = new RestTemplate();
    private final String API_URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=%s";
    private final String API_KEY = "AIzaSyBnbH1Am_QaGKaryFiANDlJGf7U5T_kTns";
    public String callApi(String prompt) {
        String apiUrl = String.format(API_URL_TEMPLATE, API_KEY);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode contentNode = objectMapper.createObjectNode();
        ObjectNode partsNode = objectMapper.createObjectNode();

        List<FieldTimeSlotDTO> list = getAllFields();
        String data = listToString(list);

        String context ="Tôi sẽ cho bạn data về các sân và giờ trống, nếu khách cung cấp thông tin về ngày giờ thì hãy chọn data phù hợp và báo cho khách, nếu khách không cung cấp thôn tin ngày giờ thì hỏi khách. Đây là data:"+data+". Đây là câu hỏi của khách:"+prompt;
        // Dùng thông tin từ District làm nội dung yêu cầu gửi đến API Gemini
        partsNode.put("text", context);

        contentNode.set("parts", objectMapper.createArrayNode().add(partsNode));

        ObjectNode requestBodyNode = objectMapper.createObjectNode();
        requestBodyNode.set("contents", objectMapper.createArrayNode().add(contentNode));

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyNode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to construct JSON request body", e);
        }

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        return response.getBody();
    }

    public List<FieldTimeSlotDTO> getAllFields() {
        List<Field> fields = fieldRepository.findAll();  // Lấy tất cả các Field từ DB
        List<FieldTimeSlotDTO> fieldTimeSlotDTOList = new ArrayList<>();

        for (Field field : fields) {
            FieldTimeSlotDTO dto = new FieldTimeSlotDTO();
            dto.setName(field.getFieldName());  // Chuyển trường tên sân
            // Chuyển đổi danh sách TimeSlot sang DTO
            List<TimeSlotDTO> timeSlotDTOList = convertTimeSlotsToDTO(field.getTimeSlotList());
            dto.setListTimeSlot(timeSlotDTOList);  // Gán danh sách TimeSlotDTO

            fieldTimeSlotDTOList.add(dto);
        }

        return fieldTimeSlotDTOList;
    }

    // Phương thức chuyển đổi TimeSlot sang TimeSlotDTO
    private List<TimeSlotDTO> convertTimeSlotsToDTO(List<TimeSlot> timeSlots) {
        List<TimeSlotDTO> timeSlotDTOList = new ArrayList<>();
        for (TimeSlot timeSlot : timeSlots) {
            TimeSlotDTO dto = new TimeSlotDTO();
            dto.setId(timeSlot.getTimeslotId());
            dto.setDate(timeSlot.getDate());
            dto.setStartTime(timeSlot.getStartTime());
            dto.setEndTime(timeSlot.getEndTime());
            dto.setStatus(timeSlot.getStatus());
            timeSlotDTOList.add(dto);
        }
        return timeSlotDTOList;
    }

    public static String listToString(List<FieldTimeSlotDTO> fieldTimeSlotDTOList) {
        if (fieldTimeSlotDTOList == null || fieldTimeSlotDTOList.isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < fieldTimeSlotDTOList.size(); i++) {
            FieldTimeSlotDTO fieldTimeSlotDTO = fieldTimeSlotDTOList.get(i);
            sb.append(fieldTimeSlotDTO.toString()); // Gọi phương thức toString của FieldTimeSlotDTO
            if (i < fieldTimeSlotDTOList.size() - 1) {
                sb.append(", "); // Thêm dấu phẩy giữa các đối tượng trong danh sách
            }
        }

        sb.append("]");
        return sb.toString();
    }
}
