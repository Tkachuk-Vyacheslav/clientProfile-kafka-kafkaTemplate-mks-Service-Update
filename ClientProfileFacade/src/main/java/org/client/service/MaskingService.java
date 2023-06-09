package org.client.service;


import org.client.common.dto.*;
import org.client.dto.shortIndividual.IndividualClientDto;
import org.client.dto.shortIndividual.IndividualDocStatusDto;
import org.client.dto.shortIndividual.IndividualShortDto;
import org.client.dto.shortIndividual.RFPassportShortDto;

public interface MaskingService {
    String maskTextInfo(String text);

    IndividualDto maskIndividual(IndividualDto individual);

    IndividualShortDto maskIndividual(IndividualShortDto individual);

    IndividualDocStatusDto maskIndividual(IndividualDocStatusDto individual);

    IndividualClientDto maskIndividual(IndividualClientDto individual);

    String maskSurname(String surname);

    String maskFullName(String fullname, String surname);

    ContactMediumDto maskContacts(ContactMediumDto contactMedium);

    RFPassportDto maskRFPassport(RFPassportDto passport);


    RFPassportShortDto maskRFPassport(RFPassportShortDto passport);
}
