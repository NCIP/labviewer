/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.hub;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.Metadata;
import gov.nih.nci.caxchange.Request;
import gov.nih.nci.caxchange.Statuses;
import gov.nih.nci.caxchange.TargetResponseMessage;
import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.services.pa.StudyProtocol;
import gov.nih.nci.lv.dto.IntegrationHubDto;
import gov.nih.nci.lv.util.LVConstants;
import gov.nih.nci.lv.util.LVException;
import gov.nih.nci.lv.util.LVUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * This class will have the service code for integrating with coppa.
 * @author Naveen Amiruddin
 *
 */
public class COPPAHub extends IntegrationHub {

    private static final Logger LOG = Logger.getLogger(COPPAHub.class);


    /**
     *
     * @param iHubDto iHubDto
     * @return StudyProtocol Coppa Study Protocol
     * @throws LVException on error
     */
    public StudyProtocol invokeCoppaStudy(IntegrationHubDto iHubDto) throws LVException  {
        iHubDto.setTarget(LVConstants.STUDY_PROTOCOL);
        iHubDto.setOperationName("search");
        iHubDto.setServiceType(LVConstants.STUDY_PROTOCOL);
        iHubDto.setExternalIdentifier("CTODS");
        createMessageElementArray(iHubDto);
        iHubDto.setRequestMessage(getRequestMessage(iHubDto));
        iHubDto.setRequestMessage(getRequestMessage(iHubDto));
        iHubDto.setResponseObj(getResponseObj(iHubDto));
        return getCoppaStudy(iHubDto);
    }

    /**
     *
     * @param iHubDto iHubDto
     * @return StudyProtocol Coppa Study Protocol
     * @throws LVException on error
     */
    public Person invokeCoppaPerson(IntegrationHubDto iHubDto) throws LVException  {
        if (LVUtils.isIINull(iHubDto.getCoppaEntityId())) {
            return null;
        }
        createMessageElementArray(iHubDto);
        invoke(iHubDto);
        return getCoppaPerson(iHubDto);
    }

    /**
     *
     * @param iHubDto iHubDto
     * @return StudyProtocol Coppa Study Protocol
     * @throws LVException on error
     */
    public Organization invokeCoppaOrganization(IntegrationHubDto iHubDto) throws LVException  {
        if (LVUtils.isIINull(iHubDto.getCoppaEntityId())) {
            return null;
        }
        createMessageElementArray(iHubDto);
        invoke(iHubDto);
        return getCoppaOrganization(iHubDto);
    }

    private void invoke(IntegrationHubDto iHubDto) throws LVException  {
        iHubDto.setRequestMessage(getRequestMessage(iHubDto));
        iHubDto.setRequestMessage(getRequestMessage(iHubDto));
        iHubDto.setResponseObj(getResponseObj(iHubDto));
    }

    @Override
    Message getRequestMessage(IntegrationHubDto iHubDto) throws LVException {
        Message message = new Message();
        Metadata metadata = new Metadata();
        metadata.setExternalIdentifier(iHubDto.getExternalIdentifier());
        metadata.setServiceType(iHubDto.getServiceType());
        metadata.setOperationName(iHubDto.getOperationName());
        metadata.setCredentials(iHubDto.getCredential());
        MessagePayload messagePayload = new MessagePayload();
        URI uri = new URI();
        try {
            uri.setPath("http://coppa.nci.nih.gov");
        } catch (MalformedURIException e) {
            LOG.error("MalformedURIException occured while creating the caXchange message: ", e);
            throw new LVException(e);
        }
        messagePayload.setXmlSchemaDefinition(uri);
        messagePayload.set_any(iHubDto.getMeArray());
        message.setMetadata(metadata);
        message.setRequest(new Request());
        message.getRequest().setBusinessMessagePayload(messagePayload);
        return message;
    }

    private LimitOffset createLimitOffSet() {
        LimitOffset limit = new LimitOffset();
        limit.setLimit(LVConstants.MAX_SEARCH_RESULTS);
        limit.setOffset(0);
        return limit;
    }

    private void createMessageElementArray(IntegrationHubDto iHubDto) throws LVException {
        if (LVConstants.STUDY_PROTOCOL.equals(iHubDto.getTarget())) {
            iHubDto.setMeArray(createCoppaStudyMessageElement(iHubDto));
        } else if (LVConstants.PERSON.equals(iHubDto.getTarget())
                || LVConstants.ORGANIZATION.equals(iHubDto.getTarget())) {
            iHubDto.setMeArray(createCoppaEntityMessageElement(iHubDto));
        }
    }

    private MessageElement[] createCoppaEntityMessageElement(IntegrationHubDto iHubDto) throws LVException {
        MessageElement[] meArray = new MessageElement[1];
        QName qName = new QName("http://po.coppa.nci.nih.gov", "Id");
        meArray[0] = createMessageElement(iHubDto.getCoppaEntityId(), qName ,
                "/gov/nih/nci/coppa/services/client/client-config.wsdd");
        return meArray;
    }

    private MessageElement[] createCoppaStudyMessageElement(IntegrationHubDto iHubDto) throws LVException {
        MessageElement[] meArray = new MessageElement[2];
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setAssignedIdentifier(iHubDto.getCoppaIi());
        QName qName = new QName("http://pa.services.coppa.nci.nih.gov", "StudyProtocol");
        meArray[0] = createMessageElement(studyProtocol, qName ,
                "/gov/nih/nci/coppa/services/pa/client/client-config.wsdd");
        qName = new QName("http://common.coppa.nci.nih.gov", "LimitOffset");
        meArray[1] = createMessageElement(createLimitOffSet(), qName ,
                "/gov/nih/nci/coppa/services/pa/client/client-config.wsdd");
        return meArray;
    }

    private MessageElement createMessageElement(Object object, QName qName , String wsdl) throws LVException
    {
        MessageElement messageElement = null;
        StringWriter writer = new StringWriter();
        InputStream wsdd = getClass().getResourceAsStream(wsdl);
        try {
            Utils.serializeObject(object, qName, writer, wsdd);
            byte[] byteArray = writer.toString().getBytes();
            InputStream inputStream = new ByteArrayInputStream(byteArray);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            messageElement = new MessageElement(document.getDocumentElement());
        } catch (Exception e) {
            LOG.error("Exception occured while creating message element: ", e);
            throw new LVException(e);
        }
        return messageElement;
    }

    /**
     * from the request object, it extracts a single coppa study. since we are searching with nci id.
     * its assumed it will return a single study.
     * @param iHubDto iHubDto
     * @return StudyProtocol
     * @throws LVException on error
     */
    private StudyProtocol getCoppaStudy(IntegrationHubDto iHubDto) throws LVException {
        StudyProtocol studyProtocol = null;
        if (iHubDto.getResponseObj().getResponseStatus().equals(Statuses.FAILURE))   {
            return studyProtocol;
        }

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            MessageElement messageElement = iHubDto.getResponseObj().getTargetResponse()[0].
                getTargetBusinessMessage().get_any()[0];
            List<MessageElement> childElements = messageElement.getChildren();
            if (childElements != null) {
                for (MessageElement childElement : childElements) {
                    Element element = childElement.getAsDOM();
                    StringWriter writer = new StringWriter();
                    transformer.transform(new DOMSource(element), new StreamResult(writer));
                    StringReader reader = new StringReader(writer.toString());
                    InputStream wsdd =
                        getClass().getResourceAsStream("/gov/nih/nci/coppa/services/pa/client/client-config.wsdd");
                    studyProtocol = (StudyProtocol) Utils.deserializeObject(reader, StudyProtocol.class, wsdd);
                    // we can break after the reading the first object. its not expeced
                    break;
                }
            }
        } catch (Exception e) {
            LOG.error("Exception occured while getting PA study protocols: ", e);
            throw new LVException(e);
        }
        return studyProtocol;
    }

    private Person getCoppaPerson(IntegrationHubDto iHubDto) throws LVException {
        Person person = null;
        if (iHubDto.getResponseObj().getResponseStatus().equals(Statuses.FAILURE))   {
            return person;
        }
        try {
            javax.xml.transform.Transformer stringTransformer =   TransformerFactory.newInstance().newTransformer();
            for (TargetResponseMessage msg : iHubDto.getResponseObj().getTargetResponse()) {
                MessageElement[] messagePay = msg.getTargetBusinessMessage().get_any();
                for (MessageElement mEle : messagePay) {
                    if (mEle != null) {
                        Element el = mEle.getAsDOM();
                        StringWriter sw = new StringWriter();
                        stringTransformer.transform(new DOMSource(el), new StreamResult(sw));
                        InputStream wsddIs = getClass().getResourceAsStream(
                                        "/gov/nih/nci/coppa/services/client/client-config.wsdd");
                        person =
                                (gov.nih.nci.coppa.po.Person) Utils.deserializeObject(
                                        new StringReader(sw.toString()),  gov.nih.nci.coppa.po.Person.class, wsddIs);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Exception occured while getting Coppa Person ", e);
            throw new LVException(e);
        }
        return person;
    }

    private Organization getCoppaOrganization(IntegrationHubDto iHubDto) throws LVException {
        Organization organization = null;
        if (iHubDto.getResponseObj().getResponseStatus().equals(Statuses.FAILURE))   {
            return organization;
        }
        try {
            javax.xml.transform.Transformer stringTransformer =   TransformerFactory.newInstance().newTransformer();
            for (TargetResponseMessage msg : iHubDto.getResponseObj().getTargetResponse()) {
                MessageElement[] messagePay = msg.getTargetBusinessMessage().get_any();
                for (MessageElement mEle : messagePay) {
                    if (mEle != null) {
                        Element el = mEle.getAsDOM();
                        StringWriter sw = new StringWriter();
                        stringTransformer.transform(new DOMSource(el), new StreamResult(sw));
                        InputStream wsddIs = getClass().getResourceAsStream(
                                        "/gov/nih/nci/coppa/services/client/client-config.wsdd");
                        organization =
                                (Organization) Utils.deserializeObject(
                                        new StringReader(sw.toString()),  Organization.class, wsddIs);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Exception occured while getting Coppa Organization ", e);
            throw new LVException(e);
        }
        return organization;
    }

}
