package com.aivle.mini7.model;

import jakarta.persistence.*;
import lombok.*;

// Hospital 테이블
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hospital")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "log_id", nullable = false)
    private Log log;

    @Column(nullable = false)
    private Integer emClass;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String addr;

    @Column(nullable = false)
    private String tel1;

    @Column
    private String tel2;

    @Column(name = "hospital_type")
    private String hospitalType;

    @Column(nullable = false)
    private Double distance;

    @Column
    private Double predDuration;

    @Column
    private Double realDuration;

    @Builder
    public Hospital(Integer id, Integer emClass, String name, String addr, String tel1, String tel2,
                    String hospitalType, Double distance, Double predDuration, Double realDuration) {
        this.id = id;
        this.emClass = emClass;
        this.name = name;
        this.addr = addr;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.hospitalType = hospitalType;
        this.distance = distance;
        this.predDuration = predDuration;
        this.realDuration = realDuration;
    }

    protected void setLog(Log log) {
        this.log = log;
    }
}
