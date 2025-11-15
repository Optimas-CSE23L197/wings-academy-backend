package com.main_service.main_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main_service.main_service.model.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {

}
