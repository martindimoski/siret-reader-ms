package com.siretreader.ms.common.service;

import com.siretreader.ms.common.model.entity.Establishment;
import com.siretreader.ms.common.model.entity.LegalUnit;
import com.siretreader.ms.exceptions.CSVFileNotFoundException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CSVService {

    public static final String JAVA_USER_FOLDER_KEY = "user.dir";
    public static final String FILENAME = "establishments.csv";

    public enum EstablishmentsHeader {
        Id, Siret, Nic, Full_Address, Creation_Date, Legal_Unit
    }

    /**
     * Creates or recreates the csv file with the establishments in the java user folder
     *
     * @param establishments the establishments to be saved
     */
    public void createEstablishmentsCSVFile(final List<Establishment> establishments) {
        try {
            var outputStream = Files.newOutputStream(
                    Paths.get(System.getProperty(JAVA_USER_FOLDER_KEY), FILENAME));
            var printer = new CSVPrinter(new BufferedWriter(new OutputStreamWriter(outputStream)),
                    CSVFormat.DEFAULT.builder().setHeader(EstablishmentsHeader.class).build());
            establishments.forEach(
                    e -> {
                        try {
                            printer.printRecord(e.getId(), e.getSiret(), e.getNic(), e.getFullAddress(),
                                    e.getCreationDate(), e.getLegalUnit().getTvaNumber());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
            printer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the establishments from the csv file
     *
     * @return list of establishments
     * @throws CSVFileNotFoundException if the csv file with establishments have not been updated yet
     */
    public List<Establishment> readEstablishmentsFromCSVFile() {
        var establishments = new ArrayList<Establishment>();
        try {
            var inputStream =
                    Files.newInputStream(Paths.get(System.getProperty(JAVA_USER_FOLDER_KEY), FILENAME));
            var csvParser = new CSVParser(new BufferedReader(new InputStreamReader(inputStream)),
                    CSVFormat.DEFAULT.builder().setHeader(EstablishmentsHeader.class).setSkipHeaderRecord(true)
                            .build());
            csvParser.getRecords().forEach(record -> establishments.add(
                    new Establishment(record.get(EstablishmentsHeader.Id), record.get(EstablishmentsHeader.Siret),
                            record.get(EstablishmentsHeader.Nic), record.get(EstablishmentsHeader.Full_Address),
                            record.get(EstablishmentsHeader.Creation_Date),
                            new LegalUnit(record.get(EstablishmentsHeader.Legal_Unit)))));
        } catch (NoSuchFileException e) {
            throw new CSVFileNotFoundException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return establishments;
    }
}
