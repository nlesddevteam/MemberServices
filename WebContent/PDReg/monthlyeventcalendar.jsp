<%@ page  language="java" 
          session="true" 
          import="java.util.*, 
          				java.text.*, 
          				com.awsd.pdreg.*,
          				com.awsd.security.*,
          				com.nlesd.school.bean.*,
          				com.nlesd.school.service.*,
          				org.apache.commons.lang.*"
          isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="awsd" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="CALENDAR-VIEW" />

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

  String prevM = (String) request.getAttribute("PrevMonth");
  String nextM = (String) request.getAttribute("NextMonth");
  String prevY = (String) request.getAttribute("PrevYear");
  String nextY = (String) request.getAttribute("NextYear");
  int curM = ((Integer) request.getAttribute("CurrentMonth")).intValue();
  int curY = ((Integer) request.getAttribute("CurrentYear")).intValue();
  //int curD;
  int firstday;

  int day;
  int dayofweek;
  boolean firstweek;

  Calendar cur = null;
  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
  User usr = (User) session.getAttribute("usr");
  UserPermissions permissions = null;

  cur =  Calendar.getInstance();
  //curD = cur.get(Calendar.DATE);
  cur.set(curY, curM, 1);
  firstday = cur.get(Calendar.DAY_OF_WEEK);

  firstweek = true;
  
  MonthlyCalendar month = (MonthlyCalendar) request.getAttribute("MonthlyEvents");
%>

<html>

<head>
  <title>Newfoundland &amp; Labrador English School District - Event Calendar</title>
  <link rel="stylesheet" href="css/redmond/jquery-ui.min.css"  />
  <link href="../css/calendar.css" rel="stylesheet">
  <script type='text/javascript' src='js/jquery-1.11.2.min.js'></script>
  <script type='text/javascript' src='js/jquery-ui.min.js'></script>
  <script type="text/javascript" src="../js/common.js"></script>
  <script type="text/javascript">
    function print_cal()
    {
      parent.cal_hidden.focus();
      window.print();
    }
    
    function loaded()
    {
    	var l = document.getElementById('loading');
    	l.style.display = 'none';
    	
    	var c = document.getElementById('calendar');
    	c.style.display = 'inline';
    }
    
    $(function(){
    	$('.btnRegionView').click(function(){
    		self.location.href = '/MemberServices/PDReg/viewMonthlyCalendar.html?dt=<%= month.getMonth() %>&region-id=' + $(this).attr('data-region-id');	
    	});
    	
    	$('#regionized-view').buttonset();
    	
    	
   		var region_id = <%= month.getZone() != null ? month.getZone().getZoneId() : "0" %>;
   		$('.btnRegionView').each(function(){
   			if($(this).attr('data-region-id') == region_id){
   				$(this).css({'color': 'red', 'font-weight' : 'bold'});
   			}
   		});
    	
    });
  </script>
</head>

<body bgcolor="#FFFFFF" marginheight="5" marginwidth="5" topmargin="5" leftmargin="5" rightmargin="5">
	<div id='calendar' style='display:inline;' width='100%'>
		<form name="month_selector" action="viewMonthlyCalendar.html" method="POST">
			<% if(month.getZone() != null) { %>
				<input type='hidden' name='region-id' value='<%= month.getZone().getZoneId() %>' />
			<% } %>
           <table cellspacing="0" cellpadding="0" border="0" width="100%">
              <tr>
                <td valign="top" bgcolor="#646464" width="100%">
                  <img src="images/spacer.gif" width="1" height="10"><br>
                </td>
              </tr>  
                <tr>
                  <td height="1" bgcolor="#FFFFFF" width="100%">
                    <img src="images/spacer.gif" width="1" height="1"><br>
                  </td>
                </tr>
              
              <tr>
                <td valign="top" width="100%">
                  <table cellspacing="0" cellpadding="0" border="0" width="100%">
                    <tr>
                      <td width="433px">
                        <img src="images/cal_title_pt1.gif"><img src="images/cal_title_pt2.jpg"><br>
                      </td>
                      
                      <td width="*" align="left">
                      	
                      	<div style='float:left; vertical-align:middle;'>
                      		<fieldset style='border: none;'>
	                      		<div id='regionized-view'>
		                      		<button class='btnRegionView' type='button' data-region-id='0'>All Regions</button>
		                      		<% for(SchoolZoneBean sz : SchoolZoneService.getSchoolZoneBeans()){ 
		                      				if(sz.getZoneId() == 5) continue; //NLESD - PROVINCIAL
		                      			%>
		                      			<button class='btnRegionView' type='button' data-region-id="<%= sz.getZoneId() %>"><%= StringUtils.capitalize(sz.getZoneName())%></button>
		                      		<%}%>
	                      		</div>
                      		</fieldset>
                      	</div>
                      	
                      	<div style='float:right;'>
                      	<table cellspacing="0" cellpadding="0" border="0">
                      		<tr>
	                      		<td width="100px">
			                        <img name="info" src="images/spacer.gif"><br>
			                      </td>
                      			<!--
			                      <td>
			                        <img src="images/viewlegend_01.jpg" 
			                          onmouseover="src='images/viewlegend_02.jpg'; info.src='images/view_legend.gif';" 
			                          onmouseout="src='images/viewlegend_01.jpg'; info.src='images/spacer.gif';" 
			                          onmousedown="src='images/viewlegend_03.jpg';"
			                          onmouseup="src='images/viewlegend_02.jpg';"
			                          onclick="openWindow('Legend', 'viewLegend.html', 410, 575, 0);"><br>
			                      </td>
			                      -->
			                      <td>&nbsp;</td>
			                      <td>
			                        <img src="images/myevents_01.jpg" title="My Events" onclick="openWindow('MemberEvents', 'viewMemberEvents.html', 450, 480, 1);"><br>
			                      </td>
			                      <% if(usr.checkPermission("CALENDAR-VIEW-SCHOOL-ENROLLMENT")) { %>
			                      <td>&nbsp;</td>
			                      <td>
			                        <img src="images/school_01.jpg" title="View School Enrollment" onclick="openWindow('SchoolEnrollment', 'viewSchoolRegistrations.html?id=<%=usr.getPersonnel().getSchool().getSchoolID()%>', 400, 465, 1);"><br>
			                      </td>
			                      <% } %>
			                      <% if(usr.checkPermission("CALENDAR-SCHEDULE")) { %>
			                      <td>&nbsp;</td>
			                      <td>
			                        <img src="images/sch_event_01.jpg" title="Schedule Event" onclick="openWindow('schedule', 'scheduleEvent.html?passthrough=true', 420, 600, 1);"><br>
			                      </td>
			                      <% } %>
			                      <td>&nbsp;</td>
			                      <td>
			                        <img src="images/printer_friendly_01.jpg" title="Print" onclick="self.parent.cal_hidden.location.href='viewPrinterFriendlyCalendar.html?dt=<%=(new SimpleDateFormat("yyyyMM")).format(cur.getTime())%>&region-id=<%= month.getZone() != null ? month.getZone().getZoneId() : "0" %>';"><br> 
			                      </td>
                      		</tr>
                      	</table>
                      	</div>
                      	<div style='clear:both;'></div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                  <td height="1" bgcolor="#FFFFFF" width="100%">
                    <img src="images/spacer.gif" width="1" height="1"><br>
                  </td>
              </tr>
            </table>
  
<table class="clsOTable" cellspacing="0" border="0" width="100%">
  <tr>
    <td bgcolor="#4682B4">
      <table cellspacing="1" cellpadding="3" border="0" width="100%">
        <tr>
          <td colspan="7">
            <table cellspacing="0" cellpadding="0" border="0" width="100%">
              <tr>
                <td>
                  <a href="viewMonthlyCalendar.html?dt=<%=prevY%><%= month.getZone() != null ? "&region-id=" + month.getZone().getZoneId() : "" %>"><img src="<%=STR_ICONPATH%>prev_year.gif" width="16" height="16" border="0" alt="previous year"></a>&nbsp;<a href="viewMonthlyCalendar.html?dt=<%=prevM%><%= month.getZone() != null ? "&region-id=" + month.getZone().getZoneId() : "" %>"><img src="<%=STR_ICONPATH%>prev.gif" width="16" height="16" border="0" alt="previous month"></a>
                </td>
                <td align="center" width="100%">
                  <font size="3" color="#ffffff"><b><%=ARR_MONTHS[curM]%>&nbsp;<%=curY%></b></font>
                </td>
                <td>
                  <a href="viewMonthlyCalendar.html?dt=<%=nextM%><%= month.getZone() != null ? "&region-id=" + month.getZone().getZoneId() : "" %>"><img src="<%=STR_ICONPATH%>next.gif" width="16" height="16" border="0" alt="next month"></a>&nbsp;<a href="viewMonthlyCalendar.html?dt=<%=nextY%><%= month.getZone() != null ? "&region-id=" + month.getZone().getZoneId() : "" %>"><img src="<%=STR_ICONPATH%>next_year.gif" width="16" height="16" border="0" alt="next year"></a>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
<%        for (int n=0; n<7; n++)
          {
%>          <td bgcolor="#87cefa" align="center">
              <font color="#ffffff">
                <%=ARR_WEEKDAYS[n]%>
              </font>
            </td>
<%        }
%>      </tr>
<%      while (cur.get(Calendar.MONTH) == curM)
        {
%>      <tr>
<%        for (int n_current_wday=0; (n_current_wday<7); n_current_wday++) 
          {
            day = cur.get(Calendar.DATE);
            dayofweek = cur.get(Calendar.DAY_OF_WEEK);

            if((n_current_wday + 1) == firstday)
            {
              firstweek = false;
            }
%>    			<td bgcolor="#ffffff" align="right" valign="top" width="14%">
<%            if (!firstweek && (cur.get(Calendar.MONTH) == curM))
              {
%>          	  <awsd:DailyCalendar date="<%=sdf.format(cur.getTime())%>" printable="false" uid="<%=usr.getPersonnel().getPersonnelID()%>" zone="<%= month.getZone() %>" />
<%            }
              else
              {
%>          	  &nbsp;
<%            }
%>          </td>
<%          if(!firstweek)
            {
              cur.add(Calendar.DATE, 1);
            }
          }
%>  	  </tr>
<%      }
%>      <tr>
          <td bgcolor="#4682B4" colspan="7">
              <select name="dt" size="1" onchange="if(document.month_selector.dt.value != -1) document.month_selector.submit();">
                <option value='-1'>SELECT MONTH</option>
<%              sdf = new SimpleDateFormat("yyyyMM");
                cur.set(curY, curM, 1);
%>              <option value='<%=sdf.format(cur.getTime())%>'>CURRENT MONTH</option>
<%              cur.add(Calendar.MONTH, -7);
                for(int i=0; i < 13; i++)
                {
                  cur.add(Calendar.MONTH, 1);
%>                <option value='<%=sdf.format(cur.getTime())%>'><%=ARR_MONTHS[cur.get(Calendar.MONTH)] + " " + cur.get(Calendar.YEAR)%></option>
<%              }
%>            </select>
          </td>
        </tr>  
      </table>
    </td>
  </tr>
</table>
</form>
</div>

</body>
</html>