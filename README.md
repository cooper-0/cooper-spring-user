# cooper-spring-user

\*유저삭제, 수정 기능 미작동

RestTemplate 사용시 UserService 맨 아래
String url = "http:///cooper-user/users"; 부분을 문서의 URL로 변경해주세요!

JSON 파일의 형식들은 JSON형태.txt 파일을 참조해주세요

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
