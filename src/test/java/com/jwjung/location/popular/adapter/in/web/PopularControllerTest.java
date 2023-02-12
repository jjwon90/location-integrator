package com.jwjung.location.popular.adapter.in.web;

import com.jwjung.location.popular.application.port.in.LoadPopularUseCase;
import com.jwjung.location.popular.domain.PopularItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PopularController.class)
class PopularControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    LoadPopularUseCase loadPopularUseCase;

    @Test
    @DisplayName("정상 일반적인 케이스 테스트")
    void test__getPopularQueries() throws Exception {
        given(loadPopularUseCase.getTopTenItems())
                .willReturn(List.of(new PopularItem("test1", 1)));

        mockMvc.perform(get("/v1/popular-queries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.popularList").isNotEmpty())
                .andExpect(jsonPath("$.popularList[0].query").value(equalTo("test1")))
                .andExpect(jsonPath("$.popularList[0].count").value(equalTo(1)));
    }

    @Test
    @DisplayName("인기검색어가 없는 케이스")
    void test__getPopularQueries_empty_case() throws Exception {
        given(loadPopularUseCase.getTopTenItems())
                .willReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/popular-queries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.popularList").isEmpty());
    }
}