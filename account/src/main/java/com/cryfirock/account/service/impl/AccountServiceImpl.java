package com.cryfirock.account.service.impl;

import java.util.List;
import java.util.Objects;
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
import com.cryfirock.account.repository.JpaAccountProductRepository;
import com.cryfirock.account.repository.JpaAccountRepository;
import com.cryfirock.account.repository.JpaAccountUserRepository;
import com.cryfirock.account.service.api.IAccountService;

/**
 * 1. Implementación del servicio para gestionar cuentas y relaciones.
 * 2. Orquesta la persistencia de cuentas con usuarios y productos asociados.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-24
 */
@Service
public class AccountServiceImpl implements IAccountService {
    // Repositorio de cuentas.
    private final JpaAccountRepository accountRepository;

    // Repositorio de relaciones cuenta usuario.
    private final JpaAccountUserRepository accountUserRepository;

    // Repositorio de relaciones cuenta producto.
    private final JpaAccountProductRepository accountProductRepository;

    /**
     * Constructor que inyecta dependencias del servicio.
     *
     * @param accountRepository Repositorio de cuentas.
     * @param accountUserRepository Repositorio de relaciones cuenta usuario.
     * @param accountProductRepository Repositorio de relaciones cuenta producto.
     */
    public AccountServiceImpl(
            JpaAccountRepository accountRepository,
            JpaAccountUserRepository accountUserRepository,
            JpaAccountProductRepository accountProductRepository
    ) {
        this.accountRepository = accountRepository;
        this.accountUserRepository = accountUserRepository;
        this.accountProductRepository = accountProductRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public AccountResponseDto create(AccountRequestDto request) {
        Account account = new Account();
        applyRequest(account, request);
        Account savedAccount = accountRepository.save(account);
        saveRelations(savedAccount.getId(), request.userIds(), request.productIds());
        return buildResponse(savedAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public AccountResponseDto update(Long id, AccountRequestDto request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        applyRequest(account, request);
        Account savedAccount = accountRepository.save(account);
        accountUserRepository.deleteAllByAccountId(savedAccount.getId());
        accountProductRepository.deleteAllByAccountId(savedAccount.getId());
        saveRelations(savedAccount.getId(), request.userIds(), request.productIds());
        return buildResponse(savedAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public AccountResponseDto findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return buildResponse(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
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
    @Override
    @Transactional
    public void delete(Long id) {
        accountUserRepository.deleteAllByAccountId(id);
        accountProductRepository.deleteAllByAccountId(id);
        accountRepository.deleteById(id);
    }

    private void applyRequest(Account account, AccountRequestDto request) {
        account.setOwnerId(request.ownerId());
        account.setAsset(request.asset());
        account.setCurrency(request.currency());
        account.setNumber(request.number());
        account.setBalance(request.balance());
        account.setNature(request.nature());
        account.setOperational(request.operational());
        account.setStatus(request.status());
    }

    private void saveRelations(Long accountId, List<Long> userIds, List<Long> productIds) {
        List<AccountUser> accountUsers = safeList(userIds).stream()
                .filter(Objects::nonNull)
                .map(userId -> new AccountUser(null, accountId, userId))
                .toList();
        List<AccountProduct> accountProducts = safeList(productIds).stream()
                .filter(Objects::nonNull)
                .map(productId -> new AccountProduct(null, accountId, productId))
                .toList();
        if (!accountUsers.isEmpty()) {
            accountUserRepository.saveAll(accountUsers);
        }
        if (!accountProducts.isEmpty()) {
            accountProductRepository.saveAll(accountProducts);
        }
    }

    private List<Long> safeList(List<Long> values) {
        return values == null ? List.of() : values;
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
                account.getOwnerId(),
                account.getAsset(),
                account.getCurrency(),
                account.getNumber(),
                account.getBalance(),
                account.getNature(),
                account.getOperational(),
                account.getStatus(),
                account.getAudit(),
                userIds,
                productIds
        );
    }
}
