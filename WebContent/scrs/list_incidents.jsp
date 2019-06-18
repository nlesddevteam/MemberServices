<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/scrs.tld" prefix="b" %>

<esd:SecurityCheck permissions='BULLYING-ANALYSIS-SCHOOL-VIEW,BULLYING-ANALYSIS-ADMIN-VIEW' />

<c:url value="listSchools.html" var="listSchoolsURL" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>List Incident Reports</title>
	
	<script type="text/javascript">
		$('document').ready(function(){
			$('.inputform tr:even').css({'background-color':'white'});
			$('.inputform tr:odd:not(.header)').css({'background-color':'#F4F4F4'});
			$('.inputform tr:not(.heading, .error) td').css({'border':'solid 1px #c4c4c4'});
		});
	</script>
</head>
<body>
	<table cellspacing="4" cellpadding="" border="0" align="center" width="95%">
		<tr>
			<td>
				<a href="index.html" class="menu">add incident</a>
				<esd:SecurityAccessRequired permissions="BULLYING-ANALYSIS-ADMIN-VIEW">
					&nbsp;|&nbsp;<a href="${listSchoolsURL}" class="menu">list schools</a>
				</esd:SecurityAccessRequired>
			</td>
		</tr>
	</table>
	<form action="addIncidentReport.html" method="post">
		<table cellspacing="4" cellpadding="4" border="0" align="center" width="95%" class="inputform">
			<tr class="heading">
				<td colspan="5" class='submitstyle heading1' style="color:white;">List Incident Reports - ${school.schoolName}</td>
			</tr>
			
			<tr class="header">
				<th>Submited By</th><th>Submitted Date</th><th>Incident Date</th><th>Student</th><th>&nbsp;</th>
			</tr>
			
			<c:choose>
				<c:when test="${ fn:length(incidents) gt 0 }">
					<c:forEach items="${incidents}" var="incident" >
						<tr>
							<td>${incident.submittedBy.fullName}</td>
							<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${incident.submittedDate}" /></td>
							<td><fmt:formatDate type="date" dateStyle="short" value="${incident.incidentDate}" /></td>
							<td>${incident.student.lastName}, ${incident.student.firstName} ${ not empty incident.student.middleName ? incident.student.middleName : '' } (${ incident.student.studentId })</td>
							<td align="center"><a href="viewIncidentReport.html?id=${incident.incidentId}" class="menu">view report</a></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan='5'>No incidents reported</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
	</form>
</body>
</html>