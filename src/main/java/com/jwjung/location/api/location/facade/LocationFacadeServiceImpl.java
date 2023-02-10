package com.jwjung.location.api.location.facade;

import com.jwjung.location.api.location.dto.LocationResultsDTO;
import com.jwjung.location.domain.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class LocationFacadeServiceImpl implements LocationFacadeService {
    private final LocationService locationService;

    @Override
    public LocationResultsDTO getLocationItems(String query) {
        if (StringUtils.isEmpty(query)) {
            return new LocationResultsDTO(Collections.emptyList());
        }

        return LocationResultsDTO.of(locationService.getLocationItems(query));
    }
}
