<%@ page language="java"
         session="true"
         import="com.awsd.security.*"%>

<%!
  User usr = null;
  Permission p = null;
%>

<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}
  
  p = (Permission) request.getAttribute("Permission");
%>

<html>
  <head>
    <title></title>
    <link href="../css/memberadmin.css" rel="stylesheet">
    <script language="JavaScript" src="../js/common.js"></script>
    <script language="JavaScript" src="js/common.js"></script>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" background="images/bg.gif">
    <form name="permissions" action="" method="post">
    <table name="content" width="100%" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td width="100%" valign="top" bgcolor="#333333">
        <img src="images/spacer.gif" width="1" height="10"><BR>
      </td>
    </tr>
    </table>
    
    <table name="content" width="550" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td width="100%">
        <img src="images/spacer.gif" width="1" height="50"><BR>
      </td>
    </tr>
    <tr>
      <td width="100%" valign="top">
          <center>
            <table width="75%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td width="100%" valign="top">
                  <img src="images/spacer.gif" width="1" height="40"><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <span class="header1">View Permission</span><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <img src="images/spacer.gif" width="1" height="10"><BR>
                  <table>
                    <tr>
                      <td>
                        <span class="header2">Unique Identifier:</span>      
                      </td>
                      <td>
                        <%=p.getPermissionUID()%>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <span class="header2">Description:</span>
                      </td>
                      <td>
                        <%=p.getPermissionDescription()%>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                      <td valign="middle" align="right" colspan="2">
                        <hr noshade color="#333333" size="1" width="100%" align="right">
                        <img name="modify" src="images/modify_01.jpg" 
                             onmouseover="src='images/modify_02.jpg'" 
                             onmouseout="src='images/modify_01.jpg'" 
                             onmousedown="src='images/modify_03.jpg'" 
                             onmouseup="src='images/modify_02.jpg'" 
                             onclick="openWindow('ModifyPermission', 'modifyPermission.html?ouid=<%=p.getPermissionUID()%>', 400, 280, 0);">
                        &nbsp;
                        <img name="delete" src="images/delete_01.jpg" 
                             onmouseover="src='images/delete_02.jpg'" 
                             onmouseout="src='images/delete_01.jpg'" 
                             onmousedown="src='images/delete_03.jpg'" 
                             onmouseup="src='images/delete_02.jpg'" 
                             onclick="openWindow('DeletePermission', 'deletePermission.html?uid=<%=p.getPermissionUID()%>', 400, 260, 0);">
                        <hr noshade color="#333333" size="1" width="100%" align="right">
                      </td>
                    </tr>
            </table>
          </center>
      </td>
    </tr>
    </table>
    </form>
  </body>
</html>
