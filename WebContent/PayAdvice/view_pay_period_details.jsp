<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         com.esdnl.payadvice.bean.*,com.esdnl.util.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>

                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<esd:SecurityCheck permissions="PAY-ADVICE-ADMIN" />
<%!
  User usr = null; 
%>
<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>NLESD - Teacher Pay Advice Manager</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		 <link rel="stylesheet" href="/MemberServices/includes/css/jquery-ui-1.10.3.custom.css" >
		<link href="includes/css/ms.css" rel="stylesheet" type="text/css">				
			<script src="/MemberServices/includes/js/jquery-1.9.1.js"></script>
			<script src="/MemberServices/includes/js/jquery-ui-1.10.3.custom.js"></script>
			<script type="text/javascript" src="/MemberServices/includes/js/common.js"></script>		
		
		<script>
		jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});
			
		
	</script>
<script type="text/javascript">
			$('document').ready(function(){
				$('tr.datalist:odd').css('background-color', '#E0E0E0');
			});
		</script>
	<script type="text/javascript">

			function startStubCreation()
			{
				ajaxRequestInfo();
				
			}

			function ajaxRequestInfo()
			{
				var sid = $.trim($('#pgid').val());
			
				var isvalid=false;
				$.ajax(
			 			{
			 				type: "POST",  
			 				url: "startProcessingNLESDPayStubCreation.html",
			 			
			 				data: {
			 					id: sid
			 				}, 
			 				success: function(xml){
			 					var i=1;
			 					$(xml).find('MESSAGE').each(function(){
										//alert($(this).find("MTEXT").text());
										$("#divsc").hide();
										$("#divsctext").show();
										document.getElementById("spsctext").innerHTML=$(this).find("MTEXT").text();
										isvalid=true;
			 						});
								},
			 				  error: function(xhr, textStatus, error){
			 				      alert(xhr.statusText);
			 				      alert(textStatus);
			 				      alert(error);
			 				  },
			 				dataType: "text",
			 				async: false
			 			}
			 		);
				return isvalid;
				}
			function closepayperiod()
			{
				ajaxRequestInfoE();
				
			}
			function ajaxRequestInfoE()
			{
				var sid = $.trim($('#pgid').val());
			
				var isvalid=false;
				$.ajax(
			 			{
			 				type: "POST",  
			 				url: "closeNLESDPayAdvicePayPeriod.html",
			 			
			 				data: {
			 					id: sid
			 				}, 
			 				success: function(xml){
			 					var i=1;
			 					$(xml).find('CLOSEDSTATUS').each(function(){
										
										//$("#dive").hide();
										//$("#divetext").show();
										//document.getElementById("etext").innerHTML=$(this).find("MTEXT").text();
										document.getElementById("started").innerHTML=$(this).find("START").text();
										document.getElementById("finished2").innerHTML=$(this).find("FINISH").text();
										document.getElementById("closedby").innerHTML=$(this).find("USER").text();
										$("#butE").hide();
										alert("Pay Period Closed");
										isvalid=true;
			 						});
								},
			 				  error: function(xhr, textStatus, error){
			 				      alert(xhr.statusText);
			 				      alert(textStatus);
			 				      alert(error);
			 				  },
			 				dataType: "text",
			 				async: false
			 			}
			 		);
				return isvalid;
				}			
</script>
	</head>

	<body>
	<br/>
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Logged in as <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="/MemberServices/includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="includes/img/header.png" alt="" width="90%" border="0"><br/>				
			</div>
			<div class="col full_block content">
				<div class="bodyText">	
			<jsp:include page="menu.jsp" />
				<br/><div align="center"><img src="/MemberServices/includes/img/bar.png" width=99% height=1></div><br/>	
				<div class="pageHeader" align="center">View Pay Period Details</div>
			<p>
										
								<table class="payrollPeriodsTableDetails">
									<tr>
										<td class='label'>Pay Begin Date:<input type='hidden' id='pgid' value='${pgbean.id}'></td>
										<td class='ppresult'>${pgbean.payBgDt}</td>
										<td class='label'>Pay End Date:</td>
										<td class='ppresult'>${pgbean.payEndDt}</td>
										<td class='label'>Check Date:</td>
										<td class='ppresult'>${pgbean.checkDt}</td>
									</tr>
									<tr>

										
										<td class='label'>Check Number:</td>
										<td class='ppresult'>${pgbean.checkNum}</td>
										<td class='label'>Pay Group:</td>
										<td  class='ppresult' colspan='3'>${pgbean.payGp}<br /></td>
										
									</tr>
									<tr><td class='header' colspan='6'>File Import Details</td></tr>
									<tr>
										<td class='label'>Import Started:</td>
										<td class='ppresult'>${ijbean.startTimeFormatted}</td>
										<td class='label'>Import Finished:</td>
										<td class='ppresult'>${ijbean.endTimeFormatted}</td>
										<td class='label'>Submitted By:</td>
										<td class='ppresult'>${ijbean.submittedBy}</td>
									</tr>
									<tr>
										<td class='label'>Mapping File:</td>
										<td class='ppresult' colspan='3'>${ijbean.mappingFileName}</td>
										<td class='label'>Status:</td>
										<td class='ppresult'>${ijbean.mappingStatus}</td>

									</tr>
									<tr>
										<td class='label'>Payroll File:</td>
										<td class='ppresult' colspan='3'>${ijbean.payrollFileName}</td>
										<td class='label'>Status:</td>
										<td class='ppresult'>${ijbean.payrollStatus}</td>

									</tr>
									<tr>
										<td class='label'>History File:</td>
										<td class='ppresult' colspan='3'>${ijbean.historyFileName}</td>
										<td class='label'>Status:</td>
										<td class='ppresult'>${ijbean.historyStatus}<br /></td>

									</tr>
									<tr><td class='header' colspan='6'>Pay Stub Creation\Email Details</td></tr>
									<tr>
										<td class='label'>Creation\Email Started:</td>
										<td class='ppresult'>${ppbean.stubCreationStartedFormatted}</td>
										<td class='label'>Creation\Email Finished:</td>
										<td class='ppresult'>${ppbean.stubCreationFinishedFormatted}</td>
										<td class='label'>Submitted By:</td>
										<td class='ppresult'>${ppbean.stubCreationBy}</td>
									</tr>
									<tr>
										<td class='label'>Emails Attempted:</td><td class='ppresult'><span id='emailssent'>${ppbean.totalPayStubs}</span></td>
										<td class='label'>Emails Sent:</td><td class='ppresult' style="color:Green;"><span id='emailssuccess'>${ppbean.totalPayStubsSent}</span></td>
										<td class='label'>Emails Failed:</td><td class='ppresult' style="color:Red;"><span id='emailsfailed'>${ppbean.totalPayStubsNotSent}</span></td>
									</tr>										
									<tr>
									<c:choose>
    										<c:when test="${ppbean.stubCreationStatus eq 'Completed'}">
    											<td colspan='2' class='ppresult' style="color:Green;">
       												Pay Stub Creation Completed
       											</td>
       											<c:choose>
    												<c:when test="${ppbean.checkForErrors}">
    													<td colspan='2' class='ppresult' style="color:Red;">
       														Process Errors (<a href='viewProcessErrors.html?payid=${ppbean.payGroupId}'>View Errors</a>)
       													</td>
       												</c:when>
       												<c:otherwise>
       													<td colspan='2' align="center" class='ppresult' style="color:Green;">
       														No Processing Errors
       													</td>
       												</c:otherwise>
       											</c:choose>
       											<c:choose>
    												<c:when test="${ppbean.manualFilename eq null}">
    													<td colspan='2' class='ppresult'>
       														<a href='startProcessingPrintMissing.html?payid=${ppbean.payGroupId}'>Create Manual Stubs</a>
       													</td>
       												</c:when>
       												<c:when test="${ppbean.manualFilename == 'CREATING'}">
       													<td colspan='2' class='ppresult' style="color:Red;">
       														Creating Manual Pay Stubs...
       														</td>
       												</c:when>
       												<c:otherwise>
       													<td colspan='2' class='ppresult'>
       														<a href='${ppbean.manualFilename}' target='_blank'>View Manual Pay Stubs</a>
       													</td>
       												</c:otherwise>
       											</c:choose>       												 
    										</c:when>
											<c:otherwise>
												<td colspan='6' align="center" class='label'>
    												<div id='divsc'>
       											 		<input type='button' value='Start Pay Stub Creation/Email' onclick="startStubCreation()" id='butSC'>
       											 	</div>
       											 	<div id='divsctext' style="display: none;">
       											 		<span id='spsctext'></span>
       											 	</div>
       											 </td>
    										</c:otherwise>
									</c:choose>
									</tr>
									<tr><td class='header' colspan='6'>Close Pay Period Details</td></tr>
									<tr>
										<td class='label'>Started:</td><td class='ppresult'><span id='started'>${ppbean.closedStartedFormatted}</span></td>
										<td class='label'>Finished:</td><td class='ppresult'><span id='finished2'>${ppbean.closedFinishedFormatted}</span></td>
										<td class='label'>Submitted By:</td><td class='ppresult' style="text-transform: capitalize;"><span id='closedby'>${ppbean.closedBy}</span></td>
									</tr>
									<tr>
									<td colspan='6' class='ppresult'>
									<c:choose>
										<c:when test="${ppbean.stubCreationStatus eq 'Completed'}">
											<c:choose>
												<c:when test="${ppbean.closedStatus eq 'Completed'}">
       												<span style="color:Green;">Pay Stub Creation Completed</span>
    											</c:when>
    											<c:when test="${ppbean.closedStatus eq 'Closed'}">
        											<span style="color:Red;">Pay Period Closed</span>
    											</c:when>
    											<c:otherwise>
       											 <div id='divse'>
       											 <input type='button' value='Close Pay Period' onclick="closepayperiod()" id='butE'>
       											 </div>
       											 <div id='divetext' style="display: none;">
       											 <span id='etext'></span>
       											 </div>
    											</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
       											<span style="color:Red;">Pay Stub Creation/Email needs to be completed first.</span>
    									</c:otherwise>	
									</c:choose>	
									</td>
									</tr>																																				
							</table>
							
<br/>
					
	</div>
</div>
			</div>

<div style="float:right;padding-right:3px;width:25%;text-align:right;"><a href="../navigate.jsp" title="Back to MemberServices Main Menu"><img src="/MemberServices/includes/img/ms-footerlogo.png" border=0></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2016 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  
<br/>
    
  </body>

</html>												