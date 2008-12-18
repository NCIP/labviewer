package gov.nih.nci.caxchange.servicemix.bean.cache;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.apache.jcs.engine.behavior.IElementAttributes;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.globus.gsi.GlobusCredential;
import org.globus.gsi.GlobusCredentialException;

public class UserProxyCacheImpl implements UserProxyCache{
	protected long maxTimeToLive=600;
	protected JCS delegatedProxyCache;
	protected Logger logger = LogManager.getLogger(UserProxyCacheImpl.class);
	
	public void init() throws UserProxyCacheException{
	  try {
		delegatedProxyCache = JCS.getInstance("delegatedProxyCache");
   		IElementAttributes elementAttributes = delegatedProxyCache.getDefaultElementAttributes();
		elementAttributes.setMaxLifeSeconds(maxTimeToLive);
		delegatedProxyCache.setDefaultElementAttributes(elementAttributes);
	  }
	  catch(CacheException ce) {
		  logger.error("JCS CacheException initializing cache.",ce);
		  throw new UserProxyCacheException("JCS CacheException initializing cache.",ce);
	  }
 	}
	

	public void put(Object key, GlobusCredential value) {
         try {
        	 delegatedProxyCache.put(key, value);
         }catch(Exception e){
        	 logger.error("Error caching key: "+key);
         }
	}
	
	public void remove(Object key) {
		try {
			if (key==null) return;
			delegatedProxyCache.remove(key);
		}catch(Exception e){
			logger.error("Error removing key:"+key);
		}
	}


	public long getMaxTimeToLive() {
		return maxTimeToLive;
	}


	public GlobusCredential get(Object key) {
		try {
			if (key == null) {
				return null;
			}
			Object value = delegatedProxyCache.get(key);
			GlobusCredential credential = (GlobusCredential)value;
			try {
				if (credential != null) {
				    credential.verify();
				}
			}catch(GlobusCredentialException gce) {
				credential = null;
				delegatedProxyCache.remove(key);
			}
			return (GlobusCredential)value;
		}catch(Exception e){
			logger.error("Error getting object.", e);
			return null;
		}
	}


	public void setMaxTimeToLive(long ttl) {
       this.maxTimeToLive = ttl;		
	}
	
	
}
