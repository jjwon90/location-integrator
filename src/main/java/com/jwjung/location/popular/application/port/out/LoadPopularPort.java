package com.jwjung.location.popular.application.port.out;

import com.jwjung.location.popular.domain.PopularItem;

import java.util.List;

public interface LoadPopularPort {
    List<PopularItem> getTopTenItems();
}
