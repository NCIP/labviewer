package gov.nih.nci.system.dao.orm;

import org.hibernate.cfg.Configuration;

public class HibernateConfigurationHolder {

	private Configuration configuration;

	public Configuration getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}
