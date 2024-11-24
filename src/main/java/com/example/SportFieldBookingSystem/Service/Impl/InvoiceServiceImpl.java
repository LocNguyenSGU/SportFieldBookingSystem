package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceBookingRequestDTO;
import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceBookingResponseDTO;
import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceResponseDTO;

import java.util.List;

public interface InvoiceServiceImpl {
    public List<InvoiceResponseDTO> getAllInvoice();
    public InvoiceResponseDTO getInvoiceByInvoiceCode(String code);
    public InvoiceBookingResponseDTO createInvoice(InvoiceBookingRequestDTO invoiceRequestDTO);
//    public InvoiceResponseDTO updateInvoice(int id, InvoiceResponseDTO invoiceRequestDTO);
    public boolean deleteInvoice(int id);
}
