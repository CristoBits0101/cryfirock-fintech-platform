package com.cryfirock.auth.security.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ================================================================================================
 * Paso 18.1: Soporte para deserializar SimpleGrantedAuthority desde JWT
 * ================================================================================================
 */
public abstract class SimpleGrantedAuthorityJsonCreator {

    /**
     * ============================================================================================
     * Paso 18.2: Constructor para Jackson
     * ============================================================================================
     */
    @JsonCreator
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role) {
        // Jackson utiliza este constructor abstracto para mapear la autoridad
    }

}
