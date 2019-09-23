<%@ page language ="java" session ="true" import = "com.awsd.security.*" isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck />
<%
  User usr = null; 
  usr = (User) session.getAttribute("usr");
%>
<div align="center" style="padding-top:10px;padding-bottom:10px;">
<esd:SecurityAccessRequired permissions="EECD-VIEW-APPROVALS">					 
	<a onclick="loadingData()" href='schoolAdminViewApprovals.html' class="btn btn-xs btn-success" style="color:White;" title="Approvals">Approvals</a> 
	<a onclick="loadingData()" href='viewEECD.html' class="btn btn-xs btn-primary" style="color:White;" title="View Areas">View Areas</a> 
	<a onclick="loadingData()" href='viewTeacherSelected.html' class="btn btn-xs btn-primary" style="color:White;" title="View Working Groups">View Working Groups</a>  
</esd:SecurityAccessRequired>
<esd:SecurityAccessRequired permissions="EECD-VIEW">					 
	<a onclick="loadingData()" href='viewEECD.html' class="btn btn-xs btn-primary" style="color:White;" title="View Areas">View Areas</a> 
	<a onclick="loadingData()" href='viewTeacherSelected.html' class="btn btn-xs btn-primary" style="color:White;" title="View Working Groups">View Working Groups</a>  
</esd:SecurityAccessRequired>
<esd:SecurityAccessRequired permissions="EECD-VIEW-SHORTLIST">					 
	<a onclick="loadingData()" href='adminViewAreas.html' class="btn btn-xs btn-primary" style="color:White;" title="View Areas">View Areas</a> 
	<a onclick="loadingData()" href='exportSelection.html' class="btn btn-xs btn-warning" style="color:White;" title="Export Shortlist(s)">Export Shortlist(s)</a>  
</esd:SecurityAccessRequired>
<esd:SecurityAccessRequired permissions="EECD-VIEW-APPROVALS-NO-SCHOOL"> 
	<a onclick="loadingData()" href='adminViewApprovalsNoSchool.html' class="btn btn-xs btn-success" style="color:White;" title="Approvals">Approvals No School</a> 				 
</esd:SecurityAccessRequired> 
<esd:SecurityAccessRequired permissions="EECD-VIEW-ADMIN"> 
	<a href='#' class="btn btn-xs btn-success" style="color:White;" title="Add New Area" onclick="openaddnewdialog();">+ Add New Area</a> 					 
</esd:SecurityAccessRequired>  

</div>
		 	