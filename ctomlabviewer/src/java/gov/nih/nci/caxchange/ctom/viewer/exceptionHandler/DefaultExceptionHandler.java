/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.exceptionHandler;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

/**
 * This is a default exception handler class.
 * 
 * @author Kunal Modi (Ekagra Software Technologies Ltd.)
 */
public class DefaultExceptionHandler extends ExceptionHandler
{

	static final Logger log =
			Logger.getLogger(DefaultExceptionHandler.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.ExceptionHandler#execute(java.lang.Exception,
	 *      org.apache.struts.config.ExceptionConfig,
	 *      org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(Exception ex, ExceptionConfig ae,
			ActionMapping mapping, ActionForm formInstance,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		HttpSession session = request.getSession();
		String errMsg = getErrorMsg(ex);
		log.error(errMsg, ex);
		session.setAttribute(DisplayConstants.ERROR_DETAILS, errMsg);
		if (log.isDebugEnabled())
			if (session != null)
				log.debug(session.getId()
						+ "|"
						+ ((LoginForm) session
								.getAttribute(DisplayConstants.LOGIN_OBJECT))
								.getLoginId() + "|"
						+ formInstance.getClass().getName()
						+ "|execute|Failure|System Exception occurred||"
						+ ex.getMessage());
			else
				log.debug("|||execute|Failure|System Exception occurred||"
						+ ex.getMessage());

		return super.execute(ex, ae, mapping, formInstance, request, response);

	}

	/**
	 * @param ex
	 *            the Exception.
	 * @return the string of the error message.
	 */
	public String getErrorMsg(Exception ex)
	{
		String msg = "Exception was NULL";

		if (ex != null)
		{
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			PrintWriter pw = new PrintWriter(bo, true);
			ex.printStackTrace(pw);
			msg =
					"Unhandled Exception occured in UPT \n" + "Message: "
							+ ex.getMessage() + "\n" + "StackTrace: "
							+ bo.toString();
		}

		return msg;
	}
}
