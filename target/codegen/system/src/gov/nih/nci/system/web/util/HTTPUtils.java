package gov.nih.nci.system.web.util;

import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.proxy.ListProxy;
import gov.nih.nci.system.util.ClassCache;
import gov.nih.nci.system.util.SystemConstant;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.Namespace;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * HTTPUtils presents various methods to generate search criteria from xquery like syntax.
 * This class also provides functionality to generate XML result.
 *
 * @author Shaziya Muhsin, Dan Dumitru
 * @version 1.1
 */

public class HTTPUtils implements Serializable{

	private static final long serialVersionUID = 1234567890L;

	private static Logger log= Logger.getLogger(HTTPUtils.class.getName());

	private ApplicationService applicationService;
	private ClassCache classCache;

	private String query;
	private String startIndex = "0";
	private String resultCounter = "1000";
	private String pageNumber;
	private String pageSize;
	private String criteria;
	private String targetClassName;
	private String servletName;
	private String targetPackageName;
	private String roleName;	
	private List results = new ArrayList();
	private Namespace namespace = Namespace.getNamespace("xlink", SystemConstant.XLINK_URL);

	public HTTPUtils(ServletContext context) {
		WebApplicationContext ctx =  WebApplicationContextUtils.getWebApplicationContext(context);
		this.classCache = (ClassCache)ctx.getBean("ClassCache");
		this.applicationService = (ApplicationService)ctx.getBean("ApplicationServiceImpl");
		
		Properties systemProperties = (Properties) ctx.getBean("WebSystemProperties");
		
		try {
			String rowCounter = systemProperties.getProperty("rowCounter");
			log.debug("rowCounter: " + rowCounter);
			if (rowCounter != null) {
				this.resultCounter = rowCounter;
			}
		} catch (Exception ex) {
			log.error("Exception: ", ex);
		}
	}	

	/**
	 * Sets the http Servlet name
	 * @param name
	 */
	public void setServletName(String name){
		servletName = name;
	}
	/**
	 * Returns http servlet name
	 * @return
	 */
	public String getServletName(){
		return servletName;
	}
	/**
	 * Returns startIndex value
	 * @return
	 */
	public String getStartIndex(){
		return startIndex;
	}
	/**
	 * Sets the startIndex value
	 * @param index
	 */
	public void setStartIndex(String index){
		startIndex = index;
	}
	/**
	 * Returns a list of result objects
	 * @return
	 */
	public List getResults(){
		return results;
	}
	/**
	 * Sets the results value
	 * @param resultList
	 */
	public void setResults(List resultList){
		results = resultList;
	}

	/**
	 * Sets argument values based on a given queryText
	 * @param queryText - http query
	 * @throws Exception
	 */
	public void setQueryArguments(String queryText) throws Exception {
		this.query = queryText;
		try{
			if (query != null && !"".equals(query.trim())) {
				if(query.indexOf(SystemConstant.AMPERSAND)<0 && query.indexOf(SystemConstant.EQUAL)>0){
					if(query.indexOf(SystemConstant.LEFT_BRACKET)>0){
						String crit = query.substring(6);
						query = query.substring(0,query.indexOf(SystemConstant.LEFT_BRACKET))+ SystemConstant.AMPERSAND + crit;
					}
					else{
						query += SystemConstant.AMPERSAND + query.substring(query.indexOf(SystemConstant.EQUAL)+1);
					}

				}
				StringTokenizer st = new StringTokenizer(query, SystemConstant.AMPERSAND_STR);
				while (st.hasMoreTokens()) {
					String param = st.nextToken();

					if(param.startsWith("query")){
						targetClassName = param.substring("query=".length());
					}
					else if(param.toLowerCase().startsWith("startindex")){
						startIndex = param.substring("startIndex=".length());
					}
					else if(param.toLowerCase().startsWith("resultcounter")){
						resultCounter = param.substring("resultCounter=".length());
					}
					else if(param.toLowerCase().startsWith("rolename=")){
						roleName = param.substring("roleName=".length());
					}					
					else if(param.toLowerCase().startsWith("pagenumber=")){
						pageNumber = param.substring("pageNumber=".length());
					}
					else if(param.toLowerCase().startsWith("pagesize=")){
						pageSize = param.substring("pageSize=".length());
					}
					else if(param.toLowerCase().startsWith("page=")){
						pageSize = param.substring("page=".length());
					}

					else{
						if(criteria == null){
							criteria = param;
						}

					}
					String target = targetClassName;

					if(target.indexOf(SystemConstant.COMMA)>0){
						target = target.substring(0, target.indexOf(SystemConstant.COMMA));
					}
					classCache.isClassNameValid(target);
					if(target.indexOf(SystemConstant.DOT)>0){
						if(classCache.isPackageNameValid(target.substring(0,target.lastIndexOf(SystemConstant.DOT)))){
							targetPackageName = target.substring(0,target.lastIndexOf(SystemConstant.DOT));
						}
					}

					if(targetPackageName == null){
						try{
							targetPackageName = getPackageName(target);
						}catch(Exception ex){
							log.error("Exception: ", ex);
							throw ex;
						}
					}
				}

			}
		}catch(Exception ex){
			log.error("Exception: ", ex);
			throw ex;
		}
	}

	/**
	 * Returns a query
	 * @return
	 */
	public String getQuery(){
		return query;
	}
	/**
	 * Returns target class name
	 * @return
	 */
	public String getTargetClassName(){
		return targetClassName;
	}
	/**
	 * Returns the criteria value
	 * @return
	 */
	public String getCriteria(){
		return criteria;
	}
	/**
	 * Returns the pageNumber
	 * @return
	 */
	public String getPageNumber(){
		return pageNumber;
	}
	/**
	 * Sets the page size value
	 * @param size
	 */
	public void setPageSize(int size){
		pageSize = String.valueOf(size);
	}
	/**
	 * Returns the page size
	 * @return
	 */
	public String getPageSize(){
		return pageSize;
	}
	/**
	 * Returns the resultCounter value
	 * @return
	 */
	public String getResultCounter(){
		return resultCounter;
	}

	/**
	 * Returns a query type value based on a given string.
	 * @param url
	 * @return
	 */
	public String getQueryType(String url){
		String queryType = "HTTPQuery";
		if(url.indexOf("Get")>0){
			queryType = url.substring(url.lastIndexOf("Get"));
		}
		return queryType;
	}
	/**
	 * Returns the target package name
	 * @return
	 */
	public String getTargetPackageName(){
		return targetPackageName;
	}

	/**
	 * Generates a search criteria from a criteria list
	 * @param packageName - specifies the package name
	 * @param criteriaList - specifies a list of criteria instances.
	 * @return
	 * @throws Exception
	 */
	private Object buildSearchCriteria(String packageName, List criteriaList ) throws Exception{
		SearchUtils searchUtils = new SearchUtils(classCache);
		return searchUtils.buildSearchCriteria(packageName, criteriaList);
	}

	/**
	 * Returns fully qualified search class names
	 * @param searchClasses - specifies the search class names
	 * @param packageName - specifies the package name
	 * @return
	 */
	private String getSearchClassNames(String searchClasses, String packageName){
		String path = "";

		/**
    if(packageName != null && !(targetClassName.indexOf(SystemConstant.DOT)>0) && (targetClassName.indexOf(",")<0)){
        targetClassName = packageName +SystemConstant.DOT+targetClassName;
    }
		 **/
		String delimiter = null;
		if(searchClasses.indexOf(SystemConstant.FORWARD_SLASH)>0){
			delimiter = SystemConstant.FORWARD_SLASH_STR;
		}
		else {
			delimiter = SystemConstant.COMMA_STR;
		}
		StringTokenizer st = new StringTokenizer(searchClasses, delimiter);
		String className = st.nextToken();
		if(className.indexOf(SystemConstant.DOT)>0){
			path = className;
		}
		else{
			path = packageName + SystemConstant.DOT + className;
		}


		if(st.countTokens()>0){
			while(st.hasMoreElements()){
				className = st.nextToken().trim();
				if(className.indexOf(SystemConstant.DOT)>0){
					path += SystemConstant.COMMA +  className;
				}
				path += SystemConstant.COMMA + packageName + SystemConstant.DOT + className;
			}
		}

		return path;
	}

	/**
	 * Returns a search criteria list from a criteria
	 * @param criteria - specifies the criteria string
	 * @return
	 */
	public List<String> getSearchCriteriaList(String criteria) {

		List<String> criteriaList = new ArrayList<String>();
		String delimiter = null;

		if (criteria.indexOf(SystemConstant.FORWARD_SLASH) > 0) {
			delimiter = SystemConstant.FORWARD_SLASH_STR;
		} else {
			delimiter = SystemConstant.BACK_SLASH;
		}

		StringBuffer critString = new StringBuffer();
		for (StringTokenizer st = new StringTokenizer(criteria, delimiter); st.hasMoreElements();) {

			String crit = st.nextToken().trim();
			critString.append(crit);

			boolean valid = validateSyntax(critString.toString());
			if (valid) {
				criteriaList.add(critString.toString());
				critString = new StringBuffer();
			} else {
				int len = critString.length();
				if (criteria.length() > critString.length()) {
					for (int i = len; i < criteria.length(); i++) {
						if (criteria.charAt(i) == delimiter.charAt(0))
							critString.append(delimiter);
						else
							break;
					}
				}
			}
		}
		return criteriaList;

	}

	private boolean validateSyntax(String query) {

		boolean valid = false;
		int startCounter = 0;
		int endCounter = 0;

		for (int i = 0; i < query.length(); i++) {
			if (query.charAt(i) == '[') {
				startCounter++;
			} else if (query.charAt(i) == ']') {
				endCounter++;
			}

		}

		if (startCounter == endCounter) {
			valid = true;
		}

		return valid;
	}

	/**
	 * Generates an org.jdom.Document based on a resultSet
	 * 
	 * @param resultSet -
	 *            specifies a list of populated domain objects
	 * @param pageNumber -
	 *            specifies the page number
	 * @return
	 * @throws Exception
	 */
	public org.jdom.Document getXMLDocument(Object[] resultSet, int pageNumber) throws Exception{

		org.jdom.Element httpQuery = new org.jdom.Element("httpQuery",namespace);
		org.jdom.Element queryRequest = new org.jdom.Element("queryRequest");

		Element queryString = new Element("queryString").setText(query);
		String targetResult = targetClassName;
		if(targetResult.indexOf(SystemConstant.COMMA)>1){
			targetResult = targetResult.substring(0, targetResult.indexOf(SystemConstant.COMMA));
		}
		if(targetResult.indexOf(SystemConstant.DOT)<0){
			targetResult = this.getPackageName(targetResult)+ SystemConstant.DOT +targetResult;
		}

		Element queryClass = new Element("class").setText(targetResult);
		Element queryElement = new Element("query").addContent(queryString).addContent(queryClass);
		queryRequest.addContent(queryElement);
		queryRequest.addContent(new org.jdom.Element("criteria").setText(criteria));

		httpQuery.addContent(queryRequest);

		int start = 0;
		int end = resultSet.length;
		int rowCount = 0;
		int index = 0;
		int resultCount = 0;
		int nextStartIndex = 0;
		int totalNumRecords = results.size();

		if(!(startIndex.equals("0") || startIndex == null)){
			index = Integer.valueOf(startIndex).intValue();
		}

		if(!(resultCounter.equals("0") ||resultCounter == null )){
			resultCount = Integer.valueOf(resultCounter).intValue();
		}

		Element xmlElement = new Element("queryResponse");

		String counter = String.valueOf(totalNumRecords);
		xmlElement.addContent(new Element("recordCounter").setText(counter));

		if(resultSet.length >0){

			if(pageSize != null){
				rowCount = Integer.parseInt(pageSize);
			}
			int pageCounter = 1;
			int size = resultSet.length;
			if((index + resultCount)> totalNumRecords){
				size = totalNumRecords - index;
			}

			if(rowCount > 0 && rowCount < size){
				pageCounter = size/rowCount;
				if((size % rowCount)>0){
					pageCounter++;
				}
			}
			if(pageNumber > pageCounter){
				pageNumber = 1;
			}
			if(pageNumber > 0 && pageNumber <= pageCounter){
				end = rowCount * pageNumber;
				start = end - rowCount;
				if(size < end){
					end = size;
				}
			}

			String recordNum = "";

			Set<String> resultClass = new HashSet<String>();
			List<String> classes = new ArrayList<String>();
			for(int x=start; x<end; x++){
				resultClass.add(resultSet[x].getClass().getName());
			}

			if(resultClass.size() >1){
				Object lists[] = new Object[resultClass.size()];
				int number =0;
				for(Iterator it= resultClass.iterator(); it.hasNext();){
					String typeName = (String)it.next();
					classes.add(typeName);
					List<Object> list = new ArrayList<Object>();
					for(int i=start; i<end ; i++){
						if(resultSet[i].getClass().getName().equals(typeName)){
							list.add(resultSet[i]);
						}
					}
					lists[number]= new Object();
					lists[number] = list ;
					number++;
				}

				for(int o=0; o<lists.length; o++){
					List subResults = (List)lists[o];
					for(int i=0; i< subResults.size(); i++){
						Object result = subResults.get(i);
						int recNum = index + i + 1;
						recordNum = String.valueOf(recNum);
						xmlElement.addContent(getElement(result, recordNum));
					}

				}


			} else{

				for(int i = start; i< end; i++){
					int recNum = index + i + 1;
					recordNum = String.valueOf(recNum);
					Object result = resultSet[i];
					xmlElement.addContent(getElement(result, recordNum));

				}
			}

			if((index - resultCount)>=0){
				nextStartIndex = index - resultCount;
				String preLink = servletName +"?query="+targetClassName + SystemConstant.AMPERSAND + criteria +"&startIndex="+nextStartIndex+"&resultCounter="+resultCounter;
				String preText = "<<< "+" PREVIOUS "+ resultCount +" RECORDS";
				Element preElement = new Element("previous").setAttribute("type","simple",namespace).setAttribute("href",preLink,namespace).setText(preText);
				xmlElement.addContent(preElement);
			}
			String pCount = String.valueOf(pageCounter);
			Element pagesElement = new Element("pages").setAttribute("count",pCount);
			if((index + resultCount)< totalNumRecords){
				nextStartIndex = index + resultCount;
				String nextLink = servletName +"?query="+targetClassName + SystemConstant.AMPERSAND +criteria +"&startIndex="+nextStartIndex+"&resultCounter="+resultCounter;
				String nextText = "NEXT "+ resultCount+" RECORDS >>> ";
				Element nextElement = new Element("next").setAttribute("type","simple",namespace).setAttribute("href",nextLink,namespace).setText(nextText);
				xmlElement.addContent(nextElement);
			}

			for(int i=0; i< pageCounter; i++){
				int p = i + 1;
				String pageLink = servletName +"?query="+targetClassName+SystemConstant.AMPERSAND+criteria +"&pageNumber="+p+"&resultCounter="+resultCounter+"&startIndex="+startIndex;
				String page = String.valueOf(p);
				String pageText = SystemConstant.SPACE + page + SystemConstant.SPACE;
				Element pElement = new Element("page").setAttribute("number",page).setAttribute("type","simple",namespace).setAttribute("href",pageLink,namespace).setText(pageText);
				pagesElement.addContent(pElement);
			}

			xmlElement.addContent(pagesElement);

			httpQuery.addContent(xmlElement);
			xmlElement.addContent(new Element("recordCounter").setText(counter));

		}
		else{

			xmlElement.addContent(new Element("recordCounter").setText("0"));
		}

		if((pageNumber -1)> 0){
			index += ((pageNumber -1)* rowCount) + 1;
		}
		else{
			index+= 1;
		}
		int endRecordNum = rowCount + index - 1;
		if(endRecordNum > totalNumRecords){
			endRecordNum = totalNumRecords;
		}

		String startCounter = String.valueOf(index);
		String endCounter = String.valueOf(endRecordNum);
		Element startElement = new Element("start").setText(startCounter);
		Element endElement = new Element("end").setText(endCounter);
		xmlElement.addContent(startElement).addContent(endElement);

		org.jdom.Document xmlDoc = new org.jdom.Document(httpQuery);

		return xmlDoc;
	}


	/**
	 * Returns the value of an id field from a given result
	 * @param result - populated instance of a class
	 * @return
	 * @throws Exception
	 */
	private String getCriteriaIdValue(Object result) throws Exception{
		Field[] fields = classCache.getAllFields(result.getClass());
		Field idField = getIdField(fields);
		String id = idField.getName();
		String criteriaIdValue = SystemConstant.AT + id + SystemConstant.EQUAL;
		if(idField.getName().indexOf(SystemConstant.DOT)>0){
			id = id.substring(id.lastIndexOf(SystemConstant.DOT)+1);
		}
		if(getFieldValue(idField, result) != null){
			criteriaIdValue += String.valueOf(getFieldValue(idField, result));
		}
		return criteriaIdValue;
	}

	/**
	 * Generates an Element for a given result object
	 * @param result - an instance of a class
	 * @param recordNum - specifies the record number in the result set
	 * @return
	 * @throws Exception
	 */
	private Element getElement(Object result, String recordNum) throws Exception{


		Element classElement = new Element("class").setAttribute("name",result.getClass().getName()).setAttribute("recordNumber",recordNum);
		String criteriaIdValue = getCriteriaIdValue(result);

		String link = null;

		Field[] fields = classCache.getAllFields(result.getClass());
		for(int f=0; f<fields.length; f++){
			String criteriaBean = result.getClass().getName();
			Field field = fields[f];
			String fieldName = field.getName();


			if(fieldName.equalsIgnoreCase("serialVersionUID")){
				continue;
			}
			Element fieldElement = new Element("field").setAttribute("name", fieldName);

			String fieldType = field.getType().getName();
			String targetBean = null;

			if(!(fieldType.startsWith("java") && !(fieldType.endsWith("Collection")) || field.getType().isPrimitive())){
				if(field.getType().getName().endsWith("Collection") ){
					SearchUtils searchUtils = new SearchUtils(classCache);
					if(searchUtils.getTargetClassName(result.getClass().getName(),fieldName)!=null){
						targetBean = searchUtils.getTargetClassName(result.getClass().getName(),fieldName);
						
						//handle primitive collections
						if (Class.forName(targetBean).isPrimitive() ||
								targetBean.startsWith("java")){
							targetBean = null;
						}
					}
				}
				else if(locateClass(fieldType)){
					targetBean = fieldType;
				}

			}

			if(targetBean != null){
				if(result.getClass().getPackage().equals(Class.forName(targetBean).getPackage())){
					targetBean = targetBean.substring(targetBean.lastIndexOf(SystemConstant.DOT)+1);
					if(criteriaBean.indexOf(SystemConstant.DOT)>0){
						criteriaBean = criteriaBean.substring(criteriaBean.lastIndexOf(SystemConstant.DOT)+1);
					}
				}
				String methodName = "get"+  fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
	
				link = servletName + "?query=" +  targetBean + SystemConstant.AMPERSAND +criteriaBean + SystemConstant.LEFT_BRACKET+ criteriaIdValue+SystemConstant.RIGHT_BRACKET + 
						SystemConstant.AMPERSAND + "roleName=" + fieldName;
				
				fieldElement.setAttribute("type","simple",namespace).setAttribute("href",link,namespace).setText(methodName);


			}

			else{
				String fieldValue = "-";
				Object value = null;

				try{
					if(fieldType.indexOf("Collection")>0 || fieldType.endsWith("HashSet") || fieldType.endsWith("ArrayList") || fieldType.indexOf("Vector")>0){
						
						if (((Collection)fields[f].get(result)).size() > 0) {
							
							Iterator it = ((Collection)fields[f].get(result)).iterator();
							fieldValue = String.valueOf(it.next());
							while(it.hasNext()){
								fieldValue += "; "+ String.valueOf(it.next());
							}
						}
						
						if(fieldValue != null){
							fieldElement.setText(fieldValue);
						}
						else{
							fieldElement.setText("-");
						}

					}
					else{
						if(this.getFieldValue(field,result)!= null){
							value = getFieldValue(field,result);
							fieldValue = String.valueOf(value);
						}
						fieldElement.setText(fieldValue);
					}

				}catch(Exception ex){
					fieldValue = "-";
					value = getFieldValue(field,result);
					fieldValue = String.valueOf(value);
					String temp = null;
					for(int s=0; s< fieldValue.length(); s++){
						String charValue = String.valueOf(fieldValue.charAt(s));
						try{
							temp += charValue;
						}catch(Exception e){
							temp += " ";
						}
					}
					if(temp != null){
						fieldValue = temp;
					}

					fieldElement.setText(fieldValue);
				}

			}

			classElement.addContent(fieldElement);
		}
		return classElement;
	}

	/**
	 * Returns the package name of a given class
	 * @param className - specifies the class name
	 * @return
	 * @throws Exception
	 */
	public String getPackageName(String className) throws Exception{
		String packageName = classCache.getPkgNameForClass(className);

		if (packageName == null){
			log.error("No package name found for class: " + className);
			throw new Exception("No package name found for class: " + className);
		}

		log.debug("Package name found for class: " + className + " is: " + packageName);
		return packageName;
	}

	/**
	 * Returns true if the given class name is found
	 * @param className
	 * @return
	 */
	public boolean locateClass(String className){
		//To make sure class is not a proxy generated by CGLIB
		if(className.indexOf("$")>1)
			className = className.substring(0, className.indexOf("$"));

		try {
			classCache.getClassFromCache(className);
		} catch(ClassNotFoundException e){
			return false;
		}

		return true;

	}

	/**
	 * Returns an id field from an array of fields
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	private Field getIdField(Field[] fields) throws Exception{
		Field id = null;
		for(int i=0; i<fields.length;i++){
			if(fields[i].getName().equalsIgnoreCase("id")){
				if(!this.locateClass(fields[i].getType().getName())){
					id = fields[i];
					break;
				}
			}
		}
		return id;
	}

	/**
	 * Returns a field that matches the given String from an array of fields
	 * @param fields All fields
	 * @param fieldName  Field name
	 * @return
	 * @throws Exception
	 */
	private Field getFieldByName(Field[] fields, String fieldName) throws Exception{
		Field field = null;
		for(int i=0; i<fields.length;i++){
			if(fields[i].getName().equalsIgnoreCase(fieldName)){
				if(!this.locateClass(fields[i].getType().getName())){
					field = fields[i];
					break;
				}
			}
		}
		return field;
	}
	/**
	 * Returns a field value
	 * @param field - specifies the field
	 * @param domain - specifies the object
	 * @return
	 * @throws Exception
	 */
	private Object getFieldValue(Field field, Object domain) throws Exception{
		Object value = null;
		if(field.get(domain)!= null){
			if(field.getType().getName().equals("java.util.Date")){
				SimpleDateFormat date = new SimpleDateFormat("MM-dd-yyyy");
				value = date.format((Date)field.get(domain));
			}
			else{
				value = field.get(domain);
			}
		}
		return value;
	}

	/**
	 * Returns an array of result objects
	 * @return
	 * @throws Exception
	 */
	public Object[] getResultSet()throws Exception{

		results = new ArrayList();
		int index = 0;
		int counter = 1000;

		try{
			String searchPath = getSearchClassNames(targetClassName, targetPackageName);
			List criteriaList = getSearchCriteriaList(criteria);
			Object criteria = buildSearchCriteria(targetPackageName, criteriaList );

			if(startIndex != null || !startIndex.equals("0")){
				index = Integer.parseInt(startIndex);
			}
			if(resultCounter != null){
				counter = Integer.parseInt(resultCounter);
			}
			if (roleName != null){
				results = applicationService.getAssociation(criteria, roleName);
			} else {
				results = applicationService.search(searchPath, criteria);
			}

			if (results != null && (results instanceof ListProxy)){
				((ListProxy)results).setAppService(applicationService);
			}
		}catch(Exception ex){
			log.error("Exception: ", ex);
			throw ex;
		}


		if((counter + index) > results.size()){
			counter = results.size();
		}
		else{
			counter += index;
		}

		Object[] resultSet = new Object[counter];
		for(int i = index, s=0; i< counter; i++,s++){
			resultSet[s]= results.get(i);
		}

		return resultSet;
	}

	/**
	 * Returns true if a match is found
	 * @param prop - an instance of an HTTPUtils class
	 * @return
	 */
	public boolean getMatch(HTTPUtils prop){
		boolean match = false;
		String oldQuery = prop.getCriteria();
		String oldTarget = prop.getTargetClassName();
		int size = prop.getResults().size();

		if((this.targetClassName.equals(oldTarget))&& this.criteria.equals(oldQuery) && size > 0){
			match = true;
		}
		return match;
	}

	/*
	 * This method is used when an XSLT stylesheet is not found.(Test methods)
	 */
	/**
	 * Prints results on screen
	 * @param resultList
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void printResults(HttpServletResponse response)throws IOException, ServletException{

		response.setContentType("text/html");
		ServletOutputStream out = response.getOutputStream();
		out.println("<br><font color=purple><b>");
		out.println("<b>"+results.size() +" records found. </b><br><hr>");
		int recordNum = 1;
		out.println();
		out.println("<font size=4 color=black> Criteria : "+ this.getCriteria()+"</font>" );
		if(results.size()>0)
			out.println("<br><font size=4 color=black> Result Class name: "+ results.get(0).getClass().getName()+"</font>" );
		else
			out.println("<br><font size=4 color=black>No records found </font>" );
		out.println("<br><hr><br>");
		out.println("<TABLE BORDER=\"2\"  style=\"table-layout:AUTO\" valign=\"top\">");
		try{

			for(int i =0; i< results.size(); i++){

				printRecord(results.get(i), servletName, out, recordNum);

				recordNum++;
			}
		}catch(Exception ex){
			log.error("Exception: ", ex);
			throw new IOException(ex.getMessage());
		}
	}
	/**
	 * Displays a record on screen
	 * @param result
	 * @param servletName
	 * @param out
	 * @param recordNum
	 * @throws Exception
	 */
	private void printRecord(Object result, String servletName, ServletOutputStream out, int recordNum)throws Exception{

		Class resultClass = result.getClass();
		String className = resultClass.getName();
		Class superClass = resultClass.getSuperclass();

		Field[] fields      = classCache.getAllFields(resultClass);
		Field[] superFields = classCache.getAllFields(superClass);


		if(recordNum == 1){
			out.println("<TR BGCOLOR=\"#E3E4FA\">");
			for(int x=0; x<fields.length; x++){
				String fName = fields[x].getName();
				if(!fName.equalsIgnoreCase("serialVersionUID")){
					out.println("<TD>"+ fName +"</TD>");
				}

			}
			for(int x=0; x<superFields.length; x++){
				String fName = superFields[x].getName();
				if(!fName.equalsIgnoreCase("serialVersionUID")){
					out.println("<TD>"+ fName +"</TD>");
				}

			}
			out.println("</TR>");
		}
		out.println("<TR VALIGN=\"TOP\">");

		Field idField = null;
		String criteriaIdValue = null;


		for(int f=0;f<fields.length; f++){
			fields[f].setAccessible(true);
			String fieldName = fields[f].getName().substring(0,1).toUpperCase()+fields[f].getName().substring(1);
			if(fields[f].getName().equalsIgnoreCase("id")){
				idField = fields[f];
				try{
					Object idValue = idField.get(result);
					String id = null;
					if(!idField.getType().getName().endsWith("String")){
						id = String.valueOf(idValue);
					}
					criteriaIdValue = SystemConstant.AT+idField.getName()+SystemConstant.EQUAL+id;
				}catch(Exception ex){
					throw new IOException(ex.getMessage());
				}
			}
			else if(fieldName.equalsIgnoreCase("serialVersionUID")){
				continue;
			}
			boolean bean = false;
			String fieldType = fields[f].getType().getName();
			if(fieldType.indexOf("$")>1)
				fieldType = fieldType.substring(0, fieldType.indexOf("$"));

			Object value = "-";
			if(fields[f].get(result)!= null){
				value = fields[f].get(result);
				fieldType = value.getClass().getName();
				if(fieldType.indexOf("$")>1)
					fieldType = fieldType.substring(0, fieldType.indexOf("$"));

				if(!fieldType.startsWith("java.")){
					bean = locateClass(fieldType);
				}

			}


			String methName = "get"+ fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);

			String beanName = null;

			if(bean){
				beanName = fieldType.substring(fieldType.lastIndexOf(SystemConstant.DOT)+1);
			}



			String returnObjectName = fields[f].getName();
			boolean collectionType = false;
			if(returnObjectName.endsWith("Collection")|| fieldType.endsWith("Vector")|| fieldType.endsWith("HashSet")){
				collectionType = true;
			}

			if((fieldType.startsWith("java.")&& !(collectionType)) || fields[f].getType().isPrimitive()){
				String strValue = " ";
				if(value != null){
					strValue = String.valueOf(value);
				}
				out.println("<TD>"+ strValue +"</TD>" );
			}
			else if(returnObjectName.endsWith("Collection")){

				String returnClassName = returnObjectName.substring(0,returnObjectName.lastIndexOf("Collection"));
				returnClassName = returnClassName.substring(0,1).toUpperCase() + returnClassName.substring(1);
				String disp = "<TD><a href="+servletName+"?query="+ returnClassName+ SystemConstant.AMPERSAND +className.substring(className.lastIndexOf(SystemConstant.DOT)+1)+"[@"+idField.getName()+ SystemConstant.EQUAL +idField.get(result)+"]>" + methName+"</a></TD>";

				out.println(disp);
			}
			else if(bean){
				String disp = "<TD><a href="+servletName+"?query="+beanName+SystemConstant.AMPERSAND+className.substring(className.lastIndexOf(SystemConstant.DOT)+1)+"[@"+idField.getName()+ SystemConstant.EQUAL +idField.get(result)+"]>"+methName+"</a></TD>";

				out.println(disp);
			}


		}
		recordNum++;
		out.println("</TR>");
	}


}


