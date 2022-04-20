package com.siretreader.ms.api.establishment;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.siretreader.ms.common.model.entity.Establishment;
import com.siretreader.ms.common.model.entity.LegalUnit;
import com.siretreader.ms.common.service.CSVService;
import com.siretreader.ms.common.service.SireneService;
import com.siretreader.ms.exceptions.CSVFileNotFoundException;
import com.siretreader.ms.exceptions.EstablishmentNotFoundException;
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
    SireneService sireneService;

    @Test
    void getEstablishmentTest() {
        final var siretNumber = "31302979500017";
        final var mockedEstablishments = createMockedEstablishmentList();
        when(csvService.readEstablishmentsFromCSVFile()).thenReturn(mockedEstablishments);

        final var establishment = establishmentService.getEstablishment(siretNumber);

        assertAll(
                () -> assertEquals(establishment.getId(), mockedEstablishments.get(0).getId()),
                () -> assertEquals(establishment.getSiret(), mockedEstablishments.get(0).getSiret()),
                () -> assertEquals(establishment.getNic(), mockedEstablishments.get(0).getNic()),
                () -> assertEquals(establishment.getFullAddress(), mockedEstablishments.get(0).getFullAddress()),
                () -> assertEquals(establishment.getCreationDate(), mockedEstablishments.get(0).getCreationDate()),
                () -> assertEquals(establishment.getLegalUnit().getTvaNumber(),
                        mockedEstablishments.get(0).getLegalUnit().getTvaNumber())
        );
    }

    @Test
    void getNotExistingEstablishmentThrowsException() {
        final var siretNumber = "31302979500017";
        when(csvService.readEstablishmentsFromCSVFile()).thenThrow(new EstablishmentNotFoundException());

        assertThrows(EstablishmentNotFoundException.class, () -> establishmentService.getEstablishment(siretNumber));
    }

    @Test
    void getEstablishmentBeforeUpdatingTheCSVFileThrowsException() {
        final var siretNumber = "31302979500017";
        when(csvService.readEstablishmentsFromCSVFile()).thenThrow(new CSVFileNotFoundException());

        assertThrows(CSVFileNotFoundException.class, () -> establishmentService.getEstablishment(siretNumber));
    }

    @Test
    void getEstablishmentsTest() {
        final var mockedEstablishments = createMockedEstablishmentList();
        when(csvService.readEstablishmentsFromCSVFile()).thenReturn(mockedEstablishments);

        final var establishments = establishmentService.getEstablishments();

        assertAll(
                () -> assertEquals(establishments.size(), mockedEstablishments.size()),
                () -> assertEquals(establishments.get(0).getId(), mockedEstablishments.get(0).getId()),
                () -> assertEquals(establishments.get(0).getSiret(), mockedEstablishments.get(0).getSiret()),
                () -> assertEquals(establishments.get(0).getNic(), mockedEstablishments.get(0).getNic()),
                () -> assertEquals(establishments.get(0).getFullAddress(),
                        mockedEstablishments.get(0).getFullAddress()),
                () -> assertEquals(establishments.get(0).getCreationDate(),
                        mockedEstablishments.get(0).getCreationDate()),
                () -> assertEquals(establishments.get(0).getLegalUnit().getTvaNumber(),
                        mockedEstablishments.get(0).getLegalUnit().getTvaNumber())
        );
    }

    @Test
    void getEstablishmentsBeforeUpdatingTheCSVFileThrowsException() {
        when(csvService.readEstablishmentsFromCSVFile()).thenThrow(new CSVFileNotFoundException());

        assertThrows(CSVFileNotFoundException.class, () -> establishmentService.getEstablishments());
    }

    @Test
    void updateEstablishments() {
        final var mockedEstablishments = createMockedEstablishmentList();
        when(sireneService.getEstablishmentsFromSiren()).thenReturn(mockedEstablishments);
        doNothing().when(csvService).createEstablishmentsCSVFile(mockedEstablishments);

        establishmentService.updateEstablishments();
    }

    private List<Establishment> createMockedEstablishmentList() {
        final var legalUnit = new LegalUnit("FR96313029795");
        final var mockedEstablishment =
                new Establishment("1557207089", "31302979500017", "00017", "", "1978-01-01", legalUnit);
        return List.of(mockedEstablishment);
    }
}
