package com.cinebook.notificationservice.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class TicketGenerator {

    // Updated to return byte[] and take extra details like Date and Price
    public byte[] generateTicket(Long bookingId, String movieName, String seatNumber, String date, String amount) {
        try {
            // 1. Create PDF Document in Memory (Not on Disk)
            Document document = new Document(PageSize.A5);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);

            document.open();

            // 2. Add Title / Header
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, new BaseColor(229, 9, 20)); // Brand Red
            Paragraph title = new Paragraph("CINEBOOK TICKET", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator(1f, 100f, BaseColor.LIGHT_GRAY, Element.ALIGN_CENTER, -2));
            document.add(new Paragraph("\n"));

            // 3. Add Details Table (Better Layout)
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            
            addTableRow(table, "Booking ID:", String.valueOf(bookingId));
            addTableRow(table, "Movie:", movieName);
            addTableRow(table, "Date & Time:", date);
            addTableRow(table, "Seats:", seatNumber);
            addTableRow(table, "Total Paid:", "Rs. " + amount);
            addTableRow(table, "Status:", "CONFIRMED");
            
            document.add(table);
            document.add(new Paragraph("\n")); 

            // 4. Generate QR Code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode("CINEBOOK_ID:" + bookingId, BarcodeFormat.QR_CODE, 200, 200);
            
            // Convert QR to Image
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            Image qrImage = Image.getInstance(pngOutputStream.toByteArray());
            qrImage.setAlignment(Element.ALIGN_CENTER);
            
            document.add(qrImage);
            
            // Footer
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY);
            Paragraph footer = new Paragraph("Show this QR at the cinema entrance.", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(10);
            document.add(footer);
            
            document.close();
            
            // Return the byte array to the controller
            return out.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to make table rows look clean
    private void addTableRow(PdfPTable table, String label, String value) {
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.DARK_GRAY);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

        PdfPCell c1 = new PdfPCell(new Phrase(label, labelFont));
        c1.setBorder(Rectangle.NO_BORDER);
        c1.setPaddingBottom(8);

        PdfPCell c2 = new PdfPCell(new Phrase(value, valueFont));
        c2.setBorder(Rectangle.NO_BORDER);
        c2.setPaddingBottom(8);
        c2.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(c1);
        table.addCell(c2);
    }
}