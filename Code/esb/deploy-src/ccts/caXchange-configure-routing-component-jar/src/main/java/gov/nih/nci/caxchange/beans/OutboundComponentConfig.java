package gov.nih.nci.caxchange.beans;

public class OutboundComponentConfig {
	
	private String serviceName;
	private String endpointName;
	private String targetId;
	private String targetOperation;
	private String serviceUrl;
	private String gridClientClassName;
	private String requestPayloadClassName;
	private String operationName;
	private String gridClientLocation;
	
	
	public String getGridClientLocation() {
		return gridClientLocation;
	}
	public void setGridClientLocation(String gridClientLocation) {
		this.gridClientLocation = gridClientLocation;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getEndpointName() {
		return endpointName;
	}
	public void setEndpointName(String endpointName) {
		this.endpointName = endpointName;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getTargetOperation() {
		return targetOperation;
	}
	public void setTargetOperation(String targetOperation) {
		this.targetOperation = targetOperation;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public String getGridClientClassName() {
		return gridClientClassName;
	}
	public void setGridClientClassName(String gridClientClassName) {
		this.gridClientClassName = gridClientClassName;
	}
	public String getRequestPayloadClassName() {
		return requestPayloadClassName;
	}
	public void setRequestPayloadClassName(String requestPayloadClassName) {
		this.requestPayloadClassName = requestPayloadClassName;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

}
