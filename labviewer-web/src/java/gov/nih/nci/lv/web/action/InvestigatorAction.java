/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.web.action;

import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.lv.dao.InvestigatorDAO;
import gov.nih.nci.lv.dto.IntegrationHubDto;
import gov.nih.nci.lv.dto.InvestigatorDto;
import gov.nih.nci.lv.hub.COPPAHub;
import gov.nih.nci.lv.util.LVConstants;
import gov.nih.nci.lv.util.LVException;

import java.util.List;

/**
 * Investigator action class.
 * @author Naveen Amiruddin
 *
 */
public class InvestigatorAction extends LabViewerAction {

    /**
     *
     * @return Success
     * @throws Exception on error
     */
    public String view() throws Exception {
        setStudyProtocolInfo();
        List<InvestigatorDto> dtos = new InvestigatorDAO().getByStudyProtocol(createProtocolObj());
        if (!isSuiteMode()) {
            getRequest().setAttribute("results", dtos);
            LOG.debug("Not synchronized with COPPA due to Standalone mode");
            return SUCCESS;
        }
        boolean dataSyn = false;
        boolean coppaSync = false;
        for (InvestigatorDto dto : dtos) {
            coppaSync = syncCoppaPerson(dto , dto.getNciIdentifier());
            if (!dataSyn) {
                dataSyn = coppaSync;
            }
        }
        if (dataSyn) {
            // call the dao, since the db has been updated
            //@todo : better way would be to update the dto
            dtos = new InvestigatorDAO().getByStudyProtocol(createProtocolObj());
            LOG.debug("Retriving the data again, since its updated from coppa");
        }
        getRequest().setAttribute("results", dtos);
        return SUCCESS;
    }

    private boolean syncCoppaPerson(InvestigatorDto dto , String ctepId) throws LVException {
        IntegrationHubDto iHubDto = getHubDto();
        iHubDto.setCoppaEntityId(setCoppaEntityId(ctepId, LVConstants.CTEP_PERSON_IDENTIFIER_ROOT));
        iHubDto.setTarget(LVConstants.PERSON);
        iHubDto.setOperationName("getPersonByCTEPId");
        iHubDto.setServiceType("PERSON_BUSINESS_SERVICE");
        Person person = null;
        try {
            person =  new COPPAHub().invokeCoppaPerson(iHubDto);
        } catch (Exception e) {
            LOG.error("Error while calling COPPA Person", e);
        }
        boolean syn = false;
        if (person != null) {
            LOG.info("Data has been updated for ctep id " + ctepId);
            //@todo : show error message in the screen
            new InvestigatorDAO().update(dto, person);
            syn = true;
        }
        return syn;
    }
}
