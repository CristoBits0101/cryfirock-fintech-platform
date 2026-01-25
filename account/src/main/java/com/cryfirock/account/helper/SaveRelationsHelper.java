package com.cryfirock.account.helper;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cryfirock.account.entity.AccountProduct;
import com.cryfirock.account.entity.AccountUser;
import com.cryfirock.account.repository.JpaAccountProductRepository;
import com.cryfirock.account.repository.JpaAccountUserRepository;
import com.cryfirock.account.util.ValidationUtil;

/**
 * 1. Helper que gestiona la persistencia de las relaciones de una cuenta con usuarios y productos.
 * 2. Orquesta la persistencia de las relaciones de una cuenta con usuarios y productos.
 * 
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-25
 */
@Component
public class SaveRelationsHelper {
        // Repositorio de acceso a los datos de las relaciones con los usuarios.
        @Autowired
        private JpaAccountUserRepository accountUserRepository;

        // Repositorio de acceso a los datos de las relaciones con los productos.
        @Autowired
        private JpaAccountProductRepository accountProductRepository;

        /**
         * Guarda las relaciones de la cuenta con los usuarios y productos.
         * 
         * @param accountId Identificador de la cuenta.
         * @param userIds Identificadores de los usuarios.
         * @param productIds Identificadores de los productos.
         */
        public void saveRelations(
                        Long accountId,
                        List<Long> userIds,
                        List<Long> productIds) {
                // Obtiene la lista de usuarios y productos.
                List<AccountUser> accountUsers = ValidationUtil
                                // Obtiene la lista de usuarios.
                                .safeMutableList(userIds)
                                // Convierte la lista de usuarios en una lista de stream.
                                .stream()
                                // Filtra la lista de usuarios para eliminar los nulos del stream.
                                .filter(Objects::nonNull)
                                // Por cada usuario se crea una relacion de cuenta con usuario.
                                .map(userId -> new AccountUser(
                                                null,      // Id de la relacion (autogenerado).
                                                accountId, // Id de la cuenta obtenido al crear la cuenta.
                                                userId,    // Id de los usuario obtenido del JSON de la petición.
                                                null))     // Audit (autogenerado).
                                // Obtiene la lista de usuarios.
                                .toList();

                // Obtiene la lista de productos.
                List<AccountProduct> accountProducts = ValidationUtil
                                // Obtiene la lista de productos.
                                .safeMutableList(productIds)
                                // Convierte la lista de productos en una lista de stream.
                                .stream()
                                // Filtra la lista de productos para eliminar los nulos del stream.
                                .filter(Objects::nonNull)
                                // Por cada producto se crea una relacion de cuenta con producto.
                                .map(productId -> new AccountProduct(
                                                null,
                                                accountId,
                                                productId,
                                                com.cryfirock.account.type.AccountProductStatus.ACTIVE,
                                                null))
                                // Obtiene la lista de productos.
                                .toList();

                // Si las entidades de relaciones no están vacías se persiste.
                if (!accountUsers.isEmpty()) accountUserRepository.saveAll(accountUsers);
                if (!accountProducts.isEmpty()) accountProductRepository.saveAll(accountProducts);
        }
}
