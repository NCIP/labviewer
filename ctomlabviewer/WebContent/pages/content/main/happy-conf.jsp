<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%> 

<div id="main">
   <div class="pane">
     <div class="box ">
		<!-- header -->
		<div class="header"><div class="background-L"><div class="background-R">
			<h2>CTODS Labviewer : 1.2</h2>
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
        	<td>caExchange URL</td>	<td><%=caXchangeurl%></td>
            </tr>
            <tr>
            <td>HOTLINK_TYPE</td><td><%=hotLinkType %></td>
            	</tr>           	
       <tr><th colspan="2"><h3>caExchange handshake</h3> </th></tr>
      <tr><td>Sample message to caAERS</td>
                	<td>fail </td></tr>
        <tr><td>Error</td><td>Connection time out .... </td></tr>
            	<tr><td>Sample message to CDMS</td><td>Pass </td></tr>
            	
           </table>
 	<!-- laf box 2nd half -->
	        </div>  <!-- content -->
        </div><!-- interior -->
    </div></div></div></div></div></div></div></div>
    <!-- end inner border -->
	</div>
</div>
<!-- laf box 2nd half -->


