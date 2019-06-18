<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
	ReferenceCheckRequestBean refReq = (ReferenceCheckRequestBean) request.getAttribute("REFERENCE_CHECK_REQUEST_BEAN");
  JobOpportunityBean job = (JobOpportunityBean) request.getAttribute("JOB");
  JobOpportunityAssignmentBean[] ass = (JobOpportunityAssignmentBean[]) request.getAttribute("JOB_ASSIGNMENTS");
  AdRequestBean ad = (AdRequestBean) request.getAttribute("AD_REQUEST_BEAN");
  ApplicantProfileBean profile = (ApplicantProfileBean) request.getAttribute("PROFILE");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
		
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		
		<style type="text/css">@import 'includes/home.css';</style>
		<style type="text/css">@import 'includes/form.css';</style>
		
		<script language="JavaScript" src="js/CalendarPopup.js"></script>
		<script language="JavaScript" src="js/common.js"></script>
		<script language="JavaScript" src="js/personnel_ajax_v1.js"></script>
		<script type="text/javascript" src="../js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
				<script>
			$('document').ready(function()
			{
				$('#btnSubmit').click(function(){
					
					var is_valid = true;
					if($('#ref_provider_name').val() == ''){
						is_valid = false;
						alert('Please enter PERSON PROVIDING REFERENCE.');
						$('#ref_provider_name').focus();
					} else if($('#ref_provider_position').val() == ''){
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
		
				
		</script>
	</head>
	
	<body style='margin:10px;'>
    
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
			                                <form action="addExternalTeacherReference.html" method="POST" name="admin_rec_form" id="admin_rec_form">
			                                	<input type='hidden' id='request_id' name='request_id' value='<%= refReq.getRequestId() %>' />
			                                	<input type='hidden' id='applicant_id' name='applicant_id' value='<%= profile.getUID() %>' />
			                                	<input type='hidden' id='confirm' name='confirm' value='true' />
			                                  <table border="0" cellspacing="2" cellpadding="2" class="maintable">
																					<tr>
																						<td class='displayHeaderTitle'>Region:</td>
																						<td><%= ass[0].getRegionText() %></td>
																					</tr>
																					<tr>
																						<td class='displayHeaderTitle'>Position:</td>
																						<td><%= job.getPositionTitle() %></td>
																					</tr>
																					<tr>
																						<td class='displayHeaderTitle'>Location:</td>
																						<td>
																							<%=(ass[0].getLocation() > 0)? ass[0].getLocationText():"&nbsp;"%>
																						</td>
																					</tr>
																					<tr>
																						<td class='displayHeaderTitle'>Comp. #:</td>
																						<td><%= job.getCompetitionNumber() %></td>
																					</tr>
																				</table>
																				<br>&nbsp;<br>
			
																				<table width="100%" border="0" cellspacing="2" cellpadding="2" class="mainbody">
																					<tr>
																							<td width="200" class='displayHeaderTitle'>Candidates Name:</td>
																							<td width="*"><%= profile.getFullName() %></td>
																					</tr>
																					
																					<tr>
																						<td width="200" class='displayHeaderTitle'>Reference check requested by:</td>
																						<td width="*"><%=refReq.getCheckRequester().getFullName() %></td>
																					</tr>
			
																					<tr>
																						<td width="200" class='displayHeaderTitle'>Person providing reference:</td>
																						<td><input type="text" name="ref_provider_name" id="ref_provider_name" size="40" class="requiredInputBox"></td>
																					</tr>
																					
																					<tr>
																						<td width="200" class='displayHeaderTitle'>Position:</td>
																						<td width="*"><input type="text" name="ref_provider_position" id="ref_provider_position" size="40" class="requiredInputBox"></td>
																					</tr>
																				</table>
																				<br>&nbsp;<br>
			
																				<table border="0" cellspacing="2" cellpadding="2" class="mainbody">
																					<tr>
																						<td colspan="2"><b>The following reference check must be completed and attached to the teacher recommendation form.</b><p></td>
																					</tr>
																					<tr>
																						<td>Did the candidate ask permission to use your name as a reference?</td>
																						<td><input type="radio" name="Q1" value="Yes">Yes  <input type="radio" name="Q1" value="No">No</td>
																					</tr>
																					<tr>
																						<td>How long have you known this teacher?</td>
																						<td><input type="text" name="Q2" size="40" class="requiredInputBox"></td>
																					</tr>
																					<tr>
																						<td>How long has he/she worked in your school?</td>
																						<td><input type="text" name="Q3" size="40" class="requiredInputBox"></td>
																					</tr>
																					<tr valign="top">
																						<td>What has been his/her teaching assignment this year?</td>
																						<td><textarea rows="10" name="Q4" cols="50" class="requiredInputBox" style='height:100px;'></textarea></td>
																					</tr>
																					<tr>
																						<td>Did this teacher complete a professional growth plan?</td>
																						<td><input type="radio" name="Q5" value="Yes">Yes  <input type="radio" name="Q5" value="No">No</td>
																					</tr>
																					<tr>
																						<td>Was the growth plan successfully followed?</td>
																						<td><input type="radio" name="Q6" value="Yes">Yes  <input type="radio" name="Q6" value="No">No</td>
																					</tr>
																					<tr>
																						<td>Has the teacher demonstrated leadership on your staff?</td>
																						<td><input type="radio" name="Q7" value="Yes">Yes  <input type="radio" name="Q7" value="No">No</td>
																					</tr>
																					<tr valign="top">
																						<td>If yes, please give examples: </td>
																						<td><textarea rows="10" name="Q7_Comment" cols="50" class="requiredInputBox" style='height:100px;'></textarea></td>
																					</tr>
																					<tr>
																						<td colspan="2">
																							<br>
																							<b>
																								On a scale of 1 to 5 with 1 being I highly agree and 5 being I highly 
																								disagree please rate the teacher on the following statements:
																							</b>
																						</td>
																					</tr>
																					<tr>
																						<td>This teacher demonstrates a positive attitude towards students:</td>
																						<td>
																							<input type="radio" name="Scale1" value="1">1  
																							<input type="radio" name="Scale1" value="2">2 
																							<input type="radio" name="Scale1" value="3">3  
																							<input type="radio" name="Scale1" value="4">4 
																							<input type="radio" name="Scale1" value="5">5 
																						</td>
																					</tr>
																					<tr>
																						<td>This teacher works collaboratively with other teachers:</td>
																						<td>
																							<input type="radio" name="Scale2" value="1">1  
																							<input type="radio" name="Scale2" value="2">2 
																							<input type="radio" name="Scale2" value="3">3  
																							<input type="radio" name="Scale2" value="4">4 
																							<input type="radio" name="Scale2" value="5">5 
																						</td>
																					</tr>
																					<tr>
																						<td>This teacher uses assessment to guide instruction:</td>
																						<td>
																							<input type="radio" name="Scale3" value="1">1  
																							<input type="radio" name="Scale3" value="2">2 
																							<input type="radio" name="Scale3" value="3">3  
																							<input type="radio" name="Scale3" value="4">4 
																							<input type="radio" name="Scale3" value="5">5 
																						</td>
																					</tr>
																					<tr>
																						<td>This teacher takes ownership for student learning:</td>
																						<td>
																							<input type="radio" name="Scale4" value="1">1  
																							<input type="radio" name="Scale4" value="2">2 
																							<input type="radio" name="Scale4" value="3">3  
																							<input type="radio" name="Scale4" value="4">4 
																							<input type="radio" name="Scale4" value="5">5 
																						</td>
																					</tr>
																					<tr>
																						<td>This teacher uses a variety of instructional strategies to address the needs of diverse learners:</td>
																						<td>
																							<input type="radio" name="Scale5" value="1">1  
																							<input type="radio" name="Scale5" value="2">2 
																							<input type="radio" name="Scale5" value="3">3  
																							<input type="radio" name="Scale5" value="4">4 
																							<input type="radio" name="Scale5" value="5">5 
																						</td>
																					</tr>
																					<tr>
																						<td>This teacher is a positive role model for students and staff:</td>
																						<td>
																							<input type="radio" name="Scale6" value="1">1  
																							<input type="radio" name="Scale6" value="2">2 
																							<input type="radio" name="Scale6" value="3">3  
																							<input type="radio" name="Scale6" value="4">4 
																							<input type="radio" name="Scale6" value="5">5 
																						</td>
																					</tr>
																					<tr>
																						<td>This teacher demonstrates a strong understanding of his/her curriculum responsibilities:</td>
																						<td>
																							<input type="radio" name="Scale7" value="1">1  
																							<input type="radio" name="Scale7" value="2">2 
																							<input type="radio" name="Scale7" value="3">3  
																							<input type="radio" name="Scale7" value="4">4 
																							<input type="radio" name="Scale7" value="5">5 
																						</td>
																					</tr>
																					<tr>
																						<td>This teacher practices good classroom management techniques:</td>
																						<td>
																							<input type="radio" name="Scale8" value="1">1  
																							<input type="radio" name="Scale8" value="2">2 
																							<input type="radio" name="Scale8" value="3">3  
																							<input type="radio" name="Scale8" value="4">4 
																							<input type="radio" name="Scale8" value="5">5 
																						</td>
																					</tr>
																					<tr>
																						<td>This teacher makes regular home contact with parents:</td>
																						<td>
																							<input type="radio" name="Scale9" value="1">1  
																							<input type="radio" name="Scale9" value="2">2 
																							<input type="radio" name="Scale9" value="3">3  
																							<input type="radio" name="Scale9" value="4">4 
																							<input type="radio" name="Scale9" value="5">5 
																						</td>
																					</tr>
																					<tr>
																						<td>This teacher understands and adheres to the principles of the ISSP process:</td>
																						<td>
																							<input type="radio" name="Scale10" value="1">1  
																							<input type="radio" name="Scale10" value="2">2 
																							<input type="radio" name="Scale10" value="3">3  
																							<input type="radio" name="Scale10" value="4">4 
																							<input type="radio" name="Scale10" value="5">5 
																						</td>
																					</tr>
																					<tr><td colspan="2"><br>&nbsp;<br></td></tr>
																					<tr>
																						<td colspan="2">Please identify the ways in which this teacher has been involved in building a positive atmosphere in your school:<br>
																							<textarea rows="3" name="Q8" cols="100" class="requiredInputBox" style='height:100px;'></textarea>
																						</td>
																					</tr>
																					<tr><td>&nbsp;</td></tr>
																					<tr valign="top">
																						<td colspan="2">If given the opportunity would you hire this teacher?<br>
																							<!--
																							<input type="radio" name="Q9" value="Yes">Yes 
																							<input type="radio" name="Q9" value="No">No
																							<input type="radio" name="Q9" value="With Reservation">With Reservation
																							-->
																							<select name="Q10" class="requiredInputBox">
																								<option value="-1">--- Select One ---</option>
																								<option value="Category 1: Recommend for permanent position" >Category 1: Recommend for permanent position.</option>
																								<option value="Category 2: Recommend for full year replacement">Category 2: Recommend for full year replacement.</option>
																								<option value="Category 3: Recommend for short term replacement">Category 3: Recommend for short term replacement.</option>
																								<option value="Category 4: Do not recommend">Category 4: Do not recommend.</option>
																								<option value="Category 5: Recommend with reservation">Category 5: Recommend with reservation.</option>
																							</select>
																							
																							<p>
																							Additional Comments:<br>
																							<textarea rows="4" name="Q9_Comment" cols="80" class="requiredInputBox" style='height:100px;'></textarea>
																						</td>
																					</tr>
																					<!--
																					<tr>
																						<td colspan="2">Overall, how would you rate this teacher:<br>
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
																				<input type="submit" value="Submit" id="btnSubmit" >&nbsp;<INPUT TYPE="RESET" VALUE="Reset Form">
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
