package gov.nih.nci.codegen.validator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ValidatorClass {

	private String name; 
	private Map<String,ValidatorAttribute> attributes;
	private List<ValidatorConstraint> classConstraints;
	
	private static final String NL = "\n";
	private static final String TAB = "\t";

	public ValidatorClass(String name,
			Map<String, ValidatorAttribute> attributes,
			List<ValidatorConstraint> classConstraints) {
		this.name = name;
		this.attributes = attributes;
		this.classConstraints = classConstraints;
	}

	public String toString() {
		StringBuilder retValue = new StringBuilder();
		retValue.append("HV Class ( ")
				.append(NL).append(TAB).append("name = ").append(this.name).append(NL);
		
		Iterator iter = attributes.keySet().iterator();
		String key;
		while(iter.hasNext()) {
			key = (String)iter.next();
			retValue.append(TAB).append(TAB).append(attributes.get(key)).append(NL);
		}

		retValue.append(" )").append(NL);

		return retValue.toString();
	}
	
	public ValidatorAttribute getAttribute(String attributeName) {
		return attributes.get(attributeName);
	}
	
	public String getConstraintAnnotationString(){
		StringBuilder retValue = new StringBuilder();
		for(ValidatorConstraint constraint: classConstraints){
			retValue.append(NL).append(constraint.getAnnotationString());
		}
		
		return retValue.toString();
	}
	
	public Set<String> getConstraintImports() {
		HashSet<String> set = new HashSet<String>();
		
		//add attribute-level constraint imports
		Iterator iter = attributes.keySet().iterator();
		String key;
		while(iter.hasNext()) {
			key = (String)iter.next();
			set.addAll(attributes.get(key).getConstraintImports());
		}
		
		//add class-level constraint imports
		for (ValidatorConstraint classConstraint : classConstraints){
			set.add(classConstraint.getValidatorClassName());
		}
		
		return set;
	}
}
