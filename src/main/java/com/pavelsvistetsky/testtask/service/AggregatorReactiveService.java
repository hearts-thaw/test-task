package com.pavelsvistetsky.testtask.service;

import com.pavelsvistetsky.testtask.model.dto.CameraAggregatedDto;
import reactor.core.publisher.Flux;

public interface AggregatorReactiveService {
    Flux<CameraAggregatedDto> getAggregatedCameras();
}
