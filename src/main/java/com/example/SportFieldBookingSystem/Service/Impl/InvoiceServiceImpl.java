package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceResponseDTO;

import java.util.List;

public interface InvoiceServiceImpl {
    public List<InvoiceResponseDTO> getAllInvoice();
    public InvoiceResponseDTO getInvoiceByInvoiceCode(String code);
    public InvoiceResponseDTO createInvoice(InvoiceResponseDTO invoiceRequestDTO);
    public InvoiceResponseDTO updateInvoice(int id, InvoiceResponseDTO invoiceRequestDTO);
    public boolean deleteInvoice(int id);
}
