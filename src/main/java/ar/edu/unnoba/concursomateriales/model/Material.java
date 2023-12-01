package ar.edu.unnoba.concursomateriales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Ingrese el nombre de su material.")
    private String name;

    @NotEmpty(message = "Ingrese la descripción de su material.")
    private String description;

    private Status status;

    private Date date;

    private int votes = 0;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    public Material(Long id, String name, String description, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = Status.PENDING;
        this.date = new Date();
        this.votes = 0;
        this.owner = owner;
    }

    public Material() {
        this.status = Status.PENDING;
        this.votes = 0;
    }

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

}
