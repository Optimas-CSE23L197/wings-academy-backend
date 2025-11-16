package com.main_service.main_service.dto;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class JobRequest {
    @NotBlank
    private String jobTitle;
    private String shortDescription;
    private String fullDescription;

    @NotEmpty
    private List<String> skillSet;
    private String location;

    @Min(0)
    private int experienceMin;

    private int experienceMax;

    @Min(1)
    private int openings;

    private LocalDate postedDate;

    @Future
    private LocalDate expiryDate;

    private boolean active;
}
