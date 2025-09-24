package com.cryfirock.auth.service.models;

import java.util.Date;

import lombok.Data;

@Data
public class Error {

    /**
     * Atributos
     */
    private String message;
    private String error;
    private int status;
    private Date date;

}