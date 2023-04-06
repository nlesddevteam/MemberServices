<%@ page language="java" session="true" import="com.awsd.security.*"
	isThreadSafe="false"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>	
	
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>

<esd:SecurityCheck />

<%
	User usr = (User) session.getAttribute("usr");
%>

<c:set var="ProfileImg" value='<%=session.getAttribute("gmailicon")%>'/>
<html>
<head>

<meta name="viewport" content="width=device-width, initial-scale=1.0">




<script>
	
	jQuery(function() {
		$(".img-swap").hover(function() {
			this.src = this.src.replace("-off", "-on");
		}, function() {
			this.src = this.src.replace("-on", "-off");
		});
	});
</script>


<title>NLESD StaffRoom</title>

</head>

<body>

<div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">

<div class="row">
<div class="col-lg-12">

<div class="alert alert-success" id="logInDetailBlock"  style="font-size:12px;margin-top:5px;text-align:center;">
<b>LOGGED IN AS:</b> <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%> &nbsp;&nbsp;
<b>CLASSIFIED AS:</b> <%=usr.getPersonnel().getPersonnelCategory().getPersonnelCategoryName()%> &nbsp;&nbsp; 
<b>LOCATION:</b> <%=(usr.getPersonnel().getSchool() != null ? usr.getPersonnel().getSchool().getSchoolName() : "<span style='color:Red;'>NO LOCATION</span>")%>
<br/><i>Information incorrect? Please use the <a href="https://forms.gle/rpYgeZfm81Wt5c138" target="_blank">StaffRoom HelpDesk</a> to send a support request.</i>
</div>
</div>
</div>

<c:choose>
<c:when test="${not empty ProfileImg}">
<a href="https://myaccount.google.com" target="_blank"><img src="${ProfileImg}" border=0 title="Your Google Profile Picture" onerror="this.onerror=null; this.src='/MemberServices/StaffRoom/includes/img/nltopleftlogo.png'" style="display: block;-webkit-user-select: none;margin: auto;max-height:120px;padding-right:10px;float:right;"/></a>
</c:when>
<c:otherwise>
  <img src="/MemberServices/StaffRoom/includes/img/nltopleftlogo.png" onerror="this.onerror=null; this.src='/MemberServices/StaffRoom/includes/img/nltopleftlogo.png'" style="max-height:120px;padding-right:10px;float:right;"/>
</c:otherwise>
</c:choose>

<div style="padding-top:5px;font-size:14px;" id="welcomeBlock">Welcome <span style="text-transform: capitalize;font-weight:bold;">
<%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></span> to your NLESD StaffRoom Portal. (Formally MemberServices).<br/><br/>

In this StaffRoom, depending on your District classification, you have access to a variety of staff web applications, resources, and support services via the icons below under various sections. 
Most of the links and pages found on the old web site under the old StaffRoom section can be found below and are still accessible via icons below.
Some applications/links may open in a new tab and/or require a secondary login depending on their access requirements. 
<i>Please note that this is a temporary access portal while we update our back-end systems and applications.</i>



</div>
<br/>

	
				
<hr>				
<div class="siteHeaderGreen">General Information</div>	
Links under this section provide general information on a given topic and/or link to external websites providing information for all NLESD staff.	<br/><br/>			
<!-- ATIPP information -->
				
				<div class="menuIconImage">
				<a href="https://myaccount.google.com" target="_blank">
				<img src="StaffRoom/includes/img/gaccount-off.png" class="img-swap menuImage" border=0 title="Your Google Account">
				</a>
		</div>	
					
		<div class="menuIconImage">
			<a href="StaffRoom/atipp.jsp">
			<img src="StaffRoom/includes/img/atipp-off.png" class="img-swap menuImage" border=0 title="ATIPP Information for Staff.">
			</a>
		</div>

<!-- Social media Awareness -->
	
		<div class="menuIconImage">
			<a href="StaffRoom/socialmedia.jsp">
			<img src="StaffRoom/includes/img/sma-off.png" class="img-swap menuImage" border=0 title="Social Media Awareness for Staff.">
			</a>
		</div>

<!-- Google GSuite -->	
							
		<div class="menuIconImage">
				<a href="StaffRoom/googleapps.jsp">
				<img src="StaffRoom/includes/img/gsuite-off.png" class="img-swap menuImage" border=0 title="Google Workspace Information.">
				</a>
		</div>		
						
<!-- Payroll Resources -->
					
		<div class="menuIconImage">
				<a href="StaffRoom/payrollresources.jsp">
				<img src="StaffRoom/includes/img/payresources-off.png" class="img-swap menuImage" border=0 title="Payroll Resources for Staff.">
				</a>
		</div>							

<!-- Teacher Resources 
						
		<div class="menuIconImage">
				<a href="StaffRoom/teacherresources.jsp">
				<img src="StaffRoom/includes/img/teacherresources-off.png" class="img-swap menuImage" border=0 title="Teacher Resources">
				</a>
		</div>	
	-->			

<!-- Staff Resources -->
	
		<div class="menuIconImage">
				<a href="StaffRoom/staffresources.jsp">
				<img src="StaffRoom/includes/img/staffresources-off.png" class="img-swap menuImage" border=0 title="Staff Resources.">
				</a>
		</div>															
<!-- Teacher Seniority Reports -->	
		<esd:SecurityAccessRequired permissions="TEACHER-SENIORITY-REPORTS">				
		<div class="menuIconImage">
				<a href="StaffRoom/teacherseniorityreports.jsp">
				<img src="StaffRoom/includes/img/teacherseniorityreports-off.png" class="img-swap menuImage" border=0 title="Teacher Seniority Reports">
				</a>
		</div>	
		</esd:SecurityAccessRequired>	
<!-- OHS-UPDATES - ALL STAFF -->	
				
		<div class="menuIconImage">
			<a href="//sites.google.com/nlesd.ca/ohsorientation/home" target="_blank">
			<img src="includes/img/menu/ohs-off.png" class="img-swap menuImage" border=0 title="OHS Information Shared with Schools and District Staff">
			</a>
		</div>	
		
<!-- Admin Planner --> 
	<esd:SecurityAccessRequired roles="ANNUAL-PLANNER-VIEW,WEB DESIGNER,WEB OPERATOR,PRINCIPAL,VICE PRINCIPAL,DIRECTOR,ASSISTANT DIRECTORS,SENIOR EDUCATION OFFICIER,SENIOR ADMINISTRATIVE OFFICER,PROGRAM SPECIALISTS,ADMINISTRATIVE ASSISTANT">

		<div class="menuIconImage">
			<a href="https://sites.google.com/nlesd.ca/theannualplanner/home" target="_blank">
			<img src="includes/img/menu/aplanner-off.png" class="img-swap menuImage" border=0 title="Annual Planner for School Administrators.">
			</a>
		</div>
	</esd:SecurityAccessRequired>		
		
<!-- Weather and Road Conditions -->					
		<div class="menuIconImage">
			<a href="StaffRoom/weather.jsp">
			<img src="StaffRoom/includes/img/weather-off.png" class="img-swap menuImage" border=0 title="Weather and Road Conditions">
			</a>
		</div>	
	
	
		
<!-- TRAINING - ALL STAFF -->			

		<div class="menuIconImage">
			<a href="Training/">
			<img src="includes/img/menu/training-off.png" class="img-swap menuImage" border=0 title="Training for District Staff">
			</a>
		</div>	
		
<!-- COVID 19-UPDATES - ALL STAFF -->					
		<div class="menuIconImage">
			<a href="covid19/index.jsp">
			<img src="includes/img/menu/covid19-off.png" class="img-swap menuImage" border=0 title="Covid-19 Information Shared with Schools and District Staff">
			</a>
		</div>		
		
<esd:SecurityAccessRequired permissions="PPGP-VIEW">	

		<div class="menuIconImage">
			<a href="https://sites.google.com/nlesd.ca/plseries-sept2020/home" target="_blank">
			<img src="includes/img/menu/pls-off.png" class="img-swap menuImage" border=0 title="Professional Learning Series">
			</a>
		</div>

		<div class="menuIconImage">
			<a href="https://sites.google.com/nlesd.ca/plseries-sept2020/professional-learning-journey?authuser=0" target="_blank">
			<img src="includes/img/menu/learningplan-off.png" class="img-swap menuImage" border=0 title="Professional Learning Journey">
			</a>
		</div>

</esd:SecurityAccessRequired>		
		
	

<hr>
<div class="siteHeaderGreen">Applications</div>	
Listed applications that you have permission to access and use.<br/><br/>
<!-- Teacher / Secretary Profile System -->
		<esd:SecurityAccessRequired permissions="PERSONNEL-PROFILE-TEACHER-VIEW,PERSONNEL-PROFILE-SECRETARY-VIEW">
			<div class="menuIconImage">
				<a href="Profile/Teacher/">
				<img src="includes/img/menu/userprofile-off.png" class="img-swap menuImage" border=0 title="User Profile Manager allows a Teacher/Support Staff employee to modify their current name and school assignment."/>
				</a>
			</div>
		</esd:SecurityAccessRequired>
			
<!-- Cayenta Connect May need to create a permission here and add it to people.-->
			
			<div class="menuIconImage">
				<a href="https://connectfinance.nlesd.ca/" target="_blank">
				<img src="StaffRoom/includes/img/cayentaconnect-off.png" class="img-swap menuImage" border=0 title="Cayenta Connect Finance.">
				</a>
			</div>				
		
				
<!-- Cayenta EServe -->
					
						<div class="menuIconImage">
							<a href="https://sdsweb.nlesd.ca/sds/eserve/login.xsp" target="_blank"> <img
								src="StaffRoom/includes/img/cayentaeserve-off.png"
								class="img-swap menuImage" border=0
								title="Cayenta EServe TimeSheets">
							</a>
						</div>

<!-- PowerSchool SmartFind -->
					
						<div class="menuIconImage">
							<a href="https://nlesd.eschoolsolutions.com/logOnInitAction.do" target="_blank"> <img
								src="StaffRoom/includes/img/pssf-off.png"
								class="img-swap menuImage" border=0
								title="SmartFind">
							</a>
						</div>

<!-- PowerSchool Admin -->
					
						<div class="menuIconImage">
							<a href="https://nlsis.powerschool.com/admin/pw.html" target="_blank"> <img
								src="StaffRoom/includes/img/psadmin-off.png"
								class="img-swap menuImage" border=0
								title="PowerSchool Admin">
							</a>
						</div>

<!-- PowerSchool Teacher -->
					
						<div class="menuIconImage">
							<a href="https://nlsis.powerschool.com/teachers/pw.html" target="_blank"> <img
								src="StaffRoom/includes/img/psteacher-off.png"
								class="img-swap menuImage" border=0
								title="PowerSchool Teacher">
							</a>
						</div>
						





						
											

<!-- PS Counts -->					
					<esd:SecurityAccessRequired roles="SENIOR EDUCATION OFFICIER,ASSISTANT DIRECTORS,ADMINISTRATOR,ADHR,DIRECTOR">
						<div class="menuIconImage">                              
							<a href="PSCounts/viewPSCounts.html">
								<img src="includes/img/menu/psdata-off.png" class="img-swap menuImage" border=0 title="School PS Allocation Data">
							</a>                                                         
						</div>
					</esd:SecurityAccessRequired>					
					
		





					<!-- Personnel Package 2019-->
					<esd:SecurityAccessRequired
						permissions="PERSONNEL-IT-VIEW-SCHOOL-EMPLOYEES,PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW,RTH-NEW-REQUEST,PERSONNEL-RTH-VIEW-APPROVALS,PERSONNEL-VIEW-SUBMITTED-REFERENCES,PERSONNEL-SEARCH-APPLICANTS-NON">
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
					
			
			<!-- ICF Registration -->
			<esd:SecurityAccessRequired permissions="ICF-REGISTRATION-ADMIN-VIEW">
						<div class="menuIconImage">                              
							<a href="schools/registration/icfreg/admin/index.html">
								<img src="includes/img/menu/icfa-off.png" class="img-swap menuImage" border=0 title="ICF REGISTRATION">
							</a>                                                         
						</div>
					</esd:SecurityAccessRequired>
					
					
				<esd:SecurityAccessRequired permissions="ICF-REGISTRATION-SCHOOL-VIEW">
						<div class="menuIconImage">                              
							<a href="schools/registration/icfreg/admin/schoolindex.html">
								<img src="includes/img/menu/icfs-on.png" class="img-swap menuImage" border=0 title="ICF REGISTRATION SCHOOL">
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
			<a href="PPGP/ppgpPolicy.html">
			<img src="includes/img/menu/learningplana-off.png" class="img-swap menuImage" border=0 title="Professional Learning Plans Archive">
			</a>
		</div>
</esd:SecurityAccessRequired>




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

		




					

					<!-- Policy Feedback 
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

					<!--  Student Conduct Reporting 
					<esd:SecurityAccessRequired permissions="BULLYING-ANALYSIS-SCHOOL-VIEW,BULLYING-ANALYSIS-ADMIN-VIEW">
						<div class="menuIconImage">
							<a href="scrs/">
							<img src="includes/img/menu/studentconduct-off.png" class="img-swap menuImage" border=0 title="Student Conduct Reporting &amp; Analysis System.">
							</a>
						</div>
					</esd:SecurityAccessRequired>
					

					<!-- Trustee Document Repository 
					<esd:SecurityAccessRequired permissions="TSDOC-VIEW">
						<div class="menuIconImage">
							<a href="tsdoc/"> <img
								src="includes/img/menu/securetrustee-off.png"
								class="img-swap menuImage" border=0
								title="Repository of communication documents and notes for the District's Board of Trustees and it various committees.">
							</a>
						</div>
					</esd:SecurityAccessRequired>
					-->

				<!-- Student Travel Request System -->
					<esd:SecurityAccessRequired permissions="STUDENT-TRAVEL-ADMIN-VIEW,STUDENT-TRAVEL-PRINCIPAL-VIEW">
						<div class="menuIconImage">
							<a href="student/travel/">
							<img src="includes/img/menu/studenttravel-off.png" class="img-swap menuImage" border=0 title="District out of province student travel request and tracking system.">
							</a>
						</div>
					</esd:SecurityAccessRequired>

				
					<!-- Travel Claim System  -->
					<esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-VIEW">
						<div class="menuIconImage">
							<a href="Travel/viewTravelClaimSystem.html">
							<img src="includes/img/menu/travelclaim-off.png" class="img-swap menuImage" border=0 title="The Travel Claim Systems allow you to submit travel claims and track the status of your claims as they are being processed.">
							</a>
						</div>
					</esd:SecurityAccessRequired>


					<!-- Maintenance Request System -->
					<esd:SecurityAccessRequired permissions="MAINTENANCE-SCHOOL-VIEW,MAINTENANCE-ADMIN-VIEW,MAINTENANCE-WORKORDERS-VIEW">
						<div class="menuIconImage">
							<a href="https://siems.nlesd.ca">
							<img src="includes/img/menu/ems-off.png" class="img-swap menuImage" border=0 title="System Inspection Enterprise Management System allows you to submit and track school maintenance requests.">
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
				<a href="BCS/index.html">
				<img src="includes/img/menu/bussystem-off.png" class="img-swap menuImage" title="Bussing Contractor System" border=0>
				</a>
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

<!-- Canva -->
					
			<div class="menuIconImage">
				<a href="https://www.canva.com/" target="_blank">
					<img src="StaffRoom/includes/img/canva-off.png" class="img-swap menuImage" border=0 title="Canva Design">
				</a>
			</div>
			
<!-- District School Map -->
		
			<div class="menuIconImage">
				<a href="https://www.google.com/maps/d/u/0/viewer?mid=1R5VvnRw7O2HyleZerwnEih2pfJngXxA&ll=51.25470996626324%2C-53.88959315085279&z=5" target="_blank"> 
					<img src="StaffRoom/includes/img/smap-off.png" class="img-swap menuImage" border=0 title="District Schools Location Map">
				</a>
			</div>

<div class="menuIconImage">
			<a href="https://portal.office.com/" target="_blank">
			<img src="StaffRoom/includes/img/msoffice-off.png" class="img-swap menuImage" border=0 title="Microsoft Office 365">
			</a>
		</div>
		
		<div class="menuIconImage">
			<a href="https://teams.microsoft.com" target="_blank">
			<img src="StaffRoom/includes/img/msteams-off.png" class="img-swap menuImage" border=0 title="Microsoft Teams">
			</a>
		</div>


		<div class="menuIconImage">
				<a href="https://mail.google.com/" target="_blank">
				<img src="StaffRoom/includes/img/gmail-off.png" class="img-swap menuImage" border=0 title="Google Mail">
				</a>
		</div>	
		<div class="menuIconImage">
				<a href="https://www.google.ca/" target="_blank">
				<img src="StaffRoom/includes/img/gsearch-off.png" class="img-swap menuImage" border=0 title="Google Search">
				</a>
		</div>	
		
		<div class="menuIconImage">
				<a href="https://sites.google.com" target="_blank">
				<img src="StaffRoom/includes/img/gsites-off.png" class="img-swap menuImage" border=0 title="Google Sites">
				</a>
		</div>	

		<div class="menuIconImage">
				<a href="https://drive.google.com" target="_blank">
				<img src="StaffRoom/includes/img/gdrive-off.png" class="img-swap menuImage" border=0 title="Google Drive">
				</a>
		</div>	
		
		<div class="menuIconImage">
			<a href="https://meet.google.com" target="_blank">
			<img src="StaffRoom/includes/img/gmeet-off.png" class="img-swap menuImage" border=0 title="Google Meet">
			</a>
		</div>
		
		<div class="menuIconImage">
			<a href="https://calendar.google.com" target="_blank">
			<img src="StaffRoom/includes/img/gcalendar-off.png" class="img-swap menuImage" border=0 title="Google Calendar">
			</a>
		</div>		



<hr>

<div class="siteHeaderGreen">District Social Media Accounts</div>
Our current public social media accounts.<br/><br/>

<!-- District YouTube Account -->
		
			<div class="menuIconImage">
				<a href="https://www.youtube.com/channel/UCIT127dtdYdGLnVFYv3c9uQ" target="_blank">
					<img src="StaffRoom/includes/img/dyt-off.png" class="img-swap menuImage" border=0 title="District YouTube Account">
				</a>
			</div>

<!-- District Twitter Account -->
		
			<div class="menuIconImage">
				<a href="https://twitter.com/NLESDCA" target="_blank"> 
				<img src="StaffRoom/includes/img/twitter-off.png" class="img-swap menuImage" border=0 title="District Twitter Account">
				</a>
			</div>

<!-- District Instagram Account -->
		
			<div class="menuIconImage">
				<a href="https://www.instagram.com/nlesd/" target="_blank">
					<img src="StaffRoom/includes/img/insta-off.png" class="img-swap menuImage" border=0 title="District Instagram Account">
				</a>
			</div>

<!-- District Facebook Account -->
		
			<div class="menuIconImage">
				<a href="https://www.facebook.com/NLESDCA" target="_blank"> 
				<img src="StaffRoom/includes/img/fb-off.png" class="img-swap menuImage" border=0 title="District Facebook Account">
				</a>
			</div>


<div class="adminApps">			
<hr>

<div class="siteHeaderGreen">Website Administration Applications (<span id="adminApps"></span>)</div>
Special applications for updating and posting information, depending on your level of access.<br/><br/>
<% int cnt=0; %>
<!-- New Member Adminsitration -->
		<esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW">
			<div class="menuIconImage">
				<a href="Administration/index.jsp">
				<img src="includes/img/menu/membersadmin-off.png" class="img-swap menuImage" border=0 title="Member Admin is an administrative interface for assigning permissions to members based on job role and application access requirements.">
				</a>
			</div>
			<%cnt++; %>
		</esd:SecurityAccessRequired>
					
<!-- Office Staff Update -->
		<esd:SecurityAccessRequired
			roles="ADMINISTRATOR,OFFICE-STAFF-WEBUPDATE-ALL,OFFICE-STAFF-WEBUPDATE-AVALON,OFFICE-STAFF-WEBUPDATE-CENTRAL,OFFICE-STAFF-WEBUPDATE-WESTERN,OFFICE-STAFF-WEBUPDATE-LABRADOR">
			<div class="menuIconImage">
				<a href="StaffDirectory/staff_directory.jsp">
					<img src="includes/img/menu/staffupdate-off.png" class="img-swap menuImage" title="Office Staff Update System" border=0>
				</a>
			</div>
			<%cnt++; %>
		</esd:SecurityAccessRequired>
		
		<!-- Tender Posting System -->
		<esd:SecurityAccessRequired permissions="TENDER-ADMIN,TENDER-EDIT,TENDER-VIEW">
			<div class="menuIconImage">
				<a href="Tenders/viewTenders.html">
				<img src="includes/img/menu/tenders-off.png" class="img-swap menuImage" title="Tender Posting System" border=0></a>
			</div>
			<%cnt++; %>
		</esd:SecurityAccessRequired>
			

<!-- Admin School Profile Update -->
		<esd:SecurityAccessRequired roles="ADMINISTRATOR,PRINCIPAL,VICE PRINCIPAL,SCHOOL SECRETARY">
			<div class="menuIconImage">
				<a href="SchoolDirectory/school_profile.jsp">
					<img src="includes/img/menu/sps-off.png" class="img-swap menuImage" title="School Profile Update System" border=0>
				</a>
			</div>
			<%cnt++; %>
		</esd:SecurityAccessRequired>				

<!-- Bus Route Update -->
		<esd:SecurityAccessRequired roles="ADMINISTRATOR,BUSROUTE-POST">
			<div class="menuIconImage">
				<a href="BusRoutes/school_directory_bus_routes.jsp">
					<img src="includes/img/menu/busroute-off.png" class="img-swap menuImage" title="Bus Route Update System" border=0>
				</a>
			</div>
			<%cnt++; %>
		</esd:SecurityAccessRequired>
	
	<!-- School Review System--> 
		<esd:SecurityAccessRequired roles="ADMINISTRATOR,SCHOOL-REVIEW-ADMIN">
			<div class="menuIconImage">                              	
				<a href="SchoolReviews/viewSchoolReviews.html">
				<img src="includes/img/menu/schoolreview-off.png" class="img-swap menuImage" border=0 title="SCHOOL REVIEW PROCESS ADMIN">
				</a>                                                         
			</div>
			<%cnt++; %>
		</esd:SecurityAccessRequired>

					
	<!-- Weather Central -->
		<esd:SecurityAccessRequired permissions="WEATHERCENTRAL-PRINCIPAL-ADMINVIEW">
			<div class="menuIconImage">
				<a href="SchoolStatus/viewWeatherCentralAdmin.html">
				<img src="includes/img/menu/schoolstatus-off.png" class="img-swap menuImage" border=0 title="The School Status application allows a principal to set the closure status for schools in their school system.">
				</a>
			</div>
			<%cnt++; %>
		</esd:SecurityAccessRequired>


		<!-- Weather Central Admin  -->
		<esd:SecurityAccessRequired roles="ADMINISTRATOR">
			<div class="menuIconImage">
				<a href="SchoolStatus/regionalized_school_admin.jsp">
				<img src="includes/img/menu/schoolstatusadmin-off.png" class="img-swap menuImage" border=0 title="The Weather Central Global Admin application allows an administrator to set the weather closure status for any school in the district.">
				</a>
			</div>
			<%cnt++; %>
		</esd:SecurityAccessRequired>			

		<!-- Web Maintenance -->
		<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-VIEW,WEBMAINTENANCE-BUSROUTES">
			<div class="menuIconImage">
				<a href="WebUpdateSystem/index.jsp">
				<img src="includes/img/menu/webupdate-off.png" class="img-swap menuImage" title="Website Update Posting System" border=0>
				</a>
			</div>
			<%cnt++; %>
		</esd:SecurityAccessRequired>					
					
</div>

<div style="display:none;">
<hr>
<div class="siteHeaderGreen">Google Applications</div>					
Quick access to some of the Google applications you may use. These links will open in a new tab or browser window.<br/><br/>			
<!-- Google Apps -->
					
		
		<div class="menuIconImage">
				<a href="https://myaccount.google.com" target="_blank">
				<img src="StaffRoom/includes/img/gaccount-off.png" class="img-swap menuImage" border=0 title="Your Google Account">
				</a>
		</div>	
		<div class="menuIconImage">
				<a href="https://mail.google.com/" target="_blank">
				<img src="StaffRoom/includes/img/gmail-off.png" class="img-swap menuImage" border=0 title="Google Mail">
				</a>
		</div>	
		<div class="menuIconImage">
				<a href="https://www.google.ca/" target="_blank">
				<img src="StaffRoom/includes/img/gsearch-off.png" class="img-swap menuImage" border=0 title="Google Search">
				</a>
		</div>	
		
		<div class="menuIconImage">
				<a href="https://sites.google.com" target="_blank">
				<img src="StaffRoom/includes/img/gsites-off.png" class="img-swap menuImage" border=0 title="Google Sites">
				</a>
		</div>	

		<div class="menuIconImage">
				<a href="https://classroom.google.com" target="_blank">
				<img src="StaffRoom/includes/img/gclass-off.png" class="img-swap menuImage" border=0 title="Google Classroom">
				</a>
		</div>	
					
		<div class="menuIconImage">
				<a href="https://drive.google.com" target="_blank">
				<img src="StaffRoom/includes/img/gdrive-off.png" class="img-swap menuImage" border=0 title="Google Drive">
				</a>
		</div>	

		<div class="menuIconImage">
				<a href="https://docs.google.com" target="_blank">
				<img src="StaffRoom/includes/img/gdocs-off.png"	class="img-swap menuImage" border=0 title="Google Docs">
				</a>
		</div>	

		<div class="menuIconImage">
			<a href="https://sheets.google.com" target="_blank">
			<img src="StaffRoom/includes/img/gsheets-off.png" class="img-swap menuImage" border=0 title="Google Sheets">
			</a>
		</div>	
		
		<div class="menuIconImage">
			<a href="https://slides.google.com" target="_blank">
			<img src="StaffRoom/includes/img/gslides-off.png" class="img-swap menuImage" border=0 title="Google Slides">
			</a>
		</div>	

		<div class="menuIconImage">
			<a href="https://forms.google.com" target="_blank">
			<img src="StaffRoom/includes/img/gforms-off.png" class="img-swap menuImage" border=0 title="Google Forms">
			</a>
		</div>	

		<div class="menuIconImage">
			<a href="https://meet.google.com" target="_blank">
			<img src="StaffRoom/includes/img/gmeet-off.png" class="img-swap menuImage" border=0 title="Google Meet">
			</a>
		</div>
		
		<div class="menuIconImage">
			<a href="https://calendar.google.com" target="_blank">
			<img src="StaffRoom/includes/img/gcalendar-off.png" class="img-swap menuImage" border=0 title="Google Calendar">
			</a>
		</div>		
		
		<div class="menuIconImage">
			<a href="https://groups.google.com" target="_blank">
			<img src="StaffRoom/includes/img/ggroups-off.png" class="img-swap menuImage" border=0 title="Google Groups">
			</a>
		</div>	
		
		<div class="menuIconImage">
			<a href="https://takeout.google.com/transfer" target="_blank">
			<img src="StaffRoom/includes/img/gtakeout-off.png" class="img-swap menuImage" border=0 title="Google Takeout">
			</a>
		</div>	
		
<!-- Google Maps -->
					
		<div class="menuIconImage">
			<a href="https://www.google.com/maps?authuser=1" target="_blank"> 
			<img src="StaffRoom/includes/img/gmaps-off.png"	class="img-swap menuImage" border=0 title="Google Maps">
			</a>
		</div>

<!-- Google Earth -->
	
		<div class="menuIconImage">
			<a href="https://earth.google.com/web/?authuser=1" target="_blank"> 
			<img src="StaffRoom/includes/img/gearth-off.png" class="img-swap menuImage" border=0 title="Google Earth">
			</a>
		</div>
		
<!-- YouTube -->
	
		<div class="menuIconImage">
			<a href="https://www.youtube.com/?authuser=1" target="_blank"> 
			<img src="StaffRoom/includes/img/yt-off.png" class="img-swap menuImage" border=0 title="YouTube">
			</a>
		</div>
	
						
</div>

<div style="display:none;">
<!-- Microsoft Office-->
<hr>
<div class="siteHeaderGreen">Microsoft Applications</div>					
Quick access to some of the Microsoft Office Web applications. Recommend if you use office, you download the desktop application. These links will open in a new tab or browser window.<br/><br/>			
					
		<div class="menuIconImage">
			<a href="https://portal.office.com/" target="_blank">
			<img src="StaffRoom/includes/img/msoffice-off.png" class="img-swap menuImage" border=0 title="Microsoft Office 365">
			</a>
		</div>
		<div class="menuIconImage">
			<a href="https://teams.microsoft.com" target="_blank">
			<img src="StaffRoom/includes/img/msteams-off.png" class="img-swap menuImage" border=0 title="Microsoft Teams">
			</a>
		</div>
		<div class="menuIconImage">
			<a href="https://nf-my.sharepoint.com/" target="_blank">
			<img src="StaffRoom/includes/img/msonedrive-off.png" class="img-swap menuImage" border=0 title="Microsoft OneDrive">
			</a>
		</div>
		<div class="menuIconImage">
			<a href="https://outlook.office.com/mail/" target="_blank">
			<img src="StaffRoom/includes/img/msoutlook-off.png" class="img-swap menuImage" border=0 title="Microsoft Outlook">
			</a>
		</div>
		<div class="menuIconImage">
			<a href="https://www.office.com/launch/word?auth=2" target="_blank">
			<img src="StaffRoom/includes/img/msword-off.png" class="img-swap menuImage" border=0 title="Microsoft Word">
			</a>
		</div>
		<div class="menuIconImage">
			<a href="https://www.office.com/launch/excel?auth=2" target="_blank">
			<img src="StaffRoom/includes/img/msexcel-off.png" class="img-swap menuImage" border=0 title="Microsoft Excel">
			</a>
		</div>
		<div class="menuIconImage">
			<a href="https://www.office.com/launch/powerpoint?auth=2" target="_blank">
			<img src="StaffRoom/includes/img/msppoint-off.png" class="img-swap menuImage" border=0 title="Microsoft PowerPoint">
			</a>
		</div>
		<div class="menuIconImage">
			<a href="https://www.office.com/launch/forms?auth=2" target="_blank">
			<img src="StaffRoom/includes/img/msforms-off.png" class="img-swap menuImage" border=0 title="Microsoft Forms">
			</a>
		</div>
		<div class="menuIconImage">
			<a href="https://nf.sharepoint.com/" target="_blank">
			<img src="StaffRoom/includes/img/mssharepoint-off.png" class="img-swap menuImage" border=0 title="Microsoft Sharepoint">
			</a>
		</div>
		<div class="menuIconImage">
			<a href="https://www.office.com/launch/visio?auth=2" target="_blank">
			<img src="StaffRoom/includes/img/msvisio-off.png" class="img-swap menuImage" border=0 title="Microsoft Visio">
			</a>
		</div>
		<div class="menuIconImage">
			<a href="https://www.office.com/launch/onenote?auth=2" target="_blank">
			<img src="StaffRoom/includes/img/msonenote-off.png" class="img-swap menuImage" border=0 title="Microsoft OneNote">
			</a>
		</div>
</div>
<hr>

<div class="siteHeaderGreen">Help Desks</div>						
Here are listed access to a variety of application and district level HelpDesks.<br/><br/>
<!-- Staff HelpCentre-->
		<div class="menuIconImage">
			<a href="StaffRoom/helpcentre.jsp">
			<img src="StaffRoom/includes/img/shc-off.png" class="img-swap menuImage" border=0 title="Staff HelpCentre">
			</a>
		</div>		
							
<!-- HelpDesk-->
	
		<div class="menuIconImage">
			<a href="https://servicedesk.nlesd.ca" target="_blank">
			<img src="StaffRoom/includes/img/servicehd-off.png" class="img-swap menuImage" border=0 title="HelpDesk">
			</a>
		</div>
			
<!-- IT Assist Portal-->
	
		<div class="menuIconImage">
			<a href="http://itassist.nlesd.ca/" target="_blank">
			<img src="StaffRoom/includes/img/itassist-off.png" class="img-swap menuImage" border=0 title="IT Assist Portal for Administrators">
			</a>
		</div>							

<!-- Payroll HelpDesk-->
	
		<div class="menuIconImage">
			<a href="https://forms.gle/nm8xb5iLthGxg4UF8" target="_blank">
			<img src="StaffRoom/includes/img/payhelpdesk-off.png" class="img-swap menuImage" border=0 title="Payroll HelpDesk"/>
			</a>
		</div>
		
<!-- Travel HelpDesk-->
	
		<div class="menuIconImage">
			<a href="https://docs.google.com/forms/d/e/1FAIpQLSfWm5FdaMXKCAAdSZZM_gTQGgkU7zkiUkZPupMWM9Pi-96NOw/viewform" target="_blank">
			<img src="StaffRoom/includes/img/travelhelp-off.png" class="img-swap menuImage" border=0 title="Travel HelpDesk" />
			</a>
		</div>		
						
<!-- Finance HelpDesk-->
	
		<div class="menuIconImage">
			<a href="https://docs.google.com/forms/d/e/1FAIpQLSds_59KsxMEzt4fiIqgcyMXDVfhhZ18eyDonU-ff-CCqq16cQ/viewform?usp=sf_link" target="_blank">
			<img src="StaffRoom/includes/img/financehd-off.png" class="img-swap menuImage" border=0 title="Finance HelpDesk"/>
			</a>
		</div>		
		
<!-- MyHRP HelpDesk-->
	
		<div class="menuIconImage">
			<a href="https://docs.google.com/forms/d/e/1FAIpQLSf2fwNSbvj0wV4laeKQagx3LDJwsOWqO1H4XRcjqKIiE80NUA/viewform" target="_blank">
			<img src="StaffRoom/includes/img/hrhelpdesk-off.png" class="img-swap menuImage" border=0 title="MyHRP HelpDesk"/>
			</a>
		</div>			
														
<!-- Staff HelpCentre-->
		<div class="menuIconImage">
			<a href="https://forms.gle/rpYgeZfm81Wt5c138" target="_blank">
			<img src="StaffRoom/includes/img/srhelp-off.png" class="img-swap menuImage" border=0 title="StaffRoom HelpCentre"/>
			</a>
		</div>	
		
					
<%
if(cnt > 0) {
%>
<script>
$(".adminApps").css("display","block");
$("#adminApps").text("<%=cnt%>");
</script>	
<%} else { %>
<script>
$(".adminApps").css("display","none");
</script>		
<%} %>

				
</div>
</div>
</div>
			
</body>
</html>