package com.example.jwtandwebsocket.utils.service;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class JpaExecutorService {

    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @PreDestroy
    public void destroy() {
        if (this.executorService != null) {
            executorService.shutdown();
        }
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
