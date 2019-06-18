<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.personnel.jobs.bean.*" 
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
		
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />

<html>

	<head>
		<title>MyHRP Applicant Profiling System</title>		
		
		<script type='text/javascript'>
			$(function(){		
				$('#btn-delete').click(function(){
					if(confirm('Are you should you want to delete this interview guide?')){
						document.location='deleteInterviewGuide.html?guideId=${guide.guideId}';
					}
				})
				$('#btn-deactivelist').click(function(){
					document.location='deactivateInterviewGuide.html?guideId=${guide.guideId}&activate=false';					
				})
				$('#btn-activelist').click(function(){
					document.location='deactivateInterviewGuide.html?guideId=${guide.guideId}&activate=true';					
				})					
			});
					
			$("#loadingSpinner").css("display","none");
		</script>
		
	</head>
	
	<body>
    <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>${guide.title} for ${guide.schoolYear}</b></div>
      			 	<div class="panel-body">
  					Rank each answer from ${guide.ratingScaleBottom} to ${guide.ratingScaleTop}, with ${guide.ratingScaleTop} being outstanding. 			
							                                 
       <div class="table-responsive">
       			<c:if test="${param.vji ne 'readOnly'}">	
      								<div style="float:right;padding-top:5px;padding-bottom:5px;">   											
					
					
	     								<form id='frm-interview-guide' action="" method="post">	
	     								    <a class="btn btn-xs btn-info" href="javascript:history.go(-1);">Back to List</a>		     							
		                                    <a class="btn btn-xs btn-primary" href="editInterviewGuide.html?guideId=${guide.guideId}">Edit</a>
		                                    <a class="btn btn-xs btn-danger" id="btn-delete">Delete</a>
											<c:choose>										
		    									<c:when test="${guide.activeList == true}">
		       										<button type="button" id='btn-deactivelist' class="btn btn-xs btn-warning">Deactivate List</button>
		    									</c:when>
		    									<c:otherwise>
		        									<button type="button" id='btn-activelist' class="btn btn-xs btn-success">Activate List</button>
		    									</c:otherwise>	    								
											</c:choose> 
										</form>	
										
				
										
										
                            		</div>
              </c:if>      
      							<table id='interview-guide-list' class="table table-condensed table-striped" style="font-size:12px;background-color:#FFFFFF;">
								    <thead>
								      <tr style="border-top:1px solid grey;">
								        <th width='5%'>#</th>
								        <th width='85%'>QUESTION/COMPETENCY</th>
								        <th width='10%'>WEIGHT</th>	
								      </tr>
								    </thead>
								    <tbody>
									    <c:forEach items="${guide.questions}" var='q' varStatus='status'>
		     								<tr> 	     								
		     									<td>${status.index + 1}.</td>	
										        <td>${q.question}</td>										        
												<td>${q.weight}</td>											
											</tr>
	     								</c:forEach>  
								       <tr><td colspan="3" style="border-top:1px solid grey;"></td></tr>   
								    </tbody>
						  		</table>
		
		<c:if test="${param.vji eq 'readOnly'}">
		<div align="center"><a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a></div>
		</c:if>
		</div>
                        
	</body>
</html>
