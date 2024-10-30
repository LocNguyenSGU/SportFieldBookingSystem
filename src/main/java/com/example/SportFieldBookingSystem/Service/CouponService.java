package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.CouponDTO.CouponResponseDTO;
import com.example.SportFieldBookingSystem.Entity.Coupon;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Repository.CouponRespository;
import com.example.SportFieldBookingSystem.Repository.UserRepository;
import com.example.SportFieldBookingSystem.Service.Impl.CouponServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponService implements CouponServiceImpl {
    private final CouponRespository couponRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    public CouponService(CouponRespository couponRepo) {
        this.couponRepo = couponRepo;
    }

    @Override
    public List<CouponResponseDTO> getAllCoupons() {
        List<CouponResponseDTO> listCouponDTO = new ArrayList<>();

        try {
            List<Coupon> listCoupon = couponRepo.findAll();
            for (Coupon coupon : listCoupon) {
                CouponResponseDTO couponResponseDTO = new CouponResponseDTO();
                couponResponseDTO.setCouponId(coupon.getCouponId());
                couponResponseDTO.setUser(coupon.getUser().getUserId());
                couponResponseDTO.setCode(coupon.getCode());
                couponResponseDTO.setExpirationDate(coupon.getExpirationDate());
                couponResponseDTO.setDiscountValue(coupon.getDiscountValue());

                listCouponDTO.add(couponResponseDTO);
            }
        } catch (Exception e) {
            System.err.println("Error fetching all coupons: " + e.getMessage());
        }

        return listCouponDTO;
    }

    @Override
    public CouponResponseDTO getCouponById(int id) {
        try {
            Coupon coupon = couponRepo.findById(id).orElse(null);
            if (coupon != null) {
                return new CouponResponseDTO(coupon.getCouponId(), coupon.getUser().getUserId(), coupon.getCode(),
                        coupon.getExpirationDate(), coupon.getDiscountValue());
            }
        } catch (Exception e) {
            System.err.println("Error fetching coupon by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public CouponResponseDTO createCoupon(Coupon coupon) {
        try {
            Coupon savedCoupon = couponRepo.save(coupon);
            return new CouponResponseDTO(savedCoupon.getCouponId(), savedCoupon.getUser().getUserId(),
                    savedCoupon.getCode(), savedCoupon.getExpirationDate(), savedCoupon.getDiscountValue());
        } catch (Exception e) {
            System.err.println("Error creating coupon: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteCoupon(int id) {
        try {
            couponRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting coupon: " + e.getMessage());
            return false;
        }
    }

    @Override
    public CouponResponseDTO updateCoupon(int id, CouponResponseDTO couponDetails) {
        try {
            Coupon existingCoupon = couponRepo.findById(id).orElse(null);
            if (existingCoupon != null) {
                User u = userRepo.findById(1).get();
                existingCoupon.setUser(u);
                existingCoupon.setCode(couponDetails.getCode());
                existingCoupon.setExpirationDate(couponDetails.getExpirationDate());
                existingCoupon.setDiscountValue(couponDetails.getDiscountValue());

                Coupon updatedCoupon = couponRepo.save(existingCoupon);
                return new CouponResponseDTO(updatedCoupon.getCouponId(), updatedCoupon.getUser().getUserId(),
                        updatedCoupon.getCode(), updatedCoupon.getExpirationDate(), updatedCoupon.getDiscountValue());
            }
        } catch (Exception e) {
            System.err.println("Error updating coupon: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean isCouponExists(String code) {
        return couponRepo.existsByCode(code);
    }
}
