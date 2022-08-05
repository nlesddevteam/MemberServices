<%@ page language="java" session="true" import="com.awsd.security.*"
	isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>

<esd:SecurityCheck />

<%
	User usr = (User) session.getAttribute("usr");
%>

<html>
<head>


<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<meta http-equiv="Pragma" content="no-cache">
<meta name="description" content="MemberServices Navigation Menu Page">
<meta name="keywords" content="">

<link rel="stylesheet" href="includes/css/jquery-ui-1.10.3.custom.css">
<link rel="stylesheet" href="includes/css/ms.css">
<script>
	(function(i, s, o, g, r, a, m) {
		i['GoogleAnalyticsObject'] = r;
		i[r] = i[r] || function() {
			(i[r].q = i[r].q || []).push(arguments)
		}, i[r].l = 1 * new Date();
		a = s.createElement(o), m = s.getElementsByTagName(o)[0];
		a.async = 1;
		a.src = g;
		m.parentNode.insertBefore(a, m)
	})(window, document, 'script', '//www.google-analytics.com/analytics.js',
			'ga');

	ga('create', 'UA-74660544-1', 'auto');
	ga('send', 'pageview');
</script>
<script src="includes/js/jquery-1.7.2.min.js"></script>
<script src="includes/js/jquery-1.9.1.js"></script>
<script src="includes/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript" src="includes/js/common.js"></script>
<!--  <script type="text/javascript">
			$('document').ready(function(){
		
				$('#loading_dialog').dialog({
					autoOpen: false,
					bgiframe: true,
					width: 270,
					height: 200,
					modal: true,
					hide: 'explode',
					closeOnEscape: false,
					draggable: false,
					resizable: false,   
					open: function(event, ui) { 
						$(".ui-dialog-titlebar").hide(); 
					}
				});

				$('a').click(function(){
					$("#loading_dialog").dialog('open');
				});	

			});
			
		</script>-->
<script>
	jQuery(function() {
		$(".img-swap").hover(function() {
			this.src = this.src.replace("-off", "-on");
		}, function() {
			this.src = this.src.replace("-on", "-off");
		});
	});
</script>
<script src="includes/js/backgroundchange.js"></script>

<title>NLESD Member Services</title>

</head>

<body>
	<br />
	<div class="mainContainer">

		<div class="section group">
			<div class="col full_block topper" style="text-align: center;">
				<script src="includes/js/date.js"></script>
			</div>
			<div class="col full_block content">
				<br />
				<div align="center">
					<img src="includes/img/msheader.png" class="msHeaderLogo">
				</div>

				<div class="welcomeMessage">
					Welcome <span style="text-transform: capitalize;"><%=usr.getPersonnel().getFirstName()%>
						<%=usr.getPersonnel().getLastName()%></span>. Below are your available
					applications.<br /> You are currently classified as:<br /><%=usr.getPersonnel().getPersonnelCategory().getPersonnelCategoryName()%>
					(<%=(usr.getPersonnel().getSchool() != null ? usr.getPersonnel().getSchool().getSchoolName() : "NO SCHOOL")%>)<br />
					For technical assistance with your account, please email <a
						href="mailto:geofftaylor@nlesd.ca?subject=Member Services Support Request for <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%>">geofftaylor@nlesd.ca</a>. If support if for a particular application below, see the help contact information in that application.
				</div>

				<br />
			</div>
			<div class="col full_block content"
				style="position: relative; text-align: center;">
				<div class="txtPadding">
					<div align="center" style="display: none;">
						<div
							style="border: 1px solid Red; width: 85%; padding: 5px; font-size: 10px; text-align: center; background-color: #FFFFF0;">
							<b>PLEASE NOTE RE NEW GOOGLE MS LOGIN AND MISSING ACCOUNT
								INFO</b> <br />If you have any issues with missing travel claims
							and/or growth plans once you login automatically using your NLESD
							Google credentials, please email us as you have possibly created
							a new account in error. Your proper MS account may have a
							different email address associated with it and needs to be
							updated. Please DO NOT enter any new data into the account if you
							feel you are missing previous entries as these will be lost when
							we associate your old data to your proper email address. <br />
							<b>If you are a new user or have no missing items, you can
								ignore this message as your account is not affected.</b>
						</div>

					</div>
					<br />
					<!-- Duplicate Account Warning -->
					<esd:SecurityAccessRequired roles="DUPLICATE ACCOUNT">
						<br />
						<div class="col full_block duplicateWarning">&nbsp; ACCOUNT
							NOTICE - DUPLICATE ACCOUNT(S)</div>
						<div class="col full_block duplicateText">
							You are seeing this message because you have multiple Member
							Services Accounts.<br /> You will need to login to your
							originally assigned Member Services account.<br /> For technical
							assistance with your account, please email <a
								href="mailto:mssupport@nlesd.ca?subject=Member Services Support Request for <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%>">mssupport@nlesd.ca</a>.
						</div>
						<br />
					</esd:SecurityAccessRequired>
					

<!-- New Member Adminsitration -->
					<esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW">
						<div class="menuIconImage">
							<a href="Administration/index.jsp"> <img
								src="includes/img/menu/membersadmin-off.png"
								class="img-swap menuImage" border=0
								title="Member Admin is an administrative interface for assigning permissions to members based on job role and application access requirements.">
							</a>
						</div>
					</esd:SecurityAccessRequired>
					
<!-- Office Staff Update -->
					<esd:SecurityAccessRequired
						roles="ADMINISTRATOR,OFFICE-STAFF-WEBUPDATE-ALL,OFFICE-STAFF-WEBUPDATE-AVALON,OFFICE-STAFF-WEBUPDATE-CENTRAL,OFFICE-STAFF-WEBUPDATE-WESTERN,OFFICE-STAFF-WEBUPDATE-LABRADOR">
						<div class="menuIconImage">
							<a href="StaffDirectory/staff_directory.jsp">
								<img src="includes/img/menu/staffupdate-off.png"
								class="img-swap menuImage" title="Office Staff Update System"
								border=0>
							</a>
						</div>
					</esd:SecurityAccessRequired>
<!-- PS Counts -->					
<esd:SecurityAccessRequired roles="SENIOR EDUCATION OFFICIER,ASSISTANT DIRECTORS,ADMINISTRATOR,ADHR,DIRECTOR">
						<div class="menuIconImage">                              
							<a href="PSCounts/viewPSCounts.html">
								<img src="includes/img/menu/psdata-off.png" class="img-swap menuImage" border=0 title="School PS Allocation Data">
							</a>                                                         
						</div>
					</esd:SecurityAccessRequired>					
					
<!-- TRAINING - ALL STAFF -->			


						<div class="menuIconImage">
							<a href="Training/"><img
								src="includes/img/menu/training-off.png"
								class="img-swap menuImage" 
								border=0 							
								title="Training for District Staff">
							</a>
						</div>			


<!-- Admin School Profile Update -->
					<esd:SecurityAccessRequired
						roles="ADMINISTRATOR,PRINCIPAL,VICE PRINCIPAL,SCHOOL SECRETARY">
						<div class="menuIconImage">
							<a href="SchoolDirectory/school_profile.jsp">
								<img src="includes/img/menu/sps-off.png"
								class="img-swap menuImage" title="School Profile Update System"
								border=0>
							</a>
						</div>
					</esd:SecurityAccessRequired>				

			<!-- Bus Route Update -->
					<esd:SecurityAccessRequired roles="ADMINISTRATOR,BUSROUTE-POST">
						<div class="menuIconImage">
							<a href="BusRoutes/school_directory_bus_routes.jsp">
								<img src="includes/img/menu/busroute-off.png"
								class="img-swap menuImage" title="Bus Route Update System"
								border=0>
							</a>
						</div>
					</esd:SecurityAccessRequired>
	
	<!-- School Review System--> 
<esd:SecurityAccessRequired roles="ADMINISTRATOR,SCHOOL-REVIEW-ADMIN">
						<div class="menuIconImage">                              	
							<a href="SchoolReviews/viewSchoolReviews.html">
							<img src="includes/img/menu/schoolreview-off.png" class="img-swap menuImage" border=0 title="SCHOOL REVIEW PROCESS ADMIN">
							</a>                                                         
						</div>
</esd:SecurityAccessRequired>

					
	<!-- Weather Central -->
					<esd:SecurityAccessRequired
						permissions="WEATHERCENTRAL-PRINCIPAL-ADMINVIEW">
						<div class="menuIconImage">
							<a href="SchoolStatus/viewWeatherCentralAdmin.html"> <img
								src="includes/img/menu/schoolstatus-off.png"
								class="img-swap menuImage" border=0
								title="The School Status application allows a principal to set the closure status for schools in their school system.">
							</a>
						</div>
					</esd:SecurityAccessRequired>


					<!-- Weather Central Admin  -->
					<esd:SecurityAccessRequired
						permissions="WEATHERCENTRAL-GLOBAL-ADMIN">
						<div class="menuIconImage">
							<a href="SchoolStatus/regionalized_school_admin.jsp"> <img
								src="includes/img/menu/schoolstatusadmin-off.png"
								class="img-swap menuImage" border=0
								title="The Weather Central Global Admin application allows an administrator to set the weather closure status for any school in the district.">
							</a>
						</div>
					</esd:SecurityAccessRequired>


					<!-- Personnel Package 2019-->
					<esd:SecurityAccessRequired
						permissions="PERSONNEL-IT-VIEW-SCHOOL-EMPLOYEES,PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW,RTH-NEW-REQUEST,PERSONNEL-RTH-VIEW-APPROVALS,PERSONNEL-VIEW-SUBMITTED-REFERENCES">
						<div class="menuIconImage">
							<a href="Personnel/admin_index.jsp"> <img
								src="includes/img/menu/myhrp-off.png" class="img-swap menuImage"
								border=0 title="Human Resources Profile System">
							</a>
						</div>
					</esd:SecurityAccessRequired> 
					

					

					<!-- Kindergarten Registration -->
					<esd:SecurityAccessRequired
						permissions="KINDERGARTEN-REGISTRATION-ADMIN-VIEW,KINDERGARTEN-REGISTRATION-SCHOOL-VIEW">
						<div class="menuIconImage">
							<a href="schools/registration/kindergarten/admin/index.html">
								<img src="includes/img/menu/kindergarten-off.png"
								class="img-swap menuImage" border=0
								title="Kindergarten Student Registration - Administration.">
							</a>
						</div>
					</esd:SecurityAccessRequired>
					
					
			<!-- Teacher / Secretary Profile System -->
					<esd:SecurityAccessRequired
						permissions="PERSONNEL-PROFILE-TEACHER-VIEW,PERSONNEL-PROFILE-SECRETARY-VIEW">
						<div class="menuIconImage">
							<a href="Profile/Teacher/"> <img
								src="includes/img/menu/userprofile-off.png"
								class="img-swap menuImage" border=0
								title="User Profile Manager allows a Teacher/Support Staff employee to modify their current name and school assignment.">
							</a>
						</div>
					</esd:SecurityAccessRequired>


<!-- COVID 19-UPDATES - ALL STAFF -->					
						<div class="menuIconImage">
							<a href="covid19/index.jsp"><img
								src="includes/img/menu/covid19-off.png"
								class="img-swap menuImage" border=0
								title="Covid-19 Information Shared with Schools and District Staff">
							</a>
						</div>
	<!-- OHS-UPDATES - ALL STAFF -->					
						<div class="menuIconImage">
							<a href="//sites.google.com/nlesd.ca/ohsorientation/home" target="_blank"><img
								src="includes/img/menu/ohs-off.png"
								class="img-swap menuImage" 
								border=0 							
								title="OHS Information Shared with Schools and District Staff">
							</a>
						</div>			




<!-- Admin Planner --> 
					<esd:SecurityAccessRequired
						roles="ANNUAL-PLANNER-VIEW,WEB DESIGNER,WEB OPERATOR,PRINCIPAL,VICE PRINCIPAL,DIRECTOR,ASSISTANT DIRECTORS,SENIOR EDUCATION OFFICIER,SENIOR ADMINISTRATIVE OFFICER,PROGRAM SPECIALISTS,ADMINISTRATIVE ASSISTANT">

						<div class="menuIconImage">
							<a href="https://sites.google.com/nlesd.ca/theannualplanner/home"
								target="_blank"> <img
								src="includes/img/menu/aplanner-off.png"
								class="img-swap menuImage" border=0
								title="Annual Planner for School Administrators.">
							</a>
						</div>
					</esd:SecurityAccessRequired>


<!-- PD Calendar -->
<esd:SecurityAccessRequired permissions="CALENDAR-VIEW">
						<div class="menuIconImage">
							<a href="PDReg/viewDistrictCalendar.html"> <img
								src="includes/img/menu/pdcal-off.png" class="img-swap menuImage"
								border=0
								title="The District Calendar allows users to register for upcoming events around the district.">
							</a>
						</div>
</esd:SecurityAccessRequired>

<!-- Professional Learning Series --> 				
<!-- Professional Learning PLAN  --> 
<esd:SecurityAccessRequired permissions="PPGP-VIEW">	

						<div class="menuIconImage">
							<a href="https://sites.google.com/nlesd.ca/plseries-sept2020/home"
								target="_blank"> <img
								src="includes/img/menu/pls-off.png"
								class="img-swap menuImage" border=0
								title="Professional Learning Series">
							</a>
						</div>
					
						
						<div class="menuIconImage">
							<a href="https://sites.google.com/nlesd.ca/plseries-sept2020/professional-learning-journey?authuser=0"
								target="_blank"> <img
								src="includes/img/menu/learningplan-off.png"
								class="img-swap menuImage" border=0
								title="Professional Learning Journey">
							</a>
						</div>

						<div class="menuIconImage">
							<a href="PPGP/ppgpPolicy.html"> <img
								src="includes/img/menu/learningplana-off.png"
								class="img-swap menuImage" border=0
								title="Professional Learning Plans Archive">
							</a>
						</div>
</esd:SecurityAccessRequired>

<!-- Professional Learning Resources
<esd:SecurityAccessRequired
						permissions="PROFESSIONAL-LEARNING-RESOURCES">

						<div class="menuIconImage">
							<a href="https://pl.nlesd.ca"
								target="_blank"> <img
								src="includes/img/menu/plr-off.png"
								class="img-swap menuImage" border=0
								title="Professional Learning Resources">
							</a>
						</div>
</esd:SecurityAccessRequired>		
				
			 --> 	
	



					<!-- Pay Advice Admin -->
					<esd:SecurityAccessRequired permissions="PAY-ADVICE-ADMIN">
						<div class="menuIconImage">
							<a href="PayAdvice/index.jsp"><img
								src="includes/img/menu/payadvice-off.png"
								class="img-swap menuImage" title="Pay Advice Admin System"
								border=0></a>
						</div>
					</esd:SecurityAccessRequired>



					<!-- Pay Advice for Teachers -->
					<esd:SecurityAccessRequired permissions="PAY-ADVICE-NORMAL">
						<div class="menuIconImage">
							<a href="PayAdvice/viewTeacherPaystubs.html"><img
								src="includes/img/menu/payadviceteacher-off.png"
								class="img-swap menuImage" title="Pay Advice for Teachers"
								border=0></a>
						</div>
					</esd:SecurityAccessRequired>

		


					<!-- Tender Posting System -->
					<esd:SecurityAccessRequired
						permissions="TENDER-ADMIN,TENDER-EDIT,TENDER-VIEW">
						<div class="menuIconImage">
							<a href="Tenders/viewTenders.html"><img
								src="includes/img/menu/tenders-off.png"
								class="img-swap menuImage" title="Tender Posting System"
								border=0></a>
						</div>
					</esd:SecurityAccessRequired>
			

			

					<!-- Web Maintenance -->
					<esd:SecurityAccessRequired
						permissions="WEBMAINTENANCE-VIEW,WEBMAINTENANCE-BUSROUTES">
						<div class="menuIconImage">
							<a href="WebUpdateSystem/index.jsp"> <img
								src="includes/img/menu/webupdate-off.png"
								class="img-swap menuImage" title="Website Update Posting System"
								border=0>
							</a>
						</div>
					</esd:SecurityAccessRequired>

					<!-- Member Adminsitration 
					<esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW">
						<div class="menuIconImage">
							<a href="MemberAdmin/viewMemberAdmin.html"> <img
								src="includes/img/menu/membersadmin-off.png"
								class="img-swap menuImage" border=0
								title="Member Admin is an administrative interface for assigning permissions to members based on job role and application access requirements.">
							</a>
						</div>
					</esd:SecurityAccessRequired>
-->

					<!-- Policy Feedback -->
					<esd:SecurityAccessRequired
						roles="WEB DESIGNER,WEB OPERATOR,PRINCIPAL,VICE PRINCIPAL,DIRECTOR,ASSISTANT DIRECTORS,SENIOR EDUCATION OFFICIER,SENIOR ADMINISTRATIVE OFFICER,PROGRAM SPECIALISTS">

						<div class="menuIconImage">
							<a href="policyfeedback/"> <img
								src="includes/img/menu/policyfeedback-off.png"
								class="img-swap menuImage" border=0
								title="NLESD Policy Feedback for School Administrators.">
							</a>
						</div>
					</esd:SecurityAccessRequired>



					


					<!-- Student Conduct Reporting -->
					<esd:SecurityAccessRequired
						permissions="BULLYING-ANALYSIS-SCHOOL-VIEW,BULLYING-ANALYSIS-ADMIN-VIEW">
						<div class="menuIconImage">
							<a href="scrs/"> <img
								src="includes/img/menu/studentconduct-off.png"
								class="img-swap menuImage" border=0
								title="Student Conduct Reporting &amp; Analysis System.">
							</a>
						</div>
					</esd:SecurityAccessRequired>


					<!-- Trustee Document Repository -->
					<esd:SecurityAccessRequired permissions="TSDOC-VIEW">
						<div class="menuIconImage">
							<a href="tsdoc/"> <img
								src="includes/img/menu/securetrustee-off.png"
								class="img-swap menuImage" border=0
								title="Repository of communication documents and notes for the District's Board of Trustees and it various committees.">
							</a>
						</div>
					</esd:SecurityAccessRequired>


					<!-- Live Webcast System 
					<esd:SecurityAccessRequired
						roles="ADMINISTRATOR,EXECUTIVE ASSISTANT,DIRECTOR,TRUSTEE,MEDIA BROADCASTER">
						<div class="menuIconImage">
							<a href="webcast/"> <img
								src="includes/img/menu/webcastlive-off.png"
								class="img-swap menuImage" border=0
								title="Live Webcast system for Public Consultations and Meetings.">
							</a>
						</div>
					</esd:SecurityAccessRequired>
-->

					<!-- Webcast Archive
					<esd:SecurityAccessRequired
						roles="ADMINISTRATOR,EXECUTIVE ASSISTANT,DIRECTOR,TRUSTEE,MEDIA BROADCASTER">
						<div class="menuIconImage">
							<a href="webcast/archive.jsp"> <img
								src="includes/img/menu/webcastarchive-off.png"
								class="img-swap menuImage" border=0
								title="Public Consultations and Board Meetings Video Session Archive.">
							</a>
						</div>
					</esd:SecurityAccessRequired>
 -->

					

					<!-- Survey Creation 
					<esd:SecurityAccessRequired permissions="SURVEY-ADMIN-VIEW">
						<div class="menuIconImage">
							<a href="survey/admin/"> <img
								src="includes/img/menu/survey-off.png"
								class="img-swap menuImage" border=0
								title="School based survey creation and administration.">
							</a>
						</div>
					</esd:SecurityAccessRequired>

 -->
 
					<!-- Complaint Tracking
					<esd:SecurityAccessRequired
						permissions="COMPLAINT-MONITOR,COMPLAINT-ADMIN,COMPLAINT-VIEW">
						<div class="menuIconImage">
							<a href="complaint/admin_complaint_summary.jsp"> <img
								src="includes/img/menu/complaint-off.png"
								class="img-swap menuImage" border=0
								title="District complaint monitoring/tracking system.">
							</a>
						</div>
					</esd:SecurityAccessRequired>
 -->
 
 
					<!-- Student Travel Request System -->
					<esd:SecurityAccessRequired
						permissions="STUDENT-TRAVEL-ADMIN-VIEW,STUDENT-TRAVEL-PRINCIPAL-VIEW">
						<div class="menuIconImage">
							<a href="student/travel/"> <img
								src="includes/img/menu/studenttravel-off.png"
								class="img-swap menuImage" border=0
								title="District out of province student travel request and tracking system.">
							</a>
						</div>
					</esd:SecurityAccessRequired>

				
					<!-- Travel Claim System  -->
					<esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-VIEW">
						<div class="menuIconImage">
							<a href="Travel/viewTravelClaimSystem.html"> <img
								src="includes/img/menu/travelclaim-off.png"
								class="img-swap menuImage" border=0
								title="The Travel Claim Systems allow you to submit travel claims and track the status of your claims as they are being processed.">
							</a>
						</div>
					</esd:SecurityAccessRequired>


					<!-- Maintenance Request System -->
					<esd:SecurityAccessRequired
						permissions="MAINTENANCE-SCHOOL-VIEW,MAINTENANCE-ADMIN-VIEW,MAINTENANCE-WORKORDERS-VIEW">
						<div class="menuIconImage">
							<a href="https://siems.nlesd.ca"> <img
								src="includes/img/menu/ems-off.png" class="img-swap menuImage"
								border=0
								title="System Inspection Enterprise Management System allows you to submit and track school maintenance requests.">
							</a>
						</div>
					</esd:SecurityAccessRequired>

					<!-- Weather Central 
					<esd:SecurityAccessRequired
						permissions="WEATHERCENTRAL-PRINCIPAL-ADMINVIEW">
						<div class="menuIconImage">
							<a href="WeatherCentral/viewWeatherCentralAdmin.html"> <img
								src="includes/img/menu/schoolstatus-off.png"
								class="img-swap menuImage" border=0
								title="The School Status application allows a principal to set the closure status for schools in their school system.">
							</a>
						</div>
					</esd:SecurityAccessRequired>
	-->

					<!-- Weather Central Admin  -
					<esd:SecurityAccessRequired
						permissions="WEATHERCENTRAL-GLOBAL-ADMIN">
						<div class="menuIconImage">
							<a href="WeatherCentral/regionalized_school_admin.jsp"> <img
								src="includes/img/menu/schoolstatusadmin-off.png"
								class="img-swap menuImage" border=0
								title="The Weather Central Global Admin application allows an administrator to set the weather closure status for any school in the district.">
							</a>
						</div>
					</esd:SecurityAccessRequired>
-->
					<esd:SecurityAccessRequired permissions="BCS-SYSTEM-ACCESS">
						<div class="menuIconImage">
							<a href="BCS/index.html"><img
								src="includes/img/menu/bussystem-off.png"
								class="img-swap menuImage" title="Bussing Contractor System"
								border=0></a>
						</div>
					</esd:SecurityAccessRequired>
					<!-- EECED -->
					<esd:SecurityAccessRequired roles="PRINCIPAL">
						<div class="menuIconImage">                              	
							<a href="EECD/schoolAdminViewApprovals.html">
							<img src="includes/img/menu/eecdp-off.png" class="img-swap menuImage" border=0 title="EECD SCHOOL ADMIN">
							</a>                                                         
						</div>
					</esd:SecurityAccessRequired>
					<esd:SecurityAccessRequired permissions="EECD-VIEW">
						<div class="menuIconImage">                              
							<a href="EECD/viewEECD.html">
								<img src="includes/img/menu/eecd-off.png" class="img-swap menuImage" border=0 title="EECD">
							</a>                                                         
						</div>
					</esd:SecurityAccessRequired>
					<esd:SecurityAccessRequired permissions="EECD-VIEW-ADMIN,EECD-VIEW-SHORTLIST">
						<div class="menuIconImage">                              
							<a href="EECD/adminViewAreas.html">
								<img src="includes/img/menu/eecda-off.png" class="img-swap menuImage" border=0 title="EECD ADMIN">
							</a>                                                         
						</div>
					</esd:SecurityAccessRequired>

				

				</div>




				<br />
				<br />


			</div>






			<div class="section group">
				<div class="col full_block copyright">&copy; 2018 Newfoundland
					and Labrador English School District</div>
			</div>
		</div>


		<br />
</body>
</html>