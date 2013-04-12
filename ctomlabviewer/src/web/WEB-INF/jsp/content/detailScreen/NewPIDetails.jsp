<%--
Copyright ScenPro, Inc and SemanticBits, LLC

Distributed under the OSI-approved BSD 3-Clause License.
See http://ncip.github.com/labviewer/LICENSE.txt for details.
--%>
<%@ page isELIgnored="false"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

		<!-- laf box 1st half -->
		<div class="box">
			<div align=center>
				<!-- header -->
				<div class="header">
					<div class="background-L">
						<div class="background-R">
							<h2>
								Principal Investigator(s) details
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
											<div class="border-BR">
												<div class="interior">
													<!-- interior -->
													<div class="content">
														<!-- laf box 1st half -->
													<table summary="" cellpadding="0" cellspacing="0"
																border="0" width="95%" height="100%">
																<tr>
																	<td valign="top">
																		<table cellpadding="0" cellspacing="0" border="0"
																			width="100%" height="100%" class="contentBegins">
																			<tr>
																				<td>	
	<display:table name="principalInvestigators" sort="list" cellspacing="0" cellpadding="3"
			               pagesize="25" uid="hsctable" export="true" 
			               defaultsort="1"
                        requestURI=""                    
	    style="border: 1px solid black; margin-top: 1em; margin-bottom: 1em; width: 100%;" class="dataTable">    
           <display:setProperty name="paging.banner.placement" value="bottom" />    
           <display:setProperty name="paging.banner.item_name" value="Principal Investigators" />               
           <display:setProperty name="paging.banner.items_name" value="Principal Investigators" />    
           <display:setProperty name="css.th.sorted" value="dataTableHeader" />
           <display:setProperty name="css.th.even" value="dataRowLight" />
           <display:setProperty name="css.th.odd" value="dataRowDark" />
           <display:setProperty name="export.excel.filename" value="PIs.xls"/>    
           <display:setProperty name="export.csv.filename" value="PIs.csv"/>               
           <display:setProperty name="export.xml.filename" value="PIs.xml"/>                          
           <display:setProperty name="export.excel.include_header" value="true"/>                                     
           <display:setProperty name="export.csv.include_header" value="true"/>                                     
           <display:setProperty name="export.xml.include_header" value="true"/>            
           <display:column property="name"  class="dataCellText" sortable="true" title="Name" maxLength="90"/>
           <display:column property="address"  class="dataCellText" sortable="true" title="Address" maxLength="200"/>
           <display:column property="phone" class="dataCellText" sortable="true" title="Phone" />        
           <display:column property="email"  class="dataCellText" sortable="true" title="Email"  />
           <display:column property="updatedDate"  class="dataCellText" style="white-space: nowrap;" sortable="true" format="{0, date, dd-MMM-yyyy}" title="Updated Date" />                                    			  
	</display:table>	
						</td>
			</tr>
			</table>
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
				</div>
				<!-- end inner border -->
			</div>
		</div>
		<!-- laf box 2nd half -->
