````markdown
# SOOPLOL

> SOOP 플랫폼의 League of Legends 스트리머, 대회, CK 경기 데이터를 한곳에서 확인할 수 있는 통계·정보 서비스

🔗 **Service**: https://www.sooplol.com

SOOPLOL은 SOOP에서 활동하는 LoL 스트리머들의 **대회 참가 기록, CK 경기 전적, 승률, 상대전적, 팀메이트 기록, 멸망전 정보** 등을 조회할 수 있도록 만든 개인 프로젝트입니다.

단순 데이터 조회를 넘어 실제 사용자들이 지속적으로 이용할 수 있는 서비스를 목표로 개발하고 있으며, 직접 데이터를 관리하고 트래픽과 성능을 모니터링하며 기능을 지속적으로 개선하고 있습니다.

---

## 주요 기능

### 스트리머

- 스트리머 목록 및 상세 정보 조회
- 대회 참가 및 성적 기록
- 전체 CK 경기 전적 및 승률
- 최근 CK 경기 결과
- 함께 플레이한 스트리머 기록
- 상대전적 조회
- CK 연승 / 연패 기록
- 즐겨찾기 스트리머 등록

### CK 경기

- CK 경기 결과 등록 및 조회
- RED / BLUE 진영별 참가자 관리
- 포지션별 참가자 기록
- 스트리머별 CK 승/패 자동 집계
- 현재 연승 / 연패 기록
- 월간 CK 랭킹
- CK 다승 및 승률 통계

### 대회

- SOOP LoL 대회 목록 조회
- 대회별 참가 팀 및 선수 구성
- 대회 결과 및 순위
- 스트리머별 대회 참가 이력
- LoL 멸망전 기록
- 대회별 티어표
- 대회 진행 기간 중 스크림 기록 및 팀별 승률

### 스크림

- 대회별 연습경기 기록
- RED / BLUE 팀별 스코어
- 팀별 스크림 승률
- 팀 간 상대전적
- 날짜 및 시간대별 경기 정렬

### 사용자

- 회원가입 / 로그인
- JWT 기반 인증
- Access Token / Refresh Token 재발급
- 스트리머 즐겨찾기
- 문의 / 신고 / 건의 / 자유 게시판
- 비회원 피드백 제출
- 관리자 피드백 처리

### 사이트 운영

- 일일 방문자 통계
- UUID 기반 비회원 방문자 구분
- 관리자 페이지
- 개발 이력 / 패치노트
- 개인정보처리방침 / 이용약관
- SEO 메타 태그
- Google Search Console / Naver Search Advisor 등록

---

## Tech Stack

### Backend

- Java 21
- Spring Boot
- MyBatis
- REST API
- JWT
- Oracle Database

### Frontend
- React
- Axios
- Jotai
- Bootstrap
- Chart.js

### Database
- Oracle SQL
- View
- Index
- Sequence


### Infrastructure
- **Frontend**: Cloudflare Pages
- **Backend**: Render
- **Database**: Oracle Cloud Infrastructure Autonomous Database
- **DNS / CDN**: Cloudflare

### Development Tools
- Spring Tool Suite / Eclipse
- Visual Studio Code
- DBeaver
- Git / GitHub

---

## Architecture

```text
┌─────────────────────┐
│       Client        │
│   React + Vite      │
└──────────┬──────────┘
           │ HTTPS
           ▼
┌─────────────────────┐
│  Cloudflare Pages   │
│ Frontend / CDN / DNS│
└──────────┬──────────┘
           │ REST API
           ▼
┌─────────────────────┐
│       Render        │
│ Spring Boot Server  │
└──────────┬──────────┘
           │ JDBC
           ▼
┌─────────────────────┐
│    Oracle Cloud     │
│ Autonomous Database │
└─────────────────────┘
````

---

## 주요 데이터 구조

### STREAMER

스트리머의 기본 정보를 관리합니다.

### TOURNAMENT

대회의 이름, 연도, 티어 유형 등의 정보를 관리합니다.

### TEAM

대회 참가 팀과 포지션별 스트리머 정보를 관리합니다.

### CK

CK 경기의 날짜, 승리 진영 및 메모 등을 관리합니다.

### CK_PARTICIPANT

각 CK에 참가한 스트리머의 진영과 포지션을 관리합니다.


## 성능 개선

SOOPLOL은 실제 서비스 운영 중 대회 개최 시 평소보다 트래픽이 크게 증가하는 특성이 있습니다.
이에 따라 단순 기능 구현뿐 아니라 DB Query와 API 응답 성능 개선도 지속적으로 진행하고 있습니다.

### N+1 Query 개선

초기 스트리머 목록 조회 과정에서 스트리머별 통계를 각각 조회하면서 반복적인 DB Query가 발생했습니다.

이를 다음 방식으로 개선했습니다.

* JOIN
* Database View
* 집계 Query

대표 View:

```text
team_list
tournament_list
streamer_stat_list
```

반복 조회를 하나의 Query 중심 구조로 변경하여 DB 접근 횟수를 줄였습니다.

---

### Pagination 개선

초기 Oracle `ROWNUM` 기반 페이징을 사용했으나 정렬 및 페이지 처리 구조를 개선하기 위해 `ROW_NUMBER()` 기반으로 변경했습니다.

```sql
ROW_NUMBER() OVER (
    ORDER BY ...
)
```

조회 순서와 페이지 범위를 명확하게 분리하여 페이징의 안정성을 개선했습니다.

---

### Index 적용

대회별 팀 조회 등 자주 사용되는 검색 조건에 Index를 추가했습니다.

예시:

```sql
TEAM(TOURNAMENT_ID, TEAM_RANK)
```

데이터 증가에 따른 불필요한 Full Scan을 줄이고 조회 성능을 개선했습니다.

---

## Backend ↔ Database Network Latency 개선

초기에는 Backend 서버와 Oracle Database가 서로 다른 지역에 위치하면서 DB 접근 과정에서 네트워크 지연이 발생했습니다.

API 응답에서 약 1초 이상의 TTFB가 발생하는 경우가 있었으며, 배포 환경 및 Backend 서버 위치를 재구성하여 응답 속도를 개선했습니다.

현재 구조:

```text
Cloudflare Pages
        ↓
Render
        ↓
OCI Autonomous Database
```

Chrome DevTools의 Network 탭을 이용하여 API별 응답시간과 TTFB를 비교하며 개선 여부를 확인했습니다.

---

## 트래픽 대응

SOOPLOL은 평상시와 LoL 대회 진행 기간의 트래픽 차이가 큰 서비스입니다.

특히 대회 시작 시 특정 시간대에 요청이 집중되기 때문에 다음 항목을 중심으로 서비스 안정성을 관리하고 있습니다.

* API Query 최적화
* 불필요한 API 호출 제거
* DB Index 적용
* 반복 조회 데이터 Cache 검토
* Cloudflare CDN 활용
* Backend CPU / Memory 모니터링
* HTTP Status 및 Error Rate 확인

실제 대회 기간 트래픽에서도 대부분의 요청에서 정상적인 `200 OK` 응답을 유지하도록 운영하고 있습니다.

---

## Cache 전략

SOOPLOL 데이터 중 일부는 변경 빈도가 매우 낮습니다.

예를 들어 다음 데이터는 매 요청마다 Database에서 다시 조회하거나 계산할 필요가 적습니다.

* 종료된 대회 목록
* 과거 대회 결과
* 대회 참가 팀
* 일부 스트리머 통계
* 랭킹 데이터

따라서 Spring Boot 내부 Cache를 활용한 캐싱을 검토하고 있습니다.

```text
대회 목록
대회 상세
스트리머 상세 통계
랭킹
```

데이터 수정 시 Cache Evict를 수행하고 일정 TTL 이후 자동으로 갱신하는 구조를 목표로 하고 있습니다.

---

## CK 연승 / 연패 시스템

CK 경기 결과가 등록되면 참가 스트리머들의 경기 기록을 기준으로 현재 연승 / 연패 상태를 다시 계산합니다.

```text
CK 등록
   ↓
참가자 등록
   ↓
참가 스트리머 ID 수집
   ↓
스트리머별 CK 기록 조회
   ↓
현재 연승 / 연패 계산
   ↓
통계 갱신
```

한 경기에서 같은 스트리머에게 중복 갱신 요청이 발생하지 않도록 `Set<Integer>`를 이용해 ID 중복을 제거합니다.

```java
Set<Integer> streamerNos = new HashSet<>();
```

---

## 방문자 통계

로그인하지 않은 사용자도 일별 방문자로 집계할 수 있도록 브라우저별 UUID를 생성하여 사용합니다.

```text
Browser
  ↓
localStorage UUID 생성
  ↓
일일 방문 여부 확인
  ↓
Visitor API
  ↓
Oracle Visitor Table
```

동일 브라우저에서 하루 동안 방문이 중복 집계되지 않도록 Frontend localStorage와 Database를 함께 활용합니다.

---

## SEO

React SPA 서비스의 검색 노출을 개선하기 위해 다음 작업을 적용했습니다.

* 페이지별 Meta Tag
* `robots.txt`
* `sitemap.xml`
* Google Search Console 등록
* Naver Search Advisor 등록
* About 페이지
* 개인정보처리방침
* 이용약관
* 문의 페이지

---

## Sitemap 개선

초기에는 Spring Backend에서 Database를 조회하여 Sitemap XML을 동적으로 생성했습니다.

하지만 Sitemap 요청 시 다량의 DB 조회가 발생하면서 응답 속도가 느려지는 문제가 발생했습니다.

이를 개선하여 Backend는 Sitemap 생성에 필요한 ID 데이터만 제공합니다.

```http
GET /api/sitemap/data
```

Frontend Build 과정에서 해당 데이터를 이용하여 정적 `sitemap.xml`을 생성합니다.

```text
Spring Boot
     ↓
Streamer / Tournament ID
     ↓
generate-sitemap.js
     ↓
sitemap.xml
     ↓
Cloudflare Pages
```

검색엔진이 Backend와 Database를 거치지 않고 정적 Sitemap 파일을 바로 조회할 수 있도록 개선했습니다.

---

## JWT 인증

Access Token 만료 시 Refresh Token을 이용해 새로운 Access Token을 발급합니다.

```text
API Request
    ↓
401 Unauthorized
    ↓
Refresh Token 요청
    ↓
Access Token 갱신
    ↓
기존 Request 재요청
```

Axios Interceptor를 이용해 인증 갱신을 처리하고 있습니다.

무한 재요청을 방지하기 위해 `_retry` 플래그를 적용했으며 Refresh API에는 Authorization Header가 추가되지 않도록 분리했습니다.

---

## 주요 문제 해결 경험

### Database

* Oracle Query 최적화
* `ROWNUM` 기반 Pagination 개선
* `ROW_NUMBER()` 적용
* JOIN 및 View를 통한 반복 조회 제거
* 복합 Index 적용
* Unique Constraint를 활용한 데이터 무결성 관리
* 날짜 및 Timestamp 처리
* Aggregate Query 최적화

### Backend

* Spring Boot REST API 설계
* MyBatis Mapper 구성
* JWT 인증 처리
* Refresh Token 재발급
* Transaction 기반 데이터 저장
* 통계 데이터 자동 갱신

### Frontend

* React SPA 개발
* React Router 기반 페이지 구성
* Axios Interceptor
* Jotai 상태 관리
* Responsive UI
* API Loading 처리
* 모바일 레이아웃 대응

### Infrastructure

* React → Cloudflare Pages 배포
* Spring Boot → Render 배포
* Oracle Autonomous Database 운영
* 서버와 DB 간 Network Latency 분석
* API TTFB 측정 및 개선
* Search Console / Sitemap 운영

---

## 운영 중심 개발

SOOPLOL은 개발 후 종료된 프로젝트가 아니라 실제 사용자에게 공개하여 지속적으로 운영하고 있는 서비스입니다.

운영 중 사용자 이용 패턴과 트래픽을 확인하면서 문제를 발견하고 개선하는 과정을 반복하고 있습니다.

```text
기능 구현
   ↓
실서비스 배포
   ↓
사용자 이용
   ↓
트래픽 / API 분석
   ↓
문제 발견
   ↓
Query / UI / Server 개선
   ↓
재배포
```

실제 운영 과정에서 개발 환경에서는 쉽게 발견하기 어려운 문제들을 경험하고 개선했습니다.

* Query 성능 문제
* Server ↔ Database Network Latency
* 대회 기간 트래픽 증가
* 불필요한 API 호출
* Frontend Loading 지연
* Sitemap 응답 지연
* 모바일 UI 문제

---

## 프로젝트를 통해 경험한 내용

SOOPLOL을 직접 개발하고 운영하며 다음 영역을 경험했습니다.

* Spring Boot 기반 REST API 설계
* React SPA 개발
* Oracle Database 모델링
* SQL Query 및 Index 최적화
* MyBatis 기반 데이터 접근
* JWT 인증 / Refresh Token 구현
* 사용자 및 관리자 기능 구현
* 실제 서비스 배포 및 운영
* Cloudflare / Render / OCI 환경 구성
* 실제 트래픽 기반 성능 분석
* Database Network Latency 분석
* SEO 및 Sitemap 구성
* 운영 데이터를 기반으로 한 지속적인 기능 개선

---

## 향후 개발

SOOPLOL을 단순 대회 기록 사이트에서 확장하여

> **SOOP League of Legends 스트리머들의 경기 데이터를 종합적으로 확인할 수 있는 데이터 서비스**

로 발전시키는 것을 목표로 하고 있습니다.


## Developer

Personal Project

**SOOPLOL**

https://www.sooplol.com


