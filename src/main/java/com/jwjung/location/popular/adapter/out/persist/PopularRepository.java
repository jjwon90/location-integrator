package com.jwjung.location.popular.adapter.out.persist;

import com.jwjung.location.popular.application.port.in.PopularCommand;
import com.jwjung.location.popular.domain.PopularItem;

import java.util.List;

public interface PopularRepository {
    List<PopularItem> getTopTenPopularItems();

    void updatePopularCount(PopularCommand popularCommand);
}
