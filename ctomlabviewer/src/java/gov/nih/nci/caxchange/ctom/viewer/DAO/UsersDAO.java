/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.DAO;

import gov.nih.nci.caxchange.ctom.viewer.beans.Users;
import gov.nih.nci.caxchange.ctom.viewer.beans.util.HibernateUtil;
import gov.nih.nci.caxchange.ctom.viewer.util.LabViewerAuthorizationHelper;
import gov.nih.nci.security.authorization.domainobjects.UserProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author asharma
 */
public class UsersDAO extends HibernateDaoSupport
{

	private static final Logger log = Logger.getLogger(UsersDAO.class);

	/**
	 * Retrieves all the users who have access to LabViewer application
	 * 
	 * @return users list
	 */
	public List<Users> retrieveUsers()
	{
		List<Users> users = new ArrayList<Users>();
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			users = session.createQuery("from Users").list();
			session.getTransaction().commit();
		}
		catch (Exception se)
		{
			log.error("Error retrieving users", se);
		}

		return users;
	}

	/**
	 * Retrieves the user for a given Id
	 * 
	 * @return users list
	 */
	public Users retrieveUser(int id)
	{
		Users user = new Users();
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List usersList =
					session.createQuery("from Users where id=" + id).list();
			if (usersList != null && !usersList.isEmpty())
			{	
				user = (Users) usersList.get(0);
			}	
			session.getTransaction().commit();
		}
		catch (Exception se)
		{
			log.error("Error retrieving users", se);
		}

		return user;
	}

	/**
	 * Add a new user.
	 * 
	 * @return successfulAdd
	 */
	public boolean addUser(Users user)
	{
		boolean successfulAdd = false;
		Session session = null;
        //Session session2 = null;
        Connection con = null;
		try
		{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(user);
            session.getTransaction().commit();

            /*  Now first retrieve csm_user  */
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query userQuery = session.createQuery(" From Users u WHERE u.loginName = '"+ user.getLoginName() + "'");
            List<Users> csmUserList = userQuery.list();
            assert csmUserList.size() == 1;
            Users csmUser = csmUserList.get(0);

            con = session.connection();
            String sql = "insert into csm_user_pe( PROTECTION_ELEMENT_ID,USER_ID,UPDATE_DATE) " +
                    " values(2,?,now())";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, csmUser.getId());
            ps.executeUpdate();
            con.commit();
            ps.close();
            successfulAdd = true;

		}
		catch (Exception se) {
			log.error("Error adding users", se);
		}
        finally {
            try {
                if (con != null)
                con.close();
                if (session.isOpen()) session.close();
            }catch(SQLException sqle) {
                log.error("Error adding users", sqle);
                successfulAdd = false;
            }
        }

		return successfulAdd;
	}

	/**
	 * Deletes user.
	 * 
	 * @return successfulDelete
	 */
	public boolean deleteUser(Users user)
	{
		boolean successfulDelete = false;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(user);
			session.getTransaction().commit();
		}
		catch (Exception se)
		{
			log.error("Error deleting users", se);
		}
		return successfulDelete;
	}

	/**
	 * Update user details.
	 * 
	 * @return successfulUpdate
	 */
	public boolean modifyUser(Users user)
	{
		boolean successfulUpdate = false;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.saveOrUpdate(user);
			session.getTransaction().commit();
		}
		catch (Exception se)
		{
			log.error("Error modifying users details", se);
		}

		return successfulUpdate;
	}
}
