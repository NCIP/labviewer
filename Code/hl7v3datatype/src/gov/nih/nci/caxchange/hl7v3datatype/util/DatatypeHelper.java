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
import org.hl7.types.impl.CDimpl;
import org.hl7.types.impl.IIimpl;

/**
 * Provides factory methods for the Data Types that creates and returns an instances
 * of the value objects defined in the data type interface package.
 *
 *
 * @author OWNER: Eric Chen  Date: Dec 25, 2007
 * @author LAST UPDATE: $Author: chene $
 * @version $Revision: 1.1 $
 * @date $$Date: 2007-12-25 23:22:37 $
 */


public class DatatypeHelper
{
    public static II newII(String root, String extenstion) {
        return IIimpl.valueOf(root, extenstion, null);
    }

    public static CD newCD(String codeString, String codeSystemString) {
        return CDimpl.valueOf(codeString, codeSystemString);
    }
    
}

/**
 * HISTORY      : : SomeClass.java,v $
 */
