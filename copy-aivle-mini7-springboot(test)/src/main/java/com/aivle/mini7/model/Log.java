package com.aivle.mini7.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Log 테이블
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime datetime;

    @Column(name = "input_text", nullable = false)
    private String inputText;

    @Column(name = "input_latitude", nullable = false)
    private Double inputLatitude;

    @Column(name = "input_longitude", nullable = false)
    private Double inputLongitude;

    @Column(name = "em_class", nullable = false)
    private Integer emClass;

    @Column
    private String summary;

    @Builder.Default
    @OneToMany(mappedBy = "log", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Hospital> hospitals = new ArrayList<>();

    public void addHospital(Hospital hospital) {
        this.hospitals.add(hospital);
        hospital.setLog(this);
    }
}