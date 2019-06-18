<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.*,
                 com.awsd.personnel.*,
                 com.awsd.mail.bean.*,
                 com.esdnl.util.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.personnel.jobs.constants.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
    ApplicantProfileBean profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
    ApplicantEsdExperienceBean esd_exp = ApplicantEsdExperienceManager.getApplicantEsdExperienceBean(profile.getSIN());
    
    String msg = "";
    
    if(esd_exp != null) {
    	
    	if(esd_exp.isPermanent() || esd_exp.isReplacement()) {
    		
    		School school = null;
    		
    		if(esd_exp.isPermanent())
    			school = SchoolDB.getSchool(esd_exp.getPermanentContractSchool());
    		else if(esd_exp.isReplacement())
    			school = SchoolDB.getSchool(esd_exp.getContractSchool());
    		
    		if(school != null) {
    			
    			Personnel principal = school.getSchoolPrincipal();
    			
    			EmailBean email = new EmailBean();
    			
    			email.setTo(principal.getEmailAddress());
    			email.setSubject("Applicant Reference Request - " + profile.getFullNameReverse());
    			email.setBody(profile.getFullNameReverse() 
    				+ " has requested that you complete the applicant reference checklist on their behalf.<BR>"
    				+ "Please login to Member Services, the applicant reference checklist can be found in the Personnel Package application.<BR><BR>"
    				+ "PLEASE DO NOT REPLY TO THIS MESSAGE.<BR><BR>"
    				+ "Thank you.<br><br>Member Services");
    			
    			email.send();
    			
    			msg = "A reference request has been sent to " + principal.getFullNameReverse() + ".";
    			
    		}
    		else
    			msg = "Please complete the \"Newfoundland &amp; Labrador English School District Experience\" section of your profile.";
    		
    	}
    	else
    		msg = "An applicant reference request can only be sent by applicant who is current in a permanent contract or replacement contract.";
    	
    }
    else
			msg = "Please complete the \"Newfoundland &amp; Labrador English School District Experience\" section of your profile.";
    
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import 'includes/home.css';</style>
		<script language="JavaScript" src="js/CalendarPopup.js"></script> 
	</head>
	
	<body>
  
<%=profile.getSurname() + ", " + profile.getFirstname()%> Reference Request


<%=msg%>
</body>
</html>
