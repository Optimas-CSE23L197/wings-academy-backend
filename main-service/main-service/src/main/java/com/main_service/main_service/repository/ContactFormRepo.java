package com.main_service.main_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main_service.main_service.model.ContactFormSubmission;

public interface ContactFormRepo extends JpaRepository<ContactFormSubmission, Long> {
    
}
