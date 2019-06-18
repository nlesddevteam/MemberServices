/*******************************************************************************
 * check fields for Step 1 Profile
 ******************************************************************************/
function checknewprofile() {
	 
	$("#divmsg").hide();
	var email = $("#email").val();
	var cemail = $("#email_confirm").val();
	var password = $("#password").val();
	var cpassword = $("#password_confirm").val();
	var surname = $("#surname").val();
	var firstname = $("#firstname").val();
	var address1 = $("#address1").val();
	var address2 = $("#address2").val();
	var province = $("#state_province").val();
	var country = $("#country").val();
	var postalcode = $("#postalcode").val();
	var homephone = $("#homephone").val();
	if (email == "") {
		$("#spanmsg").html("Email is a required field");
		$("#divmsg").show();
		return false;
	}
	var check = validateEmailAddress(email);
	if(!check){
		$("#spanmsg").html("Email address is invalid");
		$("#divmsg").show();
		return false;
	}
	if (cemail == "") {
		$("#spanmsg").html("Please confirm email address");
		$("#divmsg").show();
		return false;
	}
	if(!(email == cemail)){
		$("#spanmsg").html("Your email address and confirmed email address do not match");
		$("#divmsg").show();
		return false;
	}
	if (password == "") {
		$("#spanmsg").html("Password is a required field");
		$("#divmsg").show();
		return false;
	}
	if (cpassword == "") {
		$("#spanmsg").html("Please confirm password");
		$("#divmsg").show();
		return false;
	}
	if(!(password == cpassword)){
		$("#spanmsg").html("Your password and confirmed password do not match");
		$("#divmsg").show();
		return false;
	}
	if (surname == "") {
		$("#spanmsg").html("Surname is a required field");
		$("#divmsg").show();
		return false;
	}
	if (firstname == "") {
		$("#spanmsg").html("First name is a required field");
		$("#divmsg").show();
		return false;
	}
	if (address1 == "") {
		$("#spanmsg").html("Mailing address is a required field");
		$("#divmsg").show();
		return false;
	}
	if (address2 == "") {
		$("#spanmsg").html("City/Town is a required field");
		$("#divmsg").show();
		return false;
	}
	if (province == "-1") {
		$("#spanmsg").html("Province is a required field");
		$("#divmsg").show();
		return false;
	}
	if (country == "-1") {
		$("#spanmsg").html("Country is a required field");
		$("#divmsg").show();
		return false;
	}
	if (postalcode == "") {
		$("#spanmsg").html("Postal Code is a required field");
		$("#divmsg").show();
		return false;
	}
	if (homephone == "") {
		$("#spanmsg").html("Home phone number is a required field");
		$("#divmsg").show();
		return false;
	}
	 if( !($('#op').length)){
			if(!(checkEmailAccount(email))){
				$("#spanmsg").html("An account using " + email + " already exists");
				$("#divmsg").show();
				return false;
			}
	 }

	$("#divmsg").hide();
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
							$("#spanmsg").html("ERROR: " + $(this).find("MESSAGE").text());
							$("#divmsg").show();
						}

					});
		},
		error : function(xhr, textStatus, error) {
		    $("#spanmsg").html("ERROR: " + error);
			$("#divmsg").show();
		},
		dataType : "text",
		    async : false
		
		});
					
    return isvalid;

}
/*******************************************************************************
 * check fields for Step 2 Profile NLESD EXP
 ******************************************************************************/
function checknlesdexp() {
	 
	$("#divmsg").hide();
	var sdate = $("#sdate").val();
	var sstatus = $("#sstatus").val();
	var permschool = $("#perm_school").val();
	var permposition = $("#perm_position").val();
	var positionhours = $("#position_hours").val();
	var selected_value = $("input[name='employed']:checked").val();
	if(selected_value == "Y"){
		if (sdate == "") {
			$("#spanmsg").html("Senority date is a required field");
			$("#divmsg").show();
			return false;
		}
		if (sstatus == "") {
			$("#spanmsg").html("Senority status is a required field");
			$("#divmsg").show();
			return false;
		}
		if (permschool == "0") {
			$("#spanmsg").html("Position school is a required field");
			$("#divmsg").show();
			return false;
		}
		if (permposition == "") {
			$("#spanmsg").html("Position Held is a required field");
			$("#divmsg").show();
			return false;
		}
		if (positionhours == "") {
			$("#spanmsg").html("Position hours is a required field");
			$("#divmsg").show();
			return false;
		}
		$("#hidadd").val("ADDNEW");
	}
	
	$("#divmsg").hide();
	return true;
}
/*******************************************************************************
 * check fields for Step 3 Profile Add Employment
 *  ******************************************************************************/
function checknewemployment() {
	 
	$("#divmsg").hide();
	var company = $("#company").val();
	var address = $("#address").val();
	var phonenumber = $("#phonenumber").val();
	var supervisor = $("#supervisor").val();
	var jobtitle = $("#jobtitle").val();
	var duties = $("#duties").val();
	var fromdate = $("#fromdate").val();
	var todate = $("#todate").val();
	if (company == "") {
		$("#spanmsg").html("Company is a required field");
		$("#divmsg").show();
		return false;
	}
	if (address == "") {
		$("#spanmsg").html("Address is a required field");
		$("#divmsg").show();
		return false;
	}
	if (phonenumber == "") {
		$("#spanmsg").html("Phone number is a required field");
		$("#divmsg").show();
		return false;
	}
	if (supervisor == "") {
		$("#spanmsg").html("Supervisor is a required field");
		$("#divmsg").show();
		return false;
	}
	if (jobtitle == "") {
		$("#spanmsg").html("Job title is a required field");
		$("#divmsg").show();
		return false;
	}
	if (duties == "") {
		$("#spanmsg").html("Duties is a required field");
		$("#divmsg").show();
		return false;
	}
	if (fromdate == "") {
		$("#spanmsg").html("From date is a required field");
		$("#divmsg").show();
		return false;
	}
	if (todate == "") {
		$("#spanmsg").html("To date is a required field");
		$("#divmsg").show();
		return false;
	}
	$("#divmsg").hide();
	return true;
}
/*******************************************************************************
 * check fields for Step 4 Profile Add Education
 *  ******************************************************************************/
function checkneweducation() {
	 
	$("#divmsg").hide();
	var educationlevel = $("#educationlevel").val();
	var schoolname = $("#schoolname").val();
	var schoolcity = $("#schoolcity").val();
	var state_province = $("#state_province").val();
	var yearscompleted = $("#yearscompleted").val();
	var graduated = $("#graduated").val();
	if (educationlevel == "") {
		$("#spanmsg").html("Education level is a required field");
		$("#divmsg").show();
		return false;
	}
	if (schoolname == "") {
		$("#spanmsg").html("School name is a required field");
		$("#divmsg").show();
		return false;
	}
	if (schoolcity == "") {
		$("#spanmsg").html("School city is a required field");
		$("#divmsg").show();
		return false;
	}
	if (state_province == "-1") {
		$("#spanmsg").html("School state/province is a required field");
		$("#divmsg").show();
		return false;
	}
	if (yearscompleted == "") {
		$("#spanmsg").html("Number of years completed is a required field");
		$("#divmsg").show();
		return false;
	}
	if (graduated == "") {
		$("#spanmsg").html("Did you graduate is a required field");
		$("#divmsg").show();
		return false;
	}
	$("#divmsg").hide();
	return true;
}
/*******************************************************************************
 * check fields for Step 5 Profile Add Education Post Sec
 *  ******************************************************************************/
function checkneweducationpost() {
	$("#divmsg").hide();
	var institution = $("#institution").val();
	var from_date = $("#from_date").val();
	var to_date = $("#to_date").val();
	
	
	if (institution == "") {
		$("#spanmsg").html("Please specify name of institution");
		$("#divmsg").show();
		return false;
	}
	if (from_date == "") {
		$("#spanmsg").html("Please specify from date");
		$("#divmsg").show();
		return false;
	}
	if (to_date == "") {
		$("#spanmsg").html("Please specify to date");
		$("#divmsg").show();
		return false;
	}
	$("#divmsg").hide();
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