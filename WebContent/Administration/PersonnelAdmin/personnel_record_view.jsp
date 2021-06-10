<%@ page language="java"
        session="true"
        import="java.util.*,com.awsd.security.*,com.awsd.personnel.*"%>
<%!
  User usr = null;
  Iterator iter = null;
  Personnel tmp = null;
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

  tmp = (Personnel) request.getAttribute("RECORD");
%>
<html>
  <head>
    <title>Members Admin - Personnel Summary</title>
    <link href="/MemberServices/css/memberadmin.css" rel="stylesheet">
  </head>

<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" background="/MemberServices/MemberAdmin/images/bg.gif">
<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
    <td width="100%" valign="top" bgcolor="#333333">
      <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
    </td>
  </tr>
  <tr>
      <td width="100%" valign="top">
          <center>
            <table width="90%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td width="100%" valign="top">
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
                  <table width="100%" style="border:thin dashed #E0E0E0; background-color:#FFFFCC;">
                    <tr>
                      <td class="header1">
                        <%=tmp.getFullNameReverse()%>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <table align="center" width="100%" cellpadding="0" cellspacing="0"
                                style="border:thin dashed #E0E0E0; background-color:#FFFFCC;">
                                <tr>
                                  <td class="header">Category:</td>
                                  <td width="*"><%=tmp.getPersonnelCategory().getPersonnelCategoryName()%></td>
                                </tr>
                                <tr>
                                  <td class="header">Username:</td>
                                  <td width="*"><%=tmp.getUserName()%></td>
                                </tr>
                                <tr>
                                  <td class="header">Password:</td>
                                  <td width="*"><%=tmp.getPassword()%></td>
                                </tr>
                                <tr>
                                  <td class="header">Email:</td>
                                  <td width="*"><%=tmp.getEmailAddress()%></td>
                                </tr>

                                <%if(tmp.getSchool() != null){%>
                                  <tr>
                                    <td class="header">School:</td>
                                    <td width="*"><%=tmp.getSchool().getSchoolName()%></td>
                                  </tr>
                                <%}%>

                                <%if(tmp.getViewOnNextLogon() != null){%>
                                  <tr>
                                    <td class="header">Startup Application:</td>
                                    <td width="*"><%=tmp.getViewOnNextLogon()%></td>
                                  </tr>
                                <%}%>
                              </table>            
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td align="left" colspan="2">
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