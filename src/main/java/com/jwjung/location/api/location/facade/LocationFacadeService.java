package com.jwjung.location.api.location.facade;

import com.jwjung.location.api.location.dto.LocationResultsDTO;

public interface LocationFacadeService {
    LocationResultsDTO getLocationItems(String query);
}
