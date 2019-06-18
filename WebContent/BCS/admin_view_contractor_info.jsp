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
System.out.println(tabs);
String settab="";
if(tabs != null){
	settab="L";
}
pageContext.setAttribute("settab1", settab);
%>
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
    		          		        url: "updateContactInfoAdmin.html",
    		          		        data: frm.serialize(),
    		          		        success: function (xml) {
    		          		        	$(xml).find('CONTRACTOR').each(function(){
    		          							//now add the items if any
    		          							if($(this).find("MESSAGE").text() == "UPDATED")
    		          			 					{
    		          									$('#contractsuccessmessage').html("Contractor Info Updated").css("display","block").delay(4000).fadeOut();
    		          									
    		          			 					}else{
    		          			 						$('#contracterrormessage').html($(this).find("MESSAGE").text());
    		          			 						
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
    		                        message: 'Please select your state'
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
    		    if($("#settab1").val() == "L"){
	      	    	  $('.nav-tabs a:last').tab('show'); 
	      	      }

});
		</script>
		
	<script>
  $("#msameas").on("change", function(){
	 
	  // Same as address click with backup!!
    if (this.checked) {
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
    	//copy physical address to mailing address
	      $("#crfirstname").val($("#firstname").val()).prop("disabled",true);	     
	      $("#crlastname").val($("#lastname").val()).prop("disabled",true);	      
	      $("#crphonenumber").val($("#cellphone").val()).prop("disabled",true);	     
	      $("#cremail").val("${contractor.email}").prop("disabled",true);	    
	          
  
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
    	//copy physical address to mailing address
    	 $("#tofirstname").val($("#firstname").val()).prop("disabled",true);	     
	      $("#tolastname").val($("#lastname").val()).prop("disabled",true);	      
	      $("#tophonenumber").val($("#cellphone").val()).prop("disabled",true);	     
	      $("#toemail").val("${contractor.email}").prop("disabled",true);	       
  
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
    	         
		<div class="BCSHeaderText">Contractor Information for	
		<c:choose>
		<c:when test = "${contractor.company ne null}">${contractor.company} </c:when>
		<c:otherwise>${contractor.firstName} ${contractor.lastName}</c:otherwise>
		</c:choose>
		
		</div>
			  <br />
			  <ul class="nav nav-tabs">
	    		<li class="active"><a data-toggle="tab" href="#contact">Contact Information</a></li>
	    		<li><a data-toggle="tab" href="#company">Company Information</a></li>
	    		<li><a data-toggle="tab" href="#security">Security Information</a></li>
	    		<li><a data-toggle="tab" href="#letters">Letters On File</a></li>
	    		<li><a data-toggle="tab" href="#contracts">Contracts</a></li>
	  		  </ul>
	  		  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="" enctype='multipart/form-data'>
	  			<div class="tab-content">
	  			<div id="contact" class="tab-pane fade in active">
	  				<br />
	  				<div class="form-group">
				      <label class="control-label col-sm-2" for="email">Status:</label>
				      <div class="col-sm-6">
				        				      <c:choose>
	         				<c:when test = "${contractor.status eq 1}">	         				
	         				<span style="background-color:yellow;color:black;padding:3px;text-transform:uppercase;">&nbsp;NOT YET REVIEWED&nbsp;</span>	         				
	            				<p class="form-control-static">${ contractor.statusText}</p>	            				
	         				</c:when>
	         				<c:when test = "${contractor.status eq 2}">	         				
	         				<span style="background-color:green;color:White;padding:3px;text-transform:uppercase;">&nbsp;APPROVED&nbsp;</span>
	            				<p class="form-control-static">${ contractor.statusText}</p>	            				
	         				</c:when>
	                  		<c:when test = "${contractor.status eq 3}">
	                  		
	                  		<span style="background-color:Red;color:White;padding:3px;text-transform:uppercase;">&nbsp;NOT APPROVED&nbsp;</span>
	            				<p class="form-control-static">${contractor.statusText}</p>	         				
	         				</c:when>
	         				<c:when test = "${contractor.status eq 4}">
	         					<span style="background-color:black;color:White;padding:3px;text-transform:uppercase;">&nbsp;SUSPENDED&nbsp;</span>
	         					<p class="form-control-static">${contractor.statusText}</p>	 
	         				</c:when>
	         				<c:otherwise>
	            			<span style="background-color:black;color:White;padding:3px;text-transform:uppercase;">&nbsp;${contractor.statusText}&nbsp;</span>	            			
	            			</c:otherwise>
	      				</c:choose>
				      </div>
				    </div>
				    <span style="font-size:14px;color:Grey;">Company/Business Information</span>
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Company Name:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="companyname" name="companyname" type="text" placeholder="Enter company name" value="${contractor.company}">
				      </div>
				    </div>
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Business Number:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="businessnumber" name="businessnumber" type="text" placeholder="Enter business number" value="${contractor.businessNumber}">
				      </div>
				    </div>
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">HST Number:</label>
				      <div class="col-sm-5">
				        <p class="form-control-static">${contractor.hstNumber}</p>
				          <input class="form-control" id="hstnumber" name="hstnumber" type="text" placeholder="Enter hst number" value="${contractor.hstNumber}">
				      
				      </div>
				    </div>  
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">First Name:</label><input type="hidden" id="cid" name="cid" value="${contractor.id}">
				      <input type="hidden" id="settab1" name="settab1" value="${settab1}">
				      <div class="col-sm-5">
				        <input class="form-control" id="firstname" name="firstname" type="text" placeholder="Enter first name" value="${contractor.firstName}">
				      </div>
				    </div>    
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Last Name:</label><input type="hidden" id="hidfullname" value="${contractor.lastName},${contractor.firstName}">
				      <div class="col-sm-5">
				        <input class="form-control" id="lastname" name="lastname" type="text" placeholder="Enter last name"  value="${contractor.lastName}">
				      </div>
				    </div>    
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Middle Name:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="middlename" name="middlename" type="text" placeholder="Enter middle name" value="${contractor.middleName}">
				      </div>
				    </div>    
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Email:</label>
				      <div class="col-sm-5">
				        <p class="form-control-static">${contractor.email}</p>
				      </div>
				    </div>
				   	<img src="includes/img/bar.png" height=1 width=100%><br/>
				   	<span style="font-size:14px;color:Grey;">Physical Address</span>
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Address 1:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="address1" name="address1" type="text" placeholder="Enter address 1" value="${contractor.address1}">
				      </div>
				    </div>    
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Address 2:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="address2" name="address2" type="text" placeholder="Enter address 2" value="${contractor.address2}">
				      </div>
				    </div>    
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Town/City:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="city" name="city" type="text" placeholder="Enter city" value="${contractor.city}">
				      </div>
				    </div>
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Province:</label>
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
				      <label class="control-label col-sm-2" for="email">Postal Code:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="postalcode" name="postalcode" type="text" placeholder="Enter postal code" value="${contractor.postalCode}">
				      </div>
				    </div>
				    <img src="includes/img/bar.png" height=1 width=100%><br/>
				   	<span style="font-size:14px;color:Grey;">Mailing Address</span>
				   	<div class="form-group">
				   	 
						<label class="control-label col-sm-2" for="email">Same as Physical:</label>
						 <div class="col-sm-5">
						  
						  <input id="msameas" name="msameas" type="checkbox" onclick="MailingSame(this.form)">
						  
							      <c:choose>
		         						<c:when test = "${contractor.msameAs eq 'Y'}">
		         						<script>
				        					$('#msameas').prop('checked', true);
				        					</script>
				        				</c:when>
				        				<c:otherwise>
				        				<script>
				        				$('#msameas').prop('checked', false);
				        				</script>
				        				</c:otherwise>
		      						</c:choose>
				      	</div>
				      		      	
					</div>
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Address 1:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="maddress1" name="maddress1" type="text" placeholder="Enter mailing address 1" value='${contractor.maddress1}'>
				        <input type="hidden" id="maddresstemp1" value="${contractor.maddress1}">
				      </div>
				    </div>    
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Address 2:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="maddress2" name="maddress2" type="text" placeholder="Enter mailing address 2" value="${contractor.maddress2}">
				      <input type="hidden" id="maddresstemp2" value="${contractor.maddress2}">
				      </div>
				    </div>    
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">City:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="mcity" name="mcity" type="text" placeholder="Enter mailing city" value="${contractor.mcity}">
				        <input type="hidden" id="mcitytemp" value="${contractor.mcity}">
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
						<input type="hidden" id="mprovincetemp" value="${contractor.mprovince}">
						</div>
					</div>
										<div class="form-group">
				      <label class="control-label col-sm-2" for="email">Postal Code:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="mpostalcode" name="mpostalcode" type="text" placeholder="Enter mailing postal code" value="${contractor.mpostalCode}">
				        <input type="hidden" id="mpostalcodetemp" value="${contractor.mpostalCode}">
				      </div>
				    </div>		    
				    
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Home Phone:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="homephone" name="homephone" type="text" placeholder="Enter home phone" value="${contractor.homePhone}">
				      </div>
				    </div>
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Cell Phone:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="cellphone" name="cellphone" type="text" placeholder="Enter cell phone" value="${contractor.cellPhone}">
				      </div>
				    </div>     
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">Work Phone:</label>
				      <div class="col-sm-5">
				        <input class="form-control" id="workphone" name="workphone" type="text" placeholder="Enter work phone" value="${contractor.workPhone}">
				      </div>
				    </div>
				</div>
				<div id="company" class="tab-pane fade">
					<br />
					<div class="form-group">
		                <label class="control-label col-sm-4" for="email">Types of Transportation:</label>
		                <label class="checkbox-inline">
		      				<input type="checkbox"  id="tregular" name="tregular" <c:out value="${company.tRegular eq 'Y' ? 'CHECKED' : ''}"/>>Regular 
		    			</label>
		    			<label class="checkbox-inline">
		      				<input type="checkbox"  id="talternate" name="talternate" <c:out value="${company.tAlternate eq 'Y' ? 'CHECKED' : ''}"/>>Alternate
		    			</label>
		    			<input type="hidden" id="cid" name="cid" value="${company.id}">
		                <input type="hidden" id="scid" name="scid" value="${company.contractorId}">
		            </div>
		            <div class="form-group">
		            	<label class="control-label col-sm-4" for="email">I am a parent/guardian of the student</label>
		                <label class="checkbox-inline">
		                	<input type="checkbox"  id="tparent" name="tparent" <c:out value="${company.tParent eq 'Y' ? 'CHECKED' : ''}"/>>
		    			</label>
		            </div>
		            
		             <img src="includes/img/bar.png" height=1 width=100%><br/>
				   	<span style="font-size:14px;color:Grey;">Contractor Representative</span>
		            
		            <div class="form-group">		                
		                <label class="control-label col-sm-3" for="email">Same as contact information</label>
		                <div class="col-sm-9">
		      				<input type="checkbox"  id="crsameas" name="crsameas" <c:out value="${company.crSameAs eq 'Y' ? 'CHECKED' : ''}"/>>
		    			</div>
		            </div>
		            <div class="form-group">
		                <label class="control-label col-sm-3" for="email">First Name:</label>
		                <div class="col-sm-9">
		                    <input class="form-control" id="crfirstname" name="crfirstname" type="text" placeholder="Enter first name" value="${company.crFirstName}">
		                     <input type="hidden" id="crfirstnametemp" value="${company.crFirstName}">
		                </div>
		            </div>
		            <div class="form-group">
		                <label class="control-label col-sm-3" for="email">Last Name:</label>
		                <div class="col-sm-9">
		                    <input class="form-control" id="crlastname" name="crlastname" type="text" placeholder="Enter last name" value="${company.crLastName}">
		                    <input type="hidden" id="crlastnametemp" value="${company.crLastName}">
		                </div>
		            </div>
		            <div class="form-group">
		                <label class="control-label col-sm-3" for="email">Mobile Phone:</label>
		                <div class="col-sm-9">
		                    <input class="form-control" id="crphonenumber" name="crphonenumber" type="text" placeholder="Enter mobile phone" value="${company.crPhoneNumber}">
		                    <input type="hidden" id="crphonenumbertemp" value="${company.crPhoneNumber}">
		                </div>
		            </div>
		            <div class="form-group">
		                <label class="control-label col-sm-3" for="email">Email:</label>
		                <div class="col-sm-9">
		                    <input class="form-control" id="cremail" name="cremail" type="text" placeholder="Enter email" value="${company.crEmail}">
		                    <input type="hidden" id="cremailtemp" value="${company.crEmail}">
		                </div>
		            </div>
		            <img src="includes/img/bar.png" height=1 width=100%><br/>
				   	<span style="font-size:14px;color:Grey;">Transportation Officer</span>
		             <div class="form-group">
		              <label class="control-label col-sm-3" for="email">Same as contact information</label>
		      				<div class="col-sm-9">
		      				<input type="checkbox"  id="tosameas" name="tosameas" <c:out value="${company.toSameAs eq 'Y' ? 'CHECKED' : ''}"/>>
		      				</div>
		             </div>
		            
		            <div class="form-group">
		                <label class="control-label col-sm-3" for="email">First Name:</label>
		                <div class="col-sm-9">
		                    <input class="form-control" id="tofirstname" name="tofirstname" type="text" placeholder="Enter first name" value="${company.toFirstName}">
		                    <input type="hidden" id="tofirstnametemp" value="${company.toFirstName}">
		                </div>
		            </div>
		            <div class="form-group">
		                <label class="control-label col-sm-3" for="email">Last Name:</label>
		                <div class="col-sm-9">
		                    <input class="form-control" id="tolastname" name="tolastname" type="text" placeholder="Enter last name" value="${company.toLastName}">
		                    <input type="hidden" id="tolastnametemp" value="${company.toLastName}">
		                </div>
		            </div>
		            <div class="form-group">
		                <label class="control-label col-sm-3" for="email">Mobile Phone:</label>
		                <div class="col-sm-9">
		                    <input class="form-control" id="tophonenumber" name="tophonenumber" type="text" placeholder="Enter mobile phone" value="${company.toPhoneNumber}">
		                    <input type="hidden" id="tophonenumbertemp" value="${company.toPhoneNumber}">
		                </div>
		            </div>
		            <div class="form-group">
		                <label class="control-label col-sm-3" for="email">Email:</label>
		                <div class="col-sm-9">
		                    <input class="form-control" id="toemail" name="toemail" type="text" placeholder="Enter email" value="${company.toEmail}">
		                    <input type="hidden" id="toemailtemp" value="${company.toEmail}">
		                </div>
		            </div>
		    	</div>
			 	<div id="security" class="tab-pane fade">
			 		<br />
			 		<div class="form-group">
				      <label class="control-label col-sm-3" for="email">Email:</label>
				      <div class="col-sm-9">
				        <p class="form-control-static">${contractor.email}</p>
				      </div>
				    </div> 
				    <div class="form-group">
				      <label class="control-label col-sm-3" for="email">Security Question:</label>
				      <div class="col-sm-9">
				        <p class="form-control-static">${sec.securityQuestion}</p>
				      </div>
				    </div>    
				    <div class="form-group">
				      <label class="control-label col-sm-3" for="email">Answer:</label>
				      <div class="col-sm-9">
				        <p class="form-control-static">${sec.sqAnswer}</p>
				      </div>
				    </div>
				    <div class="form-group">
				    	<label class="control-label col-sm-4" for="email">
				      		<button type="button" class="btn btn-xs btn-info" onclick="resetPassword();">Reset Password</button>
				    	</label>
				    </div>
				    
		    	</div>
		    			<div id="letters" class="tab-pane fade">
		    			<div class="alert alert-danger" id="letters_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
           				<div class="alert alert-success" id="letters_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
		    			<p>
		    				<div align="right"><button type="button" class="btn btn-xs btn-success" onclick="openaddnewdialoglet('C');">Add New Document</button></div>
				<br/>
				  <div id="BCS-Search">
				 	<table id="BCS-table" width="100%" class="BCSTable">
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
			      		<c:set var="countDocs" value="${countDocs + 1}" />
		 					<tr style="border-bottom:1px solid silver;">
		      					<td class="field_content">${rule.lName}</td>
		      					<td class="field_content">${rule.notes}</td>
		      					<td class="field_content">${rule.dateAddedFormatted}</td>
		      					<td align="right" class="field_content">
		      					<button type="button" class="btn btn-xs btn-primary" onclick="window.open('${spath}${rule.viewPath}','_blank');">View</button>
		      					<button type="button" class="btn btn-xs btn-danger" onclick="opendeleteletterdialog('${rule.lName}','${rule.id}','C');">Del</button>
		      						<!-- <a href='${spath}${rule.viewPath}' class="menuBCS" target="_blank"><img src="includes/img/viewsm-off.png" class="img-swap" title="View Training border=0 style="padding-top:3px;padding-bottom:3px;"></a> &nbsp; 
									<a class='edit' href="#" onclick="opendeleteletterdialog('${rule.lName}','${rule.id}','C');"><img src="includes/img/deletesm-off.png" class="img-swap" border=0 title="Delete Letter" style="padding-top:3px;padding-bottom:3px;"></a>-->
								
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
	      		<img src="includes/img/bar.png" width="100%" style="padding-bottom:10px;">
	      			      		
	      		
	      		  <c:choose>
      	<c:when test="${countDocs >0 }">
      		<script>$('#letters_success_message').html("There are <b>${countDocs}</b> on file.").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-table').css("display","none");$('#letters_error_message').html("Sorry, there are no letters currently on file for this contractor at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>
	      		 
	      		</div>
	    </div>
	    <div id="contracts" class="tab-pane fade">
	    			<table id="BCS-table" width="100%" class="BCSTable">
     		 <thead>
	     		<tr style="border-bottom:1px solid grey;" class="listHeader">
	      		<th width="20%" class="listdata">Name</th>
	      		<th width="15%" class="listdata">Type</th>
	      		<th width="15%" class="listdata">Start Date</th>
	      		<th width="20%" class="listdata">Expiry Date</th>
	      		<th width="20%" class="listdata">Status</th>
	      		<th width="10%" class="listdata">Options</th>
	      		</tr>
	      		</thead>
	      		<tbody>
	      	<c:choose>
	      		<c:when test="${fn:length(contracts) > 0}">
		      		<c:forEach items="${contracts}" var="rule">
		      		<c:set var="countContracts" value="${countContracts + 1}" />
	 					<tr style="border-bottom:1px solid silver;">
	      					<td class="field_content">${rule.contractName}</td>
	      					<td class="field_content">${rule.contractTypeString}</td>
	      					<td class="field_content">${rule.contractStartDateFormatted}</td>
	      					<td class="field_content">${rule.contractExpiryDateFormatted}</td>
	      					<td class="field_content">	      					
	      					<c:choose>
		         				<c:when test = "${rule.contractHistory.statusString eq 'Cancelled'}">
		         				<span style="background-color:Red;color:White;padding:3px;">&nbsp;${rule.contractHistory.statusString}&nbsp;</span>
		            			</c:when>
		         				<c:when test = "${rule.contractHistory.statusString eq 'Approved'}">
		         				<span style="background-color:Green;color:white;padding:3px;">&nbsp;${rule.contractHistory.statusString}&nbsp;</span>
		         				</c:when>
		                  		<c:when test = "${rule.contractHistory.statusString eq 'Suspended'}">
		                  		<span style="background-color:Black;color:white;padding:3px;">&nbsp;${rule.contractHistory.statusString}&nbsp;</span>
		         				</c:when>
		         				<c:otherwise>
		            				<span style="background-color:Yellow;color:black;padding:1px;">&nbsp;${rule.contractHistory.statusString}&nbsp;</span>
		         				</c:otherwise>
		      				</c:choose>
	      					
	      					</td>
	      					<td align="right" class="field_content">
	      					 <button type="button" class="btn btn-xs btn-primary menuBCS" onclick="closeMenu();loadMainDivPage('viewContractInformation.html?cid=${rule.id}');">View</button>
	      					</td>
	      				</tr>
	        		</c:forEach>
	        	</c:when>
	        	<c:otherwise>
	        		<tr><td colspan='6' style="color:Red;">No contracts found.</td></tr>
	        	</c:otherwise>
	        </c:choose>
	        </tbody>
	      </table>
	     </div>
		    	</div>
		    	<img src="includes/img/bar.png" height=1 width=100%><br/>
		    		<div class="form-group">        
				      <div class="col-sm-offset-2 col-sm-10">
				      	<br />
				      	<div class="alert alert-danger" id="contracterrormessage" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    					<div class="alert alert-success" id="contractsuccessmessage" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
				      	
				      	
				        <button type="submit" class="btn btn-xs btn-primary id="submitupdate" name="submitupdate">Update Information</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				        <c:if test = "${contractor.status == 1 || contractor.status == 3}">
				        	<esd:SecurityAccessRequired permissions="BCS-APPROVE-REJECT">
		        				<button type="button" class="btn btn-xs btn-success" onclick="openApprove();">Approve</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		        			</esd:SecurityAccessRequired>
		      			</c:if>
		      			<c:if test = "${contractor.status eq 1}">
		      				<esd:SecurityAccessRequired permissions="BCS-APPROVE-REJECT">
		        				<button type="button" class="btn btn-xs btn-danger" onclick="openReject();">Reject</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		        			</esd:SecurityAccessRequired>
		      			</c:if>
		      			<c:if test = "${contractor.status eq 2}">
		      				<esd:SecurityAccessRequired permissions="BCS-SUSPEND-UNSUSPEND">
		        				<button type="button" class="btn btn-xs btn-warning" onclick="openSuspend();">Suspend</button>
		        			</esd:SecurityAccessRequired>
		      			</c:if>
		      			<c:if test = "${contractor.status eq 4}">
		      				<esd:SecurityAccessRequired permissions="BCS-SUSPEND-UNSUSPEND">
		        				<button type="button" class="btn btn-xs btn-primary" onclick="openUnSuspend();">Unsuspend</button>
		        			</esd:SecurityAccessRequired>
		      			</c:if>
				      </div>
				    </div>
			  </form>
		
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
      	<button type="button" class="btn btn-xs btn-default" onclick="approverejectcontractor();">Ok</button>
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
