package com.jwjung.location.search.adapter.in.web;

import com.jwjung.location.search.adapter.out.remote.kakao.dto.KakaoLocationItemV1;
import com.jwjung.location.search.adapter.out.remote.kakao.dto.KakaoLocationResponseV1;
import com.jwjung.location.search.adapter.out.remote.model.RemoteLocationItemsV1;
import com.jwjung.location.search.application.port.in.LocationSearchUseCase;
import com.jwjung.location.search.domain.LocationItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LocationController.class)
class LocationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    LocationSearchUseCase locationSearchUseCase;

    @Test
    @DisplayName("일반적인 응답 케이스 테스트")
    void test_getLocations() throws Exception {
        given(locationSearchUseCase.getLocationItems(any()))
                .willReturn(LocationItems.of(RemoteLocationItemsV1.ofKakaoItems(new KakaoLocationResponseV1(List.of(
                                new KakaoLocationItemV1("하나은행 본점", "서울 중구 을지로 35", "", "", "", "", "", "", "", "", "", ""),
                                new KakaoLocationItemV1("KB국민은행 여의도본점", "서울 영등포구 국제금융로8길 26", "", "", "", "", "", "", "", "", "", ""),
                                new KakaoLocationItemV1("KB국민은행 영등포지점", "서울 영등포구 영등포로 208", "", "", "", "", "", "", "", "", "", ""),
                                new KakaoLocationItemV1("우리은행 본점", "서울 중구 소공로 51", "", "", "", "", "", "", "", "", "", ""),
                                new KakaoLocationItemV1("KB국민은행 강남파이낸스종합금융센터", "서울 강남구 테헤란로 152", "", "", "", "", "", "", "", "", "", "")))),
                        RemoteLocationItemsV1.emptyOf()));

        mockMvc.perform(get("/v1/locations").param("query", "동탄"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultList").isNotEmpty());

        then(locationSearchUseCase).should()
                .getLocationItems(any());
    }

    @Test
    @DisplayName("문자열이 없는 것이 요청되는 경우 테스트")
    void test_getLocations__Bad_Request() throws Exception {
        mockMvc.perform(get("/v1/locations").param("query", ""))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(equalTo("BAD_REQUEST")))
                .andExpect(jsonPath("$.message").value(equalTo("getLocations.query: must not be empty")));
    }

}