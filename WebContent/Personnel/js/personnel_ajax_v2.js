/*
 * ----- Teacher Recommendation Form -----
 */

function parseCandidateSelection(data) {
	//formatting applicant address
	var addr_str = null;
	if($(data).find("ADDRESS1").length > 0){
	  addr_str = $(data).find("ADDRESS1").text();
	}
	if($(data).find("ADDRESS2").length > 0) {
	  addr_str = addr_str + "<BR>" + $(data).find("ADDRESS2").text();
	}
	addr_str = addr_str + "<BR>" + $(data).find("PROVINCE").text();
	addr_str = addr_str + ", " + $(data).find("COUNTRY").text();
	addr_str = addr_str + ", " + $(data).find("POSTAL-CODE").text();
	
	$('#candidate_address').html(addr_str);
	 
	//formatting phone numbers
	var phone_str = null;
	if($(data).find("HOME-PHONE").length > 0){
	  phone_str = $(data).find("HOME-PHONE").text() + " (home)";
	}
	if($(data).find("WORK-PHONE").length > 0) {
	  if(phone_str) {
	    phone_str = phone_str + "<BR>" + $(data).find("WORK-PHONE").text() + " (work)";
	  }
	  else {
	    phone_str = $(data).find("WORK-PHONE").text() + " (work)";
	  }
	}
	if($(data).find("CELL-PHONE").length > 0){
	  if(phone_str){
	    phone_str = phone_str + "<BR>" + $(data).find("CELL-PHONE").text() + " (cell)";
	  }
	  else{
	    phone_str = $(data).find("CELL-PHONE").text() + " (cell)";
	  }
	}
	if(!phone_str) {
	  phone_str = 'NONE ON RECORD';
	}
	
	$('#candidate_telephone').html(phone_str);
	
	/*
	var sin2 = document.getElementById('sin2_row');
	if(xmlDoc.getElementsByTagName("SIN2").length > 0)
		sin2.innerHTML = "<input type='hidden' id='sin2' name='sin2' value='"
		  +xmlDoc.getElementsByTagName("SIN2")[0].childNodes[0].nodeValue + "' />" 
		  //+ "XXX-XXX-XXX";
			+ xmlDoc.getElementsByTagName("SIN2")[0].childNodes[0].nodeValue;
	else if((xmlDoc.getElementsByTagName("SIN").length > 0) && (xmlDoc.getElementsByTagName("SIN")[0].childNodes[0].nodeValue.length < 12))
		sin2.innerHTML = "<input type='hidden' id='sin2' name='sin2' value='"
		  +xmlDoc.getElementsByTagName("SIN")[0].childNodes[0].nodeValue + "' />"
		  //+ "XXX-XXX-XXX"; 
			+ xmlDoc.getElementsByTagName("SIN")[0].childNodes[0].nodeValue;
	else
		sin2.innerHTML = "<input type='text' id='sin2' name='sin2' class='requiredInputBox' />";
	//sin2.innerHTML = "XXX-XXX-XXX";
	*/
	/*
	var dob = document.getElementById('dob_row');
	if(xmlDoc.getElementsByTagName("DOB").length > 0)
		dob.innerHTML = "<input type='hidden' id='dob' name='dob' value='"
		  + xmlDoc.getElementsByTagName("DOB")[0].childNodes[0].nodeValue + "' />" 
			+ xmlDoc.getElementsByTagName("DOB")[0].childNodes[0].nodeValue;
	else
		dob.innerHTML = "<input type='text' id='dob' name='dob' class='requiredInputBox' />";
	*/
	
	//formatting candidate info
	if($(data).find("PERM-SCHOOL").length > 0){
	    var sch_str = null;
	    if($(data).find("PERM-SCHOOL").length > 0){
	      sch_str = $(data).find("PERM-SCHOOL").text();
	      sch_str = "YES<BR>" + sch_str + "<BR>" + $(data).find("PERM-POSITION").text();
	    }
	    else if($(data).find("REPLACEMENT-CONTRACT-SCHOOL").length > 0){
	      sch_str = $(data).find("REPLACEMENT-CONTRACT-SCHOOL").text();
	      sch_str = "REPLACEMENT<BR>" + sch_str + "<BR> (REPLACEMENT ENDDATE: " + $(data).find("REPLACEMENT-CONTRACT-ENDDATE").text() + ")";
	    }
	    else {
	      sch_str = 'NO';
	    }
	    
	    $('#perm_status').html(sch_str);
	}
	else {
		$('#perm_status').html('NO');
	}
	
	if($(data).find("TRAINING-METHOD").length > 0) {
	  $('#trn_mtd').html($(data).find("TRAINING-METHOD").text());
	}
	
	if($(data).find("TEACHING-CERTIFICATE-LEVEL").length > 0) {
	  $('#cert_lvl').html($(data).find("TEACHING-CERTIFICATE-LEVEL").text());
	}
	  
	//formatting reference check requests
	parseCurrentReferenceCheckRequestsResponse(data);
	
	//formatting references
	parseCurrentReferencesResponse(data);
	
	if($(data).find("REFERENCE-CHECK-REQUEST").length <= 0 && $(data).find("REFERENCE").length <= 0){
		toggleRequestReferenceCheck(true);
	}
	else {
		toggleRequestReferenceCheck(false);
	}
	
	//formatting interview summaries
	parseCurrentInterviewSummariesResponse(data);
	
	$('#candidate_loading_msg').hide();
	$('#candidate_info').show();
	$('#btn-refresh-candidate-info').show();
}

function parseCurrentReferencesResponse(data) {
	//formatting reference check request beans
	var ref_chk_str = "<span style='color:#FF0000;'>No references on record.</span>";
	
	if($(data).find("REFERENCE").length > 0) {	
		ref_chk_str = "<table align='left' cellpadding='3' cellspacing='3' border='0' style='border:solid 1px #d4d4d4;'>";
		
		ref_chk_str += "<tr><th>Ref. Date</th><th>Ref. Type</th><th>Provided By</th><th>Position</th><th>Select</th><th>View</th></tr>";
		$(data).find("REFERENCE").each(function(){
		
			//reference date
			ref_chk_str += "<tr><td>" + $(this).find('REFERENCE-DATE').text() + "</td>";
			
			//reference type
			if($(this).find('REFERENCE-TYPE').length > 0)
				ref_chk_str += "<td>" + $(this).find('REFERENCE-TYPE').text() + "</td>";
			else 
				ref_chk_str += "<td><SPAN style='color:#FF0000;'>UNKNOWN</SPAN></td>";
			
			//provided by
			if($(this).find('PROVIDED-BY').length > 0)
				ref_chk_str += "<td>" + $(this).find('PROVIDED-BY').text() + "</td>";
			else 
				ref_chk_str += "<td><SPAN style='color:#FF0000;'>UNKNOWN</SPAN></td>";
			
			//provided by position
			if($(this).find('PROVIDED-BY-POSITION').length > 0)
				ref_chk_str += "<td>" + $(this).find('PROVIDED-BY-POSITION').text() + "</td>";
			else
				ref_chk_str += "<td><SPAN style='color:#FF0000;'>PHONE</SPAN></td>";
			
			//select reference
			ref_chk_str += "<td align='center'><input class='reference-select' type='radio' value='" + $(this).find('REFERENCE-ID').text() + "' name='reference_id' /></td>";
			
			//view reference
			//retrieve the type
			//viewNLESDAdminReference.html

			ref_chk_str += "<td align='center'><a class='btn-action' target='_blank' href='" + $(this).find('VIEW-URL').text() + "'>view</a></td></tr>";
		
		});
		
		ref_chk_str += "</table>";
	}
	
	$('#current_refs').html(ref_chk_str);
	$('#current_refs table tr th').addClass('displayHeaderTitle').css({'border' : 'solid 1px #d4d4d4'});
	$('#current_refs table tr td').addClass('displayText').css({'border' : 'solid 1px #d4d4d4', 'padding-left' : '3px'});
	$('#current_refs table tr td a.btn-action').css({'color' : '#FF0000', 'font-weight': 'bold', 'text-decoration' : 'none'});
	$('#current_refs table tr:odd td').css({'background-color': '#f0f0f0'});
	
	$('form input:radio').click(function(){
		onReferenceAndInterviewSummarySelected();
	});
}

function parseCurrentReferenceCheckRequestsResponse(data) {  
    //formatting reference check request beans
    var ref_chk_str = "<span style='color:#FF0000;'>No requests sent.</span>";

    if($(data).find("REFERENCE-CHECK-REQUEST").length > 0) {	
    	ref_chk_str = "<table align='left' cellpadding='3' cellspacing='3' border='0' style='border:solid 1px #d4d4d4;'>";
    	ref_chk_str +=  "<tr><th>Request Date</th><th>Requested By</th><th>Referrer Email</th><th>Status</th></tr>";
    	$(data).find("REFERENCE-CHECK-REQUEST").each(function() {
    		//request date
    		ref_chk_str = ref_chk_str + "<tr><td>" + $(this).find('REQUEST-DATE').text() + "</td>";
    		
    		//requested by
    		if($(this).find('REQUESTER-NAME').length > 0){
    			ref_chk_str += "<td>" + $(this).find('REQUESTER-NAME').text() + "</td>";
    		}
    		else { 
    			ref_chk_str += "<td><SPAN style='color:#FF0000;'>UNKNOWN</SPAN></td>";
    		}
    		
    		//referrer email
    		if($(this).find('REFERRER-EMAIL').text() != 'null') {
    			ref_chk_str += "<td>" + $(this).find('REFERRER-EMAIL').text() + "</td>";
    		}
    		else {
    			ref_chk_str += "<td>BY PHONE</td>";
    		}
    		
    		//status
    		ref_chk_str += "<td><SPAN style='color:#FF0000;'>NOT COMPLETE</SPAN>"
    				+ " | <span style='font-size:8pt;'><a class='btn-action' href='#' onclick='onDeleteRefCheck(" + $(this).find('REQUEST-ID').text() + ");return false;'>[delete]</a></span></td></tr>";
    	});
    	
    	ref_chk_str += "</table>";
    }
    
    $('#current_ref_requests').html(ref_chk_str);
	$('#current_ref_requests table tr th').addClass('displayHeaderTitle').css({'border' : 'solid 1px #d4d4d4'});
	$('#current_ref_requests table tr td').addClass('displayText').css({'border' : 'solid 1px #d4d4d4', 'padding-left' : '3px'});
	$('#current_ref_requests table tr td a.btn-action').css({'color' : '#FF0000', 'font-weight': 'bold', 'text-decoration' : 'none'});
	$('#current_ref_requests table tr:odd td').css({'background-color': '#f0f0f0'});
}

function parseCurrentInterviewSummariesResponse(data) {
	//formatting interview summary beans
	var summary_str = "<span style='color:#FF0000;'>No interview summary on record.</span>";
	
	if($(data).find("INTERVIEW-SUMMARY").length > 0) {	
		summary_str = "<table align='left' cellpadding='3' cellspacing='3' border='0' style='border:solid 1px #d4d4d4;'>";
		
		summary_str += "<tr><th>Date</th><th>Competition</th><th>Position</th><th>Recommendation</th><th>Select</th><th>View</th></tr>";
		$(data).find("INTERVIEW-SUMMARY").each(function(){
		
			//created date
			summary_str += "<tr><td>" + $(this).attr('created') + "</td>";
			
			//competition
			summary_str += "<td>" + $(this).attr('competitionNumber') + "</td>";
			
			//position
			summary_str += "<td>" + $(this).attr('position') + "</td>";
			
			//recommendation
			summary_str += "<td>" + $(this).attr('recommendation') + "</td>";
			
			//select reference
			summary_str += "<td align='center'><input class='interview-summary-select' type='radio' value='" + $(this).attr('interviewSummaryId') + "' name='interview_summary_id' /></td>";
			
			//view interview summary
			summary_str += "<td align='center'><a class='btn-action' target='_blank' href='viewInterviewSummary.html?id=" + $(this).attr('interviewSummaryId') + "'>view</a></td></tr>";
		});
		
		summary_str += "</table>";
	}
	
	$('#current_interview_summaries').html(summary_str);
	$('#current_interview_summaries table tr th').addClass('displayHeaderTitle').css({'border' : 'solid 1px #d4d4d4'});
	$('#current_interview_summaries table tr td').addClass('displayText').css({'border' : 'solid 1px #d4d4d4', 'padding-left' : '3px'});
	$('#current_interview_summaries table tr td a.btn-action').css({'color' : '#FF0000', 'font-weight': 'bold', 'text-decoration' : 'none'});
	$('#current_interview_summaries table tr:odd td').css({'background-color': '#f0f0f0'});
	
	$('form input:radio').click(function(){
		onReferenceAndInterviewSummarySelected();
	});
}

function onCandidateSelected(sin) {
  if(sin == -1) {
	  $('#candidate_info').hide();
	  $('#candidate-recommendation-info').hide();
	  $('#btn-refresh-candidate-info').hide();
	  
	  return;
  }
  
  $('#candidate_info').hide();
  $('#candidate-recommendation-info').hide();
  $('#btn-refresh-candidate-info').hide();
  $('#candidate_loading_msg').show();
  
  var data = {};
  data.op = 'CANDIDATE_DETAILS';
  data.sin = sin;
  var name=$("#candidate_name option:selected").text();
 
  
  $('#candidate_name_s').html(name);
  
  
  $.post('addJobTeacherRecommendation.html', data, function(xml){
	  parseCandidateSelection(xml);
  });
}

function onReferenceAndInterviewSummarySelected(){
	if($('input.interview-summary-select:checked').length > 0 && $('input.reference-select:checked').length > 0){
		$('#candidate-recommendation-info').show();
	}
	else {
		$('#candidate-recommendation-info').hide();
	}
}

function onSendReferenceCheckRequest() {
	var uid = $('#candidate_name').val();
	var email = $('#referrer_email').val();
	var reftype = $('#reftype').val();
	
	if((uid == -1)||(email == "") || (reftype == "-1" )) {
		return;
	}
  
	$('#sending_email_msg').show();
	$('#referrer_email').val("");
	
	var data = {};
	data.uid = uid;
	data.email = email;
	data.reftype=reftype;
	
	
	
	$.get('sendReferenceCheckRequest.html', data, function(xml){
		parseSendReferenceCheckRequestResponse(xml);
	}, 'xml');
}

function parseSendReferenceCheckRequestResponse(xml) {
    parseCurrentReferenceCheckRequestsResponse(xml);
    
    //formatting send response
    if($(xml).find("RESPONSE-MSG").length > 0){
    	$('#request_response_msg').html($(xml).find("RESPONSE-MSG").text());
    }
    else {
    	$('#request_response_msg').html("<SPAN style='color:#FF0000;'>Could not send request.</SPAN>");
    }
    
    $('#sending_email_msg').hide();
    $('#request_response_row').show();
}

function onManualReferenceCheckRequest() {
  var uid = $('#candidate_name').val();
  var reftype = $('#reftype').val();
	
  if((uid == -1) ||(reftype == "-1" )) {
	  if(reftype=="-1")
	  {
	  	alert("Please select Reference Type");
	  	return;
	  }else{
		  return;
	  }
  }
  
  openWindow('MANUAL_REFERENCE_CHECK',"manualReferenceCheckRequest.html?uid="+uid + "&reftype=" + reftype, 850, 600, 1); 
}

function onDeleteRefCheck(id) {
	if((id == null) || (id < 1)) {
		return;
	}
  
	if(confirm('Are you sure you want to delete this reference check request?')) {
		var data = {};
		data.id = id;
		
		$.get('deleteTeacherReferenceCheckRequest.html', data, function(xml){
			parseCurrentReferenceCheckRequestsResponse(xml);
		}, 'xml');
	}
}

function toggleRequestReferenceCheck(open) {
	if(open === true) {
		$('#req_ref_chk_img').attr('src', 'images/collapse2.jpg');
		$('#request_reference_info').show();
	}
	else if(open === false) {
		$('#req_ref_chk_img').attr('src', 'images/expand2.jpg');
		$('#request_reference_info').hide();
		
		$('#referrer_email').val('');
		$('#request_response_msg').html('');
	}
	else {
		if($('#req_ref_chk_img').attr('src').indexOf('images/expand2.jpg') >= 0) {
			$('#req_ref_chk_img').attr('src', 'images/collapse2.jpg');
			$('#request_reference_info').show();
		}
		else {
			$('#req_ref_chk_img').attr('src', 'images/expand2.jpg');
			$('#request_reference_info').hide();
			$('#referrer_email').val('');
			$('#request_response_msg').html('');
		}
	}
}

function parseAddGSUResponse(xml) {
    //formatting gsu beans
    var gsu_str = "<span color='#FF0000'>None Added.</span>";
    
    if($(xml).find("GSU-BEAN").length > 0) {
    	gsu_str = "<table width='80%' align='center' cellpadding='3' cellspacing='3' border='0' style='border:solid 1px #d4d4d4;'>";
    	
    	gsu_str += "<tr><th>Grade</th><th>Subject</th><th>Unit %</th></tr>";
    	$(xml).find("GSU-BEAN").each(function(){
    		//grade
    		gsu_str += "<tr><td>" + $(this).find("GRADE").text() + "</td>";
    		
    		//subject
    		gsu_str += "<td>" + $(this).find("SUBJECT").text() + "</td>";
    		
    		//percent
    		gsu_str += "<td>" + $(this).find("PERCENT-UNIT").text() + "%</td></tr>";
    	});
    	
    	gsu_str += "<tr><td colspan='3' align='center'><input type='button' style='font-size:10px;' value='Clear' onclick='clearGSU();' /></td></tr>";
    	
    	gsu_str += "</table>";
    } 
	
    $('#gsu_display').html(gsu_str);
	$('#gsu_display table tr th').addClass('displayHeaderTitle').css({'border' : 'solid 1px #d4d4d4'});
	$('#gsu_display table tr td').addClass('displayText').css({'border' : 'solid 1px #d4d4d4', 'padding-left' : '3px'});
	$('#gsu_display table tr td a.btn-action').css({'color' : '#FF0000', 'font-weight': 'bold', 'text-decoration' : 'none'});
	$('#gsu_display table tr:odd td').css({'background-color': '#f0f0f0'});
}

function addGSU() {
	var g = $('#gsu_grade').val();
	var s = $('#gsu_subject').val();
	var u = $('#gsu_percent').val();

    if((g != "-1") && (u != "")){
    	var data = {};
    	data.g = g;
    	data.s = s;
    	data.u = u;
    
    	$.get('addJobTeacherRecommendationGSU.html', data, function(xml){
    		parseAddGSUResponse(xml);
    	}, 'xml');
    }
    else{
    	$.get('addJobTeacherRecommendationGSU.html', function(xml){
    		parseAddGSUResponse(xml);
    	}, 'xml');
    }
}

function refreshGSU(){
	addGSU();
}

function clearGSU() {
  $.get('clearJobTeacherRecommendationsGSU.html', function(xml){
		parseAddGSUResponse(xml);
	}, 'xml');
}

function onSendApplicantLoginInfoEmail(uid) {
	if(confirm('Send login info email?')) {
		if((uid == null) || (uid < 1)) {
			return;
		}
		
		$('#email_pwd_status').html("<SPAN style='color:#FF0000;'>Sending...</SPAN>");
	  
		var data = {};
		data.uid = uid;
		
		$.get('sendApplicantLoginInfoEmail.html', data, function(xml){
			parseSendLoginInfoEmailResponse(xml);
		}, 'xml');
	}
}

function parseSendLoginInfoEmailResponse(data) {
	if($(data).find("RESPONSE-MSG").length > 0) {
		$("#email_pwd_status").html("<SPAN style='color:#FF0000;'>" + $(data).find("RESPONSE-MSG").text() + "</SPAN>");
	}
	else {
		$("#email_pwd_status").html("<SPAN style='color:#FF0000;'>Could not send email.</SPAN>");
	}
}

function onSendAllApplicantLoginInfoEmail() {
  	$('#email_pwd_status').html("<SPAN style='color:#FF0000;'>Sending Request...</SPAN>");
  
	$.get('sendAllApplicantLoginInfoEmail.html', function(xml){
		parseSendAllApplicantLoginInfoEmailResponse(xml);
	}, 'xml');
}

function parseSendAllApplicantLoginInfoEmailResponse(xmlHttp){  
    if($(data).find("RESPONSE-MSG").length > 0) {
    	$("#email_pwd_status").html("<SPAN style='color:#FF0000;'>" + $(data).find("RESPONSE-MSG").text() + "</SPAN>");
    }
    else {
    	$("#email_pwd_status").html("<SPAN style='color:#FF0000;'>Could not send request.</SPAN>");
    }
}

function onManualReferenceCheckRequestNLESD() {
	
	
	  var uid = $("#uid").val();
	 
	  var reftype = $("#reftype").val();
	  
		
	  if((uid == '') ||(reftype == "-1" )) {
		  if(reftype=="-1")
			  {
			  	alert("Please select Reference Type");
			  	return;
			  }else{
				  return;
			  }
	  }
	  
	  
	  $('#refRequest').modal('hide');
	  
	  openWindow('MANUAL_REFERENCE_CHECK',"manualReferenceCheckRequest.html?uid="+ uid + "&reftype=" + reftype, 850, 600, 1); 
	}
function onSendReferenceCheckRequestNLESD() {
	var uid = $('#uid').val();
	var email = $('#referrer_email').val();
	var reftype = $('#reftype').val();

	
	if((uid == -1)||(email == "") || (reftype == "-1" )) {
		return;
	}
  
	$('#sending_email_msg').show();
	$('#referrer_email').val("");
	
	var data = {};
	data.uid = uid;
	data.email = email;
	data.reftype=reftype;
	
	
	
	$.get('sendReferenceCheckRequest.html', data, function(xml){
		parseSendReferenceCheckRequestResponse(xml);
	}, 'xml');
}
function validateAdminComments()
{
	var isvaild=true;
	var position=$("#position option:selected").text();
	
	
	if(position.indexOf("PRINCIPAL") >= 0 || position.indexOf("LEADERSHIP") >= 0 )
	{
		var reccandcomments =$("#rec_cand_comments").val();
		if(reccandcomments.length <= 0 )
		{
			alert("Please enter Recommended Candidate Comments!");
			isvalid=false;
		}
		//check other boxes if something selected
		var candidate2=$("#candidate_2 option:selected").val();
		if(candidate2 >0)
		{
			var reccandcomments2= $("#rec_cand_comments2").val();
			if(reccandcomments2.length<= 0)
			{
				alert("Please enter Other Recommendable Candidate Comments!");
				isvalid=false;
			}
		}
		//check other boxes if something selected
		var candidate3=$("#candidate_3 option:selected").val();
		if(candidate3 >0)
		{
			var reccandcomments3= $("#rec_cand_comments3").val();
			if(reccandcomments.length <= 0)
			{
				alert("Please enter Other Recommendable Candidate Comments!");
				isvalid=false;
			}
		}
		return isvalid;
	}
		

}

