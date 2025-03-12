package es.ua.biblioteca.controller;

import es.ua.biblioteca.model.Book;
import es.ua.biblioteca.service.IBookService;
import es.ua.biblioteca.util.GeneratePdfReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    private IBookService bookService;

    @RequestMapping(value = "/pdfreport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> booksReport() {

        var books = (List<Book>) bookService.findAll();

        ByteArrayInputStream bis = GeneratePdfReport.booksReport(books);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=booksreport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

}
