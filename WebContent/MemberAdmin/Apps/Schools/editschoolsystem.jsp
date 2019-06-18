<%@ page language="java"
        session="true"
        import="java.util.*,com.awsd.security.*,com.awsd.school.*,com.awsd.personnel.*,
                java.text.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%!User usr = null;
  School school = null;
  Personnel principal = null;
  int principalID;
  Personnel viceprincipal = null;
  int viceprincipalID;
  Personnel tmp = null;
  DistrictPersonnel personnel = null;
  Iterator iter = null;%>
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

%>
<html>
  <head>
    <title>Members Admin - School System Edit</title>
    <link href="/MemberServices/css/memberadmin.css" rel="stylesheet">
  </head>

<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" background="/MemberServices/MemberAdmin/images/bg.gif">
<form name="modsys" action="schoolSystemAdmin.html" method="post">
<html:hidden property="op" value="mod-confirm" />
<html:hidden name="SCHOOLSYSTEM" property="schoolSystemID" />
<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
    <td width="100%" valign="top" bgcolor="#333333">
      <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
    </td>
  </tr>
  <tr>
      <td width="100%" valign="top">
          <center>
            <table width="70%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td width="100%" valign="top">
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="40"><BR>
                  <hr noshade="noshade" color="#333333" size="2" width="100%" align="right">
                  <span class="header1">School System Edit</span><BR>
                  <hr noshade="noshade" color="#333333" size="2" width="100%" align="right">
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
                  <table align="center" width="100%" cellpadding="5" cellspacing="0" border="0">
                    <tr>
                      <td  valign="middle" >
                      <span class="title"><font style="font-weight:bold;">School System Name:</font></span><BR>
                      </td>
                      <td><html:text name="SCHOOLSYSTEM" property="schoolSystemName" style="width:300px;" /></td>
                    </tr>
                    <tr>
                      <td  width="30%" valign="middle" >
                        <span class="title"><font style="font-weight:bold;">Administrator</font></span>
                      </td>
                      <td>
                        <logic:notEmpty name="SCHOOLSYSTEM" property="schoolSystemAdmin">
                          <html:select name="SCHOOLSYSTEM" property="schoolSystemAdmin.personnelID" size="1">
                            <html:option value="-1">SELECT ADMINISTRATOR</html:option>
                            <html:options collection="PERSONNEL" property="personnelID" labelProperty="display" /> 
                          </html:select>
                       </logic:notEmpty>
                       <logic:empty name="SCHOOLSYSTEM" property="schoolSystemAdmin">
                          <html:select property="schoolSystemAdmin.personnelID" size="1" value="-1">
                            <html:option value="-1">SELECT ADMINISTRATOR</html:option>
                            <html:options collection="PERSONNEL" property="personnelID" labelProperty="display" /> 
                          </html:select>
                       </logic:empty>
                      </td>
                    </tr>
                    <tr>
                      <td  width="30%" valign="top" >
                        <span class="title"><font style="font-weight:bold;">Backup Administrator</font></span>
                      </td>
                      <td>
                        <logic:notEmpty name="SCHOOLSYSTEM" property="schoolSystemAdminBackup">
                          <html:select name="SCHOOLSYSTEM" property="schoolSystemAdminBackupAsIntArray" size="10" multiple="true">
                            <html:option value="-1">SELECT BACKUP ADMINISTRATOR</html:option>
                            <html:options collection="PERSONNEL" property="personnelID" labelProperty="display" /> 
                          </html:select>
                        </logic:notEmpty>
                        <logic:empty name="SCHOOLSYSTEM" property="schoolSystemAdminBackup">
                          <html:select property="schoolSystemAdminBackupAsIntArray" size="10" value="-1" multiple="true">
                            <html:option value="-1">SELECT BACKUP ADMINISTRATOR</html:option>
                            <html:options collection="PERSONNEL" property="personnelID" labelProperty="display" /> 
                          </html:select>
                        </logic:empty>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td align="left" colspan="2">
                  <hr noshade="noshade" color="#333333" size="1" width="100%" align="right">
                  
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
                        <img name="add" src="/MemberServices/MemberAdmin/images/update_01.jpg" 
                            onmouseover="src='/MemberServices/MemberAdmin/images/update_02.jpg'" 
                            onmouseout="src='/MemberServices/MemberAdmin/images/update_01.jpg'" 
                            onmousedown="src='/MemberServices/MemberAdmin/images/update_03.jpg'" 
                            onmouseup="src='/MemberServices/MemberAdmin/images/update_02.jpg'" 
                            onclick="document.forms[0].submit();"><BR>
                      </td>
                    </tr>
                  </table>
                  <hr noshade="noshade" color="#333333" size="1" width="100%" align="right"> 
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