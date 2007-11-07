package gov.nih.nci.cagrid.labviewer.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.1
 * 
 */
public interface LabLoaderI {

  /**
   * loadLab method is called to persist an HL7v3 message to the database
   *
   * @param string
   */
  public void loadLab(java.lang.String string) throws RemoteException ;

  /**
   * rollback is called is there is a reason to not persist the last lab loaded
   *
   * @param string
   */
  public void rollback(java.lang.String string) throws RemoteException ;

}

