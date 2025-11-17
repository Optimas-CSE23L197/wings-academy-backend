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
        name = "services",
        indexes = {
                @Index(name = "idx_service_category", columnList = "category"),
                @Index(name = "idx_service_title", columnList = "title")
        }
)
public class Service {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String includes;

    @Column(name = "tech_stack", columnDefinition = "TEXT")
    private String techStack;

    @Column(columnDefinition = "TEXT")
    private String delivery;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(columnDefinition = "TEXT")
    private String link;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public enum Category {
        GOVT,
        TECH,
        PROMOTIONAL,
        OTHERS
    }
}