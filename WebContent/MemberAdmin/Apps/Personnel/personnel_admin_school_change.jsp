<%@ page language="java"
        session="true"
        import="java.util.*,com.awsd.security.*,com.awsd.personnel.*;"%>
                
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%!User usr = null;
  Personnel p = null;
  PersonnelCategories categories = null;
  PersonnelCategory cat = null;
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
    <title>Members Admin - Personnel Administration Change</title>
    <link href="/MemberServices/css/memberadmin.css" rel="stylesheet">
  </head>

<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" background="/MemberServices/MemberAdmin/images/bg.gif">
<form name="change" action="/MemberServices/MemberAdmin/Apps/Personnel/personnelAdminChangeSchool.html?update" method="post">
<html:hidden name="Personnel" property="personnelID" />
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
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <span class="header1">Personnel Administration - School Change</span><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
                  <table align="center" width="100%" cellpadding="5" cellspacing="0" border="0">
                    <tr>
                      <td  valign="middle" >
                      <span class="title"><font style="font-weight:bold;">Name:</font></span><BR>
                      </td>
                      <td><bean:write name="Personnel" property="fullName" /></td>
                    </tr>
                    <tr>
                      <td  width="30%" valign="middle" >
                      <span class="title"><font style="font-weight:bold;">School</font></span>
                      </td>
                      <td>
                        <logic:notEmpty name="Personnel" property="school">
                          <html:select name="Personnel" property="school.schoolID" size="1">
                          <html:option value="-1">PLEASE SELECT</html:option>
                            <html:option value="277">District/Avalon Regional Office</html:option>
                            <html:option value="279">Central Regional Office</html:option>
                            <html:option value="280">Western Regional Office</html:option>
                            <html:option value="278">Labrador Regional Office</html:option>
                            <html:option value="-1">----SCHOOLS----</html:option>
                            <html:options collection="ALL_SCHOOLS" property="schoolID" labelProperty="schoolName" /> 
                          </html:select>
                        </logic:notEmpty>
                        <logic:empty name="Personnel" property="school">
                          <html:select property="school.schoolID" size="1" value="-1">
                           <html:option value="-1">PLEASE SELECT</html:option>
                            <html:option value="277">District/Avalon Regional Office</html:option>
                            <html:option value="279">Central Regional Office</html:option>
                            <html:option value="280">Western Regional Office</html:option>
                            <html:option value="278">Labrador Regional Office</html:option>
                            <html:option value="-1">----SCHOOLS----</html:option>
                            <html:options collection="ALL_SCHOOLS" property="schoolID" labelProperty="schoolName" /> 
                          </html:select>
                        </logic:empty>
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
                        <img name="add" src="/MemberServices/MemberAdmin/images/update_01.jpg" 
                            onmouseover="src='/MemberServices/MemberAdmin/images/update_02.jpg'" 
                            onmouseout="src='/MemberServices/MemberAdmin/images/update_01.jpg'" 
                            onmousedown="src='/MemberServices/MemberAdmin/images/update_03.jpg'" 
                            onmouseup="src='/MemberServices/MemberAdmin/images/update_02.jpg'" 
                            onclick="document.change.submit();"><BR>
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