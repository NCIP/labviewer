package gov.nih.nci.caxchange.hl7v3datatype.encoding;

import javax.xml.namespace.QName;

import org.apache.axis.encoding.ser.BaseDeserializerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DatatypeDeserializerFactory extends BaseDeserializerFactory {

	protected static Log LOG = LogFactory.getLog(DatatypeDeserializerFactory.class.getName());


	public DatatypeDeserializerFactory(Class javaType, QName xmlType) {
		super(DatatypeDeserializer.class, xmlType, javaType);
		LOG.debug("Initializing DatypeDeserializerFactory for class:" + javaType + " and QName:" + xmlType);
	}
}