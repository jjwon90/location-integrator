package com.jwjung.location.search.application.port.in;

import com.jwjung.location.search.domain.LocationItems;

public interface LocationSearchUseCase {

    LocationItems getLocationItems(SearchCommand query);
}
