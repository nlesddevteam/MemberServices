<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*" 
         isThreadSafe="false"%>
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		
			<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
			<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%
	ReferenceCheckRequestBean refReq = (ReferenceCheckRequestBean) request.getAttribute("REFERENCE_CHECK_REQUEST_BEAN");
  	JobOpportunityBean job = (JobOpportunityBean) request.getAttribute("JOB");
  	JobOpportunityAssignmentBean[] ass = (JobOpportunityAssignmentBean[]) request.getAttribute("JOB_ASSIGNMENTS");
  	AdRequestBean ad = (AdRequestBean) request.getAttribute("AD_REQUEST_BEAN");
  	ApplicantProfileBean profile = (ApplicantProfileBean) request.getAttribute("PROFILE");
  	String val1="0";
	String val2="1";
	String val3="2";
	String val4="3";
	if(!(refReq == null)){
		if(refReq.getReferenceScale() == "4"){
			val1="1";
			val2="2";
			val3="3";
			val4="4";
		}
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
					if($('#ref_provider_position').val() == ''){
						is_valid = false;						
						$('#section0Error').css('display','block').delay(5000).fadeOut();
						$('#ref_provider_position').focus();
					}
					else if(!$("input[name='Q1']:checked").val() || $("input[name='Q2']").val() == '' || $("input[name='Q3']").val() == '' || 
							$("textarea[name='Q4']").val() == '' || !$("input[name='Q5']:checked").val()) {
						is_valid = false;
						$('#section1Error').css('display','block').delay(5000).fadeOut();
						$('#Q1').focus();				
										
					}
					else if(!$("input[name='Scale1']:checked").val() || !$("input[name='Scale2']:checked").val() || !$("input[name='Scale3']:checked").val() || 
							!$("input[name='Scale4']:checked").val() || !$("input[name='Scale5']:checked").val() || !$("input[name='Scale6']:checked").val() || 
							!$("input[name='Scale7']:checked").val() || !$("input[name='Scale8']:checked").val() || !$("input[name='Scale9']:checked").val()) {
						is_valid = false;
						$('#section2Error').css('display','block').delay(5000).fadeOut();
						$('#S1').focus();
					}					
					else if($("select[name='Q6']").val() == -1) {
						is_valid = false;
						$('#section3Error').css('display','block').delay(5000).fadeOut;
						$("select[name='Q6']").focus();
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
<div class="pageHeader">Guidance Counsellor Candidate <%= profile.getFullNameReverse() %> Reference</div>


			                            <%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){ %>
			                            	<div class="alert alert-danger" style="text-align:center;"><%=(String)request.getAttribute("msg")%></div>
			                            <%} %>
			                            
                        <form action="addNLESDExternalGuideReference.html" method="POST" name="admin_nlesd_rec_form" id="admin_nlesd_rec_form">
                        	<input type='hidden' id='request_id' name='request_id' value='<%= refReq.getRequestId() %>' />
                        	<input type='hidden' id='applicant_id' name='applicant_id' value='<%= profile.getUID() %>' />
                        	<input type='hidden' id='confirm' name='confirm' value='true' />
 <!-- Candidate Information -------------------------------------------------------------------------->	

	
<div class="panel panel-success">
  <div class="panel-heading">Candidate Information</div>
  	<div class="panel-body">
  		
									<div class="table-responsive"> 
		      			 	       		<table class="table table-striped table-condensed" style="font-size:12px;">							   
										    <tbody>
										        <tr>
												    <td class="tableTitle">CANDIDATE:</td>
												    <td class="tableResult" style="text-transform:Capitalize;"><%= profile.getFullName() %></td>
											    </tr>
											    <tr>
												    <td class="tableTitle">REGION:</td>
												    <td class="tableResult"><%= ass[0].getRegionText() %></td>
											    </tr>
											    <tr>
												    <td class="tableTitle">POSITION:</td>
												    <td class="tableResult"><%= job.getPositionTitle() %></td>
											    </tr>
											    <tr>
												    <td class="tableTitle">LOCATION:</td>
												    <td class="tableResult"><%=(ass[0].getLocation() > 0)? ass[0].getLocationText():"N/A"%></td>
											    </tr>
											    <tr style="border-bottom:1px solid silver;">
												    <td class="tableTitle">COMP. #:</td>
												    <td class="tableResult"><%= job.getCompetitionNumber() %></td>
											    </tr>
										    </tbody>
									    </table>
									</div>
	
	
	</div>
</div>			                                    
					
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
												    <td class="tableTitle">REQUESTED BY:</td>
												    <td class="tableResult" style="text-transform:Capitalize;">
												    <%=refReq.getCheckRequester().getFullName() %>
												    </td>
											    </tr>
											    <tr>
												    <td class="tableTitle">PROVIDING REFERENCE:</td>
												    <td class="tableResult" style="text-transform:Capitalize;">
												    <input type="text" name="ref_provider_name" id="ref_provider_name" class="form-control">
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
					

<div class="panel panel-success">
  <div class="panel-heading">Reference Questions</div>
  	<div class="panel-body">	
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
														<input type="radio" name="Q1" value="Yes">Yes  &nbsp; 
														<input type="radio" name="Q1" value="No">No
														</td>
													 </tr>
													 <tr>
													    <td>Q2.</td>
													    <td>How long have you known this candidate in a professional capacity?
														<input type="text" name="Q2" class="form-control input-sm">
														</td>
													 </tr>
													 <tr>
													    <td>Q3.</td>
													    <td>How long has he/she worked in your school?
														<input type="text" name="Q3" class="form-control input-sm">
														</td>
													 </tr>
													 <tr>
													    <td>Q4.</td>
													    <td>What has been his/her assignment this year?
													    <div id="Q4_Error" class="alert alert-danger" style="display:none;">ERROR: Charater limit exceeded. You are only allowed to input 2450 characters.</div>
														<textarea name="Q4"  id="Q4" class="form-control"></textarea>
														<div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2450 - Remain: <span id="Q4_remain">2450</span></div>
														</td>
													</tr>
													 <tr>
													    <td>Q5.</td>
													    <td>Was the learning plan successfully followed and completed? &nbsp;
														<input type="radio" name="Q5" value="Yes">Yes &nbsp; 
														<input type="radio" name="Q5" value="No">No
														</td>
													 </tr>
												</tbody>
										</table>
	</div>	
	</div>
</div>			
	
<div class="panel panel-success">
  <div class="panel-heading">Ratings</div>
  	<div class="panel-body">
  	<div class="alert alert-danger" id="section2Error" style="display:none;">ERROR: Please make sure you rate each statement below.</div>	
									<div class="table-responsive">
									On a scale of <%=val1%> to <%=val4%> please rate on the following statements 
																(<%=val4%>-Proficient, <%=val3%>-Competent, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
									
									 
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
													    <td>Standard 1: Comprehensive Guidance Program<br/>
													    	The guidance counsellor collaborates and develops an annual comprehensive school guidance program which outlines the implementation of interventions that promote the holistic development of the student:
														</td>
													    <td>
														    <input type="radio" name="Scale1" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale1" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale1" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale1" value="<%=val4%>"><%=val4%>
														</td>
													 </tr>													
							                         <tr>
													    <td>S2.</td>
													    <td>Standard 2: Education System<br/>
															The guidance counsellor understands the overall education system and engages in the planning and managing of tasks to support the learning and development of students:
														</td>
													    <td><input type="radio" name="Scale2" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale2" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale2" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale2" value="<%=val4%>"><%=val4%>
														</td>
													 </tr>
													 <tr>
													    <td>S3.</td>
													    <td>Standard 3: Student Development<br/>
															The guidance counsellor understands the diversity of human growth, development , behavior and learning and promotes the holistic development of the student:
														</td>
													    <td><input type="radio" name="Scale3" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale3" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale3" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale3" value="<%=val4%>"><%=val4%>
														</td>
													 </tr>
													 <tr>
													    <td>S4.</td>
													    <td>Standard 4: Diversity<br/>
													    	The guidance counsellor understands the dimensions of human diversity and the possible influence they many have on child/adolescent development:
													    </td>
													    <td><input type="radio" name="Scale4" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale4" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale4" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale4" value="<%=val4%>"><%=val4%>
														</td>
													 </tr>
													 <tr>
													    <td>S5.</td>
													    <td>Standard 5: Comprehensive Assessment<br/>
															The guidance counsellor understands the assessment process and its implications for student learning:</td>
													    <td><input type="radio" name="Scale5" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale5" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale5" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale5" value="<%=val4%>"><%=val4%>
														</td>
													 </tr>
													 <tr>
													    <td>S6.</td>
													    <td>Standard 6: Counselling<br/>
															The guidance counsellor possesses knowledge and skills necessary to establish and facilitate individual and group counselling:</td>
													    <td><input type="radio" name="Scale6" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale6" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale6" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale6" value="<%=val4%>"><%=val4%>
														</td>
													 </tr>
													 <tr>
													    <td>S7.</td>
													    <td>Standard 7: Career Development<br/>
															The guidance counsellor understands that career development is a lifelong process. He/she develops programs and interventions to promote the career development of all students:</td>
													    <td><input type="radio" name="Scale7" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale7" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale7" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale7" value="<%=val4%>"><%=val4%>
														</td>
													 </tr>
													 <tr>
													    <td>S8.</td>
													    <td>Standard 8: Crisis Intervention<br/>
															The guidance counsellor participates in the development and  implementation of a response plan for possible crisis situations:</td>
													    <td><input type="radio" name="Scale8" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale8" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale8" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale8" value="<%=val4%>"><%=val4%>
													    </td>
													 </tr>
													 <tr>
													    <td>S9.</td>
													    <td>Standard 9: Ethical Responsibilities<br/>
															The guidance counsellor understands the ethical requirements in providing a comprehensive school guidance program:</td>
													    <td><input type="radio" name="Scale9" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale9" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale9" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale9" value="<%=val4%>"><%=val4%>
														</td>
													 </tr>													 
							                       </tbody>
							                       </table>
							            </div>
	</div>
</div>
								
	
															
					
<div class="panel panel-success">
  <div class="panel-heading">Other Information</div>
  	<div class="panel-body">	
  	<div class="alert alert-danger" id="section3Error" style="display:none;">Please make sure Q6 is answered below.</div>
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
													   	<td>Q6.</td>
													    <td>If given the opportunity would you hire this candidate?<br>
															<select name="Q6" class="form-control">
																<option value="-1">--- Select One ---</option>
																<option value="Category 1: Recommend for permanent position">Category 1: Recommend for permanent position.</option>
																<option value="Category 2: Recommend for full year replacement">Category 2: Recommend for full year replacement.</option>
																<option value="Category 3: Do not recommend">Category 3: Do not recommend.</option>
															</select></td>
													 </tr>
													 <tr>
													   	<td colspan=2>
													   	Additional Comments:
								<div id="Q6_Comment_Error" class="alert alert-danger" style="display:none;">ERROR: Charater limit exceeded. You are only allowed to input 2450 characters.</div>
								<textarea class="form-control" id="Q6_Comment" name="Q6_Comment"></textarea>
								<div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2450 - Remain: <span id="Q6_Comment_remain">2450</span></div>
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
        set = 2450,
        remain = parseInt(set - tlength);
    $('#Q4_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#Q4_Error').css('display','block').delay(4000).fadeOut();
        $('#Q4').val((tval).substring(0, tlength - 1))
    }
})

$('#Q6_Comment').keypress(function(e) {
    var tval = $('#Q6_Comment').val(),
        tlength = tval.length,
        set = 2450,
        remain = parseInt(set - tlength);
    $('#Q6_Comment_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#Q6_Comment_Error').css('display','block').delay(4000).fadeOut();
        $('#Q6_Comment').val((tval).substring(0, tlength - 1))
    }
})
</script>
					
					
<div align="center">					
<input id="btnSubmit" class="btn btn-primary btn-xs" type="submit" value="Submit Reference" > &nbsp; 
<INPUT class="btn btn-danger btn-xs" TYPE="RESET" VALUE="Reset Form">
</div>
</div>
											</form>
			                              
</body>
</html>
