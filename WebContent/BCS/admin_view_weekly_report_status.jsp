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
<c:set var="countReports" value="0" />
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script src="includes/js/bootstrap-datepicker.min.js"></script>
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
		 <div class="BCSHeaderText">Weekly Report Status</div>	
			 <div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    		<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
			<br/>      
			 
	  		  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post">
				<br />
				<div id="BCS-Search" align="center">
						<table width="50%">
			  				<c:choose>
			  				<c:when test="${abean.runWeeklyReport }">
			  					<tr><td class="BCSHeaderText" style="padding:2px;" align="center">Current Status: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Enabled</td></tr>
	            				<tr style="background-color:White"><td align="center"><br /><button id='butstatus' type='button'  onclick="updateweeklystatus('D')">Disable Weekly Report</button></td></tr>
	            			</c:when>
			  				<c:otherwise>
			  					<tr><td class="BCSHeaderText" style="padding:2px;" align="center">Current Status: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Disabled</td></tr>
	            				<tr><td style="background-color:White" align="center"><br /><button id='butstatus' type='button' onclick="updateweeklystatus('E')">Enable Weekly Report</button></td></tr>
	            			</c:otherwise>
			  				</c:choose>
			  			</table>
			  	</div>
			 </form>
		</div>
	

	
	

	
