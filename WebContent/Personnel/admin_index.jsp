<!-- MyHRP (C) 2018  -->	
<!-- APPLICATION FOR NLESD STAFF (MEMBER) SERVICES -->
<!-- HTML 5 BOOTSTRAP 3.3.7 JQUERY 3.3.1 -->

<%@ page language ="java" session = "true" import = "com.awsd.security.*, com.esdnl.personnel.jobs.bean.*, com.esdnl.personnel.jobs.dao.*" isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW,RTH-NEW-REQUEST,PERSONNEL-RTH-VIEW-APPROVALS" />	

<%
  User usr = (User) session.getAttribute("usr");

	RecommendationStatisticsBean stats = RecommendationStatisticsManager.getRecommendationStatisticsBean();
	TeacherAllocationVacancyStatisticsBean vstats = TeacherAllocationVacancyStatisticsManager.getVacancyStats("2020-21");
%>

<html>
	<head>		
		<meta name="viewport" content="width=device-width, initial-scale=1.0">	
	</head>

	<body>
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">					
					
					<div class="pageHeader">Human Resources Administration</div>
					<div class="pageBody">
					Welcome <span style="text-transform:capitalize;"><%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></span> to your 
					NLESD Applicant Profiling System Adminstration Site. Please use the navigation menu above to continue. Not all menu items are available to all users. You will only see options available for your current job position. 
					
					<br/>&nbsp;<br/>
					<b>NOTICE: </b>Due to the information and data layout this application provides, we advise using a tablet or laptop/desktop computer to use this system.
					
					<br/>&nbsp;<br/>
					
					<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">
					<div class="container">
						<div class='row '>
							<div class='col col-md-12'>
								<table class="table table-sm table-striped table-bordered">
									<caption><%= vstats.getSchoolYear() %> Vacancy Processing Statistics</caption>
									<thead>
										<tr>
											<th scope="col">Vacancies</th>
											<th scope="col">Ad Submitted</th>
											<th scope="col">Ad Approved</th>
											<th scope="col">Ad Posted</th>
											<th scope="col">Rec Submitted</th>
											<th scope="col">Rec Approved</th>
											<th scope="col">Rec Accepted</th>
											<th scope="col">Rec Offered</th>
											<th scope="col">Filled By Competition</th>
											<th scope="col">Filled Manually</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td scope="row"><%= vstats.getTotalVacancies() %></td>
											<td><%= vstats.getTotalAdSubmitted() %></td>
											<td><%= vstats.getTotalAdApproved() %></td>
											<td><%= vstats.getTotalAdPosted() %></td>
											<td><%= vstats.getTotalRecommendationSubmitted() %></td>
											<td><%= vstats.getTotalRecommendationApproved() %></td>
											<td><%= vstats.getTotalRecommendationAccepted() %></td>
											<td><%= vstats.getTotalRecommendationOffered() %></td>
											<td><%= vstats.getTotalFilledByCompetition()  %></td>
											<td><%= vstats.getTotalFilledManually() %></td>
										</tr>
										<tr>
											<td colspan='8' class='text-success' style='text-align:right; font-weight: bold;'>Total Filled</td>
											<td colspan='2' class='text-success' style='text-align:center; font-weight: bold;'><%= vstats.getTotalFilledByCompetition() + vstats.getTotalFilledManually()  %></td>
										</tr>
										<tr>
											<td colspan='8' class='text-danger' style='text-align:right; font-weight: bold;'>Total Outstanding</td>
											<td colspan='2' class='text-danger' style='text-align:center; font-weight: bold;'><%= vstats.getTotalVacancies() - (vstats.getTotalFilledByCompetition() + vstats.getTotalFilledManually()) %></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
						<div class='row '>
							<div class='col col-md-6'>
								<table class="table table-sm table-striped table-bordered">
									<caption>Position Offer Statistics</caption>
									<thead>
										<tr>
											<th scope="col">Queued</th>
											<th scope="col">In-progress</th>
											<th scope="col">Expired</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td scope="row"><%= stats.getOffersQueued() %></td>
											<td><%= stats.getOffersInProgress() %></td>
											<td><%= stats.getOffersExpired() %></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					</esd:SecurityAccessRequired>
					
					<div class="alert alert-warning" style="text-align:center"><b>****** ACCESS TO INFORMATION PRIVACY NOTICE / WARNING ******</b><br/>
					The NLESD is committed to the protection of all personal information collected, used and/or disclosed in the operation and management of its activities.  
					As a  public body, all information collected, used and/or disclosed will be in accordance with Access to Information and Protection of Privacy Act,2015.
					By continuing to use this site, you agree with the above Act.</div>
										
					
					 <c:if test="${ msg ne null }">  
                   		<div class="alert alert-danger" id="memo_error_message" style="margin-top:10px;margin-bottom:10px;padding:5px;">${ msg } </div>     
                  	</c:if>					
					
					</div>	
			</div>			
		</div>	
  
  </body>
</html>
