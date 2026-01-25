package com.cryfirock.account.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.cryfirock.account.dto.AccountRequestDto;
import com.cryfirock.account.dto.AccountResponseDto;
import com.cryfirock.account.entity.Account;
import com.cryfirock.account.entity.AccountProduct;
import com.cryfirock.account.entity.AccountUser;
import com.cryfirock.account.helper.SaveRelationsHelper;
import com.cryfirock.account.repository.JpaAccountProductRepository;
import com.cryfirock.account.repository.JpaAccountRepository;
import com.cryfirock.account.repository.JpaAccountUserRepository;
import com.cryfirock.account.service.api.IAccountService;

/**
 * 1. Implementación del servicio para gestionar cuentas y sus relaciones.
 * 2. Orquesta la persistencia de cuentas con usuarios y productos asociados.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-24
 */
@Service
public class AccountServiceImpl implements IAccountService {
    // Repositorio de acceso a los datos de las cuentas bancarias.
    private final JpaAccountRepository accountRepository;
    // Repositorio de acceso a los datos de las relaciones con los usuarios.
    private final JpaAccountUserRepository accountUserRepository;
    // Repositorio de acceso a los datos de las relaciones con los productos.
    private final JpaAccountProductRepository accountProductRepository;
    // Helper para guardar relaciones.
    private final SaveRelationsHelper saveRelationsHelper;

    /**
     * 1. Constructor que inyecta las dependencias del servicio.
     * 2. Los atributos no requieren ser inyectados con @Autowired.
     *
     * @param accountRepository Repositorio de cuentas.
     */
    public AccountServiceImpl(
            JpaAccountRepository accountRepository,
            JpaAccountUserRepository accountUserRepository,
            JpaAccountProductRepository accountProductRepository,
            SaveRelationsHelper saveRelationsHelper) {
        this.accountRepository = accountRepository;
        this.accountUserRepository = accountUserRepository;
        this.accountProductRepository = accountProductRepository;
        this.saveRelationsHelper = saveRelationsHelper;
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional
    public AccountResponseDto create(@NonNull AccountRequestDto request) {
        Objects.requireNonNull(request, "Request must not be null");
        // Se una nueva instancia de la cuenta.
        Account account = new Account();

        // Pasa los datos del request a la cuenta.
        applyRequest(account, request);

        // Almacena la cuenta en la base de datos y retorna la cuenta con el id.
        Account savedAccount = accountRepository.save(account);

        // Guarda las relaciones de la cuenta con los usuarios y productos.
        saveRelationsHelper.saveRelations(
                savedAccount.getId(),
                request.userIds(),
                request.productIds());

        // Retorna la cuenta con las relaciones.
        return buildResponse(savedAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional
    public AccountResponseDto update(@NonNull Long id, @NonNull AccountRequestDto request) {
        Objects.requireNonNull(id, "ID must not be null");
        Objects.requireNonNull(request, "Request must not be null");
        // Obtiene la cuenta que se va a actualizar.
        Account account = Objects.requireNonNull(
                accountRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        // Aplica a la cuenta solo los datos del request que se van a actualizar.
        applyRequest(account, request);

        // Almacena la cuenta en la base de datos y retorna la cuenta con el id.
        Account savedAccount = accountRepository.save(account);

        // Guarda las relaciones de la cuenta con los usuarios y productos.
        saveRelationsHelper.saveRelations(
                savedAccount.getId(),
                request.userIds(),
                request.productIds());

        // Retorna la cuenta con las relaciones.
        return buildResponse(savedAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional(readOnly = true)
    public AccountResponseDto findById(@NonNull Long id) {
        Objects.requireNonNull(id, "ID must not be null");
        // Obtiene una cuenta bancaria.
        Account account = accountRepository
                // Obtiene la cuenta por su id.
                .findById(id)
                // Si no se encuentra la cuenta lanza una excepción.
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
        // Retorna la cuenta con las relaciones.
        return buildResponse(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional(readOnly = true)
    public List<AccountResponseDto> findByUserId(@NonNull Long userId) {
        Objects.requireNonNull(userId, "User ID must not be null");
        // Obtiene una lista de IDs de cuentas bancarias.
        List<Long> accountIds = accountUserRepository
                .findAllByUserId(userId)
                .stream()
                .map(AccountUser::getAccountId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        // Si la lista está vacía, retorna una lista vacía.
        if (accountIds.isEmpty()) return List.of();
        // Retorna la lista de cuentas bancarias encontradas por el id de la relación.
        return accountRepository
                .findAllById(Objects.requireNonNull(accountIds))
                .stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional
    public void delete(@NonNull Long id) {
        Objects.requireNonNull(id, "ID must not be null");
        // Elimina todas las asociaciones de cuentas bancarias con usuarios.
        accountUserRepository.deleteAllByAccountId(id);
        // Elimina todas las asociaciones de cuentas bancarias con productos.
        accountProductRepository.deleteAllByAccountId(id);
        // Elimina la cuenta bancaria.
        accountRepository.deleteById(id);
    }

    /**
     * Aplica los datos del request a la cuenta.
     *
     * @param account
     * @param request
     */
    private void applyRequest(Account account, AccountRequestDto request) {
        account.setMainOwnerId(request.mainOwnerId());
        account.setFinancialAssetClass(request.financialAssetClass());
        account.setCurrencyCode(request.currencyCode());
        account.setIbanNumber(request.ibanNumber());
        account.setCurrentBalance(request.currentBalance());
        account.setBankAccountPurpose(request.bankAccountPurpose());
        account.setBankAccountOperational(request.bankAccountOperational());
        account.setBankAccountStatus(request.bankAccountStatus());
    }

    private AccountResponseDto buildResponse(Account account) {
        List<Long> userIds = accountUserRepository.findAllByAccountId(account.getId())
                .stream()
                .map(AccountUser::getUserId)
                .distinct()
                .toList();
        List<Long> productIds = accountProductRepository
                .findAllByAccountId(account.getId())
                .stream()
                .map(AccountProduct::getProductId)
                .distinct()
                .toList();
        return new AccountResponseDto(
                account.getId(),
                account.getMainOwnerId(),
                account.getFinancialAssetClass(),
                account.getCurrencyCode(),
                account.getIbanNumber(),
                account.getCurrentBalance(),
                account.getBankAccountPurpose(),
                account.getBankAccountOperational(),
                account.getBankAccountStatus(),
                account.getAudit(),
                userIds,
                productIds);
    }
}
