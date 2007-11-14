package gov.nih.nci.caxchange.security;

import java.util.HashMap;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.spi.LoginModule;

public class DelegationServiceLoginConfiguration extends Configuration{
    public DelegationServiceLoginConfiguration() {
    }

    public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
        if (name.equals("DelegationService")) {
            AppConfigurationEntry entry = new AppConfigurationEntry(DelegationServiceLoginModule.class.getName(),AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, new HashMap());
        }
        return new AppConfigurationEntry[0];
    }

    public void refresh() {
    }
}
