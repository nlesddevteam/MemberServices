<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*, java.util.*"%>

<%!
  User usr = null;
  Permissions  perms = null;
  Permission p = null;
  Role r = null;
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
  perms = new Permissions();
%>

<html>
  <head>
    <title></title>
    <link href="../css/memberadmin.css" rel="stylesheet">
    
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" background="images/bg.gif">
    <table width="100%" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td width="100%" valign="top" bgcolor="#333333">
          <img src="images/spacer.gif" width="1" height="10"><BR>
        </td>
      </tr>
      <tr>
        <td width="100%">
          <img src="images/spacer.gif" width="1" height="10"><BR>
        </td>
      </tr>
      <tr>
        <td width="100%" valign="top" align="left">
          <form name="membership" action="rolePermissions.html" method="post">
          <input type="hidden" name="uid" value="<%=r.getRoleUID()%>">
          <input type="hidden" name="op" value="NONE">
          
            <center>
              <table width="90%" cellpadding="0" cellspacing="0">
                <tr>
                  <td>
                    <hr noshade color="#333333" size="2" width="100%" align="right">
                    <span class="header1">Role Permissions</span><BR>
                    <hr noshade color="#333333" size="2" width="100%" align="right">
                    <img src="images/spacer.gif" width="1" height="10"><BR>

                    <table width="350" cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="150" valign="top" align="center">
                          <span class="header2">Available</span><BR>
                          <select size="10" name="available">
                          <%
                            iter = perms.iterator(); 
                            while(iter.hasNext())
                            {
                              p = (Permission) iter.next();
                              if(!r.getRolePermissions().containsKey(p.getPermissionUID()))
                              {
                          %>    <option value="<%=p.getPermissionUID()%>"><%=p.getPermissionUID()%></option>
                          <%  }
                            }
                          %>
                          </select>
                        </td>
                        <td width="50" valign="middle" align="center">
                          <img src="images/button_right_01.jpg"
                            onMouseover="src='images/button_right_02.jpg'"
                            onMouseout="src='images/button_right_01.jpg'"
                            onMousedown="src='images/button_right_03.jpg'"
                            onMouseup="src='images/button_right_02.jpg'"
                            onClick="document.membership.op.value='ADD'; document.membership.submit();"><BR><BR><BR>
                          <img src="images/button_left_01.jpg"
                            onMouseover="src='images/button_left_02.jpg'"
                            onMouseout="src='images/button_left_01.jpg'"
                            onMousedown="src='images/button_left_03.jpg'"
                            onMouseup="src='images/button_left_02.jpg'"
                            onClick="document.membership.op.value='REMOVE'; document.membership.submit();">
                        </td>
                        <td width="150" valign="top" align="center">
                          <span class="header2">Assigned</span><BR>
                          <select size="10" name="assigned">
                          <%
                            iter = r.getRolePermissions().entrySet().iterator();
                            while(iter.hasNext())
                            {
                              p = (Permission) (((Map.Entry)iter.next()).getValue());
                          %>  <option value="<%=p.getPermissionUID()%>"><%=p.getPermissionUID()%></option>
                          <% } %>  
                          </select>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td align="right" colspan="2">
                    <img src="images/spacer.gif" width="1" height="10"><BR>
                    <hr noshade color="#333333" size="1" width="100%" align="right">                        
                    <img src="images/close_01.jpg" 
                      onmouseover="src='images/close_02.jpg';" 
                      onmouseout="src='images/close_01.jpg';"
                      onmousedown="src='images/close_03.jpg';"
                      onmouseup="src='images/close_02.jpg';"
                      onclick="self.opener.location.href='viewRole.html?uid=<%=r.getRoleUID()%>'; self.close();">
                    <hr noshade color="#333333" size="1" width="100%" align="right">
                  </td>
                </tr>
              </table>
            </center>
          </form>
        </td>
      </tr>
    </table>
  </body>
</html>
