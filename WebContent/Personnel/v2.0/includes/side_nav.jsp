<%@ page language="java"
         import="com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.personnel.v2.site.constant.*" 
         isThreadSafe="false"%>

<%
  int activeTab = TabConstants.TAB_HOME;
  if(request.getParameter("activeTab") != null)
    activeTab = Integer.parseInt(request.getParameter("activeTab"));
  
  int activeSubTab = TabConstants.TAB_UNKNOWN;
  if(request.getParameter("activeSubTab") != null)
    activeSubTab = Integer.parseInt(request.getParameter("activeSubTab"));
  
%>

  <script language="JavaScript">
      function toggle_row(id)
      {
        v_row = document.getElementById(id);
        img = document.getElementById(id + "_img");
        
        if(v_row)
        {
          cur = v_row.style.display;
          
          if((cur == null) || (cur == 'none'))
          {
            v_row.style.display = 'inline';
            
            if(img)
              img.src = "/MemberServices/Personnel/v2.0/images/collapse.jpg";
          }
          else
          {
            v_row.style.display = 'none';
            
            if(img)
              img.src = "/MemberServices/Personnel/v2.0/images/expand.jpg";
          }
        }
      }
  </script>
                  <td width="10" align="left" valign="top">
                    <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="10" height="1"><BR>
                  </td>
                  <td width="178" align="left" valign="top">
                    <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="15"><BR>
                    <table width="178" cellpadding="0" cellspacing="0" border="0" style="border-style: solid; border-color: #EBEBEB; border-width: 1px;">
                      <tr>
                        <td width="120" height="24" align="left" valin="middle" style="background-color: #EBEBEB;">
                          <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="3" height="1"><span class="displayBoxTitle">Quick Links</span>
                        </td>
                        <td width="58" height="24" align="right" valin="middle" style="background-color: #EBEBEB;">
                          <img src="/MemberServices/Personnel/v2.0/images/help_icon.gif" style="cursor: help;" alt="The options below will change depending on which features you use most often."><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><img src="/MemberServices/Personnel/v2.0/images/minimize_icon.gif"><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><img src="/MemberServices/Personnel/v2.0/images/close_icon.gif"><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><BR>
                        </td>
                      </tr>
                      <%if(activeTab == TabConstants.TAB_HOME){%>
                        <tr>
                          <td width="178" align="left" valin="top" style="background-color: #FFFFFF;" colspan="2">
                            <!-- Global Spacer for Alignment -->
                            <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="5"><BR>
                            <!-- Global Spacer for Alignment -->
                            &nbsp;<a href="javascript:toggle_row('home_menu');" class="homeSideNavCategoryLink"><img id="home_menu_img" src="/MemberServices/Personnel/v2.0/images/collapse.jpg" border="0">&nbsp;Home</a><br>
                            <div id="home_menu" style="display:inline;">
                              <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
                                <tr><td><a href="admin/" class="homeSideNavLink">>&nbsp;Administration</a></td></tr>
                                <tr><td><a href="teachers/" class="homeSideNavLink">>&nbsp;Teachers</a></td></tr>
                                <tr><td><a href="substitutes/" class="homeSideNavLink">>&nbsp;Substitutes</a></td></tr>
                                <tr><td><a href="staff/" class="homeSideNavLink">>&nbsp;Support Staff</a></td></tr>
                              </table>
                            </div>
                            <!-- Global Spacer for Alignment -->
                            <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="5"><BR>
                            <!-- Global Spacer for Alignment -->
                          </td>
                        </tr>
                      <%}else if(activeTab == TabConstants.TAB_ADMIN){%>
                        <tr>
                          <td width="178" align="left" valin="top" style="background-color: #FFFFFF;" colspan="2">
                            <!-- Global Spacer for Alignment -->
                            <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="5"><BR>
                            <!-- Global Spacer for Alignment -->
                            &nbsp;<a href="javascript:toggle_row('home_menu');" class="homeSideNavCategoryLink"><img id="admin_menu_img" src="/MemberServices/Personnel/v2.0/images/collapse.jpg" border="0">&nbsp;Administration</a><br>
                            <%if(activeSubTab == TabConstants.TAB_UNKNOWN){%>
                              <div id="admin_menu" style="display:inline;">
                                <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
                                  <tr><td><a href="/MemberServices/Personnel/admin_index.jsp" class="homeSideNavLink">>&nbsp;Employemnt Opportunities</a></td></tr>
                                  <tr><td><a href="recognition/" class="homeSideNavLink">>&nbsp;Recognition Request</a></td></tr>
                                </table>
                              </div>
                            <%}else{%>
                              <div id="sub_menu" style="display:inline;padding-left:10px;">
                                <%if(activeSubTab == TabConstants.TAB_ADMIN_RECOGNITION_REQUEST){%>
                                  &nbsp;<a href="javascript:toggle_row('sub_stud_ass_menu');" class="homeSideNavCategoryLink"><img id="sub_stud_ass_menu_img" src="/MemberServices/Personnel/v2.0/images/collapse.jpg" border="0">&nbsp;Recognition Request</a><br>
                                  <div id="sub_stud_ass_menu" style="display:inline;">
                                    <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:30px;">
                                      <tr><td><a href="listTemplates.html" class="homeSideNavLink">>&nbsp;Templates</a></td></tr>
                                      <tr><td><a href="listAwardCategories.html" class="homeSideNavLink">>&nbsp;Award Categories</a></td></tr>
                                      <tr><td><a href="addRequest.html" class="homeSideNavLink">>&nbsp;Add Request</a></td></tr>
                                      <tr><td><a href="listRequests.html?status=1" class="homeSideNavLink">>&nbsp;Pending Approval</a></td></tr>
                                      <tr><td><a href="listRequests.html?status=3" class="homeSideNavLink">>&nbsp;Pending Process</a></td></tr>
                                    </table>
                                  </div>
                                <%}%>
                              </div>
                            <%}%>
                            <!-- Global Spacer for Alignment -->
                            <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="5"><BR>
                            <!-- Global Spacer for Alignment -->
                          </td>
                        </tr>
                      <%}else if(activeTab == TabConstants.TAB_STAFF){%>
                        <tr>
                          <td width="178" align="left" valin="top" style="background-color: #FFFFFF;" colspan="2">
                            <!-- Global Spacer for Alignment -->
                            <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="5"><BR>
                            <!-- Global Spacer for Alignment -->
                            &nbsp;<a href="javascript:toggle_row('staff_menu');" class="homeSideNavCategoryLink"><img id="staff_menu_img" src="/MemberServices/Personnel/v2.0/images/collapse.jpg" border="0">&nbsp;Support Staff</a><br>
                            <div id="staff_menu" style="display:inline;">
                            <!--
                              <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
                                <tr><td><a href="admin/" class="homeSideNavLink">>&nbsp;Administration</a></td></tr>
                                <tr><td><a href="teachers/" class="homeSideNavLink">>&nbsp;Teachers</a></td></tr>
                                <tr><td><a href="substitutes/" class="homeSideNavLink">>&nbsp;Substitutes</a></td></tr>
                                <tr><td><a href="staff/" class="homeSideNavLink">>&nbsp;Support Staff</a></td></tr>
                              </table>
                            -->
                            </div>
                            <!-- Global Spacer for Alignment -->
                            <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="5"><BR>
                            <!-- Global Spacer for Alignment -->
                          </td>
                        </tr>
                      <%}else if(activeTab == TabConstants.TAB_SUBSTITUTES){%>
                        <tr>
                          <td width="178" align="left" valin="top" style="background-color: #FFFFFF;" colspan="2">
                            <!-- Global Spacer for Alignment -->
                            <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="5"><BR>
                            <!-- Global Spacer for Alignment -->
                            &nbsp;<a href="javascript:toggle_row('sub_menu');" class="homeSideNavCategoryLink"><img id="sub_menu_img" src="/MemberServices/Personnel/v2.0/images/collapse.jpg" border="0">&nbsp;Substitutes</a><br>
                            <%if(activeSubTab == TabConstants.TAB_UNKNOWN){%>
                              <div id="sub_menu" style="display:inline;">
                                <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
                                  <tr><td><a href="student_assistants/" class="homeSideNavLink">>&nbsp;Student Assistants</a></td></tr>
                                  <tr><td><a href="teachers/" class="homeSideNavLink">>&nbsp;Teachers</a></td></tr>
                                </table>
                              </div>
                            <%}else{%>
                              <div id="sub_menu" style="display:inline;padding-left:10px;">
                                <%if(activeSubTab == TabConstants.TAB_SUBSTITUTES_STUDENT_ASSISTANT){%>
                                  &nbsp;<a href="javascript:toggle_row('sub_stud_ass_menu');" class="homeSideNavCategoryLink"><img id="sub_stud_ass_menu_img" src="/MemberServices/Personnel/v2.0/images/collapse.jpg" border="0">&nbsp;Student Assistants</a><br>
                                  <div id="sub_stud_ass_menu" style="display:inline;">
                                    <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:30px;">
                                      <tr><td><a href="availability_list.jsp" class="homeSideNavLink">>&nbsp;By Availability</a></td></tr>
                                      <tr><td><a href="view_employees.jsp" class="homeSideNavLink">>&nbsp;By Name</a></td></tr>
                                      <tr><td><a href="senority_list.jsp" class="homeSideNavLink">>&nbsp;By Senority</a></td></tr>
                                    </table>
                                  </div>
                                <%}%>
                              </div>
                            <%}%>
                            <!-- Global Spacer for Alignment -->
                            <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="5"><BR>
                            <!-- Global Spacer for Alignment -->
                          </td>
                        </tr>
                      <%}else if(activeTab == TabConstants.TAB_TEACHERS){%>
                        <tr>
                          <td width="178" align="left" valin="top" style="background-color: #FFFFFF;" colspan="2">
                            <!-- Global Spacer for Alignment -->
                            <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="5"><BR>
                            <!-- Global Spacer for Alignment -->
                            &nbsp;<a href="javascript:toggle_row('teacher_menu');" class="homeSideNavCategoryLink"><img id="teacher_menu_img" src="/MemberServices/Personnel/v2.0/images/collapse.jpg" border="0">&nbsp;Teachers</a><br>
                            <div id="teacher_menu" style="display:inline;">
                            <!--
                              <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
                                <tr><td><a href="admin/" class="homeSideNavLink">>&nbsp;Administration</a></td></tr>
                                <tr><td><a href="teachers/" class="homeSideNavLink">>&nbsp;Teachers</a></td></tr>
                                <tr><td><a href="substitutes/" class="homeSideNavLink">>&nbsp;Substitutes</a></td></tr>
                                <tr><td><a href="staff/" class="homeSideNavLink">>&nbsp;Support Staff</a></td></tr>
                              </table>
                            -->
                            </div>
                            <!-- Global Spacer for Alignment -->
                            <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="5"><BR>
                            <!-- Global Spacer for Alignment -->
                          </td>
                        </tr>
                      <%}else{%>
                        <tr>
                          <td width="178" align="left" valin="top" style="background-color: #FFFFFF;" colspan="2">
                            <!-- Global Spacer for Alignment -->
                            <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="5"><BR>
                            <!-- Global Spacer for Alignment -->
                            &nbsp;<a href="javascript:toggle_row('education_positions');" class="homeSideNavCategoryLink"><img id="education_positions_img" src="/MemberServices/Personnel/v2.0/images/collapse.jpg" border="0">&nbsp;Educational Positions</a><br>
                            <div id="education_positions" style="display:inline;">
                              <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
                                <tr><td><a href="admin_post_job.jsp" class="homeSideNavLink">>&nbsp;Post Job</a></td></tr>
                                <tr><td><a href="admin_view_job_posts.jsp?status=All" class="homeSideNavLink">>&nbsp;View Posts</a></td></tr>
                                <tr><td><a href="admin_view_job_posts.jsp?status=Open" class="homeSideNavSubLink">Open</a></td></tr>
                                <tr><td><a href="admin_view_job_posts.jsp?status=Closed" class="homeSideNavSubLink">Closed</a></td></tr>
                                <tr><td><a href="admin_view_job_posts.jsp?status=NoShortlist" class="homeSideNavSubLink">Closed No Shortlist</a></td></tr>
                                <tr><td><a href="admin_view_job_posts.jsp?status=Awarded" class="homeSideNavSubLink">Awarded</a></td></tr>
                                <tr><td><a href="admin_view_job_posts.jsp?status=Cancelled" class="homeSideNavSubLink">Cancelled</a></td></tr>
                              </table>
                            </div>
                            &nbsp;<a href="javascript:toggle_row('substitute_lists');" class="homeSideNavCategoryLink"><img id="substitute_lists_img" src="/MemberServices/Personnel/v2.0/images/collapse.jpg" border="0">&nbsp;Substitute Lists</a><br>
                            <div id="substitute_lists" style="display:inline;">
                              <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
                                <tr><td><a href="admin_create_sub_list.jsp" class="homeSideNavLink">>&nbsp;Create List</a></td></tr>
                                <%for(int i=0; i < SubstituteListConstant.ALL.length; i++){%>
                                  <tr><td><a href="" class="homeSideNavSubCategoryLink"  onclick="return false;"><%=SubstituteListConstant.ALL[i].getDescription()%></a></td></tr> 
                                  <tr><td><a href="admin_view_sub_lists.jsp?type=<%=SubstituteListConstant.ALL[i].getValue()%>" class="homeSideNavSubLink">>&nbsp;View Lists</a></td></tr>
                                <%}%>
                              </table>
                            </div>
                            <!-- Global Spacer for Alignment -->
                            <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="5"><BR>
                          </td>
                        </tr>
                      <%}%>
                      <tr>
                        <td width="178" align="left" valign="top" style="background-color: #FFFFFF; border-top:solid 1px #ebebeb;padding:8px;" colspan="2">
                          <img src="/MemberServices/Personnel/v2.0/images/menu_msg.gif"><br>
                        </td>
                      </tr>
                    </table>
                    <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="10"><BR>
                    <table width="178" cellpadding="0" cellspacing="0" border="0" style="border-style: solid; border-color: #EBEBEB; border-width: 1px;">
                      <tr>
                        <td width="120" height="24" align="left" valin="middle" style="background-color: #EBEBEB;">
                          <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="3" height="1"><span class="displayBoxTitle">Help</span>
                        </td>
                        <td width="58" height="24" align="right" valin="middle" style="background-color: #EBEBEB;">
                          <img src="/MemberServices/Personnel/v2.0/images/minimize_icon.gif"><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><img src="/MemberServices/Personnel/v2.0/images/close_icon.gif"><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><BR>
                        </td>
                      </tr>
                      <tr>
                        <td width="178" align="center" valign="top" style="background-color: #FFFFFF;" colspan="2">
                          <!-- Global Spacer for Alignment -->
                          <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="5"><BR>
                          <!-- Global Spacer for Alignment //Insert contents below -->
                          <img src="/MemberServices/Personnel/v2.0/images/help_content.gif"><BR>
                          <!-- Global Spacer for Alignment -->
                          <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="5"><BR>
                          <!-- Global Spacer for Alignment -->
                        </td>
                      </tr>
                      
                    </table>
                  </td>
                  
                  <td width="21" align="left" valign="top">
                    <!-- Global Spacer for Alignment -->
                    <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="21" height="15"><BR>
                  </td>