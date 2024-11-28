package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.BookingDTO.BookingResponseDTO;
import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceThongKeDTO;
import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.TKTongQuatDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ExcelService {
    @Autowired
    private InvoiceServiceImpl invoiceService;

    public ByteArrayOutputStream exportThongKeToExcel(Date startDate, Date endDate) throws IOException {
        // Lấy dữ liệu thống kê từ service
        TKTongQuatDTO thongKe = invoiceService.getTKTongQuat(startDate, endDate);

        List<InvoiceThongKeDTO> invoiceThongKeDTOList = invoiceService.findInvoicesByDateRange(startDate, endDate);
        // Tạo workbook mới
        Workbook workbook = new XSSFWorkbook();

        // Trừ đi 1 ngày từ endDate
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1); // Trừ đi 1 ngày
        Date adjustedEndDate = calendar.getTime();

        // Tạo sheet thứ nhất
        createSheet1(workbook, thongKe, startDate, adjustedEndDate);
        createSheet2(workbook, invoiceThongKeDTOList);
        createSheet3(workbook, invoiceThongKeDTOList, thongKe.getTongDoanhThu());
        createSheet4(workbook, invoiceThongKeDTOList, thongKe.getTongDoanhThu());

        // Lưu file Excel vào ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream;
    }

    private void createSheet1(Workbook workbook, TKTongQuatDTO thongKe, Date startDate, Date endDate) {
        // Tạo sheet thứ nhất (thống kê tổng quát)
        Sheet sheet = workbook.createSheet("Thống kê tổng quát");

        // Tiêu đề headerRow0 (Báo cáo doanh thu tổng quát)
        Row headerRow0 = sheet.createRow(0);
        Cell headerCell0 = headerRow0.createCell(0);
        headerCell0.setCellValue("Báo Cáo Doanh Thu Tổng Quát ManchesterUnited");

        // Tạo kiểu dáng cho headerRow0
        CellStyle headerStyle0 = workbook.createCellStyle();
        headerStyle0.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headerStyle0.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle0.setAlignment(HorizontalAlignment.CENTER);
        headerStyle0.setVerticalAlignment(VerticalAlignment.CENTER);

        // Font chữ cho headerRow0
        Font font0 = workbook.createFont();
        font0.setBold(true);
        font0.setFontHeightInPoints((short) 18);
        font0.setColor(IndexedColors.BLACK.getIndex());
        headerStyle0.setFont(font0);

        headerCell0.setCellStyle(headerStyle0);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));  // Merge các ô để hiển thị tiêu đề

        // Tiêu đề headerRow1 (Ngày bắt đầu, ngày kết thúc)
        // Định dạng ngày theo định dạng YYYY-MM-DD
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(endDate);

        Row headerRow1 = sheet.createRow(1);
        headerRow1.createCell(0).setCellValue("Ngày Bắt Đầu: " + startDate.toString());
        headerRow1.createCell(2).setCellValue("Ngày Kết Thúc: " + formattedDate);

        // Tạo kiểu dáng cho headerRow1
        CellStyle headerStyle1 = workbook.createCellStyle();
        headerStyle1.setAlignment(HorizontalAlignment.CENTER);
        headerStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

        // Font chữ cho headerRow1
        Font font1 = workbook.createFont();
        font1.setFontHeightInPoints((short) 12); // Font size nhỏ hơn
        headerStyle1.setFont(font1);

        headerRow1.getCell(0).setCellStyle(headerStyle1);
        headerRow1.getCell(2).setCellStyle(headerStyle1);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));  // Merge ô cho ngày bắt đầu
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 3));  // Merge ô cho ngày kết thúc

        // Tiêu đề header chính
        Row headerRow = sheet.createRow(2);
        String[] headers = {"Tổng Số Hóa Đơn", "Tổng Doanh Thu", "Doanh Thu Trung Bình", "Tổng Số Booking"};

        // Tạo kiểu dáng cho header
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Tạo dòng dữ liệu
        Row dataRow = sheet.createRow(3);
        dataRow.createCell(0).setCellValue(thongKe.getTongSoHoaDon());
        dataRow.createCell(1).setCellValue(thongKe.getTongDoanhThu());
        dataRow.createCell(2).setCellValue(thongKe.getDoanhThuTrungBinh());
        dataRow.createCell(3).setCellValue(thongKe.getTongSoBooking());

        // Cài đặt chiều rộng của cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    private void createSheet2(Workbook workbook, List<InvoiceThongKeDTO> hoaDonDTOList) {
        // Tạo sheet thứ hai (danh sách hóa đơn)
        Sheet sheet = workbook.createSheet("Danh sách hóa đơn");

        // Tiêu đề header chính cho sheet thứ 2
        Row headerRow = sheet.createRow(0);
        String[] headers = {"STT","Mã Hóa Đơn", "Họ tên", "Số điện thoại", "Thời gian tạo", "Số booking", "Tổng Tiền"};

        // Tạo kiểu dáng cho header
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        // Tạo các dòng dữ liệu từ danh sách hoaDonDTOList
        int rowNum = 1;
        for (InvoiceThongKeDTO hoaDonDTO : hoaDonDTOList) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(rowNum - 1); // ben tren ++ roi, ben duoi tru di de lay cai index
            dataRow.createCell(1).setCellValue("HD" + hoaDonDTO.getInvoiceId());
            dataRow.createCell(2).setCellValue(hoaDonDTO.getName() != null ? hoaDonDTO.getName() : "N/A");
            dataRow.createCell(3).setCellValue(hoaDonDTO.getPhoneNumber() != null ? hoaDonDTO.getPhoneNumber() : "N/A");
            dataRow.createCell(4).setCellValue(hoaDonDTO.getInvDate() != null ?  dateFormat.format(hoaDonDTO.getInvDate()) : "N/A");
            dataRow.createCell(5).setCellValue(hoaDonDTO.getBookingList().size());
            dataRow.createCell(6).setCellValue(hoaDonDTO.getTotalAmount());
        }

        // Cài đặt chiều rộng của cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public static void createSheet3(Workbook workbook, List<InvoiceThongKeDTO> hoaDonDTOList, double tongDoanhThu) {
        // Tạo sheet thứ ba (Thống kê theo ngày)
        Sheet sheet = workbook.createSheet("Thống kê theo ngày");

        // Tiêu đề header chính cho sheet thứ 3
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Ngày", "Doanh thu", "% trong tổng doanh thu"};

        // Tạo kiểu dáng cho header
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Tạo Map để tổng hợp doanh thu theo ngày
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Map<String, Double> doanhThuTheoNgay = new TreeMap<>();
        for (InvoiceThongKeDTO invoice : hoaDonDTOList) {
            String ngay = dateFormat.format(invoice.getInvDate());
            double doanhThu = invoice.getTotalAmount();

            doanhThuTheoNgay.put(ngay, doanhThuTheoNgay.getOrDefault(ngay, 0.0) + doanhThu);
        }

        // Duyệt Map và ghi dữ liệu vào sheet
        int rowIndex = 1;
        for (Map.Entry<String, Double> entry : doanhThuTheoNgay.entrySet()) {
            Row row = sheet.createRow(rowIndex++);
            String ngay = entry.getKey();
            double doanhThu = entry.getValue();
            double phanTram = (tongDoanhThu > 0) ? ((doanhThu / tongDoanhThu)) : 0;

            // Ghi dữ liệu vào các cột
            row.createCell(0).setCellValue(ngay);
            row.createCell(1).setCellValue(doanhThu);

            Cell phanTramCell = row.createCell(2);
            phanTramCell.setCellValue(phanTram);

            // Định dạng phần trăm
            CellStyle percentStyle = workbook.createCellStyle();
            percentStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
            phanTramCell.setCellStyle(percentStyle);
        }

        // Tự động chỉnh độ rộng các cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public static void createSheet4(Workbook workbook, List<InvoiceThongKeDTO> hoaDonDTOList, double tongDoanhThu) {
        // Tạo sheet thứ ba (Thống kê theo ngày)
        Sheet sheet = workbook.createSheet("Thống kê theo sân");

        // Tiêu đề header chính cho sheet thứ 3
        Row headerRow = sheet.createRow(0);
        String[] headers = {"STT", "ID sân", "Tên sân", "% trong tổng doanh thu"};

        // Tạo kiểu dáng cho header
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // Đặt tiêu đề cho các cột
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Tạo Map để lưu doanh thu theo sân và tên sân
        Map<Integer, Double> fieldRevenueMap = new HashMap<>();
        Map<Integer, String> fieldNameMap = new HashMap<>();

        // Kiểm tra hoaDonDTOList có phải null không
        if (hoaDonDTOList == null || hoaDonDTOList.isEmpty()) {
            System.out.println("Lỗi: danh sách hóa đơn trống hoặc null.");
            return;
        }

        // Duyệt qua danh sách hoaDonDTOList để tính doanh thu theo từng sân
        for (InvoiceThongKeDTO invoiceThongKeDTO : hoaDonDTOList) {
            if (invoiceThongKeDTO == null || invoiceThongKeDTO.getBookingList() == null) {
                continue;
            }

            List<BookingResponseDTO> bookingResponseDTOList = invoiceThongKeDTO.getBookingList();
            for (BookingResponseDTO booking : bookingResponseDTOList) {
                if (booking == null) {
                    System.out.println("Lỗi: Booking null.");
                    continue;
                }
                int fieldId = booking.getField();
                String fieldName = booking.getFieldName();
                double totalPrice = booking.getTotalPrice();


                // Kiểm tra fieldName không phải null
                if (fieldName != null) {
                    // Cập nhật doanh thu của từng sân trong fieldRevenueMap
                    fieldRevenueMap.put(fieldId, fieldRevenueMap.getOrDefault(fieldId, 0.0) + totalPrice);
                    // Lưu tên sân vào fieldNameMap
                    fieldNameMap.putIfAbsent(fieldId, fieldName);
                } else {
                    System.out.println("Tên sân không hợp lệ cho Sân ID: " + fieldId);
                }
            }
        }

        // Kiểm tra kết quả của việc tính toán doanh thu
        for (Map.Entry<Integer, Double> entry : fieldRevenueMap.entrySet()) {
            System.out.println("Sân ID: " + entry.getKey() + " - Doanh thu: " + entry.getValue());
        }

        // Tính tỷ lệ phần trăm doanh thu của từng sân so với tổng doanh thu
        if (tongDoanhThu == 0) {
            System.out.println("Lỗi: Tổng doanh thu là 0!");
            return;
        }

        int rowIdx = 1;  // Bắt đầu từ dòng 1 vì dòng 0 là header
        int stt = 1;  // Số thứ tự
        for (Map.Entry<Integer, Double> entry : fieldRevenueMap.entrySet()) {
            Row row = sheet.createRow(rowIdx++);
            int fieldId = entry.getKey();
            double revenue = entry.getValue();
            String fieldName = fieldNameMap.get(fieldId);
            double percentage = (revenue / tongDoanhThu) * 100;


            // Điền dữ liệu vào các cột
            row.createCell(0).setCellValue(stt++);
            row.createCell(1).setCellValue("SAN" + fieldId);
            row.createCell(2).setCellValue(fieldName);
            row.createCell(3).setCellValue(String.format("%.2f", percentage));  // Tỷ lệ phần trăm
        }

        // Tự động chỉnh độ rộng các cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

    }



    private static String formatDateTime(String dateTimeString) {
        // Parse the string to LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);

        // Define the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Format the date and return it
        return dateTime.format(formatter);
    }
}
