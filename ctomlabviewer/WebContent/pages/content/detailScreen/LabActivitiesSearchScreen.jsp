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
	
<div>
	<table summary="" cellpadding="0" cellspacing="0" border="0"
		class="contentPage" width="100%" height="100%">
		<tr>
			<td>
				<h4>
				<br>
					Search for an existing Lab Activity by entering the Study Id,
					Patient Id, Begin Date, and End Date.
				<br>	
				</h4>
			</td>
		</tr>
		<tr>
		<td>
		 <html:errors />
		</td>
		</tr> 
		<tr>
			<td valign="top">
				<table cellpadding="0" cellspacing="0" border="0" width="100%"
					class="contentBegins" height="100%">
					<tr>
						<td>
							<table summary="" cellpadding="0" cellspacing="0" border="0"
								width="100%" height="100%">
								<tr>
									<td class="dataTablePrimaryLabel" height="20">
										<b>ENTER THE LAB ACTIVITY SEARCH CRITERIA</b>
									</td>
								</tr>
								<tr>
									<td>
										<html:form action="search.do" method="post">
											<table cellpadding="0" cellspacing="0" border="0"
												width="100%" height="100%">
												<tr>
													<td class="formRequiredLabel">
														* Study Identifier
													</td>
													<td>
														<html:text property="studyId"/>
													</td>
												
												</tr>
												<tr>
													<td class="formRequiredLabel">
														* Patient Identifier
													</td>
													<td>
														<html:text property="patientId" />
													</td>
													
												</tr>
												<tr>
													<td class="formRequiredLabel">
														* Begin Date (MM/DD/YYYY)
													</td>
													<td>
														<html:text property="beginDate" />
													</td>
													
												</tr>
												
												<tr>
													<td class="formRequiredLabel">
														* End Date (MM/DD/YYYY)
													</td>
													<td>
														<html:text property="endDate" />
													</td>
													</tr>
													<tr>
													<td align="right" class="actionSection">
														<!-- action buttons begins -->
														<table cellpadding="4" cellspacing="0" border="0">
															<tr>

																<td>
																	<html:reset style="actionButton">Reset</html:reset>
																</td>
																<td>
																	<html:submit style="actionButton">Search</html:submit>
																</td>
															</tr>
														</table>
														</td></tr>
														<tr>
														<td align="left">
														<h5> * indicates the fields required </h5>
														</td></tr>
													
											</table>
											</html:form>
											
									</td>
								</tr>
							</table>
							
						</td>
					</tr>
				</table>
</div>