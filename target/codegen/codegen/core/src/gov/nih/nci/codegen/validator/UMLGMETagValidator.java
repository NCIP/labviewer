package gov.nih.nci.codegen.validator;

import gov.nih.nci.codegen.GenerationException;
import gov.nih.nci.codegen.GeneratorError;
import gov.nih.nci.codegen.GeneratorErrors;
import gov.nih.nci.codegen.Validator;
import gov.nih.nci.codegen.util.TransformerUtils;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociation;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociationEnd;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.domain.UMLPackage;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * Validates the GME Project tags found within the <code>model</code> passed in as a parameter
 * GME Project Tags include:
 * 		NCI_GME_XML_NAMESPACE:  	Used on Project, Package, and Class UML elements
 *		NCI_GME_XML_ELEMENT:  		Used on Class UML elements
 *		NCI_GME_XML_LOC_REF:		Used on Attribute UML elements
 *		NCI_GME_SOURCE_XML_LOC_REF: Used on Association UML elements
 *		NCI_GME_TARGET_XML_LOC_REF:	Used on Association UML elements
 *
 * Validation rules are as follows:
 * 
 * <UL>
 * <LI>Package name should not contain spaces</LI>
 * <LI>Model should not contain duplicate classes</LI>
 * <LI>Class name should be present</LI>
 * <LI>Class name should not contain empty spaces</LI>
 * <LI>Class name should start with a character</LI>
 * <LI>Navigable association end must have a role name</LI>
 * <LI>Association end must not contain spaces</LI>
 * <LI>Association end name should start with a character</LI> 
 * <LI>Associated class should be present in the included package and not excluded</LI>
 * <LI>Association can not be made to classes in java.lang and java.util packages</LI>
 * <LI>Class should not contain duplicate associations (association ends which are not navigable are not counted)</LI>
 * <LI>Attribute name should be present</LI>
 * <LI>Attribute name should not contain empty spaces</LI>
 * <LI>Attribute name should start with a character</LI>
 * <LI>Class should not have duplicate attributes</LI>
 * <LI>Parent class of any class should be in the included package and not excluded</LI>
 * </UL>
 * 
 * @author Dan Dumitru
 *
 */
public class UMLGMETagValidator implements Validator
{

	private static Logger log = Logger.getLogger(UMLGMETagValidator.class);

	private boolean enabled = true;

	private String namespaceUriPrefix;

	private String name = UMLGMETagValidator.class.getName();	

	private TransformerUtils transformerUtils;

	public void setTransformerUtils(TransformerUtils transformerUtils) {
		this.transformerUtils = transformerUtils;
	}
	/**
	 * See description for the this class above
	 *  
	 * @see gov.nih.nci.codegen.Validator#validate(gov.nih.nci.ncicb.xmiinout.domain.UMLModel)
	 */
	public GeneratorErrors validate(UMLModel model)
	{
		GeneratorErrors errors = new GeneratorErrors();

		if (model == null){
			errors.addError(new GeneratorError(getName() + ": No Model element found within XMI file"));
			return errors;
		}
		
		validatePackages(model, errors);

		return errors;
	}
	
	private void validatePackages(UMLModel model, GeneratorErrors errors) {
		Hashtable<String, Collection<UMLClass>> packages = new Hashtable<String, Collection<UMLClass>>();
		try {
			setModelNamespace(model,transformerUtils.getBasePkgLogicalModel(),errors);//if set, override default Namespace prefix
			transformerUtils.collectPackages(transformerUtils.getAllClasses(model), packages,namespaceUriPrefix);

			if (packages == null || packages.isEmpty()){
				errors.addError(new GeneratorError(getName() + ": No qualifying packages found within the model to process"));
			}
		} catch (GenerationException ge) {
			log.error("ERROR: ", ge);
			errors.addError(new GeneratorError(getName() + ": " + ge.getMessage(), ge));
		}
		
		for (Enumeration<String> e = packages.keys(); e.hasMoreElements();) {
			String pkgName = (String)e.nextElement();
			Collection<UMLClass> klasses = (Collection<UMLClass>)packages.get(pkgName);
			log.debug("pkg.getName: " + pkgName + " has " + klasses.size() + " classes");
			
			UMLPackage pkg=null;
			for (Iterator<UMLClass> i = klasses.iterator(); i.hasNext();) {
				UMLClass klass = (UMLClass) i.next();
				pkg=klass.getPackage();
				break;
			}
			validateGmePackageTags(pkg, errors);
			validateNamespacePrefix(pkg, errors);
			validateClasses(klasses,errors);
		}
	}

	private void validateClasses(Collection<UMLClass> classes, GeneratorErrors errors) {
		
		if (classes != null){
			for(UMLClass klass1:classes)
			{
				validateGmeClassTags(klass1, errors);

				String klassName=getFQCN(klass1,errors);

				if(klassName.length()==0)
					errors.addError(new GeneratorError(getName() + ": GME Class name is empty: "+klassName + " for class " + klass1.getName()));
				if(klassName.indexOf(' ')>0)
					errors.addError(new GeneratorError(getName() + ": GME Class/package name contains empty spaces: "+klassName + " for class " + klass1.getName()));
				if(klassName.indexOf("..")>0)
					errors.addError(new GeneratorError(getName() + ": GME Class name contains an empty package name: "+klassName + " for class " + klass1.getName()));
				if(klassName.indexOf('-')>0)
					errors.addError(new GeneratorError(getName() + ": GME Class/package name contains an invalid hyphen ('-'): "+klassName + " for class " + klass1.getName()));				
				if(klassName.length() >0 && !Character.isLetter(klassName.charAt(0)))
					errors.addError(new GeneratorError(getName() + ": GME Class name starts with a non-character value: "+klassName));
				for(UMLClass klass2:classes)
				{
					String klass2Name=getFQCN(klass2,errors);

					if(klass1!=klass2 && klassName!=null && klassName.equals(klass2Name))
						errors.addError(new GeneratorError(getName() + ": Duplicate class found for "+klassName));					
				}
				validateClass(klass1, errors);
			}
		}
	}

	private void validateClass(UMLClass klass,GeneratorErrors errors) {
		validateSuperClass(klass, errors);
		validateAttributes(klass, errors);
		validateAssociations(klass, errors);
	}

	private void validateAssociations(UMLClass klass, GeneratorErrors errors) 
	{
		for(UMLAssociation association: klass.getAssociations())
		{
			try 
			{
				List <UMLAssociationEnd>ends = association.getAssociationEnds();
				UMLAssociationEnd thisEnd = transformerUtils.getThisEnd(klass, ends);
				UMLAssociationEnd otherEnd = transformerUtils.getOtherEnd(klass, ends);
				
				validateGmeAssociationTags(thisEnd,otherEnd,errors);

				String thisEndRolename = getRoleName(thisEnd,getClassName((UMLClass)thisEnd.getUMLElement(),errors),errors);
				String otherEndRolename = getRoleName(otherEnd,getClassName((UMLClass)thisEnd.getUMLElement(),errors),errors);

				//For sake of clarity, use the non-GME classnames so the user can easily identify which classes have an invalid association
				String thisClassName = transformerUtils.getFQCN(((UMLClass)thisEnd.getUMLElement()));
				String otherClassName = transformerUtils.getFQCN(((UMLClass)otherEnd.getUMLElement()));

				if(!transformerUtils.isIncluded((UMLClass)otherEnd.getUMLElement()))// TODO :: need to create a isGMEIncluded method?
					errors.addError(new GeneratorError(getName() + ": GME Association rolename '" +otherEndRolename+ "' belongs to the not included or excluded package for association between "+thisClassName +" and "+ otherClassName));
				if(thisEndRolename!=null && thisEndRolename.length()>0 && !Character.isLetter(thisEndRolename.charAt(0)))
					errors.addError(new GeneratorError(getName() + ": GME Association rolename '" +thisEndRolename+ "' starts with non-character value for association between "+thisClassName +" and "+ otherClassName));
				if(otherEndRolename!=null && otherEndRolename.length()>0 && !Character.isLetter(otherEndRolename.charAt(0)))
					errors.addError(new GeneratorError(getName() + ": GME Association rolename '" +otherEndRolename+ "' starts with non-character value for association between "+thisClassName +" and "+ otherClassName));
				if((thisEnd.isNavigable() && (thisEndRolename==null || thisEndRolename.trim().length()==0 ))|| (otherEnd.isNavigable() && (otherEndRolename==null || otherEndRolename.trim().length()==0 )))
					errors.addError(new GeneratorError(getName() + ": GME Association end name not specified for association between "+thisClassName +" and "+ otherClassName));
				if((thisEndRolename!= null && thisEndRolename.indexOf(' ') > 0 ) || (otherEndRolename!= null && otherEndRolename.indexOf(' ') > 0 ))
					errors.addError(new GeneratorError(getName() + ": GME Association end name contains empty spaces for association between "+thisClassName +" and "+ otherClassName));

				if(thisClassName.startsWith("java.lang") || thisClassName.startsWith("java.util"))
					errors.addError(new GeneratorError(getName() + ": Association to the wrapper class not allowed for association between "+thisClassName +" and "+ otherClassName));
				if(otherClassName.startsWith("java.lang") || otherClassName.startsWith("java.util"))
					errors.addError(new GeneratorError(getName() + ": Association to the wrapper class not allowed for association between "+thisClassName +" and "+ otherClassName));

				if (otherEnd.isNavigable())
				{
					for(UMLAssociation assoc: klass.getAssociations())
					{
						List <UMLAssociationEnd>ends2 = assoc.getAssociationEnds();
						if(ends!=ends2)
						{
							UMLAssociationEnd thisEnd2 = transformerUtils.getThisEnd(klass, ends2);
							UMLAssociationEnd otherEnd2 = transformerUtils.getOtherEnd(klass, ends2);
							String thisClassName2 = transformerUtils.getFQCN(((UMLClass)thisEnd2.getUMLElement()));
							String otherClassName2 = transformerUtils.getFQCN(((UMLClass)otherEnd2.getUMLElement()));	

							String otherEnd2Rolename=getRoleName(otherEnd2,otherClassName2,errors);
							log.debug("otherEnd.getRoleName(): "+ otherEnd.getRoleName()+"; otherEnd2.getRoleName(): "+otherEnd2.getRoleName()+"; otherEnd2Rolename: "+otherEnd2Rolename);
							if(otherEndRolename!=null && otherEndRolename.equals(otherEnd2Rolename))
								errors.addError(new GeneratorError(getName() + ": Duplicate GME association between "+thisClassName2 +" and "+ otherClassName2));
						}
					}
				}

			}
			catch (GenerationException e) 
			{
				errors.addError(new GeneratorError(getName() + ": GME Association validation failed", e));
			}
		}
	}

	private void validateAttributes(UMLClass klass, GeneratorErrors errors) 
	{
		String thisClassName = transformerUtils.getFQCN(klass);
		for(UMLAttribute attribute: klass.getAttributes())
		{
			validateGmeAttributeTags(klass,attribute,errors);
			String attributeName=getAttributeName(attribute,errors);

			if(attributeName==null || attributeName.trim().length()==0)
				errors.addError(new GeneratorError(getName() + ": GME Attribute name empty in class "+thisClassName+", attribute " + attribute.getName()));
			if(attributeName!=null && attributeName.indexOf(' ')>0)
				errors.addError(new GeneratorError(getName() + ": GME Attribute name contains empty spaces in class "+thisClassName+", attribute " + attribute.getName() + ": "+attributeName));
			if(attributeName.length()>1 && Character.isLowerCase(attributeName.charAt(0)) && Character.isUpperCase(attributeName.charAt(1)))
				errors.addError(new GeneratorError(getName() + ": GME Attribute name contains first character lower case and second character as upper case in "+thisClassName+", attribute " + attribute.getName() + ": "+attributeName+". This combination is currently not supported."));
			if(attributeName.length() >0 && !Character.isLetter(attributeName.charAt(0)))
				errors.addError(new GeneratorError(getName() + ": GME Attribute name starts with a non-character value in "+thisClassName+", attribute " + attribute.getName() + ": "+attributeName));

			String attributeName2=null;
			for(UMLAttribute attr: klass.getAttributes()) {
				attributeName2 = getAttributeName(attr,errors);
				if(attr!=attribute &&  attributeName2!=null && attributeName2.equals(attributeName))
					errors.addError(new GeneratorError(getName() + ": Duplicate GME attribute names found in the "+thisClassName+" class :"+attributeName2));
			}
		}
	}

	private void validateSuperClass(UMLClass klass, GeneratorErrors errors) {
		try {
			UMLClass superKlass = transformerUtils.getSuperClass(klass);
			if(superKlass!=null && !transformerUtils.isIncluded(superKlass))
				errors.addError(new GeneratorError(getName() + ": Parent of the class "+transformerUtils.getFQCN(klass)+" belongs to the not included or excluded package"));

		} catch (GenerationException e) {
			errors.addError(new GeneratorError(getName() + ": Superclass validation failed", e));
		}
	}

	private String getPackageName(UMLPackage pkg, GeneratorErrors errors){
		String namespacePkgName = null;
		try {
			namespacePkgName = transformerUtils.getGMEPackageName(pkg);
			if (namespacePkgName!=null)
				return namespacePkgName;
		} catch(GenerationException ge) {
			errors.addError(new GeneratorError(getName() + ": Error getting the GME package name for package: " + transformerUtils.getFullPackageName(pkg), ge));
		}
		return transformerUtils.getFullPackageName(pkg);
	}

	private String getClassName(UMLClass klass, GeneratorErrors errors) {
		try {
			String klassName = transformerUtils.getXMLClassName(klass);
			if (klassName!=null)
				return klassName;
		} catch(GenerationException ge) {
			errors.addError(new GeneratorError(getName() +": Error getting the GME Class (XML Element) name for klass: " + transformerUtils.getFQCN(klass), ge));
		}
		return klass.getName();
	}

	private String getFQCN(UMLClass klass, GeneratorErrors errors){
		String packageName=getPackageName(klass.getPackage(),errors);
		String klassName=getClassName(klass,errors);
		
		if(klassName==null || klassName.trim().length()==0)
			return "";

		if(packageName==null || packageName.trim().length()==0)
			return klassName.trim();
		
		return packageName.trim()+"."+klassName.trim();
	}

	private String getAttributeName(UMLAttribute attr,GeneratorErrors errors){
		try {
			String attrName = transformerUtils.getXMLAttributeName(attr);
			if (attrName!=null)
				return attrName;
		} catch(GenerationException ge) {
			errors.addError(new GeneratorError(getName()+": Error getting the GME Attribute (XML Loc Ref) name for attribute: " + attr.getName(), ge));
			return null;
		}
		return attr.getName();
	}

	private String getRoleName(UMLAssociationEnd assocEnd, String klassName, GeneratorErrors errors){
		try {
			String rolename = transformerUtils.getXMLLocRef(assocEnd,klassName);//try GME class name
			
			if (rolename==null){// try non-GME class name
				klassName = ((UMLClass)(assocEnd.getUMLElement())).getName();
				rolename = transformerUtils.getXMLLocRef(assocEnd, klassName);
			}
			
			if (rolename!=null)
				return rolename;
		} catch(GenerationException ge) {
			errors.addError(new GeneratorError(getName()+": Error getting the GME rolename (XML Source or Target Location Reference) name for association roleName: " + assocEnd.getRoleName(), ge));
		}
		return assocEnd.getRoleName();
	}

	public void validateGmeClassTags(UMLClass klass,GeneratorErrors errors)
	{
		if (transformerUtils.hasGMEXMLAttributeTag(klass)){
			errors.addError(new GeneratorError(getName()+": Invalid GME NCI_GME_XML_LOC_REF tag found on klass: " + transformerUtils.getFQCN(klass)+".  This tag is only valid on a UML Attribute element"));
		}
		
		if (transformerUtils.hasGMELocRefTag(klass)){
			errors.addError(new GeneratorError(getName()+": Invalid GME TV_NCI_GME_SOURCE_XML_LOC_REF or TV_NCI_GME_TARGET_XML_LOC_REF tag found on klass: " + transformerUtils.getFQCN(klass)+".  These tags are only valid on a UML Attribute element"));
		}
	}
	

	public void validateGmePackageTags(UMLPackage pkg,GeneratorErrors errors)
	{
		if (transformerUtils.hasGMEXMLClassTag(pkg)){
			errors.addError(new GeneratorError(getName()+": Invalid GME NCI_GME_XML_ELEMENT tag found on package: " + transformerUtils.getFullPackageName(pkg)+".  This tag is only valid on a UML Attribute element"));
		}		

		if (transformerUtils.hasGMEXMLAttributeTag(pkg)){
			errors.addError(new GeneratorError(getName()+": Invalid GME NCI_GME_XML_LOC_REF tag found on package: " + transformerUtils.getFullPackageName(pkg)+".  This tag is only valid on a UML Attribute element"));
		}
		
		if (transformerUtils.hasGMELocRefTag(pkg)){
			errors.addError(new GeneratorError(getName()+": Invalid GME TV_NCI_GME_SOURCE_XML_LOC_REF or TV_NCI_GME_TARGET_XML_LOC_REF tag found on package: " + transformerUtils.getFullPackageName(pkg)+".  These tags are only valid on a UML Attribute element"));
		}
	}
	
	private void validateNamespacePrefix(UMLPackage pkg,GeneratorErrors errors) {
		
		String pkgNamespacePrefix=null;
		
		try {
			pkgNamespacePrefix = transformerUtils.getNamespacePrefix(pkg);
		} catch (GenerationException ge) {
			log.error("ERROR: ", ge);
			errors.addError(new GeneratorError(getName()+": Error getting the GME Namespace tag value for package: " + transformerUtils.getFullPackageName(pkg), ge));
		}
		
		if (pkgNamespacePrefix==null) //use default namespace
			pkgNamespacePrefix = namespaceUriPrefix;
		
		//Check that the namespacePrefix property has been set
		if (pkgNamespacePrefix == null){
			errors.addError(new GeneratorError(getName()+": Namespace prefix has not been set for package "+transformerUtils.getFullPackageName(pkg)+".  Use format:  gme://<<Classification Scheme (UML Project ShortName)>>.<<Context>>/<<Classification Scheme Version>>/<<<Classification Scheme Item> (Optional)>>"));
		}

		//Check that the NAMESPACE_PREFIX property format is correct
		StringTokenizer tokens = new StringTokenizer(pkgNamespacePrefix, "/");
		log.debug("Namespace prefix ('/') tokenizer count: " + tokens.countTokens());
		
		if (!tokens.hasMoreTokens() || tokens.countTokens() < 3){
			errors.addError(new GeneratorError(getName()+": Namespace prefix "+pkgNamespacePrefix+" is invalid for package "+transformerUtils.getFullPackageName(pkg)+".  Use format:  gme://<<Classification Scheme (UML Project ShortName)>>.<<Context>>/<<Classification Scheme Version>>/<<<Classification Scheme Item> (Optional)>>"));
		}
	}
	

	public void validateGmeAttributeTags(UMLClass klass,UMLAttribute attr,GeneratorErrors errors)
	{
		String klassName = transformerUtils.getFQCN(klass);
		String attributeName = attr.getName();
		

		if (transformerUtils.hasGMEXMLNamespaceTag(attr)){
			errors.addError(new GeneratorError(getName()+": Invalid GME NCI_GME_XML_NAMESPACE tag found on the attribute: "+attributeName+" of class: "+klassName+". This tag is only valid on a UML Package element"));
		}

		if (transformerUtils.hasGMEXMLClassTag(attr)){
			errors.addError(new GeneratorError(getName()+": Invalid GME NCI_GME_XML_ELEMENT tag found on the attribute: "+attributeName+" of class: "+klassName+". This tag is only valid on a UML Class element"));
		}
		
		if (transformerUtils.hasGMELocRefTag(klass)){
			errors.addError(new GeneratorError(getName()+": Invalid GME TV_NCI_GME_SOURCE_XML_LOC_REF or TV_NCI_GME_TARGET_XML_LOC_REF tag found on the attribute: "+attributeName+" of class: "+klassName+". This tag is only valid on a UML Association element"));
		}

	}

	public void validateGmeAssociationTags(UMLAssociationEnd thisEnd, UMLAssociationEnd otherEnd,GeneratorErrors errors)
	{
		UMLAssociation assoc = thisEnd.getOwningAssociation();
		String thisKlassName = transformerUtils.getFQCN((UMLClass)(thisEnd.getUMLElement()));
		String otherKlassName = transformerUtils.getFQCN((UMLClass)(otherEnd.getUMLElement()));
		

		if (transformerUtils.hasGMEXMLNamespaceTag(assoc)){
			errors.addError(new GeneratorError(getName()+": Invalid GME NCI_GME_XML_NAMESPACE tag found on the association between " +thisKlassName+" and "+otherKlassName+". This tag is only valid on a UML Package element"));
		}

		if (transformerUtils.hasGMEXMLClassTag(assoc)){
			errors.addError(new GeneratorError(getName()+": Invalid GME NCI_GME_XML_ELEMENT tag found on the association between " +thisKlassName+" and "+otherKlassName+". This tag is only valid on a UML Class element"));
		}
		
		if (transformerUtils.hasGMEXMLAttributeTag(assoc)){
			errors.addError(new GeneratorError(getName()+": Invalid GME NCI_GME_XML_LOC_REF tag found on the association between " +thisKlassName+" and "+otherKlassName+". This tag is only valid on a UML Attribute element"));
		}
		
		try {
			String tv = transformerUtils.getGmeSourceLocRef(assoc);
			if (tv !=null){
				if ((tv.endsWith("/"+((UMLClass)(thisEnd.getUMLElement())).getName()) || tv.endsWith("/"+getClassName((UMLClass)(thisEnd.getUMLElement()),errors) )) 
						|| (tv.endsWith("/"+((UMLClass)(otherEnd.getUMLElement())).getName()) || tv.endsWith("/"+getClassName((UMLClass)(otherEnd.getUMLElement()),errors))) )
					;
				else
					errors.addError(new GeneratorError(getName()+": Invalid GME NCI_GME_SOURCE_XML_LOC_REF tag value found on the association between " +thisKlassName+" and "+otherKlassName+": "+tv+".  The class referenced within the tag value does not match either of the association end classes"));
			}
		} catch (GenerationException ge) {
			errors.addError(new GeneratorError(getName()+": Error getting the GME NCI_GME_SOURCE_XML_LOC_REF tag value from the association between " +thisKlassName+" and "+otherKlassName,ge));
		}

		try {
			String tv = transformerUtils.getGmeTargetLocRef(assoc);
			if (tv !=null){
				if ((tv.endsWith("/"+((UMLClass)(thisEnd.getUMLElement())).getName()) || tv.endsWith("/"+getClassName((UMLClass)(thisEnd.getUMLElement()),errors) )) 
						|| (tv.endsWith("/"+((UMLClass)(otherEnd.getUMLElement())).getName()) || tv.endsWith("/"+getClassName((UMLClass)(otherEnd.getUMLElement()),errors))) )
					;
				else
					errors.addError(new GeneratorError(getName()+": Invalid GME NCI_GME_SOURCE_XML_LOC_REF tag value found on the association between " +thisKlassName+" and "+otherKlassName+": "+tv+".  The class referenced within the tag value does not match either of the association end classes"));
			}
		} catch (GenerationException ge) {
			errors.addError(new GeneratorError(getName()+": Error getting the GME NCI_GME_TARGET_XML_LOC_REF tag value from the association between " +thisKlassName+" and "+otherKlassName,ge));
		}
	}
	
	private void setModelNamespace(UMLModel model, String basePkgLogicalModel,GeneratorErrors errors){
			//override deploy.properties NAMESPACE_PREFIX property with GME namespace tag value, if it exists

			StringTokenizer tokenizer = new StringTokenizer(basePkgLogicalModel, ".");
			UMLPackage pkg=null;
			if(tokenizer.hasMoreTokens()){
				pkg = model.getPackage(tokenizer.nextToken());
				
				while(pkg!=null && tokenizer.hasMoreTokens()){
					pkg = pkg.getPackage(tokenizer.nextToken());
				}
			}

			if (pkg==null){
				errors.addError(new GeneratorError(getName() + ": Error getting the Logical Model package for model: " + pkg.getName()+". Make sure the LOGICAL_MODEL property in deploy.property is valid."));
			}
			
			if (pkg!=null){
				log.debug("* * * pkgName: " + pkg.getName());
				try {
					String modelNamespacePrefix = transformerUtils.getNamespace(pkg);
					log.debug("* * * modelNamespacePrefix: " + modelNamespacePrefix);
					if (modelNamespacePrefix != null) {
						if (!modelNamespacePrefix.endsWith("/"))
							modelNamespacePrefix=modelNamespacePrefix+"/";
						this.namespaceUriPrefix = modelNamespacePrefix.replace(" ", "_");
					}
				} catch (GenerationException ge) {
					log.error("ERROR: ", ge);
					errors.addError(new GeneratorError(getName() + ": Error getting the GME Namespace value for model: " + pkg.getName(), ge));
				}
			}
		}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param namespaceUriPrefix e.g.: gme://caCORE.caCORE/3.2
	 */
	public void setNamespaceUriPrefix(String namespaceUriPrefix) {
		this.namespaceUriPrefix = namespaceUriPrefix.replace(" ", "_");
	}

}