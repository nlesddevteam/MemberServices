<%@ page  language="java" 
          session="true" 
          import="java.util.*, 
            java.text.*, 
            com.awsd.pdreg.*,
            com.awsd.security.*,
            com.awsd.personnel.*,
            com.awsd.school.*"
          isThreadSafe="false"%>

<%
  // months as they appear in the calendar's title
  final String ARR_MONTHS[] = {"January", 
                                "February", 
                                "March", 
                                "April", 
                                "May", 
                                "June", 
                                "July", 
                                "August", 
                                "September", 
                                "October", 
                                "November", 
                                "December"};

  // week day titles as they appear on the calendar
  final String ARR_WEEKDAYS[] = {"Sunday", 
                                  "Monday", 
                                  "Tuesday", 
                                  "Wednesday", 
                                  "Thursday", 
                                  "Friday", 
                                  "Saturday"};
  
  // path to the directory where calendar images are stored. trailing slash req.
  final String STR_ICONPATH = "images/";

  int dayofweek, sid = -1;

  Calendar cur = null;
  User usr = null;
  UserPermissions permissions = null;
  SchoolRegisteredEvents sre = null;
  RegisteredEvents revts = null;
  Personnel p = null;
  Personnel[] aps = null;
  School s = null;
  Event evt = null;
  Iterator iter = null;
  Iterator e_iter = null;
%>

<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    permissions = usr.getUserPermissions();
    if(!(permissions.containsKey("CALENDAR-VIEW") 
        && permissions.containsKey("CALENDAR-VIEW-SCHOOL-ENROLLMENT")))
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
  
  sre = (SchoolRegisteredEvents) request.getAttribute("SchoolRegisteredEvents");
  s = sre.getSchool();
  iter = sre.getSchoolTeachers().iterator();
  aps = s.getAssistantPrincipals();
%>

<html>

 <head>
  
   	<title>PD Calendar</title>
    
    <script>      
     $("#loadingSpinner").css("display","none");	  
 		$('document').ready(function(){
	  $(".staffRegistrations").DataTable({
		  "order": [[ 0, "asc" ]],		 
		  "bPaginate": false,
		  "bLengthChange": false,
		  "lengthMenu": [[25, 50, 100, 200, -1], [25, 50, 100, 200, "All"]]
	  
	  
	  });	
	 
 });
    </script>
     	<style>
		.tableTitle {font-weight:bold;width:20%;text-transform:uppercase;}
		.tableResult {font-weight:normal;width:80%;}
		.tableTitleL {font-weight:bold;width:20%;text-transform:uppercase;}
		.tableResultL {font-weight:normal;width:30%;background-color:#ffffff;}
		.tableTitleR {font-weight:bold;width:20%;text-transform:uppercase;}
		.tableResultR {font-weight:normal;width:30%;background-color:#ffffff;}
		input {border:1px solid silver;}
	</style>
    
    
  </head>

<body>
<div class="container-fluid no-print" data-spy="affix" data-offset-top="0" style="position:fixed;width:100%;height:30px;background-color:#008B8B;color:White;text-align:center;font-weight:bold;padding:5px;">                      
 <%=s.getSchoolName()%> Registrations
</div>
<div class="registerEventDisplay" style="padding-top:25px;font-size:11px;">

 <div align="center" class="no-print"><a href="viewDistrictCalendar.html"><img class="topLogoImg" src="includes/img/pdcalheader.png" border=0 style="padding-bottom:10px;"/></a></div>


 						<% if(request.getAttribute("msgERR") != null) { %>
         				 <div class="alert alert-danger" style="text-align:center;"><%= request.getAttribute("msgERR") %></div>
       					 <% } %>
       					<% if(request.getAttribute("msgOK") != null) { %>
         				 <div class="alert alert-success" style="text-align:center;"><%= request.getAttribute("msgOK") %>.</div>
       					 <% } %> 

								<table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>
							    <tr>                        
    							<td class="tableTitle">SCHOOL:</td>
    							<td class="tableResult" colspan=3><%=s.getSchoolName()%></td>
    							</tr>
    							 <tr>                        
    							<td class="tableTitle">PRINCIPAL:</td>
    							<td class="tableResult" colspan=3 style="text-transform:Capitalize;"><%=s.getSchoolPrincipal().getFullNameReverse()%></td>
    							</tr>
    							<% if((aps != null) && (aps.length > 0)) { %>
    							<tr>                        
    							<td class="tableTitle">VICE-PRINCIPAL:</td>
    							<td class="tableResult" colspan=3 style="text-transform:Capitalize;">
    							<% for(int i=0; i < aps.length; i++)
                  					out.println(aps[i].getFullNameReverse() + "<br>");
                  				%>
    							</td>
    							</tr>
								<%}%>
								</tbody>
								</table>
    

  
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>STAFF REGISTRATIONS</b></div>
      			 	<div class="panel-body"> 
      			 	Below is a list of your staff and any PD Events they maybe registered for.<br/><br/>
      			 	You currently have <b><span id="staffRegistered"></span></b> staff members out of <span id="staffRegisteredPC"></span> registered for PD events.<br/><br/>
      			 	
 <table class="table table-condensed table-striped" style="width:100%;font-size:11px;background-color:#FFFFFF;">
									   
									    <tbody> 
    
              <%	
              int numStaffRegistered = 0;  
              int numStaff = 0;
              %>
            
              <% while (iter.hasNext()) {
                  p = (Personnel) iter.next();
                  numStaff++;
                  %>
                
              	 <tr>
                  <td colspan=2 style="font-weight:bold;background-color:#FFF8DC;color:Green;"><%=p.getFullName()%></td> 
                 </tr> 
                  <% revts = (RegisteredEvents) sre.get(p);
                  		e_iter = revts.entrySet().iterator();
                  
                  		if(!e_iter.hasNext()) {%>
                      <tr><td colspan=2 style="font-style:italic;color:Silver;">&nbsp;&nbsp;Not registered for any events.</td></tr>
               			<% } else {
                  			int iCnt=0;
                  			numStaffRegistered++;
               				while(e_iter.hasNext()) {
                    		evt = (Event)((Map.Entry) e_iter.next()).getValue();
                    		iCnt++;
               			%>
               			<tr>
               			<td width="75%">&nbsp;&nbsp;<%=iCnt%>. <%= evt.getEventName()%> 
               			<%if(evt.isCloseOutDaySession()) {%>
                 			(Closeout Session)
               				<%}%>
               			
               			</td>
                        <td width="25%" style="text-align:right;"><a class="no-print btn btn-xs btn-primary" title="View Event Details" href="registerEvent.html?id=<%=evt.getEventID()%>&details=true">VIEW</a>
                			<% if(evt.isPDOpportunity()) {%>
               				 <a class="no-print btn btn-xs btn-danger" title="De-Register this staff member from this event?" href="deregisterEvent.html?pid=<%=p.getPersonnelID()%>&id=<%=evt.getEventID()%>">DE-REGISTER?</a>
               				<%}%>
               			</td></tr>
               <% } %>        
              <% } %>
              
              <% } %>
              
              
              </tbody>
              </table>
              
              
              
              
   </div></div></div>
   
   			  <div align="center" class="no-print navBottom">
  				<a href='#' title='Print this page (pre-formatted)' class="btn btn-primary btn-xs"  onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:10px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Professional Development Calendar</b></div><br/>'});"><span class="glyphicon glyphicon-print"></span> Print</a>	                     
  				<a onclick="loadingData()" class="no-print btn btn-xs btn-danger" href="viewDistrictCalendar.html">Back to Calendar</a>
  			 </div>
   
   </div>
   
   
   <script>
   $("#staffRegistered").text(<%=numStaffRegistered%>);   
   $("#staffRegisteredPC").text(<%=numStaff%>);  
   </script>
   
</body>
</html>