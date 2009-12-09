/**
 * 
 */
package gov.nih.nci.caxchange.ctom.viewer.apitest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import gov.nih.nci.caxchange.ctom.viewer.DAO.UsersDAO;
import gov.nih.nci.caxchange.ctom.viewer.beans.Users;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


/**
 * @author asharma
 *
 */
public class UsersDAOTest
{

	 Users user = new Users();
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		user.setFirstName("testUserFN");
		user.setLoginName("testUser");
		user.setLastName("testUserLN");
	}

	/**
	 * Test method for {@link gov.nih.nci.caxchange.ctom.viewer.DAO.UsersDAO#retrieveUsers()}.
	 */
	@Test
	public final void testRetrieveUsers()
	{

		UsersDAO dao = new UsersDAO();
		List users = dao.retrieveUsers();
		assertNotNull("The users list cannot be null",users);
	}

	/**
	 * Test method for {@link gov.nih.nci.caxchange.ctom.viewer.DAO.UsersDAO#retrieveUser(int)}.
	 */
	@Test
	public final void testRetrieveUser()
	{

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link gov.nih.nci.caxchange.ctom.viewer.DAO.UsersDAO#addUser(gov.nih.nci.caxchange.ctom.viewer.beans.Users)}.
	 */
	@Test
	public final void testAddUser()
	{

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link gov.nih.nci.caxchange.ctom.viewer.DAO.UsersDAO#deleteUser(gov.nih.nci.caxchange.ctom.viewer.beans.Users)}.
	 */
	@Test
	public final void testDeleteUser()
	{

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link gov.nih.nci.caxchange.ctom.viewer.DAO.UsersDAO#modifyUser(gov.nih.nci.caxchange.ctom.viewer.beans.Users)}.
	 */
	@Test
	public final void testModifyUser()
	{

		fail("Not yet implemented"); // TODO
	}

}
