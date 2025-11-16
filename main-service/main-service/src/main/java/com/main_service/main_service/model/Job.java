package com.main_service.main_service.model;


import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "job_table")
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    private String jobTitle;
    private String shortDescription;
    private String fullDescription;

    @Column(unique = true)
    private Long referenceNumber;


    @ElementCollection
    @CollectionTable(name = "job_skill_set", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "skill")
    private List<String> skillSet;

    private String location;

    private int experienceMin;
    private int experienceMax;

    private int openings;

    private LocalDate postedDate;
    private LocalDate expiryDate;

    private boolean active;
}
