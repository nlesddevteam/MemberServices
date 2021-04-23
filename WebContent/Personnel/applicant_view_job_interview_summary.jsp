<%@ page language="java"
	import="java.util.*,
                  java.text.*,
                  java.lang.reflect.*,
                  com.esdnl.personnel.jobs.bean.*, 
                  org.apache.commons.lang.*"
	isThreadSafe="false"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job"%>

<job:ApplicantLoggedOn />

<%
  	InterviewSummaryBean summary = (InterviewSummaryBean) request.getAttribute("summary"); 
	InterviewGuideBean guide = (InterviewGuideBean) request.getAttribute("guide");
	DecimalFormat df = new DecimalFormat("#,##0.00");
%>


<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>
<script>
function goBack() {
  window.history.back();
}
</script>
<style>
.tableTitle {
	font-weight: bold;
	width: 20%;
}

.tableResult {
	font-weight: normal;
	width: 80%;
}

.tableQuestion {
	font-weight: bold;
	width: 40%;
}

.tableAnswer {
	font-weight: normal;
	width: 60%;
}

.tableTitleL {
	font-weight: bold;
	width: 15%;
}

.tableResultL {
	font-weight: normal;
	width: 35%;
}
</style>

</head>

<body>
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success">
			<div class="panel-heading">
				<b>View Interview Summary</b> for Competition #<%= summary.getCompetition().getCompetitionNumber() %></div>
			<div class="panel-body">
				<table class="table table-striped table-condensed"
					style="font-size: 11px;">
					<tbody>
						<tr>
							<td class="tableTitle">Candidate:</td>
							<td class="tableResult"><%=summary.getCandidate().getFullName()%></td>
						</tr>
						<tr>
							<td class="tableTitle">Competition Number:</td>
							<td class="tableResult"><%= summary.getCompetition().getCompetitionNumber() %></td>
						</tr>
						<tr>
							<td class="tableTitle">Administrative?</td>
							<td class="tableResult"><%= summary.isAdministrative() ? "YES" : "NO" %></td>
						</tr>
						<tr>
							<td class="tableTitle">Leadership?</td>
							<td class="tableResult"><%= summary.isLeadership() ? "YES" : "NO" %></td>
						</tr>
						<tr>
							<td class="tableTitle">Strengths</td>
							<td class="tableResult"><%= summary.getStrengths() %></td>
						</tr>
						<tr>
							<td class="tableTitle">Gaps</td>
							<td class="tableResult"><%= summary.getGaps() %></td>
						</tr>
						<tr>
							<td class="tableTitle">Scores:<br /> <!--                             		
	                              		<div id="scorePc" style="float:left;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;display:none;"></div>
	                              		 -->
							</td>
							<td class="tableResult"><%= guide.getQuestionCount() %>
								questions - Each ranked from <%= guide.getRatingScaleBottom() %>
								to <%= guide.getRatingScaleTop() %>, with <%= guide.getRatingScaleTop() %>
								being outstanding.

								<table class="table table-striped table-condensed"
									style="font-size: 11px;">
									<thead>
										<tr>
											<th width="20%">Interviewer</th>

											<%
	                                    //Calculate the logic somehow re a percentage from the weighted logic. (3x15 = 45)
	                                    double perCalc=0; 
	                                    double numQ=0; 	                                   
	                                    for(int i=1; i <= guide.getQuestionCount(); i++) { %>
											<th width="10%" style="text-align: center;">Q/C<%= i %>(<%= (guide.getQuestions().get(i-1).getWeight()) %>)
											</th>
											<%
                               			perCalc = perCalc + guide.getQuestions().get(i-1).getWeight();
                               			numQ++; 
                               			} %>
											<th width="10%;" style="text-align: center;">Weighted<br />Total
												Score
											</th>
											<th width="10%;" style="text-align: center;">Weighted<br />Avg.
												Score
											</th>
										</tr>
									</thead>
									<tbody>
										<%
	                                    perCalc=(perCalc*guide.getRatingScaleTop())/numQ;int tdnum=0; 
	                                    
	                                    if(summary.getInterviewSummaryScoreBeans() != null && summary.getInterviewSummaryScoreBeans().size() > 0){
	                                    					Method method = null;
	                                    					ArrayList<InterviewGuideQuestionBean> questions = guide.getQuestions();
	                                    					InterviewGuideQuestionBean question = null;
                                    						for(InterviewSummaryScoreBean iss : summary.getInterviewSummaryScoreBeans()) {
                                    							if(StringUtils.isNotEmpty(iss.getInterviewer())) { 
                                    								tdnum=0; %>
										<tr>
											<td><%= iss.getInterviewer() %></td>

											<% for(int i=1; i<= guide.getQuestionCount(); i++) {
					                                    						question = questions.get(i-1);
					                                    						method = iss.getClass().getDeclaredMethod("getScore" + i);
					                                    						double score = ((Double)method.invoke(iss)).doubleValue();
					                                    				%>

											<td style="text-align: center;">
												<%tdnum++; %><%=(((score > 0)&&(question.getWeight() > 0)) ? Double.toString(score) : "0")%></td>

											<%}%>

											<td style="text-align: center;"><%= df.format(iss.getWeigthedTotalScore(guide)) %></td>
											<td style="text-align: center;"><%= df.format(iss.getOverallScore(guide)) %></td>
										</tr>
										<%}%>
										<%}%>

										<tr>
											<td style="font-weight: bold;" colspan="<%=tdnum+2%>">Average
												Score</td>
											<td style="font-weight: bold; text-align: center;"><%= df.format(summary.getOverallScore(guide))%></td>
										</tr>
										<!-- 
										<tr>
											<td style="font-weight: bold;" colspan="<%=tdnum+2%>">Percentage:
											</td>
											<td style="font-weight: bold; text-align: center;">
												<%double roundOff = Math.round((summary.getOverallScore(guide)/perCalc) * 100.0); 
	                                    				if (roundOff <= 50) { %> <span
												style="color: Red;"><%=roundOff%>%</span> <% } else if (roundOff >50 && roundOff < 80){ %>
												<span style="color: Orange;"><%=roundOff%>%</span> <% } else if (roundOff >=80){ %>
												<span style="color: Green;"><%=roundOff%>%</span> <%} else {%>
												<span style="color: Black;"><%=roundOff%>%</span> <%} %> <script>$("#scorePc").css("display","block").html(<%=roundOff%>+"%");</script>

											</td>
										</tr>
										 -->
										<%}else{%>
										<tr>
											<td colspan="<%=tdnum+3%>">No interviewer/scoring
												information found.</td>
										</tr>
										<%}%>
									</tbody>
								</table></td>
						</tr>
						<tr>
							<td class="tableTitle">Recommendation:</td>
							<td class="tableResult"><%= summary.getRecommendation().getText() %></td>
						</tr>
					</tbody>
				</table>

				<br /> <br />
				<div align="center">
					<a href="#" class="btn btn-sm btn-danger" onclick="goBack()">Go
						Back</a>
				</div>


			</div>
		</div>
	</div>
</body>
</html>
