<%@ page language="java" 
				 session="true"
				 isThreadSafe="false"%>

                  <td width="10" align="left" valign="top">
                    <img src="images/spacer.gif" width="10" height="1"><BR />
                  </td>
                  <td width="178" align="left" valign="top" style='padding-bottom:20px;'>
                    <img src="images/spacer.gif" width="1" height="30"><BR />
                    <%if(request.getParameter("EMPTY") == null){%>
	                    <table width="178" cellpadding="0" cellspacing="0" border="0" style="border-style: solid; border-color: #EBEBEB; border-width: 1px;">
	                      <tr>
	                        <td width="120" height="24" align="left" valign="middle" style="background-color: #EBEBEB;">
	                          <img src="images/spacer.gif" width="3" height="1"><span class="displayBoxTitle">Quick Links</span>
	                        </td>
	                        <td width="58" height="24" align="right" valign="middle" style="background-color: #EBEBEB;">
	                          <img src="images/help_icon.gif" style="cursor: help;" alt="The options below will change depending on which features you use most often."><img src="images/spacer.gif" width="2" height="1"><img src="images/minimize_icon.gif"><img src="images/spacer.gif" width="2" height="1"><img src="images/close_icon.gif"><img src="images/spacer.gif" width="2" height="1"><BR />
	                        </td>
	                      </tr>
	                      <tr>
	                        <td width="178" align="left" valign="top" style="background-color: #FFFFFF;" colspan="2">
	                          <!-- Global Spacer for Alignment -->
	                          <img src="images/spacer.gif" width="1" height="5"><BR />
	                          <!-- Global Spacer for Alignment -->
	                         
	                            &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="applicant_registration_step_1.jsp" class="homeSideNavLink">Step 1: Profile</a><BR />
	                            <%if(session.getAttribute("APPLICANT") != null){%>
	                              &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="applicant_registration_step_2.jsp" class="homeSideNavLink">Step 2: NLESD Experience</a><BR />
	                              &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="applicant_registration_step_2_perm_exp.jsp" class="homeSideNavLink">Step 2A: NLESD Perm. Experience</a><BR />
	                         		&nbsp;<span class="linkText">&gt;&nbsp;</span><a href="applicant_registration_step_2_repl_exp.jsp" class="homeSideNavLink">Step 2B: NLESD Repl. Experience</a><BR />
	                              &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="applicant_registration_step_3.jsp" class="homeSideNavLink">Step 3: Other Board Experience</a><BR />
	                              &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="applicant_registration_step_4.jsp" class="homeSideNavLink">Step 4: Substitute Experience</a><BR />
	                              &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="applicant_registration_step_5.jsp" class="homeSideNavLink">Step 5: Education</a><BR />
	                              &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="applicant_registration_step_6.jsp" class="homeSideNavLink">Step 6: Education (cont'd)</a><BR />
	                              &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="applicant_registration_step_7.jsp" class="homeSideNavLink">Step 7: Other Information</a><BR />
	                              &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="applicant_registration_step_8.jsp" class="homeSideNavLink">Step 8: References</a><BR />
	                              &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="applicant_registration_step_9.jsp" class="homeSideNavLink">Step 9: Regional Preferences</a><BR />
	                              &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="applicant_registration_step_10.jsp" class="homeSideNavLink">Step 10: Documents/Declarations</a><BR />
	                              <hr width="100%" style="height:1px;" style="color:#E0E0E0;" />
	                              
	                              
	                              &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="applicant_security.jsp" class="homeSideNavLink">Security Question</a><BR />
	                              &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="applicant_substitute_preferences.jsp" class="homeSideNavLink">Substitute Preferences</a><BR />
	                              &nbsp;<span class="linkText" style="color:#FF0000;">&gt;&nbsp;</span><a href="http://www.esdnl.ca/about/employment/" class="homeSideNavLink">Home</a><BR />
	                            <%}%>
	                          
	                          <!-- Global Spacer for Alignment -->
	                          <img src="images/spacer.gif" width="1" height="5"><BR />
	                          <!-- Global Spacer for Alignment  -->
	                          
	                        </td>
	                      </tr>
	                    </table>
	                    <img src="images/spacer.gif" width="1" height="10"><BR />
                    <%}%>
                    <table width="178" cellpadding="0" cellspacing="0" border="0" style="border-style: solid; border-color: #EBEBEB; border-width: 1px;">
                      <tr>
                        <td width="120" height="24" align="left" valign="middle" style="background-color: #EBEBEB;">
                          <img src="images/spacer.gif" width="3" height="1"><span class="displayBoxTitle">Help</span>
                        </td>
                        <td width="58" height="24" align="right" valign="middle" style="background-color: #EBEBEB;">
                          <img src="images/minimize_icon.gif"><img src="images/spacer.gif" width="2" height="1"><img src="images/close_icon.gif"><img src="images/spacer.gif" width="2" height="1"><BR />
                        </td>
                      </tr>
                      <tr>
                        <td width="178" align="center" valign="top" style="background-color: #FFFFFF;" colspan="2">
                          <!-- Global Spacer for Alignment -->
                          <img src="images/spacer.gif" width="1" height="5"><BR />
                          <!-- Global Spacer for Alignment //Insert contents below -->
                          <img src="images/help_content.gif"><BR />
                          <!-- Global Spacer for Alignment -->
                          <img src="images/spacer.gif" width="1" height="5"><BR />
                          <!-- Global Spacer for Alignment -->
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td width="21" align="left" valign="top">
                    <img src="images/spacer.gif" width="21" height="1"><BR />
                  </td>