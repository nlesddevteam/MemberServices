<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.esdnl.mrs.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>
                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>                 

<%
  User usr = null;
  MaintenanceRequest req = null;

  usr = (User) session.getAttribute("usr");  
  req = (MaintenanceRequest) request.getAttribute("MAINT_REQUEST");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Maintenance/Repair - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/maint.css";</style>
    <script language="JavaScript" src="js/common.js"></script>
    <script type="text/javascript">
      function toggle_processing_row()
      {
        var row = document.getElementById("processing_row");
        
        if(row)
        {          
          row.style.display = 'inline';
        }
      }
    </script>
	</head>
	<body style="margin:0px;">
		
		<esd:SecurityCheck permissions="MAINTENANCE-ADMIN-VIEW,MAINTENANCE-WORKORDERS-VIEW,MAINTENANCE-SCHOOL-VIEW" />
		
    <%if(request.getAttribute("COMPLETE") == null){%>
    <form id="admin_menu_form" action="addRequestComment.html" method="post">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <input type="hidden" id="req" name="req" value="<%=req.getRequestID()%>">
      <table width="100%" height="100%" cellpadding="0" cellspacing="0" align="left" valign="top">
        <tr><td align="center" style="padding-top:10px;padding-bottom:5px;"><img src="images/add_comment_title.gif"><br></td></tr>
        <tr>
          <td id="maincontent">
            <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
              <tr>
                <td width="100%" align="center" colspan="2">
                  <table width="80%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="100%" align="left">
                        <span class="requiredstar">*</span><span class="label">Comment:</span>
                      </td>
                    </tr>
                    <tr>
                      <td width="100%" align="left">
                        <textarea id="req_comment" name="req_comment" style="width:95%; height:120px;"></textarea>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr><td><img src="images/spacer.gif" height="10" width="1"></td></tr>
              <tr>
                <td width="50%" align="center">
                  <img src="images/btn_submit_01.gif"
                    onmouseover="src='images/btn_submit_02.gif';"
                    onmouseout="src='images/btn_submit_01.gif';"
                    onclick="toggle_processing_row(); document.forms[0].submit();opener.location.href='<%=usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")?"viewAdminRequestDetails.html":usr.getUserPermissions().containsKey("MAINTENANCE-SCHOOL-VIEW")?"viewSchoolRequestDetails.html":usr.getUserPermissions().containsKey("MAINTENANCE-WORKORDERS-VIEW")?"viewWorkOrderRequestDetails.html":"error_message.jsp"%>?req=<%=req.getRequestID()%>';"><br>
                </td>
                <td width="50%" align="center">
                  <img src="images/btn_cancel_01.gif"
                    onmouseover="src='images/btn_cancel_02.gif';"
                    onmouseout="src='images/btn_cancel_01.gif';"
                    onclick="document.elements['req_comment'].value='';"><br>
                </td>
              </tr>
              <tr><td><img src="images/spacer.gif" height="10" width="1"></td></tr>
              <tr id="processing_row" style="display:none;">
                <td width="100%" align="center" colspan="2" class="message_info">
                  <span class="requiredstar">*** </span>ADDING COMMENT...<span class="requiredstar"> ***</span>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table> 
    </form>
    <%}else{%>
      <script type="text/javascript">
        self.close();
      </script>
    <%}%>
	</body>
</html>