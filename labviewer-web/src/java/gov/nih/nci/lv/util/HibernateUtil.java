/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.util;
import org.hibernate.Session;

/**
 * Example implementation of HibernateUtil.
 */

public class HibernateUtil {
    private static final LVHibernateHelper HIBERNATE_HELPER = new HibernateHelper();
    private static LVHibernateHelper testHelper = null;

    /**
     * Get the hibernate helper.
     * @return the helper.
     */
    public static LVHibernateHelper getHibernateHelper() {
        if (testHelper != null) {
            return testHelper;
        }
        return HIBERNATE_HELPER;
    }

    /**
     * Get the current session.
     * @return the session.
     */
    public static Session getCurrentSession() {
        return getHibernateHelper().getCurrentSession();
    }

    /**
     * @param testHelper the testHelper to set
     */
    public static void setTestHelper(LVHibernateHelper testHelper) {
        HibernateUtil.testHelper = testHelper;
    }
}

