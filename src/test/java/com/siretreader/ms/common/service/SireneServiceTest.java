package com.siretreader.ms.common.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.siretreader.ms.common.model.entity.Establishment;
import com.siretreader.ms.common.model.entity.EstablishmentResponse;
import com.siretreader.ms.common.model.entity.LegalUnit;
import com.siretreader.ms.common.properties.SiretReaderProperties;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class SireneServiceTest {

    private final String sirenUrl = "https://entreprise.data.gouv.fr/api/sirene/v3/etablissements/";
    private final List<String> siretNumbers = List.of("31302979500017");

    @InjectMocks
    SireneService sireneService;

    @Mock
    SiretReaderProperties properties;

    @Mock
    WebClient webClient;

    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    WebClient.ResponseSpec responseSpec;

    @Test
    void getEstablishmentsFromSirenTest() {
        when(properties.getSiretNumbers()).thenReturn(siretNumbers);
        when(properties.getUrl()).thenReturn(sirenUrl);
        lenient().when(webClient.get()).thenReturn(requestHeadersUriSpec);
        lenient().when(requestHeadersUriSpec.uri(URI.create(String.format("%s%s", sirenUrl, siretNumbers.get(0)))))
                .thenReturn(requestHeadersSpec);
        lenient().when(requestHeadersSpec.accept(MediaType.APPLICATION_JSON)).thenReturn(requestHeadersSpec);
        lenient().when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        lenient().when(responseSpec.bodyToMono(EstablishmentResponse.class))
                .thenReturn(Mono.just(new EstablishmentResponse(createMockedEstablishment())));

        final var establishments = sireneService.getEstablishmentsFromSiren();

        assertAll(
                () -> assertEquals(establishments.size(), 1),
                () -> assertEquals(establishments.get(0).getSiret(), siretNumbers.get(0))
        );
    }

    private Establishment createMockedEstablishment() {
        final var legalUnit = new LegalUnit("FR96313029795");
        return new Establishment("1557207089", siretNumbers.get(0), "00017", "", "1978-01-01", legalUnit);
    }
}
