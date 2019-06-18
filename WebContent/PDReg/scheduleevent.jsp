<%@ page language="java"
         session="true"
         import="com.awsd.security.*,
                 com.awsd.pdreg.*,
                 com.awsd.pdreg.dao.*,
                 com.awsd.school.*,
                 com.nlesd.school.bean.*,
                 com.nlesd.school.service.*,
                 java.util.*,
                 org.apache.commons.lang.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="CALENDAR-SCHEDULE" />

<%
  User usr = null;
  boolean private_only = false;
  boolean isPrincipal = false;
  boolean isVicePrincipal = false;
  School school = null;

  usr = (User) session.getAttribute("usr");

  if(usr.getUserPermissions().containsKey("CALENDAR-SCHEDULE-PRIVATE-ONLY"))
  {
    private_only=true;
  }
  else
  {
    private_only = false;
  }

  if(usr.getUserRoles().containsKey("PRINCIPAL"))
  {
    isPrincipal = true;
    school = usr.getPersonnel().getSchool();
  }
  else if(usr.getUserRoles().containsKey("VICE PRINCIPAL"))
  {
    isVicePrincipal = true;
    school = usr.getPersonnel().getSchool();
  }
  else
  {
    isPrincipal = false;
    isVicePrincipal = false;
    school = null;
  }
  
  ArrayList<EventCategory> evtcats = EventCategoryManager.getEventCategorys();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
		<meta content="utf-8" http-equiv="encoding">
    <title>Newfoundland &amp; Labrador English School District - Schedule an Event</title>
    <link rel="stylesheet" href="css/redmond/jquery-ui.min.css"  />
    <style>
      td {font-family: Arial, Helvetica, sans-serif; font-size: 11px; line-height: 16px; color: #000000; font-weight:bold;}
    </style>

    <script type="text/javascript" src="js/common.js"></script>
    <script type="text/javascript" src="../js/common.js"></script>
    <script type='text/javascript' src='js/jquery-1.11.2.min.js'></script>
    <script type='text/javascript' src='js/jquery-ui.min.js'></script>
    
    <script type="text/javascript">
      clicked = false;
      function toggle(target) {
        $("#"+ target).toggle();
      }

      function notime()
      {
        document.schedule.shour.selectedIndex=-1;
        document.schedule.fhour.selectedIndex=-1;
        document.schedule.sminute.selectedIndex=-1;
        document.schedule.fminute.selectedIndex=-1;
        document.schedule.sAMPM.selectedIndex=-1;
        document.schedule.fAMPM.selectedIndex=-1;
      }

      function eventcheck()
      {
        var txt = document.schedule.evttype.options[document.schedule.evttype.selectedIndex].text;
        
        if(txt == 'CLOSE-OUT DAY PD SESSION')
        {
        	$('#closeoutoptionrow').show();
          $('#maxrowcheck').show();
          $('#evtreqsrow').show()
        }
        else if(txt == 'PD OPPORTUNITY')
        {
        	$('#evtreqsrow').show();
          $('#maxrowcheck').show();
          $('#closeoutoptionrow').hide();
        }
        else if(txt == 'SCHOOL PD REQUEST')
        {
          $('#adgendarowfileinput').show();
        }
        else
        {
        	$('#closeoutoptionrow').hide();
          $('#maxrowcheck').hide();
          $('#evtreqsrow').hide();
        }

        document.schedule.EventEndDate.value='';
        
        document.schedule.closeoutoption.selectedIndex = 0;
        
        document.schedule.limited.checked=false;
        $('#maxrowedit').hide();
        document.schedule.max.value='';
        
        document.schedule.evttime.checked=false;
        $('#startrowedit').hide();
        $('#finishrowedit').hide();
        notime();
      }
      
      $('document').ready(function(){
    	  
    	  $('#evttype').change(function(){
    		  eventcheck();
    	  });
    	  
    	  $('#evtreqs').change(function(){
    		  $('#evtreqs option').each(function(){
    			  if($(this).is(':selected') && $(this).attr('extrainfo') != "")
    				  $('#' + $(this).attr('extrainfo')+'row').show();
    			  else if($(this).attr('extrainfo') != "")
    				  $('#' + $(this).attr('extrainfo')+'row').hide();
    		  });
    	  });
    	  
    	  $('#lstSchoolID').change(function(){
			  	$('#hdnEventLocation').val("");
			  	$('#lstSchoolZone').val("");
    		  
			  	if($(this).val() == '0'){
    			  $('#divOtherLocation').show();
    			  $('#txtEventLocation').val("");
    		  }
    		  else {
    			  $('#divOtherLocation').hide();
    			  
    			  if($(this).val() != '') {
		    		  var params = {};
		    		  params.id = $(this).val();
		    		  
		    		  $.post('/MemberServices/PDReg/ajax/getSchool.html', params, function(xml){
		    			  $('#hdnEventLocation').val($(xml).find('NAME').text());
		    			  $('#lstSchoolZone').val($(xml).find('SCHOOL-ZONE').attr('zone-id'));
		    		  }, 'xml');
    		  	}
    		  }
    	  });
    	  
    	  $('#txtEventLocation').change(function(){
    		  $('#hdnEventLocation').val($(this).val());
    	  });
    	  
    	  $('.datefield').datepicker({
    		  dateFormat: "dd/mm/yy"
    	  });
      });
    </script>
    
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
  <form action="scheduleEvent.html" method="post" name="schedule" ENCTYPE="multipart/form-data">
    <table width="400" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td width="400" valign="top" bgcolor="#646464">
          <img src="images/spacer.gif" width="1" height="10"><br>
        </td>
      </tr>
      <tr>
        <td width="400" height="1" bgcolor="#FFFFFF">
          <img src="images/spacer.gif" width="1" height="1"><br>
        </td>
      </tr>
      <tr>
        <td width="400" valign="top">
          <img src="images/schedule_pt1.gif"><img src="images/title_pt2.jpg"><br>
        </td>
      </tr>
    </table>

      <table width="400" cellpadding="0" cellspacing="5" border="0">
        <tr>
          <td width="400" valign="top" colspan="2">
            *&nbsp;Denotes Required Field.
          </td>
        </tr>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Type:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <select id='evttype' name="evttype">
              <option value="-1">Choose Event Type</option>
            <% if(private_only) { %>
              <option value="5">PRIVATE CALENDAR ENTRY</option>
            <%} else if(isPrincipal || isVicePrincipal){%>
              <option value="41">SCHOOL PD REQUEST</option>
            <%} else {
                for(EventType type : new EventTypes()){
                  if((type.getEventTypeID() != 41)&&(type.getEventTypeID() != 61)){
              %>  <option value="<%=type.getEventTypeID()%>"><%=type.getEventTypeName()%></option>
              <%  }
                }
              }%>
            </select>
          </td>
        </tr>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Title:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <input type="text" name="EventName" size="20" title="Please Enter Event Title Here">
          </td>
        </tr>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Description:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <input type="text" name="EventDesc" size="40" title="Please Enter a description of the event here">
          </td>
        </tr>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Location:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <%if(!isPrincipal && !isVicePrincipal){%>
            	<input type="hidden" id='hdnEventLocation' name="EventLocation" value="" />
              <div>
	              <select id="lstSchoolID" name="EventSchoolID">
	              	<option value="">--- SELECT SCHOOL/LOCATION ---</option>
	              	<% for(SchoolZoneBean sz : SchoolZoneService.getSchoolZoneBeans()){ %>
	              		<optgroup label="<%= sz.getZoneName().toUpperCase() %>">
		              	<% for(School s : SchoolDB.getSchools(sz)){ %>
		              			<option value="<%= s.getSchoolID() %>"><%= s.getSchoolName() %></option>
		              	<% } %>
		              	</optgroup>
	              	<% } %>
	              	<optgroup label="Other/External Location">
	              		<option value="0">Other</option>
	              	</optgroup>
	              </select>
              </div>
              <div id='divOtherLocation' style='display:none;padding-top: 3px;'>
              	<input type="text" id="txtEventLocation" size="40" title="Please Enter the location here" />
              </div>
              <div style='padding-top: 3px;'>
	              <select id="lstSchoolZone" name="EventZoneID">
	              	<option value="">--- SELECT REGION ---</option>
	              	<% for(SchoolZoneBean sz : SchoolZoneService.getSchoolZoneBeans()){ %>
	              			<option value="<%= sz.getZoneId() %>"><%= StringUtils.capitalize(sz.getZoneName()) %> Region</option>
	              	<% } %>
	              </select>
              </div>
            <%}else{%>
              <input type="text" name="EventLocation" size="40" title="Please Enter the location here" value="<%=school.getSchoolName()%>" readonly>
              <input type='hidden' name='EventSchoolID' value='<%=school.getSchoolID()%>' />
              <input type='hidden' name='EventZoneID' value='<%=school.getZone().getZoneId()%>' />
            <%}%>
          </td>
        </tr>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Start Date:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <input type="text" class='datefield' name="EventDate" size="15" title="Please Pick the start date of your event by clicking the icon" READONLY>&nbsp;<img src="images/cal.gif" width="16" height="16" border="0" alt="Click Here to Pick the date">
          </td>
        </tr>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;&nbsp;&nbsp;End Date:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <input type="text" class='datefield' name="EventEndDate" size="15" title="Please Pick the end date of your event by clicking the icon" READONLY>&nbsp;<img src="images/cal.gif" width="16" height="16" border="0" alt="Click Here to Pick the date">
          </td>
        </tr>
        
        <tr id="closeoutoptionrow" style="display:none">
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Session &nbsp;&nbsp;&nbsp;&nbsp;Option:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <select name="closeoutoption" title="Please Select Closeout Session Option">
              <option value="-1">Choose Close-out Session Option
              <option value="A">Option A (All Day)</option>
              <option value="B">Option B (AM)</option>
              <option value="C">Option C (PM)</option>
            </select>            
          </td>
        </tr>
        <tr id="maxrowcheck" style="display:none">
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <input type="checkbox" name="limited" title="Limit number of participants?" onClick="if(limited.checked) max.value=''; toggle('maxrowedit');">&nbsp;&nbsp;*&nbsp;Limited Number of Participants?
          </td>
        </tr>
        <tr id="maxrowedit" style="display:none">
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Participants:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <input type="text" name="max" size="10" title="Please Enter the maximum number of participants">
          </td>
        </tr>
        <tr id="timerowcheck">
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <input type="checkbox" name="evttime" onclick="notime(); toggle('startrowedit'); toggle('finishrowedit');" /><label for='evttime'>&nbsp;&nbsp;*&nbsp;Select Event Start/End Times?</label>
          </td>
        </tr>
        <tr id="startrowedit" style="display:none">
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Start Time:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <table>
              <tr>
                <td>
                  <select name="shour">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                  </select>
                </td>
                <td>
                  <select name="sminute">
                    <option value="00">00</option>
                    <option value="15">15</option>
                    <option value="30">30</option>
                    <option value="45">45</option>
                  </select>
                </td>
                <td>
                  <select name="sAMPM">
                    <option value="AM">AM</option>
                    <option value="PM">PM</option>
                  </select>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr id="finishrowedit" style="display:none">
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Finish Time:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <table>
              <tr>
                <td>
                  <select name="fhour">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                  </select>
                </td>
                <td>
                  <select name="fminute">
                    <option value="00">00</option>
                    <option value="15">15</option>
                    <option value="30">30</option>
                    <option value="45">45</option>
                  </select>
                </td>
                <td>
                  <select name="fAMPM">
                    <option value="AM">AM</option>
                    <option value="PM">PM</option>
                  </select>
                </td>
              </tr>
            </table>
          </td>
        </tr>
         
        <tr id="adgendarowfileinput">
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Agenda:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <input type="file" name="agendafile" size="30" title="Please Enter your agenda file." />
          </td>
        </tr>
        
        <tr id='evtcatsrow'>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Categories:<br/>
            <span style='font-size:9px;'>(Use CTRL for multiple categories.)</span>
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
          	<select id='evtcats' name='evtcats' multiple='multiple' style='height:175px;'>
          		<% for(EventCategory ec : evtcats){ %>
          				<option value='<%= ec.getCategoryId() %>'><%= ec.getCategoryName() %></option>
          		<% } %>
          	</select>
          </td>
        </tr>
        
        <tr id='evtreqsrow' style='display:none;'>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Requirements:<br/>
            <span style='font-size:9px;'>(Use CTRL for multiple requirements.)</span>
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
          	<select id='evtreqs' name='evtreqs' multiple='multiple' style='height:175px;'>
          		<optgroup label='Technology'>
          			<option value='Computer Access'>Computer Access</option>
          			<option value='Computer Bank' extrainfo='computerbank'>Computer Bank</option>
          			<option value='LCD Viewer'>LCD Viewer</option>
          			<option value='Internet Access'>Internet Access</option>
	          		<option value='Smartboard Access'>Smartboard Access</option>
	          		<option value='Software Installation' extrainfo='softwarerequired'>Software Installation</option>
          		</optgroup>
          		<optgroup label="Presentation Materials">
          			<option value='Flip Chart Paper and Markers'>Flip Chart Paper and Markers</option>
          		</optgroup>
          		<optgroup label="Other">
          			<option value='Special Requirements' extrainfo='otherreqs'>Special Requirements</option>
          		</optgroup>
          	</select>
          </td>
        </tr>
        
        <tr id="computerbankrow" style="display:none">
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;# Computers:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <input type="text" id="computerbank" name="computerbank" style='width:100px;' title="# Computers Required.">
          </td>
        </tr>
        
        <tr id="softwarerequiredrow" style="display:none">
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Software:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <input type="text" id="softwarerequired" name="softwarerequired" style='width:250px;' title="Please Enter Software Required.">
          </td>
        </tr>
        
        <tr id="otherreqsrow" style="display:none">
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Special Requirements:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
          	<textarea id="otherreqs" name="otherreqs" style='width:250px;height:75px;' title="Special Requirements."></textarea>
          </td>
        </tr>
        
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <input type="checkbox" name="chk-is-government-funded" /><label for='chk-is-government-funded'>&nbsp;&nbsp;*&nbsp;Is Event Funded by Nunatsiavut Government?</label>
          </td>
        </tr>
      </table>
      
      <table width="400" cellpadding="0" cellspacing="5" border="0">
        <% if(request.getAttribute("msg") != null) { %>
        <tr>
          <td width="400" align="center" valign="middle" bgcolor="#FFFFFF">
            <font color="#FF0000">
              <%= request.getAttribute("msg") %>
            </font>
          </td>
        </tr>
        <% } %>
        <tr>
          <td width="400" valign="middle" bgcolor="#f4f4f4">
            <table>
              <tr>
                <td align="left" width="175">
                  <img name="processing" src="images/spacer.gif">
                </td>
                <td align="right">
                  <!--
                    <input type="submit" value="Schedule" onclick="return checkForm(this.form)">
                  -->
                  <img name="confirm" name="schbtn" src="images/schedule_01.jpg" 
                       onmouseover="src='images/schedule_02.jpg';"
                       onmouseout="src='images/schedule_01.jpg';"
                       onmousedown="src='images/schedule_03.jpg';"
                       onmouseup="src='images/schedule_02.jpg';"
                       onclick="if(validateEvent(document.schedule)==true)onClick(document.schedule);">
                  <!--
                    <input type="submit" value="Close" onClick="javascript:window.opener.location.reload();window.close();">
                  -->
                  <img name="cancel" src="images/close_01.jpg" 
                       onmouseover="src='images/close_02.jpg';"
                       onmouseout="src='images/close_01.jpg';"
                       onmousedown="src='images/close_03.jpg';"
                       onmouseup="src='images/close_02.jpg';"
                       onclick="window.opener.location.reload();window.close();">
                </td>
              </tr>
            </table>
          </td>
        </tr> 
      </table>
    </form>
    <script language="JavaScript">     
      notime();
      document.forms['schedule'].elements['closeoutoption'].selectedIndex=0;
    </script>
  </body>
</html>