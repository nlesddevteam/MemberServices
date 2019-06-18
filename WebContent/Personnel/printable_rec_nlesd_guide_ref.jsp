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
         
									         <% NLESDReferenceGuideBean ref = (NLESDReferenceGuideBean)request.getAttribute("mref");%>
					
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
											<br>The following reference check must be completed and attached to the Guidance recommendation form.<br>
									
											<table class="table table-striped table-condensed" style="font-size:10px;">							   
										    
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
													<td class="tableQuestion">How long has he/she worked in your school?</td>
													<td class="tableAnswer"><%=ref.getQ3() %></td>
												</tr>
												<tr valign="top">
													<td class="tableQuestion">What has been his/her assignment this year?</td>
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
										    <thead>
											   <tr>
													<th colspan="2">
															<%if(ref.getReferenceScale() == "4"){ %>
															On a scale of 1 to 4 (4-Proficient, 3-Competent, 2-Needs Improvement and 1-N/A) please rate the teacher on the following statements:
														<%}else{ %>
															On a scale of 0 to 3 (3-Proficient, 2-Competent, 1-Needs Improvement and 0-N/A) please rate the teacher on the following statements:
														<%} %>
													</td>
												</tr>
												</thead>
												<tbody>																							
												<tr>
													<td colspan="2"><b>STANDARD 1: COMPREHENSIVE GUIDANCE PROGRAM</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">The guidance counsellor collaborates and develops an annual comprehensive school guidance program which outlines the implementation of interventions that promote the holistic development of the student:</td>
													<td class="tableAnswer"><%=ref.getScale1() %></td>
												</tr>
												<tr>
													<td colspan="2"><b>STANDARD 2: EDUCATION SYSTEM</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">The guidance counsellor understands the overall education system and engages in the planning and managing of tasks to support the learning and development of students:</td>
													<td class="tableAnswer"><%=ref.getScale2() %></td>
												</tr>
												<tr>
													<td colspan="2"><b>STANDARD 3: STUDENT DEVELOPMENT</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">The guidance counsellor understands the diversity of human growth, development , behavior and learning and promotes the holistic development of the student:</td>
													<td class="tableAnswer"><%=ref.getScale3() %></td>
												</tr>
												<tr>
													<td colspan="2"><b>STANDARD 4: DIVERSITY</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">The guidance counsellor understands the dimensions of human diversity and the possible influence they many have on child/adolescent development:</td>
													<td class="tableAnswer"><%=ref.getScale4() %></td>
												</tr>																								
												<tr>
													<td colspan="2"><b>STANDARD 5: COMPREHENSIVE ASSESSMENT</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">The guidance counsellor understands the assessment process and its implications for student learning:</td>
													<td class="tableAnswer"><%=ref.getScale5() %></td>
												</tr>												
												<tr>
													<td colspan="2"><b>STANDARD 6: COUNSELLING</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">The guidance counsellor possesses knowledge and skills necessary to establish and facilitate individual and group counselling:</td>
													<td class="tableAnswer"><%=ref.getScale6() %></td>
												</tr>												
												<tr>
													<td colspan="2"><b>STANDARD 7: CAREER DEVELOPMENT</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">The guidance counsellor understands that career development is a lifelong process. He/she develops programs and interventions to promote the career development of all students:</td>
													<td class="tableAnswer"><%=ref.getScale7() %></td>
												</tr>												
												<tr>
													<td colspan="2"><b>STANDARD 8: CRISIS INTERVENTION</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">The guidance counsellor participates in the development and implementation of a response plan for possible crisis situations:</td>
													<td class="tableAnswer"><%=ref.getScale8() %></td>
												</tr>												
												<tr>
													<td colspan="2"><b>STANDARD 9: ETHICAL RESPONSIBILITIES</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">The guidance counsellor understands the ethical requirements in providing a comprehensive school guidance program:</td>
													<td class="tableAnswer"><%=ref.getScale9() %></td>
												</tr>												
												<tr><td colspan=2>&nbsp;</td></tr>
												<tr>
													<td class="tableQuestion">Would you hire this candidate?</td>
													<td class="tableAnswer"><%=ref.getQ6() %></td>
												</tr>												
												<tr>
													<td colspan='2'><b>Additional Comments:</b><br/>
													<%=(!StringUtils.isEmpty(ref.getQ6Comment())?ref.getQ6Comment():"none") %>
													</td>
												</tr>
												
												</tbody>
												</table>
  </span>	