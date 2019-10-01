<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*,com.awsd.personnel.*,com.awsd.school.*,com.awsd.security.crypto.*,
                  com.esdnl.servlet.*,
                  com.esdnl.student.travel.bean.*,
                  com.esdnl.student.travel.constant.*,com.esdnl.util.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
  User usr = (User) session.getAttribute("usr");
  Form form = (Form) request.getAttribute("FORM");
  TravelRequestBean treq = (TravelRequestBean) request.getAttribute("TRAVELREQUESTBEAN");
  Personnel approver = (Personnel) request.getAttribute("APPROVER");
%>
  <esd:SecurityCheck permissions="STUDENT-TRAVEL-ADMIN-VIEW,STUDENT-TRAVEL-PRINCIPAL-VIEW" />
  
<html>
<head>
  <title>Student Travel Management</title>
   
</head>
<body style="margin-top:15px;">

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading">Viewing Travel Request <b><%=(treq != null)?treq.getDestination():"&nbsp;"%></b> for <b><%=(treq != null)?SchoolDB.getSchool(treq.getSchoolId()).getSchoolName():"&nbsp;"%></b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 		



                                <%if(request.getAttribute("msg") != null){%>
                                 <%=(String)request.getAttribute("msg")%>
                                <%}%>
                                
<form id="frmAddTravelRequest" action="addRequest.html" method="post">
                                  <%if(treq != null){%>
                                    <input type='hidden' name='op' value='UPDATE'>
                                    <input type='hidden' name='request_id' value='<%=treq.getRequestId()%>'>
                                  <%}else{%>
                                    <input type='hidden' name='op' value='ADD'>
                                  <%}%>
                               
	                               <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
	                               		<div class="input-group">
		    								<span class="input-group-addon">STATUS:</span>
		    								<div class="form-control">		    								
			                                  <%if(treq != null){%>
			                                  <%if (treq.getStatus().getDescription() == "REJECTED"){ %>
												          				<span style="color:Red;"><span class="glyphicon glyphicon-remove"></span> <%=treq.getStatus().getDescription() %></span>
												          		<%} else if (treq.getStatus().getDescription() == "APPROVED") {%>		
												          				<span style="color:Green;"><span class="glyphicon glyphicon-ok"></span> <%=treq.getStatus().getDescription() %></span>
												          		<%} else if  (treq.getStatus().getDescription() == "SUBMITTED") { %>		
												          				<span style="color:Navy;"><span class="glyphicon glyphicon-share"></span> <%=treq.getStatus().getDescription() %></span>
												          		<%} else { %>		
												          				<span style="color:Black;"><%=treq.getStatus().getDescription() %></span>
												          		<%}%>
			                                  <%}else{ %>
			                                  N/A
			                                  <%} %>        
		    								</div>
	  									</div>
	                               </div>
	                               	                             
                                   <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
	                               		<div class="input-group">
		    								<span class="input-group-addon">REQUESTED BY:</span>
		    								<div class="form-control" style="text-transform:capitalize;"><%=(treq != null)?PersonnelDB.getPersonnel(treq.getRequestedBy()).getFullNameReverse():"&nbsp;"%></div>
	  									</div>
	                               </div>
	                               
	                               <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
	                               		<div class="input-group">
		    								<span class="input-group-addon">REQUEST DATE:</span>
		    								<div class="form-control"><%=(treq != null)?treq.getRequestedDateFormatted():"&nbsp;"%></div>
	  									</div>
	                               </div>	                                
                              
                              	<% if(treq.getRequestedBy() != treq.getActionedBy()){ %>                               
                                   <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
	                               		<div class="input-group">
		    								<span class="input-group-addon"> <%=(treq != null)?treq.getStatus().getDescription():"&nbsp;"%> BY:</span>
		    								<div class="form-control" style="text-transform:capitalize;"><%=(treq != null)?PersonnelDB.getPersonnel(treq.getActionedBy()).getFullNameReverse():"&nbsp;"%></div>
	  									</div>
	                               </div>
	                               <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
	                               		<div class="input-group">
		    								<span class="input-group-addon"><%=(treq != null)?treq.getStatus().getDescription():"&nbsp;"%> DATE:</span>
		    								<div class="form-control"> <%=(treq != null)?treq.getActionDateFormatted():"N/A"%></div>
	  									</div>
	                               </div>
	                            <%}%>   	                            
	                             
	                               <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
	                               		<div class="input-group">
		    								<span class="input-group-addon">SCHOOL:</span>
		    								<div class="form-control"><%=(treq != null)?SchoolDB.getSchool(treq.getSchoolId()).getSchoolName():"N/A"%></div>
	  									</div>
	                               </div>	                               
                               
	                              
	                               <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
	                               		<div class="input-group">
		    								<span class="input-group-addon">DEPARTURE DATE:</span>
		    								<div class="form-control"><%=(treq != null)?treq.getDepartureDateFormated():"N/A"%></div>
	  									</div>
	                               </div>
	                               <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
	                               		<div class="input-group">
		    								<span class="input-group-addon">RETURN DATE:</span>
		    								<div class="form-control"><%=(treq != null)?treq.getReturnDateFormatted():"N/A"%></div>
	  									</div>
	                               </div>                             
                              		<div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
	                               		<div class="input-group">
		    								<span class="input-group-addon"># DAYS MISSED:</span>
		    								<div class="form-control"><%=(treq != null)?Double.toString(treq.getDaysMissed()):"N/A"%></div>
	  									</div>
	                               </div>
	                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	                               		<div class="input-group">
		    								<span class="input-group-addon">DESTINATION:</span>
		    								<div class="form-control" style="overflow: auto;height:auto;"><%=(treq != null)?treq.getDestination():"N/A"%></div>
	  									</div>
	                               </div>
	                               
	                               <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	                               		<div class="input-group">
		    								<span class="input-group-addon">REASON FOR TRIP:</span>
		    								<div class="form-control" style="overflow: auto;height:auto;"><%=(treq != null)?StringUtils.encodeHTML(treq.getRational()):""%></div>
	  									</div>
	                               </div>
	                               
	                               <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
	                               		<div class="input-group">
		    								<span class="input-group-addon">GRADES:</span>
		    								<div class="form-control"><%=(treq != null)?treq.getGrades():"N/A"%></div>
	  									</div>
	                               </div>	   
	                                                           
                             		<div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
	                               		<div class="input-group">
		    								<span class="input-group-addon"># STUDENTS:</span>
		    								<div class="form-control"><%=(treq != null)?Integer.toString(treq.getNumStudents()):"N/A"%></div>
	  									</div>
	                               </div>
	                               
	                               <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
	                               		<div class="input-group">
		    								<span class="input-group-addon">TOTAL # CHAPERONES:</span>
		    								<div class="form-control"><%=(treq != null)?Integer.toString(treq.getTotalChaperons()):"N/A"%></div>
	  									</div>
	                               </div>
	                               
	                               <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
	                               		<div class="input-group">
		    								<span class="input-group-addon"># TEACHER CHAPERONES:</span>
		    								<div class="form-control"><%=(treq != null)?Integer.toString(treq.getTotalTeacherChaperons()):"N/A"%></div>
	  									</div>
	                               </div>	
                                  
                                   <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	                               		<div class="input-group">
		    								<span class="input-group-addon">TEACHER CHAPERONES:</span>
		    								<div class="form-control"> <%=(treq != null)?treq.getTeacherChaperon():"N/A"%></div>
	  									</div>
	                               </div>	                               
                                   
                                   <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	                               		<div class="input-group">
		    								<span class="input-group-addon"># OTHER CHAPERONES:</span>
		    								<div class="form-control"><%=(treq != null)?Integer.toString(treq.getTotalOtherChaperons()):"N/A"%></div>
	  									</div>
	                               </div>	
	                               
                               		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	                               		<div class="input-group">
		    								<span class="input-group-addon">OTHER CHAPERONES:</span>
		    								<div class="form-control"><%=((treq != null)&&!StringUtils.isEmpty(treq.getOtherChaperon()))?treq.getOtherChaperon():"N/A"%></div>
	  									</div>
	                               </div>	                              
                                        
	                               <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
	                               		<div class="input-group">
		    								<span class="input-group-addon">EMERGENCY CONTACT:</span>
		    								<div class="form-control"><%=(treq != null)?treq.getEmergencyContact():"N/A"%></div>
	  									</div>
	                               </div>
	                               
	                               <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	                               		<div class="input-group">
		    								<span class="input-group-addon">OTHER INFO:</span>
		    								<div class="form-control" style="overflow: auto;height:auto;">
		    								Each chaperone has <%=(treq != null)?(treq.isChaperonsApproved()?"been <span style='color:Green;'>APPROVED</span>":"<span style='color:Red;'>NOT</span> been approved"):""%> by the principal of <%=(treq != null)?SchoolDB.getSchool(treq.getSchoolId()).getSchoolName():"N/A"%>. 
		    								This trip to <%=(treq != null)?treq.getDestination():"N/A"%> <%=(treq != null)?(treq.isBilletingInvolved()?"will":"will not"):""%> involve billeting and
		    								there <%=(treq != null)?(treq.isSchoolFundraising()?"was":"wasn't any"):""%>  fundraising associated with this trip at the school.
		    								</div>
	  									</div>
	                               </div>                        
                                    
                                    <div style="clear:both;"></div>
                                    <br/>
                                    <div align="center" class="no-print">                                    
                                    <a class='btn btn-xs btn-primary' href="/MemberServices/student/travel/itineraries/<%=(treq != null)?treq.getIteneraryFilename():""%>" title="Itinerary Document. Will open in a new tab or window." target="_blank">VIEW ITINERARY DOCUMENT</a>
                                    <a href='#' title='Print this page (pre-formatted)' class="btn btn-xs btn-info" onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:10px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Student Travel System</b></div><br/>'});">PRINT THIS REQUEST</a></li>
                                
                                    <%if((usr.getPersonnel().getPersonnelID() == approver.getPersonnelID()) && (treq != null) && treq.getStatus().equals(RequestStatus.SUBMITTED)){%>
                                      
                                          <%
                                          out.println("<a href='/MemberServices/student/travel/travelRequestAdmin.html?u="
                                            + approver.getUserName() +"&op=approve&id=" +treq.getRequestId() + "' class='btn btn-xs btn-success'>APPROVE REQUEST</a>"); 
                                          
                                          out.println("<a href='/MemberServices/student/travel/travelRequestAdmin.html?u="
                                            + approver.getUserName() +"&op=decline&id=" +treq.getRequestId() + "' class='btn btn-xs btn-danger'>DECLINE REQUEST</a>");  
                                          %>
                                       
                                    <%}%>
                                    
                                   <%if(((usr.checkRole("ADMINISTRATOR") || usr.checkRole("PRINCIPAL"))  && (treq.getStatus().getDescription() != "APPROVED"))){%>
                                    	
                                    	<a class='btn btn-xs btn-danger'  onclick="return confirm('Are you sure you want to delete this request? This cannot be undone.')" href='deleteTravelRequest.html?rid=<%= treq.getRequestId() %>'>DELETE REQUEST</a>
                                   
                                   <% } %>
                                    <a href="index.jsp" class="btn btn-xs btn-danger">BACK TO LIST</a>                                    
                                    
                                 </div>
                                </form>
                          
   </div></div></div></div>
            
</body>
</html>
