<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="STUDENT-TRAVEL-ADMIN-VIEW,STUDENT-TRAVEL-PRINCIPAL-VIEW" />

<%
  User usr = (User) session.getAttribute("usr");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Newfoundland &amp; Labrador English School District - Member Services - Student Travel Administration</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <style type="text/css">@import 'css/home.css';</style>
</head>
<body style="margin-top:15px;">

<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <jsp:include page="header.jsp" flush="true" />
  <table width="760" cellpadding="0" cellspacing="0" border="0"  align="center">
    <tr>
      <td>   
        <table width="760" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="760" align="left" valign="top">
              <table width="760" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td width="228" align="left" valign="top" 
                      style="padding-top:10px;padding-left:5px;border-left:solid 1px #e0e0e0;border-right:solid 1px #FFB700;">
                    <jsp:include page="side_nav.jsp" flush="true"/>
                    
                    <img src="images/spacer.gif" width="1" height="10"><BR>
                  </td>
                  <td width="532" align="left" valign="top" style='border-right:solid 1px #e0e0e0; padding:10px;'>		
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="100%" align="left" valign="top">
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td class="displayPageTitle">Welcome</td>
                            </tr>
                            
                            <tr style="padding-top:3px;">
                              <td class="displayText">Select an action from the <span class="displayBoxTitle">Quick Links</span> menu on the left.</td>
                            </tr>
                            
                          </table>
                          
                          <%if(request.getAttribute("msg") != null){%>
                            <p style='border:solid 1px #FF0000; color:#ff0000;padding:3px;' align="left">
                              <table cellpadding="0" cellspacing="0">
                                <tr>
                                  <td class="messageText" align="left"><%=(String)request.getAttribute("msg")%></td>
                                </tr>
                              </table>
                            </p>
                          <%}%>
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
