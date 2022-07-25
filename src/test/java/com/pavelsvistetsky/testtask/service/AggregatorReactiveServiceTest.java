package com.pavelsvistetsky.testtask.service;

import com.pavelsvistetsky.testtask.model.dto.CameraAggregatedDto;
import com.pavelsvistetsky.testtask.model.dto.CameraDto;
import com.pavelsvistetsky.testtask.model.dto.SourceDataDto;
import com.pavelsvistetsky.testtask.model.dto.TokenDataDto;
import com.pavelsvistetsky.testtask.model.enums.URLType;
import com.pavelsvistetsky.testtask.service.impl.AggregatorReactiveServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"rawtypes", "unchecked"})
class AggregatorReactiveServiceTest {

    private static final String SOURCE_DATA_URL = "1S";
    private static final String TOKEN_DATA_URL = "1T";
    private static final long ID = 1L;
    private static final URLType URL_TYPE = URLType.LIVE;
    private static final String VIDEO_URL = "https://example.com";
    private static final int TTL = 255;
    private static final UUID VALUE = UUID.randomUUID();

    @Mock
    private WebClient.RequestHeadersSpec sourceRequestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec sourceResponseSpec;

    @Mock
    private WebClient.RequestHeadersSpec tokenRequestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec tokenResponseSpec;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private WebClient client;

    private AggregatorReactiveService service;

    @BeforeEach
    void setUp() {
        CameraDto cameraDtoResponse = new CameraDto(ID, SOURCE_DATA_URL, TOKEN_DATA_URL);
        SourceDataDto sourceDataDtoResponse = new SourceDataDto(URL_TYPE, VIDEO_URL);
        TokenDataDto tokenDataDtoResponse = new TokenDataDto(VALUE, TTL);

        when(client.get()).thenReturn(requestHeadersUriSpec);

        when(requestHeadersUriSpec.uri(URI.create(SOURCE_DATA_URL))).thenReturn(sourceRequestHeadersSpec);
        when(sourceRequestHeadersSpec.retrieve()).thenReturn(sourceResponseSpec);
        when(sourceResponseSpec.bodyToMono(SourceDataDto.class)).thenReturn(Mono.just(sourceDataDtoResponse));

        when(requestHeadersUriSpec.uri(URI.create(TOKEN_DATA_URL))).thenReturn(tokenRequestHeadersSpec);
        when(tokenRequestHeadersSpec.retrieve()).thenReturn(tokenResponseSpec);
        when(tokenResponseSpec.bodyToMono(TokenDataDto.class)).thenReturn(Mono.just(tokenDataDtoResponse));

        when(requestHeadersUriSpec.uri(ArgumentMatchers.<String>any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(CameraDto.class)).thenReturn(Flux.just(cameraDtoResponse));

        service = new AggregatorReactiveServiceImpl(client);
    }

    @Test
    void getAggregatedCameras() {

        Flux<CameraAggregatedDto> result = service.getAggregatedCameras();

        StepVerifier.create(result)
                .expectNextMatches(response -> response.id().equals(ID)
                        && response.type().equals(URL_TYPE)
                        && response.videoUrl().equals(VIDEO_URL)
                        && response.value().equals(VALUE)
                        && response.ttl() == TTL)
                .verifyComplete();
    }
}