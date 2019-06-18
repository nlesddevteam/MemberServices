<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>  
<%@ taglib uri="/WEB-INF/mrs.tld" prefix="mrs" %>

<esd:SecurityCheck permissions="MAINTENANCE-SCHOOL-VIEW,MAINTENANCE-WORKORDERS-VIEW,MAINTENANCE-ADMIN-VIEW" />

<%
	User usr = (User) session.getAttribute("usr");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Maintenance/Repair - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/maint.css";</style>
    <script type="text/javascript" src="js/common.js"></script>
    <script type="text/javascript">
      function swap_image(id, src)
      {
        var btn = document.getElementById(id);
        if(btn)
        {
          btn.src = src;
        }
      }
      function setSchoolId(id){
      	document.location.href='setSchool.html?id='+id;
      }
    </script>
	</head>
	<body style="margin:10;">
    <form>
    <table align="center" width="800" cellpadding="0" cellspacing="0" align="center" >
      <tr>
        <td colspan="2" height="146">
          <img src="images/maint_header.jpg"><br>
        </td>
      </tr>
      <tr>
        <td id="navigation" width="180" valign="top" align="center" style="background:url(images/nav_bottom_bg.gif) top left repeat-y;">
          <table width="180" border="0" cellpadding="0" cellspacing="0">
            <tr height="61">
              <td width="180" valign="top" style="padding:0px;">
                <img src="images/nav_top.jpg"><br>
              </td>
            </tr>
            <tr height="57">
              <td width="180" valign="top" style="padding:0px;">
              	<%if(usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")){%>
              		<img id='btn_home' style="cursor:hand;" src="images/home_01.gif"
                    onmouseover="src='images/home_02.gif'; button_info_msg('Current Requests');"
                    onmouseout="src='images/home_01.gif'; button_info_msg('&nbsp;');"
                    onclick="document.frames[0].location.href='allOutstandingRequests.html';"><br>
                <%}else if(usr.getUserPermissions().containsKey("MAINTENANCE-WORKORDERS-VIEW")){%>
              		<img id='btn_home' style="cursor:hand;" src="images/home_01.gif"
                    onmouseover="src='images/home_02.gif'; button_info_msg('Current Requests');"
                    onmouseout="src='images/home_01.gif'; button_info_msg('&nbsp;');"
                    onclick="document.frames[0].location.href='workOrderOutstandingRequests.html';"><br>
                <%}else if(usr.getUserPermissions().containsKey("MAINTENANCE-SCHOOL-VIEW")){%>
                  <img id='btn_home' style="cursor:hand;" src="images/home_01.gif"
                    onmouseover="src='images/home_02.gif'; button_info_msg('Current Requests');"
                    onmouseout="src='images/home_01.gif'; button_info_msg('&nbsp;');"
                    onclick="document.frames[0].location.href='schoolOutstandingRequests.html';"><br>
                <%}else{%>
                  <img src="images/empty_option.gif"><br>
                <%}%>
              </td>
            </tr>
            <tr height="52">
              <td width="180" style="padding:0px;">
                <%if(usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")){%>
                  <img id="btn_wrench"  style="cursor:hand;" src="images/add_request_01.gif"
                    onmouseover="src='images/add_request_02.gif'; button_info_msg('Utility Menu');"
                    onmouseout="src='images/add_request_01.gif'; button_info_msg('&nbsp;');"
                    onclick="document.frames[0].location.href='viewAdminUtilityMenu.html';"><br>
                <%}else if(usr.getUserPermissions().containsKey("MAINTENANCE-SCHOOL-VIEW")){%>
                  <img src="images/add_request_01.gif" style="cursor:hand;" 
                    onmouseover="src='images/add_request_02.gif'; button_info_msg('Add Request');"
                    onmouseout="src='images/add_request_01.gif'; button_info_msg('&nbsp;');"
                    onclick="document.frames[0].location.href='addRequest.html';"><br>
                <%}else{%>
                	<img src="images/empty_option.gif"><br>
                <%}%>
              </td>
            </tr>
            <tr height="52">
              <td width="180" valign="top" style="padding:0px;">
                <%if(usr.getUserPermissions().containsKey("MAINTENANCE-SCHOOL-VIEW")){%>
                  <img id='btn_reports' src="images/reports_01.gif" style="cursor:hand;" 
                    onmouseover="src='images/reports_02.gif'; button_info_msg('School Reports');"
                    onmouseout="src='images/reports_01.gif'; button_info_msg('&nbsp;');"
                    onclick="document.frames[0].location.href='viewSchoolReports.html';"><br>
                <%}else if(usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")){%>
                  <img id='btn_reports' src="images/reports_01.gif" style="cursor:hand;" 
                    onmouseover="src='images/reports_02.gif'; button_info_msg('Reports');"
                    onmouseout="src='images/reports_01.gif'; button_info_msg('&nbsp;');"
                    onclick="document.frames[0].location.href='viewAdminReports.html';"><br>
                <%}else{%>
                	<img src="images/empty_option.gif"><br>
                <%}%>
              </td>
            </tr>
            <tr height="52">
              <td width="180" valign="top" style="padding:0px;">
                  <a href="mailto:chriscrane@esdnl.ca?subject=MRS Help">
                  <img id='btn_contact' src="images/contact_01.gif" border="0"
                    onmouseover="src='images/contact_02.gif'; button_info_msg('HELP');"
                    onmouseout="src='images/contact_01.gif'; button_info_msg('&nbsp;');"></a><br>
              </td>
            </tr>
            <tr height="39">
              <td id="button_info" width="180"  align='center' valign="top" style="padding:0px;padding-top:5px;padding-right:18px;background:url('images/nav_bottom.jpg') top left no-repeat;">
                &nbsp;
              </td>
            </tr>
            <tr height="49">
              <td width="180" valign="top">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr><td class="label" align="center" style="padding-right:15px;">User:</td></tr>
                  <tr><td class="field_content" align="center" style="padding-right:15px;"><%=usr.getPersonnel().getFullNameReverse()%></td></tr>
                  <%if(usr.getUserPermissions().containsKey("MAINTENANCE-SCHOOL-VIEW")){%>
                  	<tr><td><img src="images/spacer.gif" width="1" height="2px"></td></tr>
	                  <tr><td class="label" align="center" style="padding-right:15px;">School:</td></tr>
	                  <tr>
	                  	<td class="field_content" align="center" style="padding-right:15px;">
	                  		<mrs:Schools 
	                  			id='school_id' 
	                  			dummy='<%=true%>' 
	                  			cls='field_content' 
	                  			style='background-color:#E76B10;font-size:9px;width:160px;height:20px;'
	                  			onchange='setSchoolId(this.value);' 
	                  			value='<%=usr.getPersonnel().getSchool()%>' />
	                  	</td>
	                  </tr>
                  <%}%>
                </table>
              </td>
            </tr>
          </table>
        </td>
        <td id="maincontent" width="620" height="100%" valign="top" align="left" style="background:url(images/maincontent_right_bg.gif) top right repeat-y;">
          <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td class="menubar" height="61" width="557">
                <img src="images/spacer.gif" width="557" height="1"><br>
              </td>
              <td width="63" height="61" valign="top">
                <img src="images/menubar_top_rt.gif" width="63" height="61"><br>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <%if(usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")){%>
                  <iframe id="maincontent_frame" src="allOutstandingRequests.html" frameborder="0" width="562px" scrolling="no"></iframe>
                <%}else if(usr.getUserPermissions().containsKey("MAINTENANCE-SCHOOL-VIEW")){%>
                  <iframe id="maincontent_frame" src="schoolOutstandingRequests.html" frameborder="0" width="562px" scrolling="no"></iframe>
                <%}else if(usr.getUserPermissions().containsKey("MAINTENANCE-WORKORDERS-VIEW")){%>
                	<iframe id="maincontent_frame" src="workOrderOutstandingRequests.html" frameborder="0" width="562px" scrolling="no"></iframe>
                <%}%>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td colspan="2" height="26">
          <table width="100%" height="26" border="0" cellpadding="0" cellspacing="0">
          	<tr>
	            <td width="180" height="26"><img src="images/maincontent_bottom_left_corner.jpg" width="180" height="26"></td>
	            <td width="557" height="26" style="background:url(images/bottom_bg.gif) top left repeat-x;"><img src="images/spacer.gif"></td>
	            <td width="63" height="26"><img src="images/maincontent_bottom_right_corner.jpg" width="63" height="26"></td>
            </tr>
          </table>
        </td>
      </tr>
    </table> 
  </form>
	</body>
</html>