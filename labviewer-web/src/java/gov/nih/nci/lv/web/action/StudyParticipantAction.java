/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.web.action;

import gov.nih.nci.lv.dao.StudyParticipantSearchDOA;
import gov.nih.nci.lv.dao.StudySearchDAO;
import gov.nih.nci.lv.dto.StudyParticipantSearchDto;
import gov.nih.nci.lv.dto.StudySearchDto;
import gov.nih.nci.lv.util.LVConstants;

/**
 * Action class for study participant.
 * @author Naveen Amiruddin
 *
 */
public class StudyParticipantAction extends LabViewerAction {
    private StudyParticipantSearchDto spsDto = new StudyParticipantSearchDto();
    /**
     *
     * @return Success
     * @throws Exception on error
     */
    public String list() throws Exception {
        // retrieve the protocol and set it in the session to be used later
        setSession(LVConstants.STUDY_SEARCH_DTO,
                new StudySearchDAO().search(new StudySearchDto(getStudyProtocolId(), getUserName())).get(0));
        // remove the study participation
        spsDto.setStudyProtocolId(getStudyProtocolId());
        spsDto.setUserName(getUserName());
        setAttribute(LVConstants.RESULTS, new StudyParticipantSearchDOA().search(spsDto));
        setAttribute(LVConstants.TOPIC, "participant");
        return SUCCESS;

    }

    /**
     *
     * @return spsDto
     */
     public StudyParticipantSearchDto getSpsDto() {
         return spsDto;
     }
     /**
      *
      * @param spsDto spsDto
      */
     public void setSpsDto(StudyParticipantSearchDto spsDto) {
         this.spsDto = spsDto;
     }


}
