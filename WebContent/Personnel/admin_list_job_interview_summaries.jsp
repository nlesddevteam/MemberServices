<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  java.lang.reflect.*,
                  com.esdnl.personnel.jobs.bean.*, 
                  com.esdnl.personnel.jobs.dao.*,
                  org.apache.commons.lang.*" 
         isThreadSafe="false"%>

		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />

<%
	ApplicantProfileBean profile = (ApplicantProfileBean) request.getAttribute("profile");
	JobOpportunityBean job = (JobOpportunityBean) request.getAttribute("job");
	Collection<InterviewSummaryBean> summaries = (Collection<InterviewSummaryBean>)request.getAttribute("summaries");
	InterviewGuideBean guide = null;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
	DecimalFormat df = new DecimalFormat("#,##0.00");
%>

<html>

	<head>
		<title>MyHRP Applicant Profiling System</title>	
<script>
$("#loadingSpinner").css("display","none");
</script>
	</head>
	
	<body>
	<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading">
	               	<b><%= profile.getFullNameReverse() %> - Active Interview Summary List</b>
	               	</div>
      			 	<div class="panel-body">
	                     <c:if test="${not empty msg}">
		                     <div class="alert alert-danger"> ${msg}</div>                                     
		                 </c:if>
	
								 <c:choose>
	                                  	<c:when test='${fn:length(summaries) gt 0}'>
	                                  	<table class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;">
	                      		<thead>
	                                  <tr class='header'>
	                                    <th width="25%">Applicant</th>
	                                    <th width="20%">Comp #</th>
	                                    <th width="10%">Administrative</th>
	                                    <th width="10%">Leadership</th>
	                                    <th width="15%">Created</th>
	                                    <th width="10%">Score</th>
	                                    <th width="10%">Options</th>
	                                  </tr>
	                              </thead>
	                                  		<tbody>	
	                                  	
	                                  	
	                                  	
	                                  		<% for(InterviewSummaryBean s : summaries) {
	                                  				guide = InterviewGuideManager.getInterviewGuideBean(s.getCompetition());
	                                  				%>
	                                  				
	                            
	                                  	<%if(guide != null) { %>
			                                  			<tr>
					                                      <td><%= s.getCandidate().getFullName() %></td>
					                                      <td><%= s.getCompetition().getCompetitionNumber() %></td>
					                                      <td><%= s.isAdministrative() ? "YES" : "NO" %></td>
					                                      <td><%= s.isLeadership() ? "YES" : "NO" %></td>
					                                      <td><%= sdf.format(s.getCreated()) %></td>
					                                      <td><%= df.format(s.getOverallScore(guide)) %></td>
					                                      <td><a class="btn btn-xs btn-primary" href="viewInterviewSummary.html?id=<%= s.getInterviewSummaryId() %>&comp_num_return=<%= job.getCompetitionNumber() %>">VIEW</a></td>
					                                    </tr>
			                                    <% } %>		                                    
			                                    
	                                  		<% } %>
	                                  		</tbody>
			                                    </table>	
	                                  		
	                                  	</c:when>
	                                  	<c:otherwise>
	                                  		 No interview summary records found.		                                     
	                                  	</c:otherwise>
	                                  </c:choose>
	                               
                               <div align="center">
	                             <a href="addInterviewSummary.html?applicant_id=<%= profile.getUID() %>&comp_num=<%= job.getCompetitionNumber() %>" class="btn btn-primary btn-xs">Add Summary</a>
	                             <a class="btn btn-danger btn-xs" href="javascript:history.go(-1);">Back</a></div>
	</div></div></div>                      
	</body>
</html>
