/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.cagrid.labviewer.service.globus.resource;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class is used by the resource to get configuration information about the 
 * resource.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class LabLoaderResourceConfiguration {
	private String registrationTemplateFile;
	private boolean performRegistration;

	private String serviceMetadataFile;



	public boolean shouldPerformRegistration() {
		return performRegistration;
	}


	public void setPerformRegistration(boolean performRegistration) {
		this.performRegistration = performRegistration;
	}


	public String getRegistrationTemplateFile() {
		return registrationTemplateFile;
	}
	


	public void setRegistrationTemplateFile(String registrationTemplateFile) {
		this.registrationTemplateFile = registrationTemplateFile;
	}
	
	
	
	public String getServiceMetadataFile() {
		return serviceMetadataFile;
	}
	
	
	public void setServiceMetadataFile(String serviceMetadataFile) {
		this.serviceMetadataFile = serviceMetadataFile;
	}
		
}
