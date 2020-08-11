<%@ page language="java"
	import="com.esdnl.personnel.jobs.bean.*,
         				java.util.*, 
          				java.text.*, 
          				com.awsd.pdreg.*,
          				com.awsd.security.*,
          				com.nlesd.school.bean.*,
          				com.nlesd.school.service.*,          				                               
          				org.apache.commons.lang.*"
	isThreadSafe="false"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job"%>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />

<%
	String title = (String) request.getAttribute("VacancyListTitle");
Collection<JobOpportunityBean> vacancies = (Collection<JobOpportunityBean>) request.getAttribute("VACANCIES");
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
	$(function() {

		$(".jobapps").DataTable(
				{
					"order" : [ [ 0, "desc" ] ],
					"lengthMenu" : [ [ 25, 50, 100, 200, -1 ],
							[ 25, 50, 100, 200, "All" ] ]
				});

	});
</script>
</head>
<body>

	<div class="pageHeader"><%=title%></div>

	<div class="table-responsive">

		<table class='jobapps table table-striped table-condensed' style='font-size: 12px; padding-top: 3px; border-top: 1px solid silver;'>
			<thead>
				<tr>
					<th class='tableCompNum'>Competition Number</th>
					<th class='tablePosTitle'>Position Title</th>
					<th class='tableCompEndDate'>Competition End Date</th>
					<th class='tableOptions'>Options</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (JobOpportunityBean j : vacancies) {
				%>
				<tr>
					<td><%=j.getCompetitionNumber()%></td>

					<td>
						<%
							for (JobOpportunityAssignmentBean ass : j) {
							if (!StringUtils.isEmpty(ass.getLocationText()))
								out.println("<b>" + ass.getLocationText() + "</b><br>");
						}

						out.println(j.getPositionTitle());
						%>
					</td>

					<td><%=j.getFormatedCompetitionEndDate()%></td>

					<td>
						<a class='btn btn-xs btn-warning' target="_blank" href='view_job_post.jsp?comp_num=<%=j.getCompetitionNumber()%>'>View</a>
					</td>

				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		
	</div>
	<br/>
  <div align="center"><a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back to Table</a></div>
  <br/>&nbsp;<br/>         
</body>
</html>
