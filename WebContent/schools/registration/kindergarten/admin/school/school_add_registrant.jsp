<%@ page language="java"
         import="java.util.*, com.esdnl.school.registration.kindergarten.bean.*" 
         isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="KINDERGARTEN-REGISTRATION-SCHOOL-VIEW" /> 

<html>
  
  <head>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <TITLE>Student Registration</title>
    
    <script type="text/javascript" src="//serverapi.arcgisonline.com/jsapi/arcgis/?v=2.8"></script>
		<script type="text/javascript" src="/MemberServices/schools/registration/kindergarten/includes/js/schoolfinder.js"></script>
    <script type="text/javascript">
    	// school ids of schools offering efi
    	//var efi = new Array(211, 215, 219, 287, 244, 209, 247, 229, 232, 289, 192, 239, 207, 241, 196, 242, 162, 464, 414, 352, 403, 330, 341);
    	var efi = new Array(330, 211, 215, 352, 219, 287, 595, 464, 244, 209, 247, 229, 232, 495, 289, 341, 162, 192, 239, 207, 241, 403, 196, 242, 414);
    	//match email address
    	var emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$/; 
    	
    	//match elements that could contain a phone number
    	var phoneNumber = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
    	
    	//match date in format DD/MM/YYYY
    	var dateDDMMYYYRegex = /^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$/;
    	
    	//match date in format MM/YYYY
    	var dateMMYYYRegex = /^(0[1-9]|1[012])[- /.](19|20)\d\d$/;
    	
    	//match postal code
    	var postalCodeRegex = /^[A-Za-z]\d[A-Za-z]\s?\d[A-Za-z]\d$/;
    	
    	//match elements that could contain a MCP number
    	var mcpNumberRegex = /^([0-9]{12})$/;
    	
    	jQuery(function(){
    		
    		$('.opbutton').button();
    		
    		$('.datefield').datepicker({
    			dateFormat: "dd/mm/yy",
					changeYear: true,
					yearRange: "c-6:c+6"
    		});
    		
    		//$('#ddl_Stream').children().remove();
    		//$('#ddl_Stream').append($('<option>').attr({'value':''}).text('--- Select School ---'));
    		
    		$('#ddl_School').change(function(){
    			$('#ddl_Stream').children().remove();

    			if(parseInt($.inArray(parseInt($('#ddl_School').val()), efi)) > -1){
    				$('#ddl_Stream').append($('<option>').attr({'value':'', 'SELECTED':'SELECTED'}).text('--- Select One ---'));
        		$('#ddl_Stream').append($('<option>').attr('value', '1').text('ENGLISH'));
    				$('#ddl_Stream').append($('<option>').attr('value', '2').text('FRENCH'));
    			}
    			else {
    				$('#ddl_Stream')
    					.append($('<option>').attr({'value':'1', 'SELECTED':'SELECTED'}).text('ENGLISH'))
    			}
    		});
    		
    		$('#ddl_School')
    			.attr({'disabled' : 'disabled'})
    			.val("${usr.personnel.school.schoolID}")
    			.change();
    		
    		/*
    		$('#txt_PhysicalStreetAddress1, #txt_PhysicalCityTown').change(function(){
    			if($('#txt_PhysicalStreetAddress1').val() != '' && $('#txt_PhysicalCityTown').val() != ''){
    				getSchoolID();
    			}
    		});
    		*/
    		
    		$('#chk_MailingAddressSame').click(function(){
    			if($(this).is(':CHECKED')){
    				$('#txt_MailingAddress1').val($('#txt_PhysicalStreetAddress1').val());
    				$('#txt_MailingAddress2').val($('#txt_PhysicalStreetAddress2').val());
    				$('#txt_MailingCityTown').val($('#txt_PhysicalCityTown').val());
    				$('#txt_MailingPostalCode').val($('#txt_PhysicalPostalCode').val());
    			}
    			else{
    				$('#txt_MailingAddress1').val('');
    				$('#txt_MailingAddress2').val('');
    				$('#txt_MailingCityTown').val('');
    				$('#txt_MailingPostalCode').val('');
    			}
    		});
    		
    		$('#btn_SubmitRegistration').click(function(){
    			if(validateRegistrantForm()) {
    				$(this).attr({'disabled' : 'disabled'});
    				$('#btn_cancelAddReg').attr({'disabled' : 'disabled'});
    				
    				$('#add-registrant-form form')[0].submit();
    			}
    			else
    				$('html, body').animate({scrollTop: $("#pnl-error-msg").offset().top}, 2000);
    		});
    		
    		$('#btn_cancelAddReg').click(function(){
    			history.go(-1);
    		});
    		
    		$('#txt_MCPNumber').change(function(){
    			$('#tblStudentMCPInfo caption').append("<img src='/MemberServices/schools/registration/kindergarten/includes/images/ajax-loader-1.gif' align='right' border='0' />");
    			
    			if(checkMCPExists($(this).val())){
    				$('#tdMCPNumber').append(
    						$('<span>')
    							.attr({'id' : 'tdMCPNumberError'})
    							.addClass("inline-error-msg")
    							.html('<br />MCP alreay exists.')
    				);
    			}
    			else{
    				$('#tdMCPNumber span').remove();
    			}
    			
    			$('#tblStudentMCPInfo caption img').remove();
    		})
    		
    		
    		$('fieldset table').each(function() {
    			$(this).children().children('tr:odd').css({'background-color': "#ffffff"});
    			$(this).children().children('tr:even').css({'background-color': "#f0f0f0"});
    			$(this).children().children('tr:not(:first)').children('td').css({'border-top': 'solid 1px #333333'});
    		});
    		
    		$('td.required').each(function(){
    			$(this).html("<span style='color:red;'>*</span>" + $(this).html());
    		});
    		
    		$('td.one-required').each(function(){
    			$(this).html("<span style='color:blue;'>*</span>" + $(this).html());
    		});
    		
    		$('input[type=text], select').css({'border' : 'solid 1px #7B9EBD', 'background-color' : '#FFFFCC'});
    		
    		$('#ddl_School option[value=205], #ddl_School option[value=223]').hide();
    		
    	});
    	
    	function checkMCPExists(p_mcp) {
    		var exists = false;
    		$.ajax(
    			{
    				type: "POST",  
    				url: "/MemberServices/schools/registration/kindergarten/ajax/checkMCPAlreadyExists.html",
    				data: {
    					mcp : p_mcp,
    					ajax : true 
    				}, 
    				success: function(data){
    					if($(data).find('CHECK-MCP-EXISTS-RESPONSE').length > 0) {
    						exists = ("true" == $(data).find('CHECK-MCP-EXISTS-RESPONSE').first().attr('exists'));
    					}
    				}, 
    				dataType: "xml",
    				async: false
    			}
    		);
    		
    		return exists
    	}
    	
    	function validateRegistrantForm(){
				var ul = $('<ul>');
      	
				if(checkMCPExists($('#txt_MCPNumber').val()))
					ul.append($('<li>').text('Student Information - MCP Number Already Registered'));
				
				if($('#ddl_School').val() == '')
					ul.append($('<li>').text('School Information - School'));
				
				if($('#ddl_Stream').val() == '')
					ul.append($('<li>').text('School Information - Stream'));
				
				if($('#ddl_Gender').val() == '')
					ul.append($('<li>').text('Student Information - Gender'));
				
				if($('#ddl_PrimaryContactRelationship').val() == '')
					ul.append($('<li>').text('Primary Contact - Relationship to Student'));
				
     		$('input[type=text].required').each(function(){
     			if($.trim($(this).val()) == ""){
     				ul.append($('<li>').text($(this).attr('errortext')));
     			}
     		});
     		
     		if(($('#txt_PrimaryContactHomePhone').val() == '') 
     				&& ($('#txt_PrimaryContactWorkPhone').val() == '')
     				&& ($('#txt_PrimaryContactCellPhone').val() == ''))
     			ul.append($('<li>').text('Primary Contact - One of Home, Work, or Cell Phone'));
     		
     		if($.trim($('#txt_SecondaryContactName').val()) != ''){
     			if($('#ddl_SecondaryContactRelationship').val() == '')
     				ul.append($('<li>').text('Optional Contact - Relationship to Student'));
     			
     			if(($('#txt_SecondaryContactHomePhone').val() == '') 
         				&& ($('#txt_SecondaryContactWorkPhone').val() == '')
         				&& ($('#txt_SecondaryContactCellPhone').val() == ''))
         			ul.append($('<li>').text('Optional Contact - One of Home, Work, or Cell Phone'));
     			
     			//if($('#txt_SecondaryContactEmail').val() == '')
     			//	ul.append($('<li>').text('Optional Contact - Email'));
     		}
     		
     		if(!$('#rbg_CustodyIssues_1').is(':checked') && !$('#rbg_CustodyIssues_0').is(':checked'))
     			ul.append($('<li>').text('Other Information - Custody Issues'));
     		
     		if(!$('#rbg_HealthOtherConcerns_1').is(':checked') && !$('#rbg_HealthOtherConcerns_0').is(':checked'))
     			ul.append($('<li>').text('Other Information - Health Concerns'));
     		
     		if(!$('#rbg_AccessibleFacility_1').is(':checked') && !$('#rbg_AccessibleFacility_0').is(':checked'))
     			ul.append($('<li>').text('Other Information - Accessible Facility'));
     		
     		if(!$('#rbg_CurrentChildEFI_1').is(':checked') && !$('#rbg_CurrentChildEFI_0').is(':checked'))
     			ul.append($('<li>').text('Other Information - EFI Sibling'));
     		
     		if($('#txt_DateOfBirth').val() != '' && !dateDDMMYYYRegex.test($('#txt_DateOfBirth').val()))
					ul.append($('<li>').text('Student Information - Date of birth invalid format'));
 		
     		if($('#txt_MCPNumber').val() != '' && !mcpNumberRegex.test($('#txt_MCPNumber').val()))
				ul.append($('<li>').text('Student Information - MCP number invalid format'));
     		
     		if($('#txt_MCPExpiration').val() != '' && !dateMMYYYRegex.test($('#txt_MCPExpiration').val()))
					ul.append($('<li>').text('Student Information - MCP Expiration date invalid format'));
 				
 				if($('#txt_PhysicalPostalCode').val() != '' && !postalCodeRegex.test($('#txt_PhysicalPostalCode').val()))
					ul.append($('<li>').text('Physical Address - Postal code invalid format'));
 				
 				if($('#txt_MailingPostalCode').val() != '' && !postalCodeRegex.test($('#txt_MailingPostalCode').val()))
					ul.append($('<li>').text('Mailing Address - Postal code invalid format'));
 				
     		if($('#txt_PrimaryContactHomePhone').val() != '' && !phoneNumber.test($('#txt_PrimaryContactHomePhone').val()))
					ul.append($('<li>').text('Primary Contact - Home phone invalid format'));
     		
     		if($('#txt_PrimaryContactWorkPhone').val() != '' && !phoneNumber.test($('#txt_PrimaryContactWorkPhone').val()))
					ul.append($('<li>').text('Primary Contact - Work phone invalid format'));
     		
     		if($('#txt_PrimaryContactCellPhone').val() != '' && !phoneNumber.test($('#txt_PrimaryContactCellPhone').val()))
					ul.append($('<li>').text('Primary Contact - Cell phone invalid format'));
     		
     		if($('#txt_PrimaryContactEmail').val() != '' && !emailRegex.test($('#txt_PrimaryContactEmail').val()))
					ul.append($('<li>').text('Primary Contact - Email invalid format'));
     		
     		if($('#txt_SecondaryContactHomePhone').val() != '' && !phoneNumber.test($('#txt_SecondaryContactHomePhone').val()))
					ul.append($('<li>').text('Optional Contact - Home phone invalid format'));
 		
 				if($('#txt_SecondaryContactWorkPhone').val() != '' && !phoneNumber.test($('#txt_SecondaryContactWorkPhone').val()))
					ul.append($('<li>').text('Optional Contact - Work phone invalid format'));
 		
 				if($('#txt_SecondaryContactCellPhone').val() != '' && !phoneNumber.test($('#txt_SecondaryContactCellPhone').val()))
					ul.append($('<li>').text('Optional Contact - Cell phone invalid format'));
     		
     		if($('#txt_SecondaryContactEmail').val() != '' && !emailRegex.test($('#txt_SecondaryContactEmail').val()))
 					ul.append($('<li>').text('Optional Contact - Email invalid format'));
     		
     		if($('#txt_EmergencyContactPhone').val() != '' && !phoneNumber.test($('#txt_EmergencyContactPhone').val()))
					ul.append($('<li>').text('Emergency Contact - Telephone invalid format'));
     		
     		if(ul.children().length > 0) {
     			$('#error-msg').children().remove();
     			$('#error-msg').append(ul);
     			
     			$('#pnl-error-msg').show();

     			return false;
     		}
     		else {
     			$('#pnl-error-msg').hide();
     			return true;
     		}
    	}
    </script>
  </head>

  <body bgcolor="#BF6200">
  	<div align='center' style='font-size:14pt;font-weight:bold;color:#33cc33;'>
	  	${ usr.personnel.school.schoolName } - Add New Registrant
  	</div>
		<div align='center'>
			<span style='color:red;'>*</span> Required field.
			<span style='color:blue;'>*</span> One Required.
		</div>
		<br />
		<c:if test="${ msg ne null }">
			<div align='center' >
				<div style='width:50%;text-align:left;'>
					<p class='important-note' style='padding:8px;'><u>Errors:</u><br/>${ msg }</p>
				</div>
			</div>
		</c:if>
		<div id='pnl-error-msg' style='display:none;padding-bottom:10px;' align='center'>
			<div style='width:50%;padding:8px;text-align:left;'>						
				<span><u>Form Errors:</u><br/>The following fields are not complete or have invalid values and must be corrected before your registration can be submitted.</span><br />
				<div id='error-msg' style='border:none;'></div>
			</div>
		</div>
		<div id='add-registrant-form' style='width:100%; display:inline;'>
			<form method='post' action="<c:url value='/schools/registration/kindergarten/admin/school/addKindergartenRegistrant.html'/>" >
				<input type='hidden' name='op' value='registrant-added' />
				<input type='hidden' name='registration_id' value='${ krp.registrationId }' />
				<div align='center'>
					<fieldset>
						<legend>Student Information</legend>
						<table align='center' cellspacing='0' cellpadding='8' width='75%'>
							<caption>Demographics</caption>
							<tr>
								<td class='label required'>First Name:</td>
								<td align='left'><input class='required' errortext='Student Information - First Name' type='text' id='txt_StudentFirstName' name='txt_StudentFirstName' style='width: 150px;' /></td>
							</tr>
							<tr>
								<td class='label required'>Last Name:</td>
								<td align='left'><input class='required' errortext='Student Information - Last Name' type='text' id='txt_StudentLastName' name='txt_StudentLastName' style='width: 150px;' /></td>
							</tr>
							<tr>
								<td class='label required'>Gender:</td>
								<td align='left'><sreg:GenderDDL id='ddl_Gender' cls='required' /></td>
							</tr>
							<tr>
								<td class='label required' valign='top'>Date of Birth:</td>
								<td align='left'>
									<input class='required datefield' errortext='Student Information - Date of Birth' type='text' id='txt_DateOfBirth' name='txt_DateOfBirth' style='width: 75px;' />
									<br /><span style='color:#333333;font-weight:bold;font-size:9px;'>dd/mm/yyyy</span>
								</td>
							</tr>
						</table>
						<br />
						<table id='tblStudentMCPInfo' align='center' cellspacing='0' cellpadding='8' width='75%'>
							<caption>Student MCP</caption>
							<tr>
								<td class='label required' valign="top">MCP Number:</td>
								<td id='tdMCPNumber' align='left'>
									<input class='required' errortext='Student Information - MCP Number' type='text' id='txt_MCPNumber' name='txt_MCPNumber' style='width: 150px;'/>
								</td>
							</tr>
							<tr>
								<td class='label required' valign='top'>MCP Expiration:</td>
								<td align='left'>
									<input class='required' errortext='Student Information - MCP Expiration' type='text' id='txt_MCPExpiration' name='txt_MCPExpiration' style='width: 75px;' />
									<br /><span style='color:#333333;font-weight:bold;font-size:9px;'>mm/yyyy</span>
								</td>
							</tr>
						</table>
						<br />
						<table id='tblPhysicalAddress' align='center' cellspacing='0' cellpadding='8' width='75%'>
							<caption>Physical Address</caption>
							<tr>
								<td class='label required' valign='top'>Street Address:</td>
								<td align='left'>
									<div style='padding-bottom: 3px;'>
										<input class='required' errortext='Physical Address - Street Address' type='text' id='txt_PhysicalStreetAddress1' name='txt_PhysicalStreetAddress1' style='width: 200px;' />
									</div>
									<input type='text' id='txt_PhysicalStreetAddress2' name='txt_PhysicalStreetAddress2' style='width: 200px;' />
								</td>
							</tr>
							<tr>
								<td class='label required'>City/Town:</td>
								<td align='left'>							
									<select id='txt_PhysicalCityTown' name='txt_PhysicalCityTown' errortext='Mailing Address - City/Town' >
										<jsp:include page="../../includes/townlist.jsp" />   
	                </select>  
								</td>
							</tr>
							<tr>
								<td class='label'>Province:</td>
								<td align='left'>Newfoundland and Labrador</td>
							</tr>
							<tr>
								<td class='label required' valign='top'>Postal Code:</td>
								<td align='left'>
									<input class='required' errortext='Physical Address - Postal Code' type='text' id='txt_PhysicalPostalCode' name='txt_PhysicalPostalCode' style='width: 75px;' />
									<br /><span style='color:#333333;font-weight:bold;font-size:9px;'>x0x 0x0</span>
								</td>
							</tr>
						</table>
						<br/>							
						<table align='center' cellspacing='0' cellpadding='8' width='75%'>
							<caption>Mailing Address <span style='font-size:10px; color:#333333;'>(<input id='chk_MailingAddressSame' type="checkbox" />Same as physical address.)</span></caption>
							<tr>
								<td class='label required' valign='top'>Address:<br/><span style='color:#333333;font-size:9px;'>(street address, <br />p.o. box, etc)</span></td>
								<td align='left'>
									<div style='padding-bottom: 3px;'>
										<input class='required' errortext='Mailing Address - Address' type='text' id='txt_MailingAddress1' name='txt_MailingAddress1' style='width: 200px;' />
									</div>
									<input type='text' id='txt_MailingAddress2' name='txt_MailingAddress2' style='width: 200px;' />
								</td>
							</tr>
							<tr>
								<td class='label required'>City/Town:</td>
								<td align='left'>								
									<select id='txt_MailingCityTown' name='txt_MailingCityTown' errortext='Mailing Address - City/Town' >
										<jsp:include page="../../includes/townlist.jsp" />                       		
	                </select>
								</td>
							</tr>
							<tr>
								<td class='label required'>Province:</td>
								<td align='left'>Newfoundland and Labrador</td>
							</tr>
							<tr>
								<td class='label required' valign='top'>Postal Code:</td>
								<td align='left'>
									<input class='required' errortext='Mailing Address - Postal Code' type='text' id='txt_MailingPostalCode' name='txt_MailingPostalCode' style='width: 75px;' />
									<br /><span style='color:#333333;font-weight:bold;font-size:9px;'>x0x 0x0</span>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
				<br />
				<div align='center'>
					<fieldset>
						<legend>School Information</legend>
						<table align='center' cellspacing='0' cellpadding='8' width='75%'>
							<tr>
								<td class='label'>School Year:</td>
								<td style='font-weight:bold;' align='left'>${krp.schoolYear}</td>
							</tr>
							<tr>
								<td class='label required'>School:</td>
								<td align='left'>
									<input type='hidden' name='hdn_School' value='${ usr.personnel.school.schoolID }' />
									<sreg:SchoolsDDL cls='required' id='ddl_School' period='${krp}' dummy='true' />
								</td>
							</tr>
							<tr>
								<td class='label required'>Program:</td>
								<td align='left'><sreg:SchoolStreamDDL cls='required' id='ddl_Stream' /></td>
							</tr>
						</table>
					</fieldset>
				</div>
				<br />
				<div align='center'>
					<fieldset>
						<legend>Contact Information</legend>								
						<table align='center' cellspacing='0' cellpadding='8' width='75%'>
							<caption>Primary Contact</caption>
							<tr>
								<td class='label required'>Full Name:</td>
								<td align='left'><input class='required' errortext='Primary Contact - Full Name' type='text' id='txt_PrimaryContactName' name='txt_PrimaryContactName' style='width: 200px;' /></td>
							</tr>
							<tr>
								<td class='label required'>Relationship to Student:</td>
								<td align='left'><sreg:ContactRelationshipDDL id='ddl_PrimaryContactRelationship' cls='required' /></td>
							</tr>
							<tr>
								<td class='label one-required' valign='top'>Home Phone:</td>
								<td align='left'>
									<input class='one-required' errortext='Primary Contact - Home Phone' type='text' id='txt_PrimaryContactHomePhone' name='txt_PrimaryContactHomePhone' style='width: 100px;' />
									<br /><span style='color:#333333;font-weight:bold;font-size:9px;'>xxx xxx-xxxx</span>
								</td>
							</tr>
							<tr>
								<td class='label one-required' valign='top'>Work Phone:</td>
								<td align='left'>
									<input class='one-required' errortext='Primary Contact - Work Phone' type='text' id='txt_PrimaryContactWorkPhone' name='txt_PrimaryContactWorkPhone' style='width: 100px;' />
									<br /><span style='color:#333333;font-weight:bold;font-size:9px;'>xxx xxx-xxxx</span>
								</td>
							</tr>
							<tr>
								<td class='label one-required' valign='top'>Cell Phone:</td>
								<td align='left'>
									<input class='one-required' errortext='Primary Contact - Cell Phone' type='text' id='txt_PrimaryContactCellPhone' name='txt_PrimaryContactCellPhone' style='width: 100px;' />
									<br /><span style='color:#333333;font-weight:bold;font-size:9px;'>xxx xxx-xxxx</span>
								</td>
							</tr>
							<tr>
								<td class='label required'>Email:</td>
								<td align='left'><input class='required' errortext='Primary Contact - Email' type='text' id='txt_PrimaryContactEmail' name='txt_PrimaryContactEmail' style='width: 200px;' /></td>
							</tr>
						</table><br/>
						<table align='center' cellspacing='0' cellpadding='8' width='75%'>
							<caption>Optional Contact</caption>
							<tr>
								<td class='label optionally-required'>Full Name:</td>
								<td align='left'><input class='optionally-required' type='text' id='txt_SecondaryContactName' name='txt_SecondaryContactName' style='width: 200px;' /></td>
							</tr>
							<tr>
								<td class='label optionally-required'>Relationship to Student:</td>
								<td align='left'><sreg:ContactRelationshipDDL id='ddl_SecondaryContactRelationship' cls='required' /></td>
							</tr>
							<tr>
								<td class='label optionally-one-required' valign='top'>Home Phone:</td>
								<td align='left'>
									<input class='optionally-one-required' type='text' id='txt_SecondaryContactHomePhone' name='txt_SecondaryContactHomePhone' style='width: 100px;' />
									<br /><span style='color:#333333;font-weight:bold;font-size:9px;'>xxx xxx-xxxx</span>
								</td>
							</tr>
							<tr>
								<td class='label optionally-one-required' valign='top'>Work Phone:</td>
								<td align='left'>
									<input class='optionally-one-required' type='text' id='txt_SecondaryContactWorkPhone' name='txt_SecondaryContactWorkPhone' style='width: 100px;' />
									<br /><span style='color:#333333;font-weight:bold;font-size:9px;'>xxx xxx-xxxx</span>
								</td>
							</tr>
							<tr>
								<td class='label optionally-one-required' valign='top'>Cell Phone:</td>
								<td align='left'>
									<input class='optionally-one-required' type='text' id='txt_SecondaryContactCellPhone' name='txt_SecondaryContactCellPhone' style='width: 100px;' />
									<br /><span style='color:#333333;font-weight:bold;font-size:9px;'>xxx xxx-xxxx</span>
								</td>
							</tr>
							<tr>
								<td class='label optionally-required'>Email:</td>
								<td align='left'><input class='optionally-required' type='text' id='txt_SecondaryContactEmail' name='txt_SecondaryContactEmail' style='width: 200px;' /></td>
							</tr>
						</table>
						<br/>
						<table align='center' cellspacing='0' cellpadding='8' width='75%'>
							<caption>Emergency Contact</caption>
							<tr>
								<td class='label required'>Full Name:</td>
								<td align='left'><input class='required' errortext='Emergency Contact - Full Name' type='text' id='txt_EmergencyContactName' name='txt_EmergencyContactName' style='width: 200px;' /></td>
							</tr>
							<tr>
								<td class='label required' valign='top'>Telephone:</td>
								<td align='left'>
									<input class='required' errortext='Emergency Contact - Telephone' type='text' id='txt_EmergencyContactPhone' name='txt_EmergencyContactPhone' style='width: 100px;' />
									<br /><span style='color:#333333;font-weight:bold;font-size:9px;'>xxx xxx-xxxx</span>
								</td>
							</tr>
						</table>
					</fieldset>
				</div><br />
				<div align='center'>
					<fieldset>
						<legend>Other Information</legend>
						<table align='center' cellspacing='0' cellpadding='8' width='75%'>
							<tr>
								<td class='label required' style='width:110px;' valign='top'><sreg:YesNoRBG id='rbg_CustodyIssues' /></td>
								<td align='left'>
									Are there any custody issues of which the school should be aware?<br /><br />
									<i>Court documentation is required if either parent is to be denied from receiving academic information and/or access to child.</i>
								</td>
							</tr>
							<tr>
								<td class='label required' style='width:110px;' valign='top'><sreg:YesNoRBG id='rbg_HealthOtherConcerns' /></td>
								<td align='left'>Does your child have any health or other concerns of which we should be aware?</td>
							</tr>
							<tr>
								<td class='label required' style='width:110px;' valign='top'><sreg:YesNoRBG id='rbg_AccessibleFacility' /></td>
								<td align='left'>Does your child require an accessible facility?</td>
							</tr>
							<tr>
								<td class='label required' style='width:110px;' valign='top'><sreg:YesNoRBG id='rbg_CurrentChildEFI' /></td>
								<td align='left'>Do you have a child currently enrolled in the Early French Immersion Program in this school?</td>
							</tr>
						</table>
					</fieldset>
				</div>
				<br />
				<div style='text-align: center;'>
					<input id='btn_SubmitRegistration' type='button' value='Submit Registration' class='opbutton' /> <a class='opbutton' href="index.html">Cancel</a>
				</div>
				<br />
			</form>
		</div>
	</body>
	
</html>