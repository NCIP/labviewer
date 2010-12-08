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

import gov.nih.nci.cagrid.caxchange.client.CaXchangeRequestProcessorClient;
import gov.nih.nci.cagrid.caxchange.context.client.CaXchangeResponseServiceClient;
import gov.nih.nci.cagrid.caxchange.context.stubs.types.CaXchangeResponseServiceReference;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.Metadata;
import gov.nih.nci.caxchange.Request;
import gov.nih.nci.caxchange.Response;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.Statuses;
import gov.nih.nci.caxchange.TargetResponseMessage;
import gov.nih.nci.lv.dto.IntegrationHubDto;
import gov.nih.nci.lv.dto.LabSearchDto;
import gov.nih.nci.lv.util.LVConstants;
import gov.nih.nci.lv.util.LVException;
import gov.nih.nci.lv.util.LVUtils;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.apache.log4j.Logger;

import webservices.Documentation;
import webservices.LabResult;
import webservices.LoadLabsRequest;
import webservices.Participant;
import webservices.PerformedActivity;
import webservices.PerformedStudy;
import webservices.StudySubject;
/**
 * A utility call for all caXhange methods.
 * @author NAmiruddin
 *
 */

public abstract class IntegrationHub {
    private static final Logger LOG = Logger.getLogger(IntegrationHub.class);

    


    void errorOnEmpty(Set<Long> labIds , String target) throws LVException {
        if (labIds == null || labIds.isEmpty()) {
            throw new LVException("Please select labs to loads to " + target);
        }
    }
    
    IntegrationHubDto invokeHub(LabSearchDto labSearchDto , List<LabSearchDto> labs , 
            IntegrationHubDto hubDto)  throws LVException {
        StringBuffer error  = new StringBuffer();
        error.append(hubDto.getServiceName() == null ? "Service Name is null" : "");
        error.append(hubDto.getServiceType() == null ? "Service Type is null" : ""); 
        error.append(hubDto.getHubUrl() == null ? "Hub URL is null" : "");
        error.append(hubDto.getCredentialEpr() == null ? "Credential EPR is null" : "");
        error.append(hubDto.getMessageXml() == null ? "Request message xml name is null" : "");
        error.append(hubDto.getUserName() == null ? "User name is null" : "");
        error.append(hubDto.getQName() == null ? "QName is null" : "");
        error.append(hubDto.getQRequest() == null ? "Q Request is null" : "");
        error.append(hubDto.getTarget() == null ? "Target is null" : "");
        if (labSearchDto.getLabIds() == null) {
            error.append("Minimum one Labs must be selected to submit to caAERS or C3D");
        }
        
        if (error.toString().length() > 0) {
            throw new LVException(error.toString());
        }
            
        hubDto.setRequestObj(createLoadLabRequestObj(labSearchDto, labs, hubDto));
        hubDto.setRequestMessage(getRequestMessage(hubDto));
        hubDto.setResponseObj(getResponseObj(hubDto));
        processResponseMessage(hubDto.getResponseObj());
        return hubDto;
        
    }
    /**
     * converts the labSearch Dto to a Loab Labs Obj.
     * @param labSearchDto labs dto
     * @param labs set to of labs to load to hub
     * @param labIds set of 
     * @param target (must be either CAERS or C3D
     * @throws LVException 
     */
    LoadLabsRequest createLoadLabRequestObj(LabSearchDto labSearchDto , List<LabSearchDto> labs ,  
            IntegrationHubDto hubDto) 
    throws LVException {
        LoadLabsRequest labRequest = new LoadLabsRequest();
        String target = hubDto.getTarget();
        String spExtn = (target.equals("C3D") ? "STUDY:" + hubDto.getStudyProtocolExtn() 
                : hubDto.getStudyProtocolExtn());
        // do error checks
        Set<Long> labIds = LVUtils.convertStringToSet(labSearchDto.getLabIds(), ",");
        errorOnEmpty(labIds, target);
        if (hubDto.getStudyProtocolExtn() == null) {
            throw new LVException("Cannot load Labs, Study Protocol Identifier is null ");
        }
        if (labSearchDto.getStudyParticipantId() == null) {
            throw new LVException("Cannot load Labs, Study Participant Id is null ");
        }
        
        int size = labIds.size();
        LabResult[] labResults = new LabResult[size];
        int i = 0;
        for (LabSearchDto labDto : labs) {
            if (!labIds.contains(labDto.getId())) {
                continue;
            }
            // Populate the study information
            Documentation documentation = new Documentation();
            PerformedStudy performedStudy = new PerformedStudy();
            // Set the study identifier on the document
            documentation.setII(generateIiArray(spExtn, hubDto.getServiceName() , target));
            Documentation[] docs = new Documentation[1];
            docs[0] = documentation;
            performedStudy.setDocumentation(docs);

            // Then set the participant and study subject assignment identifiers
            Participant participant = new Participant();
            StudySubject studySubject = new StudySubject();
            List<String> ids = labDto.getStudySubjectIdentifiers();
            if (!ids.isEmpty()) {
                String id = ids.get(0);
                id = (LVConstants.C3D.equals(target) ? "MRN:" + id : id);
                participant.setII(generateIiArray(id, hubDto.getServiceName() , target));
                id = (LVConstants.C3D.equals(target) ? "PATIENTPOSITION:" + id : id);
                studySubject.setII(generateIiArray(id, hubDto.getServiceName() , target));
            }
            studySubject.setParticipant(participant);
            studySubject.setPerformedStudy(performedStudy);
            // Set the activity name
            PerformedActivity performedActivity = new PerformedActivity();
            performedActivity.setName(labDto.getLabTestCode());
            PerformedActivity[] performedActivitys = new PerformedActivity[1];
            performedActivitys[0] = performedActivity;
            studySubject.setPerformedActivity(performedActivitys);
            // Then set the lab result
            LabResult labResult = new LabResult();
            labResult.setStudySubject(studySubject);
            // Set the reported date
            Calendar cal = Calendar.getInstance();
            cal.setTime(labDto.getActualStartDate());
            labResult.setReportedDateTime(cal);
            // Set the lab result details
            labResult.setNumericResult(labDto.getNumericResult());
            labResult.setTextResult(labDto.getTextResult());
            labResult.setNumericUnit(labDto.getUom());
            labResult.setReferenceRangeLow(labDto.getReferenceLowRange());
            labResult.setReferenceRangeHigh(labDto.getReferenceHighRange());
            labResults[i++] = labResult;
        }
        labRequest.setLabResult(labResults);
        return labRequest;
    }
    
    Message getRequestMessage(IntegrationHubDto hubDto) throws LVException {
        try {
            QName qName = new QName(hubDto.getQName(), hubDto.getQRequest());
            Object requestObj = hubDto.getRequestObj();
            Utils.serializeObject(requestObj, qName , new PrintWriter(hubDto.getMessageXml()));
            Message requestMessage = new Message();
            Metadata metadata = new Metadata();
            metadata.setExternalIdentifier(hubDto.getExternalIdentifier());
            metadata.setCredentials(hubDto.getCredential());
            metadata.setServiceType(hubDto.getServiceType());
            requestMessage.setMetadata(metadata);
            requestMessage.setRequest(new Request());
            MessagePayload messagePayload = new MessagePayload();
            URI uri = new URI();
            uri.setPath(hubDto.getQName());
            messagePayload.setXmlSchemaDefinition(uri);
            MessageElement messageElement = new MessageElement(qName, requestObj);
            messagePayload.set_any(new MessageElement[] {messageElement });
            requestMessage.getRequest().setBusinessMessagePayload(messagePayload);
            return requestMessage;
        } catch (Exception e) {
            throw new LVException(e);
        }
    }
    
    Response getResponseObj(IntegrationHubDto hubDto) throws LVException {
        try {
            CaXchangeRequestProcessorClient client =
                new CaXchangeRequestProcessorClient(hubDto.getHubUrl(), hubDto.getGlobusCredential());
            CaXchangeResponseServiceReference crsr =  client.processRequestAsynchronously(hubDto.getRequestMessage());
            CaXchangeResponseServiceClient responseService =   
                new CaXchangeResponseServiceClient(crsr.getEndpointReference(), hubDto.getGlobusCredential());
            boolean gotResponse = false;
            int responseCount = 0;
            ResponseMessage responseMessage = null;
            while (!gotResponse) {
                try {
                    responseMessage = responseService.getResponse();
                    gotResponse = true;
                } catch (Exception e) {
                    LOG.info("No response from caxchange", e);
                    responseCount++;
                    if (responseCount > LVConstants.HUB_TOT_CNT) {
                        LOG.error("Never got a response from Integration hub");
                        throw new LVException("No response from Integration hub");
                    }
                    Thread.sleep(LVConstants.HUB_SLEEP_TIME);
                }
            }
            LOG.info("caXchange response was " + responseMessage.getResponse().getResponseStatus().toString());
            return responseMessage.getResponse();
        } catch (Exception e) {
            throw new LVException(e);
        }
    }


    void processResponseMessage(Response response) throws LVException {
        LOG.info("caXchange response was " + response.getResponseStatus().toString());
        String status = "";
        Statuses respStatus = response.getResponseStatus();
        if (respStatus.equals(Statuses.SUCCESS)) {
            if (response.getCaXchangeError() != null) {
               LOG.info("Received a success from caxchange hub: " + response.getCaXchangeError().getErrorDescription());
            }
            if (response.getTargetResponse() != null) {
                status = getTargetResponse(response);
                LOG.info("Response from Target Service was " + status);
                if (!status.equals("Processed")) {
                    throw new LVException("Response from Target Service was " + status);
                }
            }
        } else if (respStatus.equals(Statuses.FAILURE)) {
            String message = "";
            if (response.getCaXchangeError() != null) {
                message =  response.getCaXchangeError().getErrorDescription();
            } else if (response.getTargetResponse() != null) {
                for (TargetResponseMessage msg : response.getTargetResponse()) {
                    message = msg.getTargetMessageStatus().getValue() + ":" 
                        + msg.getTargetError().getErrorDescription();
                }
            }
            throw new LVException(message);
        }
    }
    /**
     * 
     * @param response response
     * @return string
     */
    public String getTargetResponse(Response response) {
        String status = "";
        String errorCode = "";
        String errorDesc = "";
        for (TargetResponseMessage msg : response.getTargetResponse()) {
            MessageElement[] messagePay =  msg.getTargetBusinessMessage().get_any();
            for (MessageElement mEle : messagePay) {
                if (mEle.getName().equalsIgnoreCase("Acknowledgement")) {
                    Iterator it = mEle.getChildElements();
                    while (it.hasNext()) {
                        MessageElement me = (MessageElement) it.next();
                        if (me.getName().equalsIgnoreCase("Status"))  {
                            status = me.getChildren().get(0).toString();
                        } else if (me.getName().equalsIgnoreCase("Errors")) {
                            Iterator itr = me.getChildElements();
                            while (itr.hasNext()) {
                                MessageElement me2 = (MessageElement) itr.next();
                                if (me2.getName().equalsIgnoreCase("WsError")) {
                                    Iterator itr1 = me2.getChildElements();
                                    while (itr1.hasNext()) {
                                        MessageElement me3 = (MessageElement) itr1.next();
                                        if (me3.getName().equalsIgnoreCase("ErrorCode")) {
                                            errorCode = me3.getChildren().get(0).toString();
                                        } else if (me3.getName().equalsIgnoreCase("ErrorDesc"))  {
                                            errorDesc = me3.getChildren().get(0).toString();
                                        }
                                    } // while
                                } // if
                            } // while
                        } // if
                    } // while
                } // if
            } // for
        } // for
        LOG.debug("status" + status);
        LOG.debug("errorCode" + errorCode);
        LOG.debug("errorDesc" + errorDesc);
        if (status.equalsIgnoreCase("Processed")) {
            return status;
        } else {
            return errorCode + "-" + errorDesc;
        }
    }

    private webservices.II generateIi(String extn , String authName , String root) {
        webservices.II ii = new webservices.II();
        ii.setAssigningAuthorityName(authName);
        ii.setRoot(root);
        ii.setExtension(extn);
        return ii;
    }
    private webservices.II generateIi(Long extn , String authName , String root) {
        return generateIi(extn.toString() , authName , root);
    }
    
    
    private webservices.II[] generateIiArray(Long extn , String authName , String root) {
        return generateIiArray(extn.toString() , authName , root);
    }
    private webservices.II[] generateIiArray(String extn , String authName , String root) {
        webservices.II[] iis = new webservices.II[1];
        iis[0] = generateIi(extn , authName , root);
        return iis;
    }

}
