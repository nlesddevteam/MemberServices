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
	               	<div class="panel-heading"><b>Selected Working Groups</b></div>
      			 	<div class="panel-body"> 
                     Below is a list of all EECD Working Group Areas which you have been selected.
					
					<%if(request.getAttribute("msg")!=null){%>								
								<div class="alert alert-danger">${ msg }</div>  									
							<%} %>  
					<jsp:include page="eecd_menu.jsp"/>					
					
					<div class="alert alert-success" id="successMsg" style="text-align:center;display:none;" align="center"></div>
					<div class="alert alert-danger" id="errorMsg" style="text-align:center;display:none;" align="center"></div>
					<div style="padding-left:5px; padding-right:5px; padding-top:10px; border-top:1px solid Silver;">
					
					<c:choose>
						<c:when test='${fn:length(areas) gt 0}'>
            					<ul id="check-list-box" class="list-group checked-list-box">
                  					<c:forEach items='${ areas }' var='area'>
                  						<li class="list-group-item" id="${ area.id }"> ${ area.areaDescription }</li>
                  					</c:forEach>                  					
                  				</ul>
                		 </c:when>
                		 <c:otherwise><span style="color:DimGrey;">Sorry, no Working Group Areas selected.</span></c:otherwise>
                	</c:choose>	 
                		 
                	</div>
 </div></div></div>               		
                		
                		
                		
</body>
</html>	
			

			