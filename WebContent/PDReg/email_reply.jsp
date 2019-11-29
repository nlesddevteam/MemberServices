<%@ page language="java"
          session="true"
          isThreadSafe="false"%>
<html>
  <head>
    <title>Email Response</title>   
  </head>
  <body>
  <div class="panel panel-success">
  <div class="panel-heading"><b>Email Status Response</b></div>
  <div class="panel-body">  
   <% if(msg != null) { %>
  <div align="center" class="alert alert-info"><%=request.getAttribute("msg")%></div>
  <%} %>
  <% if(msgOK != null) { %>
  <div align="center" class="alert alert-success" id="successEmail"><%=request.getAttribute("msgOK")%></div>
  <%} %>
  <% if(msgERR != null) { %>
  <div align="center" class="alert alert-danger" id="errorEmail"><%=request.getAttribute("msgERR")%></div>
  <%} %>
  <br/>
  <div align="center"><a class="no-print btn btn-xs btn-primary" href="viewDistrictCalendar.html" title="Back to Calendar">Back to Calendar</a></div>  
  </div>
  </div>
           
</body>
</html>
             