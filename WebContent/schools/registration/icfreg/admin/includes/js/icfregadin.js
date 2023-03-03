//global variables
//match email address
var emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$/; 
//match elements that could contain a phone number
var phoneNumber = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
/*******************************************************************************
 * Check new period fields
 ******************************************************************************/
function confirmRegistrationPeriodFields() {
	var schyear = $("#selschyear").val();
	var startdate = $("#dtstartdate").val();
	var enddate = $("#dtenddate").val();
	$("#msgerradd").hide();
	if (schyear == "NONE") {
		$("#msgerradd").html("Please select school year").css("display", "block");
		$("#msgerradd").show();
		return false;
	} else if (startdate == "") {
		$("#msgerradd").html("Please select start date").css("display", "block");
		$("#msgerradd").show();
		return false;
	} else if (enddate == "") {
		$("#msgerradd").html("Please select end date").css("display", "block");
		$("#msgerradd").show();
		return false;
	}else if (enddate < startdate) {
		$("#msgerradd").html("End date must be greater than start date").css("display", "block");
		$("#msgerradd").show();
		return false;
	
	}else {
		addNewRegistrationAjax(schyear,startdate,enddate);
		//$('#addPeriodModal').modal('hide');
		//$('.modal-backdrop').remove();
		//location.reload();
		
	}
}
/*******************************************************************************
 * Calls ajax post adding new registration period
 ******************************************************************************/
function addNewRegistrationAjax(schyr,sdate,edate) {
	$("#registrationPeriodsTable").find("tr:gt(0)").remove();
	$.ajax({
			type : "POST",
			url : "addNewRegistrationPeriodAjax.html",
			data : {
					schoolyear : schyr,
					startdate : sdate,
					enddate : edate,
				},
				success : function(xml) {
					var t = $('#registrationPeriodsTable').DataTable();
					t.clear();
					$(xml)
							.find('RPERIOD')
							.each(
									function() {
										if ($(this).find("MESSAGE").text() == "SUCCESS") {
											//update period table
											var reglink ="<a onclick=\"loadingData();\" class='btn btn-xs btn-primary' href=\"/MemberServices/schools/registration/icfreg/admin/viewPeriodRegistrants.html?irp=" 
												+ $(this).find("REGPERID").text() + "\">REGISTRANTS</a> ";
											var schoollink ="<a onclick=\"loadingData();\" class='btn btn-xs btn-info' href=\"/MemberServices/schools/registration/icfreg/admin/viewPeriodSchools.html?irp=" 
												+ $(this).find("REGPERID").text() + "\">SCHOOLS</a> ";
											var exportlink ="<a onclick=\"loadingData();\" class='btn btn-xs btn-warning' href=\"/MemberServices/schools/registration/icfreg/admin/exportRegistrants.html?irp=" 
												+ $(this).find("REGPERID").text() + "\">EXPORT</a>";
											
											var alllinks = reglink + schoollink + exportlink;
											t.row.add( [
												$(this).find("REGPERSCHOOLYEAR").text(),
												$(this).find("REGPERSTARTDATET").text(),
												$(this).find("REGPERENDDATET").text(),
												$(this).find("REGPERSTATUS").text(),
												$(this).find("REGPERCOUNT").text(),
												alllinks
												] ).draw();
											
										}
									});
					//show success message
					$('#addPeriodModal').modal('hide');
					$('.modal-backdrop').remove();					
					$(".msgok").html("<b>SUCCESS:</b> Period added/updated").css("display","block").delay(3000).fadeOut(2000);
						
					
				},
				error : function(xhr, textStatus, error) {
					$(".msgerr").html(error).css("display","block").delay(3000).fadeOut(2000);
				},
				dataType : "text",
			// async: false
			});
	


	
}
/*******************************************************************************
 * opens add/edit school dialog in correct mode
 ******************************************************************************/
function showAddEditSchoolDialog(dmode,pid,schoolname,scap,sid,schid){
	var options = {
			"backdrop" : "static",
			"show" : true
	};
	if(dmode == "A"){
		$('#apsTitle').html("Add Period School");
		$("#divselschool").show();
		$("#divshowschool").hide();
		$('#txtcap').val("0");
		$('#hidsid').val("-1");
		$('#hidtype').val(dmode);
	}else{
		$('#apsTitle').text("Edit Period School");
		$("#divselschool").hide();
		$("#divshowschool").show();
		$("#spanshowschool").html(schoolname.replace("*","'"));
		$('#txtcap').val(scap);
		$('#hidsid').val(sid);
		$('#hidtype').val(dmode);
	}
	$("#btnsave").unbind().click(function() {
		saveUpdateSchool(dmode,sid,pid,schid);
	});
	$("#msgerradd").hide();
	$('#addPeriodSchoolModal').modal(options);

}
/*******************************************************************************
 * submits ajax post for add/update
 ******************************************************************************/
function saveUpdateSchool(dmode,sid,pid,schid){
	$("#msgerradd").hide();
	//check to make sure cap is not null
	if($('#txtcap').val() == ""){
		$("#msgerradd").html("Please enter cap").css("display", "block");
		$("#msgerradd").show();
		return false;
	}else{
		//good to go
		var newdata="";
		var schoolid="";
		var pschoolid=-1;
		if(dmode == "A"){
			schoolid = $("#selschools").val();
		}else{
			schoolid=sid;
			//sid
			pschoolid=sid;
		}
		var t = $('#registrationPeriodSchoolsTable').DataTable();
		//t.clear();
		
		$('#registrationPeriodSchoolsTable').dataTable().fnClearTable(); 
		$.ajax({
				type : "POST",
				url : "addUpdateRegistrationSchoolAjax.html",
				data : {
						pcap : $("#txtcap").val(),
						pperiod : pid,
						pschool : schoolid,
						pmode: dmode,
						pschid: pschoolid
					},
					success : function(xml) {
						
						$(xml)
								.find('RSCHOOL')
								.each(
										function() {
											if ($(this).find("MESSAGE").text() == "SUCCESS") {
												//update period table
												var newlink ="<a onclick=\"loadingData();\" class='btn btn-xs btn-primary' href=\"/MemberServices/schools/registration/icfreg/admin/viewPeriodRegistrantsBySchool.html?irp=" + $(this).find("REGPER").text() + "&sid=" + $(this).find("SCHSCHOOLID").text() +"\">REGISTRANTS</a>";
												var newlink1="<a onclick='showAddEditSchoolDialog(\"E\",\"" + $(this).find("REGPER").text()   + "\",\"" + $(this).find("SCHSCHOOL").text().replace("'","*") + "\",\"" + $(this).find("SCHCAP").text() + "\",\"" +
												 $(this).find("SCHID").text() + "\",\"" + $(this).find("SCHSCHOOLID").text() + "\")' class='btn btn-xs btn-warning'>EDIT</a>";
												var newlink2="<a onclick='showDeleteSchoolDialog(\"" + $(this).find("REGPER").text()   + "\",\"" + $(this).find("SCHSCHOOL").text().replace("'","*") +"\",\"" + $(this).find("SCHCOUNT").text() + "\",\"" +
												 $(this).find("SCHSCHOOLID").text() + "\")' class='btn btn-xs btn-danger'>DELETE</a>";
												
												var newlinks=newlink + "&nbsp;" + newlink1 + "&nbsp; " + newlink2;
												t.row.add( [
													$(this).find("SCHSCHOOL").text(),
													$(this).find("SCHCAP").text(),
													$(this).find("SCHCOUNT").text(),
													newlinks
													] ).draw();
												
												
											}
										});
						//show success message
						$('#addPeriodSchoolModal').modal('hide');
						$('.modal-backdrop').remove();
						$('.msgok').html("SUCCESS: School added/updated").css("display","block").delay(3000).fadeOut(2000);
						
						
					},
					error : function(xhr, textStatus, error) {
						$(".msgerr").html(error).css("display","block").delay(3000).fadeOut(2000);
					},
					dataType : "text",
				// async: false
				});
	}
	
}
function refreshTable(){
	
	mTable = $("#registrationPeriodSchoolsTable").dataTable({
		"order" : [[0,"asc"]],		
		"bPaginate": false,
		responsive: true,
		dom: 'Bfrtip',
        buttons: [			        	
        	//'colvis',
        	{
            extend: 'print',
            title: '<div align="center"><img src="/MemberServices/schools/registration/icfreg/admin/includes/img/nlesd-colorlogo.png" style="max-width:600px;"/></div>',
            messageTop: '<div align="center" style="font-size:18pt;">${sy} ICF Registration Periods</div>',           
            	 exportOptions: {
                     columns: [ 0,1,2 ],
                 }
        },
        {
            extend: 'csv',
            exportOptions: {
                columns: [ 0,1,2 ],
            }
        },
        {
            extend: 'excel',
            exportOptions: {
                columns: [ 0,1,2 ],
            }
        },    	
        ],				
		 "columnDefs": [
			 {
				 title: 'SCHOOL NAME',
				 targets: 0
			 },
			 {
				 title: 'REGISTRATION CAP',
				 targets: 1
			 },
			 {
				 title: 'REGISTRANTS COUNT',
				 targets: 2
			 },
			 {
				 title: 'OPTIONS',
				 targets: 3,
				 "orderable": false
			 }
	        ]
	});	
}
/*******************************************************************************
 * opens delete applicant confirmation
 ******************************************************************************/
function showDeleteSchoolDialog(rperiod,schoolname,schoolcount,schoolid){
	var options = {
			"backdrop" : "static",
			"show" : true
	};
	$('#aps1Title').html("Delete Period School");
	$("#schoolname").html(schoolname);
	$("#spanmessage").html("Are you sure you want to delete this school and the  " + schoolcount + " registrant(s)?");
	$("#btndelete").unbind().click(function() {
		deletePeriodSchool(rperiod,schoolname,schoolcount,schoolid);
	});
	$('#deletePeriodSchoolModal').modal(options);
}
//deletePeriodSchool.html
/*******************************************************************************
 * submits ajax post for add/update
 ******************************************************************************/
function deletePeriodSchool(rperiod,schoolname,schoolcount,schoolid){
	var t = $('#registrationPeriodSchoolsTable').DataTable();
	//t.clear();
	
	$('#registrationPeriodSchoolsTable').dataTable().fnClearTable(); 
	
	$.ajax({
				type : "POST",
				url : "deletePeriodSchool.html",
				data : {
						pid : rperiod,
						sid : schoolid
					},
					success : function(xml) {
						
						$(xml)
								.find('RSCHOOL')
								.each(
										function() {
											if ($(this).find("MESSAGE").text() == "SUCCESS") {
												//update period table
												var newlink ="<a onclick=\"loadingData();\" class='btn btn-xs btn-primary' href=\"/MemberServices/schools/registration/icfreg/admin/viewPeriodRegistrantsBySchool.html?irp=" + $(this).find("REGPER").text() + "&sid=" + $(this).find("SCHSCHOOLID").text() +"\">REGISTRANTS</a>";
												
												var newlink1="<a onclick='showAddEditSchoolDialog(\"E\",\"" + $(this).find("REGPER").text()   + "\",\"" + $(this).find("SCHSCHOOL").text().replace("'","*") +
												 "\",\"" + $(this).find("SCHCAP").text() + "\",\"" +
												 $(this).find("SCHID").text() + "\",\"" + $(this).find("SCHSCHOOLID").text() + "\")' class='btn btn-xs btn-warning'>EDIT</a>";
												var newlink2="<a onclick='showDeleteSchoolDialog(\"" + $(this).find("REGPER").text()   + "\",\"" + $(this).find("SCHSCHOOL").text().replace("'","*") +"\",\"" + $(this).find("SCHCOUNT").text() + "\",\"" +
												 $(this).find("SCHSCHOOLID").text() + "\")' class='btn btn-xs btn-danger'>DELETE</a>";
												
												
												
												var newlinks=newlink + "&nbsp;" + newlink1 + "&nbsp; " + newlink2;
												t.row.add( [
													$(this).find("SCHSCHOOL").text(),
													$(this).find("SCHCAP").text(),
													$(this).find("SCHCOUNT").text(),
													newlinks
													] ).draw();
												
												
											}
										});
						//show success message
						$('#deletePeriodSchoolModal').modal('hide');
						$('.modal-backdrop').remove();						
						$(".msgok").html("<b>SUCCESS:</b> School deleted.").css("display","block").delay(3000).fadeOut(2000);
						
					},
					error : function(xhr, textStatus, error) {
						$(".msgerr").html(error).css("display","block").delay(3000).fadeOut(2000);
					},
					dataType : "text",
				// async: false
				});
	
	
}
/*******************************************************************************
 * validate form fields
 ******************************************************************************/
function validateRegistrantForm() {
	//now we check the fields
	$("#pnl-error-msg").hide();
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
		errorstring += "<li>Telephone Number</span>";
	}
	if($('#txt_ContactNumber1').val() != '' && !phoneNumber.test($('#txt_ContactNumber1').val())){
		errorstring += "<li>Telephone Number in invalid format";
	}
	if($('#txt_ContactNumber2').val() != '' && !phoneNumber.test($('#txt_ContactNumber2').val())){
		errorstring += "<li>Optional Contact Number in invalid format";
	}
	if($("#selSchool").val() == "-1"){
		errorstring += "<li>School";
	}
	if(errorstring != ""){
		$("#error-msg").html(errorstring);
		$("#pnl-error-msg").show();
		$(".msgerr").css("display", "block").html("<b>ERROR:</b> Error(s) found in form submission. Please check the form and try again.").delay(3000).fadeOut(2000);
		return false;
	}else{
		adminUpdateRegistratAjax();
		return true;
	}
	
}
//
/*******************************************************************************
 * Calls ajax post for admin applicant update
 ******************************************************************************/
function adminUpdateRegistratAjax() {
	var test = $("#selSchool option:selected").text();
	$.ajax({
			type : "POST",
			url : "adminUpdateRegistrant.html",
			data : {
					txt_StudentName : $("#txt_StudentName").val(),
					txt_GuardianName : $("#txt_GuardianName").val(),
					txt_ParentGuardianEmail : $("#txt_ParentGuardianEmail").val(),
					txt_ContactNumber1: $("#txt_ContactNumber1").val(),
					txt_ContactNumber2: $("#txt_ContactNumber2").val(),
					selSchool: $("#selSchool").val(),
					registration_id: $("#testingid").val(),
				},
				success : function(xml) {
					$(xml)
							.find('RSCHOOL')
							.each(
									function() {
										if ($(this).find("MESSAGE").text() == "SUCCESS") {
											$(".msgok").html("<b>SUCCESS:</b> Registrant updated").css("display","block").delay(3000).fadeOut(2000);
											$("#spanschoolname").html(test);
											$("#spanfullname").html($("#txt_StudentName").val());
											
										}
									});
					
					
						
					
				},
				error : function(xhr, textStatus, error) {
					$(".msgerr").html(error).css("display","block").delay(3000).fadeOut(2000);
				},
				dataType : "text",
			// async: false
			});
}
/*******************************************************************************
 * Calls ajax post for status change
 ******************************************************************************/
function adminUpdateRegistrantAjax(status) {
	$.ajax({
			type : "POST",
			url : "updateRegistrantStatus.html",
			data : {
					rid : $("#hidrid").val(),
					sid : status,
				},
				success : function(xml) {
					$(xml)
							.find('RSCHOOL')
							.each(
									function() {
										if ($(this).find("MESSAGE").text() == "SUCCESS") {
											$(".msgok").html("SUCCESS: Registrant status updated").css("display","block").delay(3000).fadeOut(2000);
											//now update the status on screen
											//NEWSTATUS
											$("#spanstatus").html($(this).find("NEWSTATUS").text());
											if(status == 1){
												$("#butApprove").show();
												$("#butNotApprove").show();
												$("#butWait").show();
											}else if(status == 2){
												$("#butApprove").hide();
												$("#butNotApprove").show();
												$("#butWait").show();
											}else if(status == 3){
												$("#butApprove").show();
												$("#butNotApprove").hide();
												$("#butWait").show();
											}else if(status == 4){
												$("#butApprove").show();
												$("#butNotApprove").show();
												$("#butWait").hide();
											}
											//hide/unhide buttons
											//$("#spanschoolname").html(test);
											//$("#spanfullname").html($("#txt_StudentName").val());
											
										}
									});
					
					
						
					
				},
				error : function(xhr, textStatus, error) {
					$(".msgerr").html(error).css("display","block").delay(3000).fadeOut(2000);
				},
				dataType : "text",
			// async: false
			});
}
/*******************************************************************************
 * Calls ajax post for status change
 ******************************************************************************/
function resendemail(rid) {
	$.ajax({
			type : "POST",
			url : "resendEmail.html",
			data : {
					rid : rid,
					emaila : $("#hidemail").val(),
					emailt : $("#selemail").val(),
				},
				success : function(xml) {
					$(xml)
							.find('RSCHOOL')
							.each(
									function() {
										if ($(this).find("MESSAGE").text() == "SUCCESS") {
											$(".msgok").html("SUCCESS: Email resent to registrant").css("display","block").delay(3000).fadeOut(2000);
										}
									});
					
					
						
					
				},
				error : function(xhr, textStatus, error) {
					$(".msgerr").html(error).css("display","block").delay(3000).fadeOut(2000);
				},
				dataType : "text",
			// async: false
			});
}
/*******************************************************************************
 * opens delete applicant confirmation
 ******************************************************************************/
function openDeleteConfirm(rid,rname,ftype){
	var options = {
			"backdrop" : "static",
			"show" : true
	};
	$('#apsTitle').html("Delete Registrant");
	$("#studentname").html(rname);
	$("#spanmessage").html("Are you sure you want to delete " + rname +" ?");
	$("#btndeleteregistrant").unbind().click(function() {
		deleteRegistrant(rid,ftype);
	});
	$('#deleteRegistrantModal').modal(options);
}

/*******************************************************************************
 * Calls ajax post for deleting registrant
 ******************************************************************************/
function deleteRegistrant(aid,formtype) {
	var t = $('#registrationTable').DataTable();
	var schid="-1";
	if(formtype == "S"){
		schid =$("#hidsid").val();
	}
	$('#registrationTable').dataTable().fnClearTable(); 
	var counter = 0;
	$.ajax({
				type : "POST",
				url : "deleteRegistrant.html",
				data : {
						rid : aid,
						ftype : formtype,
						sid: schid,
					},
					success : function(xml) {
						
						$(xml)
								.find('RSCHOOL')
								.each(
										function() {
											counter = counter +1;
											if ($(this).find("MESSAGE").text() == "SUCCESS") {
												//update period table
												var newlink = "<a onclick=\"loadingData();\" class='btn btn-xs btn-primary' href='/schools/registration/icfreg/admin/viewRegistrant.html?irp=" + $(this).find("APPID").text() + "&vtype=V'>VIEW</a>";
												var newlink1 = "<a onclick=\"loadingData();\" class='btn btn-xs btn-warning' href='/schools/registration/icfreg/admin/viewRegistrant.html?irp=" + $(this).find("APPID").text() + "&vtype=A'>EDIT</a>";
												var newlink2 = "<a class='btn btn-xs btn-danger opbutton-delete' onclick='openDeleteConfirm(\"" + $(this).find("APPID").text() +"\",\"" + $(this).find("APPFULLNAME").text() + "\",\"" + formtype + "\");'>DEL</a>";	
												var newlinks=newlink + "&nbsp;" + newlink1 + "&nbsp; " + newlink2;
												if(formtype == "S"){
													t.row.add( [
														$(this).find("APPDATESUBMITTED").text(),
														$(this).find("APPDATESUBMITTEDT").text(),
														$(this).find("APPFULLNAME").text(),
														$(this).find("APPCONTACT1").text(),
														$(this).find("APPEMAIL").text(),
														$(this).find("APPSTATUSTEXT").text(),
														newlinks
														] ).draw();
												}else{
													t.row.add( [
														$(this).find("APPDATESUBMITTED").text(),
														$(this).find("APPDATESUBMITTEDT").text(),
														$(this).find("APPFULLNAME").text(),
														$(this).find("APPSCHOOLNAME").text(),
														$(this).find("APPCONTACT1").text(),
														$(this).find("APPEMAIL").text(),
														$(this).find("APPSTATUSTEXT").text(),
														newlinks
														] ).draw();
												}
												}
										});
						$("#spancount").html(counter);
						//show success message
						$('#deleteRegistrantModal').modal('hide');
						$('.modal-backdrop').remove();
						//$('.msgok').html("School added/updated");	
						//$('.msgok').show();
						$(".msgok").html("SUCCESS: Registrant deleted").css("display","block").delay(3000).fadeOut(2000);
						
					},
					error : function(xhr, textStatus, error) {
						$(".msgerr").html(error).css("display","block").delay(3000).fadeOut(2000);
					},
					dataType : "text",
				// async: false
				});
	
}
//
/*******************************************************************************
 * validate form fields
 ******************************************************************************/
function validateRegistrantFormAdd() {
	//now we check the fields
	
	$("#pnl-error-msg").hide();
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
		$("#pnl-error-msg").show();
		$(".msgerr").css("display", "block").html("<b>ERROR:</b> Error(s) found in form submission. Please check the form and try again.").delay(3000).fadeOut(2000);
		return false;
	}else{
		adminAddRegistratAjax();
		return true;
	}
	
}
/*******************************************************************************
 * Calls ajax post for admin applicant add
 ******************************************************************************/
function adminAddRegistratAjax() {
	var test = $("#selSchool option:selected").text();
	$.ajax({
			type : "POST",
			url : "adminAddNewRegistrantAjax.html",
			data : {
					txt_StudentName : $("#txt_StudentName").val(),
					txt_GuardianName : $("#txt_GuardianName").val(),
					txt_ParentGuardianEmail : $("#txt_ParentGuardianEmail").val(),
					txt_ContactNumber1: $("#txt_ContactNumber1").val(),
					txt_ContactNumber2: $("#txt_ContactNumber2").val(),
					selSchool: $("#selSchool").val(),
					pid: $("#regbeanid").val(),
				},
				success : function(xml) {
					$(xml)
							.find('RSCHOOL')
							.each(
									function() {
										if ($(this).find("MESSAGE").text() == "SUCCESS") {
											$(".msgok").html("SUCCESS: Registrant added").css("display","block").delay(3000).fadeOut(2000);
											
											
										}
									});
					
					
						
					
				},
				error : function(xhr, textStatus, error) {
					$(".msgerr").html(error).css("display","block").delay(3000).fadeOut(2000);
				},
				dataType : "text",
			// async: false
			});
}
