package gov.nih.nci.ccts.grid.service;

import java.rmi.RemoteException;
import org.globus.wsrf.config.ContainerConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import gov.nih.nci.ccts.grid.common.RegistrationConsumer;

/**
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 *
 * @created by Introduce Toolkit version 1.1
 *
 */
public class RegistrationConsumerImpl extends RegistrationConsumerImplBase {

    private static final String SPRING_CLASSPATH_EXPRESSION = "springClasspathExpression";

    private static final String DEFAULT_SPRING_CLASSPATH_EXPRESSION = "classpath:applicationContext-grid.xml";

    private static final String REGISTRATION_CONSUMER_BEAN_NAME = "registrationConsumerBeanName";

    private static final String DEFAULT_REGISTRATION_CONSUMER_BEAN_NAME = "registrationConsumer";

    private RegistrationConsumer consumer;

    public RegistrationConsumerImpl() throws RemoteException {
        super();
        String exp = ContainerConfig.getConfig().getOption(SPRING_CLASSPATH_EXPRESSION,
                DEFAULT_SPRING_CLASSPATH_EXPRESSION);
        String bean = ContainerConfig.getConfig().getOption(REGISTRATION_CONSUMER_BEAN_NAME,
                DEFAULT_REGISTRATION_CONSUMER_BEAN_NAME);
        ApplicationContext ctx = new ClassPathXmlApplicationContext(exp);
        this.consumer = (RegistrationConsumer) ctx.getBean(bean);
    }

  public gov.nih.nci.ccts.grid.Registration register(gov.nih.nci.ccts.grid.Registration registration) throws RemoteException, gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException, gov.nih.nci.ccts.grid.stubs.types.RegistrationConsumptionException {
        return this.consumer.register(registration);
    }

  public void rollback(gov.nih.nci.ccts.grid.Registration registration) throws RemoteException, gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException {
    this.consumer.rollback(registration);
  }

  public void commit(gov.nih.nci.ccts.grid.Registration registration) throws RemoteException, gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException {
    this.consumer.commit(registration);
  }

}

