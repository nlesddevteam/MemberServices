<!-- MyHRP (C) 2018  -->	
<!-- APPLICATION FOR NLESD STAFF (MEMBER) SERVICES -->
<!-- HTML 5 BOOTSTRAP 3.3.7 JQUERY 3.3.1 -->

<%@ page language ="java" session = "true" isThreadSafe="false"%>
<%@ page import = "java.util.*, 
									java.util.stream.*, 
									com.awsd.security.*, 
									com.esdnl.personnel.jobs.bean.*, 
									com.esdnl.personnel.jobs.dao.*, 
									com.nlesd.school.bean.*, 
									com.nlesd.school.service.*,
									org.apache.commons.lang.*" %>

<!-- LOAD JAVA TAG LIBRARIES -->
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW,RTH-NEW-REQUEST,PERSONNEL-RTH-VIEW-APPROVALS" />	

<%
  User usr = (User) session.getAttribute("usr");

	String statsSchoolYear = "2020-21";
	RecommendationStatisticsBean stats = RecommendationStatisticsManager.getRecommendationStatisticsBean();
	Map<SchoolZoneBean,TeacherAllocationVacancyStatisticsBean> vacancyStatsByRegion = TeacherAllocationVacancyStatisticsManager.getVacancyStatsByRegion(statsSchoolYear);
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
					<div class="container" style='margin-left: 0px;padding-left: 0px;'>
						<div class='row'>
							<div class='col col-md-12 col-sm-12 col-lg-12'>
								<table class="table table-sm table-striped table-bordered">
									<caption><%= statsSchoolYear %> Vacancy Processing Statistics</caption>
									<thead>
										<tr>
											<th rowspan='2' style='text-align:center;'>Region</th>
											<th rowspan='2' style='text-align:center; border-right: 5px solid #e4e4e4;'>Total<br/>Vacancies</th>
											<th colspan='3' style='text-align:center; border-right: 5px solid #e4e4e4;'>Ad Requests</th>
											<th colspan='2' style='text-align:center; border-right: 5px solid #e4e4e4;'>Shortlists</th>
											<th colspan='7' style='text-align:center; border-right: 5px solid #e4e4e4;'>Recommendations</th>
											<th colspan='4' style='text-align:center;'>Positions Filled</th>
										</tr>
										<tr>
											<th scope="col" style='text-align:center;'>Sub</th>
											<th scope="col" style='text-align:center;'>App</th>
											<th scope="col" style='text-align:center; border-right: 5px solid #e4e4e4;'>Pst</th>
											<th scope="col" style='text-align:center;'>No</th>
											<th scope="col" style='text-align:center; border-right: 5px solid #e4e4e4; white-space: nowrap;'>> 48 Hrs<br/>No Rec</th>
											<th scope="col" style='text-align:center;'>Sub</th>
											<th scope="col" style='text-align:center;'>App</th>
											<th scope="col" style='text-align:center;'>Acc</th>
											<th scope="col" style='text-align:center;'>Off</th>
											<th scope="col" style='text-align:center;'>Off Acc</th>
											<th scope="col" style='text-align:center;'>Off Rej</th>
											<th scope="col" style='text-align:center; border-right: 5px solid #e4e4e4;'>Off Exp</th>
											<th scope="col" style='text-align:center;'>Comp</th>
											<th scope="col" style='text-align:center;'>Manual</th>
											<th scope="col" style='text-align:center;'>Total</th>
											<th scope="col" style='text-align:center;'>Left</th>
										</tr>
									</thead>
									<tbody>
										<% 
											int totalVacancies = 0, totalFilledByCompetition = 0, totalFilledManually = 0;
											int totalAdSubmitted = 0, totalAdApproved = 0, totalAdPosted = 0, totalNoShortlist = 0, totalShortlistedNoRecommendation = 0;
											int totalRecSubmitted = 0, totalRecApproved = 0, totalRecAccepted = 0, totalRecOffered = 0, totalRecOfferAccepted = 0, totalRecOfferRejected = 0, totalRecOfferExpired = 0;
											for (Map.Entry<SchoolZoneBean, TeacherAllocationVacancyStatisticsBean> entry : vacancyStatsByRegion.entrySet()) { 
												totalVacancies += entry.getValue().getTotalVacancies();
												totalFilledByCompetition += entry.getValue().getTotalFilledByCompetition();
												totalFilledManually += entry.getValue().getTotalFilledManually();
												totalAdSubmitted += entry.getValue().getTotalAdSubmitted();
												totalAdApproved += entry.getValue().getTotalAdApproved();
												totalAdPosted += entry.getValue().getTotalAdPosted();
												totalRecSubmitted += entry.getValue().getTotalRecommendationSubmitted();
												totalRecApproved += entry.getValue().getTotalRecommendationApproved();
												totalRecAccepted += entry.getValue().getTotalRecommendationAccepted();
												totalRecOffered += entry.getValue().getTotalRecommendationOffered();
												totalShortlistedNoRecommendation += entry.getValue().getTotalShortlistNoRecemmendation();
												totalRecOfferRejected += entry.getValue().getTotalRecommendationOfferRejected();
												totalNoShortlist += entry.getValue().getTotalNoShortlist();
												totalRecOfferExpired += entry.getValue().getTotalRecommendationOfferExpired();
												totalRecOfferAccepted += entry.getValue().getTotalRecommendationOfferAccepted();
										%>
											<tr>
												<td scope="row"><%= StringUtils.capitalize(entry.getKey().getZoneName()) %></td>
												<td style='text-align:center; border-right: 5px solid #e4e4e4;'><%= entry.getValue().getTotalVacancies() %></td>
												<td style='text-align:center;'><%= entry.getValue().getTotalAdSubmitted() %></td>
												<td style='text-align:center;'><%= entry.getValue().getTotalAdApproved() %></td>
												<td style='text-align:center; border-right: 5px solid #e4e4e4;'><%= entry.getValue().getTotalAdPosted() %></td>
												<td style='text-align:center;'><%= entry.getValue().getTotalNoShortlist() %></td>
												<td style='text-align:center; border-right: 5px solid #e4e4e4;'><%= entry.getValue().getTotalShortlistNoRecemmendation() %></td>
												<td style='text-align:center;'><%= entry.getValue().getTotalRecommendationSubmitted() %></td>
												<td style='text-align:center;'><%= entry.getValue().getTotalRecommendationApproved() %></td>
												<td style='text-align:center;'><%= entry.getValue().getTotalRecommendationAccepted() %></td>
												<td style='text-align:center;'><%= entry.getValue().getTotalRecommendationOffered() %></td>
												<td style='text-align:center;'><%= entry.getValue().getTotalRecommendationOfferAccepted() %></td>
												<td style='text-align:center;'><%= entry.getValue().getTotalRecommendationOfferRejected() %></td>
												<td style='text-align:center; border-right: 5px solid #e4e4e4;'><%= entry.getValue().getTotalRecommendationOfferExpired() %></td>
												<td style='text-align:center;'><%= entry.getValue().getTotalFilledByCompetition()  %></td>
												<td style='text-align:center;'><%= entry.getValue().getTotalFilledManually() %></td>
												<td class='text-success' style='font-weight: bold; text-align:center;'><%= entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually() %>&nbsp;(<%= (entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually()) * 100 / entry.getValue().getTotalVacancies()  %>%)</td>
												<td class='text-danger' style='font-weight: bold; text-align:center;'><%= entry.getValue().getTotalVacancies() - (entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually()) %></td>
											</tr>
										<% } %>
										<tr>
												<td scope="row" style='border-top: double #333333;'>Totals</td>
												<td style='text-align:center; border-top: double #333333; border-right: 5px solid #e4e4e4;'><%= totalVacancies %></td>
												<td style='text-align:center; border-top: double #333333;'><%= totalAdSubmitted %></td>
												<td style='text-align:center; border-top: double #333333;'><%= totalAdApproved %></td>
												<td style='text-align:center; border-top: double #333333; border-right: 5px solid #e4e4e4;'><%= totalAdPosted %></td>
												<td style='text-align:center; border-top: double #333333;'><%= totalNoShortlist %></td>
												<td style='text-align:center; border-top: double #333333; border-right: 5px solid #e4e4e4;'><%= totalShortlistedNoRecommendation %></td>
												<td style='text-align:center; border-top: double #333333;'><%= totalRecSubmitted %></td>
												<td style='text-align:center; border-top: double #333333;'><%= totalRecApproved %></td>
												<td style='text-align:center; border-top: double #333333;'><%= totalRecAccepted %></td>
												<td style='text-align:center; border-top: double #333333;'><%= totalRecOffered %></td>
												<td style='text-align:center; border-top: double #333333;'><%= totalRecOfferAccepted %></td>
												<td style='text-align:center; border-top: double #333333;'><%= totalRecOfferRejected %></td>
												<td style='text-align:center; border-top: double #333333; border-right: 5px solid #e4e4e4;'><%= totalRecOfferExpired %></td>
												<td style='text-align:center; border-top: double #333333;'><%= totalFilledByCompetition  %></td>
												<td style='text-align:center; border-top: double #333333;'><%= totalFilledManually %></td>
												<td class='text-success' style='font-weight: bold; text-align:center; border-top: double #333333'><%= totalFilledByCompetition + totalFilledManually %>&nbsp;(<%= (totalFilledByCompetition + totalFilledManually) * 100 / totalVacancies  %>%)</td>
												<td class='text-danger' style='font-weight: bold; text-align:center; border-top: double #333333'><%= totalVacancies - (totalFilledByCompetition + totalFilledManually) %></td>
											</tr>
										<tr>
											<td colspan='14' class='text-success' style='text-align:right; font-weight: bold; border-right: 5px solid #e4e4e4;'>Total Filled</td>
											<td colspan='4' class='text-success' style='text-align:center; font-weight: bold;'><%= totalFilledByCompetition + totalFilledManually  %>&nbsp;(<%= (totalFilledByCompetition + totalFilledManually) * 100 / totalVacancies  %>%)</td>
										</tr>
										<tr>
											<td colspan='14' class='text-danger' style='text-align:right; font-weight: bold; border-right: 5px solid #e4e4e4;'>Total Outstanding</td>
											<td colspan='4' class='text-danger' style='text-align:center; font-weight: bold;'><%= totalVacancies - (totalFilledByCompetition + totalFilledManually) %></td>
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
