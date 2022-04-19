package com.siretreader.ms.api.establishment;

import com.siretreader.ms.constants.Endpoints;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Endpoints.ESTABLISHMENTS_ENDPOINT)
@RequiredArgsConstructor
public class EstablishmentController {

    public final EstablishmentMapper establishmentMapper;
    public final EstablishmentService establishmentService;

    /**
     * Gets requested establishment by the given siret number from the local storage (csv file)
     *
     * @param siret the unique number for every establishment
     * @return data transfer object for the requested establishment
     */
    @GetMapping("/{siret}")
    public EstablishmentResponseDTO getEstablishment(@PathVariable String siret) {
        return establishmentMapper.toEstablishmentResponse(establishmentService.getEstablishment(siret));
    }

    /**
     * Gets all establishments from the local storage (csv file)
     *
     * @return list of data transfer objects for the establishments
     */
    @GetMapping
    public List<EstablishmentResponseDTO> getEstablishments() {
        return establishmentMapper.toListEstablishmentResponse(establishmentService.getEstablishments());
    }

    /**
     * Gets the establishments from siren and saves in the csv file
     */
    @PutMapping
    public void updateEstablishment() {
        establishmentService.updateEstablishments();
    }
}
