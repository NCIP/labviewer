package gov.nih.nci.caxchange.ctom.viewer.util;

import gov.nih.nci.caxchange.ctom.viewer.beans.PrincipalInvestigator;

import org.displaytag.decorator.TableDecorator;

// TODO: Auto-generated Javadoc
/**
 * The Class PIDecorator.
 *
 * @author Anupama Sharma
 */
public class PIDecorator extends TableDecorator
{

	/** The Constant NBSP. */
	private static final String NBSP = "&nbsp;";

	/**
	 * Instantiates a new pI decorator.
	 */
	public PIDecorator()
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

		PrincipalInvestigator pi = (PrincipalInvestigator) getCurrentRowObject();
		String name = NBSP;
		if (pi.getName() != null && !pi.getName().equals("")
				&& !pi.getName().equals("null"))
		{
			name = pi.getName();
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

		PrincipalInvestigator pi = (PrincipalInvestigator) getCurrentRowObject();
		String addr = NBSP;
		if (pi.getAddress() != null && !pi.getAddress().equals("") && pi.getAddress().trim().length() > 1
				&& !pi.getAddress().equals("null"))
		{
			addr = pi.getAddress();
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

		PrincipalInvestigator pi = (PrincipalInvestigator) getCurrentRowObject();
		String email = NBSP;
		if (pi.getEmail() != null && !pi.getEmail().equals("")
				&& !pi.getEmail().equals("null"))
		{
			email = pi.getEmail();
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

		PrincipalInvestigator pi = (PrincipalInvestigator) getCurrentRowObject();
		String phone = NBSP;
		if (pi.getPhone() != null && !pi.getPhone().equals("")
				&& !pi.getPhone().equals("null"))
		{
			phone = pi.getPhone();
		}
		return phone;
	}
	/**
	 * Gets the updated Date.
	 *
	 * @return the upDate
	 */
	public final String getUpdatedDate()
	{

		PrincipalInvestigator pi = (PrincipalInvestigator) getCurrentRowObject();
		String upDate = NBSP;
		String coppaImg = "<img src='images/COPPA.jpg' alt='Data obtained from COPPA'>";
		if (pi.getUpdatedDate() != null && !pi.getUpdatedDate().equals("")
				&& !pi.getUpdatedDate().equals("null"))
		{
			if (pi.getCoppaUpdate() != null && pi.getCoppaUpdate().equalsIgnoreCase("Y")){
				upDate = pi.getUpdatedDate() + " " + coppaImg;
			} else {
				upDate = pi.getUpdatedDate();
			}
		}
		return upDate;
	}

}
