package com.cryfirock.account.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
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

    /**
     * 1. Constructor que inyecta las dependencias del servicio.
     * 2. Los atributos no requieren ser inyectados con @Autowired.
     *
     * @param accountRepository Repositorio de cuentas.
     */
    public AccountServiceImpl(
            JpaAccountRepository accountRepository,
            JpaAccountUserRepository accountUserRepository,
            JpaAccountProductRepository accountProductRepository) {
        this.accountRepository = accountRepository;
        this.accountUserRepository = accountUserRepository;
        this.accountProductRepository = accountProductRepository;
    }

    /**
     * Método de creación de una cuenta.
     * 
     * {@inheritDoc}
     */
    @Override @Transactional
    public AccountResponseDto create(AccountRequestDto request) {
        // Se una nueva instancia de la cuenta.
        Account account = new Account();

        // Pasa los datos del request a la cuenta.
        applyRequest(account, request);

        // Almacena la cuenta en la base de datos y retorna la cuenta con el id.
        Account savedAccount = accountRepository.save(account);

        // Guarda las relaciones de la cuenta con los usuarios y productos.
        SaveRelationsHelper saveRelationsHelper = new SaveRelationsHelper();
        saveRelationsHelper.saveRelations(
                savedAccount.getId(),
                request.userIds(),
                request.productIds());

        // Retorna la cuenta con las relaciones.
        return buildResponse(savedAccount);
    }

    /**
     * Método de actualización de una cuenta.
     * 
     * {@inheritDoc}
     */
    @Override @Transactional
    public AccountResponseDto update(Long id, AccountRequestDto request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        applyRequest(account, request);
        Account savedAccount = accountRepository.save(account);
        SaveRelationsHelper saveRelationsHelper = new SaveRelationsHelper();
        saveRelationsHelper.saveRelations(savedAccount.getId(), request.userIds(),
                request.productIds());
        return buildResponse(savedAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional(readOnly = true)
    public AccountResponseDto findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return buildResponse(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional(readOnly = true)
    public List<AccountResponseDto> findByUserId(Long userId) {
        List<Long> accountIds = accountUserRepository.findAllByUserId(userId).stream()
                .map(AccountUser::getAccountId)
                .distinct()
                .toList();
        return accountRepository.findAllById(accountIds).stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional
    public void delete(Long id) {
        accountUserRepository.deleteAllByAccountId(id);
        accountProductRepository.deleteAllByAccountId(id);
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
        List<Long> userIds = accountUserRepository.findAllByAccountId(account.getId()).stream()
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
