package gov.nih.nci.cagrid.caxchange.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CaXchangeExternalProperties {
    Properties properties=null;
    static private CaXchangeExternalProperties instance=null;
    Logger logger = LogManager.getLogger(CaXchangeExternalProperties.class);
    static final String APPLICATION_DIRECTORY_NAME="caXchange";
    static final String PROPERTY_FILE_NAME="caxchange";
    
    private CaXchangeExternalProperties() {
    	findProperties();
    }
    
    protected List<File> searchDirectories() {
        String catalinaHome = System.getProperty("catalina.home");
        String jbossHome = System.getProperty("jboss.home.dir");
        List<File> dirs = new ArrayList<File>();
        if (catalinaHome != null) {
            dirs.add(new File(catalinaHome, "conf/"+APPLICATION_DIRECTORY_NAME));
        }
        if (jbossHome != null) {
        	dirs.add(new File(jbossHome,"/"+APPLICATION_DIRECTORY_NAME));
        }
        logger.info("JBOSS HOME"+jbossHome);
        return dirs;
    }
    
    private void findProperties() {
        String propfileName = PROPERTY_FILE_NAME + ".properties";
        for (File dir : searchDirectories()) {
            File possiblePath = new File(dir, propfileName);
            String abspath = possiblePath.getAbsolutePath();
            logger.debug("Looking in " + abspath);
            if (possiblePath.exists()) {
                readProperties(possiblePath);
                return;
            } 
         }
    }
    
    private void readProperties(File path) {
        try {
            logger.info("Loading caxchange properties from " + path.getAbsolutePath());
            properties = new Properties();
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(
                "Could not read caxchange configuration from " + path.getAbsolutePath()
                    + ". (" + e.getMessage() + ')', e);
        }
    }  
    
    static public  CaXchangeExternalProperties getInstance() {
    	if (instance==null) {
    		instance= new CaXchangeExternalProperties();
    	}
    	return instance;
    }
    
    public String getProperty(String key) {
    	if (properties != null) {
    		return properties.getProperty(key);
    	}else {
    		throw new RuntimeException("Properties not loaded for caXchange.");
    	}
    }

	
}
