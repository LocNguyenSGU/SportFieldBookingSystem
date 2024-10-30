package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceResponseDTO;
import com.example.SportFieldBookingSystem.Entity.Invoice;
import com.example.SportFieldBookingSystem.Repository.InvoiceRepository;
import com.example.SportFieldBookingSystem.Service.Impl.InvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class InvoiceService implements InvoiceServiceImpl {

    final private InvoiceRepository invoiceRepo;
    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepo){
        this.invoiceRepo = invoiceRepo;
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
    public InvoiceResponseDTO createInvoice(InvoiceResponseDTO invoiceRequestDTO) {
        try {
            Invoice newInvoice = new Invoice();
            // Mapping dữ liệu từ DTO sang Entity
            newInvoice.setInvoiceCode(invoiceRequestDTO.getInvoiceCode());
            newInvoice.setInvDate(invoiceRequestDTO.getInvDate());
            newInvoice.setTotalAmount(invoiceRequestDTO.getTotalAmount());
            newInvoice.setStatus(invoiceRequestDTO.getPaymentStatus());

            invoiceRepo.save(newInvoice);

            // Mapping từ Entity sang DTO để trả về
            return new InvoiceResponseDTO(newInvoice.getInvoiceId(), newInvoice.getInvoiceCode(), newInvoice.getInvDate(), newInvoice.getTotalAmount(), newInvoice.getStatus());
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


}
