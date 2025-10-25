package com.cryfirock.auth.model;

import java.util.Date;

import lombok.Data;

@Data
public class Error {
    private String message;
    private String error;
    private int status;
    private Date date;
}
