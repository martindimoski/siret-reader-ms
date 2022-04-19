package com.siretreader.ms.api.establishment;

import com.siretreader.ms.common.model.entity.Establishment;
import java.util.List;
import org.mapstruct.*;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EstablishmentMapper {

    /**
     * Maps establishment object in data transfer object
     *
     * @param establishment the  object to be mapped
     * @return data transfer object
     */
    @Mapping(target = "tvaNumber", source = "establishment.legalUnit.tvaNumber")
    EstablishmentResponseDTO toEstablishmentResponse(Establishment establishment);

    /**
     * Maps list of establishment objects in the list of data transfer objects
     *
     * @param establishments the list of establishments to be mapped
     * @return list of data transfer objects
     */
    List<EstablishmentResponseDTO> toListEstablishmentResponse(List<Establishment> establishments);
}
