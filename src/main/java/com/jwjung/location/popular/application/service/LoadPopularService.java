package com.jwjung.location.popular.application.service;

import com.jwjung.location.popular.application.port.in.LoadPopularUseCase;
import com.jwjung.location.popular.application.port.out.LoadPopularPort;
import com.jwjung.location.popular.domain.PopularItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoadPopularService implements LoadPopularUseCase {
    private final LoadPopularPort loadPopularPort;

    @Override
    public List<PopularItem> getTopTenItems() {
        return loadPopularPort.getTopTenItems();
    }
}
