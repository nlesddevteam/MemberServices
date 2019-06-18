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
         
         
	<% NLESDReferenceAdminBean ref = (NLESDReferenceAdminBean)request.getAttribute("mref");%>
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
													<td class="tableQuestion">How long has he/she worked in your school?</td>
													<td class="tableAnswer"><%=ref.getQ3() %></td>
												</tr>
												<tr valign="top">
													<td class="tableQuestion">What has been his/her assignment this year?</td>
													<td class="tableAnswer"><%=ref.getQ4() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Was the growth plan successfully followed and completed?</td>
													<td class="tableAnswer"><%=ref.getQ6() %></td>
												</tr>
												</tbody>
											</table>
												
												
												
								<table class="table table-striped table-condensed" style="font-size:10px;">							   
										    <tbody>
											   <tr>
													<td colspan="2">
														<%if(ref.getReferenceScale().equals("5")){ %>
															On a scale of 0 to 4 (4-Proficient, 3-Competent,2-Developing Competence, 1-Needs Improvement and 0-N/A) please rate the teacher on the following statements:
														<%}else if(ref.getReferenceScale().equals("4")){ %>
															On a scale of 1 to 4 (4-Proficient, 3-Competent, 2-Needs Improvement and 1-N/A) please rate the teacher on the following statements:
														<%}else{ %>
															On a scale of 0 to 3 (3-Proficient, 2-Competent, 1-Needs Improvement and 0-N/A) please rate the teacher on the following statements:
														<%} %>
														</b>
													</td>
												</tr>
												<tr>
													<td colspan="2"><b>LEADERSHIP</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">Communicates a clear vision focused on student achievement:</td>
													<td class="tableAnswer"><%=ref.getScale1() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Establishes a culture for learning and models organizational value:</td>
													<td class="tableAnswer"><%=ref.getScale2() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Demonstrates knowledge of curriculum outcomes and provides support and feedback to teachers:</td>
													<td class="tableAnswer"><%=ref.getScale3() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Observes instruction and provides feedback to teachers in order to improve learning and teaching in the school while encouraging and supporting teacher leadership:</td>
													<td class="tableAnswer"><%=ref.getScale4() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Fosters a culture of respect:</td>
													<td class="tableAnswer"><%=ref.getScale5() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Uses the School Development Plan as the strategic guide for decision making and staff development:</td>
													<td class="tableAnswer"><%=ref.getScale6() %></td>
												</tr>
												<tr>
													<td colspan='2'><b>Additional Comments</b><br/>
													<%=(!StringUtils.isEmpty(ref.getDomain1Comments())?ref.getDomain1Comments():"none") %>
													</td>
												</tr>
												<tr><td colspan=2>&nbsp;</td></tr>
												<tr>
													<td colspan="2"><b>MANAGEMENT</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">Communicates and maintains appropriate behavioral standards:</td>
													<td class="tableAnswer"><%=ref.getScale7() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Ensures that staff have necessary resources and keeps them organized and on task:</td>
													<td class="tableAnswer"><%=ref.getScale8() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Manages school procedures and operations based on District policies in consultation with the District, if necessary:</td>
													<td class="tableAnswer"><%=ref.getScale9() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Models time and self-management:</td>
													<td class="tableAnswer"><%=ref.getScale10() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Organizes and develops the physical plant to ensure student learning:</td>
													<td class="tableAnswer"><%=ref.getScale11() %></td>
												</tr>
												<tr>
													<td colspan='2'><b>Additional Comments</b><br/>
													<%=(!StringUtils.isEmpty(ref.getDomain2Comments())?ref.getDomain2Comments():"none") %>
													</td>
												</tr>
												<tr><td colspan=2>&nbsp;</td></tr>												
												<tr>
													<td colspan="2"><b>COMMUNICATION AND INTERPERSONAL RELATIONS</b></td>
												</tr>
												<tr>
													<td class="tableQuestion">Communicates and responds clearly and effectively (both oral and written):</td>
													<td class="tableAnswer"><%=ref.getScale12() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Engages all stakeholders in the school community with a focus on student learning:</td>
													<td class="tableAnswer"><%=ref.getScale13() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Displays empathy and demonstrated ability to listen and solve problems proactively:</td>
													<td class="tableAnswer"><%=ref.getScale14() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Manages conflict effectively and respectfully:</td>
													<td class="tableAnswer"><%=ref.getScale15() %></td>
												</tr>
												<tr>
													<td class="tableQuestion">Demonstrates the ability to build and develop teams and facilitate group collaboration:</td>
													<td class="tableAnswer"><%=ref.getScale16() %></td>
												</tr>
												<tr>
													<td colspan='2'><b>Additional Comments</b><br/>
													<%=(!StringUtils.isEmpty(ref.getDomain3Comments())?ref.getDomain3Comments():"none") %>
													</td>
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

  