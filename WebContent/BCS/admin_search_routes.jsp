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
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrap-datepicker.min.js"></script>
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

	<script>
			$(document).ready(function(){ 
				$('#BCS-Search').css("display","none");
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
    		    
    		    setOptions(); // on load
    		    $('#searchby').change(setOptions); // on change
    		    function setOptions() {
    		    	switch ($("#searchby").val()) {
    		            case "SELECT" :
    		                $("#divtext").hide();
    		                $("#divselects").hide();
    		                break;
    		            case "Name":
       		                $("#divtext").show();
    		                $("#divselects").hide();
    		                break;
    		            case "School":
       		                $("#divtext").hide();
    		                $("#divselects").show();
    		                break;
    		            case "Notes":
       		                $("#divtext").show();
    		                $("#divselects").hide();
    		                break;     		                
    		            default:
    		                $("#divselects").hide();
    		                $("#divtext").show();
    		                break;
    		            }
    		    }
        	});</script>
        	
        	
        	<script>
   			$(document).ready(function () {
    		$('.menuBCS').click(function () {
    		$("#loadingSpinner").css("display","inline").delay(2000).fadeOut();
			$('#reportdata').DataTable({
				  dom: 'Bfrtip',
				  paging:         true,
				  buttons: [
				    'copyHtml5', 'excelHtml5', 'csvHtml5','print'
				  ]
				} );
  			$("#reportdata tr:even").not(':first').css("background-color", "#FFFFFF");
   		    $("#reportdata tr:odd").css("background-color", "#f2f2f2");
    		});  
   			});
		</script>
		
	<div id="printJob">	
      
	<div class="BCSHeaderText">Search Routes</div>
			<div class="alert alert-danger" id="body_error_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
            <div class="alert alert-success" id="body_success_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	<table style="width:320px;">
	<tr>
	<td style="width:150px;">
		Search By:<br/>
			<select id="searchby">
				<option value="SELECT">*** PLEASE SELECT ***</option>
				<c:forEach var="entry" items="${sby}">
					<option value='${entry.value}'>${entry.value}</option>
				</c:forEach>
			</select>

	</td>
	<td style="width:150px;padding:5px;">	
		
		<div id="divtext" style="display:none">
		For:<br/> 
		<input type="text" id="txtsearch" placeholder="Enter Search Text"><br/>
		</div>
		<div id="divselects"  style="display:none">
		For School:<br/> 
		<select id="school">
			<option value="-1">*** Select ***</option>
			<c:forEach var="test" items="${schools}" >
				<option value='${test.value}'>${test.key}</option>
			</c:forEach>
		</select><br/>
		</div>	
		</td>
		</tr>
		<tr><td colspan=2>&nbsp;</td></tr>
		<tr>
		<td colspan=2>
		<input type="button" class="menuBCS" value="Search Routes" onclick="ajaxSearchRoutes()">
		</td></tr></table>
		
	<div class="alert alert-success" id="body_success_message_bottom" style="display:none;margin-top:5px;margin-bottom:5px;padding:2px;"></div>
    <div class="alert alert-danger" id="body_error_message_bottom" style="display:none;margin-top:5px;margin-bottom:5px;padding:2px;"></div>
		<br />
		
	      
    <form>
    <div id="BCS-Search">
      <table id="reportdata" class="table table-striped table-bordered dt-responsive nowrap BCSTable">
      	<THEAD>
  			<tr style="background-color:#b3d9ff;">
	  				<th width="40%" class="listdata">Name</th>
	  				<th width="15%" class="listdata">School</th>
	  				<th width="15%" class="listdata">Contract</th>
	  				<th width="15%" class="listdata">Added By</th>
	  				<th width="10%" class="listdata">Options</th>
  			</tr>
      	</THEAD>	
      		
      		<tbody>
 			</tbody>
      </table>
      </div>
    </form>
</div>
   	<div id="myModal2" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitled">Delete Route</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1d"><span id="spantitle1" name="spantitle1"></span></p>
                    <p class="text-warning" id="title2d"><span id="spantitle2" name="spantitle2"></span></p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default"  id="buttonleftd"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonrightd"></button>
                </div>
            </div>
   		</div>
   	</div>
	

  
 <script src="includes/js/jQuery.print.js"></script>	