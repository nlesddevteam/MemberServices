<%@ page language="java" 
         session="true"
         import="com.awsd.security.*"
         isThreadSafe="false"%>

<%!
  User usr = null;
  int window_width = 450;
  int window_height = 400;
%>

<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("CALENDAR-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}%>

<html>
  <head>
    <title>Event Legend</title>

    <style>
      td {font-family: Arial, Helvetica, sans-serif; font-size: 11px; line-height: 16px; color: #000000; font-weight:bold;}
    </style>
    <script language="JavaScript" src="../js/common.js"></script>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
    <table width="425" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td width="425" valign="top" bgcolor="#646464">
          <img src="images/spacer.gif" width="1" height="10"><BR>
        </td>
      </tr>
      <tr>
        <td width="425" height="1" bgcolor="#FFFFFF">
          <img src="images/spacer.gif" width="1" height="1"><BR>
        </td>
      </tr>
      <tr>
        <td width="425" valign="top">
          <img src="images/eventlegend_pt1.gif"><img src="images/title_pt2.jpg"><BR>
        </td>
      </tr>
    </table>
    <table width="400" cellpadding="10" cellspacing="0" border="0">
      <tr>
        <td width="200" valign="top">
          <table width="200" cellpadding="1" cellspacing="0" border="0">
            <tr>
              <td width="200" valign="top">
                <img src="images/director.gif"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/drdarinking.gif" border="0" style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=2343', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/spacer.gif" height="20"><BR>
                <img src="images/assistant_directors.gif"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/edwalsh.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=2543', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/darrinpike.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=991', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/ericsnow.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=1162', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/allisterdyke.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=2576', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/spacer.gif" height="20"><BR>
                <img src="images/seo_title.gif"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/glendariteff.gif" border="0"  style="cursor:hand;" onclick=""><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/gloriataylor.gif" border="0"  style="cursor:hand;" onclick=""><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/spacer.gif" height="20"><BR>
                <img src="images/reo_title.gif"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/allanfudge.gif" border="0"  style="cursor:hand;" onclick=""><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/charlespenwell.gif" border="0"  style="cursor:hand;" onclick=""><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/haywardblake.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=1000', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>

            <tr>
              <td width="200" valign="top">
                <img src="images/spacer.gif" height="20"><BR>
                <img src="images/district_calendar_events.gif"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/districtevents.gif" border="0" style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=-1', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/district_closeout.gif" border="0" style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=-2', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
          </table>
        </td>
        <td width="200" valign="top">
          <table width="200" cellpadding="1" cellspacing="0" border="0">
            <tr>
              <td width="200" valign="top">
                <img src="images/program_specialists.gif"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/albertdalton.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=949', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/alicebridgeman.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=1370', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/christinegreene.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=2304', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/craigwhite.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=2396', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/deborahtoope.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=879', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/garycorbett.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=940', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/garyyoung.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=2347', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/jackharrington.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=1016', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/joycefewer.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=2422', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/kristavokey.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=2335', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/lynnfitzpatrick.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=2890', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/marydevereaux.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=2421', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/marylarner.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=2391', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/clarbutton.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=4484', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/susanryan.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=2596', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/wadeprior.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=2483', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
            <tr>
              <td width="200" valign="top">
                <img src="images/whytehorlick.gif" border="0"  style="cursor:hand;" onclick="openWindow('ScheduledEvents', 'viewUpcomingEvents.html?pid=2380', <%=window_width%>, <%=window_height%>, 1);"><BR>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td align="center" valign="middle" colspan="2">
          <img align="center" src="images/close_01.jpg" 
              onmouseover="src='images/close_02.jpg';" 
              onmouseout="src='images/close_01.jpg';"
              onmousedown="src='images/close_03.jpg';"
              onmouseup="src='images/close_02.jpg';"
              onclick="self.close();">
        </td>
      </tr>
    </table>
  </body>
</html>






