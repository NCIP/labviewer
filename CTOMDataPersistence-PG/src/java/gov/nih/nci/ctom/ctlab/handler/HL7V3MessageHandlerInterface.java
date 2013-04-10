/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.Protocol;

import java.sql.Connection;

/**
 * @author asharma
 */
public interface HL7V3MessageHandlerInterface
{

	/**
	 * Persists the Protocol and its associated objects to CTODS database.
	 * @param con
	 * 			Connection
	 * @param protocol
	 * 			Protocol
	 * @throws Exception
	 */
	public void persist(Connection con, Protocol protocol) throws Exception;

}
