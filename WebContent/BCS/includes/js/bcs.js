/*******************************************************************************
 * //function used to load pages inside div on index.jsp
 ******************************************************************************/
function loadMainDivPage(urltoload) {
	$("#pageContentBody").load(urltoload);
	// $('#printJob').css('height', 600);
	// $('#printJob').css('width', 600);
}
/*******************************************************************************
 * //functions used to open the approve(reject) dialog box //and set the span
 * id's with correct info
 ******************************************************************************/
function openApprove() {
	$("#modaltitle").text("Approve Contractor");
	$("#modaltext")
			.text(
					"Are you sure you would like to approve "
							+ $("#hidfullname").val());
	$("#trantype").val("A");
	$("#modalnotes").hide();
	$('#myModal').modal('show');
}
function openReject() {
	$("#modaltitle").text("Reject Contractor");
	$("#modaltext").text(
			"Are you sure you would like to reject " + $("#hidfullname").val());
	$("#trantype").val("R");
	$("#modalnotes").show();
	$('#myModal').modal('show');
}
function openSuspend() {
	$("#modaltitle").text("Suspend Contractor");
	$("#modaltext")
			.text(
					"Are you sure you would like to suspend "
							+ $("#hidfullname").val());
	$("#trantype").val("S");
	$("#modalnotes").show();
	$('#myModal').modal('show');
}
function openUnSuspend() {
	$("#modaltitle").text("Unsuspend Contractor");
	$("#modaltext").text(
			"Are you sure you would like to unsuspend "
					+ $("#hidfullname").val());
	$("#trantype").val("U");
	$("#modalnotes").show();
	$('#myModal').modal('show');
}
/*******************************************************************************
 * Calls correct ajax depending on rej or app
 ******************************************************************************/
function approverejectcontractor() {
	var ttype = $('#trantype').val();
	if (ttype == "A") {
		approvecontractor();
	} else if (ttype == "R") {
		rejectcontractor();
	} else if (ttype == "S") {
		suspendcontractor();
	} else if (ttype == "U") {
		unsuspendcontractor();
	}
}
/*******************************************************************************
 * Calls ajax post for approve contractor
 ******************************************************************************/
function approvecontractor() {
	var contractorid = "";
	contractorid = $("#cid").val();
	$.ajax({
		url : 'approveContractorAjax.html',
		type : 'POST',
		data : {
			cid : contractorid
		},
		success : function(xml) {
			$(xml).find('CONTRACTORSTATUS').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "STATUSUPDATED") {
							$('#myModal').modal('hide');
							$('.modal-backdrop').remove();
							$('body').removeClass('modal-open');
							$("#successmessage").html(
									"Contractor has been approved").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();
							var surl = "adminViewContractor.html?cid=" + contactorid;

							setTimeout(function() {
								$("#pageContentBody").load(surl);
							}, 4000);
						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalert').show();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$('#mainalerts').show();
		},
		dataType : "text",
		async : false

	});
}
/*******************************************************************************
 * Calls ajax post for reject contractor
 ******************************************************************************/
function rejectcontractor() {
	var contractorid = "";
	contractorid = $("#cid").val();
	var rejectnotes = $("#rnotes").val();
	$.ajax({
		url : 'rejectContractorAjax.html',
		type : 'POST',
		data : {
			cid : contractorid,
			rnotes : rejectnotes
		},
		success : function(xml) {
			$(xml).find('CONTRACTORSTATUS').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "STATUSUPDATED") {
							$('#myModal').modal('hide');
							$('.modal-backdrop').remove();
							$('body').removeClass('modal-open');
							$("#successmessage").html(
									"Contractor has been rejected").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();
							var surl = "adminViewContractor.html?cid=" + contractorid;
							setTimeout(function() {
								$("#pageContentBody").load(surl);
							}, 4000);
						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalert').show();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$('#mainalerts').show();
		},
		dataType : "text",
		async : false

	});
}
/*******************************************************************************
 * Calls ajax post for suspend contractor
 ******************************************************************************/
function suspendcontractor() {
	var contractorid = "";
	contractorid = $("#cid").val();
	var rejectnotes = $("#rnotes").val();
	$.ajax({
		url : 'suspendContractorAjax.html',
		type : 'POST',
		data : {
			cid : contractorid,
			rnotes : rejectnotes
		},
		success : function(xml) {
			$(xml).find('CONTRACTORSTATUS').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "STATUSUPDATED") {
							$('#myModal').modal('hide');
							$('.modal-backdrop').remove();
							$('body').removeClass('modal-open');
							$("#successmessage").html(
									"Contractor has been suspended").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();
							var surl = "adminViewContractor.html?cid=" + contractorid;

							setTimeout(function() {
								$("#pageContentBody").load(surl);
							}, 4000);
						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalert').show();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$('#mainalerts').show();
		},
		dataType : "text",
		async : false

	});
}/***************************************************************************
	 * Calls ajax post for unsuspend contractor
	 **************************************************************************/
function unsuspendcontractor() {
	var contractorid = "";
	contractorid = $("#cid").val();
	var rejectnotes = $("#rnotes").val();
	$.ajax({
		url : 'unsuspendContractorAjax.html',
		type : 'POST',
		data : {
			cid : contractorid,
			rnotes : rejectnotes
		},
		success : function(xml) {
			$(xml).find('CONTRACTORSTATUS').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "STATUSUPDATED") {
							$('#myModal').modal('hide');
							$('.modal-backdrop').remove();
							$('body').removeClass('modal-open');
							$("#successmessage").html(
									"Contractor has been unsuspended").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();
							var surl = "adminViewContractor.html?cid=" + contractorid;

							setTimeout(function() {
								$("#pageContentBody").load(surl);
							}, 4000);
						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalert').show();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$('#mainalerts').show();
		},
		dataType : "text",
		async : false

	});
}

/*******************************************************************************
 * Calls open reset password dialog
 ******************************************************************************/
function openReset() {
	var email = $("#username").val();

	if (email == "") {
		$('#errormessage').html("Please enter your email address.").css(
				"display", "block").delay(4000).fadeOut();

		return false;
	} else {
		$('#error_message').slideUp({
			opacity : "show"
		}, "slow");
		// we need to retrieve the question
		if (getResetInformation(email)) {
			$("#modaltitle").text("Reset Password");
			$("#modaltext").text($("#question").val());
			$("#cstep").val("1");
			$('#myModal').modal('show');
		} else {
			// do nothing error message show in reset information function
		}

	}

}
/*******************************************************************************
 * Calls ajax post for get reset information
 ******************************************************************************/
function getResetInformation(emailadd) {
	var isvalid = false;
	$
			.ajax({
				url : 'getResetInformationAjax.html',
				type : 'POST',
				data : {
					email : emailadd
				},
				success : function(xml) {
					$(xml)
							.find('CONTRACTOR')
							.each(
									function() {
										// now add the items if any
										if ($(this).find("MESSAGE").text() == "SUCCESS") {
											isvalid = true;
											// we need to populate hidden fields
											// and question text
											$("#email").val(
													$(this).find("EMAIL")
															.text());
											$("#question").val(
													$(this).find("QUESTION")
															.text());
											$("#answer").val(
													$(this).find("ANSWER")
															.text());
											$("#cid").val(
													$(this).find("ID").text());

										} else {
											$("#errormessage").html(
													$(this).find("MESSAGE")
															.text()).css(
													"display", "block").delay(
													6000).fadeOut();
											$('#mainalert').show();
										}

									});
				},
				error : function(xhr, textStatus, error) {
					$("#errormessage").html(error).css("display", "block")
							.delay(6000).fadeOut();
					$('#mainalerts').show();
				},
				dataType : "text",
				async : false

			});
	return isvalid;
}
/*******************************************************************************
 * Confirm which step they are at and check values
 ******************************************************************************/
function confirmSecurity() {
	var step = $("#cstep").val();
	if (step == 1) {
		// we need to check typed answer against the system one
		var ianswer = $("#txtanswer").val();
		if (ianswer == "") {
			$("#errorspanm").text("Please enter an answer");
			$("#error_message_m").show();
		} else {
			var sanswer = $("#answer").val();
			$("#error_message_m").hide();
			if (ianswer == sanswer) {
				$("#cstep").val("2")
				$("#divstep1").hide();
				$("#divstep2").show();
			} else {
				$("#errorspanm").text("Answer does not match one on file");
				$("#error_message_m").show();
			}
		}
	} else if (step == 2) {
		var newpass = $("#txtnpassword").val();
		var cnewpass = $("#txtcnpassword").val();
		if (newpass == "") {
			$("#errorspanm").text("Please enter new password");
			$("#error_message_m").show();
		} else if (cnewpass == "") {
			$("#errorspanm").text("Please enter confirm new password");
			$("#error_message_m").show();
		} else if (newpass.length < 7) {
			$("#errorspanm").text("New password must be 7 characters or more");
			$("#error_message_m").show();
		} else if (cnewpass.length < 7) {
			$("#errorspanm").text(
					"Confirm new password must be 7 characters or more");
			$("#error_message_m").show();
		} else if (newpass != cnewpass) {
			$("#errorspanm").text(
					"New password must match confirm new password");
			$("#error_message_m").show();
		} else {
			// new password valid
			$("#error_message_m").hide();
			// update password
			if (updateSecurityInfo($("#cid").val(), newpass)) {
				$("#success_message_m").show();
				$("#divstep1").hide();
				$("#divstep2").hide();
				$("#modalbuttons").hide();
			}

		}
	}
}
/*******************************************************************************
 * Calls ajax post for get reset information
 ******************************************************************************/
function updateSecurityInfo(csid, newpassword) {
	var isvalid = false;
	$.ajax({
		url : 'updatePasswordAjax.html',
		type : 'POST',
		data : {
			cid : csid,
			npassword : newpassword
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "UPDATED") {
							isvalid = true;

						} else {
							$("#errorspanm").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#error_message_m').show();
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errorspanm").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$('#error_message_m').show();
		},
		dataType : "text",
		async : false

	});
	return isvalid;
}
/*******************************************************************************
 * Check security fields for validations
 ******************************************************************************/
function confirmSecurityFields() {

	var newpass = $("#npassword").val();
	var cnewpass = $("#cnpassword").val();
	var squestion = $("#question").val();
	var sanswer = $("#answer").val();
	if (newpass == "") {
		$("#errormessage").text("Please enter password")
				.CSS("display", "block");
		$("#mainalert").show();
	} else if (cnewpass == "") {
		$("#errormessage").text("Please enter confirm password");
		$("#mainalert").show();
	} else if (newpass.length < 7) {
		$("#errormessage").text("Password must be 7 characters or more");
		$("#mainalert").show();
	} else if (cnewpass.length < 7) {
		$("#errormessage")
				.text("Confirm password must be 7 characters or more");
		$("#mainalert").show();
	} else if (newpass != cnewpass) {
		$("#errormessage").text("Password must match confirm password");
		$("#mainalert").show();
	} else if (squestion == "") {
		$("#errormessage").text("Please enter security question");
		$("#mainalert").show();
	} else if (squestion.length < 5) {
		$("#errormessage").text(
				"Security question must be 5 characters or more");
		$("#mainalert").show();
	} else if (sanswer == "") {
		$("#errormessage").text("Please enter answer");
		$("#mainalert").show();
	} else if (sanswer.length < 5) {
		$("#errormessage").text("Answer must be 5 characters or more");
		$("#mainalert").show();
	} else {
		// new password valid
		$("#mainalerts").hide();
		updateSecInformation(newpass, cnewpass, squestion, sanswer);

	}
}
/*******************************************************************************
 * Calls ajax post for get reset information
 ******************************************************************************/
function updateSecInformation(password, cnpassword, question, answer) {
	var scid = $("#scid").val();
	var isvalid = false;
	$.ajax({
		url : 'updateSecuirtyInfoAjax.html',
		type : 'POST',
		data : {
			cid : scid,
			npassword : password,
			squestion : question,
			sqanswer : answer
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "UPDATED") {
							isvalid = true;
							// $("#successmessage").text("Security Information
							// Updated");
							// $("#mainalerts").show();
							// $("#mainalert").hide();
							$('#display_success_message_bottom').text(
									"Security Information Updated").css(
									"display", "block").delay(6000).fadeOut();

						} else {
							$('#display_error_message_bottom').text(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$('#details_error_message_bottom').text(
					$(this).find("ERROR").text()).css("display", "block")
					.delay(6000).fadeOut();
			;
		},
		dataType : "text",
		async : false

	});
	return isvalid;
}
/*******************************************************************************
 * Check security fields for validations
 ******************************************************************************/
function confirmCompanyFields() {

	var tregular = $('#tregular').prop('checked');
	var talternate = $('#talternate').prop('checked');
	var tparent = $("#tparent").val();
	var crsameas = $('#crsameas').prop('checked');
	var crfirstname = $("#crfirstname").val();
	var crlastname = $("#crlastname").val();
	var crphonenumber = $("#crphonenumber").val();
	var cremail = $("#cremail").val();
	var tosameas = $('#tosameas').prop('checked');
	var tofirstname = $("#tofirstname").val();
	var tolastname = $("#tolastname").val();
	var tophonenumber = $("#tophonenumber").val();
	var toemail = $("#toemail").val();
	$("#mainalert").hide();
	if ((!(tregular)) && (!(talternate))) {
		$('#display_error_message_bottom').text(
				"Please select type of transportation").css("display", "block")
				.delay(6000).fadeOut();
		return false;
	}
	if (!(crsameas)) {
		if (crfirstname == "") {
			$('#display_error_message_bottom').text(
					"Please enter contractor representative first name").css(
					"display", "block").delay(6000).fadeOut();
			return false;
		} else if (crlastname == "") {
			$('#display_error_message_bottom').text(
					"Please enter contractor representative first name").css(
					"display", "block").delay(6000).fadeOut();
			return false;
		} else if (crphonenumber == "") {
			$('#display_error_message_bottom').text(
					"Please enter contractor representative phone number").css(
					"display", "block").delay(6000).fadeOut();
			return false;
		} else if (cremail == "") {
			$('#display_error_message_bottom').text(
					"Please enter contractor representative email").css(
					"display", "block").delay(6000).fadeOut();
			return false;
		} else if (!(isEmail(cremail))) {
			$('#display_error_message_bottom').text(
					"Please enter valid contractor representative email").css(
					"display", "block").delay(6000).fadeOut();
			return false;
		}
	}
	if (!(tosameas)) {
		if (tofirstname == "") {
			$('#display_error_message_bottom').text(
					"Please enter transportation officer first name").css(
					"display", "block").delay(6000).fadeOut();
			return false;
		} else if (tolastname == "") {
			$('#display_error_message_bottom').text(
					"Please enter transportation officerlast name").css(
					"display", "block").delay(6000).fadeOut();
			return false;
		} else if (tophonenumber == "") {
			$('#display_error_message_bottom').text(
					"Please enter transportation officer phone number").css(
					"display", "block").delay(6000).fadeOut();
			return false;
		} else if (toemail == "") {
			$('#display_error_message_bottom').text(
					"Please enter transportation officer email").css("display",
					"block").delay(6000).fadeOut();
			return false;
		} else if (!(isEmail(toemail))) {
			$('#display_error_message_bottom').text(
					"Please enter valid transportation officer email").css(
					"display", "block").delay(6000).fadeOut();
			return false;
		}
	}
	// all good
	$("#mainalerts").hide();
	updateCompanyInformation();

}
/*******************************************************************************
 * Check email address is valid
 ******************************************************************************/
function isEmail(email) {
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return regex.test(email);
}
/*******************************************************************************
 * Calls ajax post to update/insert company info
 ******************************************************************************/
function updateCompanyInformation() {
	var isvalid = false;
	var frm = $('#contact-form-up');
	$.ajax({
		url : 'updateCompanyInfo.html',
		type : 'POST',
		data : frm.serialize(),
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "UPDATED") {
							isvalid = true;
							$('#display_success_message_bottom').text(
									"Company Information Updated").css(
									"display", "block").delay(6000).fadeOut();

						} else {
							$("#display_error_message_bottom").text(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(4000).fadeOut();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#display_error_message_bottom").html(error).css("display",
					"block").delay(6000).fadeOut();
		},
		dataType : "text",
		async : false

	});
	return isvalid;
}
/*******************************************************************************
 * Check add new vehicle fields
 ******************************************************************************/
function confirmVehicleFields(usertype,validatedates) {

	var vmake = $("#vmake").val();
	var vmodel = $("#vmodel").val();
	var vyear = $("#vyear").val();
	var vserialnumber = $("#vserialnumber").val();
	var vplatenumber = $("#vplatenumber").val();
	var vtype = $("#vtype").val();
	var vsize = $("#vsize").val();
	var vrowner = $("#vrowner").val();
	var idate = $("#idate").val()
	var rdate = $("#rdate").val();
	var insuranceprovider = $("#insuranceprovider").val();
	var vmaketext = $("#vmake").find("option:selected").text();
	var vmakeother = $("#vmakeother").val();
	if (vmake == "-1") {
		$("#display_error_message_bottom").text("Please select vehicle make")
				.css("display", "block").delay(4000).fadeOut();
		$("#vmake").focus();
		return false;
	}
	if (vmaketext == "Other") {
		if (vmakeother == "") {
			$("#display_error_message_bottom")
					.text("Please enter vehicle make").css("display", "block")
					.delay(4000).fadeOut();
			$("#vmakeother").focus();
			return false;
		}
	}
	if (vmodel == "") {
		$("#display_error_message_bottom").text("Please enter vehicle model")
				.css("display", "block").delay(4000).fadeOut();

		return false;
	}
	if (vyear == "-1") {
		$("#display_error_message_bottom").text("Please select vehicle year")
				.css("display", "block").delay(4000).fadeOut();

		return false;
	}
	if (vserialnumber == "") {
		$("#display_error_message_bottom").text("Please enter serial number")
				.css("display", "block").delay(4000).fadeOut();

		return false;
	}
	if (vplatenumber == "") {
		$("#display_error_message_bottom").text("Please enter plate number")
				.css("display", "block").delay(4000).fadeOut();

		return false;
	}
	if (vtype == "-1") {
		$("#display_error_message_bottom").text("Please select vehicle type")
				.css("display", "block").delay(4000).fadeOut();

		return false;
	}
	if (vsize == "-1") {
		$("#display_error_message_bottom").text("Please select vehicle size")
				.css("display", "block").delay(4000).fadeOut();

		return false;
	}
	if (vrowner == "") {
		$("#display_error_message_bottom")
				.text("Please enter registered owner").css("display", "block")
				.delay(4000).fadeOut();

		return false;
	}
	if (rdate == "") {
		$("#display_error_message_bottom").text(
				"Please enter registration expiry date")
				.css("display", "block").delay(4000).fadeOut();

		return false;
	}
	if (idate == "") {
		$("#display_error_message_bottom").text(
				"Please enter insurance expiry date").css("display", "block")
				.delay(4000).fadeOut();

		return false;
	}
	if (insuranceprovider == "") {
		$("#display_error_message_bottom").text(
				"Please enter insurance provider").css("display", "block")
				.delay(4000).fadeOut();

		return false;
	}
	if (usertype == "A") {
		var selectedc = $("#contractor").val();
		if (selectedc < 1) {
			$("#body_error_message_top").html("Please select contractor").css(
					"display", "block");
			$('.nav-tabs a:first').tab('show')
			$("#contractor").focus();
			return false;
		}
	}
	//check the date fields
	if(validatedates == "Y"){
		if(!(checkdatefields())){
			return false;
		}
	}
	// all good
	$("#mainalert").hide();
	// updateCompanyInformation();
	if ($("#vid").val() <= 0) {
		addNewVehicle(usertype);
	} else {
		updateVehicle(usertype);
	}

}
/*******************************************************************************
 * Calls ajax post to update/insert company info
 ******************************************************************************/
function addNewVehicle(usertype) {
	var isvalid = false;
	var form = $('#contact-form-up')[0];
	var formData = new FormData(form);
	var poststring = "";
	if (usertype == "A") {
		poststring = "addNewVehicleAdminSubmit.html"
	} else {
		poststring = "addNewVehicleSubmit.html"
	}
	$.ajax({
		url : poststring,
		type : 'POST',
		data : formData,
		processData : false,
		contentType : false,
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "UPDATED") {
							isvalid = true;
							$("#display_success_message_bottom").text(
									"Vehicle has been added to system.").css(
									"display", "block").delay(4000).fadeOut();

							var vid = "";
							var surl = "";
							if (usertype == "A") {
								vid = $(this).find("VID").text();
								surl = "adminViewVehicle.html?cid=" + vid;
							} else {
								vid = $(this).find("VID").text();
								surl = "addNewVehicle.html?vid=" + vid;
							}

							$("#pageContentBody").load(surl);

						} else {
							$("#display_error_message_bottom").text(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(4000).fadeOut();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#display_error_message_bottom").html(error).css("display",
					"block").delay(6000).fadeOut();

		},
		dataType : "text",
		async : false

	});
	return isvalid;
}
/*******************************************************************************
 * Calls ajax post to update/insert company info
 ******************************************************************************/
function updateVehicle(usertype) {
	var isvalid = false;
	var form = $('#contact-form-up')[0];
	var formData = new FormData(form);
	var surl="";
	if(usertype=="A"){
		surl="updateVehicleAdminSubmit.html";
	}else{
		surl="updateVehicleSubmit.html";
	}
	$.ajax({
		url : surl,
		type : 'POST',
		data : formData,
		processData : false,
		contentType : false,
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						if ($(this).find("MESSAGE").text() == "UPDATED") {
							isvalid = true;
							$("#vehiclesuccessmessage").text(
									"Vehicle has been updated.").css("display",
									"block").delay(4000).fadeOut();

							var vid = "";
							var surl = "";
							if (usertype == "A") {
								vid = $(this).find("VID").text();
								surl = "adminViewVehicle.html?cid=" + vid;
							} else {
								vid = $(this).find("VID").text();

								surl = "addNewVehicle.html?vid=" + vid;
							}
							// $("#pageContentBody").load(surl);
							$("#pageContentBody").load(surl);
						} else {
							$("#vehicleerrormessage").text(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(4000).fadeOut();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#vehicleerrormessage").html(error).css("display", "block")
					.delay(6000).fadeOut();

		},
		dataType : "text",
		async : false

	});
	return isvalid;
}
/*******************************************************************************
 * Opens dialog for deleting km rate confirmation
 ******************************************************************************/
function opendeletedialog(splate, id,trantype) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitle').text("Delete Vehicle");
	$('#title1').text("Vehicle Plate Number: " + splate);
	$('#title2').text("");
	$('#title3').text("Are you sure you want to delete this vehicle?");
	$('#buttonleft').text("YES");
	$('#buttonright').text("NO");
	// now we add the onclick event
	$("#buttonleft").click(function() {
		deletevehicle(splate, id,trantype);
	});
	$('#myModal').modal(options);

}
/*******************************************************************************
 * Calls ajax post for delete vehicle
 ******************************************************************************/
function deletevehicle(splate, id,trantype) {
	var surl="";
	if(trantype == "A"){
		surl="deleteVehicleAdmin.html";
	}else{
		surl="deleteVehicle.html";
	}
	$.ajax({
		url : surl,
		type : 'POST',
		data : {
			vid : id,
			plate : splate,
			ttype: trantype
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#successmessage").html(
									"Vehicle Has Been Deleted").css("display",
									"block").delay(6000).fadeOut();
							$('#mainalerts').show();
							var surl="";
							if(trantype =="A"){
								surl = "viewVehiclesApprovals.html?status=re";
							}else{
								surl = "viewContractorVehicles.html";
							}
							
							$("#pageContentBody").load(surl);
							$('#myModal').modal('hide');

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for adding new document
 ******************************************************************************/
function openaddnewdialog(trantype) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonleft').text("YES");
	$('#buttonright').text("NO");
	// now we add the onclick event
	$("#buttonleft").click(function(event) {
		event.preventDefault();
		if (checknewdocument()) {
			addnewvehicledocument(trantype)
		}
		// deletevehicle(splate,id);

	});
	$('#myModal').modal(options);

}
/*******************************************************************************
 * add new vehicle document
 ******************************************************************************/
function checknewdocument() {
	var documenttitle = $.trim($('#documenttitle').val());
	// var selected = $("#documenttype option:selected");
	var selected = $("#documenttype").val();
	var pfile = $.trim($('#documentname').val());
	if (selected < 1) {
		$("#demessage").html("Please enter document type").css("display",
				"block").delay(6000).fadeOut();
		$("#dalert").show();
		return false;
	}
	if (documenttitle == "") {
		$("#demessage").html("Please enter document title").css("display",
				"block").delay(6000).fadeOut();
		$("#dalert").show();
		return false;
	}
	if (pfile == "") {
		$("#demessage").html("Please select document").css("display", "block")
				.delay(6000).fadeOut();
		$("#dalert").show();
		return false;
	}else{
		var reg = /(.*?)\.(pdf)$/;
		if(!pfile.match(reg)){
			$("#demessage").html("PDF documents only")
			.css("display", "block").delay(6000).fadeOut();
			$("#dalert").show();
			return false;
		}
	}
	$("#dalert").hide();

	return true;
}
/*******************************************************************************
 * add new vehicle document
 ******************************************************************************/
function addnewvehicledocument(trantype) {
	var documenttitle = $.trim($('#documenttitle').val());
	// var selected = $("#documenttype option:selected");
	var selected = $("#documenttype").val();
	var pfile = $.trim($('#documentname').val());
	var ufile = $('#documentname')[0].files[0];
	var vehicleid = $.trim($('#vid').val());
	var requestd = new FormData();
	requestd.append('documenttitle', documenttitle);
	requestd.append('vid', vehicleid);
	requestd.append('documenttype', selected);
	requestd.append('documentfile', ufile);
	requestd.append('documentname', documentname)
	$.ajax({
		url : "addNewVehicleDocument.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {

			var i = 1;
			$(xml).find('CONTRACTOR').each(
					function() {

						if ($(this).find("MESSAGE").text() == "UPDATED") {
							var surl="";
							if(trantype == "A"){
								surl = "adminViewVehicle.html?cid="
									+ $(this).find("VID").text() + "&tab=D";
							}else{
								surl = "addNewVehicle.html?vid="
									+ $(this).find("VID").text() + "&tab=D";
							}
							
							$("#pageContentBody").load(surl);
							$("#display_success_message_bottom").html(
									"Vehilcle Document Has Been Added").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();
							if(trantype == "A"){
								$('#myModalDoc').modal('hide');
							}else{
								$('#myModal').modal('hide');
							}
							
						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal').modal('hide');
						}
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$("#errormessage").html(textStatus).css("display", "block").delay(
					6000).fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false
	});

}
/*******************************************************************************
 * Opens dialog for adding new document
 ******************************************************************************/
function opendeletedocdialog(sdoc, id,trantype) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitled').text("Delete Vehicle Document");
	$('#spantitle1').text("Vehicle Document: " + sdoc);
	$('#spantitle2').text("Are you sure you want to delete this document");
	$('#buttonleftd').text("YES");
	$('#buttonrightd').text("NO");
	// now we add the onclick event
	$("#buttonleftd").click(function() {
		deletevehicledocument(sdoc, id,trantype)
	});
	$('#myModal2').modal(options);
}
/*******************************************************************************
 * Calls ajax post for delete vehicle
 ******************************************************************************/
function deletevehicledocument(docname, id,trantype) {
	var vehicleid = $("#vid").val();
	$.ajax({
		url : 'deleteVehicleDocument.html',
		type : 'POST',
		data : {
			vid : vehicleid,
			document : docname,
			did : id
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							var surl="";
							if(trantype == "A"){
								surl = "adminViewVehicle.html?cid="
									+ $(this).find("VID").text() + "&tab=D"
							}else{
								surl = "addNewVehicle.html?vid="
									+ $(this).find("VID").text() + "&tab=D";
							}
							$("#pageContentBody").load(surl);
							$("#display_success_message_bottom").html(
									"Vehilcle Document Has Been Deleted").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();
							if(trantype == "A"){
								$('#myModalDocD').modal('hide');
							}else{
								$('#myModal2').modal('hide');
							}
							

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal2').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * add new/update employee check
 ******************************************************************************/
function checkemployee(usert,validatedates) {
	$("#mainalert").hide();
	$("#mainalerts").hide();
	var selected = $("#employeeposition").val();
	var firstname = $("#firstname").val();
	var lastname = $("#lastname").val();
	var address1 = $("#address1").val();
	var city = $("#city").val();
	var province = $("#province").val();
	var postalcode = $("#postalcode").val();
	var email = $("#email").val();
	var homephone = $("#homephone").val();
	var startmonth = $("#vmonth").val();
	var startyear = $("#vyear").val();
	var birthdate = $("#birthdate").val();
	if (selected < 1) {
		$("#body_error_message_bottom").html("Please enter position type").css(
				"display", "block");
		$('.nav-tabs a:first').tab('show')
		$("#employeeposition").focus();
		return false;
	}
	if (firstname == "") {
		$("#body_error_message_bottom").html("Please enter first name").css(
				"display", "block");
		$('.nav-tabs a:first').tab('show')
		$("#firstname").focus();
		return false;
	}
	if (lastname == "") {
		$("#body_error_message_bottom").html("Please enter last name").css(
				"display", "block");
		$('.nav-tabs a:first').tab('show')
		$("#lastname").focus();
		return false;
	}
	if (birthdate == "") {
		$("#body_error_message_bottom").html("Please enter date of birth").css(
				"display", "block");
		$('.nav-tabs a:first').tab('show')
		$("#birthdate").focus();
		return false;
	}
	if (email == "") {
		$("#body_error_message_bottom").html("Please enter email").css(
				"display", "block");
		$('.nav-tabs a:first').tab('show')
		$("#email").focus();
		return false;
	} else {
		if (!(isEmail(email))) {
			$("#body_error_message_bottom").html("Please enter valid email")
					.css("display", "block");
			$('.nav-tabs a:first').tab('show')
			$("#email").focus();
			return false;
		}
	}
	if (address1 == "") {
		$("#body_error_message_bottom").html("Please enter address1").css(
				"display", "block");
		$('.nav-tabs a:first').tab('show')
		$("#address1").focus();
		return false;
	}
	if (city == "") {
		$("#body_error_message_bottom").html("Please enter city").css(
				"display", "block");
		$('.nav-tabs a:first').tab('show')
		$("#city").focus();
		return false;
	}
	if (province < 1) {
		$("#body_error_message_bottom").html("Please enter province").css(
				"display", "block");
		$('.nav-tabs a:first').tab('show')
		$("#province").focus();
		return false;
	}
	if (postalcode == "") {
		$("#body_error_message_bottom").html("Please enter postal code").css(
				"display", "block");
		$('.nav-tabs a:first').tab('show')
		$("#postalcode").focus();
		return false;
	}
	if (startmonth < 1) {
		$("#body_error_message_bottom").html("Please select start month").css(
				"display", "block");
		$('.nav-tabs a:first').tab('show')
		$("#vmonth").focus();
		return false;
	}
	if (startyear < 1) {
		$("#body_error_message_bottom").html("Please select start year").css(
				"display", "block");
		$('.nav-tabs a:first').tab('show')
		$("#vyear").focus();
		return false;
	}
	// now we check to see if it is a driver and check for DL Info
	if (selected == 20) {
		var dlnumber = $("#dlnumber").val();
		var dlexpirydate = $("#dlexpirydate").val();
		var dlclass = $("#dlclass").val();
		if (dlnumber == "") {
			$("#body_error_message_top").html(
					"Please enter driver licence number").css("display",
					"block");

			$('.nav-tabs a:last').tab('show')
			$("#dlnumber").focus();
			return false;
		}
		if (dlexpirydate == "") {
			$("#body_error_message_top").html(
					"Please enter driver licence expiry date").css("display",
					"block");
			$('.nav-tabs a:last').tab('show')
			$("#dlexpirydate").focus();
			return false;
		}
		if (dlclass < 1) {
			$("#body_error_message_top").html(
					"Please select driver licence class").css("display",
					"block");
			$('.nav-tabs a:last').tab('show')
			$("#dlclass").focus();
			return false;
		}
	}
	//check files to make sure they are pdf
	if(!($("#dlfront").val() == "")){
		if(!(checkfileextension($("#dlfront").val()))){
			$("#body_error_message_top").html(
			"Licence Image Front: Only pdf files accepted").css("display",
			"block");
			$('#documents').tab('show');
			//$('.nav-tabs a:last').tab('show');
			
			$("#dlfront").focus();
			return false;
		}
	}
	if(!($("#dlback").val() == "")){
		if(!(checkfileextension($("#dlback").val()))){
			$("#body_error_message_top").html(
			"Licence Image Back: Only pdf files accepted").css("display",
			"block");
			$('#documents').tab('show');
			//$('.nav-tabs a:last').tab('show');
			
			$("#dlback").focus();
			return false;
		}
	}
	if(!($("#dadocument").val() == "")){
		if(!(checkfileextension($("#dadocument").val()))){
			$("#body_error_message_top").html(
			"Driver Abstract File: Only pdf files accepted").css("display",
			"block");
			$('#documents').tab('show');
			//$('.nav-tabs a:last').tab('show');
			
			$("#dadocument").focus();
			return false;
		}
	}
	if(!($("#fadocument").val() == "")){
		if(!(checkfileextension($("#fadocument").val()))){
			$("#body_error_message_top").html(
			"First Aid/Epipen File: Only pdf files accepted").css("display",
			"block");
			$('#documents').tab('show');
			//$('.nav-tabs a:last').tab('show');
			
			$("#fadocument").focus();
			return false;
		}
	}
	if(!($("#prcvsqdocument").val() == "")){
		if(!(checkfileextension($("#prcvsqdocument").val()))){
			$("#body_error_message_top").html(
			"PRC/VSQ File: Only pdf files accepted").css("display",
			"block");
			$('#documents').tab('show');
			//$('.nav-tabs a:last').tab('show');
			
			$("#prcvsqdocument").focus();
			return false;
		}
	}
	if(!($("#pccdocument").val() == "")){
		if(!(checkfileextension($("#pccdocument").val()))){
			$("#body_error_message_top").html(
			"Provincial Court Check File: Only pdf files accepted").css("display",
			"block");
			$('#documents').tab('show');
			//$('.nav-tabs a:last').tab('show');
			
			$("#pccdocument").focus();
			return false;
		}
	}
	if(!($("#scadocument").val() == "")){
		if(!(checkfileextension($("#scadocument").val()))){
			$("#body_error_message_top").html(
			"Signed File: Only pdf files accepted").css("display",
			"block");
			$('#documents').tab('show');
			//$('.nav-tabs a:last').tab('show');
			
			$("#scadocument").focus();
			return false;
		}
	}
	//check dates to make sure they are in range
	//check to see if the employee selected to by pass date checks
	
	if(validatedates == "Y"){
		
	
	if(!($("#darundate").val() == "")){
		var today = new Date();
		var cyear = today.getFullYear();
		var cmonth = today.getMonth();
		var selectedDate = $('#darundate').datepicker('getDate');
		//first we check for future dates
		if(Date.parse(selectedDate) > Date.parse(today)){
			$("#body_error_message_top").html(
					"Driver Abstract Run Date must be in past").css("display",
					"block");
					$('#documents').tab('show');
					$("#darundate").focus();
					showBypassDialog();
					return false;
		}
		//now we check to see if it falls in the date range
		if(cmonth > 5 && cmonth < 12){
			//check current year
			var checkdate= new Date(cyear, 4, 1);
			if(Date.parse(selectedDate) < checkdate){
				$("#body_error_message_top").html(
				"Driver Abstract Run Date must be greater than " + checkdate.toLocaleDateString()).css("display",
				"block");
				$('#documents').tab('show');
				$("#darundate").focus();
				showBypassDialog();
				return false;
			}
		}else{
			//now we check back to the previous year
			var checkdate= new Date(cyear-1, 4, 1);
			if(Date.parse(selectedDate) < checkdate){
				$("#body_error_message_top").html(
						"Driver Abstract Run Date must be greater than " + checkdate.toLocaleDateString()).css("display",
						"block");
						$('#documents').tab('show');
						$("#darundate").focus();
						showBypassDialog();
						return false;
			}
		}
	}
		if(!($("#prcvsqdate").val() == "")){
			var today = new Date();
			var cyear = today.getFullYear();
			var cmonth = today.getMonth();
			var selectedDate = $('#prcvsqdate').datepicker('getDate');
			//first we check for future dates
			if(Date.parse(selectedDate) > Date.parse(today)){
				$("#spanprcvsqdate").html("PRC/VSQ Date must be in past");
				$("#divprcvsqdate").show();
				$("#body_error_message_top").html(
						"PRC/VSQ Date must be in past").css("display",
						"block");
						$('#documents').tab('show');
						$("#prvvsqdate").focus();
						showBypassDialog();
						return false;
			}
			//now we check to see if it falls in the date range
			if(!($("#continuousservice").val() == "") && !(isNaN($("#continuousservice").val()))){
				if($("#continuousservice").val() < 2 ){
					if(cmonth > 5 && cmonth < 12){
						//check current year
						var checkdate= new Date(cyear, 4, 1);
						if(Date.parse(selectedDate) < checkdate){
							$("#body_error_message_top").html(
									"PRC/VSQ Date must be greater than " + checkdate.toLocaleDateString()).css("display",
									"block");
									$('#documents').tab('show');
									$("#prcvsqdate").focus();
									showBypassDialog();
									return false;
							
						}
					}else{
						//now we check back to the previous year
						var checkdate= new Date(cyear-1, 4, 1);
						if(Date.parse(selectedDate) < checkdate){
							$("#body_error_message_top").html(
									"PRC/VSQ Date must be greater than " + checkdate.toLocaleDateString()).css("display",
									"block");
									$('#documents').tab('show');
									$("#prcvsqdate").focus();
									showBypassDialog();
									return false;
						}
					}
				}else{
					if(cmonth > 5 && cmonth < 12){
						//check current year
						var checkdate= new Date(cyear-1, 4, 1);
						if(Date.parse(selectedDate) < checkdate){
							$("#body_error_message_top").html(
									"PRC/VSQ Date must be greater than " + checkdate.toLocaleDateString()).css("display",
									"block");
									$('#documents').tab('show');
									$("#prcvsqdate").focus();
									showBypassDialog();
									return false;
						}
					}else{
						//now we check back to the previous year
						var checkdate= new Date(cyear-2, 4, 1);
						if(Date.parse(selectedDate) < checkdate){
							$("#body_error_message_top").html(
									"PRC/VSQ Date must be greater than " + checkdate.toLocaleDateString()).css("display",
									"block");
									$('#documents').tab('show');
									$("#prcvsqdate").focus();
									showBypassDialog();
									return false;
						}
					}
				}
			}
		}
			
	//now check valid date ranges
	if(!($("#dlexpirydate").val() == "")){
		var today = new Date();
		var targetDate= new Date();
		targetDate.setDate(today.getDate()+ 1825);
		var selectedDate = $('#dlexpirydate').datepicker('getDate');
		if ((Date.parse(today) > Date.parse(selectedDate)) || (Date.parse(selectedDate) > Date.parse(targetDate))){
			$("#body_error_message_top").html(
					"DL Expiry Date must be in future and no more than 5 years").css("display",
					"block");
					$('#documents').tab('show');
					$("#dlexpirydate").focus();
					showBypassDialog();
					return false;
		}
	}
	if(!($("#faexpirydate").val() == "")){
		var today = new Date();
		var targetDate= new Date();
		targetDate.setDate(today.getDate()+ 1095);
		var selectedDate = $('#faexpirydate').datepicker('getDate');
		if ((Date.parse(today) > Date.parse(selectedDate)) || (Date.parse(selectedDate) > Date.parse(targetDate))){
			$("#body_error_message_top").html(
			"First Aid/Epipen Date must be in future and no more than 3 years").css("display",
			"block");
			$('#documents').tab('show');
			$("#faexpirydate").focus();
			showBypassDialog();
			return false;
		}
	}
	if(!($("#scadate").val() == "")){
		var today = new Date();
		var selectedDate = $('#scadate').datepicker('getDate');
		if (Date.parse(selectedDate) > Date.parse(today)){
			$("#body_error_message_top").html(
			"Signed Date must not be in future").css("display",
			"block");
			$('#documents').tab('show');
			$("#scadate").focus();
			showBypassDialog();
			return false;
		}
	}
	}
	if (usert == "A") {
		var selectedc = $("#contractor").val();
		if (selectedc < 1) {
			$("#body_error_message_top").html("Please select contractor").css(
					"display", "block");
			$('.nav-tabs a:first').tab('show')
			$("#contractor").focus();
			return false;
		}
	}

	// all good
	$("#body_error_message_top").css("display", "none");
	$("#body_error_message_bottom").css("display", "none");
	// updateCompanyInformation();
	if ($("#cid").val() <= -1) {
		addNewEmployee(usert);
	} else {
		updateEmployee(usert);
	}
}
/*******************************************************************************
 * Calls ajax post to update/insert company info
 ******************************************************************************/
function addNewEmployee(usert) {
	var isvalid = false;
	var form = $('#contact-form-up')[0]; // You need to use standard
											// javascript object here
	var formData = new FormData(form);
	var poststring = "";
	if (usert == "A") {
		poststring = "addNewEmployeeAdminSubmit.html"
	} else {
		poststring = "addNewEmployeeSubmit.html"
	}
	$.ajax({
		url : poststring,
		type : 'POST',
		data : formData,
		processData : false,
		contentType : false,
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "UPDATED") {
							isvalid = true;
							$("#display_success_message_bottom").text(
									"Employee has been added.").css("display",
									"block").delay(4000).fadeOut();

							var vid = $(this).find("VID").text();
							if (usert == "A") {
								var surl = "adminViewEmployee.html?vid=" + vid;

								setTimeout(function() {
									$("#pageContentBody").load(surl);
								}, 4000);

							} else {
								var surl = "addNewEmployee.html?vid=" + vid;
								setTimeout(function() {
									$("#pageContentBody").load(surl);
								}, 4000);
							}

						} else {
							$("#body_error_message_bottom").text(
									$(this).find("MESSAGE").text()).delay(6000)
									.fadeOut();
							;

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#body_errorm_essage_bottom").html(error).css("display", "block")
					.delay(6000).fadeOut();

		},
		dataType : "text",
		async : false

	});
	return isvalid;
}
/*******************************************************************************
 * Opens dialog for deleting employee confirmation
 ******************************************************************************/
function opendeletedialogemp(splate, id,trantype) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitle').text("Delete Employee");
	$('#title1').text("Employee Name: " + splate);
	$('#title2').text("");
	$('#title3').text("Are you sure you want to delete this employee?");
	$('#buttonleft').text("YES");
	$('#buttonright').text("NO");
	// now we add the onclick event
	$("#buttonleft").click(function() {
		deleteemployee(splate, id,trantype);
	});
	$('#myModal').modal(options);

}
/*******************************************************************************
 * Calls ajax post for delete employee
 ******************************************************************************/
function deleteemployee(sname, id,trantype) {
	var surl="";
	if(trantype == "A"){
		surl="deleteEmployeeAdmin.html";
	}else{
		surl="deleteEmployee.html";
	}
	$.ajax({
		url : surl,
		type : 'POST',
		data : {
			eid : id,
			ename : sname,
			ttype:trantype
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#body_success_message_top").html(
									"Employee Has Been Deleted").css("display",
									"block").delay(6000).fadeOut();
							var surl="";
							if(trantype == "A"){
								surl="viewEmployeesApprovals.html?status=re";
							}else{
								surl = "viewContractorEmployees.html";
							}
							$("#pageContentBody").load(surl);
							$('#myModal').modal('hide');

						} else {
							$("#body_error_message_top").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();

							$('#myModal').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#body_error_message_top").html(error).css("display", "block")
					.delay(6000).fadeOut();

		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Calls ajax post for update employee
 ******************************************************************************/

function updateEmployee(usert) {
	var isvalid = false;
	var form = $('#contact-form-up')[0]; // You need to use standard
											// javascript object here
	var formData = new FormData(form);
	var poststring = "";
	if (usert == "A") {
		poststring = "adminUpdateEmployeeSubmit.html"
	} else {
		poststring = "updateEmployeeSubmit.html"
	}
	$.ajax({
		url : poststring,
		type : 'POST',
		data : formData,
		processData : false,
		contentType : false,
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "UPDATED") {
							isvalid = true;
							$("#display_success_message_bottom").text(
									"Employee has been updated.").css(
									"display", "block").delay(4000).fadeOut();

							var vid = $(this).find("VID").text();
							if (usert == "A") {
								var surl = "adminViewEmployee.html?vid=" + vid;

								setTimeout(function() {
									$("#pageContentBody").load(surl);
								}, 4000);

							} else {
								var surl = "addNewEmployee.html?vid=" + vid;
								setTimeout(function() {
									$("#pageContentBody").load(surl);
								}, 4000);
							}

						} else {
							$("#body_error_message_bottom").text(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(4000).fadeOut();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#body_error_message_top").html(error).css("display", "block")
					.delay(6000).fadeOut();

		},
		dataType : "text",
		async : false

	});
	return isvalid;
}

/*******************************************************************************
 * //functions used to open the employee approve //(reject) dialog box //and set
 * the span id's with correct info
 ******************************************************************************/
function openApproveEmp() {
	$("#modaltitle").text("Approve Employee");
	$("#modaltext")
			.text(
					"Are you sure you would like to approve "
							+ $("#hidfullname").val());
	$("#trantype").val("A");
	$("#modalnotes").hide();
	$('#myModal').modal('show');
}
function openRejectEmp() {
	$("#modaltitle").text("Reject Employee");
	$("#modaltext").text(
			"Are you sure you would like to reject " + $("#hidfullname").val());
	$("#trantype").val("R");
	$("#modalnotes").show();
	$('#myModal').modal('show');
}
function openSuspendEmp() {
	$("#modaltitle").text("Suspend Employee");
	$("#modaltext")
			.text(
					"Are you sure you would like to suspend "
							+ $("#hidfullname").val());
	$("#trantype").val("S");
	$("#modalnotes").show();
	$('#myModal').modal('show');
}
/*******************************************************************************
 * Calls correct ajax depending on rej or app employee
 ******************************************************************************/
function approverejectemployee() {
	var ttype = $('#trantype').val();
	if (ttype == "A") {
		approveemployee();
	} else if (ttype == "R") {
		rejectemployee();
	} else if (ttype == "S") {
		suspendemployee();
	}
}
/*******************************************************************************
 * Calls ajax post for approve contractor
 ******************************************************************************/
function approveemployee() {
	var employeeid = "";
	employeeid = $("#cid").val();
	$.ajax({
		url : 'approveContractorEmployeeAjax.html',
		type : 'POST',
		data : {
			cid : employeeid
		},
		success : function(xml) {
			$(xml).find('CONTRACTORSTATUS').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$('#myModal').modal('hide');
							$('.modal-backdrop').remove();
							$('body').removeClass('modal-open');
							$("#employeesuccessmessage").html(
									"Employee has been approved").css(
									"display", "block").delay(6000).fadeOut();
							$('#divbuttons').hide();
							var surl = "adminViewEmployee.html?vid=" + employeeid;

							setTimeout(function() {
								$("#pageContentBody").load(surl);
							}, 4000);
						} else {
							$("#employeeerrormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#employeeerrormessage").html(error).css("display", "block")
					.delay(6000).fadeOut();

		},
		dataType : "text",
		async : false

	});
}
/*******************************************************************************
 * Calls ajax post for reject contractor employee
 ******************************************************************************/
function rejectemployee() {
	var employeeid = "";
	employeeid = $("#cid").val();
	var rejectnotes = $("#rnotes").val();
	$.ajax({
		url : 'rejectContractorEmployeeAjax.html',
		type : 'POST',
		data : {
			cid : employeeid,
			rnotes : rejectnotes
		},
		success : function(xml) {
			$(xml).find('CONTRACTORSTATUS').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$('#myModal').modal('hide');
							$('.modal-backdrop').remove();
							$('body').removeClass('modal-open');
							$("#employeesuccessmessage").html(
									"Employee has been rejected").css(
									"display", "block").delay(6000).fadeOut();
							var surl = "adminViewEmployee.html?vid=" + employeeid;

							setTimeout(function() {
								$("#pageContentBody").load(surl);
							}, 4000);

						} else {
							$("#employeeerrormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#employeeerrormessage").html(error).css("display", "block")
					.delay(6000).fadeOut();
			$('#mainalerts').show();
		},
		dataType : "text",
		async : false

	});
}
/*******************************************************************************
 * //functions used to open the vehicle approve //(reject) dialog box //and set
 * the span id's with correct info
 ******************************************************************************/
function openApproveVeh() {
	$("#modaltitle").text("Approve Vehicle");
	$("#modaltext").text(
			"Are you sure you would like to approve vehicle plate number:  "
					+ $("#hidfullname").val());
	$("#trantype").val("A");
	$("#modalnotes").hide();
	$('#myModal').modal('show');
}
function openRejectVeh() {
	$("#modaltitle").text("Reject Vehicle");
	$("#modaltext").text(
			"Are you sure you would like to reject vehicle plate number:"
					+ $("#hidfullname").val());
	$("#trantype").val("R");
	$("#modalnotes").show();
	$('#myModal').modal('show');
}
function openSuspendVeh() {
	$("#modaltitle").text("Suspend Vehicle");
	$("#modaltext").text(
			"Are you sure you would like to reject vehicle plate number:"
					+ $("#hidfullname").val());
	$("#trantype").val("S");
	$("#modalnotes").show();
	$('#myModal').modal('show');
}
/*******************************************************************************
 * Calls correct ajax depending on rej or app employee
 ******************************************************************************/
function approverejectvehicle() {
	var ttype = $('#trantype').val();
	if (ttype == "A") {
		approvevehicle();
	} else if (ttype == "R") {
		rejectvehicle();
	} else if (ttype == "S") {
		suspendvehicle();
	}
}
/*******************************************************************************
 * Calls ajax post for approve contractor vehicle
 ******************************************************************************/
function approvevehicle() {
	var vehicleid = "";
	vehicleid = $("#vid").val();

	$.ajax({
		url : 'approveContractorVehicleAjax.html',
		type : 'POST',
		data : {
			cid : vehicleid
		},
		success : function(xml) {
			$(xml).find('CONTRACTORSTATUS').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$('#myModal').modal('hide');
							$('.modal-backdrop').remove();
							$('body').removeClass('modal-open');
							$("#vehiclesuccessmessage").html(
									"Vehicle has been approved").css("display",
									"block").delay(6000).fadeOut();

							$('#divbuttons').hide();
							var surl="";
							surl = "adminViewVehicle.html?cid=" + vehicleid;
									
							$("#pageContentBody").load(surl);
						} else {
							$("#vehicleerrormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalert').show();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#vehicleerrormessage").html(error).css("display", "block")
					.delay(6000).fadeOut();
			$('#mainalerts').show();
		},
		dataType : "text",
		async : false

	});
}
/*******************************************************************************
 * Calls ajax post for reject contractor vehicle
 ******************************************************************************/
function rejectvehicle() {
	var vehicleid = "";
	vehicleid = $("#vid").val();
	var rejectnotes = $("#rnotes").val();
	$.ajax({
		url : 'rejectContractorVehicleAjax.html',
		type : 'POST',
		data : {
			cid : vehicleid,
			rnotes : rejectnotes
		},
		success : function(xml) {
			$(xml).find('CONTRACTORSTATUS').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$('#myModal').modal('hide');
							$('.modal-backdrop').remove();
							$('body').removeClass('modal-open');
							$("#vehiclesuccessmessage").html(
									"Vehicle has been rejected").css("display",
									"block").delay(6000).fadeOut();
							var surl="";
							surl = "adminViewVehicle.html?cid=" + vehicleid;
									
							$("#pageContentBody").load(surl);

						} else {
							$("#vehicleerrormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#vehicleerrormessage").html(error).css("display", "block")
					.delay(6000).fadeOut();

		},
		dataType : "text",
		async : false

	});
}
/*******************************************************************************
 * Calls ajax post for searching contractors
 ******************************************************************************/
function ajaxSearchContractors() {
	$("#BCS-table").find("tr:gt(0)").remove();
	var vsearchby = $.trim($('#searchby').val());
	var vsearchtext = $.trim($('#txtsearch').val());
	var vsearchstatus = $.trim($('#status').val());
	var vsearchprovince = $.trim($('#province').val());
	var isvalid = false;
	var cnt = 0;
	$
			.ajax({
				type : "POST",
				url : "searchContractorsAjax.html",

				data : {
					searchby : vsearchby,
					searchfor : vsearchtext,
					searchstatus : vsearchstatus,
					searchprovince : vsearchprovince,
				},
				success : function(xml) {
					$(xml)
							.find('CONTRACTOR')
							.each(
									function() {
										if ($(this).find("MESSAGE").text() == "LISTFOUND") {

											var newrow = "<tr style='border-bottom:1px solid silver;'>";
											newrow += "<td class='field_content'>"
													+ $(this).find("FIRSTNAME")
															.text()
													+ " "
													+ $(this).find("LASTNAME")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find("CITY")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find("COMPANY")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find("STATUS")
															.text() + "</td>";
											newrow += "<td align='right' class='field_content'>";
											newrow += "<a href='#' class='menuBCS' onclick=\"closeMenu();loadMainDivPage('adminViewContractor.html?cid="
													+ $(this).find("ID").text()
													+ "');\">";
											newrow += "<img src='includes/img/viewsm-off.png' class='img-swap' title='View Contractor' border=0></a></td>";
											newrow += "</tr>";
											cnt = cnt + 1;
											$('table#BCS-table tr:last').after(
													newrow);
											isvalid = true;

										} else {
											var newrow = "<tr style='border-bottom:1px dashed silver;'><td colspan='5'>";
											newrow += $(this).find("MESSAGE")
													.text();
											newrow += "</td></tr>";
											$('table#BCS-table tr:last').after(
													newrow);

										}
									});

					if (cnt > 0) {
						$('#BCS-Search').css("display", "block");
						$('#body_success_message_bottom').html(
								"SUCCESS! <b>" + cnt + "</b> results found.")
								.css("display", "block").delay(4000).fadeOut();
						$("#BCS-table tr:even").not(':first').css(
								"background-color", "#FFFFFF");
						$("#BCS-table tr:odd").css("background-color",
								"#f2f2f2");
					} else {
						$('#body_error_message_bottom')
								.html(
										"SORRY. "
												+ cnt
												+ " results found. Please try another search or select another search criteria.")
								.css("display", "block").delay(6000).fadeOut();
					}
				},
				error : function(xhr, textStatus, error) {
					alert(xhr.statusText);
					alert(textStatus);
					alert(error);
				},
				dataType : "text",
			// async: false
			});

	return isvalid;
}
/*******************************************************************************
 * Calls ajax post for searching contractors
 ******************************************************************************/
function ajaxSearchEmployees() {
	$("#BCS-table").find("tr:gt(0)").remove();
	var vsearchby = $.trim($('#searchby').val());
	var vsearchtext = $.trim($('#txtsearch').val());
	var vsearchstatus = $.trim($('#status').val());
	var vsearchprovince = $.trim($('#province').val());
	var vsearchdl = $.trim($('#dlclass').val());
	var vsearchposition = $.trim($('#position').val());
	var isvalid = false;
	var cnt = 0;
	$
			.ajax({
				type : "POST",
				url : "searchEmployeesAjax.html",

				data : {
					searchby : vsearchby,
					searchfor : vsearchtext,
					searchstatus : vsearchstatus,
					searchprovince : vsearchprovince,
					searchposition : vsearchposition,
					searchdl : vsearchdl
				},
				success : function(xml) {
					$(xml)
							.find('CONTRACTOR')
							.each(
									function() {
										if ($(this).find("MESSAGE").text() == "LISTFOUND") {
											var newrow = "<tr style='border-bottom:1px solid silver;'>";
											newrow += "<td class='field_content'>"
													+ $(this).find("FIRSTNAME")
															.text()
													+ " "
													+ $(this).find("LASTNAME")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find("COMPANY")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find("POSITION")
															.text() + "</td>";

											var test = $(this).find("STATUS")
													.text();
											if (test === "Approved") {
												newrow += "<td class='field_content'><span style='color:white;background-color:Green;text-transform:uppercase;'>&nbsp;"
														+ $(this)
																.find("STATUS")
																.text()
														+ "&nbsp;</span></td>";

											} else if (test === "Not Yet Reviewed By District") {
												newrow += "<td class='field_content'><span style='color:black;background-color:yellow;text-transform:uppercase;'>&nbsp;PENDING REVIEW&nbsp;</span></td>";

											} else if (test === "Not Approved") {
												newrow += "<td class='field_content'><span style='color:white;background-color:red;text-transform:uppercase;'>&nbsp;"
														+ $(this)
																.find("STATUS")
																.text()
														+ "&nbsp;</span></td>";

											} else {
												newrow += "<td class='field_content'><span style='color:white;background-color:black;text-transform:uppercase;'>&nbsp;"
														+ $(this)
																.find("STATUS")
																.text()
														+ "&nbsp;</span></td>";

											}
											;

											newrow += "<td align='right' class='field_content'>";
											newrow += "<button type='button' class='btn btn-xs btn-primary' onclick=\"closeMenu();loadMainDivPage('adminViewEmployee.html?vid=" 
												+ $(this).find("ID").text() + "');\">View</button>";
											newrow += "<button type='button' class='btn btn-xs btn-danger' onclick=\"opendeletedialogemp('";
											newrow += $(this).find("LASTNAME").text() + "," + $(this).find("FIRSTNAME").text() + "','";
											newrow += $(this).find("ID").text() + "','A');\">Del</button>";
											newrow += "</td></tr>";
											cnt = cnt + 1;
											$('table#BCS-table tr:last').after(
													newrow);
											isvalid = true;

										} else {
											var newrow = "<tr style='border-bottom:1px dashed silver;'><td colspan='5'>";
											newrow += $(this).find("MESSAGE")
													.text();
											newrow += "</td></tr>";
											$('table#BCS-table tr:last').after(
													newrow);

										}
									});

					if (cnt > 0) {
						$('#BCS-Search').css("display", "block");
						$('#body_success_message_bottom').html(
								"SUCCESS! <b>" + cnt + "</b> results found.")
								.css("display", "block").delay(4000).fadeOut();
						$("#BCS-table tr:even").not(':first').css(
								"background-color", "#FFFFFF");
						$("#BCS-table tr:odd").css("background-color",
								"#f2f2f2");
					} else {
						$('#BCS-Search').css("display", "none");
						$('#body_error_message_bottom')
								.html(
										"SORRY. "
												+ cnt
												+ " results found. Please try another search or select another search criteria.")
								.css("display", "block").delay(6000).fadeOut();
					}

				},
				error : function(xhr, textStatus, error) {
					alert(xhr.statusText);
					alert(textStatus);
					alert(error);
				},
				dataType : "text",
			// async: false
			});
	return isvalid;
}
/*******************************************************************************
 * Calls ajax post for searching contractors
 ******************************************************************************/
function ajaxSearchVehicles() {
	$("#BCS-table").find("tr:gt(0)").remove();
	var vsearchby = $.trim($('#searchby').val());
	var vsearchtext = $.trim($('#txtsearch').val());
	var vsearchstatus = $.trim($('#status').val());
	var vsearchprovince = $.trim($('#province').val());
	var vsearchmake = $.trim($('#makes').val());
	var vsearchmodel = $.trim($('#models').val());
	var vsearchtype = $.trim($('#types').val());
	var vsearchsize = $.trim($('#sizes').val());
	var isvalid = false;
	var cnt = 0;
	$
			.ajax({
				type : "POST",
				url : "searchVehiclesAjax.html",

				data : {
					searchby : vsearchby,
					searchfor : vsearchtext,
					searchstatus : vsearchstatus,
					searchprovince : vsearchprovince,
					searchmake : vsearchmake,
					searchmodel : vsearchmodel,
					searchtype : vsearchtype,
					searchsize : vsearchsize
				},
				success : function(xml) {
					$(xml)
							.find('CONTRACTOR')
							.each(
									function() {
										if ($(this).find("MESSAGE").text() == "LISTFOUND") {
											var newrow = "<tr style='border-bottom:1px solid silver;'>";
											newrow += "<td class='field_content'>"
													+ $(this).find("COMPANY")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find(
															"PLATENUMBER")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find(
															"SERIALNUMBER")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find("STATUS")
															.text() + "</td>";
											newrow += "<td align='right' class='field_content'>";
											newrow += "<button type='button' class='btn btn-xs btn-primary' onclick=\"closeMenu();loadMainDivPage('adminViewVehicle.html?cid=" 
												+ $(this).find("ID").text() + "');\">View</button>";
											newrow += "<button type='button' class='btn btn-xs btn-danger' onclick=\"opendeletedialog('";
											newrow += $(this).find("PLATENUMBER").text() + "','";
											newrow += $(this).find("ID").text() + "','A');\">Del</button>";
											
											newrow += "</td></tr>";
											cnt = cnt + 1;
											$('table#BCS-table tr:last').after(
													newrow);
											isvalid = true;

										} else {
											var newrow = "<tr style='border-bottom:1px dashed silver;'><td colspan='5'>";
											newrow += $(this).find("MESSAGE")
													.text();
											newrow += "</td></tr>";
											$('table#BCS-table tr:last').after(
													newrow);

										}
									});

					if (cnt > 0) {
						$('#BCS-Search').css("display", "block");
						$('#body_success_message_bottom').html(
								"SUCCESS! <b>" + cnt + "</b> results found.")
								.css("display", "block").delay(4000).fadeOut();
						$("#BCS-table tr:even").not(':first').css(
								"background-color", "#FFFFFF");
						$("#BCS-table tr:odd").css("background-color",
								"#f2f2f2");

					} else {
						$('#body_error_message_bottom')
								.html(
										"SORRY. "
												+ cnt
												+ " results found. Please try another search or select another search criteria.")
								.css("display", "block").delay(6000).fadeOut();
					}

				},
				error : function(xhr, textStatus, error) {
					alert(xhr.statusText);
					alert(textStatus);
					alert(error);
				},
				dataType : "text",
			// async: false
			});
	return isvalid;
}
/*******************************************************************************
 * Calls ajax post for suspend contractor employee
 ******************************************************************************/
function suspendemployee() {
	var employeeid = "";
	employeeid = $("#cid").val();
	var rejectnotes = $("#rnotes").val();
	$.ajax({
		url : 'suspendContractorEmployeeAjax.html',
		type : 'POST',
		data : {
			cid : employeeid,
			rnotes : rejectnotes
		},
		success : function(xml) {
			$(xml).find('CONTRACTORSTATUS').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$('#myModal').modal('hide');
							$('.modal-backdrop').remove();
							$('body').removeClass('modal-open');
							$("#employeesuccessmessage").html(
									"Employee has been suspended").css(
									"display", "block").delay(6000).fadeOut();
							var surl = "adminViewEmployee.html?vid=" + employeeid;

							setTimeout(function() {
								$("#pageContentBody").load(surl);
							}, 4000);

						} else {
							$("#employeeerrormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#employeeerrormessage").html(error).css("display", "block")
					.delay(6000).fadeOut();

		},
		dataType : "text",
		async : false

	});
}
/*******************************************************************************
 * Calls ajax post for suspend contractor employee
 ******************************************************************************/
function resetPassword() {
	var contractorid = "";
	contractorid = $("#cid").val();
	$.ajax({
		url : 'resetContractorPasswordAjax.html',
		type : 'POST',
		data : {
			cid : contractorid
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$('#myModal').modal('hide');
							$('.modal-backdrop').remove();
							$('body').removeClass('modal-open');
							$("#contractorsuccessmessage").html(
									"New Password sent to Contractor").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();
						} else {
							$("#contractorerrormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalert').show();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#contractorerrormessage").html(error).css("display", "block")
					.delay(6000).fadeOut();
			$('#mainalerts').show();
		},
		dataType : "text",
		async : false

	});
}
/*******************************************************************************
 * Opens dialog for adding new document contractor
 ******************************************************************************/
function openaddnewdialogc() {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonleft').text("YES");
	$('#buttonright').text("NO");
	// now we add the onclick event
	$("#buttonleft").click(function(event) {
		event.preventDefault();
		if (checknewdocument()) {
			addnewvehicledocumentc()
		}
		// deletevehicle(splate,id);

	});
	$('#myModal').modal(options);

}
/*******************************************************************************
 * add new contractor document
 ******************************************************************************/
function addnewvehicledocumentc() {
	var documenttitle = $.trim($('#documenttitle').val());
	// var selected = $("#documenttype option:selected");
	var selected = $("#documenttype").val();
	var pfile = $.trim($('#documentname').val());
	var ufile = $('#documentname')[0].files[0];
	var edate = $('#expirydate').val();
	var requestd = new FormData();
	requestd.append('documenttitle', documenttitle);
	requestd.append('documenttype', selected);
	requestd.append('documentfile', ufile);
	requestd.append('documentname', documentname);
	requestd.append('expirydate', edate)
	$.ajax({
		url : "addNewContractorDocument.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {

			var i = 1;
			$(xml).find('CONTRACTOR').each(
					function() {

						if ($(this).find("MESSAGE").text() == "UPDATED") {
							var surl = "viewContractorDocuments.html";
							$("#pageContentBody").load(surl);
							$("#successmessage").html(
									"Contractor Document Has Been Added").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();
							$('#myModal').modal('hide');
						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal').modal('hide');
						}
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$("#errormessage").html(textStatus).css("display", "block").delay(
					6000).fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false
	});

}
/*******************************************************************************
 * Opens dialog for deleting contractor document
 ******************************************************************************/
function opendeletedocdialogc(sdoc, id) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitled').text("Delete Contractor Document");
	$('#spantitle1').text("Contractor Document: " + sdoc);
	$('#spantitle2').text("Are you sure you want to delete this document");
	$('#buttonleftd').text("YES");
	$('#buttonrightd').text("NO");
	// now we add the onclick event
	$("#buttonleftd").click(function() {
		deletevehicledocumentc(sdoc, id)
	});
	$('#myModal2').modal(options);
}
/*******************************************************************************
 * Calls ajax post for delete vehicle
 ******************************************************************************/
function deletevehicledocumentc(docname, id) {
	$.ajax({
		url : 'deleteContractorDocument.html',
		type : 'POST',
		data : {
			document : docname,
			did : id
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							var surl = "viewContractorDocuments.html";
							$("#pageContentBody").load(surl);
							$("#successmessage").html(
									"Contractor Document Has Been Deleted")
									.css("display", "block").delay(6000)
									.fadeOut();
							$('#mainalerts').show();
							$('#myModal2').modal('hide');

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal2').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for adding new system document
 ******************************************************************************/
function openaddnewdialogsd() {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonleft').text("YES");
	$('#buttonright').text("NO");
	// now we add the onclick event
	$("#buttonleft").click(function(event) {
		event.preventDefault();
		if (checknewdocumentsd()) {
			// addnewvehicledocumentc()
			addnewsystemdocument();
		}
		// deletevehicle(splate,id);

	});
	$('#myModal').modal(options);

}
/*******************************************************************************
 * add new vehicle document
 ******************************************************************************/
function checknewdocumentsd() {
	$("#dalert").hide();
	var documenttitle = $.trim($('#documenttitle').val());
	// var selected = $("#documenttype option:selected");
	var selected = $("#documenttype").val();
	var pfile = $.trim($('#documentname').val());
	var vint = $('#vinternal').prop('checked')
	var vext = $('#vexternal').prop('checked')
	if (selected < 1) {
		$("#demessage").html("Please enter document type").css("display",
				"block").delay(4000).fadeOut();
		// $("#dalert").show();
		return false;
	}
	if (documenttitle == "") {
		$("#demessage").html("Please enter document title").css("display",
				"block").delay(4000).fadeOut();
		// $("#dalert").show();
		return false;
	}
	if (pfile == "") {
		$("#demessage").html("Please select document").css("display", "block")
				.delay(4000).fadeOut();
		// $("#dalert").show();
		return false;
	}else{
		var reg = /(.*?)\.(pdf)$/;
		if(!pfile.match(reg)){
			$("#demessage").html("PDF documents only")
			.css("display", "block").delay(6000).fadeOut();
			//$("#dalertadd").show();
			return false;
		}
	}
	if (vint == false && vext == false) {
		$("#demessage").html("Please select Internal and/or External").css(
				"display", "block").delay(6000).fadeOut();
		// $("#dalert").show();
		return false;
	}
	$("#dalert").hide();

	return true;
}
/*******************************************************************************
 * add new vehicle document
 ******************************************************************************/
function addnewsystemdocument() {
	var documenttitle = $.trim($('#documenttitle').val());
	// var selected = $("#documenttype option:selected");
	var selected = $("#documenttype").val();
	var pfile = $.trim($('#documentname').val());
	var ufile = $('#documentname')[0].files[0];
	var vint = $('#vinternal').prop('checked');
	var vext = $('#vexternal').prop('checked');
	var smessage = $('#showmessage').prop('checked');
	var mdays = $("#messagedays").val();
	var isactive = $('#isactive').prop('checked');
	var requestd = new FormData();
	requestd.append('documenttitle', documenttitle);
	requestd.append('documenttype', selected);
	requestd.append('documentfile', ufile);
	requestd.append('documentname', documentname);
	requestd.append('vinternal', vint);
	requestd.append('vexternal', vext);
	requestd.append('showmessage', smessage);
	requestd.append('messagedays', mdays);
	requestd.append('isactive', isactive);
	$.ajax({
		url : "addNewSystemDocument.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {

			var i = 1;
			$(xml).find('CONTRACTOR').each(
					function() {

						if ($(this).find("MESSAGE").text() == "UPDATED") {
							var surl = "admimViewSystemDocuments.html";
							$("#pageContentBody").load(surl);
							$("#successmessage").html(
									"System Document Has Been Added").css(
									"display", "block").delay(15000).fadeOut();
							$('#mainalerts').show();
							$('#myModal').modal('hide');
						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(15000).fadeOut();
							$("#mainalert").show();
							$('#myModal').modal('hide');
						}
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$("#errormessage").html(textStatus).css("display", "block").delay(
					6000).fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false
	});

}
/*******************************************************************************
 * Opens dialog for deleting system document
 ******************************************************************************/
function opendeletedialogsys(splate, id) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitled').text("Delete System Document");
	$('#title1d').text("Document Title: " + splate);
	$('#title2d').text("Are you sure you want to delete this document?");
	$('#buttonleftd').text("YES");
	$('#buttonrightd').text("NO");
	// now we add the onclick event
	$("#buttonleftd").click(function() {
		deletesystemdoc(splate, id);
	});
	$('#myModal2').modal(options);

}
/*******************************************************************************
 * Calls ajax post for delete employee
 ******************************************************************************/
function deletesystemdoc(sname, id) {
	$.ajax({
		url : 'deleteSystemDocument.html',
		type : 'POST',
		data : {
			eid : id,
			ename : sname
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#successmessage").html(
									"Document Has Been Deleted").css("display",
									"block").delay(15000).fadeOut();
							$('#mainalerts').show();

							var surl = "admimViewSystemDocuments.html";
							$("#pageContentBody").load(surl);
							$('#myModal2').modal('hide');

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(15000).fadeOut();
							$("#mainalert").show();
							$('#myModal2').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for deleting contract
 ******************************************************************************/
function opendeletedialogcontract(splate, id) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitled').text("Delete Contract");
	$('#title1d').text("Contract Name: " + splate);
	$('#title2d').text("Are you sure you want to delete this contract?");
	$('#buttonleftd').text("YES");
	$('#buttonrightd').text("NO");
	// now we add the onclick event
	$("#buttonleftd").click(function() {
		deletecontract(splate, id);
	});
	$('#myModal2').modal(options);

}
/*******************************************************************************
 * Calls ajax post for delete employee
 ******************************************************************************/
function deletecontract(sname, id) {

	$.ajax({
		url : 'deleteContract.html',
		type : 'POST',
		data : {
			cid : id,
			ename : sname
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#successmessage").html(
									"Contract Has Been Deleted").css("display",
									"block").delay(6000).fadeOut();
							$('#mainalerts').show();

							var surl = "adminViewContracts.html";
							$("#pageContentBody").load(surl);
							$('#myModal2').modal('hide');

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal2').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Calls ajax post for searching contracts
 ******************************************************************************/
function ajaxSearchContracts() {
	$("#BCS-table").find("tr:gt(0)").remove();
	var vsearchby = $.trim($('#searchby').val());
	var vsearchtext = $.trim($('#txtsearch').val());
	var vctype = $.trim($('#type').val());
	var vregion = $.trim($('#region').val());
	var vexpirydate = $.trim($('#expirydate').val());
	var isvalid = false;
	var cnt = 0;
	$
			.ajax({
				type : "POST",
				url : "adminSearchContractsAjax.html",

				data : {
					searchby : vsearchby,
					searchfor : vsearchtext,
					ctype : vctype,
					cregion : vregion,
					cexpirydate : vexpirydate,
				},
				success : function(xml) {
					$(xml)
							.find('CONTRACT')
							.each(
									function() {
										if ($(this).find("MESSAGE").text() == "LISTFOUND") {
											var newrow = "<tr style='border-bottom:1px solid silver;'>";
											newrow += "<td class='field_content'>"
													+ $(this).find(
															"CONTRACTNAME")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find(
															"CONTRACTTYPE")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find(
															"CONTRACTREGION")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this)
															.find(
																	"CONTRACTEXPIRYDATE")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find(
															"CONTRACTSTATUS")
															.text() + "</td>";
											newrow += "<td align='right' class='field_content'>";
											newrow += "<a href='#' class='menuBCS' onclick=\"closeMenu();loadMainDivPage('adminViewContract.html?vid="
													+ $(this).find("ID").text()
													+ "');\">";
											newrow += "<img src='includes/img/viewsm-off.png' class='img-swap' title='View Contract' border=0></a>";
											newrow += "<a class='edit' href='#' onclick=\"opendeletedialogcontract('"
													+ $(this).find(
															"CONTRACTNAME")
															.text();
											newrow += "','"
													+ $(this).find("ID").text();
											newrow += "');\"><img src='includes/img/deletesm-off.png' class='img-swap' border=0 title='Delete Contract'></a></td>"
											newrow += "</tr>";
											cnt = cnt + 1;
											$('table#BCS-table tr:last').after(
													newrow);
											isvalid = true;

										} else {
											var newrow = "<tr style='border-bottom:1px dashed silver;'><td colspan='6'>";
											newrow += $(this).find("MESSAGE")
													.text();
											newrow += "</td></tr>";
											$('table#BCS-table tr:last').after(
													newrow);

										}

										if (cnt > 0) {
											$('#BCS-Search').css("display",
													"block");
											$('#body_success_message_bottom')
													.html(
															"SUCCESS! <b>"
																	+ cnt
																	+ "</b> results found.")
													.css("display", "block")
													.delay(4000).fadeOut();
											$("#BCS-table tr:even").not(
													':first').css(
													"background-color",
													"#FFFFFF");
											$("#BCS-table tr:odd").css(
													"background-color",
													"#f2f2f2");

										} else {
											$('#body_error_message_bottom')
													.html(
															"SORRY. "
																	+ cnt
																	+ " results found. Please try another search or select another search criteria.")
													.css("display", "block")
													.delay(6000).fadeOut();
										}

									});
				},
				error : function(xhr, textStatus, error) {
					alert(xhr.statusText);
					alert(textStatus);
					alert(error);
				},
				dataType : "text",
				async : false
			});
	return isvalid;
}
/*******************************************************************************
 * Calls ajax post for searching routes
 ******************************************************************************/
function ajaxSearchRoutes() {
	$("#BCS-table").find("tr:gt(0)").remove();
	var vsearchby = $.trim($('#searchby').val());
	var vsearchtext = $.trim($('#txtsearch').val());
	var vsearchschool = $.trim($('#school').val());
	var cnt = 0;
	var isvalid = false;
	$
			.ajax({
				type : "POST",
				url : "adminSearchRoutesAjax.html",

				data : {
					searchby : vsearchby,
					searchfor : vsearchtext,
					searchschool : vsearchschool,
				},
				success : function(xml) {
					$(xml)
							.find('ROUTE')
							.each(
									function() {
										if ($(this).find("MESSAGE").text() == "LISTFOUND") {
											var newrow = "<tr style='border-bottom:1px solid silver;'>";
											newrow += "<td class='field_content'>"
													+ $(this).find("ROUTENAME")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find(
															"ROUTESCHOOL")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find(
															"CONTRACTNAME")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find("ADDEDBY")
															.text() + "</td>";
											newrow += "<td align='right' class='field_content'>";
											newrow += "<a href='#' class='menuBCS' onclick=\"closeMenu();loadMainDivPage('adminViewRoute.html?vid="
													+ $(this).find("ID").text()
													+ "');\">";
											newrow += "<img src='includes/img/viewsm-off.png' class='img-swap' title='View Route' border=0></a>";
											newrow += "<a class='edit' href='#' onclick=\"opendeletedialogroute('"
													+ $(this).find("ROUTENAME")
															.text();
											newrow += "','"
													+ $(this).find("ID").text();
											newrow += "');\"><img src='includes/img/deletesm-off.png' class='img-swap' border=0 title='Delete Route'></a></td>"
											newrow += "</tr>";
											cnt = cnt + 1;
											$('table#BCS-table tr:last').after(
													newrow);
											isvalid = true;

										} else {
											var newrow = "<tr style='border-bottom:1px dashed silver;'><td colspan='5'>";
											newrow += $(this).find("MESSAGE")
													.text();
											newrow += "</td></tr>"
											$('table#BCS-table tr:last').after(
													newrow);

										}

										if (cnt > 0) {
											$('#BCS-Search').css("display",
													"block");
											$('#body_success_message_bottom')
													.html(
															"SUCCESS! <b>"
																	+ cnt
																	+ "</b> results found.")
													.css("display", "block")
													.delay(4000).fadeOut();
											$("#BCS-table tr:even").not(
													':first').css(
													"background-color",
													"#FFFFFF");
											$("#BCS-table tr:odd").css(
													"background-color",
													"#f2f2f2");

										} else {
											$('#body_error_message_bottom')
													.html(
															"SORRY. "
																	+ cnt
																	+ " results found. Please try another search or select another search criteria.")
													.css("display", "block")
													.delay(6000).fadeOut();
										}

									});
				},
				error : function(xhr, textStatus, error) {
					alert(xhr.statusText);
					alert(textStatus);
					alert(error);
				},
				dataType : "text",
				async : false
			});
	return isvalid;
}
/*******************************************************************************
 * Opens dialog for deleting route
 ******************************************************************************/
function opendeletedialogroute(splate, id) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitled').text("Delete Route");
	$('#title1d').text("Route Name: " + splate);
	$('#title2d').text("Are you sure you want to delete this route?");
	$('#buttonleftd').text("YES");
	$('#buttonrightd').text("NO");
	// now we add the onclick event
	$("#buttonleftd").click(function() {
		deleteroute(splate, id);
	});
	$('#myModal2').modal(options);

}
/*******************************************************************************
 * Calls ajax post for delete employee
 ******************************************************************************/
function deleteroute(sname, id) {

	$.ajax({
		url : 'deleteRoute.html',
		type : 'POST',
		data : {
			rid : id,
			rname : sname
		},
		success : function(xml) {
			$(xml).find('ROUTE').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#body_success_message_bottom").html(
									"Route Has Been Deleted").css("display",
									"block").delay(6000).fadeOut();

							var surl = "adminViewRoutes.html";
							$("#pageContentBody").load(surl);
							$('#myModal2').modal('hide');

						} else {
							$("#body_error_message_bottom").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal2').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#body_error_message_bottom").html(error).css("display", "block")
					.delay(6000).fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for adding new document
 ******************************************************************************/
function openaddnewdialogcontract(contractid) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonleft').text("YES");
	$('#buttonright').text("NO");
	// now we add the onclick event
	$("#buttonleft").click(function(event) {
		event.preventDefault();
		if (checknewdocumentcontract()) {
			addnewcontractdocument(contractid);
		}
		// deletevehicle(splate,id);

	});
	$('#myModal').modal(options);

}
/*******************************************************************************
 * add new contract document
 ******************************************************************************/
function addnewcontractdocument(cid) {
	var documenttitle = $.trim($('#documenttitle').val());
	var documentname = $.trim($('#documentname').val());
	var ufile = $('#documentname')[0].files[0];
	var requestd = new FormData();
	requestd.append('documenttitle', documenttitle);
	requestd.append('documentfile', ufile);
	requestd.append('documentname', documentname);
	requestd.append('contractid', cid);
	$.ajax({
		url : "addNewContractDocumentAjax.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {

			var i = 1;
			$(xml).find('CONTRACT').each(
					function() {

						if ($(this).find("MESSAGE").text() == "UPDATED") {
							var surl = "adminViewContract.html?vid=" + cid
									+ "&tab=D";
							$("#pageContentBody").load(surl);
							$("#successmessage").html(
									"Contract Document Has Been Added").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();
							$('#myModal').modal('hide');
						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal').modal('hide');
						}
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$("#errormessage").html(textStatus).css("display", "block").delay(
					6000).fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false
	});

}
/*******************************************************************************
 * add new contract document
 ******************************************************************************/
function checknewdocumentcontract() {
	var documenttitle = $.trim($('#documenttitle').val());
	var pfile = $.trim($('#documentname').val());

	if (documenttitle == "") {
		$("#demessage").html("Please enter document title").css("display",
				"block").delay(6000).fadeOut();
		$("#dalert").show();
		return false;
	}
	if (pfile == "") {
		$("#demessage").html("Please select document").css("display", "block")
				.delay(6000).fadeOut();
		$("#dalert").show();
		return false;
	}else{
		var reg = /(.*?)\.(pdf)$/;
		if(!pfile.match(reg)){
			$("#demessage").html("PDF documents only")
			.css("display", "block").delay(6000).fadeOut();
			$("#dalert").show();
			return false;
		}
	}
	$("#dalert").hide();

	return true;
}
/*******************************************************************************
 * Opens dialog for deleting contract document
 ******************************************************************************/
function opendeletedoccontdialog(sdoc, id, cid) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitled').text("Delete Contact Document");
	$('#spantitle1').text("Contract Document: " + sdoc);
	$('#spantitle2').text("Are you sure you want to delete this document");
	$('#buttonleftd').text("YES");
	$('#buttonrightd').text("NO");
	// now we add the onclick event
	$("#buttonleftd").click(function() {
		deletesystemcontdoc(sdoc, id, cid);
	});
	$('#myModal2').modal(options);
}
/*******************************************************************************
 * Calls ajax post for system contract doc
 ******************************************************************************/
function deletesystemcontdoc(sname, id, cid) {
	$.ajax({
		url : 'deleteSystemDocument.html',
		type : 'POST',
		data : {
			eid : id,
			ename : sname
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#successmessage").html(
									"Document Has Been Deleted").css("display",
									"block").delay(6000).fadeOut();
							$('#mainalerts').show();

							var surl = "adminViewContract.html?vid=" + cid
									+ "&tab=D";
							$("#pageContentBody").load(surl);
							$('#myModal2').modal('hide');

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal2').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for adding new document route
 ******************************************************************************/
function openaddnewdialogroute(routeid) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonleft').text("YES");
	$('#buttonright').text("NO");
	// now we add the onclick event
	$("#buttonleft").click(function(event) {
		event.preventDefault();
		// use contract function same fields
		if (checknewdocumentcontract()) {
			addnewroutedocument(routeid);
		}
		// deletevehicle(splate,id);

	});
	$('#myModal').modal(options);

}
/*******************************************************************************
 * add new contract document
 ******************************************************************************/
function addnewroutedocument(cid) {
	var documenttitle = $.trim($('#documenttitle').val());
	var documentname = $.trim($('#documentname').val());
	var ufile = $('#documentname')[0].files[0];
	var requestd = new FormData();
	requestd.append('documenttitle', documenttitle);
	requestd.append('documentfile', ufile);
	requestd.append('documentname', documentname);
	requestd.append('routeid', cid);
	$.ajax({
		url : "addNewRouteDocumentAjax.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {

			var i = 1;
			$(xml).find('CONTRACT').each(
					function() {

						if ($(this).find("MESSAGE").text() == "UPDATED") {
							var surl = "adminViewRoute.html?vid=" + cid
									+ "&tab=D";
							$("#pageContentBody").load(surl);
							$("#successmessage").html(
									"Route Document Has Been Added").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();
							$('#myModal').modal('hide');
						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal').modal('hide');
						}
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$("#errormessage").html(textStatus).css("display", "block").delay(
					6000).fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false
	});
}
/*******************************************************************************
 * Opens dialog for deleting route document
 ******************************************************************************/
function opendeletedocroutedialog(sdoc, id, cid) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitled').text("Delete Route Document");
	$('#spantitle1').text("Route Document: " + sdoc);
	$('#spantitle2').text("Are you sure you want to delete this document");
	$('#buttonleftd').text("YES");
	$('#buttonrightd').text("NO");
	// now we add the onclick event
	$("#buttonleftd").click(function() {
		deletesystemroutedoc(sdoc, id, cid);
	});
	$('#myModal2').modal(options);
}
/*******************************************************************************
 * Calls ajax post for delete system route doc
 ******************************************************************************/
function deletesystemroutedoc(sname, id, cid) {
	$.ajax({
		url : 'deleteSystemDocument.html',
		type : 'POST',
		data : {
			eid : id,
			ename : sname
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#successmessage").html(
									"Document Has Been Deleted").css("display",
									"block").delay(6000).fadeOut();
							$('#mainalerts').show();

							var surl = "adminViewRoute.html?vid=" + cid
									+ "&tab=D";
							$("#pageContentBody").load(surl);
							$('#myModal2').modal('hide');

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal2').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for add contract to route
 ******************************************************************************/
function openaddcontract(routeid, routename) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonleftc').text("YES");
	$('#buttonrightc').text("NO");
	$('#spantitle1c').text("Route: " + routename);
	$('#spantitle2c').text("Please select contract");
	// now we add the onclick event
	$("#buttonleftc").click(function(event) {
		event.preventDefault();
		// use contract function same fields
		addnewroutecontract(routeid);
	});
	$('#myModal3').modal(options);

}
/*******************************************************************************
 * Calls ajax post for delete system route doc
 ******************************************************************************/
function addnewroutecontract(routeid) {
	var contractid = $("#contracts").val();
	$.ajax({
		url : 'addNewRouteContractAjax.html',
		type : 'POST',
		data : {
			rid : routeid,
			cid : contractid
		},
		success : function(xml) {
			$(xml).find('CONTRACT').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#successmessage")
									.html("Route added to contract").css(
											"display", "block").delay(6000)
									.fadeOut();
							$('#mainalerts').show();

							var surl = "adminViewRoute.html?vid=" + routeid;
							$("#pageContentBody").load(surl);
							$('#myModal3').modal('hide');

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal3').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for deleting route contract
 ******************************************************************************/
function openremovecontract(cid, routeid) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitled').text("Remove Route Contract");
	$('#spantitle2d').text("Are you sure you want to remove this contract");
	$('#buttonleftd').text("YES");
	$('#buttonrightd').text("NO");
	// now we add the onclick event
	$("#buttonleftd").click(function() {
		deletesystemroutecontract(cid, routeid);
	});
	$('#myModal2').modal(options);
}
/*******************************************************************************
 * Calls ajax post for delete system route doc
 ******************************************************************************/
function deletesystemroutecontract(contractid, routeid) {
	$.ajax({
		url : 'removeRouteContractAjax.html',
		type : 'POST',
		data : {
			rid : routeid,
			cid : contractid
		},
		success : function(xml) {
			$(xml).find('CONTRACT').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#successmessage").html(
									"Route removed from contract").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();

							var surl = "adminViewRoute.html?vid=" + routeid;
							$("#pageContentBody").load(surl);
							$('#myModal2').modal('hide');

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal2').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for add route to contract
 ******************************************************************************/
function openaddroute(contractid, contractname) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonleftc').text("YES");
	$('#buttonrightc').text("NO");
	$('#spantitle1c').text("Contract: " + contractname);
	$('#spantitle2c').text("Please select route");
	// now we add the onclick event
	$("#buttonleftc").click(function(event) {
		event.preventDefault();
		// use contract function same fields
		addnewcontractroute(contractid);
	});
	$('#myModal3').modal(options);

}
/*******************************************************************************
 * Calls ajax post for delete system route doc
 ******************************************************************************/
function addnewcontractroute(contractid) {
	var routeid = $("#routes").val();
	$.ajax({
		url : 'addNewContractRouteAjax.html',
		type : 'POST',
		data : {
			rid : routeid,
			cid : contractid
		},
		success : function(xml) {
			$(xml).find('CONTRACT').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#successmessage")
									.html("Route added to contract").css(
											"display", "block").delay(6000)
									.fadeOut();
							$('#mainalerts').show();

							var surl = "adminViewContract.html?vid="
									+ contractid;
							$("#pageContentBody").load(surl);
							$('#myModal3').modal('hide');

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal3').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for deleting contract route
 ******************************************************************************/
function openremovecontractroute(routename, routeid, cid) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitled').text("Remove Contract Route");
	$('#title1d').text("Route: " + routename);
	$('#title2d').text("Are you sure you want to remove this route?");
	$('#buttonleftd').text("YES");
	$('#buttonrightd').text("NO");
	// now we add the onclick event
	$("#buttonleftd").click(function() {
		deletecontractroute(cid, routeid);
	});
	$('#myModal2').modal(options);
}
/*******************************************************************************
 * Calls ajax post for delete contract route
 ******************************************************************************/
function deletecontractroute(contractid, routeid) {
	$.ajax({
		url : 'removeRouteContractAjax.html',
		type : 'POST',
		data : {
			rid : routeid,
			cid : contractid
		},
		success : function(xml) {
			$(xml).find('CONTRACT').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#successmessage").html(
									"Route removed from contract").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();

							var surl = "adminViewContract.html?vid="
									+ contractid;
							$("#pageContentBody").load(surl);
							$('#myModal2').modal('hide');

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal2').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for awarding contact
 ******************************************************************************/
function openawardcontract(cid, contractname) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitle4').text("Award Contract");
	$('#title14').text("Contract: " + contractname);
	$('#title24').text("Are you sure you want to award this contract?");
	$('#buttonleft4').text("YES");
	$('#buttonright4').text("NO");
	// now we add the onclick event
	$("#buttonleft4").click(function() {
		awardContractAjax(cid);
	});
	$('#myModal4').modal(options);
}
/*******************************************************************************
 * Calls ajax post for delete contract route
 ******************************************************************************/
function awardContractAjax(contractid) {
	var contractorid = $("#contractors").val();
	var statusnotes = $("#rnotes").val();
	$.ajax({
		url : 'awardContractAjax.html',
		type : 'POST',
		data : {
			cid : contractid,
			conid : contractorid,
			snotes : statusnotes
		},
		success : function(xml) {
			$(xml).find('CONTRACTORSTATUS').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "STATUSUPDATED") {
							$("#successmessage").html(
									"Contract has been awarded").css("display",
									"block").delay(6000).fadeOut();
							$('#mainalerts').show();

							var surl = "adminViewContract.html?vid="
									+ contractid;
							$("#pageContentBody").load(surl);
							$('#myModal4').modal('hide');

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal4').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for awarding contact
 ******************************************************************************/
function opensuspendcontract(cid, contractname,stype) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	if(stype == "S"){
		$('#maintitle4').text("Suspend Contract");
		$('#title24').text("Are you sure you want to suspend this contract?");
	}else{
		$('#maintitle4').text("Unsuspend Contract");
		$('#title24').text("Are you sure you want to unsuspend this contract?");
	}
	
	$('#title14').text("Contract: " + contractname);
	
	$('#buttonleft4').text("YES");
	$('#buttonright4').text("NO");
	$('#contractors').hide();
	// now we add the onclick event
	$("#buttonleft4").click(function() {
		suspendContractAjax(cid,stype);
	});
	$('#myModal4').modal(options);
}
/*******************************************************************************
 * Calls ajax post for delete contract route
 ******************************************************************************/
function suspendContractAjax(contractid,stype) {
	var contractorid = $("#contractors").val();
	var statusnotes = $("#rnotes").val();
	$.ajax({
		url : 'suspendContractAjax.html',
		type : 'POST',
		data : {
			cid : contractid,
			snotes : statusnotes,
			statustype: stype
		},
		success : function(xml) {
			$(xml).find('CONTRACTORSTATUS').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "STATUSUPDATED") {
							$("#successmessage").html(
									"Contract has been suspended").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();

							var surl = "adminViewContract.html?vid="
									+ contractid;
							$("#pageContentBody").load(surl);
							$('#myModal4').modal('hide');

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal4').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for cancelling contract
 ******************************************************************************/
function opencancelcontract(cid, contractname) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitle4').text("Cancel Contract");
	$('#title14').text("Contract: " + contractname);
	$('#title24').text("Are you sure you want to cancel this contract?");
	$('#buttonleft4').text("YES");
	$('#buttonright4').text("NO");
	$('#contractors').hide();
	// now we add the onclick event
	$("#buttonleft4").click(function() {
		cancelContractAjax(cid);
	});
	$('#myModal4').modal(options);
}
/*******************************************************************************
 * Calls ajax post for delete contract route
 ******************************************************************************/
function cancelContractAjax(contractid) {
	var contractorid = $("#contractors").val();
	var statusnotes = $("#rnotes").val();
	$.ajax({
		url : 'cancelContractAjax.html',
		type : 'POST',
		data : {
			cid : contractid,
			snotes : statusnotes
		},
		success : function(xml) {
			$(xml).find('CONTRACTORSTATUS').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "STATUSUPDATED") {
							$("#successmessage").html(
									"Contract has been cancelled").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();

							var surl = "adminViewContract.html?vid="
									+ contractid;
							$("#pageContentBody").load(surl);
							$('#myModal4').modal('hide');

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal4').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for changing driver on route
 ******************************************************************************/
function openchangedriver(routeid) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonleft').text("YES");
	$('#buttonright').text("NO");
	// now we add the onclick event
	$("#buttonleft").click(function(event) {
		event.preventDefault();
		// use contract function same fields
		// if(checknewdocumentcontract()){
		addDriverToRoute(routeid);
		// }
		// deletevehicle(splate,id);

	});
	$('#myModal').modal(options);

}
/*******************************************************************************
 * Calls ajax post for delete contract route
 ******************************************************************************/
function addDriverToRoute(routeid) {
	var driverid = $("#drivers").val();
	var contractid = $("#contractid").val();
	$.ajax({
		url : 'addDriverToRouteAjax.html',
		type : 'POST',
		data : {
			rid : routeid,
			did : driverid
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "UPDATED") {
							$("#successmessage").html(
									"Driver has been added to route").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();

							var surl = "viewRouteInformation.html?cid="
									+ contractid + "&rid=" + routeid;
							$("#pageContentBody").load(surl);
							$('#myModal').modal('hide');

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for changing driver on route
 ******************************************************************************/
function openchangevehicle(routeid) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonleftv').text("YES");
	$('#buttonrightv').text("NO");
	// now we add the onclick event
	$("#buttonleftv").click(function(event) {
		event.preventDefault();
		// use contract function same fields
		// if(checknewdocumentcontract()){
		addVehicleToRoute(routeid);
		// }
		// deletevehicle(splate,id);

	});
	$('#myModalv').modal(options);

}
/*******************************************************************************
 * Calls ajax post for delete contract route
 ******************************************************************************/
function addVehicleToRoute(routeid) {
	var vehicleid = $("#vehicles").val();
	var contractid = $("#contractid").val();
	$.ajax({
		url : 'addVehicleToRouteAjax.html',
		type : 'POST',
		data : {
			rid : routeid,
			vid : vehicleid
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "UPDATED") {
							$("#successmessage").html(
									"Vehicle has been added to route").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();

							var surl = "viewRouteInformation.html?cid="
									+ contractid + "&rid=" + routeid;
							$("#pageContentBody").load(surl);
							$('#myModalv').modal('hide');

						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModalv').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Open step two report creation
 ******************************************************************************/
function openstep2() {
	$("#divstep1").hide();
	getReportTableFields();
	$("#divstep2").show();
}
/*******************************************************************************
 * Open step one prev button
 ******************************************************************************/
function openstep1prev() {
	$("#divstep2").hide();
	$("#divstep1").show();
}
/*******************************************************************************
 * Open step three report creation
 ******************************************************************************/
function openstep3() {
	$("#divstep2").hide();
	$("#divstep3").show();
}
/*******************************************************************************
 * Open step one prev button
 ******************************************************************************/
function openstep2prev() {
	$("#divstep3").hide();
	$("#divstep2").show();
}
/*******************************************************************************
 * Open step three report creation
 ******************************************************************************/
function openstep4() {
	$("#divstep3").hide();
	$("#divstep4").show();
}
/*******************************************************************************
 * Open step one prev button
 ******************************************************************************/
function openstep3prev() {
	$("#divstep4").hide();
	$("#divstep3").show();
}
/*******************************************************************************
 * Calls ajax post to get report fields
 ******************************************************************************/
function getReportTableFields() {
	// var tsids =$("#selecttables").val();
	var tsids = "";
	// tsids="1,2,3";
	$('#multiselect').empty();
	$('#selectfields').empty();
	$("#selecttables option:selected").each(function() {
		var $this = $(this);
		if ($this.length) {
			var selText = $this.val();
			if (tsids == "") {
				tsids = selText;
			} else {
				tsids = tsids + "," + selText
			}
		}
	});
	$.ajax({
		url : 'getReportTableFieldsAjax.html',
		type : 'POST',
		data : {
			sids : tsids
		},
		success : function(xml) {
			var optiond = new Option("Please select", "-1");
			$('#selectfields').append($(optiond));
			$('#selectfields').val("-1");
			$(xml).find('FIELD').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							var optiontext = $(this).find("TITLE").text()
									+ " (" + $(this).find("TABLETITLE").text()
									+ ")";
							var option = new Option(optiontext, $(this).find(
									"ID").text());
							$('#multiselect').append($(option));
							// we populate the critera
							var option2 = new Option(optiontext, $(this).find(
									"ID").text());
							$('#selectfields').append($(option2));
						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for changing driver on route
 ******************************************************************************/
function openaddnewcriteria() {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonleft').text("YES");
	$('#buttonright').text("NO");
	// now we add the onclick event
	// $("#buttonleft").click(function(event){
	// event.preventDefault();
	// addnewcriterarow();

	// });
	$("#criteriatext").val("");
	$('#selectfields').val("-1");
	$("#divoperator").hide();
	$("#divcriteriatext").hide();
	$("#divdropdown").hide();
	$("#dalert").hide();
	$("#dalerts").hide();
	$("#divdates").hide();
	$("#divenddate").hide();
	$('#myModal').modal(options);

}
/*******************************************************************************
 * Calls ajax post to get report field properties
 ******************************************************************************/
function getfieldproperties() {
	var fieldid = $("#selectfields").val();
	if (fieldid == "") {
		return;
	}
	$.ajax({
		url : 'getReportTableFieldPropertiesAjax.html',
		type : 'POST',
		data : {
			sid : fieldid
		},
		success : function(xml) {
			$(xml).find('FIELD').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							showfieldcontrols($(this).find("FIELDTYPE").text(),
									$(this).find("RELATEDFIELD").text(), xml);
						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * populates operator dropdown with correct values
 ******************************************************************************/
function showfieldcontrols(fieldtype, relatedfield, xml) {
	if (fieldtype == "VARCHAR") {
		$('#selectoperator').empty();
		var option2 = new Option("EQUALS", "EQ");
		$('#selectoperator').append($(option2));
		option2 = new Option("CONTAINS", "CO");
		$('#selectoperator').append($(option2));
		$("#divoperator").show();
		$("#divcriteriatext").show();
		$("#hidfieldtype").val("VARCHAR");
		$("#divdropdown").hide();
		$("#divdates").hide();
		$("#divenddate").hide();
	} else if (fieldtype == "DDLIST") {
		$('#selectoperator').empty();
		var option2 = new Option("EQUALS", "EQ");
		$('#selectoperator').append($(option2));
		$("#divoperator").show();
		$("#divcriteriatext").hide();
		$("#hidfieldtype").val("DDLIST");
		$("#divdropdown").show();
		$('#selectrelated').empty();
		var option = new Option("Please select", " ");
		$('#selectrelated').append($(option));
		$(xml).find('DDITEM').each(
				function() {
					// now add the items if any

					var option2 = new Option($(this).find("TEXT").text(), $(
							this).find("ID").text());
					$('#selectrelated').append($(option2));

				});
		$("#divdates").hide();
		$("#divenddate").hide();

	} else if (fieldtype == "DATE") {
		$('#selectoperator').empty();
		var option2 = new Option("EQUALS", "EQ");
		$('#selectoperator').append($(option2));
		option2 = new Option("GREATER THAN", "GT");
		$('#selectoperator').append($(option2));
		option2 = new Option("LESS THAN", "LT");
		$('#selectoperator').append($(option2));
		option2 = new Option("BETWEEN", "BT");
		$('#selectoperator').append($(option2));
		$("#divoperator").show();
		$("#divcriteriatext").hide();
		$("#hidfieldtype").val("DATE");
		$("#divdropdown").hide();
		$('#selectrelated').empty();
		$("#divdates").show();
		$("#divenddate").hide();
	} else if (fieldtype == "FILE") {
		$('#selectoperator').empty();
		var option2 = new Option("EQUALS", "EQ");
		$('#selectoperator').append($(option2));
		$("#divoperator").show();
		$("#divcriteriatext").hide();
		$("#hidfieldtype").val("DDLIST");
		$("#divdropdown").show();
		$('#selectrelated').empty();
		var option = new Option("Please select", " ");
		$('#selectrelated').append($(option));
		$(xml).find('DDITEM').each(
				function() {
					// now add the items if any

					var option2 = new Option($(this).find("TEXT").text(), $(
							this).find("ID").text());
					$('#selectrelated').append($(option2));

				});
		$("#divdates").hide();
		$("#divenddate").hide();

	} else if (fieldtype == "YESNO") {
		$('#selectoperator').empty();
		var option2 = new Option("EQUALS", "EQ");
		$('#selectoperator').append($(option2));
		;
		$("#divoperator").show();
		$("#divcriteriatext").hide();
		$("#hidfieldtype").val("YESNO");
		$("#divdropdown").hide();
		$('#selectrelated').empty();
		$("#divdates").hide();
		$("#divenddate").hide();
		$("#divyesno").show();
	} else {

		$("#divoperator").hide();
		$("#divcriteriatext").hide();
		$("#divdropdown").hide();
		$("#divdates").hide();
		$("#divenddate").hide();
	}
}
/*******************************************************************************
 * add new row to criteria table
 ******************************************************************************/
function addnewcriterarow() {
	if (checkcriteriafields()) {
		if ($("#hidfieldtype").val() == "VARCHAR") {
			var fieldname = $("#selectfields option:selected").text();
			var operator = $("#selectoperator option:selected").text();
			var fieldid = $("#selectfields option:selected").val();
			var operatorid = $("#selectoperator option:selected").val();
			var text = $("#criteriatext").val();
			var markup = "<tr><td>"
					+ fieldname
					+ "<input type='hidden' id='fieldid' name ='fieldid' value='"
					+ fieldid + "'></td>";
			markup += "<td align='center'>"
					+ operator
					+ "<input type='hidden' id='operatorid' name ='operatorid' value='"
					+ operatorid + "'></td>";
			markup += "<td  align='center'>" + text
					+ "<input type='hidden' id='ctext' name ='ctext' value='"
					+ text + "'>";
			markup += "<input type='hidden' id='cid' name ='cid' value='-1'>";
			markup += "<input type='hidden' id='selectid' name ='selectid' value='-1'><input type='hidden' id='startdate' name ='startdate' value=''><input type='hidden' id='enddate' name ='enddate' value=''></td>";
			markup += "<td  align='center'><a class='edit' href='#' onclick='$(this).closest(\"tr\").remove();'><img src='includes/img/deletesm-off.png' class='img-swap' border=0 title='Delete Criteria' style='padding-top:3px;padding-bottom:3px;'></a></td></tr>";
			$("#reportcriteria tbody").append(markup);
		} else if ($("#hidfieldtype").val() == "DDLIST") {
			var fieldname = $("#selectfields option:selected").text();
			var operator = $("#selectoperator option:selected").text();
			var selectvalue = $("#selectrelated option:selected").text();
			var fieldid = $("#selectfields option:selected").val();
			var operatorid = $("#selectoperator option:selected").val();
			var selectid = $("#selectrelated option:selected").val();
			// var text = $("#criteriatext").val();
			var markup = "<tr><td>"
					+ fieldname
					+ "<input type='hidden' id='fieldid' name ='fieldid' value='"
					+ fieldid + "'></td>";
			markup += "<td align='center'>"
					+ operator
					+ "<input type='hidden' id='operatorid' name ='operatorid' value='"
					+ operatorid + "'></td>";
			markup += "<td  align='center'><input type='hidden' id='ctext' name ='ctext' value=''>";
			markup += "<input type='hidden' id='cid' name ='cid' value='-1'>";
			markup += selectvalue
					+ "<input type='hidden' id='selectid' name ='selectid' value='"
					+ selectid
					+ "'><input type='hidden' id='startdate' name ='startdate' value=''><input type='hidden' id='enddate' name ='enddate' value=''></td>";
			markup += "<td  align='center'><a class='edit' href='#' onclick='$(this).closest(\"tr\").remove();'><img src='includes/img/deletesm-off.png' class='img-swap' border=0 title='Delete Criteria' style='padding-top:3px;padding-bottom:3px;'></a></td></tr>";
			$("#reportcriteria tbody").append(markup);
		} else if ($("#hidfieldtype").val() == "DATE") {
			var fieldname = $("#selectfields option:selected").text();
			var operator = $("#selectoperator option:selected").text();
			var selectvalue = $("#selectrelated option:selected").text();
			var fieldid = $("#selectfields option:selected").val();
			var operatorid = $("#selectoperator option:selected").val();
			var selectid = $("#selectrelated option:selected").val();
			var startdate = $("#sstartdate").val();
			var enddate = $("#senddate").val();
			// var text = $("#criteriatext").val();
			var markup = "<tr><td>"
					+ fieldname
					+ "<input type='hidden' id='fieldid' name ='fieldid' value='"
					+ fieldid + "'></td>";
			markup += "<td align='center'>"
					+ operator
					+ "<input type='hidden' id='operatorid' name ='operatorid' value='"
					+ operatorid + "'></td>";
			if (operatorid == "BT") {
				markup += "<td  align='center'><input type='hidden' id='ctext' name ='ctext' value=''>";
				markup += "<input type='hidden' id='cid' name ='cid' value='-1'>";
				markup += startdate
						+ " and "
						+ enddate
						+ "<input type='hidden' id='selectid' name ='selectid' value='-1'>";
				markup += "<input type='hidden' id='startdate' name ='startdate' value='"
						+ startdate + "'>";
				markup += "<input type='hidden' id='enddate' name ='enddate' value='"
						+ enddate + "'></td>";
				markup += "<td  align='center'><a class='edit' href='#' onclick='$(this).closest(\"tr\").remove();'><img src='includes/img/deletesm-off.png' class='img-swap' border=0 title='Delete Criteria' style='padding-top:3px;padding-bottom:3px;'></a></td></tr>";
				$("#reportcriteria tbody").append(markup);
			} else {
				markup += "<td  align='center'><input type='hidden' id='ctext' name ='ctext' value=''>";
				markup += "<input type='hidden' id='cid' name ='cid' value='-1'>";
				markup += startdate
						+ "<input type='hidden' id='selectid' name ='selectid' value='-1'>";
				markup += "<input type='hidden' id='startdate' name ='startdate' value='"
						+ startdate + "'>";
				markup += "<input type='hidden' id='enddate' name ='enddate' value='"
						+ enddate + "'></td>";
				markup += "<td  align='center'><a class='edit' href='#' onclick='$(this).closest(\"tr\").remove();'><img src='includes/img/deletesm-off.png' class='img-swap' border=0 title='Delete Criteria' style='padding-top:3px;padding-bottom:3px;'></a></td></tr>";
				$("#reportcriteria tbody").append(markup);
			}
		} else if ($("#hidfieldtype").val() == "YESNO") {
			var fieldname = $("#selectfields option:selected").text();
			var operator = $("#selectoperator option:selected").text();
			var selectvalue = $("#selectyesno option:selected").text();
			var fieldid = $("#selectfields option:selected").val();
			var operatorid = $("#selectoperator option:selected").val();
			var selectid = $("#selectyesno option:selected").val()
			// var text = $("#criteriatext").val();
			var markup = "<tr><td>"
					+ fieldname
					+ "<input type='hidden' id='fieldid' name ='fieldid' value='"
					+ fieldid + "'></td>";
			markup += "<td align='center'>"
					+ operator
					+ "<input type='hidden' id='operatorid' name ='operatorid' value='"
					+ operatorid + "'></td>";
			markup += "<td  align='center'><input type='hidden' id='ctext' name ='ctext' value='"
					+ selectid + "'>";
			markup += "<input type='hidden' id='cid' name ='cid' value='-1'>";
			markup += selectvalue
					+ "<input type='hidden' id='selectid' name ='selectid' value='-1'><input type='hidden' id='startdate' name ='startdate' value=''><input type='hidden' id='enddate' name ='enddate' value=''></td>";
			markup += "<td  align='center'><a class='edit' href='#' onclick='$(this).closest(\"tr\").remove();'><img src='includes/img/deletesm-off.png' class='img-swap' border=0 title='Delete Criteria' style='padding-top:3px;padding-bottom:3px;'></a></td></tr>";
			$("#reportcriteria tbody").append(markup);
		}

		$('#myModal').modal('toggle');
	} else {

	}

}
/*******************************************************************************
 * add new row to criteria table
 ******************************************************************************/
function checkcriteriafields() {
	if ($("#hidfieldtype").val() == "VARCHAR") {
		// now we check textbox
		if ($("#criteriatext").val() == "") {
			$("#demessage").html("Please enter text").css("display", "block")
					.delay(6000).fadeOut();
			$("#dalert").show();
			return false;
		}

	} else if ($("#hidfieldtype").val() == "DDLIST") {
		// now we check dropdown
		if ($("#selectrelated").val() == "") {
			$("#demessage").html("Please select value").css("display", "block")
					.delay(6000).fadeOut();
			$("#dalert").show();
			return false;
		}
	} else if ($("#hidfieldtype").val() == "DDLIST") {
		// now we check dropdown
		if ($("#startdate").val() == "") {
			$("#demessage").html("Please enter start date").css("display",
					"block").delay(6000).fadeOut();
			$("#dalert").show();
			return false;
		}
		if ($("#enddate").val() == "") {
			$("#demessage").html("Please enter end date").css("display",
					"block").delay(6000).fadeOut();
			$("#dalert").show();
			return false;
		}
	}

	return true;
}
/*******************************************************************************
 * check to see if data type is date then show correct selection fields
 ******************************************************************************/
function checkfieldtype() {
	if ($("#hidfieldtype").val() == "DATE") {
		if ($("#selectoperator").val() == "BT") {
			$("#divdates").show();
			$("#divenddate").show();
		} else {
			$("#divdates").show();
			$("#divenddate").hide();
		}
	}
}
/*******************************************************************************
 * Open step one prev button
 ******************************************************************************/
function showcompletemessage() {
	$("#divstep4").hide();
	$("#divstep5").show();
}
/*******************************************************************************
 * functions updates hidden field with deleted row ids
 ******************************************************************************/
function deletecriteriarow(cid) {
	var deletedids = $("#deletedids").val();
	if (deletedids.length > 0) {
		$("#deletedids").val(deletedids + "," + cid);
	} else {
		$("#deletedids").val(cid);
	}
}
/*******************************************************************************
 * Calls ajax post to update report
 ******************************************************************************/
function updateCustomReport() {
	var frm = $('#contact-form-up');
	var selectedids = "";
	$("#multiselect_to > option").each(function() {
		if (selectedids.length < 1) {
			selectedids = $(this).val();
		} else {
			selectedids = selectedids + "," + $(this).val();
		}
	});
	$("#selectedfields").val(selectedids);
	$.ajax({
		url : 'updateCustomReport.html',
		type : 'POST',
		data : frm.serialize(),
		success : function(xml) {
			$(xml).find('REPORT').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "UPDATED") {
							var surl = "viewMyReports.html";
							// $("#pageContentBody").load(surl);
							loadMainDivPage(surl);
							$("#successmessage").html("Report updated").css(
									"display", "block").delay(6000).fadeOut();
							$('#mainalerts').show();

						} else {
							$("#errormessage").text(
									$(this).find("MESSAGE").text());
							$("#mainalert").show();
							$("#mainalerts").hide();
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
			$("#mainalerts").hide();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for deleting report confirmation
 ******************************************************************************/
function opendeletedialogreport(stitle, id) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitle').text("Delete Report");
	$('#title1').text("Report Title: " + stitle);
	$('#title2').text("");
	$('#title3').text("Are you sure you want to delete this report?");
	$('#buttonleft').text("YES");
	$('#buttonright').text("NO");
	// now we add the onclick event
	$("#buttonleft").click(function() {
		deletereport(stitle, id);
	});
	$('#myModal').modal(options);

}
/*******************************************************************************
 * Calls ajax post for delete vehicle
 ******************************************************************************/
function deletereport(stitle, id) {
	$.ajax({
		url : 'deleteCustomReport.html',
		type : 'POST',
		data : {
			reportid : id,
			reportitle : stitle
		},
		success : function(xml) {
			$(xml).find('REPORT').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#details_success_message")
									.html("Report Has Been Deleted").css(
											"display", "block").delay(6000)
									.fadeOut();
							$('#details_success_message').show();

							var surl = "viewMyReports.html";
							$("#pageContentBody").load(surl);
							$('#myModal').modal('hide');

						} else {
							$("#details_error_message").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#details_error_message").show();
							$('#myModal').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			alert(error);
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Calls employee details page
 ******************************************************************************/
function getContractorsEmployees() {
	var reportid = $.trim($('#selectcon').val());
	var surl = "viewEmployeesDetailsReport.html?cid=" + reportid;
	$("#pageContentBody").load(surl);
}
/*******************************************************************************
 * Calls employee details page
 ******************************************************************************/
function getContractorsVehicles() {
	var reportid = $.trim($('#selectcon').val());
	var surl = "viewVehiclesDetailsReport.html?cid=" + reportid;
	$("#pageContentBody").load(surl);
}
/*******************************************************************************
 * Calls ajax post to get report fields
 ******************************************************************************/
function getSubDropdownItems() {
	// var tsids =$("#selecttables").val();
	var typeid = $('#vtype').val();
	$('#vsize').empty();
	if (typeid < 0) {
		return;
	}
	$.ajax({
		url : 'getSubDropdownItems.html',
		type : 'POST',
		data : {
			pid : typeid
		},
		success : function(xml) {
			var optiond = new Option("Please select", "-1");
			$('#vsize').append($(optiond));
			$('#selectfields').val("-1");
			$(xml).find('FIELD').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							var optiontext = $(this).find("TITLE").text();
							var option2 = new Option(optiontext, $(this).find(
									"ID").text());
							$('#vsize').append($(option2));
						} else {
							$("#errormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errormessage").html(error).css("display", "block").delay(6000)
					.fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Calls ajax post for suspend contractor vehicle
 ******************************************************************************/
function suspendvehicle() {
	var vehicleid = "";
	vehicleid = $("#vid").val();
	var rejectnotes = $("#rnotes").val();
	$.ajax({
		url : 'suspendContractorVehicleAjax.html',
		type : 'POST',
		data : {
			cid : vehicleid,
			rnotes : rejectnotes
		},
		success : function(xml) {
			$(xml).find('CONTRACTORSTATUS').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$('#myModal').modal('hide');
							$('.modal-backdrop').remove();
							$('body').removeClass('modal-open');
							$("#vehiclesuccessmessage").html(
									"Vehicle has been suspended").css(
									"display", "block").delay(6000).fadeOut();
							var surl="";
							surl = "adminViewVehicle.html?cid=" + vehicleid;
									
							$("#pageContentBody").load(surl);

						} else {
							$("#vehicleerrormessage").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#vehicleerrormessage").html(error).css("display", "block")
					.delay(6000).fadeOut();

		},
		dataType : "text",
		async : false

	});
}
/*******************************************************************************
 * Opens dialog for adding new training
 ******************************************************************************/
function openaddnewdialogtr() {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonleft').text("YES");
	$('#buttonright').text("NO");
	// now we add the onclick event
	$("#buttonleft").click(function(event) {
		event.preventDefault();
		if (checknewtraining()) {
			addnewemployeetraining('C');
		}
	});
	$('#myModal').modal(options);

}
/*******************************************************************************
 * check for add new training
 ******************************************************************************/
function checknewtraining() {
	var trainingdate = $.trim($('#trainingdate').val());
	var selected = $("#trainingtype").val();
	var selectedlen = $("#traininglength").val();
	var location = $("#location").val();
	var providedby = $("#providedby").val();
	var docname = $("#documentname").val();
	if (selected < 1) {
		$("#demessage").html("Please enter training type").css("display",
				"block").delay(6000).fadeOut();
		$("#dalert").show();
		return false;
	}
	if (trainingdate == "") {
		$("#demessage").html("Please enter training date").css("display",
				"block").delay(6000).fadeOut();
		$("#dalert").show();
		return false;
	}
	if (selectedlen < 1) {
		$("#demessage").html("Please enter training length").css("display",
				"block").delay(6000).fadeOut();
		$("#dalert").show();
		return false;
	}
	if (providedby == "") {
		$("#demessage").html("Please enter provided by").css("display",
				"block").delay(6000).fadeOut();
		$("#dalert").show();
		return false;
	}
	if (location == "") {
		$("#demessage").html("Please enter location").css("display",
				"block").delay(6000).fadeOut();
		$("#dalert").show();
		return false;
	}
	if(!(docname == "")){
		var reg = /(.*?)\.(pdf)$/;
		if(!docname.match(reg)){
			$("#demessage").html("PDF documents only")
			.css("display", "block").delay(6000).fadeOut();
			$("#dalert").show();
			return false;
		}
	}
	$("#dalert").hide();

	return true;
}
/*******************************************************************************
 * add new employee training
 ******************************************************************************/
function addnewemployeetraining(trantype) {
	var selected = $("#trainingtype").val();
	var tdate = $("#trainingdate").val();
	var edate = $("#expirydate").val();
	var ufile = $('#documentname')[0].files[0];
	var rnotes = $("#rnotestr").val();
	var employeeid = $.trim($('#cid').val());
	var selectedlen = $("#traininglength").val();
	var providedby = $("#providedby").val();
	var location = $("#location").val();
	var requestd = new FormData();
	requestd.append('trainingtype', selected);
	requestd.append('trainingdate', tdate);
	requestd.append('expirydate', edate);
	requestd.append('documentfile', ufile);
	requestd.append('rnotes', rnotes);
	requestd.append('eid', employeeid);
	requestd.append('ttype',trantype);
	requestd.append('traininglength',selectedlen);
	requestd.append('providedby',providedby);
	requestd.append('location',location);
	var surl = "";
	if(trantype == "C"){
		surl="addNewEmployeeTraining.html";
	}else{
		surl="addNewEmployeeTrainingAdmin.html";
	}
	$.ajax({
		url : surl,
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {

			var i = 1;
			$(xml).find('DOCUMENT').each(
					function() {

						if ($(this).find("MESSAGE").text() == "ADDED") {
							var surl = "";
							if(trantype=='A'){
								surl = "adminViewEmployee.html?vid=" + employeeid + "&tab=T";
							}else{
								surl = "addNewEmployee.html?vid=" + employeeid + "&tab=T";
							}
							
							$("#pageContentBody").load(surl);
							$("#display_success_message_top").html(
									"Employee Training Has Been Added").css(
									"display", "block").delay(6000).fadeOut();
							if(trantype=='A'){
								$('#myModalTrain').modal('hide');
							}else{
								$('#myModal').modal('hide');
							}
							
						} else {
							$("#display_error_message_top").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#myModal').modal('hide');
						}
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$("#display_error_message_top").html(textStatus).css("display",
					"block").delay(6000).fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false
	});
}
/*******************************************************************************
 * Opens dialog for deleting trainig
 ******************************************************************************/
function opendeletetradialog(sdoc, id) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitled').text("Delete Traing");
	$('#spantitle1').text("Training Type: " + sdoc);
	$('#spantitle2').text("Are you sure you want to delete this training");
	$('#buttonleftd').text("YES");
	$('#buttonrightd').text("NO");
	// now we add the onclick event
	$("#buttonleftd").click(function() {
		deleteemployeetraining(sdoc, id,'C');
	});
	$('#myModal2').modal(options);
}
/*******************************************************************************
 * Calls ajax post for delete vehicle
 ******************************************************************************/
function deleteemployeetraining(docname, id,trantype) {
	var employeeid = $("#cid").val();
	var surl="";
	if(trantype == "A"){
		surl="deleteEmployeeTrainingAdmin.html";
	}else{
		surl="deleteEmployeeTraining.html";
	}
	$.ajax({
		url : surl,
		type : 'POST',
		data : {
			vid : id,
			document : docname,
			eid : employeeid,
			ttype: trantype
		},
		success : function(xml) {
			$(xml).find('DOCUMENT').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							var surl = "";
							if(trantype == 'A'){
								surl = "adminViewEmployee.html?vid=" + employeeid
								+ "&tab=T";
							}else{
								surl = "addNewEmployee.html?vid=" + employeeid
								+ "&tab=T";
							}
							$("#pageContentBody").load(surl);
							$("#display_success_message_top").html(
									"Employee Training Has Been Deleted").css(
									"display", "block").delay(6000).fadeOut();
							if(trantype == 'A'){
								$('#myModalTrainD').modal('hide');
							}else{
								$('#myModal2').modal('hide');
							}
							

						} else {
							$("#display_error_message_top").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#myModa2l').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#display_error_message_top").html(error).css("display", "block")
					.delay(6000).fadeOut();

		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for adding new training
 ******************************************************************************/
function openaddnewdialoglet(lettertype) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonleftadd').text("YES");
	$('#buttonrightadd').text("NO");
	// now we add the onclick event
	$("#buttonleftadd").click(function(event) {
		event.preventDefault();
		if (checknewletter()) {
			addnewemployeeletter(lettertype);
		}
	});
	$('#modalAdd').modal(options);
	

}
/*******************************************************************************
 * check for add new letter
 ******************************************************************************/
function checknewletter() {
	$("#dalertadd").hide();
	var docname = $("#lname").val();
	var pfile = $.trim($('#ldocument').val());
	if (docname == "") {
		$("#dmessageadd").html("Please enter letter name").css("display",
				"block").delay(6000).fadeOut();
		$("#dalertadd").show();
		return false;
	}
	if (pfile == "") {
		$("#dmessageadd").html("Please select document")
				.css("display", "block").delay(6000).fadeOut();
		$("#dalertadd").show();
		return false;
	}else{
		var reg = /(.*?)\.(pdf)$/;
		if(!pfile.match(reg)){
			$("#dmessageadd").html("PDF documents only")
			.css("display", "block").delay(6000).fadeOut();
			$("#dalertadd").show();
			return false;
	}
		
	}

	return true;
}
/*******************************************************************************
 * add new employee letter
 ******************************************************************************/
function addnewemployeeletter(lettertype) {
	var lname = $("#lname").val();
	var ufile = $('#ldocument')[0].files[0];
	var lnotes = $("#lnotes").val();
	var employeeid = $.trim($('#cid').val());
	var requestd = new FormData();
	requestd.append('lname', lname);
	requestd.append('ldocument', ufile);
	requestd.append('lnotes', lnotes);
	requestd.append('eid', employeeid);
	requestd.append('ltype', lettertype);
	$.ajax({
		url : "addNewEmployeeLetter.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {

			var i = 1;
			$(xml).find('DOCUMENT').each(
					function() {

						if ($(this).find("MESSAGE").text() == "ADDED") {
							var surl = "";
							if (lettertype == "E") {
								surl = "adminViewEmployee.html?vid="
										+ employeeid + "&tab=L";
							} else {
								surl = "adminViewContractor.html?cid="
										+ employeeid + "&tab=L";
							}

							$("#pageContentBody").load(surl);
							$("#display_success_message_top").html(
									"Letter Has Been Added").css("display",
									"block").delay(6000).fadeOut();
							$('#modalAdd').modal('hide');
						} else {
							$("#display_error_message_top").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#modalAdd').modal('hide');
						}
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$("#display_error_message_top").html(textStatus).css("display",
					"block").delay(6000).fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false
	});
}
/*******************************************************************************
 * Opens dialog for deleting letter on file
 ******************************************************************************/
function opendeleteletterdialog(sdoc, id, lettertype) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitled').text("Delete Letter");
	$('#spantitle1').text("Letter Name: " + sdoc);
	$('#spantitle2').text("Are you sure you want to delete this letter?");
	$('#buttonleftd').text("YES");
	$('#buttonrightd').text("NO");
	// now we add the onclick event
	$("#buttonleftd").click(function() {
		deleteletter(sdoc, id, lettertype);
	});
	$('#modalDelete').modal(options);
}
/*******************************************************************************
 * Calls ajax post for delete letter
 ******************************************************************************/
function deleteletter(docname, id, lettertype) {
	var employeeid = $("#cid").val();
	$.ajax({
		url : 'deleteLetter.html',
		type : 'POST',
		data : {
			letterid : id
		},
		success : function(xml) {
			$(xml).find('LETTER').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							var surl = "";
							if (lettertype == "E") {
								surl = "adminViewEmployee.html?vid="
										+ employeeid + "&tab=L";
							} else {
								surl = "adminViewContractor.html?cid="
										+ employeeid + "&tab=L";
							}
							$("#pageContentBody").load(surl);
							$("#display_success_message_top").html(
									"Letter Has Been Deleted").css("display",
									"block").delay(6000).fadeOut();
							$('#modalDelete').modal('hide');

						} else {
							$("#display_error_message_top").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#modalDelete').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#display_error_message_top").html(error).css("display", "block")
					.delay(6000).fadeOut();

		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Check add contractor fields admin
 ******************************************************************************/
function checkAddContratorFields() {
	var firstname = $("#firstname").val();
	var lastname = $("#lastname").val();
	var conemail = $("#conemail").val();
	var address1 = $("#address1").val();
	var city = $("#city").val();
	var province = $("#province").val();
	var postalcode = $("#postalcode").val();

	var tregular = $('#tregular').prop('checked');
	var talternate = $('#talternate').prop('checked');
	var msameas = $('#msameas').prop('checked');
	var maddress1 = $("#maddress1").val();
	var mcity = $("#mcity").val();
	var mprovince = $("#mprovince").val();
	var mpostalcode = $("#mpostalcode").val();
	var homephone = $("#homephone").val();
	var busnumber = $("#busnumber").val();
	var hstnumber = $("#hstnumber").val();
	if (firstname == "") {
		$('#display_error_message_bottom').text("Please enter first name").css(
				"display", "block").delay(6000).fadeOut();
		return false;
	}
	if (lastname == "") {
		$('#display_error_message_bottom').text("Please enter last name").css(
				"display", "block").delay(6000).fadeOut();
		return false;
	}
	if (conemail == "") {
		$('#display_error_message_bottom').text("Please enter email").css(
				"display", "block").delay(6000).fadeOut();
		return false;
	}
	if (!(isEmail(conemail))) {
		$('#display_error_message_bottom').text("Please enter valid email")
				.css("display", "block").delay(6000).fadeOut();
		return false;
	}
	if (address1 == "") {
		$('#display_error_message_bottom').text("Please enter address1").css(
				"display", "block").delay(6000).fadeOut();
		return false;
	}
	if (city == "") {
		$('#display_error_message_bottom').text("Please enter city").css(
				"display", "block").delay(6000).fadeOut();
		return false;
	}
	if (province == " ") {
		$('#display_error_message_bottom').text("Please select province").css(
				"display", "block").delay(6000).fadeOut();
		return false;
	}
	if (postalcode == "") {
		$('#display_error_message_bottom').text("Please enter postal code")
				.css("display", "block").delay(6000).fadeOut();
		return false;
	}

	if (!(msameas)) {
		if (maddress1 == "") {
			$('#display_error_message_bottom').text(
					"Please enter mailing address1").css("display", "block")
					.delay(6000).fadeOut();
			return false;
		} else if (mcity == "") {
			$('#display_error_message_bottom')
					.text("Please enter mailing city").css("display", "block")
					.delay(6000).fadeOut();
			return false;
		} else if (mprovince == "") {
			$('#display_error_message_bottom').text(
					"Please select mailing province").css("display", "block")
					.delay(6000).fadeOut();
			return false;
		} else if (mpostalcode == "") {
			$('#display_error_message_bottom').text(
					"Please enter maililng postal code")
					.css("display", "block").delay(6000).fadeOut();
			return false;
		}
	}
	if (homephone == "") {
		$('#display_error_message_bottom').text("Please enter home phone").css(
				"display", "block").delay(6000).fadeOut();
		return false;
	}
	if (busnumber == "") {
		$('#display_error_message_bottom').text("Please enter business number")
				.css("display", "block").delay(6000).fadeOut();
		return false;
	}
	if (hstnumber == "") {
		$('#display_error_message_bottom').text("Please enter hst number").css(
				"display", "block").delay(6000).fadeOut();
		return false;
	}
	if ((!(tregular)) && (!(talternate))) {
		$('#display_error_message_bottom').text(
				"Please select type of transportation").css("display", "block")
				.delay(6000).fadeOut();
		return false;
	}
	// all good
	// $("#mainalerts").hide();
	// updateCompanyInformation();
	addNewContractor();
}
/*******************************************************************************
 * Check add contractor fields admin
 ******************************************************************************/
function addNewContractor() {
	var frm = $('#contact-form-up');
	$.ajax({
		url : 'adminAddNewContractorAjax.html',
		type : 'POST',
		data : frm.serialize(),
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "ADDED") {
							var cid = $(this).find("ID").text()
							var surl = "adminViewContractor.html?cid=" + cid;
							// $("#pageContentBody").load(surl);
							loadMainDivPage(surl);
							$("#display_success_message_bottom").html(
									"Contractor Added").css("display", "block")
									.delay(6000).fadeOut();

						} else {
							$("#display_error_message_bottom").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();

						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#display_error_message_bottom").html(error).css("display",
					"block").delay(6000).fadeOut();

		},
		dataType : "text",
		async : false

	});
}
/*******************************************************************************
 * Opens dialog for changing driver on route
 ******************************************************************************/
function openchangedriveradmin(routeid) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonleftdr').text("YES");
	$('#buttonrightdr').text("NO");
	// now we add the onclick event
	$("#buttonleftdr").click(function(event) {
		event.preventDefault();
		// use contract function same fields
		// if(checknewdocumentcontract()){
		addDriverToRouteAdmin(routeid);
		// }
		// deletevehicle(splate,id);

	});
	$('#modald').modal(options);

}
/*******************************************************************************
 * Opens dialog for changing driver on route
 ******************************************************************************/
function openchangevehicleadmin(routeid) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonleftv').text("YES");
	$('#buttonrightv').text("NO");
	// now we add the onclick event
	$("#buttonleftv").click(function(event) {
		event.preventDefault();
		// use contract function same fields
		// if(checknewdocumentcontract()){
		addVehicleToRouteAdmin(routeid);
		// }
		// deletevehicle(splate,id);

	});
	$('#modalv').modal(options);

}
/*******************************************************************************
 * Calls ajax post for add driver to route admin
 ******************************************************************************/
function addDriverToRouteAdmin(routeid) {
	var driverid = $("#drivers").val();
	var contractid = $("#contractid").val();
	$.ajax({
		url : 'addDriverToRouteAjaxAdmin.html',
		type : 'POST',
		data : {
			rid : routeid,
			did : driverid
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "UPDATED") {
							$("#display_success_message_bottom").html(
							"Driver added to route").css("display", "block")
							.delay(6000).fadeOut();

							var surl = "adminViewRoute.html?vid="+ routeid;
							$("#pageContentBody").load(surl);
							$('#modald').modal('hide');

						} else {
							$("#display_error_message_bottom").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#modaldr').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#display_error_message_bottom").html(error).css("display",
			"block").delay(6000).fadeOut();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Calls ajax post for adding vechicle to route
 ******************************************************************************/
function addVehicleToRouteAdmin(routeid) {
	var vehicleid = $("#vehicles").val();
	var contractid = $("#contractid").val();
	$.ajax({
		url : 'addVehicleToRouteAjaxAdmin.html',
		type : 'POST',
		data : {
			rid : routeid,
			vid : vehicleid
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "UPDATED") {
							$("#display_success_message_bottom").html(
							"Vehicle added to route").css("display", "block")
							.delay(6000).fadeOut();

							var surl = "adminViewRoute.html?vid="+ routeid;
							$("#pageContentBody").load(surl);
							$('#modalv').modal('hide');

						} else {
							$("#display_error_message_bottom").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#modaldr').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#display_error_message_bottom").html(error).css("display",
			"block").delay(6000).fadeOut();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for adding new bus run
 ******************************************************************************/
function openaddnewbusrun(routeid,ttype,rrid) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	if(ttype == "A"){
		$('#buttonleftnr').text("ADD");
		$('#maintitlenr').text("Add New Run");
	}else{
		$('#buttonleftnr').text("UPDATE");
		$('#maintitlenr').text("Update Run");
	}
	
	$('#buttonrightnr').text("CANCEL");
	// now we add the onclick event
	$("#buttonleftnr").click(function(event) {
		event.preventDefault();
		// use contract function same fields
		// if(checknewdocumentcontract()){
		//addVehicleToRouteAdmin(routeid);
		// }
		// deletevehicle(splate,id);
		
		if(checknewrun(routeid)){
			if(ttype == "A"){
				addRouteRun(routeid);
			}else{
				updateRouteRun(routeid,rrid);
			}
			
		}

	});
	$('#modalnewrun').modal(options);

}
/*******************************************************************************
 * check for add new bus run
 ******************************************************************************/
function checknewrun(routeid) {
	$("#dalertnr").hide();
	var routerun = $("#runs").val();
	var routetime = $("#times").val();
	if (routerun == " ") {
		$("#demessagenr").html("Please select run").css("display",
				"block").delay(6000).fadeOut();
		$("#dalertnr").show();
		return false;
	}
	if (routetime == " ") {
		$("#demessagenr").html("Please select time")
				.css("display", "block").delay(6000).fadeOut();
		$("#dalertnr").show();
		return false;
	}
	
	var selectedids = "";
	$("#multiselect_to > option").each(function() {
		if (selectedids.length < 1) {
			selectedids = $(this).val();
		} else {
			selectedids = selectedids + "," + $(this).val();
		}
	});
	if(selectedids.length < 1){
		$("#demessagenr").html("Please select school")
		.css("display", "block").delay(6000).fadeOut();
			$("#dalertnr").show();
				return false;
	}
	
	$("#dalertnr").hide();
	return true;
}
/*******************************************************************************
 * Calls ajax post for adding vechicle to route
 ******************************************************************************/
function addRouteRun(rid) {
	var vrouterun = $("#runs").val();
	var vroutetime = $("#times").val();
	var selectedids = "";
	$("#multiselect_to > option").each(function() {
		if (selectedids.length < 1) {
			selectedids = $(this).val();
		} else {
			selectedids = selectedids + "," + $(this).val();
		}
	});
	$.ajax({
		url : 'addRouteRunAjax.html',
		type : 'POST',
		data : {
			routeid : rid,
			routerun : vrouterun,
			routetime: vroutetime,
			schoolids: selectedids
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#display_success_message_bottom").html(
							"Run added to route").css("display", "block")
							.delay(6000).fadeOut();

							var surl = "adminViewRoute.html?vid="+ rid;
							$("#pageContentBody").load(surl);
							$('#modalnewrun').modal('hide');

						} else {
							$("#display_error_message_bottom").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#modalnewrun').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#display_error_message_bottom").html(error).css("display",
			"block").delay(6000).fadeOut();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for deleting route run confirmation
 ******************************************************************************/
function opendeletedialogrouterun(id,routeid) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitled').text("Delete Run");
	$('#title1d').text("Are you sure you want to delete this run?");
	$('#title2d').text("");
	$('#buttonleftd').text("YES");
	$('#buttonrightd').text("NO");
	// now we add the onclick event
	$("#buttonleftd").click(function() {
		deleterouterun(id,routeid);
	});
	$('#myModal2').modal(options);

}
/*******************************************************************************
 * Calls ajax post for delete vehicle
 ******************************************************************************/
function deleterouterun(id,routeid) {
	$.ajax({
		url : 'deleteRouteRun.html',
		type : 'POST',
		data : {
			routeid : id
		},
		success : function(xml) {
			$(xml).find('ROUTE').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#display_success_message_bottom")
									.html("Run Has Been Deleted").css(
											"display", "block").delay(6000)
									.fadeOut();
							$('#mainalerts').show();

							var surl = "adminViewRoute.html?vid="+ routeid;
							$("#pageContentBody").load(surl);
							$('#myModal2').modal('hide');

						} else {
							$("#display_error_message_bottom").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$("#mainalert").show();
							$('#myModal2').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#display_error_message_bottom").html(error).css("display",
			"block").delay(6000).fadeOut();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Calls ajax post to get report fields
 ******************************************************************************/
function getRouteRun(rid,routeid) {
    $.ajax({
        url : 'getRouteRunAjax.html',
        type : 'POST',
        data : {
            runid : rid
        },
        success : function(xml) {
            $(xml).find('RUN').each(
					function() {
					    $('#runs').val($(this).find("ROUTERUN").text());
					    $('#times').val($(this).find("ROUTETIME").text());
					    $(this).find('SCHOOL').each(
                            function() {
                                var option = new Option($(this).find("SCHOOLNAME").text(), $(this).find("SCHOOLID").text());
                                $('#multiselect_to').append($(option));
                                $("#multiselect > option[value='" + $(this).find("SCHOOLID").text() + "']").remove();
                            });
		        });
		},
		error : function(xhr, textStatus, error) {
		    $("#errormessage").html(error).css("display", "block").delay(6000)
		            .fadeOut();
		    $("#mainalert").show();
		},
		dataType : "text",
		    async : false
		
		});
					
openaddnewbusrun(routeid,'E',rid);

}
/*******************************************************************************
 * Calls ajax post for adding vechicle to route
 ******************************************************************************/
function updateRouteRun(rteid,rid) {
	var vrouterun = $("#runs").val();
	var vroutetime = $("#times").val();
	var selectedids = "";
	$("#multiselect_to > option").each(function() {
		if (selectedids.length < 1) {
			selectedids = $(this).val();
		} else {
			selectedids = selectedids + "," + $(this).val();
		}
	});
	$.ajax({
		url : 'updateRouteRunAjax.html',
		type : 'POST',
		data : {
			rrid : rid,
			routerun : vrouterun,
			routetime: vroutetime,
			schoolids: selectedids
		},
		success : function(xml) {
			$(xml).find('CONTRACTOR').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#display_success_message_bottom").html(
							"Run has been updated").css("display", "block")
							.delay(6000).fadeOut();

							var surl = "adminViewRoute.html?vid="+ rteid;
							$("#pageContentBody").load(surl);
							$('#modalnewrun').modal('hide');

						} else {
							$("#display_error_message_bottom").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#modalnewrun').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#display_error_message_bottom").html(error).css("display",
			"block").delay(6000).fadeOut();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for adding new training admin
 ******************************************************************************/
function openaddnewdialogtradmin() {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonlefttr').text("YES");
	$('#buttonrighttr').text("NO");
	// now we add the onclick event
	$("#buttonlefttr").click(function(event) {
		event.preventDefault();
		if (checknewtraining()) {
			addnewemployeetraining('A');
		}
	});
	$('#myModalTrain').modal(options);

}
/*******************************************************************************
 * Opens dialog for deleting training admin
 ******************************************************************************/
function opendeletetradialogadmin(sdoc, id) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitletrd').text("Delete Traing");
	$('#spantitle1trd').text("Training Type: " + sdoc);
	$('#spantitle2trd').text("Are you sure you want to delete this training");
	$('#buttonlefttrd').text("YES");
	$('#buttonrighttrd').text("NO");
	// now we add the onclick event
	$("#buttonlefttrd").click(function() {
		deleteemployeetraining(sdoc, id,'A');
	});
	$('#myModalTrainD').modal(options);
}
/*******************************************************************************
 * Opens dialog for adding new document
 ******************************************************************************/
function openaddnewdialogadmin(trantype) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#buttonleftad').text("YES");
	$('#buttonrightad').text("NO");
	// now we add the onclick event
	$("#buttonleftad").click(function(event) {
		event.preventDefault();
		if (checknewdocument()) {
			addnewvehicledocument(trantype);
		}
		// deletevehicle(splate,id);

	});
	$('#myModalDoc').modal(options);

}
/*******************************************************************************
 * Opens dialog for deleting vehicle doc admin
 ******************************************************************************/
function opendeletedocdialogadmin(sdoc, id,trantype) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#maintitledd').text("Delete Vehicle Document");
	$('#spantitle1dd').text("Vehicle Document: " + sdoc);
	$('#spantitle2dd').text("Are you sure you want to delete this document");
	$('#buttonleftdd').text("YES");
	$('#buttonrightdd').text("NO");
	// now we add the onclick event
	$("#buttonleftdd").click(function() {
		deletevehicledocument(sdoc, id,trantype)
	});
	$('#myModalDocD').modal(options);
}
/*******************************************************************************
 * Calls ajax post get training by id
 ******************************************************************************/
function ajaxGetTrainingById(trainingid) {
	$("#divviewdoc").hide();
	$
			.ajax({
				type : "POST",
				url : "getTrainingByIdAjax.html",

				data : {
					tid : trainingid
				},
				success : function(xml) {
					$(xml)
							.find('TITEM')
							.each(
									function() {
										if ($(this).find("MESSAGE").text() == "SUCCESS") {

											$('#hidid').val($(this).find("ID").text());
											$('#hidedit').val("E");
											$('#trainingtype').val($(this).find("TRAININGTYPE").text());
											$('#trainingdate').val($(this).find("TRAININGDATE").text());
											$('#expirydate').val($(this).find("EXPIRYDATE").text());
											$('#rnotestr').val($(this).find("NOTES").text());
											$('#traininglength').val($(this).find("TRAININGLENGTH").text());
											$('#providedby').val($(this).find("PROVIDEDBY").text());
											$('#location').val($(this).find("LOCATION").text());
											//$(".your-class-name a").attr("href", "http://www.web-design-weekly/");
											if(!($(this).find("TDOCUMENT").text() == "NONE")){
												$("#linkdoc").attr("href",$(this).find("TDOCUMENT").text());
												$("#divviewdoc").show();
											}
											
											var options = {
													"backdrop" : "static",
													"show" : true
												};
											$('#buttonlefttr').text("YES");
											$('#buttonrighttr').text("NO");
											// now we add the onclick event
											$("#buttonlefttr").click(function(event) {
												event.preventDefault();
												if (checknewtraining()) {
													updateemployeetraining('A');
												}
											});
											$('#myModalTrain').modal(options);

										} 
									});

					
				},
				error : function(xhr, textStatus, error) {
					alert(xhr.statusText);
					alert(textStatus);
					alert(error);
				},
				dataType : "text",
			// async: false
			});
}
/*******************************************************************************
 * add new employee training
 ******************************************************************************/
function updateemployeetraining(trantype) {
	var selected = $("#trainingtype").val();
	var tdate = $("#trainingdate").val();
	var edate = $("#expirydate").val();
	var ufile = $('#documentname')[0].files[0];
	var rnotes = $("#rnotestr").val();
	var employeeid = $.trim($('#cid').val());
	var selectedlen = $("#traininglength").val();
	var providedby = $("#providedby").val();
	var location = $("#location").val();
	var hidid = $("#hidid").val();
	var requestd = new FormData();
	requestd.append('trainingtype', selected);
	requestd.append('trainingdate', tdate);
	requestd.append('expirydate', edate);
	requestd.append('documentfile', ufile);
	requestd.append('rnotes', rnotes);
	requestd.append('eid', employeeid);
	requestd.append('ttype',trantype);
	requestd.append('traininglength',selectedlen);
	requestd.append('providedby',providedby);
	requestd.append('location',location);
	requestd.append('hidid',hidid);
	var surl ="";
	if(trantype == "A"){
		surl="updateEmployeeTrainingAdmin.html";
	}else{
		surl="updateEmployeeTraining.html";
	}
	$.ajax({
		url : "updateEmployeeTraining.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {

			var i = 1;
			$(xml).find('DOCUMENT').each(
					function() {

						if ($(this).find("MESSAGE").text() == "UPDATED") {
							var surl = "";
							if(trantype=='A'){
								surl = "adminViewEmployee.html?vid=" + employeeid + "&tab=T";
							}else{
								surl = "addNewEmployee.html?vid=" + employeeid + "&tab=T";
							}
							
							$("#pageContentBody").load(surl);
							$("#display_success_message_top").html(
									"Employee Training Has Been Updated").css(
									"display", "block").delay(6000).fadeOut();
							if(trantype=='A'){
								$('#myModalTrain').modal('hide');
							}else{
								$('#myModal').modal('hide');
							}
							
						} else {
							$("#display_error_message_top").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#myModal').modal('hide');
						}
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$("#display_error_message_top").html(textStatus).css("display",
					"block").delay(6000).fadeOut();
			$("#mainalert").show();
		},
		dataType : "text",
		async : false
	});
}

function checkfileextension(filename){
	var reg = /(.*?)\.(pdf)$/;
	if(!filename.match(reg)){
		return false;
	}else{
		return true;
	}
}
function checkdate(datetype){
	if(datetype == 'DLEXP'){
		$("#divdlexp").hide();
		if(!($("#dlexpirydate").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()+ 3650);
			var selectedDate = $('#dlexpirydate').datepicker('getDate');
			if ((Date.parse(today) > Date.parse(selectedDate)) || (Date.parse(selectedDate) > Date.parse(targetDate))){
				$("#divdlexp").show();
			}
		}
	}
	if(datetype == 'FAEXP'){
		$("#divfaexp").hide();
		if(!($("#faexpirydate").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()+ 1095);
			var selectedDate = $('#faexpirydate').datepicker('getDate');
			if ((Date.parse(today) > Date.parse(selectedDate)) || (Date.parse(selectedDate) > Date.parse(targetDate))){
				$("#divfaexp").show();
			}
		}
	}
	if(datetype == 'SCADATE'){
		$("#divscadate").hide();
		if(!($("#scadate").val() == "")){
			var today = new Date();
			var selectedDate = $('#scadate').datepicker('getDate');
			if (Date.parse(selectedDate) > Date.parse(today)){
				$("#divscadate").show();
			}
		}
	}
	if(datetype == 'RDATE'){
		$("#divrdate").hide();
		if(!($("#rdate").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()+ 365);
			var selectedDate = $('#rdate').datepicker('getDate');
			if ((Date.parse(today) > Date.parse(selectedDate)) || (Date.parse(selectedDate) > Date.parse(targetDate))){
				$("#divrdate").show();
			}
		}
	}
	if(datetype == 'IDATE'){
		$("#dividate").hide();
		if(!($("#idate").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()+ 365);
			var selectedDate = $('#idate').datepicker('getDate');
			if ((Date.parse(today) > Date.parse(selectedDate)) || (Date.parse(selectedDate) > Date.parse(targetDate))){
				$("#dividate").show();
			}
		}
	}
	if(datetype == 'FIDATE'){
		$("#divfidate").hide();
		if(!($("#fidate").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()- 365);
			var selectedDate = $('#fidate').datepicker('getDate');
			if ((Date.parse(selectedDate) > Date.parse(today)) || (Date.parse(selectedDate) < Date.parse(targetDate))){
				$("#divfidate").show();
			}
		}
	}
	if(datetype == 'WIDATE'){
		$("#divwidate").hide();
		if(!($("#widate").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()- 365);
			var selectedDate = $('#widate').datepicker('getDate');
			if ((Date.parse(selectedDate) > Date.parse(today)) || (Date.parse(selectedDate) < Date.parse(targetDate))){
				$("#divwidate").show();
			}
		}
	}
	if(datetype == 'FHEIDATE'){
		$("#divfheidate").hide();
		if(!($("#fheidate").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()- 365);
			var selectedDate = $('#fheidate').datepicker('getDate');
			if ((Date.parse(selectedDate) > Date.parse(today)) || (Date.parse(selectedDate) < Date.parse(targetDate))){
				$("#divfheidate").show();
			}
		}
	}
	if(datetype == 'MHEIDATE1'){
		$("#divmheidate1").hide();
		if(!($("#mheidate1").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()- 365);
			var selectedDate = $('#mheidate1').datepicker('getDate');
			if ((Date.parse(selectedDate) > Date.parse(today)) || (Date.parse(selectedDate) < Date.parse(targetDate))){
				$("#divmheidate1").show();
			}
		}
	}
	if(datetype == 'MHEIDATE2'){
		$("#divmheidate2").hide();
		if(!($("#mheidate2").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()- 365);
			var selectedDate = $('#mheidate2').datepicker('getDate');
			if ((Date.parse(selectedDate) > Date.parse(today)) || (Date.parse(selectedDate) < Date.parse(targetDate))){
				$("#divmheidate2").show();
			}
		}
	}
	if(datetype == 'DARUNDATE'){
		$("#divdarundate").hide();
		if(!($("#darundate").val() == "")){
			var today = new Date();
			var cyear = today.getFullYear();
			var cmonth = today.getMonth();
			var selectedDate = $('#darundate').datepicker('getDate');
			//first we check for future dates
			if(Date.parse(selectedDate) > Date.parse(today)){
				$("#spandarundate").html("Driver Abstract Run Date must be in past");
				$("#divdarundate").show();
			}
			//now we check to see if it falls in the date range
			if(cmonth > 5 && cmonth < 12){
				//check current year
				var checkdate= new Date(cyear, 4, 1);
				if(Date.parse(selectedDate) < checkdate){
					$("#spandarundate").html("Driver Abstract Run Date must be greater than " + checkdate.toLocaleDateString());
					$("#divdarundate").show();
				}
			}else{
				//now we check back to the previous year
				var checkdate= new Date(cyear-1, 4, 1);
				if(Date.parse(selectedDate) < checkdate){
					$("#spandarundate").html("Driver Abstract Run Date must be greater than " + checkdate.toLocaleDateString());
					$("#divdarundate").show();
				}
			}
		}
	}
	if(datetype == 'PRCVSQDATE'){
		$("#divprcvsqdate").hide();
		if(!($("#prcvsqdate").val() == "")){
			var today = new Date();
			var cyear = today.getFullYear();
			var cmonth = today.getMonth();
			var selectedDate = $('#prcvsqdate').datepicker('getDate');
			//first we check for future dates
			if(Date.parse(selectedDate) > Date.parse(today)){
				$("#spanprcvsqdate").html("PRC/VSQ Date must be in past");
				$("#divprcvsqdate").show();
			}
			//now we check to see if it falls in the date range
			if(!($("#continuousservice").val() == "") && !(isNaN($("#continuousservice").val()))){
				if($("#continuousservice").val() < 2 ){
					if(cmonth > 5 && cmonth < 12){
						//check current year
						var checkdate= new Date(cyear, 4, 1);
						if(Date.parse(selectedDate) < checkdate){
							$("#spanprcvsqdate").html("PRC/VSQ Date must be greater than " + checkdate.toLocaleDateString());
							$("#divprcvsqdate").show();
						}
					}else{
						//now we check back to the previous year
						var checkdate= new Date(cyear-1, 4, 1);
						if(Date.parse(selectedDate) < checkdate){
							$("#spanprcvsqdate").html("PRC/VSQ Date must be greater than " + checkdate.toLocaleDateString());
							$("#divprcvsqdate").show();
						}
					}
				}else{
					if(cmonth > 5 && cmonth < 12){
						//check current year
						var checkdate= new Date(cyear-1, 4, 1);
						if(Date.parse(selectedDate) < checkdate){
							$("#spanprcvsqdate").html("PRC/VSQ Date must be greater than " + checkdate.toLocaleDateString());
							$("#divprcvsqdate").show();
						}
					}else{
						//now we check back to the previous year
						var checkdate= new Date(cyear-2, 4, 1);
						if(Date.parse(selectedDate) < checkdate){
							$("#spanprcvsqdate").html("PRC/VSQ Date must be greater than " + checkdate.toLocaleDateString());
							$("#divprcvsqdate").show();
						}
					}
				}
			}
			
		}
	}
}
function checkdatefields(){

		if(!($("#rdate").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()+ 365);
			var selectedDate = $('#rdate').datepicker('getDate');
			if ((Date.parse(today) > Date.parse(selectedDate)) || (Date.parse(selectedDate) > Date.parse(targetDate))){
				$("#display_error_message_bottom").text("Registration Expiry Date must be in future and no more than 1 year")
				.css("display", "block").delay(4000).fadeOut();
				showBypassDialogV();
				return false;
			}
		}
	
		if(!($("#idate").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()+ 365);
			var selectedDate = $('#idate').datepicker('getDate');
			if ((Date.parse(today) > Date.parse(selectedDate)) || (Date.parse(selectedDate) > Date.parse(targetDate))){
				$("#display_error_message_bottom").text("Insurance Expiry Date must be in future and no more than 1 year")
				.css("display", "block").delay(4000).fadeOut();
				showBypassDialogV();
				return false;
			}
		}
	
		if(!($("#fidate").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()- 365);
			var selectedDate = $('#fidate').datepicker('getDate');
			if ((Date.parse(selectedDate) > Date.parse(today)) || (Date.parse(selectedDate) < Date.parse(targetDate))){
				$("#display_error_message_bottom").text("Fall Inspection Date must be in past and no more than 1 year ")
				.css("display", "block").delay(4000).fadeOut();
				showBypassDialogV();
				return false;
			}
		}
	
		if(!($("#widate").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()- 365);
			var selectedDate = $('#widate').datepicker('getDate');
			if ((Date.parse(selectedDate) > Date.parse(today)) || (Date.parse(selectedDate) < Date.parse(targetDate))){
				$("#display_error_message_bottom").text("Winter Inspection Date must be in past and no more than 1 year ")
				.css("display", "block").delay(4000).fadeOut();
				showBypassDialogV();
				return false;
			}
		}
	
		if(!($("#fheidate").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()- 365);
			var selectedDate = $('#fheidate').datepicker('getDate');
			if ((Date.parse(selectedDate) > Date.parse(today)) || (Date.parse(selectedDate) < Date.parse(targetDate))){
				$("#display_error_message_bottom").text("Fall H.E. Inspection Date must be in past and no more than 1 year ")
				.css("display", "block").delay(4000).fadeOut();
				showBypassDialogV();
				return false;
			}
		}
	
		if(!($("#mheidate1").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()- 365);
			var selectedDate = $('#mheidate1').datepicker('getDate');
			if ((Date.parse(selectedDate) > Date.parse(today)) || (Date.parse(selectedDate) < Date.parse(targetDate))){
				$("#display_error_message_bottom").text("Misc H.E. Inspection Date 1 must be in past and no more than 1 year ")
				.css("display", "block").delay(4000).fadeOut();
				showBypassDialogV();
				return false;
			}
		}
	
		if(!($("#mheidate2").val() == "")){
			var today = new Date();
			var targetDate= new Date();
			targetDate.setDate(today.getDate()- 365);
			var selectedDate = $('#mheidate2').datepicker('getDate');
			if ((Date.parse(selectedDate) > Date.parse(today)) || (Date.parse(selectedDate) < Date.parse(targetDate))){
				$("#display_error_message_bottom").text("Misc H.E. Inspection Date 2 must be in past and no more than 1 year ")
				.css("display", "block").delay(4000).fadeOut();
				showBypassDialogV();
				return false;
			}
		}
		return true;
		
	
}
function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode;
   if (charCode != 46 && charCode > 31 
     && (charCode < 48 || charCode > 57))
      return false;

   return true;
}
function showBypassDialog(){
	var options = {
			"backdrop" : "static",
			"show" : true
		};
		$('#spantitle2by').text("Some dates are invalid, do you still want to save employee information?");
		$('#buttonleftby').text("YES");
		$('#buttonrightby').text("NO");
		// now we add the onclick event
		$("#buttonleftby").click(function(event) {
			event.preventDefault();
			checkemployee('A','N');
			$('#myModal3').modal('hide');

		});
		$('#myModal3').modal(options);
}
function showBypassDialogV(){
	var options = {
			"backdrop" : "static",
			"show" : true
		};
		$('#spantitle2by').text("Some dates are invalid, do you still want to save vehicle information?");
		$('#buttonleftby').text("YES");
		$('#buttonrightby').text("NO");
		// now we add the onclick event
		$("#buttonleftby").click(function(event) {
			event.preventDefault();
			confirmVehicleFields('A','N');
			$('#myModal3').modal('hide');

		});
		$('#myModal3').modal(options);
}
/*******************************************************************************
 * Calls ajax post for deleting file
 ******************************************************************************/
function deleteFile(ftype,oid,fname) {
	$.ajax({
		url : 'deleteFileAjax.html',
		type : 'POST',
		data : {
			did : oid,
			dtype : ftype,
			filename: fname
		},
		success : function(xml) {
			$(xml).find('FILE').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#display_success_message_bottom").html(
							"File has been deleted").css("display", "block")
							.delay(6000).fadeOut();

							var surl = "adminViewVehicle.html?cid="+ oid;
							var surl="";
							if($(this).find("DTYPE").text() == "E"){
								var surl = "adminViewEmployee.html?vid="+ oid;
							}else{
								var surl =  "adminViewVehicle.html?cid="+ oid;
							}
							$("#pageContentBody").load(surl);
							$('#modalnewrun').modal('hide');

						} else {
							$("#display_error_message_bottom").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#modalnewrun').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#display_error_message_bottom").html(error).css("display",
			"block").delay(6000).fadeOut();
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Calls ajax post for searching contractors
 ******************************************************************************/
function getFileHistoryAjax(objectid,objecttype) {
	$("#fhtable").find("tr:gt(0)").remove();
	var isvalid = false;
	var cnt = 0;
	$
			.ajax({
				type : "POST",
				url : "getFileHistoryAjax.html",

				data : {
					cid : objectid,
					ftype : objecttype,
				},
				success : function(xml) {
					$(xml)
							.find('FILE')
							.each(
									function() {
										if ($(this).find("MESSAGE").text() == "SUCCESS") {

											var newrow = "<tr style='border-bottom:1px solid silver;'>";
											newrow += "<td class='field_content'>"
													+ $(this).find("FILEACTION")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find("ACTIONBY")
															.text() + "</td>";
											newrow += "<td class='field_content'>"
													+ $(this).find("ACTIONDATE")
															.text() + "</td>";
											newrow += "<td align='right' class='field_content'>";
											if($(this).find("FILENAME").text() == "No Previous File"){
												newrow += "No Previous File</TD>"
											}
											newrow += "<a href='" + $(this).find("FILEPATH").text() + $(this).find("FILENAME").text() +"' target='_blank' class='menuBCS'>VIEW</A></TD>";
											newrow += "</tr>";
											cnt = cnt + 1;
											$('table#fhtable tr:last').after(
													newrow);
											isvalid = true;

										} else {
											var newrow = "<tr style='border-bottom:1px dashed silver;'><td colspan='4'>";
											newrow += $(this).find("MESSAGE")
													.text();
											newrow += "</td></tr>";
											$('table#fhtable tr:last').after(
													newrow);

										}
									});
					var options = {
							"backdrop" : "static",
							"show" : true
						};
						$('#spantitlemainfh').text("View File History");

						$('#myModalFHistory').modal(options);
					
				},
				error : function(xhr, textStatus, error) {
					alert(xhr.statusText);
					alert(textStatus);
					alert(error);
				},
				dataType : "text",
			// async: false
			});
	


	return isvalid;
}
/*******************************************************************************
 * Calls ajax post for deleting file
 ******************************************************************************/
function deleteFileC(ftype,oid,fname) {
	$.ajax({
		url : 'contractorDeleteFileAjax.html',
		type : 'POST',
		data : {
			did : oid,
			dtype : ftype,
			filename: fname
		},
		success : function(xml) {
			$(xml).find('FILE').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#display_success_message_bottom").html(
							"File has been deleted").css("display", "block")
							.delay(6000).fadeOut();
							var surl="";
							if($(this).find("DTYPE").text() == "E"){
								var surl = "addNewEmployee.html?vid="+ oid;
							}else{
								var surl = "addNewVehicle.html?vid="+ oid;
							}	
							
							$("#pageContentBody").load(surl);
							$('#modalnewrun').modal('hide');

						} else {
							$("#display_error_message_bottom").html(
									$(this).find("MESSAGE").text()).css(
									"display", "block").delay(6000).fadeOut();
							$('#modalnewrun').modal('hide');
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#display_error_message_bottom").html(error).css("display",
			"block").delay(6000).fadeOut();
		},
		dataType : "text",
		async : false

	});

}
function adjustServiceTime(){
	var stime=0;
	var smonth = $("#vmonth").val();
	var syear  = $("#vyear").val();
	//check to make sure both have a value selected
	if(smonth > 0 && syear >0){
		var sdate = new Date(syear,smonth-1,1);
		var today = new Date();
		
		var days = (today - sdate) / (1000 * 60 * 60 * 24);
		var tdays = stime = days/365;
		
		stime = Math.round(tdays * 100) / 100;
	}
	$("#continuousservice").val(stime);
}