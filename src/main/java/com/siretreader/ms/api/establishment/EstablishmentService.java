package com.siretreader.ms.api.establishment;

import com.siretreader.ms.common.model.entity.Establishment;
import com.siretreader.ms.common.service.CSVService;
import com.siretreader.ms.common.service.SirenService;
import com.siretreader.ms.exceptions.CSVFileNotFoundException;
import com.siretreader.ms.exceptions.EstablishmentNotFoundException;
import com.siretreader.ms.exceptions.SireneServerDownException;
import com.siretreader.ms.exceptions.SirenEstablishmentNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstablishmentService {

    private final SirenService sirenService;
    private final CSVService createCSVFile;

    /**
     * Gets the establishments from siren and saves in the csv file
     *
     * @throws SireneServerDownException if siren server is down
     * @throws SirenEstablishmentNotFoundException if establishment with requested siret doesn't exist
     */
    public void updateEstablishments() {
        var establishments = sirenService.getEstablishmentsFromSiren();
        createCSVFile.createEstablishmentsCSVFile(establishments);
    }

    /**
     * Gets requested establishment by the given siret number from the local storage (csv file)
     *
     * @param siret the unique number for every establishment
     * @return requested establishment
     * @throws CSVFileNotFoundException if the csv file with establishments have not been updated yet
     */
    public Establishment getEstablishment(final String siret) {
        return createCSVFile.readEstablishmentsFromCSVFile().stream()
                .filter(establishment -> establishment.getSiret().equals(siret)).findFirst()
                .orElseThrow(EstablishmentNotFoundException::new);
    }

    /**
     * Gets all establishments from the local storage (csv file)
     *
     * @return list with all establishments
     * @throws CSVFileNotFoundException if the csv file with establishments have not been updated yet
     */
    public List<Establishment> getEstablishments() {
        return createCSVFile.readEstablishmentsFromCSVFile();
    }
}
