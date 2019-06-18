<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.personnel.v2.site.constant.*,
                  com.awsd.security.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>


<%
  User usr = (User) session.getAttribute("usr");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Eastern School District - Member Services - Personnel Package (v2.0)</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <style type="text/css">@import 'css/home.css';</style>
  
</head>
<body>

  <esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
  
<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <table width="760" cellpadding="0" cellspacing="0" border="0" style="border:solid 1px #000000;" align="center">
    <tr>
      <td>   
        <jsp:include page="includes/top_nav.jsp" flush="true" />
        <table width="760" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="760" align="left" valign="top">
              <table width="760" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <jsp:include page="includes/side_nav.jsp" flush="true" />
                  <td width="551" align="left" valign="top">		
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="100%" align="left" valign="top" style="padding:0px;">
                          <img src="images/home_intro.gif"><BR>
                        </td>
                      </tr>
                    </table>
                  </td>						
                </tr>
              </table>
              <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="30"><BR>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td style="border-top:solid 1px #000000;">
        <jsp:include page="includes/footer.jsp" flush="true" />
      </td>
    </tr>
  </table>
</body>
</html>
