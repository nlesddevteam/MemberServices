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

<esd:SecurityCheck roles="ADMINISTRATOR" />

<%
	MyHrpSettingsBean settings = (MyHrpSettingsBean) request.getAttribute("settings");
%>

<c:set var='ppBlockSchools' value='<%= settings.isPpBlockSchools() %>' />

<html>

<head>
<title>MyHRP Applicant Profiling System</title>
<script type='text/javascript'>
		$("#loadingSpinner").css("display","none");
		</script>
</head>
<body>

	<div class="panel panel-success" style="margin-top: 5px;">
		<div class="panel-heading">
			<b>MyHRP Administrator Settings</b>
		</div>
		<div class="panel-body">
			Please find below options to configure the MyHR Portal. <br />
			<br />
			<div class="alert alert-danger" id="ppStatus"
				style="display: none; text-align: center;"></div>

			<form id='frm-interview-guide' action="updateMyHrpSettings.html"
				method="post">
				<input type='hidden' name='confirmed' value='true' /> &nbsp; &nbsp;
				<input type="checkbox" id="blockschools" name="blockschools"
					${ ppBlockSchools ? "CHECKED" : "" } /> Block School Administrator
				Access (Principal/Vice-Principal) to Position Planner. <br />
				<br />

				<div class="no-print" style="text-align: center;">
					<input id='btn-submit' type="submit" class="btn btn-success"
						value="Update">&nbsp; <a
						href="/MemberServices/Personnel/admin_index.jsp"
						class="btn btn-danger">Cancel</a>
				</div>

			</form>

		</div>
	</div>

	<c:choose>
		<c:when test="${ppBlockSchools eq true}">
			<script>$("#ppStatus").css("display","block").addClass("alert-warning").removeClass("alert-danger").html("Position Planner is DISABLED for School Administrators.");</script>
		</c:when>
		<c:when test="${ppBlockSchools eq false}">
			<script>$("#ppStatus").css("display","block").addClass("alert-success").removeClass("alert-danger").html("<b>WARNING:</b> Position Planner for School Administrators is currently ACTIVE.");</script>
		</c:when>
		<c:otherwise>
			<script>$("#ppStatus").css("display","block").addClass("alert-danger").removeClass("alert-success").html("<b>ERROR:</b> Position Planner status is unknown. Contact HR..");</script>
		</c:otherwise>
	</c:choose>

</body>
</html>
