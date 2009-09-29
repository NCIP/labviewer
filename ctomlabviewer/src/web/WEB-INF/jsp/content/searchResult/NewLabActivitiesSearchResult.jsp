<%@ page isELIgnored="false"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template"
	prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>

<%@ page import="gov.nih.nci.caxchange.ctom.viewer.constants.*"%>
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.forms.*"%>

<script type="text/javascript" src="scripts/prototype.js"></script>
<script type="text/javascript" src="scripts/ccts-hotlinks.js"></script>
<script type="text/javascript">
    CCTS.appShortName = 'labviewer'
</script>

<script>

function setUp()
{
 
   var numRows2 = document.getElementsByName("recordIds").length;
   if(numRows2 >0)
     SelectAllOn =false;
    else
      SelectAllOn = true;
}

function SelectAll()
  {
    var numRows2 = document.getElementsByName("recordIds").length;
    
    if(SelectAllOn == false)
    {
      for(k=0; k<numRows2; k++)
      {
        formObj= eval(document.getElementsByName("recordIds")[k]);
        
        if(formObj && formObj.checked == false)
        {
          formObj.checked=true;
        }
      }
      SelectAllOn = true;
      if (document.LabActivitiesSearchResultForm.CheckGif != null){
           document.LabActivitiesSearchResultForm.CheckGif.alt = "Unselect All";
      }
    }
    else
    { 
      for(m=0; m<numRows2; m++)
      {
        formObj= eval(document.getElementsByName("recordIds")[m]);
        if(formObj && formObj.checked==true)
        {
          formObj.checked=false;
        }
      }
      SelectAllOn = false;
      if (document.LabActivitiesSearchResultForm.CheckGif != null)
          document.LabActivitiesSearchResultForm.CheckGif.alt = "Select All";
       }
      
  }
  
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
	  		var radioLen = document.getElementsByName("recordIds").length;
			
	  		if(radioLen == undefined)
	  		{
	 			if (document.getElementsByName("recordIds").checked) 			
				{	document.LabActivitiesSearchResultForm.recordId.value = document.getElementsByName("recordIds").value;
					ischecked = true;
				}
	  		}
			for (var i = 0; i <radioLen; i++)
			{   
				if (document.getElementsByName("recordIds")[i].checked) 
				{	document.LabActivitiesSearchResultForm.recordId.value = document.LabActivitiesSearchResultForm.recordId.value + "," + document.getElementsByName("recordIds")[i].value;
					ischecked = true;
				}
			}
			if (target=="saveToCSV")
  			{
  		 		document.LabActivitiesSearchResultForm.action="saveToCSV.do";  
	  			document.LabActivitiesSearchResultForm.operation.value="execute";
			
  			}   
			else if(target=="loadActivitiesToCTMS"){
				document.LabActivitiesSearchResultForm.action="loadToCTMS.do";  
	  			document.LabActivitiesSearchResultForm.operation.value="execute";
	  			}
	  		else{
	  			document.LabActivitiesSearchResultForm.action="loadTocaAERS.do";  
	  			document.LabActivitiesSearchResultForm.operation.value="execute";
	  		}	
		}
		if(ischecked) {
		   document.LabActivitiesSearchResultForm.target="_self";
     	   document.LabActivitiesSearchResultForm.submit();
     	   }
		else
			alert("Atleast one check box should be checked.");
 	}
 </script>


<body onload="javaScript:setUp();">


	<!-- laf box 1st half -->
	<div style="margin-top: 0; padding-top: 0;">
		<!-- laf box 1st half -->
		<div class="box">
			<div align="center">
				<!-- header -->
				<div class="header">
					<div class="background-L">
						<div class="background-R">
							<h2>
								Lab Activities - Search Results [${SEARCH_RESULT_COUNT}
								record(s) found]
							</h2>
						</div>
					</div>
				</div>
				<!-- inner border -->
				<div class="border-T">
					<div class="border-L">
						<div class="border-R">
							<div class="border-B">
								<div class="border-TL">
									<div class="border-TR">
										<div class="border-BL">
											<div class="border-BR">
												<div class="interior">
													<!-- interior -->
													<div class="content">
														<!-- laf box 1st half -->

														<table summary="" cellpadding="0" cellspacing="0"
															border="0" class="contentPage" width="95%" height="100%">
															<html:form styleId="LabActivitiesSearchResultForm"
																action="<%="/LabActivitiesDB"%>" target="_self">
																<html:hidden property="operation" value="read" />
																<bean:define name="<%=DisplayConstants.LOGIN_OBJECT%>"
																	id="loginObject" type="LoginForm" />
																<html:hidden property="gridProxy"
																	value="<%=loginObject.getGridProxy()%>" />
																<html:hidden property="mrn" value="" />
																<html:hidden property="nciIdentifier" value="" />
																<html:hidden property="recordId" value="" />
																<tr>
																	<td class="infoMessage" colspan="3">
																		<html:messages id="message" message="true">
																			<bean:write name="message" />
																		</html:messages>
																	</td>
																</tr>
																<tr>
																	<td colspan="3" align="center">
																		<%
																			//Get the attributes from session.
																			String caAERSurl = (String) session.getAttribute("BaseURLcaAERS");
																			String C3Durl = "";//(String)session.getAttribute("BaseURLC3D");
																			String gridId = (String) session.getAttribute("studySubjectGridId");
																			String hotLinkType = (String) session.getAttribute("hotLinkType");
																		%>
																		<a
																			href="<%=caAERSurl%>/pages/ae/list?studySubjectGridId=<%=gridId%>"
																			target="<%=hotLinkType%>">View this patient in
																			caAERS</a>
																		<!-- &nbsp;&nbsp;&nbsp;||&nbsp;&nbsp;&nbsp;<a href="<%=C3Durl%>/studySubject?studySubjectGridId=<%=gridId%>" target="c3d">View these labs in C3D</a> -->
																	</td>
																</tr>
																<tr>
																	<td valign="top">
																		<table cellpadding="0" cellspacing="0" border="0"
																			width="100%" class="contentBegins">
																			<tr>
																				<td>
																					<table summary="" cellpadding="0" cellspacing="0"
																						border="0" width="100%">
																						<logic:present
																							name="<%=DisplayConstants.SEARCH_RESULT%>">
																							<bean:define
																								name="<%=DisplayConstants.SEARCH_RESULT%>"
																								property="searchResultObjects"
																								id="searchResultObjects" />
																							<bean:define id="oddRow" value="true" />
																							<tr>
																								<td>
																									<display:table
																										name="${SEARCH_RESULT.searchResultObjects}"
																										sort="list" cellspacing="0" cellpadding="3"
																										pagesize="15" id="labtable" export="true"
																										size="totalLabs" defaultsort="5"
																										defaultorder="ascending" requestURI=""
																										decorator="gov.nih.nci.caxchange.ctom.viewer.util.LabSearchDecorator"
																										style="border: 1px solid black; margin-top: 1em; margin-bottom: 1em; width: 100%;"
																										class="dataTable">
																										<display:setProperty
																											name="paging.banner.placement" value="bottom" />
																										<display:setProperty
																											name="paging.banner.item_name"
																											value="Lab Results" />
																										<display:setProperty
																											name="paging.banner.items_name"
																											value="Lab Results" />
																										<display:setProperty name="css.th.sorted"
																											value="dataTableHeader" />
																										<display:setProperty name="css.th.even"
																											value="dataRowLight" />
																										<display:setProperty name="css.th.odd"
																											value="dataRowDark" />
																										<display:setProperty
																											name="export.excel.filename"
																											value="LabResults.xls" />
																										<display:setProperty
																											name="export.csv.filename"
																											value="LabResults.csv" />
																										<display:setProperty
																											name="export.xml.filename"
																											value="LabResults.xml" />
																										<display:setProperty
																											name="export.excel.include_header"
																											value="true" />
																										<display:setProperty
																											name="export.csv.include_header" value="true" />
																										<display:setProperty
																											name="export.xml.include_header" value="true" />
																										<display:column class="dataCellText"
																											sortable="true"
																											title="<img src='images/CheckBox.gif' alt='Select All' id='CheckGif' onclick='SelectAll();return false'/>'"
																											maxLength="90">
																											<html:multibox style="formFieldSized"
																												property="recordIds"
																												value="${labtable.recordId}"></html:multibox>
																										</display:column>
																										<display:column property="patientId"
																											class="dataCellText" sortable="true"
																											title="Patient Id" maxLength="90" />
																										<display:column property="siteId"
																											class="dataCellText" sortable="true"
																											title="Site" maxLength="200" />
																										<display:column property="date"
																											class="dataCellText" sortable="true"
																											title="Date / Time" />
																										<display:column property="labTestId"
																											class="dataCellText" sortable="true"
																											title="Lab Test" />
																										<display:column property="textResult"
																											class="dataCellText" sortable="true"
																											title="Text Result" />
																										<logic:equal name="labtable"
																											property="isAdverseEvent" value="YES">
																											<display:column property="numericResult"
																												class="dataCellTextRed" sortable="true"
																												title="Numeric Result" />
																										</logic:equal>
																										<logic:notEqual name="labtable"
																											property="isAdverseEvent" value="YES">
																											<display:column property="numericResult"
																												class="dataCellText" sortable="true"
																												title="Numeric Result" />
																										</logic:notEqual>
																										<display:column property="unitOfMeasure"
																											class="dataCellText" sortable="true"
																											title="Unit Of Measure" />
																										<display:column property="lowRange"
																											class="dataCellText" sortable="true"
																											title="Lower Limit" />
																										<display:column property="highRange"
																											class="dataCellText" sortable="true"
																											title="Upper Limit" />
																										<display:column
																												property="labsToCDMS"
																												class="dataCellText" sortable="true"
																												title="Sent to CDMS" />
																										<display:column
																												property="labsToAE"
																												class="dataCellText" sortable="true"
																												title="Sent to caAERS" />
																										
																									</display:table>
																								</td>
																							</tr>
																							<tr>
																								<td align="right" class="actionSection">
																									<!-- action buttons begins -->
																									<table cellpadding="4" cellspacing="0"
																										border="0">
																										<tr>
																											<td>
																												<html:cancel style="actionButton"
																													property="org.apache.struts.taglib.html.CANCEL"
																													value="Cancel">Back</html:cancel>
																											</td>
																											<td>
																												<html:button style="actionButton"
																													property="loadActivitiesTocaAERS"
																													onclick="setAndSubmit('loadActivitiesTocaAERS');">Load Labs to caAERS</html:button>
																											</td>
																											<td>
																												<html:button style="actionButton"
																													property="loadActivitiesToCTMS"
																													onclick="setAndSubmit('loadActivitiesToCTMS');">Load Labs to CDMS</html:button>
																											</td>
																											<!-- <td>
													   <html:button style="actionButton" property="saveToCSV" onclick="setAndSubmit('saveToCSV');">Save Labs to CSV</html:button>
													</td> -->
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
													</div>
													<!-- content -->
												</div>
												<!-- interior -->
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- end inner border -->
			</div>
		</div>
	</div>
	<!-- laf box 2nd half -->
<head>
	<script type="text/javascript">
var t = new ScrollableTable(document.getElementById('myScrollTable'), 90);
</script>
</head>








