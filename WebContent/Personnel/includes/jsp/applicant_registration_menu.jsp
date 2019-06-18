<%@ page language="java" 
				 session="true"
				 isThreadSafe="false"%>

                <table width="195px;" cellpadding="2" cellspacing="2" align="left" style="border: 1px solid #E5EAF1; background-color: #FBFBF5;"><tr bgcolor="#E5EAF1"><td>REGISTRATION STEPS</td></tr>
                
                    <%if(request.getParameter("EMPTY") == null){%>

	                         <tr><td>
	                         &middot; <a href="applicant_registration_step_1.jsp" class="homeSideNavLink">Step 1: Profile</a><BR>
	                         <%if(session.getAttribute("APPLICANT") != null){%>
	                         &middot; <a href="applicant_registration_step_2.jsp" class="homeSideNavLink">Step 2: NLESD Experience</a><BR>
	                         &middot; <a href="applicant_registration_step_2_perm_exp.jsp" class="homeSideNavLink">Step 2A: NLESD Perm. Experience</a><BR>
	                         &middot; <a href="applicant_registration_step_2_repl_exp.jsp" class="homeSideNavLink">Step 2B: NLESD Repl. Experience</a><BR>
	                         &middot; <a href="applicant_registration_step_3.jsp" class="homeSideNavLink">Step 3: Other Board Experience</a><BR>
	                         &middot; <a href="applicant_registration_step_4.jsp" class="homeSideNavLink">Step 4: Substitute Experience</a><BR>
	                         &middot; <a href="applicant_registration_step_5.jsp" class="homeSideNavLink">Step 5: Education</a><BR>
	                         &middot; <a href="applicant_registration_step_6.jsp" class="homeSideNavLink">Step 6: Education (cont'd)</a><BR>
	                         &middot; <a href="applicant_registration_step_7.jsp" class="homeSideNavLink">Step 7: Other Information</a><BR>
	                         &middot; <a href="applicant_registration_step_8.jsp" class="homeSideNavLink">Step 8: References</a><BR>
	                         &middot; <a href="applicant_registration_step_9.jsp" class="homeSideNavLink">Step 9: Regional Preferences</a><BR>
	                         &middot; <a href="applicant_registration_step_10.jsp" class="homeSideNavLink">Step 10: Documents/Declarations</a><BR />
	                         </td></tr>
	                         
	                         <tr bgcolor="#E5EAF1"><td>QUICK LINKS</td></tr>
	                         <tr><td>
	                         &middot; <a href="applicant_security.jsp" class="homeSideNavLink">Account Security</a><BR>
							 						 &middot; <a href="applicant_substitute_preferences.jsp" class="homeSideNavLink">Substitute Preferences</a><BR>
	                         &middot; <a href="http://www.nlesd.ca/employment/teachingpositions.jsp" class="homeSideNavLink">Home</a><BR>
	                         <%}%>
	                      	</td></tr>                    <%}%>
                 
                 
                 </table><br>