<%@ page language="java"
         session="true"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,
                 com.awsd.travel.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
         isThreadSafe="false"%>
         
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />
<html>

	<head>
		<title>NLESD - Travel Claim System</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
						
			<script src="includes/js/jquery.min.js"></script>		
    		<script src="includes/js/jquery-ui.js"></script>    					
			<script src="includes/js/tc.js" type="text/javascript"></script>
			<link href="includes/css/jquery-ui.css" rel="stylesheet" type="text/css">
			<link href="includes/css/tc.css" rel="stylesheet" type="text/css">
	</head>

  <body><br/>
 			Please select a claim from the menu at top to work with, or click the File, New Claim to create a new claim.

				<div class="travelNoticeBlock">
				 <b>Please Note</b>: This is a travel claim system ONLY. All other expenditures MUST be handled through
				                the SDS Purchasing System or through school accounts. Where other expenditures are included, claim processing
				                may be delayed.	
				</div>                
						
		<br/>&nbsp;<br/>
			
			
	</body>
	</html>		
			
	












