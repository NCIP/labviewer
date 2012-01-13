package gov.nih.nci.ccts.grid.studyconsumer.service;

import gov.nih.nci.ccts.grid.studyconsumer.common.StudyConsumerI;
import gov.nih.nci.ccts.grid.studyconsumer.service.sampleimpl.EchoStudyServiceImpl;

import java.rmi.RemoteException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.xml.rpc.handler.soap.SOAPMessageContext;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.globus.wsrf.config.ContainerConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * TODO:I am the service side implementation class. IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class StudyConsumerImpl extends StudyConsumerImplBase {

	private static final String SPRING_CLASSPATH_EXPRESSION = "springClasspathExpression";

	private static final String DEFAULT_APPCONTEXT_XML_PATH = "applicationContext-studyConsumer-grid.xml";

	private static final String DEFAULT_SPRING_CLASSPATH_EXPRESSION = "classpath:applicationContext-studyConsumer-grid.xml";

	private static final String STUDY_CONSUMER_BEAN_NAME = "studyConsumerBeanName";

	private static final String DEFAULT_STUDY_CONSUMER_BEAN_NAME = "studyConsumer";

	private StudyConsumerI consumer;

	public StudyConsumerImpl() throws RemoteException {
		super();

	}

  public void createStudy(gov.nih.nci.cabig.ccts.domain.Study study) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.StudyCreationException {
		initialize();
		this.consumer.createStudy(study);
	}

  public void commit(gov.nih.nci.cabig.ccts.domain.Study study) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException {
		initialize();
		this.consumer.commit(study);
	}

  public void rollback(gov.nih.nci.cabig.ccts.domain.Study study) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException {
		initialize();
		this.consumer.rollback(study);
	}

	/**
	 * Will try to locate a pre-existent {@link ApplicationContext}; if failed,
	 * will create it explicitly.
	 * 
	 * @see http://jira.semanticbits.com/browse/CAAERS-4291
	 */
	private synchronized void initialize() {
		if (this.consumer == null) {
			ApplicationContext ctx = null;
			String exp = ContainerConfig.getConfig().getOption(
					SPRING_CLASSPATH_EXPRESSION,
					DEFAULT_SPRING_CLASSPATH_EXPRESSION);
			String bean = ContainerConfig.getConfig().getOption(
					STUDY_CONSUMER_BEAN_NAME, DEFAULT_STUDY_CONSUMER_BEAN_NAME);

			// see http://jira.semanticbits.com/browse/CAAERS-4291
			// let's see if ApplicationContext is already available as a
			// WebApplicationContext
			// if so, use it; otherwise, fall back to standard approach for
			// backward compatibility.
			SOAPMessageContext messageContext = MessageContext
					.getCurrentContext();
			if (messageContext != null) {
				HttpServlet srv = (HttpServlet) messageContext
						.getProperty(HTTPConstants.MC_HTTP_SERVLET);
				if (srv != null) {
					ServletContext servletContext = srv.getServletContext();
					ctx = WebApplicationContextUtils
							.getWebApplicationContext(servletContext);
				}
			}

			if (ctx == null) {
				System.out
						.println("StudyConsumerImpl: unable to find pre-existing spring context in servlet context; falling back to direct context creation.");
				if (this.getClass().getClassLoader().getResourceAsStream(
						DEFAULT_APPCONTEXT_XML_PATH) == null) {
					System.out
							.println("no "
									+ DEFAULT_APPCONTEXT_XML_PATH
									+ " in calsspath. Loading echo study service impl...");
					this.consumer = new EchoStudyServiceImpl();

				} else {
					System.out.println("Creating spring context explicitly...");
					ctx = new ClassPathXmlApplicationContext(exp);
				}
			}

			if (ctx != null)
				this.consumer = (StudyConsumerI) ctx.getBean(bean);

		}
	}

}
