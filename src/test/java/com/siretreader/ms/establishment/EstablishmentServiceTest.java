package com.siretreader.ms.establishment;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.siretreader.ms.api.establishment.EstablishmentService;
import com.siretreader.ms.common.model.entity.Establishment;
import com.siretreader.ms.common.model.entity.LegalUnit;
import com.siretreader.ms.common.service.CSVService;
import com.siretreader.ms.common.service.SirenService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EstablishmentServiceTest {

    @InjectMocks
    EstablishmentService establishmentService;
    @Mock
    CSVService csvService;
    @Mock
    SirenService sirenService;

    @Test
    void getEstablishmentTest() {
        final var siretNumber = "31302979500017";
        final var mockedEstablishments = createMockedEstablishmentList();
        when(csvService.readEstablishmentsFromCSVFile()).thenReturn(mockedEstablishments);

        final var estblishment = establishmentService.getEstablishment(siretNumber);

        assertAll(
                () -> assertEquals(estblishment.getId(), mockedEstablishments.get(0).getId()),
                () -> assertEquals(estblishment.getSiret(), mockedEstablishments.get(0).getSiret()),
                () -> assertEquals(estblishment.getNic(), mockedEstablishments.get(0).getNic()),
                () -> assertEquals(estblishment.getFullAddress(), mockedEstablishments.get(0).getFullAddress()),
                () -> assertEquals(estblishment.getCreationDate(), mockedEstablishments.get(0).getCreationDate()),
                () -> assertEquals(estblishment.getLegalUnit().getTvaNumber(), mockedEstablishments.get(0).getLegalUnit().getTvaNumber())
        );
    }

    private List<Establishment> createMockedEstablishmentList() {
        final var legalUnit = new LegalUnit("FR96313029795");
        final var mockedEstablishment =
                new Establishment("1557207089", "31302979500017", "00017", "", "1978-01-01", legalUnit);
        return List.of(mockedEstablishment);
    }
}
