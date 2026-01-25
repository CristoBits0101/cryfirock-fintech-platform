package com.cryfirock.account.helper;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cryfirock.account.entity.AccountProduct;
import com.cryfirock.account.entity.AccountUser;
import com.cryfirock.account.repository.JpaAccountProductRepository;
import com.cryfirock.account.repository.JpaAccountUserRepository;
import com.cryfirock.account.type.AccountProductStatus;
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
         * Sincroniza las relaciones: elimina las que ya no están y añade las nuevas.
         * 
         * @param accountId Identificador de la cuenta.
         * @param userIds Identificadores de los usuarios.
         * @param productIds Identificadores de los productos.
         */
        public void saveRelations(
                        Long accountId,
                        List<Long> userIds,
                        List<Long> productIds) {
                // ============================================================================================
                // --- Sincronización de Usuarios ---
                // ============================================================================================
                // 1.1. Si la lista de usuarios es null no se modifican las relaciones.
                if (userIds != null) {
                        // 1.2. Lista de usuarios proveniente de la petición.
                        // 1.3. Limpiar la lista de valores nulos y duplicados.
                        List<Long> requestUserIds = userIds
                                        // Convierte la lista de usuarios en una lista de stream.
                                        .stream()
                                        // Filtra el id de usuarios nulos.
                                        .filter(Objects::nonNull)
                                        // Elimina los usuarios duplicados.
                                        .distinct()
                                        // Convierte la lista de stream en una lista.
                                        .toList();

                        // 1.4. Obtener las relaciones existentes en la base de datos.
                        List<AccountUser> existingAccountUsersRelationshipsDatabase = accountUserRepository.findAllByAccountId(accountId);

                        // 1.5. Identifica relaciones a eliminar (existen en BD pero no en la petición).
                        List<AccountUser> usersToDeleteDatabase = existingAccountUsersRelationshipsDatabase
                                        // Convierte la lista de relaciones en una lista de stream.
                                        .stream()
                                        // Filtra las relaciones que no están en la petición.
                                        .filter(existingRelationship -> !requestUserIds.contains(existingRelationship.getUserId()))
                                        // Convierte la lista de stream en una lista.
                                        .toList();

                        // 1.6. Identifica los IDs que ya existen para no duplicarlos.
                        List<Long> existingRelationshipsUserIds = existingAccountUsersRelationshipsDatabase
                                        // Convierte la lista de relaciones en una lista de stream.
                                        .stream()
                                        // Obtiener IDs de usuarios de las relaciones existentes.
                                        .map(AccountUser::getUserId)
                                        // Convierte la lista de stream en una lista.
                                        .toList();

                        // 1.7. Identifica las que están en la nueva lista pero no en BD.
                        List<AccountUser> usersToAdd = requestUserIds
                                        .stream()
                                        .filter(id -> !existingRelationshipsUserIds.contains(id))
                                        .map(userId -> new AccountUser(accountId, userId))
                                        .toList();

                        // ============================================================================================
                        // --- Ejecución de Cambios ---
                        // ============================================================================================
                        // 1.8. Si hay relaciones a eliminar las elimina.
                        if (!usersToDeleteDatabase.isEmpty()) accountUserRepository.deleteAll(usersToDeleteDatabase);
                        // 1.9. Si hay relaciones nuevas las guarda.
                        if (!usersToAdd.isEmpty()) accountUserRepository.saveAll(usersToAdd);
                }

                // Obtiene la lista de productos.
                List<Long> incomingProductIds = ValidationUtil.safeMutableList(productIds).stream()
                                .filter(Objects::nonNull)
                                .distinct()
                                .toList();

                // Obtiene las relaciones existentes en base de datos.
                List<AccountProduct> existingAccountProducts = accountProductRepository
                                .findAllByAccountId(accountId);

                // Identifica las relaciones a eliminar.
                List<AccountProduct> productsToDelete = existingAccountProducts.stream()
                                .filter(ap -> !incomingProductIds.contains(ap.getProductId()))
                                .toList();

                // Identifica los IDs que ya existen.
                List<Long> existingProductIds = existingAccountProducts.stream()
                                .map(AccountProduct::getProductId)
                                .toList();

                // Identifica las relaciones a añadir.
                List<AccountProduct> productsToAdd = incomingProductIds.stream()
                                .filter(id -> !existingProductIds.contains(id))
                                .map(productId -> new AccountProduct(
                                                accountId,
                                                productId,
                                                AccountProductStatus.ACTIVE))
                                .toList();

                // Ejecuta los cambios.
                if (!productsToDelete.isEmpty()) {
                        accountProductRepository.deleteAll(productsToDelete);
                }
                if (!productsToAdd.isEmpty()) {
                        accountProductRepository.saveAll(productsToAdd);
                }
        }
}
