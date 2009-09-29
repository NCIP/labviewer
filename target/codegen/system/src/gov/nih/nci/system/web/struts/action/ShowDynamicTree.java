package gov.nih.nci.system.web.struts.action;

import gov.nih.nci.system.web.ajax.tree.Category;
import gov.nih.nci.system.web.util.JSPUtils;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class ShowDynamicTree extends BaseActionSupport {

	private static final long serialVersionUID = 1234567890L;

	private static Logger log = Logger.getLogger(ShowDynamicTree.class
			.getName());

	public String execute() throws Exception {

		return SUCCESS;
	}

	public Category getTreeRootNode() {
		return Category.getById(1L);
	}

	public Category getClassTreeRootNode() {
		log.debug("Getting Class Tree Root Node");

		JSPUtils jspUtils = JSPUtils.getJSPUtils(servletContext);

		String packageName;
		String className;
		List<String> packageNames = jspUtils.getPackageNames();
		log.debug("packages: " + packageNames);
		List classNames = new ArrayList();

		Map<String,Category> packages = new HashMap<String,Category>();
		List<Category> classes = new ArrayList<Category>();
		List<Category> interfaces = new ArrayList<Category>();

		int pos = 0;
		int categoryId = 1;
		String klassName = null;

		Category root = new Category(categoryId++, getText("showdynamictree.root.title"), "",new Category[0]);
		if (packageNames != null) {

			Category tmpPackageCat = null;
			Category tmpClassCat = null;


			for (int i = 0; i < packageNames.size(); i++) {

				packageName = (String) packageNames.get(i);
				log.debug("packageName(" + i + "): " + packageName);

				classNames = jspUtils.getClassNames(packageName);
				if (classNames != null) {
					for (int j = 0; j < classNames.size(); j++) {
						className = (String) classNames.get(j);
						log.debug("className(" + j + "): " + className);

						pos = className.lastIndexOf(".");

						klassName = className.substring(pos + 1);
						if (klassName != null && klassName.length() <= 0) {
							//in case there is no package
							klassName = className;
						}
						
						try {
							Class klass = Class.forName(className);
							
							categoryId = collectInterfaceCategories(klass, interfaces, categoryId);//need to preserve current value of categoryId
							
							if (Modifier.isAbstract(klass.getModifiers())) {
								klassName += " (abstract)";
							}

						} catch (ClassNotFoundException e) {
							log.error("Trying to determine if class " + klassName + " is abstract or has interface(s), but was not able to find such a class", e);
						}	
						
						tmpClassCat = new Category(categoryId++, klassName, packageName, new Category[0]);
						log.debug("About to add Category: " + tmpClassCat);
						
						classes.add(tmpClassCat);
					}
				}

				tmpPackageCat = new Category(categoryId++, packageName, "", classes);

				packages.put(packageName,tmpPackageCat);
				
				//Reset classes array for the next package
				classes = new ArrayList<Category>();

			}

		
			//merge interfaces with the classes
			Category tmpCategory = null;
			List children = null;
			for (Category interfazeCat : interfaces){
				tmpCategory = packages.get(interfazeCat.getPackageName());
				
				if (tmpCategory != null){
					children = tmpCategory.getChildren();
					children.add(interfazeCat);
					tmpCategory.setChildren(children);
				} else {// new package
					List<Category> tmpList = new ArrayList<Category>();
					tmpList.add(interfazeCat);
					tmpPackageCat = new Category(categoryId++, interfazeCat.getPackageName(), "", tmpList);
					packages.put(interfazeCat.getPackageName(),tmpPackageCat);
				}
			}
			List<Category> values = new ArrayList<Category>();
			values.addAll(new TreeMap(packages).values());//sort by key
			root.setChildren(values);
		}

		return root;

		// Here as an Example:

		// return new Category(1L, "Root", "", new Category[] {
		// new Category(2L, "Java", "", new Category[] {
		// new Category(3L, "Web Frameworks", "", new Category[] {
		// new Category(4L, "Struts", "", new Category[0]), new Category(7L,
		// "Stripes", "", new Category[0]), new Category(8L, "Rife", "", new
		// Category[0])
		// }), new Category(9L, "Persistence", "", new Category[] {
		// new Category(10L, "iBatis", "", new Category[0]), new Category(11L,
		// "Hibernate", "", new Category[0]), new Category(12L, "JDO", "", new
		// Category[0]), new Category(13L, "JDBC", "", new Category[0])
		// })
		// }), new Category(14L, "JavaScript", "", new Category[] {
		// new Category(15L, "Dojo", "", new Category[0]), new Category(16L,
		// "Prototype", "", new Category[0]), new Category(17L, "Scriptaculous",
		// "", new Category[0]), new Category(18L, "OpenRico", "", new
		// Category[0]), new Category(19L, "DWR", "", new Category[0])
		// })
		// });
	}
	
	private int collectInterfaceCategories(Class klass, List<Category> interfaces, int categoryId){
		
		Category tmpInterfaceCat = null;
		for (Class interfaze : klass.getInterfaces()){
			
			Class superInterfaze = interfaze;
			do {
				if (!(superInterfaze.getSimpleName().equalsIgnoreCase("Serializable"))){
					String interfacePackageName = superInterfaze.getPackage().getName();
					tmpInterfaceCat = new Category(categoryId++, superInterfaze.getSimpleName() + " (interface)", interfacePackageName, new Category[0]);
					log.debug("About to add Category: " + tmpInterfaceCat);

					interfaces.add(tmpInterfaceCat);
				}
				superInterfaze = superInterfaze.getSuperclass();
			} while (superInterfaze != null);
		}
		
		return categoryId;
	}

}
