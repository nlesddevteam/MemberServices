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
                 java.io.*,
                  com.awsd.common.Utils,
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
  
  File agenda_dir = new File(session.getServletContext().getRealPath("/") + "/PDReg/agendas/");
  
  File f_agenda = null;
  
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
  
  f_agenda = (File) request.getAttribute("AGENDA_FILE");
  
%>

<html>
  <head>
  
   	<title>PD Calendar</title>
    
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
    	  
    	  <%
    	  	if(evt.getEventRequirements().size() > 0 && !modified) {
    	  		for(EventRequirement reg : evt.getEventRequirements()) {
    	  %>		
    	  $("input[value='<%= reg.getRequirement() %>']").attr('checked', 'checked'); 
    	  
    	  var opt = $("input[value='<%= reg.getRequirement() %>']");    	  			
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
  	  			
  	  		$( "input[value='<%= cat.getCategoryId() %>']" ).attr('checked', 'checked');  	  			
  	  	<%	}} 	%>
    	  
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
      
      $("#loadingSpinner").css("display","none");
    </script>
     <script>
		var pageWordCountConf = {
	    showParagraphs: true,
	    showWordCount: true,
	    showCharCount: true,
	    countSpacesAsChars: true,
	    countHTML: true,
	    maxWordCount: -1,
	    maxCharCount: 500,
	}
</script>
    
     	<style>
		.tableTitle {font-weight:bold;width:20%;text-transform:uppercase;}
		.tableResult {font-weight:normal;width:80%;}
		.tableTitleL {font-weight:bold;width:20%;text-transform:uppercase;}
		.tableResultL {font-weight:normal;width:30%;background-color:#ffffff;}
		.tableTitleR {font-weight:bold;width:20%;text-transform:uppercase;}
		.tableResultR {font-weight:normal;width:30%;background-color:#ffffff;}
		input {border:1px solid silver;}
	</style>
    
    
  </head>

  <body>
  <div class="container-fluid no-print" data-spy="affix" data-offset-top="0" style="position:fixed;width:100%;height:30px;background-color:#008B8B;color:White;text-align:center;font-weight:bold;padding:5px;">                      
 <span class="pageTitle"><%=evt.getEventName()%></span>
</div>

<div class="registerEventDisplay" style="padding-top:25px;font-size:11px;">
<div style="margin-left:5px;margin-right:5px;">
 <div align="center" class="no-print"><a href="viewDistrictCalendar.html"><img class="topLogoImg" src="includes/img/pdcalheader.png" border=0 style="padding-bottom:10px;"/></a></div>
  
  <%if(!modified){%>    
  Please update your event below.<br/><br/>
  <%} %>
  
  
  <% if(request.getAttribute("msgERR") != null) { %>
         				 <div class="alert alert-danger" style="text-align:center;"><%= request.getAttribute("msgERR") %></div>
       					 <% } %>
       				<% if(request.getAttribute("msgOK") != null) { %>
         				 <div class="alert alert-success" style="text-align:center;"><%= request.getAttribute("msgOK") %>.</div>
       					 <% } %> 
    <div class="alert alert-danger" id="scheduleMessage" style="text-align:center;display:none;"></div>	 
  
  
  
    <form action="modifyEvent.html" method="get" name="modify" onsubmit="return validateForm(this);">
    	<input type="hidden" name="id" value="<%=evt.getEventID()%>">
      	<input type="hidden" name="confirmed" value="true">
            		<div class="formTitle">TYPE:</div>
<div class="formBody">	
   											<%if(!modified){%>              
								              	<select id='evttype' name="evttype" class="form-control">
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
</div>	
         						
<div class="formTitle">TITLE:</div>
<div class="formBody">	
		    							<% if(!modified) { %>
							              <input type="text" name="EventName"  class="form-control" title="Please Enter your Name Here" value="<%=evt.getEventName()%>">
							            <% } else { %>
							              <%=evt.getEventName()%>
							            <% } %>
 </div>
<div class="formTitle">DESCRIPTION:</div>
<div class="formBody">	
		    							<% if(!modified) { %>
		    							<textarea id="EventDesc" name="EventDesc" class="form-control"><%=evt.getEventDescription()%></textarea>
    									
							            <% } else { %>
							              <%=evt.getEventDescription()%>
							            <% } %>
</div>	
                                
                                
                                <% if(!modified) { %>
              					<%if(!isPrincipal && !isVicePrincipal){%>
	              					<input type="hidden" id='hdnEventLocation' name="EventLocation" value="<%=evt.getEventLocation()%>" />
                        
<div class="formTitle"> LOCATION:</div>
<div class="formBody">	
		    								<select id="lstSchoolID" name="EventSchoolID" class="form-control">
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
		    					
                                <div id='divOtherLocation' style='display:none;padding-top: 3px;'>
                                <div class="formTitle">OTHER:</div>
                                	<input type="text" id="txtEventLocation" class="form-control" title="Please Enter the location here" value="<%=evt.getEventLocation()%>" />
	              				</div>
</div>
        						
<div class="formTitle"> REGION:</div>
<div class="formBody">	
    								<select id="lstSchoolZone" name="EventZoneID" class="form-control">
		              					<option value="">--- SELECT REGION ---</option>
						              	<% for(SchoolZoneBean sz : SchoolZoneService.getSchoolZoneBeans()){ %>
						              			<option value="<%= sz.getZoneId() %>" <%=(sz.getZoneId()==evt.getEventSchoolZoneID())?"SELECTED":""%>><%= StringUtils.capitalize(sz.getZoneName()) %> Region</option>
						              	<% } %>
		              				</select>
</div>    							
            				<%}else{%>
            				
            				
<div class="formTitle"> LOCATION:</div>
<div class="formBody">	
				              	<input type="text" name="EventLocation" class="form-control" title="Please Enter the location here" value="<%=school.getSchoolName()%>" readonly>
</div>				              	
<div class="formTitle">  REGION:</div>
<div class="formBody">	
				              	<input type="text" id="txtEventZone" class="form-control" value="<%= StringUtils.capitalize(school.getZone().getZoneName()) %> Region" readonly>				            				              					              
				              <input type='hidden' name='EventSchoolID' value='<%=school.getSchoolID()%>' />
				              <input type='hidden' name='EventZoneID' value='<%=school.getZone().getZoneId()%>' />
</div>				              
			            	<%}%>
                            <% } else { %>
                           
<div class="formTitle"> LOCATION:</div>
<div class="formBody">	
              					<%=evt.getEventLocation() + ((evt.getEventSchoolZone() != null) ? " - " + StringUtils.capitalize(evt.getEventSchoolZone().getZoneName()) + " Region" : "") %>
 </div>           					
            				<% } %>
          					
<div class="formTitle"> HOST:</div>
 <div class="formBody">	         					
            					<% if(!modified && usr.isAdmin() && evt.hasParticipants()) { %>
					        	<select id='ddl-scheduler' name='ddl-scheduler' class="form-control">
						          <% for(Personnel sch : schedulers) { %>
						          		<option value='<%= sch.getPersonnelID() %>' <%= ((evt.getSchedulerID() == sch.getPersonnelID()) ? " selected='selected'" : "") %>><%= sch.getDisplay() %></option>
						          <% } %>
					          	</select>
						          <% } else { %>
						          	<%= evt.getScheduler().getFullName() %>
						          <% } %>
 </div>           					
<div class="formTitle"> START DATE / END DATE:</div>
<div class="formBody">	
<table width="100%" style="font-size:11px;text-align:center;">
	    									<tr>
	    									<th width="50%" style="text-align:center;color:Green;">START DATE</th>
	    									<th width="50%" style="text-align:center;border-left:1px solid black;color:Red;">END DATE</th>
	    									</tr>
											<tr>
											<td>
											<% if(!modified) { %>
    										<input class='form-control' value="<%=(new SimpleDateFormat("dd/MM/yyyy")).format(evt.getEventDate())%>" data-provide="datepicker" id="startDate" name="EventDate" READONLY />
			    							<% } else { %>
			              					<%=evt.getEventDate()%>
			            					<% } %>											
											</td>
											<td>
											<% if(!modified) { %>
			    							<input class='form-control' value="<%=(evt.getEventEndDate() != null)?(new SimpleDateFormat("dd/MM/yyyy")).format(evt.getEventEndDate()):""%>" data-provide="datepicker" id="endDate" name="EventEndDate" READONLY />
			    							<% } else { %>
			              					<%=(evt.getEventEndDate()!=null)?evt.getEventEndDate().toString():"&nbsp;"%>
			            					<% } %>   
    						 				
    						 				</td>
    						 				</tr>
</table>	
						
</div>
                            
                            <div id="closeoutoptionrow" style='display:<%=(evt.isCloseOutDaySession())?"inline":"none"%>'>                        
<div class="formTitle"> SESSION OPTION:</div>
 <div class="formBody">	   								
    								<% if (!modified) { %>
    									<select name="closeoutoption" class="form-control" title="Please Select Closeout Session Option">
							              <option value="-1" <%=(evt.getEventCloseoutOption().equalsIgnoreCase("-1"))?"SELECTED":""%>>Choose Close-out Session Option</option>
							              <option value="A" <%=(evt.getEventCloseoutOption().equalsIgnoreCase("A"))?"SELECTED":""%>>Option A (All Day)</option>
							              <option value="B" <%=(evt.getEventCloseoutOption().equalsIgnoreCase("B"))?"SELECTED":""%>>Option B (AM)</option>
							              <option value="C" <%=(evt.getEventCloseoutOption().equalsIgnoreCase("C"))?"SELECTED":""%>>Option C (PM)</option>
						            	</select> 
					            	<% } else { %>
              						<%= "Option " + evt.getEventCloseoutOption()%>
            						<% } %>
</div>            						
    							</div>
    							
    					
    							
    						<% if(!modified) { %>    
  <div class="formBody">	  													
    							<input type="checkbox" name="evttime" <%=(!evt.getEventStartTime().equals("UNKNOWN"))?"CHECKED":""%> onclick="notime(); toggle('startrowedit');" /> &nbsp; <label for='evttime'>Select Event Start/End Times?</label>
  </div>							
	        			  <% } %>	        	
	     
        
      
       <div id="startrowedit" style="display:none">                        
<div class="formTitle"> START TIME / END TIME</div>
<div class="formBody">		    							
	    							<% if(!modified) { %>
	    									<table width="100%" style="font-size:11px;text-align:center;">
	    									<tr>
	    									<th colspan=3 style="text-align:center;border-bottom:1px solid black;color:Green;">START TIME</th>
	    									<th colspan=3 style="text-align:center;border-left:1px solid black;border-bottom:1px solid black;color:Red;">END TIME</th>
	    									</tr>
	    									<tr>
	    									<th width="16%">HOUR</th><th width="16%">MIN</th><th width="16%">AM/PM</th>
	    									<th width="16%">HOUR</th><th width="16%">MIN</th><th width="16%">AM/PM</th>
	    									</tr>
								              <tr>
								                <td>
								                  <select name="shour" class="form-control">
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
							                    <select name="sminute" class="form-control">
							                      <option value="00" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeMinute().equals("00"))?"SELECTED":""%>>00</option>
							                      <option value="15" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeMinute().equals("15"))?"SELECTED":""%>>15</option>
							                      <option value="30" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeMinute().equals("30"))?"SELECTED":""%>>30</option>
							                      <option value="45" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeMinute().equals("45"))?"SELECTED":""%>>45</option>
							                    </select>
							                  </td>
							                  <td>
							                    <select name="sAMPM" class="form-control">
							                      <option value="AM" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeAMPM().equals("AM"))?"SELECTED":""%>>AM</option>
							                      <option value="PM" <%=(!evt.getEventStartTime().equals("UNKNOWN") && evt.getEventStartTimeAMPM().equals("PM"))?"SELECTED":""%>>PM</option>
							                    </select>
							                  </td>							              
							                  <td>
							                    <select name="fhour" class="form-control">
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
							                    <select name="fminute" class="form-control">
							                      <option value="00" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeMinute().equals("00"))?"SELECTED":""%>>00</option>
							                      <option value="15" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeMinute().equals("15"))?"SELECTED":""%>>15</option>
							                      <option value="30" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeMinute().equals("30"))?"SELECTED":""%>>30</option>
							                      <option value="45" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeMinute().equals("45"))?"SELECTED":""%>>45</option>
							                    </select>
							                  </td>
							                  <td>
							                    <select name="fAMPM" class="form-control">
							                      <option value="AM" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeAMPM().equals("AM"))?"SELECTED":""%>>AM</option>
							                      <option value="PM" <%=(!evt.getEventFinishTime().equals("UNKNOWN") && evt.getEventFinishTimeAMPM().equals("PM"))?"SELECTED":""%>>PM</option>
							                    </select>
							                  </td>
							                </tr>
							              </table>
						            <% } else { %>
						               <%=evt.getEventStartTime()%> to <%=evt.getEventFinishTime()%>
						            <% } %>
</div>						         
						        </div>
       							
       							 <% if(!modified) { %>
         						<div id="maxrowcheck" style="display:none">         
         						 <div class="formBody">	               
    								<input type="checkbox" name="limited" title="Limit number of participants?" onClick="if(limited.checked) max.value=''; toggle('maxrowedit');" <%=(evt.getEventMaximumParticipants() > 0)?"CHECKED":""%>>&nbsp;&nbsp;&nbsp;<b>Limited Number of Participants?</b>
    								<div id="maxrowedit" style="display:none"><input type="text" name="max" title="Please Enter the maximum number of participants" value="<%=evt.getEventMaximumParticipants()%>" class="form-control" style="max-width:200px;"></div>
    								</div>
    							</div>
    							<%} else {%>
    							
<div class="formTitle">PARTICIPANT(S):</div>
<div class="formBody">	 
    						<%=evt.getEventMaximumParticipants()%>
 </div>   						
    						<%} %>

                              <% if(!modified) { %>                            
                      
<div id="adgendarowfileinput"> 
<div class="formTitle">AGENDA:</div>   
 <div class="formBody">	 							 							
    							<%if(f_agenda != null){%>
				            	<span style="color:Green;"> <span class="glyphicon glyphicon-ok"></span> Agenda attached. To change, select new file. </span><a class="btn btn-xs btn-primary" href="/MemberServices/PDReg/agendas/<%=f_agenda.getName()%>" target="_blank" style="float:right;margin-bottom:5px;">VIEW</a>
				          		<%}else{%>
				           		 <span style="color:#FF0000;font-weight:bold;"> <span class="glyphicon glyphicon-remove"></span> Agenda not available at this time.</span>
				          		<%}%>
    							<input type="file" name="agendafile"  class="form-control" title="Please Enter your agenda file." />
</div>
</div>
    							<%}%>
     						
<div class="formTitle">CATEGORY(S):</div>
 <div class="formBody">	 
    						
    							
    							<%if(!modified) { %>
    								<% for(EventCategory ec : evtcats){ %>
				          		          <div class="catList">
					   				 		<label class="checkbox-inline">
					          		 			<input type="checkbox" name="evtcats" value="<%=ec.getCategoryId()%>"><%= ec.getCategoryName() %>
					     					</label>
					     				</div>          		
				          			<%}%>
				          		<%} else { %>
				          		<ul>
				          		<c:forEach items="${evt.eventCategories}" var="cat" varStatus="status">
          						<li>${cat.categoryName}
          						</c:forEach>
				          		</ul>
				          		<%} %>	
</div>        						
 
<div id='evtreqsrow' style='display:none;'>
 <div style="clear:both;"></div>           
 <hr>            
<div class="formTitle">REQUIREMENT(S):</div>
 <div class="formBody">	     							
    							<%if(!modified) { %>    							
	    							<div class="reqList"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="Computer Access">Computer Access</label></div>       
	    							<div class="reqList"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="Computer Bank" extrainfo="computerbank" onclick="toggle('computerbankrow');">Computer Bank</label></div>       
	    							<div class="reqList"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="LCD Viewer">LCD Viewer</label></div>       
	    							<div class="reqList"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="Internet Access">Internet Access</label></div>       
	    							<div class="reqList"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="SmartBoard Access">SmartBoard Access</label></div>       
	    							<div class="reqList"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="Software installation" extrainfo="softwarerequired" onclick="toggle('softwarerequiredrow');">Software installation</label></div>       
	    							<div class="reqList"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="Flip Chart Paper and Markers">Flip Chart Paper and Markers</label></div>       
	    							<div class="reqList"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="Other Requirements" extrainfo="otherreqs" onclick="toggle('otherreqsrow');">Other Requirements</label></div>       
	    						<%}else{%>
	    						<ul>
          						<c:forEach items="${evt.eventRequirements}" var="req" varStatus="status">
          						<li>${req.requirement}<c:if test="${not empty req.extrainfo }">&nbsp;[${ req.extrainfo }]</c:if>
          						</c:forEach>
          						</ul>
          						<%}%>
</div>	    					
</div>
    							
<div id="computerbankrow" style="display:none">    
<div style="clear:both;"></div>                     
<div class="formTitle"># COMPUTERS:</div>
 <div class="formBody">	 
    							<input type="text" class="form-control" placeholder="Enter # Computers/Devices Required" id="computerbank" name="computerbank" title="# Computers Required.">
 </div>
</div>
 <div id="softwarerequiredrow" style="display:none">
 <div style="clear:both;"></div>                         
<div class="formTitle">SOFTWARE:</div>
 <div class="formBody">	 
    							<input type="text" id="softwarerequired" placeholder="Enter software required" name="softwarerequired"  class="form-control" title="Please Enter Software Required.">
</div>   					
</div>
<div id="otherreqsrow" style="display:none">                   
<div style="clear:both;"></div>      
<div class="formTitle">SPECIAL REQUIREMENTS:</div>
 <div class="formBody">	 
    							<textarea id="otherreqs" name="otherreqs"  class="form-control" title="Special Requirements."></textarea>   							 
</div>								
</div>

 <div style="clear:both;"></div>           
 <hr>            
   <div class="formBody">	                            
    							<% if(!modified)  {%>
    							<input type="checkbox" name="chk-is-government-funded" <%= evt.isGovernmentFunded() ? "checked=checked" : "" %> /><label for='chk-is-government-funded'>&nbsp;&nbsp;*&nbsp;Is Event Funded by Nunatsiavut Government?</label>
    							<% } else { %>
					            <b>Is Event Funded by Nunatsiavut Government?</b> <%= evt.isGovernmentFunded() ? "YES" : "NO" %>
					            <% } %>
 </div>   						
       
       
 <br/> 

     				<% if(request.getAttribute("msgERR") != null) { %>
         				 <div class="alert alert-danger" style="text-align:center;"><%= request.getAttribute("msgERR") %>.</div>
       					 <% } %>
       				<% if(request.getAttribute("msgOK") != null) { %>
         				 <div class="alert alert-success" style="text-align:center;"><%= request.getAttribute("msgOK") %>.</div>
       					 <% } %> 
    				<div class="alert alert-danger" id="scheduleMessage" style="text-align:center;display:none;"></div>	 
                 <hr>
                
                
                  <div align="center" class="no-print navBottom">
                  <% if(!modified) { %>           
                   
                   <a class="btn btn-xs btn-success" href="javascript:document.modify.submit();">Confirm/Save Changes</a>
                    <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Cancel</a>
                  <% } else { %>
                     <a class="btn btn-xs btn-danger" href="viewDistrictCalendar.html">Back to Calendar</a>
                  <% } %>
   				</div>
   
   
 
   
    </form>
 
 
 </div></div>
   <script>  
  	CKEDITOR.replace('EventDesc',{wordcount: pageWordCountConf,height:150});   
  	CKEDITOR.replace('otherreqs',{wordcount: pageWordCountConf,height:150});
  </script>         
 <script>
    $('document').ready(function(){
    	$("#startDate").datepicker({
          	changeMonth: true,
          	changeYear: true,
          	dateFormat: "dd/mm/yy"
       });
    	$("#endDate").datepicker({
          	changeMonth: true,
          	changeYear: true,
          	dateFormat: "dd/mm/yy"
       });	
    	
    	
    });
    
    </script>
   
    
  </body>
</html>