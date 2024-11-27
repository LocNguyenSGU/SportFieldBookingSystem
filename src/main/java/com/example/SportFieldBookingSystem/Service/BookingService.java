package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.BookingDTO.*;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldGetDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.Entity.*;
import com.example.SportFieldBookingSystem.Enum.BookingEnum;
import com.example.SportFieldBookingSystem.Enum.TimeSlotEnum;
import com.example.SportFieldBookingSystem.Repository.*;
import com.example.SportFieldBookingSystem.Service.Impl.BookingServiceImpl;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService implements BookingServiceImpl {
    private final BookingRepository bookingRepo;
    private InvoiceRepository invoiceRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private FieldRepository fieldRepo;
    @Autowired
    private ModelMapper modelMapper;
    private final UserService userService;
    @Autowired
    private FieldService fieldService;
    @Autowired
    private final TimeSlotService timeSlotService;
    private  final TimeSlotRepository timeSlotRepo;

    @Autowired
    public BookingService(BookingRepository bookingRepo, InvoiceRepository invoiceRepo, UserRepository userRepo, FieldRepository fieldRepo, TimeSlotService timeSlotService, TimeSlotRepository timeSlotRepo, UserService userService) {
        this.bookingRepo = bookingRepo;
        this.invoiceRepo = invoiceRepo;
        this.userRepo = userRepo;
        this.fieldRepo = fieldRepo;
        this.timeSlotService = timeSlotService;
        this.timeSlotRepo = timeSlotRepo;
        this.userService = userService;
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
                bookingResponseDTO.setBookingDate(booking.getBookingDate());
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
    @Transactional
    public List<BookingRequestDTO> createBookings(BookingRequestDTO bookingRequestDTO, Invoice invoice) {
        try {
            // Danh sách để lưu các booking đã được tạo
            List<Booking> createdBookings = new ArrayList<>();

            // Kiểm tra và lấy thông tin người dùng
            System.out.println("Fetching user with ID: " + bookingRequestDTO.getUserId());
            UserBasicDTO userGetDTO = userService.getUserByUserId(bookingRequestDTO.getUserId());
            FieldGetDTO fieldGetDTO = fieldService.getFieldById(bookingRequestDTO.getFieldId());

            System.out.println("User found: " + userGetDTO.getFullName());
            System.out.println("Field found: " + fieldGetDTO.getFieldName());

            // Xử lý từng event đã chọn trong danh sách
            for (Event selectedEvent : bookingRequestDTO.getSelectedEvents()) {
                System.out.println("Processing event with start time: " + selectedEvent.getStart() +
                        " and end time: " + selectedEvent.getEnd());

                // Tạo mới một booking
                Booking booking = new Booking();
                booking.setBookingCode(generateBookingCode()); // Mã booking duy nhất
                System.out.println("Generated booking code: " + booking.getBookingCode());

                booking.setUser(modelMapper.map(userGetDTO, User.class));
                booking.setField(modelMapper.map(fieldGetDTO, Field.class));
                booking.setBookingDate(bookingRequestDTO.getDate());
                System.out.println("Booking date set to: " + bookingRequestDTO.getDate());

                booking.setStartTime(selectedEvent.getStart());
                booking.setEndTime(selectedEvent.getEnd());
                booking.setTotalPrice(selectedEvent.getTotalPrice());
                booking.setInvoice(invoice);

                // Đặt trạng thái mặc định cho booking
                booking.setStatus(BookingEnum.PENDING);
                System.out.println("Booking status set to: " + BookingEnum.PENDING);

                // Lưu booking vào cơ sở dữ liệu
                Booking savedBooking = bookingRepo.save(booking);
                System.out.println("Booking saved with ID: " + savedBooking.getBookingId());

                // Cập nhật thông tin liên kết giữa TimeSlot và Booking
                Optional<TimeSlot> optionalTimeSlot = timeSlotRepo.findById(selectedEvent.getId());
                if (optionalTimeSlot.isPresent()) {
                    TimeSlot timeSlot = optionalTimeSlot.get();
                    timeSlot.setBooking(savedBooking);
                    timeSlotRepo.save(timeSlot); // Chỉ cập nhật booking, không thay đổi trạng thái
                    System.out.println("TimeSlot updated for booking ID: " + savedBooking.getBookingId());
                } else {
                    System.err.println("TimeSlot with ID " + selectedEvent.getId() + " not found!");
                }
                finalizeBooking(savedBooking.getBookingId());
                // Thêm booking đã tạo vào danh sách kết quả
                createdBookings.add(savedBooking);
            }

            // Chuyển đổi danh sách booking thành DTO để trả về
            System.out.println("Converting bookings to BookingDTO...");
            return createdBookings.stream()
                    .map(booking -> modelMapper.map(booking, BookingRequestDTO.class))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error during booking creation: " + e.getMessage());
            throw new RuntimeException("Error creating bookings: " + e.getMessage(), e);
        }
    }


    private String generateBookingCode() {
        return UUID.randomUUID().toString().substring(0, 10);
    }

    public void finalizeBooking(int bookingId) {
        TimeSlot slot = timeSlotRepo.findTimeSlotByBookingBookingId(bookingId);
        slot.setStatus(TimeSlotEnum.BOOKED);
        timeSlotRepo.save(slot);
    }

    public void cancelBooking(int bookingId) {
        TimeSlot slot = timeSlotRepo.findTimeSlotByBookingBookingId(bookingId);
        slot.setStatus(TimeSlotEnum.AVAILABLE);
        timeSlotRepo.save(slot);
    }

//    public void finalizeInvoice(int bookingId) {
//        List<TimeSlot> slots = timeSlotRepo.findTimeSlotByBookingBookingId(bookingId);
//        slots.forEach(slot -> slot.setStatus(TimeSlotEnum.BOOKED));
//        timeSlotRepo.saveAll(slots);
//    }

    @Override
    public List<FieldGetDTO> getBookedFields() {
        List<Field> bookedFields = bookingRepo.findBookedFields();
        return bookedFields.stream()
                .map(field -> {
                    FieldGetDTO fieldDTO = new FieldGetDTO();
                    fieldDTO.setFieldId(field.getFieldId());
                    fieldDTO.setFieldCode(field.getFieldCode());
                    fieldDTO.setFieldName(field.getFieldName());
                    fieldDTO.setCapacity(field.getCapacity());
                    fieldDTO.setPricePerHour(field.getPricePerHour());
                    return fieldDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getBookedFieldsWithTime() {
        List<Object[]> bookedFields = bookingRepo.findBookedFieldsWithTime();
        return bookedFields.stream()
                .map(record -> {
                    Field field = (Field) record[0];
                    LocalDate bookingDate = (LocalDate) record[1];
                    LocalTime startTime = (LocalTime) record[2];
                    LocalTime endTime = (LocalTime) record[3];

                    BookingResponseDTO dto = new BookingResponseDTO();
                    dto.setField(field.getFieldId());
                    dto.setFieldName(field.getFieldName());
                    dto.setBookingDate(bookingDate);
                    dto.setStartTime(startTime);
                    dto.setEndTime(endTime);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<RevenueDTO> getRevenueStatistics() {
        List<Object[]> revenueData = bookingRepo.getRevenueByMonthAndYear();
        return revenueData.stream()
                .map(record -> new RevenueDTO(
                        (int) record[0],  // month
                        (int) record[1],  // year
                        (double) record[2] // totalRevenue
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuarterlyRevenueDTO> getRevenueStatisticsByQuarter() {
        List<Object[]> revenueData = bookingRepo.getRevenueByQuarterAndYear();
        return revenueData.stream()
                .map(record -> new QuarterlyRevenueDTO(
                        (int) record[1],  // quarter (1-4)
                        (int) record[0],  // year
                        (double) record[2] // totalRevenue
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<MostBookedFieldDTO> getMostBookedFields() {
        List<Object[]> bookingData = bookingRepo.getMostBookedFields();
        return bookingData.stream()
                .map(record -> new MostBookedFieldDTO(
                        (int) record[0],        // fieldId
                        (String) record[1],    // fieldName
                        (long) record[2]       // bookingCount
                ))
                .collect(Collectors.toList());
    }



}
