/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import servletunit.struts.MockStrutsTestCase;

public class HappyActionTest extends MockStrutsTestCase
{
	
	public HappyActionTest(String name)
	{
		super(name);
	}
    @Before
	public void setUp() throws Exception
	{
		super.setUp();
		
		
		setContextDirectory(new File("WebContent"));
        setConfigFile("/cTODSLabViewer/WebContent/WEB-INF/conf/struts-config.xml");
		setRequestPathInfo("/Happy");
        
	}
	@Test
	public void testLoginFail()
	{
		actionPerform();
        verifyForward(ForwardConstants.LOGIN_PAGE);
	}
	
	@Test
	public void testExecute()
	{
		getSession().setAttribute("CAGRID_SSO_GRID_IDENTITY","ccts@nih.gov");
		LoginForm loginForm= new LoginForm();
        loginForm.setLoginId("ccts@nih.gov");
        loginForm.setPassword("!Ccts@nih.gov1");
        getSession().setAttribute(DisplayConstants.LOGIN_OBJECT,loginForm);
        
		actionPerform();
        verifyForward(ForwardConstants.LOAD_TEST);
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

}
