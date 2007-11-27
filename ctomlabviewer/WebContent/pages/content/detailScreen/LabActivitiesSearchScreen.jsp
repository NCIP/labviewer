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
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.forms.*"%>
<!-- laf box 1st half -->
<div class="box">
	<div class="pane" align=center>
	    <!-- header -->
	    <div class="header"><div class="background-L"><div class="background-R">
	      <h2>Enter The Lab Activity Search Criteria</h2>
	    </div></div></div>
	    <!-- end header -->
	    <!-- inner border -->
	    <div class="border-T"><div class="border-L"><div class="border-R"><div class="border-B"><div class="border-TL"><div class="border-TR"><div class="border-BL"><div class="border-BR">
	        <div class="interior"> <!-- interior -->
	            <div class="content">
<!-- laf box 1st half -->
<div>
	<table summary="" cellpadding="0" cellspacing="0" border="0"
		width="90%" height="50%">
		<tr>
			<td>
				<font size="2" face="verdana">
					Search for an existing Lab Activity by entering the Study Id,
					Patient Id, Begin Date, and End Date.
				</font>
			</td>
		</tr>
		<tr>
		<td>
		 <html:errors />
		</td>
		</tr> 
		<tr>
		<td valign="top">
				<table cellpadding="0" cellspacing="0" border="0" width="100%" height="90%">
					<tr>
						<td>
							<table summary="" cellpadding="0" cellspacing="0" border="0"
								width="100%" height="100%">
								<tr>
									<td>
										<html:form action="search.do" method="post">
											<table cellpadding="0" cellspacing="0" border="0"
												width="100%" height="100%">
												<tr>
													<td align=right>
													<font size="2" face="verdana">
													<b>
														* Study Identifier
													</b>
													</td>
													<td width=10>
													</td>
													<td>
														<html:text property="studyId"/>
													</td>
												</tr>
												<tr>
													<td align=right>
													<font size="2" face="verdana">
													<b>
														* Patient Identifier
													</b>
												    </font>
													</td>
													<td width=10>
													</td>
													<td>
														<html:text property="patientId" />
													</td>
												</tr>
												<tr>
													<td align=right>
													<font size="2" face="verdana">
													<b>
														* Begin Date (MM/DD/YYYY)
													</b>
												    </font>
													</td>
													<td width=10>
													</td>
													<td>
														<html:text property="beginDate" />
													</td>
												</tr>
												<tr>
													<td align=right>
													<font size="2" face="verdana">
													<b>
														* End Date (MM/DD/YYYY)
													</b>
												    </font>
													</td>
													<td width=10>
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
<!-- laf box 2nd half -->
	        </div>  <!-- content -->
        </div><!-- interior -->
    </div></div></div></div></div></div></div></div>
    <!-- end inner border -->
	</div>
</div>
<!-- laf box 2nd half -->
