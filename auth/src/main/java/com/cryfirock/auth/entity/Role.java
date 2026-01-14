package com.cryfirock.auth.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

/**
 * 1. Entidad JPA que representa una tabla en la base de datos.
 * 2. Nombre de la tabla en la base de datos.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Entity
@Table(name = "roles")
public class Role {
    /**
     * 1. Clave primaria de la entidad.
     * 2. Generación automática del valor de la clave primaria.
     * 3. Columna de la tabla que almacena el ID del rol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 1. Nombre del rol.
     * 2. Columna de la tabla que almacena el nombre del rol.
     * 3. El nombre no puede ser nulo y debe ser único.
     * 4. Tamaño máximo del nombre es de 50 caracteres.
     */
    @Column(name = "role", nullable = false, unique = true)
    @Size(max = 50)
    private String name;

    /**
     * 1. Lista de usuarios asociados a este rol.
     * 2. Evita referencias circulares al serializar a JSON.
     * 3. Relación muchos a muchos con la entidad User.
     */
    @JsonIgnoreProperties({ "roles" })
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    // Constructores.
    public Role() {
    }

    // Getters y setters.
    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
