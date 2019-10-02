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
  	      	$("#rightMsg").html("Please Select Date");		
  			
  		$("#approveddates").on("change", function(event) {          		
  			
  			getapprovedtravelclaimsbydate();
      	
      	var c = $("#approveddates option:selected").text();      	
      	$("#rightMsg").html(c);		
      	});

  		
  		
  		 
  		
  		
        });
        
  		
  	
  		 
  		 
  		
  		
	</script>
  		
  		
	<div id="printJob">
	
	
	
		
	
			
	
		<div class="claimHeaderText">
		
			Travel Claims Approved For Payment By Date <div id="rightMsg" style="float:right;padding-right:5px;"></div>
		</div>
		
		<div>
				<select id="approveddates">
							<option value='-1'>*** Please Select Date ***</option>
							
								<c:choose>
									<c:when test='${fn:length(approveddates) gt 0}'>
                						<c:forEach items='${approveddates}' var='g'>
                							<option value="${g.key}">${g.value}</option>
										</c:forEach>
                					</c:when>
            					</c:choose>
            				<option value='ALL'>View All Travel Claims</option>	
				</select>
		</div>		
		
		

		
		
		
		<div id="claims">						
					
						
		    <br/>
			<table id="claims-table" class="claimsTable">
				<thead>
					
					<tr class="listHeader">
						<th width="20%" class="listdata" style="padding:2px;">Employee</th>
						<th width="10%" class="listdata" style="padding:2px;">Type</th>
						<th width="40%" class="listdata" style="padding:2px;">Title/Month</th>	
						<th width="20%" class="listdata" style="padding:2px;">Supervisor</th>					
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
	
