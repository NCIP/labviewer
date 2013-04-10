/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.web.action;

import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.lv.dao.HealthcareSiteDAO;
import gov.nih.nci.lv.dto.HealthcareSiteDto;
import gov.nih.nci.lv.dto.IntegrationHubDto;
import gov.nih.nci.lv.hub.COPPAHub;
import gov.nih.nci.lv.util.LVConstants;
import gov.nih.nci.lv.util.LVException;

import java.util.List;

/**
 * Healthcaresite action class.
 * @author Naveen Amiruddin
 *
 */
public class HealthcareSiteAction extends LabViewerAction {

    /**
     *
     * @return Success
     * @throws Exception on error
     */
    public String view() throws Exception {
        setStudyProtocolInfo();
        List<HealthcareSiteDto> dtos = new HealthcareSiteDAO().getByStudyProtocol(createProtocolObj());
        if (!isSuiteMode()) {
            getRequest().setAttribute("results", dtos);
            LOG.debug("Not synchronized with COPPA due to Standalone mode");
            return SUCCESS;
        }
        boolean dataSyn = false;
        boolean coppaSync = false;
        for (HealthcareSiteDto dto : dtos) {
            coppaSync = syncCoppaOrganization(dto , dto.getNciInstituteCode());
            if (!dataSyn) {
                dataSyn = coppaSync;
            }
        }
        if (dataSyn) {
            // call the dao, since the db has been updated
            //@todo : better way would be to update the dto
            dtos = new HealthcareSiteDAO().getByStudyProtocol(createProtocolObj());
            LOG.debug("Retriving the organization data again, since its updated from coppa");
        }
        getRequest().setAttribute("results", dtos);
        return SUCCESS;
    }

    private boolean syncCoppaOrganization(HealthcareSiteDto dto , String ctepId) throws LVException {
        IntegrationHubDto iHubDto = getHubDto();
        iHubDto.setCoppaEntityId(setCoppaEntityId(ctepId, LVConstants.CTEP_ORG_IDENTIFIER_ROOT));
        iHubDto.setTarget(LVConstants.ORGANIZATION);
        iHubDto.setOperationName("getOrganizationByCTEPId");
        iHubDto.setServiceType("ORGANIZATION_BUSINESS_SERVICE");
        Organization organization = null;
        try {
            organization =  new COPPAHub().invokeCoppaOrganization(iHubDto);
        } catch (Exception e) {
            LOG.error("Error while calling COPPA Organization", e);
          //@todo : show error message in the screen
        }
        boolean syn = false;
        if (organization != null) {
            LOG.info("Data has been updated for organization ctep id " + ctepId);
            new HealthcareSiteDAO().update(dto, organization);
            syn = true;
        }
        return syn;
    }
}
