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
<c:set var="countReports" value="0" />
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script src="includes/js/bootstrap-datepicker.min.js"></script>
<script src="includes/js/bcs.js"></script>
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
    			$("#BCS-table tr:even").not(':first').css("background-color", "#FFFFFF");
    		    $("#BCS-table tr:odd").css("background-color", "#f2f2f2");
				

});
		</script>
		<script>
   		$(document).ready(function () {
    		$('.menuBCS').click(function () {
    		$("#loadingSpinner").css("display","inline");
    		});
		});
	</script>
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">	
		 <div class="BCSHeaderText">My Saved Reports</div>	
			 <div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    		<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
			<br/>      
			 
	  		  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="" enctype='multipart/form-data'>
				<br />
				<div id="BCS-Search">
				<table id="BCS-table" width="100%" class="BCSTable">
			  		<thead>
			  			<tr class="listHeader">
			  				<th width="85%" class="listdata" style="padding:2px;">Report Name</th>
			  				<th width="15%" class="listdata" style="padding:2px;">Options</th>
			  			</tr>
			  		</thead>
			  		<tbody>
					<c:choose>
		      			<c:when test="${fn:length(reports) > 0}">
			      			<c:forEach items="${reports}" var="rule">
			      			<c:set var="countReports" value="${countReports + 1}" />
			 					<tr style="border-bottom:1px solid silver;">
			      					<td class="field_contentt">${rule.reportName}</td>
			      					<td align="right" class="field_content">
			      					<button type="button" class="btn btn-xs btn-primary" onclick="window.open('runSavedReport.html?rid=${rule.id}','_blank');">View</button>
		      						<button type="button" class="btn btn-xs btn-warning" onclick="loadMainDivPage('editReport.html?rid=${rule.id}');">Edit</button>
		      						<button type="button" class="btn btn-xs btn-danger" onclick="opendeletedialogreport('${rule.reportName}','${rule.id}');">Del</button>
		      					   </td>
			      				</tr>
		        			</c:forEach>
     							</c:when>
     							<c:otherwise>
     								<tr><td colspan='2' style="color:Red;">No reports found</td></tr>
     							</c:otherwise>
     						</c:choose>
			  		</tbody>
			  	</table>
			  	</div>
			 </form>
		</div>
	
	 <c:choose>
      	<c:when test="${countReports >0 }">
      		<script>$('#details_success_message').html("There are <b>${countReports}</b> reports found.").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#details_error_message').html("Sorry, there are no reports saved at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>
	
	
	
	<div id="myModal" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitle"></h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1"></p>
                    <p class="text-warning" id="title2"></p>
 		    		<p class="text-warning" id="title3"></p>
		</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default" data-dismiss="modal" id="buttonleft"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonright"></button>
                </div>
            </div>
     
    </div>
	
