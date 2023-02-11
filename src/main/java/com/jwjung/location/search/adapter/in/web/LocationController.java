package com.jwjung.location.search.adapter.in.web;

import com.jwjung.location.search.adapter.in.web.dto.LocationResultsDTO;
import com.jwjung.location.search.application.port.in.LocationSearchUseCase;
import com.jwjung.location.search.application.port.in.SearchCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LocationController {
    private final LocationSearchUseCase locationSearchUseCase;

    @GetMapping("/v1/location")
    public LocationResultsDTO getLocations(@RequestParam("query") String query) {
        return LocationResultsDTO.of(locationSearchUseCase.getLocationItems(new SearchCommand(query)));
    }
}
