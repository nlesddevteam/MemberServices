<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.security.*,
                 com.awsd.school.*,
                 com.esdnl.school.bean.*,
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



<html>

	<head>
		<title>NLESD - Member Services - Personnel-Package</title>		
		<meta name="viewport" content="width=device-width, initial-scale=1.0">	
	</head>

	<body>	
	<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Edit Subject Group ${group.groupName}</b></div>
      			 	<div class="panel-body"> 
					<form method='post' action='saveSubjectGroup.html'>
	                       <input type='hidden' name='group_id' value='${group.groupId}' />  
	                       
	                       <div class="input-group">
		    				<span class="input-group-addon">Group Name*:</span>	                        						 
								  <input type='text' id='group_name' name='group_name' class="form-control" value='${group.groupName}' />
							</div>
							<div class="form-group">
								 <div class="form-group" style="padding-top:5px;">						 								  
								 <br/>Select subject(s) for the ${group.groupName} group from the list below.	
								 <div style="clear:both;padding-top:10px;"></div> 
		       					
		       								<c:forEach items='${subjects}' var='s'>       								
		       									<div style="float:left;min-width:220px;width:25%;font-size:12px;color:DimGrey;"><label class="checkbox-inline">
		       									<input type="checkbox" name='group_subjects' value="${s.subjectID}" <%= ((SubjectGroupBean) pageContext.getAttribute("group", PageContext.REQUEST_SCOPE)).contains((Subject) pageContext.getAttribute("s", PageContext.PAGE_SCOPE)) ? " checked" : "" %>>
		       									${s.subjectName}</label></div>       								
		       								</c:forEach>    							
		       					<div style="clear:both;padding-top:10px;"></div>              
			               </div>	                
	                <button type="submit" class="btn btn-xs btn-primary">Update ${group.groupName} Group</button>
	               </form> 
					</div>
					</div>
	</div>	
  </body>
</html>
