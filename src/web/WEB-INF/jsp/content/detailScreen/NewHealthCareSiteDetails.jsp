<%@ page isELIgnored="false"%>

<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

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
	
<html:form action="loadHealthCareSite.do">
<logic:present name="HealthCareSiteForm" property="hcsList">
<div class="box">
				<div align="center">
					<!-- header -->
					<div class="header">
						<div class="background-L">
							<div class="background-R">
								<h2>
									Search Results
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
	<display:table name="${sessionScope.HealthCareSiteForm.hcsList}" sort="list" cellspacing="0" cellpadding="3"
			               pagesize="25" uid="hsctable" export="true" 
			               size="totalHCS" 
			               defaultsort="1" defaultorder="descending"
                        requestURI=""
                        decorator="gov.nih.nci.caxchange.ctom.viewer.util.HCSDecorator"                        
	    style="border: 1px solid black; margin-top: 1em; margin-bottom: 1em; width: 100%;" class="dataTable">    
           <display:setProperty name="paging.banner.placement" value="bottom" />    
           <display:setProperty name="paging.banner.item_name" value="HealthCareSites" />               
           <display:setProperty name="paging.banner.items_name" value="HealthCareSites" />    
           <display:setProperty name="css.th.sorted" value="dataTableHeader" />
           <display:setProperty name="css.th.even" value="dataRowLight" />
           <display:setProperty name="css.th.odd" value="dataRowDark" />
           <display:setProperty name="export.excel.filename" value="HealthCareSites.xls"/>    
           <display:setProperty name="export.csv.filename" value="HealthCareSites.csv"/>               
           <display:setProperty name="export.xml.filename" value="HealthCareSites.xml"/>                          
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
			
</logic:present>				
</html:form>
