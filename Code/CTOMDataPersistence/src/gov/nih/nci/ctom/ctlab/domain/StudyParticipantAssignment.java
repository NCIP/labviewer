package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class StudyParticipantAssignment {

	private Long id;
	private Long studyPartId;
	private String  studyPartIdOrig = null;
	private String armId;
	private String subGroupCode=null;
	private Date informedConsetFrmSignedDt;
	private String informedConsetFrmSignedDtOrg;
	private Long enrollAge;
	private String enrollAgeOrg;
	private Long studyAgentDoseLevel;
	private String studyAgentDoseLevelOrg;
	private String studyAgentDoseLevelUOMCD;
	private Date offStudyDt;
	private String offStudyDtOrg;
	private String offStudyResCode;
	private String offStudyResOthTxt;
	private String type;
	private Long participantId;
	private Long studySiteId;
	private int securityKey;
	private String source;
	private Date srcExtractDt;
	private Date ctomInsertDt;
	private Date ctomUpdateDt;
	private Activity activity=null;
	private Participant participant=null;
	
	
	/**
	 * @return the studyPartId
	 */
	public Long getStudyPartId() {
		return studyPartId;
	}
	/**
	 * @param studyPartId the studyPartId to set
	 */
	public void setStudyPartId(Long studyPartId) {
		this.studyPartId = studyPartId;
	}
	/**
	 * @return the armId
	 */
	public String getArmId() {
		return armId;
	}
	/**
	 * @param armId the armId to set
	 */
	public void setArmId(String armId) {
		this.armId = armId;
	}
	/**
	 * @return the subGroupCode
	 */
	public String getSubGroupCode() {
		return subGroupCode;
	}
	/**
	 * @param subGroupCode the subGroupCode to set
	 */
	public void setSubGroupCode(String subGroupCode) {
		this.subGroupCode = subGroupCode;
	}
	/**
	 * @return the informedConsetFrmSignedDt
	 */
	public Date getInformedConsetFrmSignedDt() {
		return informedConsetFrmSignedDt;
	}
	/**
	 * @param informedConsetFrmSignedDt the informedConsetFrmSignedDt to set
	 */
	public void setInformedConsetFrmSignedDt(Date informedConsetFrmSignedDt) {
		this.informedConsetFrmSignedDt = informedConsetFrmSignedDt;
	}
	/**
	 * @return the informedConsetFrmSignedDtOrg
	 */
	public String getInformedConsetFrmSignedDtOrg() {
		return informedConsetFrmSignedDtOrg;
	}
	/**
	 * @param informedConsetFrmSignedDtOrg the informedConsetFrmSignedDtOrg to set
	 */
	public void setInformedConsetFrmSignedDtOrg(String informedConsetFrmSignedDtOrg) {
		this.informedConsetFrmSignedDtOrg = informedConsetFrmSignedDtOrg;
	}
	/**
	 * @return the enrollAge
	 */
	public Long getEnrollAge() {
		return enrollAge;
	}
	/**
	 * @param enrollAge the enrollAge to set
	 */
	public void setEnrollAge(Long enrollAge) {
		this.enrollAge = enrollAge;
	}
	/**
	 * @return the enrollAgeOrg
	 */
	public String getEnrollAgeOrg() {
		return enrollAgeOrg;
	}
	/**
	 * @param enrollAgeOrg the enrollAgeOrg to set
	 */
	public void setEnrollAgeOrg(String enrollAgeOrg) {
		this.enrollAgeOrg = enrollAgeOrg;
	}
	/**
	 * @return the studyAgentDoseLevel
	 */
	public Long getStudyAgentDoseLevel() {
		return studyAgentDoseLevel;
	}
	/**
	 * @param studyAgentDoseLevel the studyAgentDoseLevel to set
	 */
	public void setStudyAgentDoseLevel(Long studyAgentDoseLevel) {
		this.studyAgentDoseLevel = studyAgentDoseLevel;
	}
	/**
	 * @return the studyAgentDoseLevelOrg
	 */
	public String getStudyAgentDoseLevelOrg() {
		return studyAgentDoseLevelOrg;
	}
	/**
	 * @param studyAgentDoseLevelOrg the studyAgentDoseLevelOrg to set
	 */
	public void setStudyAgentDoseLevelOrg(String studyAgentDoseLevelOrg) {
		this.studyAgentDoseLevelOrg = studyAgentDoseLevelOrg;
	}
	/**
	 * @return the studyAgentDoseLevelUOMCD
	 */
	public String getStudyAgentDoseLevelUOMCD() {
		return studyAgentDoseLevelUOMCD;
	}
	/**
	 * @param studyAgentDoseLevelUOMCD the studyAgentDoseLevelUOMCD to set
	 */
	public void setStudyAgentDoseLevelUOMCD(String studyAgentDoseLevelUOMCD) {
		this.studyAgentDoseLevelUOMCD = studyAgentDoseLevelUOMCD;
	}
	/**
	 * @return the offStudyDt
	 */
	public Date getOffStudyDt() {
		return offStudyDt;
	}
	/**
	 * @param offStudyDt the offStudyDt to set
	 */
	public void setOffStudyDt(Date offStudyDt) {
		this.offStudyDt = offStudyDt;
	}
	/**
	 * @return the offStudyDtOrg
	 */
	public String getOffStudyDtOrg() {
		return offStudyDtOrg;
	}
	/**
	 * @param offStudyDtOrg the offStudyDtOrg to set
	 */
	public void setOffStudyDtOrg(String offStudyDtOrg) {
		this.offStudyDtOrg = offStudyDtOrg;
	}
	/**
	 * @return the offStudyResCode
	 */
	public String getOffStudyResCode() {
		return offStudyResCode;
	}
	/**
	 * @param offStudyResCode the offStudyResCode to set
	 */
	public void setOffStudyResCode(String offStudyResCode) {
		this.offStudyResCode = offStudyResCode;
	}
	/**
	 * @return the offStudyResOthTxt
	 */
	public String getOffStudyResOthTxt() {
		return offStudyResOthTxt;
	}
	/**
	 * @param offStudyResOthTxt the offStudyResOthTxt to set
	 */
	public void setOffStudyResOthTxt(String offStudyResOthTxt) {
		this.offStudyResOthTxt = offStudyResOthTxt;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the participantId
	 */
	public Long getParticipantId() {
		return participantId;
	}
	/**
	 * @param participantId the participantId to set
	 */
	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}
	/**
	 * @return the studySiteId
	 */
	public Long getStudySiteId() {
		return studySiteId;
	}
	/**
	 * @param studySiteId the studySiteId to set
	 */
	public void setStudySiteId(Long studySiteId) {
		this.studySiteId = studySiteId;
	}
	/**
	 * @return the securityKey
	 */
	public int getSecurityKey() {
		return securityKey;
	}
	/**
	 * @param securityKey the securityKey to set
	 */
	public void setSecurityKey(int securityKey) {
		this.securityKey = securityKey;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the srcExtractDt
	 */
	public Date getSrcExtractDt() {
		return srcExtractDt;
	}
	/**
	 * @param srcExtractDt the srcExtractDt to set
	 */
	public void setSrcExtractDt(Date srcExtractDt) {
		this.srcExtractDt = srcExtractDt;
	}
	/**
	 * @return the ctomInsertDt
	 */
	public Date getCtomInsertDt() {
		return ctomInsertDt;
	}
	/**
	 * @param ctomInsertDt the ctomInsertDt to set
	 */
	public void setCtomInsertDt(Date ctomInsertDt) {
		this.ctomInsertDt = ctomInsertDt;
	}
	/**
	 * @return the ctomUpdateDt
	 */
	public Date getCtomUpdateDt() {
		return ctomUpdateDt;
	}
	/**
	 * @param ctomUpdateDt the ctomUpdateDt to set
	 */
	public void setCtomUpdateDt(Date ctomUpdateDt) {
		this.ctomUpdateDt = ctomUpdateDt;
	}
	/**
	 * @return the studyPartIdOrig
	 */
	public String getStudyPartIdOrig() {
		return studyPartIdOrig;
	}
	/**
	 * @param studyPartIdOrig the studyPartIdOrig to set
	 */
	public void setStudyPartIdOrig(String studyPartIdOrig) {
		this.studyPartIdOrig = studyPartIdOrig;
	}
	
	/**
	 * @return the activity
	 */
	public Activity getActivity() {
		return activity;
	}
	/**
	 * @param activity the activity to set
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the participant
	 */
	public Participant getParticipant() {
		return participant;
	}
	/**
	 * @param participant the participant to set
	 */
	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

}
