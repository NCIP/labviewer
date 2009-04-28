package gov.nih.nci.caxchange.ctom.viewer.util;

import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResult;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.ParticipantSearchResult;

import org.displaytag.decorator.TableDecorator;


/**
 * The Class LabSearchDecorator.
 * 
 * @author Anupama Sharma
 */
public class LabSearchDecorator extends TableDecorator
{

	/** The Constant NBSP. */
	private static final String NBSP = "&nbsp;";

	/**
	 * Instantiates a new lab search decorator.
	 */
	public LabSearchDecorator()
	{

		super();
	}

	/**
	 * Gets the site id.
	 * 
	 * @return the site id
	 */
	public final String getSiteId()
	{

		LabActivityResult labSearchResult = (LabActivityResult) getCurrentRowObject();
		String siteId = NBSP;
		if (labSearchResult.getSiteId() != null && !labSearchResult.getSiteId().equals("")
				&& !labSearchResult.getSiteId().equals("null"))
		{
			siteId = labSearchResult.getSiteId();
		}
		return siteId;
	}

	/**
	 * Gets the text result.
	 * 
	 * @return the text result
	 */
	public final String getTextResult()
	{

		LabActivityResult labSearchResult = (LabActivityResult) getCurrentRowObject();
		String textResult = NBSP;
		if (labSearchResult.getTextResult() != null && !labSearchResult.getTextResult().equals("")
				&& !labSearchResult.getTextResult().equals("null"))
		{
			textResult = labSearchResult.getTextResult();
		}
		return textResult;
	}
	
	/**
	 * Gets the numeric result.
	 * 
	 * @return the numeric result
	 */
	public final String getNumericResult()
	{

		LabActivityResult labSearchResult = (LabActivityResult) getCurrentRowObject();
		String numResult = NBSP;
		if (labSearchResult.getNumericResult()!= null && !labSearchResult.getNumericResult().equals("")
				&& !labSearchResult.getNumericResult().equals("null"))
		{
			numResult = labSearchResult.getNumericResult();
		}
		return numResult;
	}
	
	/**
	 * Gets the unit of measure.
	 * 
	 * @return the unit of measure
	 */
	public final String getUnitOfMeasure()
	{

		LabActivityResult labSearchResult = (LabActivityResult) getCurrentRowObject();
		String uom = NBSP;
		if (labSearchResult.getUnitOfMeasure() != null && !labSearchResult.getUnitOfMeasure().equals("")
				&& !labSearchResult.getUnitOfMeasure().equals("null"))
		{
			uom = labSearchResult.getUnitOfMeasure();
		}
		return uom;
	}
	
	/**
	 * Gets the study id.
	 * 
	 * @return the study id
	 */
	public final String getStudyId()
	{

		ParticipantSearchResult partSearchResult = (ParticipantSearchResult) getCurrentRowObject();
		String studyId = NBSP;
		if (partSearchResult.getStudyId() != null && !partSearchResult.getStudyId().equals("")
				&& !partSearchResult.getStudyId().equals("null"))
		{
			studyId = partSearchResult.getStudyId();
		}
		return studyId;
	}

}
