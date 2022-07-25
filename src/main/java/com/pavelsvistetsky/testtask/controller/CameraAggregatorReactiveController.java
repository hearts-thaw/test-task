package com.pavelsvistetsky.testtask.controller;

import com.pavelsvistetsky.testtask.model.dto.CameraAggregatedDto;
import com.pavelsvistetsky.testtask.service.AggregatorReactiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CameraAggregatorReactiveController {

    private final AggregatorReactiveService aggregatorService;

    @GetMapping
    public Flux<CameraAggregatedDto> all() {
        return aggregatorService.getAggregatedCameras();
    }
}
