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
<%

	//ReferenceCheckRequestBean refReq = (ReferenceCheckRequestBean) request.getAttribute("REFERENCE_CHECK_REQUEST_BEAN");
	//JobOpportunityBean job = (JobOpportunityBean) request.getAttribute("JOB");
	//JobOpportunityAssignmentBean[] ass = (JobOpportunityAssignmentBean[]) request.getAttribute("JOB_ASSIGNMENTS");
	//AdRequestBean ad = (AdRequestBean) request.getAttribute("AD_REQUEST_BEAN");
	ApplicantRefRequestBean refReq = (ApplicantRefRequestBean) request.getAttribute("REFREQUEST");
	ApplicantProfileBean profile = (ApplicantProfileBean) request.getAttribute("PROFILE");
  	String val1="0";
	String val2="1";
	String val3="2";
	String val4="3";
	String val5="4";
	String refscale="4";
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
					if($('#ref_provider_position').val() == ''){
						is_valid = false;						
						$('#section0Error').css('display','block').delay(5000).fadeOut();
						$('#ref_provider_position').focus();
					}
					else if(!$("input[name='Q1']:checked").val() || $("input[name='Q2']").val() == '' || $("input[name='Q3']").val() == '' || $("textarea[name='Q4']").val() == '' || !$("input[name='Q5']:checked").val() ) {
						is_valid = false;
						$('#section1Error').css('display','block').delay(5000).fadeOut();
						$('#Q1').focus();					
										
					}
					else if(!$("input[name='Scale1']:checked").val() || !$("input[name='Scale2']:checked").val() || !$("input[name='Scale3']:checked").val() || !$("input[name='Scale4']:checked").val() || !$("input[name='Scale5']:checked").val() || !$("input[name='Scale6']:checked").val() ) {
						is_valid = false;
						$('#section2Error').css('display','block').delay(5000).fadeOut();
						$('#S1').focus();	
					}
					
					else if(!$("input[name='Scale7']:checked").val() || !$("input[name='Scale8']:checked").val() || !$("input[name='Scale9']:checked").val() || !$("input[name='Scale10']:checked").val() || !$("input[name='Scale11']:checked").val()) {
						is_valid = false;
						$('#section3Error').css('display','block').delay(5000).fadeOut();
						$('#S7').focus();
					}
					
					else if(!$("input[name='Scale12']:checked").val() || !$("input[name='Scale13']:checked").val() || !$("input[name='Scale14']:checked").val() || !$("input[name='Scale15']:checked").val() || !$("input[name='Scale16']:checked").val()   ) {
						is_valid = false;
						$('#section4Error').css('display','block').delay(5000).fadeOut();
						$('#S12').focus();
					}
							
					else if($("select[name='Q6']").val() == -1) {
						is_valid = false;
						$('#section5Error').css('display','block').delay(5000).fadeOut;
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
	
	<div class="pageHeader">Administrator Candidate <%= profile.getFullNameReverse() %> Reference Check</div>
	
	<form action="addNLESDExternalAdminReferenceApp.html" method="POST" name="admin_nlesd_rec_form" id="admin_nlesd_rec_form">
	<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading">Reference Information</div>
      			 	<div class="panel-body"> 
	                    		<div id="checkMsgError" class="alert alert-danger" style="text-align:center;display:none;"></div>								
								<div id="checkMsgSuccess" class="alert alert-success" style="text-align:center;display:none;"></div>
	
<%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){ %>
                  	<div class="alert alert-danger" style="text-align:center;"><%=(String)request.getAttribute("msg")%></div>
<%} %>
			                            
			                            
			     
			                                	<input type='hidden' id='applicant_id' name='applicant_id' value='<%=(profile.getUID()!=null)?profile.getUID():"" %>' />
			                                	<input type='hidden' id='confirm' name='confirm' value='true' />
			                                    <input type='hidden' id='reqreqid' name='refreqid' value='<%= refReq.getId() %>' />		
								<div class="table-responsive">      			 	       
      			 	       		<table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>								
								<tr>
									    <td class="tableTitle">Candidates Name::</td>
									    <td class="tableResult"><%= profile.getFullName() %></td>
							    </tr>
								<tr>
									    <td class="tableTitle">Person providing reference:</td>
									    <td class="tableResult">
										    <%if(!(p == null)){ %>
												<input type="text" name="ref_provider_name" id="ref_provider_name" class="form-control" value="<%=p.getFullName()%>" readonly>
											<%} else{ %>
												<input type="text" name="ref_provider_name" id="ref_provider_name" class="form-control">
											<%}%>
										</td>
							    </tr>
								<tr>
									    <td class="tableTitle">Position:</td>
									    <td class="tableResult"><input type="text" name="ref_provider_position" id="ref_provider_position" class="form-control"></td>
							    </tr>
							    </tbody>
							    </table>	
								</div>					
				</div></div></div>														

<!-- QUESTIONS ------------------------------------------------------------------>
			
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
													    <input type="radio" name="Q1" value="Yes"/>Yes &nbsp;<input type="radio" name="Q1" value="No"/>No
													    </td>
													 </tr>
													 <tr>
													    <td>Q2.</td>
													    <td>How long have you known this candidate in a professional capacity?
													    <input type="text" name="Q2" placeholder="Enter Answer" class="form-control input-sm"/>
													    </td>
													    
													 </tr> 
													 <tr>
													    <td>Q3.</td>
													    <td>How long has he/she worked in your school?
													    <input type="text" name="Q3" placeholder="Enter Answer" class="form-control input-sm"/>
													    </td>													    
													 </tr> 
													 <tr>
													    <td>Q4.</td>
													    <td>What has been his/her assignment this year?
															<textarea name="Q4" class="form-control"></textarea>
														</td>													    
													 </tr>
													 <tr>
													    <td>Q5.</td>
													    <td>Was the learning plan successfully followed and completed? &nbsp; 
														    <input type="radio" name="Q5" value="Yes"/>Yes &nbsp;<input type="radio" name="Q5" value="No"/>No
														</td>													    
													 </tr>													 
										    	</tbody>
									    </table>
									</div>	
	</div>
</div>		

<!-- LEADERSHIP ------------------------------------------------------------------>

															
<div class="panel panel-success">
  <div class="panel-heading">Leadership Ratings</div>
  	<div class="panel-body">
  	<div class="alert alert-danger" id="section2Error" style="display:none;">ERROR: Please make sure you rate each statement below.</div>	
									<div class="table-responsive">
									<%if(refscale.equals("4")){%>
										On a scale of <%=val1%> to <%=val4%> please rate the teacher on the following statements 
										(<%=val4%>-Proficient, <%=val3%>-Competent, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
									<%}else{%>
										On a scale of <%=val1%> to <%=val5%> please rate the teacher on the following statements 
										(<%=val5%>-Proficient, <%=val4%>-Competent, <%=val3%>-Developing Competence, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
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
													    <td>Communicates a clear vision focused on student achievement:</td>
													    <td><input type="radio" name="Scale1" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale1" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale1" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale1" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale1" value="<%=val5%>"><%=val5%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td>S2.</td>
													    <td>Establishes a culture for learning and models organizational value:</td>
													    <td><input type="radio" name="Scale2" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale2" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale2" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale2" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale2" value="<%=val5%>"><%=val5%>
															<%}%>
														</td>
													 </tr>
													 <tr>
													    <td>S3.</td>
													    <td>Demonstrates knowledge of curriculum outcomes and provides support and feedback to teachers:</td>
													    <td><input type="radio" name="Scale3" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale3" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale3" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale3" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale3" value="<%=val5%>"><%=val5%>
															<%}%>
														</td>
													 </tr>
													 <tr>
													    <td>S4.</td>
													    <td>Observes instruction and provides feedback to teachers in order to improve learning and teaching in the school while encouraging and supporting teacher leadership:</td>
													    <td><input type="radio" name="Scale4" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale4" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale4" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale4" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale4" value="<%=val5%>"><%=val5%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td>S5.</td>
													    <td>Fosters a culture of respect:</td>
													    <td><input type="radio" name="Scale5" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale5" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale5" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale5" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale5" value="<%=val5%>"><%=val5%>
															<%}%> </td>
													 </tr>
													 <tr>
													    <td>S6.</td>
													    <td>Uses the School Development Plan as the strategic guide for decision making and staff development:</td>
													    <td><input type="radio" name="Scale6" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale6" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale6" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale6" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale6" value="<%=val5%>"><%=val5%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td colspan=3>Additional Comments <br/>
													    <div id="d1c_Error" class="alert alert-danger" style="display:none;">ERROR: Charater limit exceeded. You are only allowed to input 2450 characters.</div>
													    <textarea class="form-control" id="d1c" name="d1c">${ REFERENCE_BEAN ne null ? REFERENCE_BEAN.domain1Comments : "" }</textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2450 - Remain: <span id="d1c_remain">2450</span></div>
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
        set = 2450,
        remain = parseInt(set - tlength);
    $('#d1c_remain').text(remain);
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#d1c_Error').css('display','block').delay(4000).fadeOut();
        $('#d1c').val((tval).substring(0, tlength - 1))
    }
})
</script>

<!-- MANAGEMENT ------------------------------------------------------------------>																	

<div class="panel panel-success">
  <div class="panel-heading">Management Ratings</div>
  	<div class="panel-body">	
  	<div class="alert alert-danger" id="section3Error" style="display:none;">ERROR: Please make sure you rate each statement below.</div>		
									<div class="table-responsive">
									<%if(refscale.equals("4")){%>
										On a scale of <%=val1%> to <%=val4%> please rate the teacher on the following statements 
										(<%=val4%>-Proficient, <%=val3%>-Competent, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
									<%}else{%>
										On a scale of <%=val1%> to <%=val5%> please rate the teacher on the following statements 
										(<%=val5%>-Proficient, <%=val4%>-Competent, <%=val3%>-Developing Competence, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
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
													    <td>Communicates and maintains appropriate behavioral standards:</td>
													    <td><input type="radio" name="Scale7" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale7" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale7" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale7" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale7" value="<%=val5%>"><%=val5%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td>S8.</td>
													    <td>Ensures that staff have necessary resources and keeps them organized and on task:</td>
													    <td><input type="radio" name="Scale8" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale8" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale8" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale8" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale8" value="<%=val5%>"><%=val5%>
															<%}%>
														</td>
													 </tr>
													 <tr>
													    <td>S9.</td>
													    <td>Manages school procedures and operations based on District policies in consultation with the District, if necessary:</td>
													    <td><input type="radio" name="Scale9" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale9" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale9" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale9" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale9" value="<%=val5%>"><%=val5%>
															<%}%>
														</td>
													 </tr>
													 <tr>
													    <td>S10.</td>
													    <td>Models time and self-management:</td>
													    <td><input type="radio" name="Scale10" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale10" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale10" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale10" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale10" value="<%=val5%>"><%=val5%>
															<%}%>
														</td>
													 </tr>
													 <tr>
													    <td>S11.</td>
													    <td>Organizes and develops the physical plant to ensure student learning:</td>
													    <td><input type="radio" name="Scale11" value="<%=val1%>"><%=val1%>  
															<input type="radio" name="Scale11" value="<%=val2%>"><%=val2%> 
															<input type="radio" name="Scale11" value="<%=val3%>"><%=val3%>  
															<input type="radio" name="Scale11" value="<%=val4%>"><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale11" value="<%=val5%>"><%=val5%>
															<%}%> 
														</td>
													 </tr>
													<tr>
													    <td colspan=3>Additional Comments <br/>
													    <div id="d2c_Error" class="alert alert-danger" style="display:none;">ERROR: Charater limit exceeded. You are only allowed to input 2450 characters.</div>
													    <textarea class="form-control" id="d2c" name="d2c">${ REFERENCE_BEAN ne null ? REFERENCE_BEAN.domain2Comments : "" }</textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2450 - Remain: <span id="d2c_remain">2450</span></div>
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
        set = 2450,
        remain = parseInt(set - tlength);
    $('#d2c_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#d2c_Error').css('display','block').delay(4000).fadeOut();
        $('#d2c').val((tval).substring(0, tlength - 1))
    }
})
</script>
																
<!-- COMMUNICATIONS ------------------------------------------------------------------>																

<div class="panel panel-success">
  <div class="panel-heading">Communication and Interpersonal Relations Ratings</div>
  	<div class="panel-body">	
  	<div class="alert alert-danger" id="section4Error" style="display:none;">ERROR: Please make sure you rate each statement below.</div>
  	
									<div class="table-responsive">
									<%if(refscale.equals("4")){%>
										On a scale of <%=val1%> to <%=val4%> please rate the teacher on the following statements 
										(<%=val4%>-Proficient, <%=val3%>-Competent, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
									<%}else{%>
										On a scale of <%=val1%> to <%=val5%> please rate the teacher on the following statements 
										(<%=val5%>-Proficient, <%=val4%>-Competent, <%=val3%>-Developing Competence, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
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
													    <td>Communicates and responds clearly and effectively (both oral and written):</td>
													    <td><input type="radio" name="Scale12" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale12 eq val1 ? "CHECKED" : "" } ><%=val1%>  
															<input type="radio" name="Scale12" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale12 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale12" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale12 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale12" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale12 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale12" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale12 eq val5 ? "CHECKED" : "" } ><%=val5%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td>S13.</td>
													    <td>Engages all stakeholders in the school community with a focus on student learning:</td>
													    <td><input type="radio" name="Scale13" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale13 eq val1 ? "CHECKED" : "" } ><%=val1%>  
															<input type="radio" name="Scale13" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale13 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale13" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale13 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale13" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale13 eq val4 ? "CHECKED" : "" } ><%=val4%> 
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale13" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale13 eq val5 ? "CHECKED" : "" } ><%=val5%>
															<%}%>
														</td>
													 </tr>
													 <tr>
													    <td>S14.</td>
													    <td>Displays empathy and demonstrated ability to listen and solve problems proactively:</td>
													    <td><input type="radio" name="Scale14" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale14 eq val1 ? "CHECKED" : "" } ><%=val1%>  
															<input type="radio" name="Scale14" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale14 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale14" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale14 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale14" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale14 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale14" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale14 eq val5 ? "CHECKED" : "" } ><%=val5%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td>S15.</td>
													    <td>Manages conflict effectively and respectfully:</td>
													    <td><input type="radio" name="Scale15" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale15 eq val1 ? "CHECKED" : "" } ><%=val1%>  
															<input type="radio" name="Scale15" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale15 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale15" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale15 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale15" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale15 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale15" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale15 eq val5 ? "CHECKED" : "" } ><%=val5%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td>S16.</td>
													    <td>Demonstrates the ability to build and develop teams and facilitate group collaboration:</td>
													    <td><input type="radio" name="Scale16" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale16 eq val1 ? "CHECKED" : "" } ><%=val1%>  
															<input type="radio" name="Scale16" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale16 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale16" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale16 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale16" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale16 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale16" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale16 eq val5 ? "CHECKED" : "" } ><%=val5%>
															<%}%>  
														</td>
													 </tr>
													 <tr>
													    <td colspan=3>Additional Comments <br/>
													    <div id="d3c_Error" class="alert alert-danger" style="display:none;">ERROR: Charater limit exceeded. You are only allowed to input 2450 characters.</div>
													    <textarea class="form-control" id="d3c" name="d3c">${ REFERENCE_BEAN ne null ? REFERENCE_BEAN.domain3Comments : "" }</textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2450 - Remain: <span id="d3c_remain">2450</span></div>
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
        set = 2450,
        remain = parseInt(set - tlength);
    $('#d3c_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#d3c_Error').css('display','block').delay(4000).fadeOut();
        $('#d3c').val((tval).substring(0, tlength - 1))
    }
})
</script>		
													
<!-- OTHER INFO ------------------------------------------------------------------>										
													
<div class="panel panel-success">
  <div class="panel-heading">Other Information</div>
  	<div class="panel-body">	
  	<div class="alert alert-danger" id="section5Error" style="display:none;">Please make sure Q6 is answered below.</div>
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
													    <td>If given the opportunity would you hire this candidate?<br/>
													    <select name="Q6" class="form-control input-sm">
																<option value="-1">--- Select One ---</option>
																<option value="Recommend for Hire" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q6 eq 'Recommend for Hire' ? "SELECTED" : "" } >Recommend for Hire</option>
																<option value="Do Not Recommend for Hire" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q6 eq 'Do Not Recommend for Hire' ? "SELECTED" : "" } >Do Recommend Not Hire</option>
															</select>
															</td>
													 </tr>													 
													 <tr>
													    <td colspan=2>Additional Comments <br/>
													    <div id="Q6_Comment_Error" class="alert alert-danger" style="display:none;">ERROR: Charater limit exceeded. You are only allowed to input 2450 characters.</div>
													    <textarea class="form-control" id="Q6_Comment" name="Q6_Comment">${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q6Comment ne null ? REFERENCE_BEAN.q6Comment : "" }</textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2450 - Remain: <span id="Q6_Comment_remain">2450</span></div>
													    </td>
													 </tr>	
												</tbody>
										</table>
									</div>
	</div>
</div>

<script>
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
<INPUT class="btn btn-danger btn-xs" TYPE="RESET" VALUE="Reset Form"> &nbsp; 
<a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>
</div>
</form>
	                              
</body>
</html>
