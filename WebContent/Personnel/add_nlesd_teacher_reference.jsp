<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*" 
         isThreadSafe="false"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<esd:SecurityCheck
	permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
<%
	//no mandatory items on page load since hr admin/principal could be loading page to search for 
	//candidates so profile would be null and no reference request object
	User usr = (User) session.getAttribute("usr");
	String val1="1";
	String val2="2";
	String val3="3";
	String val4="4";
	String val5="-1";
	String val5Text="N/A";
	String refscale="5";
	
	pageContext.setAttribute("val1", val1);
	pageContext.setAttribute("val2", val2);
	pageContext.setAttribute("val3", val3);
	pageContext.setAttribute("val4", val4);
	pageContext.setAttribute("val5", val5);
	pageContext.setAttribute("refscale", refscale);
	
%>

<html>
	<head>
		<title>MyHRP Applicant Profiling System</title>
		<script>
			$('document').ready(function(){
				if($('#hidesearch').val() == 'true'){
					$('#panelsearch').hide();
					$('#candidateFound').show();
					
				}else{
					$('#panelsearch').show();
					$('#candidateFound').hide();
				}
				$('#filter_applicant').click(function(){search_applicants();});
				$('#applicant_filter').change(function(){search_applicants();});
				$('#applicant_list').change(function(){
					$.post('addNLESDTeacherReference.html?op=CANDIDATE_DETAILS',
						{
							uid : $('#applicant_list').val()
						},
						function(data){
							var xmlDoc=data;
							var name_str = null;
							if(xmlDoc.getElementsByTagName("FIRST-NAME").length > 0) {
							      name_str = xmlDoc.getElementsByTagName("FIRST-NAME")[0].childNodes[0].nodeValue + " " + xmlDoc.getElementsByTagName("SURNAME")[0].childNodes[0].nodeValue;
							      $('#candidate_name').html(name_str);
							 }
							
						var email_str = null;
						 if(xmlDoc.getElementsByTagName("EMAIL").length > 0) {
						      email_str = xmlDoc.getElementsByTagName("EMAIL")[0].childNodes[0].nodeValue;
						      $('#candidate_email').html(email_str);
						 }
						      
						var addr_str = null;
					    if(xmlDoc.getElementsByTagName("ADDRESS1").length > 0)
					      addr_str = xmlDoc.getElementsByTagName("ADDRESS1")[0].childNodes[0].nodeValue;
					    if(xmlDoc.getElementsByTagName("ADDRESS2").length > 0)
					      addr_str = addr_str + " &middot; " + xmlDoc.getElementsByTagName("ADDRESS2")[0].childNodes[0].nodeValue;
					    addr_str = addr_str + ", " + xmlDoc.getElementsByTagName("PROVINCE")[0].childNodes[0].nodeValue;
					    addr_str = addr_str + " &middot; " + xmlDoc.getElementsByTagName("COUNTRY")[0].childNodes[0].nodeValue;
					    addr_str = addr_str + " &middot; " + xmlDoc.getElementsByTagName("POSTAL-CODE")[0].childNodes[0].nodeValue;
						$('#candidate_address').html(addr_str)
					    //formatting phone numbers
					    var phone_str = null;
					    if(xmlDoc.getElementsByTagName("HOME-PHONE").length > 0)
					      phone_str = "Res: " + xmlDoc.getElementsByTagName("HOME-PHONE")[0].childNodes[0].nodeValue;
					    if(xmlDoc.getElementsByTagName("WORK-PHONE").length > 0)
					    {
					      if(phone_str)
					        phone_str = phone_str +  " &middot; Bus: " + xmlDoc.getElementsByTagName("WORK-PHONE")[0].childNodes[0].nodeValue;
					      else
					        phone_str = "Bus: " + xmlDoc.getElementsByTagName("WORK-PHONE")[0].childNodes[0].nodeValue;
					    }
					    if(xmlDoc.getElementsByTagName("CELL-PHONE").length > 0)
					    {
					      if(phone_str)
					        phone_str = phone_str + " &middot; Cell: " +xmlDoc.getElementsByTagName("CELL-PHONE")[0].childNodes[0].nodeValue;
					      else
					        phone_str = "Cell: " + xmlDoc.getElementsByTagName("CELL-PHONE")[0].childNodes[0].nodeValue;
					    }
					    if(!phone_str)
					      phone_str = 'NONE ON RECORD';
					    $('#candidate_telephone').html(phone_str);
					    $('#candidateFound').css("display","block");    
					   $('#candidate_info').show();
						}
					);
				});
				
				$('#btnSubmit').click(function(){
					var is_valid = true;
					//hide the error messages
					$('#section0Error').css('display','none');
					$('#section1Error').css('display','none');
					$('#section2Error').css('display','none');
					$('#section3Error').css('display','none');
					$('#section4Error').css('display','none');
					$('#section5Error').css('display','none');
					$('#section6Error').css('display','none');
					if($("#applicant_list").is(":visible")){
						if($('#applicant_list').val() == -1) {
							is_valid = false;
							$('#searchMsgSuccess').html('Results found. Please select from dropdown list.').css('display','block').delay(6000).fadeOut();
							$('#candidatesFound').show();	
							$('#applicant_list').focus();
						}
					}
					if($('#ref_provider_name').val() == ''){
						is_valid = false;
						$('#section0Error').html('Please enter Person Providing Reference.')
						$('#section0Error').css('display','block').delay(5000);
						$('#ref_provider_name').focus();
					}else if($('#ref_provider_email').val() == ''){
						is_valid = false;
						$('#section0Error').html('Please enter Provider Email.')
						$('#section0Error').css('display','block').delay(5000);
						$('#ref_provider_email').focus();
					}else if(!(validateEmailAddress($('#ref_provider_email').val()))){
						is_valid = false;
						$('#section0Error').html('Please enter valid Email.')
						$('#section0Error').css('display','block').delay(5000);
						$('#ref_provider_email').focus();
					}else if($('#ref_provider_position').val() == ''){
						is_valid = false;
						$('#section0Error').html('Please enter Position.')
						$('#section0Error').css('display','block').delay(5000);
						$('#ref_provider_position').focus();
					}
					
					
					else if(!$("input[name='Q1']:checked").val() || $("input[name='Q2']").val() == '' || $("input[name='Q3']").val() == '' || $("textarea[name='Q4']").val() == ''
							|| !$("input[name='Q5']:checked").val() || !$("input[name='Q6']:checked").val()) {
						
						is_valid = false;
						$('#section1Error').css('display','block').delay(5000);
						$('#Q1').focus();
					}
					
					
					else if(!$("input[name='Scale1']:checked").val() || !$("input[name='Scale2']:checked").val() || !$("input[name='Scale3']:checked").val()
								|| !$("input[name='Scale4']:checked").val() || !$("input[name='Scale5']:checked").val() || !$("input[name='Scale6']:checked").val()) {
						is_valid = false;
						$('#section2Error').css('display','block').delay(5000);
						$('#Scale1').focus();	
					}
					
					
					else if(!$("input[name='Scale7']:checked").val() || !$("input[name='Scale8']:checked").val() || !$("input[name='Scale9']:checked").val()
								|| !$("input[name='Scale10']:checked").val() || !$("input[name='Scale11']:checked").val()) {
						is_valid = false;
						$('#section3Error').css('display','block').delay(5000);
						$('#Scale7').focus();	
					}
					
					
					else if(!$("input[name='Scale12']:checked").val() || !$("input[name='Scale13']:checked").val() || !$("input[name='Scale14']:checked").val()
							|| !$("input[name='Scale15']:checked").val() || !$("input[name='Scale16']:checked").val()) {
						is_valid = false;
						$('#section4Error').css('display','block').delay(5000);
						$('#Scale12').focus();	
					}
					
					
					
					else if(!$("input[name='Scale17']:checked").val() || !$("input[name='Scale18']:checked").val() || !$("input[name='Scale19']:checked").val()
							|| !$("input[name='Scale20']:checked").val() || !$("input[name='Scale21']:checked").val() || !$("input[name='Scale22']:checked").val()) {
						is_valid = false;
						$('#section5Error').css('display','block').delay(5000);
						$('#Scale18').focus();	
					}					
					
					
					
					else if($("select[name='Q7']").val() == -1) {
						is_valid = false;
						$('#section6Error').css('display','block').delay(5000);
						$("select[name='Q7']").focus();
					}
					return is_valid;
				});
							
			});
			function validateEmailAddress(mail) 
			{
			 if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))
			  {
			    return (true)
			  }
			    return (false)
			}
			function search_applicants(){
				if($('#applicant_filter').val() == '') {
					$('#searchMsgError').html('Please enter search criteria.').css('display','block').delay(4000).fadeOut();					
					return; 
				}
				$("#loadingSpinner").css("display","block");
				$('#candidate_info').hide();
				$('#candidateFound').css("display","none");
				$('#applicant_list').html("<option value='-1'>--- Select One ---</option>");
				$.post('addNLESDTeacherReference.html?op=APPLICANT_FILTER',
					{
						criteria : $('#applicant_filter').val()
					},
					function(data){
						var xmlDoc=data;
					var beans = xmlDoc.getElementsByTagName("APPLICANT-PROFILE");
				    if(beans.length > 0)
				    {	
					    var strOptions = "<option value='-1'>--- Select One ---</option>";
				    	for(var i = 0; i < beans.length; i++)
				    	{
					    	strUid = beans[i].getElementsByTagName('SIN')[0].childNodes[0].nodeValue;
					    	strFirstname = beans[i].getElementsByTagName('FIRST-NAME')[0].childNodes[0].nodeValue;
					    	strSurname = beans[i].getElementsByTagName('SURNAME')[0].childNodes[0].nodeValue;
					    	
				    		strOptions = strOptions + "<option value='" + strUid + "'>" + strSurname + ", " + strFirstname + "</option>";
				    	}
						$('#applicant_list').html(strOptions);						
						$('#searchMsgSuccess').html('Results found. Please select from dropdown list.').css('display','block').delay(6000).fadeOut();
						$('#candidatesFound').show();
						
						$("#loadingSpinner").css("display","none");
						$('#applicant_list').focus();
				    }
				    else {
				    	$('#searchMsgError').html('Search did not find any applicants matching your criteria.').css('display','block').delay(4000).fadeOut();
	                 	$('#candidatesFound').hide();	
	                 	$('#candidateFound').css("display","none");
	                 	$("#loadingSpinner").css("display","none");
				    }
				   
					}
				);
				
			}	
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
 <esd:SecurityCheck />


<div class="pageHeader">Teacher Candidate Reference</div>


			                            <%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){ %>
			                            	<div class="alert alert-danger" style="text-align:center;"><%=(String)request.getAttribute("msg")%></div>
			                            <%} %> 
			                            
			                            
			                            
<form action="addNLESDTeacherReference.html" method="POST" name="admin_rec_form" id="admin_rec_form">
			                                	<input type='hidden' name="confirm" value="true" />
			                                	<input type='hidden' name="hidesearch" id="hidesearch" value='${hidesearch}'>
			                                	
			                                	<c:if test="${ REFERENCE_BEAN ne null}">
			                                		<input type='hidden' name='reference_id' value='${ REFERENCE_BEAN.id }' />
			                                	</c:if>
			
									
									
<!-- Candidate Information -------------------------------------------------------------------------->			
									
<div class="panel panel-success" id="panelsearch">
  <div class="panel-heading">Candidate Select</div>
  <div class="panel-body">
  								<div id="searchMsgError" class="alert alert-danger" style="text-align:center;display:none;"></div>								
								<div id="searchMsgSuccess" class="alert alert-success" style="text-align:center;display:none;"></div>
  					
  					
  					<c:if test="${ PROFILE eq null }">
  					Please enter the name of the candidate you wish to provide a reference for and press Search.
  					</c:if>
					<c:choose>
						<c:when test="${ PROFILE eq null }">
												
								<div class="input-group">																
		   							 <input type="text" class="form-control input-sm" id='applicant_filter' name='applicant_filter' placeholder="Search for Candidate">
		   							 <div class="input-group-btn">
		      							<a class="btn btn-primary" id='filter_applicant' href="#">
		        						<i class="glyphicon glyphicon-search" style="height:16px;"></i>
		      							</a>
		    						</div>
		  						</div>								
								<div class="input-group" id="candidatesFound" style="display:none;">
									<span class="input-group-addon">Select:</span>
									<select id="applicant_list" class="form-control" name="applicant_id">
									<option value="-1">--- Select Candidate ---</option>
									</select>
								</div>		
							
							<div id="candidate_info" style="padding-top:5px;display:none;">							
							<div class="table-responsive">      			 	       
      			 	       		<table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>
							    	<tr>
									    <td class="tableTitle">NAME:</td>
									    <td class="tableResult"><span id="candidate_name"></span></td>
							    	</tr>
							    	<tr>							    
									    <td class="tableTitle">ADDRESS:</td>
									    <td class="tableResult"><span id="candidate_address"></span></td>
							    	</tr><tr>							   
			                            <td class="tableTitle">TELEPHONE:</td>
									    <td class="tableResult"><span id="candidate_telephone"></span></td>					    
							   		</tr><tr>							   
			                            <td class="tableTitle">EMAIL:</td>
									    <td class="tableResult"><span id="candidate_email"></span></td>
							    	</tr>
								</tbody>
								</table>				
							</div>
							</div>
						</c:when>						
						<c:otherwise>
								<input type='hidden' name='applicant_id' value='${ PROFILE.UID }' />								 	
								<span style="font-size:18px;color:#008000;font-weight:bold;">${ PROFILE.fullName }</span>									    
						</c:otherwise>
					</c:choose>
															
	</div>
</div>
	
	
<!-- Referencee Information -------------------------------------------------------------------------->	

<div id="candidateFound" style="display:none;">	
	
<div class="panel panel-success">
<c:if test="${ mancheck ne null }">
  <div class="panel-heading">Competition Information</div>
  	<div class="panel-body">
  	<div class="alert alert-danger" id="section0Error" style="display:none;">Please enter the position of the person providing reference.</div>		
  		<input type="hidden" name="mancheck" value="Y" />	
									<div class="table-responsive"> 
		      			 	       		<table class="table table-striped table-condensed" style="font-size:12px;">							   
										    <tbody>
										    	<tr style="border-bottom:1px solid silver;">
												    <td class="tableTitle">COMP. #:</td>
												    <td class="tableResult">${JOB ne null ? JOB.getCompetitionNumber() : ''}
												    <input type='hidden' id='jobcomp' name='jobcomp' value="${JOB ne null ? JOB.getCompetitionNumber() : ''}">
												    </td>
											    </tr>
											    <tr>
												    <td class="tableTitle">REGION:</td>
												    <td class="tableResult">${ ASS ne null ? ASS[0].getRegionText() :''}</td>
											    </tr>
											    <tr>
												    <td class="tableTitle">POSITION:</td>
												    <td class="tableResult">${JOB ne null ? JOB.getPositionTitle() : ''}</td>
											    </tr>
											    <tr>
												    <td class="tableTitle">LOCATION:</td>
												    <td class="tableResult">${ASS ne null ? ASS[0].getLocation() > 0 ? ASS[0].getLocationText() : '' :''}</td>
											    </tr>
											    
										    </tbody>
									    </table>
									</div>
	
	
	</div>

</c:if>



  <div class="panel-heading">Referencee Information</div>
  	<div class="panel-body">
  	<div class="alert alert-danger" id="section0Error" style="display:none;">Please enter the position of the person providing reference.</div>
  		<c:if test="${mancheck eq null }">
  			<input type="hidden" name="ref_provider_name" value="<%=usr.getPersonnel().getFullName() %>" />	
  		</c:if>		
  		
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
													<c:when test="${ mancheck ne null }">
														<td class="tableTitle">CANDIDATE:</td>
											    		<td class="tableResult" style="text-transform:Capitalize;">${PROFILE.getFullName()}
														<input type='hidden' name='rapplicant_id' id='rapplicant_id' value='${ PROFILE.getSIN()}' />
													</c:when>
													</c:choose>
												<tr>
												    <td class="tableTitle">PROVIDING REFERENCE:</td>
												    <c:choose>
												    	<c:when test="${mancheck ne null}">
												    		<td class="tableResult"><input type="text" id="ref_provider_name" class="form-control input-sm" placeholder="Enter Provider" name="ref_provider_name" value=""/></td>
												    	</c:when>
												    	<c:otherwise>
												    		<td class="tableResult" style="text-transform:Capitalize;"><%= usr.getPersonnel().getFullNameReverse() %></td>
												    	</c:otherwise>
												    </c:choose>
												    
											    </tr>
											    <tr>
												    <td class="tableTitle">PROVIDER EMAIL:</td>
												    <c:choose>
												    	<c:when test="${ arefreq ne null}">
												    	    <td class="tableResult"><input type="text" id="ref_provider_email" class="form-control input-sm" placeholder="Enter your Email" name="ref_provider_email" value="${ arefreq.emailAddress }"/></td>
											    		</c:when>
											    		<c:when test="${ usr ne null}">
												    	    <td class="tableResult"><input type="text" id="ref_provider_email" class="form-control input-sm" placeholder="Enter your Email" name="ref_provider_email" value="${ mancheck ne null ? '' : usr.personnel.emailAddress }"/></td>
											    		</c:when>
											    		<c:otherwise>
											    			<td class="tableResult"><input type="text" id="ref_provider_email" class="form-control input-sm" placeholder="Enter your Email" name="ref_provider_email" value=""/></td>
											    		</c:otherwise>
												    </c:choose>
												</tr>
											    <tr style="border-bottom:1px solid silver;">
												    <td class="tableTitle">POSITION:</td>
												    <td class="tableResult"><input type="text" id="ref_provider_position" class="form-control input-sm" placeholder="Enter your Job Position" name="ref_provider_position" value='${ REFERENCE_BEAN ne null ? REFERENCE_BEAN.providedByPosition : "" }' /></td>
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
													    <input type="radio" name="Q1" id="Q1" value="Yes" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q1 eq 'Yes' ? "CHECKED" : "" } />Yes &nbsp;  
														<input type="radio" name="Q1" value="No" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q1 eq 'No' ? "CHECKED" : "" } />No
														</td>
													 </tr>
													 <tr>
													    <td>Q2.</td>
													    <td>How long have you known this teacher in a professional capacity?<br/>
													    <input type="text" class="form-control" name="Q2" value="${ REFERENCE_BEAN ne null ? REFERENCE_BEAN.q2 : '' }" />
													    </td>
													 </tr>
													 <tr>
													    <td>Q3.</td>
													    <td>How long have they worked in your school?<br/>
													    <input type="text" class="form-control" name="Q3" value="${ REFERENCE_BEAN ne null ? REFERENCE_BEAN.q3 : '' }" />
													    </td>
													 </tr>
													 <tr>
													    <td>Q4.</td>
													    <td>What has been their most recent assignment?<br/>
													    <textarea name="Q4" id="Q4" class="form-control">${ REFERENCE_BEAN ne null ? REFERENCE_BEAN.q4 : "" }</textarea></td>
													 </tr>
													 <tr>
													    <td>Q5.</td>
													    <td>Did this teacher complete a Professional Learning Plan? &nbsp;
													    <input type="radio" name="Q5" value="Yes" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q5 eq 'Yes' ? "CHECKED" : "" } />Yes &nbsp;
														<input type="radio" name="Q5" value="No" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q5 eq 'No' ? "CHECKED" : "" } />No
														</td>
													 </tr>
													 <tr>
													    <td>Q6.</td>
													    <td>Was the learning plan successfully followed and completed? &nbsp; 
													    <input type="radio" name="Q6" value="Yes" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q6 eq 'Yes' ? "CHECKED" : "" } />Yes  &nbsp;
														<input type="radio" name="Q6" value="No" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q6 eq 'No' ? "CHECKED" : "" } />No </td>
													 </tr>												 
										    	</tbody>
									    </table>
									</div>	
	</div>
</div>
		
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
																	On a scale of <%=val1%> to <%=val5Text%> please rate the teacher on the following statements 
																	(<%=val5Text%>-Proficient, <%=val4%>-Competent, <%=val3%>-Developing Competence, <%=val2%>-Needs Improvement):
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
													    	<input type="radio" name="Scale1" id="Scale1" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale1 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale1" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale1 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale1" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale1 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale1" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale1 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale1" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale1 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td>S2.</td>
													    <td>This teacher demonstrates knowledge of students:</td>
													    <td>
													       	<input type="radio" name="Scale2" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale2 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale2" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale2 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale2" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale2 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale2" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale2 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale2" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale2 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%>  
														</td>
													 </tr>
													 <tr>
													    <td>S3.</td>
													    <td>This teacher selects instructional goals:</td>
													    <td>
													        <input type="radio" name="Scale3" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale3 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale3" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale3 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale3" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale3 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale3" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale3 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale3" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale3 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td>S4.</td>
													    <td>This teacher demonstrates knowledge of resources:</td>
													    <td>
													        <input type="radio" name="Scale4" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale4 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale4" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale4 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale4" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale4 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale4" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale4 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale4" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale4 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td>S5.</td>
													    <td>This teacher designs coherent instruction:</td>
													    <td>
													        <input type="radio" name="Scale5" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale5 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale5" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale5 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale5" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale5 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale5" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale5 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale5" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale5 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%>  
														</td>
													 </tr>
													 <tr>
													    <td>S6.</td>
													    <td>This teacher designs appropriate student assesment:</td>
													    <td>
													        <input type="radio" name="Scale6" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale6 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale6" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale6 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale6" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale6 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale6" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale6 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale6" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale6 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
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
																	On a scale of <%=val1%> to <%=val5Text%> please rate the teacher on the following statements 
																	(<%=val5Text%>-Proficient, <%=val4%>-Competent, <%=val3%>-Developing Competence, <%=val2%>-Needs Improvement):
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
													    	<input type="radio" name="Scale7" id="Scale7" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale7 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale7" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale7 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale7" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale7 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale7" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale7 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale7" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale7 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td>S8.</td>
													    <td>This teacher establishes a culture for learning:</td>
													    <td>
													    	<input type="radio" name="Scale8" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale8 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale8" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale8 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale8" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale8 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale8" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale8 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale8" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale8 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td>S9.</td>
													    <td>This teacher demonstrates appropriate classroom procedures:</td>
													    <td>
													    	<input type="radio" name="Scale9" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale9 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale9" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale9 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale9" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale9 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale9" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale9 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale9" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale9 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td>S10.</td>
													    <td>This teacher manages student behavior:</td>
													    <td>
													    	<input type="radio" name="Scale10" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale10 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale10" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale10 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale10" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale10 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale10" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale10 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale10" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale10 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td>S11.</td>
													    <td>This teacher organizes physical space to meet the needs of individual learners:</td>
													    <td>
													    	<input type="radio" name="Scale11" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale11 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale11" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale11 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale11" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale11 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale11" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale11 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale11" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale11 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
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
																	On a scale of <%=val1%> to <%=val5Text%> please rate the teacher on the following statements 
																	(<%=val5Text%>-Proficient, <%=val4%>-Competent, <%=val3%>-Developing Competence, <%=val2%>-Needs Improvement):
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
													    <td>This teacher communicates clearly and accurately with students:</td>
													    <td>
													        <input type="radio" name="Scale12" id="Scale12" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale12 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale12" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale12 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale12" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale12 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale12" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale12 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale12" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale12 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%>	 
														</td>
													 </tr>
													 <tr>
													    <td>S13.</td>
													    <td>This teacher uses questioning and discussion techniques:</td>
													    <td>
													    	<input type="radio" name="Scale13" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale13 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale13" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale13 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale13" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale13 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale13" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale13 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale13" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale13 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%>
														</td>
													 </tr>
													 <tr>
													    <td>S14.</td>
													    <td>This teacher engages students in learning:</td>
													    <td>
													    	<input type="radio" name="Scale14" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale14 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale14" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale14 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale14" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale14 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale14" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale14 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale14" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale14 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%>
														</td>
													 </tr>
													 <tr>
													    <td>S15.</td>
													    <td>This teacher demonstrates and utilizes appropriate formative assessment strategies:</td>
													    <td>
													    	<input type="radio" name="Scale15" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale15 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale15" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale15 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale15" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale15 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale15" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale15 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale15" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale15 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%>
														</td>
													 </tr>
													 <tr>
													    <td>S16.</td>
													    <td>This teacher demonstrates flexibility and responsiveness:</td>
													    <td>
													    	<input type="radio" name="Scale16" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale16 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale16" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale16 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale16" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale16 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale16" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale16 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale16" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale16 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
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
																	On a scale of <%=val1%> to <%=val5Text%> please rate the teacher on the following statements 
																	(<%=val5Text%>-Proficient, <%=val4%>-Competent, <%=val3%>-Developing Competence, <%=val2%>-Needs Improvement):
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
													       	<input type="radio" name="Scale17" id="Scale17" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale17 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale17" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale17 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale17" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale17 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale17" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale17 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale17" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale17 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%>
														</td>
													 </tr>
													 <tr>
													    <td>S18.</td>
													    <td>This teacher maintains accurate records:</td>
													    <td>
													       	<input type="radio" name="Scale18" id="Scale18" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale18 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale18" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale18 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale18" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale18 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale18" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale18 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale18" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale18 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td>S19.</td>
													    <td>This teacher communicates with families and/or other community stakeholders:</td>
													    <td>
													       	<input type="radio" name="Scale19" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale19 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale19" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale19 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale19" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale19 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale19" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale19 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale19" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale19 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%>  
														</td>
													 </tr>
													 <tr>
													    <td>S20.</td>
													    <td>This teacher contributes to the school and district:</td>
													    <td>
													       	<input type="radio" name="Scale20" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale20 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale20" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale20 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale20" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale20 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale20" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale20 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale20" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale20 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%>
														</td>
													 </tr>
													 <tr>
													    <td>S21.</td>
													    <td>This teacher grows and develops professionally:</td>
													    <td>
													       	<input type="radio" name="Scale21" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale21 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale21" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale21 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale21" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale21 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale21" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale21 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale21" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale21 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%> 
														</td>
													 </tr>
													 <tr>
													    <td>S22.</td>
													    <td>This teacher shows professionalism:</td>
													    <td>
													       	<input type="radio" name="Scale22" value="<%=val1%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale22 eq val1 ? "CHECKED" : "" } /><%=val1%>  
															<input type="radio" name="Scale22" value="<%=val2%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale22 eq val2 ? "CHECKED" : "" } ><%=val2%> 
															<input type="radio" name="Scale22" value="<%=val3%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale22 eq val3 ? "CHECKED" : "" } ><%=val3%>  
															<input type="radio" name="Scale22" value="<%=val4%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale22 eq val4 ? "CHECKED" : "" } ><%=val4%>
															<%if(refscale.equals("5")){%>
																<input type="radio" name="Scale22" value="<%=val5%>" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale22 eq val5 ? "CHECKED" : "" } ><%=val5Text%>
															<%}%>
														</td>
													 </tr>										
													 <tr>
													    <td colspan=3>Additional Comments <br/>
													    <div id="d4c_Error" class="alert alert-danger" style="display:none;">ERROR: Charater limit exceeded. You are only allowed to input 2450 characters.</div>
													    <textarea class="form-control" id="d4c" name="d4c">${ REFERENCE_BEAN ne null ? REFERENCE_BEAN.domain4Comments : "" }</textarea>
													    <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2450 - Remain: <span id="d4c_remain">2450</span></div>
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
        set = 2450,
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
															<select name="Q7">
																<option value="-1">--- Select One ---</option>
																<option value="Category 1: Recommend for permanent position" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q7 eq 'Category 1: Recommend for permanent position' ? "SELECTED" : "" } >Category 1: Recommend for permanent position.</option>
																<option value="Category 2: Recommend for full year replacement" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q7 eq 'Category 2: Recommend for full year replacement' ? "SELECTED" : "" } >Category 2: Recommend for full year replacement.</option>
																<option value="Category 3: Do not recommend" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q7 eq 'Category 3: Do not recommend' ? "SELECTED" : "" } >Category 3: Do not recommend.</option>
															</select>
														</td>
													 </tr>
													 <tr>
													   	<td colspan=2>
													   	Additional Comments:
															<div id="Q7_Comment_Error" class="alert alert-danger" style="display:none;">ERROR: Charater limit exceeded. You are only allowed to input 2450 characters.</div>
															<textarea class="form-control" id="Q7_Comment" name="Q7_Comment">${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q7Comment ne null ? REFERENCE_BEAN.q7Comment : "" }</textarea>
															<div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2450 - Remain: <span id="Q7_Comment_remain">2450</span></div>
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
        set = 2450,
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
<INPUT class="btn btn-danger btn-xs" TYPE="RESET" VALUE="Reset Form"> &nbsp; 
<a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>
</div>


</div>
</form>
			                              
<c:if test="${ PROFILE ne null }">
  	<script> $("#candidateFound").show();</script>
</c:if>			                            
			                         
</body>
</html>
