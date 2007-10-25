package test.gov.nih.nci.ctom.ctlab.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import junit.framework.TestCase;

public class CTLabDAOTest extends TestCase {
	
	
	public void testSaveProtocol() throws Exception {
		Connection con = null;
		CTLabDAO dao = new CTLabDAO();
		try {
			con = dao.getConnection();
			con.setAutoCommit(false);
			Protocol prot = new Protocol();
			prot.setIdAssigningAuth("EkagIdent");
			prot.setLongTxtTitle("This is the long title of the Protocol");
			prot.setNciIdentifier("90410");
			dao.saveProtocol(con, prot);
			
			con.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try{con.rollback();} catch (Exception ex){}
			e.printStackTrace();
		} finally{
			try{con.close();} catch (Exception ex){}
		}
	}

	public void testUpdateParticipant() throws Exception {
		Connection con = null;
		CTLabDAO dao = new CTLabDAO();
		try {
			con = dao.getConnection();
			con.setAutoCommit(false);
			Protocol prot = new Protocol();
			prot.setIdAssigningAuth("EkagIdent");
			prot.setLongTxtTitle("This is the long title of the Protocol");
			prot.setNciIdentifier("90410");
			dao.saveProtocol(con, prot);
			
			con.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try{con.rollback();} catch (Exception ex){}
			e.printStackTrace();
		} finally{
			try{con.close();} catch (Exception ex){}
		}
	}

}
