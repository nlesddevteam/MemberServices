<%@ page language="java"
          import="com.awsd.pdreg.*,
          com.awsd.security.*, 
                  java.text.*, 
                  java.util.*,
                  java.io.*,
                  com.awsd.common.Utils,
                  org.apache.commons.lang.*"
          isThreadSafe="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>          
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<% 
    User usr = null;
    Event evt = null;
    String img;
    String bgcolor = "";
    String txtcolor = "";
    String regionName = "";
    int width;
    SimpleDateFormat sdf = null;
%>

<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("CALENDAR-VIEW")))
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
  
  img = "includes/img/deregister_pt1.gif";
 
  sdf = new SimpleDateFormat("MM/dd/yyyy");
  
  if(request.getAttribute("msg") != null)
  {
    width = 287;
  }
  else
  {
    width = 185;
  }  
%>
<%
            //Check Regions of the events.
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
				 }
              
              %>        
<html>
  <head>
 	<title>PD Calendar</title>

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
     
  <div class="container-fluid no-print topGreenTitleArea" data-spy="affix" data-offset-top="0" style="position:fixed;width:100%;background-color:#FF0000;color:White;text-align:center;font-weight:bold;padding:5px;">                      
     DE-REGISTER FROM EVENT <span style="color:Yellow;"><%=evt.getEventType().getEventTypeName()%> <%=evt.getEventName()%>?</span>
</div>

<div class="registerEventDisplay" style="padding-top:50px;font-size:11px;">
 <div style="margin-left:5px;margin-right:5px;">    
   
   <div align="center" class="no-print"><a href="viewDistrictCalendar.html"><img class="topLogoImg" src="includes/img/pdcalheader.png" border=0 style="padding-bottom:10px;"/></a></div>
    				
    <table class="table table-condensed" style="font-size:11px;">	
   
    <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%> border-top:1px solid <%=txtcolor%>;" colspan=1>Region:</td>
                <td class="tableResult" colspan=3 style="color:<%=txtcolor%>;border-top:1px solid <%=txtcolor%>;"><%=regionName%></td>
              </tr>
              
              <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Event Type:</td>
                <td class="tableResult" colspan=3><%=evt.getEventType().getEventTypeName()%></td>
              </tr>
              
              <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Title:</td>
                <td class="tableResult" colspan=3><%=evt.getEventName()%></td>
              </tr>
              <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Description:</td>
                <td class="tableResult" colspan=3><%=evt.getEventDescription()%></td>
              </tr>
              
              <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Location (Host):</td>
                <td class="tableResult" colspan=3><%=evt.getEventLocation() + ((evt.getEventSchoolZone() != null) ? " - " + StringUtils.capitalize(evt.getEventSchoolZone().getZoneName()) + " Region" : "") %>
                <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
              	 <br/>(Hosted by: <span style="text-transform:Capitalize;"><%=evt.getScheduler().getFullNameReverse()%></span>) 
                <% } else {%>
               	<br/>(N/A)
              <%} %>
                </td>
              </tr>
              
       <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Start/End Date:</td>
                <td class="tableResult" colspan=3>
                <%=evt.getEventDate()%> to 
                <% if(evt.getEventEndDate() != null) { %>
			              <%=evt.getEventEndDate()%>
			          <% } else {%>              		
			              N/A
		              <%} %>
                 </td>
                
              </tr>
              <% if(!evt.getEventStartTime().equals("UNKNOWN")) { %>
	              <tr>
	                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Start/Finish Time:</td>
	                <td class="tableResult" colspan=3><%=evt.getEventStartTime()%> to <%=evt.getEventFinishTime()%></td>	                
	              </tr>
              <% } %>
              
              
              
                   <% if(evt.hasEventCategories()) { %>
					      <tr>
					        <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Categories:</td>
					        <td class="tableResult" colspan=3>
					          <c:forEach items="${evt.eventCategories}" var="cat" varStatus="status">
					          	${cat.categoryName}<c:if test="${status.last eq false}">, </c:if>
					          </c:forEach>
					        </td>
					      </tr>
				      <% } %>
              
              
				      
              <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
	              <tr>
	                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Max Spaces (#Registered):</td>
	                <td class="tableResult" colspan=3>
	                  	<% if(evt.getEventMaximumParticipants() > 0) { %>
	                    <%=evt.getEventMaximumParticipants()%>
	                  	<% } else { %>
	                    UNLIMITED
	                  	<% } %>
	                   <% if(!usr.checkRole("TEACHER")) { %>		              
		              	(<%=evt.getRegistrationCount()%>)	              
	              		<% } else { %>
	                   (N/A)           
	              		<%} %>
	                 </td>	              
	             </tr>
	              
              <% } %>
      
     
      
      
      <tr class="no-print">
      <td class="eventOptions" colspan="4" style="background-color:<%=bgcolor%>">
     
      <% if(request.getAttribute("msgERR") == null && request.getAttribute("msgOK") == null) { %>
      <b>Are you sure you want to de-register?</b> &nbsp;
      				<a href="deregisterEvent.html?id=<%=evt.getEventID()%>&confirmed=true" class="btn btn-danger btn-xs">YES</a>
                 <a href="javascript:history.go(-1);" class="btn btn-success btn-xs">NO</a>
                 <% } else { %>
                    <a class="no-print btn btn-xs btn-danger" href="viewDistrictCalendar.html">Back to Calendar</a>
                  <% } %>
      </td>
      </tr>
      
    </table>
    
  		<% if(request.getAttribute("msgERR") != null) { %>
      
          <div class="alert alert-danger" align="center"><%= request.getAttribute("msgERR") %></div>
        
      <% } else if(request.getAttribute("msgOK") != null) {  %>
     
          <div class="alert alert-success" align="center"><%= request.getAttribute("msgOK") %></div>
       
      <% }%>
                  
    
  

   
     </div>
   </div>

  
  
        

  </body>
</html>