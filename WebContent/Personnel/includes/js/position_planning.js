var isSystemAdministrator = false;
var isPositionPlanningAdmin = true;
//var isEditable = false;
var currentSchoolYear = '';

//Set variables for graphing pie charts.
//Because I like data being graphed.
//Summary Graph
permanentPositionsGraphVar = 0;
vacantPositionsGraphVar = 0;		
redundantPositionsGraphVar = 0;
//Allocations Graph
numSAgraphing = 0; 

numTLAgraphing = 0
numIRT1graphing = 0;
numIRT2graphing = 0;
numREADINGgraphing = 0;
numLRTgraphing = 0;
numSPECIALISTgraphing = 0;
numGUIDANCEgraphing = 0;
numADMINgraphing = 0;
numREGULARgraphing = 0;

numOTHERgraphing = 0;


$('document').ready(function(){
	
	//$('#add-allocation-operation-table tr:odd').css({'background-color' : '#F0F0F0'});
	
	$('input[type=button]').button();
	
	$('#txt_startTermDate, #txt_endTermDate').datepicker({ dateFormat: "dd/mm/yy" });
	
	$('#lst_schoolyear, #lst_school').change(function() {
		$('#add-allocation-row').hide();
		$('#divlinks').hide();
		if(isSystemAdministrator) {
			if($('#lst_schoolyear').val() != '' && ($('#span-schoolyear-ops').length == 0)){
				//$('#div_schoolyear')
				$('#div_schoolyearOptions')	
					.append(
						$('<span>')
							.attr({'id':'span-schoolyear-ops'})
							.css({'padding':'3px'})
							//.append($('<br>'))
							.append(
								$('<span>')
									.attr({'id' : 'span-disable-all-schoolyear-allocations'})
									.css({'padding':'3px'})
									.append(
										$('<a>')
											.attr({'href':'#'})
											.attr({'class':'btn btn-xs btn-danger'})												
											.text('Disable All')
											.click(function(){												
												$("#loadingSpinner").css("display","block");
												$.post("ajax/updateTeacherAllocationStatus.html", 
													{	
														schoolyear : $('#lst_schoolyear').val(),
														enabled : false,
														ajax : true 
													}, 
													function(data){
														$('#span-disable-all-schoolyear-allocations').children().remove();
														$('#span-disable-all-schoolyear-allocations')
															.css({'text-decoration' : 'none', 'color' : 'red', 'font-weight' : 'bold'})
															.text($(data).find('UPDATE-TEACHER-ALLOCATION-STATUS-RESPONSE').attr('msg'));
														$("#loadingSpinner").css("display","none");
													}, 
													"xml");
											})
									)
							)
							//.append($('<span>').html('&nbsp;'))
							.append(
								$('<span>')
									.attr({'id' : 'span-enable-all-schoolyear-allocations'})
									.css({'padding':'3px'})
									.append(
										$('<a>')
											.attr({'href':'#'})
											.attr({'class':'btn btn-xs btn-success'})
											.text('Enable All')
											.click(function(){
												$("#loadingSpinner").css("display","block");
												$.post("ajax/updateTeacherAllocationStatus.html", 
													{	
														schoolyear : $('#lst_schoolyear').val(),
														enabled : true,
														ajax : true 
													}, 
													function(data){
														$('#span-enable-all-schoolyear-allocations').children().remove();
														$('#span-enable-all-schoolyear-allocations')
															.css({'color' : 'red', 'font-weight' : 'bold'})
															.text($(data).find('UPDATE-TEACHER-ALLOCATION-STATUS-RESPONSE').attr('msg'));
														$("#loadingSpinner").css("display","none");
													}, 
													"xml");
											})
											
									)
							)
							//.append($('<br>'))
							.append(
								$('<span>')
									.attr({'id' : 'span-publish-all-schoolyear-allocations'})
									.css({'padding':'3px'})
									.append(
										$('<a>')
											.attr({'href':'#'})
											.attr({'class':'btn btn-xs btn-success'})
											.text('Publish All')
											.click(function(){
												$("#loadingSpinner").css("display","block");
												$.post("ajax/updateTeacherAllocationPublished.html", 
													{	
														schoolyear : $('#lst_schoolyear').val(),
														published : true,
														ajax : true 
													}, 
													function(data){
														$('#span-publish-all-schoolyear-allocations').children().remove();
														$('#span-publish-all-schoolyear-allocations')
															.css({'color' : 'red', 'font-weight' : 'bold'})
															.text($(data).find('UPDATE-TEACHER-ALLOCATION-PUBLISHED-RESPONSE').attr('msg'));
														$("#loadingSpinner").css("display","none");
													}, 
													"xml");
											})
											
									)
							)
							//.append($('<span>').html('&nbsp;'))
							.append(
								$('<span>')
									.attr({'id' : 'span-unpublish-all-schoolyear-allocations'})
									.css({'padding':'3px'})
									.append(
										$('<a>')
											.attr({'href':'#'})
											.attr({'class':'btn btn-xs btn-warning'})											
											.text('Unpublish All')
											.click(function(){
												$("#loadingSpinner").css("display","block");
												$.post("ajax/updateTeacherAllocationPublished.html", 
													{	
														schoolyear : $('#lst_schoolyear').val(),
														published : false,
														ajax : true 
													}, 
													function(data){
														$('#span-unpublish-all-schoolyear-allocations').children().remove();
														$('#span-unpublish-all-schoolyear-allocations')
															.css({'color' : 'red', 'font-weight' : 'bold'})
															.text($(data).find('UPDATE-TEACHER-ALLOCATION-PUBLISHED-RESPONSE').attr('msg'));
														$("#loadingSpinner").css("display","none");
													}, 
													"xml");
											})
											
									)
							)
							//.append($('<span>').html('&nbsp;'))
							.append(
								$('<span>')
									.attr({'id' : 'span-forward-schoolyear-allocations'})
									.css({'padding':'3px'})
									.append(
										$('<a>')
											.attr({'href':'#'})
											.attr({'class':'btn btn-xs btn-primary'})
											.text('Forward')
											.click(function(){
												$("#loadingSpinner").css("display","block");
												$.post("ajax/forwardTeacherAllocations.html", 
													{	
														schoolyear : $('#lst_schoolyear').val(),
														ajax : true 
													}, 
													function(data){
														$('#span-forward-schoolyear-allocations').children().remove();
														$('#span-forward-schoolyear-allocations')
															.css({'color' : 'red', 'font-weight' : 'bold'})
															.text($(data).find('FORWARD-TEACHER-ALLOCATION-RESPONSE').attr('msg'));
														$("#loadingSpinner").css("display","none");
													}, 
													"xml");
											})
											
									)
							)
					);
			}
			else if($('#lst_schoolyear').val() == '') {
				$('#span-schoolyear-ops').remove();
			}
		}
		
		$('#div-location-status').remove();
		
		if(($('#lst_schoolyear').val() != '') && ($('#lst_school').val() != '-1')) {
			loadTeacherAllocation($('#lst_schoolyear').val(), $('#lst_school').val());
		}
	});
	
	$('#btn-add-allocation').click(function() {
		$("#loadingSpinner").css("display","block");
		$.post("ajax/addTeacherAllocation.html", 
			{	
				allocationid : $('#hdn-allocation-id').val(),
				schoolyear : $('#lst_schoolyear').val(),
				locationid : $('#lst_school').val(),
				runits : $('#txt_regUnits').val(),
				aunits : $('#txt_administrative').val(),
				gunits : $('#txt_guidance').val(),
				sunits : $('#txt_specialist').val(),
				lrtunits : $('#txt_lrt').val(),
				irt1units : $('#txt_irt1').val(),
				irt2units : $('#txt_irt2').val(),
				ounits : $('#txt_other').val(),
				tlaunits: $('#txt_tla').val(),
				sahours: $('#txt_sa').val(),
				rsunits: $('#txt_reading').val(),
				ajax : true 
			}, 
			function(data){
				$('#add-allocation-message').css('display','block').text($(data).find('ADD-TEACHER-ALLOCATION-RESPONSE').attr('msg'));
				if($(data).find('TEACHER-ALLOCATION-BEAN').length > 0) {
					parseTeacherAllocationBean(data);
					loadEmployeesByLocation(['#lst_teacher', '#lst_owner', '#lst_redundantTeacher'], $('#lst_schoolyear').val(), $('#lst_school').val());
					
					$('#btn-add-allocation').text('Update Allocation');
					$('.allocation-input').removeAttr('readonly');
					$('#btn-add-allocation').show()
				}
				else {
					$('#txt_regUnits')
						.removeAttr('readonly');
			
					$('#txt_administrative')
						.removeAttr('readonly');
					
					$('#txt_guidance')
						.removeAttr('readonly');
					
					$('#txt_specialist')
						.removeAttr('readonly');
					
					$('#txt_lrt')
						.removeAttr('readonly');
					
					$('#txt_irt1')
						.removeAttr('readonly');
					
					$('#txt_irt2')
						.removeAttr('readonly');
					
					$('#txt_other')
						.removeAttr('readonly');
					
					$('#txt_tla')
						.removeAttr('readonly');
					
					$('#txt_sa')
						.removeAttr('readonly');
					
					$('#txt_reading')
						.removeAttr('readonly');
					
					$('#btn-add-allocation').show();
					
					displayPositionAlloctionSections('hide');
				}
				
				$("#loadingSpinner").css("display","none");
			}, 
			"xml");
	});
	
	$('#btn-add-allocation-extra').click(function() {		
		$("#loadingSpinner").css("display","block");
		$.post("ajax/addTeacherAllocationExtra.html", 
			{	
				allocationid : $('#hdn-allocation-id').val(),
				eid : $('#hdn_extraID').val(),
				eunits : $('#txt_extraUnits').val(),
				rationale : $('#txt_extraUnitsRationale').val(),
				atype: $('#lst_extraUnitsType').val(),
				ajax : true 
			}, 
			function(data){
				$('#add-allocation-extra-message').css('display','block').text($(data).find('ADD-TEACHER-ALLOCATION-EXTRA-RESPONSE').attr('msg'));
				if($(data).find('TEACHER-ALLOCATION-BEAN').length > 0) {
					parseTeacherAllocationBean(data);
					
					$('#hdn_extraID').val('');
					$('#txt_extraUnits').val('');
					$('#txt_extraUnitsRationale').val('');
					$('#lst_extraUnitsType').val('1');
				}
				
				$("#loadingSpinner").css("display","none");
				$('#btn-add-allocation-extra').text('Add Adjustment');
				$('#btn-cancel-allocation-extra').hide();
			}, 
			"xml");
	});
	
	$('#btn-cancel-allocation-extra').click(function() {
		$('#hdn_extraID').val('');
		$('#txt_extraUnits').val('');
		$('#txt_extraUnitsRationale').val('');
		$('#lst_extraUnitsType').val('1');
		$('#btn-add-allocation-extra').text('Add Adjustment');
		$(this).hide();
	});
	
	$('#lst_teacher').change(function(){
		if($(this).val() != '') {
			var opt = $("#lst_teacher option[value='" + $(this).val() +"']");
			
			if(opt){
				$('#txt_tenure').val(opt.attr('tenure'));
				$('#txt_fte').val(opt.attr('fte'));
				$('#txt_assignment').val(opt.attr('position'));
			}
			else {
				$('#txt_tenure').val('');
				$('#txt_fte').val('');
				$('#txt_assignment').val('');
			}
		}
		else {
			$('#txt_tenure').val('');
			$('#txt_fte').val('');
			$('#txt_assignment').val('');
		}
	});
	
	$('#lst_redundantTeacher').change(function(){
		if($(this).val() != '') {
			var opt = $("#lst_redundantTeacher option[value='" + $(this).val() +"']");
			
			if(opt){
				$('#txt_redundancyUnit').val(opt.attr('fte'));
			}
			else {
				$('#txt_redundancyUnit').val(opt.attr('fte'));
			}
		}
		else {
			$('#txt_redundancyUnit').val(opt.attr('fte'));
		}
	});
	
	$('#lst_owner').change(function(){
		if($(this).val() != '') {
			var opt = $("#lst_owner option[value='" + $(this).val() +"']");
			
			if(opt){
				$('#txt_vacancyUnit').val(opt.attr('fte'));
			}
			else {
				$('#txt_vacancyUnit').val(opt.attr('fte'));
			}
		}
		else {
			$('#txt_vacancyUnit').val(opt.attr('fte'));
		}
	});
	
	$('#btn-add-permanent-position').click(function() {
		
		$("#loadingSpinner").css("display","block");
		$.post("ajax/addTeacherAllocationPermanentPosition.html", 
			{	
				allocationid : $('#hdn-allocation-id').val(),
				positionid : $('#hdn_PermPositionID').val(),
				empid : $('#lst_teacher').val(),
				csize : '0',
				assignment : $('#txt_assignment').val(),
				unit : $('#txt_fte').val(),
				tenur: $('#txt_tenure').val(),
				ajax : true 
			}, 
			function(data){
				$('#add-permanent-position-message').css('display','block').text($(data).find('ADD-TEACHER-ALLOCATION-PERMANENT-POSITION-RESPONSE').attr('msg'));
				if($(data).find('TEACHER-ALLOCATION-BEAN').length > 0) {
					parseTeacherAllocationBean(data);
					
					$('#hdn_PermPositionID').val('');
					$('#lst_teacher').val('-1');
					$('#txt_tenure').val('');
					$('#txt_assignment').val('');
					$('#txt_fte').val('');
				}
				
				$("#loadingSpinner").css("display","none");
				$('#btn-add-permanent-position').text('Add Position');
				$('#btn-cancel-permanent-position').hide();
			}, 
			"xml");
	});
	
	$('#btn-cancel-permanent-position').click(function() {
		$('#hdn_PermPositionID').val('');
		$('#lst_teacher').val('-1');
		$('#txt_classSize').val('');
		$('#txt_assignment').val('');
		$('#txt_unit').val('');
		$('#btn-add-permanent-position').text('Add Position');
		$(this).hide();
	});
	
	$('#btn-add-vacancy-position').click(function() {
		
		$("#loadingSpinner").css("display","block");
		$.post("ajax/addTeacherAllocationVacantPosition.html", 
			{	
				allocationid : $('#hdn-allocation-id').val(),
				positionid : $('#hdn_VacantPositionID').val(),
				jobdesc : $('#txt_jobDescription').val(),
				jobtype : $('#lst_type').val(),
				empid : $('#lst_owner').val(),
				reason : $('#txt_reasonVacancy').val(),
				termstart : $('#txt_startTermDate').val(),
				termend : $('#txt_endTermDate').val(),
				unit : $('#txt_vacancyUnit').val(),
				advertised : $('#chk_advertised').is(':checked'),
				filled : $('#chk_filled').is(':checked'),
				ajax : true 
			}, 
			function(data){
				$('#add-vacancy-position-message').css('display','block').text($(data).find('ADD-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE').attr('msg'));
				if($(data).find('TEACHER-ALLOCATION-BEAN').length > 0) {
					parseTeacherAllocationBean(data);
					
					$('#hdn_VacantPositionID').val('');
					$('#txt_jobDescription').val('');
					$('#lst_type').val('-1');
					$('#lst_owner').val('-1');
					$('#txt_reasonVacancy').val('');
					$('#txt_startTermDate').val('');
					$('#txt_endTermDate').val('');
					$('#txt_vacancyUnit').val('');
					$('#chk_advertised').removeAttr('checked');
					$('#chk_filled').removeAttr('checked');
					$('#divlinks').hide();
				}
				
				$("#loadingSpinner").css("display","none");
				$('#btn-add-vacancy-position').text('Add Position');
				$('#btn-cancel-vacancy-position').hide();
			}, 
			"xml");
	});
	
	$('#btn-cancel-vacancy-position').click(function() {
		$('#hdn_VacantPositionID').val('');
		$('#txt_jobDescription').val('');
		$('#lst_type').val('-1');
		$('#lst_owner').val('-1');
		$('#txt_reasonVacancy').val('');
		$('#txt_startTermDate').val('');
		$('#txt_endTermDate').val('');
		$('#txt_vacancyUnit').val('');
		$('#chk_advertised').removeAttr('checked');
		$('#chk_filled').removeAttr('checked');
		$('#btn-add-vacancy-position').text('Add Position');
		$('#divlinks').hide();
		$(this).hide();
	});
	
	$('#btn-add-redundancy-position').click(function() {
		$('#add-redundancy-position-message').html("<img src='images/ajax-loader-1.gif' align='left' border='0' />");
		$("#loadingSpinner").css("display","block");
		$.post("ajax/addTeacherAllocationRedundantPosition.html", 
			{	
				positionid : $('#hdn_RedundantPositionID').val(),
				allocationid : $('#hdn-allocation-id').val(),
				empid : $('#lst_redundantTeacher').val(),
				rationale : $('#txt_redundancyRationale').val(),
				unit : $('#txt_redundancyUnit').val(),
				ajax : true 
			}, 
			function(data){
				$('#add-redundancy-position-message').text($(data).find('ADD-TEACHER-ALLOCATION-REDUNDANT-POSITION-RESPONSE').attr('msg'));
				if($(data).find('TEACHER-ALLOCATION-BEAN').length > 0) {
					parseTeacherAllocationBean(data);
					
					$('#hdn_RedundantPositionID').val('');
					$('#lst_redundantTeacher').val('-1');
					$('#txt_redundancyRationale').val('');
					$('#txt_redundancyUnit').val('');
				}
				
				$("#loadingSpinner").css("display","none");
				$('#btn-add-redundancy-position').text('Add Position');
				$('#btn-cancel-redundancy-position').hide()
			}, 
			"xml");
	});
	
	$('#btn-cancel-redundancy-position').click(function() {
		$('#hdn_RedundantPositionID').val('');
		$('#lst_redundantTeacher').val('-1');
		$('#txt_redundancyRationale').val('');
		$('#txt_redundancyUnit').val('');
		$('#btn-add-redundancy-position').text('Add Position');
		$(this).hide();
	});
	$('#btnDeleteVac').click(function() {
		$('#add-vacancy-position-message').css('display','none').text('');
			$("#loadingSpinner").css("display","block");										
			$.post("ajax/deleteTeacherAllocationVacantPosition.html", 
				{	
					id : $('#hidid').val(),
					ajax : true 
				}, 
				function(data){
					$('#add-vacancy-position-message').css('display','block').text($(data).find('DEL-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE').attr('msg'));
					if($(data).find('TEACHER-ALLOCATION-BEAN').length > 0) {
						parseTeacherAllocationBean(data);
						
						$('#hdn_VacantPositionID').val('');
						$('#txt_jobDescription').val('');
						$('#lst_type').val('-1');
						$('#lst_owner').val('-1');
						$('#txt_reasonVacancy').val('');
						$('#txt_startTermDate').val('');
						$('#txt_endTermDate').val('');
						$('#txt_vacancyUnit').val('');
						$('#chk_advertised').removeAttr('checked');
						$('#chk_filled').removeAttr('checked');
						$('#divlinks').hide();
					}
					
					$("#loadingSpinner").css("display","none");
				}, 
				"xml");
			$('#modalDelete').modal('hide');
			return false;
		
	});
	
});
	
function parseTeacherAllocationBean(data) {
	
	$(data).find('TEACHER-ALLOCATION-BEAN').each(function(){
		var allocationId = parseInt($(this).attr('ALLOCATION-ID'));
		var isEditable = ($(this).attr('ENABLED') === 'true' ? true : false);
		var isPublished = ($(this).attr('PUBLISHED') === 'true' ? true : false);
		
		$('#div-location-status').remove();
		$('#div_locationOptions').append(
			$('<div>')
				.attr({
					'id' : 'div-location-status'
				})
				.text('STATUS: ')
				.css({					
					'font-weight':'bold',
					'font-size' : '12px'
				})				
				
				.append(
					$('<span>')
						.attr({'id' : 'span-update-location-status'})
						.css({							
							'padding-left' : '5px',
						})
						.append(
							$('<a>')
								.css({
									'background-color' : (isEditable ? 'GREEN' : 'RED'),
									'font-weight' : 'bold',
									'color' : 'WHITE',									
									'font-weight' : 'normal',
									'font-size' : '12px',
									'padding' : '2px',				
									'text-decoration' : 'none',
									'text-transform' : 'uppercase'
								})
								.attr({'href' : '#', 'ENABLED' : isEditable})															
								.text(isEditable ? ' ACTIVE ' : ' LOCKED ')
								.click(function(){
									var _self = $(this);
									if(confirm('Are you sure you want to ' + (!($(this).attr('ENABLED') === 'true') ? 'ACTIVATE' : 'LOCK') + ' this allocation.')) {
										$("#loadingSpinner").css("display","block");
										$.post("ajax/updateTeacherAllocationStatusById.html", 
											{	
												allocationid : allocationId,
												enabled : !($(this).attr('ENABLED') === 'true'),
												ajax : true 
											}, 
											function(data){
												var enabled = ($(data).find('UPDATE-TEACHER-ALLOCATION-STATUS-RESPONSE').attr('msg') == 'enabled')
												$('#span-update-location-status a')
													.css({
														'color' : (enabled ? 'GREEN' : 'RED'),
													})
													.attr({'ENABLED' : enabled})
													.text(enabled ? ' ACTIVE ' : ' LOCKED ');
												$("#loadingSpinner").css("display","none");
											}, 
											"xml");
									}
								})
						)
				)
				.append(
					$('<span>')
						.css({
							'font-weight' : 'bold',
							'padding-left' : '5px',
							'padding-right' : '5px'
						})
						.text(' ')
				)
				.append(
					$('<span>')
						.attr({'id' : 'span-update-location-published'})
						.css({
							'width' : '50%',
						})
						.append(
							$('<a>')
								.css({
									'color' : 'WHITE',
									'background-color' : (isPublished ? 'GREEN' : 'RED'),
									'font-weight' : 'normal',
									'font-size' : '12px',
									'padding' : '2px',
									'text-decoration' : 'none',
									'text-transform' : 'uppercase'
								})
								.attr({'href' : '#', 'PUBLISHED' : isPublished})
																
								.text(isPublished ? ' PUBLISHED ' : ' NOT PUBLISHED ')
								.click(function(){
									var _self = $(this);
									if(confirm('Are you sure you want to ' + (!($(this).attr('PUBLISHED') === 'true') ? 'PUBLISHED' : 'UNPUBLISHED') + ' this allocation.')) {
										$("#loadingSpinner").css("display","block");
										$.post("ajax/updateTeacherAllocationPublishedById.html", 
											{	
												allocationid : allocationId,
												published : !($(this).attr('PUBLISHED') === 'true') ,
												ajax : true 
											}, 
											function(data){
												var published = ($(data).find('UPDATE-TEACHER-ALLOCATION-PUBLISHED-RESPONSE').attr('msg') == 'published')
												$('#span-update-location-published a')
													.css({
														'color' : (published ? 'GREEN' : 'RED'),
													})
													.attr({'PUBLISHED' : published})
													.text($(data).find('UPDATE-TEACHER-ALLOCATION-PUBLISHED-RESPONSE').attr('msg'));
												
												if(isPositionPlanningAdmin && !published) {
													$('.allocation-input').removeAttr('readonly');
													$('#btn-add-allocation')
														.text('Update Allocation')
														.show();
												}
												else {
													$('.allocation-input').attr('readonly', 'readonly');
													$('#btn-add-allocation').hide()
												}
												
												$("#loadingSpinner").css("display","none");
											}, 
											"xml");
									}
								})
						)
						
				)
		);
		
		if(!isSystemAdministrator) {
			$('#div-location-status a')
				.prop("disabled", true)
				.unbind("click");
		}
		
		$('#hdn-allocation-id').val($(this).attr('ALLOCATION-ID'))
		$('#school-allocation-units').text('School Allocation Units: ' + parseFloat($(this).attr('SCHOOL-ALLOCATIONS')).toFixed(2));
		$('.school-allocation-units-title').text(parseFloat($(this).attr('SCHOOL-ALLOCATIONS')).toFixed(2));
		
		$('#total-tchr-allocation-units').text('Total TCHR Allocation Units: ' + parseFloat($(this).attr('TOTAL-TCHR-ALLOCATIONS')).toFixed(2));
		$('#total-tla-allocation-units').text('Total TLA Allocation Units: ' + parseFloat($(this).attr('TOTAL-TLA-ALLOCATIONS')).toFixed(2));
		$('#total-sa-allocation-units').text('Total SA Allocation Hours: ' + parseFloat($(this).attr('TOTAL-SA-ALLOCATIONS')).toFixed(2));
		
		$('.total-tchr-allocation-units-title').text(parseFloat($(this).attr('TOTAL-TCHR-ALLOCATIONS')).toFixed(2));
		$('.total-tla-allocation-units-title').text(parseFloat($(this).attr('TOTAL-TLA-ALLOCATIONS')).toFixed(2));
		$('.total-sa-allocation-units-title').text(parseFloat($(this).attr('TOTAL-SA-ALLOCATIONS')).toFixed(2));
		
		$('#total-staffing-allocation-units').text('Total Assigned School Staffing Units: ' + parseFloat($(this).attr('TOTAL-STAFFING-UNITS')).toFixed(2));
		$('#total-outstanding-assigned-units').text('Total Outstanding School Staffing Units: ' + parseFloat($(this).attr('OUTSTANDING-ASSIGNMENT-UNITS')).toFixed(2));
		
		$('.total-staffing-allocation-units-title').text(parseFloat($(this).attr('TOTAL-STAFFING-UNITS')).toFixed(2));
		totalStaffing = parseFloat($(this).attr('TOTAL-STAFFING-UNITS')).toFixed(2);
		$('.total-outstanding-assigned-units-title').text(parseFloat($(this).attr('OUTSTANDING-ASSIGNMENT-UNITS')).toFixed(2));
		
		$('#total-outstanding-assigned-units').removeClass();
		if(parseFloat($(this).attr('OUTSTANDING-ASSIGNMENT-UNITS')) == +(0))
			//$('#total-outstanding-assigned-units').addClass('total-allocation-outstanding-good');
			$('#total-outstanding-assigned-units').addClass('alert alert-success');
		else
			//$('#total-outstanding-assigned-units').addClass('total-allocation-outstanding-bad');
			$('#total-outstanding-assigned-units').addClass('alert alert-danger');
		$('#txt_regUnits')
			.val(parseFloat($(this).attr('REGULAR-UNITS')).toFixed(2))
			.attr('readonly', 'readonly');
			numREGULARgraphing = parseFloat(((parseFloat($(this).attr('REGULAR-UNITS')).toFixed(2)/totalStaffing)) * 100).toFixed(2);
		
		$('#txt_administrative')
			.val(parseFloat($(this).attr('ADMINISTRATIVE-UNITS')).toFixed(2))
			.attr('readonly', 'readonly');
			numADMINgraphing = parseFloat(((parseFloat($(this).attr('ADMINISTRATIVE-UNITS')).toFixed(2)/totalStaffing)) * 100).toFixed(2); 				
				
		
		$('#txt_guidance')
			.val(parseFloat($(this).attr('GUIDANCE-UNITS')).toFixed(2))
			.attr('readonly', 'readonly');
			numGUIDANCEgraphing = parseFloat(((parseFloat($(this).attr('GUIDANCE-UNITS')).toFixed(2)/totalStaffing)) * 100).toFixed(2); 	 
							
		
		$('#txt_specialist')
			.val(parseFloat($(this).attr('SPECIALIST-UNITS')).toFixed(2))
			.attr('readonly', 'readonly');
			numSPECIALISTgraphing = parseFloat(((parseFloat($(this).attr('SPECIALIST-UNITS')).toFixed(2)/totalStaffing)) * 100).toFixed(2); 				
		
		$('#txt_lrt')
			.val(parseFloat($(this).attr('LRT-UNITS')).toFixed(2))
			.attr('readonly', 'readonly');
			numLRTgraphing = parseFloat(((parseFloat($(this).attr('LRT-UNITS')).toFixed(2)/totalStaffing)) * 100).toFixed(2);  				
		
		$('#txt_irt1')
			.val(parseFloat($(this).attr('IRT1-UNITS')).toFixed(2))
			.attr('readonly', 'readonly');		
			numIRT1graphing = parseFloat(((parseFloat($(this).attr('IRT1-UNITS')).toFixed(2)/totalStaffing)) * 100).toFixed(2); 							
		
		$('#txt_irt2')
			.val(parseFloat($(this).attr('IRT2-UNITS')).toFixed(2))
			.attr('readonly', 'readonly');		
			numIRT2graphing = parseFloat(((parseFloat($(this).attr('IRT2-UNITS')).toFixed(2)/totalStaffing)) * 100).toFixed(2);  							
		
		$('#txt_other')
			.val(parseFloat($(this).attr('OTHER-UNITS')).toFixed(2))
			.attr('readonly', 'readonly');		
			numOTHERgraphing = parseFloat(((parseFloat($(this).attr('OTHER-UNITS')).toFixed(2)/totalStaffing)) * 100).toFixed(2);
		
		$('#txt_tla')
			.val(parseFloat($(this).attr('TLA-UNITS')).toFixed(2))
			.attr('readonly', 'readonly');		   
			numTLAgraphing = parseFloat(((parseFloat($(this).attr('TLA-UNITS')).toFixed(2)/totalStaffing)) * 100).toFixed(2);
		
		$('#txt_sa')
			.val(parseFloat($(this).attr('STUDENT-ASSISTANT-HOURS')).toFixed(2))
			.attr('readonly', 'readonly');		
			numSAgraphing = parseFloat(((parseFloat($(this).attr('STUDENT-ASSISTANT-HOURS')).toFixed(2)/totalStaffing)) * 100).toFixed(2);
		
		$('#txt_reading')
			.val(parseFloat($(this).attr('READING-SPECIALIST-UNITS')).toFixed(2))
			.attr('readonly', 'readonly');
			numREADINGgraphing = parseFloat(((parseFloat($(this).attr('READING-SPECIALIST-UNITS')).toFixed(2)/totalStaffing)) * 100).toFixed(2); 			
		
		if(isPositionPlanningAdmin && !isPublished) {
			$('.allocation-input').removeAttr('readonly');
		}
		
		//load extra adjustments
		$('#extra-allocation-table .extra-allocation-table-row').remove();
		if($(this).find('TEACHER-ALLOCATION-EXTRA-BEAN').length > 0) {
			$('#total-extra-allocation-units').css('display','block').text('Total Other Units: ' 
				+ parseFloat($(this).find('TEACHER-ALLOCATION-EXTRA-BEANS').attr('TOTAL-ALLOCATIONS')).toFixed(2));			
			$('.total-extra-allocation-units-title').text(parseFloat($(this).find('TEACHER-ALLOCATION-EXTRA-BEANS').attr('TOTAL-ALLOCATIONS')).toFixed(2));			
			$(this).find('TEACHER-ALLOCATION-EXTRA-BEAN').each(function(){
				$('#extra-allocation-table')
					.append($('<tr>')
						.addClass('extra-allocation-table-row')						
						.append($('<td>').text(parseFloat($(this).attr('EXTRA-UNITS')).toFixed(2)))
						.append($('<td>').text($(this).attr('ALLOCATION-TYPE-CODE')))
						.append($('<td>').text($(this).attr('CREATED-DATE')))
						.append($('<td>').text($(this).attr('RATIONALE')))
						.append($('<td>')
							.addClass('extra-allocation-table-row-operations')		
							.append($('<a>')
								.addClass('edit-extra btn btn-xs btn-info')
								.attr({'href' : '#', 'extra-id' : $(this).attr('EXTRA-ID')})
								.text('EDIT')								
								.click(
									function() {
										
										$("#loadingSpinner").css("display","block");
										$.post("ajax/editTeacherAllocationExtra.html", 
											{	
												id : $(this).attr('extra-id'),
												ajax : true 
											}, 
											function(data){
												if($(data).find('TEACHER-ALLOCATION-EXTRA-BEAN').length > 0) {
													$(data).find('TEACHER-ALLOCATION-EXTRA-BEAN').each(function() {
														$('#hdn_extraID').val($(this).attr('EXTRA-ID'));
														$('#txt_extraUnits').val(parseFloat($(this).attr('EXTRA-UNITS')).toFixed(2));
														$('#lst_extraUnitsType').val($(this).attr('ALLOCATION-TYPE-ID'));
														$('#txt_extraUnitsRationale').val($(this).attr('RATIONALE'));														
														$('#btn-add-allocation-extra').text('Update Adjustment');
														$('#btn-cancel-allocation-extra').show();
													});
												}
												
												$("#loadingSpinner").css("display","none");
												$('#add-allocation-extra-message').text('').css('display','none');
											}, 
											"xml");
										return false;
									}
								))							
							.append($('<a>')
								.addClass('del-extra btn btn-xs btn-danger')
								.attr({'href' : '#', 'extra-id' : $(this).attr('EXTRA-ID')})
								.text('DEL')
								.css({'margin-left':'3px'})
								.click(
									function() {
										$('#add-allocation-extra-message').text('').css('display','none');
										$("#loadingSpinner").css("display","block");										
										$.post("ajax/deleteTeacherAllocationExtra.html", 
											{	
												id : $(this).attr('extra-id'),
												ajax : true 
											}, 
											function(data){
												$('#add-allocation-extra-message').css('display','block').text($(data).find('DEL-TEACHER-ALLOCATION-EXTRA-RESPONSE').attr('msg'));
												if($(data).find('TEACHER-ALLOCATION-BEAN').length > 0) {
													parseTeacherAllocationBean(data);													
													$('#hdn_extraID').val('');
													$('#txt_extraUnits').val('');
													$('#lst_extraUnitsType').val('1');
													$('#txt_extraUnitsRationale').val('');
												}
												
												$("#loadingSpinner").css("display","none");
											}, 
											"xml");
										return false;
									}
								))
						)
					);
			});
			
			$('#extra-allocation-table .extra-allocation-table-row:odd').css({'background-color': '#f0f0f0'});
			$('#extra-allocation-table .extra-allocation-table-row:not(:last) td').css({'border-bottom' : 'solid 1px #D0D0D0'});
			
			$('#extra-allocation-table')
				.append($('<tr>')
					.addClass('extra-allocation-table-row')
					
					.append($('<td>')
						.attr('colspan', '5')
						.css({'font-weight':'bold', 'border-top':'solid 1px #333333'})
						.text('TOTAL: ' + parseFloat($(this).find('TEACHER-ALLOCATION-EXTRA-BEANS').attr('TOTAL-ALLOCATIONS')).toFixed(2))));
		}
		else {
			$('#extra-allocation-table')
				.append($('<tr>')
					.addClass('extra-allocation-table-row')
					.append($('<td>')
						.attr('colspan', '5')
						.text('No extra allocations added.')));
			
			$('#total-extra-allocation-units').css('display','block').text('Total Other Units: 0.00');
			$('.total-extra-allocation-units-title').text('0.00');
		}
		
		//only admin can add adjustments.
		if(!isPositionPlanningAdmin){
			$('#extra-allocation-table .extra-allocation-table-row .extra-allocation-table-row-operations').children().remove();
			$('#extra-allocation-addition-table').hide();
		}
		
		//load permanent position
		$('#permanent-positions-table .permanent-positions-table-row').remove();
		if($(this).find('TEACHER-ALLOCATION-PERMANENT-POSITION-BEAN').length > 0) {
			$('#total-permanent-position-units').text('Permanent Position Total Units: ' 
				+ $(this).find('TEACHER-ALLOCATION-PERMANENT-POSITION-BEANS').attr('TOTAL-ALLOCATIONS'));
			$('.total-permanent-position-units-title').text($(this).find('TEACHER-ALLOCATION-PERMANENT-POSITION-BEANS').attr('TOTAL-ALLOCATIONS'));
			permanentPositionsGraphVar = parseFloat($(this).find('TEACHER-ALLOCATION-PERMANENT-POSITION-BEANS').attr('TOTAL-ALLOCATIONS'));			
			$(this).find('TEACHER-ALLOCATION-PERMANENT-POSITION-BEAN').each(function(){
				$('#permanent-positions-table')
					.append($('<tr>')
						.addClass('permanent-positions-table-row')
						.addClass('displayText' + ((parseFloat($(this).attr('UNIT')) <= 0) ? ' redundant-position' : ''))
						.append($('<td>').text($(this).attr('EMP-NAME')))
						.append($('<td>').text(parseFloat($(this).attr('SENIORITY-1')).toFixed(2)))
						.append($('<td>').text($(this).attr('TENUR')))
						.append($('<td>').text($(this).attr('ASSIGNMENT')))
						.append($('<td>').text(parseFloat($(this).attr('UNIT')).toFixed(2)))
						.append($('<td>')
							.css({'padding-right':'5px'})							
							.addClass('permanent-positions-table-row-operations')
							.append($('<a>')
								.addClass('edit-perm btn btn-xs btn-info')
								.attr({'href' : '#', 'position-id' : $(this).attr('POSITION-ID')})
								.text('EDIT')
								.click(
									function() {										
										$("#loadingSpinner").css("display","block");
										$.post("ajax/editTeacherAllocationPermanentPosition.html", 
											{	
												id : $(this).attr('position-id'),
												ajax : true 
											}, 
											function(data){
												if($(data).find('TEACHER-ALLOCATION-PERMANENT-POSITION-BEAN').length > 0) {
													$(data).find('TEACHER-ALLOCATION-PERMANENT-POSITION-BEAN').each(function() {
														$('#hdn_PermPositionID').val($(this).attr('POSITION-ID'));
														$('#lst_teacher').val($(this).attr('EMP-ID'));
														$('#txt_tenure').val($(this).attr('TENUR'));
														$('#txt_assignment').val($(this).attr('ASSIGNMENT'));
														$('#txt_fte').val(parseFloat($(this).attr('UNIT')).toFixed(2));
														
														$('#btn-add-permanent-position').text('Update Position');
														$('#btn-cancel-permanent-position').show();
													});
												}
												
												$("#loadingSpinner").css("display","none");
												$('#add-permanent-position-message').css('display','none').text('');
											}, 
											"xml");
										return false;
									}
								))
							
							.append($('<a>')
								.addClass('del-perm btn btn-xs btn-danger')
								.attr({'href' : '#', 'position-id' : $(this).attr('POSITION-ID')})
								.text('DEL')
								.css({'margin-left':'3px'})
								.click(
									function() {
										$('#add-permanent-position-message').css('display','none').text('');
										$("#loadingSpinner").css("display","block");										
										$.post("ajax/deleteTeacherAllocationPermanentPosition.html", 
											{	
												id : $(this).attr('position-id'),
												ajax : true 
											}, 
											function(data){
												$('#add-permanent-position-message').css('display','block').text($(data).find('DEL-TEACHER-ALLOCATION-PERMANENT-POSITION-RESPONSE').attr('msg'));
												if($(data).find('TEACHER-ALLOCATION-BEAN').length > 0) {
													parseTeacherAllocationBean(data);
													
													$('#hdn_PermPositionID').val('');
													$('#lst_teacher').val('-1');
													$('#txt_classSize').val('');
													$('#txt_assignment').val('');
													$('#txt_unit').val('');
												}
												
												$("#loadingSpinner").css("display","none");
											}, 
											"xml");
										return false;
									}
								))
						)
					);
			});
			
			//$('#permanent-positions-table .permanent-positions-table-row:odd').css({'background-color': '#f0f0f0'});
			//$('#permanent-positions-table .permanent-positions-table-row:not(:last) td').css({'border-bottom' : 'solid 1px #D0D0D0'});
			$('#permanent-positions-table .redundant-position').addClass('danger');
			$('#permanent-positions-table .redundant-position td:not(.permanent-positions-table-row-operations)').css({'color': '#FF0000'});
			
			$('#permanent-positions-table')
				.append($('<tr>')
					.addClass('permanent-positions-table-row')
					.addClass('displayText')
					.append($('<td>')
						.attr('colspan', '4')
						.css({'border-top':'solid 1px #333333'})
						.html('&nbsp;'))
					.append($('<td>')
						.attr('colspan', '2')
						.css({'font-weight':'bold', 'border-top':'solid 1px #333333'})
						.text(parseFloat($(this).find('TEACHER-ALLOCATION-PERMANENT-POSITION-BEANS').attr('TOTAL-ALLOCATIONS')).toFixed(2))));
		}
		else {
			$('#permanent-positions-table')
				.append($('<tr>')
					.addClass('permanent-positions-table-row')
					.addClass('displayText')
					.append($('<td>')
						.attr('colspan', '6')
						.text('No permanent positions added.')));
			
			$('#total-permanent-position-units').text('Permanent Position Total Units: 0.00');
			$('.total-permanent-position-units-title').text('0.00');
		}
		
		if(!isPositionPlanningAdmin && !isEditable){
			$('.permanent-positions-table-row-operations').empty();
			$('#permanent-positions-add-edit').hide();
		}
		else
			$('#permanent-positions-add-edit').show();
		
		//load vacant position
		$('#vacant-positions-table .vacant-positions-table-row').remove();
		if($(this).find('TEACHER-ALLOCATION-VACANT-POSITION-BEAN').length > 0) {
			$('#total-vacancy-position-units').text('Vacant Position Total Units: ' 
				+ $(this).find('TEACHER-ALLOCATION-VACANT-POSITION-BEANS').attr('TOTAL-ALLOCATIONS'));
			$('.total-vacancy-position-units-title').text($(this).find('TEACHER-ALLOCATION-VACANT-POSITION-BEANS').attr('TOTAL-ALLOCATIONS'));
			
		 vacantPositionsGraphVar = parseFloat($(this).find('TEACHER-ALLOCATION-VACANT-POSITION-BEANS').attr('TOTAL-ALLOCATIONS'));	
			
			$(this).find('TEACHER-ALLOCATION-VACANT-POSITION-BEAN').each(function(){
				$('#vacant-positions-table')
					.append($('<tr>')
						.addClass('vacant-positions-table-row')
						.addClass('displayText')
						.append($('<td>').attr('valign', 'top').text($(this).attr('JOB-DESCRIPTION')))
						.append($('<td>').attr('valign', 'top').text($(this).attr('TYPE-DESCRIPTION')))
						.append($('<td>').html(($.type($(this).attr('EMP-NAME'))!='undefined' ? $(this).attr('EMP-NAME') : 'Vacant - No Owner') + '<br /><i>' + $(this).attr('VACANCY-REASON') + '</i>'))
						.append($('<td>').attr({'valign':'top','align':'center'})
										 .html(($.type($(this).attr('TERM-START'))!='undefined' ? $(this).attr('TERM-START') : '') + '<br />' 
											+ ($.type($(this).attr('TERM-END'))!='undefined' ? $(this).attr('TERM-END') : '')))
						.append($('<td>').attr('valign', 'top').text(parseFloat($(this).attr('UNIT')).toFixed(2)))
						.append($('<td>').attr({'valign':'top','align':'center'}).text(($(this).attr('ADVERTISED') == 'false' ? 'NO' : 'YES')))
						.append($('<td>').attr({'valign':'top','align':'center'}).text(($(this).attr('FILLED') == 'false' ? 'NO' : 'YES')))
						.append($('<td>')
							.css({'padding-right':'5px'})
							.attr({'align':'right','valign':'top'})
							.addClass('vacant-positions-table-row-operations')
							.append($('<a>')
								.addClass('edit-vacant btn btn-xs btn-info')
								.attr({'href' : '#', 'position-id' : $(this).attr('POSITION-ID')})
								.text('EDIT')
								.click(
									function() {
										
										$("#loadingSpinner").css("display","block");
										$.post("ajax/editTeacherAllocationVacantPosition.html", 
											{	
												id : $(this).attr('position-id'),
												ajax : true 
											}, 
											function(data){
												if($(data).find('TEACHER-ALLOCATION-VACANT-POSITION-BEAN').length > 0) {
													$(data).find('TEACHER-ALLOCATION-VACANT-POSITION-BEAN').each(function() {
														$('#hdn_VacantPositionID').val($(this).attr('POSITION-ID'));
														$('#txt_jobDescription').val($(this).attr('JOB-DESCRIPTION'));
														$('#lst_type').val($(this).attr('TYPE-ID'));
														$('#lst_owner').val(($.type($(this).attr('EMP-ID'))!='undefined' ? $(this).attr('EMP-ID') : '0'));
														$('#txt_reasonVacancy').val(Encoder.htmlDecode($(this).attr('VACANCY-REASON')));
														$('#txt_startTermDate').val($(this).attr('TERM-START'));
														$('#txt_endTermDate').val($(this).attr('TERM-END'));
														$('#txt_vacancyUnit').val(parseFloat($(this).attr('UNIT')).toFixed(2));
														
														if($(this).attr('ADVERTISED') == 'true')
															$('#chk_advertised').attr('checked', 'checked');
														else
															$('#chk_advertised').removeAttr('checked');
														
														if($(this).attr('FILLED') == 'true')
															$('#chk_filled').attr('checked', 'checked');
														else
															$('#chk_filled').removeAttr('checked');
														
														$('#btn-add-vacancy-position').text('Update Position');
														$('#btn-cancel-vacancy-position').show();
														//now show correct link
														if($(this).attr('RECLINK') != 'NONE'){
															$('#hrefspan').text('View Recommedation');
															$('#hrefad').prop('href', $(this).attr('RECLINK'));
															$('#divlinks').show();
														}else if($(this).attr('JOBLINK') != 'NONE'){
															$('#hrefspan').text('View Job Opportunity');
															$('#hrefad').prop('href', $(this).attr('JOBLINK'));
															$('#divlinks').show();
														}else if($(this).attr('ADLINK') != 'NONE'){
															$('#hrefspan').text('View  Ad Request');
															$('#hrefad').prop('href', $(this).attr('ADLINK'));
															$('#divlinks').show();
														}else if($(this).attr('CREATELINK') != 'NONE'){
															$('#hrefspan').text('Create Ad Request');
															$("#hrefad").click(function(){ createAdRequest(); });
															//$('#hrefad').prop('href', $(this).attr('CREATELINK'));
															$('#divlinks').show();
														}else{
															$('#hrefspan').text('Create Ad Request');
															//$('#hrefad').prop('href', $(this).attr('CREATELINK'));
															$('#divlinks').hide();
														}
													});
												}
												
												$("#loadingSpinner").css("display","none");
												$('#add-vacancy-position-message').css('display','none').text('');
											}, 
											"xml");
										return false;
									}
								))
							
							.append($('<a>')
								.addClass('del-vacant btn btn-xs btn-danger')
								.attr({'href' : '#', 'position-id' : $(this).attr('POSITION-ID'),'ad-title': $(this).attr('ADTITLE'),'compnum':$(this).attr('JOBCOMP')})
								.text('DEL')
								.css({'margin-left':'3px'})
								.click(
									function() {
										$('#modalDelete').modal('show');
											$('#hidid').val($(this).attr('POSITION-ID'));
											$('#modaltitle').text("Confirm Delete Vacancy");
											$('#deletemessage').text("Are you sure you want to delete this vacancy?");
											if($(this).attr('compnum') != "NONE"){
												$('#deletejob').text("The following Job Opportunity will be cancelled : " + $(this).attr('compnum'));
											}else if($(this).attr('ad-title') != "NONE"){
												$('#deletead').text("The following Ad Request will be cancelled : " + $(this).attr('ad-title'));
											}
											
										}
								))
						)
					);
			});
			
			//$('#vacant-positions-table .vacant-positions-table-row:odd').css({'background-color': '#f0f0f0'});
			$('#vacant-positions-table .vacant-positions-table-row:not(:last) td').css({'border-bottom' : 'solid 1px #D0D0D0'});
			
			$('#vacant-positions-table')
				.append($('<tr>')
					.addClass('vacant-positions-table-row')
					.addClass('displayText')
					.append($('<td>')
						.attr('colspan', '4')
						.css({'border-top':'solid 1px #333333'})
						.html('&nbsp;'))
					.append($('<td>')
						.attr('colspan', '4')
						.css({'font-weight':'bold', 'border-top':'solid 1px #333333'})
						.text(parseFloat($(this).find('TEACHER-ALLOCATION-VACANT-POSITION-BEANS').attr('TOTAL-ALLOCATIONS')).toFixed(2))));
		}
		else {
			$('#vacant-positions-table')
				.append($('<tr>')
					.addClass('vacant-positions-table-row')
					.addClass('displayText')
					.append($('<td>')
						.attr('colspan', '8')
						.text('No vacant positions added.')));
			
			$('#total-vacancy-position-units').text('Vacant Position Total Units: 0.00');
			$('.total-vacancy-position-units-title').text('0.00');
		}
		
		if(!isPositionPlanningAdmin && !isEditable){
			$('.vacant-positions-table-row-operations').empty();
			$('#vacant-positions-add-edit').hide();
		}
		else
			$('#vacant-positions-add-edit').show();
		
		//load redundant positions
		$('#redundant-positions-table .redundant-positions-table-row').remove();
		if($(this).find('TEACHER-ALLOCATION-REDUNDANT-POSITION-BEAN').length > 0) {
			$('#total-redundant-position-units').text('redundant position total units: ' 
				+ $(this).find('TEACHER-ALLOCATION-REDUNDANT-POSITION-BEANS').attr('TOTAL-ALLOCATIONS'));
			
			 redundantPositionsGraphVar = parseFloat($(this).find('TEACHER-ALLOCATION-REDUNDANT-POSITION-BEANS').attr('TOTAL-ALLOCATIONS'));			 
						 
				 $('.total-redundant-position-units-title').text($(this).find('TEACHER-ALLOCATION-REDUNDANT-POSITION-BEANS').attr('TOTAL-ALLOCATIONS')); 
			
				 			
			
			 
			$(this).find('TEACHER-ALLOCATION-REDUNDANT-POSITION-BEAN').each(function(){
				$('#redundant-positions-table')
					.append($('<tr>')
						.addClass('redundant-positions-table-row')
						.addClass('displayText')
						.append($('<td>').attr({'valign':'top'}).text($(this).attr('EMP-NAME')))
						.append($('<td>').attr({'valign':'top'}).text($(this).attr('RATIONALE')))
						.append($('<td>').attr({'valign':'top', 'align':'center'}).text(parseFloat($(this).attr('UNIT')).toFixed(2)))
						.append($('<td>')
							.css({'padding-right':'5px'})
							.attr({'valign':'top','align':'right'})
							.addClass('redundant-positions-table-row-operations')
							.append($('<a>')
								.addClass('edit-redundant btn btn-xs btn-info')
								.attr({'href' : '#', 'position-id' : $(this).attr('POSITION-ID')})
								.text('EDIT')
								.click(
									function() {
									
										$("#loadingSpinner").css("display","block");
										$.post("ajax/editTeacherAllocationRedundantPosition.html", 
											{	
												id : $(this).attr('position-id'),
												ajax : true 
											}, 
											function(data){
												if($(data).find('TEACHER-ALLOCATION-REDUNDANT-POSITION-BEAN').length > 0) {
													$(data).find('TEACHER-ALLOCATION-REDUNDANT-POSITION-BEAN').each(function() {
														$('#hdn_RedundantPositionID').val($(this).attr('POSITION-ID'));
														$('#lst_redundantTeacher').val($(this).attr('EMP-ID'));
														$('#txt_redundancyRationale').val($(this).attr('RATIONALE'));
														$('#txt_redundancyUnit').val(parseFloat($(this).attr('UNIT')).toFixed(2));
														
														$('#btn-add-redundancy-position').text('Update Position');
														$('#btn-cancel-redundancy-position').show();
													});
												}
												
												$("#loadingSpinner").css("display","none");
												$('#add-redundancy-position-message').text('');
											}, 
											"xml");
										return false;
									}
								))
							.append($('<span>').text(' | '))
							.append($('<a>')
								.addClass('del-redundant btn btn-xs btn-danger')
								.attr({'href' : '#', 'position-id' : $(this).attr('POSITION-ID')})
								.text('DEL')
								.click(
									function() {
										$('#add-redundancy-position-message').text('');
										$("#loadingSpinner").css("display","block");										
										$.post("ajax/deleteTeacherAllocationRedundantPosition.html", 
											{	
												id : $(this).attr('position-id'),
												ajax : true 
											}, 
											function(data){
												$('#add-redundancy-position-message').text($(data).find('DEL-TEACHER-ALLOCATION-REDUNDANT-POSITION-RESPONSE').attr('msg'));
												if($(data).find('TEACHER-ALLOCATION-BEAN').length > 0) {
													parseTeacherAllocationBean(data);
													
													$('#hdn_RedundantPositionID').val('');
													$('#lst_redundantTeacher').val('');
													$('#txt_redundancyRationale').val('');
													$('#txt_redundancyUnit').val('');
												}
												
												$("#loadingSpinner").css("display","none");
											}, 
											"xml");
										return false;
									}
								))
						)
					);
			});
			
			$('#redundant-positions-table .redundant-positions-table-row:odd').css({'background-color': '#f0f0f0'});
			$('#redundant-positions-table .redundant-positions-table-row:not(:last) td').css({'border-bottom' : 'solid 1px #D0D0D0'});
			
			$('#redundant-positions-table')
				.append($('<tr>')
					.addClass('redundant-positions-table-row')
					.addClass('displayText')
					.append($('<td>')
						.attr('colspan', '2')
						.css({'border-top':'solid 1px #333333'})
						.html('&nbsp;'))
					.append($('<td>')
						.attr('colspan', '2')
						.css({'font-weight':'bold', 'border-top':'solid 1px #333333'})
						.text(parseFloat($(this).find('TEACHER-ALLOCATION-REDUNDANT-POSITION-BEANS').attr('TOTAL-ALLOCATIONS')).toFixed(2))));
		}
		else {
			$('#redundant-positions-table')
				.append($('<tr>')
					.addClass('redundant-positions-table-row')
					.addClass('displayText')
					.append($('<td>')
						.attr('colspan', '4')
						.text('No redundant positions added.')));
			
			$('#total-redundant-position-units').text('redundant position total units: 0.00');
			 $('.total-redundant-position-units-title').text('0.00');
		}
		
		if(!isPositionPlanningAdmin && !isEditable){
			$('.redundant-positions-table-row-operations').empty();
			$('#redundant-positions-add-edit').hide();
		}
		else
			$('#redundant-positions-add-edit').show();
		
		displayPositionAlloctionSections('show');
	});
}

function loadEmployeesByLocation(selectors, sy, lid){
	
	var psy = $("#lst_schoolyear option[value='" + sy +"']").attr('PSY');
	
	$.post("ajax/loadEmployeesByLocation.html", 
		{	
			locationid : lid,
			schoolyear : psy,
			ajax : true 
		}, 
		function(data){
			if($(data).find('EMPLOYEE-BEAN').length > 0) {
				$.each(selectors, function(index, value) {
					$(value + ' .employee-bean').remove();
					$(data).find('EMPLOYEE-BEAN').each(function(){
						$(value)
							.append($('<option>')
								.addClass('employee-bean')
								.attr({
									'value' : $(this).attr('employee-id'),
									'fte' : $(this).attr('fte'),
									'tenure' : $(this).attr('tenure'),
									'position' : $(this).attr('position-description')
								 })
								.html($(this).attr('last-name') + ', ' + $(this).attr('first-name')));
					});
				});
			}
		}, 
		"xml");
}

function displayPositionAlloctionSections(fn){
	$('#school-allocation-header-row')[fn]();
	$('#extra-allocation-header-row')[fn]();
	$('#total-allocation-header-row')[fn]();
	$('#permanent-positions-header-row')[fn]();
	$('#vacant-positions-header-row')[fn]();
	$('#redundant-positions-header-row')[fn]();
	$('#total-staffing-allocation-header-row')[fn]();
	$('#outstanding-positions-header-row')[fn]();
}


function loadTeacherAllocation(sy, lid){
	$('#lst_schoolyear, #lst_school').prop( "disabled", true );	
	$("#loadingSpinner").css("display","block");
	if(!isPositionPlanningAdmin){
		//$('#div_schoolyear').children().remove();
		//$('#div_schoolyear').append($('<span>').text(sy));
		$('#lst_schoolyear').val(sy);
		
		//$('#div_location').children().remove();
		$('#lst_school').val(lid);
		$('#lst_school').hide();
		
		if($('#span-readonly-school').length <= 0) {
			$('#div_location').append(
				
			$('<input>').attr({			
				'id' : 'span-readonly-school',
				'name' : 'span-readonly-school',
				'class' : 'form-control',
				'readonly' : 'true'
			
			})
			
					
			//   $('<span>')
			//	.attr({
			//		'id' : 'span-readonly-school'
			//	})
			//	.css({
			//		'text-decoration' : 'none',
			//		'font-weight':'bold'
			//	})
			);
		}
		
		//$('#span-readonly-school').text(lid);
		$('#span-readonly-school').val(lid);
		
	}
	
	$.post("ajax/loadTeacherAllocation.html", 
		{ 
			schoolyear : sy,
			locationid : lid,
			ajax : true 
		}, 
		function(data){
			if($(data).find('TEACHER-ALLOCATION-BEAN').length > 0) {
				$('#lst_teacher').attr('location', $('#lst_school').val());
				
				parseTeacherAllocationBean(data);
				loadEmployeesByLocation(['#lst_teacher', '#lst_owner', '#lst_redundantTeacher'], sy, lid);
				
				var isPublished = ($(data).find('TEACHER-ALLOCATION-BEAN').first().attr('PUBLISHED') === 'true' ? true : false);
				
				$('#add-allocation-operation-table').show();
				
				if(isPositionPlanningAdmin && !isPublished) {
					$('#btn-add-allocation').text('Update Allocation');
					$('.allocation-input').removeAttr('readonly');
					$('#btn-add-allocation').show()
				}
				else {
					$('#btn-add-allocation').hide();
				}
				
				$('#add-allocation-message').css('display','none').text('');
				
				$('#lst_schoolyear, #lst_school').prop("disabled", false);
			}
			else {
				$('#hdn-allocation-id').val('');
				
				if(isPositionPlanningAdmin){
					$('#txt_regUnits')
						.val('')
						.removeAttr('readonly');
				
					$('#txt_administrative')
						.val('')
						.removeAttr('readonly');
					
					$('#txt_guidance')
						.val('')
						.removeAttr('readonly');
					
					$('#txt_specialist')
						.val('')
						.removeAttr('readonly');
					
					$('#txt_lrt')
						.val('')
						.removeAttr('readonly');
					
					$('#txt_irt1')
						.val('')
						.removeAttr('readonly');
					
					$('#txt_irt2')
						.val('')
						.removeAttr('readonly');
					
					$('#txt_other')
						.val('')
						.removeAttr('readonly');
					
					$('#txt_tla')
						.val('')
						.removeAttr('readonly');
					
					$('#txt_sa')
						.val('')
						.removeAttr('readonly');
					
					$('#txt_reading')
						.val('')
						.removeAttr('readonly');
					
					$('#add-allocation-operation-table').show();
					$('#btn-add-allocation').show();
				}
				else{
					$('#add-allocation-operation-table').hide();
					$('#btn-add-allocation').hide();
				}
				
				$('#add-allocation-message').css('display','block').text('No allocation units assigned.');
				
				displayPositionAlloctionSections('hide');
				
				$('#lst_schoolyear, #lst_school').prop( "disabled", false );
			}
			
			$('#add-allocation-row').show();
			$('#topSummaryPanel').css("display","block");
			
			
		//Graph	Summary Positions
			var ctx = document.getElementById("summaryChart").getContext('2d');
			var summaryChart = new Chart(ctx, {
			  type: 'pie',
			  data: {
			    labels: [ "Permanent Positions","Vacant Positions","Redundant Positions",],
			    datasets: [{
			      backgroundColor: ["#008000","#DC143C","#DCDCDC"],
			      data: [ 	permanentPositionsGraphVar,
			    	  		vacantPositionsGraphVar,      			
			    	  		redundantPositionsGraphVar]
			    }]
			  },
			  
			  options: {
				  	  
			      title: {
			         display: false,
			         fontSize: 12,
			         text: 'Positions Breakdown'
			     },
			     legend: {
			         display: true,
			         fontSize: 12,
			         position: 'left',

			     },
			     responsive: false
			 }

			  
			  
			});

			
			
			
			
			
			//End load Graph
			
			
			
			
			$("#loadingSpinner").css("display","none");			
		}, 
		"xml");
}
function createAdRequest(){
	$("#loadingSpinner").css("display","block");
	$.post("ajax/createVacantPositionAd.html", 
		{	
			id : $('#hdn_VacantPositionID').val(),
			ajax : true 
		}, 
		function(data){
			if($(data).find('TEACHER-ALLOCATION-VACANT-POSITION-BEAN').length > 0) {
				parseTeacherAllocationBean(data);
				
				$('#hdn_VacantPositionID').val('');
				$('#txt_jobDescription').val('');
				$('#lst_type').val('-1');
				$('#lst_owner').val('-1');
				$('#txt_reasonVacancy').val('');
				$('#txt_startTermDate').val('');
				$('#txt_endTermDate').val('');
				$('#txt_vacancyUnit').val('');
				$('#chk_advertised').removeAttr('checked');
				$('#chk_filled').removeAttr('checked');
				
				$('#add-vacancy-position-message').html('Ad Request for Vacant Position created successfully').show();
			}
			$("#loadingSpinner").css("display","none");
			$('#btn-add-vacancy-position').text('Add Position');
			$('#btn-cancel-vacancy-position').hide();
			$('#divlinks').hide();
			
		}, 
		"xml");
}

