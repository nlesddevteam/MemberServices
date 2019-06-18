<%@ page language="java"
        session="true"
         import="com.awsd.security.*, 
                 java.util.*,com.awsd.school.*,com.awsd.personnel.*"%>

<%!
  User usr = null;
  Grades grades = null;
  Grade g = null;
  Iterator iter = null;
  boolean hs;
  boolean hg;
  int col_cnt=0;
  boolean edit = false;
  final int NUM_COLS = 3;
  final int cell_width=538/NUM_COLS;
  String color_on="#FFFFFF";
  String color_off="#F8EDDE";
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("PERSONNEL-PROFILE-TEACHER-VIEW"))) {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}
  grades = new Grades(usr);
  if(request.getParameter("edit")!=null)
  {
    edit = true;
  }
  else
  {
    edit = false;
  }
  
  hs = false;
  hg = false;
%>
<html>
  <head>
    <title>Member Services - Teacher Profile</title>
    <style>
      td {
        font-family: Arial, Helvetica, sans-serif; 
        font-size: 12px; 
        line-height: 16px; 
        color: #000000;
      }
    </style>
    <script language="Javascript" src="js/common.js"></script>
  </head>
  <body>
    <form  name='modifyGrades' action='addTeacherGrade.html' method='POST'>
    <center>
    <table style="border-style:solid;border-color:#000000;border-width:1px;" width="750">
      <tr>
        <td>
          <img src="images/spacer.gif" height="10"><br>
          <img src="images/spacer.gif" width="10">
          <img src="images/profile_top_pt1.gif"><br>
          <img src="images/spacer.gif" height="10"><br>
        </td>
        <td valign="middle" align="right">
          <img src="images/teacher_profile_nav_3.gif"><br>
        </td>
      </tr>
      <tr>
        <td align="center" colspan="2">
            <font color="#003366" style="font-weight:bold">Please verify your information, and make any necessary changes.</font>
        </td>
      </tr>
      <tr>
        <td align="center" colspan="2">
          <table width="564" cellspacing="0" cellpadding="0">
            <tr>
              <td width="564" colspan="2">
                <img src="images/spacer.gif" height="10"><br>
                <img src="images/teacher_profile_folder_top_grades.gif"><br>
              </td>
            </tr>
            <tr>
              <td align="center" valign="top" background="images/teacher_profile_folder_bg.gif" width="538">
                <table width="538" cellspacing="0" cellpadding="0">
                  <tr id="RowView" style="display:<%=!edit?"inline":"none"%>">
                    <td align="center" valign="top" width="100%">
                      <table width="95%" cellspacing="0" cellpadding="0">
                        <tr>
                          <td colspan="<%=NUM_COLS%>">
                            <font color="#003366" style="font-weight:bold">Grade(s):</font>
                          </td>
                        </tr>
                        <% iter = grades.iterator();
                           if(!iter.hasNext()) { 
                            hg = false;
                        %>
                        <tr>
                          <td colspan="<%=NUM_COLS%>">
                            <font color="#FF0000" style="font-weight:bold">No Grade(s) Entered.</font>
                          </td>
                        </tr>
                        <% } else { 
                          hg = true;
                        %>
                        <tr>
                        <%  col_cnt=0;
                            while(iter.hasNext())
                            {
                              g = (Grade) iter.next();
                        %>    <td width="<%=cell_width%>">
                                <%=g.getGradeName()%>
                              </td>
                        <%    if(++col_cnt == NUM_COLS) { 
                                col_cnt = 0;
                        %>      </tr><tr>
                        <%    }
                            }                         
                            if(col_cnt != NUM_COLS)
                            {
                              for(int i=col_cnt; i < NUM_COLS; i++) { %>
                                <td width="<%=cell_width%>">
                                  &nbsp;
                                </td>
                          <%  }
                            }%>
                        </tr>
                        <%}%>
                        <tr>
                          <td align="center" colspan="<%=NUM_COLS%>">
                            <input type='button' value="Edit" onclick="View_Edit(false);">
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr id="RowEdit" style="display:<%=edit?"inline":"none"%>">
                    <td align="center">
                      <table width="95%" cellspacing="0" cellpadding="0">
                        <tr>
                          <td colspan="<%=NUM_COLS%>">
                            <font color="#003366" style="font-weight:bold">Grade(s):</font>
                          </td>
                        </tr>
                        <% iter = grades.iterator();
                           if(!iter.hasNext()) { %>
                        <tr>
                          <td colspan="<%=NUM_COLS%>">
                            <font color="#FF0000" style="font-weight:bold">No Grade(s) Entered.</font>
                          </td>
                        </tr>
                        <% } else { %>
                        <tr>
                        <%  col_cnt=0;
                            while(iter.hasNext())
                            {
                              g = (Grade) iter.next();
                        %>    <td width="<%=cell_width%>">
                                <table>
                                  <tr style="background-color:<%=color_off%>;" onmouseover="this.style.backgroundColor='<%=color_on%>';"
                                      onmouseout="this.style.backgroundColor='<%=color_off%>';">
                                    <td width="69%">
                                      <%=g.getGradeName()%>
                                    </td>
                                    <td width="*">
                                      <a href="deleteTeacherGrade.html?gid=<%=g.getGradeID()%>"><font size="1">[remove]</font></a>
                                    </td>
                                  </tr>
                                </table>                                
                              </td>
                        <%    if(++col_cnt == NUM_COLS) { 
                                col_cnt = 0;
                        %>      </tr><tr>
                        <%    }
                              if(g.getGradeName().equalsIgnoreCase("LEVEL I")
                                || g.getGradeName().equalsIgnoreCase("LEVEL II")
                                || g.getGradeName().equalsIgnoreCase("LEVEL III"))
                              {
                                hs = true;
                              }
                            }                         
                            if(col_cnt != NUM_COLS)
                            {
                              for(int i=col_cnt; i < NUM_COLS; i++) { %>
                                <td width="<%=cell_width%>">
                                  &nbsp;
                                </td>
                          <%  }
                            }%>
                        </tr>
                        <%}%>
                        <tr>
                          <td align="center" colspan="<%=NUM_COLS%>">
                            <BR><select name='gid'>
                              <option value="-1">PLEASE SELECT GRADE</option>
                              <%
                                grades = new Grades(true);
                                iter = grades.iterator();

                                while(iter.hasNext())
                                {
                                  g = (Grade) iter.next();
                              %>  <option value='<%=g.getGradeID()%>'><%=g.getGradeName()%></option>
                              <%}%>
                            </select>&nbsp;&nbsp;
                            <input type='submit' value='Add' onclick="return validateAdd(document.modifyGrades.gid);">&nbsp;<input type='button' value="Cancel" onclick="View_Edit(true);">
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
              <td width="26" align="right">
                <img src="images/teacher_profile_folder_side.gif"><br>
              </td>
            </tr>
            <tr>
              <td colspan="2" width="564">
                <img src="images/teacher_profile_folder_bottom.gif"><br><BR>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td colspan="2" align="right">
          <input type="button" value="Prev" onclick="document.location.href='viewCurrentTeacherProfile.html?nav=2';">
          <%if(hg){ 
              if(hs) {%>
                <input type="button" value="Next" onclick="document.location.href='viewCurrentTeacherProfile.html?nav=4';">
            <%} else { %>
                <input type="button" value="Finish" onclick="document.location.href='viewCurrentTeacherProfileFinish.html';">
            <%} 
          }%>
          <img src="images/spacer.gif" width="25">
        </td>
      <tr>
        <td colspan="2">
          <img src="images/teacher_profile_bottom.gif"><br><BR>
        </td>
      </tr>
    </table>
    </center>
    </form>
  </body>
</html>