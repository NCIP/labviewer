/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package org.cagrid.gaards.websso.beans;

public class HostInformation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String hostName = null;
	
	private String hostIdentity = null;

	public String getHostName()
	{
		return hostName;
	}

	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

	public String getHostIdentity()
	{
		return hostIdentity;
	}

	public void setHostIdentity(String hostIdentity)
	{
		this.hostIdentity = hostIdentity;
	}

}
