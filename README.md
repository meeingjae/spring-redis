# spring-redis-oracle
-- -- 
## Compared Performence Redis(In Memorry DB) with Oracle(RDB)
### (초간단) In Memory DB 와 RDB 성능 비교
-- --
### 사전 조건
* Redis와 Oracle은 **단일 Cluster**로 비교한다
* 단일 Web Application 내에서 모든 테스트를 수행한다
* 비교 대상 데이터는 **10만 건**을 기준으로 한다
* Oracle의 **Indexed** Table을 비교 대상으로 한다

### 다루지 않을 내용
* 복수개의 DB Cluster or Replication
* Module 단위의 테스트 환경 격리 수준
* 10만 건 이외의 데이터
* Service Layer (Presentation Layer에 포함)
* 조회 로직과 생성 로직의 분리 (간단한 테스트 목적)
-- --
## 기능
* 유저 생성
* 유저 조회 (단건/복수건)
  * Redis
  * Oracle
* 유저 삭제 (복수건)
  * Redis
  * Oracle
-- --
## skills
* `Java 8`
* `Spring Boot 2.7.0`
  * `slf4j`
    * Logging
* ~~Redis 7.0.0~~ - replace with Embedded Redis
  * local
    * port: 6380
* `Docker`
  * Oracle
    * [Oracle 11g](https://github.com/jaspeen/docker-oracle-xe-11g)
    * docker run -d -p 1521:1521 sath89/oracle-xe-11g
* Dependency
  * `Embedded Redis`
    * [ozimov:embedded-redis 0.7.3](https://github.com/ozimov/embedded-redis)
  * ~~spring-boot-starter-data-redis~~  
  * `Oracle jdbc8`
  * `h2 database` - for Test
-- --
## Result

### 전체 건 조회 (10만 건)
| 시행 횟수 | Oracle (sec) | Redis (sec)|
| :---: | :---: | :---: |
| 1 | 10.29 | 11.31
| 2 | 9.99 | 10.5
| 3 | 10.1 | 10.6
-> 근소하게 Redis의 Performance가 떨어지는 것을 확인할 수 있다

### 단 건 조회
| Id | Oracle (sec) | Redis (sec)|
| :---: | :---: | :---: |
| 1 | 0.0439 | 0.0003
| 50000 | 0.0509 | 0.0054
| 100000 | 0.0556 | 0.0004
-> 양 극으로 갈 수록 Redis의 Performance가 월등히 뛰어난 것을 확인할 수 있다