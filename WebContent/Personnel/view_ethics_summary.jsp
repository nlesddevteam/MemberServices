<%@ page language="java"
	import="java.util.*,
                 java.text.*,
                 com.esdnl.personnel.jobs.bean.*"
	isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job"%>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="jobv2"%>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW-ETHICS-DEC" />
<html>

<head>
<title>MyHRP Applicant Profiling System</title>
<script type='text/javascript'>
		$("#loadingSpinner").css("display","none");
		</script>
		<script type="text/javascript" src="includes/js/changepopup.js"></script>
</head>
<body>
	<c:set var="now" value="<%=new java.util.Date()%>" />
	<fmt:formatDate value="${now}" pattern="yyyy" var="yearyyyy" />
	<fmt:formatDate value="${now}" pattern="yy" var="yearyy" />
<br/>
<div class="panel panel-success">
  <div class="panel-heading"><b>Code of Ethics and Conduct Summary Report</b></div>
  <div class="panel-body"><br/>
	
					<div class="alert alert-danger" id="errorText" style="display: none; text-align: center;"></div>
						
					<div class="panel panel-primary" style="width:65%;float:center;">
							  <div class="panel-heading"><b>SUMMARY STATS</b><br/></div>
							  <div class="panel-body">
										<table class="table table-sm table-striped" style="font-size:12px;">
										<tr><td style="font-weight:bold;width:70%;">Total Declarations Uploaded:</td><td style="width:30%;">${settings.totalUploaded}</td></tr>
										<tr><td style="font-weight:bold;width:70%;">Total NLESD Employees Declarations Upload:</td><td style="width:30%;">${settings.totalNLESD}</td></tr>
										<tr><td style="font-weight:bold;width:70%;">Total NON-NLESD Employees Declarations Upload:</td><td style="width:30%;">${settings.totalNonNLESD}</td></tr>
										<tr><td style="font-weight:bold;width:70%;">Total Declarations Uploaded Last 24 Hours:</td><td style="width:30%;">${settings.totalLatest}</td></tr>							
										</table>
							  </div>
					</div>
					<div style="clear:both;"></div>	
														
</div>
</div>			


</body>
</html>
