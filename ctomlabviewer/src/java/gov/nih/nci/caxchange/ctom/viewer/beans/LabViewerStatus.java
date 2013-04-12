/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.beans;

import java.util.Date;

/**
 * @author asharma
 */
public class LabViewerStatus
{

	Integer id;
	String adverseEventIndicator = "false";
	Date adverseEventSentDate = null;
	String cdmsIndicator = "false";
	Date cdmsSentDate = null;
	Integer clinicalResultId;
	Date ctomInsertDate = null;
	Date ctomUpdateDate = null;

	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * @return the adverseEventIndicator
	 */
	public String isAdverseEventIndicator()
	{
		return adverseEventIndicator;
	}

	/**
	 * @param adverseEventIndicator
	 *            the adverseEventIndicator to set
	 */
	public void setAdverseEventIndicator(String adverseEventIndicator)
	{
		this.adverseEventIndicator = adverseEventIndicator;
	}

	/**
	 * @return the adverseEventSentDate
	 */
	public Date getAdverseEventSentDate()
	{
		return adverseEventSentDate;
	}

	/**
	 * @param adverseEventSentDate
	 *            the adverseEventSentDate to set
	 */
	public void setAdverseEventSentDate(Date adverseEventSentDate)
	{
		this.adverseEventSentDate = adverseEventSentDate;
	}

	/**
	 * @return the cdmsIndicator
	 */
	public String isCdmsIndicator()
	{
		return cdmsIndicator;
	}

	/**
	 * @param cdmsIndicator
	 *            the cdmsIndicator to set
	 */
	public void setCdmsIndicator(String cdmsIndicator)
	{
		this.cdmsIndicator = cdmsIndicator;
	}

	/**
	 * @return the cdmsSentDate
	 */
	public Date getCdmsSentDate()
	{
		return cdmsSentDate;
	}

	/**
	 * @param cdmsSentDate
	 *            the cdmsSentDate to set
	 */
	public void setCdmsSentDate(Date cdmsSentDate)
	{
		this.cdmsSentDate = cdmsSentDate;
	}

	/**
	 * @return the clinicalResultId
	 */
	public Integer getClinicalResultId()
	{
		return clinicalResultId;
	}

	/**
	 * @param clinicalResultId
	 *            the clinicalResultId to set
	 */
	public void setClinicalResultId(Integer clinicalResultId)
	{
		this.clinicalResultId = clinicalResultId;
	}

	/**
	 * @return the ctomInsertDate
	 */
	public Date getCtomInsertDate()
	{
		return ctomInsertDate;
	}

	/**
	 * @param ctomInsertDate
	 *            the ctomInsertDate to set
	 */
	public void setCtomInsertDate(Date ctomInsertDate)
	{
		this.ctomInsertDate = ctomInsertDate;
	}

	/**
	 * @return the ctomUpdateDate
	 */
	public Date getCtomUpdateDate()
	{
		return ctomUpdateDate;
	}

	/**
	 * @param ctomUpdateDate
	 *            the ctomUpdateDate to set
	 */
	public void setCtomUpdateDate(Date ctomUpdateDate)
	{
		this.ctomUpdateDate = ctomUpdateDate;
	}

}
