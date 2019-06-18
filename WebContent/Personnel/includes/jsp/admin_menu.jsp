<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		import="com.awsd.security.*"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<% 
	User usr = (User) session.getAttribute("usr"); 
%>



		<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW"/>	
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0" align="center" style="background-color: #00407A;">
   		 <tr>
      		<td>	
		 <%if(usr.checkPermission("PERSONNEL-ADMIN-VIEW")){%>
		 	
		 	<jsp:include page="admin_top_menu.jsp" flush="true"/>		 
		 		
		 <%}else if(usr.checkPermission("PERSONNEL-PRINCIPAL-VIEW")){ %>
		 
		 	<jsp:include page="principal_top_menu.jsp" flush="true"/>
		 
		 <%}else if(usr.checkPermission("PERSONNEL-VICEPRINCIPAL-VIEW")){ %>
		 
		 	<jsp:include page="asstprincipal_top_menu.jsp" flush="true"/>
		 
		 <%}%>
		 
		 	</td>
		 </tr>
		 </table>
		
