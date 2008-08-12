package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class ClinicalResult {
	
	private String crCode;
	private String crCodeSystem;
	private String crCodeSystemName;
	private Double crCodeSystemVersion;
	private String crCodeDisplayText;
	private String valueUnitOfMeasureCdSystem = null;
	private String valueUnitOfMeasureCdSystemName = null;
	
	private Long id;
	private String panelName;
	private String assayMtdCode;
	private String bodyPosCode;
	private String labRefRangeCode;
	private String labTechCd;
	private String meansVitStatObtCd;
	private String normalAbnorInd;
	private String bioMarkerInd;
	private String sigInd;
	private String addTestDesc;
	private String refFlag;
	private Long numPrecision;
	private String numPrecisionOrg;
	private Double refRangeLow;
	private String refRangeLowOrg;
	private Double refRangeHigh;
	private String refRangeHighOrg;
	private String refRangeComment;
	private String refTxtList;
	private Long labTestConDesId;
	private Long performLabId;
	private Long valUOMConDesId;
	private Long conDesId;
	private String source;
	private Date srcExtractDt=null;
	private Date ctomInsertDt=null;
	private Date ctomUpdateDt=null;
	private String value ;
	private String valueUnitOfMeasureCd ;
	
	
	
	private Long observationId;
	private PerformingLaboratory performingLaboratory=null;
	/**
	 * @return the labTechCd
	 */
	public String getLabTechCd() {
		return labTechCd;
	}
	/**
	 * @param labTechCd the labTechCd to set
	 */
	public void setLabTechCd(String labTechCd) {
		this.labTechCd = labTechCd;
	}
	/**
	 * @return the panelName
	 */
	public String getPanelName() {
		return panelName;
	}
	/**
	 * @param panelName the panelName to set
	 */
	public void setPanelName(String panelName) {
		this.panelName = panelName;
	}
	/**
	 * @return the refFlag
	 */
	public String getRefFlag() {
		return refFlag;
	}
	/**
	 * @param refFlag the refFlag to set
	 */
	public void setRefFlag(String refFlag) {
		this.refFlag = refFlag;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the valueUnitOfMeasureCd
	 */
	public String getValueUnitOfMeasureCd() {
		return valueUnitOfMeasureCd;
	}
	/**
	 * @param valueUnitOfMeasureCd the valueUnitOfMeasureCd to set
	 */
	public void setValueUnitOfMeasureCd(String valueUnitOfMeasureCd) {
		this.valueUnitOfMeasureCd = valueUnitOfMeasureCd;
	}
	/**
	 * @return the performingLaboratory
	 */
	public PerformingLaboratory getPerformingLaboratory() {
		return performingLaboratory;
	}
	/**
	 * @param performingLaboratory the performingLaboratory to set
	 */
	public void setPerformingLaboratory(PerformingLaboratory performingLaboratory) {
		this.performingLaboratory = performingLaboratory;
	}
	/**
	 * @return the observationId
	 */
	public Long getObservationId() {
		return observationId;
	}
	/**
	 * @param observationId the observationId to set
	 */
	public void setObservationId(Long observationId) {
		this.observationId = observationId;
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
	 * @return the assayMtdCode
	 */
	public String getAssayMtdCode() {
		return assayMtdCode;
	}
	/**
	 * @param assayMtdCode the assayMtdCode to set
	 */
	public void setAssayMtdCode(String assayMtdCode) {
		this.assayMtdCode = assayMtdCode;
	}
	/**
	 * @return the bodyPosCode
	 */
	public String getBodyPosCode() {
		return bodyPosCode;
	}
	/**
	 * @param bodyPosCode the bodyPosCode to set
	 */
	public void setBodyPosCode(String bodyPosCode) {
		this.bodyPosCode = bodyPosCode;
	}
	/**
	 * @return the labRefRangeCode
	 */
	public String getLabRefRangeCode() {
		return labRefRangeCode;
	}
	/**
	 * @param labRefRangeCode the labRefRangeCode to set
	 */
	public void setLabRefRangeCode(String labRefRangeCode) {
		this.labRefRangeCode = labRefRangeCode;
	}
	/**
	 * @return the meansVitStatObtCd
	 */
	public String getMeansVitStatObtCd() {
		return meansVitStatObtCd;
	}
	/**
	 * @param meansVitStatObtCd the meansVitStatObtCd to set
	 */
	public void setMeansVitStatObtCd(String meansVitStatObtCd) {
		this.meansVitStatObtCd = meansVitStatObtCd;
	}
	/**
	 * @return the normalAbnorInd
	 */
	public String getNormalAbnorInd() {
		return normalAbnorInd;
	}
	/**
	 * @param normalAbnorInd the normalAbnorInd to set
	 */
	public void setNormalAbnorInd(String normalAbnorInd) {
		this.normalAbnorInd = normalAbnorInd;
	}
	/**
	 * @return the bioMarkerInd
	 */
	public String getBioMarkerInd() {
		return bioMarkerInd;
	}
	/**
	 * @param bioMarkerInd the bioMarkerInd to set
	 */
	public void setBioMarkerInd(String bioMarkerInd) {
		this.bioMarkerInd = bioMarkerInd;
	}
	/**
	 * @return the sigInd
	 */
	public String getSigInd() {
		return sigInd;
	}
	/**
	 * @param sigInd the sigInd to set
	 */
	public void setSigInd(String sigInd) {
		this.sigInd = sigInd;
	}
	/**
	 * @return the addTestDesc
	 */
	public String getAddTestDesc() {
		return addTestDesc;
	}
	/**
	 * @param addTestDesc the addTestDesc to set
	 */
	public void setAddTestDesc(String addTestDesc) {
		this.addTestDesc = addTestDesc;
	}
	/**
	 * @return the numPrecision
	 */
	public Long getNumPrecision() {
		return numPrecision;
	}
	/**
	 * @param numPrecision the numPrecision to set
	 */
	public void setNumPrecision(Long numPrecision) {
		this.numPrecision = numPrecision;
	}
	/**
	 * @return the numPrecisionOrg
	 */
	public String getNumPrecisionOrg() {
		return numPrecisionOrg;
	}
	/**
	 * @param numPrecisionOrg the numPrecisionOrg to set
	 */
	public void setNumPrecisionOrg(String numPrecisionOrg) {
		this.numPrecisionOrg = numPrecisionOrg;
	}
	/**
	 * @return the refRangeLow
	 */
	public Double getRefRangeLow() {
		return refRangeLow;
	}
	/**
	 * @param refRangeLow the refRangeLow to set
	 */
	public void setRefRangeLow(Double refRangeLow) {
		this.refRangeLow = refRangeLow;
	}
	/**
	 * @return the refRangeLowOrg
	 */
	public String getRefRangeLowOrg() {
		return refRangeLowOrg;
	}
	/**
	 * @param refRangeLowOrg the refRangeLowOrg to set
	 */
	public void setRefRangeLowOrg(String refRangeLowOrg) {
		this.refRangeLowOrg = refRangeLowOrg;
	}
	/**
	 * @return the refRangeHigh
	 */
	public Double getRefRangeHigh() {
		return refRangeHigh;
	}
	/**
	 * @param refRangeHigh the refRangeHigh to set
	 */
	public void setRefRangeHigh(Double refRangeHigh) {
		this.refRangeHigh = refRangeHigh;
	}
	/**
	 * @return the refRangeHighOrg
	 */
	public String getRefRangeHighOrg() {
		return refRangeHighOrg;
	}
	/**
	 * @param refRangeHighOrg the refRangeHighOrg to set
	 */
	public void setRefRangeHighOrg(String refRangeHighOrg) {
		this.refRangeHighOrg = refRangeHighOrg;
	}
	/**
	 * @return the refRangeComment
	 */
	public String getRefRangeComment() {
		return refRangeComment;
	}
	/**
	 * @param refRangeComment the refRangeComment to set
	 */
	public void setRefRangeComment(String refRangeComment) {
		this.refRangeComment = refRangeComment;
	}
	/**
	 * @return the refTxtList
	 */
	public String getRefTxtList() {
		return refTxtList;
	}
	/**
	 * @param refTxtList the refTxtList to set
	 */
	public void setRefTxtList(String refTxtList) {
		this.refTxtList = refTxtList;
	}
	/**
	 * @return the labTestConDesId
	 */
	public Long getLabTestConDesId() {
		return labTestConDesId;
	}
	/**
	 * @param labTestConDesId the labTestConDesId to set
	 */
	public void setLabTestConDesId(Long labTestConDesId) {
		this.labTestConDesId = labTestConDesId;
	}
	/**
	 * @return the performLabId
	 */
	public Long getPerformLabId() {
		return performLabId;
	}
	/**
	 * @param performLabId the performLabId to set
	 */
	public void setPerformLabId(Long performLabId) {
		this.performLabId = performLabId;
	}
	/**
	 * @return the valUOMConDesId
	 */
	public Long getValUOMConDesId() {
		return valUOMConDesId;
	}
	/**
	 * @param valUOMConDesId the valUOMConDesId to set
	 */
	public void setValUOMConDesId(Long valUOMConDesId) {
		this.valUOMConDesId = valUOMConDesId;
	}
	/**
	 * @return the conDesId
	 */
	public Long getConDesId() {
		return conDesId;
	}
	/**
	 * @param conDesId the conDesId to set
	 */
	public void setConDesId(Long conDesId) {
		this.conDesId = conDesId;
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
	 * @return the crCode
	 */
	public String getCrCode() {
		return crCode;
	}
	/**
	 * @param crCode the crCode to set
	 */
	public void setCrCode(String crCode) {
		this.crCode = crCode;
	}
	/**
	 * @return the crCodeSystem
	 */
	public String getCrCodeSystem() {
		return crCodeSystem;
	}
	/**
	 * @param crCodeSystem the crCodeSystem to set
	 */
	public void setCrCodeSystem(String crCodeSystem) {
		this.crCodeSystem = crCodeSystem;
	}
	/**
	 * @return the crCodeSystemName
	 */
	public String getCrCodeSystemName() {
		return crCodeSystemName;
	}
	/**
	 * @param crCodeSystemName the crCodeSystemName to set
	 */
	public void setCrCodeSystemName(String crCodeSystemName) {
		this.crCodeSystemName = crCodeSystemName;
	}
	/**
	 * @return the crCodeSystemVersion
	 */
	public Double getCrCodeSystemVersion() {
		return crCodeSystemVersion;
	}
	/**
	 * @param crCodeSystemVersion the crCodeSystemVersion to set
	 */
	public void setCrCodeSystemVersion(Double crCodeSystemVersion) {
		this.crCodeSystemVersion = crCodeSystemVersion;
	}
	/**
	 * @return the crCodeDisplayText
	 */
	public String getCrCodeDisplayText() {
		return crCodeDisplayText;
	}
	/**
	 * @param crCodeDisplayText the crCodeDisplayText to set
	 */
	public void setCrCodeDisplayText(String crCodeDisplayText) {
		this.crCodeDisplayText = crCodeDisplayText;
	}
	/**
	 * @return the valueUnitOfMeasureCdSystem
	 */
	public String getValueUnitOfMeasureCdSystem() {
		return valueUnitOfMeasureCdSystem;
	}
	/**
	 * @param valueUnitOfMeasureCdSystem the valueUnitOfMeasureCdSystem to set
	 */
	public void setValueUnitOfMeasureCdSystem(String valueUnitOfMeasureCdSystem) {
		this.valueUnitOfMeasureCdSystem = valueUnitOfMeasureCdSystem;
	}
	/**
	 * @return the valueUnitOfMeasureCdSystemName
	 */
	public String getValueUnitOfMeasureCdSystemName() {
		return valueUnitOfMeasureCdSystemName;
	}
	/**
	 * @param valueUnitOfMeasureCdSystemName the valueUnitOfMeasureCdSystemName to set
	 */
	public void setValueUnitOfMeasureCdSystemName(
			String valueUnitOfMeasureCdSystemName) {
		this.valueUnitOfMeasureCdSystemName = valueUnitOfMeasureCdSystemName;
	}
	

	
}
