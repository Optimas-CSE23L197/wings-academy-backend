package com.main_service.main_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.main_service.main_service.dto.ApiResponse;
import com.main_service.main_service.dto.JobApplicationRequest;
import com.main_service.main_service.service.JobApplicationService;

@RestController
@RequestMapping("/main/api/v1/application")
@CrossOrigin(origins = "*")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    // Submit Job Application
    @PostMapping("/submit")
    public ResponseEntity<ApiResponse> submitApplication(@RequestBody JobApplicationRequest request) {
        ApiResponse response = jobApplicationService.submitApplication(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
