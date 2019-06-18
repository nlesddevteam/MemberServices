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

<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrapvalidator.min.js"></script>
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrap-datepicker.min.js"></script>
<script src="includes/js/bcs.js"></script>
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");

});
		</script>
		

	<script>
  $("#msameas").on("change", function(){
	 //populate temp values in case filled in.
	 
	  // Same as address click with backup!!
    if (this.checked) {
    	
    	 $("#maddresstemp1").val($("#maddress1").val());
   	  $("#maddresstemp2").val($("#maddress2").val());
   	  $("#mcitytemp").val($("#mcity").val());
   	  $("#mprovincetemp").val($("#mprovince").val());
   	  $("#mpostalcodetemp").val($("#mpostalcode").val());
    	//copy physical address to mailing address
	      $("#maddress1").val($("#address1").val()).prop("disabled",true);	     
	      $("#maddress2").val($("#address2").val()).prop("disabled",true);	      
	      $("#mcity").val($("#city").val()).prop("disabled",true);	     
	      $("#mprovince").val($("#province").val()).prop("disabled",true);	     
	      $("#mpostalcode").val($("#postalcode").val()).prop("disabled",true);	     
  
    } else {
    	//re-populate with old values from memory (if any) in case ticked by mistake.
    	 $("#maddress1").val($("#maddresstemp1").val()).prop("disabled",false);    	 
    	 $("#maddress2").val($("#maddresstemp2").val()).prop("disabled",false);    	
    	 $("#mcity").val($("#mcitytemp").val()).prop("disabled",false);    	
    	 $("#mprovince").val($("#mprovincetemp").val()).prop("disabled",false);    	
    	 $("#mpostalcode").val($("#mpostalcodetemp").val()).prop("disabled",false);
    	 
    }
  });
  
  
  $("#crsameas").on("change", function(){
		 
	  
	
	  // Same as address click with backup!!
    if (this.checked) {
    	//populate temp values in case filled in.
  	  $("#crfirstnametemp").val($("#crfirstname").val());
  	  $("#crlastnametemp").val($("#crlastname").val());
  	  $("#crphonenumbertemp").val($("#crphonenumber").val());
  	  $("#cremailtemp").val($("#cremail").val());
    	//copy physical address to mailing address
	      $("#crfirstname").val($("#firstname").val()).prop("disabled",true);	     
	      $("#crlastname").val($("#lastname").val()).prop("disabled",true);	      
	      $("#crphonenumber").val($("#cellphone").val()).prop("disabled",true);	     
	      $("#cremail").val($("#conemail").val()).prop("disabled",true);	    
	          
  
    } else {
    	//re-populate with old values from memory (if any) in case ticked by mistake.
    	 $("#crfirstname").val($("#crfirstnametemp").val()).prop("disabled",false);    	 
    	 $("#crlastname").val($("#crlastnametemp").val()).prop("disabled",false);    	
    	 $("#crphonenumber").val($("#crphonenumbertemp").val()).prop("disabled",false);    	
    	 $("#cremail").val($("#cremailtemp").val()).prop("disabled",false);    	
    	
    	 
    }
  });
  
  
  $("#tosameas").on("change", function(){
	
	  // Same as address click with backup!!
    if (this.checked) {
    	//populate temp values in case filled in.
  	  $("#tofirstnametemp").val($("#tofirstname").val());
  	  $("#tolastnametemp").val($("#tolastname").val());
  	  $("#tophonenumbertemp").val($("#tophonenumber").val());
  	  $("#toemailtemp").val($("#toemail").val());
    	//copy physical address to mailing address
    	 $("#tofirstname").val($("#firstname").val()).prop("disabled",true);	     
	      $("#tolastname").val($("#lastname").val()).prop("disabled",true);	      
	      $("#tophonenumber").val($("#cellphone").val()).prop("disabled",true);	     
	      $("#toemail").val($("#conemail").val()).prop("disabled",true);	       
  
    } else {
    	//re-populate with old values from memory (if any) in case ticked by mistake.
    	 $("#tofirstname").val($("#tofirstnametemp").val()).prop("disabled",false);    	 
    	 $("#tolastname").val($("#tolastnametemp").val()).prop("disabled",false);    	
    	 $("#tophonenumber").val($("#tophonenumbertemp").val()).prop("disabled",false);    	
    	 $("#toemail").val($("#toemailtemp").val()).prop("disabled",false);    	
    	 
    }
  });
  
  
</script>	
		
		
		
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">	
				<div class="alert alert-danger" id="body_error_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    	        <div class="alert alert-success" id="body_success_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
		<div class="BCSHeaderText">Contractor Information</div>
			  
			  <br />
			  <ul class="nav nav-tabs">
	    		<li class="active"><a data-toggle="tab" href="#contact">Contact Information</a></li>
	    		<li><a data-toggle="tab" href="#company">Company Information</a></li>
	  		  </ul>
	  		  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="" enctype='multipart/form-data'>
	  			<div class="tab-content">
	  			<div id="contact" class="tab-pane fade in active">
	  				<br />
	  				<span style="font-size:14px;color:Grey;">Company/Business Information</span>
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Company Name:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="companyname" name="companyname" type="text" placeholder="Enter company name">
				      </div>
				    </div>
				    <div class="form-group">
		                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Business Number:</label>
		                <div class="col-sm-5">
		                    <input class="form-control" id="busnumber" name="busnumber" type="text" placeholder="Enter business number">
		                </div>
		            </div>
				    <div class="form-group">
		                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>HST Number:</label>
		                <div class="col-sm-5">
		                    <input class="form-control" id="hstnumber" name="hstnumber" type="text" placeholder="Enter hst number">
		                </div>
		            </div> 
	  				<div class="form-group">
				      <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>First Name:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="firstname" name="firstname" type="text" placeholder="Enter first name">
				      </div>
				    </div>    
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Last Name:</label><input type="hidden" id="hidfullname">
				      <div class="col-sm-5">
				        <input class="form-control" id="lastname" name="lastname" type="text" placeholder="Enter last name">
				      </div>
				    </div>    
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Middle Name:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="middlename" name="middlename" type="text" placeholder="Enter middle name">
				      </div>
				    </div>    
				    <div class="form-group">
		                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Email:</label>
		                <div class="col-sm-5">
		                    <input class="form-control" id="conemail" name="conemail" type="text" placeholder="Enter email">
		                </div>
		            </div>
		            
		            <img src="includes/img/bar.png" height=1 width=100%><br/>
				   	<span style="font-size:14px;color:Grey;">Physical Address</span>
		            
				  
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Address 1:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="address1" name="address1" type="text" placeholder="Enter address 1">
				      </div>
				    </div>    
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Address 2:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="address2" name="address2" type="text" placeholder="Enter address 2">
				      </div>
				    </div>    
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Town/City:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="city" name="city" type="text" placeholder="Enter city">
				      </div>
				    </div>
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Province:</label>
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
				      <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Postal Code</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="postalcode" name="postalcode" type="text" placeholder="Enter postal code">
				      </div>
				    </div>
				    
				   	<img src="includes/img/bar.png" height=1 width=100%><br/>
				   	<span style="font-size:14px;color:Grey;margin-bottom:10px;">Mailing Address</span>
				   	
				   	<div class="form-group">			
						<label class="control-label col-sm-3" for="email">Same as Physical:</label>
						 <label class="checkbox-inline">
							<input id="msameas" name="msameas" type="checkbox">
				      	 </label>
				      	
					</div>
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Address 1:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="maddress1" name="maddress1" type="text" placeholder="Enter mailing address 1">
				         <input type="hidden" id="maddresstemp1">
				        
				      </div>
				    </div>    
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Address 2:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="maddress2" name="maddress2" type="text" placeholder="Enter mailing address 2">
				      <input type="hidden" id="maddresstemp2">
				      </div>
				    </div>    
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Town/City:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="mcity" name="mcity" type="text" placeholder="Enter mailing city">
				      <input type="hidden" id="mcitytemp">
				      </div>
				    </div>
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Province:</label>
				      <div class="col-sm-5">
				        <select class="form-control"  id="mprovince" name="mprovince">
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
						<input type="hidden" id="mprovincetemp">
						</div>
					</div>
					<div class="form-group">
				      <label class="control-label col-sm-2" for="email">Postal Code:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="mpostalcode" name="mpostalcode" type="text" placeholder="Enter mailing postal code">
				        <input type="hidden" id="mpostalcodetemp">
				      </div>
				    </div>		    
				    
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Home Phone:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="homephone" name="homephone" type="text" placeholder="Enter home phone">
				      </div>
				    </div>
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Cell Phone:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="cellphone" name="cellphone" type="text" placeholder="Enter cell phone">
				      </div>
				    </div>      
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Work Phone:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="workphone" name="workphone" type="text" placeholder="Enter work phone">
				      </div>
				    </div>
				</div>
				<div id="company" class="tab-pane fade">
					<br />
			 		<div class="form-group">
		                <label class="control-label col-sm-4" for="email"><img src='includes/css/images/asterisk-small.png'/>Types of Transportation:</label>
		                <label class="checkbox-inline">
		      				<input type="checkbox"  id="tregular" name="tregular">Regular 
		    			</label>
		    			<label class="checkbox-inline">
		      				<input type="checkbox"  id="talternate" name="talternate">Alternate
		    			</label>
		            </div>
		            <div class="form-group">
		            	<label class="control-label col-sm-4" for="email">I am a parent/guardian of the student</label>
		                <label class="checkbox-inline">
		                	<input type="checkbox"  id="tparent" name="tparent">
		    			</label>
		            </div>
		            
		             <img src="includes/img/bar.png" height=1 width=100%><br/>
				   	<span style="font-size:14px;color:Grey;margin-bottom:10px;">Contractor Representative</span>
		         
		             <div class="form-group">
		               <label class="control-label col-sm-3" for="email">Same as contact information</label>		      				
		      				 <label class="checkbox-inline">
		      				<input type="checkbox"  id="crsameas" name="crsameas">
		    			  	</label>	
		            </div>
		            <div class="form-group">
		                <label class="control-label col-sm-2" for="email">First Name:</label>
		                <div class="col-sm-5">
		                    <input class="form-control" id="crfirstname" name="crfirstname" type="text" placeholder="Enter first name">
		                <input type="hidden" id="crfirstnametemp">
		                </div>
		            </div>
		            <div class="form-group">
		                <label class="control-label col-sm-2" for="email">Last Name:</label>
		                <div class="col-sm-5">
		                    <input class="form-control" id="crlastname" name="crlastname" type="text" placeholder="Enter last name">
		                    <input type="hidden" id="crlastnametemp">
		                </div>
		            </div>
		            <div class="form-group">
		                <label class="control-label col-sm-2" for="email">Mobile Phone:</label>
		                <div class="col-sm-5">
		                    <input class="form-control" id="crphonenumber" name="crphonenumber" type="text" placeholder="Enter mobile phone">
		                    <input type="hidden" id="crphonenumbertemp">
		                </div>
		            </div>
		            <div class="form-group">
		                <label class="control-label col-sm-2" for="email">Email:</label>
		                <div class="col-sm-5">
		                    <input class="form-control" id="cremail" name="cremail" type="text" placeholder="Enter email">
		                    <input type="hidden" id="cremailtemp">
		                </div>
		            </div>
		            <img src="includes/img/bar.png" height=1 width=100%><br/>
				   	<span style="font-size:14px;color:Grey;margin-bottom:10px;">Transportation Officer</span>

		            
		             <div class="form-group">
		              <label class="control-label col-sm-3" for="email">Same as contact information</label>
		      				<label class="checkbox-inline">
		      				<input type="checkbox"  id="tosameas" name="tosameas">
		      				</label>
		             </div>
		            
		            <div class="form-group">
		                <label class="control-label col-sm-2" for="email">First Name:</label>
		                <div class="col-sm-5">
		                    <input class="form-control" id="tofirstname" name="tofirstname" type="text" placeholder="Enter first name">
		                    <input type="hidden" id="tofirstnametemp">
		                </div>
		            </div>
		            <div class="form-group">
		                <label class="control-label col-sm-2" for="email">Last Name:</label>
		                <div class="col-sm-5">
		                    <input class="form-control" id="tolastname" name="tolastname" type="text" placeholder="Enter last name">
		                <input type="hidden" id="tolastnametemp">
		                </div>
		            </div>
		            <div class="form-group">
		                <label class="control-label col-sm-2" for="email">Mobile Phone:</label>
		                <div class="col-sm-5">
		                    <input class="form-control" id="tophonenumber" name="tophonenumber" type="text" placeholder="Enter mobile phone">
		                    <input type="hidden" id="tophonenumbertemp">
		                </div>
		            </div>
		            <div class="form-group">
		                <label class="control-label col-sm-2" for="email">Email:</label>
		                <div class="col-sm-5">
		                    <input class="form-control" id="toemail" name="toemail" type="text" placeholder="Enter email">
		                    <input type="hidden" id="toemailtemp">
		                </div>
		            </div>
		    	</div>
			 			
		    	</div>
		    		<div class="form-group">        
				      <div class="col-sm-offset-2 col-sm-10">
				      	<br />
				      	<div class="alert alert-danger" id="contracterrormessage" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    					<div class="alert alert-success" id="contractsuccessmessage" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
				      	<button type="button" class="btn btn-xs btn-success" id="submitupdate" name="submitupdate" onclick="checkAddContratorFields();">Add Contractor</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				        <button type="button" class="btn btn-xs btn-danger" id="cancel" name="canceladd" onclick="window.location='index.jsp';return false;">Cancel</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				      </div>
				    </div>
			  </form>
		
	</div>
