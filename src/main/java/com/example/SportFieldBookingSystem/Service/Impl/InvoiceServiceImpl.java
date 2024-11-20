package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceRequestDTO;
import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceResponseDTO;
import com.example.SportFieldBookingSystem.Entity.Invoice;

import java.util.List;

public interface InvoiceServiceImpl {
    public List<InvoiceResponseDTO> getAllInvoice();
    public InvoiceResponseDTO getInvoiceByInvoiceCode(String code);
    public Invoice createInvoice(InvoiceRequestDTO invoiceRequestDTO);
    public InvoiceResponseDTO updateInvoice(int id, InvoiceResponseDTO invoiceRequestDTO);
    public boolean deleteInvoice(int id);
}
