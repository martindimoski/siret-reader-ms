package com.siretreader.ms.common.service;

import static org.junit.jupiter.api.Assertions.*;

import com.siretreader.ms.common.model.entity.Establishment;
import com.siretreader.ms.common.model.entity.LegalUnit;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CSVServiceTest {

    @InjectMocks
    CSVService csvService;

    @Test
    void createAndReadEstablishmentsFromCSVFileTest() {
        final var mockedEstablishments = createMockedEstablishmentList();

        csvService.createEstablishmentsCSVFile(mockedEstablishments);
        final var establishments = csvService.readEstablishmentsFromCSVFile();

        assertAll(
                () -> assertEquals(establishments.size(), mockedEstablishments.size()),
                () -> assertEquals(establishments.get(0).getId(), mockedEstablishments.get(0).getId()),
                () -> assertEquals(establishments.get(0).getSiret(), mockedEstablishments.get(0).getSiret()),
                () -> assertEquals(establishments.get(0).getNic(), mockedEstablishments.get(0).getNic()),
                () -> assertEquals(establishments.get(0).getFullAddress(), mockedEstablishments.get(0).getFullAddress()),
                () -> assertEquals(establishments.get(0).getCreationDate(), mockedEstablishments.get(0).getCreationDate()),
                () -> assertEquals(establishments.get(0).getLegalUnit().getTvaNumber(), mockedEstablishments.get(0).getLegalUnit().getTvaNumber())
        );
    }

    private List<Establishment> createMockedEstablishmentList() {
        final var legalUnit = new LegalUnit("FR96313029795");
        final var mockedEstablishment =
                new Establishment("1557207089", "31302979500017", "00017", "", "1978-01-01", legalUnit);
        return List.of(mockedEstablishment);
    }
}
