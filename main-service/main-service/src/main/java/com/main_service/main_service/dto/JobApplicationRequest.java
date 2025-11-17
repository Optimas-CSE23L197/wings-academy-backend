package com.main_service.main_service.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class JobApplicationRequest {

    private Long jobReferenceNumber;

    private String firstName;
    private String lastName;

    private String email;
    private String country;
    private String experienceLevel;

    private String applicationType;
    private String skills;

    private String countryCode;
    private String mobileNumber;

    private MultipartFile resume;  // <-- handles actual file
}
