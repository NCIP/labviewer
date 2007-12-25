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
import org.hl7.util.DatatypeAnalyzer;
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
 * XML data type element name should be the same as 
 *
 * @author OWNER: Eric Chen  Date: Nov 5, 2007
 * @author LAST UPDATE: $Author: chene $
 * @version $Revision: 1.2 $
 * @date $$Date: 2007-12-25 23:22:37 $
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
        if (value instanceof ANY)
        {
            ANY any = (ANY) value;
            try
            {
                String stringType = DatatypeAnalyzer.getTypeBase((ANY) value);
                Datatype datatype = new SimpleDatatypeImpl(stringType);

                StringWriter sw = new StringWriter();

                //TODO: Need to syn data stringType local name with schema name
                transformer.transform(new SAXSource(new DatatypeXMLSpeaker(),
                    new DatatypeXMLSpeaker.DataValueInputSource((ANY) any, stringType, datatype)), new StreamResult(sw));
                context.writeString(sw.toString());

            }
            catch (NullPointerException e)
            {
                LOG.error("Problem: getter empty value.", e);
            }
            catch (TransformerException e)
            {
                LOG.error("Problem: Tranformation Error.", e);
            }
            catch (DatatypeAnalyzer.AnalysisException e)
            {
                LOG.error("Problem: getter unknow type", e);
            }


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

    public String serialize(ANY value, String datatypeName, String localName)
    {
        Datatype datatype = new SimpleDatatypeImpl(datatypeName);
        StringWriter sw = new StringWriter();
        try
        {
            transformer.transform(new SAXSource(new DatatypeXMLSpeaker(),
                new DatatypeXMLSpeaker.DataValueInputSource(value, localName, datatype)), new StreamResult(sw));
        }
        catch (NullPointerException e)
        {
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
