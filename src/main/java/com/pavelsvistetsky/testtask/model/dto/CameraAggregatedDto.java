package com.pavelsvistetsky.testtask.model.dto;

import com.pavelsvistetsky.testtask.model.enums.URLType;

import java.util.UUID;

public record CameraAggregatedDto(Long id, URLType type, String videoUrl, UUID value, int ttl) {
}
