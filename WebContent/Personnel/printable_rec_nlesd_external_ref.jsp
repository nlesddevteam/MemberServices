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
         
         
									         <% NLESDReferenceExternalBean ref = (NLESDReferenceExternalBean)request.getAttribute("mref");%>
		
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
																	<td class="tableQuestion">Did the candidate ask permission to use your name as a reference?</td>
																	<td class="tableAnswer"><%=ref.getQ1() %></td>
																</tr>
																<tr>
																	<td class="tableQuestion">How long have you known this teacher in a professional capacity?</td>
																	<td class="tableAnswer"><%=ref.getQ2() %></td>
																</tr>
																<tr>
																	<td class="tableQuestion">In what capacity are you able to assess the performance of this teacher?</td>
																	<td class="tableAnswer"><%=ref.getQ3() %></td>
																</tr>
																<tr>
																	<td colspan=2>In which subjects/classes have you observed the teacher's performance?<br/><br/>
																	<%=ref.getQ4() %>
																	</td>
																</tr>
													
												</tbody>
												</table>
													
													
													
												<table class="table table-striped table-condensed" style="font-size:10px;">						   
										    
												
											  							<thead>
																		<tr>
																			<th width="60%">DOMAIN 1: Planning and Preparation</th>
																			<th width="10%">Proficient</th>
																			<th width="10%">Competent</th>
																			<th width="10%">Needs <br/>Improvement</th>
																			<th width="10%">N/A</th>
																		</tr>
																		</thead>
																		<tbody>	
																		<tr>
																			<td>1a: Demonstrating knowledge of content and pedagogy:</td>
																				<%= ref.getScale1().equals("3") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale1().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale1().equals("1")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale1().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				
																		</tr>
																		<tr>
																			<td>1b: Demonstrating knowledge of students:</td>
																			
																				<%= ref.getScale2().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale2().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale2().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale2().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																			
																		</tr>
																		<tr>
																			<td>1c: Selecting instructional goals:</td>
																			
																				<%= ref.getScale3().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale3().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale3().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale3().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																			
																		</tr>
																		<tr>
																			<td>1d: Demonstrating knowledge of resources:</td>
																				<%= ref.getScale4().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale4().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale4().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale4().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																			
																		</tr>
																		<tr>
																			<td>1e: Designing coherent instruction:</td>
																				<%= ref.getScale5().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale5().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale5().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale5().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																			
																		</tr>
																		<tr>
																			<td >1f: Assessing student learning:</td>
																			
																				<%= ref.getScale6().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale6().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale6().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale6().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																			
																		</tr>
																		<tr>
																		<td colspan='5'><b>Comments</b><br/><%= (!StringUtils.isEmpty(ref.getDomain1Comments())?ref.getDomain1Comments():"none") %></td>
																		</tr>
										
										
										
										</tbody>																																																																								
										</table>
											
						
																	<table class="table table-striped table-condensed" style="font-size:10px;">
																		<thead>
																		<tr>
																				<th width="60%">DOMAIN 2: <br />The Classroom Environment</th>
																				<th width="10%">Proficient</th>
																				<th width="10%">Competent</th>
																				<th width="10%">Needs<br/>Improvement</th>
																				<th width="10%">N/A</th>																				
																			
																		</tr>
																		</thead>
																		<tbody>
																		<tr>
																				<td>2a: Creating an environment of respect and rapport:</td>
																				<%= ref.getScale7().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale7().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale7().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale7().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				
																		</tr>
																		<tr>
																				<td>2b: Establishing a culture for learning:</td>
																				<%= ref.getScale8().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale8().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale8().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale8().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>
																		<tr>
																				<td>2c: Managing classroom procedures:</td>
																				<%= ref.getScale9().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale9().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale9().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale9().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>																		
																		<tr>
																				<td>2d: Managing student behaviour:</td>
																				<%= ref.getScale10().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale10().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale10().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale10().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>																			
																		<tr>
																				<td>2e: Organizing physical space:</td>
																				<%= ref.getScale11().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale11().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale11().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale11().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>
																		<tr>
																		<td colspan='5'><b>Comments</b><br/><%= (!StringUtils.isEmpty(ref.getDomain2Comments())?ref.getDomain2Comments():"none") %></td>
																		</tr>
											</tbody>							
											</table>
																	
																	
																	
																	
											<table class="table table-striped table-condensed" style="font-size:10px;">
																		<thead>
																		<tr>
																				<th width="60%">DOMAIN 3: <br />Instruction</th>
																				<th width="10%">Proficient</th>
																				<th width="10%">Competent</th>
																				<th width="10%">Needs <br />Improvement</th>
																				<th width="10%">N/A</th>																				
																		</tr>
																		</thead>
																		<tbody>
																		<tr>
																				<td>3a: Communicating clearly and accurately:</td>
																				<%= ref.getScale12().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale12().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale12().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale12().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				
																		</tr>
																		<tr>
																				<td>3b: Using questioning and discussion techniques:</td>
																				<%= ref.getScale13().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale13().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale13().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale13().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>
																		<tr>
																				<td>3c: Engaging students in learning:</td>
																				<%= ref.getScale14().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale14().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale14().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale14().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>																		
																		<tr>
																				<td>3d: Providing feedback to students:</td>
																				<%= ref.getScale15().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale15().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale15().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale15().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>																			
																		<tr>
																				<td>3e: Demonstrating flexibility and responsiveness:</td>
																				<%= ref.getScale16().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale16().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale16().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale16().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>
																		<tr><td colspan='5'>
																		<b>Comments</b><br/>
																		<%= (!StringUtils.isEmpty(ref.getDomain3Comments())?ref.getDomain3Comments():"none") %></td></tr>
																	</tbody>
																	</table>
																	
																	
																	
																	
													<table class="table table-striped table-condensed" style="font-size:10px;">
																		<thead>
																		<tr>
																				<th width="60%">DOMAIN 4: <br />Professional Responsibilities</th>
																				<th width="10%">Proficient</th>
																				<th width="10%">Competent</th>
																				<th width="10%">Needs <br/>Improvement</th>
																				<th width="10%">N/A</th>																			
																			
																		</tr>
																		</thead>
																		<tbody>
																		<tr>
																				<td>4a: Reflecting on teaching:</td>
																				<%= ref.getScale17().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale17().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale17().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale17().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																				</tr>
																		<tr>
																				<td>4b: Maintaining accurate records:</td>
																				<%= ref.getScale18().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale18().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale18().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale18().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>
																		<tr>
																				<td>4c: Communicating with families:</td>
																				<%= ref.getScale19().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale19().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale19().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale19().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>																		
																		<tr>
																				<td>4d: Contributing to the school and district:</td>
																				<%= ref.getScale20().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale20().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale20().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale20().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>																			
																		<tr>
																				<td>4e: Growing and developing professionally:</td>
																				<%= ref.getScale21().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale21().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale21().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale21().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>
																		<tr>
																				<td>4f: Showing professionalism:</td>
																				<%= ref.getScale22().equals("3")? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>  
																				<%= ref.getScale22().equals("2") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale22().equals("1") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %> 
																				<%= ref.getScale22().equals("0") ? "<td>&#10004;</td>" : "<td>&nbsp;</td>" %>
																		</tr>
																	    <tr><td colspan='5'>
																	    <b>Comments</b><br/>
																	    <%= (!StringUtils.isEmpty(ref.getDomain4Comments())?ref.getDomain4Comments():"none") %></td>
																		</tr>	
																		<tr><td colspan=5>&nbsp;</td></tr>
												<tr>
													<td>Would you hire this candidate?</td>
													<td colspan=4><%=ref.getQ7() %></td>
												</tr>												
												<tr>
													<td colspan='5'><b>Additional Comments:</b><br/>
													<%= (!StringUtils.isEmpty(ref.getQ7Comment())?ref.getQ7Comment():"none") %>
													</td>
												</tr>
												</tbody>																							
											</table>
																
																
																
																



</span>
