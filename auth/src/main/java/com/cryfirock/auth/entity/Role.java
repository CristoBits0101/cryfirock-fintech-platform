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
 * 003: 
 */
@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "role", nullable = false, unique = true)
  @Size(max = 50)
  private String name;

  @JsonIgnoreProperties({ "roles" })
  @ManyToMany(mappedBy = "roles")
  private List<User> users;

  public Role() {
  }

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
