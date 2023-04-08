<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*,
                  com.esdnl.servlet.Form,
                  com.esdnl.personnel.v2.model.sds.bean.*,
                  com.esdnl.personnel.v2.database.sds.*,
                  java.util.stream.*" 
         isThreadSafe="false"%>

		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW,RTH-VIEW-SHORTLIST" />

<%
  JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");

	if(job == null) {
		response.sendRedirect("/MemberService/Personnel/admin_index.jsp");
		return;
	}
	
  JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job);
  AdRequestBean ad = null;
  RequestToHireBean rth = null;
  User usr = (User)session.getAttribute("usr");
  
  Form f = (Form) request.getAttribute("FORM");
  
  if(job.getIsSupport().equals("N")) {
	  ad = AdRequestManager.getAdRequestBean(job.getCompetitionNumber());
  }
  else {
	  rth = RequestToHireManager.getRequestToHireByCompNum(job.getCompetitionNumber());
  }
  
  boolean seniorityHire = false;
  Map<String, ApplicantProfileBean> permApplicants = null;
	if(job.getJobType().equal(JobTypeConstant.REGULAR)) {
		//permApplicants = ApplicantProfileManager.getCompetitionShortlistPermanentCandidates(job.getCompetitionNumber());
		Map<String, EmployeeBean> empBeans = EmployeeManager.getEmployeeBeanByCompetitionShortlist(job);
		
		permApplicants = Arrays.stream(ApplicantProfileManager.getApplicantShortlist(job))
				.filter(a -> empBeans.values().stream().anyMatch(e -> e.is(a) && e.isPermanent()))
				.collect(Collectors.toMap(a -> a.getSIN(), a -> a));
		
		seniorityHire = (permApplicants.size() > 0);
	}
%>

<html>
<head>
	<title>MyHRP Applicant Profiling System</title>

	<script type='text/javascript'>
		$("#loadingSpinner").css("display","none");
		
		$(function() {
			isSeniorityHire = <%= seniorityHire %>;
			$('#btn-refresh-candidate-info').click(function(){
				onCandidateSelected($('#candidate_name').val());
			});
							
			<%if(f != null){%>
				onError();
			<%}%>
		});
	</script> 
	<style>
		.tableTitle {font-weight:bold;width:20%;}
		.tableResult {font-weight:normal;width:80%;}
		.tableQuestionNum {font-weight:bold;width:5%;}
		.tableQuestion {width:95%;}
		.ratingQuestionNum {font-weight:bold;width:5%;}
		.ratingQuestion {width:75%;}
		.ratingAnswer {width:20%;}
		input[type="radio"] {margin-top: -1px;margin-left:6px;margin-right:2px;vertical-align: middle;}
	</style>	
	</head>
	
	<body>
	
	<div class="pageHeader">Competition # <%=job.getCompetitionNumber()%> Candidate Recommendation</div>

	<%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){ %>
			<div class="alert alert-danger" style="text-align:center;"><%=(String)request.getAttribute("msg")%></div>
	<%} %> 

	<% if(seniorityHire) { %>
		<p class="alert alert-success">This competition qualifies for a SENIORITY-BASED HIRE</p>
	<% } %>
          
  	<div id="msgError" class="alert alert-danger" style="text-align:center;display:none;"></div>								
  	<div id="msgSuccess" class="alert alert-success" style="text-align:center;display:none;"></div>
	<div id="msgPTR" class="alert alert-warning" style="text-align:center;display:none;"></div>
	<form action="" method="POST" name="admin_rec_form" id="admin_rec_form">
		<input type='hidden' name='op' id='op' value='CONFIRM' /> 
		<input type='hidden' name='comp_num' id='comp_num' value='<%=job.getCompetitionNumber()%>' /> 
		<input type='hidden' name='jobtype' id='jobtype' value='<%=job.getIsSupport()%>' />

		<div class="panel panel-success">
			<div class="panel-heading">SECTION 1: Candidate Information</div>
			<div class="panel-body">

				<table class="table table-condensed" style="font-size: 12px;">
					<tbody>
						<tr>
							<td class="tableTitle">Candidate Name:</td>
							<td class="tableResult">
								<table width=100%>
									<tr>
										<td width=90%><job:JobShortlistSelect id="candidate_name"
												onChange="onCandidateSelected(this.value);"
												cls="form-control"
												value='<%=(((f != null)&&(f.get("candidate_name") != null))?f.get("candidate_name"):"")%>' />

										</td>
										<td width=10%><a href="#" id='btn-refresh-candidate-info'
											class="btn btn-sm btn-warning"
											style='display: none; float: right;'>Refresh</a></td>
									</tr>
								</table>
							</td>
						</tr>
					</tbody>
				</table>

				<div id="candidate_info" style="padding-top: 5px; display: none;">
					<table class="table table-condensed"
						style="font-size: 12px; border-bottom: 1px solid DimGrey;">
						<tbody>
							<tr>
								<td class="tableTitle">Address:</td>
								<td class="tableResult"><span id="candidate_address"></span></td>
							</tr>
							<tr>
								<td class="tableTitle">Telephone:</td>
								<td class="tableResult"><span id="candidate_telephone"></span></td>
							</tr>
							<tr>
								<td colspan=2><a style="float: right;" href="#"
									class="btn btn-xs btn-info"
									onclick="$('#request_reference_info').toggle();">Request a
										Reference Check?</a></td>
							</tr>
						</tbody>
					</table>


					<div id='request_reference_info'
						style='display: none; background-color: #FFF8DC;'>
						<div style="font-size: 14px; color: Green; font-weight: bold;">Request
							a Reference Check</div>
						<table class="table table-condensed"
							style="font-size: 12px; border-bottom: 1px solid DimGrey;">
							<tbody>
								<tr>
									<td class="tableTitle">Email Address:</td>
									<td class="tableResult"><input type='text'
										id='referrer_email' name='referrer_email'
										placeholder="Enter Email Address to Send Reference"
										class='form-control'></td>
								</tr>
								<tr>
									<td class="tableTitle">Reference Type:</td>
									<td class="tableResult">
										<table width=100%>
											<tr>
												<td width=80%><select name="reftype"
													class="form-control" id="reftype">
														<option value="-1">--- Select Reference Type---</option>
														<% if(job.getIsSupport().equals("N")){ %>
														<option value="ADMIN">Administrator</option>
														<option value="GUIDE">Guidance Counsellor</option>
														<option value="TEACHER">Teacher</option>
														<option value="EXTERNAL">External</option>
														<%}else{ %>
														<option value="MANAGE">Management</option>
														<option value="SUPPORT">Support</option>
														<%} %>
												</select></td>
												<td width=20%><a style="float: right;" href="#"
													class="btn btn-xs btn-warning"
													onclick="onSendReferenceCheckRequest();">Send Request</a></td>
											</tr>
										</table>
										<div id='sending_email_msg' class="alert alert-info"
											style="display: none;">Sending Request...</div>
									</td>
								</tr>
								<tr>
									<td class="tableTitle">Make this a Telephone reference
										check?</td>
									<td class="tableResult"><a href="#"
										class="btn btn-xs btn-success"
										onclick="onManualReferenceCheckRequest();">YES</a>

										<div id="request_response_row" style='display: none;'
											class="alert alert-danger">
											<span id="request_response_msg" style="color: #FF0000;"></span>
										</div></td>
								</tr>
							</tbody>
						</table>

					</div>
					<% if(job.getIsSupport().equals("Y")){ %>
					<div style="font-size: 14px; color: Green; font-weight: bold;">
						<input type='checkbox' id="chknoref" name="chknoref" value="Y"
							onclick="NoReferenceSelected()">&nbsp;&nbsp;&nbsp;No
						Reference/Interview Guide Required For This Position
					</div>
					<%} %>

					<br />
					<div style="clear: both;"></div>
					<div style="font-size: 14px; color: Green; font-weight: bold;">Current
						Reference Request(s)</div>
					<div id='current_ref_requests'>No requests currently sent.</div>
					<div style="clear: both;"></div>
					<div style="font-size: 14px; color: Green; font-weight: bold;">Most
						Recent Candidate References</div>
					<div style="font-size: 11px; padding-bottom: 5px;">In order
						to make a recommendation, you must select a listed candidate
						reference and interview summary below. If not available, you
						cannot proceed.</div>
					<div id='current_refs' style='color: #FF0000;'>No references
						on file.</div>
					<div style="clear: both;"></div>
					<div style="font-size: 14px; color: Green; font-weight: bold;">Most
						Recent Candidate Interview Summaries</div>
					<div id='current_interview_summaries' style='color: #FF0000;'>No
						interview summaries on file.</div>
					<div style="clear: both;"></div>


				</div>

			</div>
		</div>

		<div id='candidate-recommendation-info' style='display: none;'>


			<div class="panel panel-success">
				<div class="panel-heading">SECTION 2: School/Position</div>
				<div class="panel-body">

					<table class="table table-striped table-condensed"
						style="font-size: 12px; padding-top: 3px;">
						<tbody>
							<tr>
								<td width="15%"><b>SCHOOL:</b></td>
								<td width="85%"><%=(ass[0].getLocation() > 0)? ass[0].getLocationText():"N/A"%></td>
							</tr>
							<tr>
								<td><b>REGION:</b></td>
								<td><%=ass[0].getRegionText()%></td>
							</tr>
							<%if(job.getIsSupport().equals("N")){%>
							<tr>
								<td><b>% UNIT:</b></td>
								<td><%=new DecimalFormat("0.00").format(ad.getUnits())%></td>
							</tr>
							<%} %>
							<tr>
								<td><b>POSITION:</b></td>
								<td>
									<%if(job.getIsSupport().equals("N")){%> <job:PositionType
										id='position' cls='form-control'
										onChange='onPositionTypeSelected(this);'
										value='<%=(((f != null)&&(f.get("position")!= null))?PositionTypeConstant.get(f.getInt("position")):null)%>' />
									<%}else{ %> <%=rth.getJobTitle()%> <input type="hidden"
									id="position" name="position" class="form-control"
									value="<%=rth.getPositionType()%>"> <%}%>
									<div id='recommended_position_other_row'
										style='display: none; margin-top: 5px;'>
										<b>If other, please describe:</b><br />
										<div id="S2Q1_Error" class="alert alert-danger"
											style="display: none;">ERROR: Character limit exceeded.
											You are only allowed to input 500 characters.</div>
										<textarea class="form-control" name="position_other"
											id="position_other"><%=(((f != null)&&(f.get("position_other")!=null))?f.get("position_other"):"")%></textarea>
										<div
											style="width: 100%; margin-top: 2px; text-align: right; font-size: 9; color: grey;">
											Max Characters: 500 - Remain: <span id="S2Q1_remain">500</span>
										</div>

									</div>

								</td>
							</tr>
							<% if(job.getIsSupport().equals("N")){ %>
							<tr>
								<td colspan=2><job:GradeSubjectUnitPercentage
										cls="form-control" /></td>
							</tr>
							<tr>
								<td colspan=2><div id='gsu_display'
										style='padding-top: 5px; border-top: solid 1px #e4e4e4;'>
										<span style='color: #FF0000;'>None Added.</span>
									</div></td>
							</tr>
							<%} %>


						</tbody>
					</table>
				</div>
			</div>

			<script>
$('#position_other').keypress(function(e) {
    var tval = $('#position_other').val(),
        tlength = tval.length,
        set = 500,
        remain = parseInt(set - tlength);
    $('#S2Q1_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#S2Q1_Error').css('display','block').delay(4000).fadeOut();
        $('#position_other').val((tval).substring(0, tlength - 1))
    }
})

</script>


			<div class="panel panel-success">
				<div class="panel-heading">SECTION 3: Questions</div>
				<div class="panel-body">
					<table class="table table-striped table-condensed"
						style="font-size: 12px; padding-top: 3px;">
						<tbody>
							<tr>
								<td width="40%"><b> <% if(job.getIsSupport().equals("N")){ %>
										1. Does this teacher own a Permanent Contract with the Board?
										<%}else{%> 1. Does this employee own a Permanent Contract with
										the Board? <%} %>
								</b></td>
								<td width="60%"><div id="perm_status">&nbsp;</div></td>
							</tr>
							<tr>
								<td><b>2. Does this appointment fill an existing
										position?</b></td>
								<td>
									<% if(job.getIsSupport().equals("N")){ %> <%=(!ad.isVacantPosition()?"YES":"NO")%>
									- <%= ad.getJobType() %> <%}else{ %> <%=org.apache.commons.lang.StringUtils.isNotEmpty(rth.getPreviousIncumbent())?"Yes":"No" %>
									<%} %> <% if(job.getIsSupport().equals("N")){ %> <%if(!ad.isVacantPosition() || (ad.getOwner() != null) || org.apache.commons.lang.StringUtils.isNotEmpty(ad.getVacancyReason())) {%>

									<% if(ad.getOwner() != null) {%> <br />
								<b>Previous Teacher:</b> <%=ad.getOwner().getFullnameReverse()%>
									<% } %> <% if(org.apache.commons.lang.StringUtils.isNotEmpty(ad.getVacancyReason())) { %>
									<br />
								<b>Reason For Vacancy:</b> <%=ad.getVacancyReason()%> <% } %> <%}%>
									<%}else{%> <% if(org.apache.commons.lang.StringUtils.isNotEmpty(rth.getPreviousIncumbent())) { %>
									<br />
								<b>Previous Incumbent:</b> <%=rth.getPreviousIncumbent()%> <% } %>

									<%} %>


								</td>
							</tr>
							<tr>
								<td><b>3. Please indicate employment status being
										recommended.</b></td>
								<td>
									<% if(job.getIsSupport().equals("N")){ %> <job:EmploymentStatus
										id="recommended_status" cls='form-control'
										value='<%=(((f != null)&&(f.get("recommended_status") != null))?EmploymentConstant.get(f.getInt("recommended_status")):EmploymentConstant.TERM)%>' />
									<%}else{ %> <%=rth.getPositionTypeString()%> <input type="hidden"
									id="recommended_status" name="recommended_status"
									value="<%=rth.getPositionType()%>"> <%} %>
								</td>
							</tr>
							<% if(job.getIsSupport().equals("Y")){ %>
							<tr>
								<td><b>4. Position Details.</b></td>
								<td><b>UNION:</b> <%=rth.getUnionCodeString()%> &middot; <b>POSITION:</b>
									<%=rth.getPositionNameString()%> <%=rth.getPositionSalary() == null?"":" &middot; <b>SALARY:</b> "+rth.getPositionSalary()%>
								</td>
							</tr>
							<%} %>

							<% if(job.getIsSupport().equals("N")){ %>
							<tr>
								<td><b>4. Start Date (dd/mm/yyyy):</b></td>
								<td><%=ad.getFormatedStartDate()%></td>
							</tr>
							<%}else{ %>
							<tr>
								<td><b>5. Start Date (dd/mm/yyyy):</b></td>
								<td><%=rth.getStartDateFormatted()%></td>
							</tr>
							<%} %>


						</tbody>
					</table>


				</div>
			</div>


			<div class="panel panel-success">
				<div class="panel-heading">SECTION 4: Summary</div>
				<div class="panel-body">

					<table class="table table-striped table-condensed"
						style="font-size: 12px; padding-top: 3px; border-top: 1px solid silver;">
						<tbody>
							<%int numCnt=1; %>
							<% if(job.getIsSupport().equals("N")){ %>

							<tr>
								<td width="5%"><%=numCnt++ %></td>
								<td width="95%"><b>Teaching methods completed: </b>
									<div id="trn_mtd" width="*">&nbsp;</div></td>
							</tr>
							<tr>
								<td><%=numCnt++ %></td>
								<td><b>Newfoundland Teacher Certification Level: </b>
									<div id="cert_lvl" width="*">&nbsp;</div></td>
							</tr>

							<%} %>
							<tr>
								<td><%=numCnt++ %></td>
								<td><b>Candidates Interviewed:</b><br /> <job:JobShortlistDisplay
										cls='form-control' /></td>
							</tr>
							<tr>
								<td><%=numCnt++ %></td>
								<td><b>Interview Panel:</b><br />
									<div id="S4Q4_Error" class="alert alert-danger"
										style="display: none;">ERROR: Charater limit exceeded.
										You are only allowed to input 1500 characters.</div> <textarea
										class="form-control" name="Interview_Panel"
										id="Interview_Panel"><%=(((f != null)&&(f.get("Interview_Panel") != null))?f.get("Interview_Panel"):"")%></textarea>
									<div
										style="width: 100%; margin-top: 2px; text-align: right; font-size: 9; color: grey;">
										Max Characters: 1500 - Remain: <span id="S4Q4_remain">1500</span>
									</div></td>
							</tr>
							<tr>
								<td><%=numCnt++%></td>
								<td><b>Are the references satisfactory?</b> <% if(job.getIsSupport().equals("Y")){ %>
									<input type="radio" name="References_Satisfactory" value="NA"
									CHECKED>NA <input type="radio"
									name="References_Satisfactory" value="Yes"
									<%=(((f != null)&&(f.get("References_Satisfactory") != null)&& f.get("References_Satisfactory").equalsIgnoreCase("YES"))?"CHECKED":"")%>>Yes
									<input type="radio" name="References_Satisfactory" value="No"
									<%=(((f != null)&&(f.get("References_Satisfactory") != null)&& f.get("References_Satisfactory").equalsIgnoreCase("NO"))?"CHECKED":"")%>>No
									<%}else{%> <input type="radio" name="References_Satisfactory"
									value="Yes"
									<%=(((f != null)&&(f.get("References_Satisfactory") != null)&& f.get("References_Satisfactory").equalsIgnoreCase("YES"))?"CHECKED":"")%>>Yes
									<input type="radio" name="References_Satisfactory" value="No"
									<%=(((f != null)&&(f.get("References_Satisfactory") != null)&& f.get("References_Satisfactory").equalsIgnoreCase("NO"))?"CHECKED":"")%>>No
									<%} %></td>
							</tr>
							<tr>
								<td><%=numCnt++ %></td>
								<td><b>Should any special conditions be attached to
										this appointment?</b> <input type="radio"
									name="Special_Conditions" value="Yes"
									onclick="$('#special_conditions_row').css('display','block');"
									<%=(((f != null)&&(f.get("Special_Conditions") != null)&& f.get("Special_Conditions").equalsIgnoreCase("YES"))?"CHECKED":"")%>>Yes
									<input type="radio" name="Special_Conditions" value="No"
									onclick="document.getElementById('Special_Conditions_Comment').value='';$('#special_conditions_row').css('display','none');"
									<%=(((f != null)&&(f.get("Special_Conditions") != null)&& f.get("Special_Conditions").equalsIgnoreCase("NO"))?"CHECKED":"")%>>No

									<div id="special_conditions_row"
										style='display: none; background-color: #FFF8DC;'>
										<br />
										<b>If Yes, please explain.</b><br />
										<div id="S4Q6_Error" class="alert alert-danger"
											style="display: none;">ERROR: Charater limit exceeded.
											You are only allowed to input 1500 characters.</div>
										<textarea class="form-control"
											name="Special_Conditions_Comment"
											id="Special_Conditions_Comment"><%=(((f != null)&&(f.get("Special_Conditions_Comment") != null))?f.get("Special_Conditions_Comment"):"")%></textarea>
										<div
											style="width: 100%; margin-top: 2px; text-align: right; font-size: 9; color: grey;">
											Max Characters: 1500 - Remain: <span id="S4Q6_remain">1500</span>
										</div>
									</div></td>
							</tr>
							<tr>
								<td><%=numCnt++ %></td>
								<td><b>Other comments:</b><br />
									<div id="S4Q7_Error" class="alert alert-danger"
										style="display: none;">ERROR: Charater limit exceeded.
										You are only allowed to input 1500 characters.</div> <textarea
										class="form-control" name="Other_Comments" id="Other_Comments"><%=(((f != null)&&(f.get("Other_Comments") != null))?f.get("Other_Comments"):"")%></textarea>
									<div
										style="width: 100%; margin-top: 2px; text-align: right; font-size: 9; color: grey;">
										Max Characters: 1500 - Remain: <span id="S4Q7_remain">1500</span>
									</div></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<script>
$('#Interview_Panel').keypress(function(e) {
    var tval = $('#Interview_Panel').val(),
        tlength = tval.length,
        set = 1500,
        remain = parseInt(set - tlength);
    $('#S4Q4_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#S4Q4_Error').css('display','block').delay(4000).fadeOut();
        $('#Interview_Panel').val((tval).substring(0, tlength - 1))
    }
})


$('#Special_Conditions_Comment').keypress(function(e) {
    var tval = $('#Special_Conditions_Comment').val(),
        tlength = tval.length,
        set = 1500,
        remain = parseInt(set - tlength);
    $('#S4Q6_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#S4Q6_Error').css('display','block').delay(4000).fadeOut();
        $('#Special_Conditions_Comment').val((tval).substring(0, tlength - 1))
    }
})

$('#Other_Comments').keypress(function(e) {
    var tval = $('#Other_Comments').val(),
        tlength = tval.length,
        set = 1500,
        remain = parseInt(set - tlength);
    $('#S4Q7_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#S4Q7_Error').css('display','block').delay(4000).fadeOut();
        $('#Other_Comments').val((tval).substring(0, tlength - 1))
    }
})
</script>




			<div class="panel panel-success">
				<div class="panel-heading">SECTION 5: Recommendation(s)</div>
				<div class="panel-body">
					<table class="table table-condensed" style="font-size: 11px;">
						<tbody>
							<tr>
								<td class="tableTitle">RECOMMENDED CANDIDATE:</td>
								<td class="tableResult"><div id="candidate_name_s">&nbsp;</div>
									<br />
									<div id="S5Q1_Error" class="alert alert-danger"
										style="display: none;">ERROR: Charater limit exceeded.
										You are only allowed to input 1500 characters.</div> <textarea
										class="form-control" name="rec_cand_comments"
										id="rec_cand_comments"><%=(((f != null)&&(f.get("rec_cand_comments") != null))?f.get("rec_cand_comments"):"")%></textarea>
									<div
										style="width: 100%; margin-top: 2px; text-align: right; font-size: 9; color: grey;">
										Max Characters: 1500 - Remain: <span id="S5Q1_remain">1500</span>
									</div></td>
							</tr>
							<tr>
								<td class="tableTitle">OTHER RECOMMENDABLE CANDIDATE:</td>
								<td class="tableResult"><job:JobShortlistSelect
										id="candidate_2" cls="form-control"
										value='<%=(((f != null)&&(f.get("candidate_2") != null))?f.get("candidate_2"):"")%>' /><br />
									<div id="S5Q2_Error" class="alert alert-danger"
										style="display: none;">ERROR: Charater limit exceeded.
										You are only allowed to input 1500 characters.</div> <textarea
										class="form-control" name="rec_cand_comments2"
										id="rec_cand_comments2"><%=(((f != null)&&(f.get("rec_cand_comments2") != null))?f.get("rec_cand_comments2"):"")%></textarea>
									<div
										style="width: 100%; margin-top: 2px; text-align: right; font-size: 9; color: grey;">
										Max Characters: 1500 - Remain: <span id="S5Q2_remain">1500</span>
									</div></td>
							</tr>
							<tr>
								<td class="tableTitle">OTHER RECOMMENDABLE CANDIDATE:</td>
								<td class="tableResult"><job:JobShortlistSelect
										id="candidate_3" cls="form-control"
										value='<%=(((f != null)&&(f.get("candidate_3") != null))?f.get("candidate_3"):"")%>' /><br />
									<div id="S5Q3_Error" class="alert alert-danger"
										style="display: none;">ERROR: Charater limit exceeded.
										You are only allowed to input 1500 characters.</div> <textarea
										class="form-control" name="rec_cand_comments3"
										id="rec_cand_comments3"><%=(((f != null)&&(f.get("rec_cand_comments3") != null))?f.get("rec_cand_comments3"):"")%></textarea>
									<div
										style="width: 100%; margin-top: 2px; text-align: right; font-size: 9; color: grey;">
										Max Characters: 1500 - Remain: <span id="S5Q3_remain">1500</span>
									</div></td>
							</tr>
						</tbody>
					</table>

				</div>
			</div>

			<script>
$('#rec_cand_comments').keypress(function(e) {
    var tval = $('#rec_cand_comments').val(),
        tlength = tval.length,
        set = 1500,
        remain = parseInt(set - tlength);
    $('#S5Q1_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#S5Q1_Error').css('display','block').delay(4000).fadeOut();
        $('#rec_cand_comments').val((tval).substring(0, tlength - 1))
    }
})

$('#rec_cand_comments2').keypress(function(e) {
    var tval = $('#rec_cand_comments2').val(),
        tlength = tval.length,
        set = 1500,
        remain = parseInt(set - tlength);
    $('#S5Q2_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#S5Q2_Error').css('display','block').delay(4000).fadeOut();
        $('#rec_cand_comments2').val((tval).substring(0, tlength - 1))
    }
})

$('#rec_cand_comments3').keypress(function(e) {
    var tval = $('#rec_cand_comments3').val(),
        tlength = tval.length,
        set = 1500,
        remain = parseInt(set - tlength);
    $('#S5Q3_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#S5Q3_Error').css('display','block').delay(4000).fadeOut();
        $('#rec_cand_comments3').val((tval).substring(0, tlength - 1))
    }
})

</script>


			<div class="panel panel-success">
				<div class="panel-heading">SECTION 6: Completion</div>
				<div class="panel-body">
					<table class="table table-striped table-condensed"
						style="font-size: 12px; padding-top: 3px;">
						<tbody>
							<tr>
								<td width="30%"><b>RECOMMENDATION COMPLETED BY:</b></td>
								<td width="70%"><span style="text-transform: Capitalize;"><%= usr.getPersonnel().getFullNameReverse() %></span></td>
							</tr>
							<tr>
								<td><b>DATE:</b></td>
								<td><%= new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()) %></td>
							</tr>
						</tbody>
					</table>
					<div align="center">
						<input type='submit' value='Submit Recommendation'
							class="btn btn-xs btn-success"
							onclick="return validateAdminComments();">
					</div>

				</div>
			</div>

		</div>
	</form>


	<div align="center">               
                                <%if(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW")){%>
                                  <a href='admin_view_job_applicants.jsp' class="btn btn-xs btn-danger">Back to Applicant List</a>
                                <%}%>
             </div>  
             <br/><br/>               
</body>
</html>
