package gov.nih.nci.cagrid.caxchange.listener;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.caxchange.ErrorDetails;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.MessageStatuses;
import gov.nih.nci.caxchange.Metadata;
import gov.nih.nci.caxchange.Response;
import gov.nih.nci.caxchange.ResponseMessage;

import gov.nih.nci.caxchange.ResponseMetadata;
import gov.nih.nci.caxchange.Statuses;

import gov.nih.nci.caxchange.TargetResponseMessage;

import java.io.Reader;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;

import org.w3c.dom.Document;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
/**
 * Serializes the response obtained from caXchange to a {@ResponseMessage} object
 * 
 * @author ekagra
 *
 */
public class ResponseHandler {

    static String testResponse ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
    "<caXchangeResponseMessage xmlns=\"http://caXchange.nci.nih.gov/messaging\">\n" + 
    "  <responseMetaData>\n" + 
    "    <externalIdentifier>REGISTER_SUBJECTmyExternalIdentifiercafb12d0-8ae8-11dc-9ed7-c7f4bfd99e68hmarwahapassword</externalIdentifier>\n" + 
    "    <caXchangeIdentifier>REGISTER_SUBJECTmyExternalIdentifiercafb12d0-8ae8-11dc-9ed7-c7f4bfd99e68hmarwahapassword</caXchangeIdentifier>\n" + 
    "  </responseMetaData>\n" + 
    "  <response>\n" + 
    "    <responseStatus>FAILURE</responseStatus>\n" + 
    "    <caXchangeError>\n" + 
    "      <errorCode>DELEGATION_SERVICE_ERROR</errorCode>\n" + 
    "      <errorDescription>Error occurred calling the delegation service.</errorDescription>\n" + 
    "    </caXchangeError>\n" + 
    "  </response>\n" + 
    "</caXchangeResponseMessage>";
    
    
    static String testResponse1 ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
    "<caXchangeResponseMessage>\n" + 
    "  <responseMetaData>\n" + 
    "    <externalIdentifier>REGISTER_SUBJECTmyExternalIdentifiercafb12d0-8ae8-11dc-9ed7-c7f4bfd99e68hmarwahapassword</externalIdentifier>\n" + 
    "    <caXchangeIdentifier>REGISTER_SUBJECTmyExternalIdentifiercafb12d0-8ae8-11dc-9ed7-c7f4bfd99e68hmarwahapassword</caXchangeIdentifier>\n" + 
    "  </responseMetaData>\n" + 
    "  <response>\n" + 
    "    <responseStatus>FAILURE</responseStatus>\n" + 
    "    <businessResponseMessage>\n" + 
    "      <targetServiceIdentifier>PSC</targetServiceIdentifier>\n" + 
    "      <targetServiceOperation>commit</targetServiceOperation>\n" + 
    "      <messageStatus>RESPONSE</messageStatus>\n"+
    "      <messagePayload> "+
    "         <xmlSchemaDefinition>http://test</xmlSchemaDefinition> "+
    "         <mytest><first>harsh</first><last>marwaha</last></mytest> "+
    "      </messagePayload> "+
    "    </businessResponseMessage>\n" + 
    "    <businessResponseMessage>\n" + 
    "      <targetServiceIdentifier>CAERS</targetServiceIdentifier>\n" + 
    "      <targetServiceOperation>commit</targetServiceOperation>\n" + 
    "      <messageStatus>RESPONSE</messageStatus>\n"+
    "      <messagePayload> "+
    "         <xmlSchemaDefinition>http://test1</xmlSchemaDefinition> "+
    "         <mytest1><first1>harsh</first1><last1>marwaha</last1></mytest1> "+
    "         <mytest2><first1>harsh</first1><last1>marwaha</last1></mytest2> "+
    "      </messagePayload> "+
    "    </businessResponseMessage>\n" +     
    "  </response>\n" + 
    "</caXchangeResponseMessage>";   
    
    
    static String testResponse2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    "<caXchangeResponseMessage xmlns=\"http://caXchange.nci.nih.gov/messaging\">" +
        "<responseMetadata>" +
        "   <externalIdentifier>myExternalIdentifier</externalIdentifier>" +
        "  <caXchangeIdentifier>c87a1fa0-9b83-11dc-8b10-c2ed0a64d6d3</caXchangeIdentifier>" +
        "</responseMetadata>" +
        "<response>" +
        "<responseStatus>FAILURE</responseStatus>" +
        "<targetResponse>" +
        "<targetServiceIdentifier>PROCESS</targetServiceIdentifier>" +
        "<targetServiceOperation>STUDY_CREATION</targetServiceOperation>" +
        "<targetMessageStatus>ERROR</targetMessageStatus>" +
        "<targetError>" +
        "<errorCode>NA</errorCode>" +
        "<errorDescription>Invalid element in gov.nih.nci.ccts.grid.Study - systemAssignedIdentifier</errorDescription>" +
        "</targetError>" +
        "</targetResponse>" +
        "<targetResponse>" +
        "<targetServiceIdentifier>PROCESS</targetServiceIdentifier>" +
        "<targetServiceOperation>STUDY_CREATION</targetServiceOperation>" +
        "<targetMessageStatus>ERROR</targetMessageStatus>" +
        "<targetError><errorCode>NA</errorCode>" +
        "<errorDescription>Invalid element in gov.nih.nci.ccts.grid.Study - systemAssignedIdentifier</errorDescription>" +
        "</targetError>" +
        "</targetResponse>" +
        "</response>" +
        "</caXchangeResponseMessage>";
    
    String responseText;
    
    

    public ResponseHandler() {
       
    }
    
    
 
    /**
     * Parse the response text from caXchange.
     * 
     * @return parsed ResponseMessage object
     * @throws Exception
     */
    public ResponseMessage getResponse() throws Exception {
        Reader reader = new StringReader(responseText);
        ResponseMessage rm =(ResponseMessage)Utils.deserializeObject(reader,ResponseMessage.class);
        return rm;
    }
    
    /**
     * Creates an ResponseMessage with error details for the given request Message.
     * 
     * @param caXchangeRequestMessage
     * @param errCode
     * @param errMessage
     * @return
     * @throws Exception
     */
    public ResponseMessage getResponseFromError(gov.nih.nci.caxchange.Message caXchangeRequestMessage, String errCode, String errMessage )throws Exception {
       ResponseMessage errorResponse = new ResponseMessage();
       Metadata metadata= caXchangeRequestMessage.getMetadata();
       if (metadata != null) {
          ResponseMetadata responseMetadata = new ResponseMetadata();
          responseMetadata.setCaXchangeIdentifier(metadata.getCaXchangeIdentifier());
          responseMetadata.setExternalIdentifier(metadata.getExternalIdentifier());
          errorResponse.setResponseMetadata(responseMetadata);
       }
       Response response = new  Response();
       response.setResponseStatus(Statuses.FAILURE);
       ErrorDetails errorDetails = new ErrorDetails();
       errorDetails.setErrorCode(errCode);
       errorDetails.setErrorDescription(errMessage);
       response.setCaXchangeError(errorDetails);
       
       errorResponse.setResponse(response);
       
       return errorResponse;
       
    }


    /*
     * Set the responseText from caXchange.
     */ 
    public void setResponseText(String responseText) throws Exception{
        this.responseText = responseText;
    }

    public String getResponseText() throws Exception{
        return responseText;
    }
    
    public static void main(String[] args) {
        try  {
            ResponseHandler rh = new ResponseHandler();
            rh.setResponseText(testResponse2);
            ResponseMessage rm = rh.getResponse();
        } catch (Exception ex)  {
            ex.printStackTrace();
        } finally  {
        }
        
    }
}
