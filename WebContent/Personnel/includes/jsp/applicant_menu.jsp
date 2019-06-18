<%@ page language="java" session="true" 
				 import="java.util.*,
                 java.text.*,
                 com.awsd.school.*,
                 com.esdnl.util.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.personnel.jobs.constants.*,
                 com.awsd.personnel.*" 
				 isThreadSafe="false"%>

<%
	ApplicantProfileBean profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
	ApplicantEsdExperienceBean esd_exp = ApplicantEsdExperienceManager.getApplicantEsdExperienceBean(profile.getSIN());
	School s = null;
	NLESDReferenceListBean sref=null;
	
	if(esd_exp != null) {
		if(esd_exp.getPermanentContractSchool() > 0)
			s = SchoolDB.getSchool(esd_exp.getPermanentContractSchool());
		else if (esd_exp.getContractSchool() > 0)
			s = SchoolDB.getSchool(esd_exp.getContractSchool());
		

	}
%>
                  
						 <table width="180px;" cellpadding="2" cellspacing="2" align="left" style="border: 1px solid #E5EAF1; background-color: #FBFBF5;">
						 <tr bgcolor="#E5EAF1"><td>PROFILE MENU</td></tr>              
                  
	                         <tr><td>
	                          &middot; <a href="http://www.nlesd.ca/employment/teachingpositions.jsp" class="homeSideNavLink">Home</a><br>

	                         	&middot; <a href="view_applicant.jsp" class="homeSideNavLink">View Profile</a><br>
	                          	&middot; <a href="applicant_registration_step_1.jsp" class="homeSideNavLink">Edit Profile</a><br>
	                          	&middot; <a href="javascript:window.close();" class="homeSideNavLink">Logout</a><br>
	                         
	                         </td></tr>
	                         
	                                        
                 
                 
                 </table>
                       