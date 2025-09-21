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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ==========================================================================
 * Paso 2.2: Definición de la entidad Role que representa la tabla roles
 * ==========================================================================
 */

// Clase mapeada a tabla roles
// Hibernate usa ese mapeo para convertir filas a objetos
@Entity
// Cuando clase y tabla no coinciden en nombre
// Permite añadir restricciones únicas a campos
@Table(name = "roles")
// Genera getters
@Getter
// Genera setters
@Setter
// Genera constructor vacío
@NoArgsConstructor
// Genera constructor con todos los campos
@AllArgsConstructor
public class Role {

    // Clave primaria
    @Id
    // Generación automática incremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Setter deshabilitado
    @Setter(lombok.AccessLevel.NONE)
    private Long id;

    // JPA mapea atributo a columna cuando no coinciden
    // Restricciones aplicadas al generar la tabla desde JPA
    @Column(name = "role", nullable = false, unique = true)
    @Size(max = 50)
    private String name;

    // Cuando convierto a JSON no meto el campo roles del otro lado
    // User mantiene una lista de roles y Role mantiene una lista de usuarios
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

}
