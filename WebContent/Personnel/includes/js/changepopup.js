	function OpenPopUp()
	{
		//first we check to see if they entered an email address
				
		var fieldvalue = $.trim($('#email').val());
		if(fieldvalue == null || fieldvalue =='' )
			{
			alert("Please enter your email adddress!");
			return false;
			}else{
				var check =ajaxRequestInfo();
				if(check == true)
					{
						//show box
						$(document).ready(function() {
							$('.fancybox').fancybox();
						});
					}else{
						//do nothing error was shown already
					}
			}


			
		
	}
	
    function ajaxRequestInfo()
    {
    	var fieldvalue = $.trim($('#email').val());
    	var isvalid=false;
    	
    	
    	$.ajax(
     			{
     				type: "POST",  
     				url: "getSecurityQuestion.html",
     				data: {
     					email: fieldvalue
     				}, 
     				success: function(xml){
     					

     					
     					$(xml).find('INFO').each(function(){
     							
     							if($(this).find("ERROR").text() == "No Error")
     								{
     								
             	                		var question = $(this).find("SQUESTION").text();
    	                   				$("#securityquestion").val(question);
                        				var answer = $(this).find("SANSWER").text();
    	                   				$("#csecurityanswer").val(answer);
										isvalid=true;
    	                   				
     								}else{
     									alert($(this).find("ERROR").text());
     									
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
    
    function checkanswer()
    {
    	
    	var seca = $.trim($('#securityanswer').val());
    	var secac = $.trim($('#csecurityanswer').val());

    	
    	if(seca == secac)
    		{
    		

    	    document.getElementById("step1").style.display = 'none';
    	    document.getElementById("step2").style.display = 'block';
    	   
    	    
    		
    		}else{
    			//show message
    			alert("Sorry the answer does not match the value in the system.");
    		}
    }
    
    function checkpassword()
    {
    	
    	var pwd = $.trim($('#newpassword').val());
    	var cpwd = $.trim($('#confirmpassword').val());


    	
    	if(pwd == cpwd)
    		{
    		//call the ajax function to update the values
    		if (ajaxUpdateInfo())
    		{
    			//hide the password div and show the confirmation
        		//alert("cchnaged");
        	    document.getElementById("step2").style.display = 'none';
        	    document.getElementById("step3").style.display = 'block';
    			
    		}else{
    			//alert("notcchnaged");
    		}

    	    
    	    
    		
    	}else{
    			//show message
    		alert("The Password and Confirm Passsword does not match.");
    	}
    	
    }
    
    function ajaxUpdateInfo()
    {
    	var pwd = $.trim($('#newpassword').val());
    	var cpwd = $.trim($('#confirmpassword').val());
    	var email = $.trim($('#email').val());
    	var isupdated=false;
    	$.ajax(
     			{
     				type: "POST",  
     				url: "updateApplicantPassword.html",
     				data: {
     					password: pwd,email:email
     				}, 
     				success: function(xml){
     					$(xml).find('INFO').each(function(){
     							
     							
     							if($(this).find("MESSAGE").text() == "PASSWORDUPDATED")
     								{
										isupdated=true;
     								}else{
     									alert("Error updating your password.");
     									
     								}
						});

     					
     				},
     				  error: function(xhr, textStatus, error){
     				      alert("Status:" + xhr.statusText + "  " + "Text:" +textStatus + "  " + "Error:" + error );

     				  },
     				dataType: "text",
     				async: false
     			}
     		);
    	

			return isupdated;
		
		
		

    }
    function closewindow()
    {
    	//blank fields
    	$("#securityquestion").val("");
    	$("#securityanswer").val("");
    	$("#csecurityanswer").val("");
    	$("#newpassword").val("");
    	$("#confirmpassword").val("");
    	
    	document.getElementById("step1").style.display = 'block';
    	document.getElementById("step2").style.display = 'none';
    	document.getElementById("step3").style.display = 'none';
    	$.fancybox.close(); 
    }
    
	function OpenReferencePopUp(appid)
	{
		$(document).ready(function() {
			$("#uid").val(appid);
			$('.fancybox').fancybox();
		});

		
	}
	function OpenRecommendationPopUp()
	{
		  //show box
						$(document).ready(function() {
							$('.fancybox').fancybox({autoDimensions: true});
						});
	}
	//function to check add new request to hire
	function CheckRequestToHire(){
		$("#spanmessage").html("");
		$("#errorMessage").hide();
		//job title
		var jt = $("#job_title").val();
		if(jt == ""){
			$("#spanmessage").html("Please enter Job Title");
			$("#errorMessage").show();
			return false;
		}
		jt = $("#location").val();
		if(jt == "-1"){
			$("#spanmessage").html("Please select Location");
			$("#errorMessage").show();
			return false;
		}
		jt = $("#union_code").val();
		if(jt == "-1"){
			$("#spanmessage").html("Please select Union");
			$("#errorMessage").show();
			return false;
		}
		jt = $("#position_name").val();
		if(jt == "-1"){
			$("#spanmessage").html("Please select Position");
			$("#errorMessage").show();
			return false;
		}
		jt = $("#position_type").val();
		if(jt == "-1"){
			$("#spanmessage").html("Please select Position Type");
			$("#errorMessage").show();
			return false;
		}
		jt = $("#start_date").val();
		if(jt == ""){
			$("#spanmessage").html("Please enter Start Date");
			$("#errorMessage").show();
			return false;
		}
		jt = $("#position_hours").val();
		if(jt == ""){
			$("#spanmessage").html("Please enter Position Hours");
			$("#errorMessage").show();
			return false;
		}
		jt = $("#position_term").val();
		if(jt == "-1"){
			$("#spanmessage").html("Please select Position Term");
			$("#errorMessage").show();
			return false;
		}
		jt = $("#supervisor").val();
		if(jt == "SELECT YEAR"){
			$("#spanmessage").html("Please select Supervisor");
			$("#errorMessage").show();
			return false;
		}
		jt = $("#division").val();
		if(jt == "-1"){
			$("#spanmessage").html("Please select Division");
			$("#errorMessage").show();
			return false;
		}
		jt = $("#request_type").val();
		if(jt == ""){
			$("#spanmessage").html("Please enter Request Type");
			$("#errorMessage").show();
			return false;
		}
		return true;
	}
	// call approve request to hire ajax
    function updaterequeststatus(vrtype,vstatus,vrid)
    {
    	$.ajax(
     			{
     				type: "POST",  
     				url: "approveDeclineRequestToHire.html",
     				data: {
     					rid: vrid, rtype:vrtype, status: vstatus
     				}, 
     				success: function(xml){
     					$(xml).find('RTH').each(function(){
     							
     							
     							if($(this).find("STATUS").text() == "SUCCESS")
     								{
										window.location="addRequestToHire.html?rid=" + vrid;
     								}else{
     									alert("Error updating request.");
     									
     								}
						});

     					
     				},
     				  error: function(xhr, textStatus, error){
     				      alert("Status:" + xhr.statusText + "  " + "Text:" +textStatus + "  " + "Error:" + error );

     				  },
     				dataType: "text",
     				async: false
     			}
     		);
    }
	// call approve request to hire ajax
    function updaterequeststatuscomp(vrtype,vstatus,vrid)
    {
    	var pnum = $("#position_number").val();
    	$.ajax(
     			{
     				type: "POST",  
     				url: "approveRequestToHireComp.html",
     				data: {
     					rid: vrid, rtype:vrtype, status: vstatus, pnumber:pnum
     				}, 
     				success: function(xml){
     					$(xml).find('RTH').each(function(){
     							
     							
     							if($(this).find("STATUS").text() == "SUCCESS")
     								{
										window.location="addRequestToHire.html?rid=" + vrid;
     								}else{
     									alert("Error updating request.");
     									
     								}
						});

     					
     				},
     				  error: function(xhr, textStatus, error){
     				      alert("Status:" + xhr.statusText + "  " + "Text:" +textStatus + "  " + "Error:" + error );

     				  },
     				dataType: "text",
     				async: false
     			}
     		);
    }
    //get list of schools to populate dropdowns
    function getPositions()
    {
    	var uc = $("#union_code").val();
    	if(uc == null){
    		return;
    	}
    	$.ajax(
         			{
         				type: "POST",  
         				url: "getRTHUnionPositions.html",
         				data: {
         					unioncode: uc
         				}, 
         				success: function(xml){
         					var option="<option value='-1' selected>--- SELECT POSITION---</option>";
         					var jesoption="<option value='-1' selected>--- SELECT POSITION---</option>";
         					$("#position_name").empty();
         					$("#position_name").append($(option));
         					$("#jes_pay").empty();
         					$("#jes_pay").append($(option));
         					$(xml).find('UPOSITION').each(function(){
             					//now add the items if any
               					//var option = new Option($(this).find("SCHOOLNAME").text(), $(this).find("SCHOOLID").text());
               					option =option + "<option value='" + $(this).find("ID").text() + "'>" + $(this).find("PDESCRIPTION").text() + "</option>";
               					jesoption =jesoption + "<option value='" + $(this).find("ID").text() + "'>" + $(this).find("JESPAY").text() + "</option>";
               				});
         					  $("#position_name").append(option);
         					  $("#jes_pay").append(jesoption);
         					 $("#position_salary").val('');
         					 
     					
         				},
         				  error: function(xhr, textStatus, error){
         					  alert("error test" );
         				      alert(xhr.statusText);
         				      alert(textStatus);
         				      alert(error);
         				  },
         				dataType: "text",
         				async: false
         			}
         		);   			
    		

    	return true;
    	
    }
    //get list of schools to populate dropdowns
    function getJesPay()
    {
    	//get value of currently selected position
    	var uc = $("#position_name").val();
    	if(uc == null){
    		return;
    	}
    	//now find the jes pay
    	$("#jes_pay").val(uc);
    	//clear value
    	$("#position_salary").val('');
    	var jespay = $( "#jes_pay option:selected" ).text();
    	if(!(uc == -1)){
    		$("#position_salary").val(jespay);
    	}else{
    		$("#position_salary").val('');
    	}
    	
  			
    		

    	return true;
    	
    }
    //show/hides previous incumbent if replacement selected
    function showhidepirow(){
    	var uc = $("#position_type").val();
    	if(uc == 5){
    		$("#pirow").show();
    	}else{
    		$("#pirow").hide();
    	}
    	
    }
	//function submit post add
	function PostRequestToHire(){
		$("#frmAdRequest").hide();
		$('#frmAdRequest').attr('action','postAdRequest.html');
		  $('#frmAdRequest').submit();
	}
    //get list of schools to populate dropdowns
    function getPositionsFilter()
    {
    	var uc = $("#union_code").val();
    	if(uc == null){
    		return;
    	}
    	$.ajax(
         			{
         				type: "POST",  
         				url: "getRTHUnionPositions.html",
         				data: {
         					unioncode: uc
         				}, 
         				success: function(xml){
         					var option="<option value='-1' selected>--- SELECT POSITION---</option>";
         					$("#perm_position").empty();
         					$("#perm_position").append($(option));
         					$(xml).find('UPOSITION').each(function(){
             					//now add the items if any
               					//var option = new Option($(this).find("SCHOOLNAME").text(), $(this).find("SCHOOLID").text());
               					option =option + "<option value='" + $(this).find("ID").text() + "'>" + $(this).find("PDESCRIPTION").text() + "</option>";
               				});
         					  $("#perm_position").append(option);
         					 
     					
         				},
         				  error: function(xhr, textStatus, error){
         					  alert("error test" );
         				      alert(xhr.statusText);
         				      alert(textStatus);
         				      alert(error);
         				  },
         				dataType: "text",
         				async: false
         			}
         		);   			
    		

    	return true;
    	
    }

	