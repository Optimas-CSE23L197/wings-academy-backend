package com.main_service.main_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "instructor_students",
        indexes = {
                @Index(name = "idx_instructor_id", columnList = "instructor_id"),
                @Index(name = "idx_student_id", columnList = "student_id"),
                @Index(name = "idx_course_id", columnList = "course_id")
        }
)
public class InstructorStudent {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(name = "instructor_id", nullable = false, columnDefinition = "UUID")
    private UUID instructorId;

    @Column(name = "student_id", nullable = false, columnDefinition = "UUID")
    private UUID studentId;

    @Column(name = "course_id", nullable = false, columnDefinition = "UUID")
    private UUID courseId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
