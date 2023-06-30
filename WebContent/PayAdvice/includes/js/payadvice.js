	function OpenPopUp(pid,eid)
	{
		var check =ajaxRequestInfo(pid,eid);
			if(check == true){
				$(document).ready(function() {
					$('#emailPayInfo').modal('show');
				});
			}
}
    function closewindow()
    {
    	$('#emailPayInfo').modal('hide');
    }
    function ajaxRequestInfo(pid,eid)
    {
    	var isvalid=false;
    	$.ajax(
    				{
    					type: "POST",  
    					url: "getNLESDEmployeePayAdviceInfo.html",
    					data: {
    						empid: eid,payid:pid
    					}, 
    					success: function(xml){
    						$(xml).find('EMPLOYEE').each(function(){
    							if($(this).find("MESSAGE").text() == "NO ERROR"){
     									var empname = $(this).find("EMPNAME").text();
    	                   				$("#empname").text(empname);
                        				var empemail = $(this).find("EMAIL").text();
    	                   				$("#empemail").text(empemail);
                        				var payperiod = $(this).find("PPERIOD").text();
    	                   				$("#payperiod").text(payperiod);
                        				$("#hidEID").val(eid);
                        				$("#hidPID").val(pid);
                        				$("#hidEMAIL").val(empemail);
										isvalid=true;
    	                   				
     								}else{     									     									
     									$(".msgERR").css("display","block").append($(this).find("ERROR").text());
     									isvalid=false;
     								}

    						});     					
    					},
    					error: function(xhr, textStatus, error){
    						$(".msgERR").css("display","block").append(xhr.statusText);
							$(".msgERR").css("display","block").append(textStatus);
							$(".msgERR").css("display","block").append(error);
    					},
    					dataType: "text",
    					async: false
    				}
     			);
    	return isvalid;
    }
    function sendinfo()
    {
    	if(ajaxSendInfo()){
    	$('#emailPayInfo').modal('hide');
    	}
    }
    function ajaxSendInfo()
    {
    	var isvalid=false;
    	var pid = $.trim($('#hidPID').val());
    	var eid = $.trim($('#hidEID').val());
    	var eemail = $.trim($('#hidEMAIL').val());
    	$.ajax(
     				{
     					type: "POST",  
     					url: "sendNLESDEmployeePayAdviceInfo.html",
     					data: {
     						empid: eid,payid:pid,email:eemail
     					}, 
     					success: function(xml){
     						$(xml).find('EMPLOYEE').each(function(){
     							if($(this).find("MESSAGE").text() == "MESSAGE SENT"){     									
     									$(".msgOK").css("display","block").append("Pay Advice Sent");
										isvalid=true;
    	                   		}else{
     									$(".msgERR").css("display","block").append("Error Sending Pay Advice");     								
     									
     							}
     						});
     					},
     					error: function(xhr, textStatus, error){
     						$(".msgERR").css("display","block").append(xhr.statusText);
							$(".msgERR").css("display","block").append(textStatus);
							$(".msgERR").css("display","block").append(error); 
     					},
     					dataType: "text",
     					async: false
     				}
     			);
    	return isvalid;
    }
	function OpenPopUpPassword(pid)
	{
		var check =ajaxRequestPassword(pid);
			if(check == true){
				$(document).ready(function() {
					$('#passChangeModal').modal('show');
				});
			}
	}
    function closepasswordwindow()
    {
    	$('#passChangeModal').modal('hide');
    }
    function ajaxRequestPassword(pid)
    {
    	var isvalid=false;
    	$.ajax(
     				{
     					type: "POST",  
     					url: "getEmployeePasswordInfo.html",
     					data: {
     						payid:pid
     					}, 
     					success: function(xml){
     						$(xml).find('EMPLOYEE').each(function(){
     							if($(this).find("MESSAGE").text() == "SUCCESS"){
     									var payrollid = $(this).find("PAYROLLID").text();
    	                   				var oldpassword = $(this).find("PASSWORD").text();
    	                   				$("#hidPID").val(pid);
                        				$("#hidPASSWORD").val(oldpassword);
                        				isvalid=true;
    	                   				
     							}else{
									 
									$(".msgERR").css("display","block").append($(this).find("MESSAGE").text());     									
     									isvalid=false;
     							}

     						});     					
     					},
     					error: function(xhr, textStatus, error){
							$(".msgERR").css("display","block").append(xhr.statusText);
							$(".msgERR").css("display","block").append(textStatus);
							$(".msgERR").css("display","block").append(error);     						
     					},
     					dataType: "text",
     					async: false
     				}
     			);
    	return isvalid;
    }
    function updatepassword()
    {
    	var pid = $.trim($('#hidPID').val());
    	var oldpassworddb = $.trim($('#hidPASSWORD').val());
    	var oldpassword = $.trim($('#current_password').val());
    	var newpassword = $.trim($('#new_password').val());
    	var confirmpassword = $.trim($('#confirm_password').val());
    	if(oldpassword.length > 0 && newpassword.length >0 && confirmpassword.length > 0){
    		if(newpassword.length < 6){
    			$(".msgERR").css("display","block").append("New Password must be at least 6 characters");
    			return false;
    		}
    		if(newpassword ==oldpassword){
				$(".msgERR").css("display","block").append("New Password must be not be the same as the old password");
    			return false;
    		}
    		if(newpassword != confirmpassword){
				$(".msgERR").css("display","block").append("New Password not the same as Confirm Password");
				return false;
			}
	    	if(oldpassword != oldpassworddb){
	    		$(".msgERR").css("display","block").append("Current Password incorrect");
	    		return false;
	    	}
	    	var regex = new RegExp("^[a-zA-Z0-9.]*$");
	    	if(!regex.test(newpassword)){
	    		$(".msgERR").css("display","block").append("New Password contains special characters");
	    		return false;
	    	}
	    	}else{
	    		$(".msgERR").css("display","block").append("Please fill in all fields");
	    		return false;
	    	}
	    	var check=ajaxSendPasswordUpdate();
	    	if(check){
	    		closepasswordwindow();
	    	}
    	
    	
    }
    function ajaxSendPasswordUpdate()
    {
    	var pid = $.trim($('#hidPID').val());
    	var newpassword = $.trim($('#new_password').val());
    	var isvalid=false;
    	$.ajax(
     				{
     					type: "POST",  
     					url: "updateEmployeePassword.html",
     					data: {
     						payid:pid,password:newpassword
     					}, 
     					success: function(xml){
     						$(xml).find('EMPLOYEE').each(function(){
     							if($(this).find("MESSAGE").text() == "SUCCESS"){ 									
 									$(".msgOK").css("display","block").append("SUCCESS: Password has been updated");
									isvalid=true;
    	                   				
     							}else{
									 $(".msgERR").css("display","block").append($(this).find("MESSAGE").text()); 									
 									isvalid=false;
     							}

     						});     					
     					},
     					error: function(xhr, textStatus, error){
     						$(".msgERR").css("display","block").append(xhr.statusText);
							$(".msgERR").css("display","block").append(textStatus);
							$(".msgERR").css("display","block").append(error);  
     					},
     					dataType: "text",
     					async: false
     				}
     			);
    	return isvalid;
    }
    
   