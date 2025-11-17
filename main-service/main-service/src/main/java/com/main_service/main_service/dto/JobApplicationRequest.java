package com.main_service.main_service.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class JobApplicationRequest {

    private Long referenceNumber;

    private String firstName;
    private String lastName;

    private String email;
    private String country;

    private String experience;

    private String state;
    private String applicationType;
    private String skills;

    private String countryCode;
    private String mobileNumber;

    private MultipartFile resumeFile;
}
