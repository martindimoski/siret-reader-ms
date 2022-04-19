package com.siretreader.ms.api.establishment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class EstablishmentResponseDTO {

    private String id;

    private String siret;

    private String nic;

    private String fullAddress;

    private String creationDate;

    private String tvaNumber;
}
