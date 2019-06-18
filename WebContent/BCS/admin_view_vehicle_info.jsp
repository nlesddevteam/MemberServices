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
<c:set var="countDocs" value="0" />
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrapvalidator.min.js"></script>
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrap-datepicker.min.js"></script>
<script src="includes/js/bcs.js"></script>
<%
String tabs = (String)request.getParameter("tab");
String settab="";
if(tabs != null){
	settab="D";
}
pageContext.setAttribute("settab1", settab);
pageContext.setAttribute("settab1", settab);
Integer contractorid = 0;
if(!(request.getAttribute("contractorid") == null)){
	contractorid=(Integer)request.getAttribute("contractorid");
}
pageContext.setAttribute("pcid", contractorid);
%>
<script type="text/javascript">
$(document).ready(function() {
	//clear spinner on load
	$('#loadingSpinner').css("display","none");
	var date_input=$('#rdate');
	var date_inputi=$('#idate');
	var date_inputfi=$('#fidate');
	var date_inputwi=$('#widate');
	var date_inputfhei1=$('#fheidate');
	var date_inputmhei1=$('#mheidate1');
	var date_inputmhei2=$('#mheidate2');
      var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
      var options={
        format: 'mm/dd/yyyy',
        container: container,
        todayHighlight: true,
        autoclose: true,
      };
      date_input.datepicker(options);
      date_inputi.datepicker(options);
      date_inputfi.datepicker(options);
      date_inputwi.datepicker(options);
      date_inputfhei1.datepicker(options);
      date_inputmhei1.datepicker(options);
      date_inputmhei2.datepicker(options);
      if($("#settab1").val() == "D"){
    	  $('.nav-tabs a:last').tab('show'); 
      }
      if(!($("#hidrc").val() == 0)){
			$("#contractor").val($("#hidrc").val());
			$('#contractor').attr("disabled", true);
	    }
      $('#vmake').on('change',function(){
    	  var thisvalue = $(this).find("option:selected").text();
    	  if(thisvalue=="Other"){
    		  
    		  $("#divother").show();
    	  }else{
    		  $("#vmakeother").val("");
    		  $("#divother").hide();
    	  }
    	});
      	$("#rdate").change(function(){
			checkdate('RDATE');
		});
      	$("#idate").change(function(){
			checkdate('IDATE');
		});
      	$("#fidate").change(function(){
			checkdate('FIDATE');
		});
      	$("#widate").change(function(){
			checkdate('WIDATE');
		});
      	$("#fheidate").change(function(){
			checkdate('FHEIDATE');
		});
      	$("#mheidate1").change(function(){
			checkdate('MHEIDATE1');
		});
      	$("#mheidate2").change(function(){
			checkdate('MHEIDATE2');
		});
      	checkdatefields();
});
</script>
<script>
   			$(document).ready(function () {
    		$('.menuBCS').click(function () {
    		$("#loadingSpinner").css("display","inline").delay(2000).fadeOut();
    		});  
   			});
		</script>
<%pageContext.setAttribute("now", new java.util.Date()); %>    		    
	<div id="printJob">	
	<div class="BCSHeaderText">Vehicle Information for <span style="font-weight:bold;text-transform:capitalize;">Plate #: ${vehicle.vPlateNumber} ( S/N: ${vehicle.vSerialNumber} )</span></div>
		Below you will find the details for the selected vehicle of <span style="text-transform:capitalize;">${vehicle.bcBean.company}</span>. 
			  <br/>
		  <br/>
			<ul class="nav nav-tabs">
    			<li class="active"><a data-toggle="tab" href="#details">Vehicle Details</a></li>
    			<li><a data-toggle="tab" href="#documents">Documents</a></li>
  			</ul>
  			<div class="tab-content">
  			<div id="details" class="tab-pane fade in active">
  			<br/>
	  			<form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="">
	  				<div class="form-group">
		      			<label class="control-label col-sm-2" for="email">Status:</label>
		      			<div class="col-sm-5">
		      			<c:choose>
         				<c:when test = "${vehicle.vStatus eq 1}">
         				
         				<span style="background-color:yellow;color:black;padding:3px;text-transform:uppercase;">&nbsp;NOT YET APPROVED&nbsp;</span>
            				<p class="form-control-static">${ vehicle.statusText}</p>
            				
         				</c:when>
         				<c:when test = "${vehicle.vStatus  eq 2}">
         				<span style="background-color:green;color:white;padding:3px;text-transform:uppercase;">&nbsp;APPROVED&nbsp;</span>
            				<p class="form-control-static">${ vehicle.statusText} by: ${ vehicle.approvedBy} on ${vehicle.dateApprovedFormatted}</p>
            				
         				</c:when>
                  		<c:when test = "${vehicle.vStatus  eq 3}">
                  		<span style="background-color:red;color:white;padding:3px;text-transform:uppercase;">&nbsp;NOT APPROVED&nbsp;</span>
            				<p class="form-control-static">${vehicle.statusText} by: ${vehicle.approvedBy} on ${ vehicle.dateApprovedFormatted} <br/>Notes: ${vehicle.statusNotes}]</p>
         				
         				</c:when>
         				<c:when test = "${vehicle.vStatus  eq 4}">
         					<span style="background-color:black;color:white;padding:3px;text-transform:uppercase;">&nbsp;SUSPENDED&nbsp;</span>
            				<p class="form-control-static">${vehicle.statusText}</p>         				
         				</c:when>
         				<c:otherwise>
         					<span></span>
         				</c:otherwise>
      					</c:choose>
      					</div>
        			</div> 	  	
        			<div class="form-group">		  	
		                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Contractor:</label>
		                <div class="col-sm-5">
		                <select class="form-control" id="contractor" name="contractor"  style="width:auto;">
		                	<option value="-1">Please select contractor</option>
							<c:forEach items="${contractors}" var="rule">
			  					<c:choose>
			  						<c:when test="${rule.company ne null }">
			  							<c:choose>
			  								<c:when test="${ rule.id eq vehicle.contractorId}">
			  									<option value="${rule.id}" SELECTED>${rule.lastName},${rule.firstName}(${rule.company})</option>
			  								</c:when>
			  								<c:otherwise>
			  									<option value="${rule.id}">${rule.lastName},${rule.firstName}(${rule.company})</option>
			  								</c:otherwise>
			  							</c:choose>
			  						</c:when>
			  						<c:otherwise>
			  							<c:choose>
			  								<c:when test="${ rule.id eq vehicle.contractorId}">
			  									<option value="${rule.id}" SELECTED>${rule.lastName},${rule.firstName}</option>
			  								</c:when>
			  								<c:otherwise>
			  									<option value="${rule.id}">${rule.lastName},${rule.firstName}(${rule.company})</option>
			  								</c:otherwise>
			  							</c:choose>
			  						</c:otherwise>
			  					</c:choose>
			  			</c:forEach>
				  		</select>
				  		</div>
	      			</div> 
			   	<div class="form-group">
	                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Vehicle Make:</label><input type="hidden" id="vid" name="vid" value="${vehicle.id}">
	                <input type="hidden" id="settab1" name="settab1" value="${settab1}">
	                <input type="hidden" id="rtype" name="rtype" value="A">
	                <input type="hidden" id="hidfullname" name="hidfullname" value="${vehicle.vPlateNumber} [${vehicle.bcBean.company}]">
	                <input type="hidden" id="hidrc" value="${pcid}">
	                <div class="col-sm-5">
        				<p>
        				<select class="form-control" id="vmake" name="vmake" style="width:auto;">
	                		<option value="-1">Please select make</option>
							<c:forEach var="entry" items="${vmakes}">
								<c:choose>
									<c:when test = "${vehicle.vMake == entry.id }">
										<option value='${entry.id}' SELECTED>${entry.text}</option>
									</c:when>
									<c:otherwise>
										<option value='${entry.id}'>${entry.text}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
			  			</select>
        				</p>
        				<p>
        				<c:choose>
							<c:when test = "${vehicle.makeText == 'Other' }">
								<div id="divother" name="divother">
	                    			<input class="form-control" id="vmakeother" name="vmakeother" type="text" placeholder="Enter other make" value="${vehicle.vMakeOther}">
	                			</div>
							</c:when>
							<c:otherwise>
								<div id="divother" name="divother" style="display:none;">
	                    			<input class="form-control" id="vmakeother" name="vmakeother" type="text" placeholder="Enter other make" value="${vehicle.vMakeOther}">
	                			</div>
							</c:otherwise>
					</c:choose>
					</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Vehicle Model:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="vmodel2" name="vmodel2" type="text" placeholder="Enter model" value="${vehicle.vModel2}">
        				</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Vehicle Year:</label>
	                <div class="col-sm-5">
        				<p>
        				<fmt:formatDate value="${now}" pattern="yyyy" var="startdate"/>
        				<select id="vyear" name="vyear"  class="form-control"  style="width:auto;">
							<option value="-1">Please select year</option>
				            <c:forEach begin="0" end="25" var="val">
				                <c:set var="decr" value="${(startdate+3) - val}"/>
				                <option value="${decr}" ${vehicle.vYear == decr ? "selected='selected'": ''}>${decr}</option>
				            </c:forEach>
						</select>
        				</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Serial Number:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="vserialnumber" name="vserialnumber" type="text" placeholder="Enter serial number" value="${vehicle.vSerialNumber}">
        				</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Plate Number:</label>
	                <div class="col-sm-5">
        				<p><input class="form-control" id="vplatenumber" name="vplatenumber" type="text" placeholder="Enter plate number" value="${vehicle.vPlateNumber}"></p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Vehicle Type:</label>
	                <div class="col-sm-5">
        				<p>
        				<select class="form-control" id="vtype" name="vtype"  style="width:auto;" onchange="getSubDropdownItems();"> 
	                	<option value="-1">Please select type</option>
						<c:forEach var="entry" items="${vtypes}">
							<c:choose>
							<c:when test = "${vehicle.vType == entry.key }">
								<option value='${entry.key}' SELECTED>${entry.value}</option>
							</c:when>
							<c:otherwise>
								<option value='${entry.key}'>${entry.value}</option>
							</c:otherwise>
							</c:choose>
						</c:forEach>
			  		</select>
        				</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Vehicle Size:</label>
	                <div class="col-sm-5">
        				<p>
        				<select class="form-control" id="vsize" name="vsize"  style="width:auto;">
	                	<option value="-1">Please select size</option>
						<c:forEach var="entry" items="${vsizes}">
							<c:choose>
							<c:when test = "${vehicle.vSize == entry.key }">
								<option value='${entry.key}' SELECTED>${entry.value}</option>
							</c:when>
							<c:otherwise>
								<option value='${entry.key}'>${entry.value}</option>
							</c:otherwise>
							</c:choose>
						</c:forEach>
			  		</select>
        				</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Registered Owner:</label>
	                <div class="col-sm-5">
        				<p><input class="form-control" id="vrowner" name="vrowner" type="text" placeholder="Enter registered owner" value="${vehicle.vOwner}"></p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Registration Expiry Date:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="rdate" name="rdate" placeholder="MM/DD/YYY" type="text" 
	                    value="${vehicle.regExpiryDate == null ? '' : vehicle.regExpiryDateFormatted}">
	                    <br />
	                    <div id="divrdate" name="divrdate" style="display:none;">
	                    <span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">&nbsp; Date must be in future and no more than 1 year &nbsp;</span>
	                    </div>
        				</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Insurance Expiry Date:</label> 
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="idate" name="idate" placeholder="MM/DD/YYY" type="text" 
	                    value="${vehicle.insExpiryDate == null ? '' : vehicle.insExpiryDateFormatted}">
	                    <br />
	                    <div id="dividate" name="dividate" style="display:none;">
	                    <span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">&nbsp; Date must be in future and no more than 1 year &nbsp;</span>
	                    </div>
        				</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Insurance Provider:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="insuranceprovider" name="insuranceprovider" type="text" placeholder="Enter insurance provider" value="${vehicle.insuranceProvider}">
        				</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Insurance Policy Number:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="insurancepolicynumber" name="insurancepolicynumber" type="text" placeholder="Enter insurance policy number" value="${vehicle.insurancePolicyNumber}">
        				</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Unit Number:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="unitnumber" name="unitnumber" type="text" placeholder="Enter unit number" value="${vehicle.unitNumber}">
        				</p>
      		  		</div>
	            </div>	            
	           <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Fall Inspection Date:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="fidate" name="fidate" placeholder="MM/DD/YYY" type="text" 
	                    value="${vehicle.fallInsDate == null ? '' : vehicle.fallInsDateFormatted}">
	                    <br />
	                    <div id="divfidate" name="divfidate" style="display:none;">
	                    <span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">&nbsp; Date must be in past and no more than 1 year &nbsp;</span>
	                    </div>
        				</p>
      		  		</div>
	            </div>
	           <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Fall CMVI Certificate #:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="fallcmvi" name="fallcmvi" type="text" placeholder="Enter cmvi certificate" value="${vehicle.fallCMVI}">
        				</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Fall Official Inspection Station:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="fallinsstation" name="fallinsstation" type="text" placeholder="Enter fall official inspection" value="${vehicle.fallInsStation}">
        				</p>
      		  		</div>
	            </div>
 				<div class="form-group">
	                <label class="control-label col-sm-2" for="email">Winter Inspection Date:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="widate" name="widate" placeholder="MM/DD/YYY" type="text" 
	                    value="${vehicle.winterInsDate == null ? '' : vehicle.winterInsDateFormatted}">
	                    <br />
	                    <div id="divwidate" name="divwidate" style="display:none;">
	                    <span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">&nbsp; Date must be in past and no more than 1 year &nbsp;</span>
	                    </div>
        				</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Winter CMVI Certificate #:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="wintercmvi" name="wintercmvi" type="text" placeholder="Enter cmvi certificate" value="${vehicle.winterCMVI}">
        				</p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Winter Official Inspection Station:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="winterinsstation" name="winterinsstation" type="text" placeholder="Enter winter official inspection" value="${vehicle.winterInsStation}">
        				</p>
      		  		</div>
	            </div>  
	           <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Fall H.E. Inspection Date:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="fheidate" name="fheidate" placeholder="MM/DD/YYY" type="text" 
	                    value="${vehicle.fallHeInsDate == null ? '' : vehicle.fallHeInsDateFormatted}">
	                    <br />
	                    <div id="divfheidate" name="divfheidate" style="display:none;">
	                    <span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">&nbsp; Date must be in past and no more than 1 year &nbsp;</span>
	                    </div>
        				</p>
      		  		</div>
	            </div>            
	           <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Misc H.E. Inspection Date1:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="mheidate1" name="mheidate1" placeholder="MM/DD/YYY" type="text" 
	                    value="${vehicle.miscHeInsDate1 == null ? '' : vehicle.miscHeInsDate1Formatted}">
	                    <br />
	                    <div id="divmheidate1" name="divmheidate1" style="display:none;">
	                    <span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">&nbsp; Date must be in past and no more than 1 year &nbsp;</span>
	                    </div>
        				</p>
      		  		</div>
	            </div>
	           <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Misc H.E. Inspection Date2:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="mheidate2" name="mheidate2" placeholder="MM/DD/YYY" type="text" 
	                    value="${vehicle.miscHeInsDate2 == null ? '' : vehicle.miscHeInsDate2Formatted}">
	                    <br />
	                    <div id="divmheidate2" name="divmheidate2" style="display:none;">
	                    <span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">&nbsp; Date must be in past and no more than 1 year &nbsp;</span>
	                    </div>
        				</p>
      		  		</div>
	            </div>                                                                           
				
			  </form>
			  </div>
				  <div id="documents" class="tab-pane fade">
				  
				   <div class="alert alert-danger" id="doc_details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    				<div class="alert alert-success" id="doc_details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
				  	<p>
				  	<div align="right"><button type="button" class="btn btn-xs btn-success" onclick="openaddnewdialogadmin('A');">Add Document</button></div>			  	
				  	<br/>
				   <div id="BCS-Search">
				  	<table id="BCS-table" width="100%" class="BCSTable">
		     		<thead>
		     		<tr style="border-bottom:1px solid silver;" class="listHeader">
		      		<th width="20%" class="listdata">Document Title</th>
		      		<th width="20%" class="listdata">Document Type</th>
		      		<th width="20%" class="listdata">Date Uploaded</th>
		      		<th class="listdata">Options</th>
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
		      					<td align="right" class="field_content">
		      						<button type="button" class="btn btn-xs btn-primary menuBCS" onclick="window.open('${spath}${rule.viewPath}','_blank');">View</button>
		      					<button type="button" class="btn btn-xs btn-danger" onclick="opendeletedocdialogadmin('${rule.documentTitle}','${rule.id}','A');">Del</button>
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
			  <div class="form-group">        
		      		<div class="col-sm-offset-2 col-sm-10" id="divbuttons">
		      		
		      		<div class="alert alert-danger" id="vehicleerrormessage" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    				<div class="alert alert-success" id="vehiclesuccessmessage" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
		      		<div class="col-sm-offset-2 col-sm-10" id="divbuttons">
      					<br />
      					<button type="button" class="btn btn-xs btn-primary" id="submitupdate" name="submitupdate" onclick="confirmVehicleFields('A');">Update Information</button>
      					<c:if test = "${vehicle.vStatus != 2}">
      						<esd:SecurityAccessRequired permissions="BCS-APPROVE-REJECT">
        					<button type="button" class="btn btn-xs btn-success" onclick="openApproveVeh();">Approve</button>&nbsp;<button type="button" class="btn btn-xs btn-danger" onclick="openRejectVeh();">Reject</button>
      						</esd:SecurityAccessRequired>
      					</c:if>
      					<c:if test = "${vehicle.vStatus eq 2}">
      						<esd:SecurityAccessRequired permissions="BCS-SUSPEND-UNSUSPEND">
        						<button type="button" class="btn btn-xs btn-warning" onclick="openSuspendVeh();">Suspend</button>
        					</esd:SecurityAccessRequired>
      					</c:if>
      					<br />
      				</div>
		    	</div>
		  </div>
		
	</div>
	
	<c:choose>
      	<c:when test="${countDocs >0 }">
      		<script>$('#doc_details_success_message').html("There are <b>${countDocs}</b> vehicle document(s).").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#doc_details_error_message').html("Sorry, there are no vehicle documents at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>
	
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
      		<button type="button" class="btn btn-xs btn-success" onclick="approverejectvehicle();">Ok</button>
        <button type="button" class="btn btn-xs btn-danger" data-dismiss="modal">Close</button><input type="hidden" id="trantype">
      </div>
    </div>

  </div>
</div>
	<div id="myModalDoc" class="modal">
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
                    	<option value="-1">Please Select Document Type</option>
						<c:forEach var="entry" items="${dtypes}">
							<option value='${entry.key}'>${entry.value}</option>
						</c:forEach>
                    </select>
                    </p>
                    <p class="text-warning" id="title2">Document Title:</p>
                    <p>
                    <input class="form-control" id="documenttitle" name="documenttitle" placeholder="document title" type="text">
                    </p>
 		    		<p class="text-warning" id="title3">Document:</p>
 		    		<p>
 		    		<input type="file" id="documentname" name="documentname" accept="application/pdf">(PDF file format only)					
 		    		</p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-success"  id="buttonleftad"></button>
                    <button type="button" class="btn btn-xs btn-danger" data-dismiss="modal" id="buttonrightad"></button>
                </div>
            </div>
   		</div>
   	</div>
   	<div id="myModalDocD" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitledd">Delete Document</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1dd"><span id="spantitle1dd" name="spantitle1dd"></span></p>
                    <p class="text-warning" id="title2dd"><span id="spantitle2dd" name="spantitle2dd"></span></p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default"  id="buttonleftdd"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonrightdd"></button>
                </div>
            </div>
   		</div>
   	</div>

   	