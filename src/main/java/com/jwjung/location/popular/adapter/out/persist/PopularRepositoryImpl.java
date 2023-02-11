package com.jwjung.location.popular.adapter.out.persist;

import com.jwjung.location.popular.application.port.in.PopularCommand;
import com.jwjung.location.popular.data.Popular;
import com.jwjung.location.popular.domain.PopularItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PopularRepositoryImpl implements PopularRepository{
    @Override
    public List<PopularItem> getTopTenPopularItems() {
        List<Map.Entry<String, Long>> topTenQuery = Popular.getTopTenQuery();

        return topTenQuery.stream()
                .map(entry -> new PopularItem(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public void updatePopularCount(PopularCommand popularCommand) {
        Popular.addQueryCount(popularCommand);
    }
}
