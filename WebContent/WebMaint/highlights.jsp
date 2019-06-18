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
  Date dt = null;
  Iterator iter = null;
  SimpleDateFormat sdf_full = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
  SimpleDateFormat sdf_file = new SimpleDateFormat("dd_MM_yyyy");
  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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

    iter = ((Vector)request.getAttribute("HIGHLIGHTS")).iterator();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Web Maintenance - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/webmaint.css";</style>
    <script language="JavaScript" src="../js/common.js"></script>
	</head>
	<body style="margin-top:-30px;">
    <form id="pol_cat_frm" action="addPolicyCategory.html" method="post">
      <table width="800" height="650" cellpadding="0" cellspacing="0" align="center" valign="top">
        <tr>
          <td width="100%" height="100%" id="maincontent" align="left" valign="top">
            <!--<img src="../images/spacer.gif" width="1" height="125"><br>-->
            <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
              <tr>
                <td width="100%" align="left" valign="top">
                  <img src="images/board_meeting_minutes_title.gif"><br>
                </td>
              </tr>
              <tr>
                <td width="100%" align="center" valign="middle" style="background: url('images/body_bkg.gif') top left repeat-y;">
                  <table width="60%" cellpadding="0" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td colspan="2" align="left">
                       <h2>School Board Highlights</h2>
                      </td>
                    </tr>
                    <%if(request.getAttribute("edit_msg") != null){%>
                      <tr>
                        <td colspan="2" class="message_info"><br>*** <%=(String)request.getAttribute("edit_msg")%> ***<br></td>
                      </tr>
                    <%}%>
                    <tr>
                      <td colspan="2">
                        <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
                          <tr>
                            <td width="50%" align="left" class="label">Document Date</td>
                            <td width="*" align="center">&nbsp;</td>
                          </tr>
                          <%if(!iter.hasNext()){%>
                              <tr><td colspan="2" class='content' align="left" style="color:#FF0000;font-weight:bold;">No board highlights on record.</td></tr>
                          <%}else{
                              int r_cnt = 0;
                              while(iter.hasNext()){
                                dt = (Date)iter.next();%>
                                <tr style="font-weight:bold;display:inline;<%=((r_cnt++%2)==0)?"background-color:#FFFFFF;":"background-color:#E0E0E0;"%>">
                                  <td width="15%" align="left" class="content" style="padding-left:3px;border-bottom:solid 2px #c0c0c0;"><%=sdf_full.format(dt)%></td>
                                  <td width="*" align="right" class="content" style="padding-right:3px;border-bottom:solid 2px #c0c0c0;">
                                    <table width="100%" cellpadding="0" cellspacing="1" align="center" valign="top" border="0">
                                      <tr>
                                        <td width="100%">
                                          <img src="images/btn_view_01.gif"
                                            onmouseover="src='images/btn_view_02.gif';"
                                            onmouseout="src='images/btn_view_01.gif';"
                                            onclick="openWindow('VIEW_HIGHLIGHTS', 'http://www.esdnl.ca/board/highlights/archive/<%=sdf_file.format(dt)%>.pdf',800, 600, 1);">&nbsp;
                                          <img src="images/btn_delete_01.gif"
                                            onmouseover="src='images/btn_delete_02.gif';"
                                            onmouseout="src='images/btn_delete_01.gif';"
                                            onclick="document.location.href='deleteHighlights.html?dt=<%=sdf.format(dt)%>';">
                                        </td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                            <%}%>
                          <%}%>
                        </table>
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
	</body>
</html>