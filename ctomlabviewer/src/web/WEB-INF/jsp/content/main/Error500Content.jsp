<%--
Copyright ScenPro, Inc and SemanticBits, LLC

Distributed under the OSI-approved BSD 3-Clause License.
See http://ncip.github.com/labviewer/LICENSE.txt for details.
--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%> 

<%@ page import="gov.nih.nci.caxchange.ctom.viewer.constants.*"%>

<!-- laf box 1st half -->
<div class="box" valign="top">
	<div class="pane" align=center>
		<!-- header -->
		<div class="header">
			<div class="background-L">
				<div class="background-R">
					<h2>
						Internal Error
					</h2>
				</div>
			</div>
		</div>
		<!-- end header -->
		<!-- inner border -->
		<div class="border-T">
			<div class="border-L">
				<div class="border-R">
					<div class="border-B">
						<div class="border-TL">
							<div class="border-TR">
								<div class="border-BL">
									
										<div class="interior">
											<!-- interior -->
											<div class="content">
												<!-- laf box 1st half -->
												<table  cellpadding="0" cellspacing="0" border="0">
																<tr>
																	<td align="center">
																		<h2>
																		</h2>
																	</td>
																</tr>
																<tr>
																	<td>
																		<font size="2" face="verdana">
																			<p>
																				Please notify support that you have encountered an internal error.
																				<br>
																				Note the date and time of the error.
																			</p>
																			<p>
																				<logic:present name="<%=DisplayConstants.ADMIN_USER%>">
				                                                                    <html:link action="AdminHome.do">Click here to return to Home Page</html:link>
			                                                                    </logic:present>
								
			                                                                    <logic:notPresent name="<%=DisplayConstants.ADMIN_USER%>">
				                                                                    <html:link action="Home.do">Click here to return to Home Page</html:link>
			                                                                    </logic:notPresent>
																			</p>
																		</font>
																	</td>
																</tr>
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
		<!-- end inner border -->
	</div>
</div>
<!-- laf box 2nd half -->

