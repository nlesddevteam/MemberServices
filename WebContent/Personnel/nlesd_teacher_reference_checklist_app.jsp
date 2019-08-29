<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*,com.awsd.personnel.*" 
         isThreadSafe="false"%>
         
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>		
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<esd:SecurityRequiredPageObjectsCheck
	names='<%=new String[]{"REFREQUEST", "PROFILE"}%>'
	scope='<%=PageContext.REQUEST_SCOPE%>'
	redirectTo="/Personnel/admin_index.jsp" />

<%
	ApplicantRefRequestBean refReq = (ApplicantRefRequestBean) request.getAttribute("REFREQUEST");
	ApplicantProfileBean profile = (ApplicantProfileBean) request.getAttribute("PROFILE");
	String val1="1";
	String val2="2";
	String val3="3";
	String val4="4";
	String val5="-1";
	String val5Text="N/A";
	String refscale="5";
	
	Personnel p = null;
	try{
		p = PersonnelDB.getPersonnelByEmail(refReq.getEmailAddress());
	}catch (Exception e){
		p=null;
	}
	
%>

<html>
	<head>
		<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>

		<script>
			$('document').ready(function(){
				$('#btnSubmit').click(function(){
					var is_valid = true;
					if($('#ref_provider_name').val() == ''){
						is_valid = false;
						$('#section0Error').css('display','block').delay(5000).fadeOut();
						$('#ref_provider_name').focus();
					} 
					else if($('#ref_provider_position').val() == ''){
						is_valid = false;						
						$('#section0Error').css('display','block').delay(5000).fadeOut();
						$('#ref_provider_position').focus();
					}
					
					
					else if(!$("input[name='Q1']:checked").val() || $("input[name='Q2']").val() == '' || $("input[name='Q3']").val() == '' || $("textarea[name='Q4']").val() == ''
							|| !$("input[name='Q5']:checked").val() || !$("input[name='Q6']:checked").val()) {
						
						is_valid = false;
						$('#section1Error').css('display','block').delay(5000).fadeOut();
						$('#Q1').focus();
					}
					
					
					else if(!$("input[name='Scale1']:checked").val() || !$("input[name='Scale2']:checked").val() || !$("input[name='Scale3']:checked").val()
								|| !$("input[name='Scale4']:checked").val() || !$("input[name='Scale5']:checked").val() || !$("input[name='Scale6']:checked").val()) {
						is_valid = false;
						$('#section2Error').css('display','block').delay(5000).fadeOut();
						$('#Scale1').focus();	
					}
					
					
					else if(!$("input[name='Scale7']:checked").val() || !$("input[name='Scale8']:checked").val() || !$("input[name='Scale9']:checked").val()
								|| !$("input[name='Scale10']:checked").val() || !$("input[name='Scale11']:checked").val()) {
						is_valid = false;
						$('#section3Error').css('display','block').delay(5000).fadeOut();
						$('#Scale7').focus();	
					}
					
					
					else if(!$("input[name='Scale12']:checked").val() || !$("input[name='Scale13']:checked").val() || !$("input[name='Scale14']:checked").val()
							|| !$("input[name='Scale15']:checked").val() || !$("input[name='Scale16']:checked").val()) {
						is_valid = false;
						$('#section4Error').css('display','block').delay(5000).fadeOut();
						$('#Scale12').focus();	
					}
					
					
					
					else if(!$("input[name='Scale17']:checked").val() || !$("input[name='Scale18']:checked").val() || !$("input[name='Scale19']:checked").val()
							|| !$("input[name='Scale20']:checked").val() || !$("input[name='Scale21']:checked").val() || !$("input[name='Scale22']:checked").val()) {
						is_valid = false;
						$('#section5Error').css('display','block').delay(5000).fadeOut();
						$('#Scale17').focus();	
					}					
					
					
					
					else if($("select[name='Q7']").val() == -1) {
						is_valid = false;
						$('#section6Error').css('display','block').delay(5000).fadeOut();
						$("select[name='Q7']").focus();
					}
					return is_valid;
				});
							
			});

		</script>
		
	<style>
		.tableTitle {font-weight:bold;width:20%;}
		.tableResult {font-weight:normal;width:80%;}
		.tableQuestionNum {font-weight:bold;width:5%;}
		.tableQuestion {width:95%;}
		.ratingQuestionNum {font-weight:bold;width:5%;}
		.ratingQuestion {width:75%;}
		.ratingAnswer {width:20%;}
		input[type="radio"] {margin-top: -1px;margin-left:6px;margin-right:2px;vertical-align: middle;}
	</style>	
		
		
	</head>
	<body>

<div class="pageHeader">Teacher Candidate <%= profile.getFullNameReverse() %> Reference</div>

			                            <%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){ %>
			                            	<div class="alert alert-danger" style="text-align:center;"><%=(String)request.getAttribute("msg")%></div>
			                            <%} %> 
			                            
			                            
			                            
 <form action="addNLESDExternalTeacherReferenceApp.html" method="POST" name="admin_nlesd_rec_form" id="admin_nlesd_rec_form">
							<input type='hidden' id='applicant_id' name='applicant_id' value='<%= profile.getUID() %>' />
                      		<input type='hidden' id='confirm' name='confirm' value='true' />
                        	<input type='hidden' id='reqreqid' name='refreqid' value='<%= refReq.getId() %>' />	


<!-- Referencee Information -------------------------------------------------------------------------->	

	
<div class="panel panel-success">
  <div class="panel-heading">Referencee Information</div>
  	<div class="panel-body">
  	<div class="alert alert-danger" id="section0Error" style="display:none;">Please enter the position of the person providing reference.</div>	
  		
									<div class="table-responsive"> 
		      			 	       		<table class="table table-striped table-condensed" style="font-size:12px;">							   
										    <tbody>
										        <tr>
												    <td class="tableTitle">CANDIDATE:</td>
												    <td class="tableResult" style="text-transform:Capitalize;"><%= profile.getFullName() %></td>
											    </tr>
											    <tr>
												    <td class="tableTitle">PROVIDING REFERENCE:</td>
												    <td class="tableResult" style="text-transform:Capitalize;">
												    <%if(!(p == null)){ %>
														<input type="text" class="form-control" name="ref_provider_name" id="ref_provider_name" value="<%=p.getFullName()%>" readonly>
													<%} else{ %>
														<input type="text" class="form-control" name="ref_provider_name" id="ref_provider_name" maxlength="80">
													<%}%>
												    </td>
											    </tr>
											    <tr style="border-bottom:1px solid silver;">
												    <td class="tableTitle">POSITION:</td>
												    <td class="tableResult">
												    <input type="text" class="form-control" name="ref_provider_position" id="ref_provider_position" placeholder="Enter your Job Title/Position" maxlength="130">												    
												    </td>
											    </tr>
										    </tbody>
									    </table>
									</div>
	
	
	</div>
</div>			

<!-- Reference Questions ------------------------------------------------------------>

<div class="panel panel-success">
  <div class="panel-heading">Reference Questions</div>
  	<div class="panel-body">
  	The following reference check must be completed and attached to the teacher recommendation form.
  		
  	<div class="alert alert-danger" id="section1Error" style="display:none;">Please make sure ALL questions below are answered.</div>
  	
									<div class="table-responsive"> 
		      			 	       		<table class="table table-striped table-condensed" style="font-size:12px;padding-top:3px;border-top:1px solid silver;">							   
										    
											    <thead>
											      <tr>
												    <th class="tableQuestionNum">#</th>
												    <th class="tableQuestion">QUESTION/ANSWER</th>												    
												  </tr> 
												 </thead>
												 <tbody>
												     <tr>
													    <td>Q1.</td>
													    <td>Did the candidate ask permission to use your name as a reference? &nbsp;
													    		<input type="radio" name="Q1" value="Yes">Yes &nbsp;  
																<input type="radio" name="Q1" value="No">No
														</td>
													 </tr>
													 <tr>
													    <td>Q2.</td>
													    <td>How long have you known this teacher in a professional capacity?<br/>
													    		<input type="text" name="Q2" class="form-control" maxlength="230">
													    </td>
													 </tr>
													 <tr>
													    <td>Q3.</td>
													    <td>How long have they worked in your school?<br/>
													    <input type="text" name="Q3" class="form-control" maxlength="230">
													    </td>
													 </tr>
													 <tr>
													    <td>Q4.</td>
													    <td>What has been their assignment this year?<br/>
													    <div id="Q4_Error" class="alert alert-danger" style="display:none;">ERROR: Charater limit exceeded. You are only allowed to input 975 characters.</div>
													    <textarea name="Q4" id="Q4" class="form-control"></textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 975 - Remain: <span id="Q4_remain">975</span></div>
													    </td>
													 </tr>
													 <tr>
													    <td>Q5.</td>
													    <td>Did this teacher complete a Professional Learning Plan? &nbsp;
													    <input type="radio" name="Q5" value="Yes">Yes &nbsp; 
														<input type="radio" name="Q5" value="No">No
														</td>
													 </tr>
													 <tr>
													    <td>Q6.</td>
													    <td>Was the Learning Plan successfully followed and completed? &nbsp; 
													    <input type="radio" name="Q6" value="Yes">Yes  
														<input type="radio" name="Q6" value="No">No
														</td>
													 </tr>												 
										    	</tbody>
									    </table>
									</div>	
	</div>
</div>

<script>
$('#Q4').keypress(function(e) {
    var tval = $('#Q4').val(),
        tlength = tval.length,
        set = 975,
        remain = parseInt(set - tlength);
    $('#Q4_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#Q4_Error').css('display','block').delay(4000).fadeOut();
        $('#Q4').val((tval).substring(0, tlength - 1))
    }
})
</script>			
			
			
<!-- Domain One ------------------------------------------------------------------------------------->
													
<div class="panel panel-success">
  <div class="panel-heading">Domain 1: Planning and Preparation</div>
  	<div class="panel-body">
  	<div class="alert alert-danger" id="section2Error" style="display:none;">ERROR: Please make sure you rate each statement below.</div>	
									<div class="table-responsive">														
																	<%if(refscale.equals("4") || refscale.equals("5")){%>
																		On a scale of <%=val1%> to <%=val4%> please rate the teacher on the following statements 
																		(<%=val4%>-Proficient, <%=val3%>-Competent, <%=val2%>-Developing Competence and <%=val1%>-Needs Improvement):
	<a href="includes/rubric.pdf" target="_blank">View Scoring Rubric</a>
																	<%}else{%>
																		On a scale of <%=val1%> to <%=val5%> please rate the teacher on the following statements 
																		(<%=val5%>-Proficient, <%=val4%>-Competent, <%=val3%>-Developing Competence, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
<a href="includes/rubric.pdf" target="_blank">View Scoring Rubric</a>
																	<%}%>
															
			<table class="table table-striped table-condensed" style="margin-top:10px;font-size:12px;padding-top:3px;border-top:1px solid silver;">							   
										    
											    <thead>
											      <tr>
												    <th class="ratingQuestionNum">#</th>
												    <th class="ratingQuestion">STATEMENT</th>
												    <th class="ratingAnswer">RATING</th>
												  </tr> 
												 </thead>
												 <tbody>
												     <tr>
													    <td>S1.</td>
													    <td>This teacher demonstrates knowledge of content and pedagogy:</td>
													    <td>
													    	<input type="radio" name="Scale1" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale1" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale1" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale1" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale1" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>
													 <tr>
													    <td>S2.</td>
													    <td>This teacher demonstrates knowledge of students:</td>
													    <td>
													       	<input type="radio" name="Scale2" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale2" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale2" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale2" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale2" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>
													 <tr>
													    <td>S3.</td>
													    <td>This teacher selects instructional goals:</td>
													    <td>
													        <input type="radio" name="Scale3" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale3" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale3" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale3" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale3" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>
													 <tr>
													    <td>S4.</td>
													    <td>This teacher demonstrates knowledge of resources:</td>
													    <td>
													        <input type="radio" name="Scale4" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale4" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale4" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale4" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale4" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>
													 <tr>
													    <td>S5.</td>
													    <td>This teacher designs coherent instruction:</td>
													    <td>
													        <input type="radio" name="Scale5" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale5" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale5" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale5" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale5" value="<%=val5%>"><%=val5Text%>
															<%} %>  
														</td>
													 </tr>
													 <tr>
													    <td>S6.</td>
													    <td>This teacher designs appropriate student assessment:</td>
													    <td>
													        <input type="radio" name="Scale6" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale6" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale6" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale6" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale6" value="<%=val5%>"><%=val5Text%>
															<%} %> 
														</td>
													 </tr>
													 <tr>
													    <td colspan=3>Additional Comments <br/>
													    <div id="d1c_Error" class="alert alert-danger" style="display:none;">ERROR: Charater limit exceeded. You are only allowed to input 2450 characters.</div>
													    <textarea class="form-control" id="d1c" name="d1c"></textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2200 - Remain: <span id="d1c_remain">2200</span></div>
													    </td>
													 </tr>	
												</tbody>
									    </table>
									</div>	
	</div>
</div>	


<script>
$('#d1c').keypress(function(e) {
    var tval = $('#d1c').val(),
        tlength = tval.length,
        set = 2200,
        remain = parseInt(set - tlength);
    $('#d1c_remain').text(remain);
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#d1c_Error').css('display','block').delay(4000).fadeOut();
        $('#d1c').val((tval).substring(0, tlength - 1))
    }
})
</script>						

<!-- Domain 2 ------------------------------------------------------------------------------------->
													
<div class="panel panel-success">
  <div class="panel-heading">Domain 2: The Classroom Environment</div>
  	<div class="panel-body">
  	<div class="alert alert-danger" id="section3Error" style="display:none;">ERROR: Please make sure you rate each statement below.</div>	
									<div class="table-responsive">														
													
													
														
																	<%if(refscale.equals("4") || refscale.equals("5")){%>
																		On a scale of <%=val1%> to <%=val4%> please rate the teacher on the following statements 
																		(<%=val4%>-Proficient, <%=val3%>-Competent, <%=val2%>-Developing Competence and <%=val1%>-Needs Improvement):
<a href="includes/rubric.pdf" target="_blank">View Scoring Rubric</a>
																	<%}else{%>
																		On a scale of <%=val1%> to <%=val5%> please rate the teacher on the following statements 
																		(<%=val5%>-Proficient, <%=val4%>-Competent, <%=val3%>-Developing Competence, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
<a href="includes/rubric.pdf" target="_blank">View Scoring Rubric</a>
																	<%}%>
															
			<table class="table table-striped table-condensed" style="margin-top:10px;font-size:12px;padding-top:3px;border-top:1px solid silver;">							   
										    
											    <thead>
											      <tr>
												    <th class="ratingQuestionNum">#</th>
												    <th class="ratingQuestion">STATEMENT</th>
												    <th class="ratingAnswer">RATING</th>
												  </tr> 
												 </thead>
												 <tbody>
												     <tr>
													    <td>S7.</td>
													    <td>This teacher creates an environment of respect and rapport:</td>
													    <td>
													    	<input type="radio" name="Scale7" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale7" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale7" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale7" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale7" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>
													 <tr>
													    <td>S8.</td>
													    <td>This teacher establishes a culture for learning:</td>
													    <td>
													    	<input type="radio" name="Scale8" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale8" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale8" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale8" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale8" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>
													 <tr>
													    <td>S9.</td>
													    <td>This teacher demonstrates appropriate classroom procedures:</td>
													    <td>
													    	<input type="radio" name="Scale9" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale9" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale9" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale9" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale9" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>
													 <tr>
													    <td>S10.</td>
													    <td>This teacher manages student behavior:</td>
													    <td>
													    	<input type="radio" name="Scale10" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale10" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale10" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale10" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale10" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>
													 <tr>
													    <td>S11.</td>
													    <td>This teacher organizes physical space to meet the needs of individual learners:</td>
													    <td>
													    	<input type="radio" name="Scale11" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale11" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale11" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale11" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale11" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>	
													 <tr>
													    <td colspan=3>Additional Comments <br/>
													    <div id="d2c_Error" class="alert alert-danger" style="display:none;">ERROR: Charater limit exceeded. You are only allowed to input 2200 characters.</div>
													    <textarea class="form-control" id="d2c" name="d2c"></textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2200 - Remain: <span id="d2c_remain">2200</span></div>
													    </td>
													 </tr>		
													 										 
										    	</tbody>
									    </table>
									</div>	
	</div>
</div>	

<script>
$('#d2c').keypress(function(e) {
    var tval = $('#d2c').val(),
        tlength = tval.length,
        set = 2200,
        remain = parseInt(set - tlength);
    $('#d2c_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#d2c_Error').css('display','block').delay(4000).fadeOut();
        $('#d2c').val((tval).substring(0, tlength - 1))
    }
})
</script>															
			
<!-- Domain 3 ------------------------------------------------------------------------------------->
													
<div class="panel panel-success">
  <div class="panel-heading">Domain 3: Instruction</div>
  	<div class="panel-body">
  	<div class="alert alert-danger" id="section4Error" style="display:none;">ERROR: Please make sure you rate each statement below.</div>	
									<div class="table-responsive">														
													
													
														
			<%if(refscale.equals("4") || refscale.equals("5")){%>
																		On a scale of <%=val1%> to <%=val4%> please rate the teacher on the following statements 
																		(<%=val4%>-Proficient, <%=val3%>-Competent, <%=val2%>-Developing Competence and <%=val1%>-Needs Improvement):
<a href="includes/rubric.pdf" target="_blank">View Scoring Rubric</a>
																	<%}else{%>
																		On a scale of <%=val1%> to <%=val5%> please rate the teacher on the following statements 
																		(<%=val5%>-Proficient, <%=val4%>-Competent, <%=val3%>-Developing Competence, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
<a href="includes/rubric.pdf" target="_blank">View Scoring Rubric</a>
																	<%}%>
															
			<table class="table table-striped table-condensed" style="margin-top:10px;font-size:12px;padding-top:3px;border-top:1px solid silver;">							   
										    
											    <thead>
											      <tr>
												    <th class="ratingQuestionNum">#</th>
												    <th class="ratingQuestion">STATEMENT</th>
												    <th class="ratingAnswer">RATING</th>
												  </tr> 
												 </thead>
												 <tbody>
												     <tr>
													    <td>S12.</td>
													    <td>This teacher communicates clearly and accurately:</td>
													    <td>
													        <input type="radio" name="Scale12" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale12" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale12" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale12" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale12" value="<%=val5%>"><%=val5Text%>
															<%} %> 
														</td>
													 </tr>
													 <tr>
													    <td>S13.</td>
													    <td>This teacher uses questioning and discussion techniques:</td>
													    <td>
													    	<input type="radio" name="Scale13" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale13" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale13" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale13" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale13" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>
													 <tr>
													    <td>S14.</td>
													    <td>This teacher engages students in learning:</td>
													    <td>
													    	<input type="radio" name="Scale14" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale14" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale14" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale14" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale14" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>
													 <tr>
													    <td>S15.</td>
													    <td>This teacher demonstrates and utilizes appropriate formative assessment strategies:</td>
													    <td>
													    	<input type="radio" name="Scale15" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale15" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale15" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale15" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale15" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>
													 <tr>
													    <td>S16.</td>
													    <td>This teacher demonstrates flexibility and responsiveness:</td>
													    <td>
													    	<input type="radio" name="Scale16" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale16" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale16" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale16" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale16" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>												
													 <tr>
													    <td colspan=3>Additional Comments <br/>
													    <div id="d3c_Error" class="alert alert-danger" style="display:none;">ERROR: Charater limit exceeded. You are only allowed to input 2200 characters.</div>
													    <textarea class="form-control" id="d3c" name="d3c"></textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2200 - Remain: <span id="d3c_remain">2200</span></div>
													    </td>
													 </tr>		
													 										 
										    	</tbody>
									    </table>
									</div>	
	</div>
</div>	

<script>
$('#d3c').keypress(function(e) {
    var tval = $('#d3c').val(),
        tlength = tval.length,
        set = 2200,
        remain = parseInt(set - tlength);
    $('#d3c_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#d3c_Error').css('display','block').delay(4000).fadeOut();
        $('#d3c').val((tval).substring(0, tlength - 1))
    }
})
</script>					
			
<!-- Domain 4 ------------------------------------------------------------------------------------->
													
<div class="panel panel-success">
  <div class="panel-heading">Domain 4: Professional Responsibilities</div>
  	<div class="panel-body">
  	<div class="alert alert-danger" id="section5Error" style="display:none;">ERROR: Please make sure you rate each statement below.</div>	
									<div class="table-responsive">														
													
													
														
																	<%if(refscale.equals("4") || refscale.equals("5")){%>
																		On a scale of <%=val1%> to <%=val4%> please rate the teacher on the following statements 
																		(<%=val4%>-Proficient, <%=val3%>-Competent, <%=val2%>-Developing Competence and <%=val1%>-Needs Improvement):
<a href="includes/rubric.pdf" target="_blank">View Scoring Rubric</a>
																	<%}else{%>
																		On a scale of <%=val1%> to <%=val5%> please rate the teacher on the following statements 
																		(<%=val5%>-Proficient, <%=val4%>-Competent, <%=val3%>-Developing Competence, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
<a href="includes/rubric.pdf" target="_blank">View Scoring Rubric</a>
																	<%}%>
															
			<table class="table table-striped table-condensed" style="margin-top:10px;font-size:12px;padding-top:3px;border-top:1px solid silver;">							   
										    
											    <thead>
											      <tr>
												    <th class="ratingQuestionNum">#</th>
												    <th class="ratingQuestion">STATEMENT</th>
												    <th class="ratingAnswer">RATING</th>
												  </tr> 
												 </thead>
												 <tbody>
												     <tr>
													    <td>S17.</td>
													    <td>This teacher reflects on teaching:</td>
													    <td>
													       	<input type="radio" name="Scale17" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale17" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale17" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale17" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale17" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>
													 <tr>
													    <td>S18.</td>
													    <td>This teacher maintains accurate records:</td>
													    <td>
													       	<input type="radio" name="Scale18" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale18" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale18" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale18" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale18" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>
													 <tr>
													    <td>S19.</td>
													    <td>This teacher communicates with families and/or other community stakeholders:</td>
													    <td>
													       	<input type="radio" name="Scale19" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale19" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale19" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale19" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale19" value="<%=val5%>"><%=val5Text%>
															<%} %> 
														</td>
													 </tr>
													 <tr>
													    <td>S20.</td>
													    <td>This teacher contributes to the school and district:</td>
													    <td>
													       	<input type="radio" name="Scale20" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale20" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale20" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale20" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale20" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>
													 <tr>
													    <td>S21.</td>
													    <td>This teacher grows and develops professionally:</td>
													    <td>
													       	<input type="radio" name="Scale21" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale21" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale21" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale21" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale21" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>
													 <tr>
													    <td>S22.</td>
													    <td>This teacher shows professionalism:</td>
													    <td>
													       	<input type="radio" name="Scale22" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale22" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale22" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale22" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){ %>
																<input type="radio" name="Scale22" value="<%=val5%>"><%=val5Text%>
															<%} %>
														</td>
													 </tr>										
													 <tr>
													    <td colspan=3>Additional Comments <br/>
													    <div id="d4c_Error" class="alert alert-danger" style="display:none;">ERROR: Charater limit exceeded. You are only allowed to input 2200 characters.</div>
													    <textarea class="form-control" id="d4c" name="d4c"></textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2200 - Remain: <span id="d4c_remain">2200</span></div>
													    </td>
													 </tr>		
													 										 
										    	</tbody>
									    </table>
									</div>	
	</div>
</div>	

<script>
$('#d4c').keypress(function(e) {
    var tval = $('#d4c').val(),
        tlength = tval.length,
        set = 2200,
        remain = parseInt(set - tlength);
    $('#d4c_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#d4c_Error').css('display','block').delay(4000).fadeOut();
        $('#d4c').val((tval).substring(0, tlength - 1))
    }
})
</script>											
				
<!-- Other Information ----------------------------------------------------------------------------------->		
													
<div class="panel panel-success">
  <div class="panel-heading">Other Information</div>
  	<div class="panel-body">	
  	<div class="alert alert-danger" id="section6Error" style="display:none;">Please make sure Q7 is answered below.</div>
									<div class="table-responsive">
									
									
									 
		      			 	       		<table class="table table-striped table-condensed" style="margin-top:10px;font-size:12px;padding-top:3px;border-top:1px solid silver;">							   
										    
											    <thead>
											      <tr>
												    <th class="tableQuestionNum">#</th>
												    <th class="tableQuestion">QUESTION/ANSWER</th>
												  </tr> 
												 </thead>
												 <tbody>
												     <tr>
													   	<td>Q7.</td>
													    <td>If given the opportunity would you hire this candidate?<br>
															<select name="Q7" class="form-control">																
																<option value="-1">--- Select One ---</option>
																<option value="Recommend for permanent position" >Recommend for permanent contract.</option>
																<option value="Recommend for full year replacement">Recommend for replacement contract.</option>
																<option value="Do not recommend this candidate">I do not recommend this candidate.</option>
																</select>
														</td>
													 </tr>
													 <tr>
													   	<td colspan=2>
													   	Additional Comments:
															<div id="Q7_Comment_Error" class="alert alert-danger" style="display:none;">ERROR: Charater limit exceeded. You are only allowed to input 2200 characters.</div>
															<textarea class="form-control" id="Q7_Comment" name="Q7_Comment">${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q7Comment ne null ? REFERENCE_BEAN.q7Comment : "" }</textarea>
															<div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2200 - Remain: <span id="Q7_Comment_remain">2200</span></div>
													   	</td>													    
													 </tr>
												</tbody>
										</table>
										
									</div>
	</div>
</div>	
											
<script>													
$('#Q7_Comment').keypress(function(e) {
    var tval = $('#Q7_Comment').val(),
        tlength = tval.length,
        set = 2200,
        remain = parseInt(set - tlength);
    $('#Q7_Comment_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#Q7_Comment_Error').css('display','block').delay(4000).fadeOut();
        $('#Q7_Comment').val((tval).substring(0, tlength - 1))
    }
})
</script>												

												
<div align="center">					
<input id="btnSubmit" class="btn btn-primary btn-xs" type="submit" value="Submit Reference" > &nbsp; 
<INPUT class="btn btn-danger btn-xs" TYPE="RESET" VALUE="Reset Form">
</div>					
																																																				
	
	</form>
			                             
</body>
</html>
