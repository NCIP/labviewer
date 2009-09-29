import gov.nih.nci.system.security.authentication.cagrid.GridAuthenticationHelper;
import org.globus.gsi.GlobusCredential;

public class TestClient
{
	
	public static void main(String args[]) throws Exception
	{
		String username = "SDKTestUser";
		String password = "Psat123!@#";
		GridAuthenticationHelper loginHelper = new GridAuthenticationHelper("@CAGRID_LOGIN_MODULE_NAME@");
		GlobusCredential proxy = loginHelper.login(username,password);
		System.out.println(proxy);
		System.out.println("Identity:"+proxy.getIdentity());
	}
	
}