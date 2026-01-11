package com.cryfirock.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryfirock.auth.service.IUserQueryService;

@RestController
@CrossOrigin
@RequestMapping("/api/validations")
public class UserValidationController {

    @Autowired
    private IUserQueryService userQueryService;

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = userQueryService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> checkUsernameExists(@PathVariable String username) {
        boolean exists = userQueryService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/phone/{phoneNumber}")
    public ResponseEntity<Boolean> checkPhoneNumberExists(@PathVariable String phoneNumber) {
        boolean exists = userQueryService.existsByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(exists);
    }
}
