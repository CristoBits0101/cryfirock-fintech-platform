package com.cryfirock.auth.service.security.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// Convierte las autoridades al formato de Spring Security
public abstract class SimpleGrantedAuthorityJsonCreator {

    /**
     * Constructores
     *
     * @param role rol asociado a la autoridad
     */
    @JsonCreator
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role) {
    }

}
