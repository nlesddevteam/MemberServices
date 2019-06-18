<%@ page  language="java" 
          session="true" 
          import="java.util.*, 
            java.text.*, 
            com.awsd.pdreg.*,com.awsd.security.*,com.awsd.personnel.*,com.awsd.school.*,
            com.awsd.common.Utils"
          isThreadSafe="false"%>

<%!
  // months as they appear in the calendar's title
  final String ARR_MONTHS[] = {"January", 
                                "February", 
                                "March", 
                                "April", 
                                "May", 
                                "June", 
                                "July", 
                                "August", 
                                "September", 
                                "October", 
                                "November", 
                                "December"};

  // week day titles as they appear on the calendar
  final String ARR_WEEKDAYS[] = {"Sunday", 
                                  "Monday", 
                                  "Tuesday", 
                                  "Wednesday", 
                                  "Thursday", 
                                  "Friday", 
                                  "Saturday"};
  
  // path to the directory where calendar images are stored. trailing slash req.
  final String STR_ICONPATH = "images/";

  int dayofweek;

  Calendar cur = null;
  User usr = null;
  UserPermissions permissions = null;
  RegisteredPersonnelCollection users = null;
  Personnel p = null;
  Iterator iter = null;
  Date now = null;
  Date evtdt = null;
  Date evtenddt = null;
  Event evt = null;
  School s = null;
  boolean other;
  String status = "";
  SimpleDateFormat sdf = null;
%>

<%
  int sid = -1;
  
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    permissions = usr.getUserPermissions();
    if(!(permissions.containsKey("CALENDAR-VIEW") 
        && (permissions.containsKey("CALENDAR-SCHEDULE")
            || permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS"))))
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
  
  users = (RegisteredPersonnelCollection) request.getAttribute("RegisteredPersonnel");
  iter = users.iterator();
  
  cur =  Calendar.getInstance();
  now = cur.getTime();
  
  dayofweek = cur.get(Calendar.DAY_OF_WEEK);

  evt = users.getEvent();
  evtdt = evt.getEventDate();
  evtenddt = evt.getEventEndDate();

  other = false;

  if((Utils.compareCurrentDate(evt.getEventDate())==0)||
    (((evt.getEventEndDate()!= null)&&(Utils.compareCurrentDate(evt.getEventDate())==-1)&&(Utils.compareCurrentDate(evt.getEventEndDate())>=0)) ))
    status = "ACTIVE";
  else if(Utils.compareCurrentDate(evt.getEventDate())==-1)
    status = "PAST";
  else
    status = "";
  
  sdf = new SimpleDateFormat("MM/dd/yyyy");
%>

<html>

<head>
<title>Newfoundland &amp; Labrador English School District - Event Participants</title>
<style>
	td {font-family: Tahoma, Verdana, sans-serif; font-size: 12px;}
</style>

<script language="JavaScript" src="../js/common.js"></script>

<script language="Javascript">
  function processing(image)
  {
    document.images[image].src='images/processing_ani.gif'; 
    document.images[image].onmouseover="src='images/spacer.gif';" 
    document.images[image].onmouseout="src='images/spacer.gif';"
    document.images[image].onmousedown="src='images/spacer.gif';"
    document.images[image].onmouseup="src='images/spacer.gif';"
  }

  function print_participants()
  {
    parent.participants_hidden.focus();
    window.print();
  }
</script>
</head>

<body bgcolor="#FFFFFF" marginheight="5" marginwidth="5" topmargin="5" leftmargin="5" rightmargin="5">
<table cellspacing="0" border="0" width="100%">
  <tr>
    <td bgcolor="#4682B4">
      <table cellspacing="0" cellpadding="1" border="0" width="100%">
        <tr>
          <td colspan="2">
            <table cellspacing="0" cellpadding="0" border="0" width="100%">
              <tr>
                <td>
                  &nbsp;
                </td>
                <td align="center" width="100%">
                  <font size="3" color="#ffffff"><b><%=evt.getEventName()%></b></font>
                </td>
                <td>
                  &nbsp;
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td align="center" colspan="2">
            <table width="100%" cellpadding="0" cellspacing="1" border="0">
              <tr>
                <td width="100" valign="top" bgcolor="#f4f4f4">
                  &nbsp;&nbsp;Type:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=evt.getEventType().getEventTypeName()%>
                </td>
              </tr>
              <tr>
                <td width="100" valign="top" bgcolor="#f4f4f4">
                  &nbsp;&nbsp;Title:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=evt.getEventName()%>
                </td>
              </tr>
              <tr>
                <td width="100" valign="top" bgcolor="#f4f4f4">
                  &nbsp;&nbsp;Description:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=evt.getEventDescription()%>
                </td>
              </tr>
              <tr>
                <td width="100" valign="top" bgcolor="#f4f4f4">
                  &nbsp;&nbsp;Location:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=evt.getEventLocation()%>
                </td>
              </tr>
              <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
                <tr>
                  <td width="100" valign="top" bgcolor="#f4f4f4">
                    &nbsp;&nbsp;Host:
                  </td>
                  <td width="300" valign="top" bgcolor="#FFFFFF">
                    <%=evt.getScheduler().getFullName()%>
                  </td>
                </tr>
              <% } %>
              <tr>
                <td width="100" valign="top" bgcolor="#f4f4f4">
                  &nbsp;&nbsp;Start Date:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <% if(!status.equals("")) { %>
                    <font color="FF0000" style="font-weight:BOLD;">
                  <% } %>
                  <%=sdf.format(evtdt)%>
                  <% if(!status.equals("")) { %>
                    </font>
                    <font color="FF0000" style="font-weight:BOLD;" size="1">
                    [<%=status%> EVENT]
                    </FONT>
                  <% } %>
                </td>
              </tr>
              <% if(evtenddt != null) { %>
                <tr>
                  <td width="100" valign="top" bgcolor="#f4f4f4">
                    &nbsp;&nbsp;End Date:
                  </td>
                  <td width="300" valign="top" bgcolor="#FFFFFF">
                    <% if(!status.equals("")) { %>
                      <font color="FF0000" style="font-weight:BOLD;">
                    <% } %>
                    <%=sdf.format(evtenddt)%>
                    <% if(!status.equals("")) { %>
                      </font>
                    <% } %>
                  </td>
                </tr>
              <% } %>
              <% if(!evt.getEventStartTime().equals("UNKNOWN")) { %>
              <tr>
                <td width="100" valign="top" bgcolor="#f4f4f4">
                  &nbsp;&nbsp;Start Time:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=evt.getEventStartTime()%>
                </td>
              </tr>
              <tr>
                <td width="100" valign="top" bgcolor="#f4f4f4">
                  &nbsp;&nbsp;Finish Time:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=evt.getEventFinishTime()%>
                </td>
              </tr>
              <% } %>
              <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
                <tr>
                  <td width="100" valign="top" bgcolor="#f4f4f4">
                    &nbsp;&nbsp;Max Participants:
                  </td>
                  <td width="300" valign="top" bgcolor="#FFFFFF">
                    <% if(evt.getEventMaximumParticipants() > 0) { %>
                      <%=evt.getEventMaximumParticipants()%>
                    <% } else { %>
                      UNLIMITED
                    <% } %>
                  </td>
                </tr>
                <tr>
                  <td width="100" valign="top" bgcolor="#f4f4f4">
                    &nbsp;&nbsp;# Registered:
                  </td>
                  <td width="300" valign="top" bgcolor="#FFFFFF">
                    <%=evt.getRegistrationCount()%>
                  </td>
                </tr>
              <% } %>
            </table>
          </td>
        </tr>
        <tr>
          <td>
          <table  width="100%" cellpadding="0" cellspacing="0" border="0">
            <tr>
              <td align="center">
                <img align="center" src="images/print_blue_01.jpg" 
                onmouseover="src='images/print_blue_02.jpg';" 
                onmouseout="src='images/print_blue_01.jpg';" 
                onmousedown="src='images/print_blue_03.jpg';"
                onmouseup="src='images/print_blue_02.jpg';"
                onclick="self.parent.participants_hidden.location.href='viewPrinterFriendlyEventParticipants.html?id=<%=evt.getEventID()%>';"><img align="center" src="images/email_01.jpg" 
                onmouseover="src='images/email_02.jpg';" 
                onmouseout="src='images/email_01.jpg';" 
                onmousedown="src='images/email_03.jpg';"
                onmouseup="src='images/email_02.jpg';"
                onclick="openWindow('CreateMail', 'createEventParticipantsEmail.html?id=<%=evt.getEventID()%>', 535, 485, 1);">
              </td>
            </tr>
          </table>
          </td>
        </tr>
<%      if(!iter.hasNext())
        {
%>        <tr>
              <td bgcolor="#ffffff" align="center"><font color="#FF0000"><B>No Registered Personnel.</B></font></td>
          </tr>
<%      }
        else
        {
%>        <tr>
              <td align="center" colspan="2">
                <table cellspacing="1" cellpadding="3" border="0" width="100%">
                  <% while (iter.hasNext())
                  {
                    p = (Personnel) iter.next();
                    s = p.getSchool();
                    if((s!=null)&&(s.getSchoolID() != sid))
                    {
                      sid = s.getSchoolID();
%>                    <tr>                
                          <td bgcolor="#f4f4f4" align="left" colspan="2">
                            <b><%=s.getSchoolName()%></b>
                          </td>
                      </tr>
<%                  }
                    else if((s == null)&&(!other))
                    {
                      other = true;
%>                    <tr>                
                          <td height="1" colspan="2">
                            
                          </td>
                      </tr>
                      <tr>                
                          <td bgcolor="#f4f4f4" align="left" colspan="2">
                            <b>Other</b>
                          </td>
                      </tr>
<%                  }
%>                  <tr>
                      <td bgcolor="#ffffff" align="left"><%=p.getFullName()%></td>
                      <% if(Utils.compareCurrentDate(evt.getEventDate()) > 0) { %>
                        <% if(permissions.containsKey("CALENDAR-SCHEDULE")){%>
                          <td bgcolor="#ffffff" align="center">
                          
                            <img name='<%="img"+p.getPersonnelID()%>' src="images/deregister_01.jpg" 
                                 onmouseover="src='images/deregister_02.jpg';"
                                 onmouseout="src='images/deregister_01.jpg';"
                                 onmousedown="src='images/deregister_03.jpg';"
                                 onmouseup="src='images/deregister_02.jpg';"
                                 onclick="processing('<%="img"+p.getPersonnelID()%>'); self.location.href='deregisterEvent.html?pid=<%=p.getPersonnelID()%>&id=<%=evt.getEventID()%>';">
                          
                          </td>
                        <%}%>
                      <% } %>
                    </tr>
<%                }
%>              </table>
              </td>
          </tr>
<%      }
%>    <tr>
        <td colspan="2" align="center">
          <img src="images/closeblue_01.jpg" 
               onmouseover="src='images/closeblue_02.jpg';" 
               onmouseout="src='images/closeblue_01.jpg';"
               onmousedown="src='images/closeblue_03.jpg';"
               onmouseup="src='images/closeblue_02.jpg';"
               onclick="self.parent.close();">
        </td>
      </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>