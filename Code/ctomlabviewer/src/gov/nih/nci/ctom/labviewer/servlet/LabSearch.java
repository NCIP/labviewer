package gov.nih.nci.ctom.labviewer.servlet;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.globus.gsi.GlobusCredential;
import org.globus.gsi.GlobusCredentialException;

public class LabSearch extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LabSearch() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String proxy = (String)request.getAttribute("gridProxy");
		GlobusCredential globusCredential;
		try
		{
			globusCredential = new GlobusCredential(new ByteArrayInputStream(proxy.getBytes()));
		}
		catch (GlobusCredentialException e)
		{
		      throw new ServletException("Error instantiating GlobusCredential: " + e.getMessage(), e);
		}
		String gridId = globusCredential.getIdentity();
		
		String studyId = request.getParameter("StudyId");
		String patientId = request.getParameter("PatientId");
		LoginForm loginForm = new LoginForm();
		//loginForm.setLoginId(gridId);
		loginForm.setLoginId("ctmsidemo1");
		loginForm.setGridProxy(proxy);
		HttpSession session = request.getSession(true);
		session.setAttribute(DisplayConstants.LOGIN_OBJECT,loginForm);
		String url = "LabActivitiesDBOperation.do?operation=loadSearch&studyId="+studyId+"&patientId="+patientId+"&gridId="+gridId;
		response.sendRedirect(url);
	
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
