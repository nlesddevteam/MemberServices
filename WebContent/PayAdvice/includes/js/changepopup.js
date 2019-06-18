	function OpenPopUp(pid,eid)
	{
		var check =ajaxRequestInfo(pid,eid);
			if(check == true){
				$(document).ready(function() {
					$('.fancybox').fancybox();
				});
			}
}
    function closewindow()
    {
    	$.fancybox.close(); 
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
     									alert($(this).find("ERROR").text());
     									isvalid=false;
     								}

    						});     					
    					},
    					error: function(xhr, textStatus, error){
    						alert(xhr.statusText);
    						alert(textStatus);
    						alert(error);
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
    	$.fancybox.close();
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
     									alert("Pay Advice Sent");
										isvalid=true;
    	                   		}else{
     									alert("Error Sending Pay Advice");
     									
     							}
     						});
     					},
     					error: function(xhr, textStatus, error){
     						alert(xhr.statusText);
     						alert(textStatus);
     						alert(error);
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
					$('.fancybox').fancybox();
				});
			}
	}
    function closepasswordwindow()
    {
    	$.fancybox.close(); 
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
     									alert($(this).find("MESSAGE").text());
     									isvalid=false;
     							}

     						});     					
     					},
     					error: function(xhr, textStatus, error){
     						alert(xhr.statusText);
     						alert(textStatus);
     						alert(error);
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
    			alert("New Password must be at least 6 characters");
    			return false;
    		}
    		if(newpassword ==oldpassword){
    			alert("New Password must be not be the same as the old password");
    			return false;
    		}
    		if(newpassword != confirmpassword){
				alert("New Password not the same as Confirm Password");
				return false;
			}
	    	if(oldpassword != oldpassworddb){
	    		alert("Current Password incorrect");
	    		return false;
	    	}
	    	var regex = new RegExp("^[a-zA-Z0-9.]*$");
	    	if(!regex.test(newpassword)){
	    		alert("New Password contains special characters");
	    		return false;
	    	}
	    	}else{
	    		alert("Please fill in all fields");
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
 									alert("Password has been updated");
									isvalid=true;
    	                   				
     							}else{
 									alert($(this).find("MESSAGE").text());
 									isvalid=false;
     							}

     						});     					
     					},
     					error: function(xhr, textStatus, error){
     						alert(xhr.statusText);
     						alert(textStatus);
     						alert(error);
     					},
     					dataType: "text",
     					async: false
     				}
     			);
    	return isvalid;
    }
    
   