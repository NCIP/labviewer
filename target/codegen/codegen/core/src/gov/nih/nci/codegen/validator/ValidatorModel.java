package gov.nih.nci.codegen.validator;

import java.util.Iterator;
import java.util.Map;

public class ValidatorModel {

	private Map<String,ValidatorClass> classes;
	private static final String NL = "\n";
	private static final String TAB = "\t";

	public ValidatorModel(Map<String,ValidatorClass> classes) {
		this.classes = classes;
	}

	public String toString() {
		StringBuilder retValue = new StringBuilder();
		retValue.append("HV Model ( ").append(NL);
		
		if (classes != null && !classes.isEmpty()){	
			Iterator iter = classes.keySet().iterator();
			String key;
			while(iter.hasNext()) {
				key = (String)iter.next();
				retValue.append(TAB).append(key).append("=").append(classes.get(key)).append(NL);
			}
		}

		retValue.append(" )").append(NL);

		return retValue.toString();
	}

	public ValidatorClass getClass(String className) {
		return classes.get(className);
	}
	
}
