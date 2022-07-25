package com.pavelsvistetsky.testtask.service.impl;

import com.pavelsvistetsky.testtask.model.dto.CameraAggregatedDto;
import com.pavelsvistetsky.testtask.model.dto.CameraDto;
import com.pavelsvistetsky.testtask.model.dto.SourceDataDto;
import com.pavelsvistetsky.testtask.model.dto.TokenDataDto;
import com.pavelsvistetsky.testtask.service.AggregatorReactiveService;
import com.pavelsvistetsky.testtask.util.mapper.CameraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service("reactive")
@RequiredArgsConstructor
public class AggregatorReactiveServiceImpl implements AggregatorReactiveService {

    @Value("${com.pavelsvistetsky.camera-server.all-cameras-path}")
    private String allCamerasPath;

    private final WebClient cameraWebClient;

    @Override
    public Flux<CameraAggregatedDto> getAggregatedCameras() {
        return cameraWebClient.get()
                .uri(allCamerasPath)
                .retrieve()
                .bodyToFlux(CameraDto.class)
                .flatMap(this::parseCameraData);
    }

    private Mono<CameraAggregatedDto> parseCameraData(CameraDto cameraDto) {
        Mono<SourceDataDto> sourceDataPublisher = cameraWebClient.get()
                .uri(URI.create(cameraDto.sourceDataUrl()))
                .retrieve()
                .bodyToMono(SourceDataDto.class)
                .cache();
        Mono<TokenDataDto> tokenDataPublisher = cameraWebClient.get()
                .uri(URI.create(cameraDto.tokenDataUrl()))
                .retrieve()
                .bodyToMono(TokenDataDto.class);
        return sourceDataPublisher.zipWith(tokenDataPublisher).map(zipped -> {
            SourceDataDto sourceData = zipped.getT1();
            TokenDataDto tokenData = zipped.getT2();
            return CameraMapper.aggregateCameraData(cameraDto.id(), sourceData, tokenData);
        });
    }
}
