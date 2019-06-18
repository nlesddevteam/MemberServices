<%@ page language="java" contentType="text/html" %>
<%@ taglib uri='http://java.sun.com/jstl/core_rt' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions='BULLYING-ANALYSIS-SCHOOL-VIEW,BULLYING-ANALYSIS-ADMIN-VIEW' />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ESD Bullying Data Analysis System</title>
		<link href="<c:url value='/scrs/includes/css/esd.css' />" rel="stylesheet" type="text/css" />
		<link href="<c:url value='/scrs/includes/css/ui-lightness/jquery-ui-1.9.0.custom.min.css' />" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<c:url value='/scrs/includes/js/jquery-1.8.2.min.js' />"></script>
		<script type="text/javascript" src="<c:url value='/scrs/includes/js/jquery-ui-1.9.0.custom.min.js' />"></script>
		<decorator:head />
	</head>

	<body style="margin: 10px;">
	
		<table width="955" border="0" cellspacing="0" cellpadding="0" align="center" class="maintable">
			<tr>
				<td></td>
			</tr>
			<tr style="background: #FF8000;">
				<td><div align="right" class="date">&nbsp;<SCRIPT type="text/javascript" src="<c:url value='/scrs/includes/js/date.js' />"></script>&nbsp;</div></td>
			</tr>
			<tr>
				<td><div align="left" class="headingtitle1">&nbsp;Newfoundland and Labrador English School District</div></td>
			</tr>
			<tr>
				<td><div align="center"><img src="<c:url value='/scrs/includes/img/header.jpg' />" alt="" width="955" border="0" /></div></td>
			</tr>
			<tr>
				<td><div align="right" class="headingtitle1">Student Conduct Reporting System&nbsp;</div></td>
			</tr>
			<tr>
				<td>
					<table align="center" width="100%" cellspacing="2" cellpadding="2" border="0" class="maintablebody">
						<tr>
							<td>
								<!-- Content Here -->
								
								 <decorator:body />
								
								<!-- End Content -->
							</td>
						</tr>
					</table>	
				</td>
			</tr>
			<tr>
				<td><div align="center"><img src="<c:url value='/scrs/includes/img/footer.png' />" alt="" width="955" height="157" border="0" /></div></td>
			</tr>
			<tr style="background: #FF8000;">
				<td ><div align="center" class="copyright">&copy; 2013 Newfoundland and Labrador English School District &middot; All Rights Reserved</div></td>
			</tr>
		</table>
		
	</body>
</html>
