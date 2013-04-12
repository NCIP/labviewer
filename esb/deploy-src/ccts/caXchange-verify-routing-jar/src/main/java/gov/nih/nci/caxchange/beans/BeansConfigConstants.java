/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.beans;

/**
 * A class containing constants used in this package.
 * 
 * @author Harsh Marwaha
 *
 */
public class BeansConfigConstants {
	
	public static String BEAN_ENDPOINT_XPATH="/beans/endpoint"; 
	
    public static String GENERIC_BEAN_CONFIG = "<bean  id=\"@beanId@\" class=\"@class@\"> "+
                                               " <property name=\"serviceUrl\" value=\"@serviceUrl@\" /> "+
                                               " <property name=\"gridClientClassName\" value=\"@gridClientClassName@\" /> "+         
                                               " <property name=\"requestPayloadClassName\" value=\"@requestPayloadClassName@\" /> "+    
                                               " <property name=\"operationName\" value=\"@operationName@\" /> "+             
                                               "</bean> ";

}
