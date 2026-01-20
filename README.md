


# LOLINFO -> SOOPLOL
https://www.sooplol.com/

<img width="918" height="748" alt="image" src="https://github.com/user-attachments/assets/8486e573-1a5f-48a9-98fd-309fdc9aa0d0" />

## Deployment Architecture
Client (Browser)
        ↓
Cloudflare Pages (React Frontend)
        ↓ REST API
Render (Spring Boot Backend)
        ↓
OCI (Oracle Database)

## Frontend

- React 기반 SPA
- Cloudflare Pages에 정적 사이트 배포
- GitHub 연동 자동 빌드 & 배포
- CDN 기반 빠른 응답 및 HTTPS 자동 적용

## Backend

- Spring Boot 기반 REST API 서버
- Render에 애플리케이션 배포
- GitHub 연동 자동 배포
- 환경변수 기반 설정 관리

## Database

- OCI에서 Oracle Database 운영
- 관계형 데이터 모델링
- View 기반 통계 데이터 처리

## Domain & DNS

- Gabia에서 도메인 구매 및 관리
- DNS 레코드 설정 (A / CNAME)
- 프론트엔드 / 백엔드 도메인 분리
- HTTPS 정상 적용 확인
