package gov.nih.nci.caxchange.ctom.viewer.viewobjects;

import java.util.Date;

import gov.nih.nci.labhub.domain.LabResult;
import gov.nih.nci.labhub.domain.SubjectAssignment;


public class LabActivityResult
{

	private SubjectAssignment subjectAssignment = null;
	private LabResult labResult = null;
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
	private String adverseEventReported = "";
	private String adverseEventReportedDate = "";
	
	public String getStudyId()
	{
		return studyId;
	}

	public void setStudyId(String studyId)
	{
		this.studyId = studyId;
	}

	public LabResult getLabResult()
	{
		return labResult;
	}

	public void setLabResult(LabResult labResult)
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

	
	public String getAdverseEventReported()
	{
		return adverseEventReported;
	}

	
	public void setAdverseEventReported(String adverseEventReported)
	{
		this.adverseEventReported = adverseEventReported;
	}

	
	public String getAdverseEventReportedDate()
	{
		return adverseEventReportedDate;
	}

	
	public void setAdverseEventReportedDate(String adverseEventReportedDate)
	{
		this.adverseEventReportedDate = adverseEventReportedDate;
	}

	public String getIsAdverseEvent() {
		return isAdverseEvent;
	}

	public void setIsAdverseEvent(String isAdverseEvent) {
		this.isAdverseEvent = isAdverseEvent;
	}

	public void setActualDate(Date actualDate) {
		this.actualDate = actualDate;
		
	}

	public Date getActualDate() {
		return actualDate;
	}
	
}
