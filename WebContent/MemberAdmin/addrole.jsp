<%@ page language="java" 
         session="true"
         import="com.awsd.security.*"%>

<%!
  User usr = null;
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
<%}%>



<html>
  <head>
    <title></title>
    <link href="../css/memberadmin.css" rel="stylesheet">
    <script language="JavaScript" src="../js/common.js"></script>
    <script language="JavaScript" src="js/common.js"></script>
    <script language="JavaScript">
      function addrole()
      {
        if(validateRole(document.roles)==true)
        {
          onClickAdd(document.roles);
          parent.menu.location.reload(true);
        }
      }
    </script>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" background="images/bg.gif">
    <form name="roles" action="addRole.html" method="post">
    <table name="content" width="550" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td width="100%" valign="top" bgcolor="#333333">
        <img src="images/spacer.gif" width="1" height="10"><BR>
      </td>
    </tr>
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
                  <span class="header1">Add New Role</span><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <img src="images/spacer.gif" width="1" height="10"><BR>
                  <table>
                    <tr>
                      <td>
                        <span class="header2">Unique Identifier:</span>      
                      </td>
                      <td>
                        <input type="text" name="uid" size="30" value="">
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <span class="header2">Description:</span>
                      </td>
                      <td>
                        <textarea name="description" cols="30" rows="5"></textarea><BR>
                      </td>
                    </tr>
                    
                    
                  </table>
                </td>
              </tr>
              <tr>
                      <td align="right" colspan="2">
                        <hr noshade color="#333333" size="1" width="100%" align="right">
                        <table>
                          <tr>
                            <td>
                              <img name="processing" src="images/spacer.gif" align="left">
                              <font color="#FF0000"><b>
                                <% if(request.getAttribute("msg") != null) { %>
                                  <%= request.getAttribute("msg") %>
                                <% } %>
                              </b></font>
                            </td>
                            <td>
                              <img name="add" src="images/add_01.jpg" 
                                onmouseover="src='images/add_02.jpg'" 
                                onmouseout="src='images/add_01.jpg'" 
                                onmousedown="src='images/add_03.jpg'" 
                                onmouseup="src='images/add_02.jpg'" 
                                onclick="addrole();"><BR>
                            </td>
                          </tr>
                        </table>
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