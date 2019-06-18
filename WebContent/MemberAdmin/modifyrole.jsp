<%@ page language="java"
         session="true"
         import="com.awsd.security.*"%>

<%!
  User usr = null;
  Role r = null;
  boolean modified;
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
  modified = ((Boolean) request.getAttribute("modified")).booleanValue();
%>

<html>
  <head>
    <title></title>
    <link href="../css/memberadmin.css" rel="stylesheet">
    <script language="JavaScript" src="../js/common.js"></script>
    <script language="JavaScript" src="js/common.js"></script>
    <script language="JavaScript">
      function modifyrole()
      {
        if(validateRole(document.roles))
        {
          onClick(document.roles); 
        }
      }
    </script>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" background="images/bg.gif">
    <form name="roles" action="modifyRole.html" method="post">
    <input type="hidden" name="confirmed" value="true">
    <table name="content" width="100%" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td width="100%" valign="top" bgcolor="#333333">
        <img src="images/spacer.gif" width="1" height="10"><BR>
      </td>
    </tr>
    
    <tr>
      <td width="100%" valign="top">
          <center>
            <table width="75%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td width="100%" valign="top">
                  <img src="images/spacer.gif" width="1" height="10"><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <span class="header1">Modify Role</span><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <img src="images/spacer.gif" width="1" height="10"><BR>
                  <table>
                    <tr>
                      <td>
                        <span class="header2">Unique Identifier:</span>      
                      </td>
                      <td>
                        <% if(!modified) { %>
                          <input type="text" name="uid" size="39" value="<%=r.getRoleUID()%>">
                        <% } else { %>
                          <%=r.getRoleUID()%>
                        <% } %>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <span class="header2">Description:</span>
                      </td>
                      <td>
                        <% if(!modified) { %>
                          <textarea name="description" cols="30" rows="5"><%=r.getRoleDescription()%></textarea><BR>
                        <% } else { %>
                          <%=r.getRoleDescription()%>
                        <% } %>
                      </td>
                    </tr>
                    
                    <tr>
                      <td align="right" colspan="2">
                        <hr noshade color="#333333" size="1" width="100%" align="right">
                        <img name="processing" src="images/spacer.gif" align="left">
                        <font color="#FF0000"><b>
                          <% if(request.getAttribute("msg") != null) { %>
                            <%= request.getAttribute("msg") %>
                          <% } %>
                        </b></font>
                        <% if((request.getAttribute("msg") == null) || !modified) { %>
                          <input type="hidden" name="ouid" value="<%=r.getRoleUID()%>">
                    
                          <img name="confirm" src="images/confirm_01.jpg" 
                            onmouseover="src='images/confirm_02.jpg';" 
                            onmouseout="src='images/confirm_01.jpg';"
                            onmousedown="src='images/confirm_03.jpg';"
                            onmouseup="src='images/confirm_02.jpg';"
                            onclick="modifyrole();">
                        &nbsp;
                          <img name="cancel" src="images/cancel_01.jpg" 
                            onmouseover="src='images/cancel_02.jpg';" 
                            onmouseout="src='images/cancel_01.jpg';"
                            onmousedown="src='images/cancel_03.jpg';"
                            onmouseup="src='images/cancel_02.jpg';"
                            onclick="self.close();">
                        <% } else { %>
                          <img src="images/close_01.jpg" 
                            onmouseover="src='images/close_02.jpg';" 
                            onmouseout="src='images/close_01.jpg';"
                            onmousedown="src='images/close_03.jpg';"
                            onmouseup="src='images/close_02.jpg';"
                            onclick="refresh('<%=r.getRoleUID()%>'); self.close();">
                        <% } %>
                        <hr noshade color="#333333" size="1" width="100%" align="right">
                      </td>
                    </tr>
                  </table>
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