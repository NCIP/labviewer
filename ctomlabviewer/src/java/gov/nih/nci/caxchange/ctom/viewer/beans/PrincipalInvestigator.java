/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.beans;


/**
 * @author asharma
 *
 */
public class PrincipalInvestigator
{
	private String name;
	private String address;
	private String email;
	private String phone;
	private String updatedDate;
	private String coppaUpdate;
	
	
	/**
	 * @return the coppaUpdate
	 */
	public String getCoppaUpdate()
	{
	
		return coppaUpdate;
	}


	
	/**
	 * @param coppaUpdate the coppaUpdate to set
	 */
	public void setCoppaUpdate(String coppaUpdate)
	{
	
		this.coppaUpdate = coppaUpdate;
	}


	/**
	 * @return the name
	 */
	public String getName()
	{
	
		return name;
	}

	
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
	
		this.name = name;
	}

	
	/**
	 * @return the address
	 */
	public String getAddress()
	{
	
		return address;
	}

	
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address)
	{
	
		this.address = address;
	}

	
	/**
	 * @return the email
	 */
	public String getEmail()
	{
	
		return email;
	}

	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email)
	{
	
		this.email = email;
	}

	
	/**
	 * @return the phone
	 */
	public String getPhone()
	{
	
		return phone;
	}

	
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone)
	{
	
		this.phone = phone;
	}
	/**
	 * @return the updatedDate
	 */
	public String getUpdatedDate()
	{
	
		return updatedDate;
	}


	
	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(String updatedDate)
	{
	
		this.updatedDate = updatedDate;
	}


}
