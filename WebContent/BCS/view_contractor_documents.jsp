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
        			var date_input=$('#expirydate');
        	      var options={
        	        format: 'mm/dd/yyyy',
        	        todayHighlight: true,
        	        orientation: "left top",
        	        autoclose: true,
        	      };
        	      date_input.datepicker(options);

});
		</script> 

		<script>
   			$(document).ready(function () {
    		$('.menuBCSC').click(function () {
    		$("#loadingSpinner").css("display","inline").delay(2000).fadeOut();
    		});  
   			});
		</script> 		
		   		    
	<div id="printJob">	
					
			<div class="BCSHeaderText">Contractor Documents</div>
	
				<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
   				<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
				<p>
				<div align="right"><button type="button" class="btn btn-xs btn-success" onclick="openaddnewdialogc();">Add Document</button></div>	
			<div id="BCS-Search">
			
			<table id="BCS-table" width="100%" class="BCSTable">
     		 <thead>
		     		<tr class="listHeader">
		      		<th width="30%" class="listdata">Document Title</th>
		      		<th width="40%" class="listdata">Document Type</th>
		      		<th width="10%" class="listdata">Uploaded</th>
		      		<th width="10%" class="listdata">Expiry</th>
		      		<th width="10%" class="listdata">Options</th>
		      		
		      		
		      		</tr>
		      		</thead>
		      		<tbody>
		      		<c:choose>
		      		<c:when test="${fn:length(documents) > 0}">
			      		<c:forEach items="${documents}" var="rule">
			      		<c:set var="countDocs" value="${countDocs + 1}" />
	 					<tr style="border-bottom:1px solid silver;">
		 					
		      					<td class="field_content">${rule.documentTitle}</td>
		      					<td class="field_content">${rule.typeString}</td>
		      					<td class="field_content">${rule.dateUploadedFormatted}</td>
		      					<td class="field_content">${rule.expiryDateFormatted}</td>
		      					<td class="field_content">
		      					<button type="button" class="btn btn-xs btn-primary" onclick="window.open('${spath}${rule.viewPath}','_blank');">View</button>
		      					<button type="button" class="btn btn-xs btn-danger" onclick="opendeletedocdialogc('${rule.documentTitle}','${rule.id}');">Del</button>
		      					</td>
		      				</tr>
		        		</c:forEach>
		        		</c:when>
		        		<c:otherwise>
		        			<tr><td colspan='5' style="color:Red;">No documents found.</td></tr>
		        		</c:otherwise>
		        		</c:choose>
		        	</tbody>	
	      		</table>
	</div></div>
		
		
		
		<c:choose>
      	<c:when test="${countDocs >0 }">
      		<script>$('#details_success_message').html("There are <b>${countDocs}</b> document(s) currently in the system.").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#details_error_message').html("Sorry, there are no documents posted at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>
		
		
	<div id="myModal" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitle">Add New Document</h4>
                    	<div class="alert alert-danger" style="display:none;" id="dalert" align="center">
  							<span id="demessage"></span>
						</div>
						<div class="alert alert-success" style="display:none;" id="dalerts" align="center">
  							<span id="dsmessage"></span>
						</div>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1">Document Type:</p>
                    <p>
                    <select id="documenttype" name="documenttype">
                    	<option value="-1">Please select type</option>
						<c:forEach var="entry" items="${dtypes}">
							<option value='${entry.key}'>${entry.value}</option>
						</c:forEach>
                    </select>
                    </p>
                    <p class="text-warning" id="title2">Document Title:</p>
                    <p>
                    <input class="form-control" id="documenttitle" name="documenttitle" placeholder="document title" type="text">
                    </p>
                    <p class="text-warning" id="title2">Expiry Date (If document has expiry date then please enter):</p>
                    <p>
	                <input class="form-control" id="expirydate" name="expirydate" placeholder="MM/DD/YYY" type="text">
	                </p>
	            	<p class="text-warning" id="title3">Document:</p>
 		    		<p>
 		    		<span class="btn btn-default btn-file" >
    					Browse <input type="file" id="documentname" name="documentname" accept="application/pdf">(PDF file format only)
					</span>
 		    		</p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"  id="buttonleft"></button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" id="buttonright"></button>
                </div>
            </div>
   		</div>
   	</div>
   	<div id="myModal2" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitled">Delete Document</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1d"><span id="spantitle1" name="spantitle1"></span></p>
                    <p class="text-warning" id="title2d"><span id="spantitle2" name="spantitle2"></span></p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"  id="buttonleftd"></button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" id="buttonrightd"></button>
                </div>
            </div>
   		</div>
   	</div>	

  <script src="includes/js/jQuery.print.js"></script>	