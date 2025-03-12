package es.ua.biblioteca.repository;

import java.util.List;

import es.ua.biblioteca.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    @Query("select b from Author b Where b.firstName like %:nombre%")
    List<Author> searchNombres(@Param("nombre") String nombre);

    // Método que permite filtrar por firstName y lastName
    @Query("SELECT a FROM Author a " +
            "WHERE (:firstName IS NULL OR LOWER(a.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) " +
            "AND (:lastName IS NULL OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')))")
    List<Author> searchByNames(@Param("firstName") String firstName, @Param("lastName") String lastName);
}