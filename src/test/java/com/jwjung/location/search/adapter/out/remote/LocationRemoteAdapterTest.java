package com.jwjung.location.search.adapter.out.remote;

import com.jwjung.location.search.adapter.out.remote.kakao.KakaoLocationService;
import com.jwjung.location.search.adapter.out.remote.kakao.dto.KakaoLocationItemV1;
import com.jwjung.location.search.adapter.out.remote.kakao.dto.KakaoLocationResponseV1;
import com.jwjung.location.search.adapter.out.remote.model.RemoteLocationItemV1;
import com.jwjung.location.search.adapter.out.remote.model.RemoteLocationItemsV1;
import com.jwjung.location.search.adapter.out.remote.naver.NaverLocationService;
import com.jwjung.location.search.adapter.out.remote.naver.dto.NaverLocationItemV1;
import com.jwjung.location.search.adapter.out.remote.naver.dto.NaverLocationResponseV1;
import com.jwjung.location.search.domain.LocationItem;
import com.jwjung.location.search.domain.LocationItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@MockitoSettings(strictness = Strictness.LENIENT)
class LocationRemoteAdapterTest {
    @InjectMocks
    LocationRemoteAdapter locationRemoteAdapter;

    @Mock
    KakaoLocationService kakaoLocationService;

    @Mock
    NaverLocationService naverLocationService;

    @Test
    @DisplayName("두개의 api 모두 정상 응답")
    void getLocationItems() {
        RemoteLocationItemsV1 ofNaverItems = RemoteLocationItemsV1.ofNaverItems(new NaverLocationResponseV1(5, 0, 5,
                List.of(new NaverLocationItemV1("부산은행 서울금융센터", "서울특별시 중구 무교로 6 (을지로1가)", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("신한은행 서울시청금융센터", "서울특별시 중구 태평로1가 31", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("KB국민은행 동대문패션타운", "서울특별시 중구 장충단로13길 34 동화상가 2층", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("IBK기업은행 본사", "서울특별시 중구 을지로 79", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("KB국민은행 명동영업부", "서울특별시 중구 을지로 51 (을지로2가, 교원내외빌딩)", "", "", "", "", "", "", ""))));
        CompletableFuture<RemoteLocationItemsV1> naverFuture = CompletableFuture.completedFuture(ofNaverItems);

        RemoteLocationItemsV1 ofKakaoItems = RemoteLocationItemsV1.ofKakaoItems(new KakaoLocationResponseV1(List.of(
                new KakaoLocationItemV1("하나은행 본점", "서울 중구 을지로 35", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("KB국민은행 여의도본점", "서울 영등포구 국제금융로8길 26", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("KB국민은행 영등포지점", "서울 영등포구 영등포로 208", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("우리은행 본점", "서울 중구 소공로 51", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("KB국민은행 강남파이낸스종합금융센터", "서울 강남구 테헤란로 152", "", "", "", "", "", "", "", "", "", ""))));
        CompletableFuture<RemoteLocationItemsV1> kakaoFuture = CompletableFuture.completedFuture(ofKakaoItems);

        doReturn(naverFuture).when(naverLocationService).getNaverItemsFuture(anyString());
        doReturn(kakaoFuture).when(kakaoLocationService).getKakaoItemsFuture(anyString());


        LocationItems test = locationRemoteAdapter.getLocationItems("test");

        String collect = test.itemList()
                .stream()
                .map(LocationItem::name)
                .collect(Collectors.joining(", "));

        assertEquals(10, test.itemList().size());
        assertEquals("하나은행 본점, KB국민은행 여의도본점, KB국민은행 영등포지점, 우리은행 본점, KB국민은행 강남파이낸스종합금융센터, 부산은행 서울금융센터, 신한은행 서울시청금융센터, KB국민은행 동대문패션타운, IBK기업은행 본사, KB국민은행 명동영업부",
                collect);
    }

    @Test
    @DisplayName("카카오 api 만 정상 응답")
    void getLocationItems__only_kakao_response() {
        CompletableFuture<RemoteLocationItemsV1> naverFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500L);
                return RemoteLocationItemsV1.emptyOf();
            } catch (InterruptedException e) {
                return RemoteLocationItemsV1.emptyOf();
            }
        }).completeOnTimeout(new RemoteLocationItemsV1(List.of(new RemoteLocationItemV1("test", "서울 광진구 건대로 22"))),
                300, TimeUnit.MILLISECONDS);


        RemoteLocationItemsV1 ofKakaoItems = RemoteLocationItemsV1.ofKakaoItems(new KakaoLocationResponseV1(List.of(
                new KakaoLocationItemV1("하나은행 본점", "서울 중구 을지로 35", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("KB국민은행 여의도본점", "서울 영등포구 국제금융로8길 26", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("KB국민은행 영등포지점", "서울 영등포구 영등포로 208", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("우리은행 본점", "서울 중구 소공로 51", "", "", "", "", "", "", "", "", "", ""),
                new KakaoLocationItemV1("KB국민은행 강남파이낸스종합금융센터", "서울 강남구 테헤란로 152", "", "", "", "", "", "", "", "", "", ""))));
        CompletableFuture<RemoteLocationItemsV1> kakaoFuture = CompletableFuture.completedFuture(ofKakaoItems);

        doReturn(naverFuture).when(naverLocationService).getNaverItemsFuture(anyString());
        doReturn(kakaoFuture).when(kakaoLocationService).getKakaoItemsFuture(anyString());


        LocationItems test = locationRemoteAdapter.getLocationItems("test");

        String collect = test.itemList()
                .stream()
                .map(LocationItem::name)
                .collect(Collectors.joining(", "));

        assertEquals(6, test.itemList().size());
        assertEquals("하나은행 본점, KB국민은행 여의도본점, KB국민은행 영등포지점, 우리은행 본점, KB국민은행 강남파이낸스종합금융센터, test",
                collect);
    }

    @Test
    @DisplayName("naver api 만 정상 응답")
    void getLocationItems__only_naver_response() {
        CompletableFuture<RemoteLocationItemsV1> kakaoFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500L);
                return RemoteLocationItemsV1.emptyOf();
            } catch (InterruptedException e) {
                return RemoteLocationItemsV1.emptyOf();
            }
        }).completeOnTimeout(new RemoteLocationItemsV1(List.of(new RemoteLocationItemV1("test", "서울 광진구 건대로 22"))),
                300, TimeUnit.MILLISECONDS);


        RemoteLocationItemsV1 ofNaverItems = RemoteLocationItemsV1.ofNaverItems(new NaverLocationResponseV1(5, 0, 5,
                List.of(new NaverLocationItemV1("부산은행 서울금융센터", "서울특별시 중구 무교로 6 (을지로1가)", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("신한은행 서울시청금융센터", "서울특별시 중구 태평로1가 31", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("KB국민은행 동대문패션타운", "서울특별시 중구 장충단로13길 34 동화상가 2층", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("IBK기업은행 본사", "서울특별시 중구 을지로 79", "", "", "", "", "", "", ""),
                        new NaverLocationItemV1("KB국민은행 명동영업부", "서울특별시 중구 을지로 51 (을지로2가, 교원내외빌딩)", "", "", "", "", "", "", ""))));
        CompletableFuture<RemoteLocationItemsV1> naverFuture = CompletableFuture.completedFuture(ofNaverItems);

        doReturn(naverFuture).when(naverLocationService).getNaverItemsFuture(anyString());
        doReturn(kakaoFuture).when(kakaoLocationService).getKakaoItemsFuture(anyString());


        LocationItems test = locationRemoteAdapter.getLocationItems("test");

        String collect = test.itemList()
                .stream()
                .map(LocationItem::name)
                .collect(Collectors.joining(", "));

        assertEquals(6, test.itemList().size());
        assertEquals("test, 부산은행 서울금융센터, 신한은행 서울시청금융센터, KB국민은행 동대문패션타운, IBK기업은행 본사, KB국민은행 명동영업부",
                collect);
    }

    @Test
    @DisplayName("모두 응답이 오지 않은 경우")
    void getLocationItems__all_not_have_response() {
        CompletableFuture<RemoteLocationItemsV1> kakaoFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500L);
                return RemoteLocationItemsV1.emptyOf();
            } catch (InterruptedException e) {
                return RemoteLocationItemsV1.emptyOf();
            }
        }).completeOnTimeout(new RemoteLocationItemsV1(List.of(new RemoteLocationItemV1("test", "서울 광진구 건대로 22"))),
                300, TimeUnit.MILLISECONDS);

        CompletableFuture<RemoteLocationItemsV1> naverFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500L);
                return RemoteLocationItemsV1.emptyOf();
            } catch (InterruptedException e) {
                return RemoteLocationItemsV1.emptyOf();
            }
        }).completeOnTimeout(new RemoteLocationItemsV1(List.of(new RemoteLocationItemV1("test", "서울 광진구 건대로 22"))),
                300, TimeUnit.MILLISECONDS);

        doReturn(naverFuture).when(naverLocationService).getNaverItemsFuture(anyString());
        doReturn(kakaoFuture).when(kakaoLocationService).getKakaoItemsFuture(anyString());


        LocationItems test = locationRemoteAdapter.getLocationItems("test");

        String collect = test.itemList()
                .stream()
                .map(LocationItem::name)
                .collect(Collectors.joining(", "));

        assertEquals(1, test.itemList().size());
        assertEquals("test",
                collect);
    }
}