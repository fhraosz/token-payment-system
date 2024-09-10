# Token Payment System

## 프로젝트 소개
이 프로젝트는 토큰 기반 결제 시스템을 구현하는 Spring Boot 애플리케이션입니다. Java (JDK 17)을 사용하며, H2 데이터베이스와 Gradle을 통해 관리됩니다. 타임리프를 사용하여 템플릿 렌더링을 처리합니다.

## 주요 기능
- 토큰 기반 결제 처리
- 결제 요청 및 결제 완료 기록
- 결제 완료 후 정보 표시 페이지

## 기술 스택
- **언어**: Java (JDK 17)
- **프레임워크**: Spring Boot
- **데이터베이스**: H2
- **빌드 도구**: Gradle
- **템플릿 엔진**: Thymeleaf

## 설치 및 실행
1. **프로젝트 클론**
   ```bash
   git clone https://github.com/fhraosz/token-payment-system.git
   cd token-payment-system
   ```
2. **Gradle 의존성 설치**
   ```bash
   ./gradlew build
   ```
3. **애플리케이션 실행**
   ```bash
   ./gradlew bootRun
   ```
4. **웹 브라우저에서 애플리케이션 접근**
   애플리케이션은 기본적으로 `http://localhost:8081`에서 실행됩니다.

## 프로젝트 구조
- `src/main/java/com/bulewalnut/tokenpaymentsystem`: Java 소스 코드
- `src/main/resources`: 애플리케이션 설정 파일 및 템플릿
- `src/test/java`: 테스트 코드

## 데이터베이스
이 프로젝트는 H2 데이터베이스를 사용하며, 데이터베이스 설정은 `src/main/resources/application.properties`에서 확인할 수 있습니다.
