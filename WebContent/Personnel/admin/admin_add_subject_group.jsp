<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.security.*,
                 com.esdnl.personnel.v2.model.sds.bean.LocationBean,
                 com.esdnl.personnel.v2.utils.StringUtils,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
		<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="jobv2" %>		

<c:set var="optCount" value="0" />  

<html>

	<head>
		<title>NLESD - Member Services - Personnel-Package</title>	
		<meta name="viewport" content="width=device-width, initial-scale=1.0">	

<style>

input[type=checkbox] {
    vertical-align:middle;
    position: relative;
    bottom: 0px;
}

</style>
	</head>

	<body>
	<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Add Subject Group</b></div>
      			 	<div class="panel-body">	
					
				
	  					<form method='post' action='saveSubjectGroup.html'>
	                                	<c:if test="${ not empty msg }">
	                                		<div style='text-align: center;'>
	                                			${ msg }
	                                		</div>
	                                	</c:if>
	                                	
	                        <div class="input-group">
		    				<span class="input-group-addon">Group Name*:</span>						 
						  		<input type='text' id='group_name' name='group_name' class="form-control" placeholder="Enter a Group Name" />
							</div>        	
	                                	
	                        <div class="form-group" style="padding-top:5px;">
						 		
						 		Select subject(s) for this group from the list below.					  
						  		
       							<div style="clear:both;padding-top:10px;"></div>  
       							      								
       								<c:forEach items='${subjects}' var='s'>
       								
       								<div style="float:left;min-width:220px;width:25%;color:DimGrey;font-size:12px;"><label class="checkbox-inline"><input type="checkbox" name='group_subjects' value="${s.subjectID}">${s.subjectName}</label></div>       								
       																
              						</c:forEach>     							
       							
       							
								 	<div style="clear:both;padding-top:10px;"></div>              
	                		
	                				 <div align="center" class="no-print"><a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>  <button type="submit" class="btn btn-xs btn-primary">Add Group</button></div>                   
	                      
	              			</div>	
	              	</form>
	              	</div>
	              	</div>		
	</div>
  </body>
</html>
