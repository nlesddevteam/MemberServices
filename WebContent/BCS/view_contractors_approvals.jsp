<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,                 
                 com.awsd.common.*,com.esdnl.util.*,
                 org.apache.commons.lang.StringUtils,
                 org.apache.commons.lang.StringUtils.*, 
                 java.util.*,
                 java.io.*,
                 java.text.*,
                 java.sql.*" isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<c:set var="countContractors" value="0" />
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
<esd:SecurityCheck permissions="BCS-SYSTEM-ACCESS" />
	<script>
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
    			$('#reportdata').DataTable({
  				  dom: 'Bfrtip',
  				  scrollY:        300,
  				  scrollX:        true,
  			      scrollCollapse: true,
  			      paging:         true,
  				  buttons: [
  				    'copyHtml5', 'excelHtml5', 'csvHtml5','print'
  				  ]
  				} );
    			$("#reportdata tr:even").not(':first').css("background-color", "#FFFFFF");
    		    $("#reportdata tr:odd").css("background-color", "#f2f2f2");
    		   
        	});</script>
        	
        	<script>
   			$(document).ready(function () {
    		$('.menuBCS').click(function () {
    		$("#loadingSpinner").css("display","inline");
    		});  
   			});
	</script>
        	
		
	<div id="printJob">	
      
	<div class="BCSHeaderText">${reporttitle}</div>
	<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
	<br/>       
    <form>
    <div id="BCS-Search">
      <div id="divtable" style="max-width:100%;padding-top:10px;">	
		<table id="reportdata" class="table table-striped table-bordered dt-responsive nowrap BCSTable">
      	<thead>
     		<tr style="border-bottom:1px solid grey;" class="listHeader">
      		<th width="20%" class="listdata" style="padding:2px;">Company</th>
      		<th width="10%" class="listdata" style="padding:2px;">Name</th>
      		<th width="35%" class="listdata" style="padding:2px;">City</th>
      		<th width="15%" class="listdata" style="padding:2px;">Date Submitted</th>
      		<th width="10%" class="listdata" style="padding:2px;">Options</th>
      		</tr>
      </thead>
      <tbody>
      	<c:choose>
      		<c:when test="${fn:length(contractors) > 0}">
	      		<c:forEach items="${contractors}" var="rule">
	      		<c:set var="countContractors" value="${countContractors + 1}" />
 					<tr style="border-bottom:1px solid silver;">
      					
      					<td class="field_content"><a href='#' class="menuBCS" onclick="closeMenu();loadMainDivPage('adminViewContractor.html?cid=${rule.id}');">${rule.company}</a></td>
      					<td class="field_content">${rule.lastName}, ${rule.firstName}</td>
      					<td class="field_content">${rule.city}</td>
      					<td class="field_content">${rule.dateSubmittedFormatted}</td>
      					<td class="field_content" align="center">
      					<button type="button" class="btn btn-xs btn-primary" onclick="closeMenu();loadMainDivPage('adminViewContractor.html?cid=${rule.id}');">View</button>
      					</td>
      				</tr>
        		</c:forEach>
        	</c:when>
        	<c:otherwise>
        		<tr><td colspan='5' style="color:Red;">No contractors found.</td></tr>
        	</c:otherwise>
        </c:choose>
        </tbody>
      </table>
      </div>
     </div> 
      <c:choose>
      	<c:when test="${countContractors >0 }">
      		<script>$('#details_success_message').html("There are <b>${countContractors}</b> <span style='text-transform:lowercase;'>${reporttitle}</span>.").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#details_error_message').html("Sorry, there are no <span style='text-transform:lowercase;'>${reporttitle}</span> at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>
    </form>
</div>
	

  
 