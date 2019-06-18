<%@ page language ="java" 
         session = "true"
         import = "com.awsd.security.*;"
         isThreadSafe="false"%>

<%@ taglib uri="/taglib/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck />

<%
  User usr = (User) session.getAttribute("usr");
%>
<html>
  <head>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <TITLE>Member Services 3.0</title>

    <link href="css/style.css" rel="stylesheet">
   
	<script language="Javascript" src="js/jquery-1.9.1.min.js"></script>
     <script>

       jQuery(function(){
    	     $(".swap").hover(
    	          function(){this.src = this.src.replace("-off","-on");},
    	          function(){this.src = this.src.replace("-on","-off");
    	     });
    	});


						</script>
      
  </head>

  <body bgcolor="#BF6200">
  
  		<table width="900" border="0" cellspacing="0" cellpadding="0" align="center" style="background-color: White; border: 1px solid #00407A;">
			<tr><td>
  			<img src="images/menu/cpanel.png" border="0" vspace=5 hspace=5 alt="Member Services Control Panel.">
			</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>
  			&nbsp; <span class="welcome"> Welcome  <b><%=usr.getPersonnel().getFirstName()%></b> to your member services control panel. Below are the options available to you.</span>
			</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>  
       			<table align="center" width="880" cellspacing="10" cellpadding="0" border="0">
				<tr><td>
	
    	<esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW">
	        
	        <a href="MemberAdmin/viewMemberAdmin.html"><img src="images/menu/membersadmin-off.png"
	        border="0" alt="Member Admin is an administrative interface for assigning permissions to members based on job role and application access requirements." 
	        class="swap" /></a>
	     
   	
		</esd:SecurityAccessRequired>
     
          	<a href="security/"><img src="images/menu/security-off.png"
          	class="swap" 
          	border="0" 
          	alt="Security Directives."></a>
			
      
      <esd:SecurityAccessRequired 
      permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW">
	  
	          <a href="Personnel/admin_index.jsp"><img src="images/menu/personnelpackage-off.png"
	          border="0"
	          class="swap" 
	          alt="Job Application System."></a>
	         
      </esd:SecurityAccessRequired>
      
      <esd:SecurityAccessRequired permissions="CALENDAR-VIEW">
      		<a href="PDReg/viewDistrictCalendar.html"><img src="images/menu/pdcalendar-off.png"
	        border="0"
	        class="swap" 
	        alt="The District Calendar allows users to register for upcoming events around the district."></a>
	        
      
      </esd:SecurityAccessRequired>
      
      <esd:SecurityAccessRequired permissions="BULLYING-ANALYSIS-SCHOOL-VIEW,BULLYING-ANALYSIS-ADMIN-VIEW">
	 
	          <a href="bullying/"><img src="images/menu/studentconduct-off.png" 
	          border="0" 
	          class="swap" 
	          alt="Student Conduct Report System."></a>
	          
      </esd:SecurityAccessRequired>
      
      <esd:SecurityAccessRequired permissions="TSDOC-VIEW">
	     
	          <a href="tsdoc/"><img src="images/menu/securetrustee-off.png" 
	          border="0" 
	          class="swap" 
	          alt="Repository of communication documents and notes for the Board of Trustees and it various committees."></a>
	          
      </esd:SecurityAccessRequired>
      
      <esd:SecurityAccessRequired permissions="PERSONNEL-PROFILE-TEACHER-VIEW">
	      
	          <a href="Profile/Teacher/viewCurrentTeacherProfile.html"><img src="images/menu/teacher-off.png" 
	          border="0" 
	          class="swap" 
	          alt="Teacher Profile allows a teacher to modify their current teaching assignments."></a>
	        
      </esd:SecurityAccessRequired>
    
      <esd:SecurityAccessRequired permissions="PRESENTATION-VIEW">
	      
	          <a href="presentations/presentation_list.jsp"><img src="images/menu/presentations-off.png" 
	          border="0" 
	          class="swap" 
	          alt="Presentation Directory."></a>
	        
	    </esd:SecurityAccessRequired>
	    
	    <esd:SecurityAccessRequired permissions="H1N1-ADMIN-VIEW,H1N1-PRINCIPAL-VIEW">
	      
	          <a href="h1n1/"><img src="images/menu/healthadvis-off.png" 
	          border="0" 
	          class="swap" 
	          alt="District Health Advisory System."></a>
	       
	    </esd:SecurityAccessRequired>
      
     
      <esd:SecurityAccessRequired permissions="CIDB-ADMIN-VIEW">
	  
	          <a href="cidb/admin/"><img src="images/menu/criticalissues-off.png" 
	          alt="Critical Issues Database Administration Module." 
	          border="0"
	          class="swap"></a>
	        	       
      </esd:SecurityAccessRequired>
      
      <esd:SecurityAccessRequired permissions="SURVEY-ADMIN-VIEW">
	     
	          <a href="survey/admin/"><img src="images/menu/schoolbased-off.png" 
	          border="0" 
	          class="swap"
	          alt="School based survey creation and administration."></a>
	       
      </esd:SecurityAccessRequired>
      
      <esd:SecurityAccessRequired permissions="COLLABORATION-ADMIN-VIEW,COLLABORATION-GROUP-VIEW">
	     
	          <a href="collaboration/index.html"><img src="images/menu/collab-off.png" 
	          border="0" 
	          class="swap" 
	          alt="Group Based Feedback and Brainstorming Repository."></a>
	       
	        
      </esd:SecurityAccessRequired>
      
      <esd:SecurityAccessRequired permissions="COMPLAINT-MONITOR,COMPLAINT-ADMIN,COMPLAINT-VIEW">
	      
	          <a href="complaint/admin_complaint_summary.jsp"><img src="images/menu/complaint-off.png" 
	          border="0" 
	          class="swap" 
	          alt="Complaint monitoring/tracking system."></a>
	      
      </esd:SecurityAccessRequired>
      
      <esd:SecurityAccessRequired permissions="STUDENT-TRAVEL-ADMIN-VIEW,STUDENT-TRAVEL-PRINCIPAL-VIEW">
	      
	          <a href="student/travel/"><img src="images/menu/studenttravel-off.png" 
	          border="0" 
	          class="swap"
	          alt="Out of province student travel request and tracking system."></a>
	          
      </esd:SecurityAccessRequired>
      
      <esd:SecurityAccessRequired permissions="NISEP-ADMIN-VIEW">
	      
	          <a href="Nisep/"><img src="images/menu/nisep-off.png" 
	          border="0" 
	          class="swap" 
	          alt="Internation Student Education Program (NISEP) Administration System."></a>	   
	        
      </esd:SecurityAccessRequired>
      
      
      
      <esd:SecurityAccessRequired permissions="PHOTOCOPIER-VIEW,PHOTOCOPIER-ADMIN-VIEW">
	     
	          <a href="Photocopiers/view_copiers.jsp"><img src="images/menu/copier-off.png" 
	          border="0" 
	          class="swap"
	          alt="Photocopier Support System."></a>    
	         
	      
	          
      </esd:SecurityAccessRequired>
      
      <esd:SecurityAccessRequired permissions="USER-ADMIN-VIEW">
	     
	          <a href="UserAdmin/"><img src="images/menu/user-admin-off.png" 
	          border="0" 
	          class="swap" 
	          alt="Change/modify user account options."></a>
	        
	       
      </esd:SecurityAccessRequired>
      
      <esd:SecurityAccessRequired permissions="ROEREQUEST-VIEW">
	      
	          <a href="ROERequest/myROERequest.html"><img src="images/menu/roe-off.png" 
	          border="0" 
	          class="swap" 
	          alt="Record of Employment(ROE) Request System."></a>
	    
	          
      </esd:SecurityAccessRequired>

      <esd:SecurityAccessRequired permissions="STRIKECENTRAL-ADMINVIEW">
	      
	          <a href="StrikeCentral/strikeCentralAdminView.html"><img src="images/menu/strikea-off.png" 
	          border="0" 
	          class="swap" 
	          alt="The Job Action Package provides an up to date report on the status of schools in the district during a union strike."></a>
	     
	          
      </esd:SecurityAccessRequired>
      
      <esd:SecurityAccessRequired permissions="STRIKECENTRAL-PRINCIPAL-ADMINVIEW">
	      
	          <a href="StrikeCentral/strikeCentralPrincipalAdminView.html"><img src="images/menu/strike-off.png" 
	          border="0" 
	          class="swap" 
	          alt="The Job Action application provides an interface to update the status of your school during a union strike."></a>
	          
	          
      </esd:SecurityAccessRequired>
    
    	<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-VIEW">
	      
	          <a href="WebMaint/viewWebMaintenance.html"><img src="images/menu/webmaint-off.png" 
	          border="0" 
	          class="swap" 
	          alt=">All web maintenance tasks can her found here."></a>
	         
	          
      </esd:SecurityAccessRequired>

    	<esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-VIEW">

	          <a href="Travel/viewTravelClaimSystem.html"><img src="images/menu/employee-travel-off.png" 
	          border="0" 
	          class="swap" 
	          alt="The Travel Claim System allow you to submit travel claims and track the status of your claims as they are being processed."></a>

      </esd:SecurityAccessRequired>
  
      <esd:SecurityAccessRequired permissions="MAINTENANCE-SCHOOL-VIEW,MAINTENANCE-ADMIN-VIEW,MAINTENANCE-WORKORDERS-VIEW">
	
	          <a href="MRS/viewMRS.html"><img src="images/menu/mrs-off.png" 
	          border="0" 
	          class="swap"
	          alt="The Maintenance Request System (MRS) allows you to submit and track school maintenance requests."></a>
	   

      </esd:SecurityAccessRequired>

      <esd:SecurityAccessRequired permissions="FINANCIAL-VIEW">

	          <a href="Financial/myFinancialReports.html"><img src="images/menu/finance-off.png" 
	          border="0" 
	          class="swap" 
	          alt="The Finanical Package provides up to date finanical reports on the district finanical infrastructure."></a>
	   
      </esd:SecurityAccessRequired>
    
    	<esd:SecurityAccessRequired permissions="WEATHERCENTRAL-PRINCIPAL-ADMINVIEW">

	          <a href="WeatherCentral/viewWeatherCentralAdmin.html"><img src="images/menu/weathercentral-off.png" 
	          border="0" 
	          class="swap" 
	          alt="The Weather Central Admin application allows a principal to set the weather closure status for schools in their school system."></a>

	   
      </esd:SecurityAccessRequired>
    
    	<esd:SecurityAccessRequired permissions="WEATHERCENTRAL-GLOBAL-ADMIN">

	          <a href="WeatherCentral/regionalized_school_admin.jsp"><img src="images/menu/weatherglobal-off.png" 
	          border="0" 
	          class="swap" 
	          alt="The Weather Central Global Admin application allows an administrator to set the weather closure status for any school in the district."></a>
	          
	  
      </esd:SecurityAccessRequired>
      
      <esd:SecurityAccessRequired permissions="TECHTRACKER-VIEW">
	
	          <a href="http://66.103.48.70/awsbdb/tech_tracker.nsf"><img src="images/menu/techtrack-off.png" 
	          border="0" 
	          class="swap" 
	          alt="Technician services tracking application."></a>
	
	  
      </esd:SecurityAccessRequired>
      
      <esd:SecurityAccessRequired permissions="PPGP-VIEW">

	          <a href="PPGP/ppgpPolicy.html"><img src="images/menu/profgrowthplan-off.png" 
	          border="0" 
	          class="swap"
	          alt="Professional and Personal Development Growth Plan"></a>
	   
	
      </esd:SecurityAccessRequired>
      
      <esd:SecurityAccessRequired permissions="PPGP-VIEW-SUMMARY">
	
	          <a href="PPGP/viewGrowthPlanPrincipalSummary.html"><img src="images/menu/principal-growth-off.png" 
	          border="0" 
	          class="swap"
	          alt="Professional and Personal Development Growth Plan Summary"></a>
	     
	
      </esd:SecurityAccessRequired>
     
      <esd:SecurityAccessRequired permissions="PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST">

	          <a href="PPGP/viewGrowthPlanProgramSpecialistSummary.html"><img src="images/menu/prog-spec-off.png" 
	          border="0" 
	          class="swap" 
	          alt="Program Specialist Professional and Personal Development Growth Plan"></a>
	     	
      </esd:SecurityAccessRequired>
      
	
			<esd:SecurityAccessRequired permissions="PGAP-VIEW">
	  
	          <a href="PGAP/selectForm.jsp"><img src="images/menu/prof-growth-policy-off.png" 
	          border="0" 
	          class="swap"
	          alt="Professional Growth &amp; Appraisal Policy for Educators."></a>
	     
	
      </esd:SecurityAccessRequired>

      

      <esd:SecurityAccessRequired permissions="EFILE-VIEW">
	   
	          <a href="EFile/EFileRepositoryChooser.html"><img src="images/menu/efile-off.png" 
	          border="0" 
	          class="swap"
	          alt="E-File is a repository for assignments, exams, lesson plans, alternate courses, criteria C domains, and behavior management plans."></a>
	     
	  
      </esd:SecurityAccessRequired>
      
				</td></tr>
				</table>
  		</td></tr>
  		<tr bgcolor="#00407A"><td><div align="center"><span class="copyright">Member Services 3.0 &copy; 2013 Eastern School District &middot; All Rights Reserved</span></div>
		</td></tr>
		</table>
    

  </body>
</html>