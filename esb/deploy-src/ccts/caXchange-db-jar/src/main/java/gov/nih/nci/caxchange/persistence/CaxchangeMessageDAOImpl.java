package gov.nih.nci.caxchange.persistence;

import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class CaxchangeMessageDAOImpl  implements CaxchangeMessageDAO{

    DataSource dataSource;
    static Logger logger = LogManager.getLogger(CaxchangeMessageDAOImpl.class);

    public CaxchangeMessageDAOImpl() {
    }

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


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
