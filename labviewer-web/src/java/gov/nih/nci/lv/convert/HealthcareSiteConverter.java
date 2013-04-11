/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.lv.convert;

import gov.nih.nci.lv.domain.HealthcareSite;
import gov.nih.nci.lv.dto.HealthcareSiteDto;

/**
 *
 * @author NAmiruddin
 *
 */
public class HealthcareSiteConverter extends AbstractConverter<HealthcareSiteDto, HealthcareSite> {

    @Override
    public HealthcareSite convertToBO(HealthcareSiteDto dto) {
        HealthcareSite hc = new HealthcareSite();
        hc.setId(dto.getId());
        hc.setName(dto.getName());
        hc.setCity(dto.getCity());
        hc.setCountryCode(dto.getCountryCode());
        hc.setNciInstituteCode(dto.getNciInstituteCode());
        hc.setPostalCode(dto.getPostalCode());
        hc.setStreetAddress(dto.getStreetAddress());
        hc.setTelecomAddress(dto.getTelecomAddress());
        hc.setTelecomAddress(dto.getTelecomAddress());
        hc.setCtomUpdateDate(dto.getCtomUpdateDate());
        return hc;
    }

    @Override
    public HealthcareSiteDto convertToDTO(HealthcareSite bo) {
        HealthcareSiteDto dto = new HealthcareSiteDto();
        dto.setId(bo.getId());
        dto.setName(bo.getName());
        dto.setCity(bo.getCity());
        dto.setCountryCode(bo.getCountryCode());
        dto.setNciInstituteCode(bo.getNciInstituteCode());
        dto.setPostalCode(bo.getPostalCode());
        dto.setStreetAddress(bo.getStreetAddress());
        dto.setTelecomAddress(bo.getTelecomAddress());
        dto.setCtomUpdateDate(bo.getCtomUpdateDate());
        return dto;
    }

}
