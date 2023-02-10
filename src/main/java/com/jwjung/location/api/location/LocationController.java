package com.jwjung.location.api.location;

import com.jwjung.location.api.location.dto.LocationResultsDTO;
import com.jwjung.location.api.location.facade.LocationFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LocationController {
    private final LocationFacadeService locationFacadeService;

    @GetMapping("/v1/location")
    public LocationResultsDTO getLocations(@RequestParam("query") String query) {
        return locationFacadeService.getLocationItems(query);
    }
}
