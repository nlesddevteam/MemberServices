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
  
  /*
  UserRoles roles = usr.getUserRoles();
  if(roles.containsKey("SENIOR EDUCATION OFFICIER")
      || roles.containsKey("REGIONAL EDUCATION OFFICIER")
      || roles.containsKey("REGIONAL OPERATIONS MANAGER"))
  {
    switch(usr.getPersonnel().getSchool().getSchoolID())
    {
      case 277: //DISTRICT OFFICE
        schools = SchoolDB.getSchools(RegionManager.getRegionBean(1));
        break;
      case 278: //AVALON WEST REGIONAL OFFICE
        schools = SchoolDB.getSchools(RegionManager.getRegionBean(2));
        break;
      case 279: //VISTA REGIONAL OFFICE
        schools = SchoolDB.getSchools(RegionManager.getRegionBean(3));
        break;
      case 280: //BURIN REGIONAL OFFICE
        schools = SchoolDB.getSchools(RegionManager.getRegionBean(4));
        break;
      default:
        schools = SchoolDB.getSchools(RegionManager.getRegionBean(usr.getPersonnel().getSchool()));
    }
  }
  else
    schools = (School[]) SchoolDB.getSchools().toArray(new School[0]);
  */
  zones = SchoolSystemDB.getRegionalizedSchoolClosureStatuses2();
    
  colorcnt = 0;
  
  Calendar now = Calendar.getInstance();
  
  String dt = (new SimpleDateFormat("dd/MM/yyyy")).format(now.getTime());
%>

<c:set var='zones' value='<%=zones%>' />
<c:set var='statuses' value='<%=statuses%>' />

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>School Status -  Global Admin</title>
    <style type="text/css">@import "/MemberServices/css/memberservices-new.css";</style>
    <style type="text/css">@import "css/weathercentral.css";</style>
    <style type="text/css">@import "<c:url value='/tsdoc/includes/css/south-street/jquery-ui-1.8.4.custom.css'/>";</style>
    <style type="text/css">
    	.weatherCentralRegion { width: 350px; }
    </style>
    <script type="text/javascript" src="<c:url value='js/jquery-1.4.2.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value='js/jquery-ui-1.8.4.custom.min.js'/>"></script>
    <script>
    	$('document').ready(function(){
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
        	
        	$('.datefield')
        	.val('<%= dt %>')
        	.datepicker({
						showButtonPanel: true, 
						buttonImage: 'images/cal_popup_01.gif',
						showOn: 'both',
						showAnim: 'drop',
						dateFormat: 'dd/mm/yy'
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
    <form name="schoolstatus" method="post" action="updateRegionalizedSchoolClosureStatus.html">
    <table width="100%" cellpadding="0" cellspacing="0" border="0" >	
      <tr>
        <td width="100%" height="26" align="left" valign="middle" style="background-image: url('/MemberServices/MemberAdmin/images/container_title_bg.jpg');">
          <table width="100%" cellpadding="0" cellspacing="0" border="0">
            <tr>
              <td width="100%" align="left" valign="middle">
                <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="4" height="1"><span class="containerTitleWhite">Status Central</span><BR>
              </td>
              <td width="50" align="right" valign="middle">
                <img src="/MemberServices/MemberAdmin/images/minimize_icon.gif" onClick="toggle('bodyContainer');"><img src="/MemberServices/MemberAdmin/images/spacer.gif" width="5" height="1"><img src="/MemberServices/MemberAdmin/images/close_icon.gif" onClick="document.location='../home.jsp';" alt="Close" style="cursor: hand;"><img src="/MemberServices/MemberAdmin/images/spacer.gif" width="5" height="1"><BR>
              </td>
            </tr>											
          </table>										
        </td>
      </tr>		
      <tr id="bodyContainer">
        <td width="100%" align="center" valign="top">
          <table align="center" width="50%" cellpadding="5" cellspacing="0" border="0" style="padding:5px;">
            <tr>
              <td>
                <span class="boldBlack11pxTitle">School Status - Global Admin</span><BR>
                <hr noshade size="1" width="100%">
              </td>
            </tr>
            <tr>
              <td>
		            <div class='district'>
		            	<table cellpadding='0' cellspacing='0' border='0'>
		           			<tr>
		           				<td valign='top'>
		           					<table cellpadding='0' cellspacing='0' border='0'>
		           						<tr>
		           							<td valign='middle'>
		           								<input zone-id='99' class='chk-zone-id' type="checkbox" id="zone-id-99" name="zone-id-99" />
		           							</td>
		           							<td class='weatherCentralRegion' align='left' valign='middle' style='text-transform:uppercase;'>
		           								Newfoundland &amp; Labrador English School District
		           							</td>
		           						</tr>
		           					</table>
		           				</td>
		           			</tr>
		           			<tr id='zone-status-99' style='padding-left: 25px;display:none;'>
		           				<td>
		           					
		           					<select id='lst-zone-status-99' name='lst-zone-status-99' class='requiredinput'>
			              			<c:forEach items="${statuses}" var='s'>
			              				<option value='${s.closureStatusID}' ${s.closureStatusID eq 9 ? 'SELECTED' : '' }>${s.closureStatusDescription}</option>
			              			</c:forEach>
			              		</select>
			              		<input id='txt-zone-date-99' name='txt-zone-date-99' type="text" class='datefield requiredinput' style='height:20px;' />
			              		<label class="boldBlack10pxTitle" for="chk-zone-repeattodate-99">Repeat to Date?</label>
		           					<input type="checkbox" id="chk-zone-repeattodate-99" name="chk-zone-repeattodate-99" />
			              		<label class="boldBlack10pxTitle" for="chk-zone-weather-related-99">Is this weather related?</label>
		           					<input type="checkbox" id="chk-zone-weather-related-99" name="chk-zone-weather-related-99"
		                  		onclick="this.checked ? $('#zone-weather-related-closure-panel-99').show() : $('#zone-weather-related-closure-panel-99').hide()" />
			              		<br/>
			              		<div id='zone-weather-related-closure-panel-99' style='display:none;'>
				                  <label class="boldBlack10pxTitle" for="txt-zone-rationale-99">Weather Related Rationale (internal use only):</label><br />
				                  <textarea class='txt-status-note' id="txt-zone-rationale-99" name="txt-zone-rationale-99" rows="3" cols="50" ></textarea>
			                  </div>
			                  <div>
				              		<label class="boldBlack10pxTitle" for="txt-zone-note-99">Note(optional, to be posted to public website.):</label><br/>
				              		<textarea id='txt-zone-note-99' name='txt-zone-note-99'></textarea>
			              		</div>
		              		</td>
		             		</tr>
		           		</table>
		            </div>
            	</td>
            </tr>
            <c:forEach items="${zones}" var='zentry'>
            	<c:set var='regions' value='${ zentry.value }' />
            	<tr>
            		<td width="100%" valign="middle">
            			<!-- ZONE -->
            			<div class='zone'>
            				<c:if test='${ not empty zentry.key }'>
	            				<table cellpadding='0' cellspacing='0' border='0'>
		                			<tr>
		                				<td valign='top'>
		                					<table cellpadding='0' cellspacing='0' border='0'>
		                						<tr>
		                							<td valign='middle'>
		                								<input zone-id='${zentry.key.zoneId}' class='chk-zone-id' type="checkbox" id="zone-id-${zentry.key.zoneId}" name="zone-id-${zentry.key.zoneId}" />
		                							</td>
		                							<td class='weatherCentralRegion' valign='middle' style='text-transform:uppercase;'>
		                								<c:if test='${ not empty zentry.key }'>
		                									${zentry.key.zoneName} Region
		                								</c:if>
		                							</td>
		                						</tr>
		                					</table>
		                				</td>
		                			</tr>
		                			<tr id='zone-status-${zentry.key.zoneId}' style='padding-left: 25px;display:none;'>
		                				<td>
		                					<select id='lst-zone-status-${zentry.key.zoneId}' name='lst-zone-status-${zentry.key.zoneId}' class='requiredinput'>
					                			<c:forEach items="${statuses}" var='s'>
					                				<option value='${s.closureStatusID}' ${s.closureStatusID eq 9 ? 'SELECTED' : '' }>${s.closureStatusDescription}</option>
					                			</c:forEach>
					                		</select>
					                		<input id='txt-zone-date-${zentry.key.zoneId}' name='txt-zone-date-${zentry.key.zoneId}' type="text" class='datefield requiredinput' style='height:20px;' />
					                		<label class="boldBlack10pxTitle" for="chk-zone-repeattodate-${zentry.key.zoneId}">Repeat to Date?</label>
		           								<input type="checkbox" id="chk-zone-repeattodate-${zentry.key.zoneId}" name="chk-zone-repeattodate-${zentry.key.zoneId}" />
					                		<label class="boldBlack10pxTitle" for="chk-zone-weather-related-${zentry.key.zoneId}">Is this weather related?</label>
					           					<input type="checkbox" id="chk-zone-weather-related-${zentry.key.zoneId}" name="chk-zone-weather-related-${zentry.key.zoneId}"
					                  		onclick="this.checked ? $('#zone-weather-related-closure-panel-${zentry.key.zoneId}').show() : $('#zone-weather-related-closure-panel-${zentry.key.zoneId}').hide()" />
						              		<br/>
						              		<div id='zone-weather-related-closure-panel-${zentry.key.zoneId}' style='display:none;'>
							                  <label class="boldBlack10pxTitle" for="txt-zone-rationale-${zentry.key.zoneId}">Weather Related Rationale (internal use only):</label><br />
							                  <textarea class='txt-status-note' id="txt-zone-rationale-${zentry.key.zoneId}" name="txt-zone-rationale-${zentry.key.zoneId}" rows="3" cols="50" ></textarea>
						                  </div>
					                		<div>
				              					<label class="boldBlack10pxTitle" for="txt-zone-note-${zentry.key.zoneId}">Note(optional, to be posted to public website.):</label><br/>
					                			<textarea id='txt-zone-note-${zentry.key.zoneId}' name='txt-zone-note-${zentry.key.zoneId}'></textarea>
					                		</div>
					                	</td>
					               	</tr>
		                		</table>
	                		</c:if>
					            <c:forEach items="${regions}" var='entry'>
				              	<!-- REGION -->
			                	<div class='region'>
			                		<c:if test='${ not empty entry.key }'>
				                		<table cellpadding='0' cellspacing='0' border='0'>
				                			<tr>
				                				<td valign='top'>
				                					<table cellpadding='0' cellspacing='0' border='0'>
				                						<tr>
				                							<td valign='middle'>
				                								<input region-id='${entry.key.id}' class='chk-region-id' type="checkbox" id="region-id-${entry.key.id}" name="region-id-${entry.key.id}" />
				                							</td>
				                							<td class='weatherCentralRegion' valign='middle' style='text-transform:uppercase;'>
				                								${entry.key.name} Zone
				                							</td>
				                						</tr>
				                					</table>
				                				</td>
				                			</tr>
				                			<tr id='region-status-${entry.key.id}' style='padding-left: 25px;display:none;'>
				                				<td>
				                					<select id='lst-region-status-${entry.key.id}' name='lst-region-status-${entry.key.id}' class='requiredinput'>
							                			<c:forEach items="${statuses}" var='s'>
							                				<option value='${s.closureStatusID}' ${s.closureStatusID eq 9 ? 'SELECTED' : '' }>${s.closureStatusDescription}</option>
							                			</c:forEach>
							                		</select>
							                		<input id='txt-region-date-${entry.key.id}' name='txt-region-date-${entry.key.id}' type="text" class='datefield requiredinput' style='height:20px;' />
							                		<label class="boldBlack10pxTitle" for="chk-region-repeattodate-${entry.key.id}">Repeat to Date?</label>
		           										<input type="checkbox" id="chk-region-repeattodate-${entry.key.id}" name="chk-region-repeattodate-${entry.key.id}" />
							                		<label class="boldBlack10pxTitle" for="chk-region-weather-related-${entry.key.id}">Is this weather related?</label>
							           					<input type="checkbox" id="chk-region-weather-related-${entry.key.id}" name="chk-region-weather-related-${zentry.key.zoneId}"
							                  		onclick="this.checked ? $('#region-weather-related-closure-panel-${entry.key.id}').show() : $('#region-weather-related-closure-panel-${entry.key.id}').hide()" />
								              		<br/>
								              		<div id='region-weather-related-closure-panel-${entry.key.id}' style='display:none;'>
									                  <label class="boldBlack10pxTitle" for="txt-region-rationale-${entry.key.id}">Weather Related Rationale (internal use only):</label><br />
									                  <textarea class='txt-status-note' id="txt-region-rationale-${entry.key.id}" name="txt-region-rationale-${entry.key.id}" rows="3" cols="50" ></textarea>
								                  </div>
							                		<div>
				              							<label class="boldBlack10pxTitle" for="txt-region-note-${entry.key.id}">Note(optional, to be posted to public website.):</label><br/>
							                			<textarea id='txt-region-note-${entry.key.id}' name='txt-region-note-${entry.key.id}'></textarea>
							                		</div>
							                	</td>
							               	</tr>
				                		</table>
			                		</c:if>
			                	
			                	<c:forEach items="${entry.value}" var="sys">
			                		<!-- SCHOOL SYSTEM -->
			                		<div class='schoolsystem'>
				                		<table cellpadding='0' cellspacing='0' border='0'>
							                <tr>
							                	<td>		
							                		<div>
							                			<table cellpadding='0' cellspacing='0' border='0'>
								                			<tr>
								                				<td valign='top'>
								                					<table cellpadding='0' cellspacing='0' border='0'>
								                						<tr>
								                							<td valign='middle'>
								                								<input ss-id='${sys.schoolSystemID}' class='chk-ss-id' type="checkbox" id="ss-id-${sys.schoolSystemID}" name="ss-id-${sys.schoolSystemID}" />
								                							</td>
								                							<td class='weatherCentralSchoolSystem' valign='middle'>
								                								${sys.schoolSystemName}
								                							</td>
								                						</tr>
								                					</table>
								                				</td>
								                			</tr>
								                			<tr id='ss-status-${sys.schoolSystemID}' style='padding-left: 25px;display:none;'>
								                				<td>
								                					<select id='lst-ss-status-${sys.schoolSystemID}' name='lst-ss-status-${sys.schoolSystemID}' class='requiredinput'>
											                			<c:forEach items="${statuses}" var='s'>
											                				<option value='${s.closureStatusID}' ${s.closureStatusID eq 9 ? 'SELECTED' : '' }>${s.closureStatusDescription}</option>
											                			</c:forEach>
											                		</select>
											                		<input id='txt-ss-date-${sys.schoolSystemID}' name='txt-ss-date-${sys.schoolSystemID}' type="text" class='datefield requiredinput' style='height:20px;' />
											                		<label class="boldBlack10pxTitle" for="chk-ss-repeattodate-${sys.schoolSystemID}">Repeat to Date?</label>
		           														<input type="checkbox" id="chk-ss-repeattodate-${sys.schoolSystemID}" name="chk-ss-repeattodate-${sys.schoolSystemID}" />
											                		<label class="boldBlack10pxTitle" for="chk-ss-weather-related-${sys.schoolSystemID}">Is this weather related?</label>
											           					<input type="checkbox" id="chk-ss-weather-related-${sys.schoolSystemID}" name="chk-ss-weather-related-${sys.schoolSystemID}"
											                  		onclick="this.checked ? $('#ss-weather-related-closure-panel-${sys.schoolSystemID}').show() : $('#ss-weather-related-closure-panel-${sys.schoolSystemID}').hide()" />
												              		<br/>
												              		<div id='ss-weather-related-closure-panel-${sys.schoolSystemID}' style='display:none;'>
													                  <label class="boldBlack10pxTitle" for="txt-ss-rationale-${sys.schoolSystemID}">Weather Related Rationale (internal use only):</label><br />
													                  <textarea class='txt-status-note' id="txt-ss-rationale-${sys.schoolSystemID}" name="txt-ss-rationale-${sys.schoolSystemID}" rows="3" cols="50" ></textarea>
												                  </div>
											                		<div>
								              							<label class="boldBlack10pxTitle" for="txt-ss-note-${sys.schoolSystemID}">Note(optional, to be posted to public website.):</label><br/>
											                			<textarea id='txt-ss-note-${sys.schoolSystemID}' name='txt-ss-note-${sys.schoolSystemID}'></textarea>
											                		</div>
										                		</td>
										                	</tr>
										                </table>
							                		</div>
				                				</td>
				                			</tr>
				                		</table>
			                		
			                		<c:forEach items="${sys.schoolSystemSchools}" var="sch">
			                		
			                			<!-- SCHOOL -->
				                		<div class='school'>
					                		<table cellpadding='0' cellspacing='0' border='0'>
								                <tr>
								                	<td>		
								                		<div>
								                			<table cellpadding='0' cellspacing='0' border='0'>
									                			<tr>
									                				<td valign='top'>
									                					<table cellpadding='0' cellspacing='0' border='0'>
									                						<tr>
									                							<td valign='top'>
									                								<input school-id='${sch.schoolID}' class='chk-school-id' type="checkbox" id="school-id-${sch.schoolID}" name="school-id-${sch.schoolID}" />
									                							</td>
									                							<td valign='middle'>
									                								<table cellspacing='2' cellpadding='0' border='0'>
									                									<tr>
									                										<td class='weatherCentralSchool' valign='top'>${sch.schoolName}</td>
									                										<td style='padding-left: 10px;' class='<%=SchoolClosureStatusWorker.cssClass(((School)pageContext.getAttribute("sch", PageContext.PAGE_SCOPE)).getSchoolClosureStatus().getClosureStatusID())%>'>
									                											${sch.schoolClosureStatus.closureStatusDescription}
												                								<c:if test="${sch.schoolClosureStatus.schoolClosureNote ne null}">
												                									<br/><span class='normalblack10pxText'>${sch.schoolClosureStatus.schoolClosureNote}</span>
												                								</c:if>
									                										</td>
									                									</tr>
									                								</table>
									                								 
									                							</td>
									                						</tr>
									                					</table>
									                				</td>
									                			</tr>
									                			<tr id='school-status-${sch.schoolID}' style='display:none;'>
									                				<td style='padding:5px;padding-left: 25px;'>
									                					<select id='lst-school-status-${sch.schoolID}' name='lst-school-status-${sch.schoolID}' class='requiredinput'>
												                			<c:forEach items="${statuses}" var='s'>
												                				<option value='${s.closureStatusID}' ${sch.schoolClosureStatus.closureStatusID eq s.closureStatusID ? 'SELECTED' : '' }>${s.closureStatusDescription}</option>
												                			</c:forEach>
												                		</select>
												                		<input id='txt-school-date-${sch.schoolID}' name='txt-school-date-${sch.schoolID}' type="text" class='datefield requiredinput' style='height:20px;' />
												                		<label class="boldBlack10pxTitle" for="chk-school-repeattodate-${sch.schoolID}">Repeat to Date?</label>
		           															<input type="checkbox" id="chk-school-repeattodate-${sch.schoolID}" name="chk-school-repeattodate-${sch.schoolID}}" />
												                		<label class="boldBlack10pxTitle" for="chk-school-weather-related-${sch.schoolID}">Is this weather related?</label>
												           					<input type="checkbox" id="chk-school-weather-related-${sch.schoolID}" name="chk-school-weather-related-${sch.schoolID}"
												                  		onclick="this.checked ? $('#school-weather-related-closure-panel-${sch.schoolID}').show() : $('#school-weather-related-closure-panel-${sch.schoolID}').hide()" 
												                  		${sch.schoolClosureStatus.weatherRelated ? ' CHECKED' : ''} />
													              		<br/>
													              		<div id='school-weather-related-closure-panel-${sch.schoolID}' style="display:${sch.schoolClosureStatus.weatherRelated ? 'inline' : 'none'};">
														                  <label class="boldBlack10pxTitle" for="txt-school-rationale-${sch.schoolID}">Weather Related Rationale (internal use only):</label><br />
														                  <textarea class='txt-status-note' id="txt-school-rationale-${sch.schoolID}" name="txt-school-rationale-${sch.schoolID}" rows="3" cols="50" >${sch.schoolClosureStatus.rationale ne null ? sch.schoolClosureStatus.rationale : ''}</textarea>
													                  </div>
												                		<div>
									              							<label class="boldBlack10pxTitle" for="txt-school-note-${sch.schoolID}">Note(optional, to be posted to public website.):</label><br/>
													                		<textarea id='txt-school-note-${sch.schoolID}' name='txt-school-note-${sch.schoolID}'>${sch.schoolClosureStatus.schoolClosureNote ne null ? sch.schoolClosureStatus.schoolClosureNote : ''}</textarea>
												                		</div>
											                		</td>
											                	</tr>
											                </table>
								                		</div>
					                				</td>
					                			</tr>
					                		</table>
				                		</div>
			                		</c:forEach>
			                		</div>
			                	</c:forEach>
			                	</div>
						              
					           	</c:forEach>
           					
           				</div>
           			</td>
           		</tr>		
           	</c:forEach>
            </table>
            
      	</td>
    	</tr>
    	<tr>
    		<td align="center"><br/><br/><input id='btnApply' type='button' value='Apply' /><br/><br/></td>
    	</tr>
    </table>
    <hr width="60%" color="#333333" size="5">
  	</form>    
  </body>
</html>
