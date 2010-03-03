package gov.nih.nci.caxchange.ctom.viewer.util;

import org.displaytag.export.CsvView;

/**
 * @author Lisa Kelley
 */
public class LabViewerCsvView extends CsvView
{
	protected String escapeColumnValue(java.lang.Object value)
	{
        String returnValue;
        
        if (value instanceof String)
        {
            String strValue = (String)value;
            if (strValue.trim().equalsIgnoreCase("&nbsp;"))
            {
                returnValue = "";
            } 
            else
            {
                returnValue = super.escapeColumnValue(value);
            }
        }
        else
        {
            returnValue = super.escapeColumnValue(value);
        }
        
        return returnValue;
    }	  
	
}
