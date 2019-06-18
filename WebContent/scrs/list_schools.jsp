<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/scrs.tld" prefix="b" %>

<esd:SecurityCheck permissions='BULLYING-ANALYSIS-ADMIN-VIEW' />

<c:url value="listAnalysisReports.html" var="listAnalysisReportsURL" />

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
	<table cellspacing="4" cellpadding="" border="0" align="center" width="80%">
		<tr>
			<td><a href="index.html" class="menu">add incident</a>&nbsp;|&nbsp;<a href="${listAnalysisReportsURL}" class="menu">list Analysis Reports</a></td>
		</tr>
	</table>
	<form action="" method="post">
		<table cellspacing="4" cellpadding="4" border="0" align="center" width="80%" class="inputform">
			<tr class="heading">
				<td colspan="5" class='submitstyle heading1' style="color:white;">School Incident Count</td>
			</tr>
			
			<tr class="header">
				<th>School</th><th>Total Count</th><th>Monthly Count</th><th>Weekly Count</th><th></th>
			</tr>
			
			<c:forEach items="${counts}" var="c" >
				<tr>
					<td>${c.school.schoolName}</td>
					<td>${c.overallIncidentCount}</td>
					<td>${c.monthlyIncidentCount}</td>
					<td>${(c.weeklyIncidentCount > 0) ? c.weeklyIncidentCount : c.noIncidentsReported ? 0 : "<SPAN class='noSchoolResponse'>NO SCHOOL RESPONSE</span>" }</td>
					
					<td align="center">
						<c:if test="${c.overallIncidentCount gt 0}">
							<a href="listSchoolIncidentReports.html?schoolId=${c.school.schoolID}" class="menu">list incidents</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			
			<tr>
				<td style="font-size:14px;font-weight:bold;">Total</td>
				<td style="font-size:14px;font-weight:bold;">${overallTotal}</td>
				<td style="font-size:14px;font-weight:bold;">${monthlyTotal}</td>
				<td style="font-size:14px;font-weight:bold;">${weeklyTotal}</td>
				<td align="center">&nbsp;</td>
			</tr>
		</table>
	</form>
</body>
</html>