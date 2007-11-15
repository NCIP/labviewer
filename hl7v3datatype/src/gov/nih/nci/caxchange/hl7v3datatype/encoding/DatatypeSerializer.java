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

import org.apache.axis.Constants;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.wsdl.fromJava.Types;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.meta.Datatype;
import org.hl7.meta.impl.SimpleDatatypeImpl;
import org.hl7.types.ANY;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;

/**
 * This class serializes a HL7 v3 data type java object to XML stream 
 *
 * @author OWNER: Eric Chen  Date: Nov 5, 2007
 * @author LAST UPDATE: $Author: chene $
 * @version $Revision: 1.1 $
 * @date $$Date: 2007-11-15 23:49:07 $
 */


public class DatatypeSerializer implements Serializer
{
    protected static Log LOG = LogFactory.getLog(DatatypeSerializer.class.getName());

    private static Transformer transformer;

    static
    {
        try
        {
            final TransformerFactory _transformerFactory = TransformerFactory.newInstance();
            transformer = _transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        }
        catch (Exception e)
        {
        }
    }

    public void serialize(QName name, Attributes attributes, Object value, SerializationContext context)
        throws IOException
    {
        long startTime = System.currentTimeMillis();
            
        //TODO: Hardcode datatype for now 
        Datatype datatype = new SimpleDatatypeImpl("II");

        StringWriter sw = new StringWriter();
        try {
            //TODO: Need to syn data type local name with schema name  
            transformer.transform(new SAXSource(new DatatypeXMLSpeaker(),
                new DatatypeXMLSpeaker.DataValueInputSource((ANY)value, "II", datatype)), new StreamResult(sw));
            context.writeString(sw.toString());

        } catch (NullPointerException e) {
            LOG.error("Problem: getter empty value.", e);
        } catch (TransformerException e)
        {
            LOG.error("Problem: Tranformation Error.", e);
        }

        long duration = System.currentTimeMillis() - startTime;
        LOG.debug("Total time to serialize(" + name.getLocalPart() + "):" + duration + " ms.");
    }

    public String getMechanismType()
    {
        return Constants.AXIS_SAX;
    }

    public Element writeSchema(Class aClass, Types types) throws Exception
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String serialize(ANY value, String datatypeName, String localName){
        Datatype datatype = new SimpleDatatypeImpl(datatypeName);
        StringWriter sw = new StringWriter();
        try {
            transformer.transform(new SAXSource(new DatatypeXMLSpeaker(),
                new DatatypeXMLSpeaker.DataValueInputSource(value, localName, datatype)), new StreamResult(sw));
        } catch (NullPointerException e) {
            LOG.error("Problem: getter empty value.", e);
        }
        catch (TransformerException e)
        {
            LOG.error("Problem: Tranformation Error.", e);
        }
        return sw.toString();
    }

}

/**
 * HISTORY      : : SomeClass.java,v $
 */
