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
