package com.main_service.main_service.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.main_service.main_service.dto.ApiResponse;
import com.main_service.main_service.dto.JobApplicationRequest;
import com.main_service.main_service.model.JobApplication;
import com.main_service.main_service.repository.JobApplicationRepo;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepo jobApplicationRepo;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/resumes/";

    public ApiResponse submitApplication(JobApplicationRequest request, MultipartFile resumeFile) {

        JobApplication application = new JobApplication();

        application.setJobReferenceNumber(request.getReferenceNumber());
        application.setFirstName(request.getFirstName());
        application.setLastName(request.getLastName());
        application.setEmail(request.getEmail());
        application.setCountry(request.getCountry());
        application.setExperienceLevel(request.getExperience());
        application.setApplicationType(request.getApplicationType());
        application.setState(request.getState());
        application.setSkills(request.getSkills());
        application.setCountryCode(request.getCountryCode());
        application.setMobileNumber(request.getMobileNumber());

        if (resumeFile != null && !resumeFile.isEmpty()) {
            try {
                File uploadPath = new File(UPLOAD_DIR);
                if (!uploadPath.exists()) uploadPath.mkdirs();

                String fileName = System.currentTimeMillis() + "_" + resumeFile.getOriginalFilename();
                String filePath = UPLOAD_DIR + fileName;

                File file = new File(filePath);
                resumeFile.transferTo(file);

                application.setResumePath(filePath);

            } catch (IOException io) {
                io.printStackTrace();
                return new ApiResponse(500, "File upload failed", null);
            }
        } else {
            application.setResumePath(null);
        }

        jobApplicationRepo.save(application);

        return new ApiResponse(201, "Application submitted successfully", application);
    }
}
