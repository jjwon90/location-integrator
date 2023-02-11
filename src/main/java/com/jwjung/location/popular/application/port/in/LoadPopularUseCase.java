package com.jwjung.location.popular.application.port.in;

import com.jwjung.location.popular.domain.PopularItem;

import java.util.List;

public interface LoadPopularUseCase {
    List<PopularItem> getTopTenItems();
}
