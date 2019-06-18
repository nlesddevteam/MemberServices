<%@ page language="java" 
				 session="true"
				 isThreadSafe="false"%>

                <table width="195px;" cellpadding="2" cellspacing="2" align="left" style="border: 1px solid #E5EAF1; background-color: #FBFBF5;"><tr bgcolor="#E5EAF1"><td>REGISTRATION STEPS</td></tr>
                
                    <%if(request.getParameter("EMPTY") == null){%>

	                         <tr><td>
	                         &middot; <a href="applicant_registration_step_1_ss.jsp" class="homeSideNavLink">Step 1: Profile</a><BR>
	                         <%if(session.getAttribute("APPLICANT") != null){%>
	                         &middot; <a href="applicant_registration_step_2_ss.jsp" class="homeSideNavLink">Step 2: NLESD Experience</a><BR>
	                         &middot; <a href="applicant_registration_step_3_ss.jsp" class="homeSideNavLink">Step 3: Employement History</a><BR>
	                         &middot; <a href="applicant_registration_step_5_ss.jsp" class="homeSideNavLink">Step 4: Education</a><BR>
	                         &middot; <a href="applicant_registration_step_6_ss.jsp" class="homeSideNavLink">Step 5: Professional Development</a><BR>
	                         &middot; <a href="applicant_registration_step_8_ss.jsp" class="homeSideNavLink">Step 6: References</a><BR>
	                         &middot; <a href="applicant_registration_step_9_ss.jsp" class="homeSideNavLink">Step 7: Documents/Declarations</a><BR />
	                         </td></tr>
	                         
	                         <tr bgcolor="#E5EAF1"><td>QUICK LINKS</td></tr>
	                         <tr><td>
	                         &middot; <a href="applicant_security.jsp" class="homeSideNavLink">Account Security</a><BR>
							 &middot; <a href="http://www.nlesd.ca/employment/supportadminpositions.jsp" class="homeSideNavLink">Home</a><BR>
	                         <%}%>
	                      	</td></tr>                    <%}%>
                 
                 
                 </table><br>