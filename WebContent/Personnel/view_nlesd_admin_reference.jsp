<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*" 
         isThreadSafe="false"%>

		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
	NLESDReferenceAdminBean ref = (NLESDReferenceAdminBean) request.getAttribute("REFERENCE_BEAN");
  	ApplicantProfileBean profile = (ApplicantProfileBean) request.getAttribute("PROFILE");
	String val1="0";
	String val2="1";
	String val3="2";
	String val4="3";
	String val5="4";
	String refscale="3";
	if(!(ref == null)){
		refscale=ref.getReferenceScale();
	}
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
		.tableQuestionNum {font-weight:bold;width:5%;}
		.tableQuestion {width:95%;}
		.tableAnswer  {font-style: italic;color:Green;}
		.ratingQuestionNum {font-weight:bold;width:5%;}
		.ratingQuestion {width:75%;}
		.ratingAnswer {width:20%;}
		input[type="radio"] {margin-top: -1px;margin-left:6px;margin-right:2px;vertical-align: middle;}
	</style>	
	</head>
	<body><br/>
<%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){ %>
			                            	<div class="alert alert-danger" style="text-align:center;"><%=(String)request.getAttribute("msg")%></div>
	<%} %>
    <div class="panel panel-success">
  	<div class="panel-heading">Administrator Candidate Reference Check for <b><%= profile.getFullNameReverse() %>.</b></div>
  	<div class="panel-body">
  	
  	<%
  	int totalRates=0;
  	int totalScore=0;  		
  		
	totalRates = Integer.parseInt(val4)*ref.getPossibleTotal();
	 	
  	totalScore = ref.getTotalScore().intValue();
  	double resultScore = 0;
	if(totalRates > 0){
	
  		resultScore = 100 * totalScore / totalRates;
	}  	
	%>	
  	
  	  	
  	
  	<div class="alert alert-info">The total score for <%= profile.getFullNameReverse() %> on this reference from <%= ref.getProvidedBy() %> is  <%=resultScore %>%.
  	(<%=ref.getTotalScore() %> out of a possible <%=totalRates%>).</div>
  	
  	
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

<div class="panel panel-success">
  <div class="panel-heading">Reference Questions</div>
  	<div class="panel-body">	
  	<b>The following reference check must have been completed and attached to the teacher recommendation form.</b><p>
  	
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
													     <br/><span class="tableAnswer"><%=ref.getQ3() %></span></td>													    
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
														 <br/><span class="tableAnswer"><%=ref.getQ5() %></span></td>													    
													 </tr>													 
										    	</tbody>
									    </table>
									</div>	
	</div>
</div>	

<div class="panel panel-success">
  <div class="panel-heading">Leadership Ratings</div>
  	<div class="panel-body">
  	
									<div class="table-responsive">
									<%if(refscale.equals("4") || refscale.equals("3")){%>
										The following has been rated from a scale of <%=val1%> to <%=val4%>.
										(<%=val4%>-Proficient, <%=val3%>-Competent, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
									<%}else{%>
										The following has been rated from a scale of <%=val1%> to <%=val5%>.
										(<%=val5%>-Proficient, <%=val4%>-Competent, <%=val3%>-Developing Competence, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
									<%}%>
									
									 
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
													    <td>Communicates a clear vision focused on student achievement:</td>
													    <td><span class="tableAnswer"><%=ref.getScale1()%></span></td>
													 </tr>
													 <tr>
													    <td>S2.</td>
													    <td>Establishes a culture for learning and models organizational value:</td>
													    <td><span class="tableAnswer"><%=ref.getScale2()%></span></td>
													 </tr>
													 <tr>
													    <td>S3.</td>
													    <td>Demonstrates knowledge of curriculum outcomes and provides support and feedback to teachers:</td>
													    <td><span class="tableAnswer"><%=ref.getScale3()%></span></td>
													 </tr>
													 <tr>
													    <td>S4.</td>
													    <td>Observes instruction and provides feedback to teachers in order to improve learning and teaching in the school while encouraging and supporting teacher leadership:</td>
													    <td><span class="tableAnswer"><%=ref.getScale4()%></span></td>
													 </tr>
													 <tr>
													    <td>S5.</td>
													    <td>Fosters a culture of respect:</td>
													    <td><span class="tableAnswer"><%=ref.getScale5()%></span></td>
													 </tr>
													 <tr>
													    <td>S6.</td>
													    <td>Uses the School Development Plan as the strategic guide for decision making and staff development:</td>
													    <td><span class="tableAnswer"><%=ref.getScale6()%></span></td>
													 </tr>
													 <tr>
													 	<td>&nbsp;</td>
													    <td colspan=2>Additional Comments <br/>	
													    <span class="tableAnswer"><%= (!StringUtils.isEmpty(ref.getDomain1Comments())?ref.getDomain1Comments():"none") %></span>											    
													    </td>
													 </tr>											 
										    	</tbody>
									    </table>
									</div>	
	</div>
</div>	
																				
												
<div class="panel panel-success">
  <div class="panel-heading">Management Ratings</div>
  	<div class="panel-body">	
  			
									<div class="table-responsive">
									<%if(refscale.equals("4") || refscale.equals("3")){%>
										The following has been rated from a scale of <%=val1%> to <%=val4%>.
										(<%=val4%>-Proficient, <%=val3%>-Competent, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
									<%}else{%>
										The following has been rated from a scale of <%=val1%> to <%=val5%>.
										(<%=val5%>-Proficient, <%=val4%>-Competent, <%=val3%>-Developing Competence, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
									<%}%>
									
							 
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
													    <td>S7.</td>
													    <td>Communicates and maintains appropriate behavioral standards:</td>
													    <td><span class="tableAnswer"><%=ref.getScale7() %></span></td>
													 </tr>
													 <tr>
													    <td>S8.</td>
													    <td>Ensures that staff have necessary resources and keeps them organized and on task:</td>
													    <td><span class="tableAnswer"><%=ref.getScale8() %></span></td>
													 </tr>
													 <tr>
													    <td>S9.</td>
													    <td>Manages school procedures and operations based on District policies in consultation with the District, if necessary:</td>
													    <td><span class="tableAnswer"><%=ref.getScale9() %></span></td>
													 </tr>
													 <tr>
													    <td>S10.</td>
													    <td>Models time and self-management:</td>
													    <td><span class="tableAnswer"><%=ref.getScale10() %></span></td>
													 </tr>
													 <tr>
													    <td>S11.</td>
													    <td>Organizes and develops the physical plant to ensure student learning:</td>
													    <td><span class="tableAnswer"><%=ref.getScale11() %></span></td>
													 </tr>
													<tr>
														<td>&nbsp;</td>
													    <td colspan=2>Additional Comments<br/>
													    <span class="tableAnswer"><%= (!StringUtils.isEmpty(ref.getDomain2Comments())?ref.getDomain2Comments():"none") %></span>
													    </td>
													 </tr>		
													 										 
										    	</tbody>
									    </table>
									</div>	
	</div>
</div>														
							
												
<div class="panel panel-success">
  <div class="panel-heading">Communication and Interpersonal Relations Ratings</div>
  	<div class="panel-body">	
  	  	
									<div class="table-responsive">
									<%if(refscale.equals("4") || refscale.equals("3")){%>
										The following has been rated from a scale of <%=val1%> to <%=val4%>.
										(<%=val4%>-Proficient, <%=val3%>-Competent, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
									<%}else{%>
										The following has been rated from a scale of <%=val1%> to <%=val5%>.
										(<%=val5%>-Proficient, <%=val4%>-Competent, <%=val3%>-Developing Competence, <%=val2%>-Needs Improvement and <%=val1%>-N/A):
									<%}%>
									
									 
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
													    <td>S12.</td>
													    <td>Communicates and responds clearly and effectively (both oral and written):</td>
													     <td><span class="tableAnswer"><%=ref.getScale12() %></span></td>
													 </tr>
													 <tr>
													    <td>S13.</td>
													    <td>Engages all stakeholders in the school community with a focus on student learning:</td>
													     <td><span class="tableAnswer"><%=ref.getScale13() %></span></td>
													 </tr>
													 <tr>
													    <td>S14.</td>
													    <td>Displays empathy and demonstrated ability to listen and solve problems proactively:</td>
													    <td><span class="tableAnswer"><%=ref.getScale14() %></span></td>
													 </tr>
													 <tr>
													    <td>S15.</td>
													    <td>Manages conflict effectively and respectfully:</td>
													     <td><span class="tableAnswer"><%=ref.getScale15() %></span></td>
													 </tr>
													 <tr>
													    <td>S16.</td>
													    <td>Demonstrates the ability to build and develop teams and facilitate group collaboration:</td>
													     <td><span class="tableAnswer"><%=ref.getScale16() %></span></td>
													 </tr>
													 <tr>
													 	<td>&nbsp;</td>
													    <td colspan=2>Additional Comments <br/>
													    <span class="tableAnswer"><%= (!StringUtils.isEmpty(ref.getDomain3Comments())?ref.getDomain3Comments():"none") %></span>
													    
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
													    <td>If given the opportunity would you hire this candidate?<br/>
													    <span class="tableAnswer"><%=ref.getQ6()%></span>
													    </td>
													 </tr>													 
													 <tr>
													 	<td>&nbsp;</td>
													    <td>Additional Comments <br/>
													    <span class="tableAnswer"><%= (!StringUtils.isEmpty(ref.getQ6Comment())?ref.getQ6Comment():"none") %></span>
													    </td>
													 </tr>	
												</tbody>
										</table>
									</div>
	</div>
</div>


											
</body>
</html>
