<%@ page language="java"
         import="java.util.*,
                  java.text.*,
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
<style>
.tableTitle {font-weight:bold;width:20%;}
.tableResult {font-weight:normal;width:80%;}

.tableTitleL {font-weight:bold;width:15%;}
.tableResultL {font-weight:normal;width:35%;}
.tableName {font-weight:bold;}
.tableResult1 {width:10%;}
.tableResult2 {width:10%;}
.tableResult3 {width:10%;}
.tableResult4 {width:10%;}
.tableResult5 {width:10%;}
.tableResult6 {width:10%;}
input {border:1px solid silver;}

</style>		
		
		<script type='text/javascript'>
			$(function(){
									
					
					$('#btn-submit').click(function(){
						var edstr = CKEDITOR.instances['strengths'].getData();
						var edgaps = CKEDITOR.instances['gaps'].getData();
						if(edstr == ""){							
							$('#msgerr').css('display','block').html('ERROR: Strengths are required.').delay(5000).fadeOut();
						}
						else if(edgaps == ""){							
							$('#msgerr').css('display','block').html('ERROR: Gaps are required.').delay(5000).fadeOut();
						}
						else if(!checkInterviewScores()){							
							$('#msgerr').css('display','block').html('ERROR: Interviewer and question ratings are incomplete.').delay(5000).fadeOut();
						}
						else if($('#recommendation').val() == ""){
							$('#msgerr').css('display','block').html('ERROR: Recommendation selection is required.').delay(5000).fadeOut();							
						}
						else {
							$('#msgok').css('display','block').html('SUCCESS: Summary entered and submitted successfully.').delay(5000).fadeOut();
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
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Add Interview Summary</b></div>
      			 	<div class="panel-body">
	                     <c:if test="${not empty msg}">
		                     <div class="alert alert-danger"> ${msg}</div>                                     
		                 </c:if>
		                 	<div class="alert alert-success" align="center" id="msgok" style="display:none;"></div>
							<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"></div>	
		                 
		                 
	                          <form id='frm-interview-summary' action="addInterviewSummary.html" method="post">
	                          	<input type='hidden' name='confirmed' value='true' />
	                          	<input type='hidden' name='applicant_id' value='<%= profile.getUID() %>' />
	                          	<input type='hidden' name='comp_num' value='<%= job.getCompetitionNumber() %>' />
	                            <table class="table table-striped table-condensed" style="font-size:11px;">							   
							    <tbody>
							    <tr style="border-top:1px solid silver;">
							    <td class="tableTitle">Candidate:</td>
				                <td class="tableResult"><%=profile.getFullName()%></td>
								</tr>
								<tr>
							    <td class="tableTitle">Competition#:</td>
				                <td class="tableResult"><%=job.getCompetitionNumber()%></td>
								</tr>       			
         						<tr>
							    <td class="tableTitle">Strengths:</td>
				                <td class="tableResult"><textarea id="strengths" name="strengths" class="form-control"></textarea></td>
								</tr>
	                            <tr style="border-bottom:1px solid silver;">
							    <td class="tableTitle">Gaps:</td>
	                            <td class="tableResult"><textarea id="gaps" name="gaps" class="form-control"></textarea></td>	                            
	                            </tbody>
         						</table>   
	                                 
	                            * Rank each answer from <%= guide.getRatingScaleBottom() %> to <%= guide.getRatingScaleTop() %>, with <%= guide.getRatingScaleTop() %> being outstanding.
	                                    		
	                            <table class="table table-striped table-condensed" style="font-size:11px;">							   
							    <thead>
							    <tr>
							    <th class="tableName" width="*">INTERVIEWER</th>
		                                    			<% for(int i=1; i <= guide.getQuestionCount(); i++) { %>
		                                    				 <th class="tableResult<%=i%>">Q/C<%=i%></th>
		                                    			<% } %>
	                             </tr> 
	                             </thead>      			
	                             <tbody>       			
	                                    			<%
	                                    				ArrayList<InterviewGuideQuestionBean> questions = guide.getQuestions();
	                                    				InterviewGuideQuestionBean question = null;
	                                    			
	                                    				for(int i=1; i <= max_interviewer_count; i++) { %>
	                                    				
	                                    				<tr>
	                                    				<td class=""><input class="form-control" type='text' id='interviewer<%= i %>' placeholder="Add Interviewer Name" name='interviewer<%= i %>'/></td>
	                                    				
		                                    				<%for(int j=1; j <= guide.getQuestionCount(); j++) { 
		                                    					question = questions.get(j-1); %>
		                                    					
		                                    						<td><select class="form-control" id='q<%= j %>_<%= i %>' name='q<%= j %>_<%= i %>'>
		                                    							<option value=''>Select</option>
		                                    							<%
		                                    								if(question.getWeight() <= 0){
		                                    									out.println("<option value='0'>N/A</option>");
		                                    								}
		                                    								else {
			                                    								if(guide.getRatingScaleBottom() < guide.getRatingScaleTop()) { 
			                                    									for(int r=guide.getRatingScaleBottom(); r <= guide.getRatingScaleTop(); r++){
			                                    										out.println("<option value='" + r + "'>" + r + "</option>");
			                                    									}
			                                    								}
			                                    								else {
			                                    									for(int r=guide.getRatingScaleTop(); r <= guide.getRatingScaleBottom(); r++){
			                                    										out.println("<option value='" + r + "'>" + r + "</option>");
			                                    									}
			                                    								}
		                                    								}
		                                    							%>
		                                    						</select>
		                                    					</td>
		                                    				<% } %>
		                                    				</tr>	
		                                    		
	                                    			<% } %>
	                                    </tbody>
	                                    </table>			
	                                    		
	                             <table class="table table-striped table-condensed" style="font-size:11px;">							   
							    <tbody>
							    <tr style="border-top:1px solid silver;">
							    <td class="tableTitle">Recommendation: </td>
	                            <td class="tableResult">     
	                                        <select class="form-control" id='recommendation' name='recommendation'>
	                                        	<option value=''>---Select one---</option>
	                                        	<%for(InterviewSummaryBean.SummaryRecommendation sr : InterviewSummaryBean.SummaryRecommendation.values()) {
	                                        			if(sr.equals(InterviewSummaryBean.SummaryRecommendation.UNKNOWN)) continue; %>
	                                        			<option value='<%=sr.getValue()%>'><%=sr.getText()%></option>
	                                        	<%}%>
	                                        </select>
	                             </td></tr>
	                             <tr style="border-top:1px solid silver;"><td></td><td></td></tr>
	                             </tbody>
	                             </table>         
	                              <br/>   
	                           <div align="center" class="no-print">
	                                       <input id='btn-submit' class="btn btn-xs btn-success" type="button" value="Submit Summary"/>
	                                       <a class="btn btn-danger btn-xs" href="javascript:history.go(-1);">Back</a>
	                           </div>          
	                          </form>
	                          <br/>
	    </div></div>     
	    
<script>
    CKEDITOR.replace( 'strengths');
    CKEDITOR.replace( 'gaps');
</script>	    
	                 
	</body>
</html>
