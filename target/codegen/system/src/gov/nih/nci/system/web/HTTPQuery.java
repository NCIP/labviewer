package gov.nih.nci.system.web;

import gov.nih.nci.system.util.SystemConstant;
import gov.nih.nci.system.web.util.HTTPUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.JDOMResult;
import org.jdom.transform.JDOMSource;
import org.mmbase.util.Encode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * The HTTPQuery servlet interpretes a query request and makes appropriate calls to the Application Service interface.  
 * The results are sent back to the user as an XML or HTML document based on the type of request made.  
 * XQuery like syntax is used to generate the query.
 * 
 * Syntax:
 * http://server:port/servlet/queryType?query=targetClassName&criteriaClassName[@attribute=value][association[@attribute=value]]
 * Please refer to the cacore documentation for more information on generating a query request.
 */
public class HTTPQuery extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(HTTPQuery.class.getName());

	private String cacoreStyleSheet;

	private int pageSize = 1000; //default

	ServletContext context;

	/**
	 * Initialize the servlet
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		context = config.getServletContext();
		
		WebApplicationContext ctx =  WebApplicationContextUtils.getWebApplicationContext(context);
		Properties systemProperties = (Properties) ctx.getBean("WebSystemProperties");

		cacoreStyleSheet = systemProperties.getProperty("resultOutputFormatter");
		log.debug("cacoreStylesheet: " + cacoreStyleSheet);

		try {
			String pageCount = systemProperties.getProperty("rowCounter");
			log.debug("rowCounter: " + pageCount);
			if (pageCount != null) {
				pageSize = Integer.parseInt(pageCount);
			}
		} catch (Exception ex) {
			log.error("Exception: ", ex);
		}
	}

	/**
	 * Handls Post requests
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Unload servlet
	 */
	public void destroy() {
		super.destroy();
	}

	/**
	 * Handles Get requests
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletOutputStream out = response.getOutputStream();
		out = response.getOutputStream();

		Object[] resultSet = null;
		int pageNumber = 1;

		try {
			String query = null;

			if (URLDecoder.decode(request.getQueryString(), "ISO-8859-1") != null) {
				query = URLDecoder.decode(request.getQueryString(),
						"ISO-8859-1");
			} else {
				throw new Exception("Query not defined" + getQuerySyntax());
			}
			if (query.indexOf("&username") > 0)
				query = query.substring(0, query.indexOf("&username"));

			HTTPUtils httpUtils = new HTTPUtils(context);

			String queryType = httpUtils.getQueryType(request.getRequestURL()
					.toString());
			validateQuery(query);
			httpUtils.setQueryArguments(query);

			if (httpUtils.getPageNumber() != null) {
				pageNumber = Integer.parseInt(httpUtils.getPageNumber());
			}
			httpUtils.setServletName(request.getRequestURL().toString());

			if (httpUtils.getPageSize() != null) {
				pageSize = Integer.parseInt(httpUtils.getPageSize());
			} else {
				httpUtils.setPageSize(pageSize);
			}

			try {
				resultSet = httpUtils.getResultSet();
			} catch (Exception ex) {
				log.error("Get ResultSet Exception: " + ex.getMessage());
				throw ex;
			}
			try {

				XMLOutputter xout = new XMLOutputter();
				org.jdom.Document domDoc = httpUtils.getXMLDocument(resultSet,
						pageNumber);

				if (queryType.endsWith("XML")) {
					response.setContentType("text/xml");
					xout.output(domDoc, out);
				} else {
					response.setContentType("text/html");
					Document htmlDoc = null;
					if (httpUtils.getTargetPackageName() != null) {

						if (cacoreStyleSheet != null) {
							htmlDoc = getHTMLDocument(domDoc, cacoreStyleSheet);
						}

						if (htmlDoc != null) {
							xout = new XMLOutputter();
							xout.output(htmlDoc, out);
						} else {
							httpUtils.printResults(response);
						}
					}
				}

			} catch (Exception ex) {
				log.error("Print Results Exception: " + ex.getMessage());
				throw ex;
			}
		} catch (Exception ex) {
			log.error("Exception: ", ex);
			String msg = "<font size=6><b>caCORE HTTP Servlet Error:<hr></font>";

			out.println(msg);

			out.println("<pre class=\"autoOverflow\">");
			out.println("<br><br><font size=4 color=red>");

			msg = ex.getMessage();
			Throwable tempEx = ex.getCause();
			while (tempEx != null) {
				msg += "<br><br>Caused by: " + tempEx.getMessage();
				tempEx = tempEx.getCause();
			}

			out.println(msg);

			out.println("</b></font><br><br>");
			out.println("</pre>");

		}

	}

	/**
	 * Generates an HTML Document for a given XML document with the given stylesheet specification
	 * @param doc Specifies the XML document
	 * @param styleSheet Specifies the stylesheet
	 * @return
	 * @throws Exception
	 */
	public Document getHTMLDocument(Document doc, String styleSheet)
			throws Exception {
		Document htmlDoc = null;

		try {
			InputStream styleIn = Thread.currentThread()
					.getContextClassLoader().getResourceAsStream(styleSheet);
			if (styleIn != null) {
				htmlDoc = XSLTTransformer(doc, styleIn);
			}

		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new ServletException(ex.getMessage());
		}
		return htmlDoc;
	}

	/**
	 * Generates an XML or HTML document based on a given stylesheet
	 * @param xmlDoc Specifies the xml document
	 * @param styleIn specifies the stylesheet
	 * @return
	 * @throws Exception 
	 */

	public Document XSLTTransformer(Document xmlDoc, InputStream styleIn)
			throws Exception {
		JDOMSource source = new JDOMSource(xmlDoc);
		JDOMResult result = new JDOMResult();
		try {
			if (styleIn != null) {
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Templates stylesheet = tFactory.newTemplates(new StreamSource(
						styleIn));
				Transformer processor = stylesheet.newTransformer();
				processor.transform(source, result);
			}

		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception("XSLTTransformer Exception: " + ex.getMessage());
		}
		return result.getDocument();
	}

	/**
	 * This method returns true if the query syntax is valid
	 * @param query Specifies the http query 
	 * @return return 
	 */
	private boolean validateQuery(String query) throws Exception {
		boolean valid = true;
		try {
			if (query.indexOf("query") < 0) {
				valid = false;
			} else if (query.endsWith("query=")) {
				valid = false;
			} else if (query.endsWith("query")) {
				valid = false;
			} else if (query.endsWith("&") || query.endsWith("[")) {
				valid = false;
			}

		} catch (Exception ex) {
			valid = false;
		}
		if (valid) {
			int startCounter = 0;
			int endCounter = 0;
			for (int i = 0; i < query.length(); i++) {
				if (query.charAt(i) == SystemConstant.LEFT_BRACKET) {
					startCounter++;
				} else if (query.charAt(i) == SystemConstant.RIGHT_BRACKET) {
					endCounter++;
				}
			}
			if (startCounter != endCounter) {
				throw new Exception(
						"Invalid format: '[' parenthesis does not match number of ']' parenthesis");
			}
		} else {
			Encode encoder = new Encode("ESCAPE_XML");
			throw new Exception("Invalid Syntax: " + encoder.encode(query) + getQuerySyntax());
		}
		return valid;
	}

	/**
	 * Returns the query syntax
	 * @return
	 */
	private String getQuerySyntax() {
		String syntax = "<br><br><font color=black size=4><B>Syntax: </B><br>"
				+ "<font color=purple>query=</font>TargetClassName"
				+ "<font color=purple>&</font>CriteriaClassName"
				+ "<font color=purple>[@</font>attribute"
				+ "<font color=purple>=</font>value"
				+ "<font color=purple>][</font>association"
				+ "<font color=purple>[@</font>attribute"
				+ "<font color=purple>=</font>value"
				+ "<font color=purple>]]<font color=purple></font>";
		return syntax;

	}

}
