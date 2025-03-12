package es.ua.biblioteca.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "pageNumbers")
    private int pageNumbers;

    // Se usa String para publication y originalPublication ya que en la tabla son varchar
    @Column(name = "publication")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publication;

    @Column(name = "originalPublication")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date originalPublication;

    // Relación ManyToOne con Author, usando la columna idAuthor
    @ManyToOne
    @JoinColumn(name = "idAuthor", referencedColumnName = "id")
    private Author author;

    // Campo para el id del idioma, se puede mantener como int
    @Column(name = "idLanguage")
    private int idLanguage;

    @Column(name = "URI", length = 100)
    private String URI;

    @Column(name = "CDU", length = 100)
    private String CDU;

    @Column(name = "status")
    private Boolean status;

    public Book() {
    }

    // Constructor sin id (para creación)
    public Book(String title, String description, int pageNumbers, Date publication,
                Date originalPublication, Author author, int idLanguage, String URI, String CDU, Boolean status) {
        this.title = title;
        this.description = description;
        this.pageNumbers = pageNumbers;
        this.publication = publication;
        this.originalPublication = originalPublication;
        this.author = author;
        this.idLanguage = idLanguage;
        this.URI = URI;
        this.CDU = CDU;
        this.status = status;
    }

    public Book(Long id, String title, String description, int pageNumbers, Date publication,
                Date originalPublication, Author author, int idLanguage, String URI, String CDU, Boolean status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pageNumbers = pageNumbers;
        this.publication = publication;
        this.originalPublication = originalPublication;
        this.author = author;
        this.idLanguage = idLanguage;
        this.URI = URI;
        this.CDU = CDU;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(int pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public Date getPublication() {
        return publication;
    }

    public void setPublication(Date publication) {
        this.publication = publication;
    }

    public Date getOriginalPublication() {
        return originalPublication;
    }

    public void setOriginalPublication(Date originalPublication) {
        this.originalPublication = originalPublication;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(int idLanguage) {
        this.idLanguage = idLanguage;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getCDU() {
        return CDU;
    }

    public void setCDU(String CDU) {
        this.CDU = CDU;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book other = (Book) obj;
        return Objects.equals(id, other.id)
                && Objects.equals(title, other.title)
                && Objects.equals(author, other.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pageNumbers=" + pageNumbers +
                ", publication='" + publication + '\'' +
                ", originalPublication='" + originalPublication + '\'' +
                ", author=" + (author != null ? author.getId() : null) +
                ", idLanguage=" + idLanguage +
                ", URI='" + URI + '\'' +
                ", CDU='" + CDU + '\'' +
                ", status=" + status +
                '}';
    }
}
