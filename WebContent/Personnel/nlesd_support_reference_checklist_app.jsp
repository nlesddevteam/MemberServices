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

<esd:SecurityRequiredPageObjectsCheck names='<%=new String[]{ "PROFILE" }%>'
	scope='<%=PageContext.REQUEST_SCOPE%>'
	redirectTo="/Personnel/admin_index.jsp" />
	
<%
	ApplicantRefRequestBean refReq = (ApplicantRefRequestBean) request.getAttribute("arefreq");
	ReferenceCheckRequestBean rbean =(ReferenceCheckRequestBean) request.getAttribute("refreq");
	ApplicantProfileBean profile = (ApplicantProfileBean) request.getAttribute("PROFILE");
	
	String val1="0";
	String val2="1";
	String val3="2";
	String val4="3";
	String val5="4";
	String val6="5";
	
%>
<html>
	<head>
		<title>MyHRP Applicant Profiling System</title>
		
		<script>
			$('document').ready(function(){
				$('#btnSubmit').click(function(){
					var is_valid = true;
					//hide the error messages
					$('#section0Error').css('display','none');
					$('#section1Error').css('display','none');
					$('#section2Error').css('display','none');
					$('#section3Error').css('display','none');
					if($('#ref_provider_name').val() == ''){
						is_valid = false;
						$('#section0Error').html('Please enter Provider.');
						$('#section0Error').css('display','block').delay(5000);
						$('#ref_provider_name').focus();
					}else if($('#ref_provider_email').val() == ''){
						is_valid = false;
						$('#section0Error').html('Please enter Provider Email.');
						$('#section0Error').css('display','block').delay(5000);
						$('#ref_provider_email').focus();
					}else if(!(validateEmailAddress($('#ref_provider_email').val()))){
						is_valid = false;
						$('#section0Error').html('Please enter valid Email.');
						$('#section0Error').css('display','block').delay(5000);
						$('#ref_provider_email').focus();
					
					}else if($('#ref_provider_position').val() == ''){
						is_valid = false;
						$('#section0Error').html('Please enter Provider Position.');
						$('#section0Error').css('display','block').delay(5000);
						$('#ref_provider_position').focus();
					}
					else if(!$("input[name='Q1']:checked").val() || $("input[name='Q2']").val() == '' || $("input[name='Q3']").val() == '') {
						is_valid = false;
						$('#section1Error').css('display','block').delay(5000);
						$('#Q1').focus();
					}
					else if(!$("input[name='Scale1']:checked").val() || !$("input[name='Scale2']:checked").val() || !$("input[name='Scale3']:checked").val() 
							|| !$("input[name='Scale4']:checked").val() || !$("input[name='Scale5']:checked").val() || !$("input[name='Scale6']:checked").val()
							|| !$("input[name='Scale7']:checked").val() || !$("input[name='Scale8']:checked").val() || !$("input[name='Scale9']:checked").val()
 							|| !$("input[name='Scale10']:checked").val() || !$("input[name='Scale11']:checked").val() || !$("input[name='Scale12']:checked").val()
							|| !$("input[name='Scale13']:checked").val() || !$("input[name='Scale14']:checked").val()) {
						is_valid = false;
						$('#section2Error').css('display','block').delay(5000);
						$('#Scale1').focus();	
					}
					else if($('[name="Q10"]:checked').length <= 0 || $('[name="Q11"]:checked').length <= 0){
						is_valid = false;
						$('#section0Error').html('Please enter anwsers for  Q10 and Q11.');
						$('#section3Error').css('display','block').delay(5000);
						$('#Q10').focus();
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
		<script>
			$("#loadingSpinner").css("display","none");
		</script>
	</head>
	<body>
		<div class="pageHeader">Candidate <%= profile.getFullNameReverse() %> Reference</div>	
	
			                            		<div class="alert alert-danger" id="divmessage" style="display:none;text-align:center;">
  													<span id="spanmessage"><strong></strong></span>
												</div>
												
				<div class="alert alert-warning" style="font-size:11px;">		
               		Personal information is collected under the authority of Access to Information and Protection of Privacy Act, 2015 (ATIPPA). This information will be used to 
               		determine suitability for employment within the Newfoundland and Labrador English School District.  It will be treated in accordance with the privacy 
               		protection provisions of ATIPPA.
               	
               		<br/><br/>Specifically, Section 32(a) of the Access to Information and Protection of Privacy Act, 2015 states:
               	
               		<br/><br/>32.  The head of a public body may refuse to disclose to an applicant personal information that is evaluative or opinion material, provided explicitly or 
               		implicitly in confidence, and compiled for the purpose of 
               	
               		<br/><br/>(a) determining suitability, eligibility or qualifications for employment or for the awarding of contracts or other benefits of a public body 
               	
               		<br/><br/>For further information contact the ATIPP Coordinator: <a href="mailto:atipp@nlesd.ca?subject=ATIPP Request">atipp@nlesd.ca</a> or by phone: (709) 758-4036.     
                    </div>	
			                            	
										<%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){ %>
			                            	<div class="alert alert-danger" style="text-align:center;"><%=(String)request.getAttribute("msg")%></div>
			                            <%} %>     
			                            
                       <form action="addNLESDSupportReferenceApp.html" method="POST" name="admin_nlesd_rec_form" id="admin_nlesd_rec_form">
						<input type='hidden' id='applicant_id' name='applicant_id' value='<%= profile.getUID() %>' />
                      	<input type='hidden' id='confirm' name='confirm' value='true' />	


<!-- Referencee Information -------------------------------------------------------------------------->	

	
<div class="panel panel-success">
  <div class="panel-heading">Referencee Information</div>
  	<div class="panel-body">
  	<div class="alert alert-danger" id="section0Error" style="display:none;">Please enter the position of the person providing reference.</div>	
  		
									<div class="table-responsive"> 
		      			 	       		<table class="table table-striped table-condensed" style="font-size:12px;">							   
										    <tbody>
										        <c:choose>
									    		<c:when test="${ arefreq ne null }">
													<tr>
											    		<td class="tableTitle">CANDIDATE:</td>
											    		<td class="tableResult" style="text-transform:Capitalize;">${ arefreq.applicantName }
											    		<input type='hidden' id='arefreqid' name='arefreqid' value='${ arefreq.id}' />
											    		<input type='hidden' name='rapplicant_id' id='rapplicant_id' value='${ arefreq.applicantId }' />	
											    		</td>
										    		</tr>
												</c:when>
												<c:when test="${ refreq ne null }">
													<tr>
											    		<td class="tableTitle">CANDIDATE:</td>
											    		<td class="tableResult" style="text-transform:Capitalize;">${ refreq.applicantName }
											    		<input type='hidden' id='refreqid' name='refreqid' value='${ refreq.requestId}' />
											    		<input type='hidden' name='rapplicant_id' id='rapplicant_id' value='${ refreq.candidateId }' />
											    		</td>
										    		</tr>
												</c:when>
												</c:choose>	
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
											    <tr>
												    <td class="tableTitle">PROVIDER EMAIL:</td>
													<c:choose>
												    	<c:when test="${ arefreq ne null}">
												    	    <td class="tableResult"><input type="text" id="ref_provider_email" class="form-control input-sm" placeholder="Enter your Email" name="ref_provider_email" value="${ arefreq.emailAddress }"/></td>
											    		</c:when>
											    		<c:when test="${ refreq ne null}">
												    	    <td class="tableResult"><input type="text" id="ref_provider_email" class="form-control input-sm" placeholder="Enter your Email" name="ref_provider_email" value="${ refreq.getReferrerEmail()}"/></td>
											    		</c:when>
											    		<c:otherwise>
											    			<td class="tableResult"><input type="text" id="ref_provider_email" class="form-control input-sm" placeholder="Enter your Email" name="ref_provider_email" value=""/></td>
											    		</c:otherwise>
												    </c:choose>
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
													    <input type="radio" name="Q1" id="Q1" value="Yes">Yes &nbsp;<input type="radio" name="Q1" value="No">No
													    </td>
													 </tr>
													 <tr>
													    <td>Q2.</td>
													    <td>How long have you known this candidate in a professional capacity?
													    <input type="text" name="Q2" placeholder="Enter Answer" class="form-control input-sm">
													    </td>
													    
													 </tr> 
													 <tr>
													    <td>Q3.</td>
													    <td>In what capacity are you able to assess the performance of this applicant?
													    <input type="text" name="Q3" placeholder="Enter Answer" class="form-control input-sm">
													    </td>													    
													 </tr> 
													 											 
										    	</tbody>
									    </table>
									</div>	
	</div>
</div>															
		


<!-- Ratings -------------------------------------------------------------------------->		


<div class="panel panel-success">
  <div class="panel-heading">Ratings</div>
  	<div class="panel-body">
  	<div class="alert alert-danger" id="section2Error" style="display:none;">ERROR: Please make sure you rate each statement below.</div>	
									<div class="table-responsive">
									
										On a scale of <%=val1%> to <%=val6%> please rate the candidate on the following statements: 
										
										(<%=val6%>-Excellent, <%=val5%>-Good, <%=val4%>-Average, <%=val3%>-Needs Improvement, <%=val2%>-Unacceptable and <%=val1%>-N/A):
																		
									
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
													    <td>1.</td>
													    <td>Displays positive attitude towards job</td>
													    <td>
													    <input type="radio" id="Scale1" name="Scale1" value="<%=val1%>"> <%=val1%>
														<input type="radio" name="Scale1" value="<%=val2%>"> <%=val2%>
														<input type="radio" name="Scale1" value="<%=val3%>"> <%=val3%>
														<input type="radio" name="Scale1" value="<%=val4%>"> <%=val4%>
														<input type="radio" name="Scale1" value="<%=val5%>"> <%=val5%>
														<input type="radio" name="Scale1" value="<%=val6%>"> <%=val6%>
														</td>
													 </tr>
													<tr>
													    <td>2.</td>
													    <td>Ability to get along with people (peers and management)</td>
													    <td>
													    <input type="radio" name="Scale2" value="<%=val1%>"> <%=val1%>
														<input type="radio" name="Scale2" value="<%=val2%>"> <%=val2%>
														<input type="radio" name="Scale2" value="<%=val3%>"> <%=val3%>
														<input type="radio" name="Scale2" value="<%=val4%>"> <%=val4%>
														<input type="radio" name="Scale2" value="<%=val5%>"> <%=val5%>
														<input type="radio" name="Scale2" value="<%=val6%>"> <%=val6%>
														</td>
													 </tr>					
													<tr>
													    <td>3.</td>
													    <td>Flexibility/adjustment to new situations</td>
													    <td>
													    <input type="radio" name="Scale3" value="<%=val1%>"> <%=val1%>
														<input type="radio" name="Scale3" value="<%=val2%>"> <%=val2%>
														<input type="radio" name="Scale3" value="<%=val3%>"> <%=val3%>
														<input type="radio" name="Scale3" value="<%=val4%>"> <%=val4%>
														<input type="radio" name="Scale3" value="<%=val5%>"> <%=val5%>
														<input type="radio" name="Scale3" value="<%=val6%>"> <%=val6%>
														</td>
													 </tr>
													 <tr>
													    <td>4.</td>
													    <td>Organizational and time management skills</td>
													    <td>
													    <input type="radio" name="Scale4" value="<%=val1%>"> <%=val1%>
														<input type="radio" name="Scale4" value="<%=val2%>"> <%=val2%>
														<input type="radio" name="Scale4" value="<%=val3%>"> <%=val3%>
														<input type="radio" name="Scale4" value="<%=val4%>"> <%=val4%>
														<input type="radio" name="Scale4" value="<%=val5%>"> <%=val5%>
														<input type="radio" name="Scale4" value="<%=val6%>"> <%=val6%>
														</td>
													 </tr>	
													  <tr>
													    <td>5.</td>
													    <td>Verbal and written communications skills</td>
													    <td>
													    <input type="radio" name="Scale5" value="<%=val1%>"> <%=val1%>
														<input type="radio" name="Scale5" value="<%=val2%>"> <%=val2%>
														<input type="radio" name="Scale5" value="<%=val3%>"> <%=val3%>
														<input type="radio" name="Scale5" value="<%=val4%>"> <%=val4%>
														<input type="radio" name="Scale5" value="<%=val5%>"> <%=val5%>
														<input type="radio" name="Scale5" value="<%=val6%>"> <%=val6%>
														</td>
													 </tr>		
													 <tr>
													    <td>6.</td>
													    <td>Computer knowledge and skills</td>
													    <td>
													    <input type="radio" name="Scale6" value="<%=val1%>"> <%=val1%>
														<input type="radio" name="Scale6" value="<%=val2%>"> <%=val2%>
														<input type="radio" name="Scale6" value="<%=val3%>"> <%=val3%>
														<input type="radio" name="Scale6" value="<%=val4%>"> <%=val4%>
														<input type="radio" name="Scale6" value="<%=val5%>"> <%=val5%>
														<input type="radio" name="Scale6" value="<%=val6%>"> <%=val6%>
														</td>
													 </tr>					
													  <tr>
													    <td>7.</td>
													    <td>Ability to handle stressful situations</td>
													    <td>
													    <input type="radio" name="Scale7" value="<%=val1%>"> <%=val1%>
														<input type="radio" name="Scale7" value="<%=val2%>"> <%=val2%>
														<input type="radio" name="Scale7" value="<%=val3%>"> <%=val3%>
														<input type="radio" name="Scale7" value="<%=val4%>"> <%=val4%>
														<input type="radio" name="Scale7" value="<%=val5%>"> <%=val5%>
														<input type="radio" name="Scale7" value="<%=val6%>"> <%=val6%>
														</td>
													 </tr>
													  <tr>
													    <td>8.</td>
													    <td>Dependability (punctuality and attendance)</td>
													    <td>
													    <input type="radio" name="Scale8" value="<%=val1%>"> <%=val1%>
														<input type="radio" name="Scale8" value="<%=val2%>"> <%=val2%>
														<input type="radio" name="Scale8" value="<%=val3%>"> <%=val3%>
														<input type="radio" name="Scale8" value="<%=val4%>"> <%=val4%>
														<input type="radio" name="Scale8" value="<%=val5%>"> <%=val5%>
														<input type="radio" name="Scale8" value="<%=val6%>"> <%=val6%>
														</td>
													 </tr>					
													 <tr>
													    <td>9.</td>
													    <td>Ability to work independently with minimum supervision</td>
													    <td>
													    <input type="radio" name="Scale9" value="<%=val1%>"> <%=val1%>
														<input type="radio" name="Scale9" value="<%=val2%>"> <%=val2%>
														<input type="radio" name="Scale9" value="<%=val3%>"> <%=val3%>
														<input type="radio" name="Scale9" value="<%=val4%>"> <%=val4%>
														<input type="radio" name="Scale9" value="<%=val5%>"> <%=val5%>
														<input type="radio" name="Scale9" value="<%=val6%>"> <%=val6%>
														</td>
													 </tr>	
													 <tr>
													    <td>10.</td>
													    <td>Open to constructive criticism and advice</td>
													    <td>
													    <input type="radio" name="Scale10" value="<%=val1%>"> <%=val1%>
														<input type="radio" name="Scale10" value="<%=val2%>"> <%=val2%>
														<input type="radio" name="Scale10" value="<%=val3%>"> <%=val3%>
														<input type="radio" name="Scale10" value="<%=val4%>"> <%=val4%>
														<input type="radio" name="Scale10" value="<%=val5%>"> <%=val5%>
														<input type="radio" name="Scale10" value="<%=val6%>"> <%=val6%>
														</td>
													 </tr>					
													 <tr>
													    <td>11.</td>
													    <td>Displays courtesy and tact</td>
													    <td>
													    <input type="radio" name="Scale11" value="<%=val1%>"> <%=val1%>
														<input type="radio" name="Scale11" value="<%=val2%>"> <%=val2%>
														<input type="radio" name="Scale11" value="<%=val3%>"> <%=val3%>
														<input type="radio" name="Scale11" value="<%=val4%>"> <%=val4%>
														<input type="radio" name="Scale11" value="<%=val5%>"> <%=val5%>
														<input type="radio" name="Scale11" value="<%=val6%>"> <%=val6%>
														</td>
													 </tr>					
													 <tr>
													    <td>12.</td>
													    <td>Level of professionalism</td>
													    <td>
													    <input type="radio" name="Scale12" value="<%=val1%>"> <%=val1%>
														<input type="radio" name="Scale12" value="<%=val2%>"> <%=val2%>
														<input type="radio" name="Scale12" value="<%=val3%>"> <%=val3%>
														<input type="radio" name="Scale12" value="<%=val4%>"> <%=val4%>
														<input type="radio" name="Scale12" value="<%=val5%>"> <%=val5%>
														<input type="radio" name="Scale12" value="<%=val6%>"> <%=val6%>
														</td>
													 </tr>																		
													 <tr style="display:none;">
													    <td>13.</td>
													    <td>Personal appearance and hygiene</td>
													    <td>
													    <input type="radio" name="Scale13" value="<%=val1%>"> <%=val1%>
														<input type="radio" name="Scale13" value="<%=val2%>"> <%=val2%>
														<input type="radio" name="Scale13" value="<%=val3%>"> <%=val3%>
														<input type="radio" name="Scale13" value="<%=val4%>"> <%=val4%>
														<input type="radio" name="Scale13" value="<%=val5%>"> <%=val5%>
														<input type="radio" name="Scale13" value="<%=val6%>" checked> <%=val6%>
														</td>
													 </tr>						
													 <tr>
													    <td>13.</td>
													    <td>Indvidual's performance compared to others with similar duties</td>
													    <td>
													    <input type="radio" name="Scale14" value="<%=val1%>"> <%=val1%>
														<input type="radio" name="Scale14" value="<%=val2%>"> <%=val2%>
														<input type="radio" name="Scale14" value="<%=val3%>"> <%=val3%>
														<input type="radio" name="Scale14" value="<%=val4%>"> <%=val4%>
														<input type="radio" name="Scale14" value="<%=val5%>"> <%=val5%>
														<input type="radio" name="Scale14" value="<%=val6%>"> <%=val6%>
														</td>
													 </tr>								
													</tbody>
									    </table>
									</div>	
	</div>
</div>	
			
				
<!-- Other Information  ----------------------------------------------------------------->																																				
																		
																		
<div class="panel panel-success">
  <div class="panel-heading">Questions</div>
  	<div class="panel-body">	
  	<div class="alert alert-danger" id="section3Error" style="display:none;">Please make sure you answered below.</div>
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
													   	<td>Q4.</td>
													    <td>
													    Please give a brief description of the applicant's duties while in your employ<br/>
													    <div id="Q4_Error" class="alert alert-danger" style="display:none;">ERROR: Character limit exceeded. You are only allowed to input 2450 characters.</div>
													    <textarea name="Q4" id="Q4" class="form-control"></textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2450 - Remain: <span id="Q4_remain">2450</span></div>
													    </td>
													 </tr>
												      <tr>
													   	<td>Q5.</td>
													    <td>
													    Please comment on the applicant's strengths<br/>
													    <div id="Q5_Error" class="alert alert-danger" style="display:none;">ERROR: Character limit exceeded. You are only allowed to input 2450 characters.</div>
													    <textarea name="Q5" id="Q5" class="form-control"></textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2450 - Remain: <span id="Q5_remain">2450</span></div>
													    </td>
													 </tr>
												     <tr>
													   	<td>Q6.</td>
													    <td>
													    Please comment on the applicant's weaknesses<br/>
													    <div id="Q6_Error" class="alert alert-danger" style="display:none;">ERROR: Character limit exceeded. You are only allowed to input 2450 characters.</div>
													    <textarea name="Q6" id="Q6" class="form-control"></textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2450 - Remain: <span id="Q6_remain">2450</span></div>
													    </td>
													 </tr>
												     <tr>
													   	<td>Q7.</td>
													    <td>Did the applicant have any attendance problems while in your employ?
																	<input type="radio" name="Q7" value="Yes">Yes &nbsp; 
																	<input type="radio" name="Q7" value="No">No
													    <br/>If yes, please comment<br/>
													    <div id="Q7_Error" class="alert alert-danger" style="display:none;">ERROR: Character limit exceeded. You are only allowed to input 2450 characters.</div>
													    <textarea name="Q7C" id="Q7C" class="form-control"></textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2450 - Remain: <span id="Q7_remain">2450</span></div>
													    </td>
													 </tr>
													 <tr>
													   	<td>Q8.</td>
													    <td>Did the applicant have any disciplinary issues while in your employ?
																	<input type="radio" name="Q8" value="Yes">Yes &nbsp; 
																	<input type="radio" name="Q8" value="No">No
													    <br/>If yes, please comment<br/>
													    <div id="Q8_Error" class="alert alert-danger" style="display:none;">ERROR: Character limit exceeded. You are only allowed to input 2450 characters.</div>
													    <textarea name="Q8C" id="Q8C" class="form-control"></textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2450 - Remain: <span id="Q8_remain">2450</span></div>
													    </td>
													 </tr>
													 <tr>
													   	<td>Q9.</td>
													    <td>
													    What was the applicant's reason for leaving you employ?
													    <div id="Q9_Error" class="alert alert-danger" style="display:none;">ERROR: Character limit exceeded. You are only allowed to input 2450 characters.</div>
													    <textarea name="Q9" id="Q9" class="form-control"></textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2450 - Remain: <span id="Q9_remain">2450</span></div>
													    </td>
													 </tr>
												      <tr>
													   	<td>Q10.</td>
													    <td>Would you hire/rehire the applicant? <input type="radio" name="Q10" id="Q10" value="Yes">Yes &nbsp; <input type="radio" name="Q10" value="No">No
													    </td>
													 </tr>
													 <tr>
													   	<td>Q11.</td>
													    <td>Overall, how would you rate the applicant's performance while employed with you?
													    			<input type="radio" name="Q11" value="Excellent">Excellent 
																	<input type="radio" name="Q11" value="Good">Good
																	<input type="radio" name="Q11" value="Average">Average
																	<input type="radio" name="Q11" value="Needs Improvement">Needs Improvement
																	<input type="radio" name="Q11" value="Unacceptable">Unacceptable
																	<input type="radio" name="Q11" value="Not Applicable">Not Applicable
													    </td>
													 </tr>
													 <tr>
													   	<td>Q12.</td>
													    <td>Thank you for taking the time to provide this reference.<br/>Is there any other information you'd like to provide that might be helpful in making a hiring decision?
													   <div id="Q12_Error" class="alert alert-danger" style="display:none;">ERROR: Character limit exceeded. You are only allowed to input 2450 characters.</div>
													    <textarea name="Q12" id="Q12" class="form-control"></textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2450 - Remain: <span id="Q12_remain">2450</span></div>
													    	
													   
													    </td>
													 </tr>
												</tbody>
										</table>
										
									</div>
	</div>
</div>																			
																												
<div align="center">					
<input id="btnSubmit" class="btn btn-primary btn-xs" type="submit" value="Submit Reference" > &nbsp; 
<INPUT class="btn btn-danger btn-xs" TYPE="RESET" VALUE="Reset Form">
</div>
																
															
</div>	
</form>	                              
<script>
$('#Q4').keypress(function(e) {
    var tval = $('#Q4').val(),
        tlength = tval.length,
        set = 1000,
        remain = parseInt(set - tlength);
    $('#Q4_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#Q4_Error').css('display','block').delay(4000).fadeOut();
        $('#Q4').val((tval).substring(0, tlength - 1))
    }
})

$('#Q5').keypress(function(e) {
    var tval = $('#Q5').val(),
        tlength = tval.length,
        set = 2450,
        remain = parseInt(set - tlength);
    $('#Q5_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#Q5_Error').css('display','block').delay(4000).fadeOut();
        $('#Q5').val((tval).substring(0, tlength - 1))
    }
})

$('#Q6').keypress(function(e) {
    var tval = $('#Q6').val(),
        tlength = tval.length,
        set = 2450,
        remain = parseInt(set - tlength);
    $('#Q6_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#Q6_Error').css('display','block').delay(4000).fadeOut();
        $('#Q6').val((tval).substring(0, tlength - 1))
    }
})

$('#Q7C').keypress(function(e) {
    var tval = $('#Q7C').val(),
        tlength = tval.length,
        set = 2450,
        remain = parseInt(set - tlength);
    $('#Q7_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#Q7_Error').css('display','block').delay(4000).fadeOut();
        $('#Q7C').val((tval).substring(0, tlength - 1))
    }
})

$('#Q8C').keypress(function(e) {
    var tval = $('#Q8C').val(),
        tlength = tval.length,
        set = 2450,
        remain = parseInt(set - tlength);
    $('#Q8_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#Q8_Error').css('display','block').delay(4000).fadeOut();
        $('#Q8C').val((tval).substring(0, tlength - 1))
    }
})

$('#Q9').keypress(function(e) {
    var tval = $('#Q9').val(),
        tlength = tval.length,
        set = 2450,
        remain = parseInt(set - tlength);
    $('#Q9_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#Q9_Error').css('display','block').delay(4000).fadeOut();
        $('#Q9').val((tval).substring(0, tlength - 1))
    }
})


$('#12').keypress(function(e) {
    var tval = $('#Q12').val(),
        tlength = tval.length,
        set = 2450,
        remain = parseInt(set - tlength);
    $('#Q12_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#Q12_Error').css('display','block').delay(4000).fadeOut();
        $('#Q12').val((tval).substring(0, tlength - 1))
    }
})

</script>
			                              
</body>
</html>
