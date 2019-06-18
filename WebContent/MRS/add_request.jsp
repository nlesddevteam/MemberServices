<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.school.*,
                 com.esdnl.mrs.*"
        isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>  

<%
  User usr = null;
  int outstanding = 0;

  usr = (User) session.getAttribute("usr");
  
  outstanding = ((Integer)request.getAttribute("OUTSTANDING_REQUESTS")).intValue();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Maintenance/Repair - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/maint.css";</style>
    <script language="JavaScript" src="js/common.js"></script>
    <script type="text/javascript">
      function cancel_add()
      {
        var frm = document.forms[0];
        
        frm.request_type.selectedIndex = 0;
        frm.rname_num.value = "";
        frm.request_priority.selectedIndex = 1;
        frm.request_desc.value = "";
      }
    </script>
	</head>
	<body style="margin:0px;" onload="if(top != self){resizeIFrame('maincontent_frame', 317);} cancel_add();">
	
		<esd:SecurityCheck permissions="MAINTENANCE-SCHOOL-VIEW,MAINTENANCE-ADMIN-VIEW,MAINTENANCE-ADMIN-REGIONAL-VIEW" />
		
    <form id="add_request_form" name="add_request_form" action="addRequest.html" method="post">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <table width="100%" height="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
        <tr><td align="center" style="padding-bottom:5px;"><img src="images/add_request_title.gif"><br></td></tr>
        <tr>
          <td id="maincontent">
            <table width="360" cellpadding="1" cellspacing="0" align="center" valign="top">
              <%if(request.getAttribute("msg") != null){%>
                <tr><td><img src="images/spacer.gif" height="10" width="1"></td></tr>
                <tr>
                  <td width="100%" align="center" colspan="2" class="message_info">
                    <span class="requiredstar">*** </span><%=request.getAttribute("msg")%><span class="requiredstar"> ***</span>
                  </td>
                </tr>
              <%}%>
              <%if((usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")
                    || usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-REGIONAL-VIEW"))
                    && (request.getAttribute("SCHOOLS") != null)){%>
                <tr>
                  <td width="100%" align="left" colspan="2">
                    <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                      <tr>
                        <td width="50%" valign="middle">
                          <span class="requiredstar">*</span><span class="label">School:</span>
                        </td>
                        <td width="*" align="right">
                          <select id="school" name="school" class="requiredinput">
                            <option value="-1">Select SCHOOL</option>
                            <%School[] s = (School[])request.getAttribute("SCHOOLS");
                              for(int i=0; i < s.length; i++){%>
                              	<option value='<%=s[i].getSchoolID()%>'><%=s[i].getSchoolName()%></option>
                            <%}%>
                          </select>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              <%}%>
              <tr>
                <td width="100%" align="left" colspan="2">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="50%" valign="middle">
                        <span class="requiredstar">*</span><span class="label">Request Type:</span>
                      </td>
                      <td width="*" align="right">
                        <select id="request_type" name="request_type" class="requiredinput" >
                          <option value="-1">Select Request Type</option>
                          <%RequestType[] rtype = (RequestType[])request.getAttribute("REQUEST_TYPES");
                            for(int i=0; i <rtype.length; i++){
                          %>  <option value="<%=rtype[i].getRequestTypeID()%>"><%=rtype[i].getRequestTypeID()%></option>
                          <%}%>
                        </select>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td width="100%" align="left" colspan="2">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="50%">
                        <span class="requiredstar">*</span><span class="label">Room Name/Number:</span>
                      </td>
                      <td width="*" align="right">
                        <input type="text" id="rname_num" name="rname_num" value="" style="width:200px;" class="requiredinput">
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td width="100%" align="left" colspan="2">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="50%">
                        <span class="requiredstar">*</span><span class="label">Priority:</span>
                      </td>
                      <td width="*" align="right">
                        <select id="request_priority" name="request_priority" class="requiredinput" >
                          <option value="-1">Select Priority</option>
                          <%for(int i=1; i <= (outstanding + 1); i++){%>
                              <option value="<%=i%>"><%=i%></option>
                          <%}%>
                        </select>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td width="100%" align="left" colspan="2">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="50%">
                        <span class="requiredstar">*</span><span class="label">Request Description:</span>
                      </td>
                    </tr>
                    <tr>
                      <td width="100%" align="right">
                        <textarea id="request_desc" name="request_desc" style="width:340px; height:100px;"></textarea>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td width="50%" align="left" style="padding-left:12px;">
                  <img src="images/btn_submit_01.gif"
                    onmouseover="src='images/btn_submit_02.gif';"
                    onmouseout="src='images/btn_submit_01.gif';"
                    onclick="document.forms[0].submit();"><br>
                </td>
                <td width="50%" align="right">
                  <img src="images/btn_cancel_01.gif"
                    onmouseover="src='images/btn_cancel_02.gif';"
                    onmouseout="src='images/btn_cancel_01.gif';"
                    onclick="cancel_add();"><br>
                </td>
              </tr>
              <%if(request.getAttribute("msg") != null){%>
                <tr><td><img src="images/spacer.gif" height="10" width="1"></td></tr>
                <tr>
                  <td width="100%" align="center" colspan="2" class="message_info">
                    <span class="requiredstar">*** </span><%=request.getAttribute("msg")%><span class="requiredstar"> ***</span>
                  </td>
                </tr>
              <%}%>
            </table>
          </td>
        </tr>
      </table> 
    </form>
	</body>
</html>