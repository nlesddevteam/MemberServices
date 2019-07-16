<%@ page language="java"
         import="java.text.*,
         				 java.util.*,
         				 com.awsd.security.*,
         				 com.esdnl.util.*,
         				 com.awsd.school.*,
         				 java.io.*,
                 com.awsd.school.bean.*,
         				 com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.personnel.jobs.constants.*" 
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
	
	@SuppressWarnings("unchecked")
	TreeMap<String, TreeMap<Integer, Vector<ApplicantProfileBean>>> regions 
		= (TreeMap<String, TreeMap<Integer, Vector<ApplicantProfileBean>>>) session.getAttribute("JOB_SHORTLIST");
	
	ApplicantProfileBean[] applicants = null;
  
	AdRequestBean ad = (AdRequestBean) request.getAttribute("AD_REQUEST");
  
	User usr = (User)session.getAttribute("usr");
  
  Calendar rec_search_cal = Calendar.getInstance();
  rec_search_cal.clear();
  rec_search_cal.set(2015, Calendar.JUNE, 1);
  Date rec_search_date = rec_search_cal.getTime();
  
  int totalapplicants = 0;
  int totalregion = 0;
  int statusi=0;
  int regionNum=0;
  TreeMap<String, ApplicantProfileBean> all_applicants = new TreeMap<String, ApplicantProfileBean>();  
  InterviewGuideBean guide = InterviewGuideManager.getInterviewGuideBean(job);
  Map<String, InterviewSummaryBean> interviewSummaryMap = InterviewSummaryManager.getInterviewSummaryBeansMap(job);
  
%>


<html>
<head>
	<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>
	
	
	<script type="text/javascript">
	$('document').ready(function(){

		$('tr.applicant-list:even').css('background-color', '#FFFFFF');
		$('tr.applicant-list:odd').css('background-color', '#F4F4F4');
		
		$('tr.region td').css({
			'border-bottom':'2px solid #003366', 
			'border-top':'10px solid white', 
			'font-size':'11px', 
			'font-weight':'bold', 
			'color': '#003366', 
			'padding':'3px', 
			'padding-left':'0px'
		});
		
		$('tr.region td span').css({
			'font-size':'10px', 
			'font-weight':'normal',
			'color': '#333333',
			'padding-bottom':'5px'
		});
		
		$('tr.days td').css({
			'font-size':'10px', 
			'font-weight':'bold', 
			'color': '#333333', 
			'padding':'3px',
			'padding-top' : '5px',
			'padding-left':'0px',
			'border-bottom':'solid 1px #909090'
		});
		
		$('tr.days td span').css({
			'border':'solid 1px #909090',
			'padding':'3px'
		});
		
		$('#add_applicant_dialog').dialog({
			'autoOpen': false,
			'bgiframe': true,
			'width':500,
			'height': 250,
			'modal': true,
			//'hide': 'explode',
			'buttons': {'close': function(){$('#add_applicant_dialog').dialog('close');}}
		});
		
		$('#btn_add_applicant').click(function(){
			$.post("addJobApplicant.html", { comp_num: $('#comp_num').val(), sin: $('#applicant_uid').val(), ajax: true }, 
					function(data){
						parseAddApplicantResponse(data);
					}, "xml");
		});
		
		$('#btn_apply_filter, #btn_add_applicant').hover(
			function() { $(this).addClass('ui-state-hover'); }, 
			function() { $(this).removeClass('ui-state-hover'); }
		);
		
	});

	function showAddApplicantDialog(uid) {
		$('#applicant_uid').val(uid);
		$("#add_applicant_dialog").dialog('open');
		
		return false;
	}
	
	function parseAddApplicantResponse(data){
		var xmlDoc=data.documentElement;
		var msg = xmlDoc.getElementsByTagName("RESPONSE-MSG")[0].childNodes[0].nodeValue;
		
		$('#response_msg > td').html(msg);
		$('#response_msg').show();
	}
</script>

<script>
 $('document').ready(function(){
	  $(".jobsapp").DataTable(
		{"order": [[ 2, "desc" ]],
			"lengthMenu": [[25,50,100,200,-1], ["25","50","100","200","All"]]
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
	               	<div class="panel-heading"><b>Competition # <%=job.getCompetitionNumber()%> Short List (Pool)</b></div>
      			 	<div class="panel-body">
The applicants below are categorized by region and zone. Click on a tab to open the list of applicants short listed for this competition.<br/><br/>
<!-- <a href='viewJobShortList.html?comp_num=<%=job.getCompetitionNumber()%>&alphabetized=true'>Alphabetized List</a>-->
                           
                                   
               <%if(request.getAttribute("msg")!=null){%>
               	<div class="alert alert-danger"><%=(String)request.getAttribute("msg")%></div>                                      
               <%}%>
                                  
                     <div class="table-responsive">              
                      Applicants listed below are initially sorted based on seniority. Click on any header to sort by that category. To find based on a applicant and/or region, use the search tool below right.<br/><br/>
										  
                                       <% applicants = ApplicantProfileManager.getApplicantProfileBeanByJob(job.getCompetitionNumber());%>
                     
                                  <%if(applicants.length > 0){ %>  
                                	  
                                	  
                                	   <table class="jobsapp table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;">
									    <thead>									    
									      <tr>
									        <th width='20%'>NAME</th>									        
									        <th width='17%'>EMAIL/TELEPHONE</th>
									        <th width='10%'>SENIORITY</th>
									        <th width="13%">STATUS</th>				        
									        <th width='40%'>POSITION/OPTIONS/REGIONS</th>									       					        
									      </tr>
									    </thead>
									    <tbody>                               	  
                                	  
                                	  
                                	  
                                	  
                                	  
                                  	<%TeacherRecommendationBean[] recs = null;                                  	
                                  	String cssClass = "NoPosition";
                                  	String cssText = "No Position";
                                  	String position = null;
                                  	DecimalFormat df = new DecimalFormat("0.00");
                                  	int userCnt=0;
                                  	
                                    for(int i=0; i < applicants.length; i++){
                                    	cssClass = "NoPosition";
                                    	cssText = "No Position";
                                    	position = null;
                                    	recs = applicants[i].getRecentlyAcceptedPositions(rec_search_date);
                                    	
                                    
                                    	try{
                                    		
                                    	
                                    	if((recs != null) && (recs.length > 0)){
                                    		if(recs[0].getJob().getJobType().equal(JobTypeConstant.REGULAR)){
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
                                    		else if(recs[0].getJob().getJobType().equal(JobTypeConstant.REPLACEMENT)){
                                    			cssClass = "ReplacementPosition";
                                    			cssText = "Replacement";
                                    			position = recs[0].getJob().getCompetitionNumber() + ":Replacement (" + df.format(recs[0].getTotalUnits()) + ") @ "  + recs[0].getJob().getJobLocation();
                                    		}
                                    		else if(recs[0].getJob().getJobType().equal(JobTypeConstant.TRANSFER)){
                                    			cssClass = "TransferPosition";
                                    			cssText = "Transfer";
                                    			position = recs[0].getJob().getCompetitionNumber() + ":Transfer (" + df.format(recs[0].getTotalUnits()) + ") @ "  + recs[0].getJob().getJobLocation();
                                    		}
                                    		
                                    		if(position != null) {                                    			
                                    			position += " - Accepted: " + recs[0].getOfferAcceptedDateFormatted();
                                    		}
                                    		
                                    	}
                                    	}catch(Exception ec){
                                    		System.out.println(ec.getMessage());
                                    	}
                                    %>	
                                    		<tr>                                   		
                                    		
	                                    	<%statusi++; %>	                                    	                                       
	                                    	<td width='25%'><%=applicants[i].getSurname()%>, <%=applicants[i].getFirstname()%></td>
	                                    	<td><a href="mailto:<%=applicants[i].getEmail()%>"><%=applicants[i].getEmail()%></a><br/>
	                                    	Tel: <%=applicants[i].getHomephone()%>
	                                    	</td>
	                                    	<td>
	                                    	 <%if (applicants[i].getSenority() > 0) {%>
                                                      <span style='color:red;'><%= applicants[i].getSenority()%></span>
                                            <%} else {%>                                             
                                               <span style="color:DimGrey;">0</span>
                                                <%}%>
	                                    	</td>	                                    	     
	                                    	<td style="text-align:center;vertical-align:middle;" class="<%=cssClass%>" id="statusBlock<%=statusi%>"><%=cssText%></td>
	                                    	<td>
	                                    	<div style="padding-top:5px;text-align:right;">	
			                                            <a class='btn btn-xs btn-primary' href="viewApplicantProfile.html?sin=<%=applicants[i].getSIN()%>" >Profile</a>
			                                        <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">
			                                        	<%
			                                        		if(!job.isAwarded() && !job.isCancelled())
			                                        			out.println("<a class='btn btn-xs btn-danger' href='removeShortlistApplicant.html?sin=" + applicants[i].getSIN() 
			                                        					+ "' >Remove</a>");
			                                        	
			                                        		if(job.getJobType().equal(JobTypeConstant.POOL))
			                                        			out.println("<a class='btn btn-xs btn-success' href='#' onclick='showAddApplicantDialog(" + applicants[i].getUID() 
			                                      						+ ");' >Add To</a>");
			                                        	%>
			                                        </esd:SecurityAccessRequired>
			                                        <% if(guide != null) { %>
				                                        <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">
				                                        	<% 
				                                        		if(!interviewSummaryMap.containsKey(applicants[i].getUID())) {
				                                        			out.println("<a class='btn btn-xs btn-success' href='addInterviewSummary.html?applicant_id=" + applicants[i].getUID() 
				                                        				+ "&comp_num=" + job.getCompetitionNumber() + "'>Add Interview Summary</a>");
				                                        		}
				                                        		else {
				                                        			out.println("<a class='btn btn-xs btn-primary' href='viewInterviewSummary.html?id=" 
				                                        				+ Integer.toString(((InterviewSummaryBean)interviewSummaryMap.get(applicants[i].getUID())).getInterviewSummaryId()) 
					                                        			+ "'>View Interview Summary</a>");
				                                        		}
				                                        	%>
				                                        </esd:SecurityAccessRequired>
			                                        <% } %>
			                                        </div>												    
			                                    	<div style="float:left;color:DimGrey;padding-top:3px;">
			                                    			<%if(!StringUtils.isEmpty(position)){ %>
						                                 		<b><%=cssText%> Position:</b> <%=position %>
						                                 	<%} else {%>
						                                 		No current position information available for <%=applicants[i].getFirstname()%>.
						                                 	<%} %>
			                                    	<br/>
				                                    	
		                                        
		                                        <% RegionBean[] regionPrefs = ApplicantRegionalPreferenceManager.getApplicantRegionalPreferencesMap(applicants[i]).values().toArray(new RegionBean[0]); %>
		                                           <%if((regionPrefs != null) && (regionPrefs.length > 0)) { %>
												    <span style="font-size:9px;padding-top:5px;">
												    <b>Preferred Regions (Zones): </b>												   							    
												    <% for(int rp=0; rp < regionPrefs.length; rp ++){%>                                    	
			                                    	<c:set var="whatRegion" value="<%=regionPrefs[rp].getZone()%>"/>
			                                    	<c:set var="whatZone" value="<%=regionPrefs[rp].getName()%>"/>                                  	
			                                    	<c:choose>
														<c:when test="${whatRegion eq 'avalon' or whatRegion eq 'eastern' }">Avalon Region</c:when>
														<c:when test="${whatRegion eq 'central'}">Central Region</c:when>
														<c:when test="${whatRegion eq 'western'}">Western Region</c:when>
														<c:when test="${whatRegion eq 'labrador'}">Labrador Region</c:when>
														<c:when test="${whatRegion eq 'provincial'}">Provincial</c:when>
														<c:otherwise><span style="color:Red;">ERROR: Unknown Region</span></c:otherwise>
													</c:choose>
													<c:choose>
														<c:when test="${whatZone eq 'nlesd - provincal' or whatZone eq 'nlesd - provincial'}"><i>(All Province)</i></c:when>
														<c:when test="${whatZone eq 'all labrador region'}"><i>(All Labrador)</i></c:when>	
														<c:when test="${whatZone eq 'all western region'}"><i>(All Western Zone)</i></c:when>
														<c:when test="${whatZone eq 'all central region'}"><i>(All Central Zone)</i></c:when>
														<c:when test="${whatZone eq 'all eastern region' or whatZone eq 'all avalon region'}"><i>(All Avalon Zone)</i></c:when>							
														<c:otherwise><span style="text-transform:Capitalize;"><i>(<%=regionPrefs[rp].getName()%> Regional Zone)</i></span></c:otherwise>
													</c:choose>
													<%if (rp > (regionPrefs.length-2)) {%>
														<span style="margin-left:-3px;">.</span>
													<%} else {%>
														<span style="margin-left:-3px;">,</span>
													<%}%>
													<%}%>
													</span>
													<%}%>
												</div>
												
												
												
												
												
                                      </td>
                                    </tr>
                                 <% all_applicants.put(applicants[i].getUID(), applicants[i]);
                                 		} %>
                                 	
                                 		</tbody>
                                 </table>
                                  <% }else{%> 
                                  
                                 No applicants currently short listed for this competition.
                                                                   
                                 <%} %>
                                  
                                  
									  
                                


                                  
                                  </div>
                                  </div></div></div>
                                  
                                  <!-- 
                                  <script type="text/javascript">$('#totalapplicants').text('<%=totalapplicants%>');</script>
                                   -->	
  <!-- ADMINISTRATIVE FUNCTIONS -->
		<br/>                            
	<div class="no-print" align="center">                      
                           
                                
                                <a class="btn btn-info btn-xs"  href='printable_shortlist.jsp'>Print Short List Profiles</a>
                                <%if(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW")){%>
                                  <a class="btn btn-danger btn-xs"  href='admin_view_job_applicants.jsp'>Back to Applicants List</a>
                                <%}%>
                              
                  
     		
     </div>		
  
  
<br/><br/>
  
  
  
  
  
  
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
									for(ApplicantProfileBean applicant : all_applicants.values())
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
			
			<tr id='response_msg'>
				<td colspan='2' class='ui-corner-all'>Click &quot;Add&quot; to confirm.</td>
			</tr>
		</table>
	</div>

</body>
</html>
