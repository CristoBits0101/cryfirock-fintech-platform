package com.cryfirock.account.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryfirock.account.dto.AccountRequestDto;
import com.cryfirock.account.dto.AccountResponseDto;
import com.cryfirock.account.service.api.IAccountService;

/**
 * 1. Controlador REST para operaciones CRUD de cuentas.
 * 2. Expone endpoints para consultar relaciones con usuarios y productos.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-24
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    // Servicio para operaciones de cuentas.
    private final IAccountService accountService;

    /**
     * Constructor que inyecta el servicio de cuentas.
     *
     * @param accountService Servicio de cuentas.
     */
    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 1. Crea una cuenta con usuarios y productos asociados.
     *
     * @param request Datos de la cuenta y relaciones.
     * @return Cuenta creada con relaciones.
     */
    @PostMapping
    public ResponseEntity<AccountResponseDto> create(@RequestBody AccountRequestDto request) {
        return ResponseEntity.ok(accountService.create(request));
    }

    /**
     * 1. Actualiza una cuenta y sus relaciones asociadas.
     *
     * @param id Identificador de la cuenta.
     * @param request Datos actualizados de la cuenta y relaciones.
     * @return Cuenta actualizada con relaciones.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDto> update(
            @PathVariable Long id,
            @RequestBody AccountRequestDto request
    ) {
        return ResponseEntity.ok(accountService.update(id, request));
    }

    /**
     * 1. Obtiene una cuenta con usuarios y productos asociados.
     *
     * @param id Identificador de la cuenta.
     * @return Cuenta con relaciones.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    /**
     * 1. Obtiene cuentas asociadas a un usuario.
     * 2. Incluye productos asociados a cada cuenta.
     *
     * @param userId Identificador del usuario.
     * @return Lista de cuentas con relaciones.
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<AccountResponseDto>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.findByUserId(userId));
    }

    /**
     * 1. Elimina una cuenta y sus relaciones.
     *
     * @param id Identificador de la cuenta.
     * @return Respuesta sin contenido.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

