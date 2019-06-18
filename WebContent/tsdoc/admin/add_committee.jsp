<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jstl/core_rt' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="TSDOC-ADMIN-VIEW" />

<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<meta name="menu" content="add-committee" />
		<title>Add Committee</title>
	</head>
	
	<body>
		<div class='title'><span>Add Committee</span></div><br />
	
		<div align="center">
			<form action="addCommittee.html" method="post">
				<input type='hidden' name='op' value='confirm' />
				<table  cellspacing='0' cellpadding='0' border='0' align='center'>
					<tr>
						<td class='label'>Committee Name</td>
						<td><input type='text' name='committee_name' value='' /></td>
					</tr>
					<tr>
						<td colspan='2' style='text-align:center;'>
							<input type='submit' value='add'/>
						</td>
					</tr>
					<c:if test="${not empty msg}">
						<tr class='user-message'>
							<td colspan="2">
								<c:out value="${msg}" />
							</td>
						</tr>
					</c:if>
				</table>
			</form>
		</div>
	</body>
	
</html>