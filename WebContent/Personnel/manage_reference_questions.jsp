<br/>
		<%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){ %>
			                            	<div class="alert alert-danger" style="text-align:center;"><%=(String)request.getAttribute("msg")%></div>
		<%} %>
    <div class="panel panel-success">
  	<div class="panel-heading">Management Candidate Reference Check for <b><%= profile.getFullNameReverse() %>.</b></div>
  	<div class="panel-body">
  	<%
  	int totalRates=0;
  	int totalScore=0;  		
  	totalRates = ref.getPossibleTotal()*5; 	
	totalScore = ref.getTotalScore().intValue();
  	double resultScore = 100 * totalScore / totalRates; 
	%>	
	<div class="alert alert-info">The total score for <%= profile.getFullNameReverse() %> on this reference from <%= ref.getProvidedBy() %> is <%=resultScore %>%. (<%=totalScore %> out of <%=totalRates %>).</div>
  	
	<div class="table-responsive">
                          
				<table class='table table-striped table-condensed' style='font-size:12px;'>   				
   				<tbody>
   				<tr><td class="tableTitle">Candidates Name:</td><td class="tableResult"><%= profile.getFullName() %></td></tr>
				<tr><td class="tableTitle">Person providing reference:</td><td class="tableResult"><%= ref.getProvidedBy() %></td></tr>
				<tr><td class="tableTitle">Position:</td><td class="tableResult"><%=ref.getProvidedByPosition() %></td></tr>
				<tr><td class="tableTitle">Date Provided:</td><td class="tableResult"><%=ref.getDateProvided() %></td></tr>
				</tbody>
				</table>
	</div>
	</div>
	</div>						

<!-- Questions -------------------------------------------------------------------------->		

<div class="panel panel-success">
  <div class="panel-heading">Reference Questions</div>
  	<div class="panel-body">	
  	<div class="alert alert-danger" id="section1Error" style="display:none;">Please make sure ALL questions below are answered.</div>
  	
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
													    <td>In what capacity are you able to assess the performance of this applicant?
													    <br/><span class="tableAnswer"><%=ref.getQ3() %></span>
													    </td>													    
													 </tr> 
													 											 
										    	</tbody>
									    </table>
									</div>	
	</div>
</div>	

<!-- Ratings -------------------------------------------------------------------------->		


<div class="panel panel-success">
  <div class="panel-heading">Leadership Ratings</div>
  	<div class="panel-body">
  	<div class="alert alert-danger" id="section2Error" style="display:none;">ERROR: Please make sure you rate each statement below.</div>	
									<div class="table-responsive">
									
										On a scale of <%=val1%> to <%=val6%> please rate the candidate on the following statements: 
										
										(<%=val6%>-Excellent, <%=val5%>-Good, <%=val4%>-Average, <%=val3%>-Needs Improvement, <%=val2%>-Unacceptable and <%=val1%>-N/A):
																		
									
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
													    <td>1.</td>
													    <td>Ability to understand and apply the knowledge required for this type of position</td>
													    <td><span class="tableAnswer"><%=ref.getScale1()%></span></td>
													 </tr>
													 <tr>
													    <td>2.</td>
													    <td>Ability to use appropriate leadership style</td>
													    <td><span class="tableAnswer"><%=ref.getScale2()%></span></td>
													 </tr>
													 <tr>
													    <td>3.</td>
													    <td>Ability to communicate with supervisors, co-workers, contractors, and other external stakeholders</td>
													    <td><span class="tableAnswer"><%=ref.getScale3()%></span></td>
													 </tr>
													 <tr>
													    <td>4.</td>
													    <td>Ability to make rational decisions in the best interests of the organization</td>
													    <td><span class="tableAnswer"><%=ref.getScale4()%></span></td>
													 </tr>
													 <tr>
													    <td>5.</td>
													    <td>Displays positive attitude or enthusiasm towards job</td>
													    <td><span class="tableAnswer"><%=ref.getScale5()%></span></td>
													 </tr>
													 <tr>
													    <td>6.</td>
													    <td>Ability to provide customer service and client satisfaction</td>
													   <td><span class="tableAnswer"><%=ref.getScale6()%></span></td>
													 </tr>
													 <tr>
													    <td>7.</td>
													    <td>Ability to integrate a variety of functions using organizational & time management skills</td>
													    <td><span class="tableAnswer"><%=ref.getScale7()%></span></td>
													 </tr>
													 <tr>
													    <td>8.</td>
													    <td>Ability to be a team player and get along with people(peers, management and staff)</td>
													    <td><span class="tableAnswer"><%=ref.getScale8()%></span></td>
													 </tr>
													 <tr>
													    <td>9.</td>
													    <td>Flexibility/adjustment to new situations</td>
													    <td><span class="tableAnswer"><%=ref.getScale9()%></span></td>
													 </tr>
													 <tr>
													    <td>10.</td>
													    <td>Ability to handle conflict, reduce tensions and arrive at satisfactory conclusion</td>
													    <td><span class="tableAnswer"><%=ref.getScale10()%></span></td>
													 </tr>
													 <tr>
													    <td>11.</td>
													    <td>Dependability (punctuality and attendance)</td>
													    <td><span class="tableAnswer"><%=ref.getScale11()%></span></td>
													 </tr>
													 <tr>
													    <td>12.</td>
													    <td>Ability to work independently with minimum supervision</td>
													    <td><span class="tableAnswer"><%=ref.getScale12()%></span></td>
													 </tr>
													 <tr>
													    <td>13.</td>
													    <td>Level of professionalism</td>
													    <td><span class="tableAnswer"><%=ref.getScale13()%></span></td>
													 </tr>
													 <tr>
													    <td>14.</td>
													    <td>Displays confidence and is trustworthy</td>
													   <td><span class="tableAnswer"><%=ref.getScale14()%></span></td>
													 </tr>
													 <tr>
													    <td>15.</td>
													    <td>Individual's performance compared to other with similar job duties</td>
													   <td><span class="tableAnswer"><%=ref.getScale15()%></span></td>
													 </tr>									 
										    	</tbody>
									    </table>
									</div>	
	</div>
</div>

																
<!-- Other Information  ----------------------------------------------------------------->																																				
																		
																		
<div class="panel panel-success">
  <div class="panel-heading">Questions</div>
  	<div class="panel-body">	
  	<div class="alert alert-danger" id="section3Error" style="display:none;">Please make sure you answered below.</div>
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
													   	<td>Q4.</td>
													    <td>
													    Please provide us with a summary of your professional relationship with the candidate and give a brief description of the duties while in your employ<br/>
													    <br/><span class="tableAnswer"><%=ref.getQ4()== null ? "N/A" :ref.getQ4() %></span>
													    </td>
													 </tr>
													 <tr>
													   	<td>Q5.</td>
													    <td>Please comment on the applicant's strengths
													    <br/><span class="tableAnswer"><%=ref.getQ5()== null ? "N/A" :ref.getQ5() %></span>
													    </td>
													 </tr>
													 <tr>
													   	<td>Q6.</td>
													    <td>Please comment on the applicant's weaknesses<br/>
													    <br/><span class="tableAnswer"><%=ref.getQ6()== null ? "N/A" :ref.getQ6() %></span>
													    </td>
													 </tr>
													 <tr>
													   	<td>Q7.</td>
													    <td>Did the applicant have any attendance problems while in your employ?<br/> 
													    		
														 <span class="tableAnswer"><%=ref.getQ7() ==null ? "N/A" :ref.getQ7() %></span>
													    <span class="tableAnswer"><%=ref.getQ7Comment()== null ? "" :"<br/>"+ref.getQ7Comment() %></span>
														
														
														 </td>
													 </tr>
													 <tr>
													   	<td>Q8.</td>
													    <td>Did the applicant have any disciplinary issues while in your employ?<br/>																
													     <span class="tableAnswer"><%=ref.getQ8() ==null ? "N/A" :ref.getQ8() %></span>
													    <span class="tableAnswer"><%=ref.getQ8Comment()== null ? "" :"<br/>"+ref.getQ8Comment() %></span>
													   
													    </td>
													 </tr>
													 <tr>
													   	<td>Q9.</td>
													    <td>What was the applicant's reason for leaving you employ?
													    <br/><span class="tableAnswer"><%=ref.getQ9()== null ? "N/A" :ref.getQ9() %></span>
													     </td>
													 </tr>
													 <tr>
													   	<td>Q10.</td>
													    <td>Would you hire/rehire the applicant? 
													    <br/><span class="tableAnswer"><%=ref.getQ10()== null ? "N/A" :ref.getQ10() %></span>
													    </td>
													 </tr>
													 <tr>
													   	<td>Q11.</td>
													    <td>Overall, how would you rate the applicant's performance while employed with you?
													    <br/><span class="tableAnswer"><%=ref.getQ11()== null ? "N/A" :ref.getQ11() %></span>	
													    </td>
													 </tr>
													 <tr>
													   	<td>Q12.</td>
													    <td>Is there any other information you'd like to provide that might be helpful in making a hiring decision?
													    <br/><span class="tableAnswer"><%=ref.getQ12()== null ? "None." :ref.getQ12() %></span>	
													  
													  </td>
													 </tr>
												</tbody>
										</table>
										
									</div>
	</div>
</div>