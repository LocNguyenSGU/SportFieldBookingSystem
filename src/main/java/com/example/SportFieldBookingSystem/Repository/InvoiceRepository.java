package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    public Invoice findByInvoiceCode(String code);
//    @Query("SELECT i FROM Invoice i WHERE i.bookingList.user.id = :userId")
//    List<Invoice> findByUserId(@Param("userId") int userId);
        @Query("SELECT COUNT(f) FROM Field f")
        long countTotalFields();
}

