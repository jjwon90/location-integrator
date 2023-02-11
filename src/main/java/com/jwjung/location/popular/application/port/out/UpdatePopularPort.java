package com.jwjung.location.popular.application.port.out;

import com.jwjung.location.popular.application.port.in.PopularCommand;

public interface UpdatePopularPort {
    void updatePopularCount(PopularCommand popularCommand);
}
