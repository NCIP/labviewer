/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.AdministrationForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import servletunit.struts.MockStrutsTestCase;

public class LoadAdminActionTest extends MockStrutsTestCase 
{
	private AdministrationForm adminForm;
	private LoginForm loginForm;
	
	public LoadAdminActionTest(String testName) {
        super(testName);
    }
	@Before
	public void setUp() throws Exception
	{
		super.setUp();
			
		  setContextDirectory(new File("WebContent"));
          setConfigFile("D:/Development/cTODSLabViewer/WebContent/WEB-INF/conf/struts-config.xml");
		  setRequestPathInfo("/LoadAdmin");  
            
       

	}
	@Test
	public void testExecute()
	{
		getSession().setAttribute("CAGRID_SSO_GRID_IDENTITY","ccts@nih.gov");
		getSession().setAttribute("caXchangeURL","https://cbvapp-d1017.nci.nih.gov:28445/wsrf-caxchange/services/cagrid/CaXchangeRequestProcessor");
		//populate the login form
		loginForm= new LoginForm();
		loginForm.setLoginId("ccts@nih.gov");
		loginForm.setPassword("!Ccts@nih.gov1");
		loginForm.setGridProxy("test");
        getSession().setAttribute(DisplayConstants.LOGIN_OBJECT,loginForm);
        
        adminForm =new AdministrationForm();
	   
	    //set the action form    
	    setActionForm(adminForm);
		
	    actionPerform();
	    assertEquals(adminForm.getCaxUrl(),"https://cbvapp-d1017.nci.nih.gov:28445/wsrf-caxchange/services/cagrid/CaXchangeRequestProcessor");
        verifyForward(ForwardConstants.LOAD_ADMIN_SUCCESS);
	}
	

	public void tearDown() throws Exception
	{
		super.tearDown();
	}

}
