package gov.nih.nci.caxchange.hl7v3datatype.encoding;

import javax.xml.namespace.QName;

import org.apache.axis.encoding.ser.BaseSerializerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DatatypeSerializerFactory extends BaseSerializerFactory {

	protected static Log LOG = LogFactory.getLog(DatatypeSerializerFactory.class.getName());


	public DatatypeSerializerFactory(Class javaType, QName xmlType) {
		super(DatatypeSerializer.class, xmlType, javaType);
		LOG.debug("Initializing DatatypeSerializerFactory for class:" + javaType + " and QName:" + xmlType);
	}
}