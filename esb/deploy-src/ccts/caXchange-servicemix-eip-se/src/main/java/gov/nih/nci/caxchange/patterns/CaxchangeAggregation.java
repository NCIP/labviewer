package gov.nih.nci.caxchange.patterns;

import java.util.ArrayList;
import java.util.List;

import javax.jbi.messaging.NormalizedMessage;

public class CaxchangeAggregation {
    protected int count=0;
    protected List messages;
    protected String correlationId;
    
    public CaxchangeAggregation(String correlationId) {
       this.correlationId = correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getCorrelationId() {
        return correlationId;
    }
    
    public void addMessage(NormalizedMessage message) {
        if (messages == null) {
            messages = new ArrayList();
        }
        messages.add(message);
    }
    
    public List getMessages() {
        return messages;
    }
    
    public boolean isAggregationComplete() {
        if ((messages!=null)&&(count==messages.size())) {
            return true;
        }
        return false;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
