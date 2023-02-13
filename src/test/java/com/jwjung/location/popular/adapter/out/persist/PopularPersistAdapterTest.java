package com.jwjung.location.popular.adapter.out.persist;

import com.jwjung.location.popular.application.port.in.PopularCommand;
import com.jwjung.location.popular.data.MockPopular;
import com.jwjung.location.popular.domain.PopularItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MockitoSettings
class PopularPersistAdapterTest {
    @InjectMocks
    PopularPersistAdapter popularPersistAdapter;

    @Spy
    PopularRepository popularRepository = new MockPopularRepository();

    class MockPopularRepository implements PopularRepository {

        @Override
        public List<PopularItem> getTopTenPopularItems() {
            List<Map.Entry<String, Long>> topTenQuery = MockPopular.getTopTenQuery();

            return topTenQuery.stream()
                    .map(entry -> new PopularItem(entry.getKey(), entry.getValue()))
                    .toList();
        }

        @Override
        public void updatePopularCount(PopularCommand popularCommand) {
            MockPopular.addQueryCount(popularCommand);
        }
    }

    @Nested
    class UpdateItemTest {
        @Test
        @DisplayName("update 카운팅이 제대로 되는지 확인")
        void updatePopularCount() {
            popularPersistAdapter.updatePopularCount(new PopularCommand("테스트11"));

            assertEquals(1, MockPopular.getTopTenQuery().get(0).getValue());
        }

        @AfterEach
        void reset() {
            MockPopular.reset();
        }
    }

    @Nested
    class GetItemTest {
        @Test
        @DisplayName("top ten이 제대로 return되는지 확인")
        void getTopTenItems() {
            popularPersistAdapter.updatePopularCount(new PopularCommand("테스트1"));
            popularPersistAdapter.updatePopularCount(new PopularCommand("테스트2"));
            popularPersistAdapter.updatePopularCount(new PopularCommand("테스트3"));
            popularPersistAdapter.updatePopularCount(new PopularCommand("테스트4"));
            popularPersistAdapter.updatePopularCount(new PopularCommand("테스트5"));
            popularPersistAdapter.updatePopularCount(new PopularCommand("테스트6"));
            popularPersistAdapter.updatePopularCount(new PopularCommand("테스트7"));
            popularPersistAdapter.updatePopularCount(new PopularCommand("테스트8"));
            popularPersistAdapter.updatePopularCount(new PopularCommand("테스트9"));
            popularPersistAdapter.updatePopularCount(new PopularCommand("테스트10"));

            assertEquals(10, MockPopular.getTopTenQuery().size());
        }

        @AfterEach
        void reset() {
            MockPopular.reset();
        }
    }
}