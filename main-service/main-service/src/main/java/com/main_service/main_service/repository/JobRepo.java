package com.main_service.main_service.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main_service.main_service.model.Job;

@Repository
public interface JobRepo extends JpaRepository<Job, Long> {

    // Fetch active jobs only
    List<Job> findByActive(boolean active);

    // Fetch jobs by location
    List<Job> findByLocation(String location);

    // Skill search (exact match)
    List<Job> findBySkillSetContaining(String skill);

    // Jobs that are not expired
    List<Job> findByExpiryDateAfter(LocalDate date);

    // Jobs posted recently (last X days)
    List<Job> findByPostedDateAfter(LocalDate date);

    // Unique Reference Number support
    boolean existsByReferenceNumber(Long referenceNumber);

    Job findByReferenceNumber(Long referenceNumber);
}
