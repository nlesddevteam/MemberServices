<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>


<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />

<%
  User usr = null;

  usr = (User) session.getAttribute("usr");
  
%>
<html>
	<head>
		<title>Travel Claim</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">  
		    <meta charset="utf-8">
		    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">    		
    		<link href="includes/css/jquery-ui.css" rel="stylesheet" type="text/css"> 
    		<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">  	
   			<link href="includes/css/travel.css" rel="stylesheet" type="text/css">
   			<!-- For mini-icons in menu -->
   			<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">			
    		<script src="includes/js/jquery.min.js"></script>		
    		<script src="includes/js/jquery-ui.js"></script>
    		<script src="includes/js/bootstrap.min.js"></script>
    		<script src="includes/js/travel.js"></script>
			<script src="includes/js/jquery.maskedinput.min.js"></script>
			
			<script>
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");  
			});
			
			</script>
	</head>
	<body>
  
        <div class="alert alert-danger" id="details_error_message" style="margin-top:10px;margin-bottom:10px;padding:5px;">You searched for ${param.txt} under ${param.type}. 
         <%=request.getParameter("msg")%><br/><br/> Please contract your supervisor if you feel you are recieving this message in error.        
        </div>       
	</body>
</html>
