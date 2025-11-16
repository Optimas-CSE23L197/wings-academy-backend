package com.main_service.main_service.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main_service.main_service.dto.ApiResponse;
import com.main_service.main_service.dto.JobRequest;
import com.main_service.main_service.model.Job;
import com.main_service.main_service.repository.JobRepo;

@Service
public class JobService {

    @Autowired
    private JobRepo jobRepo;

    // Add job
    public ApiResponse addJob(JobRequest jobRequest) {

        // Validate experience range
        if (jobRequest.getExperienceMin() > jobRequest.getExperienceMax()) {
            return new ApiResponse(400, "Minimum experience cannot be greater than maximum experience", null);
        }

        Job job = new Job();

        job.setJobTitle(jobRequest.getJobTitle());
        job.setShortDescription(jobRequest.getShortDescription());
        job.setFullDescription(jobRequest.getFullDescription());
        job.setReferenceNumber(generateReferenceNumber());
        job.setSkillSet(jobRequest.getSkillSet());
        job.setLocation(jobRequest.getLocation());
        job.setExperienceMin(jobRequest.getExperienceMin());
        job.setExperienceMax(jobRequest.getExperienceMax());
        job.setOpenings(jobRequest.getOpenings());
        job.setExpiryDate(jobRequest.getExpiryDate());
        job.setPostedDate(LocalDate.now());
        job.setActive(jobRequest.isActive());

        jobRepo.save(job);

        return new ApiResponse(201, "Job created successfully", job);
    }

    // Get all jobs (exclude expired)
    public ApiResponse getAllJob() {

        LocalDate today = LocalDate.now();
        List<Job> jobs = jobRepo.findByExpiryDateAfter(today);

        return new ApiResponse(200, "All active jobs fetched successfully", jobs);
    }

    // Get job by database ID
    public ApiResponse getJobById(Long jobId) {

        Job job = jobRepo.findById(jobId).orElse(null);

        if (job == null) {
            return new ApiResponse(404, "Job not found", null);
        }

        return new ApiResponse(200, "Job fetched successfully", job);
    }

    // Update job
    public ApiResponse updateJob(Long jobId, JobRequest jobRequest) {

        Job job = jobRepo.findById(jobId).orElse(null);

        if (job == null) {
            return new ApiResponse(404, "Job not found", null);
        }

        job.setJobTitle(jobRequest.getJobTitle());
        job.setShortDescription(jobRequest.getShortDescription());
        job.setFullDescription(jobRequest.getFullDescription());
        job.setSkillSet(jobRequest.getSkillSet());
        job.setLocation(jobRequest.getLocation());
        job.setExperienceMin(jobRequest.getExperienceMin());
        job.setExperienceMax(jobRequest.getExperienceMax());
        job.setOpenings(jobRequest.getOpenings());
        job.setExpiryDate(jobRequest.getExpiryDate());
        job.setActive(jobRequest.isActive());

        jobRepo.save(job);

        return new ApiResponse(200, "Job updated successfully", job);
    }

    // Delete job
    public ApiResponse deleteJob(Long jobId) {

        Job job = jobRepo.findById(jobId).orElse(null);

        if (job == null) {
            return new ApiResponse(404, "Job not found", null);
        }

        jobRepo.delete(job);

        return new ApiResponse(200, "Job deleted successfully", null);
    }

    // Get job by reference number
    public ApiResponse getJobByReference(Long refNumber) {

        Job job = jobRepo.findByReferenceNumber(refNumber);

        if (job == null) {
            return new ApiResponse(404, "Job not found", null);
        }

        return new ApiResponse(200, "Job fetched successfully", job);
    }

    // Generate unique 8-digit reference number
    private Long generateReferenceNumber() {

        Random rand = new Random();
        long referenceNumber;

        do {
            referenceNumber = 10_000_000L + rand.nextInt(90_000_000);
        } while (jobRepo.existsByReferenceNumber(referenceNumber));

        return referenceNumber;
    }
}

