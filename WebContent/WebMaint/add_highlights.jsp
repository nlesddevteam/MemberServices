<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                 java.util.*,
                 com.esdnl.webmaint.policies.*,
                 java.io.*,
                 java.text.*"%>

<%!
  User usr = null;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("WEBMAINTENANCE-VIEW")
        && usr.getUserPermissions().containsKey("WEBMAINTENANCE-BOARDHIGHLIGHTS")))
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Web Maintenance - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/webmaint.css";</style>
    <script language="JavaScript" src="js/common.js"></script>
    <script language="JavaScript" src="js/CalendarPopup.js"></script> 
	</head>
	<body style="margin-top:-30px;">
    <form id="board_highlights_frm" action="addHighlights.html" method="post" ENCTYPE="multipart/form-data">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <table width="800" height="650" cellpadding="0" cellspacing="0" align="center" valign="top">
        <tr>
          <td width="100%" height="100%" id="maincontent" align="left" valign="top">
            <!--<img src="../images/spacer.gif" width="1" height="125"><br>-->
            <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
              <tr>
                <td width="100%" align="left" valign="top">
                  <img src="images/board_highlights_title.gif"><br>
                </td>
              </tr>
              <tr>
                <td width="100%" align="center" valign="middle" style="background: url('images/body_bkg.gif') top left repeat-y;">
                  <table width="60%" cellpadding="0" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td colspan="2" align="left">
                        <h2>Add Board Highlights</h2>
                      </td>
                    </tr>
                    
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Document Date:</span></td>
                      <td width="*" align="left" valign="middle">
                        <table width="60" cellpadding="0" cellspacing="0" style="padding:0px;">
                            <tr style="height:18px;">
                              <td width="95%"><input  type="text" id="doc_date" name="doc_date" style="width:51px;" class="requiredinput_date" value="" readonly></td>
                              <td width="*" align="center">
                                <img class="requiredinput_popup_cal" src="images/cal_popup_01.gif" alt="choose date"
                                    onmouseover="this.src='images/cal_popup_02.gif';"
                                    onmouseout="this.src='images/cal_popup_01.gif';"
                                    onclick="datepicker.popup();"><br>
                              </td>
                            </tr>
                          </table>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">PDF File:</span></td>
                      <td valign="top" align="left" width="*"><input type="file" size="60" name="filedata" class="requiredinput"><BR></td>
                    </tr>
                    <%if(request.getAttribute("msg") != null){%>
                      <tr>
                        <td colspan="2" class="message_info"><br>*** <%=(String)request.getAttribute("msg")%> ***</td>
                      </tr>
                    <%}%>
                    
                    <tr>
                      <td colspan="2">
                        <br><img src="images/btn_save_01.gif"
                             onmouseover="src='images/btn_save_02.gif';"
                             onmouseout="src='images/btn_save_01.gif';"
                             onclick="progressBarInit(); document.forms[0].submit();"><br><br>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="2" align="center">
                        <script language="javascript" src="js/timerbar.js"></script>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td width="100%" height="104" align="center" valign="middle" style="background: url('images/footer_bkg.gif') top left no-repeat;">
                  <img src="images/btn_home_01.gif"
                       onmouseover="src='images/btn_home_02.gif';"
                       onmouseout = "src='images/btn_home_01.gif';"
                       onclick="document.location.href='viewWebMaintenance.html';">
                </td>
              </tr>
            </table> 
          </td>
        </tr>
      </table> 
    </form>
    <script language="JavaScript">
      var datepicker = new CalendarPopup(document.forms['board_highlights_frm'].elements['doc_date']);
      datepicker.year_scroll = true;
      datepicker.time_comp = false;
    </script>
	</body>
</html>