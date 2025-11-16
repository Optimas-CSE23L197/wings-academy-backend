package com.main_service.main_service.dto;

import java.time.LocalDate;
import java.util.List;


public class JobRequest {
    private String jobTitle;

    private String shortDescription;

    private String fullDescription;

    private List<String> skillSet;

    private String location;

    private int experienceMin;
    private int experienceMax;

    private int openings;

    private LocalDate postedDate;
    private LocalDate expiryDate;

    private boolean active;
}
