<%@ page language="java"
         import="com.awsd.security.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
  User usr = (User) session.getAttribute("usr");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <style type="text/css">@import 'includes/home.css';</style>
  </head>
  
  <body>

  <esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
  
<!--
	// Top Nav/Logo Container
	// This will be included
-->

  <table class="all_content" cellpadding="0" cellspacing="0" border="0" align="center">
    <tr>
      <td>   
        <jsp:include page="admin_top_nav.jsp" flush="true"/>
        <table width="760" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="760" align="left" valign="top">
              <table width="760" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <%if(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW")){%>                  
                    <jsp:include page="admin_side_nav.jsp" flush="true"/>
                  <%}else if(usr.getUserPermissions().containsKey("PERSONNEL-PRINCIPAL-VIEW")){%>
                    <jsp:include page="admin_principal_side_nav.jsp" flush="true"/>
                  <%}else if(usr.getUserPermissions().containsKey("PERSONNEL-VICEPRINCIPAL-VIEW")){%>
                    <jsp:include page="admin_viceprincipal_side_nav.jsp" flush="true"/>
                  <%}%>
                  <td width="551" align="left" valign="top">		
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="391" align="left" valign="top" style="padding-top:8px;">
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td class="displayPageTitle"><BR>Synchronization Manager</td>
                            </tr>
                            
                            <tr style="padding-top:3px;">
                              <td class="displayText"><%=(String)request.getAttribute("msg")%></td>
                            </tr>
                            
                          </table>
                        </td>
                        <td width="160" align="left" valign="top" style="padding:0;">
                          <img src="images/man1.gif"><BR>
                        </td>
                      </tr>
                    </table>
                  </td>						
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <jsp:include page="footer.jsp" flush="true" />
  </body>
</html>
