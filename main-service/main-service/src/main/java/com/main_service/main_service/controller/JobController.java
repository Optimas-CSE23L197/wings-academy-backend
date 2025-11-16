package com.main_service.main_service.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.main_service.main_service.dto.ApiResponse;
import com.main_service.main_service.dto.JobRequest;
import com.main_service.main_service.service.JobService;

@RestController
@RequestMapping("/main/api/v1/job")
@CrossOrigin(origins = "*")
public class JobController {

    @Autowired
    private JobService jobService;

    // Create job
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addJob(@RequestBody JobRequest jobRequest) {
        ApiResponse response = jobService.addJob(jobRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Get all jobs
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllJobs() {
        ApiResponse response = jobService.getAllJob();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Get job by jobId (database primary key)
    @GetMapping("/id/{jobId}")
    public ResponseEntity<ApiResponse> getJobById(@PathVariable Long jobId) {
        ApiResponse response = jobService.getJobById(jobId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Get job by referenceNumber (8-digit public reference)
    @GetMapping("/ref/{refNumber}")
    public ResponseEntity<ApiResponse> getJobByReference(@PathVariable Long refNumber) {
        ApiResponse response = jobService.getJobByReference(refNumber);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Update job
    @PatchMapping("/update/{jobId}")
    public ResponseEntity<ApiResponse> updateJob(
            @PathVariable Long jobId,
            @RequestBody JobRequest jobRequest) {

        ApiResponse response = jobService.updateJob(jobId, jobRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Delete job
    @DeleteMapping("/delete/{jobId}")
    public ResponseEntity<ApiResponse> deleteJob(@PathVariable Long jobId) {
        ApiResponse response = jobService.deleteJob(jobId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

