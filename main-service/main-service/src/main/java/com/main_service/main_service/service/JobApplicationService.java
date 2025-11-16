package com.main_service.main_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main_service.main_service.dto.ApiResponse;
import com.main_service.main_service.dto.JobApplicationRequest;
import com.main_service.main_service.model.JobApplication;
import com.main_service.main_service.repository.JobApplicationRepo;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepo jobApplicationRepo;

    public ApiResponse submitApplication(JobApplicationRequest request) {

        JobApplication application = new JobApplication();

        application.setJobReferenceNumber(request.getJobReferenceNumber());
        application.setFirstName(request.getFirstName());
        application.setLastName(request.getLastName());
        application.setEmail(request.getEmail());
        application.setCountry(request.getCountry());
        application.setExperienceLevel(request.getExperienceLevel());
        application.setApplicationType(request.getApplicationType());
        application.setSkills(request.getSkills());
        application.setCountryCode(request.getCountryCode());
        application.setMobileNumber(request.getMobileNumber());

        // right now no file upload, so resumePath stays null
        application.setResumePath(null);

        jobApplicationRepo.save(application);

        return new ApiResponse(201, "Application submitted successfully", application);
    }
}
