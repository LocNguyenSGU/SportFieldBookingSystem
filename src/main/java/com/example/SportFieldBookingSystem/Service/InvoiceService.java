package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceRequestDTO;
import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceResponseDTO;
import com.example.SportFieldBookingSystem.Entity.Booking;
import com.example.SportFieldBookingSystem.Entity.Invoice;
import com.example.SportFieldBookingSystem.Enum.BookingEnum;
import com.example.SportFieldBookingSystem.Enum.InvoiceEnum;
import com.example.SportFieldBookingSystem.Repository.InvoiceRepository;
import com.example.SportFieldBookingSystem.Service.Impl.InvoiceServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService implements InvoiceServiceImpl {

    private final InvoiceRepository invoiceRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepo, ModelMapper modelMapper){
        this.invoiceRepo = invoiceRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<InvoiceResponseDTO> getAllInvoice() {

        List<InvoiceResponseDTO> listInvoiceDTO = new ArrayList<>();

        try {
            List<Invoice> listInvoice = invoiceRepo.findAll();
            for (Invoice invoice : listInvoice) {
                InvoiceResponseDTO invoiceResponseDTO = new InvoiceResponseDTO();
                invoiceResponseDTO.setInvoiceId(invoice.getInvoiceId());
                invoiceResponseDTO.setInvoiceCode(invoice.getInvoiceCode());
                invoiceResponseDTO.setInvDate(invoice.getInvDate());
                invoiceResponseDTO.setTotalAmount(invoice.getTotalAmount());
                invoiceResponseDTO.setPaymentStatus(invoice.getStatus());

                listInvoiceDTO.add(invoiceResponseDTO);
            }
        } catch (Exception e) {
            // Log the error message for debugging
            System.err.println("Error fetching all invoices: " + e.getMessage());
            // Optional: you can throw a custom exception here
        }
        return listInvoiceDTO;

    }

    @Override
    public InvoiceResponseDTO getInvoiceByInvoiceCode(String code) {
        InvoiceResponseDTO invoiceResponseDTO = new InvoiceResponseDTO();
        try {
            Invoice invoice = invoiceRepo.findByInvoiceCode(code);
            invoiceResponseDTO.setInvoiceId(invoice.getInvoiceId());
            invoiceResponseDTO.setInvoiceCode(invoice.getInvoiceCode());
            invoiceResponseDTO.setInvDate(invoice.getInvDate());
            invoiceResponseDTO.setTotalAmount(invoice.getTotalAmount());
            invoiceResponseDTO.setPaymentStatus(invoice.getStatus());
        } catch (Exception e) {
            // Log the error message for debugging
            System.err.println("Error fetching invoice has code: " +code+", message:" + e.getMessage());
            invoiceResponseDTO = null;
            // Optional: you can throw a custom exception here
        }
        return invoiceResponseDTO;
    }

    @Override
    public Invoice createInvoice(InvoiceRequestDTO invoiceDTO) {
        try {
            Invoice invoice = new Invoice();
            invoice.setInvoiceCode(UUID.randomUUID().toString().substring(0, 10).toUpperCase());
            invoice.setName(invoiceDTO.getName());
            invoice.setPhoneNumber(invoiceDTO.getPhoneNumber());
            invoice.setEmail(invoiceDTO.getEmail());
            invoice.setTotalAmount(invoiceDTO.getTotalAmount());
            invoice.setInvDate(new Date());
            invoice.setStatus(InvoiceEnum.PENDING);


            return invoiceRepo.save(invoice);
        } catch (Exception e) {
            System.err.println("Error creating invoice: " + e.getMessage());
            return null;
        }
    }

    @Override
    public InvoiceResponseDTO updateInvoice(int id, InvoiceResponseDTO invoiceRequestDTO) {
        try {
            Invoice invoice = invoiceRepo.findById(id).orElse(null);
            if (invoice == null) {
                return null;
            }

            invoice.setInvDate(invoiceRequestDTO.getInvDate());
            invoice.setTotalAmount(invoiceRequestDTO.getTotalAmount());
            invoice.setStatus(invoiceRequestDTO.getPaymentStatus());

            invoiceRepo.save(invoice);

            return new InvoiceResponseDTO(invoice.getInvoiceId(), invoice.getInvoiceCode(), invoice.getInvDate(), invoice.getTotalAmount(), invoice.getStatus());
        } catch (Exception e) {
            System.err.println("Error updating invoice: " + e.getMessage());
            return null;
        }
    }
    @Override
    public boolean deleteInvoice(int id) {
        try {
            Invoice invoice = invoiceRepo.findById(id).orElse(null);
            if (invoice == null) {
                return false;
            }
            invoiceRepo.delete(invoice);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting invoice: " + e.getMessage());
            return false;
        }
    }

    public void updateInvoiceStatus(int invoiceId, InvoiceEnum status) {
        Invoice invoice = invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        invoice.setStatus(status);

        for (Booking booking : invoice.getBookingList()) {
            booking.setStatus(status == InvoiceEnum.PAID ? BookingEnum.CONFIRMED : BookingEnum.CANCELLED);
        }

        invoiceRepo.save(invoice);
    }


}
