//global variables
//match email address
var emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$/; 
//match elements that could contain a phone number
var phoneNumber = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
/*******************************************************************************
 * Checks fields and then posts form
 ******************************************************************************/
function submitRegistration() {
	$("#pnl-error-msg").css("display", "none");
	if (validateRegistrantForm()) {
		$('#btn_SubmitRegistration').attr({
			'disabled' : 'disabled'
		});
		$('#btn_cancelAddReg').attr({
			'disabled' : 'disabled'
		});
		$('#add-registrant-form form')[0].submit();
	} else {		
		$("#pnl-error-msg").css("display", "block");
		$(".msgerr").css("display", "block").html("<b>ERROR:</b> Error(s) found in form submission. Please check the form and try again.").delay(3000).fadeOut(2000);
	}
}

/*******************************************************************************
 * Cancel registration form
 ******************************************************************************/
function cancelRegistration() {
	//replace with ICF information page
	//window.location.href = "/MemberServices/schools/registration/icfreg/registration/viewStudentRegistration.html";
	window.location.href = "/families/icfregistration.jsp";
}
/*******************************************************************************
 * validate form fields
 ******************************************************************************/
function validateRegistrantForm() {
	//now we check the fields
	var errorstring="";
	
	if($("#txt_StudentName").val() == ""){
		errorstring += "<li>Student Full Name";
	}
	if($("#txt_GuardianName").val() == ""){
		errorstring += "<li>Parent/Guardian Full Name";
	}
	if($('#txt_ParentGuardianEmail').val() == ''){
		errorstring += "<li>Parent/Guardian Email Address";
	}
	if($('#txt_ParentGuardianEmail').val() != '' && !emailRegex.test($('#txt_ParentGuardianEmail').val())){
		errorstring += "<li>Parent/Guardian Email Address invalid format";
	}
	if($('#txt_ContactNumber1').val() == ''){
		errorstring += "<li>Telephone Number";
	}
	if($('#txt_ContactNumber1').val() != '' && !phoneNumber.test($('#txt_ContactNumber1').val())){
		errorstring += "<li>Telephone Number in invalid format";
	}
	if($('#txt_ContactNumber2').val() != '' && !phoneNumber.test($('#txt_ContactNumber2').val())){
		errorstring += "<li>Optional Number in invalid format";
	}
	if($("#selSchool").val() == "-1" || $("#selSchool").val() == ""){
		errorstring += "<li>School";
	}
	if(errorstring != ""){
		$("#error-msg").html(errorstring);
		return false;
	}else{
		//pass in school name so we do not need to query
		var test = $("#selSchool option:selected").text();
		$("#hidschool").val(test);
		return true;
	}
	
	
}
