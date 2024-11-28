package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.Entity.Invoice;
import com.example.SportFieldBookingSystem.Repository.BookingRepository;
import com.example.SportFieldBookingSystem.Repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingBatchProcessor {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public void processBatchUpdates() {
        // Lấy tất cả các hóa đơn cần xử lý
        List<Invoice> invoices = invoiceRepository.findAll();

        // Cập nhật tổng tiền từng hóa đơn
        for (Invoice invoice : invoices) {
            double totalAmount = bookingRepository.findTotalAmountByInvoiceId(invoice.getInvoiceId());
            invoice.setTotalAmount(totalAmount);
        }

        // Lưu hóa đơn cập nhật
        invoiceRepository.saveAll(invoices);
    }
}
