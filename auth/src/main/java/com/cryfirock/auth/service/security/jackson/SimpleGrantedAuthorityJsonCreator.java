package com.cryfirock.msvc.users.msvc_users.security.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// Convert authorities to Spring Security format
public abstract class SimpleGrantedAuthorityJsonCreator {

    /**
     * Constructors
     * 
     * @param role
     */
    @JsonCreator
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role) {
    }

}
