package gov.nih.nci.system.security.acegi.authentication;

import java.security.cert.X509Certificate;

import org.acegisecurity.AcegiMessageSource;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.providers.x509.X509AuthoritiesPopulator;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.gsi.bc.BouncyCastleUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;


/**
 * Populates the X509 authorities via an {@link org.acegisecurity.userdetails.UserDetailsService}.
 *
 */
public class CSMX509AuthoritiesPopulator implements X509AuthoritiesPopulator, InitializingBean, MessageSourceAware {
    //~ Static fields/initializers =====================================================================================

    private static final Log logger = LogFactory.getLog(CSMX509AuthoritiesPopulator.class);

    //~ Instance fields ================================================================================================

    protected MessageSourceAccessor messages = AcegiMessageSource.getAccessor();
    private UserDetailsService userDetailsService;

    //~ Methods ========================================================================================================

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(userDetailsService, "An authenticationDao must be set");
        Assert.notNull(this.messages, "A message source must be set");
    }

    public UserDetails getUserDetails(X509Certificate clientCert)
        throws AuthenticationException {

    	try
    	{
    		String userName = BouncyCastleUtil.getIdentity(clientCert);
    		return this.userDetailsService.loadUserByUsername(userName);
    	}
    	catch(UsernameNotFoundException e)
    	{
    		throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "User does not have access rights"));
    	}
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
