<%@ page language="java"
         session="true"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,                 
                 com.awsd.common.*,
                 com.nlesd.bcs.dao.*,
                 com.nlesd.bcs.bean.*,
                 java.util.*,
                 java.io.*,
                 java.text.*,com.esdnl.util.*"
         isThreadSafe="false"%>
    
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%
  User usr = null;
  usr = (User) session.getAttribute("usr");
  BussingContractorSystemCountsBean cbean = BussingContractorSystemCountsManager.getBussingContractorSystemCounts();
  
    
  %>
  <c:set var='pendingContractors' value="<%=cbean.getSubmittedContractors() %>" />
  <c:set var='pendingEmployees' value="<%=cbean.getSubmittedContractorsEmployees() %>" />
  <c:set var='pendingVehicles' value="<%=cbean.getSubmittedContractorsVehicles() %>" />
  
  
  
  
<html>

	<head>
		<title>NLESD - Busing Operator System</title>				

		  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
		    <meta charset="utf-8">
		    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">    		
    		<link href="includes/css/jquery-ui.css" rel="stylesheet" type="text/css"> 
    		<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">  	
   			<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
   			<link href="includes/css/bootstrap.min.css.map" rel="stylesheet" type="text/css">
   			<!-- For mini-icons in menu -->
   			<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">			
    		<script src="includes/js/jquery.min.js"></script>		
    		<script src="includes/js/jquery-ui.js"></script>
    		<script src="includes/js/bootstrap.min.js"></script>
    		<script src="includes/js/jquery.maskedinput.min.js"></script>
    		<script src="includes/js/bcs.js"></script>
				<script>
			$(document).ready(function(){    
        		//clear spinner and boxes on load
    			$('#loadingSpinner').css("display","none");
    			$("#display_error_message_top").css("display","none");
    		    $("#display_error_message_bottom").css("display","none");
    		    
        	});
			</script>
				
				
				<script>
				
				jQuery(function(){
					  $(".img-swap").hover(
					          function(){this.src = this.src.replace("-off","-on");},
					          function(){this.src = this.src.replace("-on","-off");});
					 });
				
				
				</script>
	</head>

  <body>
 <div class="mainContainer">
  	   	<div class="section group">	   		
	   		<div class="col full_block topper" align="center">
	   			<script src="includes/js/date.js"></script>	   		
			</div>			
			<div class="full_block center">
				<img src="includes/img/header-admin.png" alt="Student Transportation Administration System for District Personnel" width="100%" border="0"><br/>	
				<div align="left"><jsp:include page="includes/menu.jsp" /></div>				
			</div>
			<div class="col full_block content">
					<div class="bodyText">
					<div class="alert alert-danger" id="display_error_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    				<div class="alert alert-success" id="display_success_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
					<div class="alert alert-warning" id="display_warning_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
					
					
										
						<div id="printJob">
							<div id="pageContentBody" style="width:100%;">
							
								
						
						<div class="BCSInfoBox">	
							<esd:SecurityAccessRequired permissions="BCS-VIEW-CONTRACTORS">
								<table class="BCSSummaryBox">
									<tr>
									<td class="BCSSummaryBoxTitle" style="text-align:center;">SUMMARY</td><td class="BCSSummaryBoxTitle" style="text-align:center;">Contractors</td><td class="BCSSummaryBoxTitle" style="text-align:center;">Employees</td><td class="BCSSummaryBoxTitle" style="text-align:center;">Vehicles</td>
									</tr>
									</tr>
									<tr style="color:Blue;">
									<td class="BCSSummaryBoxTitle" style="text-align:right;background:#FFFFE0;color:Blue;">Pending </td><td class="BCSSummaryBoxBody">${pendingContractors}</td><td class="BCSSummaryBoxBody">${pendingEmployees}</td><td class="BCSSummaryBoxBody">${pendingVehicles}</td>
									</tr>
									<tr style="color:Green;">
									<td class="BCSSummaryBoxTitle" style="text-align:right;background:#FFFFE0;color:Green;">Approved </td><td class="BCSSummaryBoxBody"><%=cbean.getApprovedContractors() %></td><td class="BCSSummaryBoxBody"><%=cbean.getApprovedContractorsEmployees() %></td><td class="BCSSummaryBoxBody"><%=cbean.getApprovedContractorsVehicles()%></td>
									</tr>
									<tr style="color:DarkOrange;">
									<td class="BCSSummaryBoxTitle" style="text-align:right;background:#FFFFE0;color:DarkOrange;">Suspended </td><td class="BCSSummaryBoxBody"><%=cbean.getSuspendedContractors()%></td><td class="BCSSummaryBoxBody"><%=cbean.getSuspendedContractorsEmployees()%></td><td class="BCSSummaryBoxBody"><%=cbean.getSuspendedContractorsVehicles()%></td>
									</tr>
									<tr style="color:Red;">
									<td class="BCSSummaryBoxTitle" style="text-align:right;background:#FFFFE0;color:Red;">Rejected </td><td class="BCSSummaryBoxBody"><%=cbean.getRejectedContractors()%></td><td class="BCSSummaryBoxBody"><%=cbean.getRejectedContractorsEmployees()%></td><td class="BCSSummaryBoxBody"><%=cbean.getRejectedContractorsVehicles()%></td>
									</tr>
									<tr style="color:Grey;">
									<td class="BCSSummaryBoxTitle" style="text-align:right;background:#FFFFE0;color:Grey;">Removed </td><td class="BCSSummaryBoxBody"><%=cbean.getRemovedContractors() %></td><td class="BCSSummaryBoxBody"><%=cbean.getRemovedContractorsEmployees() %></td><td class="BCSSummaryBoxBody"><%=cbean.getRemovedContractorsVehicles() %></td>
									</tr>
								</table>
							</esd:SecurityAccessRequired>				
								
								<img src="includes/img/bar.jpg" height="1" width="100%" style="margin-top:5px;margin-bottom:3px;"/>
								
				              	<br/><div align=center style="padding-bottom:2px;color:#1F4279;"><b>Policies and Links</b></div>
				              	&nbsp; &middot; <a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('admimViewSystemDocuments.html');">Policies &amp; Procedures</a><br/>
				              	&nbsp; &middot; <a href="http://www.ed.gov.nl.ca/edu/k12/busing/legislation.html" target="_blank">EECD Policies and Guidelines</a><br/>
				              	&nbsp; &middot; <a href="https://www.nlesd.ca/includes/files/policies/doc/1484678775720.pdf" target="_blank">EECD-902 School Bus Transportation Policy</a><br/>
				              	&nbsp; &middot; <a href="https://www.nlesd.ca/includes/files/policies/doc/1484678795422.pdf" target="_blank">EECD-903 Alternate Transportation Policy</a><br/>
				              	&nbsp; &middot; <a href="https://k12nl.maps.arcgis.com/apps/InformationLookup/index.html?appid=7a6b71de77ff4d399331ccd96974e7ed" target="_blank">School Catchment Viewer</a><br/>
				        </div>
						

									Welcome <b><%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></b> to the NLESD Busing Operator System. 
							
							<br/><br/>The Student Transportation Operator Documentation System is designed to make it easier for the Newfoundland and Labrador English School District and Student Transportation Operators to manage:
							<br/><br/><i>
							<ul>
							<li>the collection and updating of required Operator documentation;
							<li>the driver and vehicle assignments to specific routes; and
							<li>communication relating to documentation approvals/expiries and group MEMOs/messages to Operators 
							</ul></i>
							It also provides clarity for both parties regarding documentation that is on file as well as each document's status ("Approved" or "Not Approved" including why it is not approved). This system may also be a useful tool for a Operator's operation for the tracking of student transportation drivers and student transportation fleet.
						
							<br/><br/>As a contract is awarded to a Operator, the District will add it into the system including its associated routes. 
							The Operator is responsible to keep their Operator information up-to-date, listing of drivers and fleet up-to-date, upload all required documentation under the corresponding driver or vehicle in the system and ensure each route is assigned a driver and a vehicle. 
							The system will send automatic notifications to Operators for upcoming document expirations as well as District approval of drivers and vehicles.
					
						
						
							
							</div>
							
							<div class="alert alert-danger" id="display_error_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    						<div class="alert alert-success" id="display_success_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
							<div class="alert alert-warning" id="display_warning_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
							
						<br/>&nbsp;<br/> 	
							
							<div style="clear:both;"></div>
						<div class="alert alert-warning" style="display:none;"></div>
						
						<div class="alert alert-info no-print">						
						 		<div class="acrobatMessage"><a href="http://get.adobe.com/reader/"><img src="includes/img/adobereader.png" width="150" border="0"></a></div>							
	  							Documents on this site require Adobe Acrobat&reg; Reader. Adobe Acrobat Reader software is used for viewing Adobe&reg; PDF documents. 
								Click on the Get ADOBE Reader icon to download the latest Acrobat&reg; Reader for free.							
									
  					
  					</div>			
							<div align="center"><a href="/MemberServices/navigate.jsp" class="btn btn-sm btn-danger">EXIT TO STAFFROOM</a><br/><br/></div>
						</div>
					</div>
					<div class="section group">
						<div class="col full_block copyright">NLBusingApp 1.0 &copy; 2017-2023 Newfoundland and Labrador English School District</div>
					</div>
					
			</div>
		</div>
	</div>
	<script>
				$('document').ready(function(){
					var pC=${pendingContractors};
					var pE=${pendingEmployees};
					var pV=${pendingVehicles};
					
					var message = "<b>NOTICE:</b> You currently have ";
					
					if((pC+pE+pV) > 0){
						 
						
						if(pC>0){							
							var contractors = pC + " contractor(s), ";
						};
						
						
						
						if(pE>0){							
							var employees = pE + " employee(s), ";
						};
						
						
						if(pV>0){							
							var vehicles = pV + " vehicle(s), ";
						};
					
					$("#approvalmessage").html(message + contractors + employees + vehicles + " awaiting approval. Please check the corresponding menu above to review.");
					
						
						$("#mainwarning").show();
					}});
		</script>
	
	
<!-- ENABLE PRINT FORMATTING -->
	<script src="includes/js/jQuery.print.js"></script>	
  </body>

</html>	