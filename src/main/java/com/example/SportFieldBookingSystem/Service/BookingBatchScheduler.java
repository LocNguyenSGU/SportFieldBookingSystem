package com.example.SportFieldBookingSystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BookingBatchScheduler {
    private final BookingBatchProcessor bookingBatchProcessor;

    @Autowired
    BookingBatchScheduler(BookingBatchProcessor bookingBatchProcessor) {
        this.bookingBatchProcessor = bookingBatchProcessor;
    }

    @Scheduled(cron = "0 0 * * * ?") // Chạy mỗi giờ
    public void scheduleBatchProcessing() {
        bookingBatchProcessor.processBatchUpdates();
    }
}
