# Módulo de Productos

## Tipos de productos por categoría

Los subtipos se gestionan como registros del catálogo y se asocian a la categoría principal.

### ACCOUNT
- CHECKING
- BASIC_PAYMENT_ACCOUNT
- SALARY_ACCOUNT
- SAVINGS
- YOUTH
- BUSINESS_CHECKING
- MULTICURRENCY
- TERM_DEPOSIT
- NOTICE_ACCOUNT
- ESCROW
- PREPAID_WALLET

### CARD
- DEBIT_CARD
- CREDIT_CARD

### CREDIT
- PERSONAL_LOAN
- MORTGAGE
- AUTO_LOAN
- SME_LOAN
- BNPL
- OVERDRAFT
- CREDIT_LINE

### CRYPTO
- CRYPTO_SPOT
- CRYPTO_CUSTODY
- STAKING
- EARN_FLEX
- EARN_LOCKED
- LENDING
- BORROW
- COLLATERAL

### DERIVATIVES
- MARGIN
- FUTURES
- PERPETUALS
- OPTIONS

### FINANCING
- PERSONAL_LOAN
- MORTGAGE
- AUTO_LOAN
- SME_LOAN
- BNPL

### INVESTMENT
- BROKERAGE
- CUSTODY_SECURITIES
- MUTUAL_FUNDS
- PENSION

### LOYALTY
- REWARDS

## Estado del módulo

Este módulo mantiene el catálogo de productos como enums y aún no expone endpoints públicos.

## Verificación rápida

```bash
./mvnw test
```
