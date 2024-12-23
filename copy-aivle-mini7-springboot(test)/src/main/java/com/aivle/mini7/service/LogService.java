package com.aivle.mini7.service;

import com.aivle.mini7.client.dto.HospitalResponse;
import com.aivle.mini7.dto.LogDto;
import com.aivle.mini7.model.Log;
import com.aivle.mini7.repository.LogRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.time.LocalDate;
import java.time.ZoneId;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LogService {

    private final LogRepository logRepository;

    // 기존 메서드 유지
    @Transactional(readOnly = true)
    public Page<LogDto.ResponseList> getLogList(Pageable pageable) {
        Page<Log> logs = logRepository.findAll(pageable);
        return logs.map(LogDto.ResponseList::of);
    }

    //기간 조회
    //        DateTimeFormatter sdf = DateTimeFormatter.ofPattern( "EEE MMM dd HH:mm:ss zzz uuuu" ).withLocale( Locale.US ) ;
    @Transactional(readOnly = true)
    public Page<LogDto.ResponseList> getLogList(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        try {
            // 웹에서 받은 LocalDate를 DB 형식의 String으로 변환
            SimpleDateFormat dbFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul")); // 한국 시간대 설정

            // 시작일 00:00:00 설정
            Date startDateTime = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            String startDateStr = dbFormat.format(startDateTime);

            // 종료일 23:59:59 설정
            Date endDateTime = Date.from(endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).minusSeconds(1).toInstant());
            String endDateStr = dbFormat.format(endDateTime);

            log.info("검색 시작일시: {}", startDateStr);
            log.info("검색 종료일시: {}", endDateStr);

            Page<Log> logs = logRepository.findByDatetimeBetween(startDateStr, endDateStr, pageable);
            log.info("검색된 결과 수: {}", logs.getTotalElements());

            return logs.map(LogDto.ResponseList::of);

        } catch (Exception e) {
            log.error("날짜 변환 중 오류 발생", e);
            return Page.empty(pageable);
        }
    }

    // 기존 saveLog 메서드 유지
    public void saveLog(List<HospitalResponse> hospitalResponseList, String request, double latitude, double longitude, int emClass) {
        Log hospitalLog = Log.builder()
                .inputText(request)
                .inputLatitude(latitude)
                .inputLongitude(longitude)
                .emClass(emClass)
                .datetime(String.valueOf(new Date()))
                .build();
        int count = 1;
        for(HospitalResponse hospitalResponse : hospitalResponseList) {
            log.info("hospitalResponse: {}", hospitalResponse);
            switch (count) {
                case 1:
                    hospitalLog.setHospital1(hospitalResponse.getHospitalName());
                    hospitalLog.setAddr1(hospitalResponse.getAddress());
                    hospitalLog.setTel1(hospitalResponse.getPhoneNumber1());
                    break;
                case 2:
                    hospitalLog.setHospital2(hospitalResponse.getHospitalName());
                    hospitalLog.setAddr2(hospitalResponse.getAddress());
                    hospitalLog.setTel2(hospitalResponse.getPhoneNumber1());
                    break;
                case 3:
                    hospitalLog.setHospital3(hospitalResponse.getHospitalName());
                    hospitalLog.setAddr3(hospitalResponse.getAddress());
                    hospitalLog.setTel3(hospitalResponse.getPhoneNumber1());
                    break;
            }
            count++;
        }
        logRepository.save(hospitalLog);
    }
}