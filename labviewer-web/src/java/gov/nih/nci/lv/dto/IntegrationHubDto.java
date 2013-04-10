/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.dto;

import gov.nih.nci.caxchange.Credentials;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.Response;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.lv.util.LVException;

import org.apache.axis.message.MessageElement;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.II;

/**
 *
 * @author Naveen Amiruddin
 *
 */
public class IntegrationHubDto {

    private String studyProtocolExtn;
    private String messageXml;
    private String qName;
    private String qRequest;
    private String externalIdentifier;
    private String userName;
    private String password;
    private String credentialEpr;
    private GlobusCredential globusCredential;
    private String serviceType;
    private Object requestObj;
    private String hubUrl;
    private Message requestMessage;
    private Response responseObj;
    private String target; // either CAERS or C3D
    private String serviceName; // currently CTODS
    private String operationName;
    private II coppaIi;
    private Id coppaEntityId;
    private MessageElement[] meArray = null;


    /**
     *
     * @return messageXml
     */
    public String getMessageXml() {
        return messageXml;
    }
    /**
     *
     * @param messageXml messageXml
     */
    public void setMessageXml(String messageXml) {
        this.messageXml = messageXml;
    }
    /**
     *
     * @return qName
     */
    public String getQName() {
        return qName;
    }
    /**
     *
     * @param qName qName
     */
    public void setQName(String qName) {
        this.qName = qName;
    }
    /**
     *
     * @return qRequest
     */
    public String getQRequest() {
        return qRequest;
    }
    /**
     *
     * @param qRequest qRequest
     */
    public void setQRequest(String qRequest) {
        this.qRequest = qRequest;
    }
    /**
     *
     * @return externalIdentifier
     */
    public String getExternalIdentifier() {
        return externalIdentifier;
    }
    /**
     *
     * @param externalIdentifier externalIdentifier
     */
    public void setExternalIdentifier(String externalIdentifier) {
        this.externalIdentifier = externalIdentifier;
    }
    /**
     *
     * @return userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     *
     * @param userName userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }
    /**
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     *
     * @return credentialEpr
     */
    public String getCredentialEpr() {
        return credentialEpr;
    }
    /**
     *
     * @param credentialEpr credentialEpr
     */
    public void setCredentialEpr(String credentialEpr) {
        this.credentialEpr = credentialEpr;
    }

    /**
     *
     * @return globusCredential
     */
    public GlobusCredential getGlobusCredential() {
        return globusCredential;
    }
    /**
     *
     * @param globusCredential globusCredential
     */
    public void setGlobusCredential(GlobusCredential globusCredential) {
        this.globusCredential = globusCredential;
    }
    /**
     *
     * @return Credentials
     */
    public Credentials getCredential() {
        Credentials creds = new Credentials();
        creds.setUserName(userName);
        creds.setPassword(password);
        creds.setDelegatedCredentialReference(credentialEpr);
        return creds;
    }
    /**
     *
     * @return serviceType
     */
    public String getServiceType() {
        return serviceType;
    }
    /**
     *
     * @param serviceType serviceType
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    /**
     *
     * @return requestObj
     */
    public Object getRequestObj() {
        return requestObj;
    }
    /**
     *
     * @param requestObj requestObj
     */
    public void setRequestObj(Object requestObj) {
        this.requestObj = requestObj;
    }
    /**
     *
     * @return hubUrl
     * @throws LVException
     */
    public String getHubUrl() throws LVException {
        return hubUrl;
    }
    /**
     *
     * @param hubUrl hubUrl
     */
    public void setHubUrl(String hubUrl) {
        this.hubUrl = hubUrl;
    }
    /**
     *
     * @return requestMessage
     */
    public Message getRequestMessage() {
        return requestMessage;
    }
    /**
     *
     * @param requestMessage requestMessage
     */
    public void setRequestMessage(Message requestMessage) {
        this.requestMessage = requestMessage;
    }
    /**
     *
     * @return responseObj
     */
    public Response getResponseObj() {
        return responseObj;
    }
    /**
     *
     * @param responseObj responseObj
     */
    public void setResponseObj(Response responseObj) {
        this.responseObj = responseObj;
    }
    /**
     *
     * @return target
     */
    public String getTarget() {
        return target;
    }
    /**
     *
     * @param target target
     */
    public void setTarget(String target) {
        this.target = target;
    }
    /**
     *
     * @return studyProtocolExtn
     */
    public String getStudyProtocolExtn() {
        return studyProtocolExtn;
    }
    /**
     *
     * @param studyProtocolExtn studyProtocolExtn
     */
    public void setStudyProtocolExtn(String studyProtocolExtn) {
        this.studyProtocolExtn = studyProtocolExtn;
    }
    /**
     *
     * @return serviceName
     */
    public String getServiceName() {
        return serviceName;
    }
    /**
     *
     * @param serviceName serviceName
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    /**
     *
     * @return operationName
     */
    public String getOperationName() {
        return operationName;
    }
    /**
     *
     * @param operationName operationName
     */
    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
    /**
     *
     * @return identifier
     */
    public II getCoppaIi() {
        return coppaIi;
    }
    /**
     *
     * @param coppaIi identifier
     */
    public void setCoppaIi(II coppaIi) {
        this.coppaIi = coppaIi;
    }
    /**
     *
     * @return meArray
     */
    public MessageElement[] getMeArray() {
        return meArray;
    }
    /**
     *
     * @param meArray meArray
     */
    public void setMeArray(MessageElement[] meArray) {
        this.meArray = meArray;
    }
    /**
     *
     * @return Id
     */
    public Id getCoppaEntityId() {
        return coppaEntityId;
    }
    /**
     *
     * @param coppaEntityId Id
     */
    public void setCoppaEntityId(Id coppaEntityId) {
        this.coppaEntityId = coppaEntityId;
    }










}
