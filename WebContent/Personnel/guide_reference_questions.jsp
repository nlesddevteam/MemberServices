<br/>
<%		if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){ %>
			  	<div class="alert alert-danger" style="text-align:center;"><%=(String)request.getAttribute("msg")%></div>
		<%} %>
    <div class="panel panel-success">
  	<div class="panel-heading">Guidance Counsellor Candidate Reference Check for <b><%= profile.getFullNameReverse() %>.</b></div>
  	<div class="panel-body">
  	<%
  	int totalRates=0;
  	int totalScore=0;  		
  	totalRates = ref.getPossibleTotal()*3; 	
	totalScore = ref.getTotalScore().intValue();
  	double resultScore = 100 * totalScore / totalRates;  	
	%>
  	<div class="alert alert-info">The total score for <%= profile.getFullNameReverse() %> on this reference from <%= ref.getProvidedBy() %> is <%=resultScore %>%. (<%=totalScore %> out of <%=totalRates %>).</div>
  	
	<div class="table-responsive">
                          
				<table class='table table-striped table-condensed' style='font-size:12px;'>   				
   				<tbody>
   				<tr><td class="tableTitle">Candidates Name:</td><td class="tableResult"><%= profile.getFullName() %></td></tr>
				<tr><td class="tableTitle">Person providing reference:</td><td class="tableResult"><%= ref.getProvidedBy() %></td></tr>
				<tr><td class="tableTitle">Email:</td><td class="tableResult"><%=ref.getEmailAddress()%></td></tr>
				<tr><td class="tableTitle">Position:</td><td class="tableResult"><%=ref.getProvidedByPosition() %></td></tr>
				<tr><td class="tableTitle">Date Provided:</td><td class="tableResult"><%=ref.getDateProvided() %></td></tr>
				</tbody>
				</table>
	</div>
	</div>
	</div>																			
												
	<div class="panel panel-success">
  <div class="panel-heading">Reference Questions</div>
  	<div class="panel-body">	
  	 The following reference checks must have been completed and attached to the teacher recommendation form.
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
													    <td>Did the candidate ask permission to use your name as a reference?
														 <br/><span class="tableAnswer"><%=ref.getQ1() %></span>
														</td>
													 </tr>
													 <tr>
													    <td>Q2.</td>
													    <td>How long have you known this candidate in a professional capacity?
														<br/><span class="tableAnswer"><%=ref.getQ2() %></span>
														</td>
													 </tr>
													 <tr>
													    <td>Q3.</td>
													    <td>How long has he/she worked in your school?
														<br/><span class="tableAnswer"><%=ref.getQ3() %></span>
														</td>
													 </tr>
													 <tr>
													    <td>Q4.</td>
													    <td>What has been his/her assignment this year?
													    <br/><span class="tableAnswer"><%=ref.getQ4() %></span>
													    </td>
													</tr>
													 <tr>
													    <td>Q5.</td>
													    <td>Was the growth plan successfully followed and completed?
														<br/><span class="tableAnswer"><%=ref.getQ5() %></span>
														</td>
													 </tr>
												</tbody>
										</table>
	</div>	
	</div>
</div>										
		                            
<div class="panel panel-success">
  <div class="panel-heading">Ratings</div>
  	<div class="panel-body">  	
  	
  	
									<div class="table-responsive">
									The following has been rated from a scale of <%=val1%> to <%=val4%>.  
																(<%=val4%>-Proficient, <%=val3%>-Competent, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
									
									 
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
													    <td>Standard 1: Comprehensive Guidance Program<br/>
													    	The guidance counsellor collaborates and develops an annual comprehensive school guidance program which outlines the implementation of interventions that promote the holistic development of the student:
														</td>
													    <td>
													    <span class="tableAnswer"><%=ref.getScale1() %></span>
													    </td>
													 </tr>													
							                         <tr>
													    <td>S2.</td>
													    <td>Standard 2: Education System<br/>
															The guidance counsellor understands the overall education system and engages in the planning and managing of tasks to support the learning and development of students:
														</td>
													    <td>
													    <span class="tableAnswer"><%=ref.getScale2() %></span>
													    </td>
													 </tr>
													 <tr>
													    <td>S3.</td>
													    <td>Standard 3: Student Development<br/>
															The guidance counsellor understands the diversity of human growth, development , behavior and learning and promotes the holistic development of the student:
														</td>
													    <td>
													    <span class="tableAnswer"><%=ref.getScale3() %></span>
													    </td>
													 </tr>
													 <tr>
													    <td>S4.</td>
													    <td>Standard 4: Diversity<br/>
													    	The guidance counsellor understands the dimensions of human diversity and the possible influence they many have on child/adolescent development:
													    </td>
													    <td>
													    <span class="tableAnswer"><%=ref.getScale4() %></span>
													    </td>
													 </tr>
													 <tr>
													    <td>S5.</td>
													    <td>Standard 5: Comprehensive Assessment<br/>
															The guidance counsellor understands the assessment process and its implications for student learning:</td>
													    <td>
													    <span class="tableAnswer"><%=ref.getScale5() %></span>
													    </td>
													 </tr>
													 <tr>
													    <td>S6.</td>
													    <td>Standard 6: Counselling<br/>
															The guidance counsellor possesses knowledge and skills necessary to establish and facilitate individual and group counselling:</td>
													    <td>
													    <span class="tableAnswer"><%=ref.getScale6() %></span>
													    </td>
													 </tr>
													 <tr>
													    <td>S7.</td>
													    <td>Standard 7: Career Development<br/>
															The guidance counsellor understands that career development is a lifelong process. He/she develops programs and interventions to promote the career development of all students:</td>
													    <td>
													    <span class="tableAnswer"><%=ref.getScale7() %></span>
													    </td>
													 </tr>
													 <tr>
													    <td>S8.</td>
													    <td>Standard 8: Crisis Intervention<br/>
															The guidance counsellor participates in the development and  implementation of a response plan for possible crisis situations:</td>
													    <td>
													    <span class="tableAnswer"><%=ref.getScale8() %></span>
													    </td>
													 </tr>
													 <tr>
													    <td>S9.</td>
													    <td>Standard 9: Ethical Responsibilities<br/>
															The guidance counsellor understands the ethical requirements in providing a comprehensive school guidance program:</td>
													    <td>
													    <span class="tableAnswer"><%=ref.getScale9() %></span>
													    </td>
													 </tr>													 
							                       </tbody>
							                       </table>
							            </div>
	</div>
</div>			                          
			                            
			                            
<div class="panel panel-success">
  <div class="panel-heading">Other Information</div>
  	<div class="panel-body">	
  	<div class="alert alert-danger" id="section3Error" style="display:none;">Please make sure Q6 is answered below.</div>
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
													   	<td>Q6.</td>
													    <td>If given the opportunity would you hire this candidate?
														<br/><span class="tableAnswer"><%=ref.getQ6()%></span>
														
														</td>
													 </tr>
													 <tr>
													 	<td>&nbsp;</td>
													   	<td>
													   	Additional Comments:
														<br/><span class="tableAnswer"><%= (!StringUtils.isEmpty(ref.getQ6Comment())?ref.getQ6Comment():"none") %></span>
														</td>													    
													 </tr>
												</tbody>
										</table>
										
									</div>
	</div>
</div>	