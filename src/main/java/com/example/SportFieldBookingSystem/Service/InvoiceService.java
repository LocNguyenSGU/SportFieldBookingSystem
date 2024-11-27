package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.*;
import com.example.SportFieldBookingSystem.Entity.Booking;
import com.example.SportFieldBookingSystem.Entity.Invoice;
import com.example.SportFieldBookingSystem.Enum.BookingEnum;
import com.example.SportFieldBookingSystem.Enum.InvoiceEnum;
import com.example.SportFieldBookingSystem.Mapper.InvoiceMapper;
import com.example.SportFieldBookingSystem.Repository.InvoiceRepository;
import com.example.SportFieldBookingSystem.Service.Impl.BookingServiceImpl;
import com.example.SportFieldBookingSystem.Service.Impl.InvoiceServiceImpl;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService implements InvoiceServiceImpl {

    private final InvoiceRepository invoiceRepo;
    private final ModelMapper modelMapper;
    private final BookingServiceImpl bookingService;
    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepo, ModelMapper modelMapper, BookingServiceImpl bookingService) {
        this.invoiceRepo = invoiceRepo;
        this.modelMapper = modelMapper;
        this.bookingService = bookingService;
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

    @Transactional
    @Override
    public InvoiceBookingResponseDTO createInvoice(InvoiceBookingRequestDTO invoiceBookingRequestDTO) {
        try {
            Invoice invoice = new Invoice();
            invoice.setTotalAmount(invoiceBookingRequestDTO.getTotalAmount());
            invoice.setName(invoiceBookingRequestDTO.getName());
            invoice.setEmail(invoiceBookingRequestDTO.getEmail());
            invoice.setPhoneNumber(invoiceBookingRequestDTO.getPhoneNumber());
            invoice.setInvDate(new Date());
            invoice.setTotalAmount(invoiceBookingRequestDTO.getTotalAmount());
            invoice.setStatus(InvoiceEnum.PAID);

            invoiceRepo.save(invoice);
            String formattedId = String.format("INV%03d", invoice.getInvoiceId());
            invoice.setInvoiceCode(formattedId);
            invoiceRepo.save(invoice);

            bookingService.createBookings(invoiceBookingRequestDTO.getBooking(), invoice);
            return modelMapper.map(invoiceBookingRequestDTO, InvoiceBookingResponseDTO.class);
        } catch (Exception e) {
            System.err.println("Error creating invoice: " + e.getMessage());
            return null;
        }
    }

//    @Override
//    public InvoiceResponseDTO updateInvoice(int id, InvoiceResponseDTO invoiceRequestDTO) {
//        try {
//            Invoice invoice = invoiceRepo.findById(id).orElse(null);
//            if (invoice == null) {
//                return null;
//            }
//
//            invoice.setInvDate(invoiceRequestDTO.getInvDate());
//            invoice.setTotalAmount(invoiceRequestDTO.getTotalAmount());
//            invoice.setStatus(invoiceRequestDTO.getPaymentStatus());
//
//            invoiceRepo.save(invoice);
//
//            return new InvoiceResponseDTO(invoice.getInvoiceId(), invoice.getInvoiceCode(), invoice.getInvDate(), invoice.getTotalAmount(), invoice.getStatus());
//        } catch (Exception e) {
//            System.err.println("Error updating invoice: " + e.getMessage());
//            return null;
//        }
//    }
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

    @Override
    public List<InvoiceThongKeDTO> findInvoicesByDateRange(Date startDate, Date endDate) {
        List<Invoice> invoiceList = invoiceRepo.findInvoicesByDateRange(startDate, endDate);
        return invoiceList.stream().map(InvoiceMapper::fromEntity).collect(Collectors.toList());
    }

    @Override
    public TKTongQuatDTO getTKTongQuat(Date startDate, Date endDate) {
        // Lấy dữ liệu từ repository
        List<Object[]> result = invoiceRepo.getTKTongQuatRaw(startDate, endDate);

        // Kiểm tra nếu có dữ liệu trả về
        if (result != null && !result.isEmpty()) {
            Object[] row = result.get(0);  // Dữ liệu của dòng đầu tiên

            // Kiểm tra và lấy giá trị từ Object[], nếu là null thì gán giá trị mặc định
            int tongSoHoaDon = (row[0] != null) ? ((Number) row[0]).intValue() : 0;
            double tongDoanhThu = (row[1] != null) ? ((Number) row[1]).doubleValue() : 0.0;
            double doanhThuTrungBinh = (row[2] != null) ? ((Number) row[2]).doubleValue() : 0.0;
            int tongSoBooking = (row[3] != null) ? ((Number) row[3]).intValue() : 0;

            return new TKTongQuatDTO(tongSoHoaDon, tongDoanhThu, doanhThuTrungBinh, tongSoBooking);
        }

        // Trả về DTO mặc định nếu không có dữ liệu
        return new TKTongQuatDTO();
    }

}
