package com.example.SportFieldBookingSystem.DTO.InvoiceDTO;

import com.example.SportFieldBookingSystem.Entity.Booking;
import com.example.SportFieldBookingSystem.Enum.InvoiceEnum;

import java.util.Date;
import java.util.List;

public class InvoiceResponseDTO {
    private int invoiceId;
    private String invoiceCode;
    private List<Booking> bookingList;
    private Date invDate;
    private Double totalAmount;
    private InvoiceEnum paymentStatus;

    public InvoiceResponseDTO(){}

    public InvoiceResponseDTO(int invoiceId, String invoiceCode, List<Booking> bookingId, Date invDate, Double totalAmount, InvoiceEnum paymentStatus) {
        this.invoiceId = invoiceId;
        this.invoiceCode = invoiceCode;
        this.bookingList = bookingId;
        this.invDate = invDate;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
    }

    public InvoiceEnum getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(InvoiceEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getInvDate() {
        return invDate;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }
}
