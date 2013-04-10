/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.web.action;

import gov.nih.nci.lv.dao.StudySearchDAO;
import gov.nih.nci.lv.dto.IntegrationHubDto;
import gov.nih.nci.lv.dto.StudySearchDto;
import gov.nih.nci.lv.hub.COPPAHub;
import gov.nih.nci.lv.util.LVConstants;
import gov.nih.nci.lv.util.LVUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.iso._21090.II;

/**
 * Study protocol Action class for search.
 * @author NAmiruddin
 *
 */
public class StudyProtocolAction extends LabViewerAction {
    private static final long serialVersionUID = 1234573645L;

    StudySearchDto ssDto = new StudySearchDto();
    List<StudySearchDto> results = new ArrayList<StudySearchDto>();

    /**
     *
     * @return Success
     * @throws Exception on error
     */
    public String list() throws Exception {

        if (StringUtils.isEmpty(ssDto.getNciIdentifier()) && StringUtils.isEmpty(ssDto.getShortTitle())) {
            setAttribute(LVConstants.FAILURE_MESSAGE, "Minimum one criteria is requured to do the search");
            return ERROR;
        }
        setAttribute("results", new StudySearchDAO().search(ssDto));
        if (getStudyProtocolIdFromSession() != getStudyProtocolIdentifier()) {
            // user has changed the study, set null for participant
            setSession(LVConstants.STUDY_PART_SEARCH_DTO, null);
        }
        return SUCCESS;
    }

    /**
     *
     * @return Success
     * @throws Exception on error
     */
    public String view() throws Exception {
        setStudyProtocolInfo();
        IntegrationHubDto iHubDto = getHubDto();
        setStudyIdentifier(iHubDto);
        II ii = iHubDto.getCoppaIi();
        if (!LVUtils.isIINull(ii) && ii.getExtension().startsWith(LVConstants.STUDY_ID_PREFIX) && isSuiteMode()) {
            // this is coppa study, perform coppa search
            LOG.debug("Updating the coppa study");
                new StudySearchDAO().update(new COPPAHub().invokeCoppaStudy(iHubDto));
        }
        setStudyProtocolInfo();
        return SUCCESS;
    }

    /**
     *
     * @return ssDto
     */
    public StudySearchDto getSsDto() {
        return ssDto;
    }
    /**
     *
     * @param ssDto ssDto
     */
    public void setSsDto(StudySearchDto ssDto) {
        this.ssDto = ssDto;
    }
    /**
     *
     * @return results
     */
    public List<StudySearchDto> getResults() {
        return results;
    }
    /**
     *
     * @param results results
     */
    public void setResults(List<StudySearchDto> results) {
        this.results = results;
    }

}
