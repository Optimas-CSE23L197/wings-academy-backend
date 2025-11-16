package com.main_service.main_service.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "job_application")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @Column(nullable = false)
    private Long jobReferenceNumber;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(nullable = false)
    private String experienceLevel;

    @Column(nullable = false)
    private String applicationType;

    @Column(nullable = false, length = 255)
    private String skills;

    @Column(nullable = false, length = 10)
    private String countryCode;

    @Column(nullable = false, length = 20)
    private String mobileNumber;

    private String resumePath;

    private LocalDateTime appliedDate = LocalDateTime.now();
}
