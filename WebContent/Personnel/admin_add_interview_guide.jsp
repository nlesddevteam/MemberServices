<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.esdnl.personnel.jobs.bean.*" 
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
		<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="jobv2" %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
<html>

	<head>
		<title>MyHRP Applicant Profiling System</title>		
		
		<script type='text/javascript'>
			$(function(){					
					
					$('#btn-submit').click(function(){
						
						$("#title").css("border","");
						$("#scalebottom").css("border","");
						$("#scaletop").css("border","");
						$("#q1_weight").css("border","");
						$("#q1_text").css("border","");
						
						if($('#title').val() == ''){	
							
							$("#errorText").html("Your interview guide must have a title before it can be submitted.").css("display","block").delay(4000).fadeOut();
							$("#title").css("border","1px solid red");
							$("#title").focus();
						}
						else if($('#scalebottom').val() == '' || isNaN(parseInt($('#scalebottom').val(), 10))) {							
							
							$("#errorText2").html("Rating scale minimum value is required.").css("display","block").delay(4000).fadeOut();
							$("#scalebottom").css("border","1px solid red");
							$("#scalebottom").focus();							
							
						}
						else if($('#scaletop').val() == '' || isNaN(parseInt($('#scaletop').val(), 10))) {
							
							$("#errorText2").html("Rating scale maximum value is required.").css("display","block").delay(4000).fadeOut();
							$("#scaletop").css("border","1px solid red");
							$("#scaletop").focus();							
						}
						else {
							var questions = 0;
							
							if($('#q1_weight').val() != '' && $('#q1_text').val()) {
								questions++;
							}
							if($('#q2_weight').val() != '' && $('#q2_text').val()) {
								questions++;
							}
							if($('#q3_weight').val() != '' && $('#q3_text').val()) {
								questions++;
							}
							if($('#q4_weight').val() != '' && $('#q4_text').val()) {
								questions++;
							}
							if($('#q5_weight').val() != '' && $('#q5_text').val()) {
								questions++;
							}
							if($('#q6_weight').val() != '' && $('#q6_text').val()) {
								questions++;
							}
							if($('#q7_weight').val() != '' && $('#q7_text').val()) {
								questions++;
							}
							if($('#q8_weight').val() != '' && $('#q8_text').val()) {
								questions++;
							}
							if($('#q9_weight').val() != '' && $('#q9_text').val()) {
								questions++;
							}
							if($('#q10_weight').val() != '' && $('#q10_text').val()) {
								questions++;
							}
							
							if(questions <= 0){								
								
								$("#errorText3").html("At least one question and weight must be entered.").css("display","block").delay(4000).fadeOut();
								$("#q1_weight").css("border","1px solid red");
								$("#q1_text").css("border","1px solid red");
								$("#q1_weight").focus();							
								
							}
							else {
								$('#frm-interview-guide').submit();
							}
						}
						
					});
					
			});
			$("#loadingSpinner").css("display","none");
		</script>
		
		
		
	</head>
	
	<body>	
	<c:set var="now" value="<%=new java.util.Date()%>" /> 								
	<fmt:formatDate value="${now}" pattern="yyyy" var="yearyyyy" />	
	<fmt:formatDate value="${now}" pattern="yy" var="yearyy" />	
	
	                          <form id='frm-interview-guide' action="addInterviewGuide.html" method="post">
	                          	<input type='hidden' name='confirmed' value='true' />                       
	                           
	                           
	                          
	                           
	                           <c:if test="${not empty msg}">
	                           		<div class="alert alert-danger">
  										${msg}
									</div>
	                           </c:if>
	                          	                                                     
	   <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>ADD INTERVIEW GUIDE</b><br/>* Required. At least one question must be entered up to a maximum of 10 questions.</div>
      			 	<div class="panel-body"> 
      			 	
      			 	<div class="container-fluid"> 
      			 	       <div class="row">
      			 	       <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	                          	 <div class="alert alert-danger" id="errorText" style="display:none;text-align:center;"></div>
	                       </div>
	                       </div>  
	                       <div class="row">
      			 	       <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	                            <div class="input-group">
    								<span class="input-group-addon formTitleArea">Title *</span>
								    <input type="text" class="form-control" id='title' name='title' placeholder="Enter a title for this guide. (Max 150 characters)" maxlength="150" required>
  								</div>	
  							</div>	
  							</div>	
  							<div class="row">
      			 	       		<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">	                       	    
  								<div class="input-group">
    									<span class="input-group-addon formTitleArea">Guide Type</span>
    										<select id="guide_type" name="guide_type" class="form-control">
    										<option value="-1" selected>- PLEASE SELECT -</option>
	                                      		<option value="T">Teaching</option>
	                                      		<option value="S">Support Staff</option>
	                                      	</select>
    							</div>	
  								</div>
  								<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">	
	                            <div class="input-group">
    									<span class="input-group-addon formTitleArea">School Year</span>  
    								 	<jobv2:SchoolYearListbox id="lst_schoolyear" pastYears="3" futureYears="1" cls='form-control'/>
    							</div>
    							</div>
    						</div>
    						<div class="row">
      			 	       	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
    							 <label class="checkbox-inline"><input type="checkbox" id="activelist" name="activelist">Check this box if this list is to be active.</label>
    						</div>
    						</div>
    							
    				</div></div>
    				</div>
    				<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Rating Scales</b> (Top/Bottom) for questions:</div>
      			 	<div class="panel-body">   
      			 	
      			 	 		<div class="container-fluid"> 
      			 	       <div class="row">
      			 	       <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">					 
							    <div class="input-group">
    								<span class="input-group-addon formTitleArea">Min *</span>
								    <input type="text" class="form-control" id='scalebottom' name='scalebottom' placeholder="Enter value between 0 and 10" maxlength="2" required>
  								</div>
  							</div>
  							<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">	
    							<div class="input-group">
    								<span class="input-group-addon formTitleArea">Max *</span>
								    <input type="text" class="form-control" id='scaletop' name='scaletop' placeholder="Enter value between 1 and 10" maxlength="2" required>
  								</div>
  							</div>
  							</div>
  							</div>	
  								<div class="alert alert-danger" id="errorText2" style="display:none;text-align:center;"></div>
    				
    				
    				</div>
    				</div>
    				
    				<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Questions / Competency:</b><br/>
	                           					You must enter the weight of answer/result required for any question/competency you add below.</div>
      			 	<div class="panel-body">
    												
	                           
	                            <div class="input-group">
    								<span class="input-group-addon formTitleArea">* Question or<br/>Competency 1.</span>
								    <input type="text" class="form-control" id='q1_weight' name='q1_weight' placeholder="Enter weight (ex. 1.0)" maxlength="5" required>
  								    <textarea class="form-control" rows="3" id='q1_text' name='q1_text' maxlength="250" placeholder="Enter the Question / Competency (Max 250 characters)."></textarea>
  								</div>  
  								
  								<div class="alert alert-danger" id="errorText3" style="display:none;text-align:center;"></div>    
	                                   
	                            <div class="input-group">
    								<span class="input-group-addon formTitleArea">Question or<br/>Competency 2.</span>
								    <input type="text" class="form-control" id='q2_weight' name='q2_weight' placeholder="Enter weight (ex. 1.0)" maxlength="5" required>
  								    <textarea class="form-control" rows="3" id='q2_text' name='q2_text' maxlength="250" placeholder="Enter the Question / Competency (Max 250 characters)."></textarea>
  								</div>
  								
  								<div class="input-group">
    								<span class="input-group-addon formTitleArea">Question or<br/>Competency 3.</span>
								    <input type="text" class="form-control" id='q3_weight' name='q3_weight' placeholder="Enter weight (ex. 1.0)" maxlength="5" required>
  								    <textarea class="form-control" rows="3" id='q3_text' name='q3_text' maxlength="250" placeholder="Enter the Question / Competency (Max 250 characters)."></textarea>
  								</div>      
  								
  								<div class="input-group">
    								<span class="input-group-addon formTitleArea">Question or<br/>Competency 4.</span>
								    <input type="text" class="form-control" id='q4_weight' name='q4_weight' placeholder="Enter weight (ex. 1.0)" maxlength="5" required>
  								    <textarea class="form-control" rows="3" id='q4_text' name='q4_text' maxlength="250" placeholder="Enter the Question / Competency (Max 250 characters)."></textarea>
  								</div>      
  								
  								<div class="input-group">
    								<span class="input-group-addon formTitleArea">Question or<br/>Competency 5.</span>
								    <input type="text" class="form-control" id='q5_weight' name='q5_weight' placeholder="Enter weight (ex. 1.0)" maxlength="5" required>
  								    <textarea class="form-control" rows="3" id='q5_text' name='q5_text' maxlength="250" placeholder="Enter the Question / Competency (Max 250 characters)."></textarea>
  								</div>      
  								
  								<div class="input-group">
    								<span class="input-group-addon formTitleArea">Question or<br/>Competency 6.</span>
								    <input type="text" class="form-control" id='q6_weight' name='q6_weight' placeholder="Enter weight (ex. 1.0)" maxlength="5" required>
  								    <textarea class="form-control" rows="3" id='q6_text' name='q6_text' maxlength="250" placeholder="Enter the Question / Competency (Max 250 characters)."></textarea>
  								</div>     
	                                      
	                            <div class="input-group">
    								<span class="input-group-addon formTitleArea">Question or<br/>Competency 7.</span>
								    <input type="text" class="form-control" id='q7_weight' name='q7_weight' placeholder="Enter weight (ex. 1.0)" maxlength="5" required>
  								    <textarea class="form-control" rows="3" id='q7_text' name='q7_text' maxlength="250" placeholder="Enter the Question / Competency (Max 250 characters)."></textarea>
  								</div>      
  								
  								<div class="input-group">
    								<span class="input-group-addon formTitleArea">Question or<br/>Competency 8.</span>
								    <input type="text" class="form-control" id='q8_weight' name='q8_weight' placeholder="Enter weight (ex. 1.0)" maxlength="5" required>
  								    <textarea class="form-control" rows="3" id='q8_text' name='q8_text' maxlength="250" placeholder="Enter the Question / Competency (Max 250 characters)."></textarea>
  								</div>      
  								
  								<div class="input-group">
    								<span class="input-group-addon formTitleArea">Question or<br/>Competency 9.</span>
								    <input type="text" class="form-control" id='q9_weight' name='q9_weight' placeholder="Enter weight (ex. 1.0)" maxlength="5" required>
  								    <textarea class="form-control" rows="3" id='q9_text' name='q9_text' maxlength="250" placeholder="Enter the Question / Competency (Max 250 characters)."></textarea>
  								</div>      
  								
  								<div class="input-group">
    								<span class="input-group-addon formTitleArea">Question or<br/>Competency 10.</span>
								    <input type="text" class="form-control" id='q10_weight' name='q10_weight' placeholder="Enter weight (ex. 1.0)" maxlength="5" required>
  								    <textarea class="form-control" rows="3" id='q10_text' name='q10_text' maxlength="250" placeholder="Enter the Question / Competency (Max 250 characters)."></textarea>
  								</div>             
	                                     
	                                    			
	                   	</div>
    					</div>                 			
	                                    			
	    </div>     
	   	   
	    				<div align="center" class="no-print">
	    							<button id='btn-submit' type="button" class="btn btn-success">Submit Guide</button> 
	    							&nbsp; <a href="/MemberServices/Personnel/admin_index.jsp" class="btn btn-danger">Cancel</a></div>
	                                                       
	                          </form>

	</body>
</html>
