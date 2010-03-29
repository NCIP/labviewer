package gov.nih.nci.ctom.ctlab.junits;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import gov.nih.nci.ctom.ctlab.domain.Activity;
import gov.nih.nci.ctom.ctlab.domain.AdverseEvent;
import gov.nih.nci.ctom.ctlab.domain.CentralLaboratory;
import gov.nih.nci.ctom.ctlab.domain.ClinicalResult;
import gov.nih.nci.ctom.ctlab.domain.ConceptDescriptor;
import gov.nih.nci.ctom.ctlab.domain.HealthCareSite;
import gov.nih.nci.ctom.ctlab.domain.Identifier;
import gov.nih.nci.ctom.ctlab.domain.Investigator;
import gov.nih.nci.ctom.ctlab.domain.Observation;
import gov.nih.nci.ctom.ctlab.domain.Participant;
import gov.nih.nci.ctom.ctlab.domain.PerformingLaboratory;
import gov.nih.nci.ctom.ctlab.domain.Procedure;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.ProtocolStatus;
import gov.nih.nci.ctom.ctlab.domain.Specimen;
import gov.nih.nci.ctom.ctlab.domain.SpecimenCollection;
import gov.nih.nci.ctom.ctlab.domain.StudyParticipantAssignment;
import gov.nih.nci.ctom.ctlab.domain.StudyTimePoint;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;



public class DTOTest {
	
	private static Map DEFAULT_ARGUMENTS = new HashMap(); 
	
	@Test
	public void testProperties() {
		createDefaultArguments();
	  assertGetterSetterBehavior(new Activity());
	  assertGetterSetterBehavior(new AdverseEvent());
	  assertGetterSetterBehavior(new CentralLaboratory());
	  assertGetterSetterBehavior(new ClinicalResult());
	  assertGetterSetterBehavior(new ConceptDescriptor());
	  assertGetterSetterBehavior(new HealthCareSite());
	  assertGetterSetterBehavior(new Identifier());
	  assertGetterSetterBehavior(new Investigator());
	  assertGetterSetterBehavior(new Observation());
	  assertGetterSetterBehavior(new Participant());
	  assertGetterSetterBehavior(new PerformingLaboratory());
	  assertGetterSetterBehavior(new Procedure());
	  assertGetterSetterBehavior(new Protocol());
	  assertGetterSetterBehavior(new ProtocolStatus());
	  assertGetterSetterBehavior(new Specimen());
	  assertGetterSetterBehavior(new SpecimenCollection());
	  assertGetterSetterBehavior(new StudyParticipantAssignment());
	  assertGetterSetterBehavior(new StudyTimePoint());
	  	  
	  
	}

	/**
	 * The method introspects the target object, finding read/write properties, and tests the getter
	 * and setter.
	 * @param target the object on which to invoke the getter and setter
	 */
	public static void assertGetterSetterBehavior(Object target) {
	    try {
	        BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
	        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
	        for (PropertyDescriptor descriptor : descriptors) {
	            if (descriptor.getWriteMethod() == null) {
	                continue;
	            }
	            assertGetterSetterBehavior(target, descriptor.getDisplayName(),null);
	        }
	    }
	    catch (IntrospectionException e) {
	        fail("Failed while introspecting target " + target.getClass());
	    }
	}
	/**
	 * The method accepts an explicit argument for the setter method.
	 *
	 * @param target   the object on which to invoke the getter and setter
	 * @param property the property name, e.g. "firstName"
	 * @param argument the property value, i.e. the value the setter will be invoked with
	 */
	public static void assertGetterSetterBehavior(Object target, String property, Object argument) {
	    try {
	        PropertyDescriptor descriptor = new PropertyDescriptor(property, target.getClass());
	        Method writeMethod = descriptor.getWriteMethod();
	        Method readMethod = descriptor.getReadMethod();
	        Object arg = argument;

	        if (arg == null) {
	            Class type = descriptor.getPropertyType();
	            if (DEFAULT_ARGUMENTS.containsKey(type.getName())) {
	                arg = DEFAULT_ARGUMENTS.get(type.getName());
	            }
	        }    
   	        writeMethod.invoke(target, arg);
	        Object propertyValue = readMethod.invoke(target);
	        assertSame(property + " getter/setter test passed", arg, propertyValue);
	        
	    }
	    catch (IntrospectionException e) {
	        String msg = "Error creating PropertyDescriptor for property [" + property +
	                "]. Do you have a getter and a setter?";
	      
	        fail(msg);
	    }
	    catch (IllegalAccessException e) {
	        String msg = "Error accessing property. Are the getter and setter both accessible?";
	       
	        fail(msg);
	    }
	    catch (InvocationTargetException e) {
	        String msg = "Error invoking method on target";
	        fail(msg);
	       
	    }
	}
	
	
	/**
	 * creates default types and arguments.
	 */
	private static void createDefaultArguments(){
		Long default_long=1l;
		Boolean default_bool= true;
		DEFAULT_ARGUMENTS.put("java.lang.String", "test");
		DEFAULT_ARGUMENTS.put("java.lang.Long", default_long);
		DEFAULT_ARGUMENTS.put("java.lang.Boolean", default_bool);
		DEFAULT_ARGUMENTS.put("java.util.Date", new Date());
	}

}
