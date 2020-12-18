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
    			getapprovedtravelclaimsbyregion("0");	
    			$.cookie('backurl', 'claimsApprovedByRegion.html', {expires: 1 });
    		});    		
</script>
<style>
input { border:1px solid silver;}
</style>  	
	

	<img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/approved_stamp.png" style="max-width:200px;" border=0/> 
		<div class="siteHeaderBlue">Travel Claims Approved For Payment</div>
		Below are a list of claims approved for payment sorted by approved date. 
		<br/>To sort by another column, simply click on the column header or use the search.
			<br/><br/>	

			<table id="claims-table" class="table table-condensed table-striped table-bordered claimsTable" style="font-size:11px;background-color:White;" width="100%">	
				<thead>
					<tr style="text-transform:uppercase;font-weight:bold;">  	
						<th width="10%">Approved</th>						
						<th width="15%">Employee</th>
						<th width="10%">Type</th>
						<th width="30%">Title/Month</th>
						<th width="5%">Amount</th>	
						<th width="15%">Supervisor</th>							
						<th width="10%">Region</th>					
						<th width="5%">Options</th>
					</tr>
				</thead>
				<tbody>
			</tbody>
			</table>
		
		 
		
		


   