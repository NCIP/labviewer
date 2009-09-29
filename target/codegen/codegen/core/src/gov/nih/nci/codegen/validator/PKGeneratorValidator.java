package gov.nih.nci.codegen.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import gov.nih.nci.codegen.GenerationException;
import gov.nih.nci.codegen.GeneratorError;
import gov.nih.nci.codegen.GeneratorErrors;
import gov.nih.nci.codegen.Validator;
import gov.nih.nci.codegen.util.TransformerUtils;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;

import org.apache.log4j.Logger;

public class PKGeneratorValidator implements Validator {

	private static Logger log = Logger.getLogger(PKGeneratorValidator.class);

	private boolean enabled = true;

	private String name = PKGeneratorValidator.class.getName();

	private Map<String,PKGeneratorProperty> pkGeneratorPropertyMap;
	
	private TransformerUtils transformerUtils;
	
	public void setTransformerUtils(TransformerUtils transformerUtils) {
		this.transformerUtils = transformerUtils;
	}
	
    public void setPkGeneratorPropertyMap(
			Map<String, PKGeneratorProperty> pkGeneratorPropertyMap) {
		this.pkGeneratorPropertyMap = pkGeneratorPropertyMap;
	}
   
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public Boolean isEnabled() {
		return enabled;
	}
	
	public GeneratorErrors validate(UMLModel model) {
		GeneratorErrors errors = new GeneratorErrors();

		if (model == null) {
			errors.addError(new GeneratorError(getName()+ ": no model element found within XMI file"));
			return errors;
		}	
		Collection<UMLClass> classes=new ArrayList<UMLClass>();
		try {
			classes = transformerUtils.getAllParentClasses(model);
		} catch (GenerationException ge) {
			errors.addError(new GeneratorError("Error getting all the parent classes",ge));
		}
		for (UMLClass klass : classes) {
			try {
				validatePKGeneratorTag(klass, errors);
			} catch (GenerationException e) {
				errors.addError(new GeneratorError("error validating pkgenerator domain class "+klass.getName() + ". error message : "+ e.getMessage()));
			}
		}
		return errors;
	}
	
	private GeneratorErrors validatePKGeneratorTag(UMLClass klass,GeneratorErrors errors) throws GenerationException{
		String databaseType = transformerUtils.getDatabaseType();
		String pkgenClassKey = TransformerUtils.TV_PK_GENERATOR + databaseType;
		
		UMLAttribute classIdAttr = transformerUtils.getClassIdAttr(klass);
		String fqcn = transformerUtils.getFQCN(klass);
		UMLClass table = transformerUtils.getTable(klass);
		String tableName = table.getName();
		
		if (classIdAttr == null) {
			errors.addError(new GeneratorError("No attribute found that maps to the primary key identifier for class : "+fqcn));
			return errors;
		}

		log.debug("* * * table: "+table+"; tableName: "+tableName+"; fqcn: "+fqcn+"; classIdAttr: "+classIdAttr);
		
		UMLAttribute tableIdAttribute=transformerUtils.getMappedColumn(table,fqcn+"."+classIdAttr.getName());
		
		Map<String, String> inputPkGeneratorParams = transformerUtils.getPKGeneratorTags(table, fqcn, classIdAttr);
		String pkColumnName=tableIdAttribute.getName();
		String pkDataType =transformerUtils.getHibernateDataType(klass,classIdAttr);
		
		Collection<UMLTaggedValue> taggedValues=tableIdAttribute.getTaggedValues();
		String pkGeneratorClass = transformerUtils.getTagValue(taggedValues, pkgenClassKey, 1);
		PKGeneratorProperty generatorProperty=pkGeneratorPropertyMap.get(pkGeneratorClass);
		
		log.debug("processing table " + tableName+ " pkGeneratorClass Property validator info "+ generatorProperty);
		log.debug("pkObject class datatype " + pkDataType + " database Type "+ databaseType + "  primary key Id "
				+ pkColumnName + " pkgenerator params "+ inputPkGeneratorParams.toString());
		
		if(generatorProperty==null){
			errors.addError(new GeneratorError("invalid primary key generator class name "+pkGeneratorClass+" for table "+tableName+" with column "+pkColumnName));
		}else{
			if(!(generatorProperty.getDatabaseTypes().contains(databaseType))){			
				errors.addError(new GeneratorError("invalid database "+databaseType+" for table "+tableName+" with column "+pkColumnName));
			}
			if(!(generatorProperty.getDataTypes().contains(pkDataType))){
				errors.addError(new GeneratorError("invalid dataType "+ pkDataType + " for table "+ tableName + " with column " + pkColumnName));
			}
			
			String syswidePKGeneratorTag = TransformerUtils.PK_GENERATOR_SYSTEMWIDE+databaseType;
			String modelPkGeneratorTag = TransformerUtils.TV_PK_GENERATOR+databaseType;
			
			inputPkGeneratorParams.remove(syswidePKGeneratorTag);
			inputPkGeneratorParams.remove(modelPkGeneratorTag);
			if (!inputPkGeneratorParams.keySet().containsAll(generatorProperty.getPkGeneratorParams())) {						
				errors.addError(new GeneratorError("invalid params "+ inputPkGeneratorParams.keySet().toString()+ ". required params "+generatorProperty.getPkGeneratorParams().toString()+" for primary key generator class "+ pkGeneratorClass + " in table " + tableName+ " with column " + pkColumnName));
			}
		}
		return errors;
	}
}