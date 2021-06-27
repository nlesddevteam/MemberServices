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
<c:set var="countEmp" value="0" />
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrapvalidator.min.js"></script>
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
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
<link rel="stylesheet" type="text/css" href="includes/css/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="includes/css/buttons.dataTables.min.css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
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
    			      paging:         true,
    				  buttons: [
    				    //'copyHtml5', 'excelHtml5', 'pdfHtml5', 'csvHtml5','print'
    				    'copyHtml5', 'excelHtml5',  'csvHtml5','print'
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
        	});</script>
		
		
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">	
		 <div class="BCSHeaderText">View ${companyname} Employees</div>			 
			  <br />
			   <div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    		<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
			 <div id="BCS-Search">
	<form action="viewContractorEmployeesRegDetails.html" method="post" >
	<table style="width:320px;">
	<tr>
	<td style="width:150px;">
		Regional:<br/>
		<select id="regionid" name="regionid">
				<option value="SELECT">*** PLEASE SELECT ***</option>
				<c:forEach var="entry" items="${regions}">
					<option value='${entry.key}'>${entry.value}</option>
				</c:forEach>
			</select>

	</td>
	<td style="width:150px;padding:5px;">		
		Depot:<br/> 
		<select id="depotid" name="depotid">
				<option value="SELECT">*** PLEASE SELECT ***</option>
				<option value="0">ALL</option>
				<c:forEach var="entry" items="${depots}">
					<option value='${entry.key}'>${entry.value}</option>
				</c:forEach>
			</select>
		<tr><td colspan=2>&nbsp;</td></tr>
		<tr>
		<td colspan=2>
		<input type="button" class="menuBCS" value="Get Employees" onclick="return checkregionvalues();" >
		</td></tr></table>
	
		<br />
	     
    	</form>
			 </div>
	</div>		 