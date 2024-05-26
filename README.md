# cpu-mornitoring API

### 1. 프로젝트 설정
  - java 11 사용
  - gradle 사용
  - spring boot 사용

### 2 데이터베이스 설정

2.1 개발 및 테스트(dev) 설정과 운용(prod) 설정 분리

- H2를 개발 및 테스트에 사용

application.properties 파일의
spring.profiles.active=dev H2 설정

- MariaDB를 운용 데이터베이스로 사용

application.properties 파일의
spring.profiles.active=prod MariaDB 설정

2.2 JPA 사용

database 연결 ORM을 JPA를 사용
build.gradle 파일의 dependencies에 implementation 'org.springframework.boot:spring-boot-starter-data-jpa' 추가하여 사용

### 3 기능 요구 사항
3.1 데이터 수집 및 저장
- CPU 사용률 수집: 서버의 CPU 사용률을 분 단위로 수집합니다.
  
스케줄러를 사용해 분단위 사용률 수집

- 데이터 저장: 수집된 데이터를 데이터베이스에 저장합니다.
 
service에서 가공한 객체를 repository를 통해 데이터베이스에 저장


3.2 데이터 조회 API
- 분 단위 조회: 지정한 시간 구간의 분 단위 CPU 사용률을 조회합니다.

파라미터로 전달 받은 시간 구간의 시작시간을 최근 1주로 제약을 걸어주는 것으로 1주 데이터만 제공하는 분 단위 조회

- 시 단위 조회: 지정한 날짜의 시  단위 CPU 최소/최대/평균 사용률을 조회합니다.

파라미터로 전달 받은 날짜에 최근 3달로 제약을 걸어주는 것으로 3달 데이터만 제공하는 시 단위 조회

- 일 단위 조회: 지정한 날짜 구간의 일  단위 CPU 최소/최대/평균 사용률을 조회합니다.

파라미터로 전달 받은 지정한 날짜 구간의 시작날짜를 최근 1년으로 제약을 걸어주는 것으로 1년 데이터만 제공하는 일 단위 조회

- Swagger를 사용하여 API 문서화를 설정하세요.

build.gradle 파일에 의존성
  implementation 'io.springfox:springfox-boot-starter:3.0.0'
	implementation 'io.springfox:springfox-swagger-ui:3.0.0'
 을 추가하고 swaggerConfig 파일을 만들어서 설정

4. 예외 처리
- 데이터 수집 실패 시 예외를 처리하고 로그를 남깁니다.

분 단위 데이터 수집 실패 시 logger를 이용해서 로그를 남기고 가상의 값을 넣어줌

- API 요청 시 잘못된 파라미터에 대한 예외를 처리합니다.

@DateTimeFormat 어노테이션으로 파라미터의 데이터 포맷을 지정해줌에 따라 지정된 포맷 이외의 데이터가 파라미터로 들어왔을 경우 httpstatus 400 bad request를 리턴함

5. 테스트
유닛 테스트: 서비스 계층과 데이터베이스 계층의 유닛 테스트를 작성하세요.

  - 서비스 계층
  
   @mock을 사용해 가상의 객체(repository)를 만들고 어떤 값이 들어오든지 가상의 객체를 반환함으로서 테스트함.
  
  - 데이터베이스 계층
  
  객체에 값을 지정해준 후 repository를 통해서 저장해주고 저장된 데이터와 지정된 데이터가 같은지 테스트함.
  
- 통합 테스트: 컨트롤러 계층의 통합 테스트를 작성하세요.

MockMvc를 사용하여 컨트롤러 계층의 HTTP 요청을 모의로 실행하고 응답을 테스트함.