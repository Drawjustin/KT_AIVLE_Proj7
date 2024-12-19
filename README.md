 # KT_AIVLE_Proj7 - 응급상황 인식 및 응급실 연계 서비스 포탈

## 프로젝트 개요
**KT AIVLE 7차 미니프로젝트**

### 팀원
- 김효연
- 박정은
- 박지성
- 유지현
- 유현종
- 조강윤

### 주제
응급상황 인식 및 응급실 연계 서비스 포탈 구축

### 목표
- **응급 요청에 적합한 응급실 연계 서비스 포털** 구축
- **AI 모델**: LLM, 언어모델 파인튜닝
- **API**: OpenAI, Naver Maps
- **H/W**: 클라우드 웹 서버
- **DB**: JPA, SQLite3 (향후 다양한 DBM)

## 기술 스택
- **프레임워크**: Spring Boot, FastAPI
- **AI 모델**: BERT, ChatGPT (Fine-tuning)
- **음성 인식**: Whisper (MS Azure)
- **배포 및 컨테이너화**: Docker
- **데이터베이스**: JPA, SQLite3
- **API**: OpenAI API, Naver Maps API

## 시스템 아키텍처

### 1. 사용자 입력
사용자는 웹 브라우저를 통해 음성 메시지와 위치 정보를 입력합니다. 예를 들어, "다쳤어요!"와 같은 응급 상황을 텍스트로 입력하고, 위치 정보는 응급실 추천을 위한 필수 데이터입니다.

### 2. 프론트엔드
- **구성**: View Template Engine 기반으로 구현
- **프레임워크**: Spring Boot 사용
- **기능**:
  - 사용자로부터 음성 및 위치 데이터를 수집
  - 백엔드와 통신하여 응급실 추천 결과를 표시
- **배포**: Docker를 사용하여 컨테이너화

### 3. 백엔드
- **구성**: Spring Boot와 FastAPI로 나누어진 마이크로서비스 구조
- **Spring Boot (Java)**:
  - 프론트엔드와 통신하는 역할
  - 사용자의 요청을 Python 기반 FastAPI 서비스로 전달
  - 추천된 응급실 데이터를 프론트엔드로 반환
  - 그 외 각종 추가 기능 처리
- **FastAPI (Python)**:
  - REST API를 통해 음성 인식, 텍스트 요약 및 분류 처리
  - OpenAI 모델을 활용한 응급 상황에 대한 텍스트 요약 및 분류
  - 위치 기반 병원 추천 기능 제공

## 프로젝트 실행
### 1. 개발 환경
- **Java**: JDK 11 이상
- **Python**: Python 3.8 이상
- **Docker**: Docker 20 이상

### 2. 실행 방법
#### a. 프론트엔드 (Spring Boot)
1. `src/main/resources/application.properties` 파일에서 서버 설정을 확인합니다.
2. 프로젝트 디렉토리에서 Spring Boot 애플리케이션을 실행합니다.
   ```bash
   mvn spring-boot:run
