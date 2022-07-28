package com.pavelsvistetsky.testtask.service;

import com.pavelsvistetsky.testtask.base.BaseAggregatorServiceTest;
import com.pavelsvistetsky.testtask.model.dto.CameraAggregatedDto;
import com.pavelsvistetsky.testtask.model.dto.CameraDto;
import com.pavelsvistetsky.testtask.model.dto.SourceDataDto;
import com.pavelsvistetsky.testtask.model.dto.TokenDataDto;
import com.pavelsvistetsky.testtask.service.impl.AggregatorMultithreadingServiceImpl;
import com.pavelsvistetsky.testtask.service.impl.integration.ExternalDataServiceImpl;
import com.pavelsvistetsky.testtask.service.integration.ExternalDataService;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class AggregatorMultithreadingServiceTest extends BaseAggregatorServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private AggregatorMultithreadingService service;

    @BeforeEach
    void setUp() {
        when(restTemplate.getForEntity(SOURCE_DATA_URI, SourceDataDto.class))
                .thenReturn(ResponseEntity.ok(sourceDataDtoResponse));
        when(restTemplate.getForEntity(TOKEN_DATA_URI, TokenDataDto.class))
                .thenReturn(ResponseEntity.ok(tokenDataDtoResponse));
        when(restTemplate.getForEntity(ArgumentMatchers.<String>any(), eq(CameraDto[].class)))
                .thenReturn(ResponseEntity.ok(Arrays.array(cameraDtoResponse)));

        final ExternalDataService externalDataService = new ExternalDataServiceImpl(restTemplate);
        service = new AggregatorMultithreadingServiceImpl(restTemplate, externalDataService);
    }

    @Test
    void getAggregatedCameras() {

        CameraAggregatedDto result = service.getAggregatedCameras().get(0);

        assertThat(result.id(), is(ID));
        assertThat(result.type(), is(URL_TYPE));
        assertThat(result.videoUrl(), is(VIDEO_URL));
        assertThat(result.value(), is(VALUE));
        assertThat(result.ttl(), is(TTL));
    }
}