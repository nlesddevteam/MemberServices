<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,com.awsd.personnel.profile.*,
                 com.esdnl.roer.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
        isThreadSafe="false"%>

<%!
  User usr = null;
  Profile profile = null;
  ROERequest roer = null;
  SimpleDateFormat sdf = null;
  Iterator iter = null;
  String op;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("ROEREQUEST-VIEW")))
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
  
  
  iter = ((Vector) request.getAttribute("OUTSTANDING_REQUESTS")).iterator();
  
  sdf = new SimpleDateFormat("dd/MM/yyyy");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>ROE Request System</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/roerequest.css";</style>
    <script language="JavaScript" src="js/CalendarPopup.js"></script>
    <script language="JavaScript" src="js/common.js"></script>
	</head>
	<body style="margin-top:10px;">
    <form name="add_roe_request_form" method="post" action="addROERequest.html">
      <input type="hidden" name="op" value="ADDED">
      <input type="hidden" id="sick_dates" name="sick_dates" value="">
      <input type="hidden" id="unpaid_dates" name="unpaid_dates" value="">
      <table width="600" cellpadding="0" cellspacing="0"  align="center">
        <tr>
          <td id="form_header" width="100%" height="75">
            <img src="images/roer_header.gif"><br>
          </td>
        </tr>
        <tr>
          <td id="maincontent" width="100%">
            <table width="100%" cellpadding="0" cellspacing="6" border="0">
              <tr>
                <td width="100%">
                  <table width="100%" cellpadding="0" cellspacing="6" border="0">
                    <tr>
                      <td class="label" width="33%">Date Requested</td>
                      <td class="label" width="33%">Request Reason</td>
                      <td width="*">&nbsp;</td>
                    </tr>
                    <%if(iter.hasNext()){
                        while(iter.hasNext()){
                          roer = (ROERequest) iter.next();
                          profile = roer.getPersonnel().getProfile();
                    %>    <tr>
                            <td class="content" width="33%"><%=sdf.format(roer.getRequestDate())%></td>
                            <td class="content" width="33%"><%=roer.getReasonForRecordRequest().replaceAll("_", " ")%></td>
                            <td class="content" width="*">
                              <img src="images/btn_view_01.gif"
                                   onmouseover="src='images/btn_view_02.gif';"
                                   onmouseout="src='images/btn_view_01.gif';"
                                   onclick="document.location.href='myROERequest.html?op=VIEW&rid=<%=roer.getRequestID()%>';">
                              &nbsp;&nbsp;
                              <img src="images/btn_del_01.gif"
                                   onmouseover="src='images/btn_del_02.gif';"
                                   onmouseout="src='images/btn_del_01.gif';"
                                   onclick="document.location.href='myROERequest.html?op=DELETE&rid=<%=roer.getRequestID()%>';"><br>
                              <br>
                            </td>
                          </tr>
                    <%   }
                      }else{%>
                        <tr><td colspan="3" class="message_info"> NO OUTSTANDING REQUESTS.</tr>
                    <%}%>
                    <tr>
                      <td colspan="3" align="center">
                        <img src="images/btn_new_01.gif"
                            onmouseover="src='images/btn_new_02.gif';"
                            onmouseout="src='images/btn_new_01.gif';"
                            onclick="document.location.href='myROERequest.html?op=NEW';"><br>
                        <br>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </form>
	</body>
</html>
