package gov.nih.nci.caxchange.ctom.viewer.util;

import gov.nih.nci.caxchange.ctom.viewer.beans.Users;

import org.displaytag.decorator.TableDecorator;

// TODO: Auto-generated Javadoc
/**
 * The Class PIDecorator.
 * 
 * @author Anupama Sharma
 */
public class UserDecorator extends TableDecorator
{

	/** The Constant NBSP. */
	private static final String NBSP = "&nbsp;";

	/**
	 * Instantiates a new pI decorator.
	 */
	public UserDecorator()
	{

		super();
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public final String getLoginName()
	{

		Users user = (Users) getCurrentRowObject();
		String loginName = NBSP;
		if (user.getLoginName() != null && !user.getLoginName().equals("")
				&& !user.getLoginName().equals("null"))
		{
			loginName = user.getLoginName();
		}
		return loginName;
	}
	
	public final String getFirstName()
	{

		Users user = (Users) getCurrentRowObject();
		String fName = NBSP;
		if (user.getFirstName() != null && !user.getFirstName().equals("")
				&& !user.getFirstName().equals("null"))
		{
			fName = user.getFirstName();
		}
		return fName;
	}
	
	public final String getLastName()
	{

		Users user = (Users) getCurrentRowObject();
		String lName = NBSP;
		if (user.getLastName() != null && !user.getLastName().equals("")
				&& !user.getLastName().equals("null"))
		{
			lName = user.getLastName();
		}
		return lName;
	}

	

}
