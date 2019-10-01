<%@ page language="java"
         session="true"
         import="java.util.*, 
                 java.text.*,com.awsd.security.*,
                 com.awsd.school.*,
                 com.awsd.personnel.*,
                 com.esdnl.student.travel.bean.*,
                 com.esdnl.student.travel.dao.*"
         isThreadSafe="false"%>


<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="STUDENT-TRAVEL-ADMIN-VIEW,STUDENT-TRAVEL-PRINCIPAL-VIEW" />

<%
User usr = (User) session.getAttribute("usr");

boolean isPrincipal = usr.checkPermission("STUDENT-TRAVEL-PRINCIPAL-VIEW");
boolean isSEO = usr.checkRole("SENIOR EDUCATION OFFICIER");

TravelRequestBean[] reqs = null;

	if(isPrincipal) {
	  	reqs = TravelRequestManager.getTravelRequestBeans(usr.getPersonnel().getSchool(), true);
	}
	else if(isSEO) {
		reqs = TravelRequestManager.getTravelRequestBeans(SchoolFamilyDB.getSchoolFamily(usr.getPersonnel()));
	}
	else {
	  	reqs = TravelRequestManager.getTravelRequestBeans(true);
	}

SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
School s = null;
int cntr = 0;
%>


<html>
<head>
  <title>Student Travel Management</title>
 
 
 <style>		
	
		input[type="checkbox"] {margin-top: -1px;margin-left:6px;margin-right:2px;vertical-align: middle;}
		input { border:1px solid silver;}
</style>
    <script>   
 $('document').ready(function(){
	  $(".travelApps").DataTable({
		  "order": [[ 0, "asc" ]],
		  "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]]
	  
	  
	  });	
	 
 });
    </script>
 
 
</head>
<body>
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>Travel Request(s)</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 			
					Current travel requests are listed below sorted by School. To find a particular travel listing, use the search box below. You can also sort the listings by any column by clicking on the column header.<br/>	
					
					<%if(usr.checkPermission("STUDENT-TRAVEL-PRINCIPAL-VIEW")){%>
					  	<br/><br/>
            			<a href="addRequest.html" class="btn btn-xs btn-success"><span class="glyphicon glyphicon-plus"></span> Add Request</a> 
          			<%}%>
          			
          			
                     
          			<br/><br/>
					
					<%if(reqs.length > 0){ %>
									<table class="travelApps table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;">
									    <thead>
									      <tr>
									      	<th width='20%'>SCHOOL</th>
									        <th width='30%'>TRIP</th>	
									        <th width='10%'>DEPARTURE</th>								        
									        <th width='18%'>REQUESTED BY</th>	
									        <th width='10%'>STATUS</th>								        															       
									        <th width='12%' class="no-print">OPTIONS</th>
									      </tr>
									    </thead>
									    <tbody> 
			                              	<%for(int i=0; i < reqs.length; i++){ %>
			                              	<%cntr++; %>
									           <tr class="status<%=cntr%>">
									        	 	<td>
									        	 	<% s = SchoolDB.getSchool(reqs[i].getSchoolId());%>
									         		<%=s.getSchoolName()%>
									         		</td>
									         		<td><%=reqs[i].getDestination()%></td>
									         		<td><%=sdf.format(reqs[i].getDepartureDate())%></td>									         		
									          		<td><span style="text-transform:Capitalize;"><%=(reqs[i] != null)?PersonnelDB.getPersonnel(reqs[i].getRequestedBy()).getFullNameReverse():"&nbsp;"%></span></td>
									          		<td>
									          		<%if (reqs[i].getStatus().getDescription() == "REJECTED"){ %>
									          				<span style="color:Red;"><span class="glyphicon glyphicon-remove"></span> <%=reqs[i].getStatus().getDescription() %></span>
									          				<script>$(".status<%=cntr%>").css("background","rgba(255, 0, 0, 0.2)");</script>
									          		<%} else if (reqs[i].getStatus().getDescription() == "APPROVED") {%>		
									          				<span style="color:Green;"><span class="glyphicon glyphicon-ok"></span> <%=reqs[i].getStatus().getDescription() %></span>
									          				<script>$(".status<%=cntr%>").css("background","rgba(230, 255, 230, 0.7)");</script>
									          		<%} else if  (reqs[i].getStatus().getDescription() == "SUBMITTED") { %>		
									          				<span style="color:Navy;"><span class="glyphicon glyphicon-share"></span> <%=reqs[i].getStatus().getDescription() %></span>
									          				<script>$(".status<%=cntr%>").css("background","rgba(255, 255, 255, 1)");</script>
									          		<%} else { %>		
									          				<span style="color:Black;"><%=reqs[i].getStatus().getDescription() %></span>
									          		<%}%>
									          		</td>
									            	<td><a class="btn btn-xs btn-info" title='View Details of this Request.' href="viewRequest.html?id=<%=reqs[i].getRequestId()%>">VIEW</a> 
									                <%if( ((usr.checkRole("ADMINISTRATOR")|| (usr.checkRole("PRINCIPAL"))) && (reqs[i].getStatus().getDescription() != "APPROVED"))){%>
									            		<a class="btn btn-xs btn-danger" title='Delete this Request.' onclick="return confirm('Are you sure you want to delete this request from <%=s.getSchoolName()%> for a trip to <%=reqs[i].getDestination()%>? This cannot be undone.')" href="deleteTravelRequest.html?rid=<%= reqs[i].getRequestId() %>">DEL</a>
									            	<%} %>
									            	
									            	
									            	
									            	</td>
									         </tr>   
									        <%}%>
									    </tbody>
			                        </table>
                    <%}else{%>
						 <div class="alert alert-danger" style="text-align:center;">Sorry, no current requests on file.</div>
					<%}%> 

  
  
  
  
  
 </div></div></div></div>
 
 

  <div align="center" style="padding-top:10px;padding-bottom:10px;" class="no-print"><a href="/MemberServices/navigate.jsp" class="btn btn-xs btn-danger">Back to Member Services</a></div>
  
  
  
  
                    
</body>
</html>
