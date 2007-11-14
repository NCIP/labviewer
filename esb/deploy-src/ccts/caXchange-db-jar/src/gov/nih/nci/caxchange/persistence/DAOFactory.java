package gov.nih.nci.caxchange.persistence;

import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DAOFactory {
   static ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("CaxchangeMessage.xml");
    public DAOFactory() {
    }
    
    public static CaxchangeMessageDAO getCaxchangeMessageDAO() {
        return (CaxchangeMessageDAO)appContext.getBean("caxchangeMessageDAO");
    }
    
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
