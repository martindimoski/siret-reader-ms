package com.siretreader.ms.common.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Establishment {

    private String id;

    private String siret;

    private String nic;

    @JsonProperty("unite_legale.complement_adresse")
    private String fullAddress;

    @JsonProperty("date_creation")
    private String creationDate;

    @JsonProperty("unite_legale")
    private LegalUnit legalUnit;
}
