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
      <td>
        <table width="701" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td>&nbsp;</td>
       </tr>
        <tr>
           <td>&nbsp;</td>
       </tr>
       <tr>
          <td align="center">
 
            <P>
               <bean:write name='<%=DisplayConstants.ERROR_DETAILS %>' />
            </P>
          </td>
       </tr>
  		<tr>
  		
  		<!-- new separate links depending on admin or super admin -->
							
			<logic:present name="<%=DisplayConstants.ADMIN_USER%>">
				<td align="center"><html:link action="AdminHome.do">Click here to go back to Home Page</html:link></td>
			</logic:present>
								
			<logic:notPresent name="<%=DisplayConstants.ADMIN_USER%>">
				<td align="center"><html:link action="Home.do">Click here to go back to Home Page</html:link></td>
			</logic:notPresent>		
								
							<!-- end home links -->		   
  		
  		
  		<!--
          <td align="center">
           	 <html:link action="Home.do" >
					Click here to go back to Home Page
		  	 </html:link>
		   </td>
		-->

       </tr>

  </table>
