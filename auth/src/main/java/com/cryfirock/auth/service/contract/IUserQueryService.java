package com.cryfirock.auth.service.contract;

/**
 * 1. Interfaz para el servicio de consulta de usuarios.
 * 2. Contrato de implementación para las clases que la implementen.
 * 3. Obliga a cumplir la convención de nombres y responsabilidades definidas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public interface IUserQueryService {
    /**
     * 1. Verifica si un email ya existe en el sistema.
     *
     * @param email el email a verificar.
     * @return true si el email existe y false en caso contrario.
     */
    boolean existsByEmail(String email);

    /**
     * 1. Verifica si un número de teléfono ya existe en el sistema.
     *
     * @param phoneNumber el número de teléfono a verificar.
     * @return true si el número de teléfono existe y false en caso contrario.
     */
    boolean existsByPhoneNumber(String phoneNumber);

    /**
     * 1. Verifica si un nombre de usuario ya existe en el sistema.
     *
     * @param username el nombre de usuario a verificar.
     * @return true si el nombre de usuario existe y false en caso contrario.
     */
    boolean existsByUsername(String username);
}
