package com.pavelsvistetsky.testtask.util.mapper;

import com.pavelsvistetsky.testtask.model.dto.CameraAggregatedDto;
import com.pavelsvistetsky.testtask.model.dto.SourceDataDto;
import com.pavelsvistetsky.testtask.model.dto.TokenDataDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CameraMapper {

    public CameraAggregatedDto aggregateCameraData(Long id, SourceDataDto sourceData, TokenDataDto tokenData) {
        return new CameraAggregatedDto(id,
                sourceData.urlType(),
                sourceData.videoUrl(),
                tokenData.value(),
                tokenData.ttl());
    }
}
