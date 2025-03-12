package es.ua.biblioteca.service;

import java.util.List;
import java.util.Optional;

import es.ua.biblioteca.model.Author;
import es.ua.biblioteca.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService implements IAuthorService {

    @Autowired
    private AuthorRepository repository;

    @Override
    public List<Author> findAll() {
        return (List<Author>) repository.findAll();
    }

    @Override
    public Optional<Author> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public String create(Author autor) {
        Author b = repository.save(autor);
        return "Autor created:" + b.toString();
    }

    @Override
    public String delete(long id) {
        Optional<Author> autor = repository.findById(id);

        if(autor.isPresent())
            repository.delete(autor.get());
        return "Autor deleted";
    }

    @Override
    public String update(Author autor) {
        if (autor.getId() != 0) {
            Author b = repository.save(autor);
            return "Autor updated:" + b.toString();
        }else
            return "No id provided for the autor";
    }

    @Override
    public List<Author> search(String firstName, String lastName) {
        if ((firstName == null || firstName.isEmpty()) && (lastName == null || lastName.isEmpty())) {
            return (List<Author>) repository.findAll();
        } else {
            return repository.searchByNames(firstName, lastName);
        }
    }

}
