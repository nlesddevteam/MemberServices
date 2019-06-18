<%@ page language="java"
         session="true"
         import="java.util.*,com.awsd.security.*,com.awsd.personnel.*,com.awsd.school.*"%>

<%!
  User usr = null;
  Role r = null;
  Permission p = null;
  Personnel per = null;
  //School s = null;
  
  Iterator iter = null;
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
  
  r = (Role) request.getAttribute("Role");
%>

<html>
  <head>
    <title></title>
    <link href="../css/memberadmin.css" rel="stylesheet">
    <script language="JavaScript" src="../js/common.js"></script>
    <script language="JavaScript" src="js/common.js"></script>
    <style type="text/css">
    	@media print {
  			.noprint {display:none;}
  		}
    </style>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" background="images/bg.gif">
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
        <img src="images/spacer.gif" width="1" height="10"><BR>
      </td>
    </tr>
    <tr>
      <td width="100%" valign="top">
          <center>
            <table width="80%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td width="100%" valign="top">
                  <img src="images/spacer.gif" width="1" height="10"><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="left">
                  <span class="header1">View Role</span>
                  <img src="images/spacer.gif" width="1" height="10"><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="left">
                  <table>
                    <tr>
                      <td>
                        <span class="header2">Unique Identifier:</span>      
                      </td>
                      <td>
                        <%=r.getRoleUID()%>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <span class="header2">Description:</span>
                      </td>
                      <td>
                        <%=r.getRoleDescription()%>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <span class="header2">Applied Permissions:</span>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="2" align="right" width="100%">
                        <table width="100%">                          
                            <% 
                              iter = (r.getRolePermissions()).entrySet().iterator();
                              while(iter.hasNext()) 
                              {
                                p = (Permission) (((Map.Entry)iter.next()).getValue());
                            %>  <tr>
                                <td bgcolor="#f4f4f4" valign="middle">
                                  <%=p.getPermissionUID()%>
                                </td>
                                <td>
                                  <a href="removeRolePermission.html?rid=<%=r.getRoleUID()%>&pid=<%=p.getPermissionUID()%>">REMOVE</a>
                                </td>
                                </tr>
                            <%}%>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td width="100%" colspan="2" align="left">
                        <span class="header2">Membership: </span><font size="1">(total=<%=r.getRoleMembership().size()%>)</font>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="2" align="right" width="100%">
                        <table width="100%">                          
                            <% 
                              //iter = (r.getRoleMembership()).entrySet().iterator();
                              iter = r.getRoleMembershipList().iterator();
                              while(iter.hasNext()) 
                              {
                                //per = (Personnel) (((Map.Entry)iter.next()).getValue());
                                per = (Personnel) iter.next();
                                //s = per.getSchool();
                            %>  <tr>
                                <td bgcolor="#f4f4f4" valign="middle">
                                  <%=per.getFullName()%> <br /> (ID: <%=per.getPersonnelID()%> - <%=per.getUserName()%>)
                                </td>
                                <td class='noprint'>
                                  <a href="removeRoleMember.html?rid=<%=r.getRoleUID()%>&pid=<%=per.getPersonnelID()%>">REMOVE</a> | 
                                  <a href="" onclick="top.document.location.href='../loginAs.html?pid=<%=per.getPersonnelID()%>'; return false;">LOGIN AS</a>
                                </td>
                                </tr>
                            <%}%>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr class='noprint'>
                      <td align="right" colspan="2">
                        <hr noshade color="#333333" size="1" width="100%" align="right">
                        <img name="permission" src="images/permissions_01.jpg" 
                             onmouseover="src='images/permissions_02.jpg'" 
                             onmouseout="src='images/permissions_01.jpg'" 
                             onmousedown="src='images/permissions_03.jpg'" 
                             onmouseup="src='images/permissions_02.jpg'" 
                             onclick="openWindow('RolePermissions', 'rolePermissions.html?uid=<%=r.getRoleUID()%>', 460, 340, 0);">
                        &nbsp;
                        <img name="membership" src="images/membership_01.jpg" 
                             onmouseover="src='images/membership_02.jpg'" 
                             onmouseout="src='images/membership_01.jpg'" 
                             onmousedown="src='images/membership_03.jpg'" 
                             onmouseup="src='images/membership_02.jpg'" 
                             onclick="openWindow('RoleMembership', 'roleMembership.html?uid=<%=r.getRoleUID()%>',550, 340, 0);">
                             &nbsp;
                        <img name="modify" src="images/modify_01.jpg" 
                             onmouseover="src='images/modify_02.jpg'" 
                             onmouseout="src='images/modify_01.jpg'" 
                             onmousedown="src='images/modify_03.jpg'" 
                             onmouseup="src='images/modify_02.jpg'" 
                             onclick="openWindow('ModifyRole', 'modifyRole.html?ouid=<%=r.getRoleUID()%>', 400, 280, 0);">
                        &nbsp;
                        <img name="delete" src="images/delete_01.jpg" 
                             onmouseover="src='images/delete_02.jpg'" 
                             onmouseout="src='images/delete_01.jpg'" 
                             onmousedown="src='images/delete_03.jpg'" 
                             onmouseup="src='images/delete_02.jpg'" 
                             onclick="openWindow('DeleteRole', 'deleteRole.html?uid=<%=r.getRoleUID()%>', 400, 260, 0);"><BR>
                        <hr noshade color="#333333" size="1" width="100%" align="right">
                      </td>
                    </tr>
            </table>
          </center>
      </td>
    </tr>
    </table>
  </body>
</html>
