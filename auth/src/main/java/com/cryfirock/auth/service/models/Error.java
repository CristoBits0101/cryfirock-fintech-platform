package com.cryfirock.auth.service.models;

import java.util.Date;

import lombok.Data;

@Data
public class Error {

    /**
     * Attributes
     */
    private String message;
    private String error;
    private int status;
    private Date date;

}