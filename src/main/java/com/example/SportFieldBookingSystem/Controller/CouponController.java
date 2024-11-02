package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.CouponDTO.CouponResponseDTO;
import com.example.SportFieldBookingSystem.Entity.Coupon;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Mapper.CouponMapper;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Repository.CouponRespository;
import com.example.SportFieldBookingSystem.Repository.UserRepository;
import com.example.SportFieldBookingSystem.Service.Impl.CouponServiceImpl;
import com.example.SportFieldBookingSystem.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class CouponController {

    private UserRepository uRepo;
    final private CouponServiceImpl couponServiceImpl;

    @Autowired
    public CouponController(CouponServiceImpl couponServiceImpl, UserRepository uRepo) {
        this.couponServiceImpl = couponServiceImpl;
        this.uRepo = uRepo;
    }

    // Lấy danh sách tất cả các coupon
    @GetMapping("/listCoupons")
    public ResponseEntity<ResponseData> getAllCoupons() {
        ResponseData responseData = new ResponseData();

        try {
            List<CouponResponseDTO> coupons = couponServiceImpl.getAllCoupons();
            if (coupons.isEmpty()) {
                responseData.setMessage("No coupons found");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
            }

            responseData.setMessage("Get all coupons success");
            responseData.setData(coupons);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("An error occurred while fetching coupons: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

    // Lấy thông tin chi tiết coupon theo ID
    @GetMapping("/coupon/{id}")
    public ResponseEntity<ResponseData> getCouponById(@PathVariable int id) {
        ResponseData responseData = new ResponseData();

        try {
            CouponResponseDTO coupon = couponServiceImpl.getCouponById(id);
            if (coupon == null) {
                responseData.setMessage("Coupon not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
            }

            responseData.setMessage("Get coupon success");
            responseData.setData(coupon);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("An error occurred while fetching the coupon: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

    // Thêm mới Coupon
    @PostMapping("/coupon")
    public ResponseEntity<ResponseData> createCoupon(@RequestBody CouponResponseDTO coupon) {
        ResponseData responseData = new ResponseData();
        Optional<User> u = uRepo.findById(coupon.getUser());
        try {
            // Kiểm tra nếu coupon đã tồn tại
            boolean exists = couponServiceImpl.isCouponExists(coupon.getCode());
            if (exists) {
                responseData.setMessage("Coupon already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(responseData);
            }
            Coupon cpMapper = CouponMapper.toEntity(coupon, u.get());
            // Tạo coupon mới
            CouponResponseDTO createdCoupon = couponServiceImpl.createCoupon(cpMapper);
            responseData.setMessage("Create coupon success");
            responseData.setData(createdCoupon);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
        } catch (Exception e) {
            responseData.setMessage("An error occurred while creating the coupon: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

    // Xóa Coupon
    @DeleteMapping("/deleteCoupon/{id}")
    public ResponseEntity<ResponseData> deleteCoupon(@PathVariable int id) {
        ResponseData responseData = new ResponseData();

        try {
            boolean isDeleted = couponServiceImpl.deleteCoupon(id);
            if (isDeleted) {
                responseData.setMessage("Delete coupon success");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
            } else {
                responseData.setMessage("Coupon not found for deletion");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
            }
        } catch (Exception e) {
            responseData.setMessage("An error occurred while deleting the coupon: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

    // Sửa Coupon
    @PutMapping("/coupon/{id}")
    public ResponseEntity<ResponseData> updateCoupon(@PathVariable int id, @RequestBody CouponResponseDTO couponDetails) {
        ResponseData responseData = new ResponseData();

        try {
            // Kiểm tra coupon có tồn tại không
            CouponResponseDTO existingCoupon = couponServiceImpl.getCouponById(id);
            boolean exists = couponServiceImpl.isCouponExists(couponDetails.getCode());
            if(exists && !Objects.equals(existingCoupon.getCode(), couponDetails.getCode())){
                responseData.setMessage("This code is already existed");
                responseData.setStatusCode(401);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
            }
            if (existingCoupon == null) {
                responseData.setMessage("Coupon not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
            }

            // Cập nhật coupon
            CouponResponseDTO updatedCoupon = couponServiceImpl.updateCoupon(id, couponDetails);
            responseData.setMessage("Update coupon success");
            responseData.setData(updatedCoupon);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("An error occurred while updating the coupon: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

}
