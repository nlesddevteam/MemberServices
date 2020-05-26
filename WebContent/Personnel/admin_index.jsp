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
											<th rowspan='2' style='text-align:center; border-right: 5px solid #e4e4e4;'>Total<br/>Vacancies</th>
											<th colspan='3' style='text-align:center; border-right: 5px solid #e4e4e4;'>Ad Requests</th>
											<th colspan='4' style='text-align:center; border-right: 5px solid #e4e4e4;'>Recommendations</th>
											<th colspan='2' style='text-align:center;'>Positions Filled</th>
										</tr>
										<tr>
											<th scope="col" style='text-align:center;'>Submitted</th>
											<th scope="col" style='text-align:center;'>Approved</th>
											<th scope="col" style='text-align:center; border-right: 5px solid #e4e4e4;'>Posted</th>
											<th scope="col" style='text-align:center;'>Submitted</th>
											<th scope="col" style='text-align:center;'>Approved</th>
											<th scope="col" style='text-align:center;'>Accepted</th>
											<th scope="col" style='text-align:center; border-right: 5px solid #e4e4e4;'>Offered</th>
											<th scope="col" style='text-align:center;'>By Competition</th>
											<th scope="col" style='text-align:center;'>Manually</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td scope="row" style='text-align:center; border-right: 5px solid #e4e4e4;'><%= vstats.getTotalVacancies() %></td>
											<td style='text-align:center;'><%= vstats.getTotalAdSubmitted() %></td>
											<td style='text-align:center;'><%= vstats.getTotalAdApproved() %></td>
											<td style='text-align:center; border-right: 5px solid #e4e4e4;'><%= vstats.getTotalAdPosted() %></td>
											<td style='text-align:center;'><%= vstats.getTotalRecommendationSubmitted() %></td>
											<td style='text-align:center;'><%= vstats.getTotalRecommendationApproved() %></td>
											<td style='text-align:center;'><%= vstats.getTotalRecommendationAccepted() %></td>
											<td style='text-align:center; border-right: 5px solid #e4e4e4;'><%= vstats.getTotalRecommendationOffered() %></td>
											<td style='text-align:center;'><%= vstats.getTotalFilledByCompetition()  %></td>
											<td style='text-align:center;'><%= vstats.getTotalFilledManually() %></td>
										</tr>
										<tr>
											<td colspan='8' class='text-success' style='text-align:right; font-weight: bold; border-right: 5px solid #e4e4e4;'>Total Filled</td>
											<td colspan='2' class='text-success' style='text-align:center; font-weight: bold;'><%= vstats.getTotalFilledByCompetition() + vstats.getTotalFilledManually()  %></td>
										</tr>
										<tr>
											<td colspan='8' class='text-danger' style='text-align:right; font-weight: bold; border-right: 5px solid #e4e4e4;'>Total Outstanding</td>
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
											<th scope="col" style='text-align:center;'>Queued</th>
											<th scope="col" style='text-align:center;'>In-progress</th>
											<th scope="col" style='text-align:center;'>Expired</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td scope="row" style='text-align:center;'><%= stats.getOffersQueued() %></td>
											<td style='text-align:center;'><%= stats.getOffersInProgress() %></td>
											<td style='text-align:center;'><%= stats.getOffersExpired() %></td>
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
