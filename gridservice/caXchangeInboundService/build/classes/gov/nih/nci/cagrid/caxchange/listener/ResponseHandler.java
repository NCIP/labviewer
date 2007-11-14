package gov.nih.nci.cagrid.caxchange.listener;

import gov.nih.nci.caXchange.messaging.CaXchangeResponseMessageDocument;
import gov.nih.nci.caxchange.ErrorDetails;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.MessageStatuses;
import gov.nih.nci.caxchange.Metadata;
import gov.nih.nci.caxchange.Response;
import gov.nih.nci.caxchange.ResponseMessage;

import gov.nih.nci.caxchange.ResponseMetadata;
import gov.nih.nci.caxchange.Statuses;

import gov.nih.nci.caxchange.TargetResponseMessage;

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
    
    String responseText;
    CaXchangeResponseMessageDocument responseMessageDocument=null;
    
    static final String FAILURE = "FAILURE";
    static final String SUCCESS = "SUCCESS";
    static final String CAXCHANGEIDENTIFIER = "/caXchangeResponseMessage/responseMetaData/caXchangeIdentifier";
    static final String EXTERNALIDENTIFIER = "/caXchangeResponseMessage/responseMetaData/externalIdentifier";
    static final String RESPONSESTATUS = "/caXchangeResponseMessage/response/responseStatus";
    static final String ERRORDETAILS = "/caXchangeResponseMessage/response/caXchangeError";
    static final String ERRORCODE = "/caXchangeResponseMessage/response/caXchangeError/errorCode";
    static final String ERRORDESC = "/caXchangeResponseMessage/response/caXchangeError/errorDescription";
    static final String XMLSCHEMADEF = "xmlSchemaDefinition";
    static final String MESSAGEPAYLOAD = "/messagePayload/*";
    static final String TARGETRESPONSEMESSAGE = "/caXchangeResponseMessage/response/targetResponse";
    static final String TARGETSERVICEID = "/targetServiceIdentifier";
    static final String TARGETSERVICEOP = "/targetServiceOperation";
    static final String TARGETSERVICEMS = "/targetMessageStatus";


    

    XPath xpath = XPathFactory.newInstance().newXPath();

    
    
    public ResponseHandler() {
       
    }
    
    
    public TargetResponseMessage[] getTargetResponseMessages() throws Exception {
        gov.nih.nci.caXchange.messaging.TargetResponseMessage[] sourceTargetResponseMessages =
                           responseMessageDocument.getCaXchangeResponseMessage().getResponse().getTargetResponseArray();
        if ((sourceTargetResponseMessages!=null)&&(sourceTargetResponseMessages.length>0)) {
            TargetResponseMessage[] targetResponseMessages= new TargetResponseMessage[sourceTargetResponseMessages.length];
            int i=0;
            for(gov.nih.nci.caXchange.messaging.TargetResponseMessage sourceTRM:sourceTargetResponseMessages ) {
                TargetResponseMessage trm = new TargetResponseMessage();
                trm.setTargetServiceOperation(sourceTRM.getTargetServiceOperation());
                trm.setTargetServiceIdentifier(sourceTRM.getTargetServiceIdentifier());
                gov.nih.nci.caXchange.messaging.MessageStatuses.Enum sourceMS= sourceTRM.getTargetMessageStatus();
                if (sourceMS.equals(gov.nih.nci.caXchange.messaging.MessageStatuses.FAULT)) {
                    trm.setTargetMessageStatus(MessageStatuses.FAULT);
                }else if (sourceMS.equals(gov.nih.nci.caXchange.messaging.MessageStatuses.ERROR)) {
                    trm.setTargetMessageStatus(MessageStatuses.ERROR);
                }else if (sourceMS.equals(gov.nih.nci.caXchange.messaging.MessageStatuses.RESPONSE)) {
                    trm.setTargetMessageStatus(MessageStatuses.RESPONSE);
                }
                if (sourceTRM.getTargetError()!=null) {
                    ErrorDetails targetError = new ErrorDetails();
                    gov.nih.nci.caXchange.messaging.ErrorDetails sourceTargetError = sourceTRM.getTargetError();
                    targetError.setErrorCode(sourceTargetError.getErrorCode());
                    targetError.setErrorDescription(sourceTargetError.getErrorDescription());
                    trm.setTargetError(targetError);
                }
                if (sourceTRM.getTargetBusinessMessage()!=null) {
                    MessagePayload mp = new MessagePayload();
                    gov.nih.nci.caXchange.messaging.MessagePayload sourceMP = sourceTRM.getTargetBusinessMessage();
                    mp.setXmlSchemaDefinition(new URI(sourceMP.getXmlSchemaDefinition()));
                    Node node = sourceMP.getDomNode();
                    XPathExpression exp = xpath.compile("*");
                    NodeList sourceNodes = (NodeList)exp.evaluate(node, XPathConstants.NODESET);
                    if ((sourceNodes != null)&&(sourceNodes.getLength()>0)) {
                        List mes = new ArrayList();
                        for(int k=0;k<sourceNodes.getLength();k++) {
                            Node sourceNode = sourceNodes.item(k);
                            if (!(XMLSCHEMADEF.equals(sourceNode.getNodeName()))) {
                                MessageElement me = new MessageElement((Element)sourceNode);
                                mes.add(me);
                            }
                        }
                        MessageElement[] meArr = null;
                        if (!(mes.isEmpty())) {
                           meArr = new MessageElement[mes.size()];
                           Iterator mesIterator = mes.iterator();
                           int j=0;
                           while(mesIterator.hasNext()) {
                               meArr[j++]= (MessageElement)mesIterator.next();
                           }
                        }
                        mp.set_any(meArr);
                    }
                    trm.setTargetBusinessMessage(mp);
                }
                targetResponseMessages[i++]=trm;
            }
            return targetResponseMessages;
        }
       return null;
    }
    
    public ResponseMessage getResponse() throws Exception {
        ResponseMessage rm = new ResponseMessage();
        rm.setResponseMetadata(new ResponseMetadata());
        gov.nih.nci.caXchange.messaging.ResponseMetadata sourceMetadata=responseMessageDocument.getCaXchangeResponseMessage().getResponseMetadata();
        rm.getResponseMetadata().setCaXchangeIdentifier(sourceMetadata.getCaXchangeIdentifier());
        rm.getResponseMetadata().setExternalIdentifier(sourceMetadata.getExternalIdentifier());
        rm.setResponse(new Response());
        gov.nih.nci.caXchange.messaging.Response sourceResponse = responseMessageDocument.getCaXchangeResponseMessage().getResponse();
        if (sourceResponse.getResponseStatus().equals(gov.nih.nci.caXchange.messaging.Statuses.FAILURE)) {
           rm.getResponse().setResponseStatus(Statuses.FAILURE);
        }else if (sourceResponse.getResponseStatus().equals(gov.nih.nci.caXchange.messaging.Statuses.SUCCESS)) {
           rm.getResponse().setResponseStatus(Statuses.SUCCESS);
        }
        if (sourceResponse.getCaXchangeError() != null) {
           ErrorDetails errorDetails = new ErrorDetails();
           gov.nih.nci.caXchange.messaging.ErrorDetails sourceErrorDetails = sourceResponse.getCaXchangeError();
           errorDetails.setErrorCode(sourceErrorDetails.getErrorCode());
           errorDetails.setErrorDescription(sourceErrorDetails.getErrorDescription());
            rm.getResponse().setCaXchangeError(errorDetails);
        }
        if (sourceResponse.getTargetResponseArray()!= null) {
        }
        rm.getResponse().setTargetResponse(getTargetResponseMessages());
        
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



    public void setResponseText(String responseText) throws Exception{
        this.responseText = responseText;
        responseMessageDocument = CaXchangeResponseMessageDocument.Factory.parse(responseText);
    }

    public String getResponseText() throws Exception{
        return responseText;
    }
    
    public static void main(String[] args) {
        try  {
            ResponseHandler rh = new ResponseHandler();
            rh.setResponseText(testResponse1);
            ResponseMessage rm = rh.getResponse();
        } catch (Exception ex)  {
            ex.printStackTrace();
        } finally  {
        }
        
    }
}
