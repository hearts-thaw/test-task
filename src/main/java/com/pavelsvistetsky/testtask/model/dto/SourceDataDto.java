package com.pavelsvistetsky.testtask.model.dto;

import com.pavelsvistetsky.testtask.model.enums.URLType;

public record SourceDataDto(URLType urlType, String videoUrl) {
}
