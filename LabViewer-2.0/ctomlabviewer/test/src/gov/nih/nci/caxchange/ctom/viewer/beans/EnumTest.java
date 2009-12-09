/**
 * 
 */
package gov.nih.nci.caxchange.ctom.viewer.beans;


import static org.junit.Assert.assertEquals;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.DateRangeFilter;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.NumericResultFilter;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author asharma
 *
 */
public class EnumTest extends TestCase
{
	

	
		@Test
		public void test()
		{
			testMethods(DateRangeFilter.class);
			testMethods(NumericResultFilter.class);
			
			
		}
		
		
		 public <C, T > void testMethods(Class<T> clazz){
			
			 if (clazz.getName().equals("gov.nih.nci.caxchange.ctom.viewer.viewobjects.DateRangeFilter")){			
				 String[] x = DateRangeFilter.getDisplayNames();
				 assertEquals(DateRangeFilter.values().length, x.length);
				 assertEquals("ALL_DATES_TIMES", DateRangeFilter.ALL_DATES_TIMES.getName());
				 assertEquals("All dates/times",DateRangeFilter.ALL_DATES_TIMES.getCode()); 
						 
				
			}
			 if (clazz.getName().equals("gov.nih.nci.caxchange.ctom.viewer.viewobjects.NumericResultFilter")){			
				 String[] x = NumericResultFilter.getDisplayNames();
			     assertEquals(NumericResultFilter.values().length, x.length);
			     assertEquals("IN_RANGE", NumericResultFilter.IN_RANGE.getName());
			     assertEquals("In Range", NumericResultFilter.IN_RANGE.getCode());
				
			}
			
		 } 

}
