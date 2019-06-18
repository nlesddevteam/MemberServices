<%@ page language="java"
        session="true"
         import="com.awsd.ppgp.*,com.awsd.security.*,java.text.*,com.awsd.common.*"
        isThreadSafe="false"%>
        
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/ppgp.tld" prefix="pgp" %>

<esd:SecurityCheck permissions='PPGP-VIEW' />

<%
	User usr = (User) session.getAttribute("usr");
	PPGP ppgp = (PPGP) request.getAttribute("PPGP");
	PPGPGoal goal = (PPGPGoal) request.getAttribute("PPGP_GOAL");
	PPGPTask task = (PPGPTask) request.getAttribute("PPGP_TASK");
	String years[] = ppgp.getSchoolYear().split("-");
	Integer cyear = Integer.parseInt(years[1]);
	String domainid="0";
	String strengthid="0";
	String catid="0";
	String gradeid="0";
	String subjectid="0";
	String topicid="0";
	String stopicid="0";
	
	if(!(request.getAttribute("domainid") == null)){
		domainid=request.getAttribute("domainid").toString();
	}
	if(!(request.getAttribute("strengthid") == null)){
		strengthid=request.getAttribute("strengthid").toString();
	}
	if(!(request.getAttribute("cat_id") == null)){
		catid=request.getAttribute("cat_id").toString();
	}
	if(!(request.getAttribute("grade_id") == null)){
		gradeid=request.getAttribute("grade_id").toString();
	}
	if(!(request.getAttribute("subject_id") == null)){
		subjectid=request.getAttribute("subject_id").toString();
	}
	if(!(request.getAttribute("topic_id") == null)){
		topicid=request.getAttribute("topic_id").toString();
	}
	if(!(request.getAttribute("stopic_id") == null)){
		stopicid=request.getAttribute("stopic_id").toString();
	}
	
%>

<html>
	<head>
		<title>Professional Growth Plan</title>
		<link rel="stylesheet" href="css/bootstrap.min.css">
				<link rel="stylesheet" href="css/growthplan.css">
		<link rel="stylesheet" href="css/smoothness/jquery-ui.custom.css">
		<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>

		<script type="text/javascript" src="js/jquery-ui.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/CalendarPopup.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/ppgp.js"></script>
		

		<script type="text/javascript">
		
			function openWindow(id,url,w,h) {
			       window.open(url,id,"toolbar=0,location=no,top=50,left=50,directories=0,status=0,menbar=0,scrollbars=1,resizable=0,width="+w+",height="+h);
			
			       if (navigator.appName == 'Netscape') {
			               popUpWin.focus();
			       }
			}
			
			$('document').ready(function() {
				$(document).on('change','#selectdomain',function(){
					selectdomain_change();
				});
				$('#CompletionDate').datepicker({ dateFormat: "dd/mm/yy", defaultDate: '${CompletionDate}' });
					
				$('#btn_submit_task').click(function(){
					if(validatePPGP(document.GrowthPlan)){
						
						$('#row_task_submit').text('Saving, Please wait...')
						
						$('#frmGrowthPlan').attr('action','<%=(task == null) ? "addGrowthPlan.html" : "modifyGrowthPlanTask.html"%>');
						$('#frmGrowthPlan').submit();
					}
				});
				
				if (${REFRESH_ARCHIVE ne null}) {
					parent.ppgpmenu.document.location.reload();
				}
		
				if (${msg ne null}) {
					alert('<%=request.getAttribute("msg")%>');
				}
				$("#divgrade").hide();
				$("#divsubject").hide();
				$("#divtopic").hide();
				$("#divstopic").hide();
				$("#divstopic").hide();
				$("#divstrength").hide();
				var test = parseInt($("#cyear").val(),10);
				if(test > 2017){
					$("#divnew").show();
					$("#divoriginal").hide();
					//now we set the select objects if edit
					$("#selectdomain").val('<%=domainid%>');
					$("#selectstrength").val('<%=strengthid%>');
					if(parseInt('<%=strengthid%>',10) > 0){
						$("#divstrength").show();
					}
				}else{
					$("#divnew").hide();
					$("#divoriginal").show();
					//now we set the values and show the dropdown
					$("#cat_id").val('<%=catid%>');
					$("#grade_id").val('<%=gradeid%>');
					if(parseInt('<%=gradeid%>',10) > 0){
						$("#divgrade").show();
					}
					$("#subject_id").val('<%=subjectid%>');
					if(parseInt('<%=subjectid%>',10) > 0){
						$("#divsubject").show();
					}
					$("#topic_id").val('<%=topicid%>');
					if(parseInt('<%=topicid%>',10) > 0){
						$("#divtopic").show();
					}
					$("#stopic_id").val('<%=stopicid%>');
					if(parseInt('<%=stopicid%>',10) > 0){
						$("#divstopic").show();
					}
				}
				var msgupdate="<%=request.getAttribute("msgupdate") %>";
				if(!(msgupdate == "null")){
					$("#spanerror").text(msgupdate);
					$("#diverror").show();
				}

			});
		
		</script>
	</head>

	<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
		<form id='frmGrowthPlan' name="GrowthPlan" action="addGrowthPlan.html" method="post">
		  <input type="hidden" name="op" value=""><input type="hidden" name="cyear" id="cyear" value="<%=cyear%>">
			<input type="hidden" name="pgpid" value="<%=ppgp.getPPGPID()%>" />
			
			<%if (goal != null) {%>
			  <input type="hidden" name="gid" value="<%=goal.getPPGPGoalID()%>" />
			<%}%>
			<%if (task != null) {%>
			  <input type="hidden" name="tid" value="<%=task.getTaskID()%>" />
			<%}%>
			<!------------- Begin Main Table --------------->
			<center>
				<table width="620" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="620" valign="top" align="center" colspan="2">
							<!----Begin top Logo ------>
							<table width="620" cellpadding="0" cellspacing="0" border="0">
								<tr>
									<td width="620" valign="top" colspan="2">
										<br/>
<span style="font-size:16px;font-weight:bold;color:Navy;">Professional Learning Plan</span>
									</td>
								</tr>
								<tr>
									<td width="215" valign="top" align="left">
										<img src="images/gp_logo_2.gif"><BR>
									</td>
									<td width="405" valign="middle" align="center" bgcolor="#0066CC">
										<span class="largetext"><%=ppgp.getSchoolYear()%></span><BR>
									</td>
								</tr>
							</table> 
							<!----End Top Logo ------>
						</td>
					</tr>
					<tr>
						<td width="620" valign="top" colspan="2">
						  <br/>
						  <table>
						    <tr>
						      <td>
						        <span class="toptitle">Name:</span>
						      </td>
						      <td>
						        <%=usr.getPersonnel().getFullName()%>
						      </td>
						    </tr>
						    <%if (usr.checkRole("TEACHER") || usr.checkRole("PRINCIPAL") || usr.checkRole("VICE PRINCIPAL")) { %>
						      <tr>
						        <td>
						          <span class="toptitle">School:</span>
						        </td>
						        <td>
						          <%=usr.getPersonnel().getSchool().getSchoolName()%>
						        </td>
						      </tr>
						    <%}%>
						  </table>
						  <br/>
						</td>
					</tr>
					<tr>
					<td width="620" valign="top" colspan="2">
							<div class="alert alert-danger" role="alert" style="display:none;" id="diverror" name="diverror">
  								<font style="font-weight:bold"><span id="spanerror" name="spanerror"></span></font>
							</div>
						</td>
					</tr>
					<tr>
						<td width="620" valign="top" colspan="2">
							<!------------------ Begin Primary Goal 1 ------------------>
							
							<table width="620" border='1' class="infotable" >
							  <%if (request.getAttribute("msg") != null) {%>
							    <tr>
							      <td width="400" valign="middle" align="left" bgcolor="#FFFFFF" colspan="4">
							        <span style="color:#FF0000;font-weight:bold;"><%=request.getAttribute("msg")%></span><BR />
							      </td>
							    </tr>  
							  <%}%>
							  <!-- GOAL -->
							  <tr>
							    <td width="220" valign="middle" align="center" bgcolor="#0066CC" rowspan="2">
							      <span class="title">Goal</span><BR />
							    </td>
							    <td width="400" valign="middle" align="left" bgcolor="#F4F4F4" colspan="2">
							      <span class="subtitle"><i>Establish a goal for learning</i></span><BR />
							    </td>
							  </tr>  
							  <tr>
							    <td width="400" valign="middle" align="left" bgcolor="#F4F4F4" colspan="2">
							      <%if (goal == null) {%>
							        <textarea name="Goal" cols="55" rows="5"><%=(request.getAttribute("Goal") != null) ? (String) request.getAttribute("Goal") : ""%></textarea><BR>
							      <%}else {%>
							        <%=goal.getPPGPGoalDescription()%> 
							      <%}%>
							    </td>
							  </tr>
							  
							  <!-- TASK CATEGORY -->
							  <tr>
							    <td width="220" valign="middle" align="center" bgcolor="#003399">
							      <span class="title">
							      <%if(cyear <= 2017){ %>
							 			Category
							 	  <%}else{ %>
							 	  		Domains and Strengths
							 	  <%} %>
							      </span><BR>
							    </td>
							    <td width="400" valign="middle" align="left" bgcolor="#CCCCCC" colspan="2" style="padding:0px;">
							    	<div id="divnew" style="display:none;">
							    		<table cellpadding="5" cellspacing="0" width="470" border="0">
							        		<tr>
							          			<td width="100"><span class="subtitle"><b>Domain:</b>&nbsp;&nbsp;</span></td>
							          			<td width="*">
							            			<select id="selectdomain" name="selectdomain">
							            				<option value="0">SELECT DOMAIN</option>
							            				<c:forEach items="${domains}" var="itemrow">
   															<option value="${ itemrow.domainID}">${ itemrow.domainTitle}</option>
  														</c:forEach> 
							            			</select>
							          			</td>
							        		</tr>
							        		<tr>
							        			<td colspan='2'>
							        			<div id="divstrength">
							        			<table>
							        				<tr>
							        						<td width="100"><span class="subtitle"><b>Strength:</b>&nbsp;&nbsp;</span></td>
							          						<td width="*">
							          						
							            						<select id="selectstrength" name="selectstrength">
							            							<option value="0">SELECT STRENGTH</option>
							            							<%if (!(request.getAttribute("strengths") == null)){ %>
							            									<c:forEach items="${strengths}" var="itemrow">
   																				<option value="${ itemrow.strengthID}">${ itemrow.strengthTitle}</option>
  																			</c:forEach> 
							            							<%} %>
							            						</select>
							          						</td>
							        				</tr>
							        			</table>
							        			</div>
							          			</td>
							        		</tr>							        		
							        	</table>
							    	</div>
							      <div id="divoriginal" style="display:none;">
							      <table cellpadding="5" cellspacing="0" width="470" border="0">
							        <tr>
							          <td width="100"><span class="subtitle"><b>Category:</b>&nbsp;&nbsp;</span></td>
							          <td width="*">
							            	<select id='cat_id' name='cat_id' onchange="cat_id_change();">
							            		<option value="0">SELECT CATEGORY</option>
							                	<c:forEach items="${cats}" var="itemrow">
   													<option value="${ itemrow.value.categoryID}">${ itemrow.value.categoryTitle}</option>
  												</c:forEach> 
							                </select>
							          </td>
							        </tr>
							        <tr>
							        	<td colspan='2'>
							        		<div id="divgrade">
							        		<table>
							        		<tr>
							    				<td width="100" style="border-top: solid 1px #FFFFFF;"><span class="subtitle"><b>Grade:</b>&nbsp;&nbsp;</span></td>
							            		<td width="*" style="border-top: solid 1px #FFFFFF;">
							              		<select id='grade_id' name='grade_id' onchange="grade_id_change();">
							            		<option value="0">SELECT GRADE</option>
							                	<c:forEach items="${grades}" var="itemrow">
   													<option value="${ itemrow.value.gradeID}">${ itemrow.value.gradeTitle}</option>
  												</c:forEach> 
							                </select>
							            		</td>
							        		</tr>
							        		</table>
							        		</div>
										</td>
							          </tr>
							          <tr>
							          	<td colspan='2'>
							          		<div id="divsubject">
							          		<table>
							          			<tr>
							          				<td width="100" style="border-top: solid 1px #FFFFFF;"><span class="subtitle"><b>Subject Area:</b>&nbsp;&nbsp;</span></td>
							              			<td width="*" style="border-top: solid 1px #FFFFFF;">
							                			<select id='subject_id' name='subject_id' onchange="subject_id_change();">
							                				<%if (!(request.getAttribute("subjects") == null)){ %>
							            						<c:forEach items="${subjects}" var="itemrow">
   																	<option value="${ itemrow.subjectID}">${ itemrow.subjectTitle}</option>
  																</c:forEach> 
							            					<%} %>
							                			
							                			</select>
							              			</td>
							            		</tr>
							          		</table>
							          		</div>
							          	</td>
							          </tr>
							          <tr>
							          	<td colspan='2'>
							          		<div id="divtopic">
							          			<table>
							          				<tr>
							          					<td width="100" style="border-top: solid 1px #FFFFFF;"><span class="subtitle"><b>Topic Area:</b>&nbsp;&nbsp;</span></td>
							                			<td width="*" style="border-top: solid 1px #FFFFFF;">
							                  				<select id='topic_id' name='topic_id' onchange="topic_id_change();">
							                  					<%if (!(request.getAttribute("topics") == null)){ %>
							                  						<OPTION VALUE="0">SELECT SPECIFIC TOPIC AREA</OPTION>
																	<OPTION VALUE="1" SELECTED>Other</OPTION>
							                  						<c:forEach items="${topics}" var="itemrow">
   																		<option value="${ itemrow.topicID}">${ itemrow.topicTitle}</option>
  																	</c:forEach> 
							            						<%} %>
							                  				</select>
							                			</td>
							          				</tr>
							          			</table>
							          		</div>
							          	</td>
							          </tr>	
							          <tr>
							          	<td colspan='2'>
							          		<div id="divstopic">
							          			<table>
							          				<tr>
							          					<td width="100" style="border-top: solid 1px #FFFFFF;"><span class="subtitle"><b>Specific Topic:</b>&nbsp;&nbsp;</span></td>
							                  			<td width="*" style="border-top: solid 1px #FFFFFF;">
							                    		<select id='stopic_id' name='stopic_id'>
							                    			<%if (!(request.getAttribute("stopics") == null)){ %>
							                    					<OPTION VALUE="0">SELECT SPECIFIC TOPIC AREA</OPTION>
																	<OPTION VALUE="1" SELECTED>Other</OPTION>
							                    					<c:forEach items="${stopics}" var="itemrow">
   																		<option value="${ itemrow.specificTopicID}">${ itemrow.specificTopicTitle}</option>
  																	</c:forEach> 
							            					<%} %>
							                    		</select>
							                  			</td>
							          				</tr>
							          			</table>
							          		</div>
							          	</td>
							          </tr>
							      </table>
							      </div>
							      
							    </td>
							  </tr>
							  
							  <!-- TASK STRATEGY -->
							  <tr>
							    <td width="220" valign="middle" align="center" bgcolor="#003399" rowspan="2">
							      <span class="title">Strategies</span><BR>
							    </td>
							    <td width="400" valign="middle" align="left" bgcolor="#CCCCCC" colspan="2">
							      <span class="subtitle"><i>Choose path(s) for learning</i></span><BR>
							      <span class="subtitle"><b>List a task and activity</b></span><BR>
							    </td>
							  </tr>
							  <tr>
							    <td width="400" valign="middle" align="left" bgcolor="#CCCCCC" colspan="2">
							      <textarea id='txt_task' name="Task" cols="55" rows="5"><%=(request.getAttribute("Task") != null) ? (String) request.getAttribute("Task") : ""%></textarea><BR/>
							    </td>
							  </tr>
							  
							  <!-- SUPPORTS -->
							  <tr>
							    <td width="220" valign="middle" align="center" bgcolor="#003399" rowspan="3">
							      <span class="title">Supports</span><BR>
							    </td>
							    <td width="400" valign="middle" align="left" bgcolor="#CCCCCC" colspan="2">
							      <span class="subtitle"><i>What resources and support do you need?</i></span><BR>
							    </td>
							  </tr>
							  <tr>
							    <td width="200" valign="middle" align="center" bgcolor="#CCCCCC">
							      <span class="subtitle"><b>School</b></span><BR>
							    </td>
							    <td width="200" valign="middle" align="center" bgcolor="#CCCCCC">
							      <span class="subtitle"><b>District</b></span><BR>
							    </td>
							  </tr>
							  <tr>
							    <td width="200" valign="middle" align="center" bgcolor="#CCCCCC">
							      <textarea id='txt_school_support' name="SchoolSupport" style="width:200px;height:100px;"><%=(request.getAttribute("SchoolSupport") != null) ? (String) request.getAttribute("SchoolSupport") : ""%></textarea><BR>
							    </td>
							    <td width="200" valign="middle" align="center" bgcolor="#CCCCCC">
							      <textarea id='txt_district_support' name="DistrictSupport" style="width:200px;height:100px;"><%=(request.getAttribute("DistrictSupport") != null) ? (String) request.getAttribute("DistrictSupport")
												: ""%></textarea><BR>
							    </td>
							  </tr>
							  
							  <!-- TECHNOLOGY -->
							  <tr>
							    <td width="220" valign="middle" align="center" bgcolor="#003399" rowspan="5">
							      <span class="title">Techology</span><BR>
							    </td>
							    <td width="400" valign="middle" align="left" bgcolor="#CCCCCC" colspan="2">
							      <span class="subtitle"><i>How may techology support the successfully attainment of your goal?</i></span><BR>
							    </td>
							  </tr>
							  <tr>
							  	<td width="400" valign="middle" align="center" bgcolor="#CCCCCC" colspan="2">
							      <textarea id='txt_technology' name="TechSupport" cols="55" rows="5"><%=(request.getAttribute("TechSupport") != null) ? (String) request.getAttribute("TechSupport")
												: ""%></textarea><BR>
							    </td>
							  </tr>
							  <td width="400" valign="middle" align="left" bgcolor="#CCCCCC" colspan="2">
							      <span class="subtitle"><i>What technology supports are required for the successful implementation of your growth plan?</i></span><BR>
							    </td>
							  <tr>
							    <td width="200" valign="middle" align="center" bgcolor="#CCCCCC">
							      <span class="subtitle"><b>School</b></span><BR>
							    </td>
							    <td width="200" valign="middle" align="center" bgcolor="#CCCCCC">
							      <span class="subtitle"><b>District</b></span><BR>
							    </td>
							  </tr>
							  <tr>
							    <td width="200" valign="middle" align="center" bgcolor="#CCCCCC">
							      <textarea id='txt_school_support' name="TechSchoolSupport" style="width:200px;height:100px;"><%=(request.getAttribute("TechSchoolSupport") != null) ? (String) request.getAttribute("TechSchoolSupport") : ""%></textarea><BR>
							    </td>
							    <td width="200" valign="middle" align="center" bgcolor="#CCCCCC">
							      <textarea id='txt_district_support' name="TechDistrictSupport" style="width:200px;height:100px;"><%=(request.getAttribute("TechDistrictSupport") != null) ? (String) request.getAttribute("TechDistrictSupport")
												: ""%></textarea><BR>
							    </td>
							  </tr>
							  
							  <!-- TIMELINE -->
							  <tr>
							    <td width="220" valign="middle" align="center" bgcolor="#003399" rowspan="2">
							      <span class="title">Timeline</span><BR>
							    </td>
							    <td width="400" valign="middle" align="left" bgcolor="#CCCCCC" colspan="2">
							      <span class="subtitle"><i>When will the task or activity be complete?</i></span><BR>
							    </td>
							  </tr>
							  <tr>
							    <td width="400" valign="middle" align="left" bgcolor="#CCCCCC" colspan="2">
							      <input data-provide="datepicker" id="CompletionDate" name="CompletionDate" size="20" value="<%=(request.getAttribute("CompletionDate") != null) ? (String) request.getAttribute("CompletionDate") : ""%>" READONLY>&nbsp;<img src="images/cal.gif" onmouseover="src='images/cal_on.gif';" onmouseout="src='images/cal.gif';" border="0" alt="Click Here to Pick the date">
							    
							    </td>
							  </tr>
							  
							  <!-- SELF-REFLECTION -->
						    <tr>
						      <td width="220" valign="middle" align="center" bgcolor="#003399" rowspan="2">
						        <span class="title">Self-Reflection</span><BR />
						      </td>
						      <td width="400" valign="middle" align="left" bgcolor="#CCCCCC" colspan="2">
						        <span class="subtitle"><i>Reflect on the progress you have made on your goal(s)</i></span><BR />
						      </td>
						    </tr>
						    <tr>
						    	<td width="400" valign="middle" align="left" bgcolor="#CCCCCC" colspan="2">
							    	<c:choose>
							    		<c:when test="${PPGP_TASK ne null and PPGP_TASK.past}">
							    			<textarea id='txt_self_eval' name="SelfEvaluation" cols="55" rows="5"><%=(request.getAttribute("SelfEvaluation") != null) ? (String) request.getAttribute("SelfEvaluation")
												: ""%></textarea><BR />
							    		</c:when>
							    		<c:otherwise>
							    			<span class='subtitle' style='color:red;'>Reflection will not be available until the task timeline has past.</span>
							    		</c:otherwise>
							    	</c:choose>
						      </td>
						    </tr>
						    <tr>
						    	<td id='row_task_submit' valign="middle" align="center" colspan="3" bgcolor="#F4F4F4" color='red'>
							    	<img id="btn_submit_task" src="images/submit-off.png" style='cursor:pointer;' border="0" />
										&nbsp;&nbsp;
										<img src="images/cancel-off.png" style='cursor:pointer;' border="0"
										  onclick="document.location.reload();" />
						    	</td>
								</tr>
							</table>
							
							<!------------ End Primary Goal 1 ------------->
							<br />
						</td>
					</tr>
					<tr>
						<td width="310" valign="top" align="left" colspan="1">
							<img src="images/viewlp-off.png" border="0"
						  	onclick="document.GrowthPlan.action='viewGrowthPlanSummary.html?ppgpid=<%=ppgp.getPPGPID()%>';document.GrowthPlan.submit();" />
						</td>
						<td width="310" valign="top" align="right" colspan="1">
							<img src="images/addagoal-off.png"  border="0"
							  onclick="document.GrowthPlan.action='addAdditionalGoal.html?sy=<%=ppgp.getSchoolYear()%>';document.GrowthPlan.submit();" />
							  <br /><br />
						</td>
					</tr>
				
					<tr>
						<td width="620" valign="top" align="left" colspan="2" bgcolor="#F4F4F4">
							<img src="images/bullet.gif"><a href="javascript:openWindow('availableSupports','availableSupports.jsp',550,590)">View available supports for accomplishing goals</a><BR>
						</td>
					</tr>
				
					<tr>
						<td width="620" valign="top" align="left" colspan="2" bgcolor="#F4F4F4">
							<img src="images/bullet.gif"><a href="javascript:openWindow('draftGoals','draftGoals.jsp',550,400)">View/Enter Draft Goals</a><BR>
						</td>
					</tr>
				
					<tr>
						<td width="620" valign="top" align="left" colspan="2" bgcolor="#F4F4F4">
							<img src="images/bullet.gif"><a href="javascript:openWindow('reflection','reflection.jsp',600,500)">View Reflection and Self-Assessment Guide</a><BR>
						</td>
					</tr>
					
					<tr>
						<td width="620" valign="top" align="center" colspan="2">
							<BR /><BR />
							<img src="images/bottom_line.gif" /><BR />
						</td>
					</tr>
					
					
						<tr>
<td width="310" valign="top" align="left">
<a href="http://www.nlesd.ca">nlesd.ca</a>
</td>
<td width="310" valign="top" align="right">
<span class="smalltext">Copyright 2014 Newfoundland and Labrador English School District.</span>
</td>
</tr>
			
				
				</table>
			</center>
		
		<!------------- End Main Table ---------------->
		</form>
	
	</body>
</html>