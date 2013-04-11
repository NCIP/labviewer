/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.xwork.StringUtils;
import org.iso._21090.II;
import org.iso._21090.ST;

/**
 *
 * @author Naveen Amiruddin
 *
 */
public class LVUtils {

    /**
     * converts a string array to string concat with a , to be used in a sql.
     * @param ids ids
     * @param delimiter delimiter
     * @return String
     */
    public static String convertListToStringConcat(List<String> ids , String delimiter) {
        StringBuffer data = new StringBuffer();
        for (String id : ids) {
            data.append("'" + id + "'" + delimiter);
        }
        return StringUtils.removeEnd(data.toString(), delimiter);
    }
    /**
     * converts a string array to string concat with a , to be used in a sql.
     * @param ids ids
     * @param delimiter delimiter
     * @return String
     */
    public static String convertListToNumberConcat(List<Long> ids , String delimiter) {
        StringBuffer data = new StringBuffer();
        for (Long id : ids) {
            data.append(id + delimiter);
        }
        return StringUtils.removeEnd(data.toString(), delimiter);
    }

    /**
     * converts a ids seperated by a comma to a set.
     * @param ids ids
     * @param delimiter delimiter
     * @return Set
     */
    public static Set<Long> convertStringToSet(String ids , String delimiter) {
        Set<Long> labs = new HashSet<Long>();
        if (StringUtils.isEmpty(ids)) {
            return labs;
        }
        StringTokenizer st = new StringTokenizer(ids, delimiter);
        while (st.hasMoreTokens()) {
            labs.add(new Long(st.nextElement().toString()));
        }
        return labs;
    }

    /**
     * converts a ids seperated by a comma to a set.
     * @param ids ids
     * @param delimiter delimiter
     * @return Set
     */
    public static List<Long> convertStringToList(String ids , String delimiter) {
        List<Long> labs = new ArrayList<Long>();
        if (StringUtils.isEmpty(ids)) {
            return labs;
        }
        StringTokenizer st = new StringTokenizer(ids, delimiter);
        while (st.hasMoreTokens()) {
            labs.add(new Long(st.nextElement().toString()));
        }
        return labs;
    }

    /**
     * returns true if iso ii is null.
     * @param ii ii
     * @return boolean
     */
    public static boolean isIINull(II ii) {
        if (ii == null || ii.getExtension() == null || ii.getNullFlavor() != null) {
            return true;
        }
        return false;
    }

    /**
     * returns true if iso st is null.
     * @param st ST
     * @return boolean
     */
    public static boolean isSTNull(ST st) {
        if (st == null || st.getValue() == null || st.getNullFlavor() != null) {
            return true;
        }
        return false;
    }

}
