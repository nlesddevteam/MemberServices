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
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script src="includes/js/bcs.js"></script>
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");    		    
    		    $('#province').val('${contractor.province}');
    		    $('#mprovince').val('${contractor.mprovince}');
    		    $('#contact-form-up').bootstrapValidator({
    		        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
    		        feedbackIcons: {
    		            valid: 'glyphicon glyphicon-ok',
    		            invalid: 'glyphicon glyphicon-remove',
    		            validating: 'glyphicon glyphicon-refresh'
    		        },submitHandler: function(validator, form, submitButton) {
    		        	//$('#success_message').slideDown({ opacity: "show" }, "slow"); // Do something ...
    		          			$('#contact-form-up').data('bootstrapValidator').resetForm();
    		              //var bv = form.data('bootstrapValidator');
    		              // Use Ajax to submit form data
    		              //$.post(form.attr('action'), form.serialize(), function(result) {
    		                  //console.log(result);
    		              //}, 'json');
    		              
    		          			var frm = $('#contact-form-up');
    		          			$.ajax({
    		          		        type: "POST",
    		          		        url: "updateContactInfo.html",
    		          		        data: frm.serialize(),
    		          		        success: function (xml) {
    		          		        	$(xml).find('CONTRACTOR').each(function(){
    		          							//now add the items if any
    		          							if($(this).find("MESSAGE").text() == "UPDATED")
    		          			 					{
    		          									$('#details_success_message_bottom').text("Contact Info Updated").css("display","block").delay(6000).fadeOut();
    		          									
    		          			 					}else{
    		          			 						$('#details_error_message_bottom').text($(this).find("MESSAGE").text()).css("display","block").delay(6000).fadeOut();
    		          			 						
    		          			 					}

    		          							});
    		          		        },
    		          		        error: function (xml) {
    		          		            isvalid=false;
    		          		        },
    		          		    });
    		        },
    		        fields: {
    		            firstname: {
    		                validators: {
    		                        stringLength: {
    		                        min: 2,
    		                    },
    		                        notEmpty: {
    		                        message: 'Please supply your first name'
    		                    }
    		                }
    		            },
    		             lastname: {
    		                validators: {
    		                     stringLength: {
    		                        min: 2,
    		                    },
    		                    notEmpty: {
    		                        message: 'Please supply your last name'
    		                    }
    		                }
    		            },
    		         	city: {
    		                validators: {
    		                     stringLength: {
    		                        min: 2,
    		                    },
    		                    notEmpty: {
    		                        message: 'Please supply your city'
    		                    }
    		                }
    		            },
    		             address1: {
    		                validators: {
    		                     stringLength: {
    		                        min: 2,
    		                    },
    		                    notEmpty: {
    		                        message: 'Please supply your address 1'
    		                    }
    		                }
    		            },
    		             province: {
    		                validators: {
    		                    notEmpty: {
    		                        message: 'Please select your province'
    		                    }
    		                }
    		            },
    		            postalcode: {
    		                validators: {
    		                    notEmpty: {
    		                        message: 'Please supply your postal code'
    		                    }
    		                }
    		            },
    		           homephone: {
    		                validators: {
    		                    notEmpty: {
    		                        message: 'Please supply your home phone number'
    		                    },
    		                    phone: {
    		                        country: 'US',
    		                        message: 'Please supply a vaild home phone number with area code'
    		                    }
    		                }
    		            }
    		        }
    		       });
});
		</script>
		

		
		
		
	<div id="printJob">	
		<br/>
		 <img src="includes/img/bar.png" width="100%" style="padding-bottom:10px;">
		
				<div class="alert alert-danger" id="details_error_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
   				<div class="alert alert-success" id="details_success_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
		
		 <div class="BCSHeaderText">Contact Information</div>
		 		  
		  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="">
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">First Name:</label><input type="hidden" id="cid" name="cid" value="${contractor.id}">
		      <div class="col-sm-8">
		        <input class="form-control" id="firstname" name="firstname" type="text" placeholder="Enter first name" value="${contractor.firstName}">
		      </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Middle Name:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="middlename" name="middlename" type="text" placeholder="Enter middle name" value="${contractor.middleName}">
		      </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Last Name:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="lastname" name="lastname" type="text" placeholder="Enter last name"  value="${contractor.lastName}">
		      </div>
		    </div>    
		    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Email:</label>
		      <div class="col-sm-8">
		        <p class="form-control-static">${contractor.email}</p>
		      </div>
		    </div>
		    
		     <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Home Phone:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="homephone" name="homephone" type="text" placeholder="Enter home phone" value="${contractor.homePhone}">
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Cell Phone:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="cellphone" name="cellphone" type="text" placeholder="Enter cell phone" value="${contractor.cellPhone}">
		      </div>
		    </div>     
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Work Phone:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="workphone" name="workphone" type="text" placeholder="Enter work phone" value="${contractor.workPhone}">
		      </div>
		    </div>
		    
		    
		    
		    <img src="includes/img/bar.png" width="100%" style="padding-bottom:10px;">
		    
		    <div class="BCSHeaderText">Physical Address</div>
		    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Address 1:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="address1" name="address1" type="text" placeholder="Enter address 1" value="${contractor.address1}">
		      </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Address 2:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="address2" name="address2" type="text" placeholder="Enter address 2" value="${contractor.address2}">
		      </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">City:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="city" name="city" type="text" placeholder="Enter city" value="${contractor.city}">
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Province:</label>
		      <div class="col-sm-8">
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
		      <div class="col-sm-8">
		        <input class="form-control" id="postalcode" name="postalcode" type="text" placeholder="Enter postal code" value="${contractor.postalCode}">
		      </div>
		    </div>
		    <img src="includes/img/bar.png" width="100%" style="padding-bottom:10px;">
		    <div class="BCSHeaderText">Mailing Address</div>
		    
		     <div class="form-group">
                <label class="control-label col-sm-4" for="email">Same as physical address &nbsp;&nbsp;<input type="checkbox"  onclick="SamePhys(this.checked);" id="msameas" name="msameas" <c:out value="${contractor.msameAs eq 'Y' ? 'CHECKED' : ''}"/>></label>
                      
            
            </div>
		    
		    
		   
					     
		      
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Address 1:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="maddress1" name="maddress1" type="text" placeholder="Enter mailing address 1" value='${contractor.maddress1}'>
		      </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Address 2:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="maddress2" name="maddress2" type="text" placeholder="Enter mailing address 2" value="${contractor.maddress2}">
		      </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Town/City:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="mcity" name="mcity" type="text" placeholder="Enter mailing city" value="${contractor.mcity}">
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Province:</label>
		      <div class="col-sm-8">
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
				</div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-3" for="email">Postal Code:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="mpostalcode" name="mpostalcode" type="text" placeholder="Enter mailing postal code" value="${contractor.mpostalCode}">
		      </div>
		    </div>		    
		    
		   
		    <img src="includes/img/bar.png" width="100%" style="padding-bottom:10px;">
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Company Name:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="companyname" name="companyname" type="text" placeholder="Enter company name" value="${contractor.company}">
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Business Number:</label>
		      <div class="col-sm-8">
		        <p class="form-control-static">${contractor.businessNumber}</p>
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">HST Number:</label>
		      <div class="col-sm-8">
		        <p class="form-control-static">${contractor.hstNumber}</p>
		      </div>
		    </div>  
		    <img src="includes/img/bar.png" width="100%" style="padding-bottom:10px;">  
			<div class="form-group">        
		      <div class="col-sm-offset-3 col-sm-8">
		      	<br />
		        <button type="submit" class="btn btn-xs btn-primary" id="submitupdate" name="submitupdate">Update Information</button>
		      </div>
		    </div>
		  </form>
		
	</div>
	
					<div class="alert alert-danger" id="details_error_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    				<div class="alert alert-success" id="details_success_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
					<div class="alert alert-warning" id="details_warning_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
	
	
	<script type="text/javascript">  
  
function SamePhys(checked) {  
          if (checked) {  
                    document.getElementById('maddress1').value = document.getElementById('address1').value;   
                    document.getElementById('maddress2').value = document.getElementById('address2').value;   
                    document.getElementById('mcity').value = document.getElementById('city').value;   
                    document.getElementById('mprovince').value = document.getElementById('province').value;   
                    document.getElementById('mpostalcode').value = document.getElementById('postalcode').value;   
                    
          } else {  
                    document.getElementById('maddress1').value = '';   
                    document.getElementById('maddress2').value = '';   
                    document.getElementById('mcity').value = '';   
                    document.getElementById('mprovince').value = '';   
                    document.getElementById('mpostalcode').value = '';   
          }  
}  
  
</script>
	
 <script src="includes/js/jQuery.print.js"></script>
  