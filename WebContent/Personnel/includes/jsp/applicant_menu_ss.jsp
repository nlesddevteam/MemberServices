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
%>
                  
						 <table width="180px;" cellpadding="2" cellspacing="2" align="left" style="border: 1px solid #E5EAF1; background-color: #FBFBF5;">
						 <tr bgcolor="#E5EAF1"><td>PROFILE MENU</td></tr>              
                  
	                         <tr><td>
	                          &middot; <a href="http://www.nlesd.ca/employment/supportadminpositions.jsp" class="homeSideNavLink">Home</a><br>

	                         	&middot; <a href="view_applicant_ss.jsp" class="homeSideNavLink">View Profile</a><br>
	                          	&middot; <a href="applicant_registration_step_1_ss.jsp" class="homeSideNavLink">Edit Profile</a><br>
	                          	&middot; <a href="javascript:window.close();" class="homeSideNavLink">Logout</a><br>
	                         
	                         </td></tr>
	                         
	                                        
                 
                 
                 </table>
                       