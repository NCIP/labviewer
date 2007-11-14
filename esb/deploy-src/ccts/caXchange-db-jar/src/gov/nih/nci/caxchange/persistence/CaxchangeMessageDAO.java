package gov.nih.nci.caxchange.persistence;

import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;

public interface CaxchangeMessageDAO {

    public void storeMessage(CaxchangeMessage caxchangeMessage) throws Exception;
    
    public CaxchangeMessage getMessage(String id) throws Exception;
    
    public void deleteMessage(CaxchangeMessage caxchangeMessage) throws Exception;
}
