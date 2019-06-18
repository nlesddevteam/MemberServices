<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		import="com.awsd.security.*"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
                 
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW"/>

<% 
	User usr = (User) session.getAttribute("usr"); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Insert title here</title>
	</head>
	
	<body>
		 <%if(usr.checkPermission("PERSONNEL-ADMIN-VIEW")){%>
		 		<jsp:include page="admin_side_nav.jsp" flush="true"/>
		 <%}else if(usr.checkPermission("PERSONNEL-PRINCIPAL-VIEW")){ %>
		 		<jsp:include page="admin_principal_side_nav.jsp" flush="true"/>
		 <%}else if(usr.checkPermission("PERSONNEL-VICEPRINCIPAL-VIEW")){ %>
		 		<jsp:include page="admin_viceprincipal_side_nav.jsp" flush="true"/>
		 <%}%>
	</body>
	
</html>