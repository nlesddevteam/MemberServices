<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	<title>${title}</title>
	<style type="text/css">
		img { padding: 5px; }
	</style>
</head>
<body>
	<br />
	<table cellspacing="4" cellpadding="4" border="0" align="center"  class="inputform">
		<tr>
			<td><a href="index.html" class="menu">add incident</a>&nbsp;|&nbsp;<a href="${listSchoolsURL}" class="menu">list schools</a>&nbsp;|&nbsp;<a href="${listAnalysisReportsURL}" class="menu">list Analysis Reports</a></td>
		</tr>
		<tr class="heading">
			<td class='submitstyle heading1' style="color:white;">${TITLE}</td>
		</tr>
		<tr>
			<td align='center'>
				<c:choose>
					<c:when test="${fn:length(CHARTS) gt 0}">
						<c:forEach items="${CHARTS}" var="c">
							<img src="<c:url value='/bullying/includes/images/charts/${c}' />" /><br />	
						</c:forEach>
					</c:when>
					<c:otherwise>
						<img src="<c:url value='/bullying/includes/images/charts/${CHART}' />" />
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
</body>
</html>