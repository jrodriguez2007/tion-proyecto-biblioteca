package es.ua.biblioteca.controller;

import es.ua.biblioteca.model.Author;
import es.ua.biblioteca.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private IAuthorService autorService;

    // Método GET para mostrar el formulario de creación.
    @GetMapping("/create")
    public String showCreateAuthorForm(Model model) {
        Author newAuthor = new Author();
        newAuthor.setStatus(true);
        model.addAttribute("author", newAuthor);
        return "author/form";  // ruta al template
    }

    // Método POST para crear un nuevo autor.
    @PostMapping("/create")
    public String createAuthor(@ModelAttribute Author author,
                               @RequestParam("photo") MultipartFile photo,
                               Model model) {

        // Trunca el resumen, si es necesario
        author.setSummary(truncateText(author.getSummary()));

        // Procesa la imagen, si se ha cargado un archivo
        if (!photo.isEmpty()) {
            try {
                String uploadsDir = "images/authors/";  // Directorio externo (relativo a la raíz del proyecto)
                java.io.File dir = new java.io.File(uploadsDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String originalFilename = photo.getOriginalFilename();
                String fileExtension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String fileName = System.currentTimeMillis() + fileExtension;
                String filePath = uploadsDir + fileName;
                java.nio.file.Path path = java.nio.file.Paths.get(filePath);
                java.nio.file.Files.write(path, photo.getBytes());
                // Asignar la URL relativa para la imagen
                author.setUrlPhotography("/" + uploadsDir + fileName);
            } catch (Exception e) {
                model.addAttribute("error", "Error al cargar la imagen: " + e.getMessage());
                return "author/form";
            }
        }

        // Crear el autor y obtener el resultado
        String result = autorService.create(author);
        model.addAttribute("result", result);
        return "author/result";
    }

    // Método POST para actualizar un autor existente.
    @PostMapping("/update")
    public String updateAuthor(@ModelAttribute Author author,
                               @RequestParam("photo") MultipartFile photo,
                               Model model) {
        // Truncar el resumen
        author.setSummary(truncateText(author.getSummary()));

        // Procesar la imagen si se envía un archivo nuevo
        if (!photo.isEmpty()) {
            try {
                String uploadsDir = "images/authors/";
                java.io.File dir = new java.io.File(uploadsDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String originalFilename = photo.getOriginalFilename();
                String fileExtension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String fileName = System.currentTimeMillis() + fileExtension;
                String filePath = uploadsDir + fileName;
                java.nio.file.Path path = java.nio.file.Paths.get(filePath);
                java.nio.file.Files.write(path, photo.getBytes());
                author.setUrlPhotography("/" + uploadsDir + fileName);
            } catch (Exception e) {
                model.addAttribute("error", "Error al cargar la imagen: " + e.getMessage());
                return "author/form";
            }
        }

        // Actualiza el autor en la base de datos
        String result = autorService.update(author);
        model.addAttribute("result", result);
        return "author/result";
    }

    // Método GET para listar todos los autores con filtros.
    @GetMapping("/list")
    public String listAuthors(@RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String lastName,
                              Model model) {
        // Si ambos filtros están vacíos, se muestran todos los autores.
        model.addAttribute("authors", autorService.search(firstName, lastName));
        // Se envían de vuelta los parámetros para mostrarlos en el formulario
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        return "author/list";  // Ubicación del template de listado
    }

    // Método GET para mostrar el formulario de edición.
    @GetMapping("/edit/{id}")
    public String editAuthor(@PathVariable long id, Model model) throws Exception {
        Author autor = autorService.findById(id)
                .orElseThrow(() -> new Exception("Entity not found"));
        model.addAttribute("author", autor);
        return "author/form";
    }

    // Método GET para eliminar un autor
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable long id, Model model) throws Exception {
        autorService.delete(id);
        return "redirect:/author/list";
    }

    public String truncateText(String text) {
        if (text != null && text.length() > 200) {
            return text.substring(0, 197) + "...";
        }
        return text;
    }
}
