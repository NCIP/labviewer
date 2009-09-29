package gov.nih.nci.system.dao.orm.translator;

import gov.nih.nci.system.query.nestedcriteria.NestedCriteria;
import gov.nih.nci.system.util.ClassCache;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class Path2NestedCriteria{
	private static Logger log = Logger.getLogger(Path2NestedCriteria.class);

	public static NestedCriteria createNestedCriteria(String path, List objList, ClassCache classCache) throws Exception {
		
		List<String> pathList = new ArrayList<String>();
		StringTokenizer tokens = new StringTokenizer(path, ",");
		while (tokens.hasMoreTokens()) {
			pathList.add(tokens.nextToken().trim());
		}
		
		NestedCriteria criteria = null;
		Stack<String> stack1 = new Stack<String>();

		for (int i = 0; i < pathList.size(); i++)
			stack1.push(pathList.get(i));

		String stackTop = (String) stack1.peek();
		String example = (objList.get(0)).getClass().getName();

		if (!stackTop.equals(example))
			stack1.push(example);

		if (stack1.size() == 1)
			stack1.push(stack1.peek());

		while (stack1.size() > 1) {
			String src = (String) stack1.pop();
			String dest = (String) stack1.pop();
			NestedCriteria newCriteria = new NestedCriteria();
			String roleName = null;
			String sourceRoleName = null;
			NestedCriteria newCriteria2 = new NestedCriteria();
			if (!dest.equals(src) && !hasInheritent(src, dest)) {
				roleName = getRoleName(newCriteria, Class.forName(src), Class
						.forName(dest).newInstance(), classCache);
				if (roleName == null)
					throw new Exception("No association found from " + src
							+ " to " + dest
							+ ", please double check your query path.");
				sourceRoleName = getRoleName(newCriteria2, Class.forName(dest), Class.forName(src).newInstance(), classCache);
			}

			newCriteria.setSourceObjectName(src);
			newCriteria.setTargetObjectName(dest);
			newCriteria.setRoleName(roleName);
			newCriteria.setSourceRoleName(sourceRoleName);
			newCriteria.setSourceCollection(newCriteria2.isTargetCollection());
			newCriteria.setInternalNestedCriteria(criteria);
			if (criteria == null)
				newCriteria.setSourceObjectList(objList);
			criteria = newCriteria;
			stack1.push(dest);
		}
		return criteria;
	}
	
	private static boolean hasInheritent(String sourceName, String targetName) {
		try {
			
			Class superclass=Class.forName(sourceName).getSuperclass();
			while (superclass !=null){
				if (superclass.getName().equals(targetName))
					return true;
				superclass=superclass.getSuperclass();
			}
			
			superclass=Class.forName(targetName).getSuperclass();
			while (superclass !=null){
				if (superclass.getName().equals(sourceName))
					return true;
				superclass=superclass.getSuperclass();
			}
			
			return false;
		} catch (Exception e) {
			log.error("Exception: ", e);
			return false;
		}
	}

	private static String getRoleName(NestedCriteria criteria,
			Class searchClass, Object criterion, ClassCache classCache)
			throws Exception {

		String criterionClassName = criterion.getClass().getName();
		String roleName = null;
		Field[] fields = searchClass.getDeclaredFields();

		if (searchClass.getSuperclass() != null)
			roleName = getRoleName(criteria, searchClass.getSuperclass(),
					criterion, classCache);

		if (roleName != null)
			return roleName;
		try {
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);

				String fieldName = fields[i].getName();
				String fieldType = fields[i].getType().getName();
				Class typeClass = fields[i].getType();

				if (!typeClass.isPrimitive()) {
					if (!typeClass.isArray()) {
						if (isCollectionType(typeClass)) {
							String returnType = classCache
									.getReturnType(fields[i].getGenericType()
											.toString());
							if ((returnType != null)
									&& (returnType.equals(criterionClassName))) {
								roleName = fieldName;
								criteria.setTargetCollection(true);
								break;
							}

						} else {
							if (fieldType.equals(criterionClassName)) {
								roleName = fieldName;
								break;
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			throw ex;
		}
		return roleName;
	}

	private static boolean isCollectionType(Class inputClass) {
		if (inputClass.getName().equals("java.util.Collection"))
			return true;

		Class[] interfaces = inputClass.getInterfaces();
		for (int i = 0; i < interfaces.length; i++)
			if (interfaces[i].getName().equals("java.util.Collection"))
				return true;
		return false;
	}
}