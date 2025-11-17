package com.main_service.main_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactFromRequest {
    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String lastName;

    @Email
    @NotBlank
    @Size(max = 150)
    private String email;

    @NotBlank
    @Size(max = 200)
    private String organization;

    @NotBlank
    private String region;

    @NotBlank
    private String industry;

    @NotBlank
    @Size(max = 1500)
    private String message;

    private boolean consentContact;

    private boolean consentMarketing;
}
