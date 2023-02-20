<%@ page language="java"
	import="java.util.*,
                 java.text.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*, 
                 com.esdnl.personnel.jobs.constants.*"%>

<!-- LOAD JAVA TAG LIBRARIES -->

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job"%>

<%
	JobOpportunityBean[] jobs = null;
if (request.getAttribute("PRINCIPAL_SHORTLISTS") != null)
	jobs = (JobOpportunityBean[]) request.getAttribute("PRINCIPAL_SHORTLISTS");
%>
				 								
		<fmt:formatDate value="${cacheBuster}" pattern="MMddyyyyHms" var="todayVer" />


<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display", "none");
</script>
<script>
	$('document').ready(function() {
		$("#jobsapp").DataTable({
			"order" : [ [ 1, "asc" ] ],
			"lengthMenu" : [ [ 50, 100, 200, -1 ], [ 50, 100, 200, "All" ] ]
		});
	});
</script>

<style>
input {
	border: 1px solid silver;
}
</style>
</head>
<body>

	<esd:SecurityCheck
		permissions="PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />

	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success">
			<div class="panel-heading">
				<b>My Job Shortlists</b>
			</div>
			<div class="panel-body">
				Below is a list of positions sorted by Position Title. <br />
				<br />
				<div class="table-responsive">

					<%
						int jcnt = 0;
						if (jobs.length > 0) {
					%>

					<table id="jobsapp" class="table table-condensed table-striped"
						style="font-size: 11px; background-color: #FFFFFF;">
						<thead>
							<tr>
								<th width='15%'>COMPETITION #</th>
								<th width='50%'>POSITION TITLE</th>
								<th width='15%'>END DATE</th>
								<th width='20%'>OPTIONS</th>
							</tr>
						</thead>
						<tbody>

							<%
								for (int i = 0; i < jobs.length; i++) {
									jcnt++;
									if ((jobs[i].getJobType().getValue() != JobTypeConstant.ADMINISTRATIVE.getValue())
									|| (jobs[i].getPositionTitle().toUpperCase().indexOf("ASSISTANT") >= 0)
									|| (jobs[i].getJobType().getValue() == JobTypeConstant.TLA_REGULAR.getValue())) {
							%>
										<tr>
											<td><%=jobs[i].getCompetitionNumber()%></td>
											<td><%=jobs[i].getPositionTitle()%></td>
											<td>
											<fmt:formatDate value="<%=jobs[i].getCompetitionEndDate()%>" pattern="yyyy/MM/dd hh:mm a" var="dateToUse"/>
											${dateToUse}
											</td>
											<td><a class="btn btn-xs btn-primary"
												href='viewJobShortList.html?comp_num=<%=jobs[i].getCompetitionNumber()%>'>View
													List</a></td>
										</tr>
								<% } %>
							<% } %>
						</tbody>
					</table>
					<% } else { %> 
						<div class="alert alert-danger">No positions shortlisted at this time. Thank you.</div>
					<% } %>
				</div>
			</div>
		</div>
	</div>


</body>
</html>
