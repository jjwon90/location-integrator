## Table Of Contents
- [개발 환경](#개발환경)
- [사용 오픈소스](#사용-오픈소스)
- [테스트](#테스트)
- [기능 별 구현 명세](#기능별-구현-명세)

### 개발환경
* Framework : SpringBoot 2.7.8
* Language : Java 17

### 사용 오픈소스
* Resilience4j
* Openfeign
* Apache commons
  * apache-commons-collection4
  * apache-commons-lang3
* Spring Boot
  * Spring Web
  * Spring validation
* Jasypt
* Lombok

### 테스트

### 기능별 구현 명세
#### 장소 검색
* package 구조
  ![Search Package 구조](/img/search-package.png)
* Naver api 구현
  1. 독특하게 html tag가 달려서 옴
  2. 네이버 DTO -> domain converting 할때, html tag 제거하는 것으로...
      - library 사용을 할지, 정규식으로 html 태그들을 없앨 것인지 -> 정규식으로 없애는 것을 선택
      - htmlEscaping(예를 들면, \&amp;)
* 카카오 api 구현
  * 광역 자치단체 명이 fullName으로 오지 않고 생략되서 응답함(ex. 서울, 경기)

* 카카오 api 검색 결과, 네이버 api 검색 결과 조합
  * 카카오 검색 결과를 기준으로 동일하게 나타나는 장소가 상위에 오도록
    * 동일 업체 구분 확정 후 비교
  * 동일 업체를 제외한 경우 카카오에만 존재하는 것을 상위로 오도록 ordering

* 동일 업체 여부를 어떻게 할 것인가.
  - 주소(도로명, 건물 번호 까지 같은)
    * 도로명, 건물 번호 까지 우선 동일 여부로 확인
    * 주소 체계
      1. 시, 도 -> 시, 군, 구 -> 도로명 -> 건물번호
         * 경기도 화성시 동탄순환대로 121  
      2. 시, 도 -> 시, 군, 구 -> 읍, 면, 구 -> 도로명 -> 건물번호
         * 경기도 안양시 동안구 평촌대로 111

#### 인기 검색어     
* package 구조
  ![Popular Package 구조](/img/popular-package.png)
* 인기검색어