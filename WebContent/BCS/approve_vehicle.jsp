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
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrapvalidator.min.js"></script>
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrap-datepicker.min.js"></script>
<%
String tabs = (String)request.getParameter("tab");
System.out.println(tabs);
String settab="";
if(tabs != null){
	settab="D";
}
pageContext.setAttribute("settab1", settab);
%>
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
        		$('#loadingSpinner').css("display","none");
        		if($("#settab1").val() == "D"){
        	    	  $('.nav-tabs a:last').tab('show'); 
        	    }
});
</script>
<%pageContext.setAttribute("now", new java.util.Date()); %>    		    
	<div id="printJob">	
		<div class="container">
		  <h2>Vehicle Information </h2>
		  	<br />
			<ul class="nav nav-tabs">
    			<li class="active"><a data-toggle="tab" href="#details">Vehicle Details</a></li>
    			<li><a data-toggle="tab" href="#documents">Documents</a></li>
  			</ul>
  			<div class="tab-content">
  			<div id="details" class="tab-pane fade in active">
	  			<form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="">
	  				<div class="form-group">
		      			<label class="control-label col-sm-2" for="email">Status:</label>
		      			<c:choose>
         				<c:when test = "${vehicle.vStatus eq 1}">
         				<div class="col-sm-5">
            				<p class="form-control-static">${ vehicle.statusText}</p>
            				</div>
         				</c:when>
         				<c:when test = "${vehicle.vStatus  eq 2}">
         				<div class="col-sm-5">
            				<p class="form-control-static">${ vehicle.statusText} [Approved By: ${ vehicle.approvedBy} on ${vehicle.dateApprovedFormatted}]</p>
            				</div>
         				</c:when>
                  		<c:when test = "${vehicle.vStatus  eq 3}">
                  		<div class="col-sm-5">
            				<p class="form-control-static">${vehicle.statusText} [Not Approved By: ${vehicle.approvedBy} on ${ vehicle.dateApprovedFormatted} Notes: ${vehicle.statusNotes}]</p>
         				</div>
         				</c:when>
         				<c:otherwise>
            				<div class="col-sm-5"><p class="form-control-static"></p></div>
         				</c:otherwise>
      					</c:choose>
        			</div> 	  	
			<div class="form-group">
		      	<label class="control-label col-sm-2" for="email">Company:</label>
		      	<div class="col-sm-5">
        			<p class="form-control-static">${vehicle.bcBean.company}</p>
      		  	</div>
		    </div>
			   	<div class="form-group">
	                <label class="control-label col-sm-2" for="email">Vehicle Make:</label><input type="hidden" id="vid" name="vid" value="${vehicle.id}">
	                <input type="hidden" id="settab1" name="settab1" value="${settab1}">
	                <input type="hidden" id="hidfullname" name="hidfullname" value="${vehicle.vPlateNumber} [${vehicle.bcBean.company}]">
	                <div class="col-sm-5">
        				<p class="form-control-static">${vmake}</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Vehicle Model:</label>
	                <div class="col-sm-5">
        				<p class="form-control-static">${vmodel}</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Vehicle Year:</label>
	                <div class="col-sm-5">
        				<p class="form-control-static">${vehicle.vYear}</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Serial Number:</label>
	                <div class="col-sm-5">
        				<p class="form-control-static">${vehicle.vSerialNumber}</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Plate Number:</label>
	                <div class="col-sm-5">
        				<p class="form-control-static">${vehicle.vPlateNumber}</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Vehicle Type:</label>
	                <div class="col-sm-5">
        				<p class="form-control-static">${vtype}</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Vehicle Size:</label>
	                <div class="col-sm-5">
        				<p class="form-control-static">${vsize}</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Registered Owner:</label>
	                <div class="col-sm-5">
        				<p class="form-control-static">${vehicle.vOwner}</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Registration Expiry Date:</label>
	                <div class="col-sm-5">
        				<p class="form-control-static">${vehicle.regExpiryDate == null ? '' : vehicle.regExpiryDateFormatted}</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Insurance Expiry Date:</label> 
	                <div class="col-sm-5">
        				<p class="form-control-static">${vehicle.insExpiryDate == null ? '' : vehicle.insExpiryDateFormatted}</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Insurance Provider:</label>
	                <div class="col-sm-5">
        				<p class="form-control-static">${vehicle.insuranceProvider}</p>
      		  		</div>
	            </div>
	           <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Primary CMVI Date:</label>
	                <div class="col-sm-5">
        				<p class="form-control-static">${vehicle.fallInsDate == null ? '' : vehicle.fallInsDateFormatted}</p>
      		  		</div>
	            </div>
	           <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Secondary CMVI Date:</label>
	                <div class="col-sm-5">
        				<p class="form-control-static">${vehicle.winterInsDate == null ? '' : vehicle.winterInsDateFormatted}</p>
      		  		</div>
	            </div>
	           <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Fall H.E. Inspection Date:</label>
	                <div class="col-sm-5">
        				<p class="form-control-static">${vehicle.fallHeInsDate == null ? '' : vehicle.fallHeInsDateFormatted}</p>
      		  		</div>
	            </div>            
	           <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Misc H.E. Inspection Date1:</label>
	                <div class="col-sm-5">
        				<p class="form-control-static">${vehicle.miscHeInsDate1 == null ? '' : vehicle.miscHeInsDate1Formatted}</p>
      		  		</div>
	            </div>
	           <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Misc H.E. Inspection Date2:</label>
	                <div class="col-sm-5">
        				<p class="form-control-static">${vehicle.miscHeInsDate2 == null ? '' : vehicle.miscHeInsDate2Formatted}</p>
      		  		</div>
	            </div>                                                                           
				
			  </form>
			  </div>
				  <div id="documents" class="tab-pane fade">
				  	<table id="claims-table"  class="claimsTable2">
		     		<tr style="border-bottom:1px solid grey;" class="listHeader">
		      		<td width="20%" class="listdata">Document Title</td>
		      		<td width="20%" class="listdata">Document Type</td>
		      		<td width="20%" class="listdata">Date Uploaded</td>
		      		<td class="listdataw"></td>
		      		</tr>
		      		<c:choose>
		      		<c:when test="${fn:length(documents) > 0}">
			      		<c:forEach items="${documents}" var="rule">
		 					<tr style="border-bottom:1px dashed silver;">
		      					<td class="field_content dateTest">${rule.documentTitle}</td>
		      					<td class="field_content"><span class="baseRateData">${rule.typeString}</span></td>
		      					<td class="field_content"><span class="baseRateData">${rule.dateUploadedFormatted}</span></td>
		      					<td align="right" class="field_content">
		      					<button type="button" class="btn btn-xs btn-primary" onclick="window.open('${spath}${rule.viewPath}','_blank');">View</button>		      							      					
		      					</td>
		      				</tr>
		        		</c:forEach>
		        		</c:when>
		        		<c:otherwise>
		        			<tr><td colspan='5' style="color:Red;">No documents found.</td></tr>
		        		</c:otherwise>
		        		</c:choose>
	      		</table>
			  </div>
			  <div class="form-group">        
		      		<div class="col-sm-offset-2 col-sm-10" id="divbuttons">
      					<br />
      					<c:if test = "${contractor.status != 2}">
        					<button type="button" class="btn btn-xs btn-success" onclick="openApproveVeh();">Approve</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-xs btn-danger" onclick="openRejectVeh();">Reject</button>
      					</c:if>
      					<br />
      				</div>
		    	</div>
		  </div>
		</div>
	</div>
	<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"><span id="modaltitle"></span></h4>
      </div>
      <div class="modal-body">
        <p><span id="modaltext"></span></p>
      </div>
      <div class="modal-body2" style="display:none;text-align:center;" id="modalnotes">
      	<p>Notes:</p>
      	<br>
        <textarea class = "form-control" rows = "5" style="width:75%;display: block;margin-left: auto;margin-right: auto;" id="rnotes"></textarea>
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-xs btn-default" onclick="approverejectvehicle();">Ok</button>
        <button type="button" class="btn btn-xs btn-default" data-dismiss="modal">Close</button><input type="hidden" id="trantype">
      </div>
    </div>

  </div>
</div>	
