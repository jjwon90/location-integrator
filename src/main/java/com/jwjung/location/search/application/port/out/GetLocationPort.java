package com.jwjung.location.search.application.port.out;

import com.jwjung.location.search.domain.LocationItems;

public interface GetLocationPort {
    LocationItems getLocationItems(String query);
}
