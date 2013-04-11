/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.web.action;

import gov.nih.nci.lv.dao.LabSearchDAO;
import gov.nih.nci.lv.dao.StudyParticipantSearchDOA;
import gov.nih.nci.lv.domain.Identifier;
import gov.nih.nci.lv.dto.IntegrationHubDto;
import gov.nih.nci.lv.dto.LabSearchDto;
import gov.nih.nci.lv.dto.StudyParticipantSearchDto;
import gov.nih.nci.lv.hub.C3DHub;
import gov.nih.nci.lv.hub.CAERSHub;
import gov.nih.nci.lv.hub.LabHub;
import gov.nih.nci.lv.util.LVConstants;
import gov.nih.nci.lv.util.LVException;
import gov.nih.nci.lv.util.LVUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Labs Action class.
 * @author Naveen Amiruddin
 *
 */
public class LabAction extends LabViewerAction {
    LabSearchDto labSearhDto = new LabSearchDto();
    List<LabSearchDto> labResults = new ArrayList<LabSearchDto>();
    String studySubjectGridId = null;
    private static final long serialVersionUID = 1234573645L;
    private static Logger logger = Logger.getLogger(LabAction.class);
    /**
     *
     * @return Success
     * @throws Exception on error
     */
    public String list() throws Exception {
        Long studyPartId = null;
        if (studySubjectGridId != null) {
            labViewerSetup();
            setUserInfoInSession();
            setSession(LVConstants.STUDY_ACCESS, "yes");
            logger.debug(" call from PSC/caAERS grid id = " + studySubjectGridId);
            logger.debug(" user id = " + getSessionAttr(LVConstants.USER_NAME));
            Identifier identifier =
                new StudyParticipantSearchDOA().getIdentiferByStudySubjectGridId(studySubjectGridId);
            setStudyProtocolId(identifier.getProtocol().getId());
            if (identifier != null) {
                logger.debug(" Identifier is =  " + identifier.getExtension());
            }
            setStudyProtocolInfo();
            if ((getSessionAttr(LVConstants.STUDY_SEARCH_DTO)) == null) {
                setAttribute(LVConstants.FAILURE_MESSAGE,
                        "User does not have access to the Study or Study does not exist");
                return LVConstants.PROTOCOL;
            }
            labSearhDto.setStudyParticipantId(identifier.getParticipant().getId());
            logger.debug("retrieved Protocol id = " + identifier.getProtocol().getId());
            logger.debug("retrieved Participant id = " + identifier.getParticipant().getId());
        }
        labSearhDto.setStudyProtocolId(getStudyProtocolId());
        if (labSearhDto.getStudyParticipantId() == null) {
            studyPartId = getStudyPartIdFromSession();
        } else if (labSearhDto.getStudyParticipantId() != null) {
            studyPartId = labSearhDto.getStudyParticipantId();
        }
        labSearhDto.setStudyParticipantId(studyPartId);
        List<StudyParticipantSearchDto> spsDto = new StudyParticipantSearchDOA().search(new StudyParticipantSearchDto(
                getStudyProtocolId(), studyPartId , getUserName()));
        if (spsDto.isEmpty()) {
            setAttribute(LVConstants.FAILURE_MESSAGE, "Subject not found for a study ");
            setSession(LVConstants.STUDY_PART_SEARCH_DTO, null);
            return LVConstants.PARTICIPANT;
        }
        setSession(LVConstants.STUDY_PART_SEARCH_DTO, spsDto.get(0));
        labResults = new LabSearchDAO().search(labSearhDto);
        setSession(LVConstants.LAB_RESULTS, labResults);
        setAttribute(LVConstants.TOPIC, "labs");
        return SUCCESS;
    }



    /**
     *
     * @return Success
     * @throws Exception on error
     */
    public String c3d() throws Exception {
        return loadLabs(new C3DHub() , LVConstants.C3D);
    }


    /**
     *
     * @return Success
     * @throws Exception on error
     */
    public String caers() throws Exception {
        return loadLabs(new CAERSHub(), LVConstants.CAAERS);
    }


    private String loadLabs(LabHub iHub , String target) throws Exception {
        labResults = (List<LabSearchDto>) getSessionAttr(LVConstants.LAB_RESULTS);
        IntegrationHubDto hubDto = super.getHubDto();
        hubDto.setHubUrl((String) getSessionAttr(LVConstants.HUB_URL));
        try {
            iHub.loadLabs(labSearhDto, labResults, hubDto);
            setAttribute(LVConstants.SUCCESS_MESSAGE, " Labs has been successfully submitted to " + target);
            List<Long> clinicalIds = LVUtils.convertStringToList(labSearhDto.getLabIds(), ",");
            new LabSearchDAO().saveLoadLabStatus(clinicalIds, target);
            labSearhDto.setLabIds(null);
            return list();
        } catch (LVException lve) {
            setAttribute(LVConstants.FAILURE_MESSAGE, lve.getMessage());
        }
        return SUCCESS;
    }
    /**
     *
     * @param labSearhDto labSearhDto
     */
    public void setLabSearhDto(LabSearchDto labSearhDto) {
        this.labSearhDto = labSearhDto;
    }

    /**
     *
     * @return labSearhDto
     */
    public LabSearchDto getLabSearhDto() {
        return labSearhDto;
    }

    /**
     *
     * @return labResults
     */
    public List<LabSearchDto> getLabResults() {
        return labResults;
    }

    /**
     *
     * @param labResults labResults
     */
    public void setLabResults(List<LabSearchDto> labResults) {
        this.labResults = labResults;
    }

    /**
     *
     * @return studySubjectGridId
     */
    public String getStudySubjectGridId() {
        return studySubjectGridId;
    }

    /**
     *
     * @param studySubjectGridId studySubjectGridId
     */
    public void setStudySubjectGridId(String studySubjectGridId) {
        this.studySubjectGridId = studySubjectGridId;
    }
}
