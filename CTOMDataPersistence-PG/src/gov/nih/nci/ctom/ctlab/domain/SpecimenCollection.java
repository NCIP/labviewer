package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class SpecimenCollection {
	private Long id;
	private String siteCondCd;
	private String mtdCode;
	private Long centralLabId;
	private String source;
	private Date srcExtractDt;
	private Date ctomInsertDt;
	private Date ctomUpdateDt;
	private Long procedureActivityId;
	private CentralLaboratory centralLaboratory=null;
	private Specimen specimen=null;
	
	
	/**
	 * @return the centralLaboratory
	 */
	public CentralLaboratory getCentralLaboratory() {
		return centralLaboratory;
	}
	/**
	 * @param centralLaboratory the centralLaboratory to set
	 */
	public void setCentralLaboratory(CentralLaboratory centralLaboratory) {
		this.centralLaboratory = centralLaboratory;
	}
	/**
	 * @return the specimen
	 */
	public Specimen getSpecimen() {
		return specimen;
	}
	/**
	 * @param specimen the specimen to set
	 */
	public void setSpecimen(Specimen specimen) {
		this.specimen = specimen;
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
	 * @return the siteCondCd
	 */
	public String getSiteCondCd() {
		return siteCondCd;
	}
	/**
	 * @param siteCondCd the siteCondCd to set
	 */
	public void setSiteCondCd(String siteCondCd) {
		this.siteCondCd = siteCondCd;
	}
	/**
	 * @return the mtdCode
	 */
	public String getMtdCode() {
		return mtdCode;
	}
	/**
	 * @param mtdCode the mtdCode to set
	 */
	public void setMtdCode(String mtdCode) {
		this.mtdCode = mtdCode;
	}
	/**
	 * @return the centralLabId
	 */
	public Long getCentralLabId() {
		return centralLabId;
	}
	/**
	 * @param centralLabId the centralLabId to set
	 */
	public void setCentralLabId(Long centralLabId) {
		this.centralLabId = centralLabId;
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
