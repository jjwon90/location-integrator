Table Of Contents
==
- [개발 환경](#개발환경-및-주요-library)
- [테스트](#테스트)
- [기능 별 구현 명세 및 고려사항](#기능-별-구현-명세-및-고려사항)
----
### 개발환경 및 주요 Library

----
| Library 명        | 용도                                                                                     |
|------------------|----------------------------------------------------------------------------------------|
| Java17           | - Java SDK                                                                             |
| SpringBoot 2.7.8 | - Framework                                                                            |
| Resilience4j     | - CircuitBreaker, RateLimiter 도입을 위해 사용                                                |
| OpenFeign        | - 편리하게 외부 서비스를 호출 할 수 있도록 도와주기 때문에 사용<br/>- Timeout 세팅 용이성                             |
| Apache Commons   | - apache-commons-collection4<br/> - apache-commons-lang3 <br/>- 편리하게 data 체크를 하기 위해 도입 |
| Jasypt           | - Naver, Kakao API Key Secret 값을 암호화 하기 위해 도입                                          |
| Lombok           | - 코드의 간결성을 위하여 도입                                                                      |  

----
### 테스트

----
* [Http Request File](/test-Request.http)
* curl 모음 및 응답

<table>
  <tr>
    <th>요청 CURL</th>
    <th>Response</th>
  </tr>
  <tr>
    <td>curl --location --request GET 'http://localhost:8080/v1/locations?query=%EB%8F%99%ED%83%84%EC%9D%80%ED%96%89%0A'</td>
    <td>
      <pre>
{
  "resultList": [
      {
        "placeName": "하나은행 동탄지점",
        "address": "경기 화성시 동탄반석로 204"
      },
      {
        "placeName": "KB국민은행 동탄다은지점",
        "address": "경기 화성시 동탄반석로 124"
      },
      {
        "placeName": "우리은행 동탄지점",
        "address": "경기 화성시 동탄원천로 163"
      },
      {
        "placeName": "신한은행 동탄지점",
        "address": "경기 화성시 동탄반석로 196"
      },
      {
        "placeName": "NH농협은행 동탄중앙지점",
        "address": "경기 화성시 동탄반석로 144"
      },
      {
        "placeName": "IBK기업은행 화성병점",
        "address": "경기도 화성시 효행로 1041"
      },
      {
        "placeName": "IBK기업은행 동탄테크노타워",
        "address": "경기도 화성시 동탄첨단산업1로 27 2층"
      },
      {
        "placeName": "경기화성새마을금고 동탄다은지점",
        "address": "경기도 화성시 동탄반석로 124"
      },
      {
        "placeName": "하나은행 화성병점지점",
        "address": "경기도 화성시 효행로 1015 3층"
      }
  ]
}
      </pre>
    </td>
  </tr>
  <tr>
    <td>curl --location --request GET 'http://localhost:8080/v1/locations?query='</td>
    <td>
      <pre>
{
  "status": "BAD_REQUEST",
  "message": "getLocations.query: 비어 있을 수 없습니다"
}
      </pre>
    </td>
  </tr>
  <tr>
    <td>
curl --location --request GET 'http://localhost:8080/v1/locations?query=%ED%8F%89%EC%B4%8C%EC%9D%80%ED%96%89%0A'
    </td>
    <td>
<pre>
{
    "resultList": [
        {
            "placeName": "하나은행 평촌꿈마을지점",
            "address": "경기 안양시 동안구 신기대로 147"
        },
        {
            "placeName": "우리은행 인덕원금융센터",
            "address": "경기 안양시 동안구 흥안대로 416"
        },
        {
            "placeName": "IBK기업은행 평촌남지점",
            "address": "경기 안양시 동안구 흥안대로 415"
        },
        {
            "placeName": "NH농협은행 평촌지점",
            "address": "경기 안양시 동안구 귀인로 206"
        },
        {
            "placeName": "우리은행 한림대학교성심병원",
            "address": "경기 안양시 동안구 관평로170번길 22"
        },
        {
            "placeName": "하나은행 평촌역금융센터",
            "address": "경기도 안양시 동안구 시민대로 286"
        },
        {
            "placeName": "KB국민은행 평촌범계종합금융센터",
            "address": "경기도 안양시 동안구 시민대로 196"
        },
        {
            "placeName": "하나은행 평촌범계역지점",
            "address": "경기도 안양시 동안구 시민대로 210"
        },
        {
            "placeName": "신한은행 평촌역지점",
            "address": "경기도 안양시 동안구 시민대로 278"
        },
        {
            "placeName": "우리은행 평촌금융센터",
            "address": "경기도 안양시 동안구 시민대로 167 안양 벤처텔"
        }
    ]
}
</pre>
    </td>
  </tr>
  <tr>
    <td>curl --location --request GET 'http://localhost:8080/v1/locations?query=%EB%B2%94%EA%B3%84%EC%97%AD%EC%9D%80'</td>
    <td>
      <img src="/img/no_response_naver_example.png" />
      <pre>
{
    "resultList": [
        {
            "placeName": "범계역 4호선",
            "address": "경기 안양시 동안구 동안로 지하 130"
        },
        {
            "placeName": "범계역광장",
            "address": "경기 안양시 동안구 동안로 지하 130"
        },
        {
            "placeName": "범계역주차장",
            "address": "경기 안양시 동안구 평촌대로217번길 45"
        },
        {
            "placeName": "힐스테이트범계역모비우스오피스텔",
            "address": "경기 안양시 동안구 시민대로 190"
        },
        {
            "placeName": "범계역 노상공영주차장",
            "address": "경기 안양시 동안구 호계동 1132"
        }
    ]
}
      </pre>
    </td>
  </tr>
  <tr>
  <td>
    curl --location --request GET 'http://localhost:8080/v1/popular-queries'
  </td>
  <td>
  <pre>
{
    "popularList": [
        {
            "query": "평촌은행",
            "count": 5
        },
        {
            "query": "은행",
            "count": 4
        },
        {
            "query": "동탄은행",
            "count": 1
        }
    ]
}
  </pre>
  </td>
  </tr>
</table>

----
### 기능 별 구현 명세 및 고려사항

----
* location-integrator는 Domain Handling을 위하여 Hexagonal Architecture를 지향하며 다음의 원칙으로 Layer를 구성하였다.

<table>
  <tr>
    <th>Layer 구분</th>
    <th>설명</th>
  </tr>
  <tr>
    <td>
Infrastructure Layer(Adapter)
    </td>
    <td>
        외부에서 내부 시스템의 inbound 요청<br />
        내부 시스템에서 외부 시스템으로의 outbound 요청
          <ul>
            <li>외부 시스템 RestApi Call</li>
            <li>다른 도메인 호출</li>
            <li>data hub에 대한 data 요청</li>
          </ul>
    </td>
  </tr>
  <tr>
    <td>
      Application Layer(Port)
    </td>
    <td>
      Domain Model Layer에 접근 및 제어를 위한 Business Logic 구간 
    </td>
  </tr>
  <tr>
    <td>Domain Model Layer</td>
    <td>
      Aggregate 단위로 구분된 Domain Model
    </td>
  </tr>
</table>

#### 장소 검색

----
* package 구조
  ![Search Package 구조](/img/search-package.png)
* Naver api 구현
  * 독특하게 html tag가 달려서 옴
     * library 사용을 할지, 정규식으로 html 태그들을 없앨 것인지 -> 정규식으로 없애는 것을 선택
     * htmlEscaping(예를 들면, \&amp;)
* 카카오 api 구현
  * 광역 자치단체 명이 fullName으로 오지 않고 생략되서 응답함(ex. 서울, 경기)

* 동일 업체 여부를 판단 기준
  - 주소(도로명, 건물 번호 까지 같은)
    * 도로명, 건물 번호 까지 우선 동일 여부로 확인
    * 주소 체계
      1. 시, 도 -> 시, 군, 구 -> 도로명 -> 건물번호
         * 경기도 화성시 동탄순환대로 121  
      2. 시, 도 -> 시, 군, 구 -> 읍, 면, 구 -> 도로명 -> 건물번호
         * 경기도 안양시 동안구 평촌대로 111
  - 주소가 같다면 업체명을 비교하여 완전히 같은 업체인지 판단
----
#### 인기 검색어   

----
* package 구조
  ![Popular Package 구조](/img/popular-image.png)
* 인기검색어 Data
  * ConcurrentHashMap 이용
    * 동시 데이터 갱신시, 가장 안전한 자료구조
    * 추후 확장이 필요하면, 범용적으로 많이 사용하는 Redis의 SortedSet을 이용하는 방법으로 개선 가능
* 인기검색어 호출 특징
  * 비동기로 데이터를 처리하도록 구현
    * 인기검색어 카운트를 갱신하기 위해 본래 제공되어야 하는 서비스에 영향을 주지 않기 위해 분리
----
#### 반응성, 확장성, 동시성, 가용성등에 대한 고려사항

----
* Hexagonal Architecture
  * 인터페이스나 infrastructure의 변경에 도메인로직에는 영향을 주지 않음
  * Adapter는 도메인에 직접적으로 얽히지 않고 UseCase를 통해 도메인 로직 접근 가능
  * Port는 Adapter, Application의 결합도를 낮추는 역할
  * 새로운 Adapter를 추가하는 동안 Port가 Domain을 보호
  * 모의 Adapter를 만들어 테스트를 하는데 수월하게 하는 장점
    * [서비스 테스트 예시](https://github.com/jjwon90/location-integrator/blob/master/src/test/java/com/jwjung/location/search/application/service/LocationServiceTest.java)
  * Search 기능이나 Popular이 기능 확장 고려
    * 각 도메인 간의 호출은 Adapter를 통해 이루어 지므로 상황에 맞는 개발을 진행하기 용이해짐 
* 연동 장애 오류 사항 고려
  * CircuitBreaker를 통해 장애가 자주 났을 경우 차단 기능 추가
  * Timeout을 설정하여 Fail Fast 구현
    * 본 서비스 지연 현상 방지
  * 짧은 시간에 너무 많은 호출을 방지하고자 RateLimiter 도입
  * 별도 ThreadPool Bulkhead 도입
    * 동시에 호출되는 갯수를 제한
    * 별도 ThreadPool을 만들어 application Thread에 영향이 최소화 되도록 분리
* 반응성을 위한 고려사항
  * 인기검색어 counting 업데이트는 비동기 처리
    * 본 서비스에는 지연 발생 최소화
  * Naver와 카카오 API를 동시에 호출
    * 동기식으로 호출하는 것보다 속도 향상
    * Timeout을 설정하여 오래 걸린 응답은 배제하고 작업을 진행
