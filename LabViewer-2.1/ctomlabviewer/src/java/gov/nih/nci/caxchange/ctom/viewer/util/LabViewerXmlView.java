package gov.nih.nci.caxchange.ctom.viewer.util;

import org.displaytag.export.XmlView;

/**
 * @author Lisa Kelley
 */
public class LabViewerXmlView extends XmlView
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
