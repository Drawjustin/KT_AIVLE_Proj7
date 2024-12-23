package com.aivle.mini7.dto;

import com.aivle.mini7.model.Log;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseList {
        private Integer id;  // ID 필드 추가
        private String inputText;
        private LocalDateTime datetime;
        private String summary;    // summary 필드 추가
        private Double inputLatitude;
        private Double inputLongitude;
        private Integer emClass;
        private List<HospitalInfo> hospitals;  // 병원 정보를 리스트로 관리

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class HospitalInfo {
            private String name;
            private String addr;
            private String tel1;
            private String tel2;
            private String hospitalType;
            private Double distance;
            private Double predDuration;
            private Double realDuration;
        }

//        public static ResponseList of(Log log) {
//            ResponseList dto = ResponseList.builder()
//                    .id(log.getId())
//                    .inputText(log.getInputText())
//                    .datetime(log.getDatetime())
//                    .summary(log.getSummary())
//                    .inputLatitude(log.getInputLatitude())
//                    .inputLongitude(log.getInputLongitude())
//                    .emClass(log.getEmClass())
//                    .build();
//
//            // 병원 정보를 리스트로 변환
//            dto.setHospitals(log.getHospitals().stream()
//                    .map(hospital -> HospitalInfo.builder()
//                            .name(hospital.getName())
//                            .addr(hospital.getAddr())
//                            .tel1(hospital.getTel1())
//                            .tel2(hospital.getTel2())
//                            .hospitalType(hospital.getHospitalType())
//                            .distance(hospital.getDistance())
//                            .predDuration(hospital.getPredDuration())
//                            .realDuration(hospital.getRealDuration())
//                            .build())
//                    .collect(Collectors.toList()));
//
//            return dto;
//        }
        public static ResponseList of(Log log) {
            System.out.println("Converting Log ID: " + log.getId());
            System.out.println("Raw hospitals size: " + log.getHospitals().size());

            List<HospitalInfo> hospitalInfos = log.getHospitals().stream()
                    .peek(h -> System.out.println("Processing hospital: " + h.getName()))
                    .map(hospital -> HospitalInfo.builder()
                            .name(hospital.getName())
                            .addr(hospital.getAddr())
                            .tel1(hospital.getTel1())
                            .tel2(hospital.getTel2())
                            .hospitalType(hospital.getHospitalType())
                            .distance(hospital.getDistance())
                            .predDuration(hospital.getPredDuration())
                            .realDuration(hospital.getRealDuration())
                            .build())
                    .collect(Collectors.toList());

            System.out.println("Converted hospitals size: " + hospitalInfos.size());

            ResponseList dto = ResponseList.builder()
                    .id(log.getId())
                    .inputText(log.getInputText())
                    .datetime(log.getDatetime())
                    .summary(log.getSummary())
                    .inputLatitude(log.getInputLatitude())
                    .inputLongitude(log.getInputLongitude())
                    .emClass(log.getEmClass())
                    .hospitals(hospitalInfos)
                    .build();

            return dto;
        }
    }
}