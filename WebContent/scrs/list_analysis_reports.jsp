<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/scrs.tld" prefix="b" %>

<esd:SecurityCheck permissions='BULLYING-ANALYSIS-ADMIN-VIEW' />

<c:url value="listSchools.html" var="listSchoolsURL" />
<c:url value="listAnalysisReports.html" var="listAnalysisReportsURL" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="-1" />
 
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
			<td style='color:#000000;'><a href="index.html" class="menu">add incident</a>&nbsp;|&nbsp;<a href="${listSchoolsURL}" class="menu">list schools</a>&nbsp;|&nbsp;<a href="${listAnalysisReportsURL}" class="menu">list Analysis Reports</a></td>
		</tr>
	</table>
	<form action="" method="post">
		<table cellspacing="4" cellpadding="4" border="0" align="center" width="80%" class="inputform">
			<tr class="heading">
				<td class='submitstyle heading1' style="color:white;" colspan='3'>Analysis Reports</td>
			</tr>
			
			<tr class="header">
				<th style='text-align:center;' width="33%">District</th>
				<th style='text-align:center;' width="33%">Regional</th>
				<th style='text-align:center;' width="*">School</th>
			</tr>
			
			
			<tr>
				<td valign='top'>
					Monthly Report:<br/>
					<ul>
						<li><a class="menu" href='<c:url value="/scrs/incidentFrequencyByDistrictReport.html" />'>Incident Frequency</a></li>
						<li><a class="menu" href='<c:url value="/scrs/genderIncidentFrequencyByDistrictReport.html" />'>Incident Frequency By Gender</a></li>
						<li><a class="menu" href='<c:url value="/scrs/gradeIncidentFrequencyByDistrictReport.html" />'>Incident Frequency By Grade</a></li>
						<li><a class="menu" href='<c:url value="/scrs/gradeCategoryIncidentFrequencyByDistrictReport.html" />'>Incident Frequency By Grade Category</a></li>
						<li><a class="menu" href='<c:url value="/scrs/locationIncidentFrequencyByDistrictReport.html" />'>Incident Frequency Location</a></li>
						<li><a class="menu" href='<c:url value="/scrs/timeIncidentFrequencyByDistrictReport.html" />'>Incident Frequency Time</a></li>
						<li><a class="menu" href='<c:url value="/scrs/typeIncidentFrequencyByDistrictReport.html" />'>Incident Frequency Type</a></li>
					</ul>
				</td>
				<td valign='top'>
					Monthly Report:<br/>
					<ul>
						<li><a class="menu" href='<c:url value="/scrs/incidentFrequencyByRegionReport.html" />'>Incident Frequency</a></li>
					</ul>
				</td>
				<td valign='top'>
					Monthly Report:<br/>
					<ul>
						<li><a class="menu" href='<c:url value="/scrs/incidentFrequencyBySchoolReport.html" />'>Incident Frequency</a></li>
					</ul>
				</td>
			</tr>
			
		</table>
	</form>
</body>
</html>