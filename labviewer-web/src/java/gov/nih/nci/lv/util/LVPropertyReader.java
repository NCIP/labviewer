/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.util;

import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Property reader class util.
 * @author NAmiruddin
 *
 */
public class LVPropertyReader {

    private static Properties props = null;
    private static final String RESOURCE_NAME = "lv.properties";
    private static final Logger LOG = Logger.getLogger(LVPropertyReader.class);

    static {
        try {
            props = new Properties();
            props.load(LVPropertyReader.class.getClassLoader().getResourceAsStream(RESOURCE_NAME));
        } catch (Exception e) {
            LOG.error("Unable to read lv.properties ", e);
            throw new IllegalStateException(e);
        }
    }
    /**
     *
     * @param propertyName name of the property
     * @return property value
     * @throws LVException when the property name is not found
     */
    public static String getPropertyValue(String propertyName) throws LVException {
        String value = props.getProperty(propertyName);
        if (value == null) {
            throw new LVException(propertyName + " does not have value in LV.properties");
        }
        LOG.debug("Property name " + propertyName + " Property value = " + value);
        return value.trim();
    }

    /**
     * resets the property .
     */
    public static void resetProperty() {
        try {
            LOG.debug("Property value has been reset");
            props = new Properties();
            props.load(LVPropertyReader.class.getClassLoader().getResourceAsStream(RESOURCE_NAME));
        } catch (Exception e) {
            LOG.error("Unable to read lv.properties ", e);
            throw new IllegalStateException(e);
        }
    }

    /**
     *
     * @return props property ;
     */
    public static Properties getProperty() {
        return props;
    }
}
