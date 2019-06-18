<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,                
                 com.awsd.common.*,com.esdnl.util.*,
                 org.apache.commons.lang.StringUtils,
                 org.apache.commons.lang.StringUtils.*, 
                 java.util.*,
                 java.io.*,
                 java.text.*,
                 java.sql.*"
        isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="includes/css/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="includes/css/buttons.dataTables.min.css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script src="includes/js/bootstrap-datepicker.min.js"></script>
<script src="includes/js/jquery.dataTables.min.js"></script>
<script src="includes/js/dataTables.bootstrap.min.js"></script>
<script src="includes/js/buttons.print.min.js"></script>
<script src="includes/js/buttons.html5.min.js"></script>
<script src="includes/js/dataTables.buttons.min.js"></script>
<script src="includes/js/jszip.min.js"></script>
<script src="includes/js/pdfmake.min.js"></script>
<script src="includes/js/vfs_fonts.js"></script>
<script src="includes/js/bcs.js"></script>

<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
    			
    			
});
		</script>
	<script>
   		$(document).ready(function () {
    		$('.menuBCS').click(function () {
    		$("#loadingSpinner").css("display","inline");
    		});
		});
	</script>
		
		
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">	
		 <div class="BCSHeaderText">View Contractor Employees</div>	
		
		 <p>
			  Select Contractor
			  </p>
			  <p>
			  	<select id="selectcon" name="selectcon">
			  		<option value="-1">Please select</option>
			  		<esd:SecurityAccessRequired permissions="BCS-VIEW-CONTRACTORS">
			  		<option value="0">All Contractors</option>
			  		</esd:SecurityAccessRequired>
			  		<c:forEach items="${contractors}" var="rule">
			  			<c:choose>
			  				<c:when test="${rule.company ne null }">
			  					<option value="${rule.id}">${rule.company}(${rule.lastName},${rule.firstName})</option>
			  				</c:when>
			  				<c:otherwise>
			  					<option value="${rule.id}">${rule.lastName},${rule.firstName}</option>
			  				</c:otherwise>
			  			</c:choose>
			  		</c:forEach>
			  	</select>
			  	</p>
			  	<p>
			  	<input type="button" value="Get Report Data" class="menuBCS" onclick="getContractorsEmployees();">


		</div>
	