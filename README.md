# cooper-spring-user

- RestAPI 설명 및 URL
  ```
      기능        URL        요청      설명
  
     로그인       /signin    post     데이터베이스에 저장된 유저 리스트를 기반으로
                                      이메일과 비밀번호를 검증해 로그인합니다.
  
    회원가입      /signup    post     이메일, 비밀번호, 이름을 입력 받고 회원의
                                      권한정보를 추가해 데이터베이스 유저 리스트에 저장합니다.
  
  전체 유저 조회  /user      get      데이터베이스에 저장되어 있는 모든 유저의
                                      id, 이메일, 이름, 권한 정보를 불러옵니다.
  
  개별 유저 조회  /user/{id} get      데이터베이스에 저장된 유저 중 id에 해당 되는
                                      유저의 id, email, 이름, 권한 정보를 불러옵니다.
  
    유저 삭제     /user      delete   데이터베이스에 저장된 유저의 정보를 삭제합니다.
  
  전체 유저 조회  /users     get      데이터베이스에 저장된 전체 유저의 정보를 다른 API에게 전달합니다.
  
  개별 유저 조회 /users/{id} get      데이터베아스에 저장된 개별 유저의 정보를 다른 API에게 전달합니다.
  ```

- RestAPI별 JSON 형식
`
    /signin
  ```
  {
	"email" : "test@test.com",
	"password" : "a123456!"
  }
  ```
  
    /signup
  ```
  {
	"email" :  "test@test.com",
	"password" : "a123456!",
	"checkedPassword" : "a123456!",
	"name" : "test"
  }
  ```
  
    /user   get
  ```
  { *요청 Body 없음
  }
  ```
  
    /user/{id}  get
  ```
  { *요청 Body 없음
  }
  ```

    /user/{id}  delete
  ```
  { *요청 Body 없음
  }
  ```
  
src\main\resources 아래 파일 추가 필요

- application.yml

  ```
  server:
    port: 0

  spring:
    application:
      name: cooper-user

    datasource:
      url: {your db adress}
      username: {your username}
      password: {your password}
      driver-class-name: com.mysql.cj.jdbc.Driver

    devtools:
      livereload:
        enabled: true

    thymeleaf:
      prefix: classpath:/templates/
      suffix: .html
      cache: false
      check-template-location: true

  eureka:
    client:
      register-with-eureka: true
      fetch-registry: true
      service-url:
        defaultZone: http://localhost:8761/eureka
    instance:
      instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}
  ```
