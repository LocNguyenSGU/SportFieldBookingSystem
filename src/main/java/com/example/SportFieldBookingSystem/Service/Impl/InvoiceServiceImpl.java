package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface InvoiceServiceImpl {
    public List<InvoiceResponseDTO> getAllInvoice();
    public InvoiceResponseDTO getInvoiceByInvoiceCode(String code);
    public InvoiceBookingResponseDTO createInvoice(InvoiceBookingRequestDTO invoiceRequestDTO);
//    public InvoiceResponseDTO updateInvoice(int id, InvoiceResponseDTO invoiceRequestDTO);
    public boolean deleteInvoice(int id);

    public List<InvoiceThongKeDTO> findInvoicesByDateRange(Date startDate, Date endDate);

    public TKTongQuatDTO getTKTongQuat(Date startdate, Date endDate);
    public List<InvoiceResponseDTO> getInvoiceByDate(Date start, Date end);
}
