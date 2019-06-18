<%@ page language="java"
         session="true"
         isThreadSafe="false" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>
<esd:SecurityCheck permissions="TRAVEL-CLAIM-ADMIN" />

<html>
	<head>
		<title>Add Supervisor Rule</title>
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
    	
    	<script type="text/javascript" src="includes/js/travel_ajax_v1.js"></script>
    	<script type="text/javascript">
    	$('document').ready(function(){
        	
    		$('#loadingSpinner').css("display","none");
			
    		$('#btnAdd').click(function() {
	    		//process_message('server_message', 'Processing request...');
	    		//document.forms[0].submit();

	    		//if(opener.location.href.indexOf('listSupervisorRules.html') > 0)
	    			//opener.location.href='listSupervisorRules.html';
	    		addnewtravelclaimkmrate();
	    	});

	    	$('#btnClose').click(function() {
	    		var surl="viewTravelClaimSystem.html";
	    		$("#pageContentBody").load(surl);
            	
	    	});
	    	if($('#op').val() == "ADD"){
	    	$( "#effstartdate" ).datepicker({
			      changeMonth: true,//this option for allowing user to select month
			      changeYear: true, //this option for allowing user to select from year range
			      dateFormat: "dd/mm/yy"
			 });
	    	$( "#effenddate" ).datepicker({
			      changeMonth: true,//this option for allowing user to select month
			      changeYear: true, //this option for allowing user to select from year range
			      dateFormat: "dd/mm/yy"
			 });
	    	}
	    	

    	});

    </script>
	</head>
	<body>
		
	<div id="printJob"> 	
		
	<div class="claimHeaderText">Add/Edit KM Rate</div>	
		
	<br/>Below you can add/edit new base and approved rates for travel claim kilometers. Rates may change several times throughout the year. To see past rates, <a onclick="loadMainDivPage('listKmRates.html');" href="#">please click here</a>.<br/><br/>
	
	<br/><div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
		
    <p><form name="add_claim_note_form" method="post">
      
      <c:choose>
		<c:when test="${ claimrate ne null }">
			<input type="hidden" name="op" id="op" value="UPDATE">
		</c:when>
		<c:otherwise>
 			<input type="hidden" name="op" id="op" value="ADD">
		</c:otherwise>
	  </c:choose>
      <p><b>Select Effective Start Date:</b><br/>
      
      <c:choose>
		<c:when test="${ claimrate ne null }">
			<input type="text" id="effstartdate" name="effstartdate" value='${claimrate.effectiveStartDateFormatted}' readonly>
		</c:when>
		<c:otherwise>
			<input type="text" id="effstartdate" name="effstartdate">
		</c:otherwise>
	 </c:choose>
                    	
     <p><b>Select Effective End Date:</b><br/>
        <c:choose>
				<c:when test="${ claimrate ne null }">
 							<input type="text" id="effenddate" name="effenddate" value='${claimrate.effectiveEndDateFormatted}' readonly>
				</c:when>
				<c:otherwise>
  						<input type="text" id="effenddate" name="effenddate">
				</c:otherwise>
		</c:choose>
                    	
                    	
    <p><b>Base Km Rate:</b><br/>
    
                    		<c:choose>
    							<c:when test="${ claimrate ne null }">
       								<input type="text" id="basekmrate" name="basekmrate" value='${claimrate.baseRate}'>
    							</c:when>
    							<c:otherwise>
        							<input type="text" id="basekmrate" name="basekmrate" placeholder="Enter format 0.00">
    							</c:otherwise>
							</c:choose>
    <p><b>Approved Km Rate:</b><br/>
   
   <c:choose>
    							<c:when test="${ claimrate ne null }">
       								<input type="text" id="approvedkmrate" name="approvedkmrate" value='${claimrate.approvedRate}'>
    							</c:when>
    							<c:otherwise>
        							<input type="text" id="approvedkmrate" name="approvedkmrate" placeholder="Enter format 0.00">
    							</c:otherwise>
	</c:choose>
                    	
    <p><img id='btnAdd' src="includes/img/save-off.png" class="img-swap" title="Save Rate" border=0> &nbsp; <a onclick="loadMainDivPage('listKmRates.html');" href="#"><img src="includes/img/view-off.png" class="img-swap" title="View Past Rates" border=0></a> &nbsp; <a href="index.jsp"><img src="includes/img/cancel-off.png" title="Cancel" class="img-swap" border=0></a>
                     
    </form>
    </div>
	</body>
</html>
