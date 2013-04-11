/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.lv.util;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteAuthorizationAccessException;

import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 *
 * @author NAmiruddin
 *
 */
public class ExceptionHandlerInterceptor  implements Interceptor  {

    private static Logger logger = Logger.getLogger(ExceptionHandlerInterceptor.class);

    /**
     *
     * @param invocation inv
     * @return String
     * @throws Exception on error
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> session = invocation.getInvocationContext().getSession();
        try {
            return invocation.invoke();
        }  catch (SuiteAuthorizationAccessException sae) {
            logger.error("SuiteAuthorizationAccessException", sae);
            return storeErrorMsg(invocation , session , sae.getMessage() , sae.getStackTrace());
        } catch (Exception e) {
            logger.error("Exception", e);
            return storeErrorMsg(invocation , session , e.getMessage() , e.getStackTrace());
        } catch (Throwable t) {
            logger.error("Throwable", t);
            return storeErrorMsg(invocation , session , t.getMessage() , t.getStackTrace());
        }
    }
    /**
     * {@inheritDoc}
     */
    public void destroy() {
     // do nothings
    }
    /**
     * {@inheritDoc}
     */
    public void init() {
     // do nothings
    }

    private String storeErrorMsg(ActionInvocation invocation , Map<String, Object> session ,
            String errorMsg , StackTraceElement[] stackTrace) {
        if (session != null) {
            session.put(LVConstants.FAILURE_MESSAGE, errorMsg);
            session.put(LVConstants.FAILURE_DETAILED_MESSAGE, getDetailedMessage(stackTrace));
            invocation.getInvocationContext().setSession(session);
        }
        return "gen_error";
    }

    private String getDetailedMessage(StackTraceElement[] stackTrace) {
        StringBuffer detailedMsg = new StringBuffer();
        int i = 0;
        for (StackTraceElement ste : stackTrace) {
            detailedMsg.append(ste.toString());
            if (i++ > LVConstants.NUM_10) {
                break;
            }

        }
        if (detailedMsg.length() > 1) {
            return detailedMsg.toString();
        } else {
            return null;
        }

    }


}
