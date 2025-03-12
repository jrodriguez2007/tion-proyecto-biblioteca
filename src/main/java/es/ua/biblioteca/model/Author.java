package es.ua.biblioteca.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Se renombran las propiedades para mayor claridad
    @Column(name = "firstName", length = 100)
    private String firstName;

    @Column(name = "lastName", length = 100)
    private String lastName;

    @Column(name = "dateBirth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateBirth;

    @Column(name = "summary", length = 200)
    private String summary;

    @Column(name = "urlPhotography", length = 100)
    private String urlPhotography;

    @Column(name = "status")
    private Boolean status;

    public Author() {
    }

    public Author(String firstName, String lastName, Date dateBirth, String summary, String urlPhotography, Boolean status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.summary = summary;
        this.urlPhotography = urlPhotography;
        this.status = status;
    }

    public Author(Long id, String firstName, String lastName, Date dateBirth, String summary, String urlPhotography, Boolean status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.summary = summary;
        this.urlPhotography = urlPhotography;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrlPhotography() {
        return urlPhotography;
    }

    public void setUrlPhotography(String urlPhotography) {
        this.urlPhotography = urlPhotography;
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
        Author other = (Author) obj;
        return Objects.equals(id, other.id)
                && Objects.equals(firstName, other.firstName)
                && Objects.equals(lastName, other.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateBirth=" + dateBirth +
                ", summary='" + summary + '\'' +
                ", urlPhotography='" + urlPhotography + '\'' +
                ", status=" + status +
                '}';
    }
}
