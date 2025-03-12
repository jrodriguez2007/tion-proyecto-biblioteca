package es.ua.biblioteca.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import es.ua.biblioteca.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class GeneratePdfReport {

    private static final Logger logger = LoggerFactory.getLogger(GeneratePdfReport.class);

    public static ByteArrayInputStream booksReport(List<Book> books) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Título del reporte
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.DARK_GRAY);
            Paragraph title = new Paragraph("Libros Disponibles", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Configuración de la tabla: 6 columnas
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3, 4, 3, 2, 3});
            table.setSpacingBefore(10);

            // Fuentes con tamaños ajustados
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.BLACK);

            // Definición de colores para el header
            BaseColor headerColor = new BaseColor(79, 129, 189); // tono azul
            BaseColor borderColor = BaseColor.LIGHT_GRAY;

            // Celdas del header
            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Id", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(headerColor);
            hcell.setBorderColor(borderColor);
            hcell.setPadding(5);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Título del Libro", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(headerColor);
            hcell.setBorderColor(borderColor);
            hcell.setPadding(5);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Descripción", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(headerColor);
            hcell.setBorderColor(borderColor);
            hcell.setPadding(5);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Autor", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(headerColor);
            hcell.setBorderColor(borderColor);
            hcell.setPadding(5);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Nro. Páginas", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(headerColor);
            hcell.setBorderColor(borderColor);
            hcell.setPadding(5);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Fecha Publicación", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(headerColor);
            hcell.setBorderColor(borderColor);
            hcell.setPadding(5);
            table.addCell(hcell);

            // Filas de datos
            for (Book book : books) {
                PdfPCell cell;

                // Id-Libro
                cell = new PdfPCell(new Phrase(book.getId().toString(), cellFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                cell.setBorderColor(borderColor);
                table.addCell(cell);

                // Título del Libro
                cell = new PdfPCell(new Phrase(book.getTitle(), cellFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setPadding(5);
                cell.setBorderColor(borderColor);
                table.addCell(cell);

                // Descripción
                cell = new PdfPCell(new Phrase(book.getDescription(), cellFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setPadding(5);
                cell.setBorderColor(borderColor);
                table.addCell(cell);

                // Autor (nombres y apellidos)
                String autorCompleto = book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName();
                cell = new PdfPCell(new Phrase(autorCompleto, cellFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setPadding(5);
                cell.setBorderColor(borderColor);
                table.addCell(cell);

                // Número de Páginas
                cell = new PdfPCell(new Phrase(String.valueOf(book.getPageNumbers()), cellFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                cell.setBorderColor(borderColor);
                table.addCell(cell);

                // Fecha de Publicación
                String fechaFormateada = sdf.format(book.getPublication());
                cell = new PdfPCell(new Phrase(fechaFormateada, cellFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setPadding(5);
                cell.setBorderColor(borderColor);
                table.addCell(cell);
            }

            document.add(table);
            document.close();

        } catch (DocumentException ex) {
            logger.error("Error occurred: {0}", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
