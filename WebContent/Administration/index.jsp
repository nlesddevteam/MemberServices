<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         com.awsd.school.*,
         		 com.awsd.personnel.*,                 
                 com.awsd.common.*,
         java.util.*,
         java.io.*,
         java.text.*,
         com.esdnl.util.*"%>   
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
  User usr = (User) session.getAttribute("usr");

if(usr != null)
{
  if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
  {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
}
else
{
%>  <jsp:forward page="/MemberServices/login.html">
    <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
  </jsp:forward>
<%}%>
<html>

	<head>
		<title>MemberServices Administration</title>					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  	
	
	
		
	</head>

  <body>
 
<div class="row pageBottomSpace">
  <div class="col siteBodyTextBlack">
		<div class="siteHeaderGreen">MemberServices Administration</div>
					The following options are available to administer district staff and schools.
	
	<br/><br/>
	
<esd:SecurityAccessRequired	permissions="MEMBERADMIN-VIEW">

<div align="center">
		<a href="/MemberServices/Administration/PersonnelAdmin/personnel_admin_view.jsp" class="btn btn-sm btn-primary" onclick="loadingData();"><i style="font-size:32px;"  class="fas fa-users"></i><br/>Member Account Information</a> 
		<a href="/MemberServices/Administration/SchoolAdmin/school_admin_view.jsp" class="btn btn-sm btn-primary"  onclick="loadingData();"><i style="font-size:32px;" class="fas fa-school"></i><br/>School Administration Assignment</a> 
		<a href="/MemberServices/Administration/SchoolAdmin/schoolfamilyadmin.jsp" class="btn btn-sm btn-primary" onclick="loadingData();"><i style="font-size:32px;" class="fas fa-school"></i><br/>School Family (DOS) Assignment</a>
		<a href="/MemberServices/Administration/SchoolAdmin/schoolsystemadmin.jsp" class="btn btn-sm btn-primary" onclick="loadingData();"><i style="font-size:32px;" class="fas fa-school"></i><br/>School System Assignment</a>	<br/><br/>
		<a href="/MemberServices/Administration/RolesPermissions/viewroles.jsp" class="btn btn-sm btn-danger" onclick="loadingData();"><i style="font-size:32px;" class="fas fa-user-lock"></i><br/>Security Roles</a>
		<a href="/MemberServices/Administration/RolesPermissions/viewpermissions.jsp" class="btn btn-sm btn-danger" onclick="loadingData();"><i style="font-size:32px;" class="fas fa-user-lock"></i><br/>Security Permissions</a>
		<a href="/MemberServices/Administration/viewNextLoginApp.html" class="btn btn-sm btn-danger" onclick="loadingData();"><i style="font-size:32px;"  class="fas fa-users"></i><br/>Group Startup App</a>
		<br/><br/>
		<a href="/MemberServices/Administration/SchoolStatus/closurestatusadmin.jsp" class="btn btn-sm btn-warning" onclick="loadingData();"><i style="font-size:32px;" class="fas fa-school"></i><br/>School Status Closure Codes</a>
		<a href="/MemberServices/Administration/SchoolStatus/regionalized_school_admin.jsp" class="btn btn-sm btn-warning" onclick="loadingData();"><i style="font-size:32px;" class="fas fa-school"></i><br/>School Closure Administration</a>
		<br/><br/>
		<a href="/MemberServices/navigate.jsp" class="btn btn-sm btn-danger" onclick="loadingData();"><i class="fas fa-step-backward"></i><br/>Back to Main Menu</a>
</div>

</esd:SecurityAccessRequired>		
		
		</div>
		</div>
		
							
  </body>

</html>	
			
			
			