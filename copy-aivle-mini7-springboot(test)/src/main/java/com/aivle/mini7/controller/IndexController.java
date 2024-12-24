package com.aivle.mini7.controller;

import com.aivle.mini7.client.api.FastApiClient;
import com.aivle.mini7.client.dto.EmergencyAnalysisResponse;
import com.aivle.mini7.client.dto.HospitalResponse;
import com.aivle.mini7.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final FastApiClient fastApiClient;
    private final LogService logService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/recommend_hospital")
    public ModelAndView recommend_hospital(
            @RequestParam("request") String request,
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam(value = "the_number_of_hospital", defaultValue = "3") int numberOfHospitals){



//        FastApiClient 를 호출한다.
        EmergencyAnalysisResponse response =
                fastApiClient.getHospital(request, latitude, longitude, numberOfHospitals);

        log.info("hospital: {}", response.getResult().toString());

//        강사님이 적어놓으신 emclass의 하드 코딩 수정
        if(response.getResult() !=null){
            logService.saveLog(response, request, latitude, longitude);
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("recommend_hospital");
        mv.addObject("summary", response.getSummary());
        mv.addObject("predictedClass", response.getPredictedClass());
        mv.addObject("hospitalList", response.getResult());

        return mv;
    }
}


