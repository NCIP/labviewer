/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.lv.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * A formalized group of persons or other organizations collected together for a common purpose (such as administrative.
 * legal, political) and the infrastructure to carry out that purpose.
 * @author Naveen Amiruddin
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Lab_Hub_Organization")
public class Organization extends AbstractId {
     private String identifier;
     private String name;

     /**
     * Retreives the value of identifier attribute.
     * @return identifier
     **/
     public String getIdentifier() {
         return identifier;
     }
     /**
     * Sets the value of identifier attribue.
     * @param identifier identifier
     **/
     public void setIdentifier(String identifier) {
         this.identifier = identifier;
     }
     /**
     * Retreives the value of name attribute.
     * @return name
     **/
     public String getName() {
         return name;
    }

    /**
    * Sets the value of name attribue.
    * @param name name
    **/
    public void setName(String name) {
        this.name = name;
    }
}
