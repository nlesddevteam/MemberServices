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
         
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="CALENDAR-SCHEDULE" />

<%
  User usr = null;

SchoolFamilies families = null;
SchoolFamily family = null;
Iterator fam_iter = null;

families = new SchoolFamilies();
fam_iter = families.iterator();

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


<html>
  <head>
  	<title>PD Calendar</title>
    
    
    
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
    	  
      });
      
      //$('#startDate').datepicker({ dateFormat: "dd/mm/yy"});
      
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
	</style>
  
  </head>

  <body>
  <div class="container-fluid no-print" data-spy="affix" data-offset-top="0" style="position:fixed;width:100%;height:30px;background-color:#008B8B;color:White;text-align:center;font-weight:bold;padding:5px;">                      
  SCHEDULE NEW EVENT 
</div>
<div class="registerEventDisplay" style="padding-top:25px;font-size:11px;">
<div style="margin-left:5px;margin-right:5px;">
 <div align="center" class="no-print"><a href="viewDistrictCalendar.html"><img class="topLogoImg" src="includes/img/pdcalheader.png" border=0 style="padding-bottom:10px;"/></a></div>
  
  
  Please fill out the form below to schedule your event. Include as many details as you can.<br/><br/>
 
 
                       <% if(request.getAttribute("msgERR") != null) { %>
         				 <div class="alert alert-danger" style="text-align:center;"><%= request.getAttribute("msgERR") %></div>
       					 <% } %>
       				<% if(request.getAttribute("msgOK") != null) { %>
         				 <div class="alert alert-success" style="text-align:center;"><%= request.getAttribute("msgOK") %><br/>Enter another event, or <a href="viewDistrictCalendar.html">Return to Calendar</a>.</div>
       					 <% } %> 
    <div class="alert alert-danger" id="scheduleMessage" style="text-align:center;display:none;"></div>	 
    
     <form action="scheduleEvent.html" method="post" name="schedule" ENCTYPE="multipart/form-data">   			 	 
      			 	       		
<div class="formTitle">TYPE:</div>
  <div class="formBody">	    							
    								<select id='evttype' name="evttype" class="form-control">
						              	<option value="-1">Choose Event Type</option>
						            	<% if(private_only) { %>
						              		<option value="5">PRIVATE CALENDAR ENTRY</option>
						            	<%} else if(isPrincipal || isVicePrincipal){%>
						              		<option value="41">SCHOOL PD REQUEST</option>
						            	<%} else {
						                	for(EventType type : new EventTypes()){
						                  	if((type.getEventTypeID() != 1)&&
						                  		(type.getEventTypeID() != 2)&&
						                  		(type.getEventTypeID() != 3)&&
						                  		(type.getEventTypeID() != 23)&&
						                  		(type.getEventTypeID() != 24)&&
						                  		(type.getEventTypeID() != 5)&&
						                  		(type.getEventTypeID() != 62)&&
						                  		(type.getEventTypeID() != 41)&&
						                  		(type.getEventTypeID() != 61)){ %>  
						              	<option value="<%=type.getEventTypeID()%>"><%=type.getEventTypeName()%></option>
						              	<%}}}%>
						            </select>
</div>
<div class="formTitle">TITLE:</div>
 <div class="formBody">	     	
    	<input type="text" id="EventName" name="EventName" class="form-control" title="Please Enter Event Title Here">
 </div>   	
<div class="formTitle">DESCRIPTION:</div>
<div class="formBody">	  
    							<textarea id="EventDesc" name="EventDesc"  class="form-control"></textarea>
 </div>   							
    							
    				 <%if(!isPrincipal && !isVicePrincipal){%>
            					<input type="hidden" id='hdnEventLocation' name="EventLocation" value="" />			
    							
<div class="formTitle">LOCATION:</div>
<div class="formBody">	  
    								<select id="lstSchoolID" name="EventSchoolID" class="form-control">
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
<div class="formTitle">OTHER:</div>
<div class="formBody">	  
	    							<input type="text" id="txtEventLocation" class="form-control" placeholder="Please enter the name of the location Here." title="Please Enter the location here" />
</div>   							
</div>
    							
    							
<div class="formTitle">FAMILY OF SCHOOLS:</div>
<div class="formBody">
If this event is for a particular Family of Schools, select the Family from the dropdown below. If for all the province, select Provincial.<br/>  
    								<select id="lstSchoolZone" name="EventZoneID" class="form-control">
					              		<option value="">--- SELECT FAMILY ---</option>
					              	<% while(fam_iter.hasNext()) {
				                          family = (SchoolFamily) fam_iter.next();                        
				                      %>
					       				<option value="<%=family.getSchoolFamilyID()%>"><%=family.getSchoolFamilyName()%></option>
					       			<%}%> 
					       			<% for(SchoolZoneBean sz : SchoolZoneService.getSchoolZoneBeans()){ %>				       			
					       			<%if (sz.getZoneId() == 5) { %>
					              			<option value="<%= sz.getZoneId() %>">PROVINCIAL</option>
					              			<%} %>
					              	<% } %>
					              	
					              	</select>
 </div>   							
    					<%}else{%>
    							
<div class="formTitle">LOCATION:</div>
<div class="formBody">	  
    									<input type="text" name="EventLocation" class="form-control" title="Please Enter the location here" value="<%=school.getSchoolName()%>" readonly>
						              	<input type='hidden' name='EventSchoolID' value='<%=school.getSchoolID()%>' />
						              	<input type='hidden' name='EventZoneID' value='<%=school.getSchoolFamily().getSchoolFamilyID() %>' />
 </div>   							
    							<%}%>
    							
<div class="formTitle">START DATE/END DATE:</div>
<div class="formBody">	  
<table width="100%" style="font-size:11px;text-align:center;">
	    									<tr>
	    									<th width="50%" style="text-align:center;color:Green;">START DATE</th>
	    									<th width="50%" style="text-align:center;border-left:1px solid black;color:Red;">END DATE</th>
	    									</tr>
											<tr>
											<td>
    						<input class='form-control' placeholder="Enter Start Date" data-provide="datepicker" id="startDate" name="EventDate" READONLY />
											</td>
											<td>
    						<input class='form-control' placeholder="Enter End Date" data-provide="datepicker" id="endDate" name="EventEndDate" READONLY />
    						 				</td>
    						 				</tr>
    						 				</table>			
</div>    							
    							<div id="closeoutoptionrow" style="display:none">                        
<div class="formTitle">SESSION OPTION:</div>
 <div class="formBody">	     								
    									<select name="closeoutoption" class="form-control" title="Please Select Closeout Session Option">
							              <option value="-1">Choose Close-out Session Option</option>
							              <option value="A">Option A (All Day)</option>
							              <option value="B">Option B (AM)</option>
							              <option value="C">Option C (PM)</option>
						            	</select> 
  </div>          						
    							</div>
    							
    							<div id="maxrowcheck" style="display:none">       
   <div class="formBody">	   							                 
    								<input type="checkbox" name="limited" title="Limit number of participants?" onClick="if(limited.checked) max.value=''; toggle('maxrowedit');">&nbsp;&nbsp;&nbsp;<b>Limited Number of Participants?</b>
    								<div id="maxrowedit" style="display:none">
    								<input type="text" name="max" title="Please Enter the maximum number of participants" placeholder="Enter Max #" class="form-control" style="max-width:200px;">
    								</div>    								
    							</div>
</div>    							
 <div class="formBody">	    							
    							<input type="checkbox" name="evttime" onclick="notime(); toggle('startrowedit');" /> &nbsp; <label for='evttime'>Select Event Start/End Times?</label>
 </div>   							
    							
<div id="startrowedit" style="display:none">                     
	    						
<div class="formTitle">START TIME/END TIME:</div>
<div class="formBody">	    						
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
								                <td width="16%">
								                  <select name="shour" class="form-control">
								                  <option value="-1" selected>- HR -</option>
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
								                <td width="16%">
								                  <select name="sminute" class="form-control">
								                  <option value="-1" selected>- MIN -</option>
								                    <option value="00">00</option>
								                    <option value="15">15</option>
								                    <option value="30">30</option>
								                    <option value="45">45</option>
								                  </select>
								                </td>
								                <td width="16%">
								                  <select name="sAMPM" class="form-control">
								                  <option value="-1" selected>- AM/PM -</option>
								                    <option value="AM">AM</option>
								                    <option value="PM">PM</option>
								                  </select>
								                </td>
								             
								                <td width="16%">
								                  <select name="fhour" class="form-control">
								                  <option value="-1" selected>- HR -</option>
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
								                <td width="16%">
								                  <select name="fminute" class="form-control">
								                  <option value="-1" selected>- MIN -</option>
								                    <option value="00">00</option>
								                    <option value="15">15</option>
								                    <option value="30">30</option>
								                    <option value="45">45</option>
								                  </select>
								                </td>
								                <td width="16%">
								                  <select name="fAMPM" class="form-control">
								                  <option value="-1" selected>- AM/PM -</option>
								                    <option value="AM">AM</option>
								                    <option value="PM">PM</option>
								                  </select>
								                </td>
								              </tr>
								            </table>
</div>  							
</div>

<div id="adgendarowfileinput">                        
	<div class="formTitle">AGENDA: (PDF or DOC Only)</div>
	<div class="formBody">	
	<input type="file" name="agendafile"  class="form-control" title="Please Enter your agenda file." accept=".doc,.docx,.pdf"/>
	</div>   								
</div>     
             
<div class="formTitle">CATEGORIES:</div>
<div class="formBody">	
    								<% for(EventCategory ec : evtcats){ %>
				          		          <div class="catList">
					   				 		<label class="checkbox-inline">
					          		 			<input type="checkbox" name="evtcats" value="<%=ec.getCategoryId()%>"><%= ec.getCategoryName() %> 
					     					</label>
					     				</div>          		
				          			<%}%>
</div>          						

<div id='evtreqsrow' style='display:none;'>
 <div style="clear:both;"></div>           
 <hr>                               
<div class="formTitle">REQUIREMENT(S): </div>
<div class="formBody">	
	    							<div style="float:left;min-width:200px;width:25%;color:DimGrey;font-size:11px;"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="Computer Access">Computer Access</label></div>       
	    							<div style="float:left;min-width:200px;width:25%;color:DimGrey;font-size:11px;"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="Computer Bank" onclick="toggle('computerbankrow');">Computer Bank</label></div>       
	    							<div style="float:left;min-width:200px;width:25%;color:DimGrey;font-size:11px;"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="LCD Viewer">LCD Viewer</label></div>       
	    							<div style="float:left;min-width:200px;width:25%;color:DimGrey;font-size:11px;"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="Internet Access">Internet Access</label></div>       
	    							<div style="float:left;min-width:200px;width:25%;color:DimGrey;font-size:11px;"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="SmartBoard Access">SmartBoard Access</label></div>       
	    							<div style="float:left;min-width:200px;width:25%;color:DimGrey;font-size:11px;"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="Software installation" onclick="toggle('softwarerequiredrow');">Software installation</label></div>       
	    							<div style="float:left;min-width:200px;width:25%;color:DimGrey;font-size:11px;"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="Flip Chart Paper and Markers">Flip Chart Paper and Markers</label></div>       
	    							<div style="float:left;min-width:200px;width:25%;color:DimGrey;font-size:11px;"><label class="checkbox-inline"><input type="checkbox" name="evtreqs" value="Other Requirements" onclick="toggle('otherreqsrow');">Other Requirements</label></div>       
</div>	    						
</div>
<br/>
<div id="computerbankrow" style="display:none">       
<div style="clear:both;"></div>                   
<div class="formTitle"># COMPUTERS/DEVICES: </div>
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
    <div class="formBody" style="padding-bottom:50px;">								
    							<input type="checkbox" name="chk-is-government-funded" /><label for='chk-is-government-funded'>&nbsp;&nbsp;*&nbsp;Is Event Funded by Nunatsiavut Government?</label>
       </div>  
    <br/>&nbsp;<br/>    
 <hr>                 
               <div align="center" class="no-print navBottom">
                  <a href="#" class="btn btn-xs btn-primary" onclick="if(validateEvent(document.schedule)==true)onClick(document.schedule);">Schedule This Event</a>
                   <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Cancel</a>
                  <a class="btn btn-xs btn-danger" href="viewDistrictCalendar.html" title="Cancel">Back to Calendar</a>
             </div>     
    
    
    
  
    
    
      
    </form>
    
    </div>
    </div>
     <script>  
  	CKEDITOR.replace('EventDesc',{wordcount: pageWordCountConf,height:150});   
  	CKEDITOR.replace('otherreqs',{wordcount: pageWordCountConf,height:150});
  </script>                  
    <script language="JavaScript">     
      notime();
      document.forms['schedule'].elements['closeoutoption'].selectedIndex=0;
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