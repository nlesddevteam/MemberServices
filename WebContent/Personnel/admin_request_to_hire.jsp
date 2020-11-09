3<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.*,
                 com.esdnl.util.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.personnel.jobs.constants.*,
                 com.awsd.personnel.*,com.awsd.security.*" 
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
		<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="personnel" %>


<%
	Iterator iter = null;
	Personnel p = null;
	Personnel sup = null;
	User usr = null;
  	AdRequestBean req = (AdRequestBean) request.getAttribute("AD_REQUEST");
	iter = ((Supervisors) request.getAttribute("SUPERVISORS")).iterator();
	usr = (User) session.getAttribute("usr");
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>


<script>
$(document).ready(function() {
	
	var ptval=$('#hidpt').val();
	$("#position_type").val(ptval);
	var sval=$('#hids').val();
	$("#supervisor").val(sval);
	var dval=$('#hidd').val();
	$("#division").val(dval);
	var ucval=$('#hiduc').val();
	$("#union_code").val(ucval);
	$( "#union_code" ).trigger( "onchange" );
	//getPositions();
	var upn=$('#hidpn').val();
	$("#position_name").val(upn);
	//getJesPay();
	$( "#position_name" ).trigger( "onchange" );
	var uptrm=$('#hidptrm').val();
	$("#position_term").val(uptrm);
	var ureqt=$('#hidreqt').val();
	$("#request_type").val(ureqt);
	
});
</script>

<script>
$("#loadingSpinner").css("display","none");
</script>
<script type="text/javascript" src="includes/js/changepopup.js"></script>

<script>
var pageWordCountConf = {
	    showParagraphs: true,
	    showWordCount: true,
	    showCharCount: true,
	    countSpacesAsChars: true,
	    countHTML: true,
	    maxWordCount: -1,
	    maxCharCount: 3990,
	}
</script>
<style>
		.tableTitle {font-weight:bold;width:15%;}
		.tableResult {font-weight:normal;width:85%;}
		.tableTitleL {font-weight:bold;width:15%;}
		.tableResultL {font-weight:normal;width:35%;}
		.tableTitleR {font-weight:bold;width:15%;}
		.tableResultR {font-weight:normal;width:35%;}
</style>
</head>
<body>
  
  <esd:SecurityCheck permissions="PERSONNEL-ADREQUEST-REQUEST,RTH-NEW-REQUEST" />

<div class="panel-group" style="padding-top:5px;"> 

<div class="panel panel-success">
  <div class="panel-heading">Request to Hire  <i>* Required</i></div>
  	<div class="panel-body">	
  Please complete the following form to request a new hire.<br/><br/>
  <form id="frmAdRequest" action="submitRequestToHire.html" method="post" onsubmit="return CheckRequestToHire()">
  <input type="hidden" id="issupport" name="issupport" value="Y">
                                 
                                    <%if(request.getAttribute("msg")!=null){%>
                                      <div class="alert alert-danger">                                       
                                          <%=(String)request.getAttribute("msg")%>
                                       </div>
                                    <%}%>
                                  
                                      
 <div class="alert alert-danger" id="errorMessage" style="display:none;"><span id="spanmessage"></span></div>
                                       
    
    
    
 <div class="container-fluid">    
    
  <table class='table table-striped table-condensed' style='font-size:12px;'>
                    <tbody>
                    <tr>
			     		<td class='tableTitle'>POSITION TITLE:</td>
			     		<td colspan=3 class='tableResult' id="positionTitleGroup">   
    					<c:choose>
			           	<c:when test="${empty rbean}">
			           		<input type="text" class="form-control" name="job_title" id="job_title">
			           		<input type="hidden" id="rid" name="rid" value="-1">
			           	</c:when>
			           	<c:otherwise>
			           		<c:choose>
			           			<c:when test="${rbean.status.value eq 1 }">
			           				<input type="text" class="form-control" name="job_title" id="job_title" value="${rbean.jobTitle}">
			             			<input type="hidden" id="rid" name="rid" value="${rbean.id}">
			           			</c:when>
			           			<c:otherwise>
			           				${rbean.jobTitle}
			           				<input type="hidden" id="rid" name="rid" value="${rbean.id}">
			           			</c:otherwise>
			           		</c:choose>
			           	</c:otherwise>
			           	</c:choose>
  </td>
  </tr>
 						<tr>
			     		<td class='tableTitleL'>LOCATION:</td>
			     		<td class='tableResultL' id="locationGroup">
 										<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<personnel:Locations id='location'  cls="form-control" value=""/>
                                     	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      				<personnel:Locations id='location' cls="form-control" value="${empty rbean.workLocation ?'':rbean.locationDescription}"/>
                                     			</c:when>
                                      			<c:otherwise>
                                      				${empty rbean.workLocation ?'':rbean.locationDescription}
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
 						</td>
			     		<td class='tableTitleR'>PREVIOUS INCUMBENT:</td>
			     		<td class='tableResultR'>
			     						<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<input type="text" class="form-control" name="previous_incumbent" id="previous_incumbent">
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      				<input type="text" class="form-control" name="previous_incumbent" id="previous_incumbent" value="${rbean.previousIncumbent}">
                                      			</c:when>
                                      			<c:otherwise>
                                      				${rbean.previousIncumbent}
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
 						</td>
			     		</tr>
			     		 <tr>
			     		<td class='tableTitle'>REASON FOR VACANCY:</td>
			     		<td colspan=3 class='tableResult'> 
			     		<!-- Not sure why the options below - should be combined into one statement - why? CKEditor complains. -->
	                                      <c:choose>
	                                      	<c:when test="${empty rbean}">	                                      		
	                                      		
	                                      		<textarea class="form-control" name="vacancy_reason" id="vacancy_reason"></textarea>
												
											</c:when>
	                                      	<c:otherwise>
	                                      		<c:choose>
	                                      			<c:when test="${rbean.status.value eq 1 }">
	                                      				<textarea class="form-control" name="vacancy_reason" id="vacancy_reason">${empty rbean.vacancyReason ?'':rbean.vacancyReason}</textarea>
	                                      			</c:when>
	                                      			<c:otherwise>
	                                      				${empty rbean.vacancyReason ?'':rbean.vacancyReason}
	                                      			</c:otherwise>
	                                      		</c:choose>
	                                      	</c:otherwise>
	                                      	</c:choose>
			     		
			     		
			     		  
			     		</td>
			     		</tr>
			     		<tr>
			     		<td class='tableTitleL'>DATE VACATED:</td>
			     		<td class='tableResultL'> 
			     						<c:choose>
                                      	<c:when test="${empty rbean}">                                      	
                                      	<input class="form-control requiredinput_date" type="text" name="date_vacated" id="date_vacated">                                      	
                                      	
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      				<input class="form-control requiredinput_date" type="text" name="date_vacated" id="date_vacated" 
                                        				value="${empty rbean.dateVacated ?'':rbean.dateVacatedFormatted}">
                                      			</c:when>
                                      			<c:otherwise>
                                      				${empty rbean.dateVacated ?'':rbean.dateVacatedFormatted}
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>             
						</td>
			     		<td class='tableTitleR'>UNION:</td>
			     		<td class='tableResultR' id="unionGroup">
			     						<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<SELECT name="union_code" id="union_code" class="form-control" onchange="getPositions()">
											<OPTION VALUE='-1'>--- SELECT UNION---</OPTION>
												<c:forEach var="entry" items="${unioncodes}">
  													<option VALUE='${entry.value}'>${entry.key}</option>
												</c:forEach>
											</SELECT>
											<input type='hidden' id='hiduc' name='hiduc' value="-1">
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      				<SELECT name="union_code" id="union_code" class="form-control" onchange="getPositions()">
														<OPTION VALUE='-1'>--- SELECT UNION---</OPTION>
															<c:forEach var="entry" items="${unioncodes}">
  																<option VALUE='${entry.value}'>${entry.key}</option>
															</c:forEach>
													</SELECT>
													<input type='hidden' id='hiduc' name='hiduc' value="${rbean.unionCode eq 0 ?'0':rbean.unionCode}">
                                      			</c:when>
                                      			<c:otherwise>
                                      				${rbean.unionCode eq 0 ?'':rbean.unionCodeString}
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
			     		</td>
			     		</tr>
                        <tr>
			     		<td class='tableTitleL'>POSITION:</td>
			     		<td class='tableResultL' id="positionNGroup">
			     						<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<SELECT name="position_name" id="position_name" class="form-control">
																							
											</SELECT>
											<div style="display:none;">
												<SELECT name="jes_pay" id="jes_pay" class="form-control"></SELECT>
											</div>
											<input type='hidden' id='hidpn' name='hidpn' value="-1">
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      				<SELECT name="position_name" id="position_name" class="form-control" >
														<OPTION VALUE='-1'>--- SELECT POSITION---</OPTION>															
													</SELECT>
													<div style="display:none;">
														<SELECT name="jes_pay" id="jes_pay" class="form-control"></SELECT>
													</div>
													<input type='hidden' id='hidpn' name='hidpn' value="${rbean.positionName eq 0 ?'0':rbean.positionName}">
                                      			</c:when>
                                      			<c:otherwise>
                                      				${rbean.positionName eq 0 ?'':rbean.positionNameString}
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
			     		</td>
			     		<td class='tableTitleR'>POSITION TYPE:</td>
			     		<td class='tableResultR' id="positionTypeGroup">
			     						<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<SELECT name="position_type" id="position_type" class="form-control">
											<OPTION VALUE='-1'>--- SELECT JOB TYPE ---</OPTION>
											<%for(RTHPositionTypeConstant rtype : RTHPositionTypeConstant.ALL){%>
												
												<option VALUE='<%=rtype.getValue()%>'><%=rtype.getDescription()%></option>
											<%}%>
											</SELECT>
											<input type='hidden' id='hidpt' name='hidpt' value="-1">
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      				<SELECT name="position_type" id="position_type" class="form-control">
														<OPTION VALUE='-1'>--- SELECT JOB TYPE ---</OPTION>
															<%for(RTHPositionTypeConstant rtype : RTHPositionTypeConstant.ALL){%>
																<option VALUE='<%=rtype.getValue()%>'><%=rtype.getDescription()%></option>
															<%}%>
													</SELECT>
													<input type='hidden' id='hidpt' name='hidpt' value="${rbean.positionType eq 0 ?'0':rbean.positionType}">
                                      			</c:when>
                                      			<c:otherwise>
                                      				${rbean.positionType eq 0 ?'':rbean.positionTypeString}
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
			     		</td>
			     		</tr>         	
     					<tr>
			     		<td class='tableTitleL'>SALARY:</td>
			     		<td class='tableResultL'>
			     						<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<input type="text" name="position_salary" id="position_salary" class="form-control">
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      			<input type="text" name="position_salary" id="position_salary" class="form-control" value="${empty rbean.positionSalary ? '':rbean.positionSalary}"></c:when>
                                      			<c:otherwise>
                                      			${empty rbean.positionSalary ? '':rbean.positionSalary}                                   			
                                      				
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
                        </td>
			     		<td class='tableTitleR'>HOURS/WEEK*:</td>
			     		<td class='tableResultR'>
			     		<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<input type="text" name="position_hours" id="position_hours" class="form-control">
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      				<input type="text" name="position_hours" id="position_hours" class="form-control" value="${empty rbean.positionHours ? '':rbean.positionHours}">
                                      			</c:when>
                                      			<c:otherwise>
                                      			${empty rbean.positionHours? '':rbean.positionHours}                                   			
                                      				
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
			     		</td>
			     		</tr>            	
						<tr>
			     		<td class='tableTitleL'>POSITION TERM::</td>
			     		<td class='tableResultL' id="positionTermGroup">
			     						<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<SELECT name="position_term" id="position_term" class="form-control">
											<OPTION VALUE='-1'>--- SELECT POSITION TERM ---</OPTION>
											<OPTION VALUE='10'>10 Month</OPTION>
											<OPTION VALUE='12'>12 Month</OPTION>
											</SELECT>
											<input type='hidden' id='hidptrm' name='hidptrm' value="-1">
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      				<SELECT name="position_term" id="position_term" class="form-control">
														<OPTION VALUE='-1'>--- SELECT POSITION TERM ---</OPTION>
														<OPTION VALUE='10'>10 Month</OPTION>
														<OPTION VALUE='12'>12 Month</OPTION>
													</SELECT>
													<input type='hidden' id='hidptrm' name='hidptrm' value="${rbean.positionTerm eq 0 ?'0':rbean.positionTerm}">
                                      			</c:when>
                                      			<c:otherwise>
                                      				${rbean.positionTerm eq 0 ?'':rbean.positionTermString}
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
			     		</td>			     	
			     		<td class='tableTitleR'></td>
			     		<td class='tableResultR'></td>
			     		</tr>
						<tr>
			     		<td class='tableTitle'>SHIFT DIFFERENTIAL?</td>
			     		<td colspan=3 class='tableResult'>
			     						<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<input type="checkbox" name="shift_diff" id="shift_diff"> 
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      				<input type="checkbox" name="shift_diff" id="shift_diff" ${rbean.shiftDiff == 1 ? 'checked':''}>
                                      			</c:when>
                                      			<c:otherwise>
                                      			${rbean.shiftDiff == 1 ? 'Yes':'No'}                                			
                                      				
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>			     		
			     		</td>
			     		</tr>
			     		<tr>
			     		<td class='tableTitleL'>START DATE*:</td>
			     		<td class='tableResultL' id="startGroup">
			     						<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<input type="text" name="start_date" id="start_date" class="form-control requiredinput_date" />
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      			<input type="text" name="start_date" id="start_date" class="form-control requiredinput_date" value="${empty rbean.startDate ?'':rbean.startDateFormatted}">
                                      			</c:when>
                                      			<c:otherwise>
                                      				${empty rbean.startDate ?'':rbean.startDateFormatted}
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
			     		</td>
			     		<td class='tableTitleR'>END DATE (if applicable):</td>
			     		<td class='tableResultR' id="endGroup">
			     		<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<input type="text" name="end_date" id="end_date" class="form-control requiredinput_date" />
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      			<input type="text" name="end_date" id="end_date" class="form-control requiredinput_date" value="${empty rbean.endDate ?'':rbean.endDateFormatted}">
                                      			</c:when>
                                      			<c:otherwise>
                                      				${empty rbean.endDate ?'':rbean.endDateFormatted}
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
                                      	</td>
			     		</tr>
						<tr>
			     		<td class='tableTitleL'>SUPERVISOR:</td>
			     		<td class='tableResultL'id="supervisorGroup">
			     						<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<select name="supervisor" id="supervisor" class="form-control">
                          						<option value="SELECT YEAR">SELECT SUPERVISOR</option>
						                          <%while(iter.hasNext())
						                            {
						                              p = (Personnel) iter.next();
						                              if(!(usr.getUserRoles().containsKey("DIRECTOR") )){%>
						                              <option style="text-transform:capitalize;" value="<%=p.getPersonnelID()%>"><%=p.getFullName().toLowerCase()%></option>
						                          <%  }
						                            }
						                          %>
					                        </select>
					                        <input type='hidden' name='hids' id='hids' value="SELECT YEAR">                                      	
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
	                                      			<select name="supervisor" id="supervisor" class="form-control">
	                          						<option value="SELECT YEAR">SELECT SUPERVISOR</option>
							                          <%while(iter.hasNext())
							                            {
							                              p = (Personnel) iter.next();
							                              if(!( usr.getUserRoles().containsKey("DIRECTOR") )){%>
							                              <option style="text-transform:capitalize;" value="<%=p.getPersonnelID()%>"><%=p.getFullName().toLowerCase()%></option>
							                          <%  }
							                            }
							                          %>
						                        	</select>
					                        		<input type='hidden' name='hids' id='hids' value="${rbean.supervisor eq 0 ?'SELECT YEAR':rbean.supervisor}">                                      			
                                      			</c:when>
                                      			<c:otherwise>
                                      				${rbean.supervisorName}
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
 						</td>
			     		<td class='tableTitleR'>DIVISION:</td>
			     		<td class='tableResultR' id="divisionGroup">
			     							<c:choose>
	                                      	<c:when test="${empty rbean}">
					                    		<select id='division' name='division' class='form-control'>
					                    			<option value="-1" SELECTED>--- Select Division ---</option>
					                    			<option value="1">Programs</option>
					                    			<option value="2">Finance - Information Technology</option>
					                    			<option value="8">Finance - Financial Services</option>
					                    			<option value="3">Finance - Procurement and Business Services</option>
					                    			<option value="4">Finance - Student Transportation</option>
					                    			<option value="5">Facilities</option>
					                    			<option value="6">Human Resources</option>
					                    			<option value="7">Human Resources - Casuals/Student Assistant/Teacher Aides</option>
					                    		</select>
					                    		<input type='hidden' name='hidd' id='hidd' value="-1">	                                      		
	                                      	</c:when>
	                                      	<c:otherwise>
	                                      		<c:choose>
	                                      			<c:when test="${rbean.status.value eq 1 }">
						                    		<select id='division' name='division' class='form-control'>
						                    			<option value="-1">--- Select Division ---</option>
						                    			<option value="1">Programs</option>
						                    			<option value="2">Finance - Information Technology</option>
						                    			<option value="8">Finance - Financial Services</option>
						                    			<option value="3">Finance - Procurement and Business Services</option>
						                    			<option value="4">Finance - Student Transportation</option>
						                    			<option value="5">Facilities</option>
						                    			<option value="6">Human Resources</option>
						                    			<option value="7">Human Resources - Casuals/Student Assistant/Teacher Aides</option>
						                    		</select>
						                    		<input type='hidden' name='hidd' id='hidd' value="${empty rbean ?'-1': rbean.division eq 0 ?'-1':rbean.division}">	                                      			
	                                      			</c:when>
	                                      			<c:otherwise>
	                                      				${rbean.divisionString}
	                                      			</c:otherwise>
	                                      		</c:choose>
	                                      	</c:otherwise>
	                                      	</c:choose>
			     		
			     		</td>
			     		</tr>
    					 <tr>
			     		<td class='tableTitleL'>REQUEST TYPE*:</td>
			     		<td class='tableResultL' id="requestTGroup">
			     						<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<SELECT name="request_type" id="request_type" class="form-control">
											<OPTION VALUE=''>--- SELECT REQUEST TYPE ---</OPTION>
											<OPTION VALUE='M'>Management</OPTION>
											<OPTION VALUE='S'>Support</OPTION>
											<OPTION VALUE='O'>Other</OPTION>
											</SELECT>
											<input type='hidden' id='hidreqt' name='hidreqt' value="">
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      				<SELECT name="request_type" id="request_type" class="form-control">
														<OPTION VALUE=''>--- SELECT REQUEST TYPE ---</OPTION>
														<OPTION VALUE='M'>Management</OPTION>
														<OPTION VALUE='S'>Support</OPTION>
														<OPTION VALUE='O'>Other</OPTION>
													</SELECT>
													<input type='hidden' id='hidreqt' name='hidreqt' value="${rbean.requestType eq '' ?'':rbean.requestType}">
                                      			</c:when>
                                      			<c:otherwise>
                                      				${rbean.requestType eq '' ?'':rbean.requestTypeString}
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
			     		</td>
			     		<td class='tableTitleR'></td>
			     		<td class='tableResultR'></td>
			     		</tr>
			     		 <tr>
			     		<td class='tableTitle'>COMMENTS:</td>
			     		<td colspan=3 class='tableResult'> 
			     		<!-- Not sure why the options below - should be combined into one statement - why? CKEditor complains. -->
	                                      <c:choose>
	                                      	<c:when test="${empty rbean}">	                                      		
	                                      		
	                                      		<textarea class="form-control" name="comments" id="comments"></textarea>
												
											</c:when>
	                                      	<c:otherwise>
	                                      		<c:choose>
	                                      			<c:when test="${rbean.status.value eq 1 }">
	                                      				<textarea class="form-control" name="comments" id="comments">${empty rbean.comments ?'':rbean.comments}</textarea>
	                                      			</c:when>
	                                      			<c:otherwise>
	                                      				${empty rbean.comments ?'':rbean.comments}
	                                      			</c:otherwise>
	                                      		</c:choose>
	                                      	</c:otherwise>
	                                      	</c:choose>
			     		
			     		
			     		  
			     		</td>
			     		</tr>
		                <tr>
			     		<td class='tableTitle'>LIST PRIVATE?</td>
			     		<td colspan=3 class='tableResult'>
			     						<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<input type="checkbox" name="private_list" id="private_list"> 
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      				<input type="checkbox" name="private_list" id="private_list" ${rbean.privateList == 1 ? 'checked':''}>
                                      			</c:when>
                                      			<c:otherwise>
                                      			${rbean.privateList == 1 ? 'Yes':'No'}                                   			
                                      				
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
			     		</td>
			     		</tr>

	<c:if test="${rbean.status.value eq 2 }">
	     				<tr>
			     		<td class='tableTitleL'>POSITION NUMBER:</td>
			     		<td class='tableResultL'>
			     		<c:if test="${VIEWAPPROVE}">
			     			<input type="text" name="position_number" id="position_number" class="form-control">
			     		</c:if>
			     		</td>	                    
			     		<td class='tableTitleR'></td>
			     		<td class='tableResultR'></td>
			     		</tr>
	
	 </c:if>
	<c:if test="${rbean.status.value gt 2 }">
						<tr>
			     		<td class='tableTitleL'>POSITION NUMBER:</td>
			     		<td class='tableResultL'>
			     		${empty rbean.positionNumber ?'':rbean.positionNumber}
			     		</td>	                    
			     		<td class='tableTitleR'></td>
			     		<td class='tableResultR'></td>
			     		</tr>
	
	
	
	  	
		
	</c:if>

</tbody>
</table>

 
 <div class="panel panel-info">
  <div class="panel-heading">Current Status &amp; Log:</div>
  	<div class="panel-body" style="font-size:11px;">
 
 
                                     
                                     
                                      	<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<div class="alert alert-danger" style="text-align:center;font-size:14px;">UNSUBMITTED</div>
                                     	</c:when>
                                      	<c:otherwise>
                                      		<div class="alert alert-success" style="text-align:center;font-size:14px;">${rbean.status.description}</div>
                                      	</c:otherwise>
                                      	</c:choose>
            
   
 										<ol>                    
                                      	<c:choose>
                                    		<c:when test="${not empty HBEANS}">
	                                      		<c:forEach items="${HBEANS}" var="treemap">
	                                      			<li>${treemap.value.notes} on ${treemap.value.historyDateTime}		</li>	                                      				                                      			
		                                      	</c:forEach>
		                                     </c:when>
		                                     <c:otherwise>
		                                      	<li>No status information currently logged.</li>
		                                     </c:otherwise>
		                                 </c:choose>
		                                 </ol>
</div></div>
<!-- ADMINISTRATIVE FUNCTIONS -->

<br/>  		                             
<div class="no-print" align="center">
            
              <div class="alert alert-warning" id="errorMessageS" style="display:none;"><span id="spanmessageS"></span></div>
                                                               
                                    <c:choose>
                                    	<c:when test="${empty rbean}">
                                    			
                                        				<input class="btn btn-xs btn-primary" type="submit" value="Submit">
                                      				
                                    	</c:when>
                                    	<c:otherwise>
                                    						<c:if test="${rbean.status.value eq 1 }">
                                    							<c:if test="${UPDATEDELETE}">
	                                      							<input type="submit" class="btn btn-primary btn-xs" value="Update">
	                                      							<input type="button" class="btn btn-success btn-xs" value="Delete" onclick="deleterequest('${rbean.id}');">
	                                      						</c:if>
 															</c:if>
                                      						<c:if test="${VIEWAPPROVE}">
	                                      					<c:if test="${rbean.status.value eq 1 }">
	                                      						<input type="button" class="btn btn-success btn-xs" value="Approve" onclick="updaterequeststatus('A','2','${rbean.id}')">
	                                      						<input type="button" class="btn btn-danger btn-xs" value="Decline" onclick="openreject('D','2')">
	                                      					</c:if>
	                                      					<c:if test="${rbean.status.value eq 2 }">
	                                      						<input type="button" class="btn btn-success btn-xs" value="Approve" onclick="updaterequeststatuscomp('A','3','${rbean.id}')">
	                                      						<input type="button" class="btn btn-danger btn-xs" value="Decline" onclick="openreject('D','3')">
	                                      					</c:if>
	                                      					<c:if test="${rbean.status.value eq 3 }">
	                                      						<input type="button" class="btn btn-success btn-xs" value="Approve" onclick="updaterequeststatus('A','4','${rbean.id}')">
	                                      						<input type="button" class="btn btn-danger btn-xs" value="Decline" onclick="openreject('D','4')">
	                                      					</c:if>
	                                      					<c:if test="${rbean.status.value eq 4 }">
	                                      						<input type="button" class="btn btn-success btn-xs" value="Approve" onclick="updaterequeststatus('A','5','${rbean.id}')">
	                                      						<input type="button" class="btn btn-danger btn-xs" value="Decline" onclick="openreject('D','5')">
	                                      					</c:if>
															<c:if test="${rbean.status.value eq 5 }">
                                      							<input class="btn btn-primary btn-xs" type="button" value="Post this Ad" onclick="PostRequestToHire();">
                                      						</c:if>
                                      						<c:if test="${rbean.status.value eq 6 }">
                                      							<a class="btn btn-warning btn-xs" href="view_job_post.jsp?comp_num=${rbean.competitionNumber }">View Competition ${rbean.competitionNumber }</a>
                                      						</c:if>
                                      						<c:if test="${rbean.status.value eq 12 }">
	                                      						<input type="button" class="btn btn-success btn-xs" value="Approve" onclick="updaterequeststatus('A','13','${rbean.id}')">
	                                      						<input type="button" class="btn btn-danger btn-xs" value="Decline" onclick="openreject('D','12')">
	                                      					</c:if>
	                                      					<c:if test="${rbean.status.value eq 13 }">
	                                      						<input type="button" class="btn btn-success btn-xs" value="Approve" onclick="updaterequeststatus('A','2','${rbean.id}')">
	                                      						<input type="button" class="btn btn-danger btn-xs" value="Decline" onclick="openreject('D','13')">
	                                      					</c:if>						
                                      					</c:if>
                                      					
                                      					<c:if test="${(rbean.status.value gt 1 && rbean.status.value lt 6) || (rbean.status.value == 13)}">
                                      						<esd:SecurityAccessRequired roles="ADMINISTRATOR,SEO - PERSONNEL">
                                      							<input type="button" class="btn btn-success btn-xs" value="Delete" onclick="deleteapprovedrequest('${rbean.id}');">
                                      						</esd:SecurityAccessRequired>
                                      					</c:if>
                                      					<c:if test="${rbean.status.value lt 6 || rbean.status.value gt 11 }">
                                      							<input type="button" class="btn btn-danger btn-xs" value="Resend Notification" onclick="resendrthmessage('${rbean.id}')">
                                      					</c:if>
                                      				                                 		
                                    	</c:otherwise>
                                    </c:choose>
                                      
                                   <a href='#' title='Print this page (pre-formatted)' class="btn btn-xs btn-primary" onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Human Resources Profile System</b></div><br/><br/>'});"><span class="glyphicon glyphicon-print"></span> Print Page</a>
		            				 <a class="btn btn-danger btn-xs" href="javascript:history.go(-1);">Back</a>

 </div>               
 
 
 </div>        
     </form>                           
   </div></div></div> 
 </div>        
     </form>                           
   </div></div></div> 
   <div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"><span id="modaltitle"></span></h4>
      </div>
      <div class="modal-body">
        <p><span id="modaltext"></span></p>
      </div>
      <div class="modal-body2" style="display:none;text-align:center;" id="modalnotes">
      	<p>Notes:</p>
      	<br>
        <textarea class = "form-control" rows = "5" style="width:75%;display: block;margin-left: auto;margin-right: auto;" id="rnotes"></textarea>
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-xs btn-primary" id="btnreject">Ok</button>
        <button type="button" class="btn btn-xs btn-default" data-dismiss="modal" >Close</button><input type="hidden" id="trantype"><input type="hidden" id="transtatus">
      </div>
    </div>

  </div>
</div>   
   <script>
    CKEDITOR.replace( 'comments',{wordcount: pageWordCountConf,height:150});
    CKEDITOR.replace( 'vacancy_reason',{wordcount: pageWordCountConf,height:150});
    </script>   
                          
<script language="JavaScript">
  
  $('document').ready(function(){
		$( ".requiredinput_date" ).datepicker({
	      	changeMonth: true,//this option for allowing user to select month
	      	changeYear: true, //this option for allowing user to select from year range
	      	dateFormat: "mm/dd/yy"
	      
	 	});
  });
  

</script>
</body>
</html>
