package com.jwjung.location.common.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

@Configuration
@EnableAsync
public class AsyncThreadConfig {
    @Bean(name = "asyncLocationSearcher")
    public ExecutorService asyncLocationSearcher() {
        return newFixedThreadPool(30);
    }

    @Bean(name = "asyncPopularAdder")
    public ExecutorService asyncPopularAdder() {
        return newFixedThreadPool(30);
    }
}
