package gov.nih.nci.system.query.example;

import gov.nih.nci.system.query.SDKQuery;

public abstract class ExampleQuery implements SDKQuery {
	private Object example;

	public ExampleQuery(Object example) {
		this.example = example;
	}

	public Object getExample() {
		return example;
	}

	public void setExample(Object example) {
		this.example = example;
	}
}