/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.viewobjects;
 
import java.util.List;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.)
 *
 */
public class SearchResult 
{
	private String searchResultMessage;
	private List searchResultObjects;
	
	
	/**
	 * @return Returns the searchResultMessage.
	 */
	public String getSearchResultMessage() {
		return searchResultMessage;
	}
	/**
	 * @param searchResultMessage The searchResultMessage to set.
	 */
	public void setSearchResultMessage(String searchResultMessage) {
		this.searchResultMessage = searchResultMessage;
	}
	/**
	 * @return Returns the searchResultObjects.
	 */
	public List getSearchResultObjects() {
		return searchResultObjects;
	}
	/**
	 * @param searchResultObjects The searchResultObjects to set.
	 */
	public void setSearchResultObjects(List searchResultObjects) {
		this.searchResultObjects = searchResultObjects;
	}
}
