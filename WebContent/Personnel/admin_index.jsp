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
		<style>
		th {
				text-transform:Uppercase;
				background-color: Grey;
				color:White;
				font-size:14px;
				text-align:center;			
		}
		td {
				text-align:center;
				background-color:white;
		}
	
		</style>
	</head>

	<body>
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">					
					
					<div class="pageHeader">Human Resources Administration</div>
					<div class="pageBody">
					Welcome <span style="text-transform:capitalize;"><%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></span> to your 
					NLESD Applicant Profiling System Administration Site. Please use the navigation menu above to continue. Not all menu items are available to all users. You will only see options available for your current job position. 
					
					<br/>&nbsp;<br/>
					<b>NOTICE: </b>Due to the information and data layout this application provides, we advise using a tablet or laptop/desktop computer to use this system.
					
					<br/>&nbsp;<br/>
					 <c:if test="${ msg ne null }">  
                   		<div class="alert alert-danger" id="memo_error_message" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>ERROR:</b> ${ msg } </div>     
                  	</c:if>			
                  	
					<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">					
								<table class="table table-sm table-bordered" style="font-size:11px;width:100%;background-color:White;">								
									<thead>
									<tr>
									<th colspan="18"><%= statsSchoolYear %> Vacancy Processing Statistics</th>
									</tr>
									</thead>
											<tbody>
										<tr>
											<td rowspan='2' style="background-color:rgba(0, 0, 0,1);color:White;text-transform:Uppercase;font-weight:bold;">Region</td>
											<td rowspan='2' style="background-color:rgba(105, 105, 105,1);color:White;text-transform:Uppercase;font-weight:bold;">Total<br/>Vacancies</td>
											<td colspan='3' style="background-color:rgba(255, 127, 80,1);color:White;text-transform:Uppercase;font-weight:bold;">Ad Requests</td>
											<td colspan='2' style="background-color:rgba(95, 158, 160,1);color:White;text-transform:Uppercase;font-weight:bold;">Shortlists</td>
											<td colspan='7' style="background-color:rgba(60, 179, 113,1);color:White;text-transform:Uppercase;font-weight:bold;">Recommendations</td>
											<td colspan='4' style="background-color:rgba(205, 92, 92,1);color:White;text-transform:Uppercase;font-weight:bold;">Positions Filled</td>
										</tr>
										<tr>
											<td scope="col" style="background-color:rgba(255, 127, 80,1);color:White;">Sub</td>
											<td scope="col" style="background-color:rgba(255, 127, 80,1);color:White;">App</td>
											<td scope="col" style="background-color:rgba(255, 127, 80,1);color:White;">Pst</td>
											<td scope="col" style="background-color:rgba(95, 158, 160,1);color:White;">No</td>
											<td scope="col" style='background-color:rgba(95, 158, 160,1);color:White;white-space: nowrap;'>> 48 Hrs<br/>No Rec</td>
											<td scope="col" style="background-color:rgba(60, 179, 113,1);color:White;">Sub</td>
											<td scope="col" style="background-color:rgba(60, 179, 113,1);color:White;">App</td>
											<td scope="col" style="background-color:rgba(60, 179, 113,1);color:White;">Acc</td>
											<td scope="col" style="background-color:rgba(60, 179, 113,1);color:White;">Off</td>
											<td scope="col" style="background-color:rgba(60, 179, 113,1);color:White;">Off Acc</td>
											<td scope="col" style="background-color:rgba(60, 179, 113,1);color:White;">Off Rej</td>
											<td scope="col" style="background-color:rgba(60, 179, 113,1);color:White;">Off Exp</td>
											<td scope="col" style="background-color:rgba(205, 92, 92,1);color:White;">Comp</td>
											<td scope="col" style="background-color:rgba(205, 92, 92,1);color:White;">Manual</td>
											<td scope="col" style="background-color:rgba(205, 92, 92,1);color:White;">Total</td>
											<td scope="col" style="background-color:rgba(205, 92, 92,1);color:White;">Left</td>
										</tr>
								
							
										<% 
											int rowCnt = 0;
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
										<%rowCnt++; %>
											<tr>											
											<% if (entry.getKey().getZoneName().equalsIgnoreCase("Avalon")) { %>
												<td scope="row" class="region1half"><%= StringUtils.capitalize(entry.getKey().getZoneName()) %></td>												
											<%} else if (entry.getKey().getZoneName().equalsIgnoreCase("Central")) {%>	
												<td scope="row" class="region2half"><%= StringUtils.capitalize(entry.getKey().getZoneName()) %></td>
											<%} else if (entry.getKey().getZoneName().equalsIgnoreCase("Western")) {%>
												<td scope="row" class="region3half"><%= StringUtils.capitalize(entry.getKey().getZoneName()) %></td>
											<%} else if (entry.getKey().getZoneName().equalsIgnoreCase("Labrador")) {%>
												<td scope="row" class="region4half"><%= StringUtils.capitalize(entry.getKey().getZoneName()) %></td>
											<%} else {%>
												<td scope="row" class="region5half"><%= StringUtils.capitalize(entry.getKey().getZoneName()) %></td>
											<%} %>												
												<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalVacancies() %></td>
												<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalAdSubmitted() %></td>
												<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalAdApproved() %></td>
												<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalAdPosted() %></td>
												<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalNoShortlist() %></td>
												<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalShortlistNoRecemmendation() %></td>
												<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalRecommendationSubmitted() %></td>
												<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalRecommendationApproved() %></td>
												<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalRecommendationAccepted() %></td>
												<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalRecommendationOffered() %></td>
												<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalRecommendationOfferAccepted() %></td>
												<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalRecommendationOfferRejected() %></td>
												<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalRecommendationOfferExpired() %></td>
												<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalFilledByCompetition()  %></td>
												<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalFilledManually() %></td>
												<td class="regionRow<%=rowCnt%> text-success" style='font-weight: bold;'><%= entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually() %>&nbsp;(<%= (entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually()) * 100 / entry.getValue().getTotalVacancies()  %>%)</td>
												<td class="regionRow<%=rowCnt%> text-danger" style='font-weight: bold;'><%= entry.getValue().getTotalVacancies() - (entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually()) %></td>
											</tr>
											
											<% if (entry.getKey().getZoneName().equalsIgnoreCase("Avalon")) { %>										
												<script>$(".regionRow<%=rowCnt%>").addClass("region1");</script>
											<%} else if (entry.getKey().getZoneName().equalsIgnoreCase("Central")) {%>	
												<script>$(".regionRow<%=rowCnt%>").addClass("region2");</script>
											<%} else if (entry.getKey().getZoneName().equalsIgnoreCase("Western")) {%>
										        <script>$(".regionRow<%=rowCnt%>").addClass("region3");</script>
											<%} else if (entry.getKey().getZoneName().equalsIgnoreCase("Labrador")) {%>
											    <script>$(".regionRow<%=rowCnt%>").addClass("region4");</script>
											<%} else {%>
										      <script>$(".regionRow<%=rowCnt%>").addClass("region5");</script>
											<%} %>
											
											
										<% } %>
										<tr>
												<td scope="row" style='background-color:rgba(0, 0, 0,1);font-weight:bold;color:White;border-top: double #333333;'>TOTALS:</td>
												<td style='background-color:rgba(105, 105, 105,0.3);border-top: double #333333; border-right: 5px solid #e4e4e4;'><%= totalVacancies %></td>
												<td style='background-color:rgba(255, 127, 80,0.3);border-top: double #333333;'><%= totalAdSubmitted %></td>
												<td style='background-color:rgba(255, 127, 80,0.3);border-top: double #333333;'><%= totalAdApproved %></td>
												<td style='background-color:rgba(255, 127, 80,0.3);border-top: double #333333; border-right: 5px solid #e4e4e4;'><%= totalAdPosted %></td>
												<td style='background-color:rgba(95, 158, 160,0.3);border-top: double #333333;'><%= totalNoShortlist %></td>
												<td style='background-color:rgba(95, 158, 160,0.3);border-top: double #333333; border-right: 5px solid #e4e4e4;'><%= totalShortlistedNoRecommendation %></td>
												<td style='background-color:rgba(60, 179, 113,0.3);border-top: double #333333;'><%= totalRecSubmitted %></td>
												<td style='background-color:rgba(60, 179, 113,0.3);border-top: double #333333;'><%= totalRecApproved %></td>
												<td style='background-color:rgba(60, 179, 113,0.3);border-top: double #333333;'><%= totalRecAccepted %></td>
												<td style='background-color:rgba(60, 179, 113,0.3);border-top: double #333333;'><%= totalRecOffered %></td>
												<td style='background-color:rgba(60, 179, 113,0.3);border-top: double #333333;'><%= totalRecOfferAccepted %></td>
												<td style='background-color:rgba(60, 179, 113,0.3);border-top: double #333333;'><%= totalRecOfferRejected %></td>
												<td style='background-color:rgba(60, 179, 113,0.3);border-top: double #333333; border-right: 5px solid #e4e4e4;'><%= totalRecOfferExpired %></td>
												<td style='background-color:rgba(205, 92, 92,0.3);border-top: double #333333;'><%= totalFilledByCompetition  %></td>
												<td style='background-color:rgba(205, 92, 92,0.3);border-top: double #333333;'><%= totalFilledManually %></td>
												<td class='text-success' style='background-color:rgba(205, 92, 92,0.3);font-weight: bold; border-top: double #333333'><%= totalFilledByCompetition + totalFilledManually %>&nbsp;(<%= (totalFilledByCompetition + totalFilledManually) * 100 / totalVacancies  %>%)</td>
												<td class='text-danger' style='background-color:rgba(205, 92, 92,0.3);font-weight: bold; border-top: double #333333'><%= totalVacancies - (totalFilledByCompetition + totalFilledManually) %></td>
											</tr>
										<tr>
											<td colspan='14' style='color:Green;text-transform:Uppercase;text-align:right; font-weight: bold; border-right: 5px solid #e4e4e4;'>Total Filled:</td>
											<td colspan='4' style='color:Green;text-align:center; font-weight: bold;'><%= totalFilledByCompetition + totalFilledManually  %>&nbsp;(<%= (totalFilledByCompetition + totalFilledManually) * 100 / totalVacancies  %>%)</td>
										</tr>
										<tr>
											<td colspan='14' style='color:Red;text-transform:Uppercase;text-align:right; font-weight: bold; border-right: 5px solid #e4e4e4;'>Total Outstanding:</td>
											<td colspan='4' style='color:Red;text-align:center; font-weight: bold;'><%= totalVacancies - (totalFilledByCompetition + totalFilledManually) %></td>
										</tr>
									</tbody>
								</table>
							
								<table class="table table-sm table-bordered" style="font-size:11px;width:100%;background-color:White;">
									<thead>
									<tr>
									<th colspan="3">Position Offer Statistics</th>
									</tr>
									</thead>
									<tbody>
										<tr>
											<td scope="col" style='text-align:center;text-transform:Uppercase;background-color:Navy;color:White;'>Queued</td>
											<td scope="col" style='text-align:center;text-transform:Uppercase;background-color:Green;color:White;'>In-progress</td>
											<td scope="col" style='text-align:center;text-transform:Uppercase;background-color:Red;color:White;'>Expired</td>
										</tr>							
									     <tr>
											<td scope="row" style='text-align:center;'><%= stats.getOffersQueued() %></td>
											<td style='text-align:center;'><%= stats.getOffersInProgress() %></td>
											<td style='text-align:center;'><%= stats.getOffersExpired() %></td>
										</tr>
									</tbody>
								</table>
							
					</esd:SecurityAccessRequired>
					
					<div class="alert alert-warning" style="text-align:center"><b>****** ACCESS TO INFORMATION PRIVACY NOTICE / WARNING ******</b><br/>
					The NLESD is committed to the protection of all personal information collected, used and/or disclosed in the operation and management of its activities.  
					As a  public body, all information collected, used and/or disclosed will be in accordance with Access to Information and Protection of Privacy Act,2015.
					By continuing to use this site, you agree with the above Act.</div>
										
					
					 <c:if test="${ msg ne null }">  
                   		<div class="alert alert-danger" id="memo_error_message" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>ERROR:</b> ${ msg } </div>     
                  	</c:if>					
					
					</div>	
			</div>			
		</div>	
  
  </body>
</html>
