package com.pavelsvistetsky.testtask.service.impl.integration;

import com.pavelsvistetsky.testtask.service.integration.ExternalDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ExternalDataServiceImpl implements ExternalDataService {

    private final RestTemplate externalDataRestTemplate;

    @Async
    @Override
    public <T> CompletableFuture<T> getDataDto(String dataUrl, Class<T> dataClass) {
        T dataDto = externalDataRestTemplate
                .getForEntity(URI.create(dataUrl), dataClass)
                .getBody();
        return CompletableFuture.completedFuture(dataDto);
    }

}
