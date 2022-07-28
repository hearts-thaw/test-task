package com.pavelsvistetsky.testtask.service.impl;

import com.pavelsvistetsky.testtask.model.dto.CameraAggregatedDto;
import com.pavelsvistetsky.testtask.model.dto.CameraDto;
import com.pavelsvistetsky.testtask.model.dto.SourceDataDto;
import com.pavelsvistetsky.testtask.model.dto.TokenDataDto;
import com.pavelsvistetsky.testtask.service.AggregatorMultithreadingService;
import com.pavelsvistetsky.testtask.service.integration.ExternalDataService;
import com.pavelsvistetsky.testtask.util.mapper.CameraMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service("multithreading")
@RequiredArgsConstructor
public class AggregatorMultithreadingServiceImpl implements AggregatorMultithreadingService {

    @Value("${com.pavelsvistetsky.camera-server.all-cameras-path}")
    private String allCamerasPath;

    private final RestTemplate cameraRestTemplate;

    private final ExternalDataService externalDataService;

    @Override
    public List<CameraAggregatedDto> getAggregatedCameras() {

        ResponseEntity<CameraDto[]> cameraDtoResponseEntity =
                cameraRestTemplate.getForEntity(allCamerasPath, CameraDto[].class);
        if (cameraDtoResponseEntity.hasBody()) {
            CameraDto[] cameraDtoArray = cameraDtoResponseEntity.getBody();
            return Arrays.stream(cameraDtoArray)
                    .map(this::getAggregatedCameraDto)
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        }
        return List.of();
    }


    private CompletableFuture<CameraAggregatedDto> getAggregatedCameraDto(CameraDto cameraDto) {
        CompletableFuture<SourceDataDto> sourceDataFuture = externalDataService.getDataDto(cameraDto.sourceDataUrl(), SourceDataDto.class);
        CompletableFuture<TokenDataDto> tokenDataFuture = externalDataService.getDataDto(cameraDto.tokenDataUrl(), TokenDataDto.class);

        return sourceDataFuture.thenCombine(tokenDataFuture,
                (sourceData, tokenData) -> CameraMapper.aggregateCameraData(cameraDto.id(), sourceData, tokenData));
    }

}
