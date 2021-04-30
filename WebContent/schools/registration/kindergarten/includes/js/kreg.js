	// school ids of schools offering efi
    	//var efi = new Array(211, 215, 219, 287, 244, 209, 247, 229, 232, 495, 289, 387, 192, 239, 207, 241, 196, 242, 162, 464, 414, 352, 403, 330, 341, 416, 595);
    	  var efi = new Array(330, 211, 215, 352, 219, 287, 595, 464, 244, 209, 247, 229, 232, 495, 289, 341, 162, 192, 239, 207, 241, 403, 196, 242, 414);
    	
    	//match email address
    	var emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$/; 
    	
    	//match elements that could contain a phone number
    	var phoneNumber = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
    	
    	//match date in format DD/MM/YYYY
    	//var dateDDMMYYYRegex = /^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$/;
    	
    	//match date in format MM/DD/YYYY
    	var dateMMDDYYYRegex = /^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$/;
    	
    	//match date in format MM/YYYY
    	var dateMMYYYRegex = /^(0[1-9]|1[012])[- /.](19|20)\d\d$/;
    	
    	//match postal code
    	var postalCodeRegex = /^[A-Za-z]\d[A-Za-z]\s?\d[A-Za-z]\d$/;
    	
    	//match elements that could contain a MCP number
    	var mcpNumberRegex = /^([0-9]{12})$/;
    	
    	jQuery(function(){
    		
    		    		
    		//$('#ddl_Stream').children().remove();
    		//$('#ddl_Stream').append($('<option>').attr({'value':''}).text('--- Select Stream ---'));
    		
    		$('#ddl_School').change(function(){
    			$('#ddl_Stream').children().remove();

    			if(parseInt($.inArray(parseInt($('#ddl_School').val()), efi)) > -1){
    				$('#ddl_Stream').append($('<option>').attr({'value':'', 'SELECTED':'SELECTED'}).text('--- Select One ---'));
        		$('#ddl_Stream').append($('<option>').attr('value', '1').text('ENGLISH'));
    				$('#ddl_Stream').append($('<option>').attr('value', '2').text('FRENCH'));
    			}
    			else {
    				$('#ddl_Stream').append($('<option>').attr({'value':'1', 'SELECTED':'SELECTED'}).text('ENGLISH'));
    			}
    		});
    		
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
    				//$('html, body').animate({scrollTop: $("#pnl-error-msg").offset().top}, 2000);
					$("#pnl-error-msg").css("display","block");

    		});
    		
    		$('#btn_cancelAddReg').click(function(){
    			self.location.href = "/index.jsp";
    		});
    		
    		$('#lnkGenerateMCPInfo').click(function(){
    			if(confirm('Generate MCP Number?')) {
    				$('#tblStudentMCPInfo caption').append("<img src='/MemberServices/schools/registration/kindergarten/includes/images/ajax-loader-1.gif' align='right' border='0' />");
    				$.post("/MemberServices/schools/registration/kindergarten/ajax/generateMCP.html", 
    						{	
    							ajax : true 
    						}, 
    						function(data){
    							if($(data).find('GENERATE-MCP-RESPONSE').length > 0) {
    								$('#txt_MCPNumber').val($(data).find('GENERATE-MCP-RESPONSE').first().attr('mcp'));
    								$('#txt_MCPExpiration').val($(data).find('GENERATE-MCP-RESPONSE').first().attr('mcp-expiry'));
    							}
    							
    							$('#tblStudentMCPInfo caption img').remove();
    						}, 
    						"xml");
    			}
    		});
    		
    		$('#txt_MCPNumber').change(function(){
    			
    			if(checkMCPExists($(this).val())){
				
				$("#mcpErr").css("display","block").delay(8000).fadeOut();    				

    			}
    			else{
    				$("#mcpErr").css("display","none");
    			}
    			
    		
    		})
    		
    		
    		$('fieldset table').each(function() {
    			$(this).children().children('tr:odd').css({'background-color': "#ffffff"});
    			$(this).children().children('tr:even').css({'background-color': "#ffffff"});
    			$(this).children().children('tr:not(:first)').children('td').css({'border-top': 'solid 0px silver'});
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
					ul.append($('<li>').text('1. STUDENT INFORMATION, (b) MCP - MCP Number has already registered.'));
				
				if($('#ddl_Gender').val() == '')
					ul.append($('<li>').text('1. STUDENT INFORMATION, (a) DEMOGRAPHICS - Gender'));
				
			
				
     		$('input[type=text].required').each(function(){
     			if($.trim($(this).val()) == ""){
     				ul.append($('<li>').text($(this).attr('errortext')));
     			}
     		});
     		
				if($('#ddl_School').val() == '')
					ul.append($('<li>').text('2. SCHOOL INFORMATION - School'));
				
				if($('#ddl_Stream').val() == '')
					ul.append($('<li>').text('2. SCHOOL INFORMATION - Stream'));				
				
				if($('#ddl_PrimaryContactRelationship').val() == '')
					ul.append($('<li>').text('3. CONTACT INFORMATION, (a) PRIMARY CONTACT - Relationship to Student'));


     		if(($('#txt_PrimaryContactHomePhone').val() == '') 
     				&& ($('#txt_PrimaryContactWorkPhone').val() == '')
     				&& ($('#txt_PrimaryContactCellPhone').val() == ''))
     			ul.append($('<li>').text('3. CONTACT INFORMATION, (a) PRIMARY CONTACT - One of Home, Work, or Cell Phone'));
     		
     		if($.trim($('#txt_SecondaryContactName').val()) != ''){
     			if($('#ddl_SecondaryContactRelationship').val() == '')
     				ul.append($('<li>').text('3. CONTACT INFORMATION, (b) OPTIONAL CONTACT - Relationship to Student'));
     			
     			if(($('#txt_SecondaryContactHomePhone').val() == '') 
         				&& ($('#txt_SecondaryContactWorkPhone').val() == '')
         				&& ($('#txt_SecondaryContactCellPhone').val() == ''))
         			ul.append($('<li>').text('3. CONTACT INFORMATION, (b) OPTIONAL CONTACT - One of Home, Work, or Cell Phone'));
     			
     			//if($('#txt_SecondaryContactEmail').val() == '')
     			//	ul.append($('<li>').text('Optional Contact - Email'));
     		}
     		
     		if(!$('#rbg_CustodyIssues_1').is(':checked') && !$('#rbg_CustodyIssues_0').is(':checked'))
     			ul.append($('<li>').text('4. OTHER INFORMATION, (a) - Custody Issues'));
     		
     		if(!$('#rbg_HealthOtherConcerns_1').is(':checked') && !$('#rbg_HealthOtherConcerns_0').is(':checked'))
     			ul.append($('<li>').text('4. OTHER INFORMATION, (b) Health Concerns'));
     		
     		if(!$('#rbg_AccessibleFacility_1').is(':checked') && !$('#rbg_AccessibleFacility_0').is(':checked'))
     			ul.append($('<li>').text('4. OTHER INFORMATION, (c) Accessible Facility'));
     		
     		if(!$('#rbg_CurrentChildEFI_1').is(':checked') && !$('#rbg_CurrentChildEFI_0').is(':checked'))
     			ul.append($('<li>').text('4. OTHER INFORMATION, (d) EFI Sibling'));
     		
     		if($('#txt_DateOfBirth').val() != '' && !dateMMDDYYYRegex.test($('#txt_DateOfBirth').val()))
					ul.append($('<li>').text('1. STUDENT INFORMATION, (a) DEMOGRAPHICS - Date of birth invalid format'));
 		
     		if($('#txt_MCPNumber').val() != '' && !mcpNumberRegex.test($('#txt_MCPNumber').val()))
				ul.append($('<li>').text('1. STUDENT INFORMATION, (b) MCP - MCP number invalid format'));
     		
     		if($('#txt_MCPExpiration').val() != '' && !dateMMYYYRegex.test($('#txt_MCPExpiration').val()))
					ul.append($('<li>').text('1. STUDENT INFORMATION, (b) MCP - MCP Expiration date invalid format'));
 				
 				if($('#txt_PhysicalPostalCode').val() != '' && !postalCodeRegex.test($('#txt_PhysicalPostalCode').val()))
					ul.append($('<li>').text('1. STUDENT INFORMATION, (c) PHYSICAL ADDRESS - Postal code invalid format'));
 				
 				if($('#txt_MailingPostalCode').val() != '' && !postalCodeRegex.test($('#txt_MailingPostalCode').val()))
					ul.append($('<li>').text('1. STUDENT INFORMATION, (d) MAILING ADDRESS - Postal code invalid format'));
 				
     		if($('#txt_PrimaryContactHomePhone').val() != '' && !phoneNumber.test($('#txt_PrimaryContactHomePhone').val()))
					ul.append($('<li>').text('3. CONTACT INFORMATION, (a) PRIMARY CONTACT- Home phone invalid format'));
     		
     		if($('#txt_PrimaryContactWorkPhone').val() != '' && !phoneNumber.test($('#txt_PrimaryContactWorkPhone').val()))
					ul.append($('<li>').text('3. CONTACT INFORMATION, (a) PRIMARY CONTACT - Work phone invalid format'));
     		
     		if($('#txt_PrimaryContactCellPhone').val() != '' && !phoneNumber.test($('#txt_PrimaryContactCellPhone').val()))
					ul.append($('<li>').text('3. CONTACT INFORMATION, (a) PRIMARY CONTACT - Cell phone invalid format'));
     		
     		if($('#txt_PrimaryContactEmail').val() != '' && !emailRegex.test($('#txt_PrimaryContactEmail').val()))
					ul.append($('<li>').text('3. CONTACT INFORMATION, (a) PRIMARY CONTACT - Email invalid format'));
     		
     		if($('#txt_SecondaryContactHomePhone').val() != '' && !phoneNumber.test($('#txt_SecondaryContactHomePhone').val()))
					ul.append($('<li>').text('3. CONTACT INFORMATION, (b) OPTIONAL CONTACT - Home phone invalid format'));
 		
 				if($('#txt_SecondaryContactWorkPhone').val() != '' && !phoneNumber.test($('#txt_SecondaryContactWorkPhone').val()))
					ul.append($('<li>').text('3. CONTACT INFORMATION, (b) OPTIONAL CONTACT - Work phone invalid format'));
 		
 				if($('#txt_SecondaryContactCellPhone').val() != '' && !phoneNumber.test($('#txt_SecondaryContactCellPhone').val()))
					ul.append($('<li>').text('3. CONTACT INFORMATION, (b) OPTIONAL CONTACT - Cell phone invalid format'));
     		
     		if($('#txt_SecondaryContactEmail').val() != '' && !emailRegex.test($('#txt_SecondaryContactEmail').val()))
 					ul.append($('<li>').text('3. CONTACT INFORMATION, (b) OPTIONAL CONTACT - Email invalid format'));
     		
     		if($('#txt_EmergencyContactPhone').val() != '' && !phoneNumber.test($('#txt_EmergencyContactPhone').val()))
					ul.append($('<li>').text('3. CONTACT INFORMATION, (c) EMERGENCY CONTACT - Telephone invalid format'));
     		
     		if(ul.children().length > 0) {
     			$('#error-msg').children().remove();
     			$('#error-msg').append(ul);
     			
     			$("#pnl-error-msg").css("display","block");

     			return false;
     		}
     		else {
     			$("#pnl-error-msg").css("display","none");
     			return true;
     		}
    	}


$.jMaskGlobals = {
  maskElements: 'input,td,span,div',
  dataMaskAttr: '*[data-mask]',
  dataMask: true,
  watchInterval: 300,
  watchInputs: true,
  watchDataMask: false,
  byPassKeys: [9, 16, 17, 18, 36, 37, 38, 39, 40, 91],
  translation: {
    '0': {pattern: /\d/},
    '9': {pattern: /\d/, optional: true},
    '#': {pattern: /\d/, recursive: true},
    'A': {pattern: /[a-zA-Z0-9]/},
    'S': {pattern: /[a-zA-Z]/} 	
  }
};
