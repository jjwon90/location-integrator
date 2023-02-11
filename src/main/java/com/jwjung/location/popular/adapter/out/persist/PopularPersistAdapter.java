package com.jwjung.location.popular.adapter.out.persist;

import com.jwjung.location.popular.application.port.in.PopularCommand;
import com.jwjung.location.popular.application.port.out.LoadPopularPort;
import com.jwjung.location.popular.application.port.out.UpdatePopularPort;
import com.jwjung.location.popular.domain.PopularItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularPersistAdapter implements LoadPopularPort, UpdatePopularPort {
    private final PopularRepository popularRepository;

    @Override
    public List<PopularItem> getTopTenItems() {
        return popularRepository.getTopTenPopularItems();
    }

    @Override
    public void updatePopularCount(PopularCommand popularCommand) {
        popularRepository.updatePopularCount(popularCommand);
    }
}
