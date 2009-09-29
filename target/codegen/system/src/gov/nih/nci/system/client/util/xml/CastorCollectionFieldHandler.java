package gov.nih.nci.system.client.util.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.GeneralizedFieldHandler;

/**
 * The FieldHandler for the Date class
 *
 */
public class CastorCollectionFieldHandler
extends BaseCastorFieldHandler
{

	private static Logger log = Logger.getLogger(CastorCollectionFieldHandler.class);

	/**
	 * Creates a new MyDateHandler instance
	 */
	public CastorCollectionFieldHandler() {
		super();
		setCollectionIteration(false);
//		log.debug("CastorCollectionFieldHandler() initialized");
	}


	public Object convertUponGet(Object value) {
//		log.debug("*** convertUponGet(Object value) called ***");
//		log.debug("Value: " + value);
//		log.debug("Value.class: " + value.getClass().getName());

		if (value == null) return null;

		String setMethodName, getMethodName;

		Class klass;
		Method[] methods;
		Method tempMethod;
		Object tempObject = null;

		java.util.Collection<Object> tempCollection = new ArrayList<Object>();
		ArrayList<Object> tempList = new ArrayList<Object>();
		Object[] args = {tempList};
		Class[] parameterTypes = {Collection.class};
//		log.debug("args array initialized: " + args[0].getClass().getName());       

		Enumeration collIterator = (Enumeration)value;

		// convert the collection objects from proxy to domain objects
		while (collIterator.hasMoreElements()){
			try {
				tempCollection.add(CastorCollectionFieldHandler.convertObject((Object)collIterator.nextElement(), false));
			} catch (Exception e) {
				log.error("Exception caught trying to convert proxy object to domain object: " + e.getMessage());
				e.printStackTrace();
			}
		}

		Iterator iter = tempCollection.iterator();
		while (iter.hasNext()){  
			tempObject = iter.next();
			klass = tempObject.getClass();
			methods = klass.getMethods();

//			log.debug("Number of methods: " + methods.length);

			for (int i=0; i < methods.length; i++){

				tempMethod = methods[i];

//				log.debug("tempMethod[" + i + "].getName(): " + tempMethod.getName());
//				log.debug("tempMethod[" + i + "].getReturnType().getName(): " + tempMethod.getReturnType().getName());

				// 'Erase' any collection attributes in order to prevent recursion 
				if ("java.util.Collection".equalsIgnoreCase(tempMethod.getReturnType().getName())){
					try {

						getMethodName = tempMethod.getName();
//						log.debug("getMethodName: " + getMethodName);
						setMethodName = 's' + getMethodName.substring(1);
//						log.debug("setMethodName: " + setMethodName);

						tempMethod = klass.getMethod(setMethodName, parameterTypes);
						tempMethod.invoke(tempObject, args);
//						log.debug("Successful: Collection Attribute set to empty ArrayList for method " + tempMethod.getName());

					} catch (Exception e) {
						log.error("Exception: " + e.getMessage());
						log.error("Unsuccessful:  Collection Attribute NOT set to empty ArrayList for method " + tempMethod.getName());
					}
				}
			}
		}

//		log.debug("*** final tempCollection.size(): " + tempCollection.size());
		if (tempCollection.size() == 0){return null;}
		return tempCollection;
	}


	/**
	 * This method is used to convert the value when the
	 * setValue method is called. The setValue method will
	 * call this method to obtain the converted value.
	 * The converted value will then be used as the value to
	 * set for the field.
	 *
	 * @param value the object value to convert before
	 *  performing a set operation
	 * @return the converted value.
	 */
	public Object convertUponSet(Object value) {
//		log.debug("*** convertUponSet() called - do nothing ***");
//		log.debug("*** Value: " + value);
//		log.debug("*** Value class: " + value.getClass().getName());
//		log.debug("*** Value size: " + ((ArrayList)value).size());
		return value;
	}

	/**
	 * Returns the class type for the field that this
	 * GeneralizedFieldHandler converts to and from. This
	 * should be the type that is used in the
	 * object model.
	 *
	 * @return the class type of the field
	 */
	public Class getFieldType() {
//		log.debug("*** Domain getFieldType() - returning null ***");
		return null;
	}

	/**
	 * Creates a new instance of the object described by
	 * this field.
	 *
	 * @param parent The object for which the field is created
	 * @return A new instance of the field's value
	 * @throws IllegalStateException This field is a simple
	 *  type and cannot be instantiated
	 */
	public Object newInstance( Object parent )
	throws IllegalStateException
	{
//		log.debug("*** newInstance() called ***");
//		log.debug("**** setValue parent.getClass().getName(): " + parent.getClass().getName());
		return null;
	}
}
