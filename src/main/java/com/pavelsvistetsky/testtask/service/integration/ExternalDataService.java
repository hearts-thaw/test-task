package com.pavelsvistetsky.testtask.service.integration;

import java.util.concurrent.CompletableFuture;

public interface ExternalDataService {
    <R> CompletableFuture<R> getDataDto(String dataUrl, Class<R> dataClass);
}
