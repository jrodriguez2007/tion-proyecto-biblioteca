package es.ua.biblioteca.service;

import es.ua.biblioteca.model.Author;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {
    List<Author> findAll();
    Optional<Author> findById(long id);
    String create(Author autor);
    String update(Author autor);
    String delete(long id);
    List<Author> search(String firstName, String lastName);
}
