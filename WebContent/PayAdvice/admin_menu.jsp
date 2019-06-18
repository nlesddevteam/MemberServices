<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<esd:SecurityCheck permissions="PAY-ADVICE-ADMIN" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<link href="includes/css/surveys.css" rel="stylesheet" type="text/css" />
		<title>NLESD - Pay Advice System</title>
	</head>
	
	<body>
	
		<table cellspacing="2" width="90%" cellpadding="2" border="0" align="center" class="headerbox">
			<tr class="headertitle">
				<td>Main Menu</td>
			</tr>
			<tr>
				<td>
					&gt;&nbsp;<a href='index.jsp' class="menu">Home</a><br/>
					&gt;&nbsp;<a href='upload_files.jsp' class="menu">Upload File</a><br/>
					&gt;&nbsp;<a href='viewUnprocessedNLESDPayrollDocuments.html' class="menu">Unprocessed Files</a><br/>
					&gt;&nbsp;<a href='viewNLESDPayAdviceImportJobs.html' class="menu">View Import Jobs</a><br/>
					&gt;&nbsp;<a href='viewNLESDPayAdvicePayPeriodsListAdmin.html' class="menu">View Pay Periods</a><br/>
					&gt;&nbsp;<a href='viewNLESDPayAdviceSearch.html' class="menu">Search Employees</a><br/>
					
					
				</td>
			</tr>
		</table>
		
	</body>
</html>