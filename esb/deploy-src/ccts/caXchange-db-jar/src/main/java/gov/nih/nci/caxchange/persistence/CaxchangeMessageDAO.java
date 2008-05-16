package gov.nih.nci.caxchange.persistence;

import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;
/**
 * This interface contains the methods that will be implemented 
 * for processing the caxchange message in MqSql database
 * @author hmarwaha
 *
 */
public interface CaxchangeMessageDAO {

    public void storeMessage(CaxchangeMessage caxchangeMessage) throws Exception;
    
    public CaxchangeMessage getMessage(String id) throws Exception;
    
    public void deleteMessage(CaxchangeMessage caxchangeMessage) throws Exception;
}
