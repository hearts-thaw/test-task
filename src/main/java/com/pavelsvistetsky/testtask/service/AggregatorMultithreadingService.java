package com.pavelsvistetsky.testtask.service;

import com.pavelsvistetsky.testtask.model.dto.CameraAggregatedDto;

import java.util.List;

public interface AggregatorMultithreadingService {
    List<CameraAggregatedDto> getAggregatedCameras();
}
