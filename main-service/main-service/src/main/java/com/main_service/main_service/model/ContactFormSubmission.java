package com.main_service.main_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact_form_submission")
@Data
public class ContactFormSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 200)
    private String organization;

    @Column(nullable = false, length = 100)
    private String region;

    @Column(nullable = false, length = 100)
    private String industry;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(name = "consent_contact", nullable = false)
    private boolean consentContact;

    @Column(name = "consent_marketing")
    private boolean consentMarketing;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt = LocalDateTime.now();
}
