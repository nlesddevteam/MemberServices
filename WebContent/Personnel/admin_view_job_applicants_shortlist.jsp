<%@ page language="java"
         import="java.text.*,
         				 java.util.*,
         				 com.awsd.security.*,
         				 com.esdnl.util.*,
         				 com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>
         
<!-- LOAD JAVA TAG LIBRARIES -->
		
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
<%
  JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
	JobOpportunityBean jobs[] = JobOpportunityManager.getJobOpportunityBeans("CLOSED");
	TeacherRecommendationBean[] rec = RecommendationManager.getTeacherRecommendationBean(job.getCompetitionNumber());
  ApplicantProfileBean[] applicants = (ApplicantProfileBean[]) session.getAttribute("JOB_SHORTLIST");
  HashMap<String, ApplicantProfileBean> declinedInterviewMap = (HashMap<String, ApplicantProfileBean>) session.getAttribute("JOB_SHORTLIST_DECLINES_MAP");
  AdRequestBean ad = (AdRequestBean) request.getAttribute("AD_REQUEST");
  User usr = (User)session.getAttribute("usr");
  
  InterviewGuideBean guide = InterviewGuideManager.getInterviewGuideBean(job);
  Map<String, ArrayList<InterviewSummaryBean>> interviewSummaryMap = InterviewSummaryManager.getInterviewSummaryBeansMapByShortlist(job);
  
  Calendar rec_search_cal = Calendar.getInstance();
  rec_search_cal.clear();
  rec_search_cal.set(2019, Calendar.MAY, 1);
  Date rec_search_date = rec_search_cal.getTime();
  int statusi=0;
  RequestToHireBean rth = null;
  if(!(job == null)){
	  rth = RequestToHireManager.getRequestToHireByCompNum(job.getCompetitionNumber());
  }
%>


<html>
<head>
	<title>MyHRP Applicant Profiling System</title>
	<script type='text/javascript' src="js/personnel_ajax_v2.js"></script>
<script>
	$("#loadingSpinner").css("display","none");
</script>


	<script type="text/javascript">
	$('document').ready(function(){
	
		$('#add_applicant_dialog').dialog({
			'autoOpen': false,
			'bgiframe': true,
			'width':500,
			'height': 250,
			'modal': true,
			'hide': 'explode',
			'buttons': {'close': function(){$('#add_applicant_dialog').dialog('close');}}
		});
		
		$('#btn_add_applicant').click(function(){
			$.post("addJobApplicant.html", { comp_num: $('#comp_num').val(), sin: $('#applicant_uid').val(), ajax: true }, 
					function(data){
						parseAddApplicantResponse(data);
					}, "xml");
		});
		
		
		
		$('#mark-shortlist-complete').click(function(){
			if(confirm('Are you sure you want to mark this shortlist as complete?')){
				return true;
			}
			else {
				return false;
			}
		});
		
		$('#mark-shortlist-reopen').click(function(){
			if(confirm('Are you sure you want to reopen this shortlist?')){
				return true;
			}
			else {
				return false;
			}
		});
		
		$('#btn-decline-interview').click(function(){
			if(confirm('Are you sure you want to mark this applicant as DECLINED INTERVIEW?')){
				return true;
			}
			else {
				return false;
			}
		});
		
		$('.btn-action').button();
		
	});

	function showAddApplicantDialog(uid) {
		$('#applicant_uid').val(uid);
		$("#add_applicant_dialog").dialog('open');
		
		return false;
	}
	
	function parseAddApplicantResponse(data){
		var xmlDoc=data.documentElement;
		var msg = xmlDoc.getElementsByTagName("RESPONSE-MSG")[0].childNodes[0].nodeValue;
		
		$('#response_msg').html(msg);
		$('#response_msg').show();
	}
</script>
<script>
 $('document').ready(function(){
	  $("#jobsapp").DataTable(
		{"order": [[ 3, "desc" ]],
		"lengthMenu": [[25, 50, 100, -1], [25, 50, 100, "All"]]
		
		}	  
	  );
 });
    </script>
    
    <style>
		input {    
    border:1px solid silver;
		}
		.btn {font-size:11px;}
</style>	
</head>
<body>

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Competition # <%=job.getCompetitionNumber()%> Short List</b> (Total Applicants: <%=applicants.length%>)</div>
      			 	<div class="panel-body">
									<%if(request.getAttribute("msg")!=null){%>                                  	
                                  	<div class="alert alert-danger">                                    	
                                      	<%=(String)request.getAttribute("msg")%>
                                     </div>
                                  <%}%>

  
                    <div class="table-responsive"> 
							  
  
                                  
                                  <%if(applicants.length > 0){ %>
                                  
                                   <table id="jobsapp" class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;">
								    <thead>
								      <tr>
								      <th width="8%">STATUS</th>								       								      	
								        <th width='18%'>NAME</th>								        
								        <th width='18%'>EMAIL/TELEPHONE</th>
								        <th width='14%'>SENIORITY (yrs)</th>				        
								        <th width='*'>POSITION/OPTIONS</th>									       					        
								      </tr>
								    </thead>
								    <tbody>
                                  
                                  <%
                                  	TeacherRecommendationBean[] recs = null;                                  	
                                  	String cssClass = "NoPosition";
                                  	String cssText = "No Position";
                                  	String position = null;
                                  	DecimalFormat df = new DecimalFormat("0.00");
                                  	boolean hasJobSpecificInterviewSummary = false;
                                  	boolean declinedInterview = false;
                                  	JobTypeConstant jtype = null;
                                  	
                                    for(int i=0; i < applicants.length; i++){
                                    	position = "";
                                    	if(!declinedInterviewMap.containsKey(applicants[i].getUID())) {
                                    		cssClass = "NoPosition";
                                    		cssText = "No Position";
                                    		declinedInterview = false;
	                                    	position = null;
	                                    	recs = applicants[i].getRecentlyAcceptedPositions(rec_search_date);
	                                    	
	                                    	if((recs != null) && (recs.length > 0)){
	                                    		if(recs[0].getJob() != null){
	                                    			jtype = recs[0].getJob().getJobType();
		                                    		if(jtype.equal(JobTypeConstant.REGULAR) || jtype.equal(JobTypeConstant.TLA_REGULAR)){
		                                    			if(recs[0].getTotalUnits() < 1.0){
		                                    				cssClass = "PermanentPartTimePosition";
		                                    				cssText = "Permanent/ Part Time";
		                                    				position = recs[0].getEmploymentStatus() + " Part-time (" + df.format(recs[0].getTotalUnits()) + ") @ "  + recs[0].getJob().getJobLocation();
		                                    			}
		                                    			else{
		                                    				cssClass = "PermanentFullTimePosition";
		                                    				cssText = "Permanent/ Full Time";
		                                    				position = recs[0].getEmploymentStatus() + " Full-time @ " + recs[0].getJob().getJobLocation() ;
		                                    			}
		                                    		}
		                                    		else if(jtype.equal(JobTypeConstant.REPLACEMENT) || jtype.equal(JobTypeConstant.TLA_REPLACEMENT)){
		                                    			cssClass = "ReplacementPosition";
		                                    			cssText = "Replacement";
		                                    			position = recs[0].getJob().getCompetitionNumber() + ":Replacement (" + df.format(recs[0].getTotalUnits()) + ") @ "  + recs[0].getJob().getJobLocation();
		                                    		}
		                                    		else if(jtype.equal(JobTypeConstant.TRANSFER)){
		                                    			cssClass = "TransferPosition";
		                                    			cssText = "Transfer";
		                                    			position = recs[0].getJob().getCompetitionNumber() + ":Transfer (" + df.format(recs[0].getTotalUnits()) + ") @ "  + recs[0].getJob().getJobLocation();
		                                    		}
	                                    		}
	                                    		
	                                    		if(position != null) {
	                                    			cssText = "Already Accepted Position";
	                                    			position += " - Accepted: " + recs[0].getOfferAcceptedDateFormatted();
	                                    		}
	                                    	}
	                                    		
	                                   		hasJobSpecificInterviewSummary = false;
	                                   		
	                                   		if(interviewSummaryMap.containsKey(applicants[i].getUID())) {
	                                   			for(InterviewSummaryBean isg : interviewSummaryMap.get(applicants[i].getUID())){
	                                   				if(isg.getCompetition().getCompetitionNumber().equals(job.getCompetitionNumber())){
	                                   					hasJobSpecificInterviewSummary = true;
	                                   					break;
	                                   				}
	                                   			}
	                                   		}
                                    	}
                                    	else {
                                    		cssClass = "DeclinedInterview";
                                    		declinedInterview = true;
                                    	}
                                    %>	
                                   
	                                    <tr>
	                                    <%statusi++; %>
	                                        <td style="text-align:center;vertical-align:middle;" class="<%=cssClass%>" id="statusBlock<%=statusi%>"><%=cssText%></td>
	                                    	<td style="vertical-align:middle;"><%=applicants[i].getFullNameReverse() %></td>	                                    	
	                                    	<td style="vertical-align:middle;">
	                                    	<a href="mailto:<%=applicants[i].getEmail()%>"><%=applicants[i].getEmail()%></a><br/>
	                                    	Tel: <%=applicants[i].getHomephone()%>
	                                    	</td>
	                                    	<td style="vertical-align:middle;">
	                                    	
                                             <%if (applicants[i].getSenority() > 0) {%>
                                                      <span style='color:red;'><%= applicants[i].getSenority()%></span>
                                            <%} else {%>                                               
                                                <!-- Sort by number, so negative number hidden -->
                                                <span style="color:rgba(255, 255, 255,0)">-1</span>
                                                <%}%>
	                                    	
	                                    	
	                                    	</td>
	                                    	
	                                    		                                    	
	                                    	
	                                    	<td>
	                                    	<div style="float:left;color:DimGrey;">
	                                    			<%if(!StringUtils.isEmpty(position)){ %>
				                                 		<%=position %>
				                                 	<%} else {%>
				                                 		No current position information available for <%=applicants[i].getFirstname()%>.
				                                 	<%} %>
	                                    	<br/>
	                                    	<div style="padding-top:5px;">	                                    	
	                                        <a class='btn btn-xs btn-primary' href="viewApplicantProfile.html?sin=<%=applicants[i].getSIN()%>" >Profile</a>
	                                        <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">
	                                        	<%
	                                        		if(!job.isAwarded() && !job.isCancelled() && !job.isShortlistComplete() && !declinedInterview)
	                                        			out.println("<a class='btn btn-xs btn-danger' href='removeShortlistApplicant.html?sin=" + applicants[i].getSIN() + "' >Remove</a>");
	                                        	
	                                        		if(!declinedInterview){
                                        				out.println("<a id='btn-decline-interview' class='btn btn-xs btn-danger' href='declineInterviewShortlistApplicant.html?sin=" + applicants[i].getSIN() + "' >Interview Declined?</a>");
                                        			}
	                                        		else {
	                                        	%>
	                                        			<script>
	                                        			$("#statusBlock<%=statusi%>").css("background-color","Red").css("color","White").html("DECLINED INTERVIEW");
	                                        			</script>
	                                        			
	                                        	<%
	                                        		}
	                                        		
	                                        		if(job.getJobType().equal(JobTypeConstant.POOL) && !declinedInterview)
	                                        			out.println("<a class='btn btn-xs btn-success' href='#' onclick='showAddApplicantDialog(" + applicants[i].getUID() + ");' >Add To</a>");
	                                        		
	                                        	%>
	                                        </esd:SecurityAccessRequired>
	                                        <% if(guide != null && !declinedInterview) { %>
		                                        <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">
		                                        	<% 
		                                        	//out.println(applicants[i].getUID() + ":" + interviewSummaryMap.containsKey(applicants[i].getUID()));
		                                        	
		                                        		if(!interviewSummaryMap.containsKey(applicants[i].getUID()) || job.getJobType().equal(JobTypeConstant.POOL)) {
		                                        			out.println("<a class='btn btn-xs btn-success' href='addInterviewSummary.html?applicant_id=" + applicants[i].getUID() 
		                                        				+ "&comp_num=" + job.getCompetitionNumber() + "'>Add Interview Summary</a>");
		                                        		}
		                                        		else {
		                                        			out.println("<a class='btn btn-xs btn-primary' href='listInterviewSummaries.html?comp_num=" + job.getCompetitionNumber() + "&id=" + applicants[i].getUID() 
			                                        			+ "' >Interview Summaries</a>");
		                                        		}
		                                        	%>
		                                        </esd:SecurityAccessRequired>
		                                        <esd:SecurityAccessRequired permissions="PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW">
		                                        	<% 
		                                        		/**
		                                        		if(!hasJobSpecificInterviewSummary || job.getJobType().equal(JobTypeConstant.POOL)) {
		                                        			out.println("<a class='btn-action' href='addInterviewSummary.html?applicant_id=" + applicants[i].getUID() 
		                                        				+ "&comp_num=" + job.getCompetitionNumber() + "' target='_blank' >Add Interview Summary</a>");
		                                        		}else {
		                                        			out.println("<a class='btn-action' href='listInterviewSummaries.html?comp_num=" + job.getCompetitionNumber() + "&id=" + applicants[i].getUID() 
				                                        			+ "' target='_blank' >View/Add Interview Summaries</a>");
			                                        	}
		                                        		**/
		                                        		
		                                        		if(!interviewSummaryMap.containsKey(applicants[i].getUID()) || job.getJobType().equal(JobTypeConstant.POOL)) {
		                                        			out.println("<a class='btn btn-xs btn-success' href='addInterviewSummary.html?applicant_id=" + applicants[i].getUID() 
		                                        				+ "&comp_num=" + job.getCompetitionNumber() + "'>Add Interview Summary</a>");
		                                        		}
		                                        		else {
		                                        			out.println("<a class='btn btn-xs btn-primary' href='listInterviewSummaries.html?comp_num=" + job.getCompetitionNumber() + "&id=" + applicants[i].getUID() 
			                                        			+ "'>Interview Summaries</a>");
		                                        		}
		                                        		
		                                        	%>
		                                        </esd:SecurityAccessRequired>
		                                      <% } %>
		                                      <% if(!declinedInterview) { %>
		                                      	<a href="#" class="btn btn-xs btn-warning" title="Reference Request" onclick="OpenReferencePopUp('<%=applicants[i].getUID()%>');">Reference Request</a>
		                                      <% } %>
		                                     
		                                      </div></div>
	                                      </td>
	                                     
	                                    </tr>
                                 		<%}%>
                                 		 </tbody>
                                 		 </table>
                                 	<%}else{%>
                                    No applicants currently short listed for this competition.
                                  <%}%>                                  	
                                  </div> 
                                   
                      
 <!-- ADMINISTRATIVE FUNCTIONS -->
		<br/><br/>                             
	<div class="no-print" align="center">
  		
            						<esd:SecurityAccessRequired roles="ADMINISTRATOR,SEO - PERSONNEL,SENIOR EDUCATION OFFICIER,AD HR,ASSISTANT DIRECTORS">
                                  	<%if(!job.isAwarded() && !job.isCancelled()) { %>	
	                                 		<%if(!job.isShortlistComplete()){ %>
	                   	              			<%if(job.isClosed()){ %>	                                 			
	                                 					<a id='mark-shortlist-complete' class='btn btn-xs btn-success' href='markJobShortlistComplete.html?comp_num=<%= job.getCompetitionNumber() %>&closed=true'>MARK SHORTLIST AS COMPLETE</a>
	                                 			<%}%>
	                                 		<%}else{%>
	                                 			<div class="alert alert-success">Shortlist marked as complete <%= job.getFormattedShortlistCompleteDate() %>.</div>
	                                 			<a id='mark-shortlist-reopen' class='btn btn-xs btn-danger' href='markJobShortlistComplete.html?comp_num=<%= job.getCompetitionNumber() %>&closed=false'>REOPEN / UNLOCK SHORTLIST</a>
	                                 				
	                                 		<%}%>
	                                 	<%}else{%>
	                                 		<%if(job.isShortlistComplete()){ %>
		                                 		<div class="alert alert-success">Shortlist marked as complete <%= job.getFormattedShortlistCompleteDate() %>.</div>
	                                 		<%}else{%>
	                                 			<%if(job.isClosed()){ %>
	                                 			<a id='mark-shortlist-complete' class='btn btn-xs btn-success' href='markJobShortlistComplete.html?comp_num=<%= job.getCompetitionNumber() %>&closed=true'>MARK SHORTLIST AS COMPLETE</a>
	                                 			<%}%>                                 		
	                                 		<%}%>
	                                 	<%}%>
                                 	</esd:SecurityAccessRequired>
            
            
            					<%if(job.getIsSupport().equals("Y")){ %>
            						<a class="btn-xs btn btn-primary"  href='printable_shortlist_ss.jsp' target="_blank">Print Profiles</a>
            					<%}else{ %>
            						<a class="btn-xs btn btn-primary"  href='printable_shortlist.jsp' target="_blank">Print Profiles</a>
            						<%} %>	
                                <%if((rec != null) && (rec.length > 0)){%>
                                	<a onclick="loadingData()" class="btn btn-xs btn-primary"  href='admin_view_job_recommendation_list.jsp?comp_num=<%=job.getCompetitionNumber()%>'>View Recommendation(s)</a>
                                <%}else if(!job.isAwarded() && !job.isCancelled() && job.isClosed()  && job.isShortlistComplete()
                                		&& (ad != null || rth != null) && (applicants.length > 0) && ((rec == null) || (rec.length < 1))){%>
                                  <a class="btn btn-xs btn-success"  href='addJobTeacherRecommendation.html?comp_num=<%=job.getCompetitionNumber()%>'>Make Recommendation</a>
                                <%}%>
                                <%if(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW")){%>
                                  <a class="btn btn-xs btn-danger" href='admin_view_job_applicants.jsp'>Back to Applicant List</a>
                                <%}%>        
	              
  			
  	</div>                              
                                
                              
  
  
 </div></div></div>
  
                        
  
  <!-- Add Applicant -->
  
  <div id="add_applicant_dialog" title="Add Applicant To Competition...">
		<table cellspacing='3' cellpadding='3' border='0' align="center">
			<tr>
				<td class="displayHeaderTitle" valign="middle">Applicant:</td>
				<td>
					<table cellspacing='0' cellpadding='0' border='0'>
						<tr>
							<td style='padding-bottom:6px;'>
								<SELECT name='applicant_uid' id='applicant_uid' class='requiredInputBox' style='width:205px;height:23px;'>
								<%
									for(ApplicantProfileBean applicant : applicants)
										out.println("<OPTION VALUE='" + applicant.getUID() + "'>" + applicant.getFullName() + "</OPTION>");
								%>
								</SELECT>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>
				<td class="displayHeaderTitle" valign="middle">Competition:</td>
				<td>
					<table cellspacing='0' cellpadding='0' border='0'>
						<tr>
							<td style='padding-bottom:10px;'>
								<SELECT name='comp_num' id='comp_num' class='requiredInputBox' style='width:205px;height:23px;'>
								<%
									for(JobOpportunityBean j : jobs) {
										if(j.getCompetitionNumber().equals(job.getCompetitionNumber()))
												continue;
										out.println("<OPTION VALUE='" + j.getCompetitionNumber() + "'>" + j.getCompetitionNumber() + "</OPTION>");
									}
								%>
								</SELECT> 
								<input type='button' id='btn_add_applicant' class="ui-state-default ui-corner-all" value='add' />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<div id='response_msg alert alert-warning'>Click &quot;Add&quot; to confirm.</div>
		</table>
	</div>
	
	
	
	
	
	
<!-- Reference Request Modal -->	
	
<div id="refRequest" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Reference Request</h4>
      </div>
      <div class="modal-body">
       <input type="hidden" id="uid" name="uid">
		<b>Email Address:</b><br/>
        <input type='text' class="form-control" id='referrer_email' name='referrer_email' >
        <br/>
        <b>Reference Type:</b>
        <select name="reftype" class="form-control" id="reftype">
		<option value="-1">--- Select Reference Type---</option>
		<% if(job.getIsSupport().equals("N")){ %>
			<option value="ADMIN">Administrator</option>
			<option value="GUIDE">Guidance Counsellor</option>
			<option value="TEACHER">Teacher</option>
			<option value="EXTERNAL">Teacher (External)</option>
		<%}else{ %>
			<option value="MANAGE">Management</option>
			<option value="SUPPORT">Support Staff</option>		
		<%} %>
		</select>
        
        <div id='sending_email_msg' class="alert alert-info" style='display:none;'>Sending...</div>
        
         <div id="request_response_row" style='display:none;'>
         <div id="request_response_msg"></div>
         </div>
      </div>
      <div class="modal-footer">
      <button class="btn btn-xs btn-success" type="button" onclick="onSendReferenceCheckRequestNLESD();">Send Reference Check</button>
      <button class="btn btn-xs btn-warning" type="button" onclick="onManualReferenceCheckRequestNLESD();">Phone Reference Check?</button>
      <button type="button" class="btn btn-xs btn-danger" data-dismiss="modal">Cancel</button>
      </div>
    </div>

  </div>
</div>
</body>
</html>
