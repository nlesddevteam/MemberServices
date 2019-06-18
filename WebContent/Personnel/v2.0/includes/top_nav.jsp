<%@ page language="java"  
         import="java.util.*, 
                 java.text.*,
                 com.esdnl.personnel.v2.site.constant.*" 
         isThreadSafe="false"%>

<%
  int activeTab = TabConstants.TAB_HOME;
  if(request.getParameter("activeTab") != null)
    activeTab = Integer.parseInt(request.getParameter("activeTab"));
 
  int activeSubTab = TabConstants.TAB_UNKNOWN;
  if(request.getParameter("activeSubTab") != null)
    activeSubTab = Integer.parseInt(request.getParameter("activeSubTab"));
  
  
  String img_logo = null;
  String img_spacer = null;
  String img_head = null;
  String img_search = null;
  String bgcolor = null;
  String p_title = null;
  
  switch(activeTab)
  {
    case TabConstants.TAB_HOME:
      img_logo = "home_top_logo.gif";
      img_spacer = "black_spacer.gif";
      img_head = "home_topnav_head.jpg";
      img_search = "home_search_selected.gif";
      bgcolor="#000000";
      break;
    case TabConstants.TAB_ADMIN:
      img_logo = "admin_top_logo.gif";
      img_spacer = "admin_spacer.gif";
      img_search = "admin_search_selected.gif";
      bgcolor="#FFB700";
      p_title = "Administration";
      break;
    case TabConstants.TAB_STAFF:
      img_logo = "staff_top_logo.gif";
      img_spacer = "staff_spacer.gif";
      img_search = "staff_search_selected.gif";
      bgcolor="#00833B";
      p_title = "Support Staff";
      break;
    case TabConstants.TAB_TEACHERS:
      img_logo = "teacher_top_logo.gif";
      img_spacer = "teacher_spacer.gif";
      img_search = "teacher_search_selected.gif";
      bgcolor="#0A218C";
      p_title = "Teachers";
      break;
    case TabConstants.TAB_SUBSTITUTES:
      img_logo = "subs_top_logo.gif";
      img_spacer = "subs_spacer.gif";
      img_search = "subs_search_selected.gif";
      bgcolor="#FF3E00";
      p_title = "Substitutes";
      break;
    
  }
%>
    
        <table width="760" cellpadding="0" cellspacing="0" border="0">
          <tr height="79">
            <td width="760" align="left" valign="top" style="background-color:<%=bgcolor%>;">
              <table width="760" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td width="154" align="left" valign="top">
                    <img src="/MemberServices/Personnel/v2.0/images/<%=img_logo%>"><BR>
                  </td>
                  <td width="396" align="left" valign="bottom">
                    <table width="100%" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="100%" align="center" valign="bottom">
                          <%=((p_title != null)?("<h2 style='color:#FFFFFF;font-family:Arial, Helvetica, sans-serif;'>"+p_title+"</h2>"):"")%>
                        </td>
                      </tr>
                      <tr>
                        <td width="100%" align="center" valign="top">
                          <span class="displayWhiteDate"><%=(new SimpleDateFormat("EEEE, MMMM dd, yyyy")).format(Calendar.getInstance().getTime())%></span>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <!-- **BEGINS** Help & Support / Search of Top Logo -->
                  <td width="210" align="right" valign="middle">
                    <table width="210" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="210" align="left" valign="middle" colspan="2">
                          <input type="text" class="inputSearchBox">
                        </td>
                      </tr>
                      <tr>
                        <td colspan="2" align="left" valign="middle">
                          <input type="radio"> <span class="displaySearchBy">By Competition #</span><BR>
                        </td>        
                      </tr>
                      <tr>
                        <td width="150" align="left" valign="middle">
                          <input type="radio"> <span class="displaySearchBy">By Location</span><BR>
                        </td>
                        <td width="60" align="left" valign="middle" rowspan="2">
                          <img src="/MemberServices/Personnel/v2.0/images/search_go.gif"><BR>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <!-- **ENDS** Help & Support / Search of Top Logo -->
                </tr>
              </table>				
            </td>
          </tr>
          <tr>
            <!-- **BEGINS** Top Nav Menu / Buttons for Support / Search -->
            <td width="760" align="left" valign="top">
              <table width="760" height="44" cellpadding="0" cellspacing="0" border="0" style="width: 760px; height: 44px; background: #DEDFDE url('/MemberServices/Personnel/v2.0/images/<%=img_spacer%>'); background-position: bottom; background-repeat: repeat-x;">
                <tr>
                  <td width="10" align="left" valign="bottom">
                    <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="10" height="1"><BR>
                  </td>
                  
                  <!-- HOME -->
                  <%if(activeTab == TabConstants.TAB_HOME){%>
                    <td width="106" align="left" valign="bottom">
                      <img src="/MemberServices/Personnel/v2.0/images/home_selected.gif"><BR>
                    </td>
                  <%}else{%>
                    <td width="106" align="left" valign="bottom">
                      <a href="/MemberServices/Personnel/v2.0/"><img src="/MemberServices/Personnel/v2.0/images/home_deselected.gif" border="0"><BR></a>
                    </td>
                  <%}%>
                  
                  <td width="4" align="left" valign="bottom">
                    <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="4" height="1"><BR>
                  </td>
                  
                  
                  <!-- ADMIN -->
                  <%if(activeTab == TabConstants.TAB_ADMIN){%>
                    <td width="106" align="left" valign="bottom">
                      <img src="/MemberServices/Personnel/v2.0/images/admin_selected.gif"><BR>
                    </td>
                  <%}else{%>
                    <td width="106" align="left" valign="bottom">
                      <a href="/MemberServices/Personnel/v2.0/admin/"><img src="/MemberServices/Personnel/v2.0/images/admin_deselected.gif" border="0"><BR></a>
                    </td>
                  <%}%>
                  <td width="4" align="left" valign="bottom">
                    <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="4" height="1"><BR>
                  </td>
                  
                  
                  <!-- TEACHERS -->
                  <%if(activeTab == TabConstants.TAB_TEACHERS){%>
                    <td width="106" align="left" valign="bottom">
                      <img src="/MemberServices/Personnel/v2.0/images/teachers_selected.gif"><BR>
                    </td>
                  <%}else{%>
                    <td width="106" align="left" valign="bottom">
                      <a href="/MemberServices/Personnel/v2.0/teachers/"><img src="/MemberServices/Personnel/v2.0/images/teachers_deselected.gif" border="0"><BR></a>
                    </td>
                  <%}%>
                  <td width="4" align="left" valign="bottom">
                    <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="4" height="1"><BR>
                  </td>
                  
                  
                  
                  <%if(activeTab == TabConstants.TAB_SUBSTITUTES){%>
                    <td width="106" align="left" valign="bottom">
                      <img src="/MemberServices/Personnel/v2.0/images/subs_selected.gif"><BR>
                    </td>
                  <%}else{%>
                    <td width="106" align="left" valign="bottom">
                      <a href="/MemberServices/Personnel/v2.0/substitutes/"><img src="/MemberServices/Personnel/v2.0/images/subs_deselected.gif" border="0"><BR></a>
                    </td>
                  <%}%>
                  <td width="4" align="left" valign="bottom">
                    <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="4" height="1"><BR>
                  </td>
                  
                  <!-- STAFF -->
                  <%if(activeTab == TabConstants.TAB_STAFF){%>
                    <td width="106" align="left" valign="bottom">
                      <img src="/MemberServices/Personnel/v2.0/images/staff_selected.gif"><BR>
                    </td>
                  <%}else{%>
                    <td width="106" align="left" valign="bottom">
                      <a href="/MemberServices/Personnel/v2.0/staff/"><img src="/MemberServices/Personnel/v2.0/images/staff_deselected.gif" border="0"><BR></a>
                    </td>
                  <%}%>
                  
                  
                  <td width="474" align="left" valign="bottom">
                    <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="1"><BR>
                  </td>
                  
                  
                  <td width="180" height="44" align="left" valign="bottom">
                    <table width="180" height="44" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="180" align="right" valign="top">
                          <img src="/MemberServices/Personnel/v2.0/images/home_support_deselected.gif"><img src="/MemberServices/Personnel/v2.0/images/<%=img_search%>"><BR>
                        </td>
                      </tr>
                      <%if(activeTab == TabConstants.TAB_HOME){%>
                        <tr>
                          <td width="180" align="right" valign="bottom">
                            <img src="/MemberServices/Personnel/v2.0/images/<%=img_head%>"><BR>
                          </td>
                        </tr>
                      <%}%>
                    </table>
                  </td>						
                </tr>
                
              </table>
            </td>
            <!-- **ENDS** Top Nav Menu / Buttons for Support / Search -->
          </tr>
          
          <!-- Second row of tabs -->
          <%if(activeTab == TabConstants.TAB_ADMIN){%>
            <tr>
              <!-- **BEGINS** Top Nav Menu / Buttons for Support / Search -->
              <td width="760" align="left" valign="top">
                <table width="740" cellpadding="0" cellspacing="0" border="0" align="center" style="width: 740px;" >
                  <tr>
                    <td width="740" align="left" valign="bottom" style="border-left:solid 1px <%=bgcolor%>;border-right:solid 1px <%=bgcolor%>;">
                      <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="10"><BR>
                    </td>
                  </tr>
                </table>
                <table width="740" cellpadding="0" cellspacing="0" border="0" align="center" style="width: 740px; height:30px; background: #FFFFFF url('/MemberServices/Personnel/v2.0/images/<%=img_spacer%>'); background-position: top; background-repeat: repeat-x;">
                  <tr>
                    <td width="10" align="left" valign="bottom">
                      <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="10" height="1"><BR>
                    </td>
                    <%if(activeSubTab == TabConstants.TAB_ADMIN_EMPLOYMENT_OPPORTUNITIES){%>
                      <td width="106" align="left" valign="top">
                        <img src="/MemberServices/Personnel/v2.0/images/admin_employment_opportunities_selected.gif" border="0"><BR>
                      </td>
                    <%}else{%>
                      <td width="106" align="left" valign="top">
                        <a href="/MemberServices/Personnel/admin_index.jsp">
                          <img src="/MemberServices/Personnel/v2.0/images/admin_employment_opportunities_deselected.gif" border="0"><BR>
                        </a>
                      </td>
                    <%}%>
                    <td width="2" align="left" valign="bottom">
                      <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="1"><BR>
                    </td>
                    
                    <%if(activeSubTab == TabConstants.TAB_ADMIN_RECOGNITION_REQUEST){%>
                      <td width="106" align="left" valign="top">
                        <img src="/MemberServices/Personnel/v2.0/images/recognition_request_selected.gif"><BR>
                      </td>
                    <%}else{%>
                      <td width="106" align="left" valign="top">
                        <a href="/MemberServices/Personnel/v2.0/admin/recognition/">
                          <img src="/MemberServices/Personnel/v2.0/images/recognition_request_deselected.gif" border="0"><BR>
                        </a>
                      </td>
                    <%}%>
                    <td width="2" align="left" valign="bottom">
                      <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="1"><BR>
                    </td>
                    
                    <td width="*" align="left" valign="bottom">
                      <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="1"><BR>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          <%}else if(activeTab == TabConstants.TAB_SUBSTITUTES){%>
            <tr>
              <!-- **BEGINS** Top Nav Menu / Buttons for Support / Search -->
              <td width="760" align="left" valign="top">
                <table width="740" cellpadding="0" cellspacing="0" border="0" align="center" style="width: 740px;" >
                  <tr>
                    <td width="740" align="left" valign="bottom" style="border-left:solid 1px <%=bgcolor%>;border-right:solid 1px <%=bgcolor%>;background-color:#FFD1C1;">
                      <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="10"><BR>
                    </td>
                  </tr>
                </table>
                <table width="740" cellpadding="0" cellspacing="0" border="0" align="center" style="width: 740px; height:30px; background: #FFFFFF url('/MemberServices/Personnel/v2.0/images/<%=img_spacer%>'); background-position: top; background-repeat: repeat-x;">
                  <tr>
                    <td width="10" align="left" valign="bottom">
                      <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="10" height="1"><BR>
                    </td>
                    <!-- TEACHERS -->
                    <%if(activeSubTab == TabConstants.TAB_SUBSTITUTES_TEACHER){%>
                      <td width="106" align="left" valign="top">
                        <img src="/MemberServices/Personnel/v2.0/images/sub_teachers_selected.gif" border="0"><BR>
                      </td>
                    <%}else{%>
                      <td width="106" align="left" valign="top">
                        <a href="/MemberServices/Personnel/v2.0/substitutes/teachers/">
                          <img src="/MemberServices/Personnel/v2.0/images/sub_teachers_deselected.gif" border="0"><BR>
                        </a>
                      </td>
                    <%}%>
                    <td width="2" align="left" valign="bottom">
                      <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="1"><BR>
                    </td>
                    
                    <%if(activeSubTab == TabConstants.TAB_SUBSTITUTES_STUDENT_ASSISTANT){%>
                      <td width="106" align="left" valign="top">
                        <img src="/MemberServices/Personnel/v2.0/images/sub_stud_ass_selected.gif"><BR>
                      </td>
                    <%}else{%>
                      <td width="106" align="left" valign="top">
                        <a href="/MemberServices/Personnel/v2.0/substitutes/student_assistants/">
                          <img src="/MemberServices/Personnel/v2.0/images/sub_stud_ass_deselected.gif" border="0"><BR>
                        </a>
                      </td>
                    <%}%>
                    <td width="2" align="left" valign="bottom">
                      <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="1"><BR>
                    </td>
                    
                    <td width="*" align="left" valign="bottom">
                      <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="1"><BR>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          <%}%>
        </table>