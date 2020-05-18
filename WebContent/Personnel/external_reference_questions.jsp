
<br/>
<%		if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){ %>
			  	<div class="alert alert-danger" style="text-align:center;"><%=(String)request.getAttribute("msg")%></div>
		<%} %>
    <div class="panel panel-success">
  	<div class="panel-heading">External Candidate Reference Check for <b><%= profile.getFullNameReverse() %>.</b></div>
  	<div class="panel-body">
  	 <%
  	int totalRates=0;
  	int totalScore=0;  		
  	totalRates = ref.getPossibleTotal()*3; 	
	totalScore = ref.getTotalScore().intValue();
	double resultScore = 0;
	if(totalRates > 0){
	
  		resultScore = 100 * totalScore / totalRates;
	}  	
	%>	
  	<div class="alert alert-info">The total score for <%= profile.getFullNameReverse() %> on this reference from <%= ref.getProvidedBy() %> is <%=resultScore %>%. (<%=totalScore %> out of <%=totalRates %>).</div>
  	
	<div class="table-responsive">
                          
				<table class='table table-striped table-condensed' style='font-size:12px;'>   				
   				<tbody>
   				<tr><td class="tableTitle">Candidates Name:</td><td class="tableResult"><%= profile.getFullName() %></td></tr>
				<tr><td class="tableTitle">Person providing reference:</td><td class="tableResult"><%= ref.getProvidedBy() %></td></tr>
				<tr><td class="tableTitle">Position:</td><td class="tableResult"><%=ref.getProvidedByPosition() %></td></tr>
				<tr><td class="tableTitle">Email:</td><td class="tableResult"><%=ref.getEmailAddress()%></td></tr>
				<tr><td class="tableTitle">Date Provided:</td><td class="tableResult"><%=ref.getDateProvided() %></td></tr>
				</tbody>
				</table>
	</div>
	</div>
	</div>						

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
													    <td>In what capacity are you able to assess the performance of this candidate?
														<br/><span class="tableAnswer"><%=ref.getQ3() %></span>
														</td>
													 </tr>
													 <tr>
													    <td>Q4.</td>
													    <td>In which subjects/classes have you observed the candidate's performance?
													    <br/><span class="tableAnswer"><%=ref.getQ4() %></span>
													    </td>
													</tr>													 
												</tbody>
										</table>
	</div>	
	</div>
</div>	


<!-- DOMAIN 1 -------------------------------------------------------------------->

<div class="panel panel-success">
  <div class="panel-heading">Domain 1: Planning and Preparation</div>
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
													    <td>1a.</td>
													    <td>Demonstrating knowledge of content and pedagogy:</td>
													    <td><span class="tableAnswer"><%=ref.getScale1()%></span></td>
													 </tr>																										
							                        <tr>
													    <td>1b.</td>
													    <td>Demonstrating knowledge of students:</td>
													    <td>
													    <span class="tableAnswer"><%=ref.getScale2()%></span>
													    </td>
													 </tr>
													 <tr>
													    <td>1c.</td>
													    <td>Selecting instructional goals:</td>
													    <td>
														 <span class="tableAnswer"><%=ref.getScale3()%></span>
														</td>
													 </tr>																										
							                        <tr>
													    <td>1d.</td>
													    <td>Demonstrating knowledge of resources:</td>
													    <td>
														<span class="tableAnswer"><%=ref.getScale4()%></span> 
														</td>
													 </tr>
													 <tr>
													    <td>1e.</td>
													    <td>Designing coherent instruction:</td>
													    <td>
														<span class="tableAnswer"><%=ref.getScale5()%></span>   	
														</td>
													 </tr>																										
							                        <tr>
													    <td>1f.</td>
													    <td>Assessing student learning:</td>
													    <td>
														<span class="tableAnswer"><%=ref.getScale6()%></span>   	
														</td>
													 </tr>
													 <tr>
													 	<td>&nbsp;</td>
													    <td colspan=2>Comments:<br/>
													    <span class="tableAnswer"><%= (!StringUtils.isEmpty(ref.getDomain1Comments())?ref.getDomain1Comments():"none") %></span> 
													    </td>
													 </tr>															 
							                       </tbody>
							             </table>
							         </div>
	</div>
</div>

<!-- DOMAIN 2 -------------------------------------------------------------------->


<div class="panel panel-success">
  <div class="panel-heading">Domain 2: The Classroom Environment</div>
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
													    <td>2a.</td>
													    <td>Creating an environment of respect and rapport:</td>
													    <td><span class="tableAnswer"><%=ref.getScale7()%></span></td>
													 </tr>																										
							                        <tr>
													    <td>2b.</td>
													    <td>Establishing a culture for learning:</td>
													    <td><span class="tableAnswer"><%=ref.getScale8()%></span></td>
													 </tr>
													 <tr>
													    <td>2c.</td>
													    <td>Managing classroom procedures:</td>
													    <td><span class="tableAnswer"><%=ref.getScale9()%></span></td>
													 </tr>																										
							                        <tr>
													    <td>2d.</td>
													    <td>Managing student behavior:</td>
													    <td><span class="tableAnswer"><%=ref.getScale10()%></span></td>
													 </tr>
													 <tr>
													    <td>2e.</td>
													    <td>Organizing physical space:</td>
													    <td><span class="tableAnswer"><%=ref.getScale11()%></span></td>
													 </tr>																										
							                        
													 <tr>
													 	<td>&nbsp;</td>
													    <td colspan=2>Comments:<br/>
													    <span class="tableAnswer">
													   <%= (!StringUtils.isEmpty(ref.getDomain2Comments())?ref.getDomain2Comments():"none") %>
													    </span>
													    </td>
													 </tr>															 
							                       </tbody>
							             </table>
							         </div>
	</div>
</div>																

												
<!-- DOMAIN 3 -------------------------------------------------------------------->


<div class="panel panel-success">
  <div class="panel-heading">Domain 3: Instruction</div>
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
													    <td>3a.</td>
													    <td>Communicating clearly and accurately:</td>
													     <td><span class="tableAnswer"><%=ref.getScale12()%></span></td>
													 </tr>																										
							                        <tr>
													    <td>3b.</td>
													    <td>Using questioning and discussion techniques:</td>
													     <td><span class="tableAnswer"><%=ref.getScale13()%></span></td>
													 </tr>
													 <tr>
													    <td>3c.</td>
													    <td>Engaging students in learning:</td>
													    <td><span class="tableAnswer"><%=ref.getScale14()%></span></td>
													 </tr>																										
							                        <tr>
													    <td>3d.</td>
													    <td>Providing feedback to students:</td>
													     <td><span class="tableAnswer"><%=ref.getScale15()%></span></td>
													 </tr>
													 <tr>
													    <td>3e.</td>
													    <td>Demonstrating flexibility and responsiveness:</td>
													     <td><span class="tableAnswer"><%=ref.getScale16()%></span></td>
													 </tr>																										
							                        
													 <tr>
													 	<td>&nbsp;</td>
													    <td colspan=2>Comments:<br/>
													     <span class="tableAnswer"><%= (!StringUtils.isEmpty(ref.getDomain3Comments())?ref.getDomain3Comments():"none") %></span>
													     </td>
													 </tr>															 
							                       </tbody>
							             </table>
							         </div>
	</div>
</div>																							
																																																																																										
																		
<!-- DOMAIN 4 -------------------------------------------------------------------->


<div class="panel panel-success">
  <div class="panel-heading">Domain 4: Professional Responsibilities</div>
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
													    <td>4a.</td>
													    <td>Reflecting on teaching:</td>
													    <td><span class="tableAnswer"><%=ref.getScale17()%></span></td>
													 </tr>																										
							                        <tr>
													    <td>4b.</td>
													    <td>Maintaining accurate records:</td>
													   <td><span class="tableAnswer"><%=ref.getScale18()%></span></td>
													 </tr>
													 <tr>
													    <td>4c.</td>
													    <td>Communicating with families:</td>
													    <td><span class="tableAnswer"><%=ref.getScale19()%></span></td>
													 </tr>																										
							                        <tr>
													    <td>4d.</td>
													    <td>Contributing to the school and district:</td>
													    <td><span class="tableAnswer"><%=ref.getScale20()%></span></td>
													 </tr>
													 <tr>
													    <td>4e.</td>
													    <td>Growing and developing professionally:</td>
													    <td><span class="tableAnswer"><%=ref.getScale21()%></span></td>
													 </tr>																										
							                         <tr>
													    <td>4f.</td>
													    <td>Showing professionalism:</td>
													    <td><span class="tableAnswer"><%=ref.getScale22()%></span></td>
													 </tr>	
													 <tr>
													 <td>&nbsp;</td>
													    <td colspan=2>Comments:<br/>
													    <span class="tableAnswer"><%= (!StringUtils.isEmpty(ref.getDomain4Comments())?ref.getDomain4Comments():"none") %></span>
													     </td>
													 </tr>															 
							                       </tbody>
							             </table>
							         </div>
	</div>
</div>																							
											
<!-- Other Information -------------------------------------------------->

<div class="panel panel-success">
  <div class="panel-heading">Other Information</div>
  	<div class="panel-body">	
  	
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
													   	<td>Q5.</td>
													    <td>If given the opportunity would you hire this candidate?		    
														 <br/><span class="tableAnswer"><%=ref.getQ7()%></span>
															</td>
													 </tr>
													 <tr>
													 <td>&nbsp;</td>
													   	<td>
													   	Additional Comments:
													   	<br/><span class="tableAnswer"><%= (!StringUtils.isEmpty(ref.getQ7Comment())?ref.getQ7Comment():"none") %></span>
													   	</td>													    
													 </tr>
												</tbody>
										</table>
										
									</div>
	</div>
</div>