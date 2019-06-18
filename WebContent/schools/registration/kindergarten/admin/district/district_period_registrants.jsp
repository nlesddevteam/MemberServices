<%@ page language="java"
         import="com.esdnl.school.registration.kindergarten.bean.*" 
         isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
                 
<esd:SecurityCheck permissions="KINDERGARTEN-REGISTRATION-ADMIN-VIEW" />

<html>
  
  <head>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <TITLE>Administration</title>
    <style type="text/css">
    	caption{
				font-weight: bold;
				text-align: left;
				color: #BF00BD;
			}
			th{
				vertical-align:bottom;
			}
    </style>
    
    <script type="text/javascript">
    	jQuery(function(){
    		
    		$('#btnSendPhysicalAddressProofReminder').click(function(){
    			if(confirm('Send Reminder?')) {
    				
    				$('#btnSendPhysicalAddressProofReminder').attr('disabled', 'disabled');
    				$.post("/MemberServices/schools/registration/kindergarten/admin/district/ajax/sendProofPhysicalAddressReminderEmail.html", 
    						{	
    							id: ${krp ne null ? krp.registrationId : 0},
    							ajax : true 
    						}, 
    						function(data){
    							if($(data).find('PHYSICAL-ADDRESS-PROOF-REMINDER-RESPONSE').length > 0) {
    								$('.divResponseMsg').html("<br/>" + $(data).find('PHYSICAL-ADDRESS-PROOF-REMINDER-RESPONSE').first().attr('msg') + "<br/>");
    							}
    							else{
    								$('#btnSendPhysicalAddressProofReminder').removeAttr('disabled');
    							}
    						}, 
    						"xml");
    			}
    		});
    		
    		$('.opbutton').button();
    		
    		$('.opbutton-delete').click(function(){
    			return confirm("Are you sure you want to delete the registrant?");
    		});
    		
    		$('tr.period-data-row:odd').css({'background-color':'#f0f0f0'})
    		
    	});
    	
    </script>
  </head>

  <body bgcolor="#BF6200">
		<div align='center' style='font-size:14pt;font-weight:bold;color:#33cc33;padding-bottom:15px;'>
	  	Kindergarten Registrants
  	</div>
  	<div class='divResponseMsg' style='font-weight:bold; color:red;' align='center'></div>
		<div>
			<table width="100%" border="0" cellspacing="0" cellpadding="5" align="center" style='border-bottom: solid 2px grey;'>
				<c:choose>
					<c:when test="${krp ne null}">
						<caption>Registration Period: ${krp.schoolYear} (<fmt:formatDate type="both" dateStyle="medium" value="${krp.startDate}" /> - <fmt:formatDate type="both" dateStyle="medium" value="${krp.endDate}" />)- <span style='color:red;'>${ fn:length(registrants)} Registrants</span></caption>
					</c:when>
					<c:when test="${sy ne null}">
						<caption>${sy} ${sch ne null ? sch.schoolName : ""} ${ss ne null ? ss.text : ""} - <span style='color:red;'>${ fn:length(registrants)} Registrants</span></caption>
					</c:when>
				</c:choose>
				<tr>
					<th style='width:130px;'>Registration Date</th>
					<th>MCP Number</th>
					<th>Student Name</th>
					<th>School</th>
					<th>Stream</th>
					<th>Physical<br/>Address<br/>Approved</th>
					<th>Registration<br/>Status</th>
					<th>Actions</th>
				</tr>
				<c:choose>
					<c:when test="${fn:length(registrants) gt 0 }">
						<c:forEach items="${registrants}" var="r">
							<tr class='period-data-row'>
								<td><fmt:formatDate value="${r.registrationDate}" pattern="MMMM dd yyyy" /><br /><fmt:formatDate value="${r.registrationDate}" pattern="hh:mm:ss a z" /></td>
								<td>${r.mcpNumber}</td>
								<td>${r.studentFullName}</td>
								<td>${r.school.schoolName}</td>
								<td>${r.schoolStream.text}</td>
								<td align='center'>${r.physicalAddressApproved ? "<span style='font-weight:bold;color:green;'>Yes</span>" : "<span style='font-weight:bold;color:red;'>No</span>"}</td>
								<td>${r.status.accepted eq true ? "<span style='font-weight:bold;color:green;'>Accepted</span>" : r.status.waitlisted eq true ? "<span style='font-weight:bold;color:orange;'>Waitlisted</span>" : "<span style='font-weight:bold;color:#9E7BFF;'>Processing</span>"}</td>
								<td>
									<a class='small opbutton' href="<c:url value='/schools/registration/kindergarten/admin/district/viewRegistrant.html?kr=${r.registrantId}' />">view</a><a class='small opbutton' href="<c:url value='/schools/registration/kindergarten/admin/district/editRegistrant.html?kr=${r.registrantId}' />">edit</a><a class='small opbutton opbutton-delete' href="<c:url value='/schools/registration/kindergarten/admin/district/deleteRegistrant.html?kr=${r.registrantId}' />">del</a>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan='6'>No kindergarten registrants found for this period.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		<br />
		<div align='center'>
			<a class='opbutton' href="<c:url value='/schools/registration/kindergarten/admin/district/index.html' />">Back to Registration Period List</a>
			<c:if test="${krp ne null}">
				<input id='btnSendPhysicalAddressProofReminder' type='button' class='opbutton'  value='Send Proof of Physical Address Reminder' />
			</c:if>
			<c:if test="${sy ne null and sch ne null}">
				<a class='opbutton' href="<c:url value='/schools/registration/kindergarten/admin/district/processRegistrations.html?sy=${sy}&sid=${sch.schoolID}&ss=${ss.value}' />">Process Registrations</a>
			</c:if>
		</div>
		<div class='divResponseMsg' style='font-weight:bold; color:red;' align='center'></div>
		<br />
	</body>
	
</html>