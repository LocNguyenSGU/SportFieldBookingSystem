package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    public Invoice findByInvoiceCode(String code);
}
