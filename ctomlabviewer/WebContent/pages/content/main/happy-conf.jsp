<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%> 
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.smokeTests.TestPing" %>
<div id="main">
   <div class="pane">
     <div class="box ">
		<!-- header -->
		<div class="header"><div class="background-L"><div class="background-R">
			<h2>CTODS LabViewer : 1.5</h2>
		</div>
	 </div>
   </div> 
   <% 
		//Get the attributes from session.
		  String caAERSurl=(String)session.getAttribute("BaseURLcaAERS");
		  String caXchangeurl =(String)session.getAttribute("caXchangeURL");
		  String hotLinkType= (String)session.getAttribute("hotLinkType");
		  
		 %>
          <div class="border-T"><div class="border-L"><div class="border-R"><div class="border-B"><div class="border-TL"><div class="border-TR"><div class="border-BL"><div class="border-BR">
	        <div class="interior"> <!-- interior -->
	            <div class="content">
	           		 
   	    <table cellpadding="3px"> 
        <tr><th colspan="2"><h3>Configuration</h3> </th></tr>
          <tr>
           <td> caAERS URL </td><td> <%=caAERSurl%> </td>
           </tr>
           <tr>
        	<td>caXchange URL</td>	<td><%=caXchangeurl%></td>
            </tr>
            <tr>
            <td>HOTLINK_TYPE</td><td><%=hotLinkType %></td>
            	</tr>           	
       <tr><th colspan="2"><h3>Invoking Happy Grid Service</h3> </th></tr>
      <tr><td> Status</td><td>
      <%
       TestPing test = new TestPing();
        String status =test.test();
       %>
       <%=status %>
      </td></tr>
           </table>
 	<!-- laf box 2nd half -->
	        </div>  <!-- content -->
        </div><!-- interior -->
    </div></div></div></div></div></div></div></div>
    <!-- end inner border -->
	</div>
</div>
<!-- laf box 2nd half -->


