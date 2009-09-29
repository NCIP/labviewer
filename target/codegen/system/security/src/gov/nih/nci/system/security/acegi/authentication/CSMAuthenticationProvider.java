

package gov.nih.nci.system.security.acegi.authentication;

import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.exceptions.CSInputException;
import gov.nih.nci.security.exceptions.CSInsufficientAttributesException;
import gov.nih.nci.security.exceptions.CSLoginException;

import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationServiceException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.providers.AuthenticationProvider;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.AbstractUserDetailsAuthenticationProvider;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;


/**
 * An {@link AuthenticationProvider} implementation that retrieves user details from an {@link UserDetailsService}.
 *
 */
public class CSMAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {   
    
    private CSMUserDetailsService userDetailsService;
    private boolean includeDetailsObject = true;

    //~ Methods ========================================================================================================

    protected void additionalAuthenticationChecks(UserDetails userDetails,
        UsernamePasswordAuthenticationToken authentication)
        throws AuthenticationException {

        
    	// 	Use CSM authenticationManager to authenticate User.
        try {
			this.userDetailsService.authenticationManagerInstance().authenticate(userDetails.getUsername(), authentication.getCredentials().toString());
		} catch (CSLoginException e) {
			 throw new BadCredentialsException(messages.getMessage(
	                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), includeDetailsObject ? userDetails : null);
		} catch (CSInputException e) {
			 throw new BadCredentialsException(messages.getMessage(
	                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), includeDetailsObject ? userDetails : null);
		} catch (CSConfigurationException e) {
			 throw new BadCredentialsException(messages.getMessage(
	                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), includeDetailsObject ? userDetails : null);
		} catch (CSInsufficientAttributesException e) {
			 throw new BadCredentialsException(messages.getMessage(
	                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), includeDetailsObject ? userDetails : null);
		} catch (CSException e) {
			 throw new BadCredentialsException(messages.getMessage(
	                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), includeDetailsObject ? userDetails : null);
		}
        
    }

    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
        Assert.notNull(this.userDetailsService.authorizationManagerInstance(),"Unable to initialize Authorization Manager");
    }
   
    public CSMUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
        throws AuthenticationException {
        UserDetails loadedUser;

        try {
            loadedUser = this.getUserDetailsService().loadUserByUsername(username);
        } catch (DataAccessException repositoryProblem) {
            throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new AuthenticationServiceException(
                "UserDetailsService returned null, which is an interface contract violation");
        }

        return loadedUser;
    }


    public void setUserDetailsService(CSMUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

	public boolean isIncludeDetailsObject() {
		return includeDetailsObject;
	}

	public void setIncludeDetailsObject(boolean includeDetailsObject) {
		this.includeDetailsObject = includeDetailsObject;
	}
}
