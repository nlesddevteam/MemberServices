<%@ page language="java"
	import="com.esdnl.personnel.jobs.bean.*,
         				 com.esdnl.personnel.jobs.constants.*,
         				 com.esdnl.personnel.jobs.dao.*,
         				   com.esdnl.personnel.jobs.bean.*,                
         				 com.awsd.security.*,         				 
         				 com.awsd.mail.bean.*,         				 
         				 java.util.*"
	isThreadSafe="false"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job"%>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
<esd:SecurityRequiredPageObjectsCheck
	names='<%=new String[]{"JOB", "JOB_APPLICANTS"}%>'
	scope="<%=PageContext.SESSION_SCOPE%>"
	redirectTo="/Personnel/admin_index.jsp" />

<%
	if(request.getAttribute("filterparams")==null){
		session.setAttribute("sfilterparams",null); 
	}else{
		session.setAttribute("sfilterparams",request.getAttribute("filterparams"));
	}
	User usr = (User) session.getAttribute("usr");
	JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
	ApplicantProfileBean[] applicants = (ApplicantProfileBean[]) session.getAttribute("JOB_APPLICANTS");

	int locationId = ((JobOpportunityAssignmentBean) job.get(0)).getLocation();

	ApplicantEducationOtherBean edu_other = null;

	//Lab West Schools
	List<Integer> labWestSchools = Arrays.asList(new Integer(330), new Integer(335), new Integer(338));

	//Email Applicants

	String msg = "";

	if ((request.getParameter("sent") != null) && (request.getParameter("sent").equalsIgnoreCase("1"))) {

		String to[] = null, cc[] = null, bcc[] = null;
		String subject = "", message = "";

		try {
			if (request.getParameter("to") != null) {
				to = (usr.getPersonnel().getEmailAddress() + ";" + request.getParameter("to")).split(";");
				System.err.println("TO: " + request.getParameter("to"));
			} else
				to = new String[]{usr.getPersonnel().getEmailAddress()};

			if ((request.getParameter("cc") != null) && (!request.getParameter("cc").equals(""))) {
				cc = request.getParameter("cc").split(";");
				System.err.println("CC: " + request.getParameter("cc"));
			} else {
				cc = null;
			}

			if ((request.getParameter("bcc") != null) && (!request.getParameter("bcc").equals(""))) {
				bcc = request.getParameter("bcc").split(";");
				System.err.println("BCC: " + request.getParameter("bcc"));
			} else {
				bcc = null;
			}

			if (request.getParameter("subject") != null) {
				subject = request.getParameter("subject");
			}

			if (request.getParameter("message") != null) {
				message = request.getParameter("message");
			}
			if (request.getParameter("bcc").length() < 3500) {
				EmailBean email = new EmailBean();
				email.setTo(to);
				email.setCC(cc);
				email.setBCC(bcc);
				email.setSubject(subject);
				email.setBody(message);
				email.setFrom(usr.getPersonnel().getEmailAddress());
				email.send();
			} else {
				String bcc1[] = new String[bcc.length / 2];
				String bcc2[] = new String[bcc.length - bcc1.length];

				System.arraycopy(bcc, 0, bcc1, 0, bcc1.length);
				System.arraycopy(bcc, bcc1.length, bcc2, 0, bcc2.length);

				EmailBean email = new EmailBean();
				email.setTo(to);
				email.setCC(cc);
				email.setBCC(bcc1);
				email.setSubject(subject);
				email.setBody(message);
				email.setFrom(usr.getPersonnel().getEmailAddress());
				email.send();

				email.setBCC(bcc2);
				email.send();
			}

			msg = "Email sent successfully.";

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			msg = "Could not send email.";
		}
	}
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display", "none");

	function confirm_send() {
		return confirm('Are you ready to send this email?');
	}

	$(function() {

		$("#jobsapp").DataTable({
			"order" : [ [ 2, "desc" ] ],
			"lengthMenu" : [ [ 50, 100, 200, -1 ], [ 50, 100, 200, "All" ] ]
		});
	});
</script>

<style>
input {
	border: 1px solid silver;
}
</style>

</head>
<body>



	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success">
			<div class="panel-heading">
				<b>Competition # <%=job.getCompetitionNumber()%></b> (Total
				Applicants:
				<%=applicants.length%>)
			</div>
			<div class="panel-body">

				<%
					if (request.getAttribute("msg") != null) {
				%>
				<div class="alert alert-danger">
					<%=(String) request.getAttribute("msg")%>
				</div>
				<%
					}
				%>
				<div class="alert alert-success" id="messageText"
					style="text-align: center; display: none;"></div>

				<!-- ADMINISTRATIVE FUNCTIONS -->

				<div class="no-print" align="center">

					<a onclick="loadingData()" class="btn btn-xs btn-info"
						href='view_job_post.jsp?comp_num=<%=job.getCompetitionNumber()%>'>View
						Job</a> <a class="btn btn-xs btn-primary" href='#'
						title='Print this page (pre-formatted)'
						class="btn btn-xs btn-primary"
						onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Human Resources Profile System</b></div><br/><br/>'});"><span
						class="glyphicon glyphicon-print"></span> Print</a>
					<%
						if (job.getIsSupport().equals("Y")) {
					%>
					<a class="btn btn-xs btn-warning"
						href='admin_filter_job_applicants_ss.jsp'>Filter</a>
					<%
						} else {
					%>
					<a class="btn btn-xs btn-warning"
						href='admin_filter_job_applicants.jsp'>Filter</a>
					<%
						}
					%>
					<%
						if (job.isClosed()) {
					%>
					
					<a onclick="loadingData()" class="btn btn-xs btn-info" href='viewJobShortList.html?comp_num=<%=job.getCompetitionNumber()%>'>View Shortlist</a>
					<%}%>	
					<button type="button" class="btn btn-warning btn-xs" data-toggle="modal" data-target="#emailModal">Email Applicants</button>

					<a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>


				</div>
				<br />

				<div class="table-responsive">
					<c:choose>
						<c:when test="${filterparams ne null }">
								<div class="panel-group">
  									<div class="panel panel-default">
    									<div class="panel-heading">
      										<h4 class="panel-title">
        										<a data-toggle="collapse" id="selDegs" href="#degrees"><span class="glyphicon glyphicon-triangle-bottom"></span> My Filter(s)</a>
      										</h4>
    								</div>
    								<div id="degrees" class="panel-collapse collapse in">
    									
    										<table class="table table-condensed table-striped"
						style="font-size: 11px; background-color: #FFFFFF;">
    										<tr>
    										<td width="20%">Permanent NLESD Contract?</td>
    										<td width="20%">
    											<c:choose>
    												<c:when test="${filterparams.permanentContract eq 'Y' }">
    													Yes
    												</c:when>
    												<c:when test="${filterparams.permanentContract eq 'N' }">
    													No
    												</c:when>
    												<c:otherwise>
    													Any
    												</c:otherwise>
    											</c:choose>
    										</td>
    										<td width="20%"># Math Courses:</td>
    										<td width="20%">
    											${filterparams.mathCourses }
    										</td>
    										<td width="20%">
    										Degree(s): ${filterparams.getDegreesString() }
    										</td>
    										</tr>
    										<tr>
    										<td width="20%">Permanent Exp (Months):</td>
    										<td width="20%">
    											${filterparams.permanentExp }
    										</td>
    										<td width="20%"># English Courses:</td>
    										<td width="20%">
    											${filterparams.englishCourses }
    										</td>
    										<td width="20%">
    											Major Subject Group(s): ${filterparams.getMajorsSubjectGroupsString() } 
    										</td>
    										</tr>
    										<tr>
    										<td width="20%">Replacement Exp (Months):</td>
    										<td width="20%">
    											${filterparams.replacementExp }
    										</td>
    										<td width="20%"># Music Courses:</td>
    										<td width="20%">
    											${filterparams.musicCourses }
    										</td>
    										<td width="20%">
    										Major(s): ${filterparams.getMajorsString() }
    										</td>
    										</tr>
    										<tr>
    										<td width="20%">Repl + Perm (Months):</td>
    										<td width="20%">
    											${filterparams.totalExp }
    										</td>
    										<td width="20%"># Technology Courses:</td>
    										<td width="20%">
    											${filterparams.technologyCourses }
    										</td>
    										<td width="20%">
    											Minor Subject Group(s): ${filterparams.getMinorsSubjectGroupsString() } 
    										</td>
    										</tr>
    										<tr>
    										<td width="20%"># Sub Days:</td>
    										<td width="20%">
    											${filterparams.subDays }
    										</td>
    										<td width="20%"># Science Courses:</td>
    										<td width="20%">
    											${filterparams.scienceCourses }
    										</td>
    										<td width="20%">
    											Minors(s): ${filterparams.getMinorsString() }
    										</td>
    										</tr>
    										<tr>
    										<td width="20%">TLA Courses > 20 / CEC L2:</td>
    										<td width="20%">
    											<c:choose>
    												<c:when test="${filterparams.isTLARequirements()}">
    													Yes
    												</c:when>
    												<c:otherwise>
    													No
    												</c:otherwise>
    											</c:choose>
    										</td>
    										<td width="20%"># Social Studies Courses:</td>
    										<td width="20%">
    											${filterparams.socialStudiesCourses }
    										</td>
    										<td width="20%" rowspan='3'>
    											Regional Preferences: ${filterparams.getRegionsString()}
    										</td>
    										</tr>
    										<tr>
    										<td width="20%"># Special Ed Courses:</td>
    										<td width="20%">
    											${filterparams.specialEducationCourses}
    										</td>
    										<td width="20%"># Art Courses:</td>
    										<td width="20%">
    											${filterparams.artCourses }
    										</td>
    										</tr>
    										<tr>
    										<td width="20%"># French Courses:</td>
    										<td width="20%">
    											${filterparams.frenchCourses}
    										</td>
    										<td width="20%">Level Of Prof Training:</td>
    										<td width="20%">
    											${filterparams.getTrainingString() }
    										</td>
    										</tr>
    										</table>
      									</div>
    								</div>
  									
								</div>
						</c:when>
					</c:choose>
					
					
					<%
						if (applicants.length > 0) {
					%>

					Below is a table of current applicants for this position sorted by
					seniority. 
					<%if (job.getIsSupport().equals("N")) { %>
					The type and certificates/courses are displayed for
					quick reference for <b>non support staff/management positions</b>.
					(UT = #University Transcripts, TC = #Teaching Certificates, COC =
					#Code of Conducts, FPD = #French Proficiency (DELF), CEC = #Level 2
					Early Childhood Education Certificates , and #CRS = # of Courses)
					<%}%> 
					<br />
					<br />
					<table id="jobsapp" class="table table-condensed table-striped"
						style="font-size: 11px; background-color: #FFFFFF;">
						<thead>
							<tr>
						
							
							<%if (job.getIsSupport().equals("Y")) { %>							
								<th width='30%'>NAME</th>
								<th width='30%'>EMAIL</th>
								<th width='10%'>SENIORITY</th>			
								<th width='15%'>TYPE</th>
								<th width='15%'>OPTIONS</th>
							<%} else {%>
								<th width='20%'>NAME</th>
								<th width='20%'>EMAIL</th>
								<th width='10%'>SENIORITY</th>					
								<th width='12%'>OTHER INFO</th>
								<th width='13%'>CERT./CRS</th>
								<th width='10%'>TYPE</th>
								<th width='15%'>OPTIONS</th>
							<%} %>	
							</tr>
						</thead>
						<tbody>

							<%
								boolean isLabWestSchool = false;
									for (int i = 0; i < applicants.length; i++) {
										isLabWestSchool = ((!labWestSchools.contains(locationId) && (applicants[i].getEsdExp() != null)
												&& labWestSchools.contains(applicants[i].getEsdExp().getPermanentContractSchool()))
														? true
														: false);
							%>
							<tr class='applicant-list<%=(isLabWestSchool ? " lab-west-school" : "")%>' uid='<%=applicants[i].getSIN()%>'>
							
							<!-- NAME -->	
								<td valign='top'><%=applicants[i].getFullName().replace("'", "&#39;")%></td>
								
							<!-- EMAIL -->	
								<td valign='top'><a	href="mailto:<%=applicants[i].getEmail()%>"><%=applicants[i].getEmail()%></a></td>
								
							<!-- SENIORITY -->	
								<td>
									<%if (applicants[i].getSenority() > 0) { %> <span style='color: red;'><%=applicants[i].getSenority()%></span>
									<%} else {%> <span style="color: DimGrey;">0</span> 
									<%}%>
								</td>
							<!-- OTHER (HIDE FOR NOW WITH SUPPORT JOBS -->
							<%if (job.getIsSupport().equals("N")) { %>	
								<td><%if (isLabWestSchool) {%> 
								Labrador West School: <%=applicants[i].getEsdExp().getPermanentContractLocationText()%>.
									<%} else {%> 
									<span style="color: DimGrey;">N/A</span>
									<%}%>
								</td>
							<%}%>	
					<%if ((applicants[i].getProfileType().equals("T") && job.getIsSupport().equals("Y")) || (applicants[i].getProfileType().equals("S") && job.getIsSupport().equals("N"))) {%>	
					<td style="background-color: #000000; color: White; text-align: center;vertical-align:middle;">USER WRONG PROFILE TYPE FOR THIS POSITION</td>
					<%} %>			
								
					<%if (applicants[i].getProfileType().equals("T") && job.getIsSupport().equals("N")) { %>	

								<% Collection<ApplicantDocumentBean> docs = ApplicantDocumentManager.getApplicantDocumentBean(applicants[i]);
												int coursesCompleted = 0;
												int UT = 0; //University Transcripts 1
												int TC = 0; //Teaching Certificates 2
												int CC = 0; //Code of Conduct  doc.getType().getValue() 3
												int FP = 0; //French Proficiency (DELF) doc.getType().getDescription() 4
												int EC = 0; //Level 2 Early Childhood Education Certificate   5
								%>
								<%if ((docs != null) && (docs.size() > 0)) {
													for (ApplicantDocumentBean doc : docs) {
								%>


								<%if (doc.getType().getValue() == 1) {
															UT++;
														} else if (doc.getType().getValue() == 2) {
															TC++;
														} else if (doc.getType().getValue() == 3) {
															CC++;
														} else if (doc.getType().getValue() == 4) {
															FP++;
														} else if (doc.getType().getValue() == 5) {
															EC++;
														} else {

														}
								%>

								<%}}%>

								<% edu_other = ApplicantEducationOtherManager.getApplicantEducationOtherBean(applicants[i].getSIN());%>

								<%if (edu_other != null) {
													coursesCompleted = edu_other.getTotalCoursesCompleted();
												} else {
													coursesCompleted = 0;
												}
								%>

								<!-- 
	                                    
If the user has Level 2 Early Childhood Education Certificate only, or just 20 plus courses, and NO Teaching Certificate they can be flagged as a TLA only
 
If they do not have ECE, but anything else including teacher certificate, etc, they are classified as Teacher
 
If they have a Teaching Certificate, and ECE, and/or 20 plus courses they can be a Teacher /TLA

	                              -->
	                              
	                          <!-- #Courses -->
	                              
								<td><b>UT:</b> <%=UT%> &middot; <b>TC:</b> <%=TC%><br /> 
								<b>FPD:</b>	<%=FP%> &middot; <b>COC:</b> <%=CC%><br /> <b>ECE:</b> <%=EC%> &middot; <b>#CRS:</b> <%=coursesCompleted%></td>

								<!-- If has 20+ courses or ECE Certificate) AND no teaching certificate -->
								<%if (((coursesCompleted >= 20) || (EC > 0)) && (TC < 1)) {%>
								<td style="background-color: #DDA0DD; color: Black; text-align: center;vertical-align:middle;">TLA</td>
								<!-- If is a Teacher AND a teaching certificate -->
								<% } else if ((applicants[i].getProfileType().equals("T")) && (TC > 0)) {%>
								<td style="background-color: #6495ED; color: Black; text-align: center;vertical-align:middle;">TEACHER</td>
								<!-- If is a Teacher AND a Teaching Certificate AND a ECE Certificate or 20+ courses -->
								<%} else if ((applicants[i].getProfileType().equals("T")) && (TC > 0) && ((EC > 0) || (coursesCompleted >= 20))) {%>
								<td style="background-color: #6495ED; color: Black; text-align: center;vertical-align:middle;">TEACHER/TLA</td>
								<%} else {%>
								<td style="background-color: #E9967A; color: Black; text-align: center;vertical-align:middle;">OTHER</td>
								<%}%>
								<%}  %>
								<!-- FUTURE USE for Support Types -->
							<!-- If is a Support/Managment  -->
								<% if (applicants[i].getProfileType().equals("S")) { %>
								<td style="background-color: #FFEBCD; color: Black; text-align: center;vertical-align:middle;">SUPPORT/MGMNT</td>
								<%}%>
								
								<td style="text-align:right;">
									<a  class='btn btn-xs btn-primary' href="viewApplicantProfile.html?sin=<%=applicants[i].getSIN()%>" target="_blank">Profile</a>
									<% if (usr.checkRole("ADMINISTRATOR") || usr.checkRole("MANAGER OF HR - PERSONNEL")) {%> 
									<a href="#" data-toggle="confirmation" data-title="Are you sure you wish to withdraw <%=applicants[i].getFullNameReverse()%> from this competition?"
									class="btn btn-danger btn-xs" comp-num='<%=job.getCompetitionNumber()%>' uid='<%=applicants[i].getSIN()%>'>Withdraw</a> 
									<%	}%>
								</td>
							</tr>
							<%
								}
							%>

						</tbody>
					</table>
					
					<%} else {%>
					No applicants currently on file for this competition.
					<%}%>
			</div>





			</div>
		</div>
	</div>


	<!-- ADMINISTRATIVE FUNCTIONS -->

	<div class="no-print" align="center">

		<a onclick="loadingData()" class="btn btn-xs btn-info"
			href='view_job_post.jsp?comp_num=<%=job.getCompetitionNumber()%>'>View
			Job</a> <a class="btn btn-xs btn-primary" href='#'
			title='Print this page (pre-formatted)'
			class="btn btn-xs btn-primary"
			onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Human Resources Profile System</b></div><br/><br/>'});"><span
			class="glyphicon glyphicon-print"></span> Print</a>
		<%
			if (job.getIsSupport().equals("Y")) {
		%>
		<a class="btn btn-xs btn-warning"
			href='admin_filter_job_applicants_ss.jsp'>Filter</a>
		<%
			} else {
		%>
		<a class="btn btn-xs btn-warning"
			href='admin_filter_job_applicants.jsp'>Filter</a>
		<%
			}
		%>
		<%
			if (job.isClosed()) {
		%>		
		<a onclick="loadingData()" class="btn btn-xs btn-info"
			href='viewJobShortList.html?comp_num=<%=job.getCompetitionNumber()%>'>View
			Shortlist</a>		
		<%}%>	
		<button type="button" class="btn btn-warning btn-xs"
			data-toggle="modal" data-target="#emailModal">Email
			Applicants</button>

		<a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>


	</div>
	<br />
	<!-- Email Modal -->
	<div id="emailModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">
						<span class="glyphicon glyphicon-envelope"></span> Email
						Applicants
					</h4>
				</div>
				<div class="modal-body">
					<form name="email" action="" method="post"
						onsubmit="return confirm_send();">

						<input type="hidden" name="sent" value="1">

						<div class="form-group">
							<label for="from">From:</label> <input class="form-control"
								type="text" name="from"
								value="<%=usr.getPersonnel().getEmailAddress()%>"
								readonly="readonly">
						</div>

						<div class="form-group">
							<label for="to">To:</label> <input class="form-control"
								type="text" name="to">
						</div>

						<div class="form-group">
							<label for="cc">Cc:</label> <input class="form-control"
								type="text" name="cc">
						</div>

						<div class="form-group">
							<label for="cc">Bcc:</label>
							<textarea name="bcc" class="form-control" rows="2">
    			<%
    				for (int i = 0; i < applicants.length; i++)
    					out.print(applicants[i].getEmail().replaceAll(",", ";") + ";");
    			%>    	
    	</textarea>
						</div>

						<div class="form-group">
							<label for="subject">Subject:</label> <input type="text"
								name="subject" class="form-control"
								value='NLESD Employment Opportunity System'>
						</div>

						<div class="form-group">
							<label for="cc">Message</label>
							<textarea name="message" class="form-control" rows="5"></textarea>
						</div>

						<input class="btn btn-xs btn-success" type="submit" value="Send" />
						<button type="button" class="btn btn-xs btn-danger"
							data-dismiss="modal">Cancel</button>
					</form>
				</div>

			</div>

		</div>
	</div>


	<script>
		$('[data-toggle=confirmation]').confirmation(
				{
					rootSelector : '[data-toggle=confirmation]',
					onConfirm : function(value) {
						$.post("withdrawJobApplicant.html", {
							comp_num : $(this).attr('comp-num'),
							uid : $(this).attr('uid'),
							ajax : true
						}, function(data) {
							$(
									'tr[uid='
											+ $(data).find('APPLICANT-UID')
													.text() + ']').remove();
							$('#messageText').css("display", "block").html(
									"<b>SUCCESS:</b> "
											+ $(data).find('RESPONSE').text())
									.delay(5000).fadeOut();
							$("#loadingSpinner").css("display", "none");
						}, "xml");
					},
					onCancel : function() {
						$("#loadingSpinner").css("display", "none");
					}
				});

		$('document').ready(function() {

			$('tr.lab-west-school td').css({
				'background-color' : '#FFFFCC',
				'border-top' : 'solid 2px #0000ff',
				'border-bottom' : 'solid 2px #0000ff'
			});

		});
	</script>
	<script>
		$(document).ready(function() {
			$('[data-toggle="tooltip"]').tooltip();
		});
	</script>
</body>
</html>