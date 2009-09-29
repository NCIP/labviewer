package gov.nih.nci.system.globus.gsi;


import java.net.HttpURLConnection;
import java.net.URL;

import org.globus.gsi.GSIConstants;
import org.globus.gsi.gssapi.auth.Authorization;
import org.globus.gsi.gssapi.auth.GSSAuthorization;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSName;

public abstract class GSIURLConnection extends HttpURLConnection 
{

    public static final String GSS_MODE_PROPERTY = "gssMode";

    protected GSSCredential credentials;
    protected Authorization authorization;
    protected int delegationType;
    protected Integer gssMode;

    /**
     * Subclasses must overwrite.
     */
    protected GSIURLConnection(URL url) 
    {
    	super(url);
    	this.delegationType = GSIConstants.DELEGATION_NONE;
    	this.authorization = null; // no authorization?
    }

    public abstract void disconnect();

    protected GSSName getExpectedName() throws GSSException 
    {
        if (this.authorization instanceof GSSAuthorization) 
        {
            GSSAuthorization auth = (GSSAuthorization)this.authorization;
            return auth.getExpectedName(this.credentials,this.url.getHost());
        } 
        else 
        {
            return null;
        }
    }

    public void setRequestProperty(String key, String value) {
        if (key.equals(GSS_MODE_PROPERTY)) 
        {
            if (value.equals("ssl")) 
            {
                setGSSMode(GSIConstants.MODE_SSL);
            } 
            else if (value.equals("gsi")) 
            {
                setGSSMode(GSIConstants.MODE_GSI);
            }
            else 
            {
                setGSSMode(null);
            }
        } 
        else 
        {
            super.setRequestProperty(key, value);
        }
    }

    public void setGSSMode(Integer mode) 
    {
        this.gssMode = mode;
    }
    
    public Integer getGSSMode() 
    {
        return this.gssMode;
    }

    public void setCredentials(GSSCredential credentials) 
    {
        this.credentials = credentials;
    }

    public GSSCredential getCredentials() 
    {
        return credentials;
    }

    public void setAuthorization(Authorization auth) 
    {
        authorization = auth;
    }

    public Authorization getAuthorization() 
    {
        return authorization;
    }
    
    public void setDelegationType(int delegationType) 
    {
    	this.delegationType = delegationType;
    }

    public int getDelegationType() 
    {
    	return delegationType;
    }
}
