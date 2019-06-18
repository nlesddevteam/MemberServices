<%@ page language="java"
         import="com.esdnl.school.registration.kindergarten.bean.*" 
         isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
                 
<esd:SecurityCheck permissions="KINDERGARTEN-REGISTRATION-SCHOOL-VIEW" />

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
    		
    		$('.opbutton').button();
    		
    		$('tr.period-data-row:odd').css({'background-color':'#f0f0f0'})
    		
    		$('input[type=text], select').css({'border' : 'solid 1px #7B9EBD', 'background-color' : '#FFFFCC'});
    		
    	});
    	
    </script>
  </head>

  <body bgcolor="#BF6200">
		<div align='center' style='font-size:14pt;font-weight:bold;color:#33cc33;padding-bottom:15px;'>
	  	${sch ne null ? sch.schoolName : ""} Kindergarten Registrants
  	</div>
		<div id='list-registrants-by-form' class='form-panel' style='width:100%; display:inline;'>
			<form method='post' action="<c:url value='/schools/registration/kindergarten/admin/school/index.html'/>">
				<table align='center' cellspacing='2' cellpadding='2'>
					<caption style='color:#333333;text-align:center;'>View Registrants By...</caption>
					<tr>
						<td class='label required'>School Year:</td>
						<td><sreg:RegistrationSchoolYearsDDL id='ddl_SchoolYear' offset='1' listAll='true' value='${sy}' /></td>
					</tr>
					<tr>
						<td class='label required'>Stream:</td>
						<td><sreg:SchoolStreamDDL cls='required' id='ddl_Stream' value="${ss ne null ? ss.value : 0}" /></td>
					</tr>
					<tr>
						<td colspan='2' align='right'>
							<c:if test="${krp ne null}">
								<a href="<c:url value='/schools/registration/kindergarten/admin/school/addKindergartenRegistrant.html?id=${ krp.registrationId }'/>" class='opbutton small'>Add New Registrant</a>
							</c:if>
							<input type='submit' value='View Registrants' class='opbutton small' />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div>
			<table width="100%" border="0" cellspacing="0" cellpadding="5" align="center" style='border-bottom: solid 2px grey;'>
				<c:choose>
					<c:when test="${sy ne null}">
						<caption>${sy} ${ss ne null ? ss.text : ""} - <span style='color:red;'>${ fn:length(registrants)} Registrants</span></caption>
					</c:when>
				</c:choose>
				<tr>
					<th>MCP Number</th>
					<th>Student Name</th>
					<th>Stream</th>
					<th>Physical<br/>Address<br/>Approved</th>
					<th>Registration<br/>Status</th>
					<th>Actions</th>
				</tr>
				<c:choose>
					<c:when test="${fn:length(registrants) gt 0 }">
						<c:forEach items="${registrants}" var="r">
							<tr class='period-data-row'>
								<td>${r.mcpNumber}</td>
								<td>${r.studentFullName}</td>
								<td>${r.schoolStream.text}</td>
								<td align='center'>${r.physicalAddressApproved ? "<span style='font-weight:bold;color:green;'>Yes</span>" : "<span style='font-weight:bold;color:red;'>No</span>"}</td>
								<td>${r.status.accepted eq true ? "<span style='font-weight:bold;color:green;'>Accepted</span>" : r.status.waitlisted eq true ? "<span style='font-weight:bold;color:orange;'>Waitlisted</span>" : "<span style='font-weight:bold;color:#9E7BFF;'>Processing</span>"}</td>
								<td>
									<a class='opbutton small' href="<c:url value='/schools/registration/kindergarten/admin/school/viewRegistrant.html?kr=${r.registrantId}' />">view</a>
									<a class='opbutton small' href="<c:url value='/schools/registration/kindergarten/admin/school/editKindergartenRegistrant.html?kr=${r.registrantId}' />">edit</a>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan='5'>No kindergarten registrants found.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		<br />
		<br />
	</body>
	
</html>