<%@ page language="java"
         import="com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.util.*" 
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
				
        <%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
        
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />

<c:set var="guideTCount" value="0" />  
<c:set var="guideSCount" value="0" />
<c:set var="guideCount" value="0" />
<html>
<head>
	<title>MyHRP Applicant Profiling System</title>
	<script>
$("#loadingSpinner").css("display","none");
</script>
	 <script>
 $('document').ready(function(){
	  $(".intGuides").DataTable( {
		  "order": [[ 1, "desc" ]]	
		
		}	  
	  );
 });
    </script>
    
    <style>
input {    
    border:1px solid silver;
		}
</style>
		
</head>
<body>
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>
			               	<c:if test="${ not empty param.status }">
								<span style='text-transform:capitalize;'>${ param.status }</span>
							</c:if> 
							Interview Guides (<span class="guideNumCount"></span>)</b>
	               	</div>
      			 	<div class="panel-body">
      			 	Current <c:if test="${ not empty param.status }"><b>${ param.status }</b></c:if> interview guides can be found below sorted by year.<br/><br/>
					To edit/view a particular guide, select view to the right of each list. To add a new interview guide, click on the Add Interview Guide link to the right.	
					<br/>
						<c:if test="${not empty msg}">
	                        <div class="alert alert-danger">${msg}</div>
	                    </c:if>                                 
                <br/> 
                			<ul class="nav nav-tabs">
							  <li class="active"><a data-toggle="tab" href="#teaching">Teaching (<span class="guideTNumCount"></span>)</a></li>
							  <li><a data-toggle="tab" href="#support">Support Staff (<span class="guideSNumCount"></span>)</a></li>  
							</ul>
							
							<div class="tab-content">
							  <div id="teaching" class="tab-pane fade in active">
							   
							   					<div class="table-responsive"> <br/>
														   <table id='interview-guide-list' class="intGuides table table-condensed table-striped" style="font-size:12px;background-color:#FFFFFF;">
															    
															    <c:choose>
							                                  	<c:when test='${fn:length(tguides) gt 0}'>
							                                  	<thead>
															      <tr>
															        <th width='10%'>ID</th>
															        <th width='10%'>YEAR</th>
															        <th width='60%'>TITLE</th>								        
															        <th width='10%'>QUESTIONS</th>
															        <th width='10%' class="no-print">OPTIONS</th>
															      </tr>
															    </thead>
															    <tbody>
							                                  	
							                                  	
							                                  		<c:forEach items='${tguides}' var='g'>
							                                  		<c:set var="guideTCount" value="${guideTCount + 1}" /> 								    
															    		<tr>
									                                      <td>${g.guideId}</td>
									                                      <td>${g.schoolYear}</td>
									                                      <td>${g.title}</td>
									                                      <td>${g.numberOfQuestions}</td>
									                                      <td class="no-print"><a href="viewInterviewGuide.html?guideId=${g.guideId}" class="btn btn-xs btn-warning">VIEW</a></td>
									                                    </tr>  
															    </c:forEach>
															    </tbody>
															    
							                                  	</c:when>
							                                  	<c:otherwise>    
															    <tbody>
															      <tr>
															       <td>
								                                        No Teacher interview guide records found.
								                                      </td>
															      </tr>
															    </tbody>
							                                     
							                                  	</c:otherwise>
							                                  </c:choose>    
															    <script>$(".guideTNumCount").html("${guideTCount}");</script>    
															       
															</table>
												</div>
							 	</div>
							  	<div id="support" class="tab-pane fade">
							    			
							    				<div class="table-responsive"> <br/>
														   <table id='interview-guide-list' class="intGuides table table-condensed table-striped" style="font-size:12px;background-color:#FFFFFF;">
															   
															    <c:choose>
							                                  	<c:when test='${fn:length(sguides) gt 0}'>
							                                  	 <thead>
															      <tr>
															        <th width='10%'>ID</th>
															        <th width='10%'>YEAR</th>
															        <th width='60%'>TITLE</th>								        
															        <th width='10%'>QUESTIONS</th>
															        <th width='10%' class="no-print">OPTIONS</th>
															      </tr>
															    </thead>
															    <tbody>
							                                  	
							                                  	
							                                  		<c:forEach items='${sguides}' var='g'>
							                                  		<c:set var="guideSCount" value="${guideSCount + 1}" /> 								    
															    		<tr>
									                                      <td>${g.guideId}</td>
									                                      <td>${g.schoolYear}</td>
									                                      <td>${g.title}</td>
									                                      <td>${g.numberOfQuestions}</td>
									                                      <td class="no-print"><a href="viewInterviewGuide.html?guideId=${g.guideId}" class="btn btn-xs btn-warning">VIEW</a></td>
									                                    </tr>  
															    </c:forEach>
															    </tbody>
													  			
							                                  	</c:when>
							                                  	<c:otherwise>  
							                                  	 <tbody>
															      <tr>
															       <td>
								                                        No Support Staff interview guide records found.
								                                      </td>
															      </tr>
															    </tbody>
															   
							                                  	</c:otherwise>
							                                  </c:choose>    
															   <script>$(".guideSNumCount").html("${guideSCount}");</script>	     
															   </table>
															          
															    
												</div>
							  </div>
							    
							<c:set var="guideCount" value="${guideSCount + guideTCount}" />
							<script>
							$(".guideNumCount").html("${guideCount}");							
							</script>
							</div>
	 						<div align="center" class="no-print">				
	  							<a href='addInterviewGuide.html' class="btn btn-xs btn-success" role="button">Add Interview Guide</a> &nbsp; <a href='admin_index.jsp' class="btn btn-xs btn-danger" role="button">Cancel</a>
							</div>
							                
</body>
</html>
