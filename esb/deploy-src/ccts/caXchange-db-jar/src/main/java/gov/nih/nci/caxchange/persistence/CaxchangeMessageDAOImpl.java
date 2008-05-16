package gov.nih.nci.caxchange.persistence;

import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

//import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

//import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class contains the methods for storing, retriving and deleting 
 * the message from MqSql database
 * @author hmarwaha
 *
 */
public class CaxchangeMessageDAOImpl  implements CaxchangeMessageDAO{

    DataSource dataSource;
    static Logger logger = LogManager.getLogger(CaxchangeMessageDAOImpl.class);
    /**
     * Default constructor
     */
    public CaxchangeMessageDAOImpl() {
    }
    /**
     * This method stores the message and corresponding in the table 
     * @param caxchangeMessage
     * @return
     * @throws Exception
     */
    public void storeMessage(CaxchangeMessage caxchangeMessage) throws Exception {
        Connection conn=null;
        PreparedStatement ps=null;
        String insert= "insert into CAXCHANGE_MESSAGES (MESSAGE_ID, ORIGINAL_MESSAGE) VALUES (?,?)";
        try{
            conn= dataSource.getConnection();
            ps = conn.prepareStatement(insert);
            ps.setString(1, caxchangeMessage.getMessageId());
            ps.setString(2, caxchangeMessage.getMessage());
            ps.executeUpdate();
        }catch(Exception e) {
            logger.error("Error inserting into CAXCHANGE_MESSAGES.",e);
            throw e;
        }
        finally {
            if (ps != null)ps.close();
            if (conn != null )conn.close();
        }
    }
    
    /**
     * This method get message from corresponding table 
     * @param id
     * @return message
     * @throws Exception
     */
    public CaxchangeMessage getMessage(String id) throws Exception{

        Connection conn=null;
        PreparedStatement ps=null;
        CaxchangeMessage message = null;
        String select= "select message_id, ORIGINAL_MESSAGE FROM  CAXCHANGE_MESSAGES WHERE MESSAGE_ID=?";
        try{
            conn= dataSource.getConnection();
            ps = conn.prepareStatement(select);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                message = new CaxchangeMessage();
                message.setMessage(rs.getString("ORIGINAL_MESSAGE"));
                message.setMessageId(rs.getString("message_id"));
            }
            return message;
        }catch(Exception e) {
            logger.error("Error inserting into CAXCHANGE_MESSAGES.",e);
            throw e;
        }
        finally {
            if (ps != null)ps.close();
            if (conn != null )conn.close();
        }
    }

    /**
     * This method deletes message from corresponding table 
     * @param message
     * @return 
     * @throws Exception
     */
    public void deleteMessage(CaxchangeMessage message) throws Exception{
        Connection conn=null;
        PreparedStatement ps=null;
        String delete= "delete  FROM  CAXCHANGE_MESSAGES WHERE MESSAGE_ID=?";
        try{
            conn= dataSource.getConnection();
            ps = conn.prepareStatement(delete);
            ps.setString(1, message.getMessageId());
            ps.executeUpdate();
        }catch(Exception e) {
            logger.error("Error inserting into CAXCHANGE_MESSAGES.",e);
            throw e;
        }
        finally {
            if (ps != null)ps.close();
            if (conn != null )conn.close();
        }
    }

    /**
     * This method sets data source 
     * @param dataSource
     * @return 
     * @throws 
     */  /*No references*/
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    /**
     * This method get data source 
     * @param 
     * @return dataSource
     * @throws 
     */ /*No references*/
    public DataSource getDataSource() {
        return dataSource;
    }
}
