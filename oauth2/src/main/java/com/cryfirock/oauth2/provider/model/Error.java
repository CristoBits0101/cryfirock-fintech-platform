package com.cryfirock.oauth2.provider.model;

import java.util.Date;

public record Error(String message, String error, int status, Date date) {
}
