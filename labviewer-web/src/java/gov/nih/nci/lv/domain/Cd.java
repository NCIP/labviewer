/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Cd class.
 * @author Naveen Amiruddin
 *
 */
@Entity
@Table(name = "CONCEPT_DESCRIPTOR")
public class Cd extends AbstractId {

     private String code;
     private String codeSystem;     
     private String codeSystemName;
     private String codeSystemVersion;
     private String displayName;
     /**
     * Retreives the value of code attribute.
     * @return code
     **/
     public String getCode() {
         return code;
     }

     /**
     * Sets the value of code attribue.
     * @param code 
     **/
     public void setCode(String code) {
         this.code = code;
     }
     

     /**
     * Retreives the value of codeSystem attribute.
     * @return codeSystem
     **/
     @Column(name = "CODE_SYSTEM")
     public String getCodeSystem() {
         return codeSystem;
     }
     /**
      * 
      * @param codeSystem codeSystem
      */
     public void setCodeSystem(String codeSystem) {
         this.codeSystem = codeSystem;
     }
     
     /**
     * Retreives the value of codeSystemName attribute. 
     * @return codeSystemName
     **/
     @Column(name = "CODE_SYSTEM_NAME")
     public String getCodeSystemName() {
         return codeSystemName;
     }

     /**
     * Sets the value of codeSystemName attribue.
     * @param codeSystemName codeSystemName
     **/
     public void setCodeSystemName(String codeSystemName) {
         this.codeSystemName = codeSystemName;
     }
     
     
     /**
     * Retreives the value of codeSystemVersion attribute.
     * @return codeSystemVersion
     **/
     @Column(name = "CODE_SYSTEM_VERSION")
     public String getCodeSystemVersion() {
         return codeSystemVersion;
     }
     /**
     * Sets the value of codeSystemVersion attribue.
     * @param codeSystemVersion codeSystemVersion
     **/
     public void setCodeSystemVersion(String codeSystemVersion) {
         this.codeSystemVersion = codeSystemVersion;
     }
     
     
     /**
     * Retreives the value of displayName attribute.
     * @return displayName
     **/
     @Column(name = "DISPLAY_TEXT")
     public String getDisplayName() {
         return displayName;
     }

     /**
     * Sets the value of displayName attribue.
     * @param displayName displayName
     **/
     public void setDisplayName(String displayName) {
         this.displayName = displayName;
     }
    
}
