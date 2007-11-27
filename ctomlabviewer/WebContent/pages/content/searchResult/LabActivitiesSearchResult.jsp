<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%@ page import="gov.nih.nci.caxchange.ctom.viewer.constants.*"%>
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.viewobjects.*"%>
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.forms.*"%>
<script>
<!--
   	function submitAdverseEvent(recordIdvalue)
   	{
	  		document.LabActivitiesSearchResultForm.operation.value="execute";
            document.LabActivitiesSearchResultForm.target="_self";
            document.LabActivitiesSearchResultForm.action="<%="submitAdverseEvent.do"%>";
	  		document.LabActivitiesSearchResultForm.recordId.value=recordIdvalue;
	  		document.LabActivitiesSearchResultForm.submit();
 	}

   	function setAndRedirect(patientId, studyId)
   	{

	  		document.LabActivitiesSearchResultForm.operation.value="redirect";
	  		document.LabActivitiesSearchResultForm.target="_blank";
	  		document.LabActivitiesSearchResultForm.mrn.value=patientId;
	  		document.LabActivitiesSearchResultForm.nciIdentifier.value=studyId;
            document.LabActivitiesSearchResultForm.target="_blank";
	  		document.LabActivitiesSearchResultForm.action="<%=DisplayConstants.CAAERS_URL%>";
	  		document.LabActivitiesSearchResultForm.submit();
 	}

   	function setAndSubmit(target)
   	{
   		document.LabActivitiesSearchResultForm.target="_self";
  		var ischecked = false;
  		if (target=="loadSearch")
  		{
	  		document.LabActivitiesSearchResultForm.action="LabActivitiesDB.do";  
	  		document.LabActivitiesSearchResultForm.operation.value="execute";
	  		ischecked = true;
  		}
  		else
  		{   
	  		var radioLen = document.LabActivitiesSearchResultForm.recordIds.length;
	
	  		if(radioLen == undefined)
	  		{
	 			if (document.LabActivitiesSearchResultForm.recordIds.checked) 
				{	document.LabActivitiesSearchResultForm.recordId.value = document.LabActivitiesSearchResultForm.recordIds.value;
					ischecked = true;
				}
	  		}
			for (var i = 0; i <radioLen; i++)
			{   
				if (document.LabActivitiesSearchResultForm.recordIds[i].checked) 
				{	document.LabActivitiesSearchResultForm.recordId.value = document.LabActivitiesSearchResultForm.recordId.value + "," + document.LabActivitiesSearchResultForm.recordIds[i].value;
					ischecked = true;
				}
			}   
			document.LabActivitiesSearchResultForm.action="loadToCTMS.do";  
	  		document.LabActivitiesSearchResultForm.operation.value="execute";
		}
		if(ischecked) {
		   document.LabActivitiesSearchResultForm.target="_self";
     	   document.LabActivitiesSearchResultForm.submit();
     	   }
		else
			alert("Atleast one check box should be checked.");
 	}

// -->
</script>

<!-- laf box 1st half -->
<div class="box">
	<div class="pane" align=center>
    <!-- header -->
	    <div class="header"><div class="background-L"><div class="background-R">
	      <h2>Lab Activities - Search Results</h2>
	    </div></div></div>
	    <!-- inner border -->
	    <div class="border-T"><div class="border-L"><div class="border-R"><div class="border-B"><div class="border-TL"><div class="border-TR"><div class="border-BL"><div class="border-BR">
	        <div class="interior"> <!-- interior -->
	            <div class="content">
<!-- laf box 1st half -->

<table summary="" cellpadding="0" cellspacing="0" border="0" class="contentPage" width="95%" height="100%">
	<html:form styleId="LabActivitiesSearchResultForm" action="<%="/LabActivitiesDB"%>" target="_self">
		<html:hidden property="operation" value="read" />
		<bean:define name="<%=DisplayConstants.LOGIN_OBJECT%>" id="loginObject" type="LoginForm" />
		<html:hidden property="gridProxy" value="<%=loginObject.getGridProxy()%>" />
		<html:hidden property="mrn" value="" />
		<html:hidden property="nciIdentifier" value="" />
		<html:hidden property="recordId" value="" />
		<tr>
			<td colspan="3">
				<html:errors />
			</td>
		</tr>
		<tr>
			<td class="infoMessage" colspan="3">
				<html:messages id="message" message="true">
				<bean:write name="message"/>
				</html:messages>	
				</td>
		</tr>
		<tr>
		<td colspan="3" align="center">
		<% 
		  String gridId= (String)session.getAttribute("studySubjectGridId");
		 %>
		<a href="<bean:message key="BaseURLcaAERS"/>/studySubject?studySubjectGridId=<%=gridId%>">View this patient in caAERS</a>&nbsp;&nbsp;&nbsp;||&nbsp;&nbsp;&nbsp;<a href="<bean:message key="BaseURLC3D"/>/studySubject?studySubjectGridId=<%=gridId%>">View these labs in C3D</a>
		</td>	
		</tr>
		<tr>
			<td valign="top">
				<table cellpadding="0" cellspacing="0" border="0" width="100%" class="contentBegins">
					<tr>
						<td>
							<table summary="" cellpadding="0" cellspacing="0" border="0" width="100%">
								<logic:present name="<%=DisplayConstants.SEARCH_RESULT%>">
									<bean:define name="<%=DisplayConstants.SEARCH_RESULT%>" property="searchResultObjects" id="searchResultObjects" />
									<bean:define id="oddRow" value="true" />
									<tr>
										<td>
											<table summary="Enter summary of data here" cellpadding="3" cellspacing="0" border="0" class="dataTable" width="100%">
												<tr>
													<th class="dataTableHeader" scope="col" align="center" width="5%">
														Select
													</th>
													<th class="dataTableHeader" scope="col" align="center" width="9%">
														Patient Id
													</th>
													<th class="dataTableHeader" scope="col" align="center" width="9%">
														Date / Time
													</th>
													<th class="dataTableHeader" scope="col" align="center" width="9%">
														Lab Test
													</th>
													<th class="dataTableHeader" scope="col" align="center" width="9%">
														Text Result
													</th>
													<th class="dataTableHeader" scope="col" align="center" width="9%">
														Numeric Result
													</th>
													<th class="dataTableHeader" scope="col" align="center" width="9%">
														Unit Of Measure
													</th>
													<th class="dataTableHeader" scope="col" align="center" width="9%">
														Lower Limit
													</th>
													<th class="dataTableHeader" scope="col" align="center" width="9%">
														Upper Limit
													</th>
													<!--
													<th class="dataTableHeader" scope="col" align="center" width="13%">
														Adverse Events
													</th>
													-->
												</tr>
												<logic:iterate name="searchResultObjects" id="searchResultObject" type="LabActivityResult">
													<%if (oddRow.equals("true"))
													{
														oddRow = "false";%>
													<tr class="dataRowLight">
														<td class="dataCellNumerical" width="5%">
															<html:multibox style="formFieldSized" property="recordIds" value="<%=searchResultObject.getRecordId()%>"></html:multibox>
														</td>
														<td class="dataCellText" width="9%">
															<bean:write name="searchResultObject" property="patientId" />
															&nbsp;
														</td>
														<td class="dataCellText" width="9%">
															<bean:write name="searchResultObject" property="date" />
															&nbsp;
														</td>
														<td class="dataCellText" width="9%">
															<bean:write name="searchResultObject" property="labTestId" />
															&nbsp;
														</td>
														<td class="dataCellText" width="9%">
															<bean:write name="searchResultObject" property="textResult" />
															&nbsp;
														</td>
														
														<logic:equal name="searchResultObject" property="isAdverseEvent" value="<%=DisplayConstants.YES%>">
															<td class="dataCellText" width="9%" bgcolor="#ffiooo">
																<bean:write name="searchResultObject" property="numericResult" />
																&nbsp;
															</td>
														</logic:equal>
														<logic:notEqual name="searchResultObject" property="isAdverseEvent" value="<%=DisplayConstants.YES%>">
															<td class="dataCellText" width="9%">
																<bean:write name="searchResultObject" property="numericResult" />
																&nbsp;
															</td>
														</logic:notEqual>

														<td class="dataCellText" width="9%">
															<bean:write name="searchResultObject" property="unitOfMeasure" />
															&nbsp;
														</td>

														<td class="dataCellText" width="9%">
															<bean:write name="searchResultObject" property="lowRange" />
															&nbsp;
														</td>
														<td class="dataCellText" width="9%">
															<bean:write name="searchResultObject" property="highRange" />
															&nbsp;
														</td>
														<!--
														<logic:equal name="searchResultObject" property="adverseEventReported" value="<%="0"%>">
															<td class="dataCellText" width="15%">
																<html:button style="actionButton" property="adverseEventReported" onclick="<%="submitAdverseEvent('"+searchResultObject.getRecordId()+"');"%>">Submit</html:button>
															</td>
														</logic:equal>
														<logic:notEqual name="searchResultObject" property="adverseEventReported" value="<%="0"%>">
															<td class="dataCellText" width="15%">
																<a class="sublink" href="javascript:setAndRedirect('<bean:write
																	name="searchResultObject" property="patientId" />','<bean:write
																	name="searchResultObject" property="studyId" />')">
																	Reported on
																	<bean:write name="searchResultObject" property="adverseEventReportedDate" />
																</a>
																&nbsp;
															</td>
														</logic:notEqual>
														-->
													</tr>
													<%}
													else
													{
														oddRow = "true";%>
													<tr class="dataRowDark">
														<td class="dataCellNumerical" width="5%">
															<html:multibox style="formFieldSized" property="recordIds" value="<%=searchResultObject.getRecordId()%>"></html:multibox>
														</td>
														<td class="dataCellText" width="9%">
															<bean:write name="searchResultObject" property="patientId" />
															&nbsp;
														</td>
														<td class="dataCellText" width="9%">
															<bean:write name="searchResultObject" property="date" />
															&nbsp;
														</td>
														<td class="dataCellText" width="9%">
															<bean:write name="searchResultObject" property="labTestId" />
															&nbsp;
														</td>
														<td class="dataCellText" width="9%">
															<bean:write name="searchResultObject" property="textResult" />
															&nbsp;
														</td>
														<logic:equal name="searchResultObject" property="isAdverseEvent" value="<%=DisplayConstants.YES%>">
															<td class="dataCellText" width="9%" bgcolor="#ffiooo">
																<bean:write name="searchResultObject" property="numericResult" />
																&nbsp;
															</td>
														</logic:equal>
														<logic:notEqual name="searchResultObject" property="isAdverseEvent" value="<%=DisplayConstants.YES%>">
															<td class="dataCellText" width="9%">
																<bean:write name="searchResultObject" property="numericResult" />
																&nbsp;
															</td>
														</logic:notEqual>
														<td class="dataCellText" width="9%">
															<bean:write name="searchResultObject" property="unitOfMeasure" />
															&nbsp;
														</td>
														<td class="dataCellText" width="9%">
															<bean:write name="searchResultObject" property="lowRange" />
															&nbsp;
														</td>
														<td class="dataCellText" width="9%">
															<bean:write name="searchResultObject" property="highRange" />
															&nbsp;
														</td>
														<!--
														<logic:equal name="searchResultObject" property="adverseEventReported" value="<%="0"%>">
															<td class="dataCellText" width="15%">
																<html:button style="actionButton" property="adverseEventReported" onclick="<%="submitAdverseEvent('"+searchResultObject.getRecordId()+"');"%>">Submit</html:button>
															</td>
														</logic:equal>
														<logic:notEqual name="searchResultObject" property="adverseEventReported" value="<%="0"%>">
															<td class="dataCellText" width="15%">
																<a class="sublink" href="javascript:setAndRedirect('<bean:write
																	name="searchResultObject" property="patientId" />','<bean:write
																	name="searchResultObject" property="studyId" />')">
																	Reported on
																	<bean:write name="searchResultObject" property="adverseEventReportedDate" />
																</a>
																&nbsp;
															</td>
														</logic:notEqual>
														-->
													</tr>
													<%}%>
												</logic:iterate>
											</table>
										</td>
									</tr>
									<tr>
										<td align="right" class="actionSection">
											<!-- action buttons begins -->
											<table cellpadding="4" cellspacing="0" border="0">
												<tr>
													<td>
														<html:cancel style="actionButton" property="org.apache.struts.taglib.html.CANCEL" value="Cancel">Back</html:cancel>
													</td>
													<td>
														<html:button style="actionButton" property="loadActivitiesToCTMS" onclick="setAndSubmit('loadActivitiesToCTMS');">Load Activities to CTMS</html:button>
													</td>
												</tr>
											</table>
											<!-- action buttons end -->
										</td>
									</tr>
								</logic:present>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</html:form>
</table>

<!-- laf box 2nd half -->
	        </div>  <!-- content -->
        </div><!-- interior -->
    </div></div></div></div></div></div></div></div>
    <!-- end inner border -->
	</div>
</div>
<!-- laf box 2nd half -->










