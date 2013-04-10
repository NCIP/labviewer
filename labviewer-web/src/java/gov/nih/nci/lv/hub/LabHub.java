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
 * Generic class for loading labs.
 * @author Naveen Amiruddin
 *
 */
public abstract class LabHub extends IntegrationHub {

    /**
     *
     * @param labSearchDto labSearchDto
     * @param labs labs
     * @param hubDto hubDto
     * @throws LVException on error
     */
    public abstract void loadLabs(LabSearchDto labSearchDto , List<LabSearchDto> labs , IntegrationHubDto hubDto)
    throws LVException;

}
