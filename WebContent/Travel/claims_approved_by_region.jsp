<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.travel.bean.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
        isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />

  		<script>
        $( document ).ready(function() {
        	$('#loadingSpinner').css("display","none");
        	$("#rightMsg").html("Please Select Region");	
        	
      		$("#regions").on("change", function(event) {       			
      			getapprovedtravelclaimsbyregion();
      			
      			var c = $("#regions option:selected").text();      	
      	      	$("#rightMsg").html(c);	
      			
    		} );

        });
        
        
        
  		</script>
	
	
		
			<div id="printJob">
	
	
	
		<div class="claimHeaderText">Travel Claims Approved For Payment By Region <div id="rightMsg" style="float:right;padding-right:5px;"></div></div>
		
				
		<br/><br/>
		
		Please Select Region: 
		<select id="regions">
							<option value='-1'>*** Please Select ***</option>
							<option value='0'>View All Travel Claims</option>
							<option value='2'>Central</option>
							<option value='1'>Eastern</option>
							<option value='4'>Labrador</option>
							<option value='6'>Other</option>
							<option value='5'>Provincial</option>
							<option value='3'>Western</option>
							</select>
		<p>
		<div id="claims">
			<table id="claims-table" class="claimsTable">
				<thead>					
					<tr class="listHeader">
						<th width="25%" class="listdata" style="padding:2px;">Employee</th>
						<th width="10%" class="listdata" style="padding:2px;">Type</th>
						<th width="55%" class="listdata" style="padding:2px;">Title/Month</th>
						<th width="10%" class="listdata" style="padding:2px;">Function</th>
					</tr>
				</thead>
				<tbody>
			</tbody>
			</table>
		</div>
		
		
		
		</div>
		<!-- ENABLE PRINT FORMATTING -->
	<script src="includes/js/jQuery.print.js"></script>
   