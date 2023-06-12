<%@ page language="java"
         import="java.util.*,
         				 java.util.stream.*,
		                 java.text.*,
		                 java.text.SimpleDateFormat,  
						 java.util.Date,
						 java.util.concurrent.TimeUnit,
		                 com.awsd.school.*,
		                 com.awsd.school.bean.*,
		                 com.awsd.school.dao.*,
		                 com.esdnl.util.*,
		                 com.esdnl.personnel.jobs.bean.*,
		                 com.esdnl.personnel.jobs.dao.*,
		                 com.esdnl.personnel.jobs.constants.*,
		                 com.esdnl.personnel.v2.model.sds.bean.*,
		                 com.esdnl.personnel.v2.database.sds.*,
		                 org.apache.commons.lang.StringUtils,
		                 com.nlesd.school.bean.*,
		                 com.nlesd.school.service.*"  
         isThreadSafe="false"%>

		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
		
<job:ApplicantLoggedOn/>

<%
  ApplicantProfileBean profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
	JobOpportunityBean[] jobs = JobOpportunityManager.getApplicantOpenJobOpportunityBeans(profile.getSIN());
  ApplicantEsdExperienceBean esd_exp = ApplicantEsdExperienceManager.getApplicantEsdExperienceBean(profile.getSIN());
  ApplicantEducationBean[] edu = ApplicantEducationManager.getApplicantEducationBeans(profile.getSIN());
  ApplicantEducationOtherBean edu_oth = ApplicantEducationOtherManager.getApplicantEducationOtherBean(profile.getSIN());
  ApplicantEsdReplacementExperienceBean[] rpl = ApplicantEsdReplExpManager.getApplicantEsdReplacementExperienceBeans(profile.getSIN());
  ApplicantSubstituteTeachingExpBean[] sub = ApplicantSubExpManager.getApplicantSubstituteTeachingExpBeans(profile.getSIN());
  ApplicantExperienceOtherBean[] exp_other = ApplicantExpOtherManager.getApplicantExperienceOtherBeans(profile.getSIN());
  ApplicantOtherInformationBean other_info = ApplicantOtherInfoManager.getApplicantOtherInformationBean(profile.getSIN());
  ApplicantSupervisorBean[] refs = ApplicantSupervisorManager.getApplicantSupervisorBeans(profile.getSIN());
  RegionBean[] regionPrefs = ApplicantRegionalPreferenceManager.getApplicantRegionalPreferencesMap(profile).values().toArray(new RegionBean[0]);
  Collection<ApplicantDocumentBean> docs = ApplicantDocumentManager.getApplicantDocumentBean(profile).stream().filter(dd -> dd.getTypeSS() != null).collect(Collectors.toList());
  Collection<ApplicantCriminalOffenceDeclarationBean> cods = ApplicantCriminalOffenceDeclarationManager.getApplicantCriminalOffenceDeclarationBeans(profile);
  ApplicantNLESDPermanentExperienceBean[] per = ApplicantNLESDPermExpManager.getApplicantNLESDPermanentExperienceBeans(profile.getSIN());
  Map<String, JobOpportunityBean> highlyRecommendedPools = JobOpportunityManager.getApplicantHighlyRecommendedPoolCompetitionsMap(profile.getSIN());
  ApplicantPositionOfferBean[] current_offers = ApplicantPositionOfferManager.getApplicantPositionOfferBeans(profile);
  
  Collection<ApplicantPositionOfferBean> emp_letters = ApplicantPositionOfferManager.getApplicantEmploymentLetters(profile).stream()
  		.sorted((ApplicantPositionOfferBean p1, ApplicantPositionOfferBean p2) -> {
  			return  -1 * p1.getRecommendation().getOfferAcceptedDate().compareTo(p2.getRecommendation().getOfferAcceptedDate());
  		})
  		.collect(Collectors.toList()); 
  
  Map<Integer, ApplicantSubListInfoBean> sublists = ApplicantSubListInfoManager.getApplicantSubListInfoBeanMap(profile);
  Collection<InterviewSummaryBean> interviewSummaries = InterviewSummaryManager.getInterviewSummaryBeans(profile);
  interviewSummaries = interviewSummaries.stream().filter(isb -> (isb.getCompetition().getJobType().getValue() == 8 || isb.getCompetition().getJobAwardedDate() != null) && !isb.getCompetition().isUnadvertise()).collect(Collectors.toList());
  
  EmployeeBean empbean = EmployeeManager.getEmployeeBeanByApplicantProfile(profile);
  
  SimpleDateFormat sdf_refcheck = new SimpleDateFormat("dd/MM/yyyy");
  SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
  SimpleDateFormat sdf_medium = new SimpleDateFormat("MMM d, yyyy");
  SimpleDateFormat sdf_long = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
  //used to populate sub prefs
  Collection<SchoolZoneBean> zones = SchoolZoneService.getSchoolZoneBeans();
  School[] schools = null;
  HashMap<Integer, School> sel = ApplicantSubPrefManager.getApplicantSubPrefsMap(profile);  
  HashMap<Integer, ApplicantSubListInfoBean> sublists2  = ApplicantSubListInfoManager.getApplicantSubListInfoBeanMap(profile);
  
	ApplicantSecurityBean security_question = null;
	  
	if(profile != null)
	{
	 	//create function to get values
	security_question = ApplicantSecurityManager.getApplicantSecurityBean(profile.getSIN());
	}
  
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>
<style>
.tableTitle {font-weight:bold;width:20%;}
.tableResult {font-weight:normal;width:80%;}

.tableTitleL {font-weight:bold;width:30%;}
.tableResultL {font-weight:normal;width:20%;}
.tableTitleR {font-weight:bold;width:30%;}
.tableResultR {font-weight:normal;width:20%;}
input {    
    border:1px solid silver;
}

</style>

<script>
 $('document').ready(function(){
	  $("#regPrefs").DataTable(
		{
			
			"order": [[ 0, "desc" ]],
			"lengthMenu": [[10, 25, 50, 100, 200, -1], [10, 25, 50, 100, 200, "All"]]	
		});
	  $("#subPrefs").DataTable(
				{
					"order": [[ 0, "asc" ]],
					"lengthMenu": [[10, 25, 50, 100, 200, -1], [10, 25, 50, 100, 200, "All"]]	
				});
 });
    </script>



</head>
<body>

	<% if(StringUtils.isNotBlank((String)request.getAttribute("msg"))) { %>
		<br/><p class='alert alert-danger'><%= request.getAttribute("msg") %></p>
	<% } %>

	<div style="font-size: 30px; padding-top: 10px; color: rgb(0, 128, 0, 0.3); font-weight: bold; text-align: left;"><%=profile.getFullNameReverse()%></div>
	<p>
	<span style="color:Grey;font-weight:bold;">TEACHING/TLA/EDUCATIONAL ADMIN PROFILE</span><br/>
	
	<div id="COENotice" class="alert alert-warning" style="display:block;text-align:center;">*** <b style="font-size:16px;">NOTICE: Missing Code of Ethics and Conduct Declaration Certificate</b> ***<br/><br/>
	Your profile is currently missing the <b>Code of Ethics and Conduct Training Declaration Certificate</b>. 
	
	This training is <b>mandatory for all staff</b> and new hires as communicated in a memo on February 8, 2022. 

Please upload this document as soon as possible to the <b>Documents Section</b> of your Profile (Section 10 for a Teaching Profile/Section 7 for a Support Profile).  
Please ensure that the document is uploaded by selecting <i>Code of Ethics and Conduct Declaration</i> as the Document Type.  

Profiles are considered <b>incomplete</b> for existing staff if this certificate is not uploaded properly and may affect job opportunities.  New hires will need to complete and upload prior to commencement of position.  

Link to the Training and Reference Materials can be <a href="/MemberServices/" target="_blank">found here</a> under Staff Training Modules icon.

	</div>

<%if(profile != null){%>

<%if(security_question == null){ %>

<div class="alert alert-danger" style="text-align:center;"><span style="font-size:16px;font-weight:bold;">*** IMPORTANT NOTICE ***</span><br/>You have NOT completed your secure question/answer for password recovery. Please enter a secure question and answer that you will remember and ONLY you would know in case you need to reset your password. 
If you fail to complete this, you will not be able to reset your password and will need to contact support if your login fails. Click link below to set this up now.<br/><br/>

																									  
<div align="center"><a href="/MemberServices/Personnel/applicant_security.jsp" class="btn ntn-sm btn-danger">PASSWORD RECOVERY SETUP</a></div>
</div>

<%}%>
<%}%>
	
	<p>Your current <b>Teaching/TLA/Educational Admin profile</b> information can be found	below. If any changes are required, please select the proper menu item above and/or edit link found in each section below. There are no
	registration steps, and instead you can just edit any section of your profile in any order. Please complete your profile as much as possible and ALWAYS keep it updated.
	
	<p><span style="color:Red;font-weight:bold;">Never create a second Teaching/Educational  profile</span> if you forget your login to your previous profile. Having more than one profile may result in missed
	communications regarding any employment positions and/or applications. You can, however, register for a Support Staff/Management profile account if need be to apply for jobs in those areas. You will need a different email address to register for another account type.
	
	<p>Sections with no information will display a red header. Those completed and/or with entries will display green.
	
	
	<!-- CURRENT OFFERS(S) ----------------------------------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section14">
			<div class="panel-heading">
				<b>CURRENT OFFERS(S)</b>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<%
						if (current_offers != null && current_offers.length > 0) {
					%>
					<ul>
						<%
							for (int i = 0; i < current_offers.length; i++) {
							JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(current_offers[i].getJob());
						%>
						<li><a class="menu" href="/MemberServices/Personnel/applicantPositionOfferController.html?id=<%=current_offers[i].getRecommendation().getRecommendationId()%>"><%=ass[0].getLocationText() + " (" + current_offers[i].getJob().getPositionTitle() + ")"%></a></li>
						<%}%>
					</ul>
					<%
						} else {
					%>
					No current offer(s) currently posted.
					<script>
						$("#section14").removeClass("panel-success").addClass(
								"panel-danger");
					</script>
					<%
						}
					%>

				</div>
			</div>
		</div>
	</div>

	<!-- LETTERS OF EMPLOYMENT ----------------------------------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section15">
			<div class="panel-heading">
				<b>LETTER(S) OF EMPLOYMENT</b>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<%
						if (emp_letters != null && emp_letters.size() > 0) {
					%>
					<ul>
						<%
							for (ApplicantPositionOfferBean letter : emp_letters) {
							JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager
							.getJobOpportunityAssignmentBeans(letter.getJob());
						%>
						<li><a class="menu"
							href="/MemberServices/Personnel/viewLetterOfEmployment.html?id=<%=letter.getRecommendation().getRecommendationId()%>"><%=ass[0].getLocationText() + " (" + letter.getJob().getPositionTitle() + ")"%> - <%= letter.getRecommendation().getOfferAcceptedDateFormatted() %></a></li>
						<%
							}
						%>
					</ul>
					<%
						} else {
					%>
					No letter(s) of employment currently listed.
					<script>
						$("#section15").removeClass("panel-success").addClass(
								"panel-danger");
					</script>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>
	
	<!-- DEMOGRAPHICS --------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 10px;">
		<div class="panel panel-success">
			<div class="panel-heading">
				<b>1. PROFILE INFORMATION</b> <span class="no-print"
					style="float: right; padding-right: 5px"><a
					class="btn btn-xs btn-primary"
					href="applicant_registration_step_1.jsp">EDIT</a></span>
			</div>
			<div class="panel-body">
				<div class="table-responsive">

					<table class="table table-striped table-condensed"
						style="font-size: 12px;">

						<tbody>
							<tr>
								<td class="tableTitle">NAME:</td>
								<td class="tableResult"><%=profile.getFirstname() + " " + ((profile.getMiddlename() != null) ? profile.getMiddlename() + " " : "")
		+ profile.getSurname()
		+ (((profile.getMaidenname() != null) && (profile.getMaidenname() != "")) ? (" (" + profile.getMaidenname() + ")")
				: "")%></td>
							</tr>
							<c:if test="${APPLICANT.modifiedDate ne null}">
								<tr>
									<td class="tableTitle">LAST MODIFIED:</td>
									<td class="tableResult"><fmt:formatDate
											pattern='MMMM dd, yyyy' value='${APPLICANT.modifiedDate}' /></td>
								</tr>
							</c:if>
							<!-- 
								<tr>
							    <td class="tableTitle">Verification Status:</td>
							    <td colspan=3>							    
							    <c:choose>
					    			<c:when test="${ APPLICANT.profileVerified }">					    				
					    				<c:if test="${APPLICANT.verificationBean ne null}">
					    					<span style="color:Green;"><span class="glyphicon glyphicon-ok"></span> Profile verified by ${APPLICANT.verificationBean.verifiedByName} on ${APPLICANT.verificationBean.getDateVerifiedFormatted()}</span>
					    				</c:if>
										</c:when>
					    			<c:otherwise>
					    			<span style="color:Red;"><span class="glyphicon glyphicon-remove"></span> 
					    			Your Profile has not been verified by HR. Please check again later.  
					    			There is nothing you need to do. 
					    			You can still apply for positions and edit your profile.
					    			</span>		
					    			</c:otherwise>
					    		</c:choose>
					    		</td>
							    </tr>
								 -->
							<tr>
								<td class="tableTitle">ADDRESS:</td>
								<td class="tableResult"><%=profile.getAddress1() + " &middot; " + profile.getAddress2() + ", " + profile.getProvince() + " &middot; "
		+ profile.getCountry() + " &middot; " + profile.getPostalcode()%></td>
							</tr>

							<tr>
								<td class="tableTitle">TELEPHONE:</td>
								<td class="tableResult"><%=profile.getHomephone()%></td>
							</tr>

							<%
								if (!StringUtils.isEmpty(profile.getWorkphone())) {
							%>
							<tr>
								<td class="tableTitle">WORK PHONE:</td>
								<td class="tableResult"><%=profile.getWorkphone()%></td>
							</tr>
							<%
								}
							%>

							<%
								if (!StringUtils.isEmpty(profile.getCellphone())) {
							%>
							<tr>
								<td class="tableTitle">CELL PHONE:</td>
								<td class="tableResult"><%=profile.getCellphone()%></td>
							</tr>
							<%
								}
							%>

							<tr>
								<td class="tableTitle">EMAIL</td>
								<!-- Check to see if email is old esdnl, wsdnl, ncsd, or lsb. -->
								<c:set var="emailCheck" value="<%=profile.getEmail()%>" />
								<c:choose>
									<c:when
										test="${fn:endsWith(emailCheck,'@esdnl.ca') or fn:endsWith(emailCheck,'@wnlsd.ca') or fn:endsWith(emailCheck,'@lsb.ca') or fn:endsWith(emailCheck,'@ncsd.ca')}">
										<td class="tableResult"
											title="Email needs updating. Invalid address."
											style="border: 1px solid red; background-color: #fde8ec;"><span><%=profile.getEmail()%>
												(Email is outdated, please update to valid email.)</span></td>
									</c:when>
									<c:otherwise>
										<td class="tableResult"><a
											href="mailto:<%=profile.getEmail()%>?subject=Applicant Profile"><%=profile.getEmail()%></a></td>
									</c:otherwise>
								</c:choose>

							</tr>

							<%
								if ((profile != null) && (profile.getDOB() != null)) {
							%>
							<tr>
								<td class="tableTitle">DATE OF BIRTH:</td>
								<td class="tableResult"><%=profile.getDOBFormatted()%></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>




	<!-- 2A. NLESD EXPERIENCE ------------------------------------------------------------------------------------>
   
   
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section2a">   
	               	<div class="panel-heading"><b>2a. NLESD  EXPERIENCE</b> <span class="no-print" style="float:right;padding-right:5px"><a class="btn btn-xs btn-primary" href="applicant_registration_step_2.jsp">EDIT</a></span></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">  
   								
   						  	
                               <%if((esd_exp != null)&&(esd_exp.getPermanentContractSchool() != 0)&&(esd_exp.getPermanentContractSchool() != -1)){%>
                               <table class="table table-striped table-condensed" style="font-size:11px;">
   						  		<tbody>	
                                <tr>
							    <td class="tableTitle">PERMANENT CONTRACT SCHOOL:</td>
							    <td class="tableResult"><%=esd_exp.getPermanentContractLocationText()%></td>
								</tr>
                                <tr>
							    <td class="tableTitle">PERMANENT CONTRACT POSITION:</td>
							    <td class="tableResult"><%=esd_exp.getPermanentContractPosition()%></td>
								</tr>  
								 </tbody>
                                </table>     
                               <%} else if((esd_exp != null)&&(esd_exp.getContractSchool() != 0)&&(esd_exp.getContractSchool() != -1)){%>
                               <table class="table table-striped table-condensed" style="font-size:11px;">
   						  		<tbody>	
                                <tr>
							    	<td class="tableTitle">REPLACEMENT CONTRACT SCHOOL:</td>
							    	<td class="tableResult"><%=esd_exp.getReplacementContractLocationText()%></td>
								</tr>
                                <tr>
							    	<td class="tableTitle">REPLACEMENT CONTRACT END DATE:</td>
							    	<td class="tableResult"><%=esd_exp.getFormattedContractEndDate()%></td>
								</tr>
								 </tbody>
                                </table>   
                               <%} else {%>                                                     	
                                                                                             
                                   <span style="color:Grey;">No Newfoundland &amp; Labrador English School District Experience currently on file.</span>
                                   <script>$("#section2a").removeClass("panel-success").addClass("panel-danger");</script>
                                <%}%>
                                <%if(empbean != null){%>
                               <table class="table table-striped table-condensed" style="font-size:11px;">
   						  		<tbody>	
                                <tr>
							    <td class="tableTitle">SDS Employee ID:</td>
							    <td class="tableResult"><%=empbean.getEmpId() %></td>
								</tr>
                                <tr>
							    <td class="tableTitle">Service Time:</td>
							    <td>
							    	<% 
							    		//NumberFormat nf = new DecimalFormat("0.00");
							    		//EmployeeSeniorityBean esb = empbean.getSeniority(EmployeeSeniorityBean.Union.NLTA);
							    		//out.println("PROVINCIAL: " + nf.format(esb.getSeniorityValue1()) + " yrs<br />");
							    		//out.println("OUT OF PROVINCE: " + nf.format(esb.getSeniorityValue2()) + " yrs");
							    		out.println(empbean.viewAllSeniorityTeach());
							    		
							    	%>
							    	</td>
								</tr>  
								 </tbody>
                                </table> 
                                <%}%>
  
  </div></div></div></div> 
   
   
   
   
  <!-- 2B. PERMANENT EXPERIENCE ------------------------------------------------------------------------------------>
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section2b">
			<div class="panel-heading">
				<b>2b. NLESD PERMANENT EXPERIENCE</b> (Total Months:
				<%=((esd_exp != null) ? Integer.toString(esd_exp.getPermanentLTime()) : "UNKNOWN")%>)
				<span class="no-print" style="float: right; padding-right: 5px"><a
					class="btn btn-xs btn-primary"
					href="applicant_registration_step_2_perm_exp.jsp">EDIT</a></span>
			</div>
			<div class="panel-body">
				<div class="table-responsive">


					<%
						if ((per != null) && (per.length > 0)) {
					%>

					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<thead>
							<tr>
								<th width="10%">FROM</th>
								<th width="10%">TO</th>
								<th width="30%">SCHOOL</th>
								<th width="50%">GRADES/SUBJECTS TAUGHT</th>
							</tr>
						</thead>
						<tbody>
							<%
								for (int i = 0; i < per.length; i++) {
							%>
							<tr>
								<td><%=sdf.format(per[i].getFrom())%></td>
								<td><%=sdf.format(per[i].getTo())%></td>
								<td><%=SchoolDB.getLocationText(per[i].getSchoolId())%></td>
								<td><%=per[i].getGradesSubjects()%></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
					<%
						} else {
					%>
					<span style="color: Grey;">No Newfoundland &amp; Labrador
						English School District Permanent Experience currently on file.</span>
					<script>
						$("#section2b").removeClass("panel-success").addClass(
								"panel-danger");
					</script>
					<%
						}
					%>

				</div>
			</div>
		</div>
	</div>

	<!-- 2C. REPLACEMENT CONTRACT EXPERIENCE ------------------------------------------------------------------------------------>
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section2c">
			<div class="panel-heading">
				<b>2c. NLESD REPLACEMENT CONTRACT EXPERIENCE</b> (Total Months:
				<%=((esd_exp != null) ? Integer.toString(esd_exp.getReplacementTime()) : "UNKNOWN")%>)
				<span class="no-print" style="float: right; padding-right: 5px"><a
					class="btn btn-xs btn-primary"
					href="applicant_registration_step_2_repl_exp.jsp">EDIT</a></span>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<%
						if ((rpl != null) && (rpl.length > 0)) {
					%>
					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<thead>
							<tr>
								<th width="10%">FROM</th>
								<th width="10%">TO</th>
								<th width="30%">SCHOOL</th>
								<th width="50%">GRADES/SUBJECTS TAUGHT</th>
							</tr>
						</thead>
						<tbody>
							<%
								for (int i = 0; i < rpl.length; i++) {
							%>
							<tr>
								<td><%=sdf.format(rpl[i].getFrom())%></td>
								<td><%=sdf.format(rpl[i].getTo())%></td>
								<td><%=SchoolDB.getLocationText(rpl[i].getSchoolId())%></td>
								<td><%=rpl[i].getGradesSubjects()%></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
					<%
						} else {
					%>
					<span style="color: Grey;">No Newfoundland &amp; Labrador
						English School District Replacement Contract Experience currently
						on file.</span>
					<script>
						$("#section2c").removeClass("panel-success").addClass(
								"panel-danger");
					</script>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>

	<!-- 3. EXPERIENCE WITH OTHER BOARDS ----------------------------------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section3">
			<div class="panel-heading">
				<b>3. EXPERIENCE WITH OTHER BOARDS</b> <span class="no-print"
					style="float: right; padding-right: 5px"><a
					class="btn btn-xs btn-primary"
					href="applicant_registration_step_3.jsp">EDIT</a></span>
			</div>
			<div class="panel-body">
				<div class="table-responsive">

					<%
						if ((exp_other != null) && (exp_other.length > 0)) {
					%>
					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<thead>
							<tr>
								<th width="10%">FROM</th>
								<th width="10%">TO</th>
								<th width="30%">SCHOOL &amp; BOARD</th>
								<th width="50%">GRADES/SUBJECTS TAUGHT</th>
							</tr>
						</thead>
						<tbody>
							<%
								for (int i = 0; i < exp_other.length; i++) {
							%>
							<tr>
								<td><%=sdf.format(exp_other[i].getFrom())%></td>
								<td><%=sdf.format(exp_other[i].getTo())%></td>
								<td><%=exp_other[i].getSchoolAndBoard()%></td>
								<td><%=exp_other[i].getGradesSubjects()%></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
					<%
						} else {
					%>
					<span style="color: Grey;">No Experience with other Boards
						currently on file.</span>
					<script>
						$("#section3").removeClass("panel-success").addClass(
								"panel-danger");
					</script>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>

	<!-- 4. SUBSTITUTE TEACHING EXPERIENCE ----------------------------------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section4">
			<div class="panel-heading">
				<b>4. SUBSTITUTE TEACHING EXPERIENCE</b> (Total Sub Days:
				<%=((esd_exp != null) ? Integer.toString(esd_exp.getSubstituteTime()) : "UNKNOWN")%>)
				<span class="no-print" style="float: right; padding-right: 5px"><a
					class="btn btn-xs btn-primary"
					href="applicant_registration_step_4.jsp">EDIT</a></span>
			</div>
			<div class="panel-body">
				<div class="table-responsive">

					<%
						if ((sub != null) && (sub.length > 0)) {
					%>

					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<thead>
							<tr>
								<th width="25%">FROM</th>
								<th width="25%">TO</th>
								<th width="50%"># DAYS PER YEAR</th>
							</tr>
						</thead>
						<tbody>
							<%
								for (int i = 0; i < sub.length; i++) {
							%>
							<tr>
								<td><%=sdf.format(sub[i].getFrom())%></td>
								<td><%=sdf.format(sub[i].getTo())%></td>
								<td><%=sub[i].getNumDays()%></td>
							</tr>

							<%
								}
							%>
						</tbody>
					</table>
					<%
						} else {
					%>
					<span style="color: Grey;">No Newfoundland &amp; Labrador
						English School District Substitute Teaching Experience currently
						on file.</span>
					<script>
						$("#section4").removeClass("panel-success").addClass(
								"panel-danger");
					</script>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>

	<!-- 5. UNIVERSITY/COLLEGE EDUCATION ------------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section5">
			<div class="panel-heading">
				<b>5. UNIVERSITY/COLLEGE EDUCATION</b> <span class="no-print"
					style="float: right; padding-right: 5px"><a
					class="btn btn-xs btn-primary"
					href="applicant_registration_step_5.jsp">EDIT</a></span>
			</div>
			<div class="panel-body">
				<div class="table-responsive">

					<%
						if ((edu != null) && (edu.length > 0)) {
					%>
					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<thead>
							<tr>
								<th width="24%">Institution</th>
								<th width="8%">From</th>
								<th width="8%">To</th>
								<th width="20%">Program &amp; Faculty</th>
								<th width="25%">Major(s)(#crs)/Minor(#crs)</th>
								<th width="15%">Degree Conferred</th>
							</tr>
						</thead>
						<tbody>
							<%
								for (int i = 0; i < edu.length; i++) {
									if(edu[i].getProgramFacultyName().equals("Deemed Equivalent by HR")){
										continue;
									}
							%>
							<tr>
								<td><%=edu[i].getInstitutionName()%></td>
								<td><%=sdf.format(edu[i].getFrom())%></td>
								<td><%=sdf.format(edu[i].getTo())%></td>
								<td><%=edu[i].getProgramFacultyName()%></td>
								<td><span style="color: Navy;">Major(s):</span> <%
 	if (edu[i].getMajor_other() > 0) {
 %>
									<%=SubjectDB.getSubject(edu[i].getMajor()).getSubjectName()
		+ (edu[i].getMajor_other() > 0 ? ", " + SubjectDB.getSubject(edu[i].getMajor_other()).getSubjectName() : "")
		+ " (" + edu[i].getNumberMajorCourses() + ")<br/>"%>
									<%
										} else if (edu[i].getMajor() != -1) {
									%> <%=SubjectDB.getSubject(edu[i].getMajor()).getSubjectName() + " (" + edu[i].getNumberMajorCourses() + ")<br/>"%>
									<%
										} else {
									%> N/A<br /> <%
 	}
 %> <span style="color: Green;">Minor(s):</span>
									<%
										if (edu[i].getMinor() != -1) {
									%>
									<%=SubjectDB.getSubject(edu[i].getMinor()).getSubjectName() + " (" + edu[i].getNumberMinorCourses() + ")"%>
									<%
										} else {
									%> N/A <%
										}
									%></td>
								<td><%=((!StringUtils.isEmpty(edu[i].getDegreeConferred()))
		? DegreeManager.getDegreeBeans(edu[i].getDegreeConferred()).getAbbreviation()
		: "&nbsp;")%></td>
							</tr>
							<%
								}
							%>

						</tbody>
					</table>

					<%
						} else {
					%>
					<span style="color: Grey;">No University/College education
						currently on file.</span>
					<script>
						$("#section5").removeClass("panel-success").addClass(
								"panel-danger");
					</script>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>


	<!-- 6. EDUCATION OTHER ------------------------------------------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section6">
			<div class="panel-heading">
				<b>6. EDUCATION (OTHER)</b> <span class="no-print"
					style="float: right; padding-right: 5px"><a
					class="btn btn-xs btn-primary"
					href="applicant_registration_step_6.jsp">EDIT</a></span>
			</div>
			<div class="panel-body">
				<div class="table-responsive">

					<%
						if (edu_oth != null) {
					%>

					<table class="table table-striped table-condensed"
						style="font-size: 12px;">

						<tbody>
							<tr>
								<td class="tableTitleL">TRAINING METHOD:</td>
								<td class="tableResultL"><%=edu_oth.getProfessionalTrainingLevel().getDescription()%></td>
								<td class="tableTitleR"># SPECIAL ED. COURSES:</td>
								<td class="tableResultR"><%=edu_oth.getNumberSpecialEducationCourses()%></td>
							</tr>

							<tr>
								<td class="tableTitleL"># FRENCH LANGUAGE COURSES:</td>
								<td class="tableResultL"><%=edu_oth.getNumberFrenchCourses()%></td>
								<td class="tableTitleR"># MATH COURSES:</td>
								<td class="tableResultR"><%=edu_oth.getNumberMathCourses()%></td>
							</tr>
							<tr>
								<td class="tableTitleL"># ENGLISH COURSES:</td>
								<td class="tableResultL"><%=edu_oth.getNumberEnglishCourses()%></td>
								<td class="tableTitleR"># MUSIC COURSES:</td>
								<td class="tableResultR"><%=edu_oth.getNumberMusicCourses()%></td>
							</tr>
							<tr>
								<td class="tableTitleL"># TECHNOLOGY COURSES:</td>
								<td class="tableResultL"><%=edu_oth.getNumberTechnologyCourses()%></td>
								<td class="tableTitleR"># SCIENCE COURSES:</td>
								<td class="tableResultR"><%=edu_oth.getNumberScienceCourses()%></td>
							</tr>
							<tr>
								<td class="tableTitleL"># SOCIAL STUDIES COURSES:</td>
								<td class="tableResultL"><%=edu_oth.getNumberSocialStudiesCourses()%></td>
								<td class="tableTitleR"># ART COURSES:</td>
								<td class="tableResultR"><%=edu_oth.getNumberArtCourses()%></td>
							</tr>
							<tr>
								<td class="tableTitleL">(TLA) TOTAL # COURSES COMPLETE:</td>
								<td class="tableResultL"><%=edu_oth.getTotalCoursesCompleted()%></td>
								<td class="tableTitleR"></td>
								<td class="tableResultR"></td>
							</tr>

							<tr>
								<td class="tableTitleL">CERTIFICATION LEVEL:</td>
								<td class="tableResultL"><%=!StringUtils.isEmpty(edu_oth.getTeachingCertificateLevel()) ? edu_oth.getTeachingCertificateLevel() : "N/A"%></td>
								<td class="tableTitleR">CERTIFICATION ISSUE DATE:</td>
								<td class="tableResultR"><%=((edu_oth.getTeachingCertificateIssuedDate() != null)
		? sdf.format(edu_oth.getTeachingCertificateIssuedDate())
		: "N/A")%></td>
							</tr>


						</tbody>
					</table>

					<%
						} else {
					%>
					<span style="color: Grey;">No other education currently on
						file.</span>
					<script>
						$("#section6").removeClass("panel-success").addClass(
								"panel-danger");
					</script>
					<%
						}
					%>

				</div>
			</div>
		</div>
	</div>


	<!--7. OTHER INFORMATION ----------------------------------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section7">
			<div class="panel-heading">
				<b>7. OTHER INFORMATION</b> <span class="no-print"
					style="float: right; padding-right: 5px"><a
					class="btn btn-xs btn-primary"
					href="applicant_registration_step_7.jsp">EDIT</a></span>
			</div>
			<div class="panel-body">
				<div class="table-responsive" style="font-size: 11px;">
					<%
						if ((other_info != null) && !StringUtils.isEmpty(other_info.getOtherInformation())) {
					%>

					<%=other_info.getOtherInformation().replaceAll(new String(new char[]{((char) 10)}), "<br>")%>

					<%
						} else {
					%>
					<span style="color: Grey;">No Other Information currently on
						file.</span>
					<script>
						$("#section7").removeClass("panel-success").addClass(
								"panel-danger");
					</script>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>

	<!-- 8. REFERENCES ----------------------------------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section8">
			<div class="panel-heading">
				<b>8. REFERENCES</b> <span class="no-print"
					style="float: right; padding-right: 5px"><a
					class="btn btn-xs btn-primary"
					href="applicant_registration_step_8.jsp">EDIT/VIEW</a></span>
			</div>
			<div class="panel-body">
				<div class="table-responsive">

<div class="alert alert-warning" style="margin-top:5px;">
<b>NOTE:</b> 
<ul>
<li>Only a Principal, Vice Principal, Director of Schools, Program Specialist, Itinerant and HR can complete an Admin, Guidance or Teaching reference.</li>
<li>Please do not submit a reference to a fellow teacher as teachers cannot complete references for other teachers.</li>
<li>If an intern requires a reference from a teacher, they can select the TLA/External option in the reference type dropdown.</li>
<li>If the email address of the  referencer is not a <b>@nlesd.ca</b> email address then a External/TLA link will be sent.  You will only be prompted <br />
to select a reference type for <b>@nlesd.ca</b> email addresses.
  </li>
</ul>
</div>


					<%
						if ((refs != null) && (refs.length > 0)) {
					%>

					<table class="table table-striped table-condensed"	style="font-size: 11px;">
						<thead>
							<tr>
								<th width="25%">NAME (TITLE)</th>								
								<th width="30%">ADDRESS</th>
								<th width="15%">TELEPHONE</th>
								<th width="20%">EMAIL</th>
								<th width="10%">STATUS</th>								
							</tr>
						</thead>
						<tbody>
							<%
								for (int i = 0; i < refs.length; i++) {
							%>
							<tr>
								<td width="25%"><%=refs[i].getName()%> (<%=refs[i].getTitle()%>)</td>                             
                                <td width="30%"><%=refs[i].getAddress()%></td>
                                <td width="15%"><%=refs[i].getTelephone()%></td>  		
                                  <%out.println("<td width='20%'>" + ((refs[i].getApplicantRefRequestBean()!= null)?refs[i].getApplicantRefRequestBean().getEmailAddress():"N/A" ) +"</td>");%>					
								<!-- Get current date and time to see if expired. -->
								<%
								//I hate Long variables
								Date date = new Date();		//Get today			
			int refResendTimeLeft=0;	
			int refDiff,refDiffResend,refDateRequested,refCurrentDate,refTimeLeft;; //declare ints 			
			int refExpiredTimeY = 8760; //# hours in a year exact. References expire after a year.
		
			
			if  (refs[i].getApplicantRefRequestBean()!=null && refs[i].getApplicantRefRequestBean().getDateStatus()!=null) {								
			 
				refDateRequested = (int) TimeUnit.MILLISECONDS.toHours(refs[i].getApplicantRefRequestBean().getDateStatus().getTime());
			 	refCurrentDate = (int) TimeUnit.MILLISECONDS.toHours(date.getTime());			 
			 	refDiff = (refCurrentDate - refDateRequested)-12;	//do the math hours
			 	
			} else {
				refDateRequested = 0; //if null 0 all variables
			 	refCurrentDate = 0;									
			 	refDiff = 0;				 
			}				
			
			if(refDiff >refExpiredTimeY) {  %>
													<td width='10%' class='danger' style='text-align:center;'><i class='fas fa-times'></i> EXPIRED</td>								
								<%	} else { 									
										if (refs[i].getApplicantRefRequestBean() == null) {	%> 
													<td width='10%' class='danger' style='text-align:center;'><i class='fas fa-times'></i> NOT SENT</td>
								<%	} else {
									 	if (refs[i].getApplicantRefRequestBean().getRequestStatus() == null) {%>
													<td width='10%' class='danger' style='text-align:center;'><i class='fas fa-times'></i> NOT SENT</td>
								 <%} else {									 
									 	if (refs[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REQUEST SENT")) { %>
													<td width='10%' class='info' style='text-align:center;'><i class='far fa-clock'></i> SENT/PENDING</td>
								<%	} else if (refs[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REFERENCE COMPLETED")) {%>
													<td width='10%' class='success' style='text-align:center;'><i class='fas fa-check'></i> COMPLETED</td>
								<%   } else if (refs[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REQUEST DECLINED")) { %>
													<td width='10%' class=danger' style='text-align:center;'><i class='fas fa-times'></i> DECLINED</td>
								 <%	} else { %> 			
								 					<td width='10%' class='danger' style='text-align:center;'><i class='fas fa-times'></i> NOT SENT</td>> 
								 <% } %> 
								 <% } %> 
								 <% }} %>
															
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
					<%
						} else {
					%>
					<span style="color: Grey;">No References currently on file.</span>
					<script>
						$("#section8").removeClass("panel-success").addClass(
								"panel-danger");
					</script>
					<%
						}
					%>

				</div>
			</div>
		</div>
	</div>

	<!--9.  REGIONAL PREFERENCES ----------------------------------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section9">
			<div class="panel-heading">
				<b>9. REGIONAL PREFERENCES</b> <span class="no-print"
					style="float: right; padding-right: 5px"><a
					class="btn btn-xs btn-primary"
					href="applicant_registration_step_9.jsp">EDIT</a></span>
			</div>
			<div class="panel-body">
				<div class="table-responsive">

					<%
						if ((regionPrefs != null) && (regionPrefs.length > 0)) {
						int cntr = 0;
					%>
					<table class="table table-striped table-condensed" id="regPrefs"
						style="font-size: 11px;">
						<thead>
							<tr>
								<th width="45%">REGION</th>
								<th width="45%">REGIONAL ZONE</th>
							</tr>
						</thead>
						<tbody>

							<%
								for (int i = 0; i < regionPrefs.length; i++) {
							%>

							<tr>
								<td><c:set var="whatRegion"
										value="<%=regionPrefs[i].getZone()%>" /> <c:set var="whatZone"
										value="<%=regionPrefs[i].getName()%>" /> <c:choose>
										<c:when
											test="${whatRegion eq 'avalon' or whatRegion eq 'eastern' }">Avalon Region</c:when>
										<c:when test="${whatRegion eq 'central'}">Central Region</c:when>
										<c:when test="${whatRegion eq 'western'}">Western Region</c:when>
										<c:when test="${whatRegion eq 'labrador'}">Labrador Region</c:when>
										<c:when test="${whatRegion eq 'provincial'}">Provincial</c:when>
										<c:otherwise>
											<span style="color: Red;">ERROR: Unknown Region</span>
										</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when
											test="${whatZone eq 'nlesd - provincal' or whatZone eq 'nlesd - provincial'}">All Province</c:when>
										<c:when test="${whatZone eq 'all labrador region'}">All Labrador</c:when>
										<c:when test="${whatZone eq 'all western region'}">All Western Zone</c:when>
										<c:when test="${whatZone eq 'all central region'}">All Central Zone</c:when>
										<c:when
											test="${whatZone eq 'all eastern region' or whatZone eq 'all avalon region'}">All Avalon Zone</c:when>
										<c:otherwise>
											<span style="text-transform: Capitalize;"><%=regionPrefs[i].getName()%>
												Regional Zone</span>
										</c:otherwise>
									</c:choose></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>

					<%
						} else {
					%>
					<span style="color: Grey;">No regional preferences have been
						selected.</span>
					<script>
						$("#section9").removeClass("panel-success").addClass(
								"panel-danger");
					</script>
					<%
						}
					%>


				</div>
			</div>
		</div>
	</div>

	<!--10.  DOCUMENTS ----------------------------------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section10">
			<div class="panel-heading">
				<b>10. DOCUMENTS</b>  (Upload documents here. They will display in proper sections once uploaded).<span class="no-print" style="float: right; padding-right: 5px">
				<a class="btn btn-xs btn-primary" href="applicant_registration_step_10.jsp">EDIT/UPLOAD</a></span>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<%
						if ((docs != null) && (docs.size() > 0)) {
						int i = 0;
					%>
					<table class="table table-striped table-condensed" style="font-size: 11px;">
						<thead>
							<tr>
								<th width="25%">TYPE</th>
								<th width="30%">UPLOAD DATE</th>
								<th width="10%">OPTIONS</th>
							</tr>
						</thead>
						<tbody>
						<c:set var="emailCheck" value="<%=profile.getEmail()%>" />
							<%	for (ApplicantDocumentBean doc : docs) {
									//if(!doc.getType().equals(DocumentType.LETTER) && !doc.getType().equals(DocumentType.COVID19_VAX)){ %>
							<tr>
								<td><%=doc.getType().getDescription()%>								
								<!-- if user has nlesd email, check if code of conduct, and if so, hide warning. If user has NON nlesd email, hide it anyways.-->
								<c:choose>
								<c:when test="${fn:endsWith(emailCheck,'@nlesd.ca')}">										
								<%					
								if(doc.getType().equals(DocumentType.CODE_OF_ETHICS_CONDUCT)) {%>
								
								<script>
								$("#COENotice").css("display","none");
								</script>
								
								<% }%>		
										
							   </c:when>							  
								<c:otherwise>
								<script>
								$("#COENotice").css("display","none");
								</script>
								
								</c:otherwise>
								</c:choose>
																						
								
								</td>
								<td><%=sdf_long.format(doc.getCreatedDate())%></td>
								<td><a class='btn btn-xs btn-info' href='viewDocument.html?id=<%=doc.getDocumentId()%>' target='_blank'>VIEW</a></td>
							</tr>
							<%//} %>
							<%
								}
							%>
						</tbody>
					</table>
					<%
						} else {
					%>
					<span style="color: Grey;">No Documents currently on file.</span>
					<script>
						$("#section10").removeClass("panel-success").addClass(
								"panel-danger");
					</script>
					<%
						}
					%>

				</div>
			</div>
		</div>
	</div>

 <!--10.  Letters ----------------------------------------------------------------------------------------->               
                
                
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success" id="section10b">   
	               	<div class="panel-heading"><b>DISTRICT LETTERS</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
 										<% if((docs != null) && (docs.size() > 0)) {
	                                  	int i=0; %>
	                                   <table class="table table-striped table-condensed" style="font-size:11px;">
      							    <thead>
      							    <tr>
                                       <th width="25%">TITLE</th>
                                       <th width="30%">UPLOAD DATE</th>
                                       <th width="10%">OPTIONS</th>                                    
                                      </tr>
                                      </thead>
                                      <tbody>
	                                   <% for(ApplicantDocumentBean doc : docs){ 
	                                   		if(doc.getType().equals(DocumentType.LETTER)){ %>
	                                   		<tr>
	                                      		<td><%=doc.getDescription()%></td>
	                                      		<td><%=sdf_long.format(doc.getCreatedDate())%></td>
	                                      			<td><a class='btn btn-xs btn-info' href='viewDocument.html?id=<%=doc.getDocumentId()%>' target='_blank'>VIEW</a></td>
	                                      				                                      
	                                      	</tr>
	                                      <% } %>
	                                    <%} %>  
	                                      </tbody>
	                                      </table>	                                      
	                                      <% } else {%>                                  
	                                       <span style="color:Grey;">No Documents currently on file.</span>
	                                       <script>$("#section10b").removeClass("panel-success").addClass("panel-danger");</script>
	                                    <% } %>
                              
</div></div></div></div>

 <!--10.  COVID19 ----------------------------------------------------------------------------------------->               
                
    <!-- HIDDEN NOW. Keep in case we need it again -->            
  <div class="panel-group" style="padding-top:5px;display:none;">                               
	               	<div class="panel panel-info" id="section10b">   
	               	<div class="panel-heading"><b>COVID-19 Proof of Vaccination</b> 
	               	<span class="no-print" style="float: right; padding-right: 5px">
				<a class="btn btn-xs btn-primary" href="applicant_registration_step_10.jsp">EDIT/UPLOAD</a></span>
	               </div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
 										<% if((docs != null) && (docs.size() > 0)) {
	                                  	int i=0; %>
	                                   <table class="table table-striped table-condensed" style="font-size:11px;">
      							    <thead>
      							    <tr>
                                       <th width="25%">TITLE</th>
                                       <th width="30%">UPLOAD DATE</th>
                                       <th width="35%">STATUS</th>
                                       <th width="10%">OPTIONS</th>                                     
                                      </tr>
                                      </thead>
                                      <tbody>
	                                   <% for(ApplicantDocumentBean doc : docs){ 
	                                   		if(doc.getType().equals(DocumentType.COVID19_VAX)){ %>
	                                   		<tr>
	                                      		<td width="25%"><%=doc.getType().toString()%></td>
	                                      		<td width="30%"><%=sdf_long.format(doc.getCreatedDate())%></td>
	                                      		<td width="35%">
	                                      		<%if(doc.getClBean() != null){ %>
	                                      				<%if(doc.getClBean().getDateVerified() != null) {%>
	                                      					<%if((doc.getClBean().isExcemptionDoc())){ %>
	                                      						<span style="color:Green;"><i class="fas fa-check"></i> Exemption verified on <%=doc.getClBean().getDateVerifiedFormatted() %></span>	
	                                      					<%}else{ %>
	                                      						<span style="color:Green;"><i class="fas fa-check"></i> Verified on <%=doc.getClBean().getDateVerifiedFormatted() %></span>	
	                                      					<%} %>
	                                      					                                      				
	                                      				<%}else if(doc.getClBean().getRejectedDate() != null){ %>
	                                      					<span style="color:Red;"><i class="fas fa-ban"></i> Rejected on <%=doc.getClBean().getRejectedDateFormatted() %><br/>
	                                      					 Notes: <%=doc.getClBean().getRejectedNotes() %> 
	                                      					</span>
	                                      				<%} %>	                                      			
	                                      			<%} %>
	                                      		</td>
	                                      		<%if(doc.getClBean() != null){ %>
	                                      			<%if(!(doc.getClBean().isExcemptionDoc())){ %>
	                                      			<td  width="10%"><a class='btn btn-xs btn-info' href='viewDocument.html?id=<%=doc.getDocumentId()%>' target='_blank'>VIEW</a></td>
	                                      			<%}else{ %>
	                                      			<td  width="10%"></td>
	                                      			<%} %>
	                                      		<%}else{ %>
	                                      		
	                                      		
	                                      		<%} %>
	                                      		
	                                      				                                      
	                                      	</tr>
	                                      <% } %>
	                                    <%} %>  
	                                      </tbody>
	                                      </table>	                                      
	                                      <% } else {%>                                  
	                                       <span style="color:Grey;">No Vaccination(s) currently on file.</span>
	                                       <script>$("#section10b").removeClass("panel-success").addClass("panel-danger");</script>
	                                    <% } %>
                              
</div></div></div></div>

	<!-- CRIMINAL OFFENCE DECLARATIONS ----------------------------------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section11">
			<div class="panel-heading">
				<b>CRIMINAL OFFENCE DECLARATIONS</b><span class="no-print"
					style="float: right; padding-right: 5px"><a
					class="btn btn-xs btn-primary"
					href="applicant_registration_step_10_CODF.jsp">EDIT</a></span>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<%
						if ((cods != null) && (cods.size() > 0)) {
						int i = 0;
					%>
					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<thead>
							<tr>
								<th width="90%">DECLARATION DATE</th>
								<th width="10%">OPTIONS</th>
							</tr>
						</thead>
						<tbody>
							<%
								for (ApplicantCriminalOffenceDeclarationBean cod : cods) {
							%>
							<tr>
								<td><%=sdf_long.format(cod.getDeclarationDate())%></td>
								<td><a class='btn btn-xs btn-info'
									href='viewCriminalOffenceDeclaration.html?id=<%=cod.getDeclarationId()%>'>VIEW</a></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
					<%
						} else {
					%>
					<span style="color: Grey;">No Documents currently on file.</span>
					<script>
						$("#section10").removeClass("panel-success").addClass(
								"panel-danger");
					</script>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>

	

	<!-- INTERVIEW SUMMARIES ----------------------------------------------------------------------------------------->
<%
	if (interviewSummaries.size() > 0) {
%>
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section12">
			<div class="panel-heading">
				<b>Recent Interview Summaries</b>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<thead>
							<tr>
								<th width="20%">COMP #</th>
								<th width="50%">TITLE</th>
								<th width="20%">DATE</th>
								<th width="10%">OPTIONS</th>
							</tr>
						</thead>
						<tbody>
							<% for (InterviewSummaryBean isb : interviewSummaries) { %>
								<tr>
									<td><%= isb.getCompetition().getCompetitionNumber() %></td>
									<td><%= isb.getCompetition().getPositionTitle( )%></td>
									<td><%= sdf_medium.format(isb.getCreated()) %></td>
									<td><a class="btn btn-xs btn-info"
										href="/MemberServices/Personnel/applicantViewCompetitionInterviewSummary.html?id=<%= isb.getInterviewSummaryId() %>">VIEW</a></td>
								</tr>
							<% } %>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
<% } %>

<!-- HIGHLY RECOMMENDED POOL COMPETITIONS ----------------------------------------------------------------------------------------->
<% if(highlyRecommendedPools.size() > 0) { %>
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section12">
			<div class="panel-heading">
				<b>Pool Competitions with HIGHLY RECOMMENDED status</b>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<thead>
							<tr>
								<th width="20%">COMP #</th>
								<th width="45%">TITLE</th>
								<th width="20%">LOCATION</th>
								<th class="no-print" width="15%">VIEW</th>
							</tr>
						</thead>
						<tbody>
							<% for(JobOpportunityBean j : highlyRecommendedPools.values()) { %>							
							<tr>
							<td><%= j.getCompetitionNumber() %></td>
							<td><%= j.getPositionTitle() %></td>
							<td><%= j.getJobLocation() %></td>
							<td class="no-print">
							<a class='btn btn-xs btn-info' href='/employment/view_job_post.jsp?comp_num=<%= j.getCompetitionNumber() %>'>VIEW JOB</a>							
							</td>
							</tr>
							<% } %>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
<% } %>

	<!-- CURRENT JOB COMPETITION APPLICATION(S) ----------------------------------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section12">
			<div class="panel-heading">
				<b>CURRENT JOB APPLICATION(S)</b>
			</div>
			<div class="panel-body">
				<div class="table-responsive">

					<%
						if (jobs.length > 0) {
					%>
					<table class="table table-condensed table-striped" id="jobsapp"
						style="font-size: 11px;">
						<thead>
							<tr>
								<th width="20%">COMP #</th>
								<th width="50%">TITLE</th>
								<th width="20%">CLOSING DATE</th>
								<th width="10%">OPTIONS</th>
							</tr>
						</thead>
						<tbody>
							<%
								for (int i = 0; i < jobs.length; i++) {
							%>
							<tr>
								<td><%=jobs[i].getCompetitionNumber()%></td>
								<td><%=jobs[i].getPositionTitle()%></td>
								<td><%=jobs[i].getFormatedCompetitionEndDate()%></td>
								<td><a class="btn btn-xs btn-info" href="/employment/view_job_post.jsp?comp_num=<%=jobs[i].getCompetitionNumber()%>">VIEW</a></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>

					<%
						} else {
					%>
					<span style="color: Grey;"> No open job applications on
						file.</span>
					<script>
						$("#section12").removeClass("panel-success").addClass(
								"panel-danger");
					</script>

					<%
						}
					%>

				</div>
			</div>
		</div>
	</div>


	<!-- CURRENT SUB LIST APPLICATION(S) ----------------------------------------------------------------------------------------->
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section13">
			<div class="panel-heading">
				<b>CURRENT SUBLIST APPLICATION(S)</b>
			</div>
			<div class="panel-body">
				<div class="table-responsive">

					<%
						@SuppressWarnings("unchecked")
					Map.Entry<Integer, ApplicantSubListInfoBean>[] entries = (Map.Entry<Integer, ApplicantSubListInfoBean>[]) sublists
							.entrySet().toArray(new Map.Entry[0]);
					ApplicantSubListInfoBean info = null;
					if (entries.length > 0) {
					%>
					<table class="table table-striped table-condensed"
						style="font-size: 11px;">
						<thead>
							<tr>
								<th width="55%">LIST (YEAR)/REGION</th>
								<th width="10%">DATE APPLIED</th>
								<th width="35%">STATUS</th>
							</tr>
						</thead>
						<tbody>
							<%
								for (int i = 0; i < entries.length; i++) {
								info = (ApplicantSubListInfoBean) entries[i].getValue();
							%>
							<tr>
								<td style="text-transform: Capitalize;"><%=info.getSubList().getTitle()%>
									(<%=info.getSubList().getSchoolYear()%>) - <%=info.getSubList().getRegion().getName()%>
								</td>
								<td><%=info.getAppliedDateFormatted()%></td>
								<td><%=(info.isNewApplicant()
		? "<span style='background-color:Blue;color:white;'>&nbsp;NEW&nbsp;</span>"
		: (info.isShortlisted()
				? "<span style='background-color:Green;color:white;'>&nbsp;APPROVED&nbsp;</span>"
				: (info.isNotApproved()
						? "<span style='background-color:Red;color:white;'>&nbsp;NOT APPROVED&nbsp;</span>"
						: "<span style='background-color:Yellow;color:black;'>&nbsp;IN POSITION / REMOVED&nbsp;</span>")))%>
						
						 <%if(info.isNotApproved() && info.getAuditBean() != null){ %>
			                 	<% if(info.getAuditBean().getEntryNotes() != null){ %>
			                    <br/>
			                    <span style="color:Grey;"><%=info.getAuditBean().getApplicantNotes()%></span>
  										
									<%} %>
			                   <%} %>
						</td>
							</tr>
							
							<%	}%>
						</tbody>
					</table>

					<%
						} else {
					%>
					<span style="color: Grey;"> No open job applications on
						file.</span>
					<script>
						$("#section13").removeClass("panel-success").addClass(
								"panel-danger");
					</script>

					<%
						}
					%>

				</div>
			</div>
		</div>
	</div>

<!--10.  SUBSTITUTE PREFERENCES ----------------------------------------------------------------------------------------->

	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section16">
			<div class="panel-heading">
				<b>SUBSTITUTE PREFERENCES</b> <span class="no-print"
					style="float: right; padding-right: 5px"><a
					class="btn btn-xs btn-primary"
					href="applicant_substitute_preferences.jsp">EDIT</a></span>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
				<table id="subPrefs" class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;">
					<thead>
						<tr>
							<th width=45%'>SCHOOL</th>
							<th width='20%'>CITY/TOWN</th>	
							<th width='20%'>REGION</th>
							<th width='15%'>REGIONAL ZONE</th>							
														        															       
						</tr>
					</thead>
					<tbody>
					    <%Collection<RegionBean> regions = null;
                        	for(SchoolZoneBean zone : zones) {
                             	regions = RegionManager.getRegionBeans(zone);
                             	
						%>
							<% for(RegionBean region : regions) { %>
								<%if(regions == null || regions.size() <= 1) { %>
									<%schools = SchoolDB.getSchools(zone).toArray(new School[0]);                                      		
                                	//int middle = (schools.length % 2 == 0) ? schools.length/2 : schools.length/2 + 1;
	                                for(int j=0; j < schools.length; j++){%>
	                                	<% if(sel.containsKey(schools[j].getSchoolID())){%>
	                                		<tr>
	                                		<td><%=schools[j].getSchoolName()%></td>
	                                		<td><%=schools[j].getTownCity()%></td>
	                                		<td style="text-transform:Capitalize;"><%=zone.getZoneName()%></td>
	                                		<td  style="color:Silver;">N/A</td>
	                                		</tr>
	                                	<% }%>
	                                <%} %>
									
                        		<%}else{
                        			if(region.getName().contains("all")) continue;%>
                        			<%schools = SchoolDB.getSchools(region).toArray(new School[0]);
                        			//int middle = (schools.length % 2 == 0) ? schools.length/2 : schools.length/2 + 1;
                        			%>
                        			<%for(int j=0; j < schools.length; j++){%>
	                                	<% if(sel.containsKey(schools[j].getSchoolID())){%>
	                                		<tr>
	                                		<td><%=schools[j].getSchoolName()%></td>
	                                		<td><%=schools[j].getTownCity()%></td>
	                                		<td  style="text-transform:Capitalize;"><%=zone.getZoneName()%></td>
	                                		<td style="text-transform:Capitalize;"><%=region.getName() %></td>
	                                		</tr>
	                                	<% }%>
	                                <%} %>
                        		<%}%>
                        	<%}%> 
                        <%}%> 
					</tbody>
					</table> 
				
				</div>
			</div>
		</div>
	</div>


	<div align="center" class="no-print"><a href="/employment/index.jsp?finished=true" class="btn btn-sm btn-danger">Back to Employment</a></div>
<br/><br/>


<!-- View Job -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">View Competition Application</h4>
      </div>
      <div class="modal-body">
       <iframe src="" width="500" height="400"></iframe>    
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-xs btn-danger" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>


<script>
	$(document).ready(
			function() {
				$(".showModal").click(function(e) {
					e.preventDefault();
					var url = $(this).attr("data-href");
					$("#myModal iframe").attr("src", url);
					$("#myModal").modal("show");
				});

				$("#jobsapp").dataTable(
						{
							"order" : [ [ 0, "desc" ] ],
							"lengthMenu" : [ [ 10, 25, 50, 100, 200, -1 ],
									[ 10, 25, 50, 100, 200, "All" ] ]
						});
			});
</script>                            
                     
</body>
</html>
