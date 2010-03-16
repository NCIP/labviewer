/**
 * 
 */
package gov.nih.nci.caxchange.ctom.viewer.apitest;

import gov.nih.nci.caxchange.ctom.viewer.DAO.HealthCareSiteDAO;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.HealthCareSiteForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;

import org.junit.Before;
import org.junit.Test;

import servletunit.struts.MockStrutsTestCase;


/**
 * @author asharma
 *
 */
public class HealthCareSiteDAOTest extends MockStrutsTestCase
{

	public HealthCareSiteDAOTest(String name)
	{
		super(name);
	}
   	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		super.setUp();
		LoginForm loginForm= new LoginForm();
        loginForm.setLoginId("ccts@nih.gov");
        loginForm.setPassword("!Ccts@nih.gov1");
        getSession().setAttribute(DisplayConstants.LOGIN_OBJECT,loginForm);
		getSession().setAttribute("CAGRID_SSO_GRID_IDENTITY","ccts@nih.gov");
		getSession().setAttribute("caXchangeURL","https://cbvapp-d1017.nci.nih.gov:28445/wsrf-caxchange/services/cagrid/CaXchangeRequestProcessor");
		getSession().setAttribute("ID",109);
	}

	/**
	 * Test method for {@link gov.nih.nci.caxchange.ctom.viewer.DAO.HealthCareSiteDAO#retrieveHealthCareSite(javax.servlet.http.HttpServletRequest, gov.nih.nci.caxchange.ctom.viewer.forms.HealthCareSiteForm)}.
	 */
	@Test
	public final void testRetrieveHealthCareSite()throws Exception
	{

		HealthCareSiteForm baseForm = new HealthCareSiteForm();
		HealthCareSiteDAO dao = new HealthCareSiteDAO();
		dao.retrieveHealthCareSite(getRequest(),baseForm);
		assertEquals(2,baseForm.getHcsList().size());
	}

}
