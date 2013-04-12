/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.cabig.ctms.lookandfeel.demo;

import gov.nih.nci.cabig.ctms.tools.spring.ControllerUrlResolver;
import gov.nih.nci.cabig.ctms.tools.spring.ResolvedControllerReference;

/**
 * @author Rhett Sutphin
 */
public class IdentityUrlResolver implements ControllerUrlResolver {
    public ResolvedControllerReference resolve(String controllerBeanName) {
        return new ResolvedControllerReference(
            controllerBeanName, "String.class", "", controllerBeanName);
    }
}
