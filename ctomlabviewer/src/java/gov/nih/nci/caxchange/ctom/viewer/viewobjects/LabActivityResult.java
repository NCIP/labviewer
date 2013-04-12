/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.viewobjects;

import gov.nih.nci.labhub.domain.LaboratoryResult;
import gov.nih.nci.labhub.domain.SubjectAssignment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LabActivityResult
{
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
	private SubjectAssignment subjectAssignment = null;
	private LaboratoryResult labResult = null;
	private Date actualDate = null;
	private String recordId = "";
	private String patientId = "";
	private String studyId = "";
	private String date = "";
	private String labTestId = "";
	private String textResult = "";
	private String numericResult = "";
	private String unitOfMeasure = "";
	private String labResultId = "";;
	private String lowRange = "";;
	private String highRange = "";
	private String isAdverseEvent = "";
	private boolean adverseEventReported = false;
	private String adverseEventReportedDate = "";
	private boolean labLoadedToCDMS = false;
	private String labLoadedToCDMSDate = "";
	private String siteId = "";
	private Date labsToCDMSDate;
    private Date labsToAEDate;

	/**
	 * @return the siteId
	 */
	public String getSiteId()
	{
		return siteId;
	}

	/**
	 * @param siteId
	 *            the siteId to set
	 */
	public void setSiteId(String siteId)
	{
		this.siteId = siteId;
	}

	public String getStudyId()
	{
		return studyId;
	}

	public void setStudyId(String studyId)
	{
		this.studyId = studyId;
	}

	public LaboratoryResult getLabResult()
	{
		return labResult;
	}

	public void setLabResult(LaboratoryResult labResult)
	{
		this.labResult = labResult;
	}

	public String getLabResultId()
	{
		return labResultId;
	}

	public void setLabResultId(String labResultId)
	{
		this.labResultId = labResultId;
	}

	public String getRecordId()
	{
		return recordId;
	}

	public void setRecordId(String recordId)
	{
		this.recordId = recordId;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getHighRange()
	{
		return highRange;
	}

	public void setHighRange(String highRange)
	{
		this.highRange = highRange;
	}

	public String getLabTestId()
	{
		return labTestId;
	}

	public void setLabTestId(String labTestId)
	{
		this.labTestId = labTestId;
	}

	public String getLowRange()
	{
		return lowRange;
	}

	public void setLowRange(String lowRange)
	{
		this.lowRange = lowRange;
	}

	public String getNumericResult()
	{
		return numericResult;
	}

	public void setNumericResult(String numericResult)
	{
		this.numericResult = numericResult;
	}

	public String getPatientId()
	{
		return patientId;
	}

	public void setPatientId(String patientId)
	{
		this.patientId = patientId;
	}

	public SubjectAssignment getSubjectAssignment()
	{
		return subjectAssignment;
	}

	public void setSubjectAssignment(SubjectAssignment subjectAssignment)
	{
		this.subjectAssignment = subjectAssignment;
	}

	public String getTextResult()
	{
		return textResult;
	}

	public void setTextResult(String textResult)
	{
		this.textResult = textResult;
	}

	public String getUnitOfMeasure()
	{
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure)
	{
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getAdverseEventReportedDate()
	{
		return adverseEventReportedDate;
	}

	public void setAdverseEventReportedDate(String adverseEventReportedDate)
	{
		this.adverseEventReportedDate = adverseEventReportedDate;
	}

	public String getIsAdverseEvent()
	{
		return isAdverseEvent;
	}

	public void setIsAdverseEvent(String isAdverseEvent)
	{
		this.isAdverseEvent = isAdverseEvent;
	}

	public void setActualDate(Date actualDate)
	{
		this.actualDate = actualDate;

	}

	public Date getActualDate()
	{
		return actualDate;
	}

	/**
	 * @return the labLoadedToCDMS
	 */
	public boolean isLabLoadedToCDMS()
	{
		return labLoadedToCDMS;
	}

	/**
	 * @param labLoadedToCDMS
	 *            the labLoadedToCDMS to set
	 */
	public void setLabLoadedToCDMS(boolean labLoadedToCDMS)
	{
		this.labLoadedToCDMS = labLoadedToCDMS;
	}

	/**
	 * @return the labLoadedToCDMSDate
	 */
	public String getLabLoadedToCDMSDate()
	{
		return labLoadedToCDMSDate;
	}

	/**
	 * @param labLoadedToCDMSDate
	 *            the labLoadedToCDMSDate to set
	 */
	public void setLabLoadedToCDMSDate(String labLoadedToCDMSDate)
	{
		this.labLoadedToCDMSDate = labLoadedToCDMSDate;
	}

	/**
	 * @return the adverseEventReported
	 */
	public boolean isAdverseEventReported()
	{
		return adverseEventReported;
	}

	/**
	 * @param adverseEventReported
	 *            the adverseEventReported to set
	 */
	public void setAdverseEventReported(boolean adverseEventReported)
	{
		this.adverseEventReported = adverseEventReported;
	}
	
	public void setLabsToCDMSDate(Date labsToCDMSDate)
	{
		this.labsToCDMSDate = labsToCDMSDate;
	}

	public Date getLabsToCDMSDate()
	{
		return labsToCDMSDate;
	}
	
	public void setLabsToAEDate(Date labsToAEDate)
	{
		this.labsToAEDate = labsToAEDate;
	}

	public Date getLabsToAEDate()
	{
		return labsToAEDate;
	}
	
	/**
	 * @return the labsToCDMS
	 */
	public String getLabsToCDMSDateFormatted()
	{	
		return formatter.format(labsToCDMSDate);
	}

	/**
	 * @return the labsToAE
	 */
	public String getLabsToAEDateFormatted()
	{	
		return formatter.format(labsToAEDate);
	}

}
