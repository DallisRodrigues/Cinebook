package com.cinebook.notificationservice.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

@Component
public class TicketGenerator {

    public void generateTicket(Long bookingId, String movieName, String seatNumber) {
        try {
            // 1. Create PDF Document
            Document document = new Document();
            String fileName = "Ticket_Booking_" + bookingId + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.open();

            // 2. Add Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE);
            Paragraph title = new Paragraph("CineBook Official Ticket", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n")); // Space

            // 3. Add Details
            document.add(new Paragraph("Booking ID: " + bookingId));
            document.add(new Paragraph("Movie: " + movieName));
            document.add(new Paragraph("Seat Number: " + seatNumber));
            document.add(new Paragraph("Status: CONFIRMED"));
            document.add(new Paragraph("\n")); 

            // 4. Generate QR Code (Encoding the Booking ID)
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode("BOOKING_ID:" + bookingId, BarcodeFormat.QR_CODE, 200, 200);
            
            // Convert QR to Image
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            Image qrImage = Image.getInstance(pngOutputStream.toByteArray());
            qrImage.setAlignment(Element.ALIGN_CENTER);
            
            document.add(qrImage);
            
            document.close();
            System.out.println("PDF Ticket Generated: " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}