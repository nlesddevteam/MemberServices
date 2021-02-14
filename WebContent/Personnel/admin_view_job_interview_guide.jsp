<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.awsd.security.*" 
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-OTHER-MANAGER-VIEW" />

<%
	JobOpportunityBean job = (JobOpportunityBean) request.getAttribute("job");
	InterviewGuideBean guide = (InterviewGuideBean) request.getAttribute("guide");
	Collection<InterviewGuideBean> guides = (Collection<InterviewGuideBean>) request.getAttribute("guides");
	User usr = (User)session.getAttribute("usr");
%>


<html>

	<head>
		<title>MyHRP Applicant Profiling System</title>
		<script>
			$("#loadingSpinner").css("display","none");
		</script>	
		<script type='text/javascript'>
			$(function(){		
				$('button').button();				
				$('#btn-unset').click(function(){
					if(confirm('Are you should you want to unset this interview guide?')){
						document.location='unsetJobInterviewGuide.html?comp_num=<%= job.getCompetitionNumber() %>';
					}
				})				
				$('#btn-set').click(function(){
					if($('#lst-guides').val() != ''){
						document.location='setJobInterviewGuide.html?comp_num=<%= job.getCompetitionNumber() %>&guideId=' + $('#lst-guides').val();
					}
				});					
			});
		</script>
	</head>
	
	<body>  
    <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>View/Set Competition <%= job.getCompetitionNumber() %> Interview Guide</b></div>
      			 	<div class="panel-body">
      			 	<div class="container-fluid">  
                    <form id='frm-interview-guide' action="" method="post">
                    <div class="row">
						<div class="input-group">
 								<span class="input-group-addon">Interview Guide*:</span>
 								<% if(usr.checkPermission("PERSONNEL-ADMIN-VIEW")) { %>
 									<select id='lst-guides' name='lst-guides' class="form-control">
                                    	<option value=''>--- Select Interview Guide ---</option>
                                    		<% for(InterviewGuideBean g : guides){ %>
                                    			<option value='<%= g.getGuideId() %>' <%= guide != null && g.getGuideId() == guide.getGuideId() ? " SELECTED" : "" %>><%= g.getTitle() %></option>
                                    		<% } %>
                                	</select>
 								<%}else{ %>
 									<span class="panel-heading"><b>&nbsp;&nbsp;&nbsp;
 									<%if(guide == null){%>
 										Guide Not Set
 									<%}else{%>
 										<%=guide.getTitle()%>
 									<%} %>
 									</b></span>
 								<%} %>	

                        </div>		  								
		  			</div>
	                             
	                <div class="row">					                   
	                <c:if test="${guide ne null}">
		            Answers are ranked from ${guide.ratingScaleBottom} to ${guide.ratingScaleTop}, with ${guide.ratingScaleTop} being outstanding.
		                  		<div class="table-responsive">          
						  			<table class="table table-condensed table-striped" style="font-size:12px;background-color:#FFFFFF;">
		                                  <thead>
		                                  <tr><td colspan="3" style="border-bottom:1px solid grey;"></td></tr>
		                                    <tr>
			                                    	<th style="width:10%;">Number</th>
			                                    	<th style="width:80%;">Question / Competency</th>
			                                    	<th style="width:10%;">Weight</th>
		                                    </tr>
		                                    </thead><tbody>
		                                    <c:forEach items="${guide.questions}" var='q' varStatus='status'>
			                                   <tr>
				                                   <td>${status.index + 1}</td>
				                                   <td>${q.question}</td>			                                     
				                                   <td>${q.weight}</td>
			                                    </tr>
			                                </c:forEach>
		                                    <tr><td colspan="3" style="border-top:1px solid grey;"></td></tr>
		                                    </tbody>	
		                              </table>
		                           </div>
	                 </c:if>	                
	                 </div>
	                 <div class="row" align="center">
	                 			<% if(usr.checkPermission("PERSONNEL-ADMIN-VIEW")) { %>
	                 				<input id='btn-set' class="btn btn-xs btn-success" type="button" value="Set Guide">
	                 				<c:if test='${guide ne null}'>
                                    	<input id='btn-unset' class="btn btn-xs btn-danger" type="button" value="Unset Guide">
                                    </c:if> 
	                 			<%} %>					              
                                <input id='btn-back' type='button' class="btn btn-xs btn-primary" value="View Job Post" onclick="document.location='view_job_post.jsp?comp_num=<%= job.getCompetitionNumber() %>';">
                                <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>                   
                     </div>             
	                          </form>
	    </div></div></div></div>                
	</body>
</html>
