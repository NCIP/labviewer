package gov.nih.nci.system.dao.orm;

import gov.nih.nci.system.security.helper.SecurityInitializationHelper;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public class SessionFactoryBean extends LocalSessionFactoryBean {
	
	private HibernateConfigurationHolder configHolder;
	private SecurityInitializationHelper securityHelper;

	@Override
	protected SessionFactory newSessionFactory(Configuration config) throws HibernateException {
		configHolder.setConfiguration(config);
		securityHelper.addFilters(config);
		return super.newSessionFactory(config);
	}

	public HibernateConfigurationHolder getConfigHolder() {
		return configHolder;
	}
	public void setConfigHolder(HibernateConfigurationHolder configHolder) {
		this.configHolder = configHolder;
	}

	public SecurityInitializationHelper getSecurityHelper() {
		return securityHelper;
	}

	public void setSecurityHelper(SecurityInitializationHelper securityHelper) {
		this.securityHelper = securityHelper;
	}
}
