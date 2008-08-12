package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class Specimen {

	private Long id;
	private Long sampleIDentifier;
	private String sampleIdentifierOrig = null;
	private Long volume;
	private String volumeOrg;
	private String volumeUOMCd;
	private String accessionNumber = null;
	private String condition = null;
	private String commentsFromLaboratory = null;
	private String commentsFromInvestigator = null;
	private Long sampleTypeCdId;
	private Long specimenCollectionId;
	private int securityKey;
	private String source;
	private Date srcExtractDt;
	private Date ctomInsertDt;
	private Date ctomUpdateDt;
	private Long procedureActivityId;
	/**
	 * @return the accessionNumber
	 */
	public String getAccessionNumber() {
		return accessionNumber;
	}
	/**
	 * @param accessionNumber the accessionNumber to set
	 */
	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}
	/**
	 * @return the commentsFromInvestigator
	 */
	public String getCommentsFromInvestigator() {
		return commentsFromInvestigator;
	}
	/**
	 * @param commentsFromInvestigator the commentsFromInvestigator to set
	 */
	public void setCommentsFromInvestigator(String commentsFromInvestigator) {
		this.commentsFromInvestigator = commentsFromInvestigator;
	}
	/**
	 * @return the commentsFromLaboratory
	 */
	public String getCommentsFromLaboratory() {
		return commentsFromLaboratory;
	}
	/**
	 * @param commentsFromLaboratory the commentsFromLaboratory to set
	 */
	public void setCommentsFromLaboratory(String commentsFromLaboratory) {
		this.commentsFromLaboratory = commentsFromLaboratory;
	}
	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}
	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}
	/**
	 * @return the sampleIdentifierOrig
	 */
	public String getSampleIdentifierOrig() {
		return sampleIdentifierOrig;
	}
	/**
	 * @param sampleIdentifierOrig the sampleIdentifierOrig to set
	 */
	public void setSampleIdentifierOrig(String sampleIdentifierOrig) {
		this.sampleIdentifierOrig = sampleIdentifierOrig;
	}
	/**
	 * @return the sampleTypeCd
	 *//*
	public String getSampleTypeCd() {
		return sampleTypeCd;
	}
	*//**
	 * @param sampleTypeCd the sampleTypeCd to set
	 *//*
	public void setSampleTypeCd(String sampleTypeCd) {
		this.sampleTypeCd = sampleTypeCd;
	}
	/**
	 * @return the procedureActivityId
	 */
	public Long getProcedureActivityId() {
		return procedureActivityId;
	}
	/**
	 * @param procedureActivityId the procedureActivityId to set
	 */
	public void setProcedureActivityId(Long procedureActivityId) {
		this.procedureActivityId = procedureActivityId;
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
	 * @return the sampleTypeCdSystem
	 *//*
	public String getSampleTypeCdSystem() {
		return sampleTypeCdSystem;
	}
	*//**
	 * @param sampleTypeCdSystem the sampleTypeCdSystem to set
	 *//*
	public void setSampleTypeCdSystem(String sampleTypeCdSystem) {
		this.sampleTypeCdSystem = sampleTypeCdSystem;
	}
	*//**
	 * @return the sampleTypeCdSystemName
	 *//*
	public String getSampleTypeCdSystemName() {
		return sampleTypeCdSystemName;
	}
	*//**
	 * @param sampleTypeCdSystemName the sampleTypeCdSystemName to set
	 *//*
	public void setSampleTypeCdSystemName(String sampleTypeCdSystemName) {
		this.sampleTypeCdSystemName = sampleTypeCdSystemName;
	}*/
	/**
	 * @return the sampleIDentifier
	 */
	public Long getSampleIDentifier() {
		return sampleIDentifier;
	}
	/**
	 * @param sampleIDentifier the sampleIDentifier to set
	 */
	public void setSampleIDentifier(Long sampleIDentifier) {
		this.sampleIDentifier = sampleIDentifier;
	}
	/**
	 * @return the volume
	 */
	public Long getVolume() {
		return volume;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(Long volume) {
		this.volume = volume;
	}
	/**
	 * @return the volumeOrg
	 */
	public String getVolumeOrg() {
		return volumeOrg;
	}
	/**
	 * @param volumeOrg the volumeOrg to set
	 */
	public void setVolumeOrg(String volumeOrg) {
		this.volumeOrg = volumeOrg;
	}
	/**
	 * @return the volumeUOMCd
	 */
	public String getVolumeUOMCd() {
		return volumeUOMCd;
	}
	/**
	 * @param volumeUOMCd the volumeUOMCd to set
	 */
	public void setVolumeUOMCd(String volumeUOMCd) {
		this.volumeUOMCd = volumeUOMCd;
	}
	/**
	 * @return the sampleTypeCdId
	 */
	public Long getSampleTypeCdId() {
		return sampleTypeCdId;
	}
	/**
	 * @param sampleTypeCdId the sampleTypeCdId to set
	 */
	public void setSampleTypeCdId(Long sampleTypeCdId) {
		this.sampleTypeCdId = sampleTypeCdId;
	}
	/**
	 * @return the specimenCollectionId
	 */
	public Long getSpecimenCollectionId() {
		return specimenCollectionId;
	}
	/**
	 * @param specimenCollectionId the specimenCollectionId to set
	 */
	public void setSpecimenCollectionId(Long specimenCollectionId) {
		this.specimenCollectionId = specimenCollectionId;
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
	

	
}
