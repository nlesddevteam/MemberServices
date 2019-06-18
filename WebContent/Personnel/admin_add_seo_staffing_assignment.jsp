<%@ page language="java"
         import="com.awsd.security.*" %>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />



<html>
  <head>
    <title>MyHRP Applicant Profiling System</title>
    
		<script type="text/javascript">
			$('document').ready(function(){
				$('tr.assignment-list:odd').css({'background-color' : '#e4e4e4'});
				
				$('#ctype').change(function(){
					if($(this).val() == '1') {
						$('#subjects').show();
						$('#trnmthds').hide();
					}
					else if($(this).val() == '2') {
						$('#subjects').hide();
						$('#trnmthds').show();
					}
					else {
						$('#subjects').hide();
						$('#trnmthds').hide();
					}
				});
			});
		</script>
		
		<style>
		.tableTitle {font-weight:bold;width:80%;}
		.tableResult {font-weight:bold;width:20%;}
		.tableSeo {font-weight:bold;width:60%;}
		.tableCriteria {font-weight:bold;width:30%;}
		.tableOptions {font-weight:bold;width:10%;}	 	
		</style>
		<script>		  
			$("#loadingSpinner").css("display","none");
		</script>
		<script type='text/javascript'>
			$(function(){					
					
					$('#btn-submit').click(function(){
					
					if (!$('.personnel_id').is(":checked")) {				           
				         $('#displayMsg').removeClass("alert-success").addClass("alert-danger").html('<b>ERROR:</b> Please select SEO(s) for this Staff Assignment.').css('display','block').delay(5000).fadeOut();
						 return false;
					} else if (!$('.criteria_id').is(":checked")) {	 	
						$('#displayMsg').removeClass("alert-success").addClass("alert-danger").html('<b>ERROR:</b> Please make sure you select at least one criteria type.').css('display','block').delay(5000).fadeOut();
						 return false;					
				    } else {
						$('#frm-seo-staffing').submit();
					}
						
					});
					
			});
			
		</script>
  </head>
  
  <body>
  <div class="panel-group" style="padding-top:5px;">  
  <form action='addSEOStaffingAssignment.html' id='frm-seo-staffing' method='post' >
  <input type='hidden' name='op' value='confirm' />
   
  <div class="panel panel-success">
  <div class="panel-heading"><b>Add SEO Staffing Assignment</b> (Select Criteria)</div>
  	<div class="panel-body">
  	<%if(request.getAttribute("msg") != null){%>
	<div class="alert alert-danger" style="text-align:center;"><%=(String)request.getAttribute("msg")%></div>
	<%}%>
	<div class="alert alert-success" id="displayMsg" style="text-align:center;display:none;" align="center"></div>
	
								<div class="container-fluid">
	                       	    <div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">	                        
		                             	<div class="input-group">
		    								<span class="input-group-addon">Criteria Type:</span>
		    								<select id='ctype' name="criteria_type" class='form-control'>
	                             						<option value='-1'>--- SELECT TYPE ---</option>
	                              					<c:forEach items='${ctypes}' var="ctype">
	                              						<option value="${ctype.value}">${ctype.description}</option>
	                              					</c:forEach>
	                             			</select>
		    								
		    								
		    								</div>
		  							</div>	
		  						</div>
		  						</div>

	</div></div>
		  						
<div class="panel panel-success">
  <div class="panel-heading">SELECT SEO(s):</div>
  	<div class="panel-body">
  	<c:forEach items='${seos}' var="seo">	                              					
	                              						
	 <div style="float:left;min-width:220px;width:25%;"><label class="checkbox-inline"><input type="checkbox" class="personnel_id" name='personnel_id' value="${seo.personnelID}">${seo.fullName}</label></div> 		
	</c:forEach>
	
	
	</div>
</div>			  						
		  						
<div class="panel panel-success" id='subjects' style='display:none;'>
  <div class="panel-heading">SELECT SUBJECT(s):</div>
  	<div class="panel-body">
  	<c:forEach items='${subjects}' var="subject">
  	<div style="float:left;min-width:220px;width:25%;"><label class="checkbox-inline"><input type="checkbox" class='criteria_id' name='criteria_id' value="${subject.subjectID}">${subject.subjectName}</label></div>
	</c:forEach>
	</div>
</div>		  						
		  						
<div class="panel panel-success" id='trnmthds' style='display:none;'>
  <div class="panel-heading">SELECT TRAINING METHOD(s):</div>
  	<div class="panel-body">
  	    <c:forEach items='${trnmths}' var="trnmthd">  	    
  	    <div style="float:left;min-width:220px;width:25%;"><label class="checkbox-inline"><input type="checkbox" class='criteria_id' name='criteria_id' value="${trnmthd.value}">${trnmthd.description}</label></div>
  	    </c:forEach>
	
	
	</div>
</div>
<br/>
 <div align="center" class="no-print">
 <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>  <input type='submit' id='btn-submit' class="btn btn-xs btn-success" value='Add Assignment'/>
 </div>                             


 <br />
 

<div class="table-responsive" style="margin-top:10px;"> 
<table class="table table-striped table-condensed" style="font-size:12px;">	
<tr>
<th class="tableSeo">SEO</th>
<th class="tableCriteria">CRITERIA</th>
<th class="tableOptions">OPTIONS</th>
</tr>
		                              	
<c:choose>
                   	<c:when test='${fn:length(assignments) gt 0}'>
                    	<c:forEach items='${assignments}' var='a'>
                    		<tr>
                     		<td>${a.personnel.fullName}</td>
                     		<td>${a.criteria}</td>
                     		<td><a class="btn btn-xs btn-danger" href='deleteSEOStaffingAssignment.html?sid=${a.staffingId}'>remove</a></td>
                     		</tr>
                    	</c:forEach>
                   	</c:when>
                   	<c:otherwise>
                   		<tr>
                     		<td colspan=3>No staffing assignments found.</td>
                   		</tr>
                   	</c:otherwise>
</c:choose>
</table>	     		      			 	       		
		      			 	       		
		      			 	       		
</div>

</form>
</div>  

<script>
<%=(String)request.getAttribute("displayMsgFnct")%>
</script>
  </body>
</html>
