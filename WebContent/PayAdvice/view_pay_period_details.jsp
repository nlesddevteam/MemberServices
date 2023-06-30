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
   
	<script>

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
			 					 	$(".msgERR").css("display","block").append(xhr.statusText);
									$(".msgERR").css("display","block").append(textStatus);
									$(".msgERR").css("display","block").append(error);
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
										if($(this).find("MESSAGE").text() == "NO ERROR"){
											document.getElementById("started").innerHTML=$(this).find("START").text();
											document.getElementById("finished2").innerHTML=$(this).find("FINISH").text();
											document.getElementById("closedby").innerHTML=$(this).find("USER").text();
											$("#butE").hide();
											$(".msgOK").css("display","block").append("Pay Period Closed");											
											isvalid=true;
										}else{
											$(".msgERR").css("display","block").append($(this).find("MESSAGE").text());
											
											isvalid=false;
										}
										
			 						});
								},
			 				  error: function(xhr, textStatus, error){
			 					 	$(".msgERR").css("display","block").append(xhr.statusText);
									$(".msgERR").css("display","block").append(textStatus);
									$(".msgERR").css("display","block").append(error);  
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
	<div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<jsp:include page="menu.jsp" />
<div class="siteHeaderGreen">View Pay Period Details</div>
	
	
	
		
								<table class="payrollPeriodsTableDetails" style="font-size:12px;border:0px;">
															
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
									<tr><td colspan='6'>&nbsp;</td></tr>
									<tr>
									<c:choose>
    										<c:when test="${ppbean.stubCreationStatus eq 'Completed'}">
    											<td colspan='2' class='ppresult' style="color:Green;">
       												<div class="alert alert-success" style="text-align:center;font-weight:bold;">Pay Stub Creation Completed</div>
       											</td>
       											<c:choose>
    												<c:when test="${ppbean.checkForErrors}">
    													<td colspan='2' class='ppresult' style="color:Red;">
       														<div class="alert alert-danger" style="text-align:center;font-weight:bold;">Process Errors ( <a href='viewProcessErrors.html?payid=${ppbean.payGroupId}'>View Errors</a> )</div> 
       													</td>
       												</c:when>
       												<c:otherwise>
       													<td colspan='2' align="center" class='ppresult' style="color:Green;">
       														<div class="alert alert-success" style="text-align:center;font-weight:bold;">No Processing Errors</div>
       													</td>
       												</c:otherwise>
       											</c:choose>
       											<c:choose>
    												<c:when test="${ppbean.manualFilename eq null}">
    													<td colspan='2' class='ppresult' style="text-align:center;vertical-align:top;">
       														<a class="btn btn-xs btn-info" href='startProcessingPrintMissing.html?payid=${ppbean.payGroupId}'>Create Manual Stubs</a>
       													</td>
       												</c:when>
       												<c:when test="${ppbean.manualFilename == 'CREATING'}">
       													<td colspan='2' class='ppresult' style="color:Red;">
       														Creating Manual Pay Stubs...
       														</td>
       												</c:when>
       												<c:otherwise>
       													<td colspan='2' class='ppresult' style="text-align:center;vertical-align:top;">
       														<a class="btn btn-xs btn-info" href='${ppbean.manualFilename}' target='_blank'>View Manual Pay Stubs</a>
       													</td>
       												</c:otherwise>
       											</c:choose>       												 
    										</c:when>
											<c:otherwise>
												<td colspan='6' align="center" class='label'>
    												<div id='divsc'>
       											 		<input type='button' class="btn btn-xs btn-info" value='Start Pay Stub Creation/Email' onclick="startStubCreation()" id='butSC'>
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
									<tr><td colspan='6'>&nbsp;</td></tr>
									<tr>
									<td colspan='6' class='ppresult'>
									<c:choose>
										<c:when test="${ppbean.stubCreationStatus eq 'Completed'}">
											<c:choose>
												<c:when test="${ppbean.closedStatus eq 'Completed'}">
       												<div class="alert alert-success" style="text-align:center;font-weight:bold;">Pay Stub Creation Completed</div>
    											</c:when>
    											<c:when test="${ppbean.closedStatus eq 'Closed'}">
        											<div class="alert alert-danger" style="text-align:center;font-weight:bold;">PAY PERIOD CLOSED</div>
    											</c:when>
    											<c:otherwise>
       											 <div id='divse' style="text-align:center;">
       											 <input type='button' class="btn btn-sm btn-danger" value='Close Pay Period' onclick="closepayperiod()" id='butE'>
       											 </div>
       											 <div id='divetext' style="display: none;">
       											 <span id='etext'></span>
       											 </div>
    											</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
       											<div class="alert alert-danger" style="text-align:center;font-weight:bold;">NOTE: Pay Stub Creation/Email needs to be completed first.</div>
    									</c:otherwise>	
									</c:choose>	
									</td>
									</tr>																																				
							</table>
							

    </div></div></div>
    
    
  </body>

</html>												