/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.lv.hub;

import gov.nih.nci.lv.dto.IntegrationHubDto;
import gov.nih.nci.lv.dto.LabSearchDto;
import gov.nih.nci.lv.util.LVException;

import java.util.List;

/**
 *
 * @author Naveen Amiruddin
 *
 */
public class CAERSHub extends LabHub {


    /**
     *
     * @param labSearchDto criteria
     * @param labs labs
     * @param hubDto hubdto
     * @throws LVException on error
     */
    public void loadLabs(LabSearchDto labSearchDto , List<LabSearchDto> labs , IntegrationHubDto hubDto)
        throws LVException  {
        hubDto.setTarget("CAERS");
        hubDto.setQName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain.loadlabs");
        hubDto.setQRequest("LoadLabsRequest");
        hubDto.setMessageXml("caAERSmessage.xml");
        hubDto.setServiceName("CTODS");
        hubDto.setServiceType("LAB_BASED_AE");
        hubDto.setExternalIdentifier("CTODS");
        super.invokeHub(labSearchDto, labs, hubDto);
    }



}
