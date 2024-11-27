package com.example.SportFieldBookingSystem.DTO.BookingDTO;

import com.example.SportFieldBookingSystem.Entity.Field;
import com.example.SportFieldBookingSystem.Entity.Invoice;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Enum.BookingEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingResponseDTO {
    private int bookingId;
    private String bookingCode;
    private int iduser;
    private int idField;
    private String fieldName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookingDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private BookingEnum status;
    private double totalPrice;
    private int idInvoice;


    public BookingResponseDTO( int bookingId, String bookingCode, int user, int field, LocalDate bookingDate, LocalTime startTime, LocalTime endTime, BookingEnum status, double totalPrice, int invoice) {
        this.bookingId = bookingId;
        this.bookingCode = bookingCode;
        this.iduser = user;
        this.idField = field;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.totalPrice = totalPrice;
        this.idInvoice = invoice;
    }
    public BookingResponseDTO(String fieldName, int bookingId, String bookingCode, int user, int field, LocalDate bookingDate, LocalTime startTime, LocalTime endTime, BookingEnum status, double totalPrice, int invoice) {
        this.bookingId = bookingId;
        this.bookingCode = bookingCode;
        this.iduser = user;
        this.idField = field;
        this.fieldName = fieldName;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.totalPrice = totalPrice;
        this.idInvoice = invoice;
    }
    public BookingResponseDTO(){}

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getUser() {
        return iduser;
    }

    public void setUser(int user) {
        this.iduser = user;
    }

    public int getField() {
        return idField;
    }

    public void setField(int field) {
        this.idField = field;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public BookingEnum getStatus() {
        return status;
    }

    public void setStatus(BookingEnum status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getInvoice() {
        return idInvoice;
    }

    public void setInvoice(int invoice) {
        this.idInvoice = invoice;
    }
}
