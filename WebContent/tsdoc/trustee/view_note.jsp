<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jstl/core_rt' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="TSDOC-TRUSTEE-VIEW" />

<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<meta name="menu" content="home" />
		<title>View Note</title>
	</head>
	
	<body>
		<div class='title'><span>View Note</span></div>
		<br />
		<table width='100%' cellspacing='0' cellpadding='0' border='0'>
			<c:if test="${not empty msg}">
				<tr>
					<td class='user-message'>
						<c:out value="${msg}" />
					</td>
				</tr>
			</c:if>
			<tr>
				<td>
					<table cellspacing='0' cellpadding='0' border='0' class='data-list'>
						<CAPTION>${note.noteTitle}</CAPTION>
						<THEAD>
							<tr>
								<th>Added on ${note.dateAdded}</th>
							</tr>
						</THEAD>
						<TBODY>
							<tr class='note-view'>
								<td>${note.noteHTML}</td>
							</tr>
						</TBODY>
						<TFOOT>
							<tr><td>&nbsp;</td></tr>
						</TFOOT>				
					</table>
				</td>
			</tr>
		</table>
	</body>
	
</html>