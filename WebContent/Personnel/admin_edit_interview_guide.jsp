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
			
			function clearQuestion(i) {
				$('#q'+ i + '_text').val('');
				$('#q'+ i + '_weight').val('');
				return false;
			}
			
			function goBack() {
			    window.history.back();
			}
		</script>
		

		
		
	</head>
	
	<body>
    <c:set var="now" value="<%=new java.util.Date()%>" /> 								
	<fmt:formatDate value="${now}" pattern="yyyy" var="yearyyyy" />	
	<fmt:formatDate value="${now}" pattern="yy" var="yearyy" />
	
	
	                          <form id='frm-interview-guide' action="editInterviewGuide.html" method="post">
	                          	<input type='hidden' name='guideId' value='${guide.guideId}' />
	                          	<input type='hidden' name='confirmed' value='true' />                     
	                           
	                           <c:if test="${not empty msg}">
	                           		<div class="alert alert-danger">
  										${msg}
									</div>
	                           </c:if>
   
 
 	   <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>EDITING/UPDATING INTERVIEW GUIDE: <span style="color:#000000;">${guide.title}</span></b><br/><br/>* Required. At least one question must be entered up to a maximum of 10 questions.</div>
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
								    <input type="text" class="form-control" id='title' name='title' value="${guide.title}" maxlength="150" required>
  								</div>	
  							</div>	
  							</div>	
  							<div class="row">
      			 	       		<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">	                       	    
  								<div class="input-group">
    									<span class="input-group-addon formTitleArea">Guide Type</span>
    										<select id="guide_type" name="guide_type" class="form-control">
	                                      		<c:choose>
	                                      			<c:when test="${guide.guideType eq 'S' }">
	                                      				<option value="T">Teaching</option>
	                                      				<option value="S" selected>Support Staff</option>
	                                      			</c:when>
	                                      			<c:otherwise>
	                                      				<option value="T" selected>Teaching</option>
	                                      				<option value="S">Support Staff</option>
	                                      			</c:otherwise>
	                                      		</c:choose>
	                                      	</select>
    							</div>	
  								</div>
  								<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">	
	                            <div class="input-group">
    									<span class="input-group-addon formTitleArea">School Year</span>  
    								 	<jobv2:SchoolYearListbox id="lst_schoolyear" pastYears="3" futureYears="1" cls='form-control' value='${guide.schoolYear}' />
    							</div>
    							</div>
    						</div>
    						<div class="row">
      			 	       	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
    							 <label class="checkbox-inline">
											<c:choose>
    										<c:when test="${guide.activeList == true}">
       											<input type='checkbox' id='activelist' name='activelist' checked />
    										</c:when>
    										<c:otherwise>
        										<input type='checkbox' id='activelist' name='activelist' />
    										</c:otherwise>
										</c:choose>
											Check this box if this list is to be active.</label>
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
								    <input id='scalebottom' name='scalebottom' class="form-control" value='${guide.ratingScaleBottom}' />
  								</div>
  							</div>	
  							<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">	
    							<div class="input-group">
    								<span class="input-group-addon formTitleArea">Max *</span>
								    <input id='scaletop' name='scaletop' class="form-control" value='${guide.ratingScaleTop}' />
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
    												
	                          <div class="alert alert-danger" id="errorText3" style="display:none;text-align:center;"></div>					
	                           
	                           <c:forEach items='${guide.questions}' var='q' varStatus='status'>		                       		
			                            <div class="input-group">
		    								<span class="input-group-addon formTitleArea">Question or<br/>Competency ${status.index + 1}.<br/><br/>
		    								<a class='btn btn-xs btn-warning' href='#' onclick='clearQuestion(${status.index + 1 }); return false;'>Clear</a>		    								
		    								</span>
										    <input type="text" class="form-control" id='q${status.index + 1}_weight' name='q${status.index + 1}_weight' value="${q.weight}" placeholder="Enter weight (ex. 1.0)" maxlength="5" required>
		  								    <textarea class="form-control" rows="3" id='q${status.index + 1}_text' name='q${status.index + 1}_text' maxlength="250" placeholder="Enter the Question / Competency (Max 250 characters).">${q.question}</textarea>
		  								</div>  
  							  </c:forEach>
  							  <c:forEach var='i' begin='${guide.questionCount + 1 }' end='10'>	                                
  							 		 	<div class="input-group">
		    								<span class="input-group-addon formTitleArea">Question or<br/>Competency ${i}.</span>
										    <input type="text" class="form-control" id='q${i}_weight' name='q${i}_weight' placeholder="Enter weight (ex. 1.0)" maxlength="5" required>
		  								    <textarea class="form-control" rows="3" id='q${i}_text' name='q${i}_text' maxlength="250" placeholder="Enter the Question / Competency (Max 250 characters)."></textarea>
		  								</div>  
  							  </c:forEach>   
	                              
	                                     
	                                    			
	                </div>
    				</div>                 			
	                                    			
	    </div> 
 
   						<div align="center">
	    							<button id='btn-submit' type="button" class="btn btn-success btn-xs">Save Changes</button> 
	    							&nbsp; <a class="btn btn-danger btn-xs" onclick="goBack()">Cancel</a>
	    				</div>
      
	                                      
	                          </form>
	                       
	</body>
</html>
