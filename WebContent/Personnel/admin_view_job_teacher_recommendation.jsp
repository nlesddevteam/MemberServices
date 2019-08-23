<%@ page language="java"
	import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.awsd.personnel.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*"
	isThreadSafe="false"%>


<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job"%>

<esd:SecurityCheck
	permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
<esd:SecurityRequiredPageObjectsCheck names='<%=new String[]{"JOB"}%>'
	scope='<%=PageContext.SESSION_SCOPE%>'
	redirectTo="/Personnel/admin_index.jsp" />
<esd:SecurityRequiredPageObjectsCheck names='<%=new String[]{"RECOMMENDATION_BEAN"}%>'
	scope='<%=PageContext.REQUEST_SCOPE%>'
	redirectTo="/Personnel/admin_index.jsp" />

<%
	JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");

	TeacherRecommendationBean rec = (TeacherRecommendationBean) request.getAttribute("RECOMMENDATION_BEAN");

	ApplicantProfileBean candidate = ApplicantProfileManager.getApplicantProfileBean(rec.getCandidateId());
	ApplicantEsdExperienceBean esd_exp = ApplicantEsdExperienceManager
			.getApplicantEsdExperienceBean(rec.getCandidateId());
	ApplicantEducationOtherBean exp_other = ApplicantEducationOtherManager
			.getApplicantEducationOtherBean(rec.getCandidateId());

	int candidateCount = 1;
	ApplicantProfileBean candidate_2 = null;
	if (!StringUtils.isEmpty(rec.getCandidate2())) {
		candidate_2 = ApplicantProfileManager.getApplicantProfileBean(rec.getCandidate2());
		candidateCount++;
	}

	ApplicantProfileBean candidate_3 = null;
	if (!StringUtils.isEmpty(rec.getCandidate3())) {
		candidate_3 = ApplicantProfileManager.getApplicantProfileBean(rec.getCandidate3());
		candidateCount++;
	}

	JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job);
	AdRequestBean ad = null;
	RequestToHireBean rth = null;

	if (job.getIsSupport().equals("N")) {
		ad = AdRequestManager.getAdRequestBean(job.getCompetitionNumber());

		if(ad == null) {
			request.setAttribute("msg", job.getCompetitionNumber() + " does not have an associated Ad Request. Please contact a system administrator");
			request.getRequestDispatcher("admin_index.jsp").forward(request, response);
			
			return;
		}
	} 
	else {
		rth = RequestToHireManager.getRequestToHireByCompNum(job.getCompetitionNumber());
	}

	User usr = (User) session.getAttribute("usr");
	InterviewGuideBean interviewGuide = InterviewGuideManager.getInterviewGuideBean(job);
	//ReferenceCheckRequestBean[] refReq = ReferenceCheckRequestManager.getReferenceCheckRequestBeans(job, candidate);
%>

<c:set var='candidateCount' value="<%=candidateCount%>" />
<c:set var='tform_colspan' value='${ref.totalInterviews + 5}' />



<html>
<head>
<title>MyHRP Applicant Profiling System</title>


<style>
@media print {
	.site-description {
		display: none;
	}
	.site-title {
		display: none;
	}
	@page {
		size: landscape;
		font-size: 9pt;
		width: 100%;
		margin: 30px;
		float: none;
	}
}

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

input {
	border: 1px solid silver;
}
</style>

<script>
	$("#loadingSpinner").css("display","none");

	$(function() {
				$('.datefield').datepicker({
					autoSize: true,
					showOn: 'focus',
					showAnim: 'drop',
					dateFormat: 'dd/mm/yy',
					changeMonth: true,
					changeYear: true
				});
				
				$('.rec-op-btn').click(function(){
					$('#recommendation-op').val($(this).attr('op'));
					
					$('#admin_rec_form').submit();
					
					return false;
				});
				
				$('.resend-notifications').click(function(){
					var params = {};
					
					params.id = '<%=rec.getRecommendationId()%>';

						$.post('/MemberServices/Personnel/ajax/resendRecommendationNotifications.html', params,
								function(data) {
									alert($(data).find('RESEND-TEACHER-RECOMMENDATION-NOTIFICATIONS-RESPONSE').attr('msg'));
								}
						);
				});
				
				$('.modal.printable').on('shown.bs.modal', function() {
					$('.modal-dialog', this).addClass('focused');
					$('body').addClass('modalprinter');

				}).on('hidden.bs.modal', function() {
					$('.modal-dialog', this).removeClass('focused');
					$('body').removeClass('modalprinter');
				});
		});

	function COC_Check(chkbox) {
		if (chkbox.checked == true)
			document.getElementById('accept_btn').style.display = 'inline';
		else
			document.getElementById('accept_btn').style.display = 'none';
	}

	function Phone_Contact_Check(chkbox) {
		if (chkbox.checked == true)
			document.getElementById('offer_btn').style.display = 'inline';
		else
			document.getElementById('offer_btn').style.display = 'none';
	}
</script>
</head>

<body>
	<br />
	<div style="font-size: 16px; color: DimGrey; font-weight: bold;">
		Competition #
		<%=job.getCompetitionNumber()%>
		Candidate Recommendation
	</div>
	<%
		if (!StringUtils.isEmpty((String) request.getAttribute("msg"))) {
	%>
	<div class="alert alert-warning" style="text-align: center;">
		<%=(String) request.getAttribute("msg")%>
	</div>
	<%
		}
	%>
	<form method="POST" name="admin_rec_form" id="admin_rec_form"
		action='jobRecommentationController.html'>
		<input type='hidden' name='id' value='<%=rec.getRecommendationId()%>' />
		<input type='hidden' name='op' id='recommendation-op' value='' />




		<div class="panel-group" style="padding-top: 5px;">
			<div class="panel panel-success">
				<div class="panel-heading">Section 1 - Candidate</div>
				<div class="panel-body">
					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<tbody>
							<tr>
								<td class="tableTitle">NAME:</td>
								<td class="tableResult"><%=candidate.getFullNameReverse()%>
									<a class="btn btn-xs btn-primary" style="float: right;"
									href="viewApplicantProfile.html?sin=<%=candidate.getUID()%>">VIEW
										PROFILE</a></td>
							</tr>
							<tr>
								<td class="tableTitle">EMAIL:</td>
								<td class="tableResult"><a
									href="mailto:<%=candidate.getEmail()%>"><%=candidate.getEmail()%></a></td>
							</tr>
							<tr>
								<td class="tableTitle">ADDRESS:</td>
								<td class="tableResult">
									<%
										if (!StringUtils.isEmpty(candidate.getAddress1())) {
									%> <%=candidate.getAddress1()%>, <%
 	}
 %> <%
 	if (!StringUtils.isEmpty(candidate.getAddress2())) {
 %> <%=candidate.getAddress2()%>, <%=candidate.getProvince()%>, <%=candidate.getCountry()%>
									&nbsp; &nbsp; <%=candidate.getPostalcode()%> <%
 	}
 %>
								</td>
							</tr>
							<%
								if (!StringUtils.isEmpty(candidate.getHomephone())) {
							%>
							<tr>
								<td class="tableTitle">TELEPHONE:</td>
								<td class="tableResult"><%=candidate.getHomephone()%></td>
							</tr>
							<%
								}
							%>
							<%
								if (!StringUtils.isEmpty(candidate.getCellphone())) {
							%>
							<tr>
								<td class="tableTitle">CELL:</td>
								<td class="tableResult"><%=candidate.getCellphone()%></td>
							</tr>
							<%
								}
							%>
							<%
								if (!StringUtils.isEmpty(candidate.getWorkphone())) {
							%>
							<tr>
								<td class="tableTitle">WORK:</td>
								<td class="tableResult"><%=candidate.getWorkphone()%></td>
							</tr>
							<%
								}
							%>
							<tr>
								<td class="tableTitle">SIN:</td>
								<td class="tableResult"><%=!StringUtils.isEmpty(candidate.getSIN2()) ? candidate.getSIN2()
					: "<span style='color:#FF0000;'>Currently not on file.</span>"%></td>
							</tr>
							<tr>
								<td class="tableTitle">DATE OF BIRTH (dd/mm/yyyy):</td>
								<td class="tableResult"><%=(candidate.getDOB() != null) ? candidate.getDOBFormatted()
					: "<span style='color:#FF0000;'>Currently not on file.</span>"%></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="panel panel-success">
				<div class="panel-heading">Section 2 - School/Region/Position</div>
				<div class="panel-body">
					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<tbody>
							<tr>
								<td class="tableTitle">SCHOOL:</td>
								<td class="tableResult"><%=(ass[0].getLocation() > 0) ? ass[0].getLocationText() : "&nbsp;"%></td>
							</tr>
							<tr>
								<td class="tableTitle">REGION/ZONE:</td>
								<td class="tableResult" style="text-transform: Capitalize;"><%=ass[0].getRegionText()%></td>
							</tr>
							<%
								if (job.getIsSupport().equals("N")) {
							%>
							<tr>
								<td class="tableTitle">% UNIT:</td>
								<td class="tableResult"><%=new DecimalFormat("0.00").format(ad.getUnits())%></td>
							</tr>
							<%
								}
							%>
							<%
								if (job.getIsSupport().equals("N")) {
							%>
							<tr>
								<td class="tableTitle">POSITION:</td>
								<td class="tableResult"><%=rec.getPositionType().getDescription()%>
									<%
										if (rec.getPositionType().equal(PositionTypeConstant.OTHER)) {
									%> ( <%=rec.getPositionTypeOther()%> ) <%
										}
									%></td>
							</tr>
							<%
								}
								else {
							%>
							<tr>
								<td class="tableTitle">POSITION:</td>
								<td class="tableResult"><%=rth.getJobTitle()%></td>
							</tr>
							<%
								}
							%>
							<tr>
								<td class="tableTitle">POSITION BREAKDOWN:</td>
								<td class="tableResult">
									<%
										if (job.getIsSupport().equals("N")) {
									%> <%
 	if (rec.getGSU().length > 0) {
 %>

									<table class="table table-striped table-condensed"
										style="font-size: 11px;">
										<thead>
											<tr>
												<th width="30%">GRADE</th>
												<th width="30%">SUBJECT</th>
												<th width="30%">UNIT %</th>
											</tr>
										</thead>
										<tbody>
											<%
												for (int i = 0; i < rec.getGSU().length; i++) {
											%>
											<tr>
												<td><%=rec.getGSU()[i].getGrade().getGradeName()%></td>
												<td><%=((rec.getGSU()[i].getSubject() != null) ? rec.getGSU()[i].getSubject().getSubjectName()
								: "<span style='color:Silver;'>N/A</span>")%></td>
												<td><%=rec.getGSU()[i].getUnitPercentage()%> %</td>
											</tr>
											<%
												}
											%>
										</tbody>
									</table> <%
 	}
 		else {
 %> <span style='color: Silver;'>None currently on file.</span> <%
 	}
 	}
 %>

								</td>
							</tr>
						</tbody>
					</table>


				</div>
			</div>

			<div class="panel panel-success">
				<div class="panel-heading">Section 3 - Contract</div>
				<div class="panel-body">

					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<tbody>
							<tr>
								<%
									if (job.getIsSupport().equals("N")) {
								%>
								<td class="tableQuestion">1.) Does this teacher own a
									Permanent Contract with the Board?</td>
								<%
									}
									else {
								%>
								<td class="tableQuestion">1.) Does this employee own a
									Permanent Contract with the Board?</td>
								<%
									}
								%>
								<td class="tableAnswer">
									<%
										String sch_str = null;
										if (esd_exp != null) {
											if ((esd_exp.getPermanentContractSchool() != 0) && (esd_exp.getPermanentContractSchool() != -1)) {
												sch_str = "YES<BR>" + esd_exp.getPermanentContractLocationText() + "<BR>"
														+ esd_exp.getPermanentContractPosition();
											}
											else if ((esd_exp.getContractSchool() != 0) && (esd_exp.getContractSchool() != -1)) {
												sch_str = "REPLACEMENT<BR>" + esd_exp.getReplacementContractLocationText() + "<BR>(Replacement End Date: "
														+ esd_exp.getFormattedContractEndDate() + ")";
											}
											else {
												sch_str = "NO";
											}
										}
										else
											sch_str = "NO";

										out.println(sch_str);
									%>
								</td>
							</tr>
							<tr>
								<td class="tableQuestion">2.) Does this appointment fill an
									existing position?</td>
								<td class="tableAnswer">
									<%
										if (job.getIsSupport().equals("N")) {
									%> <%=(!ad.isVacantPosition() ? "YES" : "NO")%> - <%=ad.getJobType()%>
									<%
										}
										else {
									%> <%=org.apache.commons.lang.StringUtils.isNotEmpty(rth.getPreviousIncumbent()) ? "Yes" : "No"%>
									<%
										}
									%> <%
 	if (job.getIsSupport().equals("N")) {
 %> <%
 	if (!ad.isVacantPosition() || (ad.getOwner() != null)
 				|| org.apache.commons.lang.StringUtils.isNotEmpty(ad.getVacancyReason())) {
 %> <%
 	if (ad.getOwner() != null) {
 %> <br />Previous Teacher: <%=ad.getOwner().getFullnameReverse()%>
									<%
										}
									%> <%
 	if (org.apache.commons.lang.StringUtils.isNotEmpty(ad.getVacancyReason())) {
 %> <br />Reason For Vacancy: <%=ad.getVacancyReason()%> <%
 	}
 %> <%
 	}
 %> <%
 	}
 	else {
 %> <%
 	if (org.apache.commons.lang.StringUtils.isNotEmpty(rth.getPreviousIncumbent())) {
 %> <br />Previous Incumbent: <%=rth.getPreviousIncumbent()%> <%
 	}
 %> <%
 	}
 %>
								</td>
							</tr>
							<tr>
								<td class="tableQuestion">3.) Please indicate employment
									status being recommended.</td>
								<td class="tableAnswer">
									<%
										if (job.getIsSupport().equals("N")) {
									%> <%=rec.getEmploymentStatus().getDescription()%> <%
 	}
 	else {
 %> <%=rth.getPositionTypeString()%> <%
 	}
 %>
								</td>
							</tr>

							<%
								if (job.getIsSupport().equals("Y")) {
							%>
							<tr>
								<td class="tableQuestion">4.) Position Details</td>
								<td class="tableAnswer">Union: <%=rth.getUnionCodeString()%><br />
									Position: <%=rth.getPositionNameString()%><br /> Salary: <%=rth.getPositionSalary()%>
								</td>
							</tr>
							<%
								}
							%>
							<tr>
								<%
									if (job.getIsSupport().equals("N")) {
								%>
								<td class="tableQuestion">4.) Start Date (mm/dd/yyyy):</td>
								<%
									}
									else {
								%>
								<td class="tableQuestion">5.) Start Date (mm/dd/yyyy):</td>
								<%
									}
								%>


								<%
									if (job.getIsSupport().equals("N")) {
								%>
								<%
									if (usr.checkPermission("PERSONNEL-ADMIN-ACCEPT-RECOMMENDATION") && rec.isApproved() && !rec.isAccepted()
												&& !rec.isRejected()) {
								%>
								<td class="tableAnswer"><input id='start-date'
									name='start_date' type='text' class='datefield form-control'
									value='<%=ad.getFormatedStartDate()%>' /></td>
								<%
									}
										else {
								%>
								<td class="tableAnswer"><%=ad.getFormatedStartDate()%></td>
								<%
									}
								%>
								<%
									}
									else {
								%>
								<%
									if (usr.checkPermission("PERSONNEL-ADMIN-ACCEPT-RECOMMENDATION") && rec.isApproved() && !rec.isAccepted()
												&& !rec.isRejected()) {
								%>
								<td class="tableAnswer"><input id='start-date'
									name='start_date' type='text'
									class='datefield requiredInputBox'
									value='<%=rth.getStartDateFormatted()%>' /></td>
								<%
									}
										else {
								%>
								<td class="tableAnswer"><%=rth.getStartDateFormatted()%></td>
								<%
									}
								%>
								<%
									}
								%>
							</tr>

							<%
								if (job.getIsSupport().equals("N")) {
							%>
							<tr>

								<td class="tableQuestion">5.) End Date (dd/mm/yyyy):</td>

								<%
									if (usr.checkPermission("PERSONNEL-ADMIN-ACCEPT-RECOMMENDATION") && rec.isApproved() && !rec.isAccepted()
												&& !rec.isRejected()) {
								%>
								<td class="tableAnswer"><input id='end-date'
									name='end_date' type='text' class='datefield requiredInputBox'
									value='<%=(ad.getEndDate() != null ? ad.getFormatedEndDate() : "")%>' /></td>
								<%
									}
										else {
								%>
								<td class="tableAnswer"><%=(ad.getEndDate() != null ? ad.getFormatedEndDate() : "Not Specified")%></td>
								<%
									}
								%>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>



				</div>
			</div>

			<div class="panel panel-success">
				<div class="panel-heading">Section 4</div>
				<div class="panel-body">

					<%
						int cntrx = 0;
					%>
					<%
						if (job.getIsSupport().equals("Y")) {
					%>
					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<tbody>
							<tr>
								<%
									cntrx++;
								%>
								<td class="tableQuestion"><%=cntrx%>. Candidates
									Interviewed:</td>
								<td class="tableAnswer"><job:JobShortlistDisplay
										cls='displayText1' /></td>
							</tr>
							<tr>
								<%
									cntrx++;
								%>
								<td class="tableQuestion">Interview Panel:</td>
								<td class="tableAnswer"><%=rec.getInterviewPanel()%></td>
							</tr>
							<tr>
								<%
									cntrx++;
								%>
								<td class="tableQuestion">Are the references satisfactory?</td>
								<td class="tableAnswer"><%=rec.getReferencesSatisfactory()%></td>
							</tr>





							<esd:SecurityAccessRequired
								permissions="PERSONNEL-ADMIN-APPROVE-RECOMMENDATION">
								<%
									NLESDReferenceListBean ref = NLESDReferenceListManager.getReferenceBeansByApplicantRec(rec.getReferenceId(),
													rec.getCandidateId());
								%>

								<tr>
									<%
										cntrx++;
									%>
									<td class="tableQuestion">Candidate Reference:</td>
									<td class="tableAnswer">
										<%
											if (ref != null) {
										%>
										<table class="table table-striped table-condensed"
											style="font-size: 11px;">
											<thead>
												<tr>
													<th width="30%">Reference Date</th>
													<th width="30%">Provided By</th>
													<th width="30%">Position</th>
													<th width="10%">Options</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td><%=ref.getProvidedDateFormatted()%></td>
													<td><%=ref.getProvidedBy()%></td>
													<td><%=ref.getProviderPosition()%></td>
													<td><a href="<%=ref.getViewUrl()%>">VIEW</a></td>
												</tr>
											</tbody>
										</table> <%
 	}
 			else {
 %> No reference checks found. <%
 	}
 %>

									</td>
								</tr>

							</esd:SecurityAccessRequired>
							<tr>
								<%
									cntrx++;
								%>
								<td class="tableQuestion">Should any special conditions be
									attached to this appointment?</td>
								<td class="tableAnswer"><%=rec.getSpecialConditions()%> <%
 	if (rec.getSpecialConditions().equalsIgnoreCase("Yes")) {
 %> <br /><%=rec.getSpecialConditionsComment()%> <%
 	}
 %></td>
							</tr>
							<tr>
								<%
									cntrx++;
								%>
								<td class="tableQuestion">Other comments:</td>
								<td class="tableAnswer"><%=(!StringUtils.isEmpty(rec.getOtherComments()) ? rec.getOtherComments() : "&nbsp;")%></td>
							</tr>
						</tbody>
					</table>

					<%
						}
						else {
					%>
					<%
						if (exp_other != null) {
					%>
					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<tbody>
							<tr>
								<%
									cntrx++;
								%>
								<td class="tableQuestion"><%=cntrx%>. Teaching methods
									completed:</td>
								<td class="tableAnswer"><%=exp_other.getProfessionalTrainingLevel().getDescription()%></td>
							</tr>
							<tr>
								<%
									cntrx++;
								%>
								<td class="tableQuestion"><%=cntrx%>. Newfoundland Teacher
									Certification Level:</td>
								<td class="tableAnswer"><%=exp_other.getTeachingCertificateLevel()%></td>
							</tr>
							<tr>
								<%
									cntrx++;
								%>
								<td class="tableQuestion"><%=cntrx%>. Candidates
									Interviewed:</td>
								<td class="tableAnswer"><job:JobShortlistDisplay
										cls='displayText' /></td>
							</tr>
							<tr>
								<%
									cntrx++;
								%>
								<td class="tableQuestion"><%=cntrx%>. Interview Panel</td>
								<td class="tableAnswer"><%=rec.getInterviewPanel()%></td>
							</tr>

							<tr>
								<%
									cntrx++;
								%>
								<td class="tableQuestion"><%=cntrx%>. Are the references
									satisfactory?</td>
								<td class="tableAnswer"><%=rec.getReferencesSatisfactory()%></td>
							</tr>

							<tr>
								<%
									cntrx++;
								%>
								<td class="tableQuestion"><%=cntrx%>. Reference Check:</td>
								<td class="tableAnswer"><esd:SecurityAccessRequired
										permissions="PERSONNEL-ADMIN-APPROVE-RECOMMENDATION">

										<%
											NLESDReferenceListBean ref = NLESDReferenceListManager.getReferenceBeansByApplicantRec(rec.getReferenceId(),
																rec.getCandidateId());
														if (ref != null) {
										%>

										<table class="table table-striped table-condensed"
											style="font-size: 11px;">
											<thead>
												<tr>
													<th width="30%">Reference Date</th>
													<th width="30%">Provided By</th>
													<th width="30%">Position</th>
													<th width="10%">Options</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td><%=ref.getProvidedDateFormatted()%></td>
													<td><%=ref.getProvidedBy()%></td>
													<td><%=ref.getProviderPosition()%></td>
													<td><a href="<%=ref.getViewUrl()%>">VIEW</a></td>
												</tr>
											</tbody>
										</table>
										<%
											}
														else {
										%>
			                                      	No reference checks found.
			                                      	<%
											}
										%>
									</esd:SecurityAccessRequired></td>
							</tr>

							<tr>
								<%
									cntrx++;
								%>
								<td class="tableQuestion"><%=cntrx%>. Should any special
									conditions be attached to this appointment?</td>
								<td class="tableAnswer"><%=rec.getSpecialConditions()%> <%
 	if (rec.getSpecialConditions().equalsIgnoreCase("Yes")) {
 %> <%=rec.getSpecialConditionsComment()%> <%
 	}
 %></td>
							</tr>



							<tr>
								<%
									cntrx++;
								%>
								<td class="tableQuestion"><%=cntrx%>. Other comments:</td>
								<td class="tableAnswer"><%=(!StringUtils.isEmpty(rec.getOtherComments()) ? rec.getOtherComments() : "&nbsp;")%></td>
							</tr>

						</tbody>
					</table>


					<%
						}
							else {
					%>
					<%
						if (job.getIsSupport().equals("N")) {
					%>

					<div class="alert alert-danger">
						<b>ERROR:</b> Candidate has an incompleted profile, and must be
						completed before proceeding with the recommendation process.
					</div>

					<%
						}
					%>
					<%
						}
					%>
					<%
						}
					%>



				</div>
			</div>

			<div class="panel panel-success">
				<div class="panel-heading">Section 5 - Recommended
					Candidate(s)</div>
				<div class="panel-body">
					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<tbody>
							<tr>
								<td class="tableTitle">Recommended Candidate:</td>
								<td class="tableResult"><%=candidate.getFullNameReverse()%>
									(<a href="mailto:<%=candidate.getEmail()%>"><%=candidate.getEmail()%></a>)</td>
							</tr>
							<tr>
								<td class="tableTitle">Other Recommendable Candidate:</td>
								<td class="tableResult"><%=((candidate_2 != null) ? candidate_2.getFullName() : "NONE SELECTED")%></td>
							</tr>
							<tr>
								<td class="tableTitle">Other Recommendable Candidate:</td>
								<td class="tableResult"><%=((candidate_3 != null) ? candidate_3.getFullName() : "NONE SELECTED")%></td>
							</tr>
						</tbody>
					</table>


					<div align="center">
						<!-- don't open in modal, but goto page and disable menu and allow back -->

						<a class="btn btn-primary btn-xs"
							href="viewInterviewGuide.html?guideId=<%=interviewGuide.getGuideId()%>&vji=readOnly">View
							Interview Guide</a>
						<!-- Open in Modal, not fancybox -->
						<a class="btn btn-primary btn-xs" href="#" data-toggle="modal"
							data-target="#trackingForm" title="Recommendation Tracking Form">View
							Recommendation Tracking Form</a>
					</div>

				</div>
			</div>


			<div class="panel panel-success">
				<div class="panel-heading">Section 6 - Recommendation and
					Offer Status Log</div>
				<div class="panel-body">

					<div style="font-size: 14px; color: DimGrey;">Recommendation
						Status Log</div>
					<br />

					<table class="table table-striped table-condensed"
						style="font-size: 11px;">

						<tbody>
							<%
								int cntrR = 0;
							%>
							<%
								if (rec.isRecommended()) {
									cntrR++;
							%>
							<tr>
								<td><b><%=cntrR%>. <c:set var="recommendDate"
											value="<%=rec.getRecommendedDate()%>" /> <fmt:formatDate
											type="date" pattern="EEEE MMMM d, y" value="${recommendDate}" />:</b>
									Recommendation <span style="color: #2E8B57;">COMPLETED</span>
									by <span style='text-transform: Capitalize;'><%=rec.getRecommendedByPersonnel().getFullNameReverse()%></span>
									(<a style="color: Grey;"
									href='mailto:<%=rec.getRecommendedByPersonnel().getEmailAddress()%>?subject=Recommendation Completion for <%=job.getCompetitionNumber()%>'><%=rec.getRecommendedByPersonnel().getEmailAddress()%></a>)
									<%=(rec.getRecommendedByPersonnel().getPersonnelCategory().getPersonnelCategoryName() != null)
						? rec.getRecommendedByPersonnel().getPersonnelCategory().getPersonnelCategoryName()
						: ""%> <%=(rec.getRecommendedByPersonnel().getSchool() != null)
						? "of " + rec.getRecommendedByPersonnel().getSchool().getSchoolName()
						: ""%>.</td>
							</tr>
							<%
								}
							%>
							<%
								if (rec.isApproved()) {
									cntrR++;
							%>
							<tr>
								<td><b><%=cntrR%>. <c:set var="approvedDate"
											value="<%=rec.getApprovedDate()%>" /> <fmt:formatDate
											type="date" pattern="EEEE MMMM d, y" value="${approvedDate}" />:</b>
									Recommendation <span style="color: #2E8B57;">APPROVED</span> by
									<span style='text-transform: Capitalize;'><%=rec.getApprovedByPersonnel().getFullNameReverse()%></span>
									(<a style="color: Grey;"
									href='mailto:<%=rec.getApprovedByPersonnel().getEmailAddress()%>?subject=Recommendation Approval for <%=job.getCompetitionNumber()%>'><%=rec.getApprovedByPersonnel().getEmailAddress()%></a>)
									<%=(rec.getApprovedByPersonnel().getPersonnelCategory().getPersonnelCategoryName() != null)
						? rec.getApprovedByPersonnel().getPersonnelCategory().getPersonnelCategoryName()
						: ""%>.</td>
							</tr>
							<%
								}
							%>
							<%
								if (rec.isAccepted()) {
									cntrR++;
							%>
							<tr>
								<td><b><%=cntrR%>. <c:set var="acceptDate"
											value="<%=rec.getAcceptedDate()%>" /> <fmt:formatDate
											type="date" pattern="EEEE MMMM d, y" value="${acceptDate}" />:</b>
									Recommendation <span style="color: #2E8B57;">ACCEPTED</span> by
									<span style='text-transform: Capitalize;'><%=rec.getAcceptedByPersonnel().getFullNameReverse()%></span>
									(<a style="color: Grey;"
									href='mailto:<%=rec.getAcceptedByPersonnel().getEmailAddress()%>?subject=Recommendation Acceptance for <%=job.getCompetitionNumber()%>'><%=rec.getAcceptedByPersonnel().getEmailAddress()%></a>)
									<%=(rec.getAcceptedByPersonnel().getPersonnelCategory().getPersonnelCategoryName() != null)
						? rec.getAcceptedByPersonnel().getPersonnelCategory().getPersonnelCategoryName()
						: ""%>.</td>
							</tr>
							<%
								}
							%>
							<%
								if (rec.isRejected()) {
									cntrR++;
							%>
							<tr>
								<td><b><%=cntrR%>. <c:set var="rejectDate"
											value="<%=rec.getRejectedDate()%>" /> <fmt:formatDate
											type="date" pattern="EEEE MMMM d, y" value="${rejectDate}" />:</b>
									Recommendation <span style="color: Red;">REJECTED</span> by <span
									style='text-transform: Capitalize;'><%=rec.getRejectedByPersonnel().getFullNameReverse()%></span>
									(<a style="color: Grey;"
									href='mailto:<%=rec.getRejectedByPersonnel().getEmailAddress()%>?subject=Recommendation Rejection for <%=job.getCompetitionNumber()%>'><%=rec.getRejectedByPersonnel().getEmailAddress()%></a>)
									<%=(rec.getRejectedByPersonnel().getPersonnelCategory().getPersonnelCategoryName() != null)
						? rec.getRejectedByPersonnel().getPersonnelCategory().getPersonnelCategoryName()
						: ""%>.</td>
							</tr>
							<%
								}
							%>

						</tbody>
					</table>
					<br />
					<div style="font-size: 14px; color: DimGrey;">Position Offer
						Status Log</div>
					<br />

					<table class="table table-striped table-condensed"
						style="font-size: 11px;">

						<tbody>
							<%
								int cntrO = 0;
							%>
							<%
								if (rec.isOfferMade()) {
									cntrO++;
							%>
							<tr>
								<td><b><%=cntrO%>. <c:set var="offermadeDate"
											value="<%=rec.getOfferMadeDate()%>" /> <fmt:formatDate
											type="date" pattern="EEEE MMMM d, y" value="${offermadeDate}" />:</b>
									Position Offer <span style="color: #2E8B57;">MADE</span> by <span
									style='text-transform: Capitalize;'><%=rec.getOfferMadeByPersonnel().getFullNameReverse()%></span>
									(<a style="color: Grey;"
									href='mailto:<%=rec.getOfferMadeByPersonnel().getEmailAddress()%>?subject=Offer Made for <%=job.getCompetitionNumber()%>'><%=rec.getOfferMadeByPersonnel().getEmailAddress()%></a>)
									<%=(rec.getOfferMadeByPersonnel().getPersonnelCategory().getPersonnelCategoryName() != null)
						? rec.getOfferMadeByPersonnel().getPersonnelCategory().getPersonnelCategoryName()
						: ""%></td>
							</tr>
							<%
								}
							%>
							<%
								if (rec.isOfferIgnored()) {
									cntrO++;
							%>
							<tr>
								<td><b><%=cntrO%>. <c:set var="offervalidDate"
											value="<%=rec.getOfferValidDate()%>" /> <fmt:formatDate
											type="date" pattern="EEEE MMMM d, y"
											value="${offervalidDate}" />:</b> Position Offer <span
									style="color: Red;">EXPIRED</span> on <%=rec.getOfferValidDateFormatted()%>.
								</td>
							</tr>
							<%
								}
							%>
							<%
								if (rec.isOfferAccepted()) {
									cntrO++;
							%>
							<tr>
								<td><b><%=cntrO%>. <c:set var="offeracceptedDate"
											value="<%=rec.getOfferAcceptedDate()%>" /> <fmt:formatDate
											type="date" pattern="EEEE MMMM d, y"
											value="${offeracceptedDate}" />:</b> Position Offer <span
									style="color: #2E8B57;">ACCEPTED</span> by <span
									style='text-transform: Capitalize;'><%=rec.getCandidate().getFullNameReverse()%></span>
									(<a style="color: Grey;"
									href='mailto:<%=rec.getCandidate().getEmail()%>'><%=rec.getCandidate().getEmail()%></a>)
									on <%=rec.getOfferAcceptedDateFormatted()%> <%
 	if (rec.isLetterOfOfferRequire()) {
 %> <br />Note: Paper copy of offer letter requested. <%
 	}
 %></td>
							</tr>
							<%
								}
							%>
							<%
								if (rec.isOfferRejected()) {
									cntrO++;
							%>
							<tr>
								<td><b><%=cntrO%>. <c:set var="offerrejectedDate"
											value="<%=rec.getOfferRejectedDate()%>" /> <fmt:formatDate
											type="date" pattern="EEEE MMMM d, y"
											value="${offerrejectedDate}" />:</b> Position Offer <span
									style="color: Red;">REJECTED</span> by <span
									style='text-transform: Capitalize;'><%=rec.getCandidate().getFullNameReverse()%></span>
									(<a style="color: Grey;"
									href='mailto:<%=rec.getCandidate().getEmail()%>'><%=rec.getCandidate().getEmail()%></a>)

								</td>
							</tr>
							<%
								}
							%>
							<%
								if (rec.isProcessed()) {
									cntrO++;
							%>
							<tr>
								<td><b><%=cntrO%>.<c:set var="offerprocessedDate"
											value="<%=rec.getProcessedDate()%>" /> <fmt:formatDate
											type="date" pattern="EEEE MMMM d, y"
											value="${offerprocessedDate}" />:</b> Position Offer <span
									style="color: #2E8B57;">PROCESSED</span> by <span
									style='text-transform: Capitalize;'><%=rec.getProcessedByPersonnel().getFullNameReverse()%></span>
									(<a style="color: Grey;"
									href='mailto:<%=rec.getProcessedByPersonnel().getEmailAddress()%>'><%=rec.getProcessedByPersonnel().getEmailAddress()%></a>)
									<%=(rec.getProcessedByPersonnel().getPersonnelCategory().getPersonnelCategoryName() != null)
						? rec.getProcessedByPersonnel().getPersonnelCategory().getPersonnelCategoryName()
						: ""%></td>
							</tr>
							<%
								}
							%>

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</form>

	<div align="center">
		<%
			if (exp_other != null || job.getIsSupport().equals("Y")) {
		%>
		<job:RecommendationControllerButtons recommendation='<%=rec%>' />
		<%
			}
			else {
		%>
		<a class="btn btn-danger btn-xs" href="javascript:history.go(-1);">Back</a>
		<%
			}
		%>
	</div>
	<br />
	<br />
	<!-- Tracking Form Modal -->

	<div id="trackingForm" class="modal fade" role="dialog">
		<div class="modal-dialog" style="width: 90%; min-width: 300px;">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-body">
					<div id="printModal">
						<b>Competition #</b>
						<%=job.getCompetitionNumber()%>
						<%
							if (job.getIsSupport().equals("N")) {
						%>
						(<b>Position:</b>
						<%=rec.getPositionType().getDescription()%>,
						<%=(ass[0].getLocation() > 0) ? ass[0].getLocationText() : "&nbsp;"%>)
						<%
							}
							else {
						%>
						()<b>Position:</b>
						<%=rec.getRth_position_type().getDescription()%>,
						<%=(ass[0].getLocation() > 0) ? ass[0].getLocationText() : "&nbsp;"%>)
						<%
							}
						%>

						<c:choose>
							<c:when test="${fn:length(TFORM) gt 0}">
								<table class="table table-condensed table-striped"
									style="font-size: 11px; background-color: #FFFFFF;">
									<c:forEach items='${TFORM}' var='ref' varStatus="theCount">
										<c:if test="${theCount.first}">
											<thead>
												<tr>
													<th width="20%" rowspan="2">CANDIDATE</th>
													<%
														if (job.getIsSupport().equals("N")) {
													%>
													<th width="15%" rowspan="2">QUALIFICATIONS</th>
													<th width="15%" rowspan="2">EXPERIENCE</th>
													<%
														}
													%>
													<c:choose>
														<c:when test="${ref.referenceScale eq '3'}">
															<th width="20%" colspan="2">REFERENCES [Scale of
																0-3]</th>
														</c:when>
														<c:otherwise>
															<th width="20%" colspan="2">REFERENCES [Scale of
																1-4]</th>
														</c:otherwise>
													</c:choose>

													<th width="*" colspan="${ref.totalInterviews}">NORMALIZED
														INTERVIEW SCORES<br>[Rating:
														${ref.scaleBottom}-${ref.scaleTop}(${ref.scaleTop} -
														Strong Answer)]
													</th>

												</tr>

												<tr>
													<th width="20%" colspan="2">Average Score</th>
													<c:set var="myValue" value="${ref.interviewHeader}" />
													<c:out value="${myValue}" escapeXml="false" />
												</tr>
											</thead>
										</c:if>


										<tr>
											<td>${theCount.count}.&nbsp;${ref.candidateName}</td>
											<%
												if (job.getIsSupport().equals("N")) {
											%>
											<td><c:set var="countChars"
													value="${fn:length(ref.qualifications)}" /> <c:choose>
													<c:when test="${not empty ref.qualifications}">${fn:substring(ref.qualifications, 0, countChars-2)}</c:when>
													<c:otherwise>
														<span style="color: Silver;">N/A</span>
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${not empty ref.experience}">${ref.experience}</c:when>
													<c:otherwise>
														<span style="color: Silver;">N/A</span>
													</c:otherwise>
												</c:choose></td>
											<%
												}
											%>
											<td colspan='2'>${ref.referenceScore}</td>
											<c:set var="myValue2" value="${ref.interviewRow}" />
											<c:out value="${myValue2}" escapeXml="false" />

										</tr>


										<c:if test="${theCount.count eq candidateCount}">
											<tr>
												<td colspan='${tform_colspan+3}'><b>OTHER
														SHORTLISTED CANDIDATE(S)</b></td>
											</tr>
										</c:if>



									</c:forEach>
									<tr style="border-top: 1px solid Silver;">
										<td colspan='8'></td>
									</tr>
								</table>
							</c:when>
							<c:otherwise>
	       
	              No data currently on file.
	               
	         </c:otherwise>
						</c:choose>

						<hr>
						<c:choose>
							<c:when test="${fn:length(TFORM) gt 0}">
								<table class="table table-condensed table-striped"
									style="font-size: 11px; background-color: #FFFFFF;">
									<thead>
										<tr>
											<th width="15%">CANDIDATE</th>
											<th width="10%">RECOMMENDATION</th>
											<th width="75%">COMMENTS</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items='${TFORM}' var='ref' varStatus="theCount">

											<tr>
												<td>${theCount.count}.&nbsp;${ref.candidateName}</td>
												<td><c:choose>
														<c:when test="${not empty ref.referenceRecommendation}">${ref.referenceRecommendation}</c:when>
														<c:otherwise>
															<span style="color: Silver;">N/A</span>
														</c:otherwise>
													</c:choose></td>
												<td><c:choose>
														<c:when test="${not empty ref.comments}">${ref.comments}</c:when>
														<c:otherwise>
															<span style="color: Silver;">N/A</span>
														</c:otherwise>
													</c:choose></td>
											</tr>

											<c:if test="${theCount.count eq candidateCount}">
												<tr>
													<td colspan='3'><b>OTHER SHORTLISTED CANDIDATE(S)</b></td>
												</tr>
											</c:if>
										</c:forEach>
										<tr style="border-top: 1px solid Silver;">
											<td colspan='3'></td>
										</tr>
									</tbody>
								</table>

							</c:when>
							<c:otherwise>
	         No data found.
	              
	         </c:otherwise>
						</c:choose>


						<hr>

						<table class="table table-condensed table-striped"
							style="font-size: 11px; background-color: #FFFFFF;">
							<thead>
								<tr>
									<th width="100%">SHORTLISTED CANDIDATE(S) - DECLINED
										INTERVIEW</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when
										test="${fn:length(JOB_SHORTLIST_INTERVIEW_DECLINES) gt 0}">
										<c:forEach items='${JOB_SHORTLIST_INTERVIEW_DECLINES}'
											var='app'>
											<tr>
												<td>${app.fullNameReverse}</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td class='displayText'>None currently on file.</td>
										</tr>
									</c:otherwise>
								</c:choose>
								<tr style="border-top: 1px solid Silver;">
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div align="center">
						<a href='#' class="btn btn-xs btn-primary"
							title='Print this page (pre-formatted)'
							onclick="jQuery('#printModal').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Human Resources Profile System</b></div><br/><br/>'});">Print</a>
						<button type="button" class="btn btn-danger btn-xs"
							data-dismiss="modal">Close</button>
					</div>

				</div>



			</div>


		</div>
	</div>





</body>
</html>
