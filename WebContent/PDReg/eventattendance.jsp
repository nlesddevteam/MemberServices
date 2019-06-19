<%@ page  language="java" 
          session="true" 
          import="java.util.*, 
            java.text.*, 
            com.awsd.pdreg.*,
            com.awsd.security.*,
            com.awsd.personnel.*,
            com.awsd.school.*,
            com.awsd.common.Utils,
            com.nlesd.school.bean.*,
            com.nlesd.school.service.*"
          isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions='CALENDAR-SCHEDULE' />

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

  int dayofweek;

  Calendar cur = null;
  User usr = null;
  EventAttendeeCollection attendees = null;
  Date now = null;
  Date evtdt = null;
  Date evtenddt = null;
  Event evt = null;
  School s = null;
  boolean other;
  String status = "";
  SimpleDateFormat sdf = null;

  int sid = -1;
  
  attendees = (EventAttendeeCollection) request.getAttribute("EventAttendees");
  
  cur =  Calendar.getInstance();
  now = cur.getTime();
  
  dayofweek = cur.get(Calendar.DAY_OF_WEEK);

  evt = attendees.getEvent();
  evtdt = evt.getEventDate();
  evtenddt = evt.getEventEndDate();

  other = false;

  if(evt.isActive())
    status = "ACTIVE";
  else if(evt.isPast())
    status = "PAST";
  else
    status = "";
  
  sdf = new SimpleDateFormat("MM/dd/yyyy");
%>

<html>

<head>
<title>Newfoundland &amp; Labrador English School District - Event Attendees</title>
<link rel="stylesheet" href="css/redmond/jquery-ui.min.css"  />
<style>
	td {font-family: Tahoma, Verdana, sans-serif; font-size: 12px;}
</style>

<script type='text/javascript' src='js/jquery-1.11.2.min.js'></script>
<script type='text/javascript' src='js/jquery-ui.min.js'></script>

<script type="text/javascript" src="../js/common.js"></script>

<script type="text/javascript">
	$(function(){
		$('#lnk-add-attendee').click(function(){
			$('#div-add-attendee-panel').toggle();
		});
		
		$('#lst-school-id').change(function(){
			$('#lst-attendee-id').children('option').remove();
			$('#lst-attendee-id').append($('<option>').val('').text('--- SELECT ATTENDEE ---'));
			
			if($(this).val() != "") {
				var params = {};
				params.id = $(this).val();
				
				$.post('/MemberServices/PDReg/ajax/getSchoolPersonnel.html', params, function(xml){
					
					$(xml).find('PERSONNEL').each(function(){
						$('#lst-attendee-id').append($('<option>').val($(this).find('ID').text()).text($(this).find('DISPLAY').text()));	
					});
					
				});
			}
		});
		
		$('#lst-school-id, #lst-attendee-id').change(function(){
			$('#div-add-attendee-msg').html('&nbsp;');
		});
		
		$('#btn-add-attend-submit').click(function(){
			if($('#lst-attendee-id').val() == '') {
				$('#div-add-attendee-msg').html('Select attendee to add.');
				$(this).button('enable');
			}
			else{
				$('#div-add-attendee-msg').html($('<img>').attr('src', 'images/processing_ani.gif'));
				$(this).button('disable');
				$('#frm-add-attendee').submit();
			}
		});
		
		$('#btn-update-attendance').click(function(){
			$(this).button('disable');
			$('#td-update-attendance').html($('<img>').attr('src', 'images/processing_ani.gif'));
			$('#frm-update-attendance').submit();
		});
		
		$('button').button();
		
	});
	
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
          	<div style='padding: 2px; font-size: 14px; font-weight:bold; text-decoration: underline; vertical-align: middle;'>
          		<div style='float:left;padding-right: 2px;'><img src='images/add_attendee.png' /></div>
          		<div style='float:left;'><a id='lnk-add-attendee' href='javascript:void(0);' style='color: white;'>Missing Attendee?</a></div>
          		<div style='clear:both;'></div>
          	</div>
	          <div id='div-add-attendee-panel' style='display:none; border: solid 1px #333333; background-color: #f9f9f9;padding: 3px;'>
	          	<form id='frm-add-attendee' action='addEventAttendee.html' method='POST'>
	          		<input type='hidden' name='hdn-event-id' value='<%= evt.getEventID() %>' />
		          	<div style='padding: 2px;'>
		          		<div style='float:left; width: 75px; text-align:right;'><strong>School:</strong>&nbsp;</div>
		          		<div style='float:left;'>
			              <select id="lst-school-id" name="lst-school-id">
			              	<option value="">--- SELECT SCHOOL/LOCATION ---</option>
			              	<% for(SchoolZoneBean sz : SchoolZoneService.getSchoolZoneBeans()){ %>
			              		<optgroup label="<%= sz.getZoneName().toUpperCase() %>">
				              	<% for(School sch : SchoolDB.getSchools(sz)){ %>
				              			<option value="<%= sch.getSchoolID() %>"><%= sch.getSchoolName() %></option>
				              	<% } %>
				              	</optgroup>
			              	<% } %>
			              </select>
		              </div>
		              <div style='clear:both;'></div>
	              </div>
	              <div style='padding: 2px;'>
	              	<div style='float:left; width: 75px; text-align:right;'><strong>Attendee:</strong>&nbsp;</div>
		              <div style='float:left;'>
			              <select id="lst-attendee-id" name="lst-attendee-id">
			              	<option value="">--- SELECT ATTENDEE ---</option>
			              </select>
		              </div>
		              <div style='clear:both;'></div>
	              </div>
	              <div style='padding: 2px; text-align: right'>
	              	<div id='div-add-attendee-msg' style='float:left; vertical-align: bottom; color:red; font-weight:bold;'>&nbsp;</div>
	              	<div style='float:right;'>
		              	<button id='btn-add-attend-submit' type='button'>Add Attendee</button>
		              </div>
		              <div style='clear:both;'></div>
	              </div>
	              <div style='clear:both;'></div>
              </form>
	          </div>
          </td>
        </tr>
<%      if(attendees == null || (attendees.size() <= 0))
        {
%>        <tr>
              <td bgcolor="#ffffff" align="center"><font color="#FF0000"><B>No Registered Personnel.</B></font></td>
          </tr>
<%      }
        else
        {
%>        <tr>
              <td align="center" colspan="2">
              	<form id='frm-update-attendance' action='updateEventAttendance.html' method='POST'>
              	<input type='hidden' name='hdn-event-id' value='<%= evt.getEventID() %>' />
                <table cellspacing="1" cellpadding="3" border="0" width="100%">
                	<tr>                
                      <td bgcolor="#e5e5e5" align="left">
                        &nbsp;
                      </td>
                      <td bgcolor="#e5e5e5" align="center">
                        <b>Attended?</b>
                      </td>
                  </tr>
                  <% for (EventAttendee ea : attendees) {
                    s = ea.getPersonnel().getSchool();
                    
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
                      <td bgcolor="#ffffff" align="left"><%= ea.getPersonnel().getFullName()%></td>
                      <% if(evt.isActive() || evt.isPast()) { %>
                          <td bgcolor="#ffffff" align="center">
                            <input type="checkbox" name='attendees[]' 
                            	value='<%= ea.getPersonnel().getPersonnelID() %>' <%= ea.isAttended() ? " CHECKED" : "" %> />
                          </td>
                      <% } %>
                    </tr>
<%                }
%>              </table>
								</form>
              </td>
          </tr>
<%      }
%>    <tr>
        <td id='td-update-attendance' colspan="2" align="center">
          <button id='btn-update-attendance' type='button'>Confirm</button>
        </td>
      </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>