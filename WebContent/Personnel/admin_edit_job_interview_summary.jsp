<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  java.lang.reflect.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />

<%
  	InterviewSummaryBean summary = (InterviewSummaryBean) request.getAttribute("summary");
	JobOpportunityBean job = (JobOpportunityBean) request.getAttribute("job");
	ApplicantProfileBean profile = (ApplicantProfileBean) request.getAttribute("profile");
	InterviewGuideBean guide = InterviewGuideManager.getInterviewGuideBean(job);
	int max_interviewer_count = 5;
%>

<html>

	<head>
				<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>
		
		<script type='text/javascript'>
			$(function(){
					$('button').button();
					$('tr.form-row:not(.form-row-inner):odd').css({'background-color' : '#f4f4f4'});
					$('tr.form-row:not(.form-row-inner):even').css({'background-color' : '#ffffff'});
					$('tr.form-row td:not(.form-row-inner td)').css({'border-bottom' : 'solid 1px #e4e4e4'});
					
					$('#btn-submit').click(function(){
						if($('#strengths').val() == ''){
							alert('Strengths are required.');
						}
						else if($('#gaps').val() == ''){
							alert('Gaps are required.');
						}
						else if(!checkInterviewScores()){
							alert('Interviewer and question ratings are incomplete.');
						}
						else if($('#recommendation').val() == ''){
							alert('Recommendation selection is required.');
						}
						else {
							$('#frm-interview-summary').submit();
						}
					});
					
					function checkInterviewScores(){
						var found = false;
						var valid = true;
						
						for(j=1; j <= <%=max_interviewer_count %>; j++) {
							var interview_error = false;
							if($('#interviewer' + j).val() != '') {
								for(i=1; i <= <%= guide.getQuestionCount() %>; i++) {
									if($('#q' + i + '_' + j).val() == ''){
										interview_error = true;
										break;
									}
								}
							}
							else {
								for(i=1; i <= <%= guide.getQuestionCount() %>; i++) {
									if($('#q' + i + '_' + j).val() != ''){
										interview_error = true;
										break;
									}
								}
							}
							
							if(!interview_error) {
								found = true;
							}
							else {
								valid = false;
							}
						}

						return found && valid;
					}
					
			});
		</script>
	</head>
	
	<body>
  
	
	                          <form id='frm-interview-summary' action="editInterviewSummary.html" method="post">
	                          	<input type='hidden' name='confirmed' value='true' />
	                          	<input type='hidden' name='id' value='<%= summary.getInterviewSummaryId() %>' />
	                            
	                            
	                            Edit/Update Interview Summary
	                            
	                            
	                            Candidate
	                            
	                            <%= profile.getFullName() %>
	                            
	                            
	                            Competition Number
	                            
	                            <%= job.getCompetitionNumber() %>
	                            
	                            Strengths
	                           <textarea id='strengths' name='strengths'><%=summary.getStrengths() %></textarea>
	                               
	                           Gaps
	                           <textarea id='gaps' name='gaps'><%=summary.getGaps() %></textarea>
	                                      
	                                      
	                                      * Rank each answer from <%= guide.getRatingScaleBottom() %> to <%= guide.getRatingScaleTop() %>, with <%= guide.getRatingScaleTop() %> being outstanding.
	                                    		
	                                    		
	                                    		Interviewer
		                                    			<% for(int i=1; i <= guide.getQuestionCount(); i++) { %>
		                                    				Q<%= i %>
		                                    			<% } %>
	                                    			
	                                    			
	                                    			<%
	                                    				Method method = null;
	                                    				ArrayList<InterviewGuideQuestionBean> questions = guide.getQuestions();
	                                    				InterviewGuideQuestionBean question = null;	                                    				
	                                    				ArrayList<InterviewSummaryScoreBean> scores = summary.getInterviewSummaryScoreBeans();
	                                    				InterviewSummaryScoreBean score = null;	                                    				
	                                    				Double rating = null;
	                                    			
	                                    				for(int i=1; i <= max_interviewer_count; i++) { 
	                                    					if((i - 1) < scores.size()) {
	                                    						score = scores.get(i - 1);
	                                    					}
	                                    					else {
	                                    						score = null;
	                                    					}
	                                    			%>
	                                    				
		                                    					<input class="form-control" type='text' id='interviewer<%= i %>' name='interviewer<%= i %>' 
		                                    						value='<%= (score != null ? score.getInterviewer() : "") %>' />
		                                    				
		                                    				<%
		                                    					for(int j=1; j <= guide.getQuestionCount(); j++) { 
			                                    					question = questions.get(j-1);
			                                    					if(score != null) {
			                                    						method =	score.getClass().getDeclaredMethod("getScore"+j);
			                                    						
			                                    						rating = (Double)method.invoke(score);
			                                    					}
			                                    					else {
			                                    						rating = null;
			                                    					}
		                                    				%>
		                                    					
		                                    						<select class="form-control" id='q<%= j %>_<%= i %>' name='q<%= j %>_<%= i %>'>
		                                    							<option value=''>&nbsp;</option>
		                                    							<%
		                                    								if(question.getWeight() <= 0){
		                                    									out.println("<option value='0'" + (((rating != null)&&(rating.doubleValue() == 0)) ? "selected" : "") + ">N/A</option>");
		                                    								}
		                                    								else {
			                                    								if(guide.getRatingScaleBottom() < guide.getRatingScaleTop()) { 
			                                    									for(int r=guide.getRatingScaleBottom(); r <= guide.getRatingScaleTop(); r++){
			                                    										out.println("<option value='" + r + "'" + (((rating != null)&&(rating.doubleValue() == r)) ? "selected" : "") + ">" + r + "</option>");
			                                    									}
			                                    								}
			                                    								else {
			                                    									for(int r=guide.getRatingScaleTop(); r <= guide.getRatingScaleBottom(); r++){
			                                    										out.println("<option value='" + r + "'" + (((rating != null)&&(rating.doubleValue() == r)) ? "selected" : "") + ">" + r + "</option>");
			                                    									}
			                                    								}
		                                    								}
		                                    							%>
		                                    						</select>
		                                    					
		                                    				<% } %>
		                                    					
		                                    			
	                                    			<% } %>
	                                    			
	                                    		
	                                    		
	                                    		
	                                    		Recommendation
	                                    		
	                                        <select id='recommendation' name='recommendation'>
	                                        	<option value=''>---Select one---</option>
	                                        	<%for(InterviewSummaryBean.SummaryRecommendation sr : InterviewSummaryBean.SummaryRecommendation.values()) {
	                                        			if(sr.equals(InterviewSummaryBean.SummaryRecommendation.UNKNOWN)) continue; %>
	                                        			<option value='<%=sr.getValue()%>' <%= summary.getRecommendation().getValue() == sr.getValue() ? " SELECTED" : "" %>><%=sr.getText()%></option>
	                                        	<%}%>
	                                        </select>
	                                      
	                                      
	                                      
	                                        <button id='btn-submit' type="button">Update Summary</button>
	                                     
	                          </form>
	                        
	</body>
</html>
