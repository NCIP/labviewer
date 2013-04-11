/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.viewobjects;

public enum NumericResultFilter {
	ALL("All"), 
	IN_RANGE("In Range"),
	OUT_OF_RANGE("Out of Range");
	
	

	 private String code;
   /**
    * 
    * @param code
    */
   private NumericResultFilter(String code) {
       this.code = code;
      
   }
   /**
    * @return code code
    */
   public String getCode() {
       return code;
   }

   /**
    * 
    * @return String name
    */
   public String getName() {
       return name();
   }

    /**
    * @return String[] display names of enums
    */
   public static String[]  getDisplayNames() {
	   NumericResultFilter[] l = NumericResultFilter.values();
       String[] a = new String[l.length];
       for (int i = 0; i < l.length; i++) {
           a[i] = l[i].getCode();
       }
       return a;
   }
}
