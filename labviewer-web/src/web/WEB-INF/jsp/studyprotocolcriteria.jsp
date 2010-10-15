<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="studyProtocol.search.title"/></title>
    <s:head/>

</head>
<body>
<!-- main content begins-->

    <h1><fmt:message key="studyProtocol.search.header"/></h1>
    <s:form>
        <table class="form">
            <tr>
                <td  scope="row" class="label">
                    <label for="officialTitle"> <fmt:message key="studyProtocol.officialTitle"/></label>
                </td>
                <td>
                    <s:textfield id="officialTitle" name="criteria.officialTitle" maxlength="200" size="100" cssStyle="width:200px"  />
                </td>
            </tr>
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><li>
                            <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Search</span></span></s:a>
                            <s:a href="#" cssClass="btn" onclick="resetValues();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                        </li>
                </ul>
            </del>

        </div>


   </s:form>

 </div>
 <div class="line"></div>


</body>
</html>
