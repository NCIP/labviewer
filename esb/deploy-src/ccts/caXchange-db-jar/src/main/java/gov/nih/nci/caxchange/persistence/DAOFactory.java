package gov.nih.nci.caxchange.persistence;

import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;

import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * This class process CaxchangeMessage.xml file to send and get 
 * message form database
 * @author hmarwaha
 *
 */
public class DAOFactory {
   static ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("CaxchangeMessage.xml");
   
   /**
    * Default constructor
    */ 
   public DAOFactory() {
    }
    
   /**
    * This method gets caxchange DAO message 
    * @param
    * @return CaxchangeMessageDAO object
    * @throws
    */
    public static CaxchangeMessageDAO getCaxchangeMessageDAO() {
        return (CaxchangeMessageDAO)appContext.getBean("caxchangeMessageDAO");
    }
    
    /**
     * Main methods to exercise setters and getters  
     * defined in CaxchangeMessageDAO
     * @param args
     * @return 
     * @throws
     */
    
    public static void main(String [] args) {
        try  {
           CaxchangeMessage message =  new CaxchangeMessage();
           message.setMessageId("4");
           message.setMessage("frdjrkdsjfdksfjdkjfdkjfdkfdfd,fdgfgf,gfds,gf,,gfds,gf,fdfd");
           CaxchangeMessageDAO dao = getCaxchangeMessageDAO();
           dao.storeMessage(message);
            CaxchangeMessage mm =new CaxchangeMessage();
            mm.setMessageId("3");
           dao.deleteMessage(mm);
           CaxchangeMessage mm1 = dao.getMessage("1");
           System.out.println("mm1 "+mm1);

        } catch (Exception ex)  {
            ex.printStackTrace();
        } finally  {
        }
        
    }
}
