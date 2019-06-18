<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*,com.awsd.personnel.*" 
         isThreadSafe="false"%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>



<%NLESDReferenceSSManageBean ref = (NLESDReferenceSSManageBean) request.getAttribute("mref");%>


<span style="font-size:11px;">	

			                           
			                           
			                           
			                    <table class="table table-striped table-condensed" style="font-size:10px;">							   
										    <tbody>
										    <tr>
										    <td class="tableTitle">REFERENCE BY:</td>
										    <td class="tableResult"><%= ref.getProvidedBy() %></td>
										    </tr>
										    <tr>
										     <td class="tableTitle">POSITION:</td>
										     <td class="tableResult"><%=ref.getProvidedByPosition() %></td>
										    </tr>
											</tbody>
								</table>
															

								<table class="table table-striped table-condensed" style="font-size:10px;padding-top:3px;border-top:1px solid silver;">		
								 				<thead>											    
											      <tr>
												    <th>QUESTION</th>
												    <th>ANSWER</th>												    
												  </tr> 
												 </thead>
												 <tbody>	
																<tr>
																	<td class="tableQuestion">Did the candidate ask permission to use your name as a reference</td>
																	<td class="tableAnswer"><%=ref.getQ1() %></td>
																</tr>
																<tr>
																	<td class="tableQuestion">How long have you known this applicant in a professional capacity</td>
																	<td class="tableAnswer"><%=ref.getQ2() %></td>
																</tr>
																<tr>
																	<td class="tableQuestion">In what capacity are you able to assess the performance of this applicant?</td>
																	<td class="tableAnswer"><%=ref.getQ3() %></td>
																</tr>
												</tbody>
								</table>
																
																
																
												<table class="table table-striped table-condensed" style="font-size:10px;">							   
										   								 <thead>
																		<tr>
																			<th width="40%">Job Competencies</th>
																			<th width="10%">Excellent</th>
																			<th width="10%">Good</th>
																			<th width="10%">Average</th>
																			<th width="10%">Needs <br/>Improvement</th>
																			<th width="10%">Unacceptable</th>
																			<th width="10%">N/A</th>																			
																		</tr>
																		</thead>
																		<tbody>
																		<tr>
																			<td>1. Ability to understand and apply the knowledge required for this type of position</td>
																				<%= ref.getScale1().equals("5") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale1().equals("4") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale1().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale1().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale1().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale1().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>
																		<tr>
																			<td>2. Ability to use appropriate leadership style</td>
																			
																				<%= ref.getScale2().equals("5") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale2().equals("4") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale2().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale2().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale2().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale2().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>
																		<tr>
																			<td>3. Ability to communicate with supervisors, co-workers, contractors, and other external stakeholders</td>
																			
																				<%= ref.getScale3().equals("5") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale3().equals("4") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale3().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale3().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale3().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale3().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																			
																		</tr>
																		<tr>
																			<td>4. Ability to make rational decisions in the best interests of the organization</td>
																				<%= ref.getScale4().equals("5") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale4().equals("4") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale4().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale4().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale4().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale4().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>
																		<tr>
																			<td>5. Displays positive attitude or enthusiasm towards job</td>
																				<%= ref.getScale5().equals("5") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale5().equals("4") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale5().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale5().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale5().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale5().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																			
																		</tr>
																		<tr>
																			<td>6. Ability to provide customer service and client satisfaction</td>
																			
																				<%= ref.getScale6().equals("5") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale6().equals("4") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale6().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale6().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale6().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale6().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																			
																		</tr>
																		<tr>
																			<td>7. Ability to integrate a variety of functions using organizational & time management skills</td>
																				<%= ref.getScale7().equals("5") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale7().equals("4") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale7().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale7().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale7().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale7().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>
																		<tr>
																			<td>8. Ability to be a team player and get along with people(peers, management and staff)</td>
																			
																				<%= ref.getScale8().equals("5") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale8().equals("4") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale8().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale8().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale8().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale8().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>
																		<tr>
																			<td>9. Flexibility/adjustment to new situations<</td>
																			
																				<%= ref.getScale9().equals("5") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale9().equals("4") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale9().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale9().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale9().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale9().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																			
																		</tr>
																		<tr>
																			<td>10. Ability to handle conflict, reduce tensions and arrive at satisfactory conclusion</td>
																				<%= ref.getScale10().equals("5") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale10().equals("4") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale10().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale10().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale10().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale10().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>
																		<tr>
																			<td>11. Dependability (punctality and attendance)</td>
																				<%= ref.getScale11().equals("5") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale11().equals("4") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale11().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale11().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale11().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale11().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																			
																		</tr>
																		<tr>
																			<td>12. Ability to work independently with minimum supervision</td>
																			
																				<%= ref.getScale12().equals("5") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale12().equals("4") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale12().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale12().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale12().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale12().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																			
																		</tr>
																		<tr>
																			<td>13. Level of professionalism</td>
																				<%= ref.getScale13().equals("5") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale13().equals("4") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale13().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale13().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale13().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale13().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																			
																		</tr>
																		<tr>
																			<td>14. Displays confidence and is trustworthy</td>
																			
																				<%= ref.getScale14().equals("5") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale14().equals("4") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale14().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale14().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale14().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale14().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																			
																		</tr>
																		<tr>
																			<td>15. Individual's performance compared to other with similar job duties</td>
																			
																				<%= ref.getScale15().equals("5") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale15().equals("4") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale15().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale15().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale15().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				<%= ref.getScale15().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																			
																		</tr>
																		</tbody>
																																																																																										
																		</table>
																	
																	
																	
																	
																	
																	
										<table class="table table-striped table-condensed" style="font-size:10px;">							   
										    
												<tbody>	
											   	<tr>							
														<td>Please provide us with a summary of your professional relationship with the candidate and give a brief 
																	description of the duties while in your employ</td>
																</tr>
																<tr>
																	<td><%=ref.getQ4()== null ? "" :ref.getQ4() %></td>
																</tr>
																<tr><td>&nbsp;</td></tr>
																<tr>
																	<td>Please comment on the applicant's strengths.</td>
																</tr>
																<tr>
																	<td><%=ref.getQ5()== null ? "" :ref.getQ5() %></td>
																</tr>
																<tr><td>&nbsp;</td></tr>
																<tr>
																	<td>Please comment on the applicant's weaknesses.</td>
																</tr>
																<tr>
																	<td><%=ref.getQ6()== null ? "" :ref.getQ6() %></td>
																</tr>
																<tr><td>&nbsp;</td></tr>
																<tr>
																	<td>Did the applicant have any attendance problems while in your employ? <%=ref.getQ7() ==null ? "N/A" :ref.getQ7() %></td>
																</tr>
																<tr>
																	<td><%=ref.getQ7Comment()== null ? "" :ref.getQ7Comment() %></td>
																</tr>
																<tr><td>&nbsp;</td></tr>
																<tr>
																	<td>Did the applicant have any disciplinary issues while in your employ? <%=ref.getQ8() ==null ? "N/A" :ref.getQ8() %></td>
																</tr>
																<tr>
																	<td><%=ref.getQ8Comment()== null ? "" :ref.getQ8Comment() %></td>
																</tr>
																<tr><td>&nbsp;</td></tr>
																<tr>
																	<td>What was the applicant's reason for leaving your employ</td>
																</tr>
																<tr>
																	<td><%=ref.getQ9()== null ? "" :ref.getQ9() %></td>
																</tr>
																<tr><td>&nbsp;</td></tr>
																<tr>
																	<td>Would you hire/rehire the applicant - <%=ref.getQ10() ==null ? "N/A" :ref.getQ10() %></td>
																</tr>
																<tr><td>&nbsp;</td></tr>
																<tr>
																	<td>Overall, how would you rate the applicant's performance while employed with you - <%=ref.getQ11()== null ? "" :ref.getQ11() %></td>
																</tr>
																<tr><td>&nbsp;</td></tr>
																<tr>
																	<td>Thank you for taking the time to provide this reference. Is there any other information you'd like to provide<br/>
																	that might be helpful in making a hiring decision</td>
																</tr>
																<tr>
																	<td><%=ref.getQ12()== null ? "" :ref.getQ12() %></td>
																</tr>															
																</table>
															
</span>										