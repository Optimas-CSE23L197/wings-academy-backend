package com.main_service.main_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main_service.main_service.dto.ApiResponse;
import com.main_service.main_service.dto.JobRequest;

@RestController
@RequestMapping("/main/api/v1")
public class JobController {
    

    @PostMapping("/job/create")
    public ResponseEntity<ApiResponse> addNewJob(@RequestBody JobRequest jobRequest) {

        ApiResponse response = new ApiResponse(200,"Job Added Successfully");
        return new ResponseEntity<>(200,response);
    }
}
