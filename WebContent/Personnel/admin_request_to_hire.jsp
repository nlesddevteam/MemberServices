<%@ page language="java"
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
<script type="text/javascript" src="js/changepopup.js"></script> 

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

</head>
<body>
  
  <esd:SecurityCheck permissions="PERSONNEL-ADREQUEST-REQUEST" />

<div class="panel-group" style="padding-top:5px;"> 

<div class="panel panel-success">
  <div class="panel-heading">Request to Hire  <i>* Required</i></div>
  	<div class="panel-body">	
  Please complete the following form to request a new hire.<br/>
  <form id="frmAdRequest" action="submitRequestToHire.html" method="post" onsubmit="return CheckRequestToHire()">
  <input type="hidden" id="issupport" name="issupport" value="Y">
                                 
                                    <%if(request.getAttribute("msg")!=null){%>
                                      <div class="alert alert-danger">                                       
                                          <%=(String)request.getAttribute("msg")%>
                                       </div>
                                    <%}%>
                                  
                                      
 <div class="alert alert-danger" id="errorMessage" style="display:none;"><span id="spanmessage"></span></div>
                                        
                                      
<div class="input-group" id="positionTitleGroup">

    <span class="input-group-addon">Position Title*:</span>  
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
           				<div class="form-control">${rbean.jobTitle}</div>
           				<input type="hidden" id="rid" name="rid" value="${rbean.id}">
           			</c:otherwise>
           		</c:choose>
           	</c:otherwise>
           	</c:choose>
</div>       
 <div class="input-group">
    <span class="input-group-addon">Previous Incumbent:</span>                                   
                                    
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
                                      				<div class="form-control">${rbean.previousIncumbent}</div>
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
 </div>                                     	
 <div class="input-group" id="locationGroup">
    <span class="input-group-addon">Location*:</span>                                     	
                                     
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
                                      				<div class="form-control">${empty rbean.workLocation ?'':rbean.locationDescription}</div>
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
 </div>                                     	
 <div class="input-group">
    <span class="input-group-addon">Date Vacated:</span>                                     
                                     
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
                                      				<div class="form-control">${empty rbean.dateVacated ?'':rbean.dateVacatedFormatted}</div>
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
 </div>                                    
<div class="input-group">
    <span class="input-group-addon">Union:</span>
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
                                      				<div class="form-control">${rbean.unionCode eq 0 ?'':rbean.unionCodeString}</div>
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
</div>      
<span id="positionGroup">                              	
<div class="input-group" >
    <span class="input-group-addon">Position:</span>
                                      	<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<SELECT name="position_name" id="position_name" class="form-control" onchange="getJesPay()">
																							
											</SELECT>
											<div style="display:none;">
												<SELECT name="jes_pay" id="jes_pay" class="form-control"></SELECT>
											</div>
											<input type='hidden' id='hidpn' name='hidpn' value="-1">
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      				<SELECT name="position_name" id="position_name" class="form-control" onchange="getJesPay()">
														<OPTION VALUE='-1'>--- SELECT POSITION---</OPTION>															
													</SELECT>
													<div style="display:none;">
														<SELECT name="jes_pay" id="jes_pay" class="form-control"></SELECT>
													</div>
													<input type='hidden' id='hidpn' name='hidpn' value="${rbean.positionName eq 0 ?'0':rbean.positionName}">
                                      			</c:when>
                                      			<c:otherwise>
                                      				<div class="form-control">${rbean.positionName eq 0 ?'':rbean.positionNameString}</div>
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
 </div>  </span>                                	
 <div class="input-group"  id="positionTypeGroup">
    <span class="input-group-addon">Position Type*:</span>                                     	

                                      
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
                                      				<div class="form-control">${rbean.positionType eq 0 ?'':rbean.positionTypeString}</div>
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
</div>                                      	
<div class="input-group">
    <span class="input-group-addon">Position Salary:</span>                                      	
                                     
                                      	<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<input type="text" name="position_salary" id="position_salary" class="form-control">
                                      	</c:when>
                                      	<c:otherwise>
                                      		<c:choose>
                                      			<c:when test="${rbean.status.value eq 1 }">
                                      				<input type="text" name="position_salary" id="position_salary" class="form-control" value="${empty rbean.positionSalary ? '':rbean.positionSalary}">
                                      			</c:when>
                                      			<c:otherwise>
                                      			<div class="form-control">${empty rbean.positionSalary ? '':rbean.positionSalary}</div>                                     			
                                      				
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
 </div>
 <div class="input-group">
    <span class="input-group-addon">Hours/Week*:</span>                                      	
                                     
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
                                      			<div class="form-control">${empty rbean.positionHours? '':rbean.positionHours}</div>                                     			
                                      				
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
 </div>
  <div class="input-group"  id="positionTypeGroup">
    <span class="input-group-addon">Position Term*:</span>                                     	

                                      
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
                                      				<div class="form-control">${rbean.positionTerm eq 0 ?'':rbean.positionTermString}</div>
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
</div>
<div class="input-group">
    <span style="color:DimGrey">&nbsp; &nbsp; Does this position offer a Shift Differential? &nbsp; </span>                                   	
                                     
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
                                      			<div class="form-control">${rbean.shiftDiff == 1 ? 'Yes':'No'}</div>                                     			
                                      				
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
 </div>
 <div class="input-group"  id="startGroup">
    <span class="input-group-addon">Start Date*:</span>                                   	
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
                                      				<div class="form-control">${empty rbean.startDate ?'':rbean.startDateFormatted}</div>
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
</div>
 <div class="input-group"  id="endGroup">
    <span class="input-group-addon">End Date (if applicable):</span>                                   	
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
                                      				<div class="form-control">${empty rbean.endDate ?'':rbean.endDateFormatted}</div>
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
</div>
<div class="input-group">
    <span class="input-group-addon">Supervisor:</span>                                      	
                                      
                                      	<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<select name="supervisor" id="supervisor" class="form-control">
                          						<option value="SELECT YEAR">SELECT SUPERVISOR</option>
						                          <%while(iter.hasNext())
						                            {
						                              p = (Personnel) iter.next();
						                              if((p.getPersonnelID() != usr.getPersonnel().getPersonnelID()) 
						                                || usr.getUserRoles().containsKey("DIRECTOR") || usr.getUserRoles().containsKey("ADMINISTRATOR")){%>
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
							                              if((p.getPersonnelID() != usr.getPersonnel().getPersonnelID()) 
							                                || usr.getUserRoles().containsKey("DIRECTOR") || usr.getUserRoles().containsKey("ADMINISTRATOR")){%>
							                              <option style="text-transform:capitalize;" value="<%=p.getPersonnelID()%>"><%=p.getFullName().toLowerCase()%></option>
							                          <%  }
							                            }
							                          %>
						                        	</select>
					                        		<input type='hidden' name='hids' id='hids' value="${rbean.supervisor eq 0 ?'SELECT YEAR':rbean.supervisor}">                                      			
                                      			</c:when>
                                      			<c:otherwise>
                                      				<div class="form-control">${rbean.supervisorName}</div>
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
</div>                                     	
<div class="input-group">
    <span class="input-group-addon">Division:</span>                                      	
                                      
	                                      	<c:choose>
	                                      	<c:when test="${empty rbean}">
					                    		<select id='division' name='division' class='form-control'>
					                    			<option value="-1" SELECTED>--- Select Division ---</option>
					                    			<option value="1">Programs</option>
					                    			<option value="2">Finance - Information Technology</option>
					                    			<option value="3">Finance - Procurement and Business Services</option>
					                    			<option value="4">Finance - Student Transportation</option>
					                    			<option value="5">Facilities</option>
					                    			<option value="6">Human Resources</option>
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
						                    			<option value="3">Finance - Procurement and Business Services</option>
						                    			<option value="4">Finance - Student Transportation</option>
						                    			<option value="5">Facilities</option>
						                    			<option value="6">Human Resources</option>
						                    		</select>
						                    		<input type='hidden' name='hidd' id='hidd' value="${empty rbean ?'-1': rbean.division eq 0 ?'-1':rbean.division}">	                                      			
	                                      			</c:when>
	                                      			<c:otherwise>
	                                      				<div class="form-control">${rbean.divisionString}</div>
	                                      			</c:otherwise>
	                                      		</c:choose>
	                                      	</c:otherwise>
	                                      	</c:choose>
</div>	
  <div class="input-group"  id="positionTypeGroup">
    <span class="input-group-addon">Request Type*:</span>                                     	

                                      
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
                                      				<div class="form-control">${rbean.requestType eq '' ?'':rbean.requestTypeString}</div>
                                      			</c:otherwise>
                                      		</c:choose>
                                      	</c:otherwise>
                                      	</c:choose>
</div>
                                                                       	
<div class="input-group">
    <span class="input-group-addon">Comments:</span>
                                                                          
	                                   <!-- Not sure why the options below - should be combined into one statement -->
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
	                                      				<div class="form-control">${empty rbean.comments ?'':rbean.comments}</div>
	                                      			</c:otherwise>
	                                      		</c:choose>
	                                      	</c:otherwise>
	                                      	</c:choose>
	                                      	
	                                      	
	                                      	
	                                   
</div>
<c:if test="${VIEWAPPROVE}">
	<c:if test="${rbean.status.value eq 2 }">
	  	<div class="input-group"  id="positionNumberGroup">
    		<span class="input-group-addon">Position Number:</span>                                     	

			<input type="text" name="position_number" id="position_number" class="form-control">
		</div>
		
	</c:if>
	<c:if test="${rbean.status.value gt 2 }">
	  	<div class="input-group"  id="positionNumberGroup">
    		<span class="input-group-addon">Position Number:</span>                                     	

			<div class="form-control">${empty rbean.positionNumber ?'':rbean.positionNumber}</div>
		</div>
		
	</c:if>
</c:if>


 
 <div class="panel panel-info">
  <div class="panel-heading">Current Status:</div>
  	<div class="panel-body">
 
 
                                     
                                     
                                      	<c:choose>
                                      	<c:when test="${empty rbean}">
                                      		<span style="background-color:Red;padding:2px;color:White;">UNSUBMITTED</span>
                                     	</c:when>
                                      	<c:otherwise>
                                      		<span style="background-color:Green;padding:2px;color:White;">${rbean.status.description}</span>
                                      	</c:otherwise>
                                      	</c:choose>
            
</div></div> 
 
 <div class="panel panel-info">
  <div class="panel-heading">Status Information Log:</div>
  	<div class="panel-body">
   
 										<ul>                    
                                      	<c:choose>
                                    		<c:when test="${not empty rbean}">
		                                      	<li>Submitted by ${rbean.requestBy} on ${rbean.dateRequestedFormatted}
		                                      	
		                                      		<c:if test="${not empty rbean.ddApproved}">
		                                      		
		                                      		<li>Division Director Approval by ${rbean.ddApprovedBy} on ${rbean.ddApprovedFormatted}
		                                      		
		                                      		</c:if>
		                                      		<c:if test="${not empty rbean.bcApproved}">
		                                      	
		                                      		<li>Comptroller Approval by ${rbean.bcApprovedBy} on ${rbean.bcApprovedFormatted}
		                                      
		                                      		</c:if>	
		                                      		<c:if test="${not empty rbean.adApproved}">
		                                      
		                                      		<li>Assistant Director Approval by ${rbean.adApprovedBy} on ${rbean.adApprovedFormatted}
		                                      		
		                                      		</c:if>
		                                      		<c:if test="${not empty rbean.adhrApproved}">
		                                      		
		                                      		<li>Assistant Director HR Approval by ${rbean.adhrApprovedBy} on ${rbean.adhrApprovedFormatted}
		                                      		
		                                      		</c:if>
		                                      		<c:if test="${rbean.status.value eq 7}">
		                                      		
		                                      		<li><span style="color:Red;">Request has been declined.</span>
		                                      			
		                                      		</c:if>
		                                      		<c:if test="${not empty rbean.competitionNumber}">
		                                      		
		                                      		<li><span style="color:Green;">Competition Number ${ rbean.competitionNumber} created.</span>
		                                      			
		                                      		</c:if>			                                      					                                      				                                      			
		                                      
		                                      </c:when>
		                                      <c:otherwise>
		                                      <li>No status information currently logged.
		                                      </c:otherwise>
		                                 </c:choose>
		                                 </ul>
</div></div>
<!-- ADMINISTRATIVE FUNCTIONS -->

<br/>  		                             
<div class="no-print" align="center">
            
                                                                      
                                    <c:choose>
                                    	<c:when test="${empty rbean}">
                                    			
                                        				<input class="btn btn-primary" type="submit" value="Submit">
                                      				
                                    	</c:when>
                                    	<c:otherwise>
 												
                                      					<c:if test="${VIEWAPPROVE}">
	                                      					<c:if test="${rbean.status.value eq 1 }">
	                                      						<input type="submit" class="btn btn-primary" value="Submit">
	                                      						<input type="button" class="btn btn-success" value="Approve" onclick="updaterequeststatus('A','2','${rbean.id}')">
	                                      						<input type="button" class="btn btn-danger" value="Decline" onclick="updaterequeststatus('D','2','${rbean.id}')">
	                                      					</c:if>
	                                      					<c:if test="${rbean.status.value eq 2 }">
	                                      						<input type="button" class="btn btn-success" value="Approve" onclick="updaterequeststatuscomp('A','3','${rbean.id}')">
	                                      						<input type="button" class="btn btn-danger" value="Decline" onclick="updaterequeststatus('D','3','${rbean.id}')">
	                                      					</c:if>
	                                      					<c:if test="${rbean.status.value eq 3 }">
	                                      						<input type="button" class="btn btn-success" value="Approve" onclick="updaterequeststatus('A','4','${rbean.id}')">
	                                      						<input type="button" class="btn btn-danger" value="Decline" onclick="updaterequeststatus('D','4','${rbean.id}')">
	                                      					</c:if>
	                                      					<c:if test="${rbean.status.value eq 4 }">
	                                      						<input type="button" class="btn btn-success" value="Approve" onclick="updaterequeststatus('A','5','${rbean.id}')">
	                                      						<input type="button" class="btn btn-danger" value="Decline" onclick="updaterequeststatus('D','5','${rbean.id}')">
	                                      					</c:if>

                                      					</c:if>
                                      					<c:if test="${rbean.status.value eq 5 }">
                                      						<input class="btn btn-primary" type="button" value="Post this Ad" onclick="PostRequestToHire();">
                                      					</c:if>
                                      					<c:if test="${rbean.status.value eq 6 }">
                                      						<a class="btn btn-warning" href="view_job_post.jsp?comp_num=${rbean.competitionNumber }">View Competition ${rbean.competitionNumber }</a>
                                      					</c:if>
                                      				                                 		
                                    	</c:otherwise>
                                    </c:choose>
                                   <a class="btn btn-danger" href="javascript:history.go(-1);">Back</a>

 </div>                       
     </form>                           
   </div></div></div>  
   
   <script>
    CKEDITOR.replace( 'comments',{wordcount: pageWordCountConf,height:150});
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
