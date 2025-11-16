package com.main_service.main_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "services")
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID serviceId;

    private String title;

    private String subtitle;

    private String bannerImage;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "service_description",
            joinColumns = @JoinColumn(name = "service_id")
    )
    @Column(name = "paragraph")
    private List<String> description;

    private String mainImage;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "service_requirements",
            joinColumns = @JoinColumn(name = "service_id")
    )
    @Column(name = "requirement")
    private List<String> requirements;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "service_other_services",
            joinColumns = @JoinColumn(name = "service_id")
    )
    private List<OtherService> otherServices;

    // ======================= INNER CLASS =======================
    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OtherService {
        private String title;
        private String shortText;
        private String image;
        private String refId;   // example: "web-development"
    }
}
