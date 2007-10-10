package gov.nih.nci.cabig.ctms.web.sso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.globus.gsi.GlobusCredential;

public class Utils {
    
    public static String getGridIdentity(String proxy){
        String identity = null;
        try{
            identity = new GlobusCredential(new ByteArrayInputStream(proxy.getBytes())).getIdentity();
        }catch(Exception ex){
            throw new RuntimeException("Error reading proxy: " + ex.getMessage(), ex);
        }
        return identity;
    }

    public static String toString(GlobusCredential proxy) {
        String proxyStr = null;
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            proxy.save(buf);
            proxyStr = buf.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error writing proxy to String: "
                            + ex.getMessage(), ex);
        }
        return proxyStr;
    }

    
    public static Object findAttribute(HttpServletRequest request, String attName){
        Object attribute = null;
        
        attribute = request.getAttribute(attName);
        if(attribute == null){
            attribute = request.getSession().getAttribute(attName);
        }
        
        return attribute;
    }
    
    public static String findParameter(HttpServletRequest request, String paramName){
        String param = null;
        
        param = request.getParameter(paramName);
        if(param == null){
            try{
                param = (String)request.getSession().getAttribute(paramName);
            }catch(ClassCastException ex){
                //Do nothing
            }
        }
        
        return param;
    }
}
