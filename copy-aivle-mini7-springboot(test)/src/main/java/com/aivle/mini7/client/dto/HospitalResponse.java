package com.aivle.mini7.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalResponse {
    private String hospitalName;
    private String address;
    private String phoneNumber1;
    private String phoneNumber2;
    private String hospitalType;
    private Double distance;
    private Double predictedDuration;
    private Double realDuration;
}


