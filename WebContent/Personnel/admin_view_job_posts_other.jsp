<%@ page language="java"
         import="com.esdnl.personnel.jobs.constants.*,
         				java.util.*, 
          				java.text.*, 
          				com.awsd.pdreg.*,
          				com.awsd.security.*,
          				com.nlesd.school.bean.*,
          				com.nlesd.school.service.*,
          				org.apache.commons.lang.*"
        isThreadSafe="false"%>

		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%
	int zoneid=0;
	zoneid = Integer.parseInt(request.getParameter("zoneid").toString());
	System.out.println(zoneid);
	String statuslong="";
	
	if(request.getParameter("status").equals("All")){
		statuslong="All";
	}else if(request.getParameter("status").equals("Open")){
		statuslong="Open";
	}else if(request.getParameter("status").equals("Unadvertised")){
		statuslong="Unadvertised";
	}else if(request.getParameter("status").equals("NoShortlist")){
		statuslong="Closed No Shortlist";
	}else if(request.getParameter("status").equals("RECAPPROVAL")){
		statuslong="Pending Recommendation Approval";
	}else if(request.getParameter("status").equals("RECACCEPTANCE")){
		statuslong="Pending Recommendation Acceptance";
	}else if(request.getParameter("status").equals("Cancelled")){
		statuslong="Cancelled";
	}else if(request.getParameter("status").equals("Awarded")){
		statuslong="Awarded";
	}
	
%>

<html>
<head>
<title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>

<script type="text/javascript">  
  	$( document ).ready(function() {
  		$('.btnRegionView').click(function(){
  			//self.location.href = 'admin_view_job_posts_other.jsp?zoneid=' + $(this).attr('data-region-id');
  			self.location.href = 'admin_view_job_posts_other.jsp?status=' + $('#status').val() + '&zoneid=' + $(this).attr('data-region-id');
  		});
  		//$('#regionized-view').buttonset();
  		$('.btnRegionView').each(function(){
  			if($(this).attr('data-region-id') == $('#zoneid').val()){
  				$(this).removeClass("btn-primary").addClass("btn-danger");
   			}
 		});
  	
  });
</script>
<style>
		.tableCompNum {width:15%;}
		.tablePosTitle {width:60%;}
		.tableCompEndDate {width:20%;}
		.tableOptions {width:5%;}
		input { border:1px solid silver;}
		input[type="checkbox"] {margin-top: -1px;margin-left:6px;margin-right:2px;vertical-align: middle;}
</style>
</head>
<body>
  
  <esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
  

	<form action='managePosts.html' method="post">
	<input type="hidden" name="op" value="AWARD" />
	<input type="hidden" name="status" id="status" value="<%=request.getParameter("status")%>" />
	<input type="hidden" name="zoneid" id="zoneid" value="<%=zoneid%>" />
  
     <div class="pageHeader"><%=statuslong%> Support Staff Job Opportunities 
     <c:set var="Zone" value="<%=zoneid %>" />
       
       <c:choose>
       <c:when test="${Zone eq 1 }">
       (Avalon Region)   
       </c:when>
        <c:when test="${Zone eq 2 }">
       (Central Region)    
       </c:when>
        <c:when test="${Zone eq 3 }">
       (Western Region)       
       </c:when>
        <c:when test="${Zone eq 4 }">
       (Labrador Region)      
       </c:when>
       <c:when test="${Zone eq 5 }">
       (Provincial)       
       </c:when>
       <c:otherwise>
       (All Regions)      
       </c:otherwise>       
       </c:choose>
     </div> 
     
     <div class="panel-group">	
     
     Below are the <%=statuslong%> job opportunities for the selected region(s) with the total number of positions in brackets in each heading category. To see the positions in each category, click on a category name to open the list. If there are no positions in a category, the header will display Red.
     
		                      				  <div align="center" class="no-print" style="margin-bottom:10px;margin-top:10px;"> 					
			                      				<button class='btnRegionView btn btn-primary btn-xs' type='button' data-region-id='0'>All Regions</button>
			                      					<% for(SchoolZoneBean sz : SchoolZoneService.getSchoolZoneBeans()){ 
			                      						//if(sz.getZoneId() == 5) continue; //NLESD - PROVINCIAL
			                      					%>
			                      				<button class='btnRegionView btn btn-primary btn-xs' type='button' data-region-id="<%= sz.getZoneId() %>"><%= StringUtils.capitalize(sz.getZoneName())%> Region</button>
			                      					<%}%>
		                     				 </div>
		                      
		                      
	                      				
								<%for(int i = 0; i < JobTypeConstant.ALL.length; i++){
                              		if(!(JobTypeConstant.ALL[i].equals(JobTypeConstant.EXTERNALONLY)) &&
                            		  !(JobTypeConstant.ALL[i].equals(JobTypeConstant.INTERNALONLY)) && !(JobTypeConstant.ALL[i].equals(JobTypeConstant.INTERNALEXTERNAL)))
                                		continue;%>
                             <div class="panel panel-danger" id="panelChange<%=i%>">
							    <div class="panel-heading">
							      <h4 class="panel-title">
							        <a data-toggle="collapse" href="#collapse<%=i%>"><%=JobTypeConstant.ALL[i].getDescription()%></a> (<span class="totalCountVal<%=i%>">  </span>)
							      </h4>
							    </div>
							  
							    <div id="collapse<%=i%>" class='panel-collapse collapse <%=(i==0) ? "in":"" %>'>
							      <div class="panel-body">
							      <div class="table-responsive">
							      <job:OtherJobPosts status='<%=request.getParameter("status")%>' type='<%=JobTypeConstant.ALL[i].getValue()%>' zone='<%=zoneid %>'/>
							      
							      <script> 								
 																      	
 									$(".totalCountVal<%=i%>").html("${JobCount}");
 									if (${JobCount} > 0) { 										
 										$("#panelChange<%=i%>").toggleClass("panel-danger panel-success"); 	
 										// $("#collapse<%=i%>").toggleClass("in"); 
 										}
 									
							     </script>
							      </div>
							  
							      </div>    
							    </div>
							  </div>
                            <%}%>
                          
    </div>
     
     <div align="center" class="no-print">
	    	<a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a> 
	    <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-AWARD-MULTIPLE">
	    	<input type="submit" class="btn btn-xs btn-success" value="Award Selected" />
	     </esd:SecurityAccessRequired>
	    </div>
     
    
  
  </form>
  
</body>
</html>
