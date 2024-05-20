# cooper-spring-user


src\main\resources   아래 파일 추가 필요
* application.yml
  ```
  server:
    port: 0

  spring:
    application:
      name: cooper-media
  
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
