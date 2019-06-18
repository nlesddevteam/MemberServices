<%@ page language="java" session="true" 
				 import="java.util.*,
                 java.text.*,
                 com.awsd.school.*,
                 com.esdnl.util.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.personnel.jobs.constants.*" 
				 isThreadSafe="false"%>

<%
	ApplicantProfileBean profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
	ApplicantEsdExperienceBean esd_exp = ApplicantEsdExperienceManager.getApplicantEsdExperienceBean(profile.getSIN());
	
	School s = null;
	ReferenceBean sref = null;
	
	if(esd_exp != null) {
		if(esd_exp.getPermanentContractSchool() > 0)
			s = SchoolDB.getSchool(esd_exp.getPermanentContractSchool());
		else if (esd_exp.getContractSchool() > 0)
			s = SchoolDB.getSchool(esd_exp.getContractSchool());
		
		if(s != null && s.getSchoolPrincipal() != null)
			sref = ReferenceManager.getReferenceBean(profile, s.getSchoolPrincipal());
	}
%>
                  <td width="10" align="left" valign="top">
                    &nbsp;
                  </td>
                  <td width="178" align="left" valign="top">
                    <img src="images/spacer.gif" width="0" height="30"><BR>
                    <table width="178" cellpadding="0" cellspacing="0" border="0" style="border: solid 1px #EBEBEB;">
                      <tr>
                        <td width="120" height="24" align="left" valign="middle" style="background-color: #EBEBEB;">
                          <img src="images/spacer.gif" width="3" height="1"><span class="displayBoxTitle">Quick Links</span>
                        </td>
                        <td width="58" height="24" align="right" valign="middle" style="background-color: #EBEBEB;">
                          <img src="images/help_icon.gif" style="cursor: help;" alt="The options below will change depending on which features you use most often."><img src="images/spacer.gif" width="2" height="1"><img src="images/minimize_icon.gif"><img src="images/spacer.gif" width="2" height="1"><img src="images/close_icon.gif"><img src="images/spacer.gif" width="2" height="1"><BR>
                        </td>
                      </tr>
                      <tr>
                        <td width="178" align="left" valign="top" style="background-color: #FFFFFF;" colspan="2">
                          <!-- Global Spacer for Alignment -->
                          <img src="images/spacer.gif" width="1" height="5"><BR>
                          <!-- Global Spacer for Alignment -->

                          &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="view_applicant.jsp" class="homeSideNavLink">View Profile</a><BR>
                          &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="applicant_registration_step_1.jsp" class="homeSideNavLink">Edit Profile</a><BR>
                          &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="http://www.nlesd.ca/employment/teachingpositions.jsp" class="homeSideNavLink">Home</a><BR>
                          <img src="images/spacer.gif" width="1" height="10"><BR>
                          &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="javascript:window.close();" class="homeSideNavLink">Logout</a><BR>
                          <!-- Global Spacer for Alignment -->
                          <img src="images/spacer.gif" width="1" height="5"><BR>
                          <!-- Global Spacer for Alignment -->
                        </td>
                      </tr>
                    </table>
                    <img src="images/spacer.gif" width="1" height="10"><BR>
                    <table width="178" cellpadding="0" cellspacing="0" border="0" style="border-style: solid; border-color: #EBEBEB; border-width: 1px;">
                      <tr>
                        <td width="120" height="24" align="left" valign="middle" style="background-color: #EBEBEB;">
                          <img src="images/spacer.gif" width="3" height="1"><span class="displayBoxTitle">Help</span>
                        </td>
                        <td width="58" height="24" align="right" valign="middle" style="background-color: #EBEBEB;">
                          <img src="images/minimize_icon.gif"><img src="images/spacer.gif" width="2" height="1"><img src="images/close_icon.gif"><img src="images/spacer.gif" width="2" height="1"><BR>
                        </td>
                      </tr>
                      <tr>
                        <td width="178" align="center" valign="top" style="background-color: #FFFFFF;" colspan="2">
                          <!-- Global Spacer for Alignment -->
                          <img src="images/spacer.gif" width="1" height="5"><BR>
                          <!-- Global Spacer for Alignment //Insert contents below -->
                          <img src="images/help_content.gif"><BR>
                          <!-- Global Spacer for Alignment -->
                          <img src="images/spacer.gif" width="1" height="5"><BR>
                          <!-- Global Spacer for Alignment -->
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td width="21" align="left" valign="top">
                    <img src="images/spacer.gif" width="21" height="1"><BR>
                  </td>