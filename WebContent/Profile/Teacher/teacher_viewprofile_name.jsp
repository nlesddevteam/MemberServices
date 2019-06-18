<%@ page language="java"
        session="true"
         import="com.awsd.security.*, 
                 java.util.*,com.awsd.school.*,com.awsd.personnel.*"%>

<%!
  User usr = null;
  Personnel p = null;
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

  p = usr.getPersonnel();
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
    <form  name='modifyName' action='modifyPersonnelName.html?id=<%=p.getPersonnelID()%>' method='POST'>
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
          <img src="images/teacher_profile_nav_1.gif"><br>
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
                <img src="images/teacher_profile_folder_top_name.gif"><br>
              </td>
            </tr>
            <tr>
              <td align="center" valign="top" background="images/teacher_profile_folder_bg.gif" width="538">
                <table>
                  <tr id="RowView" style="display:inline">
                    <td align="center" valign="top">
                      <font color="#003366" style="font-weight:bold">First Name:&nbsp</font><%= p.getFirstName()%>&nbsp;<img src="images/spacer.gif" width="10">
                      <font color="#003366" style="font-weight:bold">Last Name:&nbsp</font><%= p.getLastName()%><BR><BR>
                      <input type='button' value="Edit" onclick="View_Edit(false);">
                    </td>
                  </tr>
                  <tr id="RowEdit" style="display:none">
                    <td align="center">
                      <font color="#003366" style="font-weight:bold">First Name:&nbsp</font><input type='text' name='firstname' value='<%=p.getFirstName()%>'>
                        <img src="images/spacer.gif" width="10"><font color="#003366" style="font-weight:bold">Last Name:&nbsp</font><input type='text' name='lastname' value='<%=p.getLastName()%>'><br><br>
                        <input type='submit' value='Sumbit' onclick="return validateName(document.modifyName);">&nbsp;<input type='button' value="Cancel" onclick="View_Edit(true);">
                    </td>
                  </tr>
                </table>
              </td>
              <td width="26" align="right">
                <img src="images/teacher_profile_folder_side.gif"><br>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <img src="images/teacher_profile_folder_bottom.gif"><br><BR>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td colspan="2" align="right">
          <input type="button" value="Next" onclick="document.location.href='viewCurrentTeacherProfile.html?nav=2';"><img src="images/spacer.gif" width="25">
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
