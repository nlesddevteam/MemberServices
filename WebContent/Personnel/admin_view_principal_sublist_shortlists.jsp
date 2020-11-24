<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.security.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*, 
                 com.esdnl.personnel.jobs.constants.*" %>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<esd:SecurityCheck permissions="PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
<c:set var="listItemCnt" value="0" />
<%
int listCnt=0;
  User usr = (User) session.getAttribute("usr");
  
	HashMap<SubstituteListConstant, ArrayList<SubListBean>> lists = null;
  
  if(request.getAttribute("PRINCIPAL_SHORTLISTS") != null)
    lists = (HashMap<SubstituteListConstant, ArrayList<SubListBean>>) request.getAttribute("PRINCIPAL_SHORTLISTS");
%>
  

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
$("#loadingSpinner").css("display","none");
</script>
<script>
 $('document').ready(function(){
	  $(".jobsapp").DataTable(
		{"order": [[ 0, "asc" ]],
		"lengthMenu": [[25, 50, 100, 200, -1], [25, 50, 100, 200, "All"]]
		});
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
	               	<div class="panel-heading"><b>My Substitute Shortlists</b></div>
      			 	<div class="panel-body"> 
      			 	
      			 	Below are the Substitute Lists currently available. Click on a tab to open that list. Lists are sorted by name by default.
      			 	 <br/><br/>
					<div class="table-responsive"> 


<div class="panel-group" id="accordion">
  



                              	<c:forEach var='sl' items="${PRINCIPAL_SHORTLISTS}">
                              	
                              	<%listCnt++; %>
                              	
                              	<div class="panel panel-default">
    							<div class="panel-heading">
      							<h4 class="panel-title">
        						<a data-toggle="collapse" data-parent="#accordion" href="#collapse<%=listCnt%>">${sl.key.description } (<span class="totalCountVal<%=listCnt%>"></span>)</a>
      							</h4>
    							</div>
    							<div id="collapse<%=listCnt%>" class="panel-collapse collapse">
     							 <div class="panel-body">
     							 
     							 <c:choose>
	                                		<c:when test="${fn:length(sl.value) gt 0}">
	                                		
	                                	<table class="jobsapp table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;">
									    <thead>
									      <tr>
									        <th width='40%'>LIST NAME</th>
									        <th width='40%'>REGION</th>									        															       
									        <th width='20%'>OPTIONS</th>
									      </tr>
									    </thead>
									    <tbody>
	                                	<c:forEach var='l' items="${sl.value}" varStatus="status">
			                                      <tr>
			                                      	<td>${l.title}</td>
			                                        <td><span style="text-transform:Capitalize;">${l.region.name}</span></td>
			                                        <td><a class="btn btn-xs btn-primary" href='viewSubListShortList.html?list_id=${l.id}'>View List</a></td>
			                                      </tr>
			                              <c:set var="listItemCnt" value="${listItemCnt+1}"/>        
			                            </c:forEach>
			      											<tr><td colspan='3'>&nbsp;</td></tr>
			                            <tr>
                                  	<td colspan='2'>Primary/Elementary - Complete List</td>
                                    <td><a class="btn btn-xs btn-primary" href='viewSubListShortListByTrnLvl.html?trnlvl_id=2'>View List</a></td>
                                  </tr>
                                  <tr>
                                  	<td colspan='2'>Secondary  - Complete List</td>
                                    <td><a class="btn btn-xs btn-primary" href='viewSubListShortListByTrnLvl.html?trnlvl_id=3'>View List</a></td>
                                  </tr>
			                            </tbody>
			                            </table>
	                                  	</c:when>
		                                  <c:otherwise>
		                                     <div class="alert alert-danger">No lists avaliable at this time. Thank you.</div>
		                                	</c:otherwise>
	                                	</c:choose>
	                              
	                              	
	                             	</div>
    								</div>
  									</div>
  									<br/>
  									<script>
	                              $(".totalCountVal<%=listCnt%>").html("${listItemCnt}");
	                               </script>
                                </c:forEach>
                    
                    </div>
                    </div>
                    </div>
 </div>            
                                
                              
</body>
</html>
