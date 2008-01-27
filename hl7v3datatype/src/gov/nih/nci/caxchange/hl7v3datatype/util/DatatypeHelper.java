/**
 * : $
 *
 * ******************************************************************
 * COPYRIGHT NOTICE
 * ******************************************************************
 *
 *	The America on Top corp Software License, Version 1.0
 *
 *  All Right Reserved   
 *
 * ********************************************************************
 */

package gov.nih.nci.caxchange.hl7v3datatype.util;

import org.hl7.types.CD;
import org.hl7.types.II;
import org.hl7.types.TS;
import org.hl7.types.impl.CDimpl;
import org.hl7.types.impl.IIimpl;
import org.hl7.types.impl.TSjuDateAdapter;

/**
 * Provides factory methods for the Data Types that creates and returns an instances
 * of the value objects defined in the data type interface package.
 *
 *
 * @author OWNER: Eric Chen  Date: Dec 25, 2007
 * @author LAST UPDATE: $Author: chene $
 * @version $Revision: 1.2 $
 * @date $$Date: 2008-01-27 20:00:01 $
 */


public class DatatypeHelper
{
    public static II newII(String root, String extenstion) {
        return IIimpl.valueOf(root, extenstion, null);
    }

    public static CD newCD(String codeString, String codeSystemString) {
        return CDimpl.valueOf(codeString, codeSystemString);
    }

    /**
     *  Input String's format will be yyyyMMddHHmmss.SSS
     * @param timeString
     * @return
     */
    public static TS newTS(String timeString) {
        return TSjuDateAdapter.valueOf(timeString);
    }

    
}

/**
 * HISTORY      : : SomeClass.java,v $
 */
