package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

/**
 * @author griffinch
 *
 */
public class Procedure {
	private Long id;
	private String anatomicSiteCd;
	private String anatomicSiteCdSys;
	private String fastingStatus;
	private String subtypeCd;
	private String source;
	private Date srcExtractDt;
	private Date ctomInsertDt;
	private Date ctomUpdateDt;
	private SpecimenCollection specimenCollection=null;

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
	 * @return the specimenCollection
	 */
	public SpecimenCollection getSpecimenCollection() {
		return specimenCollection;
	}

	/**
	 * @param specimenCollection the specimenCollection to set
	 */
	public void setSpecimenCollection(SpecimenCollection specimenCollection) {
		this.specimenCollection = specimenCollection;
	}

	/**
	 * @return the fastingStatus
	 */
	public String getFastingStatus() {
		return fastingStatus;
	}

	/**
	 * @param fastingStatus the fastingStatus to set
	 */
	public void setFastingStatus(String fastingStatus) {
		this.fastingStatus = fastingStatus;
	}

	/**
	 * @return the anatomicSiteCd
	 */
	public String getAnatomicSiteCd() {
		return anatomicSiteCd;
	}

	/**
	 * @param anatomicSiteCd the anatomicSiteCd to set
	 */
	public void setAnatomicSiteCd(String anatomicSiteCd) {
		this.anatomicSiteCd = anatomicSiteCd;
	}

	/**
	 * @return the anatomicSiteCdSys
	 */
	public String getAnatomicSiteCdSys() {
		return anatomicSiteCdSys;
	}

	/**
	 * @param anatomicSiteCdSys the anatomicSiteCdSys to set
	 */
	public void setAnatomicSiteCdSys(String anatomicSiteCdSys) {
		this.anatomicSiteCdSys = anatomicSiteCdSys;
	}

	/**
	 * @return the subtypeCd
	 */
	public String getSubtypeCd() {
		return subtypeCd;
	}

	/**
	 * @param subtypeCd the subtypeCd to set
	 */
	public void setSubtypeCd(String subtypeCd) {
		this.subtypeCd = subtypeCd;
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
