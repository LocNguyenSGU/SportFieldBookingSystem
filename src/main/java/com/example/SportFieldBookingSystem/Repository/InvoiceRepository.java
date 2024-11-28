package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    public Invoice findByInvoiceCode(String code);
//    @Query("SELECT i FROM Invoice i WHERE i.bookingList.user.id = :userId")
//    List<Invoice> findByUserId(@Param("userId") int userId);
        @Query("SELECT COUNT(f) FROM Field f")
        long countTotalFields();

    // Lấy hóa đơn trong khoảng ngày
    @Query("SELECT i FROM Invoice i WHERE i.invDate BETWEEN :startDate AND :endDate")
    List<Invoice> findInvoicesByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    @Query("SELECT COUNT(DISTINCT i.invoiceId), " +
            "SUM(b.totalPrice), " +
            "SUM(b.totalPrice) / COUNT(DISTINCT i.invoiceId), " +
            "COUNT(DISTINCT b.bookingId) " +
            "FROM Invoice i " +
            "LEFT JOIN Booking b ON i.invoiceId = b.invoice.invoiceId " +
            "WHERE i.invDate BETWEEN :startDate AND :endDate")
    List<Object[]> getTKTongQuatRaw(@Param("startDate") Date startDate,
                                    @Param("endDate") Date endDate);
}

