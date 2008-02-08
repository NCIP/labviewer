package gov.nih.nci.caXchange;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

public class CaxchangeConstants {
    public CaxchangeConstants() {
    }

    public static final String USERNAME_XPATH="/caXchangeRequestMessage/metaData/credentials/userName";
    public static final String GROUPNAME_XPATH="/caXchangeRequestMessage/metaData/credentials/groupName";
    public static final String GRIDIDENTIFIER_XPATH="/caXchangeRequestMessage/metaData/credentials/gridIdentifier";
    public static final QName  RESPONSE_QUEUE =  new QName("http://nci.nih.gov/caXchange","ResponseQueueService");
    public static final QName  MESSAGE_TYPE_ROUTER =  new QName("http://nci.nih.gov/caXchange","MessageTypeRouter");
    

    public static final String ORIGINAL_EXCHANGE_CORRELATIONID="caxchange.original.correlationId";
    public static final String EXCHANGE_CORRELATIONID="org.apache.servicemix.correlationId";
    public static final String ERROR_MESSAGE="caxchange.errormessage";
    public static final String ERROR_CODE="caxchange.errorcode";
    
    public static final String TARGET_ID="caxchange.gridservice.target.id";    
    
    public static final Set ROLLBACK_STATUSES = new HashSet();
	public static final QName ROLLBACK_PIPELINE = new QName("http://nci.nih.gov/caXchange","rollbackPipeline");
	
    static {
        ROLLBACK_STATUSES.add("ERROR");
        ROLLBACK_STATUSES.add("FAULT");
    }
    
}
