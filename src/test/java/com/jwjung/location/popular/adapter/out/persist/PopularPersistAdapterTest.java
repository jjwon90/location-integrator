package com.jwjung.location.popular.adapter.out.persist;

import com.jwjung.location.popular.application.port.in.PopularCommand;
import com.jwjung.location.popular.data.Popular;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PopularPersistAdapterTest {
    @InjectMocks
    PopularPersistAdapter popularPersistAdapter;

    @Spy
    PopularRepository popularRepository = new PopularRepositoryImpl();

    @Test
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

        assertEquals(10, Popular.getTopTenQuery().size());
    }

    @Test
    void updatePopularCount() {
    }
}