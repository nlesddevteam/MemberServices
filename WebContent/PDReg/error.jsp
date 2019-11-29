<%@ page language="java" 
isErrorPage="true" 
import="java.lang.*" 
isThreadSafe="false"%>

<%
  Exception e = null;
%>
<%
  if(request.getAttribute("err") != null)
  {
    e = (Exception) request.getAttribute("err");
  }
  else
  {
    e =  (Exception) exception;
  }
%>
<html>
  <head>
  	<title>ERROR PD Calendar</title>

<style>
.tableTitle {font-weight:bold;width:20%;color:White;text-transform:uppercase;}
.tableResult {font-weight:normal;width:80%;background-color:#ffffff;}
.tableTitleL {font-weight:bold;width:20%;background-color:#006400;color:White;text-transform:uppercase;}
.tableResultL {font-weight:normal;width:30%;background-color:#ffffff;}
.tableTitleR {font-weight:bold;width:20%;background-color:#006400;color:White;text-transform:uppercase;}
.tableResultR {font-weight:normal;width:30%;background-color:#ffffff;}
input {border:1px solid silver;}
</style>
    
    <script type="text/javascript">
    $("#loadingSpinner").css("display","none");

    </script>
  </head>
<body>
 <div class="container-fluid no-print" data-spy="affix" data-offset-top="0" style="position:fixed;width:100%;height:30px;background-color:#FF0000;color:White;text-align:center;font-weight:bold;padding:5px;">                      
  CALENDAR ERROR
  </div>
<div class="registerEventDisplay" style="padding-top:25px;font-size:11px;">
   
   <div align="center" class="no-print"><a href="viewDistrictCalendar.html"><img class="topLogoImg" src="includes/img/pdcalheader.png" border=0 style="padding-bottom:10px;"/></a></div>
  
   <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-danger">   
	               	<div class="panel-heading"><b>ERROR: PD Calendar</b></div>
      			 	<div class="panel-body">
					  <b>The PD Calendar has encountered an error:</b> <br/><br/>  
					  <span style="color:Red;"><%=e!=null?e:"UNKNOWN ERROR"%>.</span>  
					  <br/><br/>
					  <% if(request.getAttribute("msgERR") != null) { %>
         				 <div class="alert alert-danger" style="text-align:center;"><%= request.getAttribute("msgERR") %></div>
       					 <% } %>
       					 <% if(request.getAttribute("msg") != null) { %>
         				 <div class="alert alert-danger" style="text-align:center;"><%= request.getAttribute("msg") %></div>
       					 <% } %>
       					<% if(request.getAttribute("msgOK") != null) { %>
         				 <div class="alert alert-success" style="text-align:center;"><%= request.getAttribute("msgOK") %>.</div>
       					 <% } %> 
					  
					  
					  <div align="center"><a href="/MemberServices/" class="btn btn-danger btn-xs">Exit to Member Services</a></div>  
					</div>
        			</div> 
         
   </div>
</div>
</body>
</html>
