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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public Double getInputLatitude() {
        return inputLatitude;
    }

    public void setInputLatitude(Double inputLatitude) {
        this.inputLatitude = inputLatitude;
    }

    public Double getInputLongitude() {
        return inputLongitude;
    }

    public void setInputLongitude(Double inputLongitude) {
        this.inputLongitude = inputLongitude;
    }

    public Integer getEmClass() {
        return emClass;
    }

    public void setEmClass(Integer emClass) {
        this.emClass = emClass;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", datetime=" + datetime +
                ", inputText='" + inputText + '\'' +
                ", inputLatitude=" + inputLatitude +
                ", inputLongitude=" + inputLongitude +
                ", emClass=" + emClass +
                ", summary='" + summary + '\'' +
                ", hospitals=" + hospitals +
                '}';
    }
}