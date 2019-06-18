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
<c:set var="countTrain" value="0" />
<c:set var="countLetter" value="0" />
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<script src="includes/js/bootstrapvalidator.min.js"></script>

<script src="includes/js/bootstrap-datepicker.min.js"></script>
<script src="includes/js/bcs.js"></script>
<%
String tabs = (String)request.getParameter("tab");
System.out.println(tabs);
String settab="";
if(tabs != null){
	settab="T";
}
pageContext.setAttribute("settab1", settab);
%>
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");    		    
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
				
				var date_training=$('#trainingdate');
				date_training.datepicker(options);
				var date_expiry=$('#expirydate');
				date_expiry.datepicker(options);
				var date_birth=$('#birthdate');
				date_birth.datepicker(options);
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
				if($("#settab1").val() == "T"){
      	    	  $('.nav-tabs a:last').tab('show'); 
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
				})
				checkEmployeeDateRanges();
				
});
		</script>
	<%pageContext.setAttribute("now", new java.util.Date()); %>   
	
	
			
			
	<div id="printJob">	
		<div class="BCSHeaderText">Employee Information</div>
		
		<div class="alert alert-danger" id="body_error_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    	<div class="alert alert-success" id="body_success_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
		
		<div style="max-width:100%;font-weight:normal;">
			  <ul class="nav nav-tabs">
	    		<li class="active"><a data-toggle="tab" href="#details">Employee Details</a></li>
	    		<li><a data-toggle="tab" href="#documents">Documents</a></li>
	    		<li><a data-toggle="tab" href="#training">Training</a></li>
	  		  </ul>
	  		  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="" enctype='multipart/form-data'>
	  			<div class="tab-content">
	  			<br/>
	  	<div id="details" class="tab-pane fade in active" style="font-size:11px;">
	  				<div class="form-group">
		      			<label class="control-label col-sm-3" for="email">Status:</label>
		      			<div class="col-sm-5">
			      		<c:choose>
	         				<c:when test = "${employee.status eq 1}">
	         				
	            				<p class="form-control-static"><span style="background-color:Yellow;color:black;padding:3px;text-transform:uppercase;">&nbsp;${ employee.statusText}&nbsp;</span></p>
	            			
	         				</c:when>
	         				<c:when test = "${employee.status eq 2}">
	         				
	            				<p class="form-control-static"><span style="background-color:Green;color:white;padding:3px;text-transform:uppercase;">&nbsp;${ employee.statusText}&nbsp;</span><br/>
	            				<span style="color:Green;">Approved on ${employee.dateApprovedFormatted}</span></p>
	            			
	         				</c:when>
	                  		<c:when test = "${employee.status eq 3}">
	                  	
	            				<p class="form-control-static"><span style="background-color:Red;color:white;padding:3px;text-transform:uppercase;">&nbsp;${employee.statusText}&nbsp;</span><br/>
	            				<span style="color:Red;">Not Approved on ${ employee.dateApprovedFormatted}.</span><br/>
	            				Notes: ${employee.statusNotes}</p>
	         				
	         				</c:when>
	         				<c:when test = "${employee.status eq 4}">
	                  	
	            				<p class="form-control-static"><span style="background-color:black;color:white;padding:3px;text-transform:uppercase;">&nbsp;${employee.statusText}&nbsp;</span><br/>
	            				<span style="color:Red;">Suspended on ${ employee.dateApprovedFormatted}.</span><br/>
	            				Notes: ${employee.statusNotes}</p>
	         				
	         				</c:when>
	         				<c:otherwise>
	            				
	            				<p class="form-control-static"><span style="background-color:black;color:white;padding:3px;text-transform:uppercase;">&nbsp;N/A&nbsp;</span></p>
	            				
	         				</c:otherwise>
	      				</c:choose>
	      				</div>
        			</div> 	
		  <div class="form-group">		  
                <label class="control-label col-sm-3" for="email">Employee Position:</label>
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
		      <label class="control-label col-sm-3" for="email">First Name:</label><input type="hidden" id="cid" name="cid" value="${employee.id}">
		      <input type="hidden" id="settab1" name="settab1" value="${settab1}">
		      <div class="col-sm-5">
		        <input class="form-control" id="firstname" name="firstname" type="text" placeholder="Enter first name" value="${employee.firstName}">
		      </div>
		    </div>    
		     <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Middle Name:</label>
		      <div class="col-sm-5">
		        <input class="form-control" id="middlename" name="middlename" type="text" placeholder="Enter middle name" value="${employee.middleName}">
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Last Name:</label>
		      <div class="col-sm-5">
		        <input class="form-control" id="lastname" name="lastname" type="text" placeholder="Enter last name"  value="${employee.lastName}">
		      </div>
		    </div>    
		   <div class="form-group">
                <label class="control-label col-sm-3" for="email">Date of Birth:</label> 
                <div class="col-sm-5">
                    <input class="form-control" id="birthdate" name="birthdate" placeholder="MM/DD/YYYY" type="text" 
                    value="${employee.birthDate == null ? '' : employee.birthDateFormatted}">
                </div>
	       </div>   
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Email:</label>
		      <div class="col-sm-5">
		        <input class="form-control" id="email" name="email" type="text" placeholder="Enter email" value="${employee.email}">
		      </div>
		    </div>
		   	<div class="form-group">
		      <label class="control-label col-sm-3" for="email">Address 1:</label>
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
		      <label class="control-label col-sm-3" for="email">Town/City:</label>
		      <div class="col-sm-5">
		        <input class="form-control" id="city" name="city" type="text" placeholder="Enter city" value="${employee.city}">
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Province:</label>
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
		      <label class="control-label col-sm-3" for="email">Postal Code:</label>
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
		      <label class="control-label col-sm-3" for="email">Start Date With Contractor</label>
		      <div class="col-sm-5">
		      <div class="form-inline">
		      		<select id="vmonth" name="vmonth"  class="form-control"  style="width:auto;">
						<option value="-1">Please select month</option>
			            <c:forEach begin="1" end="12" var="val">
			                <c:set var="decr" value="${val}"/>
			                <option value="${decr}" ${employee.startMonth == decr ? "selected='selected'": ''}>${decr}</option>
			        	</c:forEach>
			        </select>
	        	    <fmt:formatDate value="${now}" pattern="yyyy" var="startdate"/>
                	<select id="vyear" name="vyear"  class="form-control"  style="width:auto;">
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
		        <input class="form-control" id="continuousservice" name="continuousservice" type="text" placeholder="Enter years of continuous service" value="${employee.continuousService}">
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
		  		</select><br/>
		  		<span style="color:Grey;">Note: Select the lowest class the driver has.</span>
		  		</div>
		  		</div>
		  </div>
		  <div class="form-group">
               <label class="control-label col-sm-3" for="email">Licence Image Front:</label> 
                <div class="col-sm-5">					
 		    	 <c:choose>
                	<c:when test = "${employee.dlFront != null}">                	
                	<img src="${spath}${dpath}${employee.dlFront}"  border=0 class="zoomimg" style="width:100px;">  
                	<br/>Hover mouse over to enlarge above image.    
                	<br/>To Update         	
                	</c:when>
                	
                <c:otherwise>
                	<span style="background-color:Red;color:white;padding:3px;text-transform:uppercase;">NO FRONT IMAGE CURRENTLY ON FILE</span>
                	<br/>To Add
                	</c:otherwise>	
                </c:choose>	
                	 image of license front, choose file below to upload:<br/><br/>
    				<input type="file" id="dlfront" name="dlfront" accept="application/pdf">(PDF file format only)
 		    	
                </div>
	       </div>
	      <div class="form-group">
                 <label class="control-label col-sm-3" for="email">Licence Image Back:</label> 
                <div class="col-sm-5">
                 <c:choose>
                	<c:when test = "${employee.dlBack != null}">                	
                	<img src="${spath}${dpath}${employee.dlBack}"  border=0 class="zoomimg" style="width:100px;"> 
                	<br/>Hover mouse over to enlarge above image.    
                	<br/>To Update            	
                	</c:when>
                <c:otherwise>
                	<span style="background-color:Red;color:white;padding:3px;text-transform:uppercase;">NO BACK IMAGE CURRENTLY ON FILE</span>
                	<br/>To Add
                	</c:otherwise>	
                </c:choose>	
                 image of license back, choose file below to upload:<br/><br/>                
                <input type="file" id="dlback" name="dlback" accept="application/pdf">(PDF file format only)	
                </div>
	       </div>
	        <img src="includes/img/bar.png" height=1 width=100%><br/>
				   	<span style="font-size:14px;color:Grey;margin-bottom:10px;">Driver Abstract Information</span>  
       	   <div class="form-group">
                <label class="control-label col-sm-3" for="email">Run Date:</label> 
                <div class="col-sm-5">
                    <input class="form-control" id="darundate" name="darundate" placeholder="MM/DD/YYYY" type="text" 
                    value="${employee.daRunDate == null ? '' : employee.daRunDateFormatted}">
                     <br />
	                    <div id="divdarundate" name="divdarundate" style="display:none;">
	                    	<span id="spandarundate" name="spandarundate" style="color:White;background-color:Red;padding:2px;text-transform:uppercase;"></span>
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
	                	<br/><br/>To Update	                		               	
	                	</c:when>
	                	<c:otherwise>
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
                <label class="control-label col-sm-3" for="email">Driver Abstract Convictions:</label>
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
	                	<br/><br/>To Update	 
	                	</c:when>
	                	<c:otherwise>
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
	                    	<span id="spanprcvsqdate" name="spanprcvsqdate" style="color:White;background-color:Red;padding:2px;text-transform:uppercase;"></span>
	                    </div>
                </div>
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-3" for="email">PRC/VSQ File:</label> 
                <div class="col-sm-5">
                <c:choose>
	                	<c:when test = "${employee.prcvsqDocument != null}">
	                	<span style="color:White;background-color:Green;padding:2px;text-transform:uppercase;">&nbsp; A PRC/VSQ is currently on file &nbsp;</span>
	                	<br/><br/><a href="${spath}${dpath}${employee.prcvsqDocument}" title="Click to open" target="_blank">Current Document (${employee.prcvsqDocument})</a>
	                	<br/><br/>To Update	               	
	                	</c:when>
	                	<c:otherwise>
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
	                	<br/><br/>To Update		               	
	                	</c:when>
	                	<c:otherwise>
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
				   	<span style="font-size:14px;color:Grey;margin-bottom:10px;">Confidentiality Agreement</span>
	       
	       
	       <div class="form-group">
                <label class="control-label col-sm-3" for="email">Signed Date:</label> 
                <div class="col-sm-5">
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
	                	<br/><br/>To Update		               	
	                	</c:when>
	                	<c:otherwise>
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
				  	<div align="right"><button type="button" class="btn btn-xs btn-success" onclick="openaddnewdialogtr();">Add Training Doc</button></div>			  	
				  	<br/>
				  <div id="BCS-Search">
				 	
				  	<table id="BCS-table" width="100%" class="BCSTable">
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
		      					<button type="button" class="btn btn-xs btn-primary" onclick="window.open('${spath}${rule.viewPath}','_blank');">View</button>
		      					<button type="button" class="btn btn-xs btn-danger" onclick="opendeletetradialog('${rule.trainingTypeString}','${rule.pk}');">Del</button>
		      						
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
      		<script>$('#BCS-table').css("display","none");$('#training_error_message').html("Sorry, there are no training docs currently on file for this employee.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>	 
	      		
	      		</div>
	      		
			  </div>  
			 
			</form>      	
	           	       	       	        
		</div>
		
		
		
		
		
		
		</div>
		<img src="includes/img/bar.png" height=1 width=100%><br/><br/>   
				    <div class="form-group">  
				       
		      <div class="col-sm-offset-3 col-sm-9">
		      	
		      	
		      	<div class="alert alert-danger" id="employeeerrormessage" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    				<div class="alert alert-success" id="employeesuccessmessage" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>	      	
		      	
		      	
		        <button type="button" class="btn btn-xs btn-primary" id="submitupdate" name="submitupdate" onclick="checkemployee('U');">Update Employee</button>
		      </div>
		    </div>
			 
		 </form>
	</div>
	
		<div class="alert alert-danger" id="body_error_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    	<div class="alert alert-success" id="body_success_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
		
	</div>
		<div id="myModal" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitle">Add New Training</h4>
                    	<div class="alert alert-danger" style="display:none;" id="dalert" align="center">
  							<span id="demessage"></span>
						</div>
						<div class="alert alert-success" style="display:none;" id="dalerts" align="center">
  							<span id="dsmessage"></span>
						</div>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1">Training Type:</p>
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
                    <input class="form-control" id="location" name="location" placeholder="location" type="text">
                    </p>
                    <p>
                    <p class="text-warning" id="title2">Expiry Date (if applicable):</p>
                    <p>
                    <input class="form-control" id="expirydate" name="expirydate" placeholder="expiry date" type="text">
                    </p>
 		    		<p class="text-warning" id="title3">Document:</p>
 		    		<p>
 		    		<input type="file" id="documentname" name="documentname" accept="application/pdf">(PDF file format only				
 		    		</p>
 		    		<p class="text-warning" id="title3">Notes:</p>
 		    		<p>
      				<textarea class = "form-control" rows = "5" style="width:75%;display: block;margin-left: auto;margin-right: auto;" id="rnotestr"></textarea>
      				</p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-success"  id="buttonleft"></button>
                    <button type="button" class="btn btn-xs btn-danger" data-dismiss="modal" id="buttonright"></button>
                </div>
            </div>
   		</div>
   	</div>
    <div id="myModal2" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitled">Delete Training</h4>
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
