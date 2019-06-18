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
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
<script type="text/javascript">
	$( document ).ready(function() {
  		$('.btnRegionView').click(function(){
  			self.location.href = 'admin_view_job_posts_other_o.jsp?zoneid=' + $(this).attr('data-region-id');
  		});
  		//$('#regionized-view').buttonset();
  		$('.btnRegionView').each(function(){
  			if($(this).attr('data-region-id') == $('#zoneid').val()){
   				$(this).css({'color': 'red', 'font-weight' : 'bold'});
   			}
 		});
  	
  });
</script>
<style>
		.tableCompNum {width:60%;}
		.tablePosTitle {width:15%;}
		.tableCompEndDate {width:20%;}
		.tableOptions {width:5%;}
		input[type="checkbox"] {margin-top: -1px;margin-left:6px;margin-right:2px;vertical-align: middle;}
</style>
</head>
<body>
  
  <esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
  

	<form action='managePosts.html' method="post">
	<input type="hidden" name="op" value="AWARD" />
	<input type="hidden" name="zoneid" id="zoneid" value="<%=zoneid%>" />
	     <div class="pageHeader">Support Staff Job Opportunities 
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
     
     		<div align="center" class="no-print" style="margin-bottom:10px;margin-top:10px;"> 					
				<button class='btnRegionView btn btn-primary btn-xs' type='button' data-region-id='0'>All Regions</button>
			    	<% for(SchoolZoneBean sz : SchoolZoneService.getSchoolZoneBeans()){ 
			        	//if(sz.getZoneId() == 5) continue; //NLESD - PROVINCIAL
			        %>
			        <button class='btnRegionView btn btn-primary btn-xs' type='button' data-region-id="<%= sz.getZoneId() %>"><%= StringUtils.capitalize(sz.getZoneName())%> Region</button>
			        <%}%>
		    </div>
		                   		<div class="panel-body">
							      <div class="table-responsive">
							      <job:OtherJobPostsOrig status='ADMIN' type='<%=zoneid%>'/>
							      </div>
							  
							      </div>    
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
