package com.example.SportFieldBookingSystem.Controller;
import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceResponseDTO;
import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceThongKeDTO;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.Impl.ExcelService;
import com.example.SportFieldBookingSystem.Service.Impl.InvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class InvoiceController {
    private final InvoiceServiceImpl invoiceServiceImpl;
    @Autowired
    private ExcelService excelService;

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
//    @PostMapping("/invoice")
//    public ResponseEntity<ResponseData> createInvoice(@RequestBody InvoiceRequestDTO invoiceRequestDTO) {
//        ResponseData responseData = new ResponseData();
//
//        try {
//            InvoiceResponseDTO createdInvoice = invoiceServiceImpl.createInvoice(invoiceRequestDTO);
//            if (createdInvoice == null) {
//                responseData.setMessage("Failed to create invoice");
//                return ResponseEntity.badRequest().body(responseData);
//            }
//
//            responseData.setMessage("Create invoice success");
//            responseData.setData(createdInvoice);
//            return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
//        } catch (Exception e) {
//            responseData.setMessage("An error occurred while creating the invoice: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
//        }
//    }

    // Cập nhật hóa đơn
//    @PutMapping("/invoice/{id}")
//    public ResponseEntity<ResponseData> updateInvoice(@PathVariable int id, @RequestBody InvoiceResponseDTO invoiceRequestDTO) {
//        ResponseData responseData = new ResponseData();
//
//        try {
//            InvoiceResponseDTO updatedInvoice = invoiceServiceImpl.updateInvoice(id, invoiceRequestDTO);
//            if (updatedInvoice == null) {
//                responseData.setMessage("Invoice not found for update");
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
//            }
//
//            responseData.setMessage("Update invoice success");
//            responseData.setData(updatedInvoice);
//            return ResponseEntity.ok(responseData);
//        } catch (Exception e) {
//            responseData.setMessage("An error occurred while updating the invoice: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
//        }
//    }

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
    @GetMapping("/thongke/excel")
    public ResponseEntity<ResponseData> thongkeExcel(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        ResponseData responseData = new ResponseData();

        try {
            // Chuyển đổi từ LocalDate sang java.sql.Date
            java.sql.Date sqlStartDate = java.sql.Date.valueOf(startDate);
            java.sql.Date sqlEndDate = java.sql.Date.valueOf(endDate);

            // In ra startDate và endDate để kiểm tra
            System.out.println("Start Date: " + sqlStartDate);
            System.out.println("End Date: " + sqlEndDate);

            // Gọi hàm trong service để lấy danh sách hóa đơn theo khoảng thời gian
            List<InvoiceThongKeDTO> invoiceThongKeDTOList = invoiceServiceImpl.findInvoicesByDateRange(sqlStartDate, sqlEndDate);

            // Cập nhật dữ liệu response
            responseData.setStatusCode(200);
            responseData.setData(invoiceThongKeDTOList);
            responseData.setMessage("Danh sách hóa đơn được lấy thành công.");

            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            // Nếu có lỗi, cập nhật response với mã lỗi
            responseData.setStatusCode(400);
            responseData.setData(null);
            responseData.setMessage("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());

            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("thongke/export")
    public ResponseEntity<byte[]> exportThongKeToExcel(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) throws IOException {
        // Chuyển đổi từ LocalDate sang java.sql.Date

        // Adjust endDate by adding 1 day to include the full end date
        LocalDate adjustedEndDate = endDate.plusDays(1);
        java.sql.Date sqlStartDate = java.sql.Date.valueOf(startDate);
        java.sql.Date sqlEndDate = java.sql.Date.valueOf(adjustedEndDate);

        // Gọi service để xuất Excel
        ByteArrayOutputStream outputStream = excelService.exportThongKeToExcel(sqlStartDate, sqlEndDate);

        // Đặt header cho response để người dùng tải file
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=ThongKeTongQuatSport.xlsx");

        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

}
