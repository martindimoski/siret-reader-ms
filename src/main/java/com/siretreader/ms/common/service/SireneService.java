package com.siretreader.ms.common.service;

import com.siretreader.ms.common.model.entity.Establishment;
import com.siretreader.ms.common.model.entity.EstablishmentResponse;
import com.siretreader.ms.common.properties.SiretReaderProperties;
import com.siretreader.ms.exceptions.SirenEstablishmentNotFoundException;
import com.siretreader.ms.exceptions.SireneServerDownException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class SireneService {

    private final SiretReaderProperties properties;

    /**
     * Gets the establishments from siren api one by one, by given siret numbers from application properties
     *
     * @return list of establishments received from siren
     * @throws SireneServerDownException           if siren server is down
     * @throws SirenEstablishmentNotFoundException if establishment with requested siret doesn't exist
     */
    public List<Establishment> getEstablishmentsFromSiren() {
        var establishments = new ArrayList<Establishment>();
        var webClient = WebClient.builder().filter(errorHandler()).build();
        properties.getSiretNumbers().forEach(siret -> {
            var response = webClient.get().uri(URI.create(String.format("%s%s", properties.getUrl(), siret)))
                    .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(EstablishmentResponse.class).share()
                    .block();
            establishments.add(Objects.requireNonNull(response).getEstablishment());
        });
        return establishments;
    }

    /**
     * Custom error handler for the web client
     */
    private static ExchangeFilterFunction errorHandler() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().is5xxServerError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(SireneServerDownException::new));
            } else if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                LOGGER.info(clientResponse.toString());
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(SirenEstablishmentNotFoundException::new));
            } else if (clientResponse.statusCode().equals(HttpStatus.TOO_MANY_REQUESTS)) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return Mono.just(clientResponse);
        });
    }
}
