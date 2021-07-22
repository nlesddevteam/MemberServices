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
				if($("#settab1").val() == "T"){
      	    	  $('.nav-tabs a:last').tab('show'); 
      	      	}else{
      	      	$('.nav-tabs a:first').tab('show'); 
      	      	}
				$("#dlexpirydate").change(function(){
					checkdate('DLEXP');
				});
				$("#faexpirydate").change(function(){
					checkdate('FAEXP');
				});
				$("#scadate").change(function(){
					checkdate('SCADATE');
				})
				
				if($("#estatus").val() == 1 || $("#estatus").val() == 3 || $("#estatus").val() == 4){
					if(checkemployee('U','Y',false)){
						$("#submitapp").show();
					}else{
						$("#submitapp").hide();
					}
				}else{
					$("#submitapp").hide();
				}
				var cvalues = $('#hidctypes').val().split(",");
				//
				$("#contypes").val(cvalues);
				
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
		      			<label class="control-label col-sm-3" for="email">Status:<input type="hidden" id="estatus" value="${employee.status}"></label>
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
	         				<c:when test = "${employee.status eq 6}">
	                  	
	            				<p class="form-control-static"><span style="background-color:BLue;color:white;padding:3px;text-transform:uppercase;">&nbsp;${employee.statusText}&nbsp;</span><br/>
	            			</c:when>
	            			<c:when test = "${employee.status eq 7}">
	         					<span style="background-color:yellow;color:black;padding:3px;text-transform:uppercase;">&nbsp;TEMPORARILY ON HOLD&nbsp;</span><br />	         				
	            				Notes: ${employee.statusNotes}</p>
	         				</c:when>	
	         				<c:otherwise>
	            				
	            				<p class="form-control-static"><span style="background-color:black;color:white;padding:3px;text-transform:uppercase;">&nbsp;N/A&nbsp;</span></p>
	            				
	         				</c:otherwise>
	      				</c:choose>
	      				</div>
        			</div>
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
		        			<button type='button' onclick="checkdl('C')">Check Licence Number</button>
		        		</div>
		        		<div class="col-sm-5">
		        			<div  id="dlvalid" style="display:none;">
  								<span id="dlspan"style="color:White;background-color:Red;padding:2px;text-transform:uppercase;"></span>
							</div>
		        		</div>
		        	</div>
		        </div>

		    </div>
		    <c:if test="${bcbean.boardOwned eq 'Y'}">
		    		  <div class="form-group">		  	
                <label class="control-label col-sm-3" for="email"><img src='includes/css/images/asterisk-small.png'/>Region:</label>
                <div class="col-sm-5">
                <select class="form-control" id="regioncode" name="regioncode"  style="width:auto;">
                	<option value="-1">N/A</option>
					<c:forEach var="entry" items="${rcodes}">
						<c:choose>
							<c:when test = "${employee.regionBean ne null }">
								<c:choose>
									<c:when test = "${employee.regionBean.regionCode == entry.key }">
										<option value='${entry.key}' SELECTED>${entry.value}</option>
									</c:when>
									<c:otherwise>
										<option value='${entry.key}'>${entry.value}</option>
									</c:otherwise>
								</c:choose>
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
                <label class="control-label col-sm-3" for="email"><img src='includes/css/images/asterisk-small.png'/>Depot:</label>
                <div class="col-sm-5">
                <select class="form-control" id="depotcode" name="depotcode"  style="width:auto;">
                	<option value="-1">N/A</option>
					<c:forEach var="entry" items="${dcodes}">
						<c:choose>
							<c:when test = "${employee.regionBean ne null }">
								<c:choose>
									<c:when test = "${employee.regionBean.depotCode == entry.key }">
										<option value='${entry.key}' SELECTED>${entry.value}</option>
									</c:when>
									<c:otherwise>
										<option value='${entry.key}'>${entry.value}</option>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
										<option value='${entry.key}'>${entry.value}</option>
									</c:otherwise>
						</c:choose>
					</c:forEach>
		  		</select>
		  		</div>
	      </div>	
	</c:if>	    
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
		        <input class="form-control" id="continuousservice" name="continuousservice" type="text" placeholder="Enter years of continuous service" value="${employee.continuousService}" readonly>
		      </div>
		    </div>
		</div>
		
		
		
		<div id="documents" class="tab-pane fade" style="font-size:11px;">
		<br />		
		<span style="font-size:14px;color:Grey;margin-bottom:10px;">Driver Licence Information:</span>

	        <div class="form-group">
	                <label class="control-label col-sm-3" for="email">Expiry Date:</label> 
	                <div class="col-sm-5">
	                    <input class="form-control" id="dlexpirydate" name="dlexpirydate" placeholder="MM/DD/YYYY" type="text" 
	                    value="${employee.dlExpiryDate == null ? '' : employee.dlExpiryDateFormatted}">
	                     <br />
	                    <div id="divdlexp" name="divdlexp" style="display:none;">
	                    	<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">&nbsp; Date must be in future and no more than 10 years &nbsp;</span>
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
                	<span style="color:White;background-color:Green;padding:2px;text-transform:uppercase;">&nbsp; A front image is currently on file &nbsp;</span>
	                	<br/><br/><a href="${spath}${dpath}${employee.dlFront}" title="Click to open"  target="_blank">Current Document (${employee.dlFront})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFileC('1','${employee.id}','${employee.dlFront}');">Delete File</button>   
                	<br/>To Update<input type="hidden" id="hiddlfront" value='Y'>         	
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
                	<span style="color:White;background-color:Green;padding:2px;text-transform:uppercase;">&nbsp; A back image is currently on file &nbsp;</span>
	                	<br/><br/><a href="${spath}${dpath}${employee.dlBack}" title="Click to open"  target="_blank">Current Document (${employee.dlBack})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFileC('2','${employee.id}','${employee.dlBack}');">Delete File</button>    
                	<br/>To Update<input type="hidden" id="hiddlback" value='Y'>        	
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
                </div>
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-3" for="email">Lost Demerit Points:</label>
                <div class="col-sm-5"> 
                		<select class="form-control" id="demeritpoints" name="demeritpoints">
                			<c:forEach begin="0" end="20" step="1" varStatus="lcount">
    								<c:choose>
    									<c:when test="${employee.demeritPoints eq lcount.count-1}">
    										<option value="${lcount.count-1}" selected>${lcount.count-1}</option>
    									</c:when>
    									<c:otherwise>
    										<option value="${lcount.count-1}">${lcount.count-1}</option>
    									</c:otherwise>
    								</c:choose>
    						</c:forEach>
                		</select>
				</div>
	       </div>	       
      	  <div class="form-group">
                <label class="control-label col-sm-3" for="email">File:</label> 
                <div class="col-sm-5">	
                 <c:choose>
	                	<c:when test = "${employee.daDocument != null}">
	                	<span style="color:White;background-color:Green;padding:2px;text-transform:uppercase;">&nbsp; A document is currently on file &nbsp;</span> 
		                	<br/><br/><a href="${spath}${dpath}${employee.daDocument}" title="Click to open" target="_blank">Current Document (${employee.daDocument})</a>
		                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFileC('3','${employee.id}','${employee.daDocument}');">Delete File</button>
	                	<br/><br/>To Update<input type="hidden" id="hiddadocument" value='Y'>	                		               	
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
                <label class="control-label col-sm-3" for="email">Convicted of Dangerous Driving Within the Last 5yrs:</label>
                <div class="col-sm-5"> 
                <label class="radio-inline"><input type="radio" name="dangerousdriving" value='Y' ${employee.dangerousDriving == 'Y' ? 'checked=\'checked\'' : ''}>Yes</label>
				<label class="radio-inline"><input type="radio" name="dangerousdriving" value='N' ${employee.dangerousDriving == 'N' ? 'checked=\'checked\'' : ''}>No</label>
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
                <label class="control-label col-sm-3" for="email">Suspension Within the Last 36 Months?:</label>
                <div class="col-sm-5"> 
                <label class="radio-inline"><input type="radio" name="suspensions" value='Y' ${employee.suspensions == 'Y' ? 'checked=\'checked\'' : ''}>Yes</label>
				<label class="radio-inline"><input type="radio" name="suspensions" value='N' ${employee.suspensions == 'N' ? 'checked=\'checked\'' : ''}>No</label>
				</div>
	       </div> 	        
	             	  <div class="form-group">
                <label class="control-label col-sm-3" for="email">"At Fault" Accident(s):</label>
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
                <label class="control-label col-sm-3" for="email">Level C:</label>
                <div class="col-sm-5"> 
                <label class="radio-inline"><input type="radio" name="falevelc" value='Y' ${employee.faLevelC == 'Y' ? 'checked=\'checked\'' : ''}>Yes</label>
				<label class="radio-inline"><input type="radio" name="falevelc" value='N' ${employee.faLevelC == 'N' ? 'checked=\'checked\'' : ''}>No</label>
				</div>
	       </div> 
      	  <div class="form-group">
                <label class="control-label col-sm-3" for="email">Certificate File:</label> 
                <div class="col-sm-5">
                <c:choose>
	                	<c:when test = "${employee.faDocument != null}">
	                	<span style="color:White;background-color:Green;padding:2px;text-transform:uppercase;">&nbsp; A certificate is currently on file &nbsp;</span>
	                	<br/><br/><a href="${spath}${dpath}${employee.faDocument}" title="Click to open"  target="_blank">Current Document (${employee.faDocument})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFileC('4','${employee.id}','${employee.faDocument}');">Delete File</button>
	                	<br/><br/>To Update<input type="hidden" id="hidfadocument" value='Y'>	 
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
                </div>
	       </div>
	       <div class="form-group">
                <label class="control-label col-sm-3" for="email">Vulnerable Sector:</label>
                <div class="col-sm-5"> 
                <label class="radio-inline"><input type="radio" name="vulsector" value='-1' ${employee.vulnerableSector == -1 ? 'checked=\'checked\'' : ''}>Negative</label>
				<label class="radio-inline"><input type="radio" name="vulsector" value='1' ${employee.vulnerableSector == 1 ? 'checked=\'checked\'' : ''}>Positive</label>
				</div>
	       </div>	       
      	  <div class="form-group">
                <label class="control-label col-sm-3" for="email">PRC/VSQ File:</label> 
                <div class="col-sm-5">
                <c:choose>
	                	<c:when test = "${employee.prcvsqDocument != null}">
	                	<span style="color:White;background-color:Green;padding:2px;text-transform:uppercase;">&nbsp; PRC/VSQ currently on file &nbsp;</span>
	                	<br/><br/><a href="${spath}${dpath}${employee.prcvsqDocument}" title="Click to open" target="_blank">Current Document (${employee.prcvsqDocument})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFileC('5','${employee.id}','${employee.prcvsqDocument}');">Delete File</button>
	                	<br/><br/>To Update<input type="hidden" id="hidprcvsqdocument" value='Y'>	               	
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
                <label class="radio-inline"><input type="radio" name="findingsofguilt"  value='Y' ${employee.findingsOfGuilt == 'Y' ? 'checked=\'checked\'' : ''}>Yes</label>
				<label class="radio-inline"><input type="radio" name="findingsofguilt"  value='N' ${employee.findingsOfGuilt== 'N' ? 'checked=\'checked\'' : ''}>No</label>
				</div>
	       </div>
	       <div class="form-group">
                <label class="control-label col-sm-3" for="email">Conviction Types:</label> 
                <div class="col-sm-5">
                	<select id="contypes" name="contypes" size="15" multiple>
						<option value="0">None</option>
						<c:forEach var="entry" items="${convicttypes}">
								<option value='${entry.key}'>${entry.value}</option>
						</c:forEach>
					</select><input type="hidden" id="hidctypes" value="${employee.convictionTypes}">
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
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFileC('6','${employee.id}','${employee.pccDocument}');">Delete File</button>
	                	<br/><br/>To Update<input type="hidden" id="hidpccdocument" value='Y'>		               	
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
				   	<span style="font-size:14px;color:Grey;margin-bottom:10px;">Criminal Offence Declaration</span>
	       
	       <div class="form-group">
                <label class="control-label col-sm-3" for="email">Date Signed:</label> 
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
	                	&nbsp;&nbsp;&nbsp;
	                	<br/><br/>To Update<input type="hidden" id="hidcoddocument" value='Y'>		               	
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
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFileC('7','${employee.id}','${employee.scaDocument}');">Delete File</button>
	                	<br/><br/>To Update<input type="hidden" id="hidscadocument" value='Y'>		               	
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
		      					<c:choose>
		      						<c:when test="${rule.tDocument eq null }">
		      							<button type="button" class="btn btn-xs btn-info" disabled>Doc</button>
		      						</c:when>
		      						<c:otherwise>
		      							<button type="button" class="btn btn-xs btn-info" onclick="window.open('${spath}${rule.viewPath}','_blank');">Doc</button>
		      						</c:otherwise>
		      					</c:choose>
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
			<label class="control-label col-sm-3" for="email"></label>  
			<div class="col-sm-5">
		      	<div class="alert alert-danger" id="employeeerrormessage" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    			<div class="alert alert-success" id="employeesuccessmessage" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>	      	
		      	<div class="form-inline">
		        	<button type="button" class="btn btn-xs btn-primary" id="submitupdate" name="submitupdate" onclick="addupdateemployee('U','N');">Update Employee</button>
		       		<button type="button" class="btn btn-xs btn-primary" id="submitapp" name="submitapp" onclick="submitemployeeapproval();">Submit For Approval</button>
		      		
		      </div>
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

