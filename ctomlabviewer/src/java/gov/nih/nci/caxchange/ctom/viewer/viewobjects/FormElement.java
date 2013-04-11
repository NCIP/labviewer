/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.viewobjects;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.) TODO To change the
 *         template for this generated type comment go to Window - Preferences -
 *         Java - Code Style - Code Templates
 */
public class FormElement
{

	private String propertyLabel;
	private String propertyName;
	private String propertyValue;
	private String propertyType;
	private String propertyRequired;
	private boolean propertyDisabled;

	/**
	 * @param propertyLabel
	 * @param propertyName
	 * @param propertyValue
	 * @param propertyType
	 * @param propertyRequired
	 */
	public FormElement(String propertyLabel, String propertyName,
			String propertyValue, String propertyType, String propertyRequired,
			boolean propertyDisabled)
	{
		super();
		this.propertyLabel = propertyLabel;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.propertyType = propertyType;
		this.propertyRequired = propertyRequired;
		this.propertyDisabled = propertyDisabled;
	}

	/**
	 * @return Returns the propertyLabel.
	 */
	public String getPropertyLabel()
	{
		return propertyLabel;
	}

	/**
	 * @return Returns the propertyName.
	 */
	public String getPropertyName()
	{
		return propertyName;
	}

	/**
	 * @return Returns the propertyRequired.
	 */
	public String getPropertyRequired()
	{
		return propertyRequired;
	}

	/**
	 * @return Returns the propertyType.
	 */
	public String getPropertyType()
	{
		return propertyType;
	}

	/**
	 * @return Returns the propertyValue.
	 */
	public String getPropertyValue()
	{
		return propertyValue;
	}

	/**
	 * @return Returns the propertyDisabled.
	 */
	public boolean getPropertyDisabled()
	{
		return propertyDisabled;
	}
}
