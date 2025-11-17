package com.main_service.main_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main_service.main_service.dto.ApiResponse;
import com.main_service.main_service.dto.ContactFromRequest;
import com.main_service.main_service.service.ContactFormService;

@RestController
@RequestMapping("/main/api/v1/contact-form")
public class ContactFormController {
    
    @Autowired
    private ContactFormService contactFormService;

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse> submitContactForm(@RequestBody ContactFromRequest contactFromRequest) {
        ApiResponse response = contactFormService.contactFormSubmission(contactFromRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
