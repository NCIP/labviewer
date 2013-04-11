/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.lv.convert;

import gov.nih.nci.lv.domain.Investigator;
import gov.nih.nci.lv.dto.InvestigatorDto;


/**
 *
 * @author NAmiruddin
 *
 */
public class InvestigatorConverter extends AbstractConverter<InvestigatorDto, Investigator> {

    @Override
    public Investigator convertToBO(InvestigatorDto dto) {
        Investigator inv = new Investigator();
        inv.setId(dto.getId());
        inv.setFirstName(dto.getFirstName());
        inv.setLastName(dto.getLastName());
        inv.setCity(dto.getCity());
        inv.setCountryCode(dto.getCountryCode());
        inv.setNciIdentifier(dto.getNciIdentifier());
        inv.setPostalCode(dto.getPostalCode());
        inv.setStreetAddress(dto.getStreetAddress());
        inv.setTelecomAddress(dto.getTelecomAddress());
        inv.setCtomUpdateDate(dto.getCtomUpdateDate());
        return inv;
    }

    @Override
    public InvestigatorDto convertToDTO(Investigator bo) {
        InvestigatorDto dto = new InvestigatorDto();
        dto.setId(bo.getId());
        dto.setFirstName(bo.getFirstName());
        dto.setLastName(bo.getLastName());
        dto.setCity(bo.getCity());
        dto.setCountryCode(bo.getCountryCode());
        dto.setNciIdentifier(bo.getNciIdentifier());
        dto.setPostalCode(bo.getPostalCode());
        dto.setStreetAddress(bo.getStreetAddress());
        dto.setTelecomAddress(bo.getTelecomAddress());
        dto.setCtomUpdateDate(bo.getCtomUpdateDate());
        return dto;

    }

}
