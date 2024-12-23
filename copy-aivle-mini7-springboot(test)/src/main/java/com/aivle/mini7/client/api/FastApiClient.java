package com.aivle.mini7.client.api;

import com.aivle.mini7.client.dto.EmergencyAnalysisResponse;
import com.aivle.mini7.client.dto.HospitalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * FastApiClient
 * @app.get("/hospital/{request}/{latitude}/{longitude}/{the_number_of_hospita}") 를 호출한다.
 */

@FeignClient(name = "fastApiClient", url = "${hospital.api.host}")
public interface FastApiClient {
     @GetMapping("/hospital_by_module")
     EmergencyAnalysisResponse getHospital(
             @RequestParam("request") String request,
             @RequestParam("latitude") double latitude,
             @RequestParam("longitude") double longitude,
             @RequestParam(value = "the_number_of_hospital", defaultValue = "3") int numberOfHospitals
     );
}
