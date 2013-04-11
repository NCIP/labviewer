/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.web.action;

import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.lv.auth.LabViewerAuthorizationHelper;
import gov.nih.nci.lv.dao.StudySearchDAO;
import gov.nih.nci.lv.domain.Protocol;
import gov.nih.nci.lv.dto.IntegrationHubDto;
import gov.nih.nci.lv.dto.StudyParticipantSearchDto;
import gov.nih.nci.lv.dto.StudySearchDto;
import gov.nih.nci.lv.util.LVConstants;
import gov.nih.nci.lv.util.LVException;
import gov.nih.nci.lv.util.LVPropertyReader;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.II;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
/**
 *
 * @author NAmiruddin
 *
 */
public class LabViewerAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 1234573645L;
    private Long studyProtocolId = null;
    private Long studyParticipantId = null;
    private static Logger logger = Logger.getLogger(LabViewerAction.class);

    /**
     * {@inheritDoc}
     */
    public void prepare() {

    }
    /**
     * {@inheritDoc}
     */
    public String execute() throws Exception {
        return SUCCESS;
    }

    /**
    *
    * @return studyProtocolId
    */
   public Long getStudyProtocolId() {
       if (studyProtocolId == null) {
           // if the value is not, then it will be in session
           return getStudyProtocolIdFromSession();
       }
       return studyProtocolId;
   }

   /**
   *
   * @return studyProtocolId
   */
   Long getStudyProtocolIdentifier() {
      return studyProtocolId;
   }



   /**
   *
   * @return studyProtocolId
   */
    public Long getStudyProtocolIdFromSession() {
       StudySearchDto ssDto = (StudySearchDto) getSession().getAttribute(LVConstants.STUDY_SEARCH_DTO);
       return (ssDto != null ? ssDto.getId() : null);
   }
    /**
    *
    * @return studyProtocolId
    */
    public Long getStudyPartIdFromSession() {
        StudyParticipantSearchDto spsDto = (StudyParticipantSearchDto) getSession().getAttribute(
             LVConstants.STUDY_PART_SEARCH_DTO);
        return (spsDto != null ? spsDto.getId() : null);
    }
   /**
    *
    * @return protocol
    */
    Protocol createProtocolObj() {
       Protocol protocol = new Protocol();
       protocol.setId(studyProtocolId);
       return protocol;
    }
   /**
    *
    * @param studyProtocolId studyProtocolId
    */
   public void setStudyProtocolId(Long studyProtocolId) {
       this.studyProtocolId = studyProtocolId;
   }


   /**
    *
    * @return studyParticipantId
    */
    public Long getStudyParticipantId() {
        if (studyParticipantId == null) {
            return getStudyPartIdFromSession();
        }
        return studyParticipantId;
    }
    /**
     *
     * @param studyParticipantId studyParticipantId
     */
    public void setStudyParticipantId(Long studyParticipantId) {
        this.studyParticipantId = studyParticipantId;
    }

    String getUserName() {
        return (String) getSessionAttr(LVConstants.USER_NAME);
    }
    /**
     * return the current http session.
     * @return HttpSession
     */
    public HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }


    /**
     * return the current http session.
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    void setAttribute(String arg0, Object arg1) {
        ServletActionContext.getRequest().setAttribute(arg0, arg1);
    }
    void setSession(String arg0, Object arg1) {
        ServletActionContext.getRequest().getSession().setAttribute(arg0, arg1);
    }
    Object getSessionAttr(String arg0) {
        return getSession().getAttribute(arg0);
    }
    void setStudyProtocolInfo() throws Exception {
        //@todo : throw exception if it returned 0 result
        List<StudySearchDto> ssDtos = new StudySearchDAO().search(new StudySearchDto(studyProtocolId, getUserName()));
        StudySearchDto ssDto = null;
        if (!ssDtos.isEmpty()) {
            ssDto = ssDtos.get(0);
        }
        //@todo: this to be removed
        getRequest().setAttribute(LVConstants.STUDY_SEARCH_DTO, ssDto);
        getSession().setAttribute(LVConstants.STUDY_SEARCH_DTO, ssDto);
    }
    //@todo : throw exception when gridIdentifity is null
    void setUserInfoInSession() {
        String gridIDentity = (String) getSession().getAttribute(LVConstants.CAGRID_SSO_GRID_IDENTITY);
        logger.debug(" grid id = " + gridIDentity);
        if (gridIDentity != null) {
            int beginIndex = gridIDentity.lastIndexOf("=");
            int endIndex = gridIDentity.length();
            setSession(LVConstants.USER_NAME, gridIDentity.substring(beginIndex + 1, endIndex));
            setSession(LVConstants.USER_ROLES,
                    new LabViewerAuthorizationHelper().getUserRoles((String) getSessionAttr(LVConstants.USER_NAME)));
            logger.debug(" setting user info in session for " + gridIDentity.substring(beginIndex + 1, endIndex));
        }
    }

    /**
     * this method will be called when coming from login using csm.
     * @throws LVException
     */
    void setUserInfoInSession(String username) throws LVException {
        setDefaults();
        setSession(LVConstants.USER_NAME, username);
        setSession(LVConstants.USER_ROLES,
                new LabViewerAuthorizationHelper().getUserRoles((String) getSessionAttr(LVConstants.USER_NAME)));
        enableMenu();
    }

    private void setDefaults() throws LVException {
        setSession(LVConstants.ADMIN_ACCESS, "no");
        setSession(LVConstants.STUDY_ACCESS, "no");
        setSession(LVConstants.ALLOW_ACCESS, "no");
        setSession(LVConstants.CAAERS_ACCESS, "no");
        setProperty(LVPropertyReader.getProperty());
    }

    void setProperty(Properties prop) {
        setSession(LVConstants.VERSION, prop.getProperty(LVConstants.VERSION));
        setSession("help_link", prop.getProperty(LVConstants.HELP_LINK));
        setSession(LVConstants.CAAERS_URL, prop.getProperty(LVConstants.CAAERS_URL));
        setSession(LVConstants.C3D_URL, prop.getProperty(LVConstants.C3D_URL));
        setSession(LVConstants.HUB_URL, prop.getProperty(LVConstants.HUB_URL));
        setSession(LVConstants.WEBSSO_ENABLED, Boolean.parseBoolean(prop.getProperty(LVConstants.WEBSSO_ENABLED)));
    }
    /**
     * this method to be called after calling setuserInfoInSession.
     */
    private void enableMenu() {
        Set<SuiteRole> userRoles = (Set<SuiteRole>) getSessionAttr(LVConstants.USER_ROLES);
        // todo : throw error on null
        if (userRoles != null) {
            if (userRoles.contains(SuiteRole.SYSTEM_ADMINISTRATOR)) {
                setSession(LVConstants.ADMIN_ACCESS, "yes");
                setSession(LVConstants.ALLOW_ACCESS, "yes");
            }
            if (userRoles.contains(SuiteRole.LAB_DATA_USER)) {
                setSession(LVConstants.STUDY_ACCESS, "yes");
                setSession(LVConstants.ALLOW_ACCESS, "yes");
            }
            if (userRoles.contains(SuiteRole.AE_REPORTER)) {
                setSession(LVConstants.CAAERS_ACCESS, "yes");
            }
        }
    }
    void labViewerSetup() throws LVException {
        setDefaults();
        setUserInfoInSession();
        enableMenu();
    }
    IntegrationHubDto getHubDto() {
        IntegrationHubDto hubDto = new IntegrationHubDto();
        hubDto.setCredentialEpr((String) getSessionAttr(LVConstants.CAGRID_SSO_DELEGATION_SERVICE_EPR));
        hubDto.setGlobusCredential((GlobusCredential) getSessionAttr(LVConstants.CAGRID_SSO_GRID_CREDENTIAL));
        hubDto.setUserName((String) getSessionAttr(LVConstants.USER_NAME));
        hubDto.setStudyProtocolExtn(((StudySearchDto) getSessionAttr(LVConstants.STUDY_SEARCH_DTO)).getNciIdentifier());
        hubDto.setHubUrl((String) getSessionAttr(LVConstants.HUB_URL));
        hubDto.setExternalIdentifier("CTODS");
        return hubDto;
    }

    void setStudyIdentifier(IntegrationHubDto iHubDto) {
        II assignedIdentifier = new II();
        StudySearchDto ssDto = (StudySearchDto) getSessionAttr(LVConstants.STUDY_SEARCH_DTO);
        assignedIdentifier.setRoot(LVConstants.STUDY_PROTOCOL_ROOT);
        assignedIdentifier.setExtension(ssDto.getNciIdentifier());
        iHubDto.setCoppaIi(assignedIdentifier);

    }

    Id setCoppaEntityId(String extn , String root) {
        Id identifier = new Id();
        identifier.setRoot(root);
        identifier.setExtension(extn);
        return identifier;

    }
    /**
     * true means suite mode, false means standalone mode.
     * @return
     * @throws LVException
     */
    boolean isSuiteMode() throws LVException {
        return  Boolean.parseBoolean(LVPropertyReader.getPropertyValue(LVConstants.WEBSSO_ENABLED));
    }
}
