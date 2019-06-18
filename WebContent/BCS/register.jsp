<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<html>
<head>

<title>Busing Operator Registration</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">  
		    <meta charset="utf-8">
		    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">    		
    		<link href="includes/css/jquery-ui.css" rel="stylesheet" type="text/css"> 
    		<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">  	
   			<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
   			<link href="includes/css/bootstrap.min.css.map" rel="stylesheet" type="text/css">
   			<!-- For mini-icons in menu -->
   			<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">			
    		<script src="includes/js/jquery.min.js"></script>		
    		<script src="includes/js/jquery-ui.js"></script>
    		<script src="includes/js/bootstrap.min.js"></script>
    		<script src="includes/js/jquery.maskedinput.min.js"></script>
			<script src="includes/js/bootstrapvalidator.min.js"></script>
    		<script src="includes/js/bcs.js"></script>
				<script>
				
				jQuery(function(){
					  $(".img-swap").hover(
					          function(){this.src = this.src.replace("-off","-on");},
					          function(){this.src = this.src.replace("-on","-off");});
					 });
				
				
				</script>
 
<script type="text/javascript">

   $(document).ready(function() {
	   
    $('#contact-form').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        submitHandler: function(validator, form, submitButton) {
          $('#contact-form').data('bootstrapValidator').resetForm();var frm = $('#contact-form');
          			$.ajax({
          		        type: "POST",
          		        url: "submitNewBussingContractor.html",
          		        data: frm.serialize(),
          		        success: function (xml) {
          		        	$(xml).find('CONTRACTOR').each(function(){
          							//now add the items if any
          							if($(this).find("MESSAGE").text() == "SUCCESS")
          			 					{
          									$('#success_message').html("Thank you for your submission, you will recieve an email with further details.").css("display","block").delay(8000).fadeOut();          									
          									$("#btnsubmit").css("display","none");
          			 					}else{
          			 						$('#error_message').text($(this).find("MESSAGE").text()).css("display","block").delay(8000).fadeOut();         			 					
          			 						
          			 					};

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
       	 	email: {
             	validators: {
                 	notEmpty: {
                     message: 'Please supply your email address'
                 },
                 emailAddress: {
                     message: 'Please supply a valid email address'
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
            },
            businessnumber: {
                validators: {
                    notEmpty: {
                        message: 'Please supply your business number'
                    }
                }
            },
            hstnumber: {
                validators: {
                    notEmpty: {
                        message: 'Please supply your hst number'
                    }
                }
            }
        }
       });

   });

   </script>
</head>
<body>
 <div class="mainContainer">
  	   	<div class="section group">	   		
	   		<div class="col full_block topper" align="center">
	   			<script src="includes/js/date.js"></script>	   		
			</div>			
			<div class="full_block center">
				<img src="includes/img/header-operator.png" alt="Student Transportation Management System for Operators and Contractors" width="100%" border="0"><br/>	
				<div align="left" style="background-color:#ffd333;width:100%;height:5px;"></div>				
			</div>
			<div class="col full_block content">
					<div class="bodyText">
					
					<div id="loadingSpinner" style="display:none;"><div id="spinner"><img src="includes/img/animated-bus.gif" width="200" border=0><br/>Transporting data, please wait...<div id="secondMessage" style="display:none;"></div></div></div>
					<div style="float:right;padding-right:5px;color:Silver;text-align:right;padding-top:5px;">
					<button type="button" class="btn btn-xs btn-primary menuBCS" onclick="window.location='contractorLogin.html';">Back</button></div>
		      								   
    									
						<div id="printJob">
							<div id="pageContentBody" style="width:100%;">
							
							<div class="BCSHeaderText">New Operator Registration</div>
							
							<br/>To apply to be a NLESD Busing Contractor/Operator, please fill out the form below. Once submitted, you will receive a confirmation email later the same day, and account approval may take a few days to process. 
							
							Please do not apply more than once.
					
							
							
                           


<br/><br/><div style="max-width:600px;">
  <form class="form-horizontal" id="contact-form" name="contact-form">
	    <div class="form-group">
	      <label class="control-label col-sm-3" for="email">First Name:</label>
	      <div class="col-sm-9">
	      <input class="form-control" id="firstname" name="firstname" type="text" placeholder="Enter first name">
	      </div>
	   </div>   
    
    	<div class="form-group">
	      <label class="control-label col-sm-3" for="email">Middle Name:</label>
	      <div class="col-sm-9"><input class="form-control" id="middlename" name="middlename" type="text" placeholder="Enter middle name"></div>
	     </div> 
   
    	<div class="form-group">
	    <label class="control-label col-sm-3" for="email">Last Name:</label>
	    <div class="col-sm-9"><input class="form-control" id="lastname" name="lastname" type="text" placeholder="Enter last name"></div>
	    </div> 
      
      
    <div class="form-group">
      <label class="control-label col-sm-3" for="email">Email:</label>
      <div class="col-sm-9"><input type="email" class="form-control" id="email" placeholder="Enter email" name="email"></div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-3" for="email">Address 1:</label>
      <div class="col-sm-9"><input class="form-control" id="address1" name="address1" type="text" placeholder="Enter address 1"></div>
    </div>    
    <div class="form-group">
    	<label class="control-label col-sm-3" for="email">Address 2:</label>
    	<div class="col-sm-9"><input class="form-control" id="address2" name="address2" type="text" placeholder="Enter address 2"></div>
    </div>    
    <div class="form-group">
     	<label class="control-label col-sm-3" for="email">Town/City:</label>
      	<div class="col-sm-9"><input class="form-control" id="city" name="city" type="text" placeholder="Enter city"></div>
    </div>
    
    <div class="form-group">
      <label class="control-label col-sm-3" for="email">Province:</label>
      <div class="col-sm-9"><select class="form-control"  id="province" name="province">
			<option value=" ">Select province</option>
			<option value="AB">Alberta</option>
			<option value="BC">British Columbia</option>
			<option value="MB">Manitoba</option>
			<option value="NB">New Brunswick</option>
			<option value="NL" selected>Newfoundland and Labrador</option>
			<option value="NS">Nova Scotia</option>
			<option value="ON">Ontario</option>
			<option value="PE">Prince Edward Island</option>
			<option value="QC">Quebec</option>
			<option value="SK">Saskatchewan</option>
			<option value="NT">Northwest Territories</option>
			<option value="NU">Nunavut</option>
			<option value="YT">Yukon</option>
		</select></div>
	</div>
	
    <div class="form-group">
      <label class="control-label col-sm-3" for="email">Postal Code:</label>
      <div class="col-sm-9"><input class="form-control" id="postalcode" name="postalcode" type="text" placeholder="Enter postal code"></div>
    </div>
    
    <div class="form-group">
      <label class="control-label col-sm-3" for="email">Home Phone:</label>
      <div class="col-sm-9"><input class="form-control" id="homephone" name="homephone" type="text" placeholder="Enter home phone"></div>
    </div>
    
    <div class="form-group">
      <label class="control-label col-sm-3" for="email">Cell Phone:</label>
      <div class="col-sm-9"><input class="form-control" id="cellphone" name="cellphone" type="text" placeholder="Enter cell phone"></div>
    </div>
         
    <div class="form-group">
      <label class="control-label col-sm-3" for="email">Work Phone:</label>
      <div class="col-sm-9"><input class="form-control" id="workphone" name="workphone" type="text" placeholder="Enter work phone"></div>
    </div>
    
    <div class="form-group">
      <label class="control-label col-sm-3" for="email">Company Name:</label>
      <div class="col-sm-9"><input class="form-control" id="companyname" name="companyname" type="text" placeholder="Enter company name"></div>
    </div>
    
    <div class="form-group">
      <label class="control-label col-sm-3" for="email">Business Number:</label>
      <div class="col-sm-9"><input class="form-control" id="businessnumber" name="businessnumber" type="text" placeholder="Enter business number"></div>
    </div>
     
    <div class="form-group">
      <label class="control-label col-sm-3" for="email">HST Number:</label>
      <div class="col-sm-9"><input class="form-control" id="hstnumber" name="hstnumber" type="text" placeholder="Enter hst number"></div>
    </div> 
   					<div class="alert alert-success" id="success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
					<div class="alert alert-warning" id="approval_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
					<div class="alert alert-warning" id="error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"><span id="errorspan"></span></div>
					
    <div class="form-group">       
      <div class="col-sm-offset-2 col-sm-10">
      	<br />
        <button type="submit" class="btn btn-xs btn-primary" id="btnsubmit">Submit Registration</button>
      </div>
    </div>
  </form>
  </div>
</div>
						<br/>&nbsp;<br/> 	
							
							<div style="clear:both;"></div>
						<div class="alert alert-warning" style="display:none;"></div>
						
						<div class="alert alert-info no-print">						
						 		<div class="acrobatMessage"><a href="http://get.adobe.com/reader/"><img src="includes/img/adobereader.png" width="150" border="0"></a></div>							
	  							Documents on this site require Adobe Acrobat&reg; Reader. Adobe Acrobat Reader software is used for viewing Adobe&reg; PDF documents. 
								Click on the Get ADOBE Reader icon to download the latest Acrobat&reg; Reader for free.							
									
  					
  					</div>			
							
						</div>
					</div>
					<div class="section group">
						<div class="col full_block copyright">NLBusingApp 1.0 &copy; 2017 Newfoundland and Labrador English School District</div>
					</div>
			</div>
		</div>
	</div>

<script>
$('#form-signin').submit(function() {
	
	$("#loadingSpinner").css("display","inline");
});

</script>



  </body>
</html>