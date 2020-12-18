<%@ page language="java"
         session="true"
         isThreadSafe="false" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>
<esd:SecurityCheck permissions="TRAVEL-CLAIM-ADMIN" />


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
	
<img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/addrate.gif" style="max-width:150px;" border=0/> 
 
 <div class="pageHeader">Add/Edit KM Rate</div>
<div class="pageBodyText">	
	<br/>Below you can add/edit new base and approved rates for travel claim kilometers. Rates may change several times throughout the year. <br/><br/>
	
	<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
	<br/><br/>
   <form name="add_claim_note_form" method="post">
      
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
			<input type="text" id="effstartdate" class="form-control" name="effstartdate" value='${claimrate.effectiveStartDateFormatted}' readonly>
		</c:when>
		<c:otherwise>
			<input type="text" id="effstartdate" class="form-control" name="effstartdate">
		</c:otherwise>
	 </c:choose>
                    	
     <p><b>Select Effective End Date:</b><br/>
        <c:choose>
				<c:when test="${ claimrate ne null }">
 							<input type="text" id="effenddate" class="form-control" name="effenddate" value='${claimrate.effectiveEndDateFormatted}' readonly>
				</c:when>
				<c:otherwise>
  						<input type="text" id="effenddate" class="form-control" name="effenddate">
				</c:otherwise>
		</c:choose>
                    	
                    	
    <p><b>Base Km Rate:</b><br/>    
                    		<c:choose>
    							<c:when test="${ claimrate ne null }">
       								<input type="text" id="basekmrate" class="form-control" name="basekmrate" value='${claimrate.baseRate}'>
    							</c:when>
    							<c:otherwise>
        							<input type="text" id="basekmrate" class="form-control" name="basekmrate" placeholder="Enter format 0.00">
    							</c:otherwise>
							</c:choose>
    <p><b>Approved Km Rate:</b><br/>
   
   <c:choose>
    							<c:when test="${ claimrate ne null }">
       								<input type="text" id="approvedkmrate" class="form-control" name="approvedkmrate" value='${claimrate.approvedRate}'>
    							</c:when>
    							<c:otherwise>
        							<input type="text" id="approvedkmrate" class="form-control" name="approvedkmrate" placeholder="Enter format 0.00">
    							</c:otherwise>
	</c:choose>
                    	
    <p>
    <div align="center">
    <a class="btn btn-sm btn-success" href="#" id='btnAdd'><i class="fas fa-save"></i> Save</a>   
    <a class="btn btn-sm btn-primary" href="#" onclick="loadingData();loadMainDivPage('listKmRates.html');return false;"><i class="fa fa-fw fa-file-text-o"></i> List Current Rates</a>
	<a class="btn btn-sm btn-dark" href="https://www.gov.nl.ca/exec/hrs/working-with-us/auto-reimbursement/" target="_blank"><i class="fa fa-fw fa-file-text-o"></i> Check Gov NL Rates</a>
 	<a href="index.jsp"  class="btn btn-sm btn-danger"><i class="fas fa-times"></i> Cancel</a>
    </div>                  
    </form>
  
	</div>
