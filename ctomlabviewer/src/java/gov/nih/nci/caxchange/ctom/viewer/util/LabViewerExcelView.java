package gov.nih.nci.caxchange.ctom.viewer.util;

import org.displaytag.export.ExcelView;

/**
 * @author Lisa Kelley
 */
public class LabViewerExcelView extends ExcelView
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
