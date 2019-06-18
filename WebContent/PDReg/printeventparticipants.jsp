<%@ page  language="java" 
          session="true" 
          import="java.util.*, 
            java.text.*, 
            com.awsd.pdreg.*,com.awsd.security.*,com.awsd.personnel.*,com.awsd.school.*"
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

  int dayofweek, sid = -1;

  Calendar cur = null;
  SimpleDateFormat sdf = null;
  User usr = null;
  UserPermissions permissions = null;
  RegisteredPersonnelCollection users = null;
  Personnel p = null;
  Iterator iter = null;
  Date now = null;
  Event evt = null;
  School s = null;
  boolean other;
%>

<%
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
  dayofweek = cur.get(Calendar.DAY_OF_WEEK);

  //evt = (Event) request.getAttribute("Event");
  evt = users.getEvent();

  other = false;
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
</script>
</head>

<body bgcolor="#FFFFFF" marginheight="5" marginwidth="5" topmargin="5" leftmargin="5" rightmargin="5">
<table cellspacing="0" border="0" width="100%">
  <tr>
    <td bgcolor="#FFFFFF">
      <table cellspacing="0" cellpadding="1" border="0" width="100%">
        <tr>
          <td colspan="2">
            <table cellspacing="0" cellpadding="0" border="0" width="100%">
              <tr>
                <td>
                  &nbsp;
                </td>
                <td align="center" width="100%">
                  <font size="3" color="#000000"><b><%=evt.getEventName()%></b></font>
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
                <td width="100" valign="top" bgcolor="#FFFFFF" colspan="2">
                  &nbsp;
                </td>
              </tr>
              <tr>
                <td width="100" valign="top" bgcolor="#FFFFFF">
                  &nbsp;&nbsp;Type:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=evt.getEventType().getEventTypeName()%>
                </td>
              </tr>
              <tr>
                <td width="100" valign="top" bgcolor="#FFFFFF">
                  &nbsp;&nbsp;Description:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=evt.getEventDescription()%>
                </td>
              </tr>
              <tr>
                <td width="100" valign="top" bgcolor="#FFFFFF">
                  &nbsp;&nbsp;Location:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=evt.getEventLocation()%>
                </td>
              </tr>
              <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
              <tr>
                <td width="100" valign="top" bgcolor="#FFFFFF">
                  &nbsp;&nbsp;Host:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=evt.getScheduler().getFullName()%>
                </td>
              </tr>
              <% } %>
              <tr>
                <td width="100" valign="top" bgcolor="#FFFFFF">
                  &nbsp;&nbsp;Start Date:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=(new SimpleDateFormat("MM/dd/yyyy")).format(evt.getEventDate())%>
                </td>
              </tr>
              <% if(evt.getEventEndDate() != null) { %>
              <tr>
                <td width="100" valign="top" bgcolor="#FFFFFF">
                  &nbsp;&nbsp;End Date:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=(new SimpleDateFormat("MM/dd/yyyy")).format(evt.getEventEndDate())%>
                </td>
              </tr>
              <% } %>
              <% if(!evt.getEventStartTime().equals("UNKNOWN")) { %>
              <tr>
                <td width="100" valign="top" bgcolor="#FFFFFF">
                  &nbsp;&nbsp;Start Time:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=evt.getEventStartTime()%>
                </td>
              </tr>
              <tr>
                <td width="100" valign="top" bgcolor="#FFFFFF">
                  &nbsp;&nbsp;Finish Time:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=evt.getEventFinishTime()%>
                </td>
              </tr>
              <% } %>
              <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
                <tr>
                  <td width="100" valign="top" bgcolor="#FFFFFF">
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
                  <td width="100" valign="top" bgcolor="#FFFFFF">
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
<%      if(!iter.hasNext())
        {
%>        <tr>
              <td bgcolor="#ffffff" align="center">No Registered Personnel.</td>
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
                          <td bgcolor="#FFFFFF" align="left" colspan="2">
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
                          <td bgcolor="#FFFFFF" align="left" colspan="2">
                            <b>Other</b>
                          </td>
                      </tr>
<%                  }
%>                  <tr>
                      <td bgcolor="#ffffff" colspan="2" align="left"><%=p.getFullName()%></td>
                    </tr>
<%                }
%>              </table>
              </td>
          </tr>
<%      }
%>    </table>
    </td>
  </tr>
</table>
<script language="JavaScript">
  parent.participants_main.print_participants();
</script>
</body>
</html>