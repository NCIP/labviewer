package gov.nih.nci.caxchange.ctom.viewer.util;

import gov.nih.nci.caxchange.ctom.viewer.beans.HCSite;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResult;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.ParticipantSearchResult;

import org.displaytag.decorator.TableDecorator;

// TODO: Auto-generated Javadoc
/**
 * The Class HCSDecorator.
 * 
 * @author Anupama Sharma
 */
public class HCSDecorator extends TableDecorator
{

	/** The Constant NBSP. */
	private static final String NBSP = "&nbsp;";

	/**
	 * Instantiates a new hCS decorator.
	 */
	public HCSDecorator()
	{

		super();
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public final String getName()
	{

		HCSite hcSite = (HCSite) getCurrentRowObject();
		String name = NBSP;
		if (hcSite.getName() != null && !hcSite.getName().equals("")
				&& !hcSite.getName().equals("null"))
		{
			name = hcSite.getName();
		}
		return name;
	}

	/**
	 * Gets the address.
	 * 
	 * @return the address
	 */
	public final String getAddress()
	{

		HCSite hcSite = (HCSite) getCurrentRowObject();
		String addr = NBSP;
		if (hcSite.getAddress() != null && !hcSite.getAddress().equals("")
				&& !hcSite.getAddress().equals("null"))
		{
			addr = hcSite.getAddress();
		}
		return addr;
	}
	
	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	public final String getEmail()
	{

		HCSite hcSite = (HCSite) getCurrentRowObject();
		String email = NBSP;
		if (hcSite.getEmail() != null && !hcSite.getEmail().equals("")
				&& !hcSite.getEmail().equals("null"))
		{
			email = hcSite.getEmail();
		}
		return email;
	}
	
	/**
	 * Gets the phone.
	 * 
	 * @return the phone
	 */
	public final String getPhone()
	{

		HCSite hcSite = (HCSite) getCurrentRowObject();
		String phone = NBSP;
		if (hcSite.getPhone() != null && !hcSite.getPhone().equals("")
				&& !hcSite.getPhone().equals("null"))
		{
			phone = hcSite.getPhone();
		}
		return phone;
	}

}
