<%@ page  language="java" 
          session="true" 
          import="java.util.*, 
            java.text.*, 
            com.awsd.pdreg.*,com.awsd.security.*,com.awsd.personnel.*,com.awsd.school.*"
          isThreadSafe="false"%>

<%
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
  User usr = null;
  UserPermissions permissions = null;
  SchoolRegisteredEvents sre = null;
  RegisteredEvents revts = null;
  Personnel p = null;
  Personnel[] aps = null;
  School s = null;
  Event evt = null;
  Iterator iter = null;
  Iterator e_iter = null;
%>

<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    permissions = usr.getUserPermissions();
    if(!(permissions.containsKey("CALENDAR-VIEW") 
        && permissions.containsKey("CALENDAR-VIEW-SCHOOL-ENROLLMENT")))
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
  
  sre = (SchoolRegisteredEvents) request.getAttribute("SchoolRegisteredEvents");
  s = sre.getSchool();
  iter = sre.getSchoolTeachers().iterator();
  aps = s.getAssistantPrincipals();
%>

<html>

<head>
<title>Newfoundland &amp; Labrador English School District - Event Participants</title>
<link href="../css/calendar.css" rel="stylesheet">
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
                  <font size="3" color="#ffffff"><b><%=s.getSchoolName()%></b></font>
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
                  &nbsp;&nbsp;Principal:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=s.getSchoolPrincipal().getFullNameReverse()%>
                </td>
              </tr>
              <% if((aps != null) && (aps.length > 0)) { %>
              <tr>
                <td width="100" valign="top" bgcolor="#f4f4f4">
                  &nbsp;&nbsp;Vice Principal:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%
                  	for(int i=0; i < aps.length; i++)
                  		out.println(aps[i].getFullNameReverse() + "<br>");
                  %>
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
                <font color="#ffffff"><b>Registrations</b></font>
              </td>
            </tr>
          </table>
          </td>
        </tr>
        <tr>
          <td align="center" colspan="2">
            <table cellspacing="1" cellpadding="3" border="0" width="100%">
              <% while (iter.hasNext())
                {
                  p = (Personnel) iter.next();
              %>  
                  <tr>                
                    <td bgcolor="#f4f4f4" align="left" colspan="2">
                      <b><%=p.getFullName()%></b>
                    </td>
                  </tr>  
               <% revts = (RegisteredEvents) sre.get(p);
                  e_iter = revts.entrySet().iterator();
                  if(!e_iter.hasNext())
                  {%>
                    <td bgcolor="#ffffff" align="left" colspan="2"><font color="#FF0000"><b>No Registrations.</b></font></td>
               <% } else {
                  while(e_iter.hasNext())
                  {
                    evt = (Event)((Map.Entry) e_iter.next()).getValue();
               %>   <tr>
                      <td bgcolor="#ffffff" align="left"><a class="closeout" href="javascript:openWindow('registration', 'registerEvent.html?id=<%=evt.getEventID()%>&details=true', 400, 465, 0);"><%= evt.getEventName()%></a></td>
                      <td bgcolor="#ffffff" align="center">
                        <% if(evt.isPDOpportunity()) {%>
                          <img name='<%="img"+p.getPersonnelID()%>' src="images/deregister_01.jpg" 
                             onmouseover="src='images/deregister_02.jpg';"
                             onmouseout="src='images/deregister_01.jpg';"
                             onmousedown="src='images/deregister_03.jpg';"
                             onmouseup="src='images/deregister_02.jpg';"
                             onclick="processing('<%="img"+p.getPersonnelID()%>'); self.location.href='deregisterEvent.html?pid=<%=p.getPersonnelID()%>&id=<%=evt.getEventID()%>';">
                        <%} else if(evt.isCloseOutDaySession()) {%>
                          <font class="closeout">Closeout Session</font>
                        <%}%>
                      </td>
                    </tr>
                <% } %>        
              <% } %>
              <% } %>
            </table>
          </td>
        </tr>
        <tr>
          <td colspan="2" align="center">
            <img src="images/closeblue_01.jpg" 
               onmouseover="src='images/closeblue_02.jpg';" 
               onmouseout="src='images/closeblue_01.jpg';"
               onmousedown="src='images/closeblue_03.jpg';"
               onmouseup="src='images/closeblue_02.jpg';"
               onclick="self.close();">
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>