package gov.nih.nci.caxchange.persistence;

import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;
import gov.nih.nci.caxchange.jdbc.CaxchangeMetadata;

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
public class CaxchangeMetadataDAOImpl  implements CaxchangeMetadataDAO{

    DataSource dataSource;
    static Logger logger = LogManager.getLogger(CaxchangeMetadataDAOImpl.class);
    /**
     * Default constructor
     */
    public CaxchangeMetadataDAOImpl() {
    }
    /**
     * This method stores the metadata object in the table
     * @param caxchangeMetadata
     * @return
     * @throws Exception
     */
    public void storeMetadata(CaxchangeMetadata caxchangeMetadata) throws Exception {
         try{
        	CaxchangeMetadata metadata = getMetadata(caxchangeMetadata.getMessageType());
        	if (metadata == null) {
                insertMetadata(caxchangeMetadata);
        	}else {
        		updateMetadata(caxchangeMetadata);
        	}
        }catch(Exception e) {
            logger.error("Error storing into CAXCHANGE_MESSAGE_TYPES."+caxchangeMetadata,e);
            throw e;
        }

    }

    protected void insertMetadata(CaxchangeMetadata caxchangeMetadata) throws Exception {
        Connection conn=null;
        PreparedStatement ps=null;
        String insert= "insert into CAXCHANGE_MESSAGE_TYPES (MESSAGE_TYPE, PAYLOAD_NAMESPACE) VALUES (?,?)";
        try{
            conn= dataSource.getConnection();
            ps = conn.prepareStatement(insert);
            ps.setString(1, caxchangeMetadata.getMessageType());
            ps.setString(2, caxchangeMetadata.getPayloadNamespace());
            ps.executeUpdate();
        }catch(Exception e) {
            logger.error("Error inserting into CAXCHANGE_MESSAGE_TYPES.",e);
            throw e;
        }
        finally {
            if (ps != null)ps.close();
            if (conn != null )conn.close();
        }
    }

    protected void updateMetadata(CaxchangeMetadata caxchangeMetadata) throws Exception {
        Connection conn=null;
        PreparedStatement ps=null;
        String update= "UPDATE CAXCHANGE_MESSAGE_TYPES set PAYLOAD_NAMESPACE=? WHERE MESSAGE_TYPE=?";
        try{
            conn= dataSource.getConnection();
            ps = conn.prepareStatement(update);
            ps.setString(2, caxchangeMetadata.getMessageType());
            ps.setString(1, caxchangeMetadata.getPayloadNamespace());
            ps.executeUpdate();
        }catch(Exception e) {
            logger.error("Error updating  CAXCHANGE_MESSAGE_TYPES. "+caxchangeMetadata,e);
            throw e;
        }
        finally {
            if (ps != null)ps.close();
            if (conn != null )conn.close();
        }
    }
    /**
     * This method gets metadata from the message_types table
     * @param messageType
     * @return message
     * @throws Exception
     */
    public CaxchangeMetadata getMetadata(String messageType) throws Exception{

        Connection conn=null;
        PreparedStatement ps=null;
        CaxchangeMetadata metadata = null;
        String select= "select MESSAGE_TYPE, PAYLOAD_NAMESPACE FROM  CAXCHANGE_MESSAGE_TYPES WHERE MESSAGE_TYPE=?";
        try{
            conn= dataSource.getConnection();
            ps = conn.prepareStatement(select);
            ps.setString(1, messageType);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	metadata = new CaxchangeMetadata();
            	metadata.setMessageType(rs.getString("MESSAGE_TYPE"));
            	metadata.setPayloadNamespace(rs.getString("PAYLOAD_NAMESPACE"));
            }
            return metadata;
        }catch(Exception e) {
            logger.error("Error querying CAXCHANGE_MESSAGES.",e);
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
    public void deleteMetadata(CaxchangeMetadata metadata) throws Exception{
        Connection conn=null;
        PreparedStatement ps=null;
        String delete= "delete  FROM  CAXCHANGE_MESSAGE_TYPES WHERE MESSAGE_TYPE=?";
        try{
            conn= dataSource.getConnection();
            ps = conn.prepareStatement(delete);
            ps.setString(1, metadata.getMessageType());
            ps.executeUpdate();
        }catch(Exception e) {
            logger.error("Error deleting from CAXCHANGE_MESSAGE_TYPES."+metadata,e);
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
