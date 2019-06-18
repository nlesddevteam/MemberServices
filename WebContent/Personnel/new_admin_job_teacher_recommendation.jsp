<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*,
                  com.esdnl.servlet.Form" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />

<%
  JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
  JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job);
  AdRequestBean ad = null;
  RequestToHireBean rth = null;
  User usr = (User)session.getAttribute("usr");
  
  Form f = (Form) request.getAttribute("FORM");
  
  if(job.getIsSupport().equals("N")){
	  ad = AdRequestManager.getAdRequestBean(job.getCompetitionNumber());
	  
  }else{
	  rth = RequestToHireManager.getRequestToHireByCompNum(job.getCompetitionNumber());
  }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link href="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/smoothness/jquery-ui.css" type="text/css" rel="Stylesheet" />
		<style type="text/css">@import 'includes/home.css';</style>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js" type="text/javascript"></script>
		<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
		<script type='text/javascript' src="js/common.js"></script>
		<script type='text/javascript' src="js/personnel_ajax_v2.js"></script>
		<script type='text/javascript'>
			$('document').ready(function() {
				$('#btn-refresh-candidate-info').click(function(){
					onCandidateSelected($('#candidate_name').val());
				});
				
				$('button').css({'font-size' : '0.8em'}).button();
				
				<%if(f != null){%>
					onError();
				<%}%>
			});
		</script> 
	</head>
	
	<body>
<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <table width="760" cellpadding="0" cellspacing="0" border="0" style="border-top: solid 1px #FFB700;border-bottom: solid 1px #FFB700;" align="center">
    <tr>
      <td>   
        <jsp:include page="admin_top_nav.jsp" flush="true">
        	<jsp:param value="false" name="showHead"/>
        </jsp:include>
        <table width="760" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="760" align="left" valign="top">
              <table width="760" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <jsp:include page="admin_menu.jsp" flush="true"/>
                  <td width="551" align="left" valign="top">		
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="100%" align="left" valign="top" style="padding-top:8px;">
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td class="displayPageTitle"  width="100%">Competition # <%=job.getCompetitionNumber()%> Candidate Recommendation</td>
                            </tr>
                            <%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){ %>
			                      	<tr>
				                      	<td class="messageText" style="padding-top:5px;" width="100%">Error(s):<br><%=(String)request.getAttribute("msg")%></td>
				                      </tr>
			                      <%} %>
                            <tr style="padding-top:8px;">
                              <td style="padding-top:10px;padding-bottom:10px;" width="100%">
                                	<form action="" method="POST" name="admin_rec_form" id="admin_rec_form">
                                	<input type='hidden' name='op' id='op' value='CONFIRM'/>
                                	<input type='hidden' name='comp_num' id='comp_num' value='<%=job.getCompetitionNumber() %>' />
                                	<fieldset><legend>Section 1</legend>
                                    <table width="100%" cellspacing="4" cellpadding="2" border="0">
                                    	<tr valign="top">
                                        <td width="100%">
                                          <table width="100%" cellspacing="0" cellpadding="0" border="0">
                                            <tr>
	                                            <td width="100%" align="left" style="padding:0px;">
	                                            		<table align="left" cellspacing="2" cellpadding="0" border="0">
			                                            	<tr>
				                                              <td valign="middle" class="displayHeaderTitle" style="padding-right:15px;" >Candidates Name:</td>
				                                              <td valign="middle" width="*">
				                                              	<job:JobShortlistSelect id="candidate_name" onChange="onCandidateSelected(this.value);" cls="requiredInputBox"  value='<%=(((f != null)&&(f.get("candidate_name") != null))?f.get("candidate_name"):"")%>' />
				                                              	<span id='candidate_loading_msg' style='color:#FF0000; font-family:Arial;font-size:10px;font-weight:bold; display:none;'>Loading...</span>
				                                              	<button id='btn-refresh-candidate-info' type='button' style='display:none;'>Refresh</button>
				                                              </td>
			                                              </tr>
			                                            </table>
	                                            </td>  
                                            </tr>
                                            <tr id="candidate_info" style="padding-top:5px;display:none;">
                                            	<td width="100%" align="left" style="padding:0px;">
                                            		<table width="100%" align="left" cellspacing="2" cellpadding="0" border="0" style='table-layout:fixed;'>
                                            			<tr style="padding-top:5px;">
			                                              <td width='112' valign="top" align="left" class="displayHeaderTitle">Address:</td>
			                                              <td width="*" align="left" valign="top" id="candidate_address" class="displayText">&nbsp;</td>
			                                            </tr>
			                                            <tr style="padding-top:8px;">
			                                              <td width='112' valign="top" class="displayHeaderTitle">Telephone:</td>
			                                              <td width="*" align="left" valign="top" id="candidate_telephone" class="displayText">&nbsp;</td>
			                                            </tr>
			                                            <tr style="padding-top:5px;">
			                                            	<td width="100%" colspan='2'>
			                                            		<table align="left" cellspacing="0" cellpadding="0" border="0">
			                                            			<tr>
			                                            				<td valign="middle" class="displayHeaderTitle" style='padding-top:5px;color: #003366;font-size:12px;'>
			                                            						Request Reference Check 
			                                            						<a href="" onclick="toggleRequestReferenceCheck();return false;"><img src='images/expand2.jpg' border="0" id='req_ref_chk_img'></a>
			                                            				</td>
			                                            			</tr>
			                                            			<tr id='request_reference_info' style='display:none;'>
			                                            				<td style='border:solid 1px #d4d4d4;padding-left: 0px;'>
			                                            					<table width="100%" align="left" cellspacing="0" cellpadding="5" border="0" style='background-color:#f4f4f4;'>
						                                            			<tr>
						                                            				<td valign="middle" class="displayHeaderTitle">
									                                              	Email Address:<br />
									                                              	<input type='text' id='referrer_email' name='referrer_email' style="width:225px;"  class='requiredInputBox' >&nbsp;&nbsp;
									                                              	<br />
									                                              	Reference Type:<br />
									                                              	<select name="reftype" class="requiredInputBox" id="reftype">
																						<option value="-1">--- Select Reference Type---</option>
																						<% if(job.getIsSupport().equals("N")){ %>
																						<option value="ADMIN">Administrator</option>
																						<option value="GUIDE">Guidance Counsellor</option>
																						<option value="TEACHER">Teacher</option>
																						<option value="EXTERNAL">External</option>
																						<%}else{ %>
																						<option value="MANAGE">Management</option>
																						<option value="SUPPORT">Support</option>
																						<%} %>
																					</select>
									                                              	<button type="button" onclick="onSendReferenceCheckRequest();">Send</button>
									                                              	<span id='sending_email_msg' style='color:#FF0000; font-family:Arial;font-size:10px;font-weight:bold; display:none;'>Sending...</span>
									                                              </td>
						                                            			</tr>
						                                            			<tr style="padding-top:5px;">
						                                            				<td> 
						                                            					<table align="left" cellspacing="0" cellpadding="0" border="0">
						                                            						<tr>
									                                            				<td valign="middle" class="displayHeaderTitle">
									                                            					Enter phone reference check?&nbsp;&nbsp;<button type="button" onclick="onManualReferenceCheckRequest();">OK</button>
												                                              </td>
						                                            						</tr>
						                                            					</table>
						                                            				</td>
						                                            			</tr>
						                                            			<tr id="request_response_row" style='display:none;'>
						                                            				<td id="request_response_msg"  valign="middle" class="displayText" style="color:#FF0000;"></td>
						                                            			</tr>
			                                            					</table>
			                                            				</td>
			                                            			</tr>
			                                            			<tr valign="top">
										                                     	<td id='current_ref_requests'class="displayText" style='padding-top:5px;'><span style='color:#FF0000;'>No requests sent.</span></td>
										                                    </tr>
			                                            			
			                                            			<tr>
			                                            				<td valign="middle" class="displayHeaderTitle" style='padding-top:10px;color: #003366;font-size:12px;'>
			                                            					Most Recent Candidate References
			                                            				</td>
			                                            			</tr>
			                                            			<tr valign="top">
										                                     	<td id='current_refs'class="displayText" style='padding-top:5px;'><span style='color:#FF0000;'>No references on file.</span></td>
										                                    </tr>
										                                    
										                                    <tr>
			                                            				<td valign="middle" class="displayHeaderTitle" style='padding-top:10px;color: #003366;font-size:12px;'>
			                                            					Most Recent Candidate Interview Summaries
			                                            				</td>
			                                            			</tr>
			                                            			<tr valign="top">
										                                     	<td id='current_interview_summaries' class="displayText" style='padding-top:5px;'><span style='color:#FF0000;'>No interview summaries on file.</span></td>
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
                                  </fieldset>
                                  <div id='candidate-recommendation-info' style='display:none;'>
                                  	<br>
	                                  <fieldset><legend>Section 2</legend>
	                                    <table width="100%" cellspacing="4" cellpadding="2" border="0">
	                                      
	                                   	  <tr valign="top">
	                                        <td width="33%">
	                                          <table cellspacing="0" cellpadding="0" width="100%">
	                                            <tr>
	                                              <td valign="top" class="displayHeaderTitle" width="50" valign="middle">School:</td>
	                                              <td valign="top" class="displayText" width="*" valign="middle"><%=(ass[0].getLocation() > 0)? ass[0].getLocationText():"&nbsp;"%></td>
	                                            </tr>
	                                          </table>
	                                        </td>
	                                        
	                                        <td width="33%">
	                                          <table cellspacing="0" cellpadding="0" width="100%">
	                                            <tr>
	                                              <td valign="top" class="displayHeaderTitle" width="50" valign="middle">Region:</td>
	                                              <td valign="top" class="displayText" width="*" valign="middle"><%=ass[0].getRegionText()%></td>
	                                            </tr>
	                                          </table>
	                                        </td>
	                                        
	                                        <td width="*">
	                                          <table cellspacing="0" cellpadding="0" width="100%">
	                                            <tr>
	                                              
	                                              <%if(job.getIsSupport().equals("N")){%>
	                                              	<td valign="top" class="displayHeaderTitle" width="50" valign="middle">% Unit:</td>
	                                              		<td valign="top" class="displayText" width="*" valign="middle"><%=new DecimalFormat("0.00").format(ad.getUnits())%></td>
	                                            	<%}else{ %>
	                                            		<td valign="top" class="displayText" width="*" valign="middle"></td>
	                                            		<td valign="top" class="displayText" width="*" valign="middle"></td>
	                                            	<%} %>
	                                            </tr>
	                                          </table>
	                                        </td>
	                                      </tr>
	                                      
	                                      <tr valign="top">
	                                        <td colspan="3">
	                                          <table cellspacing="0" cellpadding="0" width="100%" border="0">
	                                            <tr>
	                                              <td width="100%" valign="top" class="displayHeaderTitle">Position:&nbsp;
	                                              <%if(job.getIsSupport().equals("N")){%>
	                                              	<job:PositionType id='position' cls='requiredInputBox' onChange='onPositionTypeSelected(this);' value='<%=(((f != null)&&(f.get("position")!= null))?PositionTypeConstant.get(f.getInt("position")):null)%>' />
	                                              <%}else{ %>
	                                              	<%=rth.getJobTitle()%><input type="hidden" id="position" name="position" value="<%=rth.getPositionType()%>">
	                                              <%}%>
	                                              </td>
	                                            </tr>
	                                            <tr id='recommended_position_other_row' style='display:none;'>
		                                        		<td width="100%" style="padding-left:55px;">
		                                        			<textarea cols="40" rows="4" name="position_other" id="position_other"><%=(((f != null)&&(f.get("position_other")!=null))?f.get("position_other"):"")%></textarea>
		                                        		</td>
	                                      			</tr>
	                                          </table>
	                                        </td>
	                                     </tr>
	                                     
	                                     <tr valign="top">
	                                        <td colspan="3" width='100%'>
	                                        	<% if(job.getIsSupport().equals("N")){ %>
	                                          		<job:GradeSubjectUnitPercentage cls="requiredInputBox"/>
	                                          	<%} %>
	                                        </td>
	                                     </tr>
	                                     
	                                     <tr valign="top">
	                                     <% if(job.getIsSupport().equals("N")){ %>
	                                     	<td id='gsu_display' colspan="3" width='100%' class="displayText" style='padding-top:5px;border-top:solid 1px #e4e4e4;'><span style='color:#FF0000;'>None Added.</span></td>
	                                     <%} %>
	                                     </tr>
	                                  
	                                    </table>
	                                  </fieldset>
	                                  
	                                  <br>
	                                  
	                                  <fieldset><legend>Section 3</legend>
	                                    <table align="center" width="98%" cellspacing="2" cellpadding="2" border="0">
	                                      <tr>
	                                        <td valign="top" class="displayHeaderTitle" width="75%">
	                                        <% if(job.getIsSupport().equals("N")){ %>
	                                        		1.) Does this teacher own a Permanent Contract with the Board?
	                                        	<%}else{%>
	                                        		1.) Does this employee own a Permanent Contract with the Board?
	                                        	<%} %>
	                                        </td>
	                                        <td valign="top" class="displayText" id="perm_status" width="*">&nbsp;</td>
	                                      </tr>
	                                      
	                                      <tr>
	                                        <td valign="top" class="displayHeaderTitle" width="75%">2.) Does this appointment fill an existing position?</td>
	                                        <td valign="top" class="displayText" width="*">
	                                        	<% if(job.getIsSupport().equals("N")){ %>
	                                          		<%=(!ad.isVacantPosition()?"YES":"NO")%> - <%= ad.getJobType() %>
	                                          	<%}else{ %>
	                                          		<%=org.apache.commons.lang.StringUtils.isNotEmpty(rth.getPreviousIncumbent())?"Yes":"No" %>
	                                          	<%} %>
	                                        </td>
	                                      </tr>
	                                      <% if(job.getIsSupport().equals("N")){ %>
	                                      <%if(!ad.isVacantPosition() || (ad.getOwner() != null) 
	                                      		|| org.apache.commons.lang.StringUtils.isNotEmpty(ad.getVacancyReason())) {%>
		                                      <tr>
		                                        <td valign="top" class="displayText" colspan='2' style="padding-left:20px;">
		                                        		<% if(ad.getOwner() != null) {%>
		                                            	<U>Previous Teacher:</U><BR /><%=ad.getOwner().getFullnameReverse()%><BR>
		                                            <% } %>
		                                            <% if(org.apache.commons.lang.StringUtils.isNotEmpty(ad.getVacancyReason())) { %>
		                                            	<u>Reason For Vacancy:</u><BR /><%=ad.getVacancyReason()%>
		                                            <% } %>
		                                        </td>
		                                      </tr>
	                                      <%}%>
	                                      <%}else{%>
		                                      <tr>
		                                        <td valign="top" class="displayText" colspan='2' style="padding-left:20px;">
		                                        	<% if(org.apache.commons.lang.StringUtils.isNotEmpty(rth.getPreviousIncumbent())) { %>
		                                            	<u>Previous Incumbent:</u><BR /><%=rth.getPreviousIncumbent()%>
		                                            <% } %>
		                                        </td>
		                                      </tr>	                                      
	                                      
	                                      <%} %>
	                                      <tr>
	                                        <td valign="middle" class="displayHeaderTitle" width="75%">
	                                          3.) Please indicate employment status being recommended.
	                                        </td>
	                                        <td valign="middle" class="displayText" width="*">
	                                        	<% if(job.getIsSupport().equals("N")){ %>
	                                        		<job:EmploymentStatus id="recommended_status" cls='requiredInputBox' value='<%=(((f != null)&&(f.get("recommended_status") != null))?EmploymentConstant.get(f.getInt("recommended_status")):EmploymentConstant.TERM)%>' />
	                                        	<%}else{ %>
	                                        		<%=rth.getPositionTypeString()%><input type="hidden" id="recommended_status" name="recommended_status" value="<%=rth.getPositionType()%>">
	                                        	<%} %>
	                                        
	                                        
	                                        </td>
	                                      </tr>
	                                      <% if(job.getIsSupport().equals("Y")){ %>
	                                      <tr>
	                                        <td valign="middle" class="displayHeaderTitle" width="75%">
	                                          4.) Position Details
	                                        </td>
	                                      </tr>
	                                      <tr>
	                                        <td valign="top" class="displayText" colspan='2' style="padding-left:20px;">
	                                        	<table border='0'>
	                                        	<tr><td><u>Union:</u></td><td><%=rth.getUnionCodeString()%></td></tr>
	                                        	<tr><td><u>Position:</u></td><td><%=rth.getPositionNameString()%></td></tr>
	                                        	<tr><td><u>Salary:</u></td><td><%=rth.getPositionSalary()%></td></tr>
	                                        	</table>
	                                        </td>
	                                      </tr>
	                                      <%} %>	                                      	                                      
	                                      <tr>
	                                        <td valign="top" class="displayHeaderTitle" width="75%">
	                                        	<% if(job.getIsSupport().equals("N")){ %>
	                                          		4.) Start Date (mm/dd/yyyy):
	                                          	<%}else{ %>
	                                          		5.) Start Date (mm/dd/yyyy):
	                                          	<%} %>
	                                        </td>
	                                        <td valign="top" class="displayText" width="*">
	                                        	<% if(job.getIsSupport().equals("N")){ %>
	                                          		<%=ad.getFormatedStartDate()%>
	                                          	<%}else{ %>
	                                          		<%=rth.getStartDateFormatted()%>
	                                          	<%} %>
	                                        </td>
	                                      </tr>
	                                    </table>
	                                  </fieldset>
	                                  
	                                  <BR>
	                                  
	                                  <fieldset><legend>Section 4</legend>
	                                    <table align="center" width="98%" cellspacing="2" cellpadding="2" border="0">
	                                    <% if(job.getIsSupport().equals("N")){ %>
	                                      <tr>
	                                        <td class="displayHeaderTitle" width="75%">1.) Teaching methods completed:</td>
	                                        <td class="displayText" id="trn_mtd" width="*">&nbsp;</td>
	                                      </tr>
	                                      <tr>
	                                        <td class="displayHeaderTitle" width="75%">2.) Newfoundland Teacher Certification Level:</td>
	                                        <td class="displayText" id="cert_lvl" width="*">&nbsp;</td>
	                                      </tr>
	                                      <%} %>
	                                      <tr>
	                                        <td colspan="2" class="displayHeaderTitle">3.) Candidates Interviewed:</td>
	                                      </tr>
	                                      <tr>
	                                        <td colspan="2" class="displayText">
	                                        	<job:JobShortlistDisplay cls='displayText' />
	                                        </td>
	                                      </tr>
	                                      <tr>
	                                        <td colspan="2" class="displayHeaderTitle">4.) Interview Panel:</td>
	                                      </tr>
	                                      <tr>
	                                        <td colspan="2" class="displayText" style="padding-left:15px;">
	                                        	<textarea cols="40" rows="4" name="Interview_Panel" id="Interview_Panel"><%=(((f != null)&&(f.get("Interview_Panel") != null))?f.get("Interview_Panel"):"")%></textarea>
	                                        </td>
	                                      </tr>
	                                      <tr>
	                                        <td class="displayHeaderTitle">5.) Are the references satisfactory?</td>
	                                        <td class="displayText" >
	                                        	<input type="radio" name="References_Satisfactory" value="Yes" <%=(((f != null)&&(f.get("References_Satisfactory") != null)&& f.get("References_Satisfactory").equalsIgnoreCase("YES"))?"CHECKED":"")%>>Yes &nbsp;&nbsp;<input type="radio" name="References_Satisfactory" value="No" <%=(((f != null)&&(f.get("References_Satisfactory") != null)&& f.get("References_Satisfactory").equalsIgnoreCase("NO"))?"CHECKED":"")%>>No &nbsp;&nbsp;
																					</td>
	                                      </tr>
	                                      <tr>
	                                        <td class="displayHeaderTitle" width="75%">6.) Should any special conditions be attached to this appointment?</td>
	                                        <td class="displayText" width="*">
	                                        	<input type="radio" name="Special_Conditions" value="Yes" onclick="toggleRow('special_conditions_row', 'inline');" <%=(((f != null)&&(f.get("Special_Conditions") != null)&& f.get("Special_Conditions").equalsIgnoreCase("YES"))?"CHECKED":"")%>>Yes &nbsp;&nbsp;
																						<input type="radio" name="Special_Conditions" value="No" onclick="document.getElementById('Special_Conditions_Comment').value='';toggleRow('special_conditions_row', 'none');" <%=(((f != null)&&(f.get("Special_Conditions") != null)&& f.get("Special_Conditions").equalsIgnoreCase("NO"))?"CHECKED":"")%>>No
	                                        </td>
	                                      </tr>
	                                      <tr id="special_conditions_row" style='display:none;'>
	                                      	<td colspan="2" style='padding-left:15px;'>
	                                      		<table width="100%" cellspacing="0" cellpadding="0" border="0">
	                                      			<tr>
				                                    	<td class="displayHeaderTitle">If Yes, please explain.</td>
				                                    </tr>
				                                    <tr>
				                                        <td class="displayText">
				                                        	<textarea cols="40" rows="2" name="Special_Conditions_Comment" id="Special_Conditions_Comment"><%=(((f != null)&&(f.get("Special_Conditions_Comment") != null))?f.get("Special_Conditions_Comment"):"")%></textarea>
				                                        </td>
				                                    </tr>
	                                      		</table>
	                                      	</td>
	                                      </tr>
	                                      
	                                      <tr>
	                                        <td colspan="2" class="displayHeaderTitle">7.) Other comments:</td>
	                                      </tr>
	                                      <tr>
	                                        <td colspan="2" class="displayText" style="padding-left:15px;">
	                                        	<textarea cols="40" rows="4" name="Other_Comments" id="Other_Comments"><%=(((f != null)&&(f.get("Other_Comments") != null))?f.get("Other_Comments"):"")%></textarea>
	                                        </td>
	                                      </tr>
	                                   	</table>
	                              	 </fieldset>
	                              	
	                              	<BR>
	
											<fieldset><legend>Section 5</legend>
												<table align="center" width="98%" cellspacing="2" cellpadding="2" border="0">
													<tr>
														<td class="displayHeaderTitle">
														Recommended Candidate:
														</td>
														<td align="left" valign="top" id="candidate_name_s" class="displayText">&nbsp;</td>
													</tr>
	                                      			<tr>
	                                        			<td colspan="2" class="displayHeaderTitle">Recommended Candidate Comments:</td>
	                                      			</tr>
	                                      			<tr>
	                                        			<td colspan="2" class="displayText" style="padding-left:15px;">
	                                        				<textarea cols="40" rows="4" name="rec_cand_comments" id="rec_cand_comments"><%=(((f != null)&&(f.get("rec_cand_comments") != null))?f.get("rec_cand_comments"):"")%></textarea>
	                                        			</td>
	                                      			</tr>	                                      			
	                                      																									
													<tr>
														<td class="displayHeaderTitle" colspan="2">
															Other Recommendable Candidate: <job:JobShortlistSelect id="candidate_2" cls="requiredInputBox" value='<%=(((f != null)&&(f.get("candidate_2") != null))?f.get("candidate_2"):"")%>'/>
														</td>
													</tr>
	                                      			<tr>
	                                        			<td colspan="2" class="displayHeaderTitle">Other Recommendable Candidate Comments:</td>
	                                      			</tr>
	                                      			<tr>
	                                        			<td colspan="2" class="displayText" style="padding-left:15px;">
	                                        				<textarea cols="40" rows="4" name="rec_cand_comments2" id="rec_cand_comments2"><%=(((f != null)&&(f.get("rec_cand_comments2") != null))?f.get("rec_cand_comments2"):"")%></textarea>
	                                        			</td>
	                                      			</tr>													
													<tr>
														<td class="displayHeaderTitle">
															Other Recommendable Candidate: <job:JobShortlistSelect id="candidate_3" cls="requiredInputBox" value='<%=(((f != null)&&(f.get("candidate_3") != null))?f.get("candidate_3"):"")%>'/>
														</td>
													</tr>
	                                      			<tr>
	                                        			<td colspan="2" class="displayHeaderTitle">Other Recommendable Candidate Comments:</td>
	                                      			</tr>
	                                      			<tr>
	                                        			<td colspan="2" class="displayText" style="padding-left:15px;">
	                                        				<textarea cols="40" rows="4" name="rec_cand_comments3" id="rec_cand_comments3"><%=(((f != null)&&(f.get("rec_cand_comments3") != null))?f.get("rec_cand_comments3"):"")%></textarea>
	                                        			</td>
	                                      			</tr>													
												</table>
											</fieldset>
											
											<BR>
	
											<fieldset><legend>Section 6</legend>
												<table width="100%" cellspacing="2" cellpadding="2" border="0">
													<tr>
														<td class="displayHeaderTitle">Recommendation completed by:</td>
														<td class="displayText"><%= usr.getPersonnel().getFullNameReverse() %></td>
													</tr>
													
													<tr>
														<td class="displayHeaderTitle">Date:</td>
														<td class="displayText"><%= new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()) %></td>
													</tr>
												</table>
											</fieldset>
	
											<table width="100%" cellspacing="2" cellpadding="2" border="0">
												<tr>
													<td align="center" width="100%" class="displayText">
														<input type='submit' value='Submit Recommendation' onclick="return validateAdminComments();">
													</td>
												</tr>
											</table>
										</div>
										</form>
                              </td>
                            </tr>
                            
                            <tr style="padding-top:10px; padding-bottom:10px;">
                              <td  align="center" valign="middle">
                                <%if(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW")){%>
                                  <a href='admin_view_job_applicants.jsp'><img border='0' src='images/back-off.jpg' onmouseover="src='images/back-on.jpg';" onmouseout="src='images/back-off.jpg';"/></a>
                                <%}%>
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
  <jsp:include page="footer.jsp" flush="true" />
</body>
</html>
