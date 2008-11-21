package gov.nih.nci.ccts.grid.studyconsumer.service;


import gov.nih.nci.ccts.grid.studyconsumer.common.StudyConsumerI;
import gov.nih.nci.ccts.grid.studyconsumer.service.sampleimpl.EchoStudyServiceImpl;

import java.rmi.RemoteException;

import org.globus.wsrf.config.ContainerConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class StudyConsumerImpl extends StudyConsumerImplBase {

	
    private static final String SPRING_CLASSPATH_EXPRESSION = "springClasspathExpression";

    private static final String DEFAULT_APPCONTEXT_XML_PATH="applicationContext-studyConsumer-grid.xml";
    
    private static final String DEFAULT_SPRING_CLASSPATH_EXPRESSION = "classpath:applicationContext-studyConsumer-grid.xml";

    private static final String STUDY_CONSUMER_BEAN_NAME = "studyConsumerBeanName";

    private static final String DEFAULT_STUDY_CONSUMER_BEAN_NAME = "studyConsumer";

    private StudyConsumerI consumer;
        
        public StudyConsumerImpl() throws RemoteException {
        super();
        if(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_APPCONTEXT_XML_PATH)==null){
            System.out.println("no "+DEFAULT_APPCONTEXT_XML_PATH+" in calsspath. Loading echo study service impl...");
            this.consumer=new EchoStudyServiceImpl();
            
        }else{
            String exp = ContainerConfig.getConfig().getOption(SPRING_CLASSPATH_EXPRESSION,
                            DEFAULT_SPRING_CLASSPATH_EXPRESSION);
            String bean = ContainerConfig.getConfig().getOption(STUDY_CONSUMER_BEAN_NAME,
                            DEFAULT_STUDY_CONSUMER_BEAN_NAME);
            ApplicationContext ctx = new ClassPathXmlApplicationContext(exp);
            this.consumer = (StudyConsumerI) ctx.getBean(bean);
        }
        
    }
	
  public void createStudy(gov.nih.nci.cabig.ccts.domain.Study study) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.StudyCreationException {
      this.consumer.createStudy(study);
  }

  public void commit(gov.nih.nci.cabig.ccts.domain.Study study) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException {
      this.consumer.commit(study);
  }

  public void rollback(gov.nih.nci.cabig.ccts.domain.Study study) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException {
      this.consumer.rollback(study);
  }

}

