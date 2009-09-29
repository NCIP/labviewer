<%@ page isELIgnored="false"%>
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

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%> 
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.smokeTests.TestPing" %>
<%@ page import="org.globus.gsi.GlobusCredential"%>

<!-- laf box 1st half -->
<div style="margin-top: 0; padding-top: 0;">
 <!-- laf box 1st half -->
  <div class="box">
	<div  align="center">
	    <!-- header -->
	    <div class="header"><div class="background-L"><div class="background-R">
	      <h2>Test Configurations</h2>
	    </div></div></div>
	    <!-- end header -->
	    <!-- inner border -->
	    <div class="border-T"><div class="border-L"><div class="border-R"><div class="border-B"><div class="border-TL"><div class="border-TR"><div class="border-BL"><div class="border-BR">
	        <div class="interior"> <!-- interior -->
	            <div class="content">
     <!-- laf box 1st half -->      
 	<table summary="" cellpadding="0" cellspacing="0" border="0" width="95%" height="100%" >
 	<tr>
 <% 
		//Get the attributes from session.
		  String caAERSurl=(String)session.getAttribute("BaseURLcaAERS");
		  String caXchangeurl =(String)session.getAttribute("caXchangeURL");
		  String hotLinkType= (String)session.getAttribute("hotLinkType");
		  GlobusCredential gc = (GlobusCredential)session.getAttribute("CAGRID_SSO_GRID_CREDENTIAL");
		  String status ="";
		 %>
        <table cellpadding="3px"> 
          <tr><th colspan="2"><h3>Configuration</h3> </th></tr>
          <tr>
           <td> <font size="2" face="arial"> caAERS URL</font> </td><td><font size="2" face="arial"> <%=caAERSurl%></font> </td>
          </tr>
          <tr>
        	<td> <font size="2" face="arial">caXchange URL</font></td>	<td> <font size="2" face="arial"><%=caXchangeurl%></font></td>
          </tr>
          <tr>
            <td><font size="2" face="arial">HOTLINK_TYPE</font></td><td><font size="2" face="arial"><%=hotLinkType %></font></td>
         </tr>           	
       <tr><th colspan="2"><h3>Invoking Happy Grid Service</h3> </th></tr>
      <tr><td> <font size="2" face="arial">Status</font></td><td><font size="2" face="arial">
      <%
       if(gc!=null){
        TestPing test = new TestPing();
        status =test.test(gc);
       }else{
         status ="No grid credentials available to test the Happy service";
       }
       %>
       <%=status %>
    </font>  </td></tr>
           </table>
 
<!-- laf box 2nd half -->
	        </div>  <!-- content -->
        </div><!-- interior -->
    </div></div></div></div></div></div></div></div>
    <!-- end inner border -->
	</div>
</div>
<!-- laf box 2nd half -->    
</div>

