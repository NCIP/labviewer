package gov.nih.nci.caxchange.servicemix.bean.validation;

import javax.xml.validation.Schema;

public interface CaxchangeSchemaFactory {

	public void init();
	
	public Schema getSchema(String messageType) throws SchemaFactoryException ;
}
