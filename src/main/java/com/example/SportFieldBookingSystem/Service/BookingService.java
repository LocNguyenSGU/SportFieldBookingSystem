package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.BookingDTO.BookingRequestDTO;
import com.example.SportFieldBookingSystem.DTO.BookingDTO.BookingResponseDTO;
import com.example.SportFieldBookingSystem.DTO.BookingDTO.Event;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldGetDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.Entity.Booking;
import com.example.SportFieldBookingSystem.Entity.Field;
import com.example.SportFieldBookingSystem.Entity.Invoice;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Enum.BookingEnum;
import com.example.SportFieldBookingSystem.Repository.BookingRepository;
import com.example.SportFieldBookingSystem.Repository.FieldRepository;
import com.example.SportFieldBookingSystem.Repository.InvoiceRepository;
import com.example.SportFieldBookingSystem.Repository.UserRepository;
import com.example.SportFieldBookingSystem.Service.Impl.BookingServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService implements BookingServiceImpl {

    @Autowired
    private final BookingRepository bookingRepo;
    @Autowired
    private InvoiceRepository invoiceRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private FieldRepository fieldRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private FieldService fieldService;
    @Autowired
    public BookingService(BookingRepository bookingRepo) {

        this.bookingRepo = bookingRepo;
    }
    @Override
    public boolean checkForOverlappingBookings(int bookingId, int fieldId, LocalDate bookingDate, LocalTime startTime, LocalTime endTime) {
        List<Booking> overlappingBookings = bookingRepo.findOverlappingBookings(
                fieldId,
                bookingId,
                bookingDate,
                startTime,
                endTime
        );
        return !overlappingBookings.isEmpty();
    }
    @Override
    public List<BookingResponseDTO> getAllBooking() {
        List<BookingResponseDTO> listBookingDTO = new ArrayList<>();

        try {
            List<Booking> listBookings = bookingRepo.findAll();
            for (Booking booking : listBookings) {
                BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
                bookingResponseDTO.setBookingId(booking.getBookingId());
                bookingResponseDTO.setUser(booking.getUser().getUserId());
                bookingResponseDTO.setField(booking.getField().getFieldId());
                bookingResponseDTO.setBookingCode(booking.getBookingCode());
                bookingResponseDTO.setStartTime(booking.getStartTime());
                bookingResponseDTO.setEndTime(booking.getEndTime());
                bookingResponseDTO.setTotalPrice(booking.getTotalPrice());
                listBookingDTO.add(bookingResponseDTO);
            }
        } catch (Exception e) {
            System.err.println("Error fetching all bookings: " + e.getMessage());
        }

        return listBookingDTO;
    }

    @Override
    public BookingResponseDTO getBookingByCode(String code) {
        try {
            Booking booking = bookingRepo.findByBookingCode(code);
            if (booking != null) {
                return new BookingResponseDTO(
                        booking.getBookingId(),
                        booking.getBookingCode(),
                        booking.getUser().getUserId(),
                        booking.getField().getFieldId(),
                        booking.getBookingDate(),
                        booking.getStartTime(),
                        booking.getEndTime(),
                        booking.getStatus(),
                        booking.getTotalPrice(),
                        booking.getInvoice().getInvoiceId()
                );
            }
        } catch (Exception e) {
            System.err.println("Error fetching booking by code: " + e.getMessage());
        }
        return null;
    }

    @Override
    public BookingResponseDTO createNewBooking(BookingResponseDTO booking) {
        try {
            Invoice invoiceEntity = invoiceRepo.findById(booking.getInvoice()).get();
            Booking updatedBooking = new Booking();
            updatedBooking.setBookingDate(booking.getBookingDate());
            updatedBooking.setBookingCode(booking.getBookingCode());
            updatedBooking.setInvoice(invoiceEntity);
            updatedBooking.setEndTime(booking.getEndTime());
            updatedBooking.setStartTime(booking.getStartTime());
            updatedBooking.setTotalPrice(booking.getTotalPrice());
            updatedBooking.setStatus(booking.getStatus());
            Field field = new Field();
            field.setFieldId(booking.getField());
            updatedBooking.setField(field);
            User u = new User();
            u.setUserId(booking.getUser());
            updatedBooking.setUser(u);

            bookingRepo.save(updatedBooking);
            return new BookingResponseDTO(
                    updatedBooking.getBookingId(),
                    updatedBooking.getBookingCode(),
                    updatedBooking.getUser().getUserId(),
                    updatedBooking.getField().getFieldId(),
                    updatedBooking.getBookingDate(),
                    updatedBooking.getStartTime(),
                    updatedBooking.getEndTime(),
                    updatedBooking.getStatus(),
                    updatedBooking.getTotalPrice(),
                    updatedBooking.getInvoice().getInvoiceId()
            );
        } catch (Exception e) {
            System.err.println("Error creating booking: " + e.getMessage());
            return null;
        }
    }

    @Override
    public BookingResponseDTO updateBooking(int id, Booking bookingDetails) {
        try {
            Booking existingBooking = bookingRepo.findById(id).orElse(null);
            if (existingBooking != null) {
                existingBooking.setUser(bookingDetails.getUser());
                existingBooking.setField(bookingDetails.getField());
                existingBooking.setBookingCode(bookingDetails.getBookingCode());
                existingBooking.setStartTime(bookingDetails.getStartTime());
                existingBooking.setEndTime(bookingDetails.getEndTime());

                Booking updatedBooking = bookingRepo.save(existingBooking);
                return new BookingResponseDTO(
                        updatedBooking.getBookingId(),
                        updatedBooking.getBookingCode(),
                        updatedBooking.getUser().getUserId(),
                        updatedBooking.getField().getFieldId(),
                        updatedBooking.getBookingDate(),
                        updatedBooking.getStartTime(),
                        updatedBooking.getEndTime(),
                        updatedBooking.getStatus(),
                        updatedBooking.getTotalPrice(),
                        updatedBooking.getInvoice().getInvoiceId()
                );
            }
        } catch (Exception e) {
            System.err.println("Error updating booking: " + e.getMessage());
        }
        return null;
    }

    public List<BookingRequestDTO> createBookings(BookingRequestDTO bookingRequestDTO, Invoice invoice) {
        List<Booking> createdBookings = new ArrayList<>();

        // Kiểm tra và lấy thông tin người dùng
        System.out.println("Fetching user with ID: " + bookingRequestDTO.getUserId());
        UserBasicDTO userGetDTO = userService.getUserByUserId(bookingRequestDTO.getUserId());
        FieldGetDTO fieldGetDTO = fieldService.getFieldById(bookingRequestDTO.getFieldId());
        System.out.println(userGetDTO.getFullName());
        // Tạo một booking cho mỗi event trong danh sách
        System.out.println("Selected events: " + bookingRequestDTO.getSelectedEvents().size());
        for (Event selectedEvent : bookingRequestDTO.getSelectedEvents()) {
            System.out.println("Processing event with start time: " + selectedEvent.getStart()
                    + " and end time: " + selectedEvent.getEnd());

            Booking booking = new Booking();
            booking.setBookingCode(generateBookingCode()); // Đảm bảo mã là duy nhất
            System.out.println("Generated booking code: " + booking.getBookingCode());

            booking.setUser(modelMapper.map(userGetDTO, User.class));
            booking.setField(modelMapper.map(fieldGetDTO, Field.class));
            booking.setBookingDate(bookingRequestDTO.getDate());
            System.out.println("Booking date set to: " + bookingRequestDTO.getDate());

            booking.setStartTime(selectedEvent.getStart());
            System.out.println(booking.getStartTime());
            booking.setEndTime(selectedEvent.getEnd());
            System.out.println(booking.getEndTime());
            booking.setTotalPrice(selectedEvent.getTotalPrice());
            System.out.println("Total price set to: " + selectedEvent.getTotalPrice());
            booking.setInvoice(invoice);
            booking.setStatus(BookingEnum.PENDING); // Trạng thái mặc định
            System.out.println("Booking status set to: " + BookingEnum.PENDING);

            // Lưu booking và thêm vào danh sách kết quả
            Booking savedBooking = bookingRepo.save(booking);
            System.out.println("Booking saved with ID: " + savedBooking.getBookingId());
            createdBookings.add(savedBooking);
        }

        // Chuyển đổi kết quả sang BookingDTO và trả về
        System.out.println("Converting bookings to BookingDTO...");
        List<BookingRequestDTO> bookingRequestDTOList = createdBookings.stream()
                .map(bookingRS -> modelMapper.map(bookingRS, BookingRequestDTO.class))
                .collect(Collectors.toList());
        System.out.println("Conversion completed. Total bookings created: " + bookingRequestDTOList.size());

        return bookingRequestDTOList;
    }

    private String generateBookingCode() {
        return UUID.randomUUID().toString().substring(0, 10);
    }
}
