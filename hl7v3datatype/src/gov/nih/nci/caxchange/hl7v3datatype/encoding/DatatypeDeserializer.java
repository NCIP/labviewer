/**
 * : $
 *
 * ******************************************************************
 * COPYRIGHT NOTICE
 * ******************************************************************
 *
 *	The America on Top corp Software License, Version 1.0
 *
 *  All Right Reserved   
 *
 * ********************************************************************
 */

package gov.nih.nci.caxchange.hl7v3datatype.encoding;

import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.DeserializerImpl;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.message.MessageElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.meta.Datatype;
import org.hl7.meta.impl.SimpleDatatypeImpl;
import org.hl7.types.ANY;
import org.hl7.types.II;
import org.hl7.xml.parser.StandaloneDataTypeContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * HISTORY      : : SomeClass.java,v $
 */

/**
 * This class deserializes a XML to HL7 v3 data type java object
 *
 * @author OWNER: Eric Chen  Date: Nov 5, 2007
 * @author LAST UPDATE: $Author: chene $
 * @version $Revision: 1.1 $
 * @date $$Date: 2007-11-15 23:49:07 $
 */


public class DatatypeDeserializer extends DeserializerImpl implements Deserializer
{
    public static final String HL7_URI = "urn:hl7-org:v3";

    protected static Log LOG = LogFactory.getLog(DatatypeDeserializer.class.getName());    

    public void onEndElement(String namespace, String localName, DeserializationContext context) {
        long startTime=System.currentTimeMillis();

        Datatype datatype = new SimpleDatatypeImpl("II");

        MessageElement msgElem = context.getCurElement();
        String asString = null;

        try
        {
            asString = msgElem.getAsString();
        }
        catch (Exception e)
        {
            LOG.error("Problem extracting message type! Result will be null!", e);
        }


        InputStream is = new ByteArrayInputStream(asString.getBytes());
        try
        {
            value = StandaloneDataTypeContentHandler.parseValue(is, datatype);
        }
        catch (SAXException e)
        {
            LOG.error("Problem with JavaSIG data type desirization!", e);
        }

        long duration=System.currentTimeMillis()- startTime;
        LOG.debug("Total time to deserialize("+localName+"):"+duration+" ms.");
    }

    /**
     * For own test purpose
     * @param is
     * @param datatypeName
     * @return
     */
    private ANY deserialize(InputStream is, String datatypeName) {
        Datatype datatype = new SimpleDatatypeImpl(datatypeName);
        ANY value = null;
        try
        {
            value = (II) StandaloneDataTypeContentHandler.parseValue(is, datatype);
        }
        catch (SAXException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return value;
    }

    public static void main(String[] args)
    {
        final String input = "<id xmlns=\"" + HL7_URI + "\" "
            + "assigningAuthorityName=\"authName\" root=\"2.16.840.1.113883.19\" displayable=\"true\" extension=\"c266\"/>";
        // TODO: Maybe using DocumentInpurSource object instead input stream
        InputStream is = new ByteArrayInputStream(input.getBytes());
        DatatypeDeserializer deserializerFactory = new DatatypeDeserializer();
        ANY ii = deserializerFactory.deserialize(is, "II");
        DatatypeSerializer serializerFactory = new DatatypeSerializer();
        System.out.println(serializerFactory.serialize(ii, "II", "identifier"));
   }
    
}