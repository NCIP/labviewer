package gov.nih.nci.caxchange.persistence;

import gov.nih.nci.caxchange.jdbc.CaxchangeMetadata;
/**
 * This interface contains the methods that will be implemented
 * for processing the caxchange message in MqSql database
 * @author hmarwaha
 *
 */
public interface CaxchangeMetadataDAO {
	
    static public java.util.Map<String, CaxchangeMetadata> metadataCache = new java.util.HashMap<String, CaxchangeMetadata>(10);


    public void storeMetadata(CaxchangeMetadata caxchangeMetadata) throws Exception;

    public CaxchangeMetadata getMetadata(String messageType) throws Exception;
    
    public void deleteMetadata(CaxchangeMetadata caxchangeMetadata) throws Exception;

}
