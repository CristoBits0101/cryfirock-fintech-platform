package com.cryfirock.accounts.service.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cryfirock.accounts.dto.AccountCreateDto;
import com.cryfirock.accounts.dto.AccountResponseDto;
import com.cryfirock.accounts.dto.AccountUpdateDto;
import com.cryfirock.accounts.entity.Account;
import com.cryfirock.accounts.exception.AccountNotFoundException;
import com.cryfirock.accounts.mapper.AccountMapper;
import com.cryfirock.accounts.model.AccountNature;
import com.cryfirock.accounts.model.AccountStatus;
import com.cryfirock.accounts.repository.JpaAccountRepository;
import com.cryfirock.accounts.service.contract.IAccountService;
import com.cryfirock.accounts.util.AccountNumberGenerator;

/**
 * 1. Implementación del servicio de cuentas.
 * 2. Maneja la lógica de negocio para operaciones de cuentas.
 * 3. Utiliza transacciones para garantizar la integridad de los datos.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-18
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Service
public class AccountServiceImpl implements IAccountService {
    /**
     * Repositorio para acceso a datos de cuentas.
     */
    private final JpaAccountRepository accountRepository;

    /**
     * Mapper para conversión entre entidades y DTOs.
     */
    private final AccountMapper accountMapper;

    /**
     * Constructor que inyecta las dependencias necesarias.
     *
     * @param accountRepository Repositorio de cuentas.
     * @param accountMapper Mapper de cuentas.
     */
    public AccountServiceImpl(JpaAccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional
    public AccountResponseDto create(AccountCreateDto dto) {
        Account account = accountMapper.toEntity(dto);
        account.setAccountNumber(generateUniqueAccountNumber());
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toResponseDto(savedAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional(readOnly = true)
    public Optional<AccountResponseDto> findById(Long id) {
        return accountRepository.findById(id).map(accountMapper::toResponseDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional(readOnly = true)
    public Optional<AccountResponseDto> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(accountMapper::toResponseDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional(readOnly = true)
    public List<AccountResponseDto> findByOwnerId(Long ownerId) {
        return accountRepository.findByOwnerId(ownerId).stream().map(accountMapper::toResponseDto)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional(readOnly = true)
    public List<AccountResponseDto> findByOwnerIdAndStatus(Long ownerId, AccountStatus status) {
        return accountRepository.findByOwnerIdAndStatus(ownerId, status).stream()
                .map(accountMapper::toResponseDto)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional(readOnly = true)
    public List<AccountResponseDto> findByOwnerIdAndNature(Long ownerId, AccountNature nature) {
        return accountRepository.findByOwnerIdAndNature(ownerId, nature).stream()
                .map(accountMapper::toResponseDto)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional
    public AccountResponseDto update(Long id, AccountUpdateDto dto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        if (dto.status() != null) {
            account.setStatus(dto.status());
        }

        Account updatedAccount = accountRepository.save(account);
        return accountMapper.toResponseDto(updatedAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional(readOnly = true)
    public List<AccountResponseDto> findAll() {
        return accountRepository.findAll().stream().map(accountMapper::toResponseDto).toList();
    }

    /**
     * 1. Genera un número de cuenta único.
     * 2. Verifica que no exista en la base de datos.
     *
     * @return Número de cuenta único generado.
     */
    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = AccountNumberGenerator.generate();
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }
}
