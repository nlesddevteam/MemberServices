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

<%
	User usr = (User) session.getAttribute("usr");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
		
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		
		<style type="text/css">@import 'includes/home.css';</style>
		<style type="text/css">@import 'includes/form.css';</style>
		<script type="text/javascript" src="../js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
		<script language="JavaScript" src="js/CalendarPopup.js"></script>
		<script language="JavaScript" src="js/common.js"></script>
		<script language="JavaScript" src="js/personnel_ajax_v1.js"></script>
		
		<script>
			$('document').ready(function(){

				$('#filter_applicant').click(function(){search_applicants();});
				$('#applicant_filter').change(function(){search_applicants();});
				
				$('#applicant_list').change(function(){
					
					$.post('addTeacherReference.html?op=CANDIDATE_DETAILS',
						{
							uid : $('#applicant_list').val()
						},
						function(data){
							var xmlDoc=data;
						    
					    var addr_str = null;
					    if(xmlDoc.getElementsByTagName("ADDRESS1").length > 0)
					      addr_str = xmlDoc.getElementsByTagName("ADDRESS1")[0].childNodes[0].nodeValue;
					    if(xmlDoc.getElementsByTagName("ADDRESS2").length > 0)
					      addr_str = addr_str + "<BR>" + xmlDoc.getElementsByTagName("ADDRESS2")[0].childNodes[0].nodeValue;
					    addr_str = addr_str + "<BR>" + xmlDoc.getElementsByTagName("PROVINCE")[0].childNodes[0].nodeValue;
					    addr_str = addr_str + ", " + xmlDoc.getElementsByTagName("COUNTRY")[0].childNodes[0].nodeValue;
					    addr_str = addr_str + ", " + xmlDoc.getElementsByTagName("POSTAL-CODE")[0].childNodes[0].nodeValue;

					    $('#candidate_address').html(addr_str)
					       
					    //formatting phone numbers
					    var phone_str = null;
					    if(xmlDoc.getElementsByTagName("HOME-PHONE").length > 0)
					      phone_str = xmlDoc.getElementsByTagName("HOME-PHONE")[0].childNodes[0].nodeValue + " (home)";
					    if(xmlDoc.getElementsByTagName("WORK-PHONE").length > 0)
					    {
					      if(phone_str)
					        phone_str = phone_str + "<BR>" + xmlDoc.getElementsByTagName("WORK-PHONE")[0].childNodes[0].nodeValue + " (work)";
					      else
					        phone_str = xmlDoc.getElementsByTagName("WORK-PHONE")[0].childNodes[0].nodeValue + " (work)";
					    }
					    if(xmlDoc.getElementsByTagName("CELL-PHONE").length > 0)
					    {
					      if(phone_str)
					        phone_str = phone_str + "<BR>" + xmlDoc.getElementsByTagName("CELL-PHONE")[0].childNodes[0].nodeValue + " (cell)";
					      else
					        phone_str = xmlDoc.getElementsByTagName("CELL-PHONE")[0].childNodes[0].nodeValue + " (cell)";
					    }
					    if(!phone_str)
					      phone_str = 'NONE ON RECORD';
					      
					    $('#candidate_telephone').html(phone_str);
						    
					    $('#candidate_info').show();
						}
					);
				});

				$('#btnSubmit').click(function(){

					var is_valid = true;

					if($('#applicant_list').val() == -1) {
						is_valid = false;
						alert('Please select candidate from dropdown list.');
						$('#applicant_list').focus();
					}
					else if($('#ref_provider_position').val() == ''){
						is_valid = false;
						alert('Please enter PROVIDER POSITION.');
						$('#ref_provider_position').focus();
					}
					else if(!$("input[name='Q1']:checked").val()) {
						is_valid = false;
						alert('Please answer Q1.');
					}
					else if($("input[name='Q2']").val() == '') {
						is_valid = false;
						alert('Please answer Q2.');
					}
					else if($("input[name='Q3']").val() == '') {
						is_valid = false;
						alert('Please answer Q3.');
					}
					else if($("textarea[name='Q4']").val() == '') {
						is_valid = false;
						alert('Please answer Q4.');
					}
					else if(!$("input[name='Q5']:checked").val()) {
						is_valid = false;
						alert('Please answer Q5.');
					}
					else if(!$("input[name='Q6']:checked").val()) {
						is_valid = false;
						alert('Please answer Q6.');
					}
					else if(!$("input[name='Q7']:checked").val()) {
						is_valid = false;
						alert('Please answer Q7.');
					}
					else if($("input[name='Q7']:checked").val()=='Yes' && $("textarea[name='Q7_Comment']").val() == '') {
						is_valid = false;
						alert('Please provide additional comments for Q7.');
					}
					else if(!$("input[name='Scale1']:checked").val()) {
						is_valid = false;
						alert('Please answer S1.');
					}
					else if(!$("input[name='Scale2']:checked").val()) {
						is_valid = false;
						alert('Please answer S2.');
					}
					else if(!$("input[name='Scale3']:checked").val()) {
						is_valid = false;
						alert('Please answer S3.');
					}
					else if(!$("input[name='Scale4']:checked").val()) {
						is_valid = false;
						alert('Please answer S4.');
					}
					else if(!$("input[name='Scale5']:checked").val()) {
						is_valid = false;
						alert('Please answer S5.');
					}
					else if(!$("input[name='Scale6']:checked").val()) {
						is_valid = false;
						alert('Please answer S6.');
					}
					else if(!$("input[name='Scale7']:checked").val()) {
						is_valid = false;
						alert('Please answer S7.');
					}
					else if(!$("input[name='Scale8']:checked").val()) {
						is_valid = false;
						alert('Please answer S8.');
					}
					else if(!$("input[name='Scale9']:checked").val()) {
						is_valid = false;
						alert('Please answer S9.');
					}
					else if(!$("input[name='Scale10']:checked").val()) {
						is_valid = false;
						alert('Please answer S10.');
					}
					else if($("textarea[name='Q8']").val() == '') {
						is_valid = false;
						alert('Please answer Q8.');
					}/*
					else if(!$("input[name='Q9']:checked").val()) {
						is_valid = false;
						alert('Please answer Q9.');
					}*/
					else if($("select[name='Q10']").val() == -1) {
						is_valid = false;
						alert('Please answer Q9.');
						$("select[name='Q10']").focus();
					}

					return is_valid;
				});
							
			});

			function search_applicants(){
				if($('#applicant_filter').val() == '') {
					alert('Please enter search criteria.');
					return; 
				}
				
				$('#filter_applicant').html('Loading Please Wait...');

				$('#candidate_info').hide();
				$('#applicant_list').html("<option value='-1'>--- Select One ---</option>");
				
				
				$.post('addTeacherReference.html?op=APPLICANT_FILTER',
					{
						criteria : $('#applicant_filter').val()
					},
					function(data){
						var xmlDoc=data;
					    
				    //formatting reference check request beans
				    
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

				    	//$('#applicant_list').effect("highlight", {}, 3000)

				    	$('#applicant_list').focus();
				    }
				    else
					    alert('Search did not find any applicants matching your criteria.');

				    $('#filter_applicant').html('Search');
					}
				);
				
			}	
		</script>
	</head>
	
	<body style='margin:10px;'>
  
  <esd:SecurityCheck />
<!--
	// Top Nav/Logo Container
	// This will be included
-->
	<table width="800" cellpadding="0" cellspacing="5" border="0" align="center" style='border:solid 1px #333333;background-color:#C0C0C0;'>
		<tr>
			<td style='border:solid 1px #333333;background-color:#FFFFFF;'>
				<table width="760" cellpadding="0" cellspacing="0" border="0" align="center">
			    <tr>
			      <td align="left" style='padding-top:15px;'>
			      	<img src="images/refheader.gif" width='760'><br>
			      </td>
			    </tr>
			  </table>
			  <table width="760" cellpadding="0" cellspacing="0" border="0" align="center">
			    <tr>
			      <td>   
			        <table width="760" cellpadding="0" cellspacing="0" border="0">
			          <tr>
			            <td width="760" align="left" valign="top">
			              <table width="760" cellpadding="0" cellspacing="0" border="0">
			                <tr>
			                  <td width="600" align="left" valign="top">		
			                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
			                      <tr>
			                        <td width="100%" align="left" valign="top" style="padding-top:8px;">
			                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
			                            <tr>
			                              <td class="displayPageTitle"  width="100%">Candidate Reference Check</td>
			                            </tr>
			                            <%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){ %>
			                            	<tr>
				                              <td class="messageText"  width="100%"><%=(String)request.getAttribute("msg")%></td>
				                            </tr>
			                            <%} %>
			                            <tr style="padding-top:8px;">
			                              <td style="padding-bottom:10px;" width="100%">
			                                <form action="addTeacherReference.html" method="POST" name="admin_rec_form" id="admin_rec_form">
			                                	<input type='hidden' name="confirm" value="true" />
			                                	
			                                	<c:if test="${ REFERENCE_BEAN ne null}">
			                                		<input type='hidden' name='reference_id' value='${ REFERENCE_BEAN.id }' />
			                                	</c:if>
			
																				<table width="100%" border="0" cellspacing="2" cellpadding="2" class="mainbody">
																					<tr>
																							<td width="200" class='displayHeaderTitle' valign="top">Candidate:</td>
																							<td width="*">
																								<c:choose>
																									<c:when test="${ PROFILE eq null }">
																										<input type='text' id='applicant_filter' name='applicant_filter' size='40' class='requiredInputBox'/>
																										<a id='filter_applicant' href='#'>Search</a><br />
																										<select id="applicant_list" name="applicant_id" class='requiredInputBox' style='width:230px;'>
																											<option value="-1">--- Select One ---</option>
																										</select><br />
																										<table id="candidate_info" cellspacing='0' cellpadding='0' style="padding-top:5px;display:none;">
																											<tr>
					                                            	<td width="100%" align="left" style="padding:0px;">
					                                            		<table align="left" cellspacing="0" cellpadding="0" border="0">
					                                            			<tr style="padding-top:5px;">
								                                              <td valign="top" class="displayHeaderTitle" style='width:75px;'>Address:</td>
								                                              <td width="*" align="left" valign="top" id="candidate_address" class="displayText">&nbsp;</td>
								                                            </tr>
								                                            <tr style="padding-top:10px;">
								                                              <td valign="top" class="displayHeaderTitle">Telephone:</td>
								                                              <td width="*" align="left" valign="top" id="candidate_telephone" class="displayText">&nbsp;</td>
								                                            </tr>
					                                            		</table>
					                                            	</td>
					                                            </tr>
																										</table>
																									</c:when>
																									<c:otherwise>
																										<input type='hidden' name='applicant_id' value='${ PROFILE.UID }' />
																										${ PROFILE.fullName }
																									</c:otherwise>
																								</c:choose>
																							</td>
																					</tr>
																					
																					<tr>
																						<td width="200" class='displayHeaderTitle'>Person providing reference:</td>
																						<td>
																							<input type="hidden" name="ref_provider_name" value="<%=usr.getPersonnel().getFullName() %>" /> 
																							<%= usr.getPersonnel().getFullName() %>
																						</td>
																					</tr>
																					
																					<tr>
																						<td width="200" class='displayHeaderTitle'>Position:</td>
																						<td width="*">
																							<input type="text" 
																								id="ref_provider_position" 
																								name="ref_provider_position" 
																								size="40" 
																								class="requiredInputBox"
																								value='${ REFERENCE_BEAN ne null ? REFERENCE_BEAN.referenceProviderPosition : "" }' />
																						</td>
																					</tr>
																				</table>
																				<br>&nbsp;<br>
			
																				<table border="0" cellspacing="2" cellpadding="2" class="mainbody">
																					<tr>
																						<td colspan="2"><b>The following reference check must be completed and attached to the teacher recommendation form.</b><p></td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>Q1.</span> Did the candidate ask permission to use your name as a reference?</td>
																						<td><input type="radio" name="Q1" value="Yes" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q1 eq 'Yes' ? "CHECKED" : "" } />Yes  <input type="radio" name="Q1" value="No" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q1 eq 'No' ? "CHECKED" : "" } />No</td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>Q2.</span> How long have you known this teacher?</td>
																						<td><input type="text" name="Q2" size="40" class="requiredInputBox" value="${ REFERENCE_BEAN ne null ? REFERENCE_BEAN.q2 : '' }" /></td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>Q3.</span> How long has he/she worked in your school?</td>
																						<td><input type="text" name="Q3" size="40" class="requiredInputBox" value="${ REFERENCE_BEAN ne null ? REFERENCE_BEAN.q3 : '' }" /></td>
																					</tr>
																					<tr valign="top">
																						<td colspan='2'><span style='font-weight:bold;'>Q4.</span> What has been his/her teaching assignment this year?</td>
																					</tr>
																					<tr valign="top">
																						<td style="padding-left:25px;" colspan='2'><textarea rows="10" name="Q4" cols="100" class="requiredInputBox" style='height:100px;'>${ REFERENCE_BEAN ne null ? REFERENCE_BEAN.q4 : "" }</textarea></td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>Q5.</span> Did this teacher complete a professional growth plan?</td>
																						<td><input type="radio" name="Q5" value="Yes" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q5 eq 'Yes' ? "CHECKED" : "" } />Yes  <input type="radio" name="Q5" value="No" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q5 eq 'No' ? "CHECKED" : "" } />No</td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>Q6.</span> Was the growth plan successfully followed?</td>
																						<td><input type="radio" name="Q6" value="Yes" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q6 eq 'Yes' ? "CHECKED" : "" } />Yes  <input type="radio" name="Q6" value="No" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q6 eq 'No' ? "CHECKED" : "" } />No</td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>Q7.</span> Has the teacher demonstrated leadership on your staff?</td>
																						<td><input type="radio" name="Q7" value="Yes" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q7 eq 'Yes' ? "CHECKED" : "" } />Yes  <input type="radio" name="Q7" value="No" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q7 eq 'No' ? "CHECKED" : "" } />No</td>
																					</tr>
																					<tr valign="top">
																						<td style="padding-left:25px;" colspan='2'>If yes, please give examples: </td>
																					</tr>
																					<tr valign="top">
																						<td style="padding-left:25px;" colspan='2' ><textarea rows="10" name="Q7_Comment" cols="100" class="requiredInputBox" style='height:100px;'>${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q7Comment ne null ? REFERENCE_BEAN.q7Comment : "" }</textarea></td>
																					</tr>
																					<tr>
																						<td colspan="2">
																							<br>
																							<b>
																								On a scale of 1 to 5 with <u><span style='color:red;'>1 being HIGHLY AGREE</span></u> and <u><span style='color:red;'>5 being HIGHLY 
																								DISAGREE</span></u> please rate the teacher on the following statements:
																							</b>
																						</td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>S1.</span> This teacher demonstrates a positive attitude towards students:</td>
																						<td>
																							<input type="radio" name="Scale1" value="1" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale1 eq '1' ? "CHECKED" : "" } />1  
																							<input type="radio" name="Scale1" value="2" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale1 eq '2' ? "CHECKED" : "" } >2 
																							<input type="radio" name="Scale1" value="3" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale1 eq '3' ? "CHECKED" : "" } >3  
																							<input type="radio" name="Scale1" value="4" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale1 eq '4' ? "CHECKED" : "" } >4 
																							<input type="radio" name="Scale1" value="5" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale1 eq '5' ? "CHECKED" : "" } >5
																							<input type="radio" name="Scale1" value="N/A" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale1 eq 'N/A' ? "CHECKED" : "" } >N/A 
																						</td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>S2.</span> This teacher works collaboratively with other teachers:</td>
																						<td>
																							<input type="radio" name="Scale2" value="1" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale2 eq '1' ? "CHECKED" : "" } />1  
																							<input type="radio" name="Scale2" value="2" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale2 eq '2' ? "CHECKED" : "" } />2 
																							<input type="radio" name="Scale2" value="3" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale2 eq '3' ? "CHECKED" : "" } />3  
																							<input type="radio" name="Scale2" value="4" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale2 eq '4' ? "CHECKED" : "" } />4 
																							<input type="radio" name="Scale2" value="5" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale2 eq '5' ? "CHECKED" : "" } />5
																							<input type="radio" name="Scale2" value="N/A" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale2 eq 'N/A' ? "CHECKED" : "" } />N/A 
																						</td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>S3.</span> This teacher uses assessment to guide instruction:</td>
																						<td>
																							<input type="radio" name="Scale3" value="1" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale3 eq '1' ? "CHECKED" : "" } />1  
																							<input type="radio" name="Scale3" value="2" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale3 eq '2' ? "CHECKED" : "" } />2 
																							<input type="radio" name="Scale3" value="3" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale3 eq '3' ? "CHECKED" : "" } />3  
																							<input type="radio" name="Scale3" value="4" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale3 eq '4' ? "CHECKED" : "" } />4 
																							<input type="radio" name="Scale3" value="5" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale3 eq '5' ? "CHECKED" : "" } />5
																							<input type="radio" name="Scale3" value="N/A" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale3 eq 'N/A' ? "CHECKED" : "" } />N/A 
																						</td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>S4.</span> This teacher takes ownership for student learning:</td>
																						<td>
																							<input type="radio" name="Scale4" value="1" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale4 eq '1' ? "CHECKED" : "" } />1  
																							<input type="radio" name="Scale4" value="2" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale4 eq '2' ? "CHECKED" : "" } />2 
																							<input type="radio" name="Scale4" value="3" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale4 eq '3' ? "CHECKED" : "" } />3  
																							<input type="radio" name="Scale4" value="4" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale4 eq '4' ? "CHECKED" : "" } />4 
																							<input type="radio" name="Scale4" value="5" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale4 eq '5' ? "CHECKED" : "" } />5 
																							<input type="radio" name="Scale4" value="N/A" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale4 eq 'N/A' ? "CHECKED" : "" } />N/A 
																						</td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>S5.</span> This teacher uses a variety of instructional strategies to address the needs of diverse learners:</td>
																						<td>
																							<input type="radio" name="Scale5" value="1" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale5 eq '1' ? "CHECKED" : "" } />1  
																							<input type="radio" name="Scale5" value="2" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale5 eq '2' ? "CHECKED" : "" } />2 
																							<input type="radio" name="Scale5" value="3" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale5 eq '3' ? "CHECKED" : "" } />3  
																							<input type="radio" name="Scale5" value="4" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale5 eq '4' ? "CHECKED" : "" } />4 
																							<input type="radio" name="Scale5" value="5" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale5 eq '5' ? "CHECKED" : "" } />5 
																							<input type="radio" name="Scale5" value="N/A" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale5 eq 'N/A' ? "CHECKED" : "" } />N/A 
																						</td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>S6.</span> This teacher is a positive role model for students and staff:</td>
																						<td>
																							<input type="radio" name="Scale6" value="1" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale6 eq '1' ? "CHECKED" : "" } />1  
																							<input type="radio" name="Scale6" value="2" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale6 eq '2' ? "CHECKED" : "" } />2 
																							<input type="radio" name="Scale6" value="3" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale6 eq '3' ? "CHECKED" : "" } />3  
																							<input type="radio" name="Scale6" value="4" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale6 eq '4' ? "CHECKED" : "" } />4 
																							<input type="radio" name="Scale6" value="5" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale6 eq '5' ? "CHECKED" : "" } />5
																							<input type="radio" name="Scale6" value="N/A" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale6 eq 'N/A' ? "CHECKED" : "" } />N/A  
																						</td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>S7.</span> This teacher demonstrates a strong understanding of his/her curriculum responsibilities:</td>
																						<td>
																							<input type="radio" name="Scale7" value="1" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale7 eq '1' ? "CHECKED" : "" } />1  
																							<input type="radio" name="Scale7" value="2" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale7 eq '2' ? "CHECKED" : "" } />2 
																							<input type="radio" name="Scale7" value="3" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale7 eq '3' ? "CHECKED" : "" } />3  
																							<input type="radio" name="Scale7" value="4" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale7 eq '4' ? "CHECKED" : "" } />4 
																							<input type="radio" name="Scale7" value="5" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale7 eq '5' ? "CHECKED" : "" } />5
																							<input type="radio" name="Scale7" value="N/A" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale7 eq 'N/A' ? "CHECKED" : "" } />N/A  
																						</td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>S8.</span> This teacher practices good classroom management techniques:</td>
																						<td>
																							<input type="radio" name="Scale8" value="1" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale8 eq '1' ? "CHECKED" : "" } />1  
																							<input type="radio" name="Scale8" value="2" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale8 eq '2' ? "CHECKED" : "" } />2 
																							<input type="radio" name="Scale8" value="3" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale8 eq '3' ? "CHECKED" : "" } />3  
																							<input type="radio" name="Scale8" value="4" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale8 eq '4' ? "CHECKED" : "" } />4 
																							<input type="radio" name="Scale8" value="5" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale8 eq '5' ? "CHECKED" : "" } />5
																							<input type="radio" name="Scale8" value="N/A" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale8 eq 'N/A' ? "CHECKED" : "" } />N/A 
																						</td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>S9.</span> This teacher makes regular home contact with parents:</td>
																						<td>
																							<input type="radio" name="Scale9" value="1" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale9 eq '1' ? "CHECKED" : "" } />1  
																							<input type="radio" name="Scale9" value="2" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale9 eq '2' ? "CHECKED" : "" } />2 
																							<input type="radio" name="Scale9" value="3" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale9 eq '3' ? "CHECKED" : "" } />3  
																							<input type="radio" name="Scale9" value="4" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale9 eq '4' ? "CHECKED" : "" } />4 
																							<input type="radio" name="Scale9" value="5" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale9 eq '5' ? "CHECKED" : "" } />5
																							<input type="radio" name="Scale9" value="N/A" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale9 eq 'N/A' ? "CHECKED" : "" } />N/A  
																						</td>
																					</tr>
																					<tr>
																						<td><span style='font-weight:bold;'>S10.</span> This teacher understands and adheres to the principals of the ISSP process:</td>
																						<td>
																							<input type="radio" name="Scale10" value="1" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale10 eq '1' ? "CHECKED" : "" } />1  
																							<input type="radio" name="Scale10" value="2" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale10 eq '2' ? "CHECKED" : "" } />2 
																							<input type="radio" name="Scale10" value="3" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale10 eq '3' ? "CHECKED" : "" } />3  
																							<input type="radio" name="Scale10" value="4" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale10 eq '4' ? "CHECKED" : "" } />4 
																							<input type="radio" name="Scale10" value="5" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale10 eq '5' ? "CHECKED" : "" } />5
																							<input type="radio" name="Scale10" value="N/A" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.scale10 eq 'N/A' ? "CHECKED" : "" } />N/A  
																						</td>
																					</tr>
																					<tr><td colspan="2"><br>&nbsp;<br></td></tr>
																					<tr>
																						<td colspan="2">
																							<span style='font-weight:bold;'>Q8.</span> Please identify the ways in which this teacher has been involved in building a positive atmosphere in your school:
																						</td>
																					</tr>
																					<tr>
																						<td style="padding-left:25px;" colspan="2">
																							<textarea rows="3" name="Q8" cols="100" class="requiredInputBox" style='height:100px;'>${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q8 ne null ? REFERENCE_BEAN.q8 : "" }</textarea>
																						</td>
																					</tr>
																					<tr><td>&nbsp;</td></tr>
																					<tr valign="top">
																						<td colspan="2"><span style='font-weight:bold;'>Q9.</span> If given the opportunity would you hire this teacher?<br>
																							<!--
																							<input type="radio" name="Q9" value="Yes">Yes 
																							<input type="radio" name="Q9" value="No">No
																							<input type="radio" name="Q9" value="With Reservation">With Reservation
																							-->
																							<select name="Q10" class="requiredInputBox">
																								<option value="-1">--- Select One ---</option>
																								<option value="Category 1: Recommend for permanent position" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q10 eq 'Category 1: Recommend for permanent position' ? "SELECTED" : "" } >Category 1: Recommend for permanent position.</option>
																								<option value="Category 2: Recommend for full year replacement" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q10 eq 'Category 2: Recommend for full year replacement' ? "SELECTED" : "" } >Category 2: Recommend for full year replacement.</option>
																								<option value="Category 3: Recommend for short term replacement" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q10 eq 'Category 3: Recommend for short term replacement' ? "SELECTED" : "" } >Category 3: Recommend for short term replacement.</option>
																								<option value="Category 4: Do not recommend" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q10 eq 'Category 4: Do not recommend' ? "SELECTED" : "" } >Category 4: Do not recommend.</option>
																								<option value="Category 5: Recommend with reservation" ${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q10 eq 'Category 5: Recommend with reservation' ? "SELECTED" : "" } >Category 5: Recommend with reservation.</option>
																							</select>
																							
																							<p>
																							Additional Comments:<br>
																							<textarea rows="4" name="Q9_Comment" cols="100" class="requiredInputBox" style='height:100px;'>${ REFERENCE_BEAN ne null and REFERENCE_BEAN.q9Comment ne null ? REFERENCE_BEAN.q9Comment : "" }</textarea>
																						</td>
																					</tr>
																					<!-- 
																					<tr>
																						<td colspan="2"><span style='font-weight:bold;'>Q10.</span> Overall, how would you rate this teacher:<br>
																							
																							<!--
																							<input type="radio" name="Q10" value="Poor">Poor  
																							<input type="radio" name="Q10" value="Fair">Fair 
																							<input type="radio" name="Q10" value="Good">Good  
																							<input type="radio" name="Q10" value="Very Good">Very Good 
																							<input type="radio" name="Q10" value="Excellent">Excellent
																							
																						</td>
																					</tr>
																					--> 
																				</table>
																				<p>
																				<input id="btnSubmit" type="submit" value="Submit" >&nbsp;<INPUT TYPE="RESET" VALUE="Reset Form">
																			</form>
			                              </td>
			                            </tr>
			                          </table>
			                        </td>
			                      </tr>
			                    </table>
			                  </td>						
			                </tr>
			              </table>
			            </td>
			          </tr>
			        </table>
			      </td>
			    </tr>
			  </table>
  		</td>
  	</tr>
  	<tr>
  		<td style='border:solid 1px #333333;'>
		  	<jsp:include page="footer.jsp" flush="true">
		  		<jsp:param name="width" value="800"/>
		  	</jsp:include>
	  	</td>
  	</tr>
  </table>
</body>
</html>
