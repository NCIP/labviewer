package gov.nih.nci.codegen.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.nih.nci.codegen.GenerationException;
import gov.nih.nci.codegen.GeneratorError;
import gov.nih.nci.codegen.GeneratorErrors;
import gov.nih.nci.codegen.Validator;
import gov.nih.nci.codegen.util.TransformerUtils;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociation;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociationEnd;
import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLGeneralization;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;

import org.apache.log4j.Logger;

public class WritableApiValidator implements Validator {

	private static Logger log = Logger.getLogger(WritableApiValidator.class);
	private static final String CASCADE_DELETE_ORPHAN = "delete-orphan";

	private boolean enabled = true;

	private String name = WritableApiValidator.class.getName();

	private TransformerUtils transformerUtils;

	public void setTransformerUtils(TransformerUtils transformerUtils) {
		this.transformerUtils = transformerUtils;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Boolean isEnabled() {
		return enabled;
	}
	
	public GeneratorErrors validate(UMLModel model) {
		GeneratorErrors errors = new GeneratorErrors();
		if (model == null){
			errors.addError(new GeneratorError(getName() + ": No Model element found within XMI file"));
			return errors;
		}		
		
		Collection<UMLClass> classes=new ArrayList<UMLClass>();
		try {
			classes = transformerUtils.getAllParentClasses(model);
		} catch (GenerationException ge) {
			errors.addError(new GeneratorError("Error getting all the parent classes",ge));
		}
		for(UMLClass klass:classes)
			validateWritableApi(model, klass, errors);
		return errors;
	}

	private void validateWritableApi(UMLModel model, UMLClass klass,GeneratorErrors errors){
		validateCascadeMany2OneMapping(model,klass,errors);
		validateInheritanceSubClass(model,klass,errors);
		validateBidirectionalImplicitMapping(model,klass,errors);		
	}
	
	private void validateCascadeMany2OneMapping(UMLModel model, UMLClass klass,GeneratorErrors errors){
		UMLClass currentKlass = klass;
		do {
			for (UMLAssociation association : currentKlass.getAssociations()) {
				try {					
					List <UMLAssociationEnd>ends = association.getAssociationEnds();
					UMLAssociationEnd thisEnd = transformerUtils.getThisEnd(currentKlass, ends);
					UMLAssociationEnd otherEnd = transformerUtils.getOtherEnd(currentKlass, ends);					
					if (otherEnd.isNavigable()) {
						if(transformerUtils.isMany2One(thisEnd,otherEnd)){
							UMLClass assocKlass = (UMLClass)otherEnd.getUMLElement();
							String cascadeStyle = transformerUtils.findCascadeStyle(currentKlass, otherEnd.getRoleName(), association);
							if(cascadeStyle.equals(CASCADE_DELETE_ORPHAN)){
								errors.addError(new GeneratorError(getName() + ": Cascade-style setting delete-orphan is not supported on the Many-to-One association between " + transformerUtils.getFQCN(currentKlass) + " and " + transformerUtils.getFQCN(assocKlass) +".  remove  the NCI_CASCADE_ASSOCIATION Tag Value "+CASCADE_DELETE_ORPHAN+" from the association."));
							}
						}
					}
				}catch (GenerationException e) {
					errors.addError(new GeneratorError(getName()+ ": validateCascadeMany2OneMapping validation failed ", e));
				}
			} // for
		} while(currentKlass!=null && transformerUtils.isImplicitParent(currentKlass));
	}
	
	private void validateInheritanceSubClass(UMLModel model, UMLClass klass,GeneratorErrors errors) {
		if(!hasMultipleSubClass(klass)) return;
		
		log.debug("validate writable api inheritance subClass for klass: " +klass.getName());
		String fqcn = transformerUtils.getFQCN(klass);
		log.debug("fqcn: " + fqcn);
		try {
			String discriminatorColumnName = transformerUtils.findDiscriminatingColumnName(klass);		
			log.debug("discriminatorColumnName: " + discriminatorColumnName);
			if(!(discriminatorColumnName== null || "".equals(discriminatorColumnName))){				
				for (UMLGeneralization gen : klass.getGeneralizations()) {
					UMLClass subKlass = (UMLClass)gen.getSubtype();
					log.debug("subKlass: " + subKlass.getName());
					if (subKlass != klass) {
						String subFqcn = transformerUtils.getFQCN(subKlass);
						log.debug("subFqcn: " + subFqcn);
							for (UMLAssociation association : subKlass.getAssociations()) {
								List<UMLAssociationEnd> ends = association.getAssociationEnds();
								UMLAssociationEnd thisEnd = transformerUtils.getThisEnd(subKlass, ends);
								UMLAssociationEnd otherEnd = transformerUtils.getOtherEnd(subKlass, ends);								
								if(otherEnd.isNavigable()){
									UMLClass assocKlass = (UMLClass)otherEnd.getUMLElement();
									String otherEndKlassName = transformerUtils.getFQCN(assocKlass);
									if(transformerUtils.isMany2One(thisEnd,otherEnd)){
										if (otherEnd.getLowMultiplicity()> 0) {
											errors.addError(new GeneratorError(getName() + ": Many-to-One association between "+subFqcn+" and "+otherEndKlassName+" with low multiplicity =1 is not supported. In table per inheritance hierarchy with more than one subclass,if one of them have association with other table,the multiplicity must be 0..1"));
										}
									}
								}
							}
					}
				}
			}
		} catch (GenerationException e) {
			e.printStackTrace();
			errors.addError(new GeneratorError(getName()+ ": validateInheritanceSubClass validation failed for " + fqcn+ " class", e));
		}		
	}
	
	private void validateBidirectionalImplicitMapping(UMLModel model,UMLClass klass, GeneratorErrors errors) {
		UMLClass currentKlass = klass;
		do {
			for (UMLAssociation association : currentKlass.getAssociations()) {
				try {				
					List <UMLAssociationEnd>ends = association.getAssociationEnds();
					UMLAssociationEnd thisEnd = transformerUtils.getThisEnd(currentKlass, ends);
					UMLAssociationEnd otherEnd = transformerUtils.getOtherEnd(currentKlass, ends);					
					UMLClass assocKlass = (UMLClass)otherEnd.getUMLElement();
					if (otherEnd.isNavigable()) {
						if (transformerUtils.isMany2Any(thisEnd, otherEnd)) {
							if (thisEnd.isNavigable()) {
								if (transformerUtils.isMany2Many(otherEnd,thisEnd)) {
									errors.addError(new GeneratorError(getName() + ": Implicit polymorphic bi-directional association between class " + currentKlass.getName() + " and implicit parent class " + assocKlass.getName() +" is not supported.Bidirectional <many-any> and <many-many> mapping is not supported .Please change to unidirectional <many-any> association between "+currentKlass.getName()+" and "+assocKlass.getName()+"."));
								}
							}
						}
						
						if (transformerUtils.isAny(thisEnd, otherEnd)) {
							if (thisEnd.isNavigable()) {
								if (transformerUtils.isOne2Many(otherEnd,thisEnd)) {
									errors.addError(new GeneratorError(getName() + ": Implicit polymorphic bi-directional association between class " + currentKlass.getName() + " and implicit parent class " + assocKlass.getName() +" is not supported.Bidirectional <any> and <one-many> mapping is not supported .Please change to unidirectional <any> association between "+currentKlass.getName()+" and "+assocKlass.getName()+"."));
								}
							}
						}
					}
				} catch (GenerationException e) {
					errors.addError(new GeneratorError(getName()+ ": validateBidirectionalImplicitMapping validation failed ", e));
				}
			} // for
		} while(currentKlass!=null && transformerUtils.isImplicitParent(currentKlass));
	}

	private Boolean hasMultipleSubClass(UMLClass klass) {
		if (klass.getGeneralizations().size() == 0)
			return false;
		int count = 0;
		for (UMLGeneralization gen : klass.getGeneralizations()) {
			UMLClass subKlass = (UMLClass) gen.getSubtype();
			if (subKlass != klass) {
				count++;
			}
			if (count >= 2)
				return true;
		}
		return false;
	}
}