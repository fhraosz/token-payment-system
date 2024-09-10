이 컨트롤러들을 바탕으로 `README.md` 파일에 API 엔드포인트를 문서화할 수 있습니다. 각 엔드포인트에 대한 설명과 예제 요청을 추가하면 좋을 것 같습니다. 아래는 API 문서화 예시입니다:

```markdown
# Token Payment System

## 프로젝트 소개
이 프로젝트는 토큰 기반 결제 시스템을 구현하는 Spring Boot 애플리케이션입니다. Java (JDK 17)을 사용하며, H2 데이터베이스와 Gradle을 통해 관리됩니다. 타임리프를 사용하여 템플릿 렌더링을 처리합니다.

## 주요 기능
- 카드 등록 및 결제 처리
- 카드 목록 조회
- 1회용 토큰 발급 및 검증

## 엔드포인트

### 카드 등록
- **URL**: `/cards/register`
- **메서드**: `GET`
- **설명**: 카드 등록 페이지를 표시합니다.

- **URL**: `/cards/register`
- **메서드**: `POST`
- **설명**: 카드를 등록합니다.
- **요청 본문**:
  ```json
  {
    "cardNumber": "1234567812345678",
    "cardHolder": "John Doe",
    "expiryDate": "12/25",
    "cvv": "123"
  }
  ```
- **응답 예시**:
  - 성공:
    ```json
    {
      "message": "카드가 성공적으로 등록되었습니다. REF_ID: 1234567890123456"
    }
    ```
  - 실패:
    ```json
    {
      "error": "카드 등록 과정에서 문제가 발생했습니다."
    }
    ```

### 카드 목록 조회
- **URL**: `/cards/payment`
- **메서드**: `GET`
- **설명**: 카드 목록을 조회하여 결제 페이지를 표시합니다.

### 카드 결제
- **URL**: `/cards/payment/process`
- **메서드**: `POST`
- **설명**: 결제를 처리합니다.
- **요청 본문**:
  ```json
  {
    "token": "abc123",
    "amount": 1000
  }
  ```
- **응답 예시**:
  - 성공:
    ```json
    {
      "paymentId": "123456",
      "status": "completed",
      "amount": 1000
    }
    ```
  - 실패:
    ```json
    {
      "error": "결제 처리에 실패하였습니다. 다시 시도해 주세요."
    }
    ```

### 카드 등록 (API)
- **URL**: `/tokenization/register`
- **메서드**: `POST`
- **설명**: 카드를 등록합니다.
- **요청 본문**: (위와 동일)
- **응답 예시**: (위와 동일)

### 카드 검색
- **URL**: `/tokenization/search`
- **메서드**: `GET`
- **설명**: 고객이 등록한 카드를 검색합니다.
- **요청 파라미터**:
  - `userCi` (필수): 사용자 CI
- **응답 예시**:
  ```json
  [
    {
      "cardNumber": "1234567812345678",
      "cardHolder": "John Doe",
      "expiryDate": "12/25",
      "cvv": "123"
    }
  ]
  ```

### 1회용 토큰 발급
- **URL**: `/tokenization/token`
- **메서드**: `GET`
- **설명**: REF_ID를 기반으로 1회용 토큰을 발급합니다.
- **요청 파라미터**:
  - `refId` (필수): 카드 참조값
- **응답 예시**:
  ```json
  {
    "token": "abc123"
  }
  ```

### 토큰 유효성 검사
- **URL**: `/tokenization/validate`
- **메서드**: `POST`
- **설명**: 토큰의 유효성을 검사합니다.
- **요청 본문**:
  ```json
  {
    "token": "abc123"
  }
  ```
- **응답 예시**:
  - 성공:
    ```json
    {
      "valid": true
    }
    ```
  - 실패:
    ```json
    {
      "valid": false
    }
    ```

### 토큰 상태 변경
- **URL**: `/tokenization/change/state`
- **메서드**: `POST`
- **설명**: 토큰의 상태를 변경합니다.
- **요청 본문**: (위와 동일)
- **응답 예시**: (위와 동일)

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
   애플리케이션은 기본적으로 `http://localhost:8080`에서 실행됩니다.

## 프로젝트 구조
- `src/main/java/com/bulewalnut/tokenpaymentsystem`: Java 소스 코드
- `src/main/resources`: 애플리케이션 설정 파일 및 템플릿
- `src/test/java`: 테스트 코드

## 데이터베이스
이 프로젝트는 H2 데이터베이스를 사용하며, 데이터베이스 설정은 `src/main/resources/application.properties`에서 확인할 수 있습니다.
