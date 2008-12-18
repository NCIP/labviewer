package gov.nih.nci.caxchange.servicemix.bean.cache;

import org.globus.gsi.GlobusCredential;

public interface UserProxyCache {

    public void init() throws UserProxyCacheException;
    /**
     * Maximum time to live in the cache in seconds.
     * @param ttl
     */
	public void setMaxTimeToLive(long ttl);
	
	public long getMaxTimeToLive();
	
	public void put(Object key, GlobusCredential value);
	
	public void remove(Object key);
	
	public GlobusCredential get(Object key);
	
}
