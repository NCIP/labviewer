package gov.nih.nci.ccts.grid.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.1
 * 
 */
public interface StudyConsumerI {

  /**
   * Create a new Protocol
   *
   * @param study
   * @throws InvalidStudyException
   *	
   * @throws StudyCreationException
   *	
   */
  public void createStudy(gov.nih.nci.ccts.grid.Study study) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.StudyCreationException ;

  /**
   * Commit a create study operation
   *
   * @param study
   * @throws InvalidStudyException
   *	
   */
  public void commit(gov.nih.nci.ccts.grid.Study study) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException ;

  /**
   * Rollback a previous createStudy operation
   *
   * @param study
   * @throws InvalidStudyException
   *	
   */
  public void rollback(gov.nih.nci.ccts.grid.Study study) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException ;

}
