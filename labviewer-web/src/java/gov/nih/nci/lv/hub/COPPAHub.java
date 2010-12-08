/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/

package gov.nih.nci.lv.hub;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.Metadata;
import gov.nih.nci.caxchange.Request;
import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.services.pa.StudyProtocol;
import gov.nih.nci.lv.dto.IntegrationHubDto;
import gov.nih.nci.lv.util.LVConstants;
import gov.nih.nci.lv.util.LVException;

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
        }
        
    }
    
    private MessageElement[] createCoppaStudyMessageElement(IntegrationHubDto iHubDto) throws LVException {
        MessageElement[] meArray = new MessageElement[2];
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setAssignedIdentifier(iHubDto.getCoppaIi());
        QName qName = new QName("http://pa.services.coppa.nci.nih.gov", "StudyProtocol");
        meArray[0] = createMessageElement(studyProtocol, qName);
        qName = new QName("http://common.coppa.nci.nih.gov", "LimitOffset");
        meArray[1] = createMessageElement(createLimitOffSet(), qName);
        return meArray;

    }
    
    private MessageElement createMessageElement(Object object, QName qName) throws LVException
    {
        MessageElement messageElement = null;   
        StringWriter writer = new StringWriter();
        InputStream wsdd = getClass().getResourceAsStream("/gov/nih/nci/coppa/services/pa/client/client-config.wsdd");
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
                }
            }
        } catch (Exception e) {
            LOG.error("Exception occured while getting PA study protocols: ", e);
            throw new LVException(e);
        }
        return studyProtocol;
    }
    
}
