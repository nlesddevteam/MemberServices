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
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
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
<script>
			
			jQuery(function(){
				  $(".img-swap").hover(
				          function(){this.src = this.src.replace("-off","-on");},
				          function(){this.src = this.src.replace("-on","-off");});
				 });
			
			
			</script>

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
		
		  <div class="BCSHeaderText">Vehicle Information</div>
		  
		  	
		
		  
		  <div style="max-width:100%;padding-top:10px;font-weight:normal;">
			<ul class="nav nav-tabs">
    			<li class="active"><a data-toggle="tab" href="#details">Vehicle Details</a></li>
    			<li><a data-toggle="tab" href="#documents">Documents</a></li>
  			</ul>
  			<form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="">
  			<div class="tab-content">
  			<div id="details" class="tab-pane fade in active">
  			<br/>
	  			
	  				  	<div class="form-group">
			      			<label class="control-label col-sm-3" for="email">Status:</label>
				      		<c:choose>
		         				<c:when test = "${vehicle.vStatus eq 1}">
		         				<div class="col-sm-5">
		            				<span style="background-color:Yellow;color:black;padding:3px;">&nbsp;${ vehicle.statusText}&nbsp;</span>
		            				</div>
		         				</c:when>
		         				<c:when test = "${vehicle.vStatus eq 2}">
		         				<div class="col-sm-5">
		            				<span style="background-color:Green;color:white;padding:3px;">&nbsp;${ vehicle.statusText}&nbsp;</span><br/>
		            				<span style="color:Green;">Approved on ${vehicle.dateApprovedFormatted}</span>
		            				</div>
		         				</c:when>
		                  		<c:when test = "${vehicle.vStatus eq 3}">
		                  		<div class="col-sm-5">
		            				<span style="background-color:Red;color:white;padding:3px;">&nbsp;${vehicle.statusText}&nbsp;</span><br/>
		            				<span style="color:Red;">Not Approved on ${ vehicle.dateApprovedFormatted}</span><br/>
		            				Notes: ${vehicle.statusNotes}
		         				</div>
		         				</c:when>
		         				<c:otherwise>
		            				<div class="col-sm-5"><span style="background-color:Black;color:white;padding:3px;">&nbsp;N/A&nbsp;</span></div>
		         				</c:otherwise>
		      				</c:choose>
        				</div>
			   	<div class="form-group">
	                <label class="control-label col-sm-3" for="email">Vehicle Make:</label>
	                <input type="hidden" id="vid" name="vid" value="${vehicle.id}">
	                <input type="hidden" id="settab1" name="settab1" value="${settab1}">
	                <input type="hidden" id="rtype" name="rtype" value="C">
	                <input type="hidden" id=contractor" name="contractor" value="${vehicle.contractorId}">
	                <div class="col-sm-5">
	                <select class="form-control" id="vmake" name="vmake" style="width:auto;">
	                	<option value="-1">Please select make</option>
						<c:forEach var="entry" items="${vmakes}">
							<c:choose>
							<c:when test = "${vehicle.vMake == entry.id}">
								<option value='${entry.id}' SELECTED>${entry.text}</option>
							</c:when>
							<c:otherwise>
								<option value='${entry.id}'>${entry.text}</option>
							</c:otherwise>
							</c:choose>
						</c:forEach>
			  		</select>
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
			  		
			  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-3" for="email">Vehicle Model:</label>
	                <div class="col-sm-5">
	                <input class="form-control" id="vmodel2" name="vmodel2" type="text" placeholder="Enter model" value="${vehicle.vModel2}">
	                </div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-3" for="email">Vehicle Year:</label>
	                	<fmt:formatDate value="${now}" pattern="yyyy" var="startdate"/>
	                	<div class="col-sm-5">
	                	<select id="vyear" name="vyear"  class="form-control"  style="width:auto;">
							<option value="-1">Please select year</option>
				            <c:forEach begin="0" end="20" var="val">
				                <c:set var="decr" value="${(startdate+3) - val}"/>
				                <option value="${decr}" ${vehicle.vYear == decr ? "selected='selected'": ''}>${decr}</option>
				            </c:forEach>
						</select>
	            		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-3" for="email">Serial Number:</label>
	                <div class="col-sm-5">
	                    <input class="form-control" id="vserialnumber" name="vserialnumber" type="text" placeholder="Enter serial number" value="${vehicle.vSerialNumber}">
	                </div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-3" for="email">Plate Number:</label>
	                <div class="col-sm-5">
	                    <input class="form-control" id="vplatenumber" name="vplatenumber" type="text" placeholder="Enter plate number" value="${vehicle.vPlateNumber}">
	                </div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-3" for="email">Vehicle Type:</label>
	                
	                <div class="col-sm-5">
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
			  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-3" for="email">Vehicle Size:</label>
	                <div class="col-sm-5">
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
			  		</div>
	            </div>
	          	<div class="form-group">
	                <label class="control-label col-sm-3" for="email">Wheelchair Accessible:</label>
	                <div class="col-sm-5">
        				<p>
        					<select class="form-control" id="vwheel" name="vwheel"  style="width:auto;">
	                			<option value="1" ${vehicle.vModel eq 1 ? 'SELECTED':''}>Yes</option>
	                			<option value="0" ${vehicle.vModel le 0 ? 'SELECTED':''}>No</option>
	                		</select>
        				</p>
      		  		</div>
	            </div>	
	            <div class="form-group">
	                <label class="control-label col-sm-3" for="email">Unit Number:</label>
	                <div class="col-sm-5">
        				<p>
        				<input class="form-control" id="unitnumber" name="unitnumber" type="text" placeholder="Enter unit number" value="${vehicle.unitNumber}">
        				</p>
      		  		</div>
	            </div>
	            </div>
				<div id="documents" class="tab-pane fade">
				   <div class="alert alert-danger" id="doc_details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    				<div class="alert alert-success" id="doc_details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
				  	<p>
				 <div class="form-group">
	                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Registered Owner:</label>
	                <div class="col-sm-5">
        				<p><input class="form-control" id="vrowner" name="vrowner" type="text" placeholder="Enter registered owner" value="${vehicle.vOwner}"></p>
      		  		</div>
	            </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Registration Expiry Date:</label>
	                <div class="col-sm-5">
        				<input class="form-control" id="rdate" name="rdate" placeholder="MM/DD/YYY" type="text" 
	                    value="${vehicle.regExpiryDate == null ? '' : vehicle.regExpiryDateFormatted}">
	                    <br />
	                    <div id="divrdate" name="divrdate" style="display:none;">
	                    <span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">&nbsp; Date must be in future and no more than 1 year &nbsp;</span>
	                    </div>
        			</div>
	            </div>
				<div class="form-group">
                <label class="control-label col-sm-2" for="email">Registration File:</label> 
                <div class="col-sm-5">					
					<c:choose>
	                	<c:when test = "${vehicle.regFile != null}">
	                	
	                	<a href="${spath}${dpath}${vehicle.regFile}" title="Click to open"  target="_blank">Current Document (${vehicle.regFile})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFileC('8','${vehicle.id}','${vehicle.regFile}');">Delete File</button>
	                	<br/><br/>To Update	 
	                	</c:when>
	                	<c:otherwise>
	                		<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">No file currently available</span><br/><br/>To Add	 
	                	</c:otherwise>
                	</c:choose>   
                    document, choose file below to upload:
                	<br/><br/>                
					<input type="file" id="regFile" name="regFile" accept="application/pdf">
					(PDF file format only)<br/>
 		    	
                </div>
           		</div>
           		           <img src="includes/img/bar.png" height=1 width=100%><br/><br/>	            
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
                <label class="control-label col-sm-2" for="email">Proof of Insurance File:</label> 
                <div class="col-sm-5">					
					<c:choose>
	                	<c:when test = "${vehicle.insFile != null}">
	                	
	                	<br/><br/><a href="${spath}${dpath}${vehicle.insFile}" title="Click to open"  target="_blank">Current Document (${vehicle.insFile})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFileC('9','${vehicle.id}','${vehicle.insFile}');">Delete File</button>
	                	<br/><br/>To Update	 
	                	</c:when>
	                	<c:otherwise>
	                		<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">No file currently available</span><br/><br/>To Add	 
	                	</c:otherwise>
                	</c:choose>   
                    document, choose file below to upload:
                	<br/><br/>                
					<input type="file" id="insFile" name="insFile" accept="application/pdf">
					(PDF file format only)<br/>
 		    	
                </div>
           </div>
            <img src="includes/img/bar.png" height=1 width=100%><br/><br/>
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
                <label class="control-label col-sm-2" for="email">Fall Inspection File:</label> 
                <div class="col-sm-5">					
					<c:choose>
	                	<c:when test = "${vehicle.fallInsFile != null}">
	                	
	                	<br/><br/><a href="${spath}${dpath}${vehicle.fallInsFile}" title="Click to open"  target="_blank">Current Document (${vehicle.fallInsFile})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFileC('10','${vehicle.id}','${vehicle.fallInsFile}');">Delete File</button>
	                	<br/><br/>To Update	 
	                	</c:when>
	                	<c:otherwise>
	                		<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">No file currently available</span><br/><br/>To Add	 
	                	</c:otherwise>
                	</c:choose>   
                    document, choose file below to upload:
                	<br/><br/>                
					<input type="file" id="fallInsFile" name="fallInsFile" accept="application/pdf">
					(PDF file format only)<br/>
 		    	
                </div>
           </div>
           <img src="includes/img/bar.png" height=1 width=100%><br/><br/>	            
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
                <label class="control-label col-sm-2" for="email">Winter Inspection File:</label> 
                <div class="col-sm-5">					
					<c:choose>
	                	<c:when test = "${vehicle.winterInsFile != null}">
	                	
	                	<br/><br/><a href="${spath}${dpath}${vehicle.winterInsFile}" title="Click to open"  target="_blank">Current Document (${vehicle.winterInsFile})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFileC('11','${vehicle.id}','${vehicle.winterInsFile}');">Delete File</button>
	                	<br/><br/>To Update	 
	                	</c:when>
	                	<c:otherwise>
	                		<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">No file currently available</span><br/><br/>To Add	 
	                	</c:otherwise>
                	</c:choose>   
                    document, choose file below to upload:
                	<br/><br/>                
					<input type="file" id="winterInsFile" name="winterInsFile" accept="application/pdf">
					(PDF file format only)<br/>
 		    	
                </div>
           </div>
           <img src="includes/img/bar.png" height=1 width=100%><br/><br/>	              
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
                <label class="control-label col-sm-2" for="email">Fall H.E. Inspection File:</label> 
                <div class="col-sm-5">					
					<c:choose>
	                	<c:when test = "${vehicle.fallHEInsFile != null}">
	                	
	                	<br/><br/><a href="${spath}${dpath}${vehicle.fallHEInsFile}" title="Click to open"  target="_blank">Current Document (${vehicle.fallHEInsFile})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFileC('12','${vehicle.id}','${vehicle.fallHEInsFile}');">Delete File</button>
	                	<br/><br/>To Update	 
	                	</c:when>
	                	<c:otherwise>
	                		<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">No file currently available</span><br/><br/>To Add	 
	                	</c:otherwise>
                	</c:choose>   
                    document, choose file below to upload:
                	<br/><br/>                
					<input type="file" id="fallHEInsFile" name="fallHEInsFile" accept="application/pdf">
					(PDF file format only)<br/>
                </div>
           </div>
           <img src="includes/img/bar.png" height=1 width=100%><br/><br/>
           
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
                <label class="control-label col-sm-2" for="email">Misc H.E. Inspection File1:</label> 
                <div class="col-sm-5">					
					<c:choose>
	                	<c:when test = "${vehicle.miscHEInsFile1 != null}">
	                	
	                	<br/><br/><a href="${spath}${dpath}${vehicle.miscHEInsFile1}" title="Click to open"  target="_blank">Current Document (${vehicle.miscHEInsFile1})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFileC('13','${vehicle.id}','${vehicle.miscHEInsFile1}');">Delete File</button>
	                	<br/><br/>To Update	 
	                	</c:when>
	                	<c:otherwise>
	                		<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">No file currently available</span><br/><br/>To Add	 
	                	</c:otherwise>
                	</c:choose>   
                    document, choose file below to upload:
                	<br/><br/>                
					<input type="file" id="miscHEInsFile1" name="miscHEInsFile1" accept="application/pdf">
					(PDF file format only)<br/>
 		    	
                </div>
           </div>
            <img src="includes/img/bar.png" height=1 width=100%><br/><br/>           	                        
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
			<div class="form-group">
                <label class="control-label col-sm-2" for="email">Misc H.E. Inspection File2:</label> 
                <div class="col-sm-5">					
					<c:choose>
	                	<c:when test = "${vehicle.miscHEInsFile2 != null}">
	                	
	                	<br/><br/><a href="${spath}${dpath}${vehicle.miscHEInsFile2}" title="Click to open"  target="_blank">Current Document (${vehicle.miscHEInsFile2})</a>
	                	&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-xs btn-danger' onclick="deleteFileC('14','${vehicle.id}','${vehicle.miscHEInsFile1}');">Delete File</button>
	                	<br/><br/>To Update	 
	                	</c:when>
	                	<c:otherwise>
	                		<span style="color:White;background-color:Red;padding:2px;text-transform:uppercase;">No file currently available</span><br/><br/>To Add	 
	                	</c:otherwise>
                	</c:choose>   
                    document, choose file below to upload:
                	<br/><br/>                
					<input type="file" id="miscHEInsFile2" name="miscHEInsFile2" accept="application/pdf">
					(PDF file format only)<br/>
 		    	
                </div>
           </div>				  	
				 <img src="includes/img/bar.png" height=1 width=100%><br/><br/>
            
			  </div>

				
		  </div>
		  			  	<div class="form-group">        
			      <div class="col-sm-offset-3 col-sm-9">
			      	<br />
			        <button type="button" class="btn btn-xs btn-primary" id="submitupdate" name="submitupdate" onclick="addupdatevehicle('C','Y');">Update Information</button>
			      </div>
			    </div>
			    <div class="alert alert-danger" id="vehicleerrormessage" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    			<div class="alert alert-success" id="vehiclesuccessmessage" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
		  </form>	
		  </div>

	
	
		


	

   	
