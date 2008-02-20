package gov.nih.nci.caxchange.servicemix.bean.util;


import gov.nih.nci.caXchange.messaging.CaXchangeRequestMessageDocument;

import gov.nih.nci.caXchange.messaging.CaXchangeResponseMessageDocument;
import gov.nih.nci.caXchange.messaging.Credentials;
import gov.nih.nci.caXchange.messaging.ErrorDetails;
import gov.nih.nci.caXchange.messaging.Message;

import gov.nih.nci.caXchange.messaging.MessagePayload;
import gov.nih.nci.caXchange.messaging.MessageStatuses;
import gov.nih.nci.caXchange.messaging.MessageTypes;


import gov.nih.nci.caXchange.messaging.Metadata;
import gov.nih.nci.caXchange.messaging.Operations;
import gov.nih.nci.caXchange.messaging.Request;

import gov.nih.nci.caXchange.messaging.Response;
import gov.nih.nci.caXchange.messaging.ResponseMessage;

import gov.nih.nci.caXchange.messaging.ResponseMetadata;
import gov.nih.nci.caXchange.messaging.Statuses;

import gov.nih.nci.caXchange.messaging.TargetResponseMessage;

import java.io.StringReader;

import javax.jbi.messaging.NormalizedMessage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;

import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.expression.JAXPNodeSetXPathExpression;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;

import org.apache.servicemix.jbi.jaxp.StringSource;

import org.apache.xmlbeans.XmlOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

public class XPathUtil {
 
    public static final String CAXCHANGE_URI="http://caXchange.nci.nih.gov/messaging";
    static Logger logger=LogManager.getLogger(XPathUtil.class);

    private CaXchangeNamespaceContext nsc;
    private CaXchangeRequestMessageDocument requestDocument;
    
    String myDocument = "<caXchangeRequestMessage xmlns=\"http://caXchange.nci.nih.gov/mes\n" + 
    "saging\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
    "  <metaData>\n" + 
    "    <messageType>STUDY_CREATION</messageType>\n" + 
    "    <externalIdentifier>myExternalIdentifier</externalIdentifier>\n" + 
    "    <caXchangeIdentifier>0da440a0-8ff5-11dc-babe-d4801775f7f7</caXchangeIdentifi\n" + 
    "er>\n" + 
    "    <credentials>\n" + 
    "      <groupName xsi:nil=\"true\"/>\n" + 
    "      <gridIdentifier xsi:nil=\"true\"/>\n" + 
    "    </credentials>\n" + 
    "  </metaData>\n" + 
    "  <request>\n" + 
    "    <businessMessagePayload>\n" + 
    "      <xmlSchemaDefinition>http://test</xmlSchemaDefinition>\n" + 
    "      <mytest1 xmlns=\"\">\n" + 
    "        <first1>harsh</first1>\n" + 
    "        <last1>marwaha</last1>\n" + 
    "      </mytest1>\n" + 
    "    </businessMessagePayload>\n" + 
    "  </request>\n" + 
    "</caXchangeRequestMessage>";
    
    String myDocumentAgg = "<aggregateResponse>"+
    " <targetResponse xmlns=\"http://caXchange.nci.nih.gov/messaging\">\n" + 
    "                <targetServiceIdentifier>targetServiceIdentifier0</targetServiceIdentifier>\n" + 
    "                <targetServiceOperation>targetServiceOperation0</targetServiceOperation>\n" + 
    "                <targetMessageStatus>RESPONSE</targetMessageStatus>\n" + 
    "                <messagePayload>\n" + 
    "                    <xmlSchemaDefinition>http://www.oxygenxml.com/</xmlSchemaDefinition>\n" + 
    "<mytest1>\n" + 
    "   <first>Harsh</first>\n" + 
    "   <last>Marwaha</last>\n" + 
    "</mytest1> "+
    "                </messagePayload>\n" + 
    "            </targetResponse>\n" + 
    "<targetResponse> " + 
    " <targetServiceIdentifier>PROCESS</targetServiceIdentifier> " + 
    " <targetServiceOperation>STUDY_CREATION</targetServiceOperation> " + 
    " <targetMessageStatus>ERROR</targetMessageStatus> " + 
    " <targetError><errorCode>NA</errorCode><errorDescription>Invalid element in gov.nih.nci.ccts.grid.Study - systemAssignedIdentifier</errorDescription></targetError> " + 
    " </targetResponse>"+
    "    </aggregateResponse>";    
    
    NormalizedMessage in;
    
    
    public XPathUtil() {
    }
    
    public void initialize() throws Exception{
        if (in.getContent() instanceof DOMSource) {
            DOMSource ds= (DOMSource)in.getContent();
            logger.debug("ds.node is "+ds.getNode().getNamespaceURI()+" "+ds.getNode().getNodeName());
            requestDocument =CaXchangeRequestMessageDocument.Factory.parse(((DOMSource)in.getContent()).getNode());
        }
        else if (in.getContent() instanceof StreamSource) {
            StreamSource ss = (StreamSource)in.getContent();
            requestDocument = CaXchangeRequestMessageDocument.Factory.parse(ss.getInputStream());
        }
    }
    
    public String getUserName() throws Exception{
        Credentials credentials = requestDocument.getCaXchangeRequestMessage().getMetadata().getCredentials();
        String userName=null;
        if (credentials != null) {
            userName=credentials.getUserName();
        }
        return userName;
        
    }
    
    public String getDelegatedCredentialReference() throws Exception{
        Credentials credentials = requestDocument.getCaXchangeRequestMessage().getMetadata().getCredentials();
        String delegatedCredentialReference=null;
        if (credentials != null) {
        	delegatedCredentialReference=credentials.getDelegatedCredentialReference();
        }
        return delegatedCredentialReference;
        
    }
    
    public String getCaXchangeIdentifier() throws Exception {
        return requestDocument.getCaXchangeRequestMessage().getMetadata().getCaXchangeIdentifier();
    }
    
    public String getExternalIdentifier() throws Exception {
        return requestDocument.getCaXchangeRequestMessage().getMetadata().getExternalIdentifier();
    }    
    
    public String getMessageType() throws Exception {
        return requestDocument.getCaXchangeRequestMessage().getMetadata().getMessageType().toString();
    }        
    

    
    public Source generateResponseFromErroredRequest(String errCode, String errMessage) throws Exception {
        CaXchangeResponseMessageDocument responseDocument = CaXchangeResponseMessageDocument.Factory.newInstance();
        ResponseMessage responseMessage = responseDocument.addNewCaXchangeResponseMessage();
        ResponseMetadata responseMetadata = responseMessage.addNewResponseMetadata();
        responseMetadata.setExternalIdentifier(getExternalIdentifier());
        responseMetadata.setCaXchangeIdentifier(getCaXchangeIdentifier());
        Response response = responseMessage.addNewResponse();
        response.setResponseStatus(Statuses.FAILURE);
        ErrorDetails errorDetails = response.addNewCaXchangeError();
        errorDetails.setErrorCode(errCode);
        errorDetails.setErrorDescription(errMessage);
        
        return new DOMSource(responseDocument.getDomNode());
        
    }
    
    public CaXchangeResponseMessageDocument generateResponseFromAggregatedResponse(Source aggregatedResponse) throws Exception {
        CaXchangeResponseMessageDocument responseDocument = CaXchangeResponseMessageDocument.Factory.newInstance();
        ResponseMessage responseMessage = responseDocument.addNewCaXchangeResponseMessage();
        ResponseMetadata responseMetaData= responseMessage.addNewResponseMetadata();
        Metadata metaData = requestDocument.getCaXchangeRequestMessage().getMetadata();
        responseMetaData.setCaXchangeIdentifier(metaData.getCaXchangeIdentifier());
        responseMetaData.setExternalIdentifier(metaData.getExternalIdentifier());
        Response response = responseMessage.addNewResponse();
        response = buildResponse(aggregatedResponse, response);
        
        
        return responseDocument;
    }
    
    public Response buildResponse(Source aggregatedResponse, Response response) throws Exception{
        XPath xpath = XPathFactory.newInstance().newXPath();
        CaXchangeNamespaceContext cnc = new CaXchangeNamespaceContext();
        cnc.addNameSpace("caxchange","http://caXchange.nci.nih.gov/messaging");
        Node node = ((DOMSource)aggregatedResponse).getNode();
        XPathExpression expression = xpath.compile("/aggregateResponse/targetResponse");
        XPathExpression tsiExp = xpath.compile("targetServiceIdentifier");
        XPathExpression tsoExp = xpath.compile("targetServiceOperation");
        XPathExpression msExp = xpath.compile("targetMessageStatus");
        XPathExpression mpExp = xpath.compile("messagePayload");
        XPathExpression teExp = xpath.compile("targetError");
        XPathExpression schemaDefExp = xpath.compile("xmlSchemaDefinition");
        NodeList nodes = (NodeList)expression.evaluate(node,XPathConstants.NODESET);
        Statuses.Enum responseStatus = Statuses.SUCCESS;
        for(int i=0;i<nodes.getLength();i++) {
           Node brNode = nodes.item(i);
           TargetResponseMessage brm = response.addNewTargetResponse();
           brm.setTargetServiceIdentifier((String)tsiExp.evaluate(brNode, XPathConstants.STRING));
           brm.setTargetServiceOperation((String)tsoExp.evaluate(brNode, XPathConstants.STRING));
           String targetMessageStatus = (String)msExp.evaluate(brNode, XPathConstants.STRING);
           if (MessageStatuses.FAULT.toString().equals(targetMessageStatus)) {
               brm.setTargetMessageStatus(MessageStatuses.FAULT);
               responseStatus = Statuses.FAILURE;
           }else if (MessageStatuses.ERROR.toString().equals(targetMessageStatus)) {
               brm.setTargetMessageStatus(MessageStatuses.ERROR);
               responseStatus = Statuses.FAILURE;
           }
           else if (MessageStatuses.RESPONSE.toString().equals(targetMessageStatus)) {
               brm.setTargetMessageStatus(MessageStatuses.RESPONSE);
           }
           Node mpNode =  (Node)mpExp.evaluate(brNode, XPathConstants.NODE);
           if (mpNode != null) {
               MessagePayload payload= brm.addNewTargetBusinessMessage();
               payload.setXmlSchemaDefinition((String)schemaDefExp.evaluate(mpNode, XPathConstants.STRING));
               Node payloadNode = payload.getDomNode();
               Document doc = payloadNode.getOwnerDocument();
               NodeList mpNodes = mpNode.getChildNodes();
               for(int k=0;k<mpNodes.getLength();k++) {
                   Node mpcNode = mpNodes.item(k);
                   if (!("xmlSchemaDefinition".equals(mpcNode.getNodeName()))) {
                      Node importedNode= doc.importNode(mpcNode, true);
                      payloadNode.appendChild(importedNode);
                   }
               }
           }
           Node teNode = (Node)teExp.evaluate(brNode, XPathConstants.NODE);
           if (teNode!= null) {
              ErrorDetails targetError = ErrorDetails.Factory.newInstance();
              NodeList teNodes=teNode.getChildNodes();              
              for (int k=0;k<teNodes.getLength();k++) {
                  Node teChildNode = teNodes.item(k);
                  if ("errorCode".equals(teChildNode.getNodeName())) {
                      targetError.setErrorCode(teChildNode.getTextContent());
                  }
                  if ("errorDescription".equals(teChildNode.getNodeName())) {
                      targetError.setErrorDescription(teChildNode.getTextContent());
                  }                  
              }
              brm.setTargetError(targetError);
           }
        }
        response.setResponseStatus(responseStatus);
        return response;
    }
    
    public boolean isRollbackRequired(Source aggregatedResponse) throws Exception{
        XPath xpath = XPathFactory.newInstance().newXPath();
        Node node = ((DOMSource)aggregatedResponse).getNode();
        XPathExpression expression = xpath.compile("/aggregateResponse/businessResponse/messageStatus");
        NodeList nodes = (NodeList)expression.evaluate(node,XPathConstants.NODESET);
        for(int i=0;i<nodes.getLength();i++) {
            Node brNode =  nodes.item(i);
            String messageStatus = brNode.getTextContent();
            if ((MessageStatuses.FAULT.toString().equals(messageStatus))||
                 (MessageStatuses.FAULT.toString().equals(messageStatus))){
                     return true;
                 }
        }
        return false;
    }
    
    public Source getRollbackMessage() throws Exception {
        requestDocument.getCaXchangeRequestMessage().getMetadata().setOperation(Operations.ROLLBACK);
        return new DOMSource(requestDocument.getDomNode());
    }
    

    public void setIn(NormalizedMessage in) {
        this.in = in;
    }

    public NormalizedMessage getIn() {
        return in;
    }
    
    public static void main(String [] args) {
        try  {
            XPathUtil util = new XPathUtil();
            //util.generateResponseFromAggregatedResponse(null);
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document= db.parse(new InputSource(new StringReader(util.myDocumentAgg)));
            DOMSource ds = new DOMSource(document);
            //util.isRollbackRequired(ds);
            Response response = Response.Factory.newInstance();
            util.buildResponse(ds, response);
            System.out.println(response);
            /*util.st= new SourceTransformer();
            util.document= util.st.toDOMDocument(new StringSource(util.myDocument));
            util.expression = new JAXPNodeSetXPathExpression();
            util.nsc = new CaXchangeNamespaceContext();
            util.nsc.addNameSpace("caxchange",CAXCHANGE_URI);
            util.expression.setNamespaceContext(util.nsc);
            util.isRollbackRequired();
            */
            /*
            System.out.println( util.isCaXchangeRequest());
            util.expression = new JAXPNodeSetXPathExpression();
            System.out.println(util.getExternalIdentifier());
            util.expression = new JAXPNodeSetXPathExpression();
            System.out.println(util.getCaXchangeIdentifier()); */
        } catch (Exception ex)  {
            ex.printStackTrace();
        } finally  {
        }
        
    }
 
}
