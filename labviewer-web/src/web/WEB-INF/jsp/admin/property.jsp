<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="/WEB-INF/jsp/common/includejs.jsp"/>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
    <title><fmt:message key="property.search.title"/></title>
    <s:head/>

    <script type="text/javascript">
        function submitAction() {
            document.propertyFrom.action = "propertysave.action";
            document.propertyFrom.submit();
        }
        function resetAction() {
            document.propertyFrom.action = "propertylist.action";
            document.propertyFrom.submit();
        }

        </script>
</head>
<body>

<!-- main content begins-->
    <c:set var="topic" scope="request" value="property"/>
    <h1><fmt:message key="property.search.header"/></h1>
    <s:form name="propertyFrom">
        <jsp:include page="/WEB-INF/tags/failureMessage.jsp"/>
        <jsp:include page="/WEB-INF/tags/successMessage.jsp"/>
        <table class="form" >
            <tr>
                <td class="label"><fmt:message key="caers.url"/></label> <span class="required">*</span></td>
                <td>
                    <s:textfield name="caaersUrl"  id="caaersUrl" style="width:500px"/>
                    <span class="formErrorMsg"><s:fielderror><s:param>caaersUrl</s:param></s:fielderror></span>
                </td>
            </tr>
            <tr>
                <td class="label"><fmt:message key="c3d.url"/></label> <span class="required">*</span></td>
                <td><s:textfield name="c3dUrl"  id="c3dUrl" style="width:500px"/>
                <span class="formErrorMsg"><s:fielderror><s:param>c3dUrl</s:param></s:fielderror></span></td>
            </tr>
            <tr>
                <td class="label"><fmt:message key="hub.url"/></label> <span class="required">*</span></td>
                <td><s:textfield name="hubUrl"  id="hubUrl" style="width:700px"/>
                <span class="formErrorMsg"><s:fielderror><s:param>hubUrl</s:param></s:fielderror></span></td>
            </tr>
            <tr>
                <td class="label"><fmt:message key="version"/></label> <span class="required">*</span></td>
                <td>
                    <s:textfield name="version"  id="version" style="width:200px"/>
                    <span class="formErrorMsg"><s:fielderror><s:param>version</s:param></s:fielderror></span>
                </td>
            </tr>
            <tr>
                <td class="label"><fmt:message key="help.link"/></label> <span class="required">*</span></td>
                <td>
                    <s:textfield name="helpLink"  id="helpLink" style="width:650px"/>
                    <span class="formErrorMsg"><s:fielderror><s:param>helpLink</s:param></s:fielderror></span>
                </td>
            </tr>
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><li>
                            <s:a href="#" cssClass="btn" onclick="submitAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                            <s:a href="#" cssClass="btn" onclick="resetAction()"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                            
                        </li>
                </ul>
            </del>

        </div>
        <c:choose>
        <c:when test="${results != null}">
            <h2>Search Results</h2>
            <jsp:include page="/WEB-INF/jsp/protocol/result.jsp"/>
        </c:when>
        </c:choose>
   </s:form>

 </div>
 <div class="line"></div>
 
</body>
</html>
