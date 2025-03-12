package es.ua.biblioteca.controller;

import es.ua.biblioteca.model.Author;
import es.ua.biblioteca.model.Book;
import es.ua.biblioteca.service.IBookService;
import es.ua.biblioteca.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private IAuthorService authorService;

    // Método GET para mostrar el formulario de creación de libro.
    @GetMapping("/create")
    public String showCreateBookForm(Model model) {
        Book newBook = new Book();
        newBook.setStatus(true);
        model.addAttribute("book", newBook);
        // Cargar la lista de autores para el combobox.
        model.addAttribute("authors", authorService.findAll());
        return "book/form";  // Ubicación del template: book/form.html
    }

    // Método POST para procesar la creación de un nuevo libro.
    @PostMapping("/create")
    public String createBook(@Valid @ModelAttribute Book book, BindingResult result, Model model) {
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            // Recarga el formulario (por ejemplo, se vuelve a cargar la lista de autores)
            model.addAttribute("authors", authorService.findAll());
            return "book/form";
        }
        String resultMsg = bookService.create(book);
        model.addAttribute("result", resultMsg);
        return "book/result";
    }

    // Método GET para listar todos los libros.
    @GetMapping("/list")
    public String listBooks(@RequestParam(required = false) String title, Model model) {
        if(title == null || title.trim().isEmpty()){
            model.addAttribute("books", bookService.findAll());
        } else {
            model.addAttribute("books", bookService.search(title));
        }
        // Enviamos el valor del filtro al template para que se muestre en el formulario
        model.addAttribute("title", title);
        return "book/list";  // Se asume que existe el template book/list.html
    }

    // Método GET para mostrar el detalle de un libro.
    @GetMapping("/{id}")
    public String getBook(@PathVariable long id, Model model) throws Exception {
        Book book = bookService.findById(id)
                .orElseThrow(() -> new Exception("Book not found"));
        model.addAttribute("book", book);
        return "book/detail";  // Asumiendo que existe el template book/detail.html
    }

    // Método GET para mostrar el formulario de edición.
    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable long id, Model model) throws Exception {
        Book book = bookService.findById(id)
                .orElseThrow(() -> new Exception("Book not found"));
        model.addAttribute("book", book);
        // Se vuelve a cargar la lista de autores para el combobox.
        model.addAttribute("authors", authorService.findAll());
        return "book/form";
    }

    // Método POST para actualizar un libro existente.
    @PostMapping("/update")
    public String updateBook(@ModelAttribute Book book, Model model) {
        String result = bookService.update(book);
        model.addAttribute("result", result);
        return "book/result";
    }

    // Método GET para eliminar un libro.
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable long id) throws Exception {
        bookService.delete(id);
        // Redirige a la lista de libros después de eliminar.
        return "redirect:/book/list";
    }
}
