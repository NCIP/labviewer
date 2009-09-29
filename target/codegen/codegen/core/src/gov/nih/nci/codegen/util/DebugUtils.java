package gov.nih.nci.codegen.util;

import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociation;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociationEnd;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.domain.UMLPackage;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * Utility methods for assisting in the debugging
 * 
 * @author Satish Patel
 *
 */
public class DebugUtils
{
	private static Logger log = Logger.getLogger(DebugUtils.class);
	
	/**
	 * Logs the UML model information on the console
	 * 
	 * @param model
	 */
	public static void printModel(UMLModel model)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("\n*******************************************************************\n");
		sb.append("******  Printing UML Model\n");
		sb.append("*******************************************************************\n\n");
		for(UMLPackage rootPackage:model.getPackages())
		{
			sb.append("<").append(rootPackage.getName()).append(">\n");
			printPackage(rootPackage, sb,"----");
			sb.append("</").append(rootPackage.getName()).append(">\n");
		}
		sb.append("\n*******************************************************************\n");
		sb.append("******  Printing Complete\n");
		sb.append("*******************************************************************\n");
		log.debug(sb);
	}
	
	private static void printPackage(UMLPackage rootPackage, StringBuilder sb, String prefix)
	{
		for(UMLPackage pack:rootPackage.getPackages())
		{
			sb.append(prefix).append("<").append(pack.getName()).append(">\n");
			for(UMLClass klass:pack.getClasses())
			{
				sb.append(prefix).append("----<").append(klass.getName()).append(">\n");
				for(UMLAttribute attr:klass.getAttributes())
					sb.append(prefix).append("--------</att:").append(attr.getName()).append(">\n");
				
				for(UMLAssociation assoc:klass.getAssociations())
				{
					List<UMLAssociationEnd> assocEnds = assoc.getAssociationEnds();
					UMLAssociationEnd end1 = assocEnds.get(0);
					UMLAssociationEnd end2 = assocEnds.get(1);
					if(end1.getUMLElement().equals(klass) && end2.getUMLElement().equals(klass))
						sb.append(prefix).append("--------</selfref:").append(end1.getRoleName()).append(">\n");
					else if(end1.getUMLElement().equals(klass))
						sb.append(prefix).append("--------</ref1:").append(end1.getRoleName()).append(">\n");
					else if(end2.getUMLElement().equals(klass))
						sb.append(prefix).append("--------</ref2:").append(end2.getRoleName()).append(">\n");
				}
				sb.append(prefix).append("----</").append(klass.getName()).append(">\n");
			}
			printPackage(pack, sb,prefix+"----");
			sb.append(prefix).append("</").append(pack.getName()).append(">\n");
		}
	}
	
}