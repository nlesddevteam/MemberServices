<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*" 
         isThreadSafe="false"%>

		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<esd:SecurityCheck
	permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
<esd:SecurityRequiredPageObjectsCheck names='<%=new String[]{ "REFERENCE_BEAN", "PROFILE" }%>'
	scope='<%=PageContext.REQUEST_SCOPE%>'
	redirectTo="/Personnel/admin_index.jsp" />

<%
	NLESDReferenceAdminBean ref = (NLESDReferenceAdminBean) request.getAttribute("REFERENCE_BEAN");
  	ApplicantProfileBean profile = (ApplicantProfileBean) request.getAttribute("PROFILE");
	String val1="0";
	String val2="1";
	String val3="2";
	String val4="3";
	String val5="4";
	String refscale="3";
	if(!(ref == null)){
		refscale=ref.getReferenceScale();
	}
%>

<html>
	<head>	
	<title>MyHRP Applicant Profiling System</title>
	<script>
	$("#loadingSpinner").css("display","none");
	</script>
	<style>
		.tableTitle {font-weight:bold;width:20%;}
		.tableResult {font-weight:normal;width:80%;}
		.tableQuestionNum {font-weight:bold;width:5%;}
		.tableQuestion {width:95%;}
		.tableAnswer  {font-style: italic;color:Green;}
		.ratingQuestionNum {font-weight:bold;width:5%;}
		.ratingQuestion {width:75%;}
		.ratingAnswer {width:20%;}
		input[type="radio"] {margin-top: -1px;margin-left:6px;margin-right:2px;vertical-align: middle;}
	</style>	
	</head>
	<body>
		<%@ include file="admin_reference_questions.jsp" %>
	</body>
</html>
