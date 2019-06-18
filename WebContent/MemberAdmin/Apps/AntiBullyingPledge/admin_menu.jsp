<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<link href="../includes/css/surveys.css" rel="stylesheet" type="text/css" />
		<title>NLESD - Bullying Pledge Admin</title>
	</head>
	
	<body>
	
		<esd:SecurityCheck permissions="SURVEY-ADMIN-VIEW" />
	
		<table cellspacing="2" width="90%" cellpadding="2" border="0" align="center" class="headerbox">
			<tr class="headertitle">
				<td>Main Menu</td>
			</tr>
			<tr>
				<td>
					
					&gt;&nbsp;<a href='index.jsp' class="menu">Home</a><br/>
					&gt;&nbsp;<a href='searchpledges.html' class="menu">Search Pledges</a>
					&gt;&nbsp;<a href='reportpledges.html' class="menu">Reports</a>
				</td>
			</tr>
		</table>
		
	</body>
</html>