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
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="jobv2" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW,RTH-NEW-REQUEST,PERSONNEL-RTH-VIEW-APPROVALS" />	

<%
  User usr = (User) session.getAttribute("usr");

	String statsSchoolYear = StringUtils.isNotBlank(request.getParameter("lst_schoolyear")) ? request.getParameter("lst_schoolyear") 
			: com.esdnl.personnel.v2.utils.StringUtils.getSchoolYear(Calendar.getInstance().getTime());
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
	.btn-xs { min-width:30px;}
		</style>
		
		<script src="/MemberServices/Personnel/includes/js/Chart.min.js"></script>
	</head>
    
	<body>
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">					
					
					<div class="pageHeader">Latest Hiring Statistics</div>
					<div class="pageBody">
				 Please find below the latest Vacancy Processing and Position Offer Statistics.
									
					<br/>&nbsp;<br/>
					<c:if test="${ msg ne null }">  
          		<div class="alert alert-warning" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>WARNING:</b> ${ msg } </div>     
         	</c:if>	
         	<c:if test="${ msgOK ne null }">  
          		<div class="alert alert-success" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>SUCCESS:</b> ${ msgOK } </div>     
         	</c:if>	
         	<c:if test="${ msgERR ne null }">  
          		<div class="alert alert-danger" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>ERROR:</b> ${ msgERR } </div>     
         	</c:if>	
         	<%if(!(request.getParameter("delmess") == null) ){ %>
         		<div class="alert alert-success" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b><%=request.getParameter("delmess") %></b></div>
         	<%} %>
         	<%if(!(request.getParameter("delmesserr") == null)){ %>
         		<div class="alert alert-danger" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b><%=request.getParameter("delmesserr") %></b></div>
         	<%} %>			
                  	
					<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">
					
						<div class="alert alert-warning">
							<b>IMPORTANT:</b> The <span class='btn-xs btn btn-info' style='font-weight: bold;'>blue</span> numbers in the tables below are CLICKABLE LINKS to a page listing the competitions used to calculate the statistic. 
						</div>
						
						<div>
							<div style="font-size:14px;font-weight:bold;color:#1F4279; float:left;">
								<form method="post" style='margin:0; padding: 0;'>
									<jobv2:SchoolYearListbox id="lst_schoolyear" value='<%= statsSchoolYear %>' pastYears="3" futureYears="1" style='width:100px;text-transform: uppercase;' />
									<label for='lst_schoolyear'>Vacancy Processing Statistics</label>
								</form>
							</div>
							<div style='float:right;'>
								<a id='lnk-showhide-graphs' href='javascript:void(0);'></a>
							</div>
							<div style="clear:both;"></div>	
						</div>
							
							<table class="table table-sm table-bordered" style="font-size:11px;width:100%;background-color:White;">								
								
									<tbody>
									<tr>
										<td rowspan='2' style="color: rgba(0,0,0,1);text-transform:Uppercase;font-weight:bold;">Region</td>
										<td rowspan='2' style="color: rgba(0,0,0,1);text-transform:Uppercase;font-weight:bold;">Total<br/>Vacancies</td>
										<td colspan='4' style="color: rgba(0,0,0,1);text-transform:Uppercase;font-weight:bold;">Ad Requests</td>
										<td rowspan='2' style="color: rgba(0,0,0,1);text-transform:Uppercase;font-weight:bold;">Comp<br/>In<br/>Prog</td>
										<td colspan='2' style="color: rgba(0,0,0,1);text-transform:Uppercase;font-weight:bold;">Shortlists</td>
										<td colspan='8' style="color: rgba(0,0,0,1);text-transform:Uppercase;font-weight:bold;">Recommendations</td>
										<td colspan='4' style="color: rgba(0,0,0,1);text-transform:Uppercase;font-weight:bold;">Positions Filled</td>
									</tr>
									<tr>
										<td scope="col" style="color: rgba(0,0,0,1);">No</td>
										<td scope="col" style="color: rgba(0,0,0,1);">Sub</td>
										<td scope="col" style="color: rgba(0,0,0,1);">App</td>
										<td scope="col" style="color: rgba(0,0,0,1);">Pst</td>
										<td scope="col" style="color: rgba(0,0,0,1);">No</td>
										<td scope="col" style='color: rgba(0,0,0,1);'>No Rec</td>
										<td scope="col" style="color: rgba(0,0,0,1);">Sub</td>
										<td scope="col" style="color: rgba(0,0,0,1);">Rej</td>
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
										int totalNoAdCreated = 0, totalAdSubmitted = 0, totalAdApproved = 0, totalAdPosted = 0, totalCompInProgress = 0,  totalNoShortlist = 0, totalShortlistedNoRecommendation = 0;
										int totalRecSubmitted = 0, totalRecRejected = 0, totalRecApproved = 0, totalRecAccepted = 0, totalRecOffered = 0, totalRecOfferAccepted = 0, totalRecOfferRejected = 0, totalRecOfferExpired = 0;
										for (Map.Entry<SchoolZoneBean, TeacherAllocationVacancyStatisticsBean> entry : vacancyStatsByRegion.entrySet()) { 
											totalVacancies += entry.getValue().getTotalVacancies();
											totalFilledByCompetition += entry.getValue().getTotalFilledByCompetition();
											totalFilledManually += entry.getValue().getTotalFilledManually();
											totalNoAdCreated += entry.getValue().getTotalNoAdCreated();
											totalAdSubmitted += entry.getValue().getTotalAdSubmitted();
											totalAdApproved += entry.getValue().getTotalAdApproved();
											totalAdPosted += entry.getValue().getTotalAdPosted();
											totalCompInProgress += entry.getValue().getTotalCompetitionsInProgress();
											totalRecSubmitted += entry.getValue().getTotalRecommendationSubmitted();
											totalRecRejected += entry.getValue().getTotalRecommendationRejected();
											totalRecApproved += entry.getValue().getTotalRecommendationApproved();
											totalRecAccepted += entry.getValue().getTotalRecommendationAccepted();
											totalRecOffered += entry.getValue().getTotalRecommendationOffered();
											totalShortlistedNoRecommendation += entry.getValue().getTotalShortlistNoRecemmendation();
											totalRecOfferRejected += entry.getValue().getTotalRecommendationOfferRejected();
											totalNoShortlist += entry.getValue().getTotalNoShortlist();
											totalRecOfferExpired += entry.getValue().getTotalRecommendationOfferExpired();
											totalRecOfferAccepted += entry.getValue().getTotalRecommendationOfferAccepted();
											
											rowCnt++;
									%>
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
											<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalNoAdCreated() %></td>
											<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalAdSubmitted() %></td>
											<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalAdApproved() %></td>
											<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalAdPosted() %></td>
											<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalCompetitionsInProgress() %></td>
											<td class="regionRow<%=rowCnt%>"><a class="btn-xs btn btn-info" href="listVacanciesWithNoShortlist.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalNoShortlist() %></a></td>
											<td class="regionRow<%=rowCnt%>"><a class="btn-xs btn btn-info" href="listVacanciesShortlistedWithNoRecommendation.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalShortlistNoRecemmendation() %></a></td>
											<td class="regionRow<%=rowCnt%>"><a class="btn-xs btn btn-info" href="listVacanciesWithRecommendationSubmitted.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalRecommendationSubmitted() %></a></td>
											<td class="regionRow<%=rowCnt%>"><a class="btn-xs btn btn-info" href="listVacanciesWithRecommendationRejected.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalRecommendationRejected() %></a></td>
											<td class="regionRow<%=rowCnt%>"><a class="btn-xs btn btn-info" href="listVacanciesWithRecommendationApproved.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalRecommendationApproved() %></a></td>
											<td class="regionRow<%=rowCnt%>"><a class="btn-xs btn btn-info" href="listVacanciesWithRecommendationAccepted.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalRecommendationAccepted() %></a></td>
											<td class="regionRow<%=rowCnt%>"><a class="btn-xs btn btn-info" href="listVacanciesWithCurrentOffer.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalRecommendationOffered() %></a></td>
											<td class="regionRow<%=rowCnt%>"><a class="btn-xs btn btn-info" href="listVacanciesWithAcceptedOffer.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalRecommendationOfferAccepted() %></a></td>
											<td class="regionRow<%=rowCnt%>"><a class="btn-xs btn btn-info" href="listVacanciesWithRejectedOffer.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalRecommendationOfferRejected() %></a></td>
											<td class="regionRow<%=rowCnt%>"><a class="btn-xs btn btn-info" href="listVacanciesWithExpiredOffer.html?sy=<%= statsSchoolYear %>&zid=<%= entry.getKey().getZoneId() %>"><%= entry.getValue().getTotalRecommendationOfferExpired() %></a></td>
											<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalFilledByCompetition()  %></td>
											<td class="regionRow<%=rowCnt%>"><%= entry.getValue().getTotalFilledManually() %></td>
											<td class="regionRow<%=rowCnt%> text-success" style='font-weight: bold;'><%= entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually() %>&nbsp;(<%= (entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually()) * 100 / entry.getValue().getTotalVacancies()  %>%)</td>
											<td class="regionRow<%=rowCnt%> text-danger" style='font-weight: bold;'><%= entry.getValue().getTotalVacancies() - (entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually()) %></td>
										</tr>
										
										<% if (entry.getKey().getZoneName().equalsIgnoreCase("Avalon")) { %>										
											<script>
											totalVacanciesAvalon = <%= entry.getValue().getTotalVacancies() %>
											totalFilledAvalon = <%= entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually() %>
											totalRemainAvalon= <%= entry.getValue().getTotalVacancies() - (entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually()) %>
											$(".regionRow<%=rowCnt%>").addClass("region1");
											</script>
										<% } else if (entry.getKey().getZoneName().equalsIgnoreCase("Central")) {%>											
											<script>
											totalVacanciesCentral = <%= entry.getValue().getTotalVacancies() %>
											totalFilledCentral = <%= entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually() %>
											totalRemainCentral= <%= entry.getValue().getTotalVacancies() - (entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually()) %>
											$(".regionRow<%=rowCnt%>").addClass("region2");
											</script>
										<% } else if (entry.getKey().getZoneName().equalsIgnoreCase("Western")) {%>
									        <script>
									        totalVacanciesWestern = <%= entry.getValue().getTotalVacancies() %>
									        totalFilledWestern = <%= entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually() %>
									        totalRemainWestern= <%= entry.getValue().getTotalVacancies() - (entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually()) %>
									        $(".regionRow<%=rowCnt%>").addClass("region3");
									        </script>
										<% } else if (entry.getKey().getZoneName().equalsIgnoreCase("Labrador")) {%>
										    <script>
										    totalVacanciesLabrador = <%= entry.getValue().getTotalVacancies() %>
										    totalFilledLabrador = <%= entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually() %>
										    totalRemainLabrador= <%= entry.getValue().getTotalVacancies() - (entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually()) %>
										    $(".regionRow<%=rowCnt%>").addClass("region4");
										    </script>
										<% } else {%>
									      <script>
									      	totalVacanciesProvincial = <%= entry.getValue().getTotalVacancies() %>
										   	totalFilledProvincial = <%= entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually() %>
										    totalRemainProvincial = <%= entry.getValue().getTotalVacancies() - (entry.getValue().getTotalFilledByCompetition() + entry.getValue().getTotalFilledManually()) %>
										    $(".regionRow<%=rowCnt%>").addClass("region5");
										    </script>
										<% } %>
										
									<% } %>
									<tr>
										<td scope="row" style='font-weight:bold;color:rgba(0,0,0,1);border-top: double #333333;background-color: #fafafa;'>TOTALS:</td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa;'><%= totalVacancies %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa;'><%= totalNoAdCreated %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa;'><%= totalAdSubmitted %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa;'><%= totalAdApproved %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa; '><%= totalAdPosted %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa; '><%= totalCompInProgress %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa;'><%= totalNoShortlist %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa; '><%= totalShortlistedNoRecommendation %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa;'><%= totalRecSubmitted %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa;'><%= totalRecRejected %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa;'><%= totalRecApproved %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa;'><%= totalRecAccepted %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa;'><%= totalRecOffered %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa;'><%= totalRecOfferAccepted %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa;'><%= totalRecOfferRejected %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa;'><%= totalRecOfferExpired %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa;'><%= totalFilledByCompetition  %></td>
										<td style='font-weight: bold;border-top: double #333333;background-color: #fafafa;'><%= totalFilledManually %></td>
										<td class='text-success' style='font-weight: bold; border-top: double #333333;background-color: #fafafa;'><%= totalFilledByCompetition + totalFilledManually %>&nbsp;(<%= totalVacancies > 0 ? (totalFilledByCompetition + totalFilledManually) * 100 / totalVacancies : 0  %>%)</td>
										<td class='text-danger' style='font-weight: bold; border-top: double #333333;background-color: #fafafa;'><%= totalVacancies - (totalFilledByCompetition + totalFilledManually) %></td>
									</tr>
									<tr>
										<td colspan='2' style='background-color: #fafafa;border-bottom: solid 1px #a0a0a0;'>&nbsp;</td>
										<td colspan='4' style='font-weight: bold;background-color: #fafafa;border-bottom: solid 1px #a0a0a0;'><%= totalNoAdCreated + totalAdSubmitted + totalAdApproved + totalAdPosted %></td>
										<td style='background-color: #fafafa;border-bottom: solid 1px #a0a0a0;'>&nbsp;</td>
										<td colspan='2' style='font-weight: bold;background-color: #fafafa;border-bottom: solid 1px #a0a0a0;'><%= totalNoShortlist + totalShortlistedNoRecommendation %></td>
										<td colspan='8' style='font-weight: bold;background-color: #fafafa;border-bottom: solid 1px #a0a0a0;'><%= totalRecSubmitted + totalRecRejected + totalRecApproved + totalRecAccepted +  totalRecOffered + totalRecOfferAccepted + totalRecOfferRejected + totalRecOfferExpired %></td>
										<td colspan='4' style='background-color: #fafafa;border-bottom: solid 1px #a0a0a0;'>&nbsp;</td>
									</tr>
									<tr>
										<td colspan='17' style='color:Green;text-transform:Uppercase;text-align:right; font-weight: bold;border-top: solid 1px #a0a0a0;'>Total Filled:</td>
										<td colspan='4' style='color:Green;text-align:center; font-weight: bold;border-top: solid 1px #a0a0a0;'><%= totalFilledByCompetition + totalFilledManually  %>&nbsp;(<%= totalVacancies > 0 ? (totalFilledByCompetition + totalFilledManually) * 100 / totalVacancies : 0  %>%)</td>
									</tr>
									<tr>
										<td colspan='17' style='color:Red;text-transform:Uppercase;text-align:right; font-weight: bold;'>Total Outstanding:</td>
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
											<td scope="row" style='text-align:center;font-size:18;color:Navy;'><a class="btn btn-info" href="listVacanciesWithOffersQueued.html"><%= stats.getOffersQueued() %></a></td>
											<td style='text-align:center;font-size:18;color:Green;'><a class="btn btn-info" href="listVacanciesWithOffersInprogress.html"><%= stats.getOffersInProgress() %></a></td>
											<td style='text-align:center;font-size:18;color:Red;'><a class="btn btn-info" href="listVacanciesWithOffersExpired.html"><%= stats.getOffersExpired() %></a></td>
										</tr>
									</tbody>
								</table>
							<div style="clear:both;"></div>	
							
						<div id='div-graphs'>
							<hr>
							<div style="font-size:14px;font-weight:bold;color:#1F4279;">Vacancies by Region</div>
								
							<canvas id="avalonDataChart" style="width:33%;float:left;height:200px;min-width:400px;"></canvas>
							<canvas id="centralDataChart" style="width:33%;float:left;height:200px;min-width:400px;"></canvas>
							<canvas id="westernDataChart" style="width:33%;float:left;height:200px;min-width:400px;"></canvas>							
							<canvas id="labradorDataChart" style="width:33%;float:left;height:200px;min-width:400px;"></canvas>
							<canvas id="provincialDataChart" style="width:33%;float:left;height:200px;min-width:400px;"></canvas>
							
							<div style="clear:both;"></div>	
							<br/>&nbsp;<br/>	
							<canvas id="totalVacanciesChart" style="width:33%;float:left;max-width:400px;min-width:300px;"></canvas>
							<canvas id="totalFilledChart" style="width:33%;float:left;max-width:400px;min-width:300px;"></canvas>							
							<canvas id="totalRemainChart" style="width:33%;float:left;max-width:400px;min-width:300px;"></canvas>
				
							<div style="clear:both;"></div>	
						</div>
							
						<br/>&nbsp;<br/>	
						
					</esd:SecurityAccessRequired>
					
					<c:if test="${ msg ne null }">  
          		<div class="alert alert-warning"  style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>WARNING:</b> ${ msg } </div>     
         	</c:if>	
         	<c:if test="${ msgOK ne null }">  
          		<div class="alert alert-success" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>SUCCESS:</b> ${ msgOK } </div>     
         	</c:if>	
         	<c:if test="${ msgERR ne null }">  
          		<div class="alert alert-danger"  style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>ERROR:</b> ${ msgERR } </div>     
         	</c:if>					
					
					</div>	
			</div>			
		</div>	
		
  <script>
  	$(function(){
  		
  		$('#lst_schoolyear').on('change', function(){
  			$(this).parent().submit();
  		});
  		
  		$('#lnk-showhide-graphs').on('click', function() {
  			if(getCookie('myhrp-index-show-graphs') == '1') {
  				setCookie('myhrp-index-show-graphs', '0');
  				$('#div-graphs').hide();
  				$(this).text('Show Graphs');
  			}
  			else {
  				setCookie('myhrp-index-show-graphs', '1');
  				createCharts();
  				$('#div-graphs').show();
  				$(this).text('Hide Graphs');
  			}
  			
  		});
  		
  		if(getCookie('myhrp-index-show-graphs') == '1') {
  			$('#lnk-showhide-graphs').text('Hide Graphs');
  			createCharts();
  			$('#div-graphs').show();
  		}
  		else {
  			$('#div-graphs').hide();
  			$('#lnk-showhide-graphs').text('Show Graphs');
  		}
  	});  
  
  	function createCharts() {
  		createTotalVacanciesChart();
  		createTotalFilledChart();
  		createTotalRemainChart();
  		createAvalonDataChart();
  		createCentralDataChart();
  		createWesternDataChart();
  		createLabradorDataChart();
  		createProvincialDataChart();
  	}
  	
	  function createTotalVacanciesChart() {
		  totalVacancies = totalVacanciesAvalon+totalVacanciesCentral+totalVacanciesWestern+ totalVacanciesLabrador+ totalVacanciesProvincial;
		  totalVacanciesAvalonPCT = (totalVacancies > 0 ? (totalVacanciesAvalon/totalVacancies)*100 : 0).toFixed(2);
		  totalVacanciesCentralPCT = (totalVacancies > 0 ? (totalVacanciesCentral/totalVacancies)*100 : 0).toFixed(2);
		  totalVacanciesWesternPCT = (totalVacancies > 0 ? (totalVacanciesWestern/totalVacancies)*100 : 0).toFixed(2);
		  totalVacanciesLabradorPCT = (totalVacancies > 0 ? (totalVacanciesLabrador/totalVacancies)*100 : 0).toFixed(2);
		  totalVacanciesProvincialPCT = (totalVacancies > 0 ? (totalVacanciesProvincial/totalVacancies)*100 : 0).toFixed(2);  
		  var ctx = document.getElementById('totalVacanciesChart').getContext('2d');
		  var totalVacanciesChart = new Chart(ctx, {
			  	type: 'pie',
			  	  data: {
			  	    labels: [ "Avalon: "+ totalVacanciesAvalon +" ("+totalVacanciesAvalonPCT+"%)",
			  	    	"Central: "+totalVacanciesCentral +" ("+totalVacanciesCentralPCT+"%)",
			  	    	"Western: "+totalVacanciesWestern +" ("+totalVacanciesWesternPCT+"%)",
			  	    	"Labrador: "+totalVacanciesLabrador +" ("+totalVacanciesLabradorPCT+"%)",
			  	    	"Provincial: "+totalVacanciesProvincial +" ("+totalVacanciesProvincialPCT+"%)",],
			  	    datasets: [{
			  	      backgroundColor: ["#bf0000","#00bf00","#ff8400","#7f82ff","#800080"],
			  	      data: [ totalVacanciesAvalonPCT, totalVacanciesCentralPCT, totalVacanciesWesternPCT, totalVacanciesLabradorPCT, totalVacanciesProvincialPCT]
			  	    }]
			  	  },
			  	  
			  	  options: {
			  		  	  
			  	      title: {
			  	         display: true,
			  	         fontSize: 14,
			  	         fontFamily:"Arial",
			  	         text: 'Total Vacancies by Region'
			  	     },
			  	     legend: {
			  	         display: true,
			  	         fontSize: 14,
			  	         position: 'bottom',
		
			  	     },
			  	     responsive: false
			  	 }
			});
	  }
	  
	  function createTotalFilledChart() {
		  totalFilled = totalFilledAvalon+totalFilledCentral+totalFilledWestern+ totalFilledLabrador+ totalFilledProvincial;
		  totalFilledAvalonPCT = (totalFilled > 0 ? (totalFilledAvalon/totalFilled)*100 : 0).toFixed(2);
		  totalFilledCentralPCT = (totalFilled > 0 ? (totalFilledCentral/totalFilled)*100 : 0).toFixed(2);
		  totalFilledWesternPCT = (totalFilled > 0 ? (totalFilledWestern/totalFilled)*100 : 0).toFixed(2);
		  totalFilledLabradorPCT = (totalFilled > 0 ? (totalFilledLabrador/totalFilled)*100 : 0).toFixed(2);
		  totalFilledProvincialPCT = (totalFilled > 0 ? (totalFilledProvincial/totalFilled)*100 : 0).toFixed(2);   
		  var ctx = document.getElementById('totalFilledChart').getContext('2d');  
		  var totalFilledChart = new Chart(ctx, {
		  	type: 'pie',
		  	  data: {
		  	    labels: [ "Avalon: "+ totalFilledAvalon +" ("+totalFilledAvalonPCT+"%)",
		  	    	"Central: "+totalFilledCentral +" ("+totalFilledCentralPCT+"%)",
		  	    	"Western: "+totalFilledWestern +" ("+totalFilledWesternPCT+"%)",
		  	    	"Labrador: "+totalFilledLabrador +" ("+totalFilledLabradorPCT+"%)",
		  	    	"Provincial: "+totalFilledProvincial +" ("+totalFilledProvincialPCT+"%)",],
		  	    datasets: [{
		  	    backgroundColor: ["#bf0000","#00bf00","#ff8400","#7f82ff","#800080"],
		  	    	data: [ 	 totalFilledAvalonPCT, totalFilledCentralPCT, totalFilledWesternPCT, totalFilledLabradorPCT, totalFilledProvincialPCT]
		  	    }]
		  	  },
		  	  
		  	  options: {
		  		  	  
		  	      title: {
		  	         display: true,
		  	         fontSize: 14,
		  	         text: 'Positions Filled by Region'
		  	     },
		  	     legend: {
		  	         display: true,
		  	         fontSize: 14,
		  	         position: 'bottom',
		
		  	     },
		  	     responsive: false
		  	 }
			});
	  }
	  
	  function createTotalRemainChart() {
		  totalRemain = totalRemainAvalon+totalRemainCentral+totalRemainWestern+ totalRemainLabrador+ totalRemainProvincial;
		  totalRemainAvalonPCT = (totalRemain > 0 ? (totalRemainAvalon/totalRemain)*100 : 0).toFixed(2);
		  totalRemainCentralPCT = (totalRemain > 0 ? (totalRemainCentral/totalRemain)*100 : 0).toFixed(2);
		  totalRemainWesternPCT = (totalRemain > 0 ? (totalRemainWestern/totalRemain)*100 : 0).toFixed(2);
		  totalRemainLabradorPCT = (totalRemain > 0 ? (totalRemainLabrador/totalRemain)*100 : 0).toFixed(2);
		  totalRemainProvincialPCT = (totalRemain > 0 ? (totalRemainProvincial/totalRemain)*100 : 0).toFixed(2);     
		  var ctx = document.getElementById('totalRemainChart').getContext('2d');
		  var totalRemainChart = new Chart(ctx, {
			  	type: 'pie',
			  	  data: {
			  	    labels: [ "Avalon: "+ totalRemainAvalon +" ("+totalRemainAvalonPCT+"%)",
			  	    	"Central: "+totalRemainCentral +" ("+totalRemainCentralPCT+"%)",
			  	    	"Western: "+totalRemainWestern +" ("+totalRemainWesternPCT+"%)",
			  	    	"Labrador: "+totalRemainLabrador +" ("+totalRemainLabradorPCT+"%)",
			  	    	"Provincial: "+totalRemainProvincial +" ("+totalRemainProvincialPCT+"%)",],
			  	    datasets: [{
			  	      backgroundColor: ["#bf0000","#00bf00","#ff8400","#7f82ff","#800080"],
			  	      data: [ totalRemainAvalonPCT, totalRemainCentralPCT, totalRemainWesternPCT, totalRemainLabradorPCT,totalRemainProvincialPCT]
			  	    }]
			  	  },
			  	  
			  	  options: {
			  		  	  
			  	      title: {
			  	         display: true,
			  	         fontSize: 14,
			  	         text: 'Positions Remaining by Region'
			  	     },
			  	     legend: {
			  	         display: true,
			  	         fontSize: 14,
			  	         position: 'bottom',
		
			  	     },
			  	     responsive: false
			  	 }  	    
			});
	  }
	  
	  function createAvalonDataChart() {
		  var ctx = document.getElementById('avalonDataChart').getContext('2d');
		  var avalonDataChart = new Chart(ctx, {
			  	type: 'bar',
			  	  data: {
			  		  fontFamily:"Arial",
			  	    labels: [ "Total","Filled","Remain",],
			  	    datasets: [{
			  	    	label: "",
			  	    	backgroundColor: ["#6495ED","#8FBC8F","#FFB6C1"],
			  	      data: [ totalVacanciesAvalon,totalFilledAvalon,totalRemainAvalon]
			  	    }]
			  	  },	  	  
			  	  options: {	  	
			  		  
			  		scales: {
			  	         yAxes: [{
			  	             ticks: {
			  	                 beginAtZero:true
			  	             }
			  	         }]
			  	     },
			  		  
			  	      title: {
			  	         display: true,
			  	         fontSize: 14,
			  	         text: 'Avalon Vacancies'
			  	     },  	     
			  	  
			  	     legend: {
			  	    	 display: false,
		
			  	     },
			  	     responsive: true
			  	 }  	  
			});
	  }
	  
	  function createCentralDataChart() {
		  var ctx = document.getElementById('centralDataChart').getContext('2d');
		  var centralDataChart = new Chart(ctx, {
			  	type: 'bar',
			  	  data: {
			  	    labels: [ "Total","Filled","Remain",],
			  	    datasets: [{
			  	    	label: "",
			  	      backgroundColor: ["#6495ED","#8FBC8F","#FFB6C1"],
			  	      data: [ totalVacanciesCentral,totalFilledCentral,totalRemainCentral]
			  	    }]
			  	  },	  	  
			  	  options: {	  
			  		scales: {
			  	         yAxes: [{
			  	             ticks: {
			  	                 beginAtZero:true
			  	             }
			  	         }]
			  	     },
			  	      title: {
			  	         display: true,
			  	         fontSize: 14,
			  	         text: 'Central Vacancies'
			  	     },
			  	     legend: {
			  	    	 display: false,
		
			  	     },
			  	     responsive: true
			  	 }
			});
	  }
	  
	  function createWesternDataChart() {
		  var ctx = document.getElementById('westernDataChart').getContext('2d');
		  var westernDataChart = new Chart(ctx, {
			  	type: 'bar',
			  	  data: {
			  		  labels: [ "Total","Filled","Remain",],
			  	    datasets: [{
			  	    	label: "",
			  	    	backgroundColor: ["#6495ED","#8FBC8F","#FFB6C1"],
			  	      data: [ totalVacanciesWestern,totalFilledWestern,totalRemainWestern]
			  	    }]
			  	  },	  	  
			  	  options: {	  		  	
			  		scales: {
			  	         yAxes: [{
			  	             ticks: {
			  	                 beginAtZero:true
			  	             }
			  	         }]
			  	     },
			  	      title: {
			  	         display: true,
			  	         fontSize: 14,
			  	         text: 'Western Vacancies'
			  	     },
			  	     legend: {
			  	    	 display: false,      
		
			  	     },
			  	     responsive: true
			  	 }  
			});
	  }
	  
	  function createLabradorDataChart() {
		  var ctx = document.getElementById('labradorDataChart').getContext('2d');
		  var labradorDataChart = new Chart(ctx, {
			  	type: 'bar',
			  	  data: {
			  		 labels: [ "Total","Filled","Remain",],
			  	    datasets: [{
			  	    	label: "",
			  	    	backgroundColor: ["#6495ED","#8FBC8F","#FFB6C1"],
			  	      data: [totalVacanciesLabrador,totalFilledLabrador,totalRemainLabrador]
			  	    }]
			  	  },	  	  
			  	  options: {	  		
			  		scales: {
			  	         yAxes: [{
			  	             ticks: {
			  	                 beginAtZero:true
			  	             }
			  	         }]
			  	     },
			  	      title: {
			  	         display: true,
			  	         fontSize: 14,	  	      
			  	         text: 'Labrador Vacancies'
			  	     },
			  	     legend: {
			  	    	 display: false,  
		
			  	     },
			  	     responsive: true
			  	 }
			});
	  }
	  
	  function createProvincialDataChart() {
		  var ctx = document.getElementById('provincialDataChart').getContext('2d');
		  var provincialDataChart = new Chart(ctx, {
			  	type: 'bar',
			  	  data: {
			  		 labels: [ "Total","Filled","Remain",],
			  	    datasets: [{
			  	    	label: "",
			  	    	backgroundColor: ["#6495ED","#8FBC8F","#FFB6C1"],
			  	      data: [ totalVacanciesProvincial,totalFilledProvincial,totalRemainProvincial]
			  	    }]
			  	  },	  	  
			  	  options: {	  
			  		scales: {
			  	         yAxes: [{
			  	             ticks: {
			  	                 beginAtZero:true
			  	             }
			  	         }]
			  	     },
			  	      title: {
			  	         display: true,
			  	         fontSize: 14,
			  	         text: 'Provincial Vacancies'
			  	     },
			  	     legend: {
			  	    	 display: false,
				  	        	  	      
		
			  	     },
			  	     responsive: true
			  	 }	  	  
			  	  
			});  
	  }
	  
	  function setCookie(cname, cvalue) {
		  var d = new Date();
		  d.setTime(d.getTime() + (365 * 24 * 60 * 60 * 1000));
		  var expires = "expires="+d.toUTCString();
		  document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
		}

		function getCookie(cname) {
		  var name = cname + "=";
		  var ca = document.cookie.split(';');
		  for(var i = 0; i < ca.length; i++) {
		    var c = ca[i];
		    while (c.charAt(0) == ' ') {
		      c = c.substring(1);
		    }
		    if (c.indexOf(name) == 0) {
		      return c.substring(name.length, c.length);
		    }
		  }
		  return "";
		}
  </script>
  </body>
</html>
