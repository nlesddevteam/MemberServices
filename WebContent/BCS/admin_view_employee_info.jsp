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
 <c:set var="countTrain" value="0" /><c:set var="countLetter" value="0" />
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
	
	settab=tabs;
}
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
    		    //$("#claims-table tr:even").not(':first').css("background-color", "#FFFFFF");
    		    //$("#claims-table tr:odd").css("background-color", "#E3F1E6");
    		    $('#province').val('${employee.province}');
    		    var date_inputdle=$('#dlexpirydate');
    		    var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
      	      	var options={
      	        	format: 'mm/dd/yyyy',
      	        	container: container,
      	        	todayHighlight: true,
      	        	autoclose: true,
      	      	};
				date_inputdle.datepicker(options);
				var date_inputda=$('#darundate');
				date_inputda.datepicker(options);
				var date_inputfa=$('#faexpirydate');
				date_inputfa.datepicker(options);
				var date_inputprc=$('#prcvsqdate');
				date_inputprc.datepicker(options);
				var date_inputpcc=$('#pccdate');
				date_inputpcc.datepicker(options);
				var date_inputsca=$('#scadate');
				date_inputsca.datepicker(options);
				var date_inputbd=$('#birthdate');
				date_inputbd.datepicker(options);
				var date_training=$('#trainingdate');
				date_training.datepicker(options);
				var date_expiry=$('#expirydate');
				date_expiry.datepicker(options);
				var date_codexpiry=$('#coddate');
				date_codexpiry.datepicker(options);
				$('.zoomimg').hover(function() {
				    $(this).css("cursor", "pointer");
				    $(this).animate({
				        width: "500px"				       
				    }, 'fast');

				}, function() {
				    $(this).animate({
				        width: "100px"
				    }, 'fast');

				});
				
				
				
				
				if($("#settab1").val() == "L"){
	      	    	  $('.nav-tabs a:last').tab('show'); 
	      	    }
				if($("#settab1").val() == "T"){
	      	    	  $('#training').tab('show');
	      	    	$('.nav-tabs li:eq(2) a').tab('show')
	      	    }
				if(!($("#hidrc").val() == 0)){
					$("#contractor").val($("#hidrc").val());
					//$('#contractor').attr("disabled", true);
	      	    }
				$("#dlexpirydate").change(function(){
					checkdate('DLEXP');
				});
				$("#faexpirydate").change(function(){
					checkdate('FAEXP');
				});
				$("#scadate").change(function(){
					checkdate('SCADATE');
				});
				$("#darundate").change(function(){
					checkdate('DARUNDATE');
				});
				$("#prcvsqdate").change(function(){
					checkdate('PRCVSQDATE');
				});
				
				
				

});
		</script>
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">	
		      <div class="alert alert-danger" id="body_error_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    	      <div class="alert alert-success" id="body_success_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
			
			  <div class="BCSHeaderText">Employee Information for <span style="font-weight:bold;text-transform:capitalize;">${employee.firstName} ${employee.lastName}</span></div>
		Below you will find the details for employee <span style="text-transform:capitalize;">${employee.firstName} ${employee.lastName}</span> of <span style="text-transform:capitalize;">${employee.city}</span>. 
			  <br/>
			  <br />
			  <ul class="nav nav-tabs">
	    		<li class="active"><a data-toggle="tab" href="#details">Employee Details</a></li>
	    		<li><a data-toggle="tab" href="#documents">Documents</a></li>
	    		<li><a data-toggle="tab" href="#training">Training</a></li>
	    		<li><a data-toggle="tab" href="#letters">Letters On File</a></li>
	  		  </ul>
	  		  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="" enctype='multipart/form-data'>
	  			<div class="tab-content">
	  	<div id="details" class="tab-pane fade in active">
	  				<div class="form-group">
		      			<br/>
		      			<label class="control-label col-sm-2" for="email">Status:</label>
		      			<div class="col-sm-5">
			      		<br/>
			      		<c:choose>
	         				<c:when test = "${employee.status eq 1}">	         				
	         				<span style="background-color:yellow;color:black;padding:3px;text-transform:uppercase;">&nbsp;NOT YET SUBMITTED&nbsp;</span>	         				
	            				<p class="form-control-static">${ employee.statusText}</p>
	            				
	         				</c:when>
	         				<c:when test = "${employee.status eq 2}">
	         				
	         				<span style="background-color:green;color:White;padding:3px;text-transform:uppercase;">&nbsp;APPROVED&nbsp;</span>
	            				<p class="form-control-static">${ employee.statusText}  on ${employee.dateApprovedFormatted}</p>
	            				
	         				</c:when>
	                  		<c:when test = "${employee.status eq 3}">
	                  		
	                  		<span style="background-color:Red;color:White;padding:3px;text-transform:uppercase;">&nbsp;NOT APPROVED&nbsp;</span>
	            				<p class="form-control-static">${employee.statusText} on ${ employee.dateApprovedFormatted} 
	            				<br/>Notes: ${employee.statusNotes}</p>
	         				
	         				</c:when>
	         				<c:when test = "${employee.status eq 4}">
	         					<span style="background-color:black;color:White;padding:3px;text-transform:uppercase;">&nbsp;SUSPENDED&nbsp;</span>
	         				</c:when>
	         				<c:when test = "${employee.status eq 6}">
	         					<span style="background-color:blue;color:White;padding:3px;text-transform:uppercase;">&nbsp;SUBMITTED FOR APPROVAL&nbsp;</span>
	         				</c:when>	         				
	         				<c:otherwise>
	            				<span></span>
	            			</c:otherwise>
	      				</c:choose>
	      				</div>
        			</div>
        			<div class="form-group">		  	
		                <label class="control-label col-sm-3" for="email"><img src='includes/css/images/asterisk-small.png'/>Contractor:</label>
		                <div class="col-sm-5">
		                <select class="form-control" id="contractor" name="contractor"  style="width:auto;">
		                	<option value="-1">Please select contractor</option>
							<c:forEach items="${contractors}" var="rule">
			  					<c:choose>
			  						<c:when test="${rule.company ne null }">
			  							<c:choose>
			  								<c:when test="${ rule.id eq employee.contractorId}">
			  									<option value="${rule.id}" SELECTED>${rule.lastName},${rule.firstName}(${rule.company})</option>
			  								</c:when>
			  								<c:otherwise>
			  									<option value="${rule.id}">${rule.lastName},${rule.firstName}(${rule.company})</option>
			  								</c:otherwise>
			  							</c:choose>
			  						</c:when>
			  						<c:otherwise>
			  							<c:choose>
			  								<c:when test="${ rule.id eq employee.contractorId}">
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
                <label class="control-label col-sm-3" for="email"><img src='includes/css/images/asterisk-small.png'/>Employee Position:</label>
                <div class="col-sm-5">
                <select class="form-control" id="employeeposition" name="employeeposition"  style="width:auto;">
                	<option value="-1">Please select position</option>
					<c:forEach var="entry" items="${positions}">
						<c:choose>
						<c:when test = "${employee.employeePosition == entry.key }">
							<option value='${entry.key}' SELECTED>${entry.value}</option>
						</c:when>
						<c:otherwise>
							<option value='${entry.key}'>${entry.value}</option>
						</c:otherwise>
						</c:choose>
					</c:forEach>
		  		</select>
		  		</div>
	      </div>		  
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email"><img src='includes/css/images/asterisk-small.png'/>First Name:</label><input type="hidden" id="cid" name="cid" value="${employee.id}">
		      <input type="hidden" id="hidrc" value="${pcid}">
		      <input type="hidden" id="settab1" name="settab1" value="${settab1}">
		      <div class="col-sm-5">
		        <input class="form-control" id="firstname" name="firstname" type="text" placeholder="Enter first name" value="${employee.firstName}">
		      </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email"><img src='includes/css/images/asterisk-small.png'/>Last Name:</label><input type="hidden" id="hidfullname" name="hidfullname" value="${employee.lastName},${employee.firstName}">
		      <div class="col-sm-5">
		        <input class="form-control" id="lastname" name="lastname" type="text" placeholder="Enter last name"  value="${employee.lastName}">
		      </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Middle Name:</label>
		      <div class="col-sm-5">
		        <input class="form-control" id="middlename" name="middlename" type="text" placeholder="Enter middle name" value="${employee.middleName}">
		      </div>
		    </div>
		    <div class="form-group">
	                <label class="control-label col-sm-3" for="email"><img src='includes/css/images/asterisk-small.png'/>Date of Birth:</label> 
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="birthdate" name="birthdate" placeholder="MM/DD/YYY" type="text" 
	                    value="${employee.birthDate == null ? '' : employee.birthDateFormatted}">
        				</p>
      		  		</div>
	            </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email"><img src='includes/css/images/asterisk-small.png'/>Email:</label>
		      <div class="col-sm-5">
		        <input class="form-control" id="email" name="email" type="text" placeholder="Enter email" value="${employee.email}">
		      </div>
		    </div>
		   	<div class="form-group">
		      <label class="control-label col-sm-3" for="email"><img src='includes/css/images/asterisk-small.png'/>Address 1:</label>
		      <div class="col-sm-5">
		        <input class="form-control" id="address1" name="address1" type="text" placeholder="Enter address 1" value="${employee.address1}">
		      </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Address 2:</label>
		      <div class="col-sm-5">
		        <input class="form-control" id="address2" name="address2" type="text" placeholder="Enter address 2" value="${employee.address2}">
		      </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email"><img src='includes/css/images/asterisk-small.png'/>Town/City:</label>
		      <div class="col-sm-5">
		        <input class="form-control" id="city" name="city" type="text" placeholder="Enter city" value="${employee.city}">
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email"><img src='includes/css/images/asterisk-small.png'/>Province:</label>
		      <div class="col-sm-5">
		        <select class="form-control"  id="province" name="province">
					<option value=" ">Select province</option>
					<option value="AB">Alberta</option>
					<option value="BC">British Columbia</option>
					<option value="MB">Manitoba</option>
					<option value="NB">New Brunswick</option>
					<option value="NL">Newfoundland and Labrador</option>
					<option value="NS">Nova Scotia</option>
					<option value="ON">Ontario</option>
					<option value="PE">Prince Edward Island</option>
					<option value="QC">Quebec</option>
					<option value="SK">Saskatchewan</option>
					<option value="NT">Northwest Territories</option>
					<option value="NU">Nunavut</option>
					<option value="YT">Yukon</option>
				</select>
				</div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-3" for="email"><img src='includes/css/images/asterisk-small.png'/>Postal Code:</label>
		      <div class="col-sm-5">
		        <input class="form-control" id="postalcode" name="postalcode" type="text" placeholder="Enter postal code" value="${employee.postalCode}">
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Home Phone:</label>
		      <div class="col-sm-5">
		        <input class="form-control" id="homephone" name="homephone" type="text" placeholder="Enter home phone" value="${employee.homePhone}">
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Cell Phone:</label>
		      <div class="col-sm-5">
		        <input class="form-control" id="cellphone" name="cellphone" type="text" placeholder="Enter cell phone" value="${employee.cellPhone}">
		      </div>
		    </div>
		    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email"><img src='includes/css/images/asterisk-small.png'/>Start Date With Contractor</label>
		      <div class="col-sm-5">
		      <div class="form-inline">
		      		<select id="vmonth" name="vmonth"  class="form-control"  style="width:auto;" onchange="adjustServiceTime();">
						<option value="-1">Please select month</option>
			            <c:forEach begin="1" end="12" var="val">
			                <c:set var="decr" value="${val}"/>
			                <option value="${decr}" ${employee.startMonth == decr ? "selected='selected'": ''}>${decr}</option>
			        	</c:forEach>
			        </select>
	        	    <fmt:formatDate value="${now}" pattern="yyyy" var="startdate"/>
                	<select id="vyear" name="vyear"  class="form-control"  style="width:auto;" onchange="adjustServiceTime();">
						<option value="-1">Please select year</option>
			            <c:forEach begin="0" end="46" var="val">
			                <c:set var="decr" value="${startdate - val}"/>
			                <option value="${decr}" ${employee.startYear == decr ? "selected='selected'": ''}>${decr}</option>
			            </c:forEach>
					</select>
				</div>
				</div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Years of Continuous Service</label>
		      <div class="col-sm-5">
		        <input class="form-control" id="continuousservice" name="continuousservice" type="text" 
		        placeholder="Enter years of continuous service" value="${employee.continuousService}" readonly>
		      </div>
		    </div>
		</div>
		<div id="documents" class="tab-pane fade" style="font-size:11px;">
		<br />
				   	<span style="font-size:14px;color:Grey;margin-bottom:10px;">Driver Licence Information:</span>
			<div class="form-group">
		      <label class="control-label col-sm-3" for="email">Licence #:</label>
		      <div class="col-sm-5">
		        <input class="form-control" id="dlnumber" name="dlnumber" type="text" placeholder="Enter Driver Licence Number" value="${employee.dlNumber}">
		      </div>
		    </div>
		    <div class="form-group">
				<label class="control-label col-sm-3" for="email"></label>
		      	<div class="col-sm-5">
		      		<div class="row">
		      			<div class="col-sm-5">
		        			<button type='button' onclick="checkdl()">Check Licence Number</button>
		        		</div>
		        		<div class="col-sm-5">
		        			<div  id="dlvalid" style="display:none;">
  								<span id="dlspan"style="color:White;background-color:Red;padding:2px;text-transform:uppercase;"></span>
							</div>
		        		</div>
		        	</div>
		        </div>

		    </div>
	        <div class="form-group">
	                <label class="control-label col-sm-3" for="email">Expiry Date:</label> 
	                <div class="col-sm-5">
	                    <input class="form-control" id="dlexpirydate" name="dlexpirydate" placeholder="MM/DD/YYYY" type="text" 
	                    value="${employee.dlExpiryDate == null ? '' : employee.dlExpiryDateFormatted}">
	                    <br />
	                    <div id="divdlexp" name="divdlexp" style="display:none;">
	                    <span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">&nbsp; Date must be in future and no more than 5 years &nbsp;</span>
	                    </div>
	                </div>
	       </div>
	       <div class="form-group">
                <label class="control-label col-sm-3" for="email">Class:</label>
                <div class="col-sm-5">
		      	<div class="form-inline">
                <select class="form-control" id="dlclass" name="dlclass"  style="width:auto;">
 					<option value="-1">Please select position</option>
					<c:forEach var="entry" items="${lclasses}">
						<c:choose>
						<c:when test = "${employee.dlClass == entry.key }">
							<option value='${entry.key}' SELECTED>${entry.value}</option>
						</c:when>
						<c:otherwise>
							<option value='${entry.key}'>${entry.value}</option>
						</c:otherwise>
						</c:choose>
					</c:forEach>
		  		</select>
		  		<label class="control-label" for="email">Note: Select the lowest class of licence the driver has</label>
		  		</div>
		  		</div>
		  </div>
           <div class="form-group">
                <label class="control-label col-sm-3" for="email">Licence Image Front:</label>  
                <div class="col-sm-5">
				
				<c:choose>
	                	<c:when test = "${employee.dlFront != null}">
	                	<span style="color:White;background-color:Green;padding:2px;text-transform:uppercase;">&nbsp; A front image is currently on file &nbsp;</span>
	                	<br/><br/><a href="${spath}${dpath}${employee.dlFront}" title="Click to open"  target="_blank">Current Document (${employee.dlFront})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFile('1','${employee.id}','${employee.dlFront}');">Delete File</button>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','1');">View History</button>
	                	<br/><br/>To Update	 
	                	</c:when>
	                	<c:otherwise>
	                		<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','1');">View History</button>
	                		<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">No file currently available</span><br/><br/>To Add	 
	                	</c:otherwise>
                	</c:choose>   
                    document, choose file below to upload:
                	<br/><br/>                
					<input type="file" id="dlfront" name="dlfront" accept="application/pdf">
					(PDF file format only)<br/>
				
				
                </div>                
	       </div>
           <div class="form-group">
                <label class="control-label col-sm-3" for="email">Licence Image Back:</label>  
                <div class="col-sm-5">
				
				<c:choose>
	                	<c:when test = "${employee.dlBack != null}">
	                	<span style="color:White;background-color:Green;padding:2px;text-transform:uppercase;">&nbsp; A back image is currently on file &nbsp;</span>
	                	<br/><br/><a href="${spath}${dpath}${employee.dlBack}" title="Click to open"  target="_blank">Current Document (${employee.dlBack})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFile('2','${employee.id}','${employee.dlBack}');">Delete File</button>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','2');">View History</button>
	                	<br/><br/>To Update	 
	                	</c:when>
	                	<c:otherwise>
	                	<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','2');">View History</button>
	                		<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">No file currently available</span><br/><br/>To Add	 
	                	</c:otherwise>
                	</c:choose>   
                    document, choose file below to upload:
                	<br/><br/>                
					<input type="file" id="dlback" name="dlback" accept="application/pdf">
					(PDF file format only)<br/>
				
				
                </div>                
	       </div>
            <img src="includes/img/bar.png" height=1 width=100%><br/>
				   	<span style="font-size:14px;color:Grey;margin-bottom:10px;">Driver Abstract Information</span>
	       
       	   
<div class="form-group">
                <label class="control-label col-sm-3" for="email">Run Date:</label> 
                <div class="col-sm-5">
                    <input class="form-control" id="darundate" name="darundate" placeholder="MM/DD/YYYY" type="text" value="${employee.daRunDate == null ? '' : employee.daRunDateFormatted}">
                		<br />
	                    <div id="divdarundate" name="divdarundate" style="display:none;">
	                    <span id="spandarundate" style="color:White;background-color:Red;padding:2px;text-transform:uppercase;"></span>
	                    </div>
                </div>
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-3" for="email">File:</label> 
                <div class="col-sm-5">
					<c:choose>
	                	<c:when test = "${employee.daDocument != null}">
		                	<span style="color:White;background-color:Green;padding:2px;text-transform:uppercase;">&nbsp; A document is currently on file &nbsp;</span> 
		                	<br/><br/><a href="${spath}${dpath}${employee.daDocument}" title="Click to open" target="_blank">Current Document (${employee.daDocument})</a>
		                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFile('3','${employee.id}','${employee.daDocument}');">Delete File</button>
	                		&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','3');">View History</button>
		                	<br/><br/>To Update
	                	</c:when>
	                	<c:otherwise>
	                		<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','3');">View History</button>
	                		<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">No file currently available</span> <br/><br/>To Add
	                	</c:otherwise>
                	</c:choose>
                   document, choose file below to upload:
                	<br/><br/>
                	<input type="file" id="dadocument" name="dadocument" accept="application/pdf">
 		    		(PDF file format only)<br/>
	       		</div>
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-3" for="email">Convictions:</label>
                <div class="col-sm-5"> 
                <label class="radio-inline"><input type="radio" name="daconvictions" value='Y' ${employee.daConvictions == 'Y' ? 'checked=\'checked\'' : ''}>Yes</label>
				<label class="radio-inline"><input type="radio" name="daconvictions" value='N' ${employee.daConvictions == 'N' ? 'checked=\'checked\'' : ''}>No</label>
				</div>
	       </div>
	       <div class="form-group">
                <label class="control-label col-sm-3" for="email">Driver Abstract Suspensions:</label>
                <div class="col-sm-5"> 
                <label class="radio-inline"><input type="radio" name="dasuspensions" value='Y' ${employee.daSuspensions == 'Y' ? 'checked=\'checked\'' : ''}>Yes</label>
				<label class="radio-inline"><input type="radio" name="dasuspensions" value='N' ${employee.daSuspensions == 'N' ? 'checked=\'checked\'' : ''}>No</label>
				</div>
	       </div> 
	             	  <div class="form-group">
                <label class="control-label col-sm-3" for="email">Driver Abstract Accidents:</label>
                <div class="col-sm-5"> 
                <label class="radio-inline"><input type="radio" name="daaccidents" value='Y' ${employee.daAccidents == 'Y' ? 'checked=\'checked\'' : ''}>Yes</label>
				<label class="radio-inline"><input type="radio" name="daaccidents" value='N' ${employee.daAccidents == 'N' ? 'checked=\'checked\'' : ''}>No</label>
				</div>
	       </div> 
	       <img src="includes/img/bar.png" height=1 width=100%><br/>
				   	<span style="font-size:14px;color:Grey;margin-bottom:10px;">First Aid/Epipen Training</span>
<div class="form-group">
                <label class="control-label col-sm-3" for="email">Expiry Date:</label> 
                <div class="col-sm-5">
                    <input class="form-control" id="faexpirydate" name="faexpirydate" placeholder="MM/DD/YYYY" type="text" 
                    value="${employee.faExpiryDate == null ? '' : employee.faExpiryDateFormatted}">
                    	<br />
	                    <div id="divfaexp" name="divfaexp" style="display:none;">
	                    <span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">&nbsp; Date must be in future and no more than 3 years &nbsp;</span>
	                    </div>
                </div>
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-3" for="email">Certificate File:</label> 
                <div class="col-sm-5">
				
				<c:choose>
	                	<c:when test = "${employee.faDocument != null}">
	                	<span style="color:White;background-color:Green;padding:2px;text-transform:uppercase;">&nbsp; A certificate is currently on file &nbsp;</span>
	                	<br/><br/><a href="${spath}${dpath}${employee.faDocument}" title="Click to open"  target="_blank">Current Document (${employee.faDocument})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFile('4','${employee.id}','${employee.faDocument}');">Delete File</button>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','4');">View History</button>
	                	<br/><br/>To Update	 
	                	</c:when>
	                	<c:otherwise>
	                	<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','4');">View History</button>
	                		<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">No file currently available</span><br/><br/>To Add	 
	                	</c:otherwise>
                	</c:choose>   
                    document, choose file below to upload:
                	<br/><br/>                
					<input type="file" id="fadocument" name="fadocument" accept="application/pdf">
					(PDF file format only)<br/>
				
				
                </div>                
	       </div>
	       <img src="includes/img/bar.png" height=1 width=100%><br/>
				   	<span style="font-size:14px;color:Grey;margin-bottom:10px;">Police Records / Vunerable Sector Checks</span>
	       
       	   <div class="form-group">
                <label class="control-label col-sm-3" for="email">PRC/VSQ Date:</label> 
                <div class="col-sm-5">
                    <input class="form-control" id="prcvsqdate" name="prcvsqdate" placeholder="MM/DD/YYYY" type="text" 
                    value="${employee.prcvsqDate == null ? '' : employee.prcvsqDateFormatted}">
                    <br />
	                    <div id="divprcvsqdate" name="divprcvsqdate" style="display:none;">
	                    <span id="spanprcvsqdate" style="color:White;background-color:Red;padding:2px;text-transform:uppercase;"></span>
	                    </div>
                </div>
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-3" for="email">PRC/VSQ File:</label> 
                <div class="col-sm-5">
					<c:choose>
	                	<c:when test = "${employee.prcvsqDocument != null}">
	                	<span style="color:White;background-color:Green;padding:2px;text-transform:uppercase;">&nbsp; PRC/VSQ currently on file &nbsp;</span>
	                	<br/><br/><a href="${spath}${dpath}${employee.prcvsqDocument}" title="Click to open" target="_blank">Current Document (${employee.prcvsqDocument})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFile('5','${employee.id}','${employee.prcvsqDocument}');">Delete File</button>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','5');">View History</button>
	                	<br/><br/>To Update	               	
	                	</c:when>
	                	<c:otherwise>
	                	<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','5');">View History</button>
	                		<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">No file currently available</span> <br/><br/>To Add
	                	</c:otherwise>
                	</c:choose>  
                 	document, choose file below to upload:
                	<br/><br/>                
					<input type="file" id="prcvsqdocument" name="prcvsqdocument" accept="application/pdf">
					(PDF file format only)<br/>
                </div>                
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-3" for="email">Findings of Guilt:</label>
                <div class="col-sm-5"> 
                <label class="radio-inline"><input type="radio" name="findingsofguilt" value='Y' ${employee.findingsOfGuilt == 'Y' ? 'checked=\'checked\'' : ''}>Yes</label>
				<label class="radio-inline"><input type="radio" name="findingsofguilt" value='N' ${employee.findingsOfGuilt== 'N' ? 'checked=\'checked\'' : ''}>No</label>
				</div>
	       </div>
       	   <div class="form-group">
                <label class="control-label col-sm-3" for="email">Provincial Court Check Date:</label> 
                <div class="col-sm-5">
                    <input class="form-control" id="pccdate" name="pccdate" placeholder="MM/DD/YYYY" type="text" 
                    value="${employee.pccDate == null ? '' : employee.pccDateFormatted}">
                </div>
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-3" for="email">Provincial Court Check File:</label> 
                <div class="col-sm-5">
					<c:choose>
	                	<c:when test = "${employee.pccDocument != null}">
	                	<span style="color:White;background-color:Green;padding:2px;text-transform:uppercase;">&nbsp; A document is currently on file &nbsp;</span>
	                	<br/><br/><a href="${spath}${dpath}${employee.pccDocument}" title="Click to open" target="_blank">Current Document (${employee.pccDocument})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFile('6','${employee.id}','${employee.pccDocument}');">Delete File</button>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','6');">View History</button>
	                	<br/><br/>To Update		               	
	                	</c:when>
	                	<c:otherwise>
	                		<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','6');">View History</button>
	                		<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">No file currently available</span> <br/><br/>To Add
	                	</c:otherwise>
                	</c:choose>  
                    document, choose file below to upload:
                	<br/><br/>             
                    <input type="file" id="pccdocument" name="pccdocument" accept="application/pdf">
					(PDF file format only)<br/>
				</div>                
	       </div>
	       <img src="includes/img/bar.png" height=1 width=100%><br/>
				   	<span style="font-size:14px;color:Grey;margin-bottom:10px;">Criminal Offence Declaration</span>
	       
	       <div class="form-group">
                <label class="control-label col-sm-3" for="email">Expiry Date:</label> 
                <div class="col-sm-9">
                    <input class="form-control" id="coddate" name="coddate" placeholder="MM/DD/YYYY" type="text" 
                    value="${employee.codExpiryDate == null ? '' : employee.codExpiryDateFormatted}">
                    <br />
	                    <div id="divscadate" name="divscadate" style="display:none;">
	                    <span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">&nbsp; Criminal Offence Declaration has expired&nbsp;</span>
	                    </div>
                </div>
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-3" for="email">Criminal Offence Declaration File:</label> 
                <div class="col-sm-5">
					<c:choose>
	                	<c:when test = "${employee.codDocument != null}">
	                	<span style="color:White;background-color:Green;padding:2px;text-transform:uppercase;">&nbsp; A document is currently on file &nbsp;</span>
	                	<br/><br/><a href="${spath}${dpath}${employee.codDocument}" title="Click to open" target="_blank">Current Document (${employee.codDocument})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFile('15','${employee.id}','${employee.codDocument}');">Delete File</button>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','15');">View History</button>
	                	<br/><br/>To Update		               	
	                	</c:when>
	                	<c:otherwise>
	                	<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','15');">View History</button>
	                		<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">No file currently available</span> <br/><br/>To Add
	                	</c:otherwise>
                	</c:choose> 
                     document, choose file below to upload:
                	<br/><br/>             
                
					<input type="file" id="coddocument" name="coddocument" accept="application/pdf">
					(PDF file format only)<br/>
                </div>                
	       </div>
	       <img src="includes/img/bar.png" height=1 width=100%><br/>
			<span style="font-size:14px;color:Grey;margin-bottom:10px;">Confidentiality Agreement</span>
	       
	       <div class="form-group">
                <label class="control-label col-sm-3" for="email">Signed Date:</label> 
                <div class="col-sm-9">
                    <input class="form-control" id="scadate" name="scadate" placeholder="MM/DD/YYYY" type="text" 
                    value="${employee.scaDate == null ? '' : employee.scaDateFormatted}">
                    <br />
	                    <div id="divscadate" name="divscadate" style="display:none;">
	                    <span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">&nbsp; Date must not be in future&nbsp;</span>
	                    </div>
                </div>
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-3" for="email">Signed File:</label> 
                <div class="col-sm-5">
					<c:choose>
	                	<c:when test = "${employee.scaDocument != null}">
	                	<span style="color:White;background-color:Green;padding:2px;text-transform:uppercase;">&nbsp; A document is currently on file &nbsp;</span>
	                	<br/><br/><a href="${spath}${dpath}${employee.scaDocument}" title="Click to open" target="_blank">Current Document (${employee.scaDocument})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFile('7','${employee.id}','${employee.scaDocument}');">Delete File</button>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','7');">View History</button>
	                	<br/><br/>To Update		               	
	                	</c:when>
	                	<c:otherwise>
	                	<button type='button' class='btn btn-xs btn-warning' onclick="getFileHistoryAjax('${employee.id}','7');">View History</button>
	                		<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">No file currently available</span> <br/><br/>To Add
	                	</c:otherwise>
                	</c:choose> 
                     document, choose file below to upload:
                	<br/><br/>             
                
					<input type="file" id="scadocument" name="scadocument" accept="application/pdf">
					(PDF file format only)<br/>
                </div>                
	       </div>	        	       	       	        
		</div>
		<div id="training" class="tab-pane fade">
				  <div class="alert alert-danger" id="training_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    				<div class="alert alert-success" id="training_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
				  	<p>
				  	<div align="right"><button type="button" class="btn btn-xs btn-success" onclick="openaddnewdialogtradmin();">Add Training Doc</button></div>			  	
				  	<br/>
				   
				  <div id="BCS-Search">
				 	
				  	<table id="BCS-table1" width="100%" class="BCSTable">
		     		<thead>
		     		<tr style="border-bottom:1px solid grey;" class="listHeader">
		      		<th width="20%" class="listdata">Training Type</th>
		      		<th width="15%" class="listdata">Training Date</th>
		      		<th width="15%" class="listdata">Expiry Date</th>
		      		<th width="40%" class="listdata">Notes</th>
		      		<th width="10%" class="listdata">Options</th>
		      		</tr>
		      		</thead>
		      		<tbody>
					<c:choose>
		      		<c:when test="${fn:length(training) > 0}">
			      		<c:forEach items="${training}" var="rule">
			      		<c:set var="countTrain" value="${countTrain + 1}" />
		 					<tr style="border-bottom:1px solid silver;">
		      					<td class="field_content">${rule.trainingTypeString}</td>
		      					<td class="field_content">${rule.trainingDateFormatted}</td>
		      					<td class="field_content">${rule.expiryDateFormatted}</td>
		      					<td class="field_content">${rule.notes}</td>
		      					<td align="right" class="field_content">
		      					<c:choose>
		      						<c:when test="${rule.tDocument eq null }">
		      							<button type="button" class="btn btn-xs btn-primary" onclick="ajaxGetTrainingById('${rule.pk}');">Edit</button>
		      							<button type="button" class="btn btn-xs btn-info" disabled>Doc</button>
		      							<button type="button" class="btn btn-xs btn-danger" onclick="opendeletetradialogadmin('${rule.trainingTypeString}','${rule.pk}');">Del</button>
		      						</c:when>
		      						<c:otherwise>
		      							<button type="button" class="btn btn-xs btn-primary" onclick="ajaxGetTrainingById('${rule.pk}');">Edit</button>
		      							<button type="button" class="btn btn-xs btn-info" onclick="window.open('${spath}${rule.viewPath}','_blank');">Doc</button>
		      				   			<button type="button" class="btn btn-xs btn-danger" onclick="opendeletetradialogadmin('${rule.trainingTypeString}','${rule.pk}');">Del</button>
		      						</c:otherwise>
		      					</c:choose>
		      					</td>
		      				</tr>
		        		</c:forEach>
		        		</c:when>
		        		<c:otherwise>
		        			<tr><td colspan='4' style="color:Red;">No training found.</td></tr>
		        		</c:otherwise>
		        		</c:choose>		      		
		        	</tbody>	
	      		</table>
	      			      		 <c:choose>
      	<c:when test="${countTrain >0 }">
      		<script>$('#training_success_message').html("There are <b>${countTrain}</b> training docs on file.").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-table1').css("display","none");$('#training_error_message').html("Sorry, there are no training docs currently on file for this employee.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>
      	</div>
	    </div>
		<div id="letters" class="tab-pane fade">
				  		<div class="alert alert-danger" id="letters_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    				<div class="alert alert-success" id="letters_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
					<p>
					<div align="right"><button type="button" class="btn btn-xs btn-success" onclick="openaddnewdialoglet('E');">Add Letter</button></div>
					<br/>
				  <div id="BCS-Search">
				  <table id="BCS-table2" width="100%" class="BCSTable">
		     		<thead>
		     		<tr style="border-bottom:1px solid grey;" class="listHeader">
		      		<th width="40%" class="listdata">Name</th>
		      		<th width="30%" class="listdata">Notes</th>
		      		<th width="20%" class="listdata">Date Added</th>
		      		<th width="10" class="listdata">Options</th>
		      		</tr>
		      		</thead>
		      		<tbody>
					<c:choose>
		      		<c:when test="${fn:length(letters) > 0}">
			      		<c:forEach items="${letters}" var="rule">
			      		<c:set var="countLetter" value="${countLetter + 1}" />
		 					<tr style="border-bottom:1px solid silver;">
		      					<td class="field_content">${rule.lName}</td>
		      					<td class="field_content">${rule.notes}</td>
		      					<td class="field_content">${rule.dateAddedFormatted}</td>
		      					<td align="right" class="field_content">
		      					
		      					<button type="button" class="btn btn-xs btn-primary" onclick="window.open('${spath}${rule.viewPath}','_blank');">View</button>
		      					<button type="button" class="btn btn-xs btn-danger" onclick="opendeleteletterdialog('${rule.lName}','${rule.id}','E');">Del</button>
		      					
		      					
								</td>
		      				</tr>
		        		</c:forEach>
		        		</c:when>
		        		<c:otherwise>
		        			<tr><td colspan='4' style="color:Red;">No letters found.</td></tr>
		        		</c:otherwise>
		        		</c:choose>		      		
		        	</tbody>	
	      		</table>
	      			      		<c:choose>
      	<c:when test="${countLetter >0 }">
      		<script>$('#letters_success_message').html("There are <b>${countLetter}</b> on file.").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-table2').css("display","none");$('#letters_error_message').html("Sorry, there are no letters currently on file for this contractor at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>
	      		
	      		      		
	      		</div>
	    </div> 	       	
		</div>
		<img src="includes/img/bar.png" height=1 width=100%><br/><br/>
			<div class="form-group">        
		      <div class="col-sm-offset-3 col-sm-9">
		      	<br />
		      		<div class="alert alert-danger" id="employeeerrormessage" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    				<div class="alert alert-success" id="employeesuccessmessage" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
		      	<button type="button" class="btn btn-xs btn-primary" id="submitupdate" name="submitupdate" onclick="addupdateemployee('A','Y');">Update Employee</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		        <c:if test = "${employee.status != 2 && employee.status != 0}">
		        	<esd:SecurityAccessRequired permissions="BCS-APPROVE-REJECT">
        				<button type="button" class="btn btn-xs btn-success" onclick="openApproveEmp();">Approve</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        			</esd:SecurityAccessRequired>
      			</c:if>
      			<c:if test = "${employee.status eq 1}">
      				<esd:SecurityAccessRequired permissions="BCS-APPROVE-REJECT">
        				<button type="button" class="btn btn-xs btn-danger" onclick="openRejectEmp();">Reject</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				</esd:SecurityAccessRequired>
      			</c:if>
      			<c:if test = "${employee.status eq 2}">
      				<esd:SecurityAccessRequired permissions="BCS-SUSPEND-UNSUSPEND">
        				<button type="button" class="btn btn-xs btn-warning" onclick="openSuspendEmp();">Suspend</button>
        				</esd:SecurityAccessRequired>
      			</c:if>
		      </div>
		    </div>
			 
			  </form>
		
	</div>
		<div class="alert alert-danger" id="body_error_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    	<div class="alert alert-success" id="body_success_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>



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
      	<button type="button" class="btn btn-xs btn-primary" onclick="approverejectemployee();">Ok</button>
        <button type="button" class="btn btn-xs btn-default" data-dismiss="modal">Close</button><input type="hidden" id="trantype">
      </div>
    </div>

  </div>
</div>			  
<div id="modalAdd" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitle">Add New Letter</h4>
                    	<div class="alert alert-danger" style="display:none;" id="dalertadd" align="center">
  							<span id="dmessageadd"></span>
						</div>
						<div class="alert alert-success" style="display:none;" id="dalertadds" align="center">
  							<span id="dmessageadds"></span>
						</div>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title3">Letter Name:</p>
 		    		<p>
 		    		<input type="text" id="lname" name="lname" >					
 		    		</p>
                    <p class="text-warning" id="title3">Document:</p>
 		    		<p>
 		    		<input type="file" id="ldocument" name="ldocument" accept="application/pdf">(PDF file format only)					
 		    		</p>
 		    		<p class="text-warning" id="title3">Notes:</p>
 		    		<p>
      				<textarea class = "form-control" rows = "5" style="width:75%;display: block;margin-left: auto;margin-right: auto;" id="lnotes"></textarea>
      				</p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-success"  id="buttonleftadd"></button>
                    <button type="button" class="btn btn-xs btn-danger" data-dismiss="modal" id="buttonrightadd"></button>
                </div>
            </div>
   		</div>
   	</div>	
    <div id="modalDelete" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitled">Delete Letter</h4>
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
	<div id="myModalTrain" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitletr">Add New Training</h4>
                    	<div class="alert alert-danger" style="display:none;" id="dalert" align="center">
  							<span id="demessage"></span>
						</div>
						<div class="alert alert-success" style="display:none;" id="dalerts" align="center">
  							<span id="dsmessage"></span>
						</div>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1tr">Training Type:</p>
                    <input type="hidden" id="hidid" name="hidid">
                    <input type="hidden" id="hidedit" name="hidedit">
                    <p>
                    <select id="trainingtype" name="trainingtype">
                    	<option value="-1">Please Select Training Type</option>
						<c:forEach var="entry" items="${ttypes}">
							<option value='${entry.key}'>${entry.value}</option>
						</c:forEach>
                    </select>
                    </p>
                    <p class="text-warning" id="title2">Training Date:</p>
                    <p>
                    <input class="form-control" id="trainingdate" name="trainingdate" placeholder="training date" type="text">
                    </p>
                    <p class="text-warning" id="title1tr">Training Length:</p>
                    <p>
                    <select id="traininglength" name="traininglength">
                    	<option value="-1">Please Select Training Length</option>
						<c:forEach var="entry" items="${tlengths}">
							<option value='${entry.key}'>${entry.value}</option>
						</c:forEach>
                    </select>
                    </p>
                    <p class="text-warning" id="title2">Provided By:</p>
                    <p>
                    <input class="form-control" id="providedby" name="providedby" placeholder="provided by" type="text">
                    </p>
                    <p class="text-warning" id="title2">Location:</p>
                    <p>
                    <input class="form-control" id="location" name="location" placeholder="location" type="text">
                    </p>
                    <p class="text-warning" id="title2">Expiry Date (if applicable):</p>
                    <p>
                    <input class="form-control" id="expirydate" name="expirydate" placeholder="expiry date" type="text">
                    </p>
 		    		<p class="text-warning" id="title3">Document:</p>
 		    		<p class="text-warning" id="title3">
 		    		<div id="divviewdoc" name="divviewdoc" style="display:none;">
 		    			<a href="" target="_blank" id="linkdoc" name="linkdoc">View Current Doc</a>
 		    		</div>
 		    		</p>
 		    		<p>
 		    		<input type="file" id="documentname" name="documentname" accept="application/pdf">(PDF file format only)					
 		    		</p>
 		    		<p class="text-warning" id="title3">Notes:</p>
 		    		<p>
      				<textarea class = "form-control" rows = "5" style="width:75%;display: block;margin-left: auto;margin-right: auto;" id="rnotestr"></textarea>
      				</p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-success"  id="buttonlefttr"></button>
                    <button type="button" class="btn btn-xs btn-danger" data-dismiss="modal" id="buttonrighttr"></button>
                </div>
            </div>
   		</div>
   	</div>
    <div id="myModalTrainD" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitletrd">Delete Training</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1trd"><span id="spantitle1trd" name="spantitle1trd"></span></p>
                    <p class="text-warning" id="title2trd"><span id="spantitle2trd" name="spantitle2trd"></span></p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default"  id="buttonlefttrd"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonrighttrd"></button>
                </div>
            </div>
   		</div>
   	</div>
   	   	    <div id="myModal3" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitled">Bypass Date Validations</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1dby"><span id="spantitle1by" name="spantitle1by"></span></p>
                    <p class="text-warning" id="title2dby"><span id="spantitle2by" name="spantitle2by"></span></p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default"  id="buttonleftby"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonrightby"></button>
                </div>
            </div>
   		</div>
   	</div>
   	   	   	<div id="myModalFHistory" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitledd"><span id="spantitlemainfh" name="spantitlemainfh"></span></h4>
                </div>
                <div class="modal-body">
                 <table id="fhtable" width="100%" class="BCSTable">
			  		<thead>
			  			<tr class="listHeader">
			  				<th width="25%" class="listdata" style="padding:2px;">Action</th>
			  				<th width="25%" class="listdata" style="padding:2px;">Action By</th>
			  				<th width="25%" class="listdata" style="padding:2px;">Action Date</th>
			  				<th width="25%" class="listdata" style="padding:2px;">Link</th>
			  			</tr>
			  		</thead>
			  		<tbody>
					</tbody>
			  	</table>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonrightdd">Close</button>
                </div>
            </div>
   		</div>
   	</div>	   		
<script src="includes/js/jQuery.print.js"></script>	