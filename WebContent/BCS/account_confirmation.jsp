<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Bussing Contractor Registration</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">  
		    <meta charset="utf-8">
		    <META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrapvalidator.min.js"></script>
 <link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/bcs.js"></script>
<script type="text/javascript">

   $(document).ready(function() {
	   $("#error_message").hide();
	   $("#success_message").hide();
	   //check for error message
	   if($("#msg").val() != ""){
		   $("#error_message").text($("#msg").val());
		   $("#error_message").show();
	   }
    $('#contact-form').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        submitHandler: function(validator, form, submitButton) {
          //$('#success_message').slideDown({ opacity: "show" }, "slow"); // Do something ...
          			$('#contact-form').data('bootstrapValidator').resetForm();
          		var frm = $('#contact-form');
          			$.ajax({
          		        type: "POST",
          		        url: "confirmNewAccountAjax.html",
          		        data: frm.serialize(),
          		        success: function (xml) {
          		        	$(xml).find('CONTRACTOR').each(function(){
          							//now add the items if any
          							if($(this).find("MESSAGE").text() == "CONFIRMED")
          			 					{
          									$('#success_message').css("display","block").delay(6000).fadeOut();
          									$("#error_message").hide();
          									$("#divcontainer").hide();
          			 					}else{
          			 						$('#error_message').text($(this).find("MESSAGE").text()).css("display","block").delay(6000).fadeOut();
          			 						$("#success_message").hide();
          			 						$('#error_message').show();
          			 					}

          							});
          		        },
          		        error: function (xml) {
          		            isvalid=false;
          		        },
          		    });
        },
        fields: {
        	tpassword: {
                validators: {
                        notEmpty: {
                        message: 'Please enter temporary password'
                    },callback: {
                        message: 'Temporary Password Invalid',
                        callback: function (value, validator, $field) {
                            // Determine the numbers which are generated in captchaOperation
                        	var tpassword = $("#hidid").val();
                    	   	return tpassword == value;
                        }
                    }
                }
            },
             npassword: {
                validators: {
                     stringLength: {
                        min: 7,
                        message: 'Password must be 7 characters or more'
                    },
                    notEmpty: {
                        message: 'Please enter new password'
                    }
                }
            },	
       	 	cnpassword: {
             	validators: {
                 	notEmpty: {
                     message: 'Please enter confirm new password'
                 },
                 identical: {
                     field: 'npassword',
                     message: 'New password and confirm new password do not match'
                 }
             }
         	},
             squestion: {
                validators: {
                     stringLength: {
                        min: 5,
                        message: 'Security must be 5 characters or more'
                    },
                    notEmpty: {
                        message: 'Please enter security question'
                    }
                }
            },
             sqanswer: {
                validators: {
                     stringLength: {
                        min: 5,
                        message: 'Answer must be  characters or more'
                    },
                    notEmpty: {
                        message: 'Please enter answer'
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
				<img src="includes/img/header.png" alt="" width="100%" border="0"><br/>	
				<div align="left" style="background-color:#ffd333;width:100%;height:5px;"></div>				
			</div>
			<div class="col full_block content">
					<div class="bodyText">
					
					<div id="loadingSpinner" style="display:none;"><div id="spinner"><img src="includes/img/animated-bus.gif" width="200" border=0><br/>Transporting data, please wait...<div id="secondMessage" style="display:none;"></div></div></div>
					        
    									
						<div id="printJob">
							<div id="pageContentBody" style="width:100%;">
							
							<div class="BCSHeaderText">Bussing Contractor Account Confirmation</div>



<div  class="alert alert-success" role="alert" id="success_message"> Thank you for confirming your account.  Please <a href='http://www.nlesd.ca/MemberServices/BCS/contractorlogin.html'>click here</a> to login</div>                   	      
<div class="alert alert-danger" role="alert" id="error_message"></div> 
    
<div  id="divcontainer">

  <form class="form-horizontal"  id="contact-form" name="contact-form">
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Name:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.lastName},${contractor.firstName}</p>
      </div>
    </div>    
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Email:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.email}</p>
      </div>
    </div>    
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Company:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.company}</p>
      </div>
    </div>    
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Temporary Password:</label>
      <input type="hidden" id="hidid" name="hidid" value="${contractor.secBean.password}">
      <input type="hidden" id="hidcid" name="hidcid" value="${contractor.id}">
      <input type="hidden" id="hidcsid" name="hidcsid" value="${contractor.secBean.id}">
      <input type="hidden" id="msg" name="msg" value="${msg}">
      <div class="col-sm-5">
        <input type="password" class="form-control" id="tpassword" placeholder="Enter temporary password" name="tpassword">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">New Password:</label>
      <div class="col-sm-5">
        <input type="password" class="form-control" id="npassword" name="npassword"  placeholder="Enter new password">
      </div>
    </div>    
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Confirm New Password:</label>
      <div class="col-sm-5">
        <input type="password" class="form-control" id="cnpassword" name="cnpassword"  placeholder="Enter confirm new password">
      </div>
    </div>    
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Security Question:</label>
      <div class="col-sm-10">
        <input class="form-control" id="squestion" name="squestion" type="text" placeholder="Enter security question">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Anwser:</label>
      <div class="col-sm-5">
        <input class="form-control" id="sqanswer" name="sqanswer" type="text" placeholder="Enter answer">
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
      	<br />
        <button type="submit" class="btn btn-default">Confirm Account</button>
      </div>
    </div>
  </form>
</div>
</div>
						<br/>&nbsp;<br/> 	
							
							<div style="clear:both;"></div>
						<div class="alert alert-warning" style="display:none;" id="divmessage"></div>
						
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


<script src="includes/js/jQuery.print.js"></script>	
</body>
</html>