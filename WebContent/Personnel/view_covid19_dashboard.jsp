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

<esd:SecurityCheck roles="ADMINISTRATOR,AD HR" />
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
  <div class="panel-heading"><b>Covid19 Dashboard Report</b></div>
  <div class="panel-body"><br/>
	
					<div class="alert alert-danger" id="errorText" style="display: none; text-align: center;"></div>
						
					<div class="panel panel-primary" style="width:48%;float:left;">
							  <div class="panel-heading"><b>EMPLOYEE STATS</b><br/><i>Includes all employees who are marked as active in SDS.</i></div>
							  <div class="panel-body">
										<table class="table table-sm table-striped" style="font-size:12px;">
										<tr><td style="font-weight:bold;width:50%;">Employees:</td><td style="width:50%;">${settings.activeEmployees}</td></tr>
										<tr><td style="font-weight:bold;width:50%;">With Profiles:</td><td style="width:50%;">${settings.nlesdTotalEmployees}</td></tr>
										<tr><td style="font-weight:bold;width:50%;">Uploaded Docs:</td><td style="width:50%;">${settings.nlesdDocumentsUploaded}</td></tr>
										<tr><td style="font-weight:bold;width:50%;">Verified Docs:</td><td style="width:50%;">${settings.nlesdDocumentsVerified}</td></tr>
										<tr><td style="font-weight:bold;width:50%;">Non-Verified Docs:</td><td style="width:50%;">${settings.nlesdDocumentsNotVerified}</td></tr>
										<tr><td style="font-weight:bold;width:50%;">Rejected Docs:</td><td style="width:50%;">${settings.nlesdDocumentsRejected}</td></tr>
										<tr><td style="font-weight:bold;width:50%;">Exempted Docs:</td><td style="width:50%;">${settings.nlesdDocumentsExemptions}</td></tr>
										<tr><td style="font-weight:bold;width:50%;">Special Status <br/> (Retired/On Leave):</td><td style="width:50%;">${settings.nlesdSpecialStatus}</td></tr>							
										</table>
							  </div>
					</div>
					<div style="width:4%;float:left;">&nbsp;</div>
					<div class="panel panel-danger" style="width:48%;float:left;">
					<div class="panel-heading"><b>NON-EMPLOYEE STATS</b><br/><i>Includes profiles with non nlesd email and updated in last six months.</i></div>
							  <div class="panel-body">
							<table class="table table-sm table-striped" style="font-size:12px;">							
							<tr><td style="font-weight:bold;width:50%;">&nbsp;</td><td style="width:50%;"></td></tr>
							<tr><td style="font-weight:bold;width:50%;">With Profiles:</td><td style="width:50%;">${settings.totalEmployees}</td></tr>
							<tr><td style="font-weight:bold;width:50%;">Uploaded Docs:</td><td style="width:50%;">${settings.documentsUploaded}</td></tr>
							<tr><td style="font-weight:bold;width:50%;">Verified Docs:</td><td style="width:50%;">${settings.documentsVerified}</td></tr>
							<tr><td style="font-weight:bold;width:50%;">Non-Verified Docs:</td><td style="width:50%;">${settings.documentsNotVerified}</td></tr>
							<tr><td style="font-weight:bold;width:50%;">Rejected Docs:</td><td style="width:50%;">${settings.documentsRejected}</td></tr>
							<tr><td style="font-weight:bold;width:50%;">Exemption Docs:</td><td style="width:50%;">${settings.documentsExemptions}</td></tr>
							<tr><td style="font-weight:bold;width:50%;">&nbsp;</td><td style="width:50%;"></td></tr>
					</table>
					</div>					
					</div>	
						<div style="clear:both;"></div>	
														
</div>
</div>			


</body>
</html>
