<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.esdnl.mrs.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>

<%!
  User usr = null;
  RequestCategory[] type = null;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")))
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

  type = (RequestCategory[])request.getAttribute("REQUEST_CATEGORIES");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Maintenance/Repair - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/maint.css";</style>
    <script language="JavaScript" src="js/common.js"></script>
	</head>
	<body style="margin:0px;" onload="if(top != self){resizeIFrame('maincontent_frame', 317);}">
    <form id="admin_menu_form" action="addRequestCategory.html" method="post">
      <input type="hidden" id="op" name="op" value="ADD">
      <input type="hidden" id="t_id" name="t_id" value="">
      <table width="100%" height="100%" cellpadding="0" cellspacing="0" align="left" valign="top">
        <tr><td align="center" style="padding-bottom:5px;"><img src="images/admin_utility_menu_add_request_category_title.gif"><br></td></tr>
        <tr>
          <td id="maincontent">
            <table width="260" cellpadding="1" cellspacing="0" align="center" valign="top">
              <%if(type.length > 0){
              %><tr><td colspan="2" class="header">Request Categories</td></tr>
              <%    for(int i=0; i < type.length;i++){
              %>    <tr>
                      <td width="100%" align="left" colspan="2">
                        <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                          <tr>
                            <td width="50%">
                              <span class="label_no_underline"><%=type[i].getRequestCategoryID()%></span>
                            </td>
                            <td width="*" align="right">
                              <a href="" class="small" 
                                onclick="document.forms[0].op.value='DEL'; document.forms[0].t_id.value='<%=type[i].getRequestCategoryID()%>'; document.forms[0].submit(); return false;">delete</a>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                <%}%>
                <tr><td colspan="2" class="footer" height="1"><img src="images/spacer.gif" width="1" height="1"></td></tr>
                <tr><td colspan="2"><img src="images/spacer.gif" width="1" height="10"></td></tr>
              <%}%>
              <tr>
                <td width="100%" align="left" colspan="2">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="50%">
                        <span class="requiredstar">*</span><span class="label">Category</span>
                      </td>
                      <td width="*">
                        <input type="text" id="type_id" name="type_id" value="" style="width:150px;" class="requiredinput">
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr><td><img src="images/spacer.gif" height="10" width="1"></td></tr>
              <tr>
                <td style="padding-left:12px;" width="50%" align="left">
                  <img src="images/btn_submit_01.gif"
                    onmouseover="src='images/btn_submit_02.gif';"
                    onmouseout="src='images/btn_submit_01.gif';"
                    onclick="document.forms[0].submit();"><br>
                </td>
                <td width="50%" align="right">
                  <img src="images/btn_cancel_01.gif"
                    onmouseover="src='images/btn_cancel_02.gif';"
                    onmouseout="src='images/btn_cancel_01.gif';"
                    onclick="document.elements['type_id'].value='';"><br>
                </td>
              </tr>
              <tr><td><img src="images/spacer.gif" height="10" width="1"></td></tr>
              <%if(request.getAttribute("msg") != null){%>
                <tr>
                  <td width="100%" align="center" colspan="2" class="message_info">
                    <%=request.getAttribute("msg")%>
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