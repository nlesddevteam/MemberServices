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
         
									         <% ReferenceBean ref = (ReferenceBean)request.getAttribute("mref");%>
											
											
	<span style="font-size:11px;">								
										<table class="table table-striped table-condensed" style="font-size:10px;">							   
										    <tbody>
										    <tr>
										    <td class="tableTitle">REFERENCE BY:</td>
										    <td class="tableResult"><%= ref.getReferenceProviderName() %></td>
										    </tr>
										    <tr>
										     <td class="tableTitle">POSITION:</td>
										     <td class="tableResult"><%=ref.getReferenceProviderPosition() %></td>
										    </tr>
											</tbody>
										</table>
											
										<br/>The following reference check must be completed and attached to the teacher recommendation form.<br/>	
											
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
													<td class="tableQuestion">How long have you known this teacher?</td>
													<td class="tableAnswer"><%=ref.getQ2() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">How long has he/she worked in your school?</td>
													<td class="tableAnswer"><%=ref.getQ3() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">What has been his/her teaching assignment this year?</td>
													<td class="tableAnswer"><%=ref.getQ4() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Did this teacher complete a professional growth plan?</td>
													<td class="tableAnswer"><%=ref.getQ5() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Was the growth plan successfully followed?</td>
													<td class="tableAnswer"><%=ref.getQ6() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Has the teacher demonstrated leadership on your staff?</td>
													<td class="tableAnswer"><%=ref.getQ7() %></td>
												</tr>
												<%if(ref.getQ7().equalsIgnoreCase("YES")){ %>
													<tr>
														<td colspan=2>If yes, please give examples: <br/><br/><%=(!StringUtils.isEmpty(ref.getQ7Comment())?ref.getQ7Comment():"none") %></td>
													</tr>
												<%} %>
												
												</tbody>
												</table>
												
												
												
									<br/>		
												
												
									<table class="table table-striped table-condensed" style="font-size:10px;">							   
										    
												<tbody>	
											   <tr>
													<th colspan="2">
															On a scale of 1 to 5 with 1 being I highly agree and 5 being I highly disagree please rate the teacher on the following statements:
													</td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher demonstrates a positive attitude towards students:</td>
													<td class="tableAnswer"><%=ref.getScale1() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher works collaboratively with other teachers:</td>
													<td class="tableAnswer"><%=ref.getScale2() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher uses assessment to guide instruction:</td>
													<td class="tableAnswer"><%=ref.getScale3() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher takes ownership for student learning:</td>
													<td class="tableAnswer"><%=ref.getScale4() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher uses a variety of instructional strategies to address the needs of diverse learners:</td>
													<td class="tableAnswer"><%=ref.getScale5() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher is a positive role model for students and staff:</td>
													<td class="tableAnswer"><%=ref.getScale6() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher demonstrates a strong understanding of his/her curriculum responsibilities:</td>
													<td class="tableAnswer"><%=ref.getScale7() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher practices good classroom management techniques:</td>
													<td class="tableAnswer"><%=ref.getScale8() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher makes regular home contact with parents:</td>
													<td class="tableAnswer"><%=ref.getScale9() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">This teacher understands and adheres to the principals of the ISSP process:</td>
													<td class="tableAnswer"><%=ref.getScale10() %></td>
												</tr>
												<tr><td colspan=2>&nbsp;</td></tr>
												<tr>
													<td colspan="2">Please identify the ways in which this teacher has been involved in building a positive atmosphere in your school:<br><br>
														<%=ref.getQ8() %>
													</td>
												</tr>
												<tr><td colspan=2>&nbsp;</td></tr>
												<tr>
													<td colspan="2">If given the opportunity would you hire this teacher? &nbsp;&nbsp;<%=ref.getQ9() %>
														<p>
														<b>Additional Comments:</b><br>
														<%=(!StringUtils.isEmpty(ref.getQ9Comment())?ref.getQ9Comment():"none") %>
													</td>
												</tr>
												<tr>
													<td colspan="2">Overall, how would you rate this teacher:<br>
														<%=ref.getQ10() %>
													</td>
												</tr>
												</tbody>
											</table>


</span>

  