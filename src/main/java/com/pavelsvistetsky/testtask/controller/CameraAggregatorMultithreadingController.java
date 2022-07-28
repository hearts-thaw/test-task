package com.pavelsvistetsky.testtask.controller;

import com.pavelsvistetsky.testtask.model.dto.CameraAggregatedDto;
import com.pavelsvistetsky.testtask.service.AggregatorMultithreadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class CameraAggregatorMultithreadingController {

    private final AggregatorMultithreadingService aggregatorService;

    @GetMapping
    public List<CameraAggregatedDto> all() {
        return aggregatorService.getAggregatedCameras();
    }
}
