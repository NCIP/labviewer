<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%> 

<div id="main">
   <div class="pane">
     <div class="box ">
		<!-- header -->
		<div class="header"><div class="background-L"><div class="background-R">
			<h2>cTODS Labviewer : 1.2</h2>
		</div>
	 </div>
   </div> 
   <% 
		//Get the attributes from session.
		  String caAERSurl=(String)session.getAttribute("BaseURLcaAERS");
		  String caXchangeurl =(String)session.getAttribute("caXchangeURL");
		  String hotLinkType= (String)session.getAttribute("hotLinkType");
		  
		 %>
   <div class="division ">   
      <div class="content">  
	      <div class="header"><h3>Configuration</h3> </div>
      
        	<div class="leftpanel">
        		<div class="row">
                	<div class="label">caAERS URL</div>
                	<div class="value"><%=caAERSurl%> </div>
            	</div>
           		
            	<div class="row">
                	<div class="label">caExchange URL</div>
                	<div class="value"><%=caXchangeurl%></div>
            	</div> 
            	<div class="row">
                	<div class="label">HOTLINK_TYPE</div>
                	<div class="value"><%=hotLinkType %></div>
            	</div>           	
            </div>
	  </div>
  </div>
  <div class="division ">   
      <div class="content">  
	      <div class="header"><h3>caExchange handshake</h3> </div>
      
        	<div class="leftpanel">
        		<div class="row">
                	<div class="label">Sample message to caAERS</div>
                	<div class="fail">Fail </div>
            	</div>
        		<div class="row">
                	<div class="label">Error</div>
                	<div class="error">Connection time out .... </div>
            	</div>				
           		<div class="row">
                	<div class="label">Sample message to CDMS</div>
                	<div class="pass">Pass </div>
            	</div>           	
            </div>
	  </div>
  </div>
  <div class="content buttons autoclear"/>
  
  
</div>




