<%@ page language="java"
         import="com.awsd.security.*"%>

<%
  User usr = (User) session.getAttribute("usr");
  
  boolean isPrincipal = usr.checkRole("PRINCIPAL") || usr.checkRole("PRINCIPAL REPRESENTATIVE");
%>                 
                  <td width="10" align="left" valign="top">
                    <img src="images/spacer.gif" width="10" height="1"><BR>
                  </td>
                  <td width="178" align="left" valign="top" style='padding-bottom:10px;'>
                    <img src="images/spacer.gif" width="1" height="10"><BR>
                    <table width="178" cellpadding="0" cellspacing="0" border="0" style="border-style: solid; border-color: #EBEBEB; border-width: 1px;">
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
                          <img src="images/spacer.gif" width="1" height="5"><BR />
                          <!-- Global Spacer for Alignment -->
                          
                          <%if(isPrincipal){%>
                          
                          	&nbsp;<span class="linkText">&gt;&nbsp;</span>Add Reference<BR />                          
                          	&nbsp;&nbsp;&nbsp;<span class="linkText">&gt;&nbsp;</span><a href="addNLESDAdminReference.html" class="homeSideNavLink" style='color:#FF0000;' target="_blank">Add Administrator Reference</a><BR />
                          	&nbsp;&nbsp;&nbsp;<span class="linkText">&gt;&nbsp;</span><a href="addNLESDGuideReference.html" class="homeSideNavLink" style='color:#FF0000;' target="_blank">Add Guidance Reference</a><BR />
                          	&nbsp;&nbsp;&nbsp;<span class="linkText">&gt;&nbsp;</span><a href="addNLESDTeacherReference.html" class="homeSideNavLink" style='color:#FF0000;' target="_blank">Add Teacher Reference</a><BR />                          	                          	
                          	<%if(usr.checkRole("PRINCIPAL")){%>
                          		&nbsp;<span class="linkText">&gt;&nbsp;</span><a href="principalNLESDCompletedReferences.html" class="homeSideNavLink">Completed Reference(s)</a><BR />
	                            
	                            &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="viewPositionPlanning.html" class="homeSideNavLink">Position Planning</a><BR/>
                          	<%}%>
                          	
                            &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="principalJobShortlists.html" class="homeSideNavLink">Job Short List(s)</a><BR />
                          <%}%>
                          
                          <%if(isPrincipal || usr.checkRole("TEACHER SUB LIST ACCESS")){%>
                            &nbsp;<span class="linkText">&gt;&nbsp;</span><a href="principalSubListShortlists.html" class="homeSideNavLink">Sub List Short List(s)</a><BR />
                          <%}%>
                          
                          <%if(isPrincipal || usr.checkRole("SA CALL IN LIST ACCESS")){%>
                          	&nbsp;<span class="linkText">&gt;&nbsp;</span><a href="availability_list.jsp" class="homeSideNavLink">Student Assistant Sub List</a><BR />
                          <%}%>
                          
                          <!-- Global Spacer for Alignment -->
                          <img src="images/spacer.gif" width="1" height="5"><BR />
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