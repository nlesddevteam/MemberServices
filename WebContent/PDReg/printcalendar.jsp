<%@ page  language="java" 
          session="true" 
          import="java.util.*, java.text.*, com.awsd.pdreg.*,com.awsd.security.*" 
          errorPage="error.jsp"
          isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

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

  String prevM;
  String nextM;
  String prevY;
  String nextY;
  int curM;
  int curY;
  int curD;
  int firstday;

  int day;
  int dayofweek;
  boolean firstweek;

  Calendar cur = null;
  SimpleDateFormat sdf = null;
  User usr = null;
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
<%}
  
  prevM = (String) request.getAttribute("PrevMonth");
  prevY = (String) request.getAttribute("PrevYear");
  nextM = (String) request.getAttribute("NextMonth");
  nextY = (String) request.getAttribute("NextYear");
  curM = ((Integer) request.getAttribute("CurrentMonth")).intValue();
  curY = ((Integer) request.getAttribute("CurrentYear")).intValue();

  cur =  Calendar.getInstance();
  curD = cur.get(Calendar.DATE);
  cur.set(curY, curM, 1);
  firstday = cur.get(Calendar.DAY_OF_WEEK);

  firstweek = true;

  sdf = new SimpleDateFormat("yyyyMMdd");
%>

<html>

<head>
  <title>Newfoundland &amp; Labrador English School District - Event Calendar</title>
  <link href="../css/calendar.css" rel="stylesheet">
  <script language="JavaScript" src="../js/common.js"></script>
</head>

<body bgcolor="#FFFFFF" marginheight="0" marginwidth="0" topmargin="0" leftmargin="0" rightmargin="0">
<table border="0" cellspacing="0" cellpadding="1" width="100%">
  <tr>
    <td align="center" width="100%">
        <BR><font style="font-family:times new roman; font-size:36px; font-weight:none; color:#000000;"><%=ARR_MONTHS[curM]%>&nbsp;<%=curY%></font><br><br>
    </td>
  </tr>
</table>
<table class="table_printout" cellspacing="0" cellpadding="0" width="100%" height="90%">
  <tr>
    <td>
      <table cellspacing="0" cellpadding="0"  width="100%" height="100%">
        <tr>
<%        for (int n=1; n<6; n++)
          {
%>          <td class="cell_printout" align="center">
              <font color="#000000">
                <%=ARR_WEEKDAYS[n]%>
              </font>
            </td>
<%        }
%>      </tr>
<%      while (cur.get(Calendar.MONTH) == curM)
        {
%>      <tr width="100%" height="20%">
<%        for (int n_current_wday=0; (n_current_wday<7); n_current_wday++) 
          {
            //day = cur.get(Calendar.DATE);
            dayofweek = cur.get(Calendar.DAY_OF_WEEK);

            if((n_current_wday + 1) == firstday)
            {
              firstweek = false;
            }
            
            if(((n_current_wday + 1) != Calendar.SUNDAY)&&((n_current_wday + 1) != Calendar.SATURDAY))
            { 
%>    			  <td class="cell_printout" bgcolor="#ffffff" align="center" valign="top" width="20%">
<%              if (!firstweek && (cur.get(Calendar.MONTH) == curM))
                {
%>          	    <awsd:DailyCalendar date="<%=sdf.format(cur.getTime())%>" printable="true" uid="<%=usr.getPersonnel().getPersonnelID()%>" />
<%              }
                else
                {
%>          	    &nbsp;
<%              }
%>            </td>
<%          }
            if(!firstweek)
            {
              cur.add(Calendar.DATE, 1);
            }
          }
%>  	  </tr>
<%      }
%>     </table>
    </td>
  </tr>
</table><BR>
<span style="text-transform:capitalize;"><%=usr.getLotusUserFullNameReverse().toLowerCase() + "/AWSB"%></span>

<script language="JavaScript">
  parent.cal_main.print_cal();
</script>
</body>
</html>