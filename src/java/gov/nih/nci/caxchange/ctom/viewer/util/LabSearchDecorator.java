package gov.nih.nci.caxchange.ctom.viewer.util;

import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResult;

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
	 * Gets the low range.
	 * 
	 * @return the low range
	 */
	public final String getLowRange()
	{

		LabActivityResult labSearchResult = (LabActivityResult) getCurrentRowObject();
		String low = NBSP;
		if (labSearchResult.getLowRange() != null && !labSearchResult.getLowRange().equals("")
				&& !labSearchResult.getLowRange().equals("null"))
		{
			low = labSearchResult.getLowRange();
		}
		return low;
	}
	
	/**
	 * Gets the high range.
	 * 
	 * @return the high range
	 */
	public final String getHighRange()
	{

		LabActivityResult labSearchResult = (LabActivityResult) getCurrentRowObject();
		String high = NBSP;
		if (labSearchResult.getHighRange() != null && !labSearchResult.getHighRange().equals("")
				&& !labSearchResult.getHighRange().equals("null"))
		{
			high = labSearchResult.getHighRange();
		}
		return high;
	}
	
	/**
	 * Gets the labs to cdms.
	 * 
	 * @return the labs to cdms
	 */
	public final String getLabsToCDMS()
	{

		LabActivityResult labSearchResult = (LabActivityResult) getCurrentRowObject();
		String labsToCDMS = "false";
		if (labSearchResult.isLabLoadedToCDMS())
		{
			labsToCDMS = labSearchResult.getLabLoadedToCDMSDate();
		}
		return labsToCDMS;
	}
	
	/**
	 * Gets the labs to ae.
	 * 
	 * @return the labs to ae
	 */
	public final String getLabsToAE()
	{

		LabActivityResult labSearchResult = (LabActivityResult) getCurrentRowObject();
		String labsToAE = "false";
		if (labSearchResult.isAdverseEventReported())
		{
			labsToAE = labSearchResult.getAdverseEventReportedDate();
		}
		return labsToAE;
	}
	
}
