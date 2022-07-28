package com.pavelsvistetsky.testtask.service;

import com.pavelsvistetsky.testtask.base.BaseAggregatorServiceTest;
import com.pavelsvistetsky.testtask.model.dto.CameraAggregatedDto;
import com.pavelsvistetsky.testtask.model.dto.CameraDto;
import com.pavelsvistetsky.testtask.model.dto.SourceDataDto;
import com.pavelsvistetsky.testtask.model.dto.TokenDataDto;
import com.pavelsvistetsky.testtask.service.impl.AggregatorReactiveServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@SuppressWarnings({"rawtypes", "unchecked"})
class AggregatorReactiveServiceTest extends BaseAggregatorServiceTest {

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
        when(client.get()).thenReturn(requestHeadersUriSpec);

        when(requestHeadersUriSpec.uri(SOURCE_DATA_URI)).thenReturn(sourceRequestHeadersSpec);
        when(sourceRequestHeadersSpec.retrieve()).thenReturn(sourceResponseSpec);
        when(sourceResponseSpec.bodyToMono(SourceDataDto.class)).thenReturn(Mono.just(sourceDataDtoResponse));

        when(requestHeadersUriSpec.uri(TOKEN_DATA_URI)).thenReturn(tokenRequestHeadersSpec);
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