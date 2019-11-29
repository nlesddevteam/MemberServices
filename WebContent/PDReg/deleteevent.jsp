<%@ page language="java"
         import="com.awsd.pdreg.*,com.awsd.security.*,java.text.*"
         isThreadSafe="false"%>

<%
    User usr = null;
    UserPermissions permissions = null;
    Event evt = null;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    String pageTitle;
    String bgcolor = "";
    String txtcolor = "";
    String regionName = "";
 %>   
    
 <%   
    
    
	 usr = (User) session.getAttribute("usr");
 	 if(usr != null)
  {
    permissions = usr.getUserPermissions();
    if(!(permissions.containsKey("CALENDAR-VIEW") 
          && permissions.containsKey("CALENDAR-SCHEDULE")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}

  evt = (Event) request.getAttribute("evt");
  
  //If event is deleted and this is a response back from class, ignore this.
  if(evt !=null) {
  
  if(evt.getEventSchoolZoneID() ==1) {
		 bgcolor ="rgba(191, 0, 0, 0.1)";
		 txtcolor ="rgba(191, 0, 0, 1);";
		 regionName ="AVALON REGION";
	 } else if (evt.getEventSchoolZoneID() == 2) {
		 bgcolor ="rgba(0, 191, 0, 0.1)";
		 txtcolor ="rgba(0, 191, 0, 1);";
		 regionName ="CENTRAL REGION";
	 } else if (evt.getEventSchoolZoneID() ==3) {
		 bgcolor ="rgba(255, 132, 0, 0.1)";
		 txtcolor ="rgba(255, 132, 0, 1);";
		 regionName ="WESTERN REGION";
	 } else if (evt.getEventSchoolZoneID() ==4) {
		 bgcolor ="rgba(127, 130, 255, 0.1)";
		 txtcolor ="rgba(127, 130, 255, 1);";
		 regionName ="LABRADOR REGION";
	 } else if (evt.getEventSchoolZoneID() ==5) {
		 bgcolor ="rgba(128, 0, 128, 0.1)";
		 txtcolor ="rgba(128, 0, 128, 1);";
		 regionName ="PROVINCIAL";
	 } else {
		 bgcolor ="#FFFFFF";
		 txtcolor ="#000000;";
		 regionName ="";
	 }}
 
%>
    
<html>
  <head>
    <title>PD Calendar</title>
    
    <script langauge="JavaScript">
    $("#loadingSpinner").css("display","none");  
    clicked = false;
    </script>
      <style>
  .tableTitle {font-weight:bold;width:20%;color:White;text-transform:uppercase;}
  .tableResult {font-weight:normal;width:80%;background-color:#ffffff;}
  .tableTitleL {font-weight:bold;width:20%;background-color:#006400;color:White;text-transform:uppercase;}
  .tableResultL {font-weight:normal;width:30%;background-color:#ffffff;}
  .tableTitleR {font-weight:bold;width:20%;background-color:#006400;color:White;text-transform:uppercase;}
  .tableResultR {font-weight:normal;width:30%;background-color:#ffffff;}
  input {border:1px solid silver;}
  
  
  @media print {
  
  table {font-size:10px;}
  
  }
  
  </style>
    
  </head>

  <body>
  <div class="container-fluid no-print" data-spy="affix" data-offset-top="0" style="position:fixed;width:100%;height:30px;background-color:#FF0000;color:White;text-align:center;font-weight:bold;padding:5px;">                      
  <span class="glyphicon glyphicon-info-sign"></span> DELETE EVENT <span style="color:Yellow;">&quot;<%=(evt==null)?"N/A":evt.getEventName()%>&quot;</span> CONFIRMATION <span class="glyphicon glyphicon-info-sign"></span>
</div>
<div class="registerEventDisplay" style="padding-top:25px;font-size:11px;">

 <div align="center" class="no-print"><a href="viewDistrictCalendar.html"><img class="topLogoImg" src="includes/img/pdcalheader.png" border=0 style="padding-bottom:10px;"/></a></div>
    
    <form action="deleteEvent.html" name="delevt" method="post">

  		
  		
  		<% if(request.getAttribute("msgok") != null) { %>
							<div class="alert alert-success" align="center"><%= request.getAttribute("msgok") %></div>
							<div align="center"><a class="btn btn-xs btn-primary" href="viewDistrictCalendar.html" title="Back to Calendar">Back to Calendar</a></div>
							
		<% } %>	
		
		<% if(request.getAttribute("msgerr") != null) { %>
							<div class="alert alert-danger" align="center"><%= request.getAttribute("msgerr") %></div>
							<div align="center"><a class="btn btn-xs btn-primary" href="viewDistrictCalendar.html" title="Back to Calendar">Back to Calendar</a></div>
							
		<% }%>				 
  		
  	<% if(request.getAttribute("msgok") == null && request.getAttribute("msgerr") == null ) { %>
  	
  	<b>Are you sure you wish to delete this event?</b>
  	<br/><br/>Check the event details below to confirm you wan to delete this event. Once deleted it cannot be retrieved.<br/><br/>
  		
    <table class="table table-condensed" style="font-size:11px;">	
                <tbody>
 				<tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%> border-top:1px solid <%=txtcolor%>;" colspan=1>Region:</td>
                <td class="tableResult" colspan=3 style="color:<%=txtcolor%>;border-top:1px solid <%=txtcolor%>;"><%=regionName%></td>
                </tr>
                <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Event Type:</td>
                <td class="tableResult" colspan=3><%=(evt==null)?"N/A":evt.getEventType().getEventTypeName()%></td>
              </tr>
              
              <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Title:</td>
                <td class="tableResult" colspan=3><%=(evt==null)?"N/A":evt.getEventName()%></td>
              </tr>
              <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Description:</td>
                <td class="tableResult" colspan=3><%=(evt==null)?"N/A":evt.getEventDescription()%></td>
              </tr>
               <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Location (Host):</td>
                <td class="tableResult" colspan=3 style="text-transform:Capitalize;"><%=(evt==null)?"N/A":evt.getEventLocation()%></td>
              </tr>
              <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Start/End Date:</td>
                <td class="tableResult" colspan=3>
                <%=(evt==null)?"N/A":evt.getEventDate()%> to 
                <% if(evt.getEventEndDate() != null) { %>
			              <%=(evt==null)?"N/A":evt.getEventEndDate()%>
			          <% } else {%>              		
			              N/A
		              <%} %>
                 </td>
                
              </tr>
              <% if(!evt.getEventStartTime().equals("UNKNOWN")) { %>
	              <tr>
	                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Start/Finish Time:</td>
	                <td class="tableResult" colspan=3><%=(evt==null)?"N/A":evt.getEventStartTime()%> to <%=(evt==null)?"N/A":evt.getEventFinishTime()%></td>	                
	              </tr>
              <% } %>            
              
   				      
              <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
	              <tr>
	                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Max Spaces (#Registered):</td>
	                <td class="tableResult" colspan=3>
	                  	<% if(evt.getEventMaximumParticipants() > 0) { %>
	                    <%=(evt==null)?"N/A":evt.getEventMaximumParticipants()%>
	                  	<% } else { %>
	                    UNLIMITED
	                  	<% } %>
	                   <% if(!usr.checkRole("TEACHER")) { %>		              
		              	(<%=(evt==null)?"N/A":evt.getRegistrationCount()%>)	              
	              		<% } else { %>
	                   (N/A)           
	              		<%} %>
	                 </td>	              
	             </tr>
	              
              <% } %>
						
							
     <tr class="no-print">
           <td align="right" colspan="4" style="background-color:<%=bgcolor%>"> 
            <input type="hidden" name="confirmed" value="true">
                  <% if(request.getAttribute("msgok") == null) { %>
                    <input type="hidden" name="id" value="<%=evt.getEventID()%>">
                    <a href="#" onclick="onClick(document.delevt);" class="btn btn-xs btn-danger" title="Confirm Delete">Confirm Delete</a>
                    <a class="btn btn-xs btn-primary" href="javascript:history.go(-1);" title="Cancel">Cancel</a>
                  <% } else { %>
                    <a class="btn btn-xs btn-primary" href="viewDistrictCalendar.html" title="Back to Calendar">Back to Calendar</a>
                  <% } %>
           </td>
           
           </tr>




    </tbody>
    </table>
<%} %>
 

              
    </form>
    
    </div>
  </body>
</html>