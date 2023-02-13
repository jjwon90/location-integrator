package com.jwjung.location.search.application.service;

import com.jwjung.location.popular.application.port.in.PopularCommand;
import com.jwjung.location.popular.data.MockPopular;
import com.jwjung.location.search.adapter.out.remote.model.RemoteLocationItemsV1;
import com.jwjung.location.search.application.port.in.SearchCommand;
import com.jwjung.location.search.application.port.out.GetLocationPort;
import com.jwjung.location.search.application.port.out.PopularPort;
import com.jwjung.location.search.domain.LocationItems;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MockitoSettings(strictness = Strictness.LENIENT)
class LocationServiceTest {
    @Spy
    GetLocationPort getLocationPort = new MockGetLocationPort();

    @Spy
    PopularPort popularPort = new MockPopularPort();

    @InjectMocks
    LocationService locationService;

    @Test
    @DisplayName("제대로 데이터가 insert되고, 결과값이 return되는지 확인")
    void getLocationItems() {
        String query = "test";

        LocationItems locationItems = locationService.getLocationItems(new SearchCommand(query));

        assertTrue(locationItems.itemList().isEmpty());
        assertEquals(1, (long) MockPopular.getTopTenQuery().get(0).getValue());
    }

    @AfterEach
    void reset() {
        MockPopular.reset();
    }

    static class MockPopularPort implements PopularPort {
        @Override
        public void producePopularEvent(String query) {
            updateAsync(query);
        }

        public void updateAsync(String query) {
            new Thread(() -> MockPopular.addQueryCount(new PopularCommand(query))).start();
        }
    }

    static class MockGetLocationPort implements GetLocationPort {

        @Override
        public LocationItems getLocationItems(String query) {
            return LocationItems.of(RemoteLocationItemsV1.emptyOf(), RemoteLocationItemsV1.emptyOf());
        }
    }
}