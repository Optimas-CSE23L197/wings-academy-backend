package com.main_service.main_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main_service.main_service.dto.ApiResponse;
import com.main_service.main_service.dto.ContactFromRequest;
import com.main_service.main_service.model.ContactFormSubmission;
import com.main_service.main_service.repository.ContactFormRepo;

@Service
public class ContactFormService {

    @Autowired
    private ContactFormRepo contactFormRepo;

    public ApiResponse contactFormSubmission(ContactFromRequest submitContactForm) {

        // Create entity and map DTO fields
        ContactFormSubmission addDetails = new ContactFormSubmission();
        addDetails.setFirstName(submitContactForm.getFirstName());
        addDetails.setLastName(submitContactForm.getLastName());
        addDetails.setEmail(submitContactForm.getEmail());
        addDetails.setOrganization(submitContactForm.getOrganization());
        addDetails.setRegion(submitContactForm.getRegion());
        addDetails.setIndustry(submitContactForm.getIndustry());
        addDetails.setMessage(submitContactForm.getMessage());
        addDetails.setConsentContact(submitContactForm.isConsentContact());
        addDetails.setConsentMarketing(submitContactForm.isConsentMarketing());

        // Save to DB
        contactFormRepo.save(addDetails);

        // Return API response
        return new ApiResponse(201, "Contact form submitted successfully!", addDetails);
    }
}
