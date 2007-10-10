<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%@ page import="gov.nih.nci.caxchange.ctom.viewer.constants.*"%>
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.forms.*"%>


<br>
  <table width="700" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr valign="middle">

      <TD>
      <h1 align="center"></h1>
      </td>
    </tr>

    <tr>
      <td>&nbsp;</td>
     </tr>
      <tr>
           <td>&nbsp;</td>
       </tr>
       <tr>
          <td align="center">
            <b>ERROR - The server has encountered a severe error.&nbsp;&nbsp;Please try again.</b>
            <BR>
          </td>
        </tr>
        <tr>
          <td align="center">
      	    
           	 <html:link action="ViewErrorDetails" >
					Click here to view Error Details
		  	 </html:link>
		   </td>
        </tr>
  		<tr>
  		
  		<!--
          <td align="center">
           	 <html:link action="Home.do" >
					Click here to go back to Home Page
		  	 </html:link>
		   </td>
		   -->
		   
	<!-- new separate links depending on admin or super admin -->
							
			<logic:present name="<%=DisplayConstants.ADMIN_USER%>">
				<td align="center"><html:link action="AdminHome.do">Click here to go back to Home Page</html:link></td>
			</logic:present>
								
			<logic:notPresent name="<%=DisplayConstants.ADMIN_USER%>">
				<td align="center"><html:link action="Home.do">Click here to go back to Home Page</html:link></td>
			</logic:notPresent>		
								
							<!-- end home links -->		   
		   
		   
       </tr>


  </table>
