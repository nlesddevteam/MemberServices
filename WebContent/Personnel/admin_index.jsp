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
				background-color: rgba(0, 102, 255,0);
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
                   		<div class="alert alert-warning" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>WARNING:</b> ${ msg } </div>     
                  	</c:if>	
                  	<c:if test="${ msgOK ne null }">  
                   		<div class="alert alert-success" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>SUCCESS:</b> ${ msg } </div>     
                  	</c:if>	
                  	<c:if test="${ msgERR ne null }">  
                   		<div class="alert alert-danger" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>ERROR:</b> ${ msg } </div>     
                  	</c:if>			
                  	
					<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">			
					
					<div style="font-size:14px;font-weight:bold;color:#1F4279;"><%= statsSchoolYear %> Vacancy Processing Statistics</div>
							
								<table class="table table-sm table-bordered" style="font-size:11px;width:100%;background-color:White;">								
									
										<tbody>
										<tr>
											<td rowspan='2' style="color: rgba(0,0,0,1);text-transform:Uppercase;font-weight:bold;">Region</td>
											<td rowspan='2' style="color: rgba(0,0,0,1);text-transform:Uppercase;font-weight:bold;">Total<br/>Vacancies</td>
											<td colspan='3' style="color: rgba(0,0,0,1);text-transform:Uppercase;font-weight:bold;">Ad Requests</td>
											<td colspan='2' style="color: rgba(0,0,0,1);text-transform:Uppercase;font-weight:bold;">Shortlists</td>
											<td colspan='7' style="color: rgba(0,0,0,1);text-transform:Uppercase;font-weight:bold;">Recommendations</td>
											<td colspan='4' style="color: rgba(0,0,0,1);text-transform:Uppercase;font-weight:bold;">Positions Filled</td>
										</tr>
										<tr>
											<td scope="col" style="color: rgba(0,0,0,1);">Sub</td>
											<td scope="col" style="color: rgba(0,0,0,1);">App</td>
											<td scope="col" style="color: rgba(0,0,0,1);">Pst</td>
											<td scope="col" style="color: rgba(0,0,0,1);">No</td>
											<td scope="col" style='color: rgba(0,0,0,1);white-space: nowrap;'>> 48 Hrs<br/>No Rec</td>
											<td scope="col" style="color: rgba(0,0,0,1);">Sub</td>
											<td scope="col" style="color: rgba(0,0,0,1);">App</td>
											<td scope="col" style="color: rgba(0,0,0,1);">Acc</td>
											<td scope="col" style="color: rgba(0,0,0,1);">Off</td>
											<td scope="col" style="color: rgba(0,0,0,1);">Off Acc</td>
											<td scope="col" style="color: rgba(0,0,0,1);">Off Rej</td>
											<td scope="col" style="color: rgba(0,0,0,1);">Off Exp</td>
											<td scope="col" style="color: rgba(0,0,0,1);">Comp</td>
											<td scope="col" style="color: rgba(0,0,0,1);">Manual</td>
											<td scope="col" style="color: rgba(0,0,0,1);">Total</td>
											<td scope="col" style="color: rgba(0,0,0,1);">Left</td>
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
												<td class="regionRow<%=rowCnt%>"><a href="listVacanciesWithNoShortlist.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalNoShortlist() %></a></td>
												<td class="regionRow<%=rowCnt%>"><a href="listVacanciesShortlistedWithNoRecommendation.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalShortlistNoRecemmendation() %></a></td>
												<td class="regionRow<%=rowCnt%>"><a href="listVacanciesWithRecommendationSubmitted.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalRecommendationSubmitted() %></a></td>
												<td class="regionRow<%=rowCnt%>"><a href="listVacanciesWithRecommendationApproved.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalRecommendationApproved() %></a></td>
												<td class="regionRow<%=rowCnt%>"><a href="listVacanciesWithRecommendationAccepted.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalRecommendationAccepted() %></a></td>
												<td class="regionRow<%=rowCnt%>"><a href="listVacanciesWithCurrentOffer.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalRecommendationOffered() %></a></td>
												<td class="regionRow<%=rowCnt%>"><a href="listVacanciesWithAcceptedOffer.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalRecommendationOfferAccepted() %></a></td>
												<td class="regionRow<%=rowCnt%>"><a href="listVacanciesWithRejectedOffer.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalRecommendationOfferRejected() %></a></td>
												<td class="regionRow<%=rowCnt%>"><a href="listVacanciesWithExpiredOffer.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalRecommendationOfferExpired() %></a></td>
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
												<td scope="row" style='font-weight:bold;color:rgba(0,0,0,1);border-top: double #333333;'>TOTALS:</td>
												<td style='border-top: double #333333; '><%= totalVacancies %></td>
												<td style='border-top: double #333333;'><%= totalAdSubmitted %></td>
												<td style='border-top: double #333333;'><%= totalAdApproved %></td>
												<td style='border-top: double #333333; '><%= totalAdPosted %></td>
												<td style='border-top: double #333333;'><%= totalNoShortlist %></td>
												<td style='border-top: double #333333; '><%= totalShortlistedNoRecommendation %></td>
												<td style='border-top: double #333333;'><%= totalRecSubmitted %></td>
												<td style='border-top: double #333333;'><%= totalRecApproved %></td>
												<td style='border-top: double #333333;'><%= totalRecAccepted %></td>
												<td style='border-top: double #333333;'><%= totalRecOffered %></td>
												<td style='border-top: double #333333;'><%= totalRecOfferAccepted %></td>
												<td style='border-top: double #333333;'><%= totalRecOfferRejected %></td>
												<td style='border-top: double #333333;'><%= totalRecOfferExpired %></td>
												<td style='border-top: double #333333;'><%= totalFilledByCompetition  %></td>
												<td style='border-top: double #333333;'><%= totalFilledManually %></td>
												<td class='text-success' style='font-weight: bold; border-top: double #333333'><%= totalFilledByCompetition + totalFilledManually %>&nbsp;(<%= (totalFilledByCompetition + totalFilledManually) * 100 / totalVacancies  %>%)</td>
												<td class='text-danger' style='font-weight: bold; border-top: double #333333'><%= totalVacancies - (totalFilledByCompetition + totalFilledManually) %></td>
											</tr>
										<tr>
											<td colspan='14' style='color:Green;text-transform:Uppercase;text-align:right; font-weight: bold;'>Total Filled:</td>
											<td colspan='4' style='color:Green;text-align:center; font-weight: bold;'><%= totalFilledByCompetition + totalFilledManually  %>&nbsp;(<%= (totalFilledByCompetition + totalFilledManually) * 100 / totalVacancies  %>%)</td>
										</tr>
										<tr>
											<td colspan='14' style='color:Red;text-transform:Uppercase;text-align:right; font-weight: bold;'>Total Outstanding:</td>
											<td colspan='4' style='color:Red;text-align:center; font-weight: bold;'><%= totalVacancies - (totalFilledByCompetition + totalFilledManually) %></td>
										</tr>
									</tbody>
								</table>
							
							
							
							<div style="font-size:14px;font-weight:bold;color:#1F4279;">Position Offer Statistics</div>
							
								<table class="table table-sm table-bordered" style="width:100%;max-width:600px;font-size:11px;width:100%;background-color:White;">									
									<tbody>
										<tr>
											<td scope="col" style='font-weight:bold;text-align:center;text-transform:Uppercase;background-color:Navy;color:White;'>Queued</td>
											<td scope="col" style='font-weight:bold;text-align:center;text-transform:Uppercase;background-color:Green;color:White;'>In-progress</td>
											<td scope="col" style='font-weight:bold;text-align:center;text-transform:Uppercase;background-color:Red;color:White;'>Expired</td>
										</tr>							
									     <tr>
											<td scope="row" style='text-align:center;font-size:18;color:Navy;'><a href="listVacanciesWithOffersQueued.html" target="_blank"><%= stats.getOffersQueued() %></a></td>
											<td style='text-align:center;font-size:18;color:Green;'><a href="listVacanciesWithOffersInprogress.html" target="_blank"><%= stats.getOffersInProgress() %></a></td>
											<td style='text-align:center;font-size:18;color:Red;'><a href="listVacanciesWithOffersExpired.html" target="_blank"><%= stats.getOffersExpired() %></a></td>
										</tr>
									</tbody>
								</table>
							
					</esd:SecurityAccessRequired>
					
					<div class="alert alert-warning" style="text-align:center"><b>****** ACCESS TO INFORMATION PRIVACY NOTICE / WARNING ******</b><br/>
					The NLESD is committed to the protection of all personal information collected, used and/or disclosed in the operation and management of its activities.  
					As a  public body, all information collected, used and/or disclosed will be in accordance with Access to Information and Protection of Privacy Act,2015.
					By continuing to use this site, you agree with the above Act.</div>
										
					
					 <c:if test="${ msg ne null }">  
                   		<div class="alert alert-warning"  style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>WARNING:</b> ${ msg } </div>     
                  	</c:if>	
                  	<c:if test="${ msgOK ne null }">  
                   		<div class="alert alert-success" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>SUCCESS:</b> ${ msg } </div>     
                  	</c:if>	
                  	<c:if test="${ msgERR ne null }">  
                   		<div class="alert alert-danger"  style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>ERROR:</b> ${ msg } </div>     
                  	</c:if>					
					
					</div>	
			</div>			
		</div>	
  
  </body>
</html>
