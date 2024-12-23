package com.aivle.mini7.client.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyAnalysisResponse {
    private String summary;
    private Integer predictedClass;
    private List<HospitalResponse> result;
}
