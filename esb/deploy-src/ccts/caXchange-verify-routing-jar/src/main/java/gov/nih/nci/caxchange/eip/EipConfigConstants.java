/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.eip;

import java.util.Set;

/**
 * Common constants used with in this package.
 * 
 * @author Harsh Marwaha
 *
 */
public class EipConfigConstants {
   public static Set<String> ignoreServiceNames = new java.util.HashSet<String>();
   static {
	   ignoreServiceNames.add("caxchange:ResponseQueueService");
   }
}
