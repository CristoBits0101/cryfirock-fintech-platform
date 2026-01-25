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
        // Si la lista de usuarios es null no se modifican las relaciones.
        if (userIds != null) {
            // 1.Lista de usuarios proveniente de la petición.
            // 2.Limpiar la lista de valores nulos y duplicados.
            List<Long> requestUserIds = userIds
                    // Convierte la lista de usuarios en una lista de stream.
                    .stream()
                    // Filtra el id de usuarios nulos.
                    .filter(Objects::nonNull)
                    // Elimina los usuarios duplicados.
                    .distinct()
                    // Convierte la lista de stream en una lista.
                    .toList();

            // Obtener las relaciones existentes en la base de datos.
            List<AccountUser> existingAccountUsersRelationshipsDatabase = accountUserRepository
                    .findAllByAccountId(accountId);

            // Desvincula los usuarios que ya no están.
            unlinkOldAccountUser(
                    requestUserIds,
                    existingAccountUsersRelationshipsDatabase);

            // Vincula los usuarios que no están.
            linkNewAccountUser(
                    accountId,
                    requestUserIds,
                    existingAccountUsersRelationshipsDatabase);
        }

        if (productIds != null) {
            // Obtiene la lista de productos.
            List<Long> requestProductIds = productIds
                    .stream()
                    .filter(Objects::nonNull)
                    .distinct()
                    .toList();

            // Obtiene las relaciones existentes en base de datos.
            List<AccountProduct> existingAccountProducts = accountProductRepository
                    .findAllByAccountId(accountId);

            // Desvincula los productos que ya no están.
            unlinkOldAccountProduct(
                    requestProductIds,
                    existingAccountProducts);

            // Vincula los productos que no están.
            linkNewAccountProduct(
                    accountId,
                    requestProductIds,
                    existingAccountProducts);
        }

    }

    /**
     * Identifica las relaciones a eliminar.
     *
     * @param requestUserIds Lista de IDs de usuarios proveniente de la petición.
     * @param existingAccountUsersRelationshipsDatabase Lista de relaciones existentes en la
     * base de datos.
     */
    public void unlinkOldAccountUser(
            List<Long> requestUserIds,
            List<AccountUser> existingAccountUsersRelationshipsDatabase) {
        // Identifica relaciones a eliminar (existen en BD pero no en la petición).
        List<AccountUser> usersToDeleteDatabase = existingAccountUsersRelationshipsDatabase
                // Convierte la lista de relaciones en una lista de stream.
                .stream()
                // Filtra las relaciones que no están en la petición.
                .filter(existingRelationship -> !requestUserIds
                        .contains(existingRelationship.getUserId()))
                // Convierte la lista de stream en una lista.
                .toList();

        // Si hay relaciones a eliminar las elimina.
        if (!usersToDeleteDatabase.isEmpty())
            accountUserRepository.deleteAll(usersToDeleteDatabase);
    }

    /**
     * Identifica las relaciones a añadir.
     *
     * @param requestUserIds Lista de IDs de usuarios proveniente de la petición.
     * @param existingAccountUsersRelationshipsDatabase Lista de relaciones existentes en la
     * base de datos.
     */
    public void linkNewAccountUser(
            Long accountId,
            List<Long> requestUserIds,
            List<AccountUser> existingAccountUsersRelationshipsDatabase) {
        // Identifica los IDs que ya existen para no duplicarlos.
        List<Long> existingRelationshipsUserIds = existingAccountUsersRelationshipsDatabase
                // Convierte la lista de relaciones en una lista de stream.
                .stream()
                // Obtiener IDs de usuarios de las relaciones existentes.
                .map(AccountUser::getUserId)
                // Convierte la lista de stream en una lista.
                .toList();

        // Identifica las que están en la nueva lista pero no en BD.
        List<AccountUser> usersToAdd = requestUserIds
                .stream()
                .filter(id -> !existingRelationshipsUserIds.contains(id))
                .map(userId -> new AccountUser(accountId, userId))
                .toList();

        // Si hay relaciones nuevas las guarda.
        if (!usersToAdd.isEmpty()) accountUserRepository.saveAll(usersToAdd);
    }

    /**
     * Identifica las relaciones de productos a eliminar.
     *
     * @param requestProductIds Lista de IDs de productos proveniente de la petición.
     * @param existingAccountProductsRelationshipsDatabase Lista de relaciones existentes en la
     * base de datos.
     */
    public void unlinkOldAccountProduct(
            List<Long> requestProductIds,
            List<AccountProduct> existingAccountProductsRelationshipsDatabase) {
        // Identifica relaciones a eliminar (existen en BD pero no en la petición).
        List<AccountProduct> productsToDeleteDatabase = existingAccountProductsRelationshipsDatabase
                // Convierte la lista de relaciones en una lista de stream.
                .stream()
                // Filtra las relaciones que no están en la petición.
                .filter(existingRelationship -> !requestProductIds
                        .contains(existingRelationship.getProductId()))
                // Convierte la lista de stream en una lista.
                .toList();

        // Si hay relaciones a eliminar las elimina.
        if (!productsToDeleteDatabase.isEmpty())
            accountProductRepository.deleteAll(productsToDeleteDatabase);
    }

    /**
     * Identifica las relaciones de productos a añadir.
     *
     * @param accountId Identificador de la cuenta.
     * @param requestProductIds Lista de IDs de productos proveniente de la petición.
     * @param existingAccountProductsRelationshipsDatabase Lista de relaciones existentes en la
     * base de datos.
     */
    public void linkNewAccountProduct(
            Long accountId,
            List<Long> requestProductIds,
            List<AccountProduct> existingAccountProductsRelationshipsDatabase) {
        // Identifica los IDs que ya existen para no duplicarlos.
        List<Long> existingRelationshipsProductIds = existingAccountProductsRelationshipsDatabase
                // Convierte la lista de relaciones en una lista de stream.
                .stream()
                // Obtiener IDs de productos de las relaciones existentes.
                .map(AccountProduct::getProductId)
                // Convierte la lista de stream en una lista.
                .toList();

        // Identifica las que están en la nueva lista pero no en BD.
        List<AccountProduct> productsToAdd = requestProductIds
                .stream()
                .filter(id -> !existingRelationshipsProductIds.contains(id))
                .map(productId -> new AccountProduct(
                        accountId,
                        productId,
                        AccountProductStatus.ACTIVE))
                .toList();

        // Si hay relaciones nuevas las guarda.
        if (!productsToAdd.isEmpty()) accountProductRepository.saveAll(productsToAdd);
    }
}
