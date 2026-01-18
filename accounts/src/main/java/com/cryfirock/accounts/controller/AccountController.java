package com.cryfirock.accounts.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cryfirock.accounts.dto.AccountCreateDto;
import com.cryfirock.accounts.dto.AccountResponseDto;
import com.cryfirock.accounts.dto.AccountUpdateDto;
import com.cryfirock.accounts.model.AccountNature;
import com.cryfirock.accounts.model.AccountStatus;
import com.cryfirock.accounts.service.contract.IAccountService;

import jakarta.validation.Valid;

/**
 * 1. Controlador REST para operaciones de cuentas.
 * 2. Permite solicitudes CORS desde cualquier origen.
 * 3. Mapea las solicitudes a /api/accounts.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-18
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@RestController @CrossOrigin @RequestMapping("/api/accounts")
public class AccountController {
    /**
     * Servicio para operaciones de cuentas.
     */
    private final IAccountService accountService;

    /**
     * Constructor que inyecta las dependencias necesarias.
     *
     * @param accountService Servicio de cuentas.
     */
    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 1. Crea una nueva cuenta.
     * 2. Valida los datos de entrada.
     *
     * @param dto Datos para crear la cuenta.
     * @return ResponseEntity con la cuenta creada y status 201.
     */
    @PostMapping
    public ResponseEntity<AccountResponseDto> create(@Valid @RequestBody AccountCreateDto dto) {
        AccountResponseDto createdAccount = accountService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    /**
     * 1. Obtiene todas las cuentas del sistema.
     * 2. Solo para uso administrativo.
     *
     * @return ResponseEntity con la lista de cuentas.
     */
    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> findAll() {
        return ResponseEntity.ok(accountService.findAll());
    }

    /**
     * 1. Obtiene una cuenta por su ID.
     * 2. Retorna 404 si no existe.
     *
     * @param id Identificador de la cuenta.
     * @return ResponseEntity con la cuenta o 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> findById(@PathVariable Long id) {
        return accountService.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 1. Obtiene una cuenta por su número único.
     * 2. Retorna 404 si no existe.
     *
     * @param accountNumber Número de cuenta.
     * @return ResponseEntity con la cuenta o 404.
     */
    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<AccountResponseDto> findByAccountNumber(
            @PathVariable String accountNumber) {
        return accountService.findByAccountNumber(accountNumber).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 1. Obtiene todas las cuentas de un propietario.
     * 2. Permite filtrar por estado o naturaleza.
     *
     * @param ownerId Identificador del propietario.
     * @param status Estado de la cuenta (opcional).
     * @param nature Naturaleza de la cuenta (opcional).
     * @return ResponseEntity con la lista de cuentas.
     */
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<AccountResponseDto>> findByOwnerId(
            @PathVariable Long ownerId,
            @RequestParam(required = false) AccountStatus status,
            @RequestParam(required = false) AccountNature nature) {

        List<AccountResponseDto> accounts;

        if (status != null) {
            accounts = accountService.findByOwnerIdAndStatus(ownerId, status);
        } else if (nature != null) {
            accounts = accountService.findByOwnerIdAndNature(ownerId, nature);
        } else {
            accounts = accountService.findByOwnerId(ownerId);
        }

        return ResponseEntity.ok(accounts);
    }

    /**
     * 1. Actualiza el estado de una cuenta.
     * 2. Retorna 404 si la cuenta no existe.
     *
     * @param id Identificador de la cuenta.
     * @param dto Datos para actualizar.
     * @return ResponseEntity con la cuenta actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDto> update(@PathVariable Long id,
            @RequestBody AccountUpdateDto dto) {
        AccountResponseDto updatedAccount = accountService.update(id, dto);
        return ResponseEntity.ok(updatedAccount);
    }
}
