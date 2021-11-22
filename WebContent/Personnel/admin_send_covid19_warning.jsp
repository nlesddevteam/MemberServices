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

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW-COVID19-EMAIL" />

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
			<b>COVID19 Warning Email(s)</b>
		</div>
		<div class="panel-body">
			This screen can be used to send a warning email to all employees who do not currently have a COVID19 Vaccination file uploaded, who do not have<br />
			a Applicant profile completed or do not have an applicant profile correctly linked to their SDS record.
			<br />
			<div class="alert alert-danger" id="ppStatus"
				style="display: none; text-align: center;"></div>

			<form id='frm-interview-guide' action="sendCovid19EmailList.html" method="post">
				<c:choose>
                <c:when test='${ cid eq null }'>
                  							
				<div class="no-print" style="text-align: center;">
				<br />
					<input id='btn-submit' type="submit" class="btn btn-success"
						value="Send Warning Email(s)" onclick="loadingData()">&nbsp; <a
						href="/MemberServices/Personnel/admin_index.jsp"
						class="btn btn-danger">Cancel</a>
				</div>
				</c:when>
				<c:otherwise>
					<div class="no-print" style="text-align: center;">
					<br />
					<br />
						<script>$("#loadingSpinner").css("display","none");</script>
						The system sent warning message(s) to ${emailcount} employee(s).  To download a csv file of the employee list, click 
						<a href="viewCovid19EmailListCSV.html?cid=${cid}"> here </a>.  Due to some<br />
						employees having multiple locations in SDS, the file could contain more records than emails sent.
					</div>
				</c:otherwise>
				</c:choose>

			</form>

		</div>
	</div>
	

</body>
</html>
