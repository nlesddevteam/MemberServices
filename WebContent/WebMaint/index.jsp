<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>
                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="WEBMAINTENANCE-VIEW" />

<%
  User usr = (User) session.getAttribute("usr");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Web Maintenance - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/webmaint.css";</style>
    <script language="JavaScript" src="js/common.js"></script>
	</head>
	<body style="margin-top:-30px;">
    <form>
      <table width="800" height="650" cellpadding="0" cellspacing="0" align="center" valign="top">
        <tr>
          <td width="100%" height="100%" id="maincontent" align="left" valign="top" style="background: url(images/webmaint_bkg.jpg) top left no-repeat;">
            <img src="../images/spacer.gif" width="1" height="125"><br>
            <table width="50%" height="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
               <tr>
                <td width="100%" align="left" valign="top">
                  
                  <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-DIRECTORSWEB">
                    <h2>Director's Website</h2>
                    <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
                      <tr>
                        <td width="25%" align="right" valign="middle">
                          &nbsp;
                        </td>
                        <td width="25%" align="right" valign="middle">
                          <img src="images/btn_directors_report_01.gif"
                               onmouseover="src='images/btn_directors_report_02.gif';"
                               onmouseout = "src='images/btn_directors_report_01.gif';"
                               onclick="document.location.href='CEOWeb/viewDirectorReports.html';"><br>
                        </td>
                        <td width="25%" align="right" valign="middle">
                          <img src="images/btn_school_visits_01.gif"
                               onmouseover="src='images/btn_school_visits_02.gif';"
                               onmouseout = "src='images/btn_school_visits_01.gif';"
                               onclick="document.location.href='CEOWeb/viewSchoolVisits.html';"><br>
                        </td>
                        <td width="25%" align="right" valign="middle">
                          <img src="images/btn_messages_01.gif"
                               onmouseover="src='images/btn_messages_02.gif';"
                               onmouseout = "src='images/btn_messages_01.gif';"
                               onclick="document.location.href='CEOWeb/viewMessages.html';"><br>
                        </td>
                      </tr>
                    </table>
                  </esd:SecurityAccessRequired>
                  
                  <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-ANNOUNCEMENTS">
                    <h2>Messages/Announcement</h2>
                    <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
                      <tr>
                        <td width="50%" align="right" valign="middle">
                          &nbsp;
                        </td>
                        
                        <td width="25%" align="right" valign="middle">
                          <img src="images/btn_archive_01.gif"
                               onmouseover="src='images/btn_archive_02.gif';"
                               onmouseout = "src='images/btn_archive_01.gif';"
                               onclick="document.location.href='viewArchivedAnnouncements.html';"><br>
                        </td>
                        
                        <td width="25%" align="right" valign="middle">
                          <img src="images/btn_view_01.gif"
                               onmouseover="src='images/btn_view_02.gif';"
                               onmouseout = "src='images/btn_view_01.gif';"
                               onclick="document.location.href='viewAnnouncements.html';"><br>
                        </td>
                      </tr>
                    </table>
                  </esd:SecurityAccessRequired>
                  
                  <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BOARDMINUTES">
                    <h2>Board Meeting Minutes</h2>
                    <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
                      <tr>
                        <td width="50%" align="right" valign="middle">
                          &nbsp;
                        </td>
                        
                        <td width="25%" align="right" valign="middle">
                          <img src="images/btn_add_01.gif"
                               onmouseover="src='images/btn_add_02.gif';"
                               onmouseout = "src='images/btn_add_01.gif';"
                               onclick="document.location.href='addMinutes.html';"><br>
                        </td>
                        <td width="25%" align="right" valign="middle">
                          <img src="images/btn_view_01.gif"
                               onmouseover="src='images/btn_view_02.gif';"
                               onmouseout = "src='images/btn_view_01.gif';"
                               onclick="document.location.href='viewMinutes.html';"><br>
                        </td>
                      </tr>
                    </table>
                  </esd:SecurityAccessRequired>
                  
                  <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BOARDHIGHLIGHTS">
                    <h2>Board Highlights</h2>
                    <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
                      <tr>
                        <td width="50%" align="right" valign="middle">
                          &nbsp;
                        </td>
                        
                        <td width="25%" align="right" valign="middle">
                          <img src="images/btn_add_01.gif"
                               onmouseover="src='images/btn_add_02.gif';"
                               onmouseout = "src='images/btn_add_01.gif';"
                               onclick="document.location.href='addHighlights.html';"><br>
                        </td>
                        <td width="25%" align="right" valign="middle">
                          <img src="images/btn_view_01.gif"
                               onmouseover="src='images/btn_view_02.gif';"
                               onmouseout = "src='images/btn_view_01.gif';"
                               onclick="document.location.href='viewHighlights.html';"><br>
                        </td>
                      </tr>
                    </table>
                  </esd:SecurityAccessRequired>
                  
                  <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-DISTRICTPOLICIES">
                    <h2>District Policies</h2>
                    <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
                      <tr>
                        <td width="25%" align="right" valign="middle">
                          <img src="images/btn_add_01.gif"
                               onmouseover="src='images/btn_add_02.gif';"
                               onmouseout = "src='images/btn_add_01.gif';"
                               onclick="document.location.href='addPolicy.html';"><br>
                        </td>
                        <td width="25%" align="right" valign="middle">
                          <img src="images/btn_view_01.gif"
                               onmouseover="src='images/btn_view_02.gif';"
                               onmouseout = "src='images/btn_view_01.gif';"
                               onclick="document.location.href='viewPolicies.html';"><br>
                        </td>
                        <td width="25%" align="right" valign="middle">
                          <img src="images/btn_categories_01.gif"
                               onmouseover="src='images/btn_categories_02.gif';"
                               onmouseout = "src='images/btn_categories_01.gif';"
                               onclick="document.location.href='viewPolicyCategories.html';"><br>
                        </td>
                        <td width="25%" align="right" valign="middle">
                          <img src="images/btn_reg_01.gif"
                               onmouseover="src='images/btn_reg_02.gif';"
                               onmouseout = "src='images/btn_reg_01.gif';"
                               onclick="document.location.href='addPolicyRegulation.html';"><br>
                        </td>
                      </tr>
                    </table>
                  </esd:SecurityAccessRequired>
                  
                </td>
              </tr>
            </table> 
          </td>
        </tr>
      </table> 
    </form>
	</body>
</html>