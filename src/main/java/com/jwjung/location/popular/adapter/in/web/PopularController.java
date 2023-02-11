package com.jwjung.location.popular.adapter.in.web;

import com.jwjung.location.popular.adapter.in.web.dto.PopularQueriesDTO;
import com.jwjung.location.popular.application.port.in.LoadPopularUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PopularController {
    private final LoadPopularUseCase loadPopularUseCase;

    @GetMapping("/v1/popular-queries")
    public PopularQueriesDTO getPopularQueries() {
        return PopularQueriesDTO.of(loadPopularUseCase.getTopTenItems());
    }
}
