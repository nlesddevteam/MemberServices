<%@ page language="java" 
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 java.util.*"%>

<%!User usr = null;
  PersonnelCategories categories = null;
  PersonnelCategory category = null;
  Iterator iter = null;%>

<%
	usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%
	}
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%
	}

  categories = new PersonnelCategories();
%>

<html>
  <head>
    <title></title>
    <link href="/MemberServices/css/memberadmin.css" rel="stylesheet">
    <script language="JavaScript" src="../../js/common.js"></script>
    <script language="JavaScript" src="../js/common.js"></script>
    <script language="JavaScript">
      function setLoginApp()
      {
        if(validatePermission(document.loginapp)==true)
        {
          onClickAdd(document.loginapp);
          //parent.menu.location.reload(true);
        }
      }
    </script>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" background="/MemberServices/MemberAdmin/images/bg.gif">
    <form name="loginapp" action="/MemberServices/MemberAdmin/Apps/setNextLoginApp.html" method="post">
    <table name="content" width="550" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td width="100%" valign="top" bgcolor="#333333">
        <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
      </td>
    </tr>
    <tr>
      <td width="100%">
        <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="50"><BR>
      </td>
    </tr>
    <tr>
      <td width="100%" valign="top">
          <center>
            <table width="75%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td width="100%" valign="top">
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="40"><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <span class="header1">Set Next Login Application</span><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
                  <table>
                    <tr>
                      <td>
                        <span class="header2">Group:</span>      
                      </td>
                      <td>
                        <SELECT name="group">
                          <OPTION VALUE="-1">PLEASE SELECT GROUP</OPTION>
                        <%
                        	iter = categories.iterator();
                                                   while(iter.hasNext())
                                                   {
                                                      category = (PersonnelCategory) iter.next();
                        %>    <OPTION value="<%=category.getPersonnelCategoryID()%>"><%=category.getPersonnelCategoryName()%></OPTION>
                        <% } %>
                        </SELECT>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <span class="header2">Application:</span>
                      </td>
                      <td>
                        <SELECT name="app">
                          <OPTION VALUE="-1">PLEASE SELECT APPLICATION</OPTION>
                          <OPTION value="NONE">NONE</OPTION>
                          <OPTION value="PROFILE">PROFILE</OPTION>
                        </SELECT>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td align="left" colspan="2">
                  <hr noshade color="#333333" size="1" width="100%" align="right">
                  <table width="100%">
                    <tr>
                      <td align="left" width="75%">
                        <img name="processing" src="/MemberServices/MemberAdmin/images/spacer.gif" align="left">
                        <font color="#FF0000">
                          <b>
                            <% if(request.getAttribute("msg") != null) { %>
                              <%= request.getAttribute("msg") %>
                            <% } %>
                          </b>
                        </font>
                      </td>
                      <td align="right" width="25%">
                        <img name="add" src="/MemberServices/MemberAdmin/images/add_01.jpg" 
                            onmouseover="src='/MemberServices/MemberAdmin/images/add_02.jpg'" 
                            onmouseout="src='/MemberServices/MemberAdmin/images/add_01.jpg'" 
                            onmousedown="src='/MemberServices/MemberAdmin/images/add_03.jpg'" 
                            onmouseup="src='/MemberServices/MemberAdmin/images/add_02.jpg'" 
                            onclick="document.loginapp.submit();"><BR>
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