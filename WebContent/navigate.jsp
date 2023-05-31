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



<title>NLESD StaffRoom</title>





<script>
$(document).ready(function () {	
	
	jQuery(function() {
		$(".img-swap").hover(function() {
			this.src = this.src.replace("-off", "-on");
		}, function() {
			this.src = this.src.replace("-on", "-off");
		});
	});
});		
		


</script>
<style>
.appcnt {font-size:10;color:Silver;}
</style>


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
<div style="font-size:14px;" id="welcomeBlock">
<c:choose>
<c:when test="${not empty ProfileImg}">
<a href="https://myaccount.google.com" target="_blank"><img src="${ProfileImg}" border=0 title="Your Google Profile Picture" onerror="this.onerror=null; this.src='/MemberServices/StaffRoom/includes/img/nltopleftlogo.png'" style="display: block;-webkit-user-select: none;margin: auto;max-height:150px;padding-right:10px;float:right;"/></a>
</c:when>
<c:otherwise>
  <img src="/MemberServices/StaffRoom/includes/img/nltopleftlogo.png" onerror="this.onerror=null; this.src='/MemberServices/StaffRoom/includes/img/nltopleftlogo.png'" style="max-height:150px;padding-right:10px;float:right;"/>
</c:otherwise>
</c:choose>

Welcome <span style="text-transform: capitalize;font-weight:bold;">
<%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></span> to your NLESD StaffRoom Portal. (Formally MemberServices).<br/><br/>

In this StaffRoom, depending on your District classification, you have access to a variety of staff web applications, resources, and support services via the icons below under various sections. 

Most of the links and pages found on the old web site under the old StaffRoom section can be found below and are still accessible via icons below.

Some applications/links may open in a new tab and/or require a secondary login depending on their access requirements.
 
<i>Please note that this is a temporary access portal while we update our back-end systems and applications so there will be ongoing changes.</i>


</div>
<br/>
<div class="favoriteBlock" style="display:none;">
<hr>
<a href="#/" id="clearFavorites" class="btn btn-sm btn-danger favBtn" style="float:right;">Clear List</a>
<div class="siteHeaderGreen">Your Favorites</div>
Your most used applications will display here after a few uses. You can clear these favorites from the list if they fill up or if you like to reset. 
<br/><br/>
<div id="myFavorites"></div>
</div>

<div style="clear:both;"></div>
<div id="links">
<esd:SecurityAccessRequired	permissions="MEMBERADMIN-VIEW">
<hr>
<div class="siteHeaderGreen">Staff/Member Administration Applications</div>
Special applications for administering staff, permissions, roles, and categories.<br/><br/>
</esd:SecurityAccessRequired>

<div id="<%=usr.getPersonnel().getPersonnelID()%>app1">
<esd:SecurityAccessRequired	permissions="MEMBERADMIN-VIEW">
		<div class="menuIconImage">
		<a href="/MemberServices/Administration/PersonnelAdmin/personnel_admin_view.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app1');loadingData();">
		<img src="StaffRoom/includes/img/staffadmin-off.png" class="img-swap menuImage" border=0 title="Staff/Member Account Information"/>
		</a>
		</div>
		
</esd:SecurityAccessRequired>
</div>

<div id="<%=usr.getPersonnel().getPersonnelID()%>app2">		
<esd:SecurityAccessRequired	permissions="MEMBERADMIN-VIEW">		
		<div class="menuIconImage">
		<a href="/MemberServices/Administration/SchoolAdmin/school_admin_view.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app2');loadingData();">
		<img src="StaffRoom/includes/img/schooladminassignments-off.png" class="img-swap menuImage" border=0 title="School Administration Assignment"/>
		</a>
		</div>		
		
</esd:SecurityAccessRequired>	
</div>

<div id="<%=usr.getPersonnel().getPersonnelID()%>app3">		
<esd:SecurityAccessRequired	permissions="MEMBERADMIN-VIEW">		
		<div class="menuIconImage">
		<a href="/MemberServices/Administration/SchoolAdmin/schoolfamilyadmin.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app3');loadingData();">
		<img src="StaffRoom/includes/img/fos-off.png" class="img-swap menuImage" border=0 title="School Family (DOS) Assignment"/>
		</a>
		
		</div>
</esd:SecurityAccessRequired>
</div>

<div id="<%=usr.getPersonnel().getPersonnelID()%>app4">		
<esd:SecurityAccessRequired	permissions="MEMBERADMIN-VIEW">			
		<div class="menuIconImage">
		<a href="/MemberServices/Administration/SchoolAdmin/schoolsystemadmin.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app4');loadingData();">
		<img src="StaffRoom/includes/img/schoolsystemassignment-off.png" class="img-swap menuImage" border=0 title="School System Assignment"/>
		</a>
		</div>
</esd:SecurityAccessRequired>
</div>		

<div id="<%=usr.getPersonnel().getPersonnelID()%>app5">		
<esd:SecurityAccessRequired	permissions="MEMBERADMIN-VIEW">			
		<div class="menuIconImage">
		<a href="/MemberServices/Administration/StaffCategories/viewCategories.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app5');loadingData();">
		<img src="StaffRoom/includes/img/staffcategories-off.png" class="img-swap menuImage" border=0 title="Staff Job Categories"/>
		</a>
		</div>
</esd:SecurityAccessRequired>
</div>		

<div id="<%=usr.getPersonnel().getPersonnelID()%>app6">		
<esd:SecurityAccessRequired	permissions="MEMBERADMIN-VIEW">			
		<div class="menuIconImage">
		<a href="/MemberServices/Administration/RolesPermissions/viewroles.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app6');loadingData();">
		<img src="StaffRoom/includes/img/securityroles-off.png" class="img-swap menuImage" border=0 title="Security Roles"/>
		</a>
		</div>
</esd:SecurityAccessRequired>
</div>		

<div id="<%=usr.getPersonnel().getPersonnelID()%>app7">		
<esd:SecurityAccessRequired	permissions="MEMBERADMIN-VIEW">			
		<div class="menuIconImage">
		<a href="/MemberServices/Administration/RolesPermissions/viewpermissions.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app7');loadingData();">
		<img src="StaffRoom/includes/img/securitypermissions-off.png" class="img-swap menuImage" border=0 title="Security Permissions"/>
		</a>
		</div>
</esd:SecurityAccessRequired>
</div>		

<div id="<%=usr.getPersonnel().getPersonnelID()%>app8">		
<esd:SecurityAccessRequired	permissions="MEMBERADMIN-VIEW">			
		<div class="menuIconImage">
		<a href="/MemberServices/Administration/viewNextLoginApp.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app8');loadingData();">
		<img src="StaffRoom/includes/img/startup-off.png" class="img-swap menuImage" border=0 title="Group Startup App"/>
		</a>
		</div>
</esd:SecurityAccessRequired>
</div>		

<div id="<%=usr.getPersonnel().getPersonnelID()%>app9">		
<esd:SecurityAccessRequired	permissions="MEMBERADMIN-VIEW">			
		<div class="menuIconImage">
		<a href="/MemberServices/Administration/SchoolStatus/closurestatusadmin.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app9');loadingData();">
		<img src="StaffRoom/includes/img/closurecodes-off.png" class="img-swap menuImage" border=0 title="School Status Closure Codes"/>
		</a>
		</div>
</esd:SecurityAccessRequired>
</div>		

<div id="<%=usr.getPersonnel().getPersonnelID()%>app10">		
<esd:SecurityAccessRequired	permissions="MEMBERADMIN-VIEW">			
		<div class="menuIconImage">
		<a href="/MemberServices/Administration/SchoolStatus/regionalized_school_admin.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app10');loadingData();">
		<img src="StaffRoom/includes/img/statusadmin-off.png" class="img-swap menuImage" border=0 title="School Closure Administration"/>
		</a>
		</div>
</esd:SecurityAccessRequired>
</div>

<div style="clear:both;"></div>				
<hr>				
<div class="siteHeaderGreen">General Information</div>	
Links under this section provide general information on a given topic and/or link to external websites providing information for all NLESD staff.	<br/><br/>			


<!-- Google Account -->		
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app11">		
		<div class="menuIconImage">
				<a href="https://myaccount.google.com" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app11');">
				<img src="StaffRoom/includes/img/gaccount-off.png" class="img-swap menuImage" border=0 title="Your Google Account">
				</a>
		</div>	
	</div>		
<!-- ATIPP information -->	
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app12">			
		<div class="menuIconImage">
			<a href="StaffRoom/atipp.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app12');loadingData();">
			<img src="StaffRoom/includes/img/atipp-off.png" class="img-swap menuImage" border=0 title="ATIPP Information for Staff.">
			</a>
		</div>
	</div>
<!-- Social media Awareness -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app13">
		<div class="menuIconImage">
			<a href="StaffRoom/socialmedia.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app13');loadingData();">
			<img src="StaffRoom/includes/img/sma-off.png" class="img-swap menuImage" border=0 title="Social Media Awareness for Staff.">
			</a>
		</div>
	</div>
<!-- Google GSuite -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app14">						
		<div class="menuIconImage">
				<a href="StaffRoom/googleapps.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app14');loadingData();">
				<img src="StaffRoom/includes/img/gsuite-off.png" class="img-swap menuImage" border=0 title="Google Workspace Information.">
				</a>
		</div>		
	</div>					
<!-- Payroll Resources -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app15">				
		<div class="menuIconImage">
				<a href="StaffRoom/payrollresources.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app15');loadingData();">
				<img src="StaffRoom/includes/img/payresources-off.png" class="img-swap menuImage" border=0 title="Payroll Resources for Staff.">
				</a>
		</div>							
	</div>
<!-- Staff Resources -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app16">
		<div class="menuIconImage">
				<a href="StaffRoom/staffresources.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app16');loadingData();">
				<img src="StaffRoom/includes/img/staffresources-off.png" class="img-swap menuImage" border=0 title="Staff Resources.">
				</a>
		</div>	
	</div>	
<!-- Unionized Staff Seniority Reports -->	
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app17">			
		<div class="menuIconImage">
				<a href="StaffRoom/staffseniorityreports.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app17');loadingData();">
				<img src="StaffRoom/includes/img/staffseniorityreports-off.png" class="img-swap menuImage" border=0 title="Unionized Staff Seniority Reports">
				</a>
		</div>	
	</div>			
																
			
<!-- OHS-UPDATES - ALL STAFF -->	
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app18">			
		<div class="menuIconImage">
			<a href="//sites.google.com/nlesd.ca/ohsorientation/home" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app18');">
			<img src="includes/img/menu/ohs-off.png" class="img-swap menuImage" border=0 title="OHS Information Shared with Schools and District Staff">
			</a>
		</div>	
	</div>	
<!-- Admin Planner --> 
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app19">
		<esd:SecurityAccessRequired roles="ANNUAL-PLANNER-VIEW,WEB DESIGNER,WEB OPERATOR,PRINCIPAL,VICE PRINCIPAL,DIRECTOR,ASSISTANT DIRECTORS,SENIOR EDUCATION OFFICIER,SENIOR ADMINISTRATIVE OFFICER,PROGRAM SPECIALISTS,ADMINISTRATIVE ASSISTANT">

		<div class="menuIconImage">
			<a href="https://sites.google.com/nlesd.ca/theannualplanner/home" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app19');">
			<img src="includes/img/menu/aplanner-off.png" class="img-swap menuImage" border=0 title="Annual Planner for School Administrators.">
			</a>
		</div>
		</esd:SecurityAccessRequired>		
	</div>
		
<!-- Weather and Road Conditions -->	
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app20">				
		<div class="menuIconImage">
			<a href="StaffRoom/weather.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app20');loadingData();">
			<img src="StaffRoom/includes/img/weather-off.png" class="img-swap menuImage" border=0 title="Weather and Road Conditions">
			</a>
		</div>	
	</div>
	
		
<!-- TRAINING - ALL STAFF -->			
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app21">	
		<div class="menuIconImage">
			<a href="Training/" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app21');loadingData();">
			<img src="includes/img/menu/training-off.png" class="img-swap menuImage" border=0 title="Training for District Staff">
			</a>
		</div>	
	</div>		
<!-- COVID 19-UPDATES - ALL STAFF -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app22">					
		<div class="menuIconImage">
			<a href="covid19/index.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app22');loadingData();">
			<img src="includes/img/menu/covid19-off.png" class="img-swap menuImage" border=0 title="Covid-19 Information Shared with Schools and District Staff">
			</a>
		</div>		
	</div>
<!-- Professional Learning Series -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app23">		
		<esd:SecurityAccessRequired permissions="PPGP-VIEW">	
		
				<div class="menuIconImage">
					<a href="https://sites.google.com/nlesd.ca/plseries-sept2020/home" target="_blank"  onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app23');">
					<img src="includes/img/menu/pls-off.png" class="img-swap menuImage" border=0 title="Professional Learning Series">
					</a>
				</div>
		</esd:SecurityAccessRequired>
	</div>
<!-- Professional Learning Series -->	
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app24">	
		<esd:SecurityAccessRequired permissions="PPGP-VIEW">
				<div class="menuIconImage">
					<a href="https://sites.google.com/nlesd.ca/plseries-sept2020/professional-learning-journey?authuser=0" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app24');">
					<img src="includes/img/menu/learningplan-off.png" class="img-swap menuImage" border=0 title="Professional Learning Journey">
					</a>
				</div>
		
		</esd:SecurityAccessRequired>		
	</div>	
	
<div style="clear:both;"></div>	
<hr>
<div class="siteHeaderGreen">Applications</div>	
Listed applications that you have permission to access and use.<br/><br/>

<!-- Teacher / Secretary Profile System -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app30">
		<esd:SecurityAccessRequired permissions="PERSONNEL-PROFILE-TEACHER-VIEW,PERSONNEL-PROFILE-SECRETARY-VIEW">
			<div class="menuIconImage">
				<a href="Profile/Teacher/" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app30');loadingData();">
				<img src="includes/img/menu/userprofile-off.png" class="img-swap menuImage" border=0 title="User Profile Manager allows a Teacher/Support Staff employee to modify their current name and school assignment."/>
				</a>
			</div>
		</esd:SecurityAccessRequired>
	</div>		
			
<!-- Cayenta Connect May need to create a permission here and add it to people.-->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app31">			
			<div class="menuIconImage">
				<a href="https://connectfinance.nlesd.ca/" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app31');">
				<img src="StaffRoom/includes/img/cayentaconnect-off.png" class="img-swap menuImage" border=0 title="Cayenta Connect Finance.">
				</a>
			</div>				
	</div>			
				
<!-- Cayenta EServe -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app32">					
			<div class="menuIconImage">
				<a href="https://sdsweb.nlesd.ca/sds/eserve/login.xsp" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app32');">
				 <img src="StaffRoom/includes/img/cayentaeserve-off.png" class="img-swap menuImage" border=0 title="Cayenta EServe TimeSheets">
				</a>
			</div>
	</div>	
	
<!-- PowerSchool SmartFind -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app33">					
			<div class="menuIconImage">
				<a href="https://nlesd.sfe.powerschool.com/homeAction.do" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app33');"> 
				<img src="StaffRoom/includes/img/pssf-off.png" class="img-swap menuImage" border=0 title="SmartFind">
				</a>
			</div>
	</div>	
	
<!-- PowerSchool Admin -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app34">					
			<div class="menuIconImage">
				<a href="https://nlsis.powerschool.com/admin/pw.html" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app34');"> 
				<img src="StaffRoom/includes/img/psadmin-off.png" class="img-swap menuImage" border=0 title="PowerSchool Admin">
				</a>
			</div>
	</div>	
	
<!-- PowerSchool Teacher -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app35">					
			<div class="menuIconImage">
				<a href="https://nlsis.powerschool.com/teachers/pw.html" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app35');"> 
				<img src="StaffRoom/includes/img/psteacher-off.png" class="img-swap menuImage" border=0 title="PowerSchool Teacher">
				</a>
			</div>
	</div>	

<!-- PowerSchool Sub Teacher -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app65">					
			<div class="menuIconImage">
				<a href="https://nlsis.powerschool.com/subs/pw.html" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app65');"> 
				<img src="StaffRoom/includes/img/pssub-off.png" class="img-swap menuImage" border=0 title="PowerSchool Substitute Teacher">
				</a>
			</div>
	</div>	
	
<!-- PS Counts -->	
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app36">				
			<esd:SecurityAccessRequired roles="SENIOR EDUCATION OFFICIER,ASSISTANT DIRECTORS,ADMINISTRATOR,ADHR,DIRECTOR">
				<div class="menuIconImage">                              
					<a href="PSCounts/viewPSCounts.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app36');loadingData();">
						<img src="includes/img/menu/psdata-off.png" class="img-swap menuImage" border=0 title="School PS Allocation Data">
					</a>                                                         
				</div>
			</esd:SecurityAccessRequired>					
	</div>		
	
<!-- MyHR Personnel Package-->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app37">
			<esd:SecurityAccessRequired
				permissions="PERSONNEL-IT-VIEW-SCHOOL-EMPLOYEES,PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW,RTH-NEW-REQUEST,PERSONNEL-RTH-VIEW-APPROVALS,PERSONNEL-VIEW-SUBMITTED-REFERENCES,PERSONNEL-SEARCH-APPLICANTS-NON">
				<div class="menuIconImage">
					<a href="Personnel/admin_index.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app37');loadingData();"> 
					<img src="includes/img/menu/myhrp-off.png" class="img-swap menuImage" border=0 title="Human Resources Profile System">
					</a>
				</div>
			</esd:SecurityAccessRequired> 
	</div>		
					
<!-- Kindergarten Registration -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app38">
			<esd:SecurityAccessRequired permissions="KINDERGARTEN-REGISTRATION-ADMIN-VIEW,KINDERGARTEN-REGISTRATION-SCHOOL-VIEW">
				<div class="menuIconImage">
					<a href="schools/registration/kindergarten/admin/index.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app38');loadingData();">
						<img src="StaffRoom/includes/img/kreg-off.png" class="img-swap menuImage" border=0 title="Kindergarten Student Registration - Administration.">
					</a>
				</div>
			</esd:SecurityAccessRequired>
	</div>					
		
<!-- ICF Registration ADMIN-->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app39">
			<esd:SecurityAccessRequired permissions="ICF-REGISTRATION-ADMIN-VIEW">
				<div class="menuIconImage">                              
					<a href="schools/registration/icfreg/admin/index.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app39');loadingData();">
						<img src="includes/img/menu/icfa-off.png" class="img-swap menuImage" border=0 title="ICF REGISTRATION">
					</a>                                                         
				</div>
			</esd:SecurityAccessRequired>
	</div>	
	
<!-- ICF Registration SCHOOL-->						
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app40">					
			<esd:SecurityAccessRequired permissions="ICF-REGISTRATION-SCHOOL-VIEW">
				<div class="menuIconImage">                              
					<a href="schools/registration/icfreg/admin/schoolindex.html"  onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app40');loadingData();">
						<img src="includes/img/menu/icfs-on.png" class="img-swap menuImage" border=0 title="ICF REGISTRATION SCHOOL">
					</a>                                                         
				</div>
			</esd:SecurityAccessRequired>
	</div>		
			
<!-- PD Calendar -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app41">
			<esd:SecurityAccessRequired permissions="CALENDAR-VIEW">
				<div class="menuIconImage">
					<a href="PDReg/viewDistrictCalendar.html"  onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app41');loadingData();"> 
					<img src="includes/img/menu/pdcal-off.png" class="img-swap menuImage" border=0 title="The District Calendar allows users to register for upcoming events around the district.">
					</a>
				</div>
			</esd:SecurityAccessRequired>
	</div>	
	
<!-- Professional Learning Series --> 				
<!-- Professional Learning PLAN  -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app42"> 
			<esd:SecurityAccessRequired permissions="PPGP-VIEW">	
					<div class="menuIconImage">
						<a href="PPGP/ppgpPolicy.html"  onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app42');loadingData();">
						<img src="includes/img/menu/learningplana-off.png" class="img-swap menuImage" border=0 title="Professional Learning Plans Archive">
						</a>
					</div>
			</esd:SecurityAccessRequired>
	</div>	

<!-- Pay Advice Admin -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app43">
			<esd:SecurityAccessRequired permissions="PAY-ADVICE-ADMIN">
				<div class="menuIconImage">
					<a href="PayAdvice/index.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app43');loadingData();">
					<img src="includes/img/menu/payadvice-off.png" class="img-swap menuImage" title="Pay Advice Admin System" border=0>
					</a>
				</div>
			</esd:SecurityAccessRequired>
	</div>	

<!-- Pay Advice for Teachers -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app44">
			<esd:SecurityAccessRequired permissions="PAY-ADVICE-NORMAL">
				<div class="menuIconImage">
					<a href="PayAdvice/viewTeacherPaystubs.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app44');loadingData();">
					<img src="includes/img/menu/payadviceteacher-off.png" class="img-swap menuImage" title="Pay Advice for Teachers" border=0>
					</a>
				</div>
			</esd:SecurityAccessRequired>
	</div>	

<!-- Student Travel Request System -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app45">
			<esd:SecurityAccessRequired permissions="STUDENT-TRAVEL-ADMIN-VIEW,STUDENT-TRAVEL-PRINCIPAL-VIEW">
				<div class="menuIconImage">
					<a href="student/travel/" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app45');loadingData();">
					<img src="includes/img/menu/studenttravel-off.png" class="img-swap menuImage" border=0 title="District out of province student travel request and tracking system.">
					</a>
				</div>
			</esd:SecurityAccessRequired>
	</div>	
				
<!-- Travel Claim System  -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app46">
			<esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-VIEW">
				<div class="menuIconImage">
					<a href="Travel/viewTravelClaimSystem.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app46');loadingData();">
					<img src="includes/img/menu/travelclaim-off.png" class="img-swap menuImage" border=0 title="The Travel Claim Systems allow you to submit travel claims and track the status of your claims as they are being processed.">
					</a>
				</div>
			</esd:SecurityAccessRequired>
	</div>	
	
<!-- SIEMS  -->		
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app47">			
			<div class="menuIconImage">
				<a href="https://siems.nlesd.ca" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app47');">
				<img src="includes/img/menu/ems-off.png" class="img-swap menuImage" border=0 title="System Inspection Enterprise Management System allows you to submit and track school maintenance requests.">
				</a>
			</div>
	</div>	
						
<!-- Busing Contractor System -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app48">	
			<esd:SecurityAccessRequired permissions="BCS-SYSTEM-ACCESS">
				<div class="menuIconImage">
					<a href="BCS/index.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app48');loadingData();">
					<img src="StaffRoom/includes/img/buscontract-off.png" class="img-swap menuImage" title="Bussing Contractor System" border=0>
					</a>
				</div>
			</esd:SecurityAccessRequired>
	</div>	
			
<!-- BUS PLANNER -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app49">
			<esd:SecurityAccessRequired roles="ADMINISTRATOR,PRINCIPAL,VICE PRINCIPAL,SCHOOL SECRETARY">
				<div class="menuIconImage">                              	
					<a href="https://nlesd.mybusplanner.ca/default" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app49');">
					<img src="StaffRoom/includes/img/busplanner-off.png" class="img-swap menuImage" border=0 title="My Bus Planner">
					</a>                                                         
				</div>
			</esd:SecurityAccessRequired>
	</div>			
		
<!-- EECED PRINCIPAL-->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app50">
			<esd:SecurityAccessRequired roles="PRINCIPAL,VICE PRINCIPAL">
				<div class="menuIconImage">                              	
					<a href="EECD/schoolAdminViewApprovals.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app50');loadingData();">
					<img src="includes/img/menu/eecdp-off.png" class="img-swap menuImage" border=0 title="EECD SCHOOL ADMIN">
					</a>                                                         
				</div>
			</esd:SecurityAccessRequired>
	</div>	
	
<!-- EECED VIEW-->				
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app51">		
			<esd:SecurityAccessRequired permissions="EECD-VIEW">
				<div class="menuIconImage">                              
					<a href="EECD/viewEECD.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app51');loadingData();">
					<img src="includes/img/menu/eecd-off.png" class="img-swap menuImage" border=0 title="EECD">
					</a>                                                         
				</div>
			</esd:SecurityAccessRequired>
	</div>	

<!-- EECED ADMIN-->				
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app52">		
			<esd:SecurityAccessRequired permissions="EECD-VIEW-ADMIN,EECD-VIEW-SHORTLIST">
				<div class="menuIconImage">                              
					<a href="EECD/adminViewAreas.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app52');loadingData();">
					<img src="includes/img/menu/eecda-off.png" class="img-swap menuImage" border=0 title="EECD ADMIN">
					</a>                                                         
				</div>
			</esd:SecurityAccessRequired>
	</div>	
	
<!-- Canva -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app53">					
			<div class="menuIconImage">
				<a href="https://www.canva.com/" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app53');">
					<img src="StaffRoom/includes/img/canva-off.png" class="img-swap menuImage" border=0 title="Canva Design">
				</a>
			</div>
	</div>	
				
<!-- District School Map -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app54">
			<div class="menuIconImage">
				<a href="https://www.google.com/maps/d/u/0/viewer?mid=1R5VvnRw7O2HyleZerwnEih2pfJngXxA&ll=51.25470996626324%2C-53.88959315085279&z=5" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app54');"> 
					<img src="StaffRoom/includes/img/smap-off.png" class="img-swap menuImage" border=0 title="District Schools Location Map">
				</a>
			</div>
	</div>	
				
	
<!-- Microsoft Office -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app55">
		<div class="menuIconImage">
			<a href="https://portal.office.com/" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app55');">
			<img src="StaffRoom/includes/img/msoffice-off.png" class="img-swap menuImage" border=0 title="Microsoft Office 365">
			</a>
		</div>
	</div>	
	
<!-- Teams -->	
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app56">	
		<div class="menuIconImage">
			<a href="https://teams.microsoft.com" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app56');">
			<img src="StaffRoom/includes/img/msteams-off.png" class="img-swap menuImage" border=0 title="Microsoft Teams">
			</a>
		</div>
	</div>	
	
<!-- GMail -->	
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app57">
		<div class="menuIconImage">
				<a href="https://mail.google.com/" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app57');">
				<img src="StaffRoom/includes/img/gmail-off.png" class="img-swap menuImage" border=0 title="Google Mail">
				</a>
		</div>
	</div>		
		
<!-- Google Search -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app58">			
		<div class="menuIconImage">
				<a href="https://www.google.ca/" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app58');">
				<img src="StaffRoom/includes/img/gsearch-off.png" class="img-swap menuImage" border=0 title="Google Search">
				</a>
		</div>	
	</div>	
	
<!-- Google Sites -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app59">		
		<div class="menuIconImage">
				<a href="https://sites.google.com" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app59');">
				<img src="StaffRoom/includes/img/gsites-off.png" class="img-swap menuImage" border=0 title="Google Sites">
				</a>
		</div>	
	</div>	
	
<!-- Google Drive -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app60">
		<div class="menuIconImage">
				<a href="https://drive.google.com" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app60');">
				<img src="StaffRoom/includes/img/gdrive-off.png" class="img-swap menuImage" border=0 title="Google Drive">
				</a>
		</div>	
	</div>	
	
<!-- Google Meet -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app61">		
		<div class="menuIconImage">
			<a href="https://meet.google.com" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app61');">
			<img src="StaffRoom/includes/img/gmeet-off.png" class="img-swap menuImage" border=0 title="Google Meet">
			</a>
		</div>
	</div>	
	
<!-- Google Calendar -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app62">		
		<div class="menuIconImage">
			<a href="https://calendar.google.com" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app62');">
			<img src="StaffRoom/includes/img/gcalendar-off.png" class="img-swap menuImage" border=0 title="Google Calendar">
			</a>
		</div>		
	</div>	
	
<!-- Google Earth -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app63">		
		<div class="menuIconImage">
			<a href="https://earth.google.com/web/@53.50390637,-61.46249748,-88.43444723a,3022831.40276581d,35y,-0h,0t,0r" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app63');">
			<img src="StaffRoom/includes/img/gearth-off.png" class="img-swap menuImage" border=0 title="Google Earth">
			</a>
		</div>		
	</div>	
	
<!-- Google Maps -->	
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app64">
		<div class="menuIconImage">
				<a href="https://www.google.com/maps?authuser=1" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app64');"> 
				<img src="StaffRoom/includes/img/gmaps-off.png"	class="img-swap menuImage" border=0 title="Google Maps">
				</a>
		</div>
	</div>

<div style="clear:both;"></div>	
<hr>

<div class="siteHeaderGreen">District Social Media Accounts</div>
Our current public social media accounts.<br/><br/>

<!-- District YouTube Account -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app70">		
			<div class="menuIconImage">
				<a href="https://www.youtube.com/channel/UCIT127dtdYdGLnVFYv3c9uQ" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app70');">
					<img src="StaffRoom/includes/img/dyt-off.png" class="img-swap menuImage" border=0 title="District YouTube Account">
				</a>
			</div>
	</div>
	
<!-- District Twitter Account -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app71">		
			<div class="menuIconImage">
				<a href="https://twitter.com/NLESDCA" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app71');"> 
				<img src="StaffRoom/includes/img/twitter-off.png" class="img-swap menuImage" border=0 title="District Twitter Account">
				</a>
			</div>
	</div>
	
<!-- District Instagram Account -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app72">		
			<div class="menuIconImage">
				<a href="https://www.instagram.com/nlesd/" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app72');">
					<img src="StaffRoom/includes/img/insta-off.png" class="img-swap menuImage" border=0 title="District Instagram Account">
				</a>
			</div>
	</div>
	
<!-- District Facebook Account -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app73">		
			<div class="menuIconImage">
				<a href="https://www.facebook.com/NLESDCA" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app73');"> 
				<img src="StaffRoom/includes/img/fb-off.png" class="img-swap menuImage" border=0 title="District Facebook Account">
				</a>
			</div>
	</div>
<div style="clear:both;"></div>	
<div class="adminApps">	
		
<hr>

<div class="siteHeaderGreen">Website Administration Applications (<span id="adminApps"></span>)</div>
Special applications for updating and posting information, depending on your level of access.<br/><br/>
<% int cnt=0; %>
					
<!-- Office Staff Update -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app80">
		<esd:SecurityAccessRequired
			roles="ADMINISTRATOR,OFFICE-STAFF-WEBUPDATE-ALL,OFFICE-STAFF-WEBUPDATE-AVALON,OFFICE-STAFF-WEBUPDATE-CENTRAL,OFFICE-STAFF-WEBUPDATE-WESTERN,OFFICE-STAFF-WEBUPDATE-LABRADOR">
			<div class="menuIconImage">
				<a href="StaffRoom/StaffDirectory/staff_directory.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app80');loadingData();">
					<img src="StaffRoom/includes/img/staffdir-off.png" class="img-swap menuImage" title="Office Staff Update System" border=0>
				</a>
			</div>
			<%cnt++; %>
		</esd:SecurityAccessRequired>
	</div>		
<!-- Tender Posting System -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app81">
		<esd:SecurityAccessRequired permissions="TENDER-ADMIN,TENDER-EDIT,TENDER-VIEW">
			<div class="menuIconImage">
				<a href="Tenders/viewTenders.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app81');loadingData();">
				<img src="StaffRoom/includes/img/tender-off.png" class="img-swap menuImage" title="Tender Posting System" border=0></a>
			</div>
			<%cnt++; %>
		</esd:SecurityAccessRequired>
	</div>				

<!-- Admin School Profile Update -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app82">
		<esd:SecurityAccessRequired roles="ADMINISTRATOR,PRINCIPAL,VICE PRINCIPAL,SCHOOL SECRETARY">
			<div class="menuIconImage">
				<a href="StaffRoom/SchoolDirectory/school_profile.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app82');loadingData();">
					<img src="StaffRoom/includes/img/schoolprofile-off.png" class="img-swap menuImage" title="School Profile Update System" border=0>
				</a>
			</div>
			<%cnt++; %>
		</esd:SecurityAccessRequired>				
	</div>	
	
<!-- Bus Route Update -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app83">
		<esd:SecurityAccessRequired roles="ADMINISTRATOR,BUSROUTE-POST">
			<div class="menuIconImage">
				<a href="StaffRoom/BusRoutes/school_directory_bus_routes.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app83');loadingData();">
					<img src="StaffRoom/includes/img/busroute-off.png" class="img-swap menuImage" title="Bus Route Update System" border=0>
				</a>
			</div>
			<%cnt++; %>
		</esd:SecurityAccessRequired>
	</div>	
		
<!-- School Review System--> 
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app84">
		<esd:SecurityAccessRequired roles="ADMINISTRATOR,SCHOOL-REVIEW-ADMIN">
			<div class="menuIconImage">                              	
				<a href="SchoolReviews/viewSchoolReviews.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>pp84');loadingData();">
				<img src="StaffRoom/includes/img/schoolreview-off.png" class="img-swap menuImage" border=0 title="SCHOOL REVIEW PROCESS ADMIN">
				</a>                                                         
			</div>
			<%cnt++; %>
		</esd:SecurityAccessRequired>
	</div>	
					
<!-- Weather Central -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app85">
		<esd:SecurityAccessRequired permissions="WEATHERCENTRAL-PRINCIPAL-ADMINVIEW">
			<div class="menuIconImage">
				<a href="SchoolStatus/viewWeatherCentralAdmin.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app85');loadingData();">
				<img src="StaffRoom/includes/img/schoolstatus-off.png" class="img-swap menuImage" border=0 title="The School Status application allows a principal to set the closure status for schools in their school system.">
				</a>
			</div>
			<%cnt++; %>
		</esd:SecurityAccessRequired>
	</div>	
	
<!-- Web Maintenance -->		
 <!-- News Postings --> 
 	<div id="<%=usr.getPersonnel().getPersonnelID()%>app86">     	
	          	<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-VIEW,WEBMAINTENANCE-ANNOUNCEMENTS">
	          	<div class="menuIconImage">      
	          		<a href="/MemberServices/WebUpdateSystem/NewsPostings/viewNewsPostings.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app86');loadingData();">
	          		<img src="StaffRoom/includes/img/webnewspost-off.png" class="img-swap menuImage" title="View News Items">
	          		</a>
	          	</div>
	          	<%cnt++; %>
	          	</esd:SecurityAccessRequired> 
	</div>	
		          	
<!-- Meeting Minutes -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app87">				
	          	<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-VIEW,WEBMAINTENANCE-BOARDMINUTES">          	
				<div class="menuIconImage">     
	          		<a href="/MemberServices/WebUpdateSystem/MeetingMinutes/viewMeetingMinutes.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app87');loadingData();">
	          		<img src="StaffRoom/includes/img/webminutes-off.png" class="img-swap menuImage" border=0 title="View Meeting Minutes">
	          		</a>
	          	</div>
	          	<%cnt++; %>	
	          	</esd:SecurityAccessRequired>
	</div>	
		    
 <!-- Meeting Highlights -->
 	<div id="<%=usr.getPersonnel().getPersonnelID()%>app88">      	
	          	 <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-VIEW,WEBMAINTENANCE-BOARDHIGHLIGHTS">
				 <div class="menuIconImage">
	          		<a href="/MemberServices/WebUpdateSystem/MeetingHighlights/viewMeetingHighlights.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app88');loadingData();">
	          		<img src="StaffRoom/includes/img/webhighlights-off.png" class="img-swap menuImage" title="View Meeting Highlights">
	          		</a>
	          	 </div>
	          	 <%cnt++; %>
	          	 </esd:SecurityAccessRequired>
	</div>	
		    
<!-- Banner Postings --> 
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app89">     	
	          	<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-VIEW,WEBMAINTENANCE-ANNOUNCEMENTS">
	          	<div class="menuIconImage">    
	          		<a href="/MemberServices/WebUpdateSystem/Banners/viewBanners.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app89');loadingData();">
	          		<img src="StaffRoom/includes/img/webbannerpost-off.png" class="img-swap menuImage" title="View Banners">
	          		</a>
	          	</div>
	          	<%cnt++; %>
	          	</esd:SecurityAccessRequired>
	</div>	
		    
<!-- Blog (Not currently Used)-->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app90">      	
	          	<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-VIEW,WEBMAINTENANCE-DIRECTORSWEB">
	          	<div class="menuIconImage">     
	          		<a href="/MemberServices/WebUpdateSystem/Blogs/viewBlogs.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app90');loadingData();">
	          		<img src="StaffRoom/includes/img/webblog-off.png" class="img-swap menuImage" title="View Superintendent Blogs">
	          		</a>
	          	</div>
	          	<%cnt++; %>
	          	</esd:SecurityAccessRequired>
	</div>	
		          	
<!-- Policy Postings -->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app91">      	
	          	 <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-VIEW,WEBMAINTENANCE-DISTRICTPOLICIES">
	          	<div class="menuIconImage">     
	          		<a href="/MemberServices/WebUpdateSystem/Policies/viewPolicies.html" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app91');loadingData();">
	          		<img src="StaffRoom/includes/img/webpolicy-off.png" class="img-swap menuImage" title="View Policies">
	          		</a>
	          	</div>
	          	<%cnt++; %>
	          	</esd:SecurityAccessRequired>
	</div>		    
			
</div>


<div style="clear:both;"></div>	
<hr>	

<div class="siteHeaderGreen">Help Desks</div>						
Here are listed access to a variety of application and district level HelpDesks.<br/><br/>

<!-- Staff HelpCentre-->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app100">
		<div class="menuIconImage">
			<a href="StaffRoom/helpcentre.jsp" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app100');loadingData();">
			<img src="StaffRoom/includes/img/shc-off.png" class="img-swap menuImage" border=0 title="Staff HelpCentre">
			</a>
		</div>		
	</div>	
								
<!-- HelpDesk-->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app101">	
		<div class="menuIconImage">
			<a href="https://servicedesk.nlesd.ca" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app101');">
			<img src="StaffRoom/includes/img/servicehd-off.png" class="img-swap menuImage" border=0 title="HelpDesk">
			</a>
		</div>
	</div>	
				
<!-- IT Assist Portal-->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app102">	
		<div class="menuIconImage">
			<a href="http://itassist.nlesd.ca/" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app102');">
			<img src="StaffRoom/includes/img/itassist-off.png" class="img-swap menuImage" border=0 title="IT Assist Portal for Administrators">
			</a>
		</div>							
	</div>	
	
<!-- Payroll HelpDesk-->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app103">	
		<div class="menuIconImage">
			<a href="https://forms.gle/nm8xb5iLthGxg4UF8" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app103');">
			<img src="StaffRoom/includes/img/payhelpdesk-off.png" class="img-swap menuImage" border=0 title="Payroll HelpDesk"/>
			</a>
		</div>
	</div>	
			
<!-- Travel HelpDesk-->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app104">	
		<div class="menuIconImage">
			<a href="https://docs.google.com/forms/d/e/1FAIpQLSfWm5FdaMXKCAAdSZZM_gTQGgkU7zkiUkZPupMWM9Pi-96NOw/viewform" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app104');">
			<img src="StaffRoom/includes/img/travelhelp-off.png" class="img-swap menuImage" border=0 title="Travel HelpDesk" />
			</a>
		</div>		
	</div>		
						
<!-- Finance HelpDesk-->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app105">	
		<div class="menuIconImage">
			<a href="https://docs.google.com/forms/d/e/1FAIpQLSds_59KsxMEzt4fiIqgcyMXDVfhhZ18eyDonU-ff-CCqq16cQ/viewform?usp=sf_link" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app105');">
			<img src="StaffRoom/includes/img/financehd-off.png" class="img-swap menuImage" border=0 title="Finance HelpDesk"/>
			</a>
		</div>		
	</div>		
		
<!-- MyHRP HelpDesk-->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app106">	
		<div class="menuIconImage">
			<a href="https://docs.google.com/forms/d/e/1FAIpQLSf2fwNSbvj0wV4laeKQagx3LDJwsOWqO1H4XRcjqKIiE80NUA/viewform" target="_blank" onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app106');">
			<img src="StaffRoom/includes/img/hrhelpdesk-off.png" class="img-swap menuImage" border=0 title="MyHRP HelpDesk"/>
			</a>
		</div>			
	</div>			
													
<!-- Staff HelpCentre-->
	<div id="<%=usr.getPersonnel().getPersonnelID()%>app107">
		<div class="menuIconImage">
			<a href="https://forms.gle/rpYgeZfm81Wt5c138" target="_blank"  onclick="checkCookie('<%=usr.getPersonnel().getPersonnelID()%>app107');">
			<img src="StaffRoom/includes/img/srhelp-off.png" class="img-swap menuImage" border=0 title="StaffRoom HelpCentre"/>
			</a>
		</div>	
	</div>	

	
	



<div style="clear:both;"></div>	
<hr>	
	

	
					
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
	</div>
	
<script>

// Cookie will expire after 10 days if not used. If used, will reset to 10 days.
// Number of Clicks before it gets recognized as a Favorite

var cookiePrefix = '<%=usr.getPersonnel().getPersonnelID()%>app';
var allCookies = $.cookie();
var numClicks = 3;
var cookieExpiryDays = 5; 

$('#clearFavorites').click(function() {
	  var answer = confirm("Are you sure you want to remove your favorites? Doing so will reset them and will start a new list. This cannot be undone.");

	  if (answer) {
		  clearFavorites();		  
	  } else {
	    return;
	  }
	  
	});

for (var cookieName in allCookies) {
	  if (cookieName.startsWith(cookiePrefix)) {
	    var cookieValue = allCookies[cookieName];	
	    //var theIconCountName = "." + cookieName + "cnt";
    	//$(theIconCountName).text($.cookie(cookieName));
	    if ($.cookie(cookieName) > numClicks) {	
	    	$(".favoriteBlock").css("display","block");
	    	var theIconName = "#" + cookieName;
	    	var sourceCopy = $(theIconName).clone();
	    	sourceCopy.appendTo('#myFavorites');	    	
	    	}
	  }
	}

function checkCookie(cookieName) {
	if (!$.cookie(cookieName)) {
		setCookie(cookieName, 1, cookieExpiryDays);
	} else {	
		incrementCookieValue(cookieName);
	}
}

function setCookie(name, value, days) {	 
	$.cookie(name, value, { expires: days, path: '/MemberServices/' });
}

function incrementCookieValue(cookieName) {
	  var cookieValue = $.cookie(cookieName);
	  var newValue = parseInt(cookieValue) + 1;	
	  setCookie(cookieName, newValue, cookieExpiryDays);

	}

function clearFavorites() {	 

	for (var cookieName in allCookies) {
		  if (cookieName.startsWith(cookiePrefix)) {
			   setCookie(cookieName, 1, cookieExpiryDays);
	  }
	}
	$(".favoriteBlock").css("display","none");
	location.reload();
}	


</script>
			
</body>
</html>