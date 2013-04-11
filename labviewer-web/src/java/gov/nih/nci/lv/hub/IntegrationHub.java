/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
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
        processResponseMessage(hubDto.getResponseObj() , hubDto.getTarget());
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


    void processResponseMessage(Response response , String target) throws LVException {
        LOG.info("caXchange response was " + response.getResponseStatus().toString());
        String status = "";
        Statuses respStatus = response.getResponseStatus();
        if (respStatus.equals(Statuses.SUCCESS)) {
            if ("CAERS".equals(target)) {
                if (response.getCaXchangeError() != null) {
                   LOG.info("Received a success from caxchange hub: "
                           + response.getCaXchangeError().getErrorDescription());
                }
                if (response.getTargetResponse() != null) {
                    status = getTargetResponse(response);
                    LOG.info("Response from Target Service was " + status);
                    if (!status.equals("Processed")) {
                        throw new LVException("Response from Target Service was " + status);
                    }
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
        StringBuffer errorCode = new StringBuffer("");
        StringBuffer errorDesc = new StringBuffer("");
        for (TargetResponseMessage msg : response.getTargetResponse()) {
            MessageElement[] messagePay =  msg.getTargetBusinessMessage().get_any();
            for (MessageElement mEle : messagePay) {
                if (mEle.getName().equalsIgnoreCase("Acknowledgement")) {
                    Iterator it = mEle.getChildElements();
                    while (it.hasNext()) {
                        MessageElement me = (MessageElement) it.next();
                        if (me == null) {
                            continue;
                        }
                        if (me.getName().equalsIgnoreCase("Status") && me.getChildren() != null
                                && me.getChildren().size() >= 0) {
                                status = me.getChildren().get(0).toString();
                        } else if (me.getName().equalsIgnoreCase("Errors")) {
                            Iterator itr = me.getChildElements();
                            while (itr.hasNext()) {
                                MessageElement me2 = (MessageElement) itr.next();
                                if (me2.getName().equalsIgnoreCase("WsError")) {
                                    Iterator itr1 = me2.getChildElements();
                                    while (itr1.hasNext()) {
                                        MessageElement me3 = (MessageElement) itr1.next();
                                        if (me3.getName().equalsIgnoreCase("ErrorCode") && me3.getChildren() != null
                                                && me3.getChildren().size() >= 0) {
                                            errorCode.append(me3.getChildren().get(0).toString());
                                        } else if (me3.getName().equalsIgnoreCase("ErrorDesc")
                                                && me3.getChildren() != null && me3.getChildren().size() >= 0) {
                                            errorDesc.append(me3.getChildren().get(0).toString());
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
            return (errorCode.append("-").append(errorDesc).toString());
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
