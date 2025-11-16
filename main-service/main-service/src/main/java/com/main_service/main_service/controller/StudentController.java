package com.main_service.main_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main_service.main_service.dto.ApiResponse;
import com.main_service.main_service.dto.StudentRequest;
import com.main_service.main_service.service.StudentService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;
    
    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse> registerStudent(@RequestBody StudentRequest studentRequest) {
        studentService.registerStudent(studentRequest);
        ApiResponse response = new ApiResponse(200, "Student Register Successful");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/munsi")
    public String susiGala(){
        return "animesh bokachoda";
    } 
}
