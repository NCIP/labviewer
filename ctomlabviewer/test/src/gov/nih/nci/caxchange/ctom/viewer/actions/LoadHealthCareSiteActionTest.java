/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.HealthCareSiteForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;

import java.io.File;

import servletunit.struts.MockStrutsTestCase;

public class LoadHealthCareSiteActionTest extends MockStrutsTestCase 
{
	private HealthCareSiteForm hcsForm;
	private LoginForm loginForm;
	
	public LoadHealthCareSiteActionTest(String testName) {
        super(testName);
    }
	
	public void setUp() throws Exception
	{
		super.setUp();
			
		  setContextDirectory(new File("WebContent"));
          setConfigFile("D:/Development/cTODSLabViewer/WebContent/WEB-INF/conf/struts-config.xml");
		  setRequestPathInfo("/loadHealthCareSite");  
            
       

	}

	public void testExecute()
	{
		getSession().setAttribute("CAGRID_SSO_GRID_IDENTITY","ccts@nih.gov");
		getSession().setAttribute("ID",108);
		//populate the login form
		loginForm= new LoginForm();
		loginForm.setLoginId("ccts@nih.gov");
		loginForm.setPassword("!Ccts@nih.gov1");
		loginForm.setGridProxy("test");
        getSession().setAttribute(DisplayConstants.LOGIN_OBJECT,loginForm);
        
        hcsForm =new HealthCareSiteForm();
	   
	    //set the action form    
	    setActionForm(hcsForm);
		
	    actionPerform();
	    assertNull(hcsForm.getHcsList());
	    verifyForward(ForwardConstants.LOAD_HEALTHCARESITE_SUCCESS);
	}
	

	public void tearDown() throws Exception
	{
		super.tearDown();
	}

}
