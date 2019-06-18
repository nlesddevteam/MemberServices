<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.school.*,
                  com.esdnl.util.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*" 
         isThreadSafe="false"%>
         
         		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
		
		
									         <% NLESDReferenceTeacherBean ref = (NLESDReferenceTeacherBean )request.getAttribute("mref");%>
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
		
		<br/>
		The following reference check must be completed and attached to the teacher recommendation form.
		</br>
  	
  	
		      			 	       		<table class="table table-striped table-condensed" style="font-size:10px;padding-top:3px;border-top:1px solid silver;">							   
										    
											    <thead>											    
											      <tr>
												    <th>QUESTION</th>
												    <th>ANSWER</th>												    
												  </tr> 
												 </thead>
												 <tbody>										 
												
												<tr>
													<td class="tableQuestion">Did the candidate ask permission to use your name as a reference?</td>
													<td class="tableAnswer"><%=ref.getQ1() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">How long have you known this candidate in a professional capacity?</td>
													<td class="tableAnswer"><%=ref.getQ2() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">How long have they worked in your school?</td>
													<td class="tableAnswer"><%=ref.getQ3() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">What has been their most recent assignment?</td>
													<td class="tableAnswer"><%=ref.getQ4() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Did this teacher complete a professional learning plan?</td>
													<td class="tableAnswer"><%=ref.getQ5() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Was the learning plan successfully followed and completed?</td>
													<td class="tableAnswer"><%=ref.getQ6() %></td>
												</tr>
												</tbody>
												</table>
												
												
												
		<table class="table table-striped table-condensed" style="font-size:10px;">							   
										    
												<tbody>	
											   <tr>
													<th colspan="2">
														<%if(ref.getReferenceScale().equals("5") || ref.getReferenceScale().equals("3")){ %>
															On a scale of 1 to 4 (4-Proficient, 3-Competent,2-Developing Competence, 1-Needs Improvement) please rate the teacher on the following statements:
														<%}else if(ref.getReferenceScale().equals("4")){ %>
															On a scale of 1 to 4 (4-Proficient, 3-Competent, 2-Needs Improvement and 1-N/A) please rate the teacher on the following statements:
														<%}else{ %>
															On a scale of 0 to 3 (3-Proficient, 2-Competent, 1-Needs Improvement and 0-N/A) please rate the teacher on the following statements:
														<%} %>													
													</th>
												</tr>
																																		
												<tr>
													<td colspan="2"><b>DOMAIN 1: PLANNING &amp; PREPARATION</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher demonstrates knowledge of content and pedagogy:</td>
													<td class="tableAnswer"><%=ref.getScale1() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher demonstrates knowledge of students:</td>
													<td class="tableAnswer"><%=ref.getScale2() %> 
													</td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher selects instructional goals:</td>
													<td class="tableAnswer"><%=ref.getScale3() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher demonstrates knowledge of resources:</td>
													<td class="tableAnswer"><%=ref.getScale4() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher designs coherent instruction:</td>
													<td class="tableAnswer"><%=ref.getScale5() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher designs appropriate student assessment:</td>
													<td class="tableAnswer"><%=ref.getScale6() %></td>
												</tr>
												<tr>
													<td colspan='2'>
													<b>Additional Comments:</b><br/>
													<%=(!StringUtils.isEmpty(ref.getDomain1Comments())?ref.getDomain1Comments():"none") %>
													</td>
												</tr>												
												<tr><td colspan=2>&nbsp;</td></tr>												
												<tr>
													<td colspan="2" style="border-top:1px solid black;"><b>DOMAIN 2: THE CLASSROOM ENVIROMENT</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher creates an environment of respect and rapport:</td>
													<td class="tableAnswer"><%=ref.getScale7() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher establishes a culture for learning:</td>
													<td class="tableAnswer"><%=ref.getScale8() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher demonstrates appropriate classroom procedures:</td>
													<td class="tableAnswer"><%=ref.getScale9() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher managers student behavior:</td>
													<td class="tableAnswer"><%=ref.getScale10() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher organizes physical space to meet the needs of individual learners:</td>
													<td class="tableAnswer"><%=ref.getScale11() %></td>
												</tr>
												<tr>
													<td colspan='2'>
													<b>Additional Comments:</b><br/>
													<%=(!StringUtils.isEmpty(ref.getDomain2Comments())?ref.getDomain2Comments():"none") %>
													</td>
												</tr>												
												<tr><td colspan=2>&nbsp;</td></tr>												
												<tr>
													<td colspan="2" style="border-top:1px solid black;"><b>DOMAIN 3: INSTRUCTION</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">his teacher communicates clearly and accurately with students:</td>
													<td class="tableAnswer"><%=ref.getScale12() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher uses questioning and discussion techniques:</td>
													<td class="tableAnswer"><%=ref.getScale13() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher engages students in learning:</td>
													<td class="tableAnswer"><%=ref.getScale14() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher demonstrates and utilizes appropriate formative assessment strategies:</td>
													<td class="tableAnswer"><%=ref.getScale15() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher demonstrates flexibility and responsiveness:</td>
													<td class="tableAnswer"><%=ref.getScale16() %></td>
												</tr>
												<tr>
													<td colspan='2'>
													<b>Additional Comments:</b><br/>
													<%=(!StringUtils.isEmpty(ref.getDomain3Comments())?ref.getDomain3Comments():"none") %>
													</td>
												</tr>	
												<tr><td colspan=2>&nbsp;</td></tr>												
												<tr>
													<td colspan="2" style="border-top:1px solid black;"><b>DOMAIN 4: PROFESSIONAL RESPONSIBILITIES</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher reflects on teaching:</td>
													<td class="tableAnswer"><%=ref.getScale17() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher maintains accurate records:</td>
													<td class="tableAnswer"><%=ref.getScale18() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher communicates with families and/or other community stakeholders:</td>
													<td class="tableAnswer"><%=ref.getScale19() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher contributes to the school and district:td>
													<td class="tableAnswer"><%=ref.getScale20() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher grows and develops professionalism:</td>
													<td class="tableAnswer"><%=ref.getScale21() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher shows professionalism:</td>
													<td class="tableAnswer"><%=ref.getScale22() %></td>
												</tr>												
												<tr>
													<td colspan='2'><b>Additional Comments:</b><br/>
													<%=(!StringUtils.isEmpty(ref.getDomain4Comments())?ref.getDomain4Comments():"none") %>
													</td>
												</tr>
												<tr><td colspan=2>&nbsp;</td></tr>
												<tr>
													<td class="tableQuestion">Would you hire this candidate?</td>
													<td class="tableAnswer"><%=ref.getQ7() %></td>
												</tr>												
												<tr>
													<td colspan='2'><b>Additional Comments:</b><br/>
													<%=(!StringUtils.isEmpty(ref.getQ7Comment())?ref.getQ7Comment():"none") %>
													</td>
												</tr>
												</tbody>
												
											</table>
						
</span>
  