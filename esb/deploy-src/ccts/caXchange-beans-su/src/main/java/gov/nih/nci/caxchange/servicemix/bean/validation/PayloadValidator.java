package gov.nih.nci.caxchange.servicemix.bean.validation;

import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Validates the payload against a given schema.
 * 
 * @author marwahah
 *
 */
public class PayloadValidator {
	private SAXParserFactory parserFactory=null;
	private Logger logger = LogManager.getLogger(PayloadValidator.class);
	
    public void init() {
    	parserFactory = SAXParserFactory.newInstance();
    	parserFactory.setValidating(false);
    	parserFactory.setNamespaceAware(true);
    	
        logger.info("Payload validator initialized.");
    }
    /**
     * Validates the payload against the schema.
     * 
     * @param payload
     * @param schema
     */
	public void validatePayload(Node payload, Schema schema) throws PayloadValidationException{
	        Validator validator = schema.newValidator();
			ValidatingErrorHandler validatingErrorHandler = new ValidatingErrorHandler();
	        validator.setErrorHandler(validatingErrorHandler);
	        DOMSource source = new DOMSource(payload);
	        try {
	           logger.info("validating payload."); 
	           validator.validate(source);
	        }catch(SAXException e1){
	        	throw new PayloadValidationException("SAX Exception validating payload."+e1.getMessage(), e1);
	        }catch(IOException e2){
	        	throw new PayloadValidationException("IO Exception validating payload."+e2.getMessage(), e2);
	        }
	        
			PayloadValidationException pve=validatingErrorHandler.getPayloadValidationException();
			if (pve!=null) {
				throw pve;
			}
	}
	

}
