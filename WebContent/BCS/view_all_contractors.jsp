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
<c:set var="countCont" value="0" />
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
    			//$("#reportdata").dataTable();
    			$('#reportdata').DataTable({
    				  dom: 'Bfrtip',
    				  scrollY:        300,
    				  scrollX:        true,
    			      scrollCollapse: true,
    			      paging:         false,
    				  buttons: [
    				    'copyHtml5', 'excelHtml5', 'csvHtml5','print'
    				  ]
    				} );
});
		</script>
		<script>
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
    		    $("#reportdata tr:even").not(':first').css("background-color", "#FFFFFF");
    		    $("#reportdata tr:odd").css("background-color", "#f2f2f2");
        	});
		</script>
		
		
		
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">	
	<div class="BCSHeaderText">View Contractors</div>	
	
		 <br />
			   <div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    		<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
			 <div id="BCS-Search">
			 Below is a table of all the <b>Contractors</b> data as requested. Depending on the data returned, you may need to scroll to see other data column and/or row fields. 
			 If you require to sort data in a column, you can click on the arrows located to the right in each column title header. 
			 To export this data to Excel, CSV or to print/copy, you can select either option below to download a copy to open or print.
			
			 
			  	<div id="divtable" style="max-width:100%;padding-top:10px;">	
			  	
			  	<table id="reportdata" class="table table-striped table-bordered dt-responsive nowrap BCSTable">
				<thead>
				<tr style="background-color:#b3d9ff;">
				<th class="listdata">First Name</th>
				<th class="listdata">Middle Name</th>
				<th class="listdata">Last Name</th>				
				<th class="listdata">Email</th>
				<th class="listdata">Address 1</th>
				<th class="listdata">Address 2</th>
				<th class="listdata">City</th>
				<th class="listdata">Province</th>
				<th class="listdata">Postal Code</th>
				<th class="listdata">Home Phone</th>
				<th class="listdata">Cell Phone</th>
				<th class="listdata">Work Phone</th>
				<th class="listdata">Company</th>
				<th class="listdata">Status</th>
				<th class="listdata">Business Number</th>
				<th class="listdata">Hst Number</th>

				</thead>
				<tbody>
					<c:forEach items="${contractors}" var="rule">
					<c:set var="countCont" value="${countCont + 1}" />
							<tr>
							<td class="field_content">${rule.firstName}</td>
							<td class="field_content">${rule.middleName}</td>
							<td class="field_content">${rule.lastName}</td>							
							<td class="field_content">${rule.email}</td>
							<td class="field_content">${rule.address1}</td>
							<td class="field_content">${rule.address2}</td>
							<td class="field_content">${rule.city}</td>
							<td class="field_content">${rule.province}</td>
							<td class="field_content">${rule.postalCode}</td>
							<td class="field_content">${rule.homePhone}</td>
							<td class="field_content">${rule.cellPhone}</td>
							<td class="field_content">${rule.workPhone}</td>
							<td class="field_content">${rule.company}</td>
							<td class="field_content">${rule.statusText}</td>
							<td class="field_content">${rule.businessNumber}</td>
							<td class="field_content">${rule.hstNumber}</td>

							</tr>
					</c:forEach>
				</tbody>
				</table>
				

		</div>
	</div></div>
	
	
	<c:choose>
      	<c:when test="${countCont >0 }">
      		<script>$('#details_success_message').html("There are <b>${countCont}</b> contractor entries found.").css("display","block").delay(6000).fadeOut();      		
      		</script>	
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#details_error_message').html("Sorry, there are no contractor entries at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>