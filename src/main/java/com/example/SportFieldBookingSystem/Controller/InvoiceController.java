package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceResponseDTO;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.Impl.InvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoiceController {
    private final InvoiceServiceImpl invoiceServiceImpl;

    @Autowired
    public InvoiceController(InvoiceServiceImpl invoiceServiceImpl) {
        this.invoiceServiceImpl = invoiceServiceImpl;
    }

    // Lấy danh sách tất cả hóa đơn
    @GetMapping("/listInvoice")
    public ResponseEntity<ResponseData> getAllInvoices() {
        ResponseData responseData = new ResponseData();

        try {
            List<InvoiceResponseDTO> invoices = invoiceServiceImpl.getAllInvoice();
            if (invoices.isEmpty()) {
                responseData.setMessage("No invoices found");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
            }

            responseData.setMessage("Get all invoices success");
            responseData.setData(invoices);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("An error occurred while fetching invoices: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

    // Lấy thông tin hóa đơn theo mã
    @GetMapping("/invoice/{code}")
    public ResponseEntity<ResponseData> getInvoiceByCode(@PathVariable String code) {
        ResponseData responseData = new ResponseData();

        try {
            InvoiceResponseDTO invoice = invoiceServiceImpl.getInvoiceByInvoiceCode(code);
            if (invoice == null) {
                responseData.setMessage("Invoice with code " + code + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
            }

            responseData.setMessage("Get invoice success");
            responseData.setData(invoice);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("An error occurred while fetching the invoice: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

    // Tạo mới hóa đơn
    @PostMapping("/invoice")
    public ResponseEntity<ResponseData> createInvoice(@RequestBody InvoiceResponseDTO invoiceRequestDTO) {
        ResponseData responseData = new ResponseData();

        try {
            InvoiceResponseDTO createdInvoice = invoiceServiceImpl.createInvoice(invoiceRequestDTO);
            if (createdInvoice == null) {
                responseData.setMessage("Failed to create invoice");
                return ResponseEntity.badRequest().body(responseData);
            }

            responseData.setMessage("Create invoice success");
            responseData.setData(createdInvoice);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
        } catch (Exception e) {
            responseData.setMessage("An error occurred while creating the invoice: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

    // Cập nhật hóa đơn
    @PutMapping("/invoice/{id}")
    public ResponseEntity<ResponseData> updateInvoice(@PathVariable int id, @RequestBody InvoiceResponseDTO invoiceRequestDTO) {
        ResponseData responseData = new ResponseData();

        try {
            InvoiceResponseDTO updatedInvoice = invoiceServiceImpl.updateInvoice(id, invoiceRequestDTO);
            if (updatedInvoice == null) {
                responseData.setMessage("Invoice not found for update");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
            }

            responseData.setMessage("Update invoice success");
            responseData.setData(updatedInvoice);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("An error occurred while updating the invoice: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

    // Xóa hóa đơn
    @DeleteMapping("/invoice/{id}")
    public ResponseEntity<ResponseData> deleteInvoice(@PathVariable int id) {
        ResponseData responseData = new ResponseData();

        try {
            boolean isDeleted = invoiceServiceImpl.deleteInvoice(id);
            if (!isDeleted) {
                responseData.setMessage("Invoice not found for deletion");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
            }

            responseData.setMessage("Delete invoice success");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        } catch (Exception e) {
            responseData.setMessage("An error occurred while deleting the invoice: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }
}
