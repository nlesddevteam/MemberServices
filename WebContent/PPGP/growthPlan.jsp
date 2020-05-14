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
		<title>Professional Learning Plan</title>
<script type="text/javascript">	
			
			
			$('document').ready(function() {
				$(document).on('change','#selectdomain',function(){
					selectdomain_change();
				});
				$('#CompletionDate').datepicker({ dateFormat: "dd/mm/yy", defaultDate: '${CompletionDate}' });
					
				$('#btn_submit_task').click(function(){
					if(validatePPGP(document.GrowthPlan)){
						
						$('#row_task_submit').text('Saving, Please wait...');						
						$('#frmGrowthPlan').attr('action','<%=(task == null) ? "addGrowthPlan.html" : "modifyGrowthPlanTask.html"%>');
						$('#frmGrowthPlan').submit();
					}
				});
				
				if (${REFRESH_ARCHIVE ne null}) {
					parent.ppgpmenu.document.location.reload();
				}		
				
				$("#divgrade").hide();
				$("#divsubject").hide();
				$("#divtopic").hide();
				$("#divstopic").hide();				
				$("#divstrength").hide();
				var test = parseInt($("#cyear").val(),10);
				if(test > 2017){
					
					//now we set the select objects if edit
					$("#selectdomain").val('<%=domainid%>');
					$("#selectstrength").val('<%=strengthid%>');
					if(parseInt('<%=strengthid%>',10) > 0){
						$("#divstrength").show();
					}
				}else{
					
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
					$("#msgok").html("<b>SUCCESS:</b> "+msgupdate).delay(5000).css("display","block").fadeOut();
					$("#msgSubmitGoal").css("display","block");
				}

			});
		
</script>	
<script>
var goalWordCountConf = {
	    showParagraphs: true,
	    showWordCount: true,
	    showCharCount: true,
	    countSpacesAsChars: true,
	    countHTML: true,
	    maxWordCount: -1,
	    maxCharCount: 500,
	}
var selfRefCountConf = {
	    showParagraphs: true,
	    showWordCount: true,
	    showCharCount: true,
	    countSpacesAsChars: true,
	    countHTML: true,
	    maxWordCount: -1,
	    maxCharCount: 4000,
	}	  
var pageWordCountConf = {
	    showParagraphs: true,
	    showWordCount: true,
	    showCharCount: true,
	    countSpacesAsChars: true,
	    countHTML: true,
	    maxWordCount: -1,
	    maxCharCount: 500,
	}
</script>


<script>
    $("#loadingSpinner").css("display","none");
</script>
<style>
.tableTitle {font-weight:bold; font-size:16px;}
.tableResult {font-weight:normal;}
.tableTitleWide {column-span: all;}
.tableTitleL {font-weight:bold;font-size:16px;width:15%;}
.tableResultL {font-weight:normal;width:35%;}
.tableTitleR {font-weight:bold;font-size:16px;width:15%;}
.tableResultR {font-weight:normal;width:35%;}
input {border:1px solid silver;}

</style>
	</head>

	<body>
	<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b><%=ppgp.getSchoolYear()%> Professional Learning Plan for <span style="text-transform:capitalize;"><%=usr.getPersonnel().getFullNameReverse()%></span></b>
	               	<%if (usr.checkRole("TEACHER") || usr.checkRole("PRINCIPAL") || usr.checkRole("VICE PRINCIPAL")) { %>
						     <%=(usr.getPersonnel().getSchool().getSchoolName() !=null)?"<br/>of "+ usr.getPersonnel().getSchool().getSchoolName():""%>
					<%}%>
	               	
	               	
	               	</div>
      			 	<div class="panel-body"> 
	
	                
	
<form id='frmGrowthPlan' name="GrowthPlan" action="addGrowthPlan.html" method="post">
		  <input type="hidden" name="op" value=""><input type="hidden" name="cyear" id="cyear" value="<%=cyear%>">
			<input type="hidden" name="pgpid" value="<%=ppgp.getPPGPID()%>" />
			
			<%if (goal != null) {%>
			  <input type="hidden" name="gid" value="<%=goal.getPPGPGoalID()%>" />
			<%}%>
			<%if (task != null) {%>
			  <input type="hidden" name="tid" value="<%=task.getTaskID()%>" />
			<%}%>
			
			<div class="alert alert-warning alert-dismissible" style="text-align:center;"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				<b>PLEASE NOTE:</b><br/>Due to your browser secure session, please make sure you fill out the form below within 10 minutes. 
				Taking longer may result (like your online banking session) a timeout disconnect of your secure browsing session resulting in your data not being saved.
				If you think you need more time, please write up your plan in Google Docs, NotePad or MS Word and then copy and paste into this form when ready. 
				You can also fill out what you can, submit and come back to edit later. <b>You are limited to 500 characters in each text box unless otherwise specified.</b> Try to keep it simple.
			</div>		
					
					<div class="alert alert-success" align="center" id="msgok" style="display:none;"><b>SUCCESS:</b> ${msg}</div>
					<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"><b>ERROR:</b> ${errmsg}</div>
							  
				<div id="msgSubmitGoal" style="display:none;text-align:center;" class="alert alert-info">
				You just added a Task/Strategy to your Goal. You can add another Task to this Goal using the form below, or add a additional new Goal.<br/><br/>
				<a href="addAdditionalGoal.html?sy=<%=ppgp.getSchoolYear()%>" class="btn btn-xs btn-success" onclick="loadingData()">Add Additional Goal</a>
				<a href="viewGrowthPlanSummary.html?ppgpid=<%=ppgp.getPPGPID()%>" class="btn btn-xs btn-primary" onclick="loadingData()">Exit to Learning Plan</a>				
				<br/><br/>
				</div>  
<table class="table table-striped table-condensed" style="font-size:11px;">							   
<tbody>
<tr>							  
							  
 <!-- GOAL -->
			<td class="tableTitle">Goal:</td>
			<td class="tableResult" colspan=3>
			<%if (goal == null) {%>
			Establish a goal for learning this year.<br/>
			<textarea id="Goal" name="Goal" class="form-control"><%=(request.getAttribute("Goal") != null) ? (String) request.getAttribute("Goal") : ""%></textarea>
			<script>			
			CKEDITOR.replace('Goal', {wordcount: goalWordCountConf,height:150});			
			</script>
			<%}else {%>
			<textarea id="Goal" name="Goal" class="form-control" readonly style="display:none;"><%=goal.getPPGPGoalDescription()%></textarea>
			<%=goal.getPPGPGoalDescription()%>
			<%}%>
			</td>
</tr>			
<!-- TASK CATEGORY -->
	

<!-- 2018 and up -->	
<%if(cyear > 2017){ %>

		<tr>
		<td class="tableTitle">Domain:</td>
		<td class="tableResult" colspan=3>							          			
				<select id="selectdomain" name="selectdomain" class="form-control">
							<option value="0">SELECT DOMAIN</option>
						<c:forEach items="${domains}" var="itemrow">
							<option value="${ itemrow.domainID}">${ itemrow.domainTitle}</option>
						</c:forEach> 
				</select>
		</td>
		</tr>
		
		<tr id="divstrength">
			<td class="tableTitle">Strength:</td>
			<td class="tableResult" colspan=3>				          						
				<select id="selectstrength" name="selectstrength" class="form-control">
								<option value="0">SELECT STRENGTH</option>
						<%if (!(request.getAttribute("strengths") == null)){ %>
							<c:forEach items="${strengths}" var="itemrow">
								<option value="${ itemrow.strengthID}">${ itemrow.strengthTitle}</option>
							</c:forEach> 
						<%} %>
				</select>
			</td>				          				
		</tr>
	 <input type="hidden" name="cat_id" value="0" />	
<!-- 2017 and earlier  -->		
<%}else{ %>
				<tr>
				<td class="tableTitle">Category:</td>
				<td class="tableResult" colspan=3>
		            	<select id='cat_id' name='cat_id' onchange="cat_id_change();" class="form-control">
		            						<option value="0">SELECT CATEGORY</option>
		                			<c:forEach items="${cats}" var="itemrow">
											<option value="${ itemrow.value.categoryID}">${ itemrow.value.categoryTitle}</option>
									</c:forEach> 
		                </select>
				</td>
				</tr>
							         
				
				<tr id="divgrade">
				<td class="tableTitle">Grade:</td>
				<td class="tableResult" colspan=3>			            		
	              		<select id='grade_id' name='grade_id' onchange="grade_id_change();" class="form-control">
	            							<option value="0">SELECT GRADE</option>
	                				<c:forEach items="${grades}" var="itemrow">
											<option value="${ itemrow.value.gradeID}">${ itemrow.value.gradeTitle}</option>
									</c:forEach> 
	                	</select>
				</td>
				</tr>			            	
				
										
				<tr id="divsubject">
				<td class="tableTitle">Subject Area:</td>
				<td class="tableResult" colspan=3>
               			<select id='subject_id' name='subject_id' onchange="subject_id_change();" class="form-control">
               					<%if (!(request.getAttribute("subjects") == null)){ %>
           							<c:forEach items="${subjects}" var="itemrow">
											<option value="${ itemrow.subjectID}">${ itemrow.subjectTitle}</option>
									</c:forEach> 
           					<%} %>
               			</select>
				</td>			              			
				</tr>
							          	
				<tr id="divtopic">
				<td class="tableTitle">Topic Area:</td>
				<td class="tableResult" colspan=3>							                			
           				<select id='topic_id' name='topic_id' onchange="topic_id_change();" class="form-control">
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
							          	
				<tr id="divstopic">
				<td class="tableTitle">Specific Topic:</td>
				<td class="tableResult" colspan=3>
                   		<select id='stopic_id' name='stopic_id' class="form-control">
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
							      

<%} %>							      
							 
<!-- TASK STRATEGY -->
				
				<tr>		
				<td class="tableTitle">Task/Strategies:</td>
				<td class="tableResult" colspan=3>Choose path(s) for learning. List a task and activity.<br/>
				<textarea id='txt_task' name="Task"><%=(request.getAttribute("Task") != null) ? (String) request.getAttribute("Task") : ""%></textarea>
				</td>
				</tr>
							  
<!-- SUPPORTS -->
				<tr>		
				<td class="tableTitleWide" colspan=4><b>Resources and Supports: What resources and support do you need?</b></td>
				</tr>
				<tr>
				<td class="tableTitleL">School:</td>
				<td class="tableResultL"><textarea id='txt_school_support' name="SchoolSupport"><%=(request.getAttribute("SchoolSupport") != null) ? (String) request.getAttribute("SchoolSupport") : ""%></textarea></td>
				<td class="tableTitleR">District:</td>
				<td class="tableResultR"><textarea id='txt_district_support' name="DistrictSupport"><%=(request.getAttribute("DistrictSupport") != null) ? (String) request.getAttribute("DistrictSupport"): ""%></textarea></td>
				</tr>		
							  
<!-- TECHNOLOGY -->
                <tr>
				<td class="tableTitle">Techology:</td>
				<td class="tableResult" colspan=3>How may techology support the successfully attainment of your goal?<br/>
						<textarea id='txt_technology' name="TechSupport"><%=(request.getAttribute("TechSupport") != null) ? (String) request.getAttribute("TechSupport"): ""%></textarea>
				</td>
				</tr>
				<tr>
				<td class="tableTitleWide" colspan=4><b>Technology Supports: What technology supports are required for the successful implementation of your growth plan?</b></td>
				</tr>
				<tr>
				<td class="tableTitleL">School:</td>
				<td class="tableResultL"><textarea id='txt_school_support_tech' name="TechSchoolSupport"><%=(request.getAttribute("TechSchoolSupport") != null) ? (String) request.getAttribute("TechSchoolSupport") : ""%></textarea></td>
				<td class="tableTitleR">District:</td>
				<td class="tableResultR"><textarea id='txt_district_support_tech' name="TechDistrictSupport"><%=(request.getAttribute("TechDistrictSupport") != null) ? (String) request.getAttribute("TechDistrictSupport"): ""%></textarea></td>
				</tr>		
 <!-- TIMELINE -->
                <tr>
				<td class="tableTitle">Timeline:</td>
				<td class="tableResult" colspan=3>When will the task or activity be complete?<br/>
				<input data-provide="datepicker" id="CompletionDate" name="CompletionDate" class="form-control" value="<%=(request.getAttribute("CompletionDate") != null) ? (String) request.getAttribute("CompletionDate") : ""%>" READONLY>
				</td>
				</tr>
									
<!-- SELF-REFLECTION -->
 				<tr>
				<td class="tableTitle">Self-Reflection:</td>
				<td class="tableResult" colspan=3>
				<c:choose>
				    		<c:when test="${PPGP_TASK ne null and PPGP_TASK.past}">
				    			Reflect on the progress you have made on your goal(s):<br/>
				    			<textarea id='txt_self_eval' name="SelfEvaluation"><%=(request.getAttribute("SelfEvaluation") != null) ? (String) request.getAttribute("SelfEvaluation"): ""%></textarea>
				    		    <script>				    		      	
				    		    CKEDITOR.replace('txt_self_eval', {wordcount: selfRefCountConf,height:150});</script>
				    		</c:when>
				    		<c:otherwise>
				    			<div class="alert alert-danger"><%=(request.getAttribute("CompletionDate") != null) ? "<b>NOTE:</b> Self-Reflection will not be available until the task timeline of "+ (String) request.getAttribute("CompletionDate")+ " has past." : "<b>NOTE:</b> You have yet to specify a timeline above for completion of this task."%></div>
				    		</c:otherwise>
				</c:choose>
				</td>
				</tr>     				 
</tbody>
</table>
							 
<div align="center">
<a href="#" class="btn btn-xs btn-success" id="btn_submit_task" title="Submit Goal" onclick="loadingData()">
<%if (goal == null) {%>
Submit Goal
<%} else { %>
Submit
<%}%>
</a>
<a href="policy.jsp" class="btn btn-xs btn-danger" title="Cancel">Cancel</a>	
<a href="viewGrowthPlanSummary.html?ppgpid=<%=ppgp.getPPGPID()%>" class="btn btn-xs btn-primary" onclick="loadingData()">View Learning Plan</a>
<a href="addAdditionalGoal.html?sy=<%=ppgp.getSchoolYear()%>" class="btn btn-xs btn-success" onclick="loadingData()">Add Additional Goal</a>
</div>						
												
						
					
</div></div></div>
		</form>
		
<%if(request.getAttribute("msg")!=null){%>
	<script>$("#msgok").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
<%if(request.getAttribute("errmsg")!=null){%>
	<script>$("#msgerr").css("display","block").delay(5000).fadeOut();</script>							
<%}%>		
		
<script>
$('document').ready(function(){
	$("#CompletionDate").datepicker({
      	changeMonth: true,
      	changeYear: true,
      	dateFormat: "dd/mm/yy"
   });	
	
	
});

//Configure char count for just this page. Default max is 2460. 4000 for this page.
    
CKEDITOR.replace('txt_task', {wordcount: pageWordCountConf,height:150});  
CKEDITOR.replace('txt_school_support', {wordcount: pageWordCountConf,height:150});  
CKEDITOR.replace('txt_district_support', {wordcount: pageWordCountConf,height:150});  
CKEDITOR.replace('txt_technology', {wordcount: pageWordCountConf,height:150}); 
CKEDITOR.replace('txt_school_support_tech', {wordcount: pageWordCountConf,height:150});  
CKEDITOR.replace('txt_district_support_tech', {wordcount: pageWordCountConf,height:150});  

</script> 
	</body>
</html>