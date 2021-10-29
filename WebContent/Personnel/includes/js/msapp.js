jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});


function goBack() {
    window.history.back()
}


/*******************************************************************************
 * check fields for Step 1 Profile
 ******************************************************************************/
function checknewprofile() {	
	$("#divmsg").css("display","none");
	var email = $("#email").val();
	$("#emailW").removeClass("has-error");
	var cemail = $("#email_confirm").val();
	$("#emailW").removeClass("has-error");
	var password = $("#password").val();
	$("#passwordW").removeClass("has-error");
	var cpassword = $("#password_confirm").val();
	$("#cpasswordW").removeClass("has-error");
	var surname = $("#surname").val();
	$("#surnameW").removeClass("has-error");
	var firstname = $("#firstname").val();
	$("#firstnameW").removeClass("has-error");
	var address1 = $("#address1").val();
	$("#address1W").removeClass("has-error");
	var address2 = $("#address2").val();
	$("#address2W").removeClass("has-error");
	var province = $("#state_province").val();
	$("#provinceW").removeClass("has-error");
	var country = $("#country").val();
	$("#countryW").removeClass("has-error");
	var postalcode = $("#postalcode").val();
	$("#postalcodeW").removeClass("has-error");
	var homephone = $("#homephone").val();
	$("#homephoneW").removeClass("has-error");

	if (email === "") {		
		$("#divmsgAI").css("display","block").html("Email is a required field.").delay(5000).fadeOut();
		$("#emailW").addClass("has-error");
		$("#email").focus();
		return false;
	}
	var check = validateEmailAddress(email);
	if(!check){		
		$("#divmsgAI").css("display","block").html("Email address is invalid.").delay(5000).fadeOut();
		$("#emailW").addClass("has-error");
		$("#email").focus();
		return false;
	}
		
	if (cemail == "") {		
		$("#divmsgAI").css("display","block").html("Please confirm email address.").delay(5000).fadeOut();
		$("#cemailW").addClass("has-error");
		$("#cemail").focus();
		return false;
	}
	if(!(email == cemail)){		
		$("#divmsgAI").css("display","block").html("Your email address and confirmed email address do not match.").delay(5000).fadeOut();
		$("#cemailW").addClass("has-error");
		$("#email").focus();
		return false;
	}
	if (password == "") {		
		$("#divmsgAI").css("display","block").html("Password is a required field.").delay(5000).fadeOut();
		$("#passwordW").addClass("has-error");
		$("#password").focus();
		return false;
	}
	if (cpassword == "") {		
		$("#divmsgAI").css("display","block").html("Please confirm password.").delay(5000).fadeOut();
		$("#cpasswordW").addClass("has-error");
		$("#cpassword").focus();
		return false;
	}
	if(!(password == cpassword)){		
		$("#divmsgAI").css("display","block").html("Your password and confirmed password do not match.").delay(5000).fadeOut();
		$("#cpasswordW,passwordW").addClass("has-error");
		$("#cpassword").focus();
		return false;
	}
	if (surname == "") {		
		$("#divmsgPI").css("display","block").text("Surname is a required field.").delay(5000).fadeOut();
		$("#surnameW").addClass("has-error");
		$("#surname").focus();
		return false;
	}
	if (firstname == "") {		
		$("#firstnameW").addClass("has-error");
		$("#firstname").focus();
		$("#divmsgPI").html("First name is a required field.").css("display","block").delay(5000).fadeOut();
		
		return false;
	}
	if (address1 == "") {		
		$("#divmsgPI").css("display","block").html("Mailing address is a required field.").delay(5000).fadeOut();
		$("#address1W").addClass("has-error")
		$("#address1").focus();
		return false;
	}
	if (address2 == "") {		
		$("#divmsgPI").css("display","block").html("City/Town is a required field.").delay(5000).fadeOut();
		$("#address2W").addClass("has-error");
		$("#address2").focus();
		return false;
	}
	if (province == "-1") {		
		$("#divmsgPI").css("display","block").html("Province is a required field.").delay(5000).fadeOut();
		$("#provinceW").addClass("has-error");
		$("#province").focus();
		return false;
	}
	if (country == "-1") {		
		$("#divmsgPI").css("display","block").html("Country is a required field.").delay(5000).fadeOut();
		$("#countryW").addClass("has-error");
		$("#country").focus();
		return false;
	}
	if (postalcode == "") {		
		$("#divmsgPI").css("display","block").html("Postal Code is a required field.").delay(5000).fadeOut();
		$("#postalcodeW").addClass("has-error");
		$("#postalcode").focus();
		return false;
	}
	if (homephone == "") {		
		$("#divmsgPI").css("display","block").html("Home phone number is a required field.").delay(5000).fadeOut();
		$("#homephoneW").addClass("has-error");
		$("#homephone").focus();
		return false;
	}
	 if( !($('#op').length)){
			if(!(checkEmailAccount(email))){				
				$("#divmsg").css("display","block").html("An account using " + email + " already exists.").delay(5000).fadeOut();
				$("#emailW,cemailW").addClass("has-error");
				$("#email").focus();
				return false;
			}
	 }

	$("#divmsg").css("display","none");
	return true;
}
/*******************************************************************************
 * check fields for Step 1 Profile
 ******************************************************************************/
function validateEmailAddress(mail) 
{
 if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))
  {
    return (true)
  }
    return (false)
}
/*******************************************************************************
 * Calls ajax post to check to see if email exists already
 ******************************************************************************/
function checkEmailAccount(emaila) {
	var isvalid=true;
	
    $.ajax({
        url : 'checkApplicantEmail.html',
        type : 'POST',
        data : {
            email : emaila
        },
        success : function(xml) {
        	$(xml).find('ECHECK').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "NOTFOUND") {
							isvalid=true;
						}else if ($(this).find("MESSAGE").text() == "FOUND") {
							isvalid=false;
						}else{
							isvalid=false;							
							$("#divmsg").css("display","block").html("ERROR: " + $(this).find("MESSAGE").text()).delay(5000).fadeOut();
						}

					});
		},
		error : function(xhr, textStatus, error) {		 
		    $("#divmsg").css("display","block").html("ERROR: " + error).delay(5000).fadeOut();
		},
		dataType : "text",
		    async : false
		
		});
					
    return isvalid;

}
/*******************************************************************************
 * check fields for Step 2 Profile NLESD EXP
 ******************************************************************************/
//function used to post form without validations being triggered
function submitNlesdExp(stype){
	if(stype =="C"){
		if(checknlesdexp()){
			document.forms[0].submit();
		}
	}else{
		//updating 
		document.forms[0].submit();
	}
}

function checknlesdexp() {
	 	
	var sdate = $("#sdate").val();
	$("#sdateW").removeClass("has-error");
	var sstatus = $("#sstatus").val();
	$("#sstatusW").removeClass("has-error");
	var permschool = $("#perm_school").val();
	$("#permschoolW").removeClass("has-error");
	var permposition = $("#perm_position").val();
	$("#permpositionW").removeClass("has-error");
	var positionhours = $("#position_hours").val();
	$("#positionhoursW").removeClass("has-error");
	var selected_value = $("input[name='employed']:checked").val();
	if(selected_value == "Y"){
		if (sdate == "") {
			
			$("#msgerr").css("display","block").html("ERROR: Senority date is a required field.").delay(5000).fadeOut();
			$("#sdateW").addClass("has-error");
			$("#sdate").focus();
			return false;
			
		}
		if (sstatus == "") {
			$("#msgerr").css("display","block").html("ERROR: Senority status is a required field.").delay(5000).fadeOut();
			$("#sstatusW").addClass("has-error");
			$("#sstatus").focus();
			return false;
			
		}
		if (permschool == "0") {			
			$("#msgerr").css("display","block").html("ERROR: Position school is a required field.").delay(5000).fadeOut();
			$("#permschoolW").addClass("has-error");
			$("#permschool").focus();
			return false;			
			
		}
		if (permposition == "") {
			$("#msgerr").css("display","block").html("ERROR: Position Held is a required field.").delay(5000).fadeOut();
			$("#permpositionW").addClass("has-error");
			$("#permposition").focus();
			return false;
		}
		if (positionhours == "") {
			$("#msgerr").css("display","block").html("ERROR: Position hours is a required field.").delay(5000).fadeOut();
			$("#positionhoursW").addClass("has-error");
			$("#positionhours").focus();
			return false;		
			
		}
		$("#hidadd").val("ADDNEW");
	}	
	
	return true;
}
/*******************************************************************************
 * check fields for Step 3 Profile Add Employment
 *  ******************************************************************************/
function checknewemployment() {	 
	
	var company = $("#company").val();
	$("#companyW").removeClass("has-error");
	var address = $("#address").val();
	$("#addressW").removeClass("has-error");
	var phonenumber = $("#phonenumber").val();
	$("#phonenumberW").removeClass("has-error");
	var supervisor = $("#supervisor").val();
	$("#supervisorW").removeClass("has-error");
	var jobtitle = $("#jobtitle").val();
	$("#jobtitleW").removeClass("has-error");
	var duties = $("#duties").val();
	$("#dutiesW").removeClass("has-error");
	var fromdate = $("#fromdate").val();
	$("#fromdateW").removeClass("has-error");
	var todate = $("#todate").val();
	$("#todateW").removeClass("has-error");
	if (company == "") {
		$("#msgerr").css("display","block").html("ERROR: Company is a required field.").delay(5000).fadeOut();
		$("#companyW").addClass("has-error");
		$("#company").focus();
		return false;
	}
	if (address == "") {		
		$("#msgerr").css("display","block").html("ERROR: Address is a required field.").delay(5000).fadeOut();
		$("#addressW").addClass("has-error");
		$("#address").focus();
		return false;
	}
	if (phonenumber == "") {		
		$("#msgerr").css("display","block").html("ERROR: Phone number is a required field.").delay(5000).fadeOut();
		$("#phonenumberW").addClass("has-error");
		$("#phonenumber").focus();
		return false;
	}
	if (supervisor == "") {		
		$("#msgerr").css("display","block").html("ERROR: Supervisor is a required field.").delay(5000).fadeOut();
		$("#supervisorW").addClass("has-error");
		$("#supervisor").focus();
		return false;
	}
	if (jobtitle == "") {		
		$("#msgerr").css("display","block").html("ERROR: Job Title is a required field.").delay(5000).fadeOut();
		$("#jobtitleW").addClass("has-error");
		$("#jobtitle").focus();
		return false;
	}
	if (duties == "") {		
		$("#msgerr").css("display","block").html("ERROR: Duties is a required field.").delay(5000).fadeOut();
		$("#dutiesW").addClass("has-error");
		$("#duties").focus();
		return false;
	}
	if (fromdate == "") {
		$("#msgerr").css("display","block").html("ERROR: From Date is a required field.").delay(5000).fadeOut();
		$("#fromdateW").addClass("has-error");
		$("#fromdate").focus();	
		return false;
	}
	//if (todate == "") {		
		//$("#msgerr").css("display","block").html("ERROR: To Date is a required field.").delay(5000).fadeOut();
		//$("#todateW").addClass("has-error");
	//	$("#todate").focus();	
	//	return false;
	//}
	
	return true;
}
/*******************************************************************************
 * check fields for Step 4 Profile Add Education
 *  ******************************************************************************/
function checkneweducation() {
	 	
	var educationlevel = $("#educationlevel").val();
	$("#educationlevelW").removeClass("has-error");
	var schoolname = $("#schoolname").val();
	$("#schoolnameW").removeClass("has-error");
	var schoolcity = $("#schoolcity").val();
	$("#schoolcityW").removeClass("has-error");
	var state_province = $("#state_province").val();
	$("#state_provinceW").removeClass("has-error");
	var yearscompleted = $("#yearscompleted").val();
	$("#yearscompletedW").removeClass("has-error");
	var graduated = $("#graduated").val();
	$("#graduatedW").removeClass("has-error");
	
	if (educationlevel == "") {
		$("#msgerr").css("display","block").html("Education level is a required field").delay(5000).fadeOut();
		$("#educationlevelW").addClass("has-error");
		$("#educationlevel").focus();
		return false;
	}
	if (schoolname == "") {
		$("#msgerr").css("display","block").html("School name is a required field").delay(5000).fadeOut();
		$("#schoolnameW").addClass("has-error");
		$("#schoolname").focus();
		return false;
	}
	if (schoolcity == "") {
		$("#msgerr").css("display","block").html("School city is a required field").delay(5000).fadeOut();
		$("#schoolcityW").addClass("has-error");
		$("#schoolcity").focus();
		return false;
	}
	if (state_province == "-1") {
		$("#msgerr").css("display","block").html("School state/province is a required field").delay(5000).fadeOut();
		$("#state_provinceW").addClass("has-error");
		$("#state_province").focus();
		return false;
	}
	if (yearscompleted == "") {
		$("#msgerr").css("display","block").html("Number of years completed is a required field").delay(5000).fadeOut();
		$("#yearscompletedW").addClass("has-error");
		$("#yearscompleted").focus();
		return false;
	}
	if (graduated == "") {
		$("#msgerr").css("display","block").html("Did you graduate is a required field").delay(5000).fadeOut();
		$("#graduatedW").addClass("has-error");
		$("#graduated").focus();
		return false;
	}
	
	return true;
}
/*******************************************************************************
 * check fields for Step 5 Profile Add Education Post Sec
 *  ******************************************************************************/
function checkneweducationpostdegree()  {
	
	var institution = $("#institution").val();
	$("#institutionW").removeClass("has-error");
	var from_date = $("#from_date").val();
	$("#from_dateW").removeClass("has-error");
	var to_date = $("#to_date").val();
	$("#to_dateW").removeClass("has-error");
	
	
	if (institution == "") {
		$("#msgerr").css("display","block").html("Please specify name of institution").delay(5000).fadeOut();
		$("#institutionW").addClass("has-error");
		$("#institution").focus();
		return false;
	}
	if (from_date == "") {
		$("#msgerr").css("display","block").html("Please specify from date").delay(5000).fadeOut();
		$("#from_dateW").addClass("has-error");
		$("#from_date").focus();
		return false;
	}
	if (to_date == "") {
		$("#msgerr").css("display","block").html("Please specify to date").delay(5000).fadeOut();
		$("#to_dateW").addClass("has-error");
		$("#to_date").focus();
		return false;
	}
	 $("#ApplicantRegistrationStep1").attr('action', 'applicantRegistrationSS.html?step=5b');
	 $("#ApplicantRegistrationStep1").submit();

	return true;
}
/*******************************************************************************
 * check fields for Step 8 Profile Add New Reference
 *  ******************************************************************************/
function checknewreference() {
	$("#divmsg").hide();
	var fullname = $("#full_name").val();
	var title = $("#title").val();
	var address = $("#address").val();
	var telephone = $("#telephone").val();
	var email = $("#email").val();
	if (fullname == "") {
		$("#spanmsg").html("Please specify full name");
		$("#divmsg").show();
		return false;
	}
	if (title == "") {
		$("#spanmsg").html("Please specify title");
		$("#divmsg").show();
		return false;
	}
	if (address == "") {
		$("#spanmsg").html("Please specify address");
		$("#divmsg").show();
		return false;
	}
	if (telephone == "") {
		$("#spanmsg").html("Please specify telephone");
		$("#divmsg").show();
		return false;
	}
	if (email == "") {
		$("#spanmsg").html("Please specify email");
		$("#divmsg").show();
		return false;
	}
	if(!(validateEmailAddress(email))){
		$("#spanmsg").html("Email address is invalid");
		$("#divmsg").show();
		return false;
	}
	$("#divmsg").hide();
	return true;
}
/*******************************************************************************
 * retrieve job details and appliant status
 ******************************************************************************/
function openapply(jobid) {
	//alert("here");
	//getotherjobdetails(jobid);
	//$('#jobModal').modal('show').find('.modal-body').load($(this).attr('view_other_job_post.jsp?jobid='+jobid));
	//$('#jobModal').modal('show');
	var dataURL = 'https://www.nlesd.ca/employment/view_other_job_post.jsp?jobid='+jobid;
    $('.modal-body').load(dataURL,function(){
        $('#jobModal').modal({show:true});
    });
	
	
}
/*******************************************************************************
 * check fields for adding new support staff request
 *  ******************************************************************************/
function checknewadrequest() {
	$("#divmsg").hide();
	var adtitle = $("#ad_title").val();
	var vlocation = $("#location").val();
	var vacancyreason = $("#vacancy_reason").val();
	var startdate = $("#start_date").val();
	if (adtitle == "") {
		$("#spanmsg").html("Please specify position title");
		$("#divmsg").show();
		return false;
	}
	if (vlocation == "-1") {
		$("#spanmsg").html("Please specify location");
		$("#divmsg").show();
		return false;
	}
	if (vacancyreason == "") {
		$("#spanmsg").html("Please specify vacancy reason");
		$("#divmsg").show();
		return false;
	}
	if (startdate == "") {
		$("#spanmsg").html("Please specify Start Date");
		$("#divmsg").show();
		return false;
	}
	document.forms[0].op.value='ADD_REQUEST'; 
	document.forms[0].submit()
}




function OpenPopUp()
{
	//first we check to see if they entered an email address
			
	var fieldvalue = $.trim($('#email').val());
	if(fieldvalue == null || fieldvalue =='' )
		{
		$('#msgerr').css('display','block').html("ERROR: Please enter your email address.").delay(5000).fadeOut();
		$('#email').focus();
		return false;
		}else{
			var check =ajaxRequestInfo();
			if(check == true)
				{
					//show box
					$(document).ready(function() {
						$('#step1').modal('show');
					});
				}else{
					$('#step1').modal('hide');
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
 									//$('#msgerrstep1').css('display','block').html("We could not find the email in our system. Did you create your security question when you initially registered? If not, we cannot reset your password. Please contact support.").delay(5000).fadeOut();
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
		
		
		$('#step1').modal('hide');
		$('#step2').modal('show');
	   
	    
		
		}else{
			//show message
			
			$('#msgerrstep1').css('display','block').html("ERROR: Sorry the answer does not match the value in the system.").delay(5000).fadeOut();
			
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
    	    $('#step2').modal('hide');
    	    $('#msgok').css('display','block').html("SUCCESS: Your password has been successfully updated. You can now try to login.").delay(5000).fadeOut();
    	    
			
		}else{
			$('#msgerrstep2').css('display','block').html("ERROR: Could not change your password at this time. Please try again.").delay(5000).fadeOut();
		}

	    
	    
		
	}else{
		$('#msgerrstep2').css('display','block').html("ERROR: The Password and Confirm Passsword does not match. Please try again.").delay(5000).fadeOut();
		
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
 									$('#msgerrstep2').css('display','block').html("ERROR: There was a problem updating your password. Please try again or contact support.").delay(5000).fadeOut();
 									
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
	
	$('#step1').modal('hide');
	$('#step2').modal('hide');
	
}

function OpenReferencePopUp(appid)
{
	$(document).ready(function() {
		$("#uid").val(appid);
		$("#refRequest").modal('show');		
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

	//job title
	
	$("#positionTitleGroup").removeClass("has-error");
	$("#locationGroup").removeClass("has-error");
	$("#positionTypeGroup").removeClass("has-error");
	$("#startGroup").removeClass("has-error");
	$("#unionGroup").removeClass("has-error");
	$("#positionNGroup").removeClass("has-error");
	$("#positionTypeGroup").removeClass("has-error");
	$("#supervisorGroup").removeClass("has-error");
	$("#divisionGroup").removeClass("has-error");
	$("#requestTGroup").removeClass("has-error");
	
	
	
	var jt = $("#job_title").val();
	if(jt == ""){
		$("#positionTitleGroup").addClass("has-error");
		$("#errorMessage").html("Please enter Position Title. This is a required field.").css("display","block").delay(4000).fadeOut();
		return false;
	}
	jt = $("#location").val();
	if(jt == "-1"){
		$("#locationGroup").addClass("has-error");
		$("#errorMessage").html("Please enter a valid location. This is a required field.").css("display","block").delay(4000).fadeOut();
		return false;
	}
	jt = $("#union_code").val();
	if(jt == "-1"){
		$("#unionGroup").addClass("has-error");
		$("#errorMessage").html("Please select Union. This is a required field.").css("display","block").delay(4000).fadeOut();
		return false;
	}
	jt = $("#position_name").val();
	if(jt == "-1"){
		$("#positionNGroup").addClass("has-error");
		$("#errorMessage").html("Please select Position. This is a required field.").css("display","block").delay(4000).fadeOut();
		return false;
	}
	jt = $("#position_type").val();
	if(jt == "-1"){
		$("#positionTypeGroup").addClass("has-error");
		$("#errorMessage").html("Please select position type. This is a required field.").css("display","block").delay(4000).fadeOut();		
		
		return false;
	}
	jt = $("#position_term").val();
	if(jt == "-1"){
		$("#positionTermGroup").addClass("has-error");
		$("#errorMessage").html("Please select position term. This is a required field.").css("display","block").delay(4000).fadeOut();		
		
		return false;
	}
	jt = $("#start_date").val();
	if(jt == ""){
		$("#startGroup").addClass("has-error");
		$("#errorMessage").html("Please enter Start Date. This is a required field.").css("display","block").delay(4000).fadeOut();			
		return false;
	}
	jt = $("#supervisor").val();
	if(jt == "SELECT YEAR"){
		$("#supervisorGroup").addClass("has-error");
		$("#errorMessage").html("Please select supervisor. This is a required field.").css("display","block").delay(4000).fadeOut();		
		
		return false;
	}
	jt = $("#division").val();
	if(jt == "-1"){
		$("#divisionGroup").addClass("has-error");
		$("#errorMessage").html("Please select division. This is a required field.").css("display","block").delay(4000).fadeOut();		
		
		return false;
	}
	jt = $("#request_type").val();
	if(jt == ""){
		$("#requestTGroup").addClass("has-error");
		$("#errorMessage").html("Please select request type. This is a required field.").css("display","block").delay(4000).fadeOut();		
		
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
}
//get list of schools to populate dropdowns
function getPositions()
{
	var uc = $("#union_code").val();
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
     					 //$("#position_salary").val('');
     					 
 					
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
		

	return true;
	
}
//get list of schools to populate dropdowns
function getJesPay()
{
	//get value of currently selected position
	var uc = $("#position_name").val();
	//now find the jes pay
	$("#jes_pay").val(uc);
	//clear value
	//$("#position_salary").val('');
	var jespay = $( "#jes_pay option:selected" ).text();
	
	//$("#position_salary").val(jespay);
			
		

	return true;
	
}     

function openWindow(id,url,w,h, scroll) 
{
  var winl = (screen.width-w)/2;
  var wint = (screen.height - h - 25 )/2;

  window.open(url,id,"titlebar=0,toolbar=0,location=no,top="+wint+",left="+winl+",directories=0,status=1,menbar=0,scrollbars="+scroll+",resizable=0,width="+w+",height="+h);
}

function validateNotEmpty(fld) 
{
    return /\S+/.test(fld.value);
}

function validateDateFormat(fld)
{
  return /^\d{2}\/\d{4}$/.test(fld.value);
}

function validateSelectionMade(fld)
{
  return ((fld.selectedIndex != -1) && (fld.options[fld.selectedIndex].value != -1));
}

function AddReplExpForm_Validator(theForm)
{

  if (!validateDateFormat(theForm.from_date))
  {
    alert("Invalid value for \"From Date\" field, must match mm/yyyy format.");
    theForm.from_date.focus();
    return (false);
  }
  
  if (!validateDateFormat(theForm.to_date))
  {
    alert("Invalid value for \"To Date\" field, must match mm/yyyy format.");
    theForm.to_date.focus();
    return (false);
  }
  
  if (!validateSelectionMade(theForm.school_id))
  {
    alert("Please select a value for the \"School\" field.");
    theForm.school_id.focus();
    return (false);
  }

  if (!validateNotEmpty(theForm.grds_subs))
  {
    alert("Please enter a value for the \"Grades and/or Subjects Taught\" field.");
    theForm.grds_subs.focus();
    return (false);
  }

  return (true);
}

function onPositionTypeSelected(ele)
{
	if(ele.value == '7') //Other
		$("#recommended_position_other_row").css("display","block");
		
	else
		$("#recommended_position_other_row").css("display","none");
}

function onSpecialConditionChecked(ele){
	if(ele.checked)
		$("#special_conditions_row").css("display","block");		
	else
		$("#special_conditions_row").css("display","none");
}

function onError(){
	//load candidate info
	var ele = document.getElementById('candidate_name');
	onCandidateSelected(ele.value);
	
	ele = document.getElementById('position');
	onPositionTypeSelected(ele);
	
	refreshGSU();
	
	ele = document.getElementById('Special_Conditions');
	onSpecialConditionChecked(ele);
}

function validateEmail(fld) 
{
    return /^[\w.\-]+@[\w\-]+\.[a-zA-Z0-9]+$/.test(fld.value);
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
//call approve request to hire ajax
function resendrthmessage(vrid)
{
	$.ajax(
 			{
 				type: "POST",  
 				url: "resendRequestToHireMessage.html",
 				data: {
 					rid: vrid
 				}, 
 				success: function(xml){
 					$(xml).find('RTH').each(function(){
 							
 							
 							if($(this).find("STATUS").text() == "SUCCESS")
 								{
									//window.location="addRequestToHire.html?rid=" + vrid;
 									$("#spanmessageS").html("Notification has been resent").css("display","block");
 									$("#errorMessageS").show();
 								}else{
 									$("#spanmessage").html("Error sending notification").css("display","block");
 									$("#errorMessage").show();
 									
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
//open confirm applicant delete box
function openDeleteApplicant(appid,dtype){
	var options = {
			"backdrop" : "static",
			"show" : true
		};
		if(dtype =="D"){
			$('#spandelete').text("Are you sure you want to delete the profile for " + $("#appname").val());
			$('#delemptext').text("Delete Applicant");
			$('#btn_delete_app_ok').text("Delete Applicant");
		}else{
			$('#spandelete').text("Are you sure you want to restore the profile for " + $("#appname").val());
			$('#delemptext').text("Restore Applicant");
			$('#btn_delete_app_ok').text("Restore Applicant");
		}
		// now we add the onclick event
		$("#btn_delete_app_ok").click(function(event) {
			event.preventDefault();
			if(dtype =="D"){
				deleteApplicantProfileSubmit(appid,1);
				
			}else{
				deleteApplicantProfileSubmit(appid,0);
			}
			
		});
		
		$('#delete_app_dialog').modal(options);
		$('#delete_app_dialog').modal('show');
}
//soft delete applicant profile
function deleteApplicantProfileSubmit(vrid,dval)
{
	$.ajax(
 			{
 				type: "POST",  
 				url: "deleteApplicantProfileAjax.html",
 				data: {
 					appid: vrid,dvalue:dval
 				}, 
 				success: function(xml){
 					$(xml).find('PROFILE').each(function(){
 							
 							
 							if($(this).find("STATUS").text() == "SUCCESS"){
									//$("#spanmessageS").html("Profile has been deleted").css("display","block");
 									//$("#errorMessageS").show();
 									if(dval == "0"){
 										window.location="admin_index.jsp?delmess=Profile has been restored";
 									}else{
 										window.location="admin_index.jsp?delmess=Profile has been deleted";
 									}
 									
 							}else{
 									//$("#spanmessage").html("Error sending notification").css("display","block");
 									//$("#errorMessage").show();
 									if(dval == "0"){
 										window.location="admin_index.jsp?delmesserr=Error restoring profile";
 									}else{
 										window.location="admin_index.jsp?delmesserr=Error deleting profile";
 									}
 									
 							}
					});

 					
 				},
 				  error: function(xhr, textStatus, error){
 				      //alert("Status:" + xhr.statusText + "  " + "Text:" +textStatus + "  " + "Error:" + error );
 				     window.location="admin_index.jsp?delmesserr=Error deleting profile";
 				  },
 				dataType: "text",
 				async: false
 			}
 		);
}
//open confirm applicant delete box
function openPDeleteApplicant(appid){
	var options = {
			"backdrop" : "static",
			"show" : true
		};
		// now we add the onclick event
		$("#btn_delete_app_ok").click(function(event) {
			event.preventDefault();
			window.location="deleteApplicant.html?uid=" + appid;
			
		});
		
		$('#delete_app_dialog').modal(options);
		$('#delete_app_dialog').modal('show');
}
//open approve, reject, reset sublist
function openSublistDialog(appid,sublistid,ttype){
	var options = {
			"backdrop" : "static",
			"show" : true
		};
		if(ttype =="A"){
			//notes not mandatory but send along
			var surl ="shortListApplicant.html";
			surl=surl + "?sin=" + appid + "&list_id=" + sublistid;
			window.location=surl;
		}else if(ttype =="NA"){
			var cid = "#sl" + sublistid;
			$('#sltitle'+sublistid).text("Reason For Not Approving");
			$('#bs'+sublistid).prop("value", "Not Approve");
			$('#bc'+sublistid).prop("value", "Cancel"); 
			$(cid).show();
		}else if(ttype =="R"){
			var cid = "#sl" + sublistid;
			$('#sltitle'+sublistid).text("Reason For Resetting");
			$('#bs'+sublistid).prop("value", "Reset");
			$('#bc'+sublistid).prop("value", "Cancel"); 
			$(cid).show();
		}
		// now we add the onclick event
		$("#btn_sublist_ok").click(function(event) {
			event.preventDefault();
			submitSubList(appid,sublistid,ttype);
			
		});
		
		$('#sub_list_dialog').modal(options);
		$('#sub_list_dialog').modal('show');
}
//function used to submit the correct url for approval, nonapproval and reset sublist
function submitSubListRow(sublistid,btext){
	var appid = $("#id").val();
	$('#response_msg_sld').hide();
	if(btext == "Not Approve"){
		if($('#sltext' + sublistid).val().trim() == ""){
			$('#response_msg_sld').text("Please Enter Reason For Not Approving");
			$('#response_msg_sld').show();
			return;
		}
		//all good we submit
		var surl ="applicantNotApproved.html";
		surl=surl + "?sin=" + appid + "&list_id=" + sublistid + "&slnotes=" + $('#sltext' + sublistid).val();
		window.location=surl;
	}else if(btext == "Reset"){
		if($('#sltext' + sublistid).val().trim() == ""){
			$('#response_msg_sld').text("Please Enter Reason For Resetting");
			$('#response_msg_sld').show();
			return;
		}
			var surl ="resetApplicantApproval.html";
			surl=surl + "?uid=" + appid + "&list_id=" + sublistid + "&slnotes=" + $('#sltext' + sublistid).val();
			window.location=surl;
		
	}
}
//function used when cancel button selected on not approve and reset
function cancelSubListRow(sublistid,btext){
	$('#response_msg_sld').hide();
	
	$("#sublisttable tr").each(function () {
		
		if(typeof($(this).attr('id'))  != "undefined"){
			var cid = $(this).attr('id');
			$(this).hide();
		}
	});
	
}
//function used to retrieve the history details for the sublist/app and show table
function showHistory(appid,sublistid){
	var requestd = new FormData();
	requestd.append('appid', appid);
	requestd.append('sublistid', sublistid);
	$.ajax({
		url : "getSublistApplicantHistory.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {
			$("#historytable").find("tr:gt(0)").remove();
			$(xml).find('SLENTRY').each(
				function() {
					if ($(this).find("MESSAGE").text() == "DATA") {
						var newrow = "<tr>";
						newrow += "<td>" + $(this).find("ENTRYNOTES").text() + "</td>";
						newrow += "<td>" + $(this).find("ENTRYDATE").text() + "</td>";
						newrow += "<td></td>";
						newrow += "</tr>";
						$('#historytable tr:last').after(newrow);
					} 
					else if ($(this).find("MESSAGE").text() == "NODATA"){
						var newrow = "<tr>";
						newrow += "<td colspan='3'>No History Found</td>";
						newrow += "</tr>";
						$('#historytable tr:last').after(newrow);
					}else{
						var newrow = "<tr>";
						newrow += "<td colspan='3'>" + $(this).find("MESSAGE").text() + "</td>";
						newrow += "</tr>";
						$('#historytable tr:last').after(newrow);
					}
				});
			
			
			
		},
		error : function(jqXHR, textStatus, errorThrown) {
			var newrow = "<tr>";
			newrow += "<td colspan='3'>" + textStatus + "</td>";
			newrow += "</tr>";
			$('#historytable tr:last').after(newrow);
		},
		dataType : "text",
		async : false
	});
	$("#historyrow").show();
}
//function used to close sublist/app history table
function closeTableHistory(){
	$("#historyrow").hide();
}
//get list of schools to zone
function getSubListsSchools()
{
	var zi = $("#selregion").val();
			$.ajax(
     			{
     				type: "POST",  
     				url: "getSubListsSchools.html",
     				data: {
     					zoneid: zi
     				}, 
     				success: function(xml){
     					if($(xml).find('MESSAGE').text() ==  "SUCCESS"){
     							$("#selschool").empty();
     	     					$("#selsublist").empty();
     	     					//now populate schools
     	     					var option="<option value='-1' selected>Select school</option>";
     	     					$(xml).find('ZSCHOOL').each(function(){
     	     						option =option + "<option value='" + $(this).find("SID").text() + "'>" + $(this).find("SNAME").text() + "</option>";
     	     					});
     	     					$("#selschool").append(option);
     	     					//now we append the sublist
     	     					option="<option value='-1' selected>Select list</option>";
     	     					$(xml).find('ZLIST').each(function(){
     	     						option =option + "<option value='" + $(this).find("ZID").text() + "'>" + $(this).find("ZNAME").text() + "</option>";
     	     					});
     	     					$("#selsublist").append(option);
     					}
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
		

	return true;
	
}
//get list of schools to zone
function getSubListShortlistAppsBySchool()
{
	var s = $("#selschool").val();
	var l = $("#selsublist").val();
			$.ajax(
     			{
     				type: "POST",  
     				url: "getSubListApplicantsBySchool.html",
     				data: {
     					sid: s,lid:l
     				}, 
     				success: function(xml){
     					if($(xml).find('MESSAGE').text() ==  "SUCCESS"){
     						//$('#reportdata tr:gt(0)').remove()
     						$('#reportdata').DataTable().clear();
     						//$("#reportdata tbody").empty();
     						//refreshdatatable();
     	     					//now populate schools
     							$(xml).find('PROFILE').each(function(){
     								var buttext ="<a class='btn btn-xs btn-primary' href='viewApplicantProfile.html?sin=" 
     									+ $(this).find("APPID").text() + "'>PROFILE</a>";
     								if($('#c19').length) {
     									$('#reportdata').DataTable().row.add([$(this).find("LASTNAME").text()+ ", " +$(this).find("FIRSTNAME").text(),
         									$(this).find("MAJORS").text().replace("\n","<br />"),
         										$(this).find("EMAIL").text(),$(this).find("COMMUNITY").text(),$(this).find("PHONE").text().replace("\n","<br />")
         										,$(this).find("CV19").html(),buttext]);
     									
     								}else{
     									$('#reportdata').DataTable().row.add([$(this).find("LASTNAME").text() +", " +$(this).find("FIRSTNAME").text(),
         									$(this).find("MAJORS").text().replace("\n","<br />"),
         										$(this).find("EMAIL").text(),$(this).find("COMMUNITY").text(),$(this).find("PHONE").text().replace("\n","<br />")
         										,buttext]);
     								}


     							});
     							$('#reportdata').DataTable().draw();
     	     					
     					}
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
		

	return true;
	
}
//reinitialize datatable
function refreshdatatable(){
	$('#reportdata').DataTable({
		
		"order": [[ 1, 'asc' ]],
		  dom: 'Bfrtip',			  
		  buttons: [ 'copyHtml5', 'excelHtml5', 
			  {
                extend: 'pdfHtml5',
                orientation: 'landscape',
                pageSize: 'LETTER'
                
            }, 'csvHtml5',{
                extend: 'print',
                orientation: 'landscape',
                pageSize: 'LETTER'
            }
		  ]
		 ,"bAutoWidth": false
		  
		} );
	
	$("#reportdata").css('table-layout', "fixed");
}

//delete reference ajax call
function deleteref(but,refid){
	$.ajax(
 			{
 				type: "POST",  
 				url: "adminDeleteReference.html",
 				data: {
 					delid: refid
 				}, 
 				success: function(xml){
 					
 					if($(xml).find('STATUS').text() ==  "SUCCESS"){
 						//$('#reportdata tr:gt(0)').remove()
 						$(".msgok").html("SUCCESS: Reference removed.").css("display","block").delay(4000).fadeOut();
 						$(but).closest('tr').remove(); 
 	     					
 					}
 				},
 				  error: function(xhr, textStatus, error){
					$(".msgerr").html("ERROR: " + xhr.statusText +", "+textStatus + ", "+ error ).css("display","block").delay(4000).fadeOut(); 				      

 				  },
 				dataType: "text",
 				async: false
 			}
 		);
}
//delete reference ajax call
function verifycovid19(did,btn){
	$.ajax(
 			{
 				type: "POST",  
 				url: "verifyCovid19Doc.html",
 				data: {
 					id: did
 				}, 
 				success: function(xml){
 					
 					if($(xml).find('STATUS').text() ==  "SUCCESS"){
 						$("#spvdate").html($(xml).find('VDATE').text());
 						$("#spvby").html($(xml).find('VBY').text());
 						$("#divverify").show();
						$("#divnotver").hide();
					
 						$(btn).hide();
 					}
 				},
 				  error: function(xhr, textStatus, error){
					$(".msgerr").html("ERROR: " + xhr.statusText +", "+textStatus + ", "+ error ).css("display","block").delay(4000).fadeOut(); 				      

 				  },
 				dataType: "text",
 				async: false
 			}
 		);
}
//delete reference ajax call
function verifycovid19list(did,btn){
	$.ajax(
 			{
 				type: "POST",  
 				url: "verifyCovid19Doc.html",
 				data: {
 					id: did
 				}, 
 				success: function(xml){
 					
 					if($(xml).find('STATUS').text() ==  "SUCCESS"){
 						//$("#spvdate").html($(xml).find('VDATE').text());
 						//$("#spvby").html($(xml).find('VBY').text());
 						//$("#divverify").show();
 						var test = "#" + did;
 						$(test).html("Document Verified By " + $(xml).find('VBY').text() + " on " + $(xml).find('VDATE').text());
 						$(btn).hide();
 						
 					}
 				},
 				  error: function(xhr, textStatus, error){
					$(".msgerr").html("ERROR: " + xhr.statusText +", "+textStatus + ", "+ error ).css("display","block").delay(4000).fadeOut(); 				      

 				  },
 				dataType: "text",
 				async: false
 			}
 		);
}




