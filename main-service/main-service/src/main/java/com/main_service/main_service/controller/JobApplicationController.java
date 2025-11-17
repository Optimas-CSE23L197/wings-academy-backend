package com.main_service.main_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.main_service.main_service.dto.ApiResponse;
import com.main_service.main_service.dto.JobApplicationRequest;
import com.main_service.main_service.service.JobApplicationService;

@RestController
@RequestMapping("/main/api/v1/application")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @PostMapping(value = "/submit", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse> submitApplication(
            @ModelAttribute JobApplicationRequest request,
            @RequestPart(value = "resumeFile", required = false) MultipartFile resumeFile) {

        ApiResponse response = jobApplicationService.submitApplication(request, resumeFile);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
