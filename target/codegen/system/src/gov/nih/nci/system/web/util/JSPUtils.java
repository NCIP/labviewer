/**
 * Created on May, 2007
 *
 */
package gov.nih.nci.system.web.util;

import gov.nih.nci.system.util.ClassCache;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Dan Dumitru
 */

public class JSPUtils {
	private static Logger log = Logger.getLogger(JSPUtils.class.getName());

	private static JSPUtils jspUtils;
	private static boolean secured;

	private ClassCache classCache;

	public JSPUtils(ServletContext context) {
		WebApplicationContext ctx = WebApplicationContextUtils
		.getWebApplicationContext(context);
		this.classCache = (ClassCache) ctx.getBean("ClassCache");
		Properties systemProperties = (Properties)ctx.getBean("WebSystemProperties");
		String securityEnabled = (String)systemProperties.getProperty("securityEnabled");
		secured = "yes".equalsIgnoreCase(securityEnabled) || "true".equalsIgnoreCase(securityEnabled);
	}

	/**
	 * Instantiate JSPUtils
	 * @param context
	 * @return JSPUtils
	 */
	synchronized public static JSPUtils getJSPUtils(ServletContext context) {
		try {
			if (jspUtils == null)
				jspUtils = new JSPUtils(context);
		} catch (Exception e) {
			log.error("Exception caught getting a handle to JSPUtils: ", e);
		}

		return jspUtils;
	}

	/**
	 * Get all the packages
	 * @return all packages
	 */
	public List<String> getPackageNames() {
		List<String> pkgNames = classCache.getAllPackageNames();
		return pkgNames;
	}

	/**
	 * Get all the class names within a package
	 * @param packageName
	 * @return list of class names
	 */
	public List<String> getClassNames(String packageName) {
		List<String> pkgClassNames = null;

		if (packageName == null || packageName.equalsIgnoreCase("All")) {
			pkgClassNames = classCache.getAllUnqualClassNames();
			//Collections.sort(pkgClassNames);  // Comes sorted already
			return pkgClassNames;
		}

		pkgClassNames = classCache.getPkgClassNames(packageName);
		Collections.sort(pkgClassNames);
		return pkgClassNames;
	}

	/**
	 * Get the list of all fields for the class
	 * @param className
	 * @return List of all fields for the given class
	 */
	public List<String> getAllFields(String className) {
		return classCache.getAllFieldNames(className);
	}

	/**
	 * Get the list of all searchable Fields for the class
	 * @param className
	 * @return Field[] of all fields for the given class
	 */
	public List<Field> getSearchableFields(String className) {
		Field[] fields = null;
		List<Field> searchableFields = new ArrayList<Field>();

		try {
			Class klass = classCache.getClassFromCache(className);
			log.debug("Retrieved " + className + " from cache");

			fields = classCache.getAllFields(klass);
			String fieldType;

			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				fieldType = fields[i].getType().getName();

				if (classCache.isSearchable(fieldType)) {
					searchableFields.add(fields[i]);
				}

			}
		} catch (ClassNotFoundException e) {
			// Do nothing.  Abstract class names have been purposely modified 
			// with a " (abstract)" suffix on the UI, and thus are no longer found in the cache
			// This is intentional; abstract classes cannot be used as a target or 
			// search criteria object since they cannot be instantiated via Class.forName().newInstance();
			log.debug("Searchable fields not found for class: " + className + ".  This warning can be safely ignored if the class is abstract or an interface.");
		} catch (Exception e) {
			log.error("Exception caught generating a list of searchable fields for class " + className, e);
		}
		return searchableFields;
	}

	/**
	 * @param className
	 * @return A list of associations for className
	 * @throws Exception
	 */
	public List<String> getAssociations(String className) throws Exception {
		return classCache.getAssociations(className);
	}
	
	public boolean isSecurityEnabled()
	{
		return secured;
	}
}
