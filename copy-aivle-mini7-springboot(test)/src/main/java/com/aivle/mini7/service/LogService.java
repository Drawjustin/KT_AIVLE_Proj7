package com.aivle.mini7.service;

import com.aivle.mini7.client.dto.EmergencyAnalysisResponse;
import com.aivle.mini7.client.dto.HospitalResponse;
import com.aivle.mini7.dto.LogDto;
import com.aivle.mini7.model.Hospital;
import com.aivle.mini7.model.Log;
import com.aivle.mini7.repository.HospitalRepository;
import com.aivle.mini7.repository.LogRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LogService {
    private final LogRepository logRepository;
    private final HospitalRepository hospitalRepository;

    @Transactional(readOnly = true)
    public Page<LogDto.ResponseList> getLogList(Pageable pageable) {
        return logRepository.findAll(pageable).map(LogDto.ResponseList::of);
    }

    @Transactional(readOnly = true)
    public Page<LogDto.ResponseList> getLogList(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

//        return logRepository.findByDatetimeBetween(startDateTime, endDateTime, pageable)
//                .map(LogDto.ResponseList::of);
        Page<Log> logs = logRepository.findByDatetimeBetween(startDateTime, endDateTime, pageable);

        // 디버깅을 위한 코드
        logs.getContent().forEach(log -> {
            Log logWithHospitals = logRepository.findByIdWithHospitals(log.getId())
                    .orElseThrow(() -> new RuntimeException("Log not found"));
            System.out.println("Log ID: " + log.getId());
            System.out.println("Actual hospitals count: " + logWithHospitals.getHospitals().size());
            logWithHospitals.getHospitals().forEach(h ->
                    System.out.println("Hospital: " + h.getName()));
        });

        return logs.map(LogDto.ResponseList::of);
    }

    public void saveLog(EmergencyAnalysisResponse response, String request, double latitude, double longitude) {
        // Log 엔티티 생성
        Log log = Log.builder()
                .id(UUID.randomUUID().hashCode())
                .datetime(LocalDateTime.now())
                .inputText(request)
                .summary(response.getSummary())
                .inputLatitude(latitude)
                .inputLongitude(longitude)
                .emClass(response.getPredictedClass())
                .build();

        // result가 있을 경우에만 병원 정보 저장
        if (response.getResult() != null && !response.getResult().isEmpty()) {
            // 모든 병원 정보를 반복문으로 처리
            for (HospitalResponse hospitalResponse : response.getResult()) {
                Hospital hospital = Hospital.builder()
                        .id(UUID.randomUUID().hashCode())
                        .emClass(response.getPredictedClass())
                        .name(hospitalResponse.getHospitalName())
                        .addr(hospitalResponse.getAddress())
                        .tel1(hospitalResponse.getPhoneNumber1())
                        .tel2(hospitalResponse.getPhoneNumber2())
                        .distance(hospitalResponse.getDistance())
                        .predDuration(hospitalResponse.getPredictedDuration())
                        .realDuration(hospitalResponse.getRealDuration())
                        .build();

                log.addHospital(hospital);
            }
        }

        logRepository.save(log);
    }

    @Transactional(readOnly = true)
    public LogDto.ResponseList getLog(Integer id) {
        return logRepository.findByIdWithHospitals(id)
                .map(LogDto.ResponseList::of)
                .orElseThrow(() -> new EntityNotFoundException("Log not found with id: " + id));
    }
}