package gov.nih.nci.codegen.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class ValidatorConstraint {

	private static Logger log = Logger.getLogger(ValidatorConstraint.class);
	private String validatorClassName; 
	private Map<String,String> constraintProperties;

	public ValidatorConstraint(String validatorClass, Map<String,String> constraintProperties) {
		this.validatorClassName=validatorClass;
		this.constraintProperties = constraintProperties;
	}
	
	public ValidatorConstraint(String validatorClass) {
		this.validatorClassName=validatorClass;
		this.constraintProperties = new HashMap<String,String>();
	}
	
	public String getValidatorClassName() {
		return validatorClassName;
	}

	public String getAnnotationString() {
		StringBuilder retValue = new StringBuilder();
		retValue.append(getAnnotationClassName());
		
		if (constraintProperties != null && !constraintProperties.isEmpty()){
			retValue.append("(");
			
			Iterator<String> iter = constraintProperties.keySet().iterator();
			String key = (String)iter.next();
			retValue.append(key).append("=").append(constraintProperties.get(key));
			while(iter.hasNext()) {
				key = (String)iter.next();
				retValue.append(",").append(key).append("=").append(constraintProperties.get(key));
			}
			retValue.append(")");
		}

		return retValue.toString();
	}
	
	public Collection<String> getXSDRestrictionValues() {
		Collection<String> constraintCollection = new ArrayList<String>();
		if (constraintProperties != null && !constraintProperties.isEmpty()){
			Iterator<String> iter = constraintProperties.keySet().iterator();
			String key=null;
			while(iter.hasNext()) {
				key = (String)iter.next();
				if (key.equalsIgnoreCase("regex"))
					constraintCollection.addAll(getConstraintValues(constraintProperties.get(key)));
			}
		}
		return constraintCollection;
	}
	
	private Collection<String> getConstraintValues(String constraintValueStr) {
		Collection<String> constraintCollection = new ArrayList<String>();
		
		if (constraintValueStr==null || constraintValueStr.length()==0)
			return constraintCollection;
		
		int beginIndex=constraintValueStr.indexOf('(');
		int endIndex=constraintValueStr.indexOf(')');
		
		if ((beginIndex < 0) || (endIndex < 0))
			return constraintCollection;
		
		constraintValueStr=constraintValueStr.substring(beginIndex+1, endIndex);
		log.debug("* * * constraintValueStr: "+constraintValueStr);
		
		StringTokenizer tokens = new StringTokenizer(constraintValueStr, "|");
		
		while (tokens.hasMoreTokens()){
			String constraintValue = tokens.nextToken();
			log.debug("* * * constraintValue: "+constraintValue);
			constraintCollection.add(constraintValue);
		}
		
		return constraintCollection;
	}
	
	private String getAnnotationClassName(){
		if (validatorClassName == null) return "";
		
		return "@" + validatorClassName.substring(validatorClassName.lastIndexOf(".")+1);
	}
	
}
