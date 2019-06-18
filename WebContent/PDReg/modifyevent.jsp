<%@ page language="java"
         import="com.awsd.security.*,
                 com.awsd.pdreg.*,
                 com.awsd.pdreg.dao.*,
                 com.awsd.school.*,
                 com.awsd.personnel.*,
                 com.nlesd.school.bean.*,
                 com.nlesd.school.service.*,
                 java.util.*,
                 java.text.*,
                 org.apache.commons.lang.*"
         isThreadSafe="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>                
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="CALENDAR-SCHEDULE" />

<%
  User usr = (User) session.getAttribute("usr");
	boolean private_only = false;
	boolean isPrincipal = false;
	boolean isVicePrincipal = false;
	School school = null;
  UserPermissions permissions = null;
  Event evt = null;  
  boolean modified;
  int width;
  
  evt = (Event) request.getAttribute("evt");
  modified = ((Boolean) request.getAttribute("modified")).booleanValue();
  if(modified)
  {
    width = 280;
  }
  else
  {
    width = 175;
  }
  
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
  
  List<Personnel> schedulers = (List<Personnel>) request.getAttribute("SCHEDULERS");
%>

<html>
  <head>
  	<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
		<meta content="utf-8" http-equiv="encoding">
    <title>Newfoundland &amp; Labrador English School District - Modify an Event</title>
    <link rel="stylesheet" href="css/redmond/jquery-ui.min.css"  />
    <style>
      td {font-family: Arial, Helvetica, sans-serif; font-size: 11px; line-height: 16px; color: #000000; font-weight:bold;}
    </style>

    <script language="JavaScript" src="js/common.js"></script>
    <script langauge="JavaScript" src="../js/common.js"></script>
    <script type='text/javascript' src='js/jquery-1.11.2.min.js'></script>
    <script type='text/javascript' src='js/jquery-ui.min.js'></script>
    
    <script type='text/javascript'>
      clicked = false;

      function toggle(target) {
          $("#"+ target).toggle();
      }
      
      function notime()
      {
        document.modify.shour.selectedIndex=-1;
        document.modify.fhour.selectedIndex=-1;
        document.modify.sminute.selectedIndex=-1;
        document.modify.fminute.selectedIndex=-1;
        document.modify.sAMPM.selectedIndex=-1;
        document.modify.fAMPM.selectedIndex=-1;
      }

      function eventcheck()
      {
        var txt = document.modify.evttype.options[document.modify.evttype.selectedIndex].text;
        
        if(txt == 'CLOSE-OUT DAY PD SESSION')
        {
        	$('#closeoutoptionrow').show();
        	$('#maxrowcheck').show();
          $('#evtreqsrow').show();
        }
        else if(txt == 'PD OPPORTUNITY')
        {
        	$('#maxrowcheck').show();
        	$('#closeoutoptionrow').hide();
          $('#evtreqsrow').show();
        }
        else
        {
        	$('#closeoutoptionrow').hide();
        	$('#maxrowcheck').hide();
          $('#evtreqsrow').hide();
        }
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
	  	  
	  	  $('#btn-confirm').click(function(){
	  			if(validateEvent(document.modify)){ 
	  				$('#img-processing').attr('src', '/MemberServices/images/processing_ani.gif');
	  				$('#btn-confirm, #btn-cancel').hide();
	  				
	  				document.modify.submit();
	  	  	}
	  	  });
	  	  
	  	  $('.datefield').datepicker({
	  		  dateFormat: "dd/mm/yy"
	  	  });
    	  
    	  <%
    	  	if(evt.getEventRequirements().size() > 0 && !modified) {
    	  		for(EventRequirement reg : evt.getEventRequirements()) {
    	  %>		var opt = $("#evtreqs option[value='<%= reg.getRequirement() %>']");
    	  			opt.attr('selected', 'selected');
    	  			if(opt.attr('extrainfo')){
    	  				$('#' + opt.attr('extrainfo') + 'row').show();
    	  				$('#' + opt.attr('extrainfo')).val('<%= reg.getExtrainfo() %>');
    	  			}
    	  <%	}
    	  	}
    	  %>
    	  
    	  <%
  	  		if(evt.getEventCategories().size() > 0 && !modified) {
  	  			for(EventCategory cat : evt.getEventCategories()) { %>		
  	  				$("#evtcats option[value='<%= cat.getCategoryId() %>']").attr('selected', 'selected');
  	  	<%	}
  	  		}
  	  	%>
    	  
    	  <%if((evt.getEventSchoolID() <= 0 && StringUtils.isNotEmpty(evt.getEventLocation()))) {%>
    	  	$('#divOtherLocation').show();
    	  <%}else{%>
    	  	$('#divOtherLocation').hide();
    	  <%}%>
    	  
    		<%if(evt.isCloseOutDaySession() || evt.isPDOpportunity()){%>
    			$('#maxrowcheck').show();
    		<%}else{%>
    			$('#maxrowcheck').hide();
    		<%}%>
    		
    		<%if((evt.isCloseOutDaySession() || evt.isPDOpportunity()) && (evt.getEventMaximumParticipants() > 0)){%>
					$('#maxrowedit').show();
				<%}else{%>
					$('#maxrowedit').hide();
				<%}%>
				
				<%if(!evt.getEventStartTime().equals("UNKNOWN")){%>
					$('#startrowedit').show();
				<%}else{%>
					$('#startrowedit').hide();
				<%}%>
				
				<%if(!evt.getEventStartTime().equals("UNKNOWN")){%>
					$('#finishrowedit').show();
				<%}else{%>
					$('#finishrowedit').hide();
				<%}%>	
				
				<%if(evt.getEventRequirements().size() > 0){%>
					$('#evtreqsrow').show();
				<%}else{%>
					$('#evtreqsrow').hide();
				<%}%>
				
				<%if(evt.isCloseOutDaySession()){%>
					$('#closeoutoptionrow').show();
				<%}else{%>
					$('#closeoutoptionrow').hide();
				<%}%>
				
				eventcheck();
				
				$('button').button();
      });
    </script>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
    <form action="modifyEvent.html" method="get" name="modify" onsubmit="return validateForm(this);">
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
            <img src="images/modify_pt1.gif"><img src="images/title_pt2.jpg"><br>
          </td>
        </tr>
      </table>
      
    
      <input type="hidden" name="id" value="<%=evt.getEventID()%>">
      <input type="hidden" name="confirmed" value="true">
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
            <%if(!modified){%>
              <select id='evttype' name="evttype">
	              <option value="-1">Choose Event Type</option>
	            	<% if(private_only) { %>
	              	<option value="5">PRIVATE CALENDAR ENTRY</option>
	            	<%} else if(isPrincipal || isVicePrincipal){%>
	              	<option value="41">SCHOOL PD REQUEST</option>
	            	<%} else {
	              		for(EventType type : new EventTypes()){
	                  	if((type.getEventTypeID() != 41)&&(type.getEventTypeID() != 61)){
	              %>  		<option value="<%=type.getEventTypeID()%>" <%=(type.getEventTypeID()==evt.getEventType().getEventTypeID())?"SELECTED":""%>><%=type.getEventTypeName()%></option>
	            	<%  	}
	                	}
	              	}
	              %>
            	</select>
            <%}else{%>
               <%=evt.getEventType().getEventTypeName()%>
            <%}%>
          </td>
        </tr>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Title:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <% if(!modified) { %>
              <input type="text" name="EventName" size="20" title="Please Enter your Name Here" value="<%=evt.getEventName()%>">
            <% } else { %>
              <%=evt.getEventName()%>
            <% } %>
          </td>
        </tr>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Description:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <% if(!modified) { %>
              <input type="text" name="EventDesc" size="40" title="Please Enter a description of the event here" value="<%=evt.getEventDescription()%>">
            <% } else { %>
              <%=evt.getEventDescription()%>
            <% } %>
          </td>
        </tr>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Location:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <% if(!modified) { %>
              <%if(!isPrincipal && !isVicePrincipal){%>
	              <input type="hidden" id='hdnEventLocation' name="EventLocation" value="<%=evt.getEventLocation()%>" />
	              <div>
		              <select id="lstSchoolID" name="EventSchoolID">
		              	<option value="">--- SELECT SCHOOL/LOCATION ---</option>
		              	<% for(SchoolZoneBean sz : SchoolZoneService.getSchoolZoneBeans()){ %>
		              		<optgroup label="<%= sz.getZoneName().toUpperCase() %>">
			              	<% for(School s : SchoolDB.getSchools(sz)){ %>
			              			<option value="<%= s.getSchoolID() %>" <%=(s.getSchoolID()==evt.getEventSchoolID())?"SELECTED":""%>><%= s.getSchoolName() %></option>
			              	<% } %>
			              	</optgroup>
		              	<% } %>
		              	<optgroup label="Other/External Location">
		              		<option value="0" <%=(evt.getEventSchoolID() <= 0 && StringUtils.isNotEmpty(evt.getEventLocation()))?"SELECTED":""%>>Other</option>
		              	</optgroup>
		              </select>
	              </div>
	              <div id='divOtherLocation' style='display:none;padding-top: 3px;'>
	              	<input type="text" id="txtEventLocation" size="40" title="Please Enter the location here" value="<%=evt.getEventLocation()%>" />
	              </div>
	              <div style='padding-top: 3px;'>
		              <select id="lstSchoolZone" name="EventZoneID">
		              	<option value="">--- SELECT REGION ---</option>
		              	<% for(SchoolZoneBean sz : SchoolZoneService.getSchoolZoneBeans()){ %>
		              			<option value="<%= sz.getZoneId() %>" <%=(sz.getZoneId()==evt.getEventSchoolZoneID())?"SELECTED":""%>><%= StringUtils.capitalize(sz.getZoneName()) %> Region</option>
		              	<% } %>
		              </select>
	              </div>
              <%}else{%>
	              <input type="text" name="EventLocation" size="40" title="Please Enter the location here" value="<%=school.getSchoolName()%>" readonly>
	              <input type="text" id="txtEventZone" size="40" value="<%= StringUtils.capitalize(school.getZone().getZoneName()) %> Region" readonly>
	              
	              <input type='hidden' name='EventSchoolID' value='<%=school.getSchoolID()%>' />
	              <input type='hidden' name='EventZoneID' value='<%=school.getZone().getZoneId()%>' />
            	<%}%>
            <% } else { %>
              <%=evt.getEventLocation() + ((evt.getEventSchoolZone() != null) ? " - " + StringUtils.capitalize(evt.getEventSchoolZone().getZoneName()) + " Region" : "") %>
            <% } %>
          </td>
        </tr>
        
	      <tr>
	        <td width="100" valign="top" bgcolor="#f4f4f4">
	          &nbsp;&nbsp;*&nbsp;Host:
	        </td>
	        <td width="300" valign="top" bgcolor="#FFFFFF">
	        	<% if(!modified && usr.isAdmin() && evt.hasParticipants()) { %>
		        	<select id='ddl-scheduler' name='ddl-scheduler'>
			          <% for(Personnel sch : schedulers) { %>
			          		<option value='<%= sch.getPersonnelID() %>' <%= ((evt.getSchedulerID() == sch.getPersonnelID()) ? " selected='selected'" : "") %>><%= sch.getDisplay() %></option>
			          <% } %>
		          </select>
	          <% } else { %>
	          	<%= evt.getScheduler().getFullName() %>
	          <% } %>
	        </td>
	      </tr>
	      
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Start Date:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <% if(!modified) { %>
              <input type="text" class='datefield' name="EventDate" size="15" title="Please Pick the date of your event by clicking the icon" value="<%=(new SimpleDateFormat("dd/MM/yyyy")).format(evt.getEventDate())%>" READONLY>&nbsp;<img src="images/cal.gif" width="16" height="16" border="0" alt="Click Here to Pick the date">
            <% } else { %>
              <%=evt.getEventDate()%>
            <% } %>
          </td>
        </tr>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;&nbsp;&nbsp;End Date:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <% if(!modified) { %>
              <input type="text" class='datefield' name="EventEndDate" size="15" title="Please Pick the end date of your event by clicking the icon" value="<%=(evt.getEventEndDate() != null)?(new SimpleDateFormat("dd/MM/yyyy")).format(evt.getEventEndDate()):""%>" READONLY>
              <img src="images/cal.gif" width="16" height="16" border="0" alt="Click Here to Pick the date">&nbsp;
              <img src="images/eraser.jpg" width="16" height="16" border="0" alt="Click Here to clear event end date" style="cursor:hand;" onclick="modify.EventEndDate.value='';">
            <% } else { %>
              <%=(evt.getEventEndDate()!=null)?evt.getEventEndDate().toString():"&nbsp;"%>
            <% } %>
          </td>
        </tr>
        <tr id="closeoutoptionrow" style='display:<%=(evt.isCloseOutDaySession())?"inline":"none"%>'>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Session &nbsp;&nbsp;&nbsp;&nbsp;Option:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <% if (!modified) { %>
              <select name="closeoutoption" title="Please Select Closeout Session Option">
                <option value="-1" <%=(evt.getEventCloseoutOption().equalsIgnoreCase("-1"))?"SELECTED":""%>>Choose Close-out Session Option
                <option value="A" <%=(evt.getEventCloseoutOption().equalsIgnoreCase("A"))?"SELECTED":""%>>Option A</option>
                <option value="B" <%=(evt.getEventCloseoutOption().equalsIgnoreCase("B"))?"SELECTED":""%>>Option B</option>
                <option value="C" <%=(evt.getEventCloseoutOption().equalsIgnoreCase("C"))?"SELECTED":""%>>Option C</option>
              </select>
            <% } else { %>
              <%= "Option " + evt.getEventCloseoutOption()%>
            <% } %>
          </td>
        </tr>
        <% if(!modified) { %>
        <tr id="maxrowcheck">
          <td width="100" valign="top" bgcolor="#f4f4f4">
              &nbsp;
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
              <input type="checkbox" name="limited" title="Limit number of participants?" <%=(evt.getEventMaximumParticipants() > 0)?"CHECKED":""%> onclick="toggle('maxrowedit');">&nbsp;&nbsp;*&nbsp;Limited Number of Participants?
          </td>
        </tr>
        <% } %>
        <tr id="maxrowedit">
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;# Participants:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <% if(!modified) { %>
              <input type="text" name="max" size="10" value="<%=evt.getEventMaximumParticipants()%>" title="Please Enter the maximum number of participants">
            <% } else { %>
              <%=evt.getEventMaximumParticipants()%>
            <% } %>
          </td>
        </tr>
        <% if(!modified) { %>
        <tr id="timerowcheck">
          <td width="100" valign="top" bgcolor="#f4f4f4">
              &nbsp;
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
              <input type="checkbox" name="evttime" title="All Day Event?" <%=(!evt.getEventStartTime().equals("UNKNOWN"))?"CHECKED":""%> onclick="notime();toggle('startrowedit'); toggle('finishrowedit');"> &nbsp;&nbsp;*&nbsp;Select Event Start/End Times?
          </td>
        </tr>
        <% } %>
        <tr id="startrowedit">
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Start Time:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <% if(!modified) { %>
              <table>
                <tr>
                  <td>
                    <select name="shour">
                      <option value="1" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeHour().equals("1"))?"SELECTED":""%>>1</option>
                      <option value="2" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeHour().equals("2"))?"SELECTED":""%>>2</option>
                      <option value="3" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeHour().equals("3"))?"SELECTED":""%>>3</option>
                      <option value="4" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeHour().equals("4"))?"SELECTED":""%>>4</option>
                      <option value="5" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeHour().equals("5"))?"SELECTED":""%>>5</option>
                      <option value="6" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeHour().equals("6"))?"SELECTED":""%>>6</option>
                      <option value="7" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeHour().equals("7"))?"SELECTED":""%>>7</option>
                      <option value="8" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeHour().equals("8"))?"SELECTED":""%>>8</option>
                      <option value="9" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeHour().equals("9"))?"SELECTED":""%>>9</option>
                      <option value="10" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeHour().equals("10"))?"SELECTED":""%>>10</option>
                      <option value="11" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeHour().equals("11"))?"SELECTED":""%>>11</option>
                      <option value="12" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeHour().equals("12"))?"SELECTED":""%>>12</option>
                    </select>
                  </td>
                  <td>
                    <select name="sminute">
                      <option value="00" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeMinute().equals("00"))?"SELECTED":""%>>00</option>
                      <option value="15" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeMinute().equals("15"))?"SELECTED":""%>>15</option>
                      <option value="30" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeMinute().equals("30"))?"SELECTED":""%>>30</option>
                      <option value="45" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeMinute().equals("45"))?"SELECTED":""%>>45</option>
                    </select>
                  </td>
                  <td>
                    <select name="sAMPM">
                      <option value="AM" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeAMPM().equals("AM"))?"SELECTED":""%>>AM</option>
                      <option value="PM" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeAMPM().equals("PM"))?"SELECTED":""%>>PM</option>
                    </select>
                  </td>
                </tr>
              </table>
            <% } else { %>
              <%=evt.getEventStartTime()%>
            <% } %>
          </td>
        </tr>
        <tr id="finishrowedit">
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Finish Time:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <% if(!modified) { %>
              <table>
                <tr>
                  <td>
                    <select name="fhour">
                      <option value="1" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeHour().equals("1"))?"SELECTED":""%>>1</option>
                      <option value="2" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeHour().equals("2"))?"SELECTED":""%>>2</option>
                      <option value="3" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeHour().equals("3"))?"SELECTED":""%>>3</option>
                      <option value="4" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeHour().equals("4"))?"SELECTED":""%>>4</option>
                      <option value="5" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeHour().equals("5"))?"SELECTED":""%>>5</option>
                      <option value="6" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeHour().equals("6"))?"SELECTED":""%>>6</option>
                      <option value="7" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeHour().equals("7"))?"SELECTED":""%>>7</option>
                      <option value="8" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeHour().equals("8"))?"SELECTED":""%>>8</option>
                      <option value="9" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeHour().equals("9"))?"SELECTED":""%>>9</option>
                      <option value="10" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeHour().equals("10"))?"SELECTED":""%>>10</option>
                      <option value="11" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeHour().equals("11"))?"SELECTED":""%>>11</option>
                      <option value="12" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeHour().equals("12"))?"SELECTED":""%>>12</option>
                    </select>
                  </td>
                  <td>
                    <select name="fminute">
                      <option value="00" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeMinute().equals("00"))?"SELECTED":""%>>00</option>
                      <option value="15" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeMinute().equals("15"))?"SELECTED":""%>>15</option>
                      <option value="30" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeMinute().equals("30"))?"SELECTED":""%>>30</option>
                      <option value="45" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeMinute().equals("45"))?"SELECTED":""%>>45</option>
                    </select>
                  </td>
                  <td>
                    <select name="fAMPM">
                      <option value="AM" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeAMPM().equals("AM"))?"SELECTED":""%>>AM</option>
                      <option value="PM" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeAMPM().equals("PM"))?"SELECTED":""%>>PM</option>
                    </select>
                  </td>
                </tr>
              </table>
            <% } else { %>
              <%=evt.getEventFinishTime()%>
            <% } %>
          </td>
        </tr>
        
        <tr id='evtcatsrow'>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Categories:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
          <%if(!modified) { %>
          	<select id='evtcats' name='evtcats' multiple='multiple' style='height:175px;'>
          		<% for(EventCategory ec : evtcats){ %>
          				<option value='<%= ec.getCategoryId() %>'><%= ec.getCategoryName() %></option>
          		<% } %>
          	</select>
          <%}else{ %>
          	<c:forEach items="${evt.eventCategories}" var="cat" varStatus="status">
          		<i>${cat.categoryName}</i><c:if test="${status.last eq false}"><br /></c:if>
          	</c:forEach>
          <%}%>
          </td>
        </tr>
        
        <tr id='evtreqsrow'>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;*&nbsp;Requirements:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
          <%if(!modified) { %>
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
          <%}else{%>
          	<c:forEach items="${evt.eventRequirements}" var="req" varStatus="status">
          		<i>${req.requirement}</i><c:if test="${not empty req.extrainfo }">&nbsp;[${ req.extrainfo }]</c:if><c:if test="${status.last eq false}"><br /></c:if>
          	</c:forEach>
          <%}%>
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
            <% if(modified)  {%>
            	Funded by Nunatsiavut Government?
            <% } else { %>
            	&nbsp;
            <% } %>
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
          <% if(!modified)  {%>
            <input type="checkbox" name="chk-is-government-funded" <%= evt.isGovernmentFunded() ? "checked=checked" : "" %> /><label for='chk-is-government-funded'>&nbsp;&nbsp;*&nbsp;Is Event Funded by Nunatsiavut Government?</label>
          <% } else { %>
          	<%= evt.isGovernmentFunded() ? "YES" : "NO" %>
          <% } %>
          </td>
        </tr>
      </table>

      <table width="400" cellpadding="1" cellspacing="5" border="0">
        <tr>
          <td width="400" valign="middle" bgcolor="#f4f4f4">
            <table>
              <tr>
                <td align="left" width="<%=width%>">
                  <img id='img-processing' name="processing" src="images/spacer.gif">
                  <font color="#FF0000">
                    <% if(request.getAttribute("msg") != null) { %>
                      <%= request.getAttribute("msg") %>
                    <% } %>
                  </font>
                </td>
                <td align="right">
                  <% if(request.getAttribute("msg") == null || !modified) { %>
                    <button id='btn-confirm' type='button' >Confirm</button>
                    <button id='btn-cancel' type='button' onclick='self.close();'>Cancel</button>
                  <% } else { %>
                    <button id='btn-close' type='button' onclick="self.opener.location.reload(); self.close();">Close</button>
                  <% } %>
                </td>
              </tr>
            </table>
          </td>
        </tr> 
      </table>
    </form>
  </body>
</html>