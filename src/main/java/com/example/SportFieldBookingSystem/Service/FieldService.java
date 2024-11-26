package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.FieldDTO.*;
import com.example.SportFieldBookingSystem.Entity.Field;
import com.example.SportFieldBookingSystem.Enum.FieldEnum;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface FieldService {
    // Đếm số lượng sân theo trạng thái
    long countFieldsByStatus(FieldEnum status);


    // Tạo mới một Field
    FieldGetDTO createField(FieldCreateDTO fieldCreateDTO);

    // Lấy thông tin chi tiết Field theo ID
    FieldGetDTO getFieldById(int fieldId);

    // Lấy danh sách tất cả các Field
    List<FieldListDTO> getAllFields();

    // Cập nhật Field theo ID
    FieldGetDTO updateField(int fieldId, FieldUpdateDTO fieldUpdateDTO);


    // Xóa Field theo ID
    boolean deleteField(int fieldId);

    // tìm field theo loại, tên, địa chỉ
    List<FieldListDTO> getFieldsByTimKiem(int loai, String ten, String diaChi);

    // Đếm tổng số sân
    long countTotalFields();
    Page<FieldGetDTO> searchFields(String fieldName, Integer fieldTypeId, Integer minCapacity, Integer maxCapacity, String fieldAddress, int page, int size);
}