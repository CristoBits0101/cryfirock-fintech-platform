package com.cryfirock.auth.service.entities;

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
 * ==============================================================================
 * Paso 2.1: Definición de la entidad Role que representa la tabla roles
 * ==============================================================================
 */

// Clase mapeada a tabla roles
// Hibernate usa ese mapeo para convertir filas a objetos
@Entity
// Cuando clase y tabla no coinciden en nombre
// Permite añadir restricciones únicas a campos
@Table(name = "roles")
public class Role {

    /**
     * ==========================================================================
     * Paso 2.2: Columnas
     * ==========================================================================
     */

    // Clave primaria
    @Id
    // Generación automática incremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // JPA mapea atributo a columna cuando no coinciden
    // Restricciones aplicadas al generar la tabla desde JPA
    @Column(name = "role", nullable = false, unique = true)
    @Size(max = 50)
    private String name;

    // Cuando convierto a JSON no meto el campo roles del otro lado
    // User mantiene una lista de roles y Role mantiene una lista de users
    // El bucle ocurre por las referencias en memoria entre instancias
    // Es una referencia cíclica en el grafo de objetos en memoria
    @JsonIgnoreProperties({ "roles" })
    // Relación de muchos a muchos pertenece al mapeo definido en User
    // En esta clase no se configura ninguna tabla intermedia
    // Lista de usuarios vinculados a ESTA instancia de Role
    // Se puebla a través de la tabla intermedia users_roles
    // La doble lista existe solo si defines la relación bidireccional
    // Permite hacer joins y consultas desde ambas tablas User y Role
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    /**
     * ==========================================================================
     * Paso 2.3: Constructores
     * ==========================================================================
     */

    // Constructor vacío requerido si hay otros con parámetros
    // Spring crea el constructor vacío si no hay otros con parámetros
    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    /**
     * ==========================================================================
     * Paso 2.4: Métodos Getters
     * ==========================================================================
     */

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    /**
     * ==========================================================================
     * Paso 2.5: Métodos Setters
     * ==========================================================================
     */

    // El id lo genera la base de datos
    // Si dejas un setId() alguien podría asignar un valor y romper la integridad
    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
