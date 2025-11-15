package com.main_service.main_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main_service.main_service.dto.StudentRequest;
import com.main_service.main_service.model.Student;
import com.main_service.main_service.repository.StudentRepo;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public String registerStudent(StudentRequest studentRequest) {
        Student newStudent = new Student();

        newStudent.setStudentName(studentRequest.getStudentName());
        newStudent.setStudentEmail(studentRequest.getStudentEmail());
        studentRepo.save(newStudent);
        System.out.println(newStudent);
        System.out.println("Student Register Successfull");
        System.out.println(newStudent.getStudentName());
        System.out.println(newStudent.getStudentEmail());
        return "Student Register Successfull";
    }
}
