<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*" 
         isThreadSafe="false"%>

		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />

<%
	JobOpportunityBean job = null;
	TeacherRecommendationBean[] rec = null;
	if(request.getAttribute("comp") == null){
		job = JobOpportunityManager.getJobOpportunityBean(request.getParameter("comp_num"));
	}
	else{
		job = (JobOpportunityBean) request.getAttribute("comp");
	}
	
	if(request.getAttribute("recs") == null){
		rec = RecommendationManager.getTeacherRecommendationBean(request.getParameter("comp_num")); 
	}
	else{
		rec = (TeacherRecommendationBean[]) request.getAttribute("recs");
	}
%>

<html>
<head>
	<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>

	<script type="text/javascript">
		function confirmRecDelete(){
			if(confirm('Are you sure you want to delete this recommendation?'))
				return true;
			else
				return false;
		}
	</script> 
</head>
<body>
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success">
			<div class="panel-heading">
				<b><%=job.getCompetitionNumber()%> Recommendation(s)</b>
			</div>
			<div class="panel-body">
				<div class="table-responsive">


					<% if(request.getAttribute("msg") != null){%>
						<div class="alert alert-danger" align="center"><%=(String)request.getAttribute("msg")%></div>
					<%}%>

					<% if(rec.length > 0) { %>
						<table id="jobsapp" class="table table-condensed table-striped" style="font-size: 11px; background-color: #FFFFFF;">
							<thead>
								<tr>
									<th width='25%'>RECOMMENDATION DATE</th>
									<th width='50%'>CANDIDATE</th>
									<th width='15%'>STATUS</th>
									<th width='10%'>OPTIONS</th>
								</tr>
							</thead>
							<tbody>
								<%
									boolean all_expired = true;
									boolean existing_rec = false;
									boolean existing_non_processed_rec = false;
									for (int i = 0; i < rec.length; i++) {
										if (!rec[i].isExpired()) {
											all_expired = false;
										}
										if (!existing_rec || !existing_non_processed_rec) {
											if (!rec[i].getCurrentStatus().equals(RecommendationStatus.REJECTED) 
													&& !rec[i].getCurrentStatus().equals(RecommendationStatus.OFFER_REJECTED)
													&& !rec[i].isOfferIgnored()) {
												existing_rec = true;
												
												if(!rec[i].getCurrentStatus().equal(RecommendationStatus.PROCESSED)) {
													existing_non_processed_rec = true;
												}
											}
										}
								%>
									<tr>
										<td><%=rec[i].getRecommendedDateFormatted()%></td>
										<td><%=rec[i].getCandidate().getFullNameReverse()%></td>
										<td><%=(!rec[i].isOfferIgnored()? rec[i].getCurrentStatus().getDescription():"<SPAN style='color:#FF0000;'>EXPIRED</SPAN>")%></td>
										<td>
											<a class="btn btn-xs btn-primary" href='viewJobTeacherRecommendation.html?id=<%=rec[i].getRecommendationId()%>'>VIEW</a>
											<esd:SecurityAccessRequired permissions='PERSONNEL-ADMIN-DELETE-RECOMMENDATION'>
												<a class="btn btn-xs btn-danger"
													onclick="return confirmRecDelete();"
													href='deleteJobTeacherRecommendation.html?id=<%=rec[i].getRecommendationId()%>'>DEL</a>
											</esd:SecurityAccessRequired>
										</td>
									</tr> 
								<% } %>
							</tbody>
						</table>
	
						<div align="center">
							<a class="btn btn-xs btn-info" href='view_job_post.jsp?comp_num=<%=job.getCompetitionNumber()%>'>View Job Post</a>
							<% if(!job.isAwarded()) { %>
								<% if((all_expired && !existing_rec) || (job.isReopened() && !existing_non_processed_rec)) { %>
									<a class="btn btn-xs btn-primary" href='addJobTeacherRecommendation.html?comp_num=<%=job.getCompetitionNumber()%>'>Make Recommendation</a>
								<% } %>
							<% } else { %>
								<esd:SecurityAccessRequired roles="ADMINISTRATOR,SEO - PERSONNEL">
									<a class="btn btn-xs btn-primary" href='reopenCompetition.html?comp_num=<%=job.getCompetitionNumber()%>'>Reopen Competition</a>
								</esd:SecurityAccessRequired>
							<% } %>
							<a class="btn btn-danger btn-xs" href="javascript:history.go(-1);">Back</a>
						</div>
					<%} else {%>
						<p class='alert alert-danger'>No recommendations currently on file.</p>
					<%} %>
					
				</div>
			</div>
		</div>
	</div>

</body>
</html>
