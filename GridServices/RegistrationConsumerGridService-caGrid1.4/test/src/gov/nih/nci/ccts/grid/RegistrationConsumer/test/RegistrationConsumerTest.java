package gov.nih.nci.ccts.grid.RegistrationConsumer.test;

import gov.nih.nci.cabig.ccts.domain.Registration;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.ccts.grid.client.RegistrationConsumerClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import junit.framework.TestCase;

public class RegistrationConsumerTest extends TestCase{

    private RegistrationConsumerClient registrationConsumerClient;
    private String clientConfigPath="/Users/kruttikagarwal/Documents/workspace/ccts-grid-services/RegistrationConsumerGridService/src/gov/nih/nci/ccts/grid/client/client-config.wsdd";
    private String url = "https://cbvapp-d1017.nci.nih.gov:28445/wsrf-psc/services/cagrid/RegistrationConsumer";
    
    public void testRegistrationConsumer() throws Exception{
        registrationConsumerClient=new RegistrationConsumerClient(url);
        String xmlPath = "/Users/kruttikagarwal/Documents/workspace/ccts-grid-services/RegistrationConsumerGridService/test/resources/sample.xml";
        String xml=readFileAsString(xmlPath);
        System.out.println(xml);
        Registration registration=(Registration)Utils.deserializeObject(new FileReader(xmlPath), Registration.class, new FileInputStream(new File(clientConfigPath)));
        registrationConsumerClient.register(registration);
    }

    private static String readFileAsString(String filePath) throws java.io.IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
}
