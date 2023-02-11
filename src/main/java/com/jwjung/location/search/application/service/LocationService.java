package com.jwjung.location.search.application.service;

import com.jwjung.location.search.application.port.in.LocationSearchUseCase;
import com.jwjung.location.search.application.port.in.SearchCommand;
import com.jwjung.location.search.application.port.out.GetLocationPort;
import com.jwjung.location.search.application.port.out.PopularPort;
import com.jwjung.location.search.domain.LocationItems;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService implements LocationSearchUseCase {
    private final GetLocationPort getLocationPort;
    private final PopularPort popularPort;

    @Override
    public LocationItems getLocationItems(SearchCommand searchCommand) {
        popularPort.producePopularEvent(searchCommand.query());
        return getLocationPort.getLocationItems(searchCommand.query());
    }
}
