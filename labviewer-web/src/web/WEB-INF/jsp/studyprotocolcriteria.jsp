<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <c:set var="topic" scope="request" value="submit"/>
    <title>
            <s:text name="trialList.title" />
    </title>
</head>
<body>
    <s:form name="studyListForm">
    <h1><s:text name="studySearch.title"/></h1>
    <table class="form">
    <tr><td><h2><s:text name="studySearch.page.header"/></h2></td></tr>
    <tr>
        <td>
		    <table class="form">
		      <tr>
		        <td class="label"><s:text name="studySearch.title" /></td>
		        <td class="value"><s:textfield  name="title" cssStyle="width:98%;max-width:360px"/></td>
		        <td class="label"><s:text name="studySearch.identifier"/></td>
		        <td class="value"><s:textfield  name="identifier"  /></td>
		      </tr>
			</table>
		</td>
	</tr>
	<tr>
	   <td>
	      <div class="actionsrow">
	        <del class="btnwrapper">
	          <ul class="btnrow">
	            <li>
	              <s:a id="searchBtn" href="#" cssClass="btn" onclick="handleSearchAction()"><span class="btn_img"><span class="search">Search</span></span></s:a>
	              <s:a id="resetBtn" href="#" cssClass="btn" onclick="handleResetAction()"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
	            </li>
	          </ul>
	        </del>
	      </div>
	    </td>
	</tr>  
	</s:form>  
</body>
</html>


