package com.cryfirock.auth.entity;

import java.time.LocalDate;
import java.util.List;

import com.cryfirock.auth.model.AccountStatus;
import com.cryfirock.auth.validation.IExistsByEmail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 003: 
 */
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "given_name", nullable = false)
  @NotEmpty(message = "{NotEmpty.user.givenName}")
  @Pattern(regexp = "^[A-Za-zÁáÉéÍíÓóÚúÝýÆæØøÅåÄäÖöÑñÜüß]+$", message = "{Pattern.user.givenName}")
  @Size(min = 1, max = 50, message = "{Size.user.givenName}")
  private String givenName;

  @Column(name = "family_name", nullable = false)
  @NotEmpty(message = "{NotEmpty.user.familyName}")
  @Pattern(regexp = "^[A-Za-zÁáÉéÍíÓóÚúÝýÆæØøÅåÄäÖöÑñÜüß]+(\\s[A-Za-zÁáÉéÍíÓóÚúÝýÆæØøÅåÄäÖöÑñÜüß]+)*$", message = "{Pattern.user.familyName}")
  @Size(min = 1, max = 50, message = "{Size.user.familyName}")
  private String familyName;

  @Column(nullable = false, unique = true)
  @IExistsByEmail(message = "{Email.user.email}")
  @NotBlank(message = "{NotBlank.user.email}")
  @Size(min = 1, max = 100, message = "{Size.user.email}")
  private String email;

  @Column(name = "phone_number", nullable = false, unique = true)
  @NotEmpty(message = "{NotEmpty.user.phoneNumber}")
  @Pattern(regexp = "^[0-9]{9,20}$", message = "{Pattern.user.phoneNumber}")
  @Size(min = 9, max = 20, message = "{Size.user.phoneNumber}")
  private String phoneNumber;

  @Column(nullable = false)
  @NotBlank(message = "{NotBlank.user.username}")
  @Size(min = 1, max = 50, message = "{Size.user.username}")
  private String username;

  @Column(name = "password_hash", nullable = false)
  @NotBlank(message = "{NotBlank.user.password}")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String passwordHash;

  @Column(nullable = false)
  @NotNull(message = "{NotNull.user.dob}")
  private LocalDate dob;

  @Column(nullable = false)
  @NotNull(message = "{NotNull.user.address}")
  private String address;

  @Column(name = "account_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private AccountStatus enabled = AccountStatus.ACTIVE;

  @JsonIgnoreProperties({ "users", "handler", "hibernateLazyInitializer" })
  @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
      "user_id", "role_id" }))
  @ManyToMany
  private List<Role> roles;

  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private boolean admin;

  @PrePersist
  public void prePersist() {
    enabled = AccountStatus.ACTIVE;
  }
}
