<%@ page contentType="text/html; charset=iso-8859-1" language="java" 
         session="true"
         isThreadSafe="false"
         import="java.sql.*,
                 java.util.*,
                 java.text.*,
                 java.io.*,com.awsd.personnel.*,
                 com.awsd.weather.*,com.awsd.security.*,com.awsd.school.*,com.awsd.school.bean.*,com.awsd.school.dao.*,
                 com.nlesd.school.bean.*"%>
<%@ taglib uri='http://java.sun.com/jstl/core_rt' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="WEATHERCENTRAL-GLOBAL-ADMIN" />


<%
  User usr = (User) session.getAttribute("usr");
  School[] schools = null;
  Personnel principal = null;
  int colorcnt = 0;
  ClosureStatus sstat = null;
  HashMap<SchoolZoneBean, HashMap<RegionBean, Vector<SchoolSystem>>> zones= null;
  ClosureStatuses statuses = new ClosureStatuses();
  
  zones = SchoolSystemDB.getRegionalizedSchoolClosureStatuses2();
    
  colorcnt = 0;
  int cnt = 0;
  int cntR = 0;
  Calendar now = Calendar.getInstance();
  
  String dt = (new SimpleDateFormat("dd/MM/yyyy")).format(now.getTime());
%>

<c:set var='zones' value='<%=zones%>' />
<c:set var='statuses' value='<%=statuses%>' />

<html>
  <head>
 
    <title>School Status -  Global Admin</title>
    <!-- HAVE TO LOAD THESE HERE AS THEY WILL NOT WORK USING DECORATOR LOAD for date-->
     <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>   
    <script>
    	$('document').ready(function(){
    		
    		$(".loadPage").show();
    		$(".loadingTable").css("display","none");
    		$("#loadingSpinner").css("display","none");		
    		
    			$('.chk-zone-id').click(function(){
            	var id = $(this).attr('zone-id');
							if($('#zone-status-'+ id).is(':hidden'))
								$('#zone-status-'+ id).show();
							else
								$('#zone-status-'+ id).hide();
        	});
    		
        	$('.chk-region-id').click(function(){
            	var id = $(this).attr('region-id');
							if($('#region-status-'+ id).is(':hidden'))
								$('#region-status-'+ id).show();
							else
								$('#region-status-'+ id).hide();
        	});

        	$('.chk-ss-id').click(function(){
            	var id = $(this).attr('ss-id');
							if($('#ss-status-'+ id).is(':hidden'))
								$('#ss-status-'+ id).show();
							else
								$('#ss-status-'+ id).hide();
        	});

        	$('.chk-school-id').click(function(){
            	var id = $(this).attr('school-id');
							if($('#school-status-'+ id).is(':hidden'))
								$('#school-status-'+ id).show();
							else
								$('#school-status-'+ id).hide();
        	});

        	$('div.schoolsystem').each(function(idx){
						$(this).children('div.school:odd').addClass('alternate');
					});
        	
        	$('.datefield').val('<%= dt %>');        	
        	
        	   $( ".datefield" ).datepicker({
       		      changeMonth: true,//this option for allowing user to select month
       		      changeYear: true, //this option for allowing user to select from year range
       		      dateFormat: "dd/mm/yy",
       		      maxDate: '+2y',
       		      minDate: '-14d'
       		    });  
        	

				$('#btnApply').click(function(){
						$(this).attr('disabled', 'disabled');
						$(this).val('Applying Changes...');
						document.forms[0].submit();
				});
    	});
    
    </script>
  </head>
  
  <body>
  <div class="siteHeaderGreen">  School Status - Global Admin  </div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Administration Data, please wait.<br/>
This will take a few moments!
</div>		

<div style="display:none;border:0px" class="loadPage"> 
  
  <div align="center">       
       <a class="btn btn-sm btn-danger" href="../navigate.jsp">Back to MS</a>
       </div>
  <br/><br/>
    <form name="schoolstatus" method="post" action="updateRegionalizedSchoolClosureStatus.html">



 <!-- REGIONAL ZONE ------------------------------------------------------------------------------------------------------->       
  <div id="accordionRegion">
  
 <c:forEach items="${zones}" var='zentry'>
 <%cntR++;%>
            	<c:set var='regions' value='${ zentry.value }' />          	
            			<div class='zone'>        
            			
            			    
<div class="card">


<c:choose>
<c:when test="${zentry.key.zoneName eq 'avalon' }">
<c:set var="regionColor" value="bgcolor1" />
</c:when>
<c:when test="${zentry.key.zoneName eq 'central' }">
<c:set var="regionColor" value="bgcolor2" />
</c:when>
<c:when test="${zentry.key.zoneName eq 'western' }">
<c:set var="regionColor" value="bgcolor3" />
</c:when>
<c:when test="${zentry.key.zoneName eq 'labrador' }">
<c:set var="regionColor" value="bgcolor4" />
</c:when>
<c:otherwise><c:set var="regionColor" value="bgcolor5" /></c:otherwise>										

</c:choose>

<div class="card-header ${regionColor}">
<div style="font-size:18px;font-weight:bold;text-transform:Capitalize;">

<a class="card-link card<%=cntR%>" data-toggle="collapse" href="#collapseR<%=cntR%>"><span id="icon<%=cntR%>"><i class='fas fa-folder'></i></span> ${zentry.key.zoneName} Region Schools</a>

</div>

<script>
 $('.card<%=cntR%>').on("click", function(e){       
				 if( $("#collapseR<%=cntR%>").hasClass("show")) {
	                	$("#icon<%=cntR%>").html("<i class='fas fa-folder'></i>");
	                } else {                	
	                	 $("#icon<%=cntR%>").html("<i class='fas fa-folder-open'></i>");
	                }                	 
	                	 e.preventDefault();                	 
	                	});                 
</script>
  	



 </div>
 <div id="collapseR<%=cntR%>" class="collapse" data-parent="#accordionRegion">
 <div class="card-body">
			   
		                	<input zone-id='${zentry.key.zoneId}' class='chk-zone-id' type="checkbox" id="zone-id-${zentry.key.zoneId}" name="zone-id-${zentry.key.zoneId}" />
		                	<span style="text-transform:Capitalize;">Set Status for All ${zentry.key.zoneName} Regional Schools</span>
		                	   <br/><br/>
		                	        <div id='zone-status-${zentry.key.zoneId}' style='display:none;'>
		                			<select id='lst-zone-status-${zentry.key.zoneId}' name='lst-zone-status-${zentry.key.zoneId}' class='form-control form-control-sm' required>
					                			<c:forEach items="${statuses}" var='s'>
					                				<option value='${s.closureStatusID}' ${s.closureStatusID eq 9 ? 'SELECTED' : '' }>${s.closureStatusDescription}</option>
					                			</c:forEach>
					                </select>
					                		<br/><b>Date:</b> ( Repeat to Date? <input type="checkbox" id="chk-zone-repeattodate-${zentry.key.zoneId}" style="vertical-align: middle;bottom:2px;" name="chk-zone-repeattodate-${zentry.key.zoneId}" /> )
					                		<input required id='txt-zone-date-${zentry.key.zoneId}' name='txt-zone-date-${zentry.key.zoneId}' type="text" class='datefield form-control form-control-sm'/><br/>
					                		<b>Note:</b> (optional, to be posted to public web site)<br/>
					                		<textarea id='txt-zone-note-${zentry.key.zoneId}' name='txt-zone-note-${zentry.key.zoneId}' class="form-control form-control-sm"></textarea>
					              			<br/><b>Is this weather related?</b> <input type="checkbox" id="chk-zone-weather-related-${zentry.key.zoneId}" name="chk-zone-weather-related-${zentry.key.zoneId}" onclick="this.checked ? $('#zone-weather-related-closure-panel-${zentry.key.zoneId}').show() : $('#zone-weather-related-closure-panel-${zentry.key.zoneId}').hide()" /><br/>
						              		<div id='zone-weather-related-closure-panel-${zentry.key.zoneId}' style='display:none;'>
							                <br/><b>Weather Related Rationale</b> (<span style="color:Red;">internal use only</span>):
							               	<textarea class='txt-status-note form-control  form-control-sm' id="txt-zone-rationale-${zentry.key.zoneId}" name="txt-zone-rationale-${zentry.key.zoneId}" ></textarea>
						                  	</div>
						                  
						                  <br/>&nbsp;	
								</div>
							
								
								

					     
      
					        
								<!-- ZONE REGION ------------------------------------------------------------------------------------->		
			        <div id="accordionZone">
 								<c:forEach items="${regions}" var='entry'>	
 								<%cnt++;%>			              
			                	<div class='region'>
			                	
			                	  <div class="card">           
			                	<div class="card-header">
									<div style="font-size:14px;font-weight:bold;text-transform:Capitalize;">
									<a class="card-link" data-toggle="collapse" href="#collapse<%=cnt%>">${entry.key.name} Zone Schools</a>
									</div>		
									</div>
									<div id="collapse<%=cnt%>" class="collapse" data-parent="#accordionZone">
									<div class="card-body">               		
				                	<input region-id='${entry.key.id}' class='chk-region-id' type="checkbox" id="region-id-${entry.key.id}" name="region-id-${entry.key.id}" />
				                	<span style="text-transform:Capitalize;">Set Status for All ${entry.key.name} Zone Schools</span>
				                	<br/>
				                	<div id='region-status-${entry.key.id}' style='display:none;'>		
				                					<br/><b>Status:</b>		                				
				                					<select required id='lst-region-status-${entry.key.id}' name='lst-region-status-${entry.key.id}' class='form-control form-control-sm'>
							                			<c:forEach items="${statuses}" var='s'>
							                				<option value='${s.closureStatusID}' ${s.closureStatusID eq 9 ? 'SELECTED' : '' }>${s.closureStatusDescription}</option>
							                			</c:forEach>
							                		</select>
							                		<br/><b>Date:</b> ( Repeat to Date? <input type="checkbox" id="chk-region-repeattodate-${entry.key.id}" style="vertical-align: middle;bottom:2px;" name="chk-region-repeattodate-${entry.key.id}" /> )
							                		<input id='txt-region-date-${entry.key.id}' name='txt-region-date-${entry.key.id}' type="text" class='datefield form-control form-control-sm' required /><br/>
							                		<br/><b>Note:</b> (optional, to be posted to public web site)
							                		<textarea id='txt-region-note-${entry.key.id}' name='txt-region-note-${entry.key.id}' class="form-control form-control-sm"></textarea>							                	
							        
							         				<br/><b>Is this weather related?</b> <input type="checkbox" id="chk-region-weather-related-${entry.key.id}" name="chk-region-weather-related-${zentry.key.zoneId}" onclick="this.checked ? $('#region-weather-related-closure-panel-${entry.key.id}').show() : $('#region-weather-related-closure-panel-${entry.key.id}').hide()" /><br/>
								              		<div id='region-weather-related-closure-panel-${entry.key.id}' style='display:none;'>
									                 <br/><b>Weather Related Rationale</b> (<span style="color:Red;">internal use only</span>):
									                  <textarea class='txt-status-note form-control form-control-sm' id="txt-region-rationale-${entry.key.id}" name="txt-region-rationale-${entry.key.id}"></textarea>
								                  </div>		
							          
							          <br/>&nbsp;
							          </div>
				                	
			                	
			                	
			                	
													<!-- SCHOOL SYSTEM -------------------------------------------------------------------------------->
													<table style="width:100%;font-size:11px;" class="table table-sm">
									<thead class="table-dark">
									<tr valign="top">
									<th width="10%">SELECT</th>
									<th width="90%">SCHOOL SYSTEM &amp; SCHOOLS</th>
									</tr>
									</thead>
									<tbody>
													
													
													
													<c:forEach items="${entry.value}" var="sys">

											                		<div class='schoolsystem'>												                		
									
									
									<tr valign="top">
									<td width="10%" class="align-top"><input ss-id='${sys.schoolSystemID}' class='chk-ss-id' type="checkbox" id="ss-id-${sys.schoolSystemID}" name="ss-id-${sys.schoolSystemID}" /></td>
									<td width="90%" class="align-top"><span style="text-transform:Capitalize;font-weight:bold;">Set Status for All ${sys.schoolSystemName}</span><br/>
																     <br/>
																     <div id='ss-status-${sys.schoolSystemID}' style=display:none;'>								                				
								                					<select required id='lst-ss-status-${sys.schoolSystemID}' name='lst-ss-status-${sys.schoolSystemID}' class='form-control form-control-sm'>
											                			<c:forEach items="${statuses}" var='s'>
											                				<option value='${s.closureStatusID}' ${s.closureStatusID eq 9 ? 'SELECTED' : '' }>${s.closureStatusDescription}</option>
											                			</c:forEach>
											                		</select>
											                		<br/>
											                		<b>Date:</b> ( Repeat to Date? <input type="checkbox" id="chk-ss-repeattodate-${sys.schoolSystemID}" style="vertical-align: middle;bottom:2px;" name="chk-ss-repeattodate-${sys.schoolSystemID}" /> )<br/>
											                		<input required id='txt-ss-date-${sys.schoolSystemID}' name='txt-ss-date-${sys.schoolSystemID}' type="text" class='datefield form-control form-control-sm' /><br/>
											                		
											                		
											                		<br/><b>Note:</b> (optional, to be posted to public web site)<br/>
											                		<textarea id='txt-ss-note-${sys.schoolSystemID}' name='txt-ss-note-${sys.schoolSystemID}' class="form-control form-control-sm"></textarea>
											                		
											                		<br/><b>Is this weather related?</b> <input type="checkbox" id="chk-ss-weather-related-${sys.schoolSystemID}" name="chk-ss-weather-related-${sys.schoolSystemID}"	 onclick="this.checked ? $('#ss-weather-related-closure-panel-${sys.schoolSystemID}').show() : $('#ss-weather-related-closure-panel-${sys.schoolSystemID}').hide()" /><br/>
												              		
												              		<div id='ss-weather-related-closure-panel-${sys.schoolSystemID}' style='display:none;'>
													                 <br/><b>Weather Related Rationale</b> <span style="color:Red;">internal use only</span>):
													                  <textarea class='txt-status-note form-control form-control-sm' id="txt-ss-rationale-${sys.schoolSystemID}" name="txt-ss-rationale-${sys.schoolSystemID}"  ></textarea>
												                  </div>
											                		<br/>&nbsp;
											                		</div>
																		               
											                		<br/>
											               		
											                		
																				<!-- SCHOOL ----------------------------------------------------------------------------------------------------------------->
																				<table style="width:100%;font-size:11px;" class="table table-striped table-sm">
																				<thead class="table-dark">
																				<tr valign="top">
																				<th width="10%">SELECT</th>
																				<th width="70%">SCHOOL</th>
																				<th width="20%">STATUS</th>
																				</tr>
																				</thead>
																				<tbody>
																				
																				<c:forEach items="${sys.schoolSystemSchools}" var="sch">
																				                		<div class='school'>
																				                		<tr valign="top">
																					                	<td  class="align-top" width="10%"><input school-id='${sch.schoolID}' class='chk-school-id' type="checkbox" id="school-id-${sch.schoolID}" name="school-id-${sch.schoolID}" /></td>
																					                	<td width="70%"><span style="text-transform:Capitalize;">${sch.schoolName}</span>
																					                	<div id='school-status-${sch.schoolID}' style='display:none;'>																				                	
																					                	<br/>
																					                	<b>Status:</b>
																					                	<select id='lst-school-status-${sch.schoolID}' name='lst-school-status-${sch.schoolID}' required class='form-control form-control-sm'>
																												                			<c:forEach items="${statuses}" var='s'>
																												                				<option value='${s.closureStatusID}' ${sch.schoolClosureStatus.closureStatusID eq s.closureStatusID ? 'SELECTED' : '' }>${s.closureStatusDescription}</option>
																												                			</c:forEach>
																										</select>
																										<br/>																										
																										<b>Date:</b>  ( Repeat to Date? <input type="checkbox" id="chk-school-repeattodate-${sch.schoolID}" style="vertical-align: middle;bottom:2px;" name="chk-school-repeattodate-${sch.schoolID}}" /> )<br/>
																										<input required id='txt-school-date-${sch.schoolID}' name='txt-school-date-${sch.schoolID}' type="text" class='datefield form-control form-control-sm' />
																										<br/>
																					           			<b>Note (optional, to be posted to public website.):</b><br/>
																										<textarea class="form-control form-control-sm" id='txt-school-note-${sch.schoolID}' name='txt-school-note-${sch.schoolID}'>${sch.schoolClosureStatus.schoolClosureNote ne null ? sch.schoolClosureStatus.schoolClosureNote : ''}</textarea>
																										<br/>
																					           			<b>Is this weather related?</b> <input type="checkbox" id="chk-school-weather-related-${sch.schoolID}" name="chk-school-weather-related-${sch.schoolID}" onclick="this.checked ? $('#school-weather-related-closure-panel-${sch.schoolID}').show() : $('#school-weather-related-closure-panel-${sch.schoolID}').hide()" ${sch.schoolClosureStatus.weatherRelated ? ' CHECKED' : ''} /><br/>
																										<div id='school-weather-related-closure-panel-${sch.schoolID}' style="display:${sch.schoolClosureStatus.weatherRelated ? 'inline' : 'none'};">
																										 <br/>
																										 <b>Weather Related Rationale</b> (<span style="color:Red;">internal use only</span>):
																										<textarea class='txt-status-note form-control form-control-sm' id="txt-school-rationale-${sch.schoolID}" name="txt-school-rationale-${sch.schoolID}">${sch.schoolClosureStatus.rationale ne null ? sch.schoolClosureStatus.rationale : ''}</textarea>
																										 </div>
																										 <br/>&nbsp;
																					           			</div>
																					                	
																					                	</td>
																					                	<td width="20%" class="align-top">
																					                	<c:choose>
																					                	<c:when test="${sch.schoolClosureStatus.closureStatusDescription eq 'School open'}">
																					                	<span style="color:Green;">OPEN</span>
																					                	 </c:when>
																					                	 <c:otherwise>
																					                	 <span style="color:Red;">${sch.schoolClosureStatus.closureStatusDescription}</span>
																					                	 </c:otherwise>
																										</c:choose>
																										</td>
																									              
																												        </tr>
																										</div>
																				</c:forEach>								
																</tbody></table>
																
																</td>
																</tr>
																		</div>
																</c:forEach>
																
																</tbody>
																</table>
											</div>	</div>
											</div>	
											</div>	
											<br/>	              
											</c:forEach>
											</div></div></div>
							</div></div>
							<br/>
							</c:forEach>
      </div>
      

<!-- ALL PROVINCIAL SCHOOLS -->
<div class="card">
<div class="card-header bgcolor5">
<div style="font-size:18px;font-weight:bold;text-transform:Capitalize;">Provincial School System</div>
</div>
<div class="card-body">
<input zone-id='99' class='chk-zone-id' type="checkbox" id="zone-id-99" name="zone-id-99" />	 All Provincial Schools
		           							
		           			<div id='zone-status-99' style='padding-left: 25px;display:none;'>
		           			<div class="row container-fluid" style="padding-top:5px;">
		           			<div class="col-lg-6 col-12">
		           			<br/><b>Status:</b>
		           			<select id='lst-zone-status-99' name='lst-zone-status-99' class='form-control form-control-sm'>
			              			<c:forEach items="${statuses}" var='s'>
			              				<option value='${s.closureStatusID}' ${s.closureStatusID eq 9 ? 'SELECTED' : '' }>${s.closureStatusDescription}</option>
			              			</c:forEach>
			              	</select>
			              	</div>
			              	<div class="col-lg-6 col-12">
			              	<br/><b>Date:</b> ( Repeat to this Date? <input required type="checkbox" id="chk-zone-repeattodate-99" style="vertical-align: middle;bottom:2px;" name="chk-zone-repeattodate-99" />) 	<br/>
			              	<input id='txt-zone-date-99' name='txt-zone-date-99' type="text" class='datefield form-control form-control-sm' placeholder="dd/mm/yy" />
			              	</div>
			              	</div>
			              	<div class="row container-fluid" style="padding-top:5px;">
			              	<div class="col-lg-6 col-12">		              		
			              	 <br/><b>Note (optional, to be posted to public website.):</b><br/>
				             <textarea id='txt-zone-note-99' name='txt-zone-note-99' class="form-control form-control-sm"></textarea>
				             </div>
				             <div class="col-lg-6 col-12">
			              	<b>Is this weather related? </b><input type="checkbox" id="chk-zone-weather-related-99" name="chk-zone-weather-related-99" onclick="this.checked ? $('#zone-weather-related-closure-panel-99').show() : $('#zone-weather-related-closure-panel-99').hide()" /><br/>
			              	<div id='zone-weather-related-closure-panel-99' style='display:none;'>
				             <b>Weather Related Rationale (<span style="color:red;">internal use only</span>):</b><br/>
				            <textarea class='txt-status-note form-control form-control-sm' id="txt-zone-rationale-99" name="txt-zone-rationale-99"></textarea>
			                </div>		</div></div>
			               </div>
</div>
</div>	       

<br/><br/>
      
       <div align="center">
       <input id='btnApply' class="btn btn-sm btn-primary" type='button' value='Apply Changes' /> &nbsp;
       <a class="btn btn-sm btn-danger" href="../navigate.jsp">Back to MS</a>
       </div>
       
  	</form>    
  	</div>
  	
  	
  	
  	
  	
  	
  </body>
</html>
