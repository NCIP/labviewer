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

import org.hl7.meta.Datatype;
import org.hl7.meta.ParametrizedDatatype;
import org.hl7.meta.SimpleDatatype;
import org.hl7.meta.UnknownDatatypeException;
import org.hl7.meta.impl.DatatypeMetadataFactoryDatatypes;
import org.hl7.meta.impl.DatatypeMetadataFactoryImpl;
import org.hl7.types.ANY;
import org.hl7.util.DatatypeAnalyzer;
import org.hl7.util.FactoryException;
import org.hl7.xml.DatatypePresenter;
import org.hl7.xml.DatatypePresenterBase;
import org.hl7.xml.DatatypePresenterFactory;
import org.hl7.xml.builder.BuilderException;
import org.hl7.xml.builder.DatatypeBuilder;
import org.hl7.xml.builder.RimGraphXMLSpeaker;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Convert datatype to xml stream
 *
 * @author OWNER: Eric Chen
 *         Date: May 19, 2005
 *         Time: 7:36:44 AM
 * @author LAST UPDATE $Author: chene $
 * @version $Revision: 1.1 $
 *          $Date: 2007-11-15 23:49:08 $
 */
public class DatatypeXMLSpeaker extends RimGraphXMLSpeaker {

//    private static Log log = LogFactory.getLog(DatatypeXMLSpeaker.class);

    public class ContentBuilder extends RimGraphXMLSpeaker.ContentBuilder {

        protected ContentBuilder() {
            super();
            super.setNamespace(DatatypePresenterBase.HL7_URI);
        }

        Datatype _typeConstraint;

        public void setTypeConstraint(Datatype typeConstraint) {
            _typeConstraint = typeConstraint;
        }

        public Datatype getTypeConstraint() {
            return _typeConstraint;
        }

        // add xsi type if data is abstract: ANY, QTY
        public boolean nullValueHandled(ANY value) throws SAXException {

            Datatype typeConstraint = getTypeConstraint();
            Datatype actualType = null;

            try
            {
                String datatypename = DatatypeAnalyzer.getTypeBase(value);
                actualType = DatatypeMetadataFactoryImpl.instance().create(datatypename);
            }
            catch (DatatypeAnalyzer.AnalysisException ex)
            {
//                throw new SAXException(ex);
                ex.printStackTrace();
            }
            catch (UnknownDatatypeException ex)
            {
//                throw new SAXException(ex);
                ex.printStackTrace();
            }

            boolean isSetComponent = false;
            String passedDownXsiType = getAttributeValue(DatatypePresenterBase.W3_XML_SCHEMA, "type");

            if (passedDownXsiType == null)
            {
//                if (actualType.isCompleteTypeOf(DatatypeMetadataFactoryDatatypes.instance().IVL_GENERIC_TYPE) || actualType.isCompleteTypeOf(DatatypeMetadataFactoryDatatypes.instance().PIVL_GENERIC_TYPE))
//                {
//                    this.addAttribute(DatatypePresenterBase.W3_XML_SCHEMA, "type", "xsi:type", "CDATA", ((ParametrizedDatatype) actualType).getType() + "_" + ((ParametrizedDatatype) actualType).getParameter(0).getFullName());
//                }
//                else

                if (typeConstraint != null && !typeConstraint.equals(actualType))
                {
                    String xsiType = convertNameToXsiType(actualType.getFullName());
                    if (isSetComponent && !xsiType.contains("_"))
                        this.addAttribute(DatatypePresenterBase.W3_XML_SCHEMA, "type", "xsi:type", "CDATA", "SXCM_" + xsiType);
                    else
                        this.addAttribute(DatatypePresenterBase.W3_XML_SCHEMA, "type", "xsi:type", "CDATA", xsiType);
                }
            }

            return super.nullValueHandled(value);
        }

        /* HACK - only used in above code.  If name is RTO<PQ,INT> will return RTO_PQ_INT */
        private String convertNameToXsiType(String name)
        {
            name = name.replaceAll("<", "_");
            name = name.replaceAll(">", "_");
            name = name.replaceAll(",", "_");
            if (name.endsWith("_"))
            {
                name = name.substring(0, name.lastIndexOf("_"));
            }
            return name;
        }

    }


    /**
     * Another specialization of the standard SAX InputSource that is
     * passed to the parse() method to begin serialization of just
     * a standalone data value.
     */
    public static class DataValueInputSource extends org.xml.sax.InputSource {
        public DataValueInputSource(ANY value, String localName, Datatype typeConstraint) {
            _typeConstraint = typeConstraint;
            _value = value;
            _localName = localName;
        }

        public Datatype getTypeConstraint() {
            return _typeConstraint;
        }

        private Datatype _typeConstraint = null;
        private ANY _value = null;

        public void setValue(ANY value) {
            _value = value;
        }

        public ANY getValue() {
            return _value;
        }

        private String _localName = null;

        public void setLocalName(String localName) {
            _localName = localName;
        }

        public String getLocalName() {
            return _localName;
        }
    }


    public void parse(org.xml.sax.InputSource input) throws IOException, SAXException {
        try {
            if (input instanceof DataValueInputSource) {
                DataValueInputSource dvinput = (DataValueInputSource) input;
                ContentBuilder contentBuilder = new ContentBuilder();
                contentBuilder.setTypeConstraint(dvinput.getTypeConstraint());
                contentBuilder.addAttribute("", "xsi", "xmlns:xsi", "CDATA", DatatypePresenterBase.W3_XML_SCHEMA);
                final ANY value = dvinput.getValue();
                final Datatype datatype = dvinput.getTypeConstraint();

                String datatypename = null;
                if (datatype instanceof SimpleDatatype) {
                    SimpleDatatype simpleDatatype = (SimpleDatatype) datatype;
                    datatypename = simpleDatatype.getFullName();
                    if (datatype.equals(DatatypeMetadataFactoryDatatypes.instance().ANYTYPE) ||
                        datatype.equals(DatatypeMetadataFactoryDatatypes.instance().QTYTYPE)) {
                        try {
                            datatypename = DatatypeAnalyzer.getTypeBase(value);
                        } catch (DatatypeAnalyzer.AnalysisException e) {
//                            log.error(this, e);
                        }
                    }

                } else if (datatype instanceof ParametrizedDatatype) {
                    ParametrizedDatatype parametrizedDatatype = (ParametrizedDatatype) datatype;
                    String fullName = parametrizedDatatype.getFullName();
                    if (fullName.startsWith("LIST") ||
                        fullName.startsWith("BAG") ||
                        fullName.startsWith("SET")) {
                        datatypename = parametrizedDatatype.getType();
                    } else {
                        datatypename = parametrizedDatatype.getFullName();
                    }

                } else {
                    throw new RuntimeException("datatype " + datatype.getFullName() + " is not validated. ");
                }

                final DatatypePresenter presenter = DatatypePresenterFactory.getInstance().createPresenter(datatypename);
                DatatypeBuilder builder = (DatatypeBuilder) presenter.getBuilder();
                builder.build(contentBuilder, value, dvinput.getLocalName());

            } else {
                // XXX: we could switch to normal XML parsing if input is a stream?
                throw new IllegalArgumentException("input must be " +
                    DataValueInputSource.class.getName());
            }
        } catch (BuilderException ex) {
            if (ex.getCause() == null)
            {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                throw new SAXException(ex);
            }
            else if (ex.getCause() instanceof SAXException)
            {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex.getCause());
                throw (SAXException) ex.getCause();
            }
            else if (ex.getCause() instanceof Exception)
            {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex.getCause());
                throw new SAXException((Exception) ex.getCause());
            }
            else
            {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex.getCause());
                throw new SAXException(ex);
            }

        } catch (FactoryException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SAXException(ex);
        }
    }
}


/**
 * HISTORY      : : SomeClass.java,v $
 */
