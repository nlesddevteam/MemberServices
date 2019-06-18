<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.school.*,
         		 com.awsd.personnel.*,                 
                 com.awsd.common.*,
		         java.util.*,
		         java.io.*,
		         java.text.*,
		         com.esdnl.util.*"%>  


<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<html>

	<head>
		<title>Education and Early Childhood Development Groups Admin</title>
					
<script>
$('document').ready(function(){
$("#loadingSpinner").css("display","none");	

 });
    </script>

	
	</head>

  <body>
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Working Groups</b></div>
      			 	<div class="panel-body"> 
  							Below is a list of all EECD Working Group Areas.  Please select all groups that you are interested in being considered for and press the Update button.  An email will be sent to your school
							Administrator stating new groups have been selected for their approval.  Once an Administrator has approved or declined the selection you will be sent an email and the status will show on this
							screen.  If you are selected for a working group that group will show up on the View Working Groups screen.
                 	
                 	<jsp:include page="eecd_menu.jsp"/>
										
					 		<%if(request.getAttribute("msg")!=null){%>								
								<div id="fadeMessage" class="alert alert-danger">${ msg }</div>  									
							<%} %>                 	
                 	
                 			 <div class="alert alert-success" id="successMsg" style="text-align:center;display:none;" align="center"></div>
							 <div class="alert alert-danger" id="errorMsg" style="text-align:center;display:none;" align="center"></div>
					 		
					 		<div style="padding-left:5px; padding-right:5px; padding-top:10px; border-top:1px solid Silver;">
                 	
                 	
                 	<ul id="check-list-box" class="list-group checked-list-box">
                  					<c:forEach items='${ areas }' var='area'>
                  						<c:choose>
                  							<c:when test='${ area.additionalText ne null }'>
                  									<li class="list-group-item" id="${ area.id }" nocheck="Y">
                  									<c:choose>
                  									<c:when test="${ area.currentStatus eq 2 }">
                  										<div class="alert alert-success" role="alert"> ${ area.areaDescription }  [${ area.additionalText }]</div></li>
                  									</c:when>
                  									<c:when test="${ area.currentStatus eq 3 }">
                  										<div class="alert alert-danger" role="alert"> ${ area.areaDescription }  [${ area.additionalText }]</div></li>
                  									</c:when>
                  									<c:otherwise>
                  										<div class="alert alert-primary" role="alert"> ${ area.areaDescription }  [${ area.additionalText }]</div></li>
                  									</c:otherwise>
                  									</c:choose>
                  							</c:when>
                  							<c:otherwise>
                  							<li class="list-group-item" id="${ area.id }" nocheck="N"> ${ area.areaDescription }</li>
                  							</c:otherwise>
                  						</c:choose>
                  						
                  					</c:forEach>
                  					
                  				</ul>
                		
                		<input type="hidden" id="firstsave" name="firstsave" value="${firstsave}">
                		<input type="hidden" id="teacherareas" name="teacherareas" value="${teacherareas}">
                		<div align="center"><button class="btn btn-success btn-xs" id="get-checked-data">Update Teacher Areas</button></div>
            		</div>
</div></div></div>            			
            			
</body>
</html>	
			

			