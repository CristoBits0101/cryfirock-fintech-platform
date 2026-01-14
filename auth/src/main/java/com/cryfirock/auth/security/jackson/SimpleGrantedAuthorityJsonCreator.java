package com.cryfirock.auth.security.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 1. Mixin de Jackson para deserializar SimpleGrantedAuthority.
 * 2. Clase abstracta que define el constructor para Jackson.
 * 3. Permite crear autoridades desde JSON con la propiedad "authority".
 * 4. Utilizada por JwtValidationFilter para extraer autoridades del token.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public abstract class SimpleGrantedAuthorityJsonCreator {
    /**
     * 1. Constructor anotado para que Jackson cree SimpleGrantedAuthority.
     * 2. Mapea la propiedad "authority" del JSON al parámetro role.
     *
     * @param role Nombre del rol o autoridad.
     */
    @JsonCreator
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role) {
    }
}
