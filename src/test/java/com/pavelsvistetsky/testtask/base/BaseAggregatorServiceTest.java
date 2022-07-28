package com.pavelsvistetsky.testtask.base;

import com.pavelsvistetsky.testtask.model.dto.CameraDto;
import com.pavelsvistetsky.testtask.model.dto.SourceDataDto;
import com.pavelsvistetsky.testtask.model.dto.TokenDataDto;
import com.pavelsvistetsky.testtask.model.enums.URLType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class BaseAggregatorServiceTest {
    protected static final String SOURCE_DATA_URL = "1S";
    protected static final URI SOURCE_DATA_URI = URI.create("1S");
    protected static final String TOKEN_DATA_URL = "1T";
    protected static final URI TOKEN_DATA_URI = URI.create("1T");
    protected static final long ID = 1L;
    protected static final URLType URL_TYPE = URLType.LIVE;
    protected static final String VIDEO_URL = "https://example.com";
    protected static final int TTL = 255;
    protected static final UUID VALUE = UUID.randomUUID();

    protected static final CameraDto cameraDtoResponse = new CameraDto(ID, SOURCE_DATA_URL, TOKEN_DATA_URL);
    protected static final SourceDataDto sourceDataDtoResponse = new SourceDataDto(URL_TYPE, VIDEO_URL);
    protected static final TokenDataDto tokenDataDtoResponse = new TokenDataDto(VALUE, TTL);
}
