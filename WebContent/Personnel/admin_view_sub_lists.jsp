<%@ page language="java"
         import="com.esdnl.personnel.jobs.constants.*"
         isThreadSafe="false"%>

		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<%
SubstituteListConstant type = null;

if(request.getParameter("type") != null){
	type = SubstituteListConstant.get(Integer.parseInt(request.getParameter("type")));
}else{
	if(request.getAttribute("type") != null){
		type = (SubstituteListConstant) request.getAttribute("type");
	}else{
		type = SubstituteListConstant.get(1);
	}
}
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>
</head>
<body>
  
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
  

<job:SubLists type='<%=type%>' />
                              
</body>
</html>
