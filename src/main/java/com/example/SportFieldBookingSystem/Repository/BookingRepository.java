package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.Booking;
import com.example.SportFieldBookingSystem.Entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    public Booking findByBookingCode(String code);
    @Query("SELECT b FROM Booking b WHERE b.field.fieldId = :fieldId " +
            "AND b.bookingId <> :bookingId " +
            "AND b.bookingDate = :bookingDate " +
            "AND ((b.startTime < :endTime AND b.endTime > :startTime))")
    List<Booking> findOverlappingBookings(@Param("fieldId") int fieldId,
                                          @Param("bookingId") int bookingId,
                                          @Param("bookingDate") LocalDate bookingDate,
                                          @Param("startTime") LocalTime startTime,
                                          @Param("endTime") LocalTime endTime);

    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.invoice.invoiceId = :invoiceId AND b.status != 'CANCELLED'")
    double findTotalAmountByInvoiceId(@Param("invoiceId") int invoiceId);

    @Query("SELECT DISTINCT b.field FROM Booking b WHERE b.status != 'CANCELLED'")
    List<Field> findBookedFields();

    @Query("SELECT b.field, b.bookingDate, b.startTime, b.endTime FROM Booking b WHERE b.status != 'CANCELLED'")
    List<Object[]> findBookedFieldsWithTime();

    @Query("SELECT FUNCTION('MONTH', b.bookingDate) AS month, " +
            "       FUNCTION('YEAR', b.bookingDate) AS year, " +
            "       SUM(b.totalPrice) AS totalRevenue " +
            "FROM Booking b " +
            "WHERE b.status = 'PENDING' " +
            "GROUP BY FUNCTION('YEAR', b.bookingDate), FUNCTION('MONTH', b.bookingDate) " +
            "ORDER BY year, month")
    List<Object[]> getRevenueByMonthAndYear();

    @Query("SELECT FUNCTION('YEAR', b.bookingDate) AS year, " +
            "       (CASE " +
            "           WHEN FUNCTION('MONTH', b.bookingDate) BETWEEN 1 AND 3 THEN 1 " +
            "           WHEN FUNCTION('MONTH', b.bookingDate) BETWEEN 4 AND 6 THEN 2 " +
            "           WHEN FUNCTION('MONTH', b.bookingDate) BETWEEN 7 AND 9 THEN 3 " +
            "           WHEN FUNCTION('MONTH', b.bookingDate) BETWEEN 10 AND 12 THEN 4 " +
            "       END) AS quarter, " +
            "       SUM(b.totalPrice) AS totalRevenue " +
            "FROM Booking b " +
            "WHERE b.status = 'PENDING' " +
            "GROUP BY FUNCTION('YEAR', b.bookingDate), quarter " +
            "ORDER BY year, quarter")
    List<Object[]> getRevenueByQuarterAndYear();

    @Query("SELECT b.field.fieldId, b.field.fieldName, COUNT(b) AS bookingCount " +
            "FROM Booking b " +
            "WHERE b.status = 'PENDING' " +
            "GROUP BY b.field.fieldId, b.field.fieldName " +
            "ORDER BY bookingCount DESC")
    List<Object[]> getMostBookedFields();


}
