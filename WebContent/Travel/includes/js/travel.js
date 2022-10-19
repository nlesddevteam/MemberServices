$( '.dropdown-menu .dropdown-toggle' ).on('click', function() {    
    var $el = $(this);
    var $parent = $el.offsetParent(".dropdown-menu");    
    if (!$el.next().hasClass("show")) {
        $el.parents('.dropdown-menu').first().find(".show").removeClass("show");
    }
    $el.next(".dropdown-menu").toggleClass("show").parent("li").toggleClass("show");
    
    $el.parents("li.nav-item.dropdown.show").on("hidden.bs.dropdown", function () {
        $(".dropdown-menu .show").removeClass("show");
    });    
    if (!$parent.parent().hasClass("navbar-nav")) {
        $el.next().css({"top":$el[0].offsetTop,"left":$parent.outerWidth()});
    }    
    return false;
});


function isNumber(evt) {
        evt = (evt) ? evt : window.event;
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if ( (charCode > 31 && charCode < 48) || charCode > 57 || charCode == 46) {
	   $(".decOnlyMsg").html("<span style='color:Red;'><b>ERROR:</b> Please round to nearest kilometer. No decimals.</span>").css("display","block").delay(4000).fadeOut();
         return false;
        }
        return true;
    }
function isNumberDec(evt)
{
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode != 46 && charCode > 31 
     && (charCode < 48 || charCode > 57)) {
   return false;
   }
   return true;
}  

$(document).ready(function () {	


//CHANGE FONT SIZE		   	
				var $affectedElements = $("div,p,span");
				maxFont = 4;
				minFont=-2;
				curFont=0;
				 
				$affectedElements.each( function(){
				  var $this = $(this);
				  $this.data("orig-size", $this.css("font-size") );
				});
				
				$("#btn-increase").click(function(){				
				 curFont++;
				 if(curFont<maxFont) {				 
				  changeFontSize(1);
				  } 	 
				});
				
				$("#btn-decrease").click(function(){			
				 curFont--;				
				 if(curFont>minFont) {				 
				  changeFontSize(-1);
				  }
				 });
				
				$("#btn-orig").click(function(){
				curFont=0;
				  $affectedElements.each( function(){
				        var $this = $(this);
				        $this.css( "font-size" , $this.data("orig-size") );
				   });
				});
				
				function changeFontSize(direction){
				    $affectedElements.each( function(){
				        var $this = $(this);				        
				        $this.css( "font-size" , parseInt($this.css("font-size"))+direction );
				    });
				};	

});



/************************************************
call ajax post to populate claims months based
on what year is selected
*************************************************/
function adjustMonths()
{
	$("#claim_month").empty();
	$('<option value="-1" SELECTED>SELECT MONTH</option>').appendTo("#claim_month");
	var isvalid=false;
	if( $("#claim_year").val()== 0 )
	{
		alert("PLEASE SELECT YEAR");
	}else{
		var id = $("#claim_year").val();
		$.ajax(
 			{
 				type: "POST",  
 				url: "getTravelClaimsMonth.html",
 				data: {
 					syear: id
 				}, 
 				success: function(xml){

   						$(xml).find('MONTH').each(function(){
 						//now add the items if any
   							if($(this).find("MESSAGE").text() == "LISTFOUND")
   		 					{
   								var option = new Option( $(this).find("MONTHNAME").text() + " " + $(this).find("SCHOOLYEAR").text(),$(this).find("MONTHNUMBER").text() );
   								$("#claim_month").append($(option));
   		 					}else{
   		 						//alert("ERROR GETTING TRAVEL CLAIM MONTHS");
   		 						return false;
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
	}
	return isvalid;
}
/************************************************
call user level validation and submit ajax post 
for adding new travel claim
*************************************************/
function submitnewtravelclaim()
{
	var claimtype = $('input:radio[name=claim_type]:checked').val();
	var datastring="";
	var selectedvalue="";
	$(".details_error_message").css("display","none");
	//monthly claim
	if(claimtype == "0"){
		if($("#supervisor_id option:selected").text() == "SELECT SUPERVISOR"){
			$("#tc_step1_error").html("<b><i class='fas fa-exclamation-triangle'></i> SUPERVISOR ERROR:</b> Please select your supervisor. If your supervisor is not listed, please contact your supervsior or admin.").css("display","block").delay(5000).fadeOut();
			$( "#supervisor_id").focus();
			return false;
		}
		
	if($("#claim_year option:selected").text() == "SELECT YEAR"){
			$("#tc_step2_error").html("<b><i class='fas fa-exclamation-triangle'></i> INVALID YEAR ERROR:</b> Please select the Year of this claim.").css("display","block").delay(5000).fadeOut();
			$( "#claim_year option").focus();
			return false;
		}
		
		if($("#claim_month option:selected").text() == "SELECT MONTH"){
			$("#tc_step3_error").html("<b><i class='fas fa-exclamation-triangle'></i> INVALID MONTH ERROR:</b> Please select the month of this claim. If the month you wish to select is not listed, it means you already have a claim started or submitted for that month. Check My Claims menu above.").css("display","block").delay(5000).fadeOut();
			$( "#claim_year option").focus();
			return false;
		}
		

		//dropdown might not exisit so we will take value from hidden field
		var selectedvalue="";
		if ($("#supervisor_id").length > 1){
			selectedvalue=$("#supervisor_id option:selected").val();
		}else{
			selectedvalue=$("#supervisor_id").val(); 
		}
		datastring = "supervisior=" + selectedvalue + "&year=" + $("#claim_year option:selected").val() + "&month=" + $("#claim_month option:selected").val();
		datastring += "&ctype=o";
		
	
	}else{
	
//*********************** PD CLAIM ENTRY *****************************************	
			
		$('#loadingSpinner').css("display","none");           		
		//pd claim
		
			if(!$("#title").val()) {		
			$("#pd_step3_error").html("<b><i class='fas fa-exclamation-triangle'></i> TITLE ERROR:</b> Please enter a title for this PD.").css("display","block").delay(5000).fadeOut();
			$( "#title").focus();
			return false;
		}
		
		if($("#start_date").val() == ""){			
			$("#pd_step1_error").html("<b><i class='fas fa-exclamation-triangle'></i> START DATE ERROR:</b> Please enter a start date for this PD.").css("display","block").delay(5000).fadeOut();
			$( "#start_date").focus();
			return false;
		}
		if($("#finish_date").val() == ""){			
			$("#pd_step2_error").html("<b><i class='fas fa-exclamation-triangle'></i> FINISH DATE ERROR:</b> Please enter a finish date for this PD.").css("display","block").delay(5000).fadeOut();
			$( "#finish_date").focus();
			return false;
		}		
	
		//Check to see if data is entered.
			textbox_data = CKEDITOR.instances.desc.getData();
   			 if (textbox_data==='')
    			{        
			$("#pd_step4_error").html("<b><i class='fas fa-exclamation-triangle'></i> DESCRIPTION ERROR:</b> Please enter a description for this PD.").css("display","block").delay(5000).fadeOut();
			$('.cke_wysiwyg_frame').contents().find('.cke_editable').focus();	     	
			return false;
		}
		
		if($("#pd_supervisor_id option:selected").text() == "SELECT SUPERVISOR"){			
			$("#pd_step5_error").html("<b><i class='fas fa-exclamation-triangle'></i> SUPERVISOR ERROR:</b> Please select the supervisor for this PD.").css("display","block").delay(5000).fadeOut();
			$( "#pd_supervisor_id").focus();
			return false;
		}
		//dropdown might not exisit so we will take value from hidden field
		
		
		var selectedvalue="";
		if ($('#pd_supervisor_id').length > 1) {
			selectedvalue = $("#pd_supervisor_id option:selected").val();
		}else{
			selectedvalue = $("#pd_supervisor_id").val();
			
		}
		//datastring = "supervisior=" + selectedvalue + "&title=" + $("#title").val() + "&desc=" + $("#desc").val();
		datastring = "supervisior=" + selectedvalue + "&title=" + $("#title").val() + "&desc=" + textbox_data;
		datastring += "&sdate=" + $("#start_date").val() + "&fdate=" + $("#finish_date").val() + "&ctype=p";
		
	}
	
//***********ADD NEW CLAIM ****************************************************	
	//all fields are valid, now we post form with ajax
	var claimid=-1;
	$.ajax({
        url: 'addNewClaimAjax.html',
        type: 'POST',
        data: datastring,
        beforeSend: function(){
        	$(".details_success_message").html("<b>SUCCESS:</b>  New Claim Has Been Started.").css("display","block").delay(3000).fadeOut();	
			$("#loadingSpinner").css("display","inline");

		   },
        
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "ADDED")
						{ 
							
								
							claimid=$(this).find("CLAIMID").text();
							$("#claimid").val(claimid);
			            	var surl="viewTravelClaimDetails.html?id=" + claimid;
			    			$("#pageContentBody").load(surl);
						}else{
							
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();
							
						}

					}); 
        		
        		
        		setTimeout(function() {
					$("#loadingSpinner").css("display","none");
					}, 2000);
        		
				},
				  error: function(xhr, textStatus, error){
					  
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();					  
				      
				  },
				dataType: "text",
				//async: false
	
        
    });
	
	
	//$('#addnewitem').dialog('close');
}
/************************************************
call ajax post for deleting a travel claims
*************************************************/
function deletetravelclaim(cid)
{
	var optext = "CONFIRM";
	var timeDelay = 3000;           // MILLISECONDS (5 SECONDS).
	$(".details_success_message").html("<b>SUCCESS:</b>  Travel Claim Has Been Deleted.").css("display","block").delay(5000).fadeOut();		
	setTimeout(loadXML1, timeDelay);  
	
	function loadXML1() {
	
	$.ajax({
        url: 'deleteTravelClaimAjax.html',
        type: 'POST',
        data: {id: cid,op: optext},
       
        beforeSend: function(){

				$("#loadingSpinner").css("display","inline");

			   },
        
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						     			
        			
        			
        			
						if($(this).find("STATUS").text() == "SUCCESS")
						{
																					
							$(".details_success_message").html("<b>SUCCESS:</b> Travel Claim Has Been Deleted.").css("display","block").delay(5000).fadeOut();		
																		            				            	
			    			//var surl="viewTravelClaimDetails.html";
			            	//var surl="addTravelClaim.html";
			            	//$("#pageContentBody").load(surl);
							var surl="index.jsp";
			            	window.location.href = surl;
			            	
			            	
			            	$('#travelModal').modal('hide');
						}else{
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();
						}

					});   
        		
        		setTimeout(function() {
					$("#loadingSpinner").css("display","none");
					}, 1000);
        		
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
				  },
				dataType: "text",
				//async: true
	
        
    });

	}
	
}
/************************************************
User level validation for checking to make sure
new supervisor is selected before submitting post
*************************************************/
function checksupervisor()
{
	var newsupervisor=$("#supervisor_id option:selected").val();
	var newsupervisortext=$("#supervisor_id option:selected").text();
	var osupervisor= $("#osupervisor_id").val();
	if(newsupervisortext == "SELECT SUPERVISOR"){
		$(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>ERROR:</b> Please Select New Supervisor").css("display","block").delay(5000).fadeOut();
		return false;
	}
	if(newsupervisor == osupervisor){		
		$(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>ERROR:</b> Please Select Different Supervisor").css("display","block").delay(5000).fadeOut();
		return false;
	}
	return true;
}
/************************************************
Calls ajax post to update travel claim supervisor
*************************************************/
function updatetravelclaimsupervisor(cid)
{
	if(!(checksupervisor())){
		return false;
	}
	var optext = "CONFIRM";
	var supervisorid=$("#supervisor_id option:selected").val();
	var supervisoridt=$("#supervisor_id option:selected").text();
	$.ajax({
        url: 'updateTravelClaimSupervisorAjax.html',
        type: 'POST',
        data: {claim_id: cid,op: optext,supervisor_id:supervisorid},
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("STATUS").text() == "SUCCESS")
						{							
							$(".details_success_message").html("<b>SUCCESS:</b>  Travel Claim Supervisor Has Been Updated").css("display","block").delay(5000).fadeOut();			    			
							//var test = $("#pageContentBody").contents().find("#td1");
							//test.html(supervisoridt);
							
							$('#travelModal').modal('hide');
							$('.modal-backdrop').remove();
							// $('body').css('overflow', 'auto');
							 $('body').removeClass('modal-open');
							
							
							var surl="viewTravelClaimDetails.html?id=" + cid;
			            	$("#pageContentBody").load(surl);
							
							
						
							
							
						}else{
							
							 $(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
				  },
				dataType: "text",
				async: false
	
        
    });
	
}
/************************************************
User level validation for add new travel claim
item
*************************************************/


//invalid characters!!! Replace and post (Temp solution)
function findTheInvalids() {	
	
 $( '#item_desc' ).val( $('#item_desc').val().replace(/[^\w\s,.$+=-]/gi, ''));	
		 
	
};

function findTheInvalidsAdding() {	
	

	$( '#title' ).val( $('#title').val().replace(/[^\w\s]/gi, ''));
	$( '#desc' ).val( $('#desc').val().replace(/[^\w\s,.$+=-]/gi, ''));	
	
};
	
	
function findTheProfileInvalids() {	
	

	$( '#cur_street_addr' ).val( $('#cur_street_addr').val().replace(/[^\w\s']/gi, ''));
	$( '#cur_community' ).val( $('#cur_community').val().replace(/[^\w\s']/gi, ''));	
	
};
	




function addnewtravelclaimitem(claimid,op,itemid){


		$("#loadingSpinner").css("display","none");
	//check mandatory fields first
	if($("#item_date").val() == ""){
		$('.details_error_message').html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>DATE ERROR:</b> Please enter Date of claim.").css("display","block").delay(4000).fadeOut();
		$("#item_date").focus();
		return false;
	}
	if($("#item_departure_time").val() == ""){
		$(".details_info_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>DEPARTURE TIME NOTICE:</b> If no departure time we will assume you were <b>overnight</b>.<br/>If this is correct, please continue to Add item, or select a valid departure time (i.e. 8:30am).").css("display","block").delay(10000).fadeOut();
		$( "#item_departure_time").val("Overnight");
		//$( "#item_departure_time").focus();
		
		return false;
	}
	if($("#item_return_time").val() == ""){
		$(".details_info_message").html("<b>RETURN TIME NOTICE:</b> If no return time we will assume you stayed <b>overnight</b>.<br/>If this is correct, please contine to Add item, or please select a valid return time (i.e. 8:30pm).").css("display","block").delay(10000).fadeOut();
		$( "#item_return_time").val("Overnight");
		//$( "#item_return_time").focus();
		
		return false;
	}
	
	
	if($("#item_kms").val() == "" || $("#item_meals").val() == "" || $("#item_lodging").val() == "" || $("#item_other").val() == ""){
		$('.details_error_message').html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>EMPTY FIELD ERROR(S): </b>Please enter a value greater than 0 for at least one of the following: KMS,MEALS,LODGING,OTHER.").css("display","block").delay(5000).fadeOut();
		$("#item_kms").focus();
		return false;
	}
	
	//If CK Editor has text or not...
	   
	 textbox_data = CKEDITOR.instances.item_desc.getData();
	    if( textbox_data === '' ) {
	     $("#item_kms").focus();     
	    	$('.details_error_message').html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>DESCRIPTION ERROR:</b> Please enter a Description of this claim item.").css("display","block").delay(4000).fadeOut();
	        $('.cke_wysiwyg_frame').contents().find('.cke_editable').focus();
	          
	       
	        return false; 
	    }        
	
		
	
	
	
   
	
	
	//all good submit ajax post
	$('.details_error_message').html("").css("display","none");
	if(op == "ADD"){
		ajaxAddNewTravelClaimItem(claimid);
	}else{
		ajaxUpdateTravelClaimItem(claimid,itemid);
	}
	
}
/************************************************
Calls ajax post for adding new travel claim item
*************************************************/
function ajaxAddNewTravelClaimItem(claimid)
{
	var optext = "CONFIRM";
	//removeCurrency
	var meals = removeCurrencyTravel($("#item_meals").val()) == "" ? "0" : removeCurrencyTravel($("#item_meals").val());
	var lodging = removeCurrencyTravel($("#item_lodging").val()) == "" ? "0" : removeCurrencyTravel($("#item_lodging").val());
	var otheritem =removeCurrencyTravel($("#item_other").val()) == "" ? "0" : removeCurrencyTravel($("#item_other").val());
	var kms = removeCurrencyTravel($("#item_kms").val()) == "" ? "0" : removeCurrencyTravel($("#item_kms").val());
	//get values to reset datepickter after ajax
	var cm = $("#cm").val();
	var cy = $("#cy").val();
	var ldm = $("#ldm").val();
	var descR= CKEDITOR.instances['item_desc'].getData();
	var requestd = new FormData();
	var filecount = 0;
	$("#add_claim_item_form").find("input[type=file]").each(function(index, field){
			if(field.files.length > 0){
				filecount++;
				var testname = "file" + filecount;
				requestd.append(testname,field.files[0]);
			}
	});
	requestd.append("filecount",filecount);
	//now pass the file descriptions
	if(filecount > 0){
		var filenames = new Array(filecount);
		var x =0;
		//alert(this.value);
	        //filenames[x]=this.value;
	       // x++;
		$("#add_claim_item_form").find("input[name=filetext]").each(function(index, field){
			if(this.value != ""){
				filenames[x]=this.value;
				x++;
			}
		});
		requestd.append("filedesc",filenames);
	}
	requestd.append('op',optext);
	requestd.append('id',claimid);
	requestd.append('item_date',$("#item_date").val());
	requestd.append('item_desc',descR);
	requestd.append('item_kms',kms);
	requestd.append('item_meals',meals);
	requestd.append('item_lodging',lodging);
	requestd.append('item_other',otheritem);
	requestd.append('item_departure_time',$("#item_departure_time").val());
	requestd.append('item_return_time',$("#item_return_time").val());
	
	
	$.ajax({
        url: 'addNewTravelClaimItemAjax.html',
        type: 'POST',
        contentType: false,
        processData: false,
        data: requestd,
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "SUCCESS")
						{
							$(".details_success_message").html("<b>SUCCESS:</b> Travel Claim Item Has Been Added.").css("display","block").delay(5000).fadeOut();			    			
							var surl="viewTravelClaimDetails.html?id=" + claimid;
			            	$("#pageContentBody").load(surl);
							
							
						}else{
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
				  },
				dataType: "text",
				async: false
	
        
    });
	refreshJquery(cm,cy,ldm);
}
function removeCurrencyTravel( fld ) {
	/************************************************
	DESCRIPTION: Removes currency formatting from
	  source string.

	PARAMETERS:
	  strValue - Source string from which currency formatting
	     will be removed;

	RETURNS: Source string with commas removed.
	*************************************************/
	var testing="";
	try{
		
	
	var objRegExp = /\-/;
	  //var strMinus = '';

	  //check if negative
	  //if(objRegExp.test(fld.value)){
	  //  strMinus = '-';
	  //}
	  objRegExp = /\)|\(|[,]/g;
	  testing= fld.replace(objRegExp,'');
	  if(testing.indexOf('$') >= 0){
		    testing = fld.substring(1, fld.length);
		  }
		}catch(e){
			alert(e);
		}
		return testing.replace(/,/g,"");

	  //fld.value =  strMinus + fld.value;
}
function addCurrencyTravel( fld ) {
	/************************************************
	DESCRIPTION: Formats a number as currency.

	PARAMETERS:
	  strValue - Source string to be formatted

	REMARKS: Assumes number passed is a valid
	  numeric value in the rounded to 2 decimal
	  places.  If not, returns original value.
	*************************************************/
	var testing="";
	var finalvalue="";
	try{
	  var objRegExp = /-?[0-9]+\.[0-9]{2}$/;
	  testing = removeCurrencyTravel(fld);
	  if(objRegExp.test(testing)) {
	      objRegExp.compile('^-');
	      finalvalue = addCommas(testing);
	  }
	}catch(e){
		alert(e);
	}
	return "$" + finalvalue;
	
}
/************************************************
Calls ajax post for update travel claim item
*************************************************/
function ajaxUpdateTravelClaimItem(claimid,vitemid)
{
	var optext = "CONFIRM";
	//removeCurrency
	var meals = removeCurrencyTravel($("#item_meals").val());
	var lodging = removeCurrencyTravel($("#item_lodging").val());
	var otheritem =removeCurrencyTravel($("#item_other").val());
	var descR= CKEDITOR.instances['item_desc'].getData();
	var kms = removeCurrencyTravel($("#item_kms").val()) == "" ? "0" : removeCurrencyTravel($("#item_kms").val());
	//get values to reset datepickter after ajax
	var cm = $("#cm").val();
	var cy = $("#cy").val();
	var ldm = $("#ldm").val();
	var requestd = new FormData();
	var filecount = 0;
	$("#add_claim_item_form").find("input[type=file]").each(function(index, field){
		if(field.files.length > 0){
			filecount++;
			var testname = "file" + filecount;
			requestd.append(testname,field.files[0]);
		}
	});
	requestd.append("filecount",filecount);
	//now pass the file descriptions
	if(filecount > 0){
		var filenames = new Array(filecount);
		var x =0;
		//alert(this.value);
        //filenames[x]=this.value;
       // x++;
		$("#add_claim_item_form").find("input[name=filetext]").each(function(index, field){
			if(this.value != ""){
				filenames[x]=this.value;
				x++;
			}
		});
	requestd.append("filedesc",filenames);
	}
	requestd.append('op',optext);
	requestd.append('id',claimid);
	requestd.append('item_date',$("#item_date").val());
	requestd.append('item_desc',descR);
	requestd.append('item_kms',kms);
	requestd.append('item_meals',meals);
	requestd.append('item_lodging',lodging);
	requestd.append('item_other',otheritem);
	requestd.append('item_departure_time',$("#item_departure_time").val());
	requestd.append('item_return_time',$("#item_return_time").val());
	//requestd.append('item_file',ufile);
	requestd.append('itemid',vitemid);
	requestd.append('deletedfiles',$("#hidfiledelete").val());
	$.ajax({
        url: 'updateTravelClaimItemAjax.html',
        type: 'POST',
        contentType: false,
        processData: false,
        data: requestd,
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "SUCCESS")
						{
							$(".details_success_message").html("<b>SUCCESS:</b> Travel Claim Item Has Been Updated").css("display","block").delay(5000).fadeOut();
			    				var surl="viewTravelClaimDetails.html?id=" + claimid;
			            	$("#pageContentBody").load(surl);
						}else{
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
        
				  },
				dataType: "text",
				async: false
	
        
    });

	refreshJquery(cm,cy,ldm);
}
/************************************************
Calls ajax post for delete travel claim item
*************************************************/
function deletetravelclaimitem(cid,ciid)
{
	var optext = "CONFIRM";
	//get values to reset datepickter after ajax
	var cm = $("#cm").val();
	var cy = $("#cy").val();
	var ldm = $("#ldm").val();
	$.ajax({
        url: 'deleteTravelClaimItemAjax.html',
        type: 'POST',
        data: {id: ciid,op: optext,clid:cid},
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("STATUS").text() == "SUCCESS")
						{
							$(".details_success_message").html("<b>SUCCESS:</b> Travel Claim Item Has Been Deleted").css("display","block").delay(5000).fadeOut();			    			
							
														
							var surl="viewTravelClaimDetails.html?id=" + cid;
			            	$("#pageContentBody").load(surl);
							
						}else{
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();							
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
				  },
				dataType: "text",
				async: false
	
        
    });
	refreshJquery(cm,cy,ldm);
	
}
/************************************************
User level validation for update my proifle
*************************************************/
function updatemyprofile(op){
	$('.details_error_message').html("");
	//check mandatory fields first
	if($("#cur_street_addr").val() == ""){
		$('.details_error_message').html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>ERROR:</b> Please enter a value for Full Mailing Address ").css("display","block").delay(5000).fadeOut();
		return false;
	}
	if($("#cur_community").val() == ""){
		$('.details_error_message').html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>ERROR:</b> Please enter a value for City/Town").css("display","block").delay(5000).fadeOut();
		return false;
	}
	if($("#cur_postal_code").val() == ""){
		$('.details_error_message').html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>ERROR:</b> Please enter a value for Postal Code").css("display","block").delay(5000).fadeOut();
		return false;
	}
	if($("#home_phone").val() == ""){
		$('.details_error_message').html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>ERROR:</b> Please enter a value for Home Phone").css("display","block").delay(5000).fadeOut();
		return false;
	}
	//all good submit ajax post
	$('.details_error_message').html("").css("display","none");
	ajaxUpdateMyProfile(op);
	
	
}
/************************************************
Calls ajax post for update my profile
*************************************************/
function ajaxUpdateMyProfile(vop)
{
	$.ajax({
        url: 'updateMyProfileAjax.html',
        type: 'POST',
        data: {op:vop,cur_street_addr:$("#cur_street_addr").val(),cur_community:$("#cur_community").val(),cur_province:$("#cur_province").val(),cur_postal_code:$("#cur_postal_code").val(),home_phone:$("#home_phone").val(),fax: $("#fax").val(),
        	cell_phone: $("#cell_phone").val(),gender: $("#gender").val()},
        success: function(xml) {
        		$(xml).find('PROFILE').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "SUCCESS")
						{
							
							$('.details_success_message').html("<b>SUCCESS</b>: Profile has been " + vop).css("display","block").delay(5000).fadeOut();
							
			            	//var surl="viewTravelClaimSystem.html";
			    			//$("#pageContentBody").load(surl);
						}else{
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();							
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
						
				  },
				dataType: "text",
				async: false
	
        
    });

	
}
/************************************************
Calls ajax post for submit travel claim
*************************************************/
function submittravelclaim(claimid)
{
	//var claimid = $("#id").val();
	$.ajax({
        url: 'submitTravelClaimAjax.html',
        type: 'POST',
        data: {id: claimid},
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "SUCCESS")
						{							
							
							$('#travelModal').modal('hide');
							$('.modal-backdrop').remove();
							// $('body').css('overflow', 'auto');
							 $('body').removeClass('modal-open');
							
							$('.details_success_message').html("<b>SUCCESS:</b> Travel Claim Has Been Submitted.").css("display","block").delay(5000).fadeOut();
							var surl="viewTravelClaimDetails.html?id=" + claimid;
			            	$("#pageContentBody").load(surl);
							
						}else{
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
				  },
				dataType: "text",
				async: false
	
        
    });
	
}
/************************************************
Calls ajax post for supervisor approve/decline
travel claim
*************************************************/
function approvedeclinetravelclaim(claimid,optext)
{
	var glaccount = "";
	var notetext = "";
	if(optext == "APPROVED"){
		glaccount=$("#glaccount").data().rawMaskFn();
		if(glaccount == ""){
			$('#glaccount').css('background','yellow');
			$('#glaccount').focus();
			return false;
		}
	}else{
		notetext = $("#note").val();
	}
	$.ajax({
        url: 'approveDeclineTravelClaimAjax.html',
        type: 'POST',
        data: {id: claimid,op: optext, note: notetext, gl_acc: glaccount},
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "APPROVED")
						{
							$('#travelModal').modal('hide');
							$('.modal-backdrop').remove();							
							$('body').removeClass('modal-open');							
							$('.details_success_message').html("<b>SUCCESS:</b> Travel Claim Has Been Approved!").css("display","block").delay(5000).fadeOut();
					
							var surl="viewTravelClaimDetails.html?id=" + claimid;
			            	$("#pageContentBody").load(surl);
			            
							
						}else if($(this).find("MESSAGE").text() == "DECLINED"){		
							
							$('#travelModal').modal('hide');
							$('.modal-backdrop').remove();							
							$('body').removeClass('modal-open');
			    			$('.details_error_message').html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>DECLINED:</b> Travel claim has been declined.").css("display","block").delay(5000).fadeOut();
							var surl="viewTravelClaimDetails.html?id=" + claimid;
			            	$("#pageContentBody").load(surl);
			            	
						}
						else{
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();
							
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
				  },
				dataType: "text",
				async: false
	
        
    });
}
/************************************************
Calls ajax post for pay travel claim
*************************************************/
function paytravelclaim(claimid)
{
	var glaccount = "";
	var sdsvendor = "";
	var sdsteacher ="";
	glaccount=$("#glaccount").data().rawMaskFn();
	sdsvendor = $("#sds_ven_num").val();
	if($("#sds_tchr_par").is(':checked')){
		sdsteacher="Y";
	}else{
		sdsteacher="N";
	}
	if(sdsvendor == ""){
		$('#sds_ven_num').css('background','yellow');
		$('#sds_ven_num').focus();
		return false;
	}
	if(glaccount == ""){
		$('#glaccount').css('background','yellow');
		$('#glaccount').focus();
		return false;
	}
	$.ajax({
        url: 'payTravelClaimAjax.html',
        type: 'POST',
        data: {id: claimid,sds_ven_num: sdsvendor, sds_tchr_par: sdsteacher, gl_acc: glaccount},
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "SUCCESS")
						{
							$('#travelModal').modal('hide');
							$('.modal-backdrop').remove();
							$('body').removeClass('modal-open');
							$(".details_success_message").html("Travel Claim Has Been Set To Paid").css("display","block").delay(5000).fadeOut();
							//var surl="viewTravelClaimDetails.html?id=" + claimid;	
							var surl="claimsApprovedByRegion.html";
							
			            	$("#pageContentBody").load(surl);
			            
			            	
						}else{							
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();

						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();;
				  },
				dataType: "text",
				async: false
	
        
    });
}
/************************************************
Calls ajax post for pay pending travel claim
*************************************************/
function paymentpendingtravelclaim(claimid)
{
	var claimnote = $("#note").val();
	$.ajax({
        url: 'paymentPendingTravelClaimAjax.html',
        type: 'POST',
        data: {id: claimid,note: claimnote},
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the .delay(5000)s if any
						if($(this).find("MESSAGE").text() == "SUCCESS")
						{
							
							$('#travelModal').modal('hide');
							$('.modal-backdrop').remove();
							$('body').removeClass('modal-open');
							
							$(".details_success_message").html("Travel Claim Status Set To Pending").css("display","block").delay(5000).fadeOut();							
			    			var surl="viewTravelClaimDetails.html?id=" + claimid;
			            	$("#pageContentBody").load(surl);
			            	
						}else{
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();							
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
        
				  },
				dataType: "text",
				async: false
	
        
    });
}
/************************************************
Post save travel claim screen to save changes
*************************************************/
function savetravelclaimchanges(claimid){
	var surl="saveTravelClaimChangesAjax.html";
		var frm = $('#add_claim_item_form');
	    $.ajax({
            url:surl,
            type:'POST',
            data:frm.serialize(),
            success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "SUCCESS")
						{
							$(".details_success_message").html("Travel Claim Changes Saved").css("display","block").delay(5000).fadeOut();
							
							//$('#travelModal').modal('close');
			    			var surl="viewTravelClaimDetails.html?id=" + claimid;
			            	$("#pageContentBody").load(surl);
			            	
						}else{
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();
							
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
				  },
				dataType: "text",
				async: false

    	});
}
/************************************************
Calls ajax post for add new travel claim note
*************************************************/
function addnewtravelclaimnote(claimid)
{
	//var claimid = $("#id").val();
	var claimnote = $("#note").val();
	if(claimnote == ""){
		alert("Please enter note");
		return false;
	}
	$.ajax({
        url: 'addNewTravelClaimNoteAjax.html',
        type: 'POST',
        data: {id: claimid,note: claimnote},
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "SUCCESS")
						{
							$(".details_success_message").html("<b>SUCCESS:</b> Travel Claim note added. Check the Notes tab above.").css("display","block").delay(5000).fadeOut();
			    			
			    			$('#travelModal').modal('close');
			    			var surl="viewTravelClaimDetails.html?id=" + claimid;
			            	$("#pageContentBody").load(surl);
							
						}else{
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();
							
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
						
				  },
				dataType: "text",
				async: false
	
        
    });
}
/************************************************
Calls ajax post for add/edit supervisor rule
*************************************************/
function addeditsupervisorrule()
{
	var supervisorkeytype = $("#supervisor_keytype").val();
	var supervisorkey = $("#supervisor_key").val();
	var userkeytype = $("#user_keytype").val();
	var userkey = $("#user_key").val();
	var divisionid = $("#division_id").val();
	var ruleid=null;
	if($("#rule_id").val() != ""){
		ruleid=$("#rule_id").val();
	}
	if(supervisorkeytype == "-1"){
		$(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>ERROR:</b>  Please select Supervisor Key Type").css("display","block").delay(5000).fadeOut();
		
		return false;
	}
	if(userkeytype == "-1"){
		$(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>ERROR:</b>  Please select User Key Type").css("display","block").delay(5000).fadeOut();
		
		return false;
	}
	if(divisionid == ""){
		$(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>ERROR:</b>  Please select Division").css("display","block").delay(5000).fadeOut();
		
		return false;
	}
	if(ruleid == null){
		ruleid="-1";
	}
	$.ajax({
        url: 'addEditSupervisorRuleAjax.html',
        type: 'POST',
        data: {supervisor_keytype: supervisorkeytype,supervisor_key: supervisorkey,user_keytype: userkeytype, user_key: userkey, division_id: divisionid, rule_id: ruleid},
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "ADDED")
						{
							$(".details_success_message").html("<b>SUCCESS:</b> Supervisor Rule added").css("display","block").delay(5000).fadeOut();							
							var surl="listSupervisorRules.html";
							$("#pageContentBody").load(surl);
							
							
						}else if($(this).find("MESSAGE").text() == "UPDATED"){
							$(".details_success_message").html("<b>SUCCESS:</b> Supervisor Rule updated").css("display","block").delay(5000).fadeOut();
							
							var surl="listSupervisorRules.html";
							$("#pageContentBody").load(surl);
						}else{
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();
							
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
						
				  },
				dataType: "text",
				async: false
	
        
    });
}
/************************************************
Calls ajax post for add/edit travel budget
*************************************************/
function addedittravelbudget()
{
	var personnelid = $("#personnel_id").val();
	var fiscalyear = $("#fiscal_year").val();
	var budgetedamount = $("#budgeted_amount").val();
	var divisionid = $("#division_id").val();
	var supervisorid = $("#supervisor_id").val();
	var budgetid=null;
	if($("#budget_id").val() != ""){
		budgetid=$("#budget_id").val();
	}
	if(personnelid == "-1"){
		$(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>ERROR:</b>  Please select Employee").css("display","block").delay(5000).fadeOut();
		
		return false;
	}
	if(budgetedamount == ""){
		$(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>ERROR:</b>  Please enter Budgeted Amount").css("display","block").delay(5000).fadeOut();
		
		return false;
	}
	if(budgetid == null){
		budgetid="-1";
	}
	$.ajax({
        url: 'addTravelBudgetAjax.html',
        type: 'POST',
        data: {budget_id: budgetid,personnel_id: personnelid,fiscal_year: fiscalyear, budgeted_amount: budgetedamount, division_id: divisionid, supervisor_id: supervisorid},
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "ADDED")
						{
							$(".details_success_message").html("<b>SUCCESS:</b> Travel Budget added").css("display","block").delay(5000).fadeOut();
							
							var surl="listTravelBudgets.html";
							$("#pageContentBody").load(surl);
							
						}else if($(this).find("MESSAGE").text() == "UPDATED"){
							$(".details_success_message").html("<b>SUCCESS:</b> Travel Budget updated").css("display","block").delay(5000).fadeOut();							
							
							var surl="listTravelBudgets.html";
							$("#pageContentBody").load(surl);
						}else{
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();
							
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
        
				  },
				dataType: "text",
				async: false
	
        
    });
}
/************************************************
Function called when user clicks letter button on
Travel Claims Paid today Screen.  Clears table,
passes Letter to ajax function and returns claims
paid by that letter.
*************************************************/
function gettravelclaimsbyletter(letter)
{	
	cleartable();
	ajaxRequestInfo(letter);		
	
}
/************************************************
Generic function used to clear table with id of
Claims-Table
*************************************************/
function cleartable()
{
	$('#claims-table td').parent().remove();
}
/************************************************
Calls ajax post for getting claims paid today 
by letter
*************************************************/
function ajaxRequestInfo(sletter)
{
			
	var isvalid=false;
	$.ajax(
 			{   
 				type: "POST",  
 				url: "getTravelClaimsByLetterAjax.html",
 			
 				data: {
 					letter: sletter
 				}, 
 				
 				beforeSend: function(){

 					$("#loadingSpinner").css("display","inline");

 				   },
 				
 				success: function(xml){
 					
 					$("#loadingSpinner").css("display","none");
 					var newrow="";
 					var amount="";
 					 					
 					$(xml).find('CLAIM').each(function(){
 						
 							if($(this).find("MESSAGE").text() == "LISTFOUND")
 								{
 								
 								
 								
 									newrow+="<tr>";
                                    newrow += "<td style='vertical-align:middle;'>" + $(this).find("EMPLOYEE").text() + "</td>";                                     
                                    if($(this).find("TYPE").text() == "PD")	{								
                                    	newrow += "<td style='vertical-align:middle;color:white;text-align:center;background-color:#ff8400;font-weight:bold;'>PD CLAIM </td>";
									} else {
										newrow += "<td style='vertical-align:middle;color:white;text-align:center;background-color:#1c90ec;font-weight:bold;'>MONTHLY CLAIM</td>";								
									}                                    
                                    newrow += "<td style='vertical-align:middle;'>" + $(this).find("TITLE").text() + "</td>";  
                                    amount=+($(this).find('AMOUNT').text());                                  
                                    newrow += "<td  style='vertical-align:middle;'> $" + amount.toFixed(2) + "</td>";
                                    newrow += "<td style='vertical-align:middle;'>" + $(this).find("SUPERVISOR").text() + "</td>"; 
                                    if($(this).find("ZONE").text() == "avalon")	{		
										 newrow += "<td class='region1solid' style='text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>AVALON</td>";								
									  } else if($(this).find("ZONE").text() == "central")	{
										 newrow += "<td class='region2solid' style='text-align:center;vertical-align:middle;color:rgba(255,255,255, 1);font-weight:bold;'>CENTRAL</td>";	
									 } else if($(this).find("ZONE").text() == "western")	{
										 newrow += "<td class='region3solid' style='text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>WESTERN</td>";	
									 } else if($(this).find("ZONE").text() == "labrador")	{
										 newrow += "<td class='region4solid' style='text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>LABRADOR</td>";	
									 } else if($(this).find("ZONE").text() == "provincial")	{
										 newrow += "<td class='region5solid' style='text-align:center;color:rgba(255,255,255,1);vertical-align:middle;font-weight:bold;'>PROVINCIAL</td>";	
									 } else {
										 newrow += "<td style='background-color:Black;text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>N/A</td>";	 									
									}
                                    newrow += "<td style='vertical-align:middle;'><a href='#' class='btn btn-xs btn-primary' onclick='loadingData();loadMainDivPage(&quot;viewTravelClaimDetails.html?id=" + $(this).find("ID").text() + "&quot;);'>VIEW</a></td>";
									newrow += "</tr>";						
									
									isvalid=true;	                   				
 								}
 							
						});
 					
 					$(".claimsTable tbody").append(newrow);
 					
 					$("#claims-table").DataTable({ 					
						  "order": [[ 0, "desc" ]],
						   "responsive": true,
						  dom: 'Blfrtip',
					        buttons: [			        	
					        	//'colvis',
					        	//'copy', 
					        	//'csv', 
					        	'excel', 
					        	{
					                extend: 'pdfHtml5',
					                footer:true,
					                //orientation: 'landscape',
					                messageTop: 'Travel/PD Claims ',
					                messageBottom: null,
					                exportOptions: {
					                    columns: [ 0, 1, 2, 3 ]
					                }
					            },
					        	{
					                extend: 'print',
					                //orientation: 'landscape',
					                footer:true,
					                messageTop: 'Travel/PD Claims',
					                messageBottom: null,
					                exportOptions: {
					                    columns: [ 0, 1, 2, 3]
					                }
					            }
					        ],
						  "lengthMenu": [[25, 50, 100, 250, -1], [25, 50, 100, 250, "All"]]							
					}); 					
 					
 					
					},
 				  error: function(xhr, textStatus, error){
 					 $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
       
 				  },
 				dataType: "text",
 				//async: false
 			}
 		);		
	 
	return isvalid;
	
	
}
/************************************************
Function called when user clicks letter button on
Travel Claims Pending Screen.  Clears table,
passes Letter to ajax function and returns claims
paid by that letter.
*************************************************/
function getpendingtravelclaimsbyletter(letter,date1)
{	
	cleartable();
	ajaxRequestInfoPending(letter,date1);
	//window.parent.$('#claim_details').css('height', $('#claims-table').height()+100);
	//$("#claim_details").style.height = $("#claims-table").offsetHeight + 'px';   
}
/************************************************
Calls ajax post for getting Claim Pending 
by letter
*************************************************/
function ajaxRequestInfoPending(sletter,sdate)
{
	
	var d = new Date();
	var n = d.getFullYear(); 
	var cnt = 0;
	
	var isvalid=false;
	$.ajax(
 			{
 				type: "POST",  
 				url: "getTravelClaimsPaymentPendingByLetterAjax.html",
 			
 				data: {
 					letter: sletter,
 					date1: sdate
 				}, 
 				
 				beforeSend: function(){
						
 					$("#loadingSpinner").css("display","inline");

 				   },
 				
 				success: function(xml){
 					
 					var newrow="";
 					$("#loadingSpinner").css("display","none");
 					
 					$(xml).find('CLAIM').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "LISTFOUND") {														
 								if((sletter == "All") ||  ($(this).find("CLAIMDATE").text().substr($(this).find("CLAIMDATE").text().length - 4)  == sdate)) {								
 									newrow +="<tr style='vertical-align:middle;'>";
                                    newrow += "<td style='vertical-align:middle;'>" + $(this).find("EMPLOYEE").text() + "</td>";
                                    if($(this).find("TYPE").text() == "PD")	{								
                                    	newrow += "<td style='vertical-align:middle;color:white;text-align:center;background-color:#ff8400;font-weight:bold;'>PD CLAIM</td>";
									} else {
										newrow += "<td style='vertical-align:middle;color:white;text-align:center;background-color:#1c90ec;font-weight:bold;'>MONTHLY</td>";								
									} 
                                    newrow += "<td style='vertical-align:middle;'>" + $(this).find("TITLE").text() + "</td>";
                                    newrow += "<td style='vertical-align:middle;'>" + $(this).find("CLAIMDATE").text().substr($(this).find("CLAIMDATE").text().length - 4) + "</td>";
                                    newrow += "<td style='vertical-align:middle;'>" + $(this).find("SUPERVISOR").text() + "</td>";
                                	if($(this).find("ZONE").text() == "avalon")	{		
										 newrow += "<td class='region1solid' style='text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>AVALON</td>";								
									  } else if($(this).find("ZONE").text() == "central")	{
										 newrow += "<td class='region2solid' style='text-align:center;vertical-align:middle;color:rgba(255,255,255, 1);font-weight:bold;'>CENTRAL</td>";	
									 } else if($(this).find("ZONE").text() == "western")	{
										 newrow += "<td class='region3solid' style='text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>WESTERN</td>";	
									 } else if($(this).find("ZONE").text() == "labrador")	{
										 newrow += "<td class='region4solid' style='text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>LABRADOR</td>";	
									 } else if($(this).find("ZONE").text() == "provincial")	{
										 newrow += "<td class='region5solid' style='text-align:center;color:rgba(255,255,255,1);vertical-align:middle;font-weight:bold;'>PROVINCIAL</td>";	
									 } else {
										 newrow += "<td style='background-color:Black;text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>N/A</td>";	 									
									}                                  
                                    
                                    newrow += "<td style='vertical-align:middle;'>";										
									newrow += "<a href='#' class='btn btn-xs btn-primary' onclick='loadingData();loadMainDivPage(&quot;viewTravelClaimDetails.html?id=" + $(this).find("ID").text() + "&quot;);'>VIEW</a></td>";
									newrow += "</tr>";
							
									isvalid=true;
	                   				
 								}							 
 															
 								}
 								});
 							
 							
 							
 							$(".claimsTable tbody").append(newrow);
 		 					
 		 					$("#claims-table").DataTable({	  		 				
 								  "order": [[ 3, "desc" ],[0,"asc"]],
 								   "responsive": true,
 								  dom: 'Blfrtip',
 							        buttons: [			        	
 							        	//'colvis',
 							        	'copy', 
 							        	'csv', 
 							        	'excel', 
 							        	{
 							                extend: 'pdfHtml5',
 							                footer:true,
 							                //orientation: 'landscape',
 							                messageTop: 'Travel/PD Claims ',
 							                messageBottom: null,
 							                exportOptions: {
 							                    columns: [ 0, 1, 2, 3, 4, 5]
 							                }
 							            },
 							        	{
 							                extend: 'print',
 							                //orientation: 'landscape',
 							                footer:true,
 							                messageTop: 'Travel/PD Claims',
 							                messageBottom: null,
 							                exportOptions: {
 							                    columns: [ 0, 1, 2, 3, 4, 5]
 							                }
 							            }
 							        ],
 								  "lengthMenu": [[50, 100, 250, -1], [50, 100, 250, "All"]]							
 							}); 		
 							
 		 				
 				},
					
 				  error: function(xhr, textStatus, error){
 					 $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
       
 				  },
 				dataType: "text",
 				//async: false
 			}
 		);
	
	return isvalid;
}
/************************************************
Function called when user clicks link to get
approved claims by date.  Checks to make sure date
selected, clears table and calls ajax function
*************************************************/
function getapprovedtravelclaimsbydate()
{
	//check to make sure value set
	var selectedvalue = $('#approveddates').val();
	if(selectedvalue == "-1"){		
		$(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>ERROR:</b>  Please select date").css("display","block").delay(5000).fadeOut();
	}
	cleartable();	

		  

		
	ajaxRequestInfoApprovedDate(selectedvalue);
	window.parent.$('#claim_details').css('height', $('#claims-table').height()+100);
	
}
/************************************************
Calls ajax post for getting claims approved by
date
*************************************/
function ajaxRequestInfoApprovedDate(sapproveddate)
{
	var isvalid=false;
	
	$.ajax(
 			{
 				type: "POST",  
 				url: "getTravelClaimsApprovedByDateAjax.html", 				
 				data: {
 					approveddate: sapproveddate,  					
 				},	
 				
 				beforeSend: function(){

 					$("#loadingSpinner").css("display","inline");

 				   },


 			  
 				success: function(xml){
 					 
 					 //$('#loading').html("<p>Result Complete...</p>");
 					$(xml).find('CLAIM').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "LISTFOUND")
 								{
 							 													
 									var newrow="<tr>";
 									newrow += "<td class='listdata'>" + $(this).find("APPROVED").text() +"</a></td>";
                                    newrow += "<td class='listdata'>" + $(this).find("EMPLOYEE").text() +"</a></td>";
                                    if($(this).find("TYPE").text() == "PD")	{								
                                    	newrow += "<td style='color:white;text-align:center;background-color:#ff8400;font-weight:bold;'>PD CLAIM</td>";
									} else {
										newrow += "<td style='color:white;text-align:center;background-color:#1c90ec;font-weight:bold;'>MONTHLY</td>";								
									} 
                                    newrow += "<td class='listdata'>" + $(this).find("TITLE").text() + "</td>"; 
                                    newrow += "<td class='listdata'>" + $(this).find("SUPERVISOR").text() + "</td>"; 
                                    newrow += "<td class='listdata'>" + $(this).find("AMOUNT").text() + "</td>"; 
                                    newrow += "<td class='listdata' align='center'>";										
                                    newrow += "<a href='#' onclick='loadingData();loadMainDivPage(&quot;viewTravelClaimDetails.html?id=" + $(this).find("ID").text() + "&quot;);'>VIEW</a></td>";
									newrow += "</tr>";
									$('table#claims-table tr:last').after(newrow);
									isvalid=true;
									
 								}else{
 									//alert($(this).find("MESSAGE").text());
 									var newrow="<tr>";
                                    newrow += "<td align='center' colspan='5'>No Travel Claims Found</td>";
                                    $('table#claims-table tr:last').after(newrow);
 								}
						});
 					
 					$("#claims-table tr:even").not(':first').css("background-color", "#FFFFFF");
 					$("#claims-table tr:odd").css("background-color", "#E3F1E6");
 					 
 					$("#loadingSpinner").css("display","none");
					},
					
					

					
 				  error: function(xhr, textStatus, error){
 					 $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
       
 				  },
 				   				  
 				dataType: "text",
 			//	async: true
 			}
 		);
	
	return isvalid;
	
}
/************************************************
Function called when user clicks link to get
approved claims by region.  Checks to make sure
region selected, clears table and calls ajax 
function
*************************************************/
function getapprovedtravelclaimsbyregion()
{
	//check to make sure value set
	
	var selectedvalue = "0";	
	cleartable();	
	ajaxRequestInfoApprovedRegion("0");	
}
/************************************************
Calls ajax post for getting claims approved by
region
*************************************/
function ajaxRequestInfoApprovedRegion(region)
{
	var isvalid=false;
	
	$.ajax(
 			{
 				type: "POST",  
 				url: "getTravelClaimsApprovedByRegionAjax.html",
 			
 				data: {
 					regionid: region
 				}, 
 				
 				beforeSend: function(){

 					$("#loadingSpinner").css("display","inline");

 				   },
 				
 				success: function(xml){
 					var amount="";
 					var newrow="";
 					amt = 0;
 					$("#loadingSpinner").css("display","none");
 					
 					$(xml).find('CLAIM').each(function(){
 						
 							if($(this).find("MESSAGE").text() == "LISTFOUND")
 								{										
 							   
 								
 									newrow +="<tr style='vertical-align:middle;'>";
 									newrow += "<td style='vertical-align:middle;'>" + $(this).find("APPROVED").text() +"</td>";					
 									newrow += "<td style='vertical-align:middle;'> " + $(this).find("EMPLOYEE").text() + "</td>";                                    
                                    if($(this).find("TYPE").text() == "PD")	{								
                                    	newrow += "<td style='vertical-align:middle;color:white;text-align:center;background-color:#ff8400;font-weight:bold;'>PD CLAIM</td>";
									} else {
										newrow += "<td style='vertical-align:middle;color:white;text-align:center;background-color:#1c90ec;font-weight:bold;'>MONTHLY</td>";								
									} 
                                    newrow += "<td style='vertical-align:middle;'>" + $(this).find("TITLE").text() + "</td>";          
                                    amount=+($(this).find('AMOUNT').text());                                  
                                    newrow += "<td  style='vertical-align:middle;'> $" + amount.toFixed(2) + "</td>";                                  
                                    newrow += "<td style='vertical-align:middle;'>" + $(this).find("SUPERVISOR").text() + "</td>"; 
                              	  	if($(this).find("ZONE").text() == "avalon")	{		
										 newrow += "<td class='region1solid' style='text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>AVALON</td>";								
									  } else if($(this).find("ZONE").text() == "central")	{
										 newrow += "<td class='region2solid' style='text-align:center;vertical-align:middle;color:rgba(255,255,255, 1);font-weight:bold;'>CENTRAL</td>";	
									 } else if($(this).find("ZONE").text() == "western")	{
										 newrow += "<td class='region3solid' style='text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>WESTERN</td>";	
									 } else if($(this).find("ZONE").text() == "labrador")	{
										 newrow += "<td class='region4solid' style='text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>LABRADOR</td>";	
									 } else if($(this).find("ZONE").text() == "provincial")	{
										 newrow += "<td class='region5solid' style='text-align:center;color:rgba(255,255,255,1);vertical-align:middle;font-weight:bold;'>PROVINCIAL</td>";	
									 } else {
										 newrow += "<td style='background-color:Black;text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>N/A</td>";	 									
									}
                                    newrow += "<td style='vertical-align:middle;'>";										
                                    newrow += "<a href='#' class='btn btn-xs btn-primary' onclick='loadingData();loadMainDivPage(&quot;viewTravelClaimDetails.html?id=" + $(this).find("ID").text() + "&quot;);'>VIEW</a></td>";
									newrow += "</tr>";								
									isvalid=true;	                   				
 								}
						});
 					
 					$("#claims-table tbody").append(newrow);
	 					
	 					$("#claims-table").DataTable({	 
							  "order": [[ 0, "desc" ]],
							   "responsive": true,
							  dom: 'Blfrtip',
						        buttons: [			        	
						        	//'colvis',
						        	'copy', 
						        	'csv', 
						        	'excel', 
						        	{
						                extend: 'pdfHtml5',
						                footer:true,
						                //orientation: 'landscape',
						                messageTop: 'Travel/PD Claims ',
						                messageBottom: null,
						                exportOptions: {
						                    columns: [ 0, 1, 2, 3,4,5 ]
						                }
						            },
						        	{
						                extend: 'print',
						                //orientation: 'landscape',
						                footer:true,
						                messageTop: 'Travel/PD Claims',
						                messageBottom: null,
						                exportOptions: {
						                    columns: [ 0, 1, 2, 3,4,5]
						                }
						            }
						        ],
							  "lengthMenu": [[25, 50, 100, 250, -1], [25, 50, 100, 250, "All"]]							
						}); 		
 					
	 				 					
					},
 				  error: function(xhr, textStatus, error){
 					 $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
       
 				  },
 				dataType: "text",
 				
 			}
 		);
	
	return isvalid;
}
function loadMainDivPage(urltoload){	
	
		if (urltoload==null || urltoload=="" || urltoload=="back") {
			//If back page is detected, load cookie address.
			urltoload = $.cookie('backurl');				
		} 
		
		$("#pageContentBody").load(urltoload);		
		 $("#loadingSpinner").css("display","inline");		 
	
				 
		//$('#printJob').css('height', 600);
		//$('#printJob').css('width', 600);
		
}
/************************************************
function used to load model dialog on main screen
pass in claim id, what type of dialog should show
and information used to poplulate labels on dialog
*************************************************/
function openModalDialog(claimid,dialogtype,otherinfo){
	var options = {
           backdrop :false,
            show:true
      	};
		$('#buttonleft').off('click');
		
if(dialogtype == "submitclaim"){
			$('#maintitle').html("<span style='color:Green;'>Submit this Claim?</span>");
			$('#title1').html("<b>CLAIM:</b> " + otherinfo +"<br/>");
			$('#title2').html("Are you sure you want to submit this claim for procressing?");
			$('#title3').html("");
			$('#title4').html("");
			$('#buttonleft').text("YES");
			$('#buttonright').text("NO");
			$('#selectbox').hide();
			$('#glaccountbox').hide();
			$('#declinenotes').hide();
			$('#teacherpaybox').hide();
			$('#sdsvendorbox').hide();
			//now we add the onclick event
			$("#buttonleft").click(function(){
				submittravelclaim(claimid);
			});
			
}else if(dialogtype == "deleteclaim"){
			$('#maintitle').html("<span style='color:Red;'>Delete This Claim?</span>");
			$('#title1').html("<b>CLAIM:</b> " + otherinfo +"<br/>");
			$('#title2').html("Are you sure you want to delete this claim?");
			$('#title3').html("");
			$('#title4').html("");
			$('#buttonleft').text("YES");
			$('#buttonright').text("NO");
			$('#selectbox').hide();
			$('#glaccountbox').hide();
			$('#declinenotes').hide();
			$('#teacherpaybox').hide();
			$('#sdsvendorbox').hide();
			//now we add the onclick event
			$("#buttonleft").click(function(){
				deletetravelclaim(claimid)
			});
			
}else if(dialogtype == "changesupervisor"){
			$('#maintitle').html("<span style='color:#004178;'>Change Your Supervisor</span>");
			$('#title1').html("");
			$('#title2').html("Select your supervisor from the list below. If not listed, please contact your supervisor or support.");
			$('#title3').html("");
			$('#title4').html("");
			$('#buttonleft').text("OK");
			$('#buttonright').text("Cancel");
			$('#selectbox').show();
			$('#glaccountbox').hide();
			$('#declinenotes').hide();
			$('#teacherpaybox').hide();
			$('#sdsvendorbox').hide();
			//now we add the onclick event
			$("#buttonleft").click(function(){
				updatetravelclaimsupervisor(claimid)
			});
			
}else if (dialogtype == "supervisorapprove"){
			$('#maintitle').html("<span style='color:Green;'>Approve this Claim?</span>");
			var res = otherinfo.split(",");
			$('#title1').html("<b>CLAIMANT:</b><span style='text-transform: capitalize;'> "+ res[1] +"</span>");
			$('#title2').html("<br/><b>CLAIM:</b>" + res[0]);
			$('#title3').html("<br/>Are you sure you want to <b>APPROVE</b> this claim?");
			$('#title4').html("<div class='alert alert-info'><b>NOTE:</b> A Cayenta account code MUST be used for all claims going forward. Acceptable formats are as follows:<br/>For DAC: ##.####.### (ex. 30.3200.699)<br/>For JOB: AA####-A###&#92;#### (ex. SF9110-A699&#92;3200)<br/>Codes entered in a format other than the above will be rejected.</div>");
			$('#buttonleft').text("YES");
			$('#buttonright').text("NO");
			$('#selectbox').hide();
			$('#glaccountbox').show();
			$('#declinenotes').hide();
			$('#teacherpaybox').hide();
			$('#sdsvendorbox').hide();
			$("#buttonleft").click(function(){
								if(approvedeclinetravelclaim(claimid,"APPROVED")){
					
				}else{
					return false;
				}
			});

}else if (dialogtype == "supervisordecline"){
				$('#maintitle').html("<span style='color:Red;'>Decline Claim?</span>");
				var res = otherinfo.split(",");
				$('#title1').html("<b>CLAIMANT:</b><span style='text-transform: capitalize;'> "+ res[1] +"</span>");
				$('#title2').html("<br/><b>CLAIM:</b>  " + res[0]);
				$('#title3').html("<br/>Are you sure you want to <b>DECLINE</b> this claim?");
				$('#title4').text("");
				$('#buttonleft').text("YES");
				$('#buttonright').text("NO");
				$('#selectbox').hide();
				$('#glaccountbox').hide();
				$('#declinenotes').show();
				$('#teacherpaybox').hide();
				$('#sdsvendorbox').hide();
				$("#buttonleft").click(function(){
					approvedeclinetravelclaim(claimid,"DECLINED");
				});
				
}else if (dialogtype == "paytravelclaim"){
			$('#maintitle').html("<span style='color:Green;'>Pay This Claim?</span>");
			var res = otherinfo.split(",");
			$('#title1').html("<b>CLAIMANT:</b><span style='text-transform: capitalize;'> "+ res[1] +"</span>");
			$('#title2').html("<br/><b>CLAIM:</b>  " + res[0]);
			$('#title3').html("Are you sure you want to <b>PAY</b> this claim?");
			$('#title4').html("");
			$('#buttonleft').text("YES");
			$('#buttonright').text("NO");
			$('#selectbox').hide();
			$('#glaccountbox').show();
			$('#declinenotes').hide();
			$('#teacherpaybox').show();
			$('#sdsvendorbox').show();
			$('#optionaltitle').text('');
			$("#buttonleft").click(function(){
				if(paytravelclaim(claimid)){
					
				}else{
					return false;
				}
			});
			
}else if (dialogtype == "paypendingtravelclaim"){
			$('#maintitle').html("<span style='color:#004178;'>Payment Pending Travel Claim</span>");			
			var res = otherinfo.split(",");
			$('#title1').html("<b>CLAIMANT:</b><span style='text-transform: capitalize;'> "+ res[1] +"</span>");
			$('#title2').html("<br/><b>CLAIM:</b>  " + res[0]);
			$('#title3').html("Are you sure you want to make claim <b>PENDING</b>?");
			$('#title4').html("");
			$('#buttonleft').text("YES");
			$('#buttonright').text("NO");
			$('#selectbox').hide();
			$('#glaccountbox').hide();
			$('#declinenotes').show();
			$('#teacherpaybox').hide();
			$('#sdsvendorbox').hide();
			$('#optionaltitle').text('');
			$("#buttonleft").click(function(){
				paymentpendingtravelclaim(claimid);
			});
			
}else if (dialogtype == "travelclaimnote"){		
			$('#maintitle').html("<span style='color:#004178;'>Travel Claim Note</span>");	
			var res = otherinfo.split(",");
			$('#title1').html("<b>CLAIMANT:</b> <span style='text-transform: capitalize;'> "+ res[1] +"</span><br/>");
			$('#title2').html("<b>CLAIM:</b>  " + res[0]);
			$('#title3').html("");
			$('#title4').html("");			
			$('#buttonleft').text("Add");
			$('#buttonright').text("Close");
			$('#selectbox').hide();
			$('#glaccountbox').hide();
			$('#declinenotes').show();
			$('#teacherpaybox').hide();
			$('#sdsvendorbox').hide();
			$('#optionaltitle').text('');
			$("#buttonleft").click(function(){
				addnewtravelclaimnote(claimid);
			});
			
}else if (dialogtype == "savetravelclaim"){
			$('#maintitle').html("<span style='color:Green;'>Save Claim Changes?</span>");
			var res = otherinfo.split(",");
			$('#title1').html("<b>ITEM DATE:</b> "  + res[1]+ "<br/>");
			$('#title2').html("<b>CLAIM: </b>" + res[0] + "<br/>");
			$('#title3').html("<br/>Are you sure you want to <b>SAVE CHANGES</b> to this claim?");
			$('#title4').html("");
			$('#buttonleft').text("YES");
			$('#buttonright').text("NO");
			$('#selectbox').hide();
			$('#glaccountbox').hide();
			$('#declinenotes').hide();
			$('#teacherpaybox').hide();
			$('#sdsvendorbox').hide();
			$("#buttonleft").click(function(){
				savetravelclaimchanges(claimid);
			});

}else if (dialogtype == "deletetravelclaimitem"){
			$('#maintitle').html("<span style='color:Red;'>Delete Travel Claim Item?</span>");			
			var res = otherinfo.split(",");
			$('#title2').html("<b>ITEM DATE:</b> "  + res[1]+ "<br/>");
			$('#title3').html("<b>DESCRIPTION:</b> "  + res[2]+ "<br/>");
			$('#title1').html("<b>CLAIM:</b>  " + res[0] + "<br/>");
			$('#title4').html("<br/>Are you sure you want to DELETE to this claim item?");
			$('#buttonleft').text("YES");
			$('#buttonright').text("NO");
			$('#selectbox').hide();
			$('#glaccountbox').hide();
			$('#declinenotes').hide();
			$('#teacherpaybox').hide();
			$('#sdsvendorbox').hide();
			$("#buttonleft").click(function(){
				deletetravelclaimitem($('#id').val(),claimid);
			});
    	
		}
	$('#travelModal').modal(options);
}
/************************************************
used to populate edit item
*************************************************/
function loadEditItem(claimid,itemid){
	var surl="editTravelClaimItem.html?id=" + claimid + "&iid=" + itemid;
	$("#pageContentBody").load(surl);
	$('.cke_wysiwyg_frame').contents().find('.cke_editable').focus();
	//get values to reset datepickter after ajax
	var cm = $("#cm").val();
	var cy = $("#cy").val();
	var ldm = $("#ldm").val();
	refreshJquery(cm,cy,ldm);
}
/************************************************
call used to clear info from the new add item controls
*************************************************/
function unloadEditItem(claimid){
	var surl="viewTravelClaimDetails.html?id=" + claimid;
	$("#pageContentBody").load(surl);	
	//get values to reset datepickter after ajax
	var cm = $("#cm").val();
	var cy = $("#cy").val();
	var ldm = $("#ldm").val();
	refreshJquery(cm,cy,ldm);
}
/************************************************
call used to load search items returned
*************************************************/
function searchclaims(){	
	backurl="";	
	var searchtextnav= $('#search-text-nav').val();	
	var searchtexttop= $('#search-text-top').val();
	var searchtype= $('input[name=searchtype]:checked').val();	
	if(searchtype == ""){		
		$(".details_error_message").html("<i class='fas fa-exclamation-circle'></i> <b>ERROR:</b> Please select name or vendor option before searching. Please try again. <i class='fas fa-exclamation-circle'></i> ").css("display","block").delay(5000).fadeOut();
		$(".search-text").focus();
		return false;
	}	
	if(searchtextnav == "" && searchtexttop == ""){		
		$(".details_error_message").html("<i class='fas fa-exclamation-circle'></i> <b>ERROR:</b> Please enter search text before searching. Please try again. <i class='fas fa-exclamation-circle'></i> ").css("display","block").delay(5000).fadeOut();
		$(".search-text").focus();
		return false;
	}
	$("#loadingSpinner").css("display","inline");	
	var surl="viewSearchResults.html?";
	var searchtext = searchtextnav+searchtexttop;
	$.cookie('backurl', surl+"txt="+searchtext+"&type="+searchtype, {expires: 1 });	
	$("#pageContentBody").load(surl,{txt:searchtext,type:searchtype});

}
/************************************************
call used to refresh jquery objects after ajax post
breaks them
*************************************************/
function refreshJquery(cm,cy,ldm){

	setTimeout(function() {
		$( ".requiredinput_date" ).datepicker({
		      	changeMonth: false,//this option for allowing user to select month
		      	changeYear: false, //this option for allowing user to select from year range
		      	dateFormat: "dd/mm/yy",
		      	minDate: new Date(cy, cm, 1),
	    		maxDate: new Date(cy,cm, ldm)
		    	//set date range for claim
		 	});
		$("#glaccount").mask("9-9999-9-99-9999-99-999", {placeholder: "_-____-_-__-____-__-___"});
    },1000);
}
/************************************************
call used to load delete supervisor screen
*************************************************/
function deleteSupervisorRule(ruleid){
	$(".details_success_message").html("<b>SUCCESS:</b> Supervisor Rule deleted").delay(5000).fadeOut();
	var surl="deleteSupervisorRule.html?rule_id=" + ruleid;
	$("#pageContentBody").load(surl);
}
/************************************************
call used to load my profile screen
*************************************************/
function loadProfileScreen(op,id){
	var surl = "myProfile.html?op=" + op + "&id=" + id;
	$("#pageContentBody").load(surl);
}
function loadProfileScreenUpdate(op,id){
	var surl = "myProfile.html?id=" + id;
	$("#pageContentBody").load(surl);
}

jQuery(function(){
    $(".img-swap").hover(
         function(){this.src = this.src.replace("-off","-on");},
         function(){this.src = this.src.replace("-on","-off");});
	});


/**************************************************************
 * From Common.js
 * 
 */
	
function resizeIFrame(fid, mh)
{
	var fr = parent.document.getElementById(fid);
	fr.height = 0;
	fr.height = (((+document.body.scrollHeight)>(+mh))?(+document.body.scrollHeight) + (+25) :(+mh));
	//fr.height = (+document.body.scrollHeight);
	//fr.width = (+document.body.scrollWidth);
}

function process_message(id, str)
{
  var msg = document.getElementById(id);
  if(msg)
  {
    msg.innerHTML = "<table width='100%' cellspacing='0' cellpadding='0'><tr><td width='22' valign='middle'><img src='images/hour_glass_ani.gif'><br></td><td width='*' align='left' valign='middle' style='padding-left:3px;'>" + str + "</td></tr></table>";
  }
}

function toggleTableRowHighlight(target, color)
{
  var rowSelected = document.getElementById(target);
  
  rowSelected.style.backgroundColor=color; 
}

function toggleTableRow(target, state)
{
  var rowSelected = document.getElementById(target);
  
  rowSelected.style.display=state; 
}

function  validateDollar( fld ) {
/******************************************************************************
DESCRIPTION: Validates that a string contains only valid numbers.

PARAMETERS:
   strValue - String to be tested for validity

RETURNS:
   True if valid, otherwise false.
******************************************************************************/
  var objRegExp  =  /^((\$\d*)|(\$\d*\.\d{2})|(\$\d*\.\d{1})|(\d*)|(\d*\.\d{2})|(\d*\.\d{1})|(\$\d*\.)|(\d*\.)|(\$\d*)|(\-\$\d*\.\d{2})|(\-\$\d*\.\d{1})|(\-\d*)|(\-\d*\.\d{2})|(\-\d*\.\d{1})|(\-\$\d*\.)|(\-\d*\.))$/;
  var objRegExpNoDP  =  /^((\$\d*)|(\d*)|(\-\$\d*)|(\-\d*))$/;
  var objRegExpDPOnly  =  /^((\$\d*\.)|(\d*\.)|(\-\$\d*\.)|(\-\d*\.))$/;
  var objRegExp1DP = /^((\$\d*\.\d{1})|(\d*\.\d{1})|\-(\$\d*\.\d{1})|(\-\d*\.\d{1}))$/;
  var objRegExp2DP = /^((\$\d*\.\d{2})|(\d*\.\d{2})|(\-\$\d*\.\d{2})|(\-\d*\.\d{2}))$/;

  removeCurrency(fld);
  
  var temp_value = fld.value; 

  var check = true;

  if (!validateNotEmpty(temp_value)) 
  { 
    fld.value = "$0.00"; 
  } 
  else if (!objRegExp.test(temp_value)) 
  { 
    check = false;
    $(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>INVALID CURRENCY AMOUNT:</b> Please enter correct format of $0.00.").css("display","block").delay(5000).fadeOut();
    fld.focus(); 
    fld.select(); 
  }
  else
  {
    if(objRegExpNoDP.test(temp_value))
    {
      fld.value = fld.value + '.00';
    }
    else if(objRegExpDPOnly.test(temp_value))
    {
      fld.value = fld.value + '00';
    }
    else if(objRegExp1DP.test(temp_value))
    {
      fld.value = fld.value + '0';
    }
      
    addCurrency(fld);
  }
  return check;
}

function  validateNumeric( strValue ) {
/******************************************************************************
DESCRIPTION: Validates that a string contains only valid numbers.

PARAMETERS:
   strValue - String to be tested for validity

RETURNS:
   True if valid, otherwise false.
******************************************************************************/
  var objRegExp  =  /(^-?\d\d*\.\d*$)|(^-?\d\d*$)|(^-?\.\d\d*$)/;

  var temp_value = fld.value; 

  var check = true;

  //check for numeric characters
  return objRegExp.test(strValue);
}

function validateInteger(fld) {
/************************************************
DESCRIPTION: Validates that a string contains only
    valid integer number.

PARAMETERS:
   strValue - String to be tested for validity

RETURNS:
   True if valid, otherwise false.
******************************************************************************/
  var objRegExp  = /(^-?\d\d*$)/;
  var temp_value = fld.value; 
  var check = true;

  if (!validateNotEmpty(temp_value)) 
  { 
    fld.value = 0;
  }
  else if (!objRegExp.test(temp_value)) 
  { 
    check = false;
    $(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>KM ENTRY ERROR:</b> Please round to nearest number. (i.e. 2.8km will be 3km, 2.4km will be 2km.)").css("display","block").delay(5000).fadeOut();
    fld.focus(); 
    fld.select(); 
  } 

  //check for integer characters
  return check;
}

function validateTime(fld) {

  var objRegExp  = /(^\d{1,2}(:\d{2})?\s?([aApP][mM])$)/;
  var temp_value = fld.value; 
  var check = true;

  if (!objRegExp.test(temp_value)) 
  { 
    check = false;
    $(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>INVALID TIME FORMAT:</b> Please enter standard time format(i.e. 12:00am).").css("display","block").delay(5000).fadeOut();
    fld.focus(); 
    fld.select(); 
  } 

  //check for integer characters
  return check;
}

function validateNotEmpty( strValue ) {
/************************************************
DESCRIPTION: Validates that a string is not all
  blank (whitespace) characters.

PARAMETERS:
   strValue - String to be tested for validity

RETURNS:
   True if valid, otherwise false.
*************************************************/
   var strTemp = strValue;
   strTemp = trimAll(strTemp);
   if(strTemp.length > 0){
     return true;
   }
   return false;
}

function validateValue( strValue, strMatchPattern ) {
/************************************************
DESCRIPTION: Validates that a string a matches
  a valid regular expression value.

PARAMETERS:
   strValue - String to be tested for validity
   strMatchPattern - String containing a valid
      regular expression match pattern.

RETURNS:
   True if valid, otherwise false.
*************************************************/
var objRegExp = new RegExp( strMatchPattern);

 //check if string matches pattern
 return objRegExp.test(strValue);
}


function rightTrim( strValue ) {
/************************************************
DESCRIPTION: Trims trailing whitespace chars.

PARAMETERS:
   strValue - String to be trimmed.

RETURNS:
   Source string with right whitespaces removed.
*************************************************/
var objRegExp = /^([\w\W]*)(\b\s*)$/;

      if(objRegExp.test(strValue)) {
       //remove trailing a whitespace characters
       strValue = strValue.replace(objRegExp, '$1');
    }
  return strValue;
}

function leftTrim( strValue ) {
/************************************************
DESCRIPTION: Trims leading whitespace chars.

PARAMETERS:
   strValue - String to be trimmed

RETURNS:
   Source string with left whitespaces removed.
*************************************************/
var objRegExp = /^(\s*)(\b[\w\W]*)$/;

      if(objRegExp.test(strValue)) {
       //remove leading a whitespace characters
       strValue = strValue.replace(objRegExp, '$2');
    }
  return strValue;
}

function trimAll( strValue ) {
/************************************************
DESCRIPTION: Removes leading and trailing spaces.

PARAMETERS: Source string from which spaces will
  be removed;

RETURNS: Source string with whitespaces removed.
*************************************************/
 var objRegExp = /^(\s*)$/;

    //check for all spaces
    if(objRegExp.test(strValue)) {
       strValue = strValue.replace(objRegExp, '');
       if( strValue.length == 0)
          return strValue;
    }

   //check for leading & trailing spaces
   objRegExp = /^(\s*)([\W\w]*)(\b\s*$)/;
   if(objRegExp.test(strValue)) {
       //remove leading and trailing whitespace characters
       strValue = strValue.replace(objRegExp, '$2');
    }
  return strValue;
}

function removeCurrency( fld ) {
/************************************************
DESCRIPTION: Removes currency formatting from
  source string.

PARAMETERS:
  strValue - Source string from which currency formatting
     will be removed;

RETURNS: Source string with commas removed.
*************************************************/
	var objRegExp = /\-/;
  //var strMinus = '';

  //check if negative
  //if(objRegExp.test(fld.value)){
  //  strMinus = '-';
  //}

  objRegExp = /\)|\(|[,]/g;
  fld.value = fld.value.replace(objRegExp,'');
  if(fld.value.indexOf('$') >= 0){
    fld.value = fld.value.substring(1, fld.value.length);
  }
  //fld.value =  strMinus + fld.value;
}

function addCurrency( fld ) {
/************************************************
DESCRIPTION: Formats a number as currency.

PARAMETERS:
  strValue - Source string to be formatted

REMARKS: Assumes number passed is a valid
  numeric value in the rounded to 2 decimal
  places.  If not, returns original value.
*************************************************/
  var objRegExp = /-?[0-9]+\.[0-9]{2}$/;
  	removeCurrency(fld);
    if(objRegExp.test(fld.value)) {
      objRegExp.compile('^-');
      fld.value = addCommas(fld.value);
      //if (objRegExp.test(fld.value)){
      //  fld.value = '(' + fld.value.replace(objRegExp,'') + ')';
      //}
      fld.value =  '$' + fld.value;
    }
}

function removeCommas( strValue ) {
/************************************************
DESCRIPTION: Removes commas from source string.

PARAMETERS:
  strValue - Source string from which commas will
    be removed;

RETURNS: Source string with commas removed.
*************************************************/
  var objRegExp = /,/g; //search for commas globally

  //replace all matches with empty strings
  return strValue.replace(objRegExp,'');
}

function addCommas( strValue ) {
/************************************************
DESCRIPTION: Inserts commas into numeric string.

PARAMETERS:
  strValue - source string containing commas.

RETURNS: String modified with comma grouping if
  source was all numeric, otherwise source is
  returned.

REMARKS: Used with integers or numbers with
  2 or less decimal places.
*************************************************/
  var objRegExp  = new RegExp('(-?[0-9]+)([0-9]{3})');

    //check for match to search criteria
    while(objRegExp.test(strValue)) {
       //replace original string with first group match,
       //a comma, then second group match
       strValue = strValue.replace(objRegExp, '$1,$2');
    }
  return strValue;
}

function removeCharacters( strValue, strMatchPattern ) {
/************************************************
DESCRIPTION: Removes characters from a source string
  based upon matches of the supplied pattern.

PARAMETERS:
  strValue - source string containing number.

RETURNS: String modified with characters
  matching search pattern removed

USAGE:  strNoSpaces = removeCharacters( ' sfdf  dfd',
                                '\s*')
*************************************************/
 var objRegExp =  new RegExp( strMatchPattern, 'gi' );

 //replace passed pattern matches with blanks
  return strValue.replace(objRegExp,'');
}

function validateAddClaimItem(frm)
{
  var check = true;
  if(!validateNotEmpty(frm.item_date.value))
  {
    $(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>DATE ERROR:</b> Date is a required field.").css("display","block").delay(5000).fadeOut();
    check = false;
  }
  else if(!validateNotEmpty(frm.item_desc.value))
  {
	  $(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>DESCRIPTION IS EMPTY:</b> Description is a required field.").css("display","block").delay(5000).fadeOut();
	 check = false;
  }
  else if(!validateInteger(frm.item_kms))
  {
   $(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>INVALID ENTRY:</b> Please enter a integer only.").css("display","block").delay(5000).fadeOut();
	check = false;
  }
  else if(!validateDollar(frm.item_meals))
  {
  $(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>INVALID ENTRY:</b> Please enter a integer only.").css("display","block").delay(5000).fadeOut();
	check = false;
  }
  else if(!validateDollar(frm.item_lodging))
  {
  $(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>INVALID ENTRY:</b> Please enter a integer only.").css("display","block").delay(5000).fadeOut();
	check = false;
  }
  else if(!validateDollar(frm.item_other))
  {
  $(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>INVALID ENTRY:</b> Please enter a integer only.").css("display","block").delay(5000).fadeOut();
	check = false;
  }
  else if(validateNotEmpty(frm.item_departure_time) && !validateTime(frm.item_departure_time))
  {
  $(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>INVALID ENTRY:</b> Please enter a time only.").css("display","block").delay(5000).fadeOut();
	check = false;
  }
  else if(validateNotEmpty(frm.item_return_time) && !validateTime(frm.item_return_time))
  {
  $(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>INVALID ENTRY:</b> Please enter a time only.").css("display","block").delay(5000).fadeOut();
	check = false;
  }

  if(check)
  {
    removeCurrency(frm.item_meals);
    removeCurrency(frm.item_lodging);
    removeCurrency(frm.item_other);
  }
  
  return check;
}

/************************************************
User level validation for add new travel claim
km rate
*************************************************/
function addnewtravelclaimkmrate(){
	$('.details_error_message').html("").css("display","none");
	//check mandatory fields first
	if($("#effstartdate").val() == ""){
		$('.details_error_message').html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>START DATE ERROR:</b> Please enter Effective Start Date.").css("display","block").delay(5000).fadeOut();
		$("#effstartdate").focus();
		return false;
	}
	if($("#effenddate").val() == ""){
		$(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>END DATE ERROR:</b> Please enter Effective End Date").css("display","block").delay(5000).fadeOut();
		$( "#effenddate").focus();
		return false;
	}
	if($("#basekmrate").val() == ""){
		$(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>BASE RATE ERROR:</b> Please Enter value for Base Rate").css("display","block").delay(5000).fadeOut();
		$( "#basekmrate").focus();
		return false;
	}
	if($("#approvedkmrate").val() == ""){
		$(".details_error_message").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>APPROVED RATE ERROR:</b> Please Enter value for Approved Rate").css("display","block").delay(5000).fadeOut();
		$( "#approvedkmrate").focus();
		return false;
	}
	//all good submit ajax post
	$('.details_error_message').html("").css("display","none");
	ajaxAddNewTravelClaimKMRate();
	
	
}
/************************************************
Calls ajax post for adding new travel claim km rate
*************************************************/
function ajaxAddNewTravelClaimKMRate()
{
	var sd = $("#effstartdate").val();
	var ed = $("#effenddate").val();
	var br = $("#basekmrate").val();
	var ar = $("#approvedkmrate").val();
	var optype = $("#op").val();
	$.ajax({
        url: 'addNewTravelClaimKmRateAjax.html',
        type: 'POST',
        data: {estartdate: sd,eenddate: ed,basekmrate: br,approvedkmrate: ar, op: optype},
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "SUCCESS")
						{
							if(optype == "ADD"){
								$(".details_success_message").html("<b>SUCCESS:</b> Travel Claim KM Rate Has Been Added").css("display","block").delay(5000).fadeOut();
							}else{
								$(".details_success_message").html("<b>SUCCESS:</b>  Travel Claim KM Rate Has Been Updated").css("display","block").delay(5000).fadeOut();
							}
							
			    			//$('#mainalert').show();
							var surl="listKmRates.html";
			            	$("#pageContentBody").load(surl);
							
							
						}else{
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();
							//$("#mainalert").show();
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
						//$("#mainalert").show();
				  },
				dataType: "text",
				async: false
	
        
    });
	
}
/************************************************
Opens dialog for deleting km rate confirmation
*************************************************/
function opendeletedialog(sdate,edate,brate,arate){
	var options = {
            "backdrop" : "static",
            "show":true
    };
	$('#maintitle').html("Delete this KM Rate?");
	$('#title1').html("KM Rate For " + sdate + " to " + edate);
	$('#title2').html("<br/><b>Base Rate:</b> " + brate + "    <b>Approved Rate:</b> " + arate);
	$('#title3').html("<br/>Are you sure you want to delete this KM Rate?");
	$('#buttonleft').text("YES");
	$('#buttonright').text("NO");
	//now we add the onclick event
	$("#buttonleft").click(function(){
		deletekmrate(sdate,edate);
	});
	$('#travelModal').modal(options);

}

/************************************************
Calls ajax post for delete km rate
*************************************************/
function deletekmrate(sdate,edate){
	$.ajax({
        url: 'deleteKmRateAjax.html',
        type: 'POST',
        data: {estartdate:sdate,eenddate:edate},
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "SUCCESS")
						{
							$(".details_success_message").html("<b>SUCCESS:</b>  Travel Claim Rate Has Been Deleted.").css("display","block").delay(5000).fadeOut();
			    			//$('#mainalert').show();
			    			
							var surl="listKmRates.html";
			            	$("#pageContentBody").load(surl);
			            	$('#travelModal').modal('hide');
							
						}else{
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();
							//$("#mainalert").show();
							$('#travelModal').modal('hide');
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
						//$("#mainalert").show();
				  },
				dataType: "text",
				async: false
	
        
    });	
	
}

/************************************************
Function called when user clicks letter button on
Travel Claims Pre-Submission Screen.  Clears table,
passes Letter to ajax function and returns claims
paid by that letter.
*************************************************/
function getpresubmissiontravelclaimsbyletter(letter,date1)
{	
	cleartable();
	ajaxRequestInfoPreSubmission(letter,date1);
	//window.parent.$('#claim_details').css('height', $('#claims-table').height()+100);
	//$("#claim_details").style.height = $("#claims-table").offsetHeight + 'px';   
}
/************************************************
Calls ajax post for getting Claim Pending 
by letter
*************************************************/
function ajaxRequestInfoPreSubmission(sletter,sdate)
{
	
	var d = new Date();
	var n = d.getFullYear(); 
	var cnt = 0;
	
	var isvalid=false;
	$.ajax(
 			{
 				type: "POST",  
 				url: "getTravelClaimsPreSubmissionByLetterAjax.html",
 			
 				data: {
 					letter: sletter,
 					date1: sdate
 				}, 
 				
 				beforeSend: function(){

 					$("#loadingSpinner").css("display","inline");

 				   },
 				
 				success: function(xml){
 					
 					var newrow="";
 					var amount ="";
 					$("#loadingSpinner").css("display","none");
 					
 					$(xml).find('CLAIM').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "LISTFOUND")
 								{
 								newrow+="<tr>";
                                newrow += "<td  style='vertical-align:middle;'>" + $(this).find("EMPLOYEE").text() + "</td>";
                                newrow += "<td  style='vertical-align:middle;'>" + $(this).find("CLAIMDATE").text().substr($(this).find("CLAIMDATE").text().length - 4) + "</td>";
                                if($(this).find("TYPE").text() == "PD")	{								
                                	newrow += "<td style='vertical-align:middle;color:white;text-align:center;background-color:#ff8400;font-weight:bold;'>PD CLAIM </td>";
								} else {
									newrow += "<td style='vertical-align:middle;color:white;text-align:center;background-color:#1c90ec;font-weight:bold;'>MONTHLY CLAIM</td>";								
								}            
                                newrow += "<td  style='vertical-align:middle;'>" + $(this).find("TITLE").text() + "</td>";             
                                amount=+($(this).find('TOTAL').text());                                  
                                newrow += "<td  style='vertical-align:middle;'> $" + amount.toFixed(2) + "</td>";                            
                                newrow += "<td style='vertical-align:middle;'>" + $(this).find("SUPERVISOR").text() + "</td>"; 
                          	  	if($(this).find("ZONE").text() == "avalon")	{		
									 newrow += "<td class='region1solid' style='text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>AVALON</td>";								
								  } else if($(this).find("ZONE").text() == "central")	{
									 newrow += "<td class='region2solid' style='text-align:center;vertical-align:middle;color:rgba(255,255,255, 1);font-weight:bold;'>CENTRAL</td>";	
								 } else if($(this).find("ZONE").text() == "western")	{
									 newrow += "<td class='region3solid' style='text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>WESTERN</td>";	
								 } else if($(this).find("ZONE").text() == "labrador")	{
									 newrow += "<td class='region4solid' style='text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>LABRADOR</td>";	
								 } else if($(this).find("ZONE").text() == "provincial")	{
									 newrow += "<td class='region5solid' style='text-align:center;color:rgba(255,255,255,1);vertical-align:middle;font-weight:bold;'>PROVINCIAL</td>";	
								 } else {
									 newrow += "<td style='background-color:Black;text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>UNKNOWN</td>";	 									
								}                              	  
                                newrow += "<td  style='vertical-align:middle;'>";										
								newrow += "<a href='#' class='btn btn-xs btn-primary'  onclick='loadingData();loadMainDivPage(&quot;viewTravelClaimDetails.html?id=" + $(this).find("ID").text() + "&quot;);'>VIEW</a></td>";
								newrow += "</tr>";								
								isvalid=true;	                   				
 								}
						}); 					
					$("#presub-table tbody").append(newrow);
					$("#presub-table").DataTable({	 
						  "order": [[ 1, "desc" ],[0,"asc"]],
						   "responsive": true,
						  dom: 'Blfrtip',						  
					        buttons: [			        	
					        	//'colvis',
					        	'copy', 
					        	'csv', 
					        	'excel', 
					        	{
					                extend: 'pdfHtml5',
					                footer:true,
					                //orientation: 'landscape',
					                messageTop: 'Pre-Submission Travel/PD Claims ',
					                messageBottom: null,
					                exportOptions: {
					                    columns: [ 0, 1, 2, 3,4,5,6 ]
					                }
					            },
					        	{
					                extend: 'print',
					                //orientation: 'landscape',
					                footer:true,
					                messageTop: 'Pre-Submission Travel/PD Claims',
					                messageBottom: null,
					                exportOptions: {
					                    columns: [ 0, 1, 2, 3,4,5,6]
					                }
					            }
					        ],
						  "lengthMenu": [[100, 250, 500,  -1], [100, 250, 500, "All"]]							
					}); 				
		
			},
			
		  error: function(xhr, textStatus, error){
			 $(".details_error_message").html(error).css("display","block");

		  },
		dataType: "text",
		//async: false
	}
);

return isvalid;

}	
/************************************************
Function called when user clicks letter button on
Travel Claims Rejected Screen.  Clears table,
passes Letter to ajax function and returns claims
paid by that letter.
*************************************************/
function getrejectedtravelclaimsbyletter(letter,year)
{	
	cleartable();
	ajaxRequestInfoRejected(letter,year);
	
}
/************************************************
Calls ajax post for getting Claim Pending 
by letter
*************************************************/
function ajaxRequestInfoRejected(sletter,sdate)
{
	
	var d = new Date();
	var n = d.getFullYear(); 
	var cnt = 0;
	
	var isvalid=false;
	$.ajax(
 			{
 				type: "POST",  
 				url: "getTravelClaimsRejectedByLetterAjax.html",
 			
 				data: {
 					letter: sletter,
 					year: sdate
 				}, 
 				
 				beforeSend: function(){

 					$("#loadingSpinner").css("display","inline");

 				   },
 				
 				success: function(xml){
 					
 					var newrow="";
 					var amount ="";
 					$("#loadingSpinner").css("display","none");
 										
 					$(xml).find('CLAIM').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "LISTFOUND")
 								{
 																			
 									
 								 	newrow+="<tr>";
                                    newrow += "<td  style='vertical-align:middle;'>" + $(this).find("EMPLOYEE").text() + "</td>";
                                    newrow += "<td  style='vertical-align:middle;'>" + $(this).find("CLAIMDATE").text().substr($(this).find("CLAIMDATE").text().length - 4) + "</td>";
                                    if($(this).find("TYPE").text() == "PD")	{								
                                    	newrow += "<td style='vertical-align:middle;color:white;text-align:center;background-color:#ff8400;font-weight:bold;'>PD CLAIM </td>";
									} else {
										newrow += "<td style='vertical-align:middle;color:white;text-align:center;background-color:#1c90ec;font-weight:bold;'>MONTHLY CLAIM</td>";								
									}            
                                    newrow += "<td  style='vertical-align:middle;'>" + $(this).find("TITLE").text() + "</td>";             
                                    amount=+($(this).find('TOTAL').text());                                  
                                    newrow += "<td  style='vertical-align:middle;'> $" + amount.toFixed(2) + "</td>";                            
                                    newrow += "<td style='vertical-align:middle;'>" + $(this).find("SUPERVISOR").text() + "</td>"; 
                              	  	if($(this).find("ZONE").text() == "avalon")	{		
										 newrow += "<td class='region1solid' style='text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>AVALON</td>";								
									  } else if($(this).find("ZONE").text() == "central")	{
										 newrow += "<td class='region2solid' style='text-align:center;vertical-align:middle;color:rgba(255,255,255, 1);font-weight:bold;'>CENTRAL</td>";	
									 } else if($(this).find("ZONE").text() == "western")	{
										 newrow += "<td class='region3solid' style='text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>WESTERN</td>";	
									 } else if($(this).find("ZONE").text() == "labrador")	{
										 newrow += "<td class='region4solid' style='text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>LABRADOR</td>";	
									 } else if($(this).find("ZONE").text() == "provincial")	{
										 newrow += "<td class='region5solid' style='text-align:center;color:rgba(255,255,255,1);vertical-align:middle;font-weight:bold;'>PROVINCIAL</td>";	
									 } else {
										 newrow += "<td style='background-color:Black;text-align:center;color:rgba(255,255,255, 1);vertical-align:middle;font-weight:bold;'>UNKNOWN</td>";	 									
									}                              	  
                                    newrow += "<td  style='vertical-align:middle;'>";										
									newrow += "<a href='#' class='btn btn-xs btn-primary'  onclick='loadingData();loadMainDivPage(&quot;viewTravelClaimDetails.html?id=" + $(this).find("ID").text() + "&quot;);'>VIEW</a></td>";
									newrow += "</tr>";								
									isvalid=true;	                   				
 								}
 								}); 					
 							$("#rejected-table tbody").append(newrow);
 							
 							$("#rejected-table").DataTable({	 
 								  "order": [[ 1, "desc" ],[0,"asc"]],
 								   "responsive": true,
 								  dom: 'Blfrtip',
 							        buttons: [			        	
 							        	//'colvis',
 							        	'copy', 
 							        	'csv', 
 							        	'excel', 
 							        	{
 							                extend: 'pdfHtml5',
 							                footer:true,
 							                //orientation: 'landscape',
 							                messageTop: 'Rejected Travel/PD Claims ',
 							                messageBottom: null,
 							                exportOptions: {
 							                    columns: [ 0, 1, 2, 3,4,5,6 ]
 							                }
 							            },
 							        	{
 							                extend: 'print',
 							                //orientation: 'landscape',
 							                footer:true,
 							                messageTop: 'Rejected Travel/PD Claims',
 							                messageBottom: null,
 							                exportOptions: {
 							                    columns: [ 0, 1, 2, 3,4,5,6]
 							                }
 							            }
 							        ],
 								  "lengthMenu": [[100, 250, 500,  -1], [100, 250, 500, "All"]]							
 							}); 		
 				
 		
 							
					},
					
 				  error: function(xhr, textStatus, error){
 					 $(".details_error_message").html(error).css("display","block");
       
 				  },
 				dataType: "text",
 				//async: false
 			}
 		);
	
	return isvalid;
	
}	

/************************************************
Calls ajax post to get pdclaim event details
*************************************************/
function getpdclaimeventdetails(){
	var eventid= $("#pdtitle").val();
	$.ajax({
        url: 'getPDClaimEventDetails.html',
        type: 'POST',
        data: {pid:eventid},
        success: function(xml) {
        		$(xml).find('PDEVENT').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "SUCCESS")
						{
							//$(".details_success_message").html("<b>SUCCESS:</b> Travel Claim Item Has Been Deleted").css("display","block").delay(5000).fadeOut();
			    		
							$('#desc').val('');
							//==== SET TEXT IN CKEDITOR ========
							CKEDITOR.instances['desc'].setData($(this).find("EVENTDESCRIPTION").text());	
							$("#title").val($(this).find("EVENTNAME").text());
							$("#start_date").val($(this).find("EVENTSTARTDATE").text());
							$("#finish_date").val($(this).find("EVENTENDDATE").text());						
							
						}else{
							$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();
							
							$('#myModal').modal('hide');
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
						//$("#mainalert").show();
				  },
				dataType: "text",
				async: false
	
        
    });	
	
}


//Supervisor Rules----------------------------------------------------------------------------------------------------------------------------------------------


function initRequest() 
{
  var xmlHttp = null;
  
  try
  {    
    // Firefox, Opera 8.0+, Safari    
    xmlHttp=new XMLHttpRequest();    
  }
  catch (e)
  {    
    // Internet Explorer    
    try
    {      
      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
    }
    catch (e)
    {      
      try
      {        
        xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");        
      }
      catch (e)
      {        
        alert("Your browser does not support AJAX!");
      }      
    }    
  }
  
  return xmlHttp;
}


function onKeyTypeSelected(keytype, kid)
{
  if(keytype.value == -1)
  {
    return;
  }
  
  if(keytype.name == 'supervisor_keytype')
  {
	  $("#dataLoading1").css("display","block");
	  $("#supervisor_row").css("display","none");	  
  }
  else if(keytype.name == 'user_keytype')
  {	  
	  $("#dataLoading2").css("display","block");
	  $("#user_row").css("display","none");

  }
  
  var xmlHttp = initRequest();
  
  if(xmlHttp)
  {
    xmlHttp.onreadystatechange = function()
    {
      parseKeyTypeSelection(xmlHttp, keytype, kid);
    }
    xmlHttp.open("GET","addSupervisorRule.html?op=KEYTYPE_SELECTED&kt="+keytype.value,true);
    xmlHttp.send(null);
  }
  else
  	alert('xmlHttp not initialized.');
}

function parseKeyTypeSelection(xmlHttp, keytype, kid)
{
  if(xmlHttp.readyState==4)
  {
    var xmlDoc=xmlHttp.responseXML.documentElement;
    
    var r = null;
    var r_c = null;
    var r_str = null;
    var loading = null;  
    r_str = "";
    if(keytype.name == 'supervisor_keytype')
    {    	
    	
	    if(keytype.value == 1)
	    {	  	
	    	r_str = generateSelectElement(xmlDoc.getElementsByTagName("ROLE"), "supervisor_key", kid);
	    	$("#supervisor_row").css("display","block");
	    }
	    else if(keytype.value == 2)
	    {
	    	r_str = generateSelectElement(xmlDoc.getElementsByTagName("PERSONNEL"), "supervisor_key", kid);
	    	$("#supervisor_row").css("display","block");
	    }
	    $("#supervisor_row_content").html(r_str); 	 
	    $("#dataLoading1").css("display","none");			  
	  }
	  else if(keytype.name == 'user_keytype')
	  {
    	 if(keytype.value == 1)
	    {
	    	r_str = generateSelectElement(xmlDoc.getElementsByTagName("ROLE"), "user_key", kid);
	    	$("#user_row").css("display","block");
	    }
	    else if(keytype.value == 2)
	    {   	
	    	r_str = generateSelectElement(xmlDoc.getElementsByTagName("PERSONNEL"), "user_key", kid);
	    	$("#user_row").css("display","block");
	    }
	    $("#user_row_content").html(r_str); 	  
	    $("#dataLoading2").css("display","none");
	  }
	  
  }
}



function generateSelectElement(beans, ele_name, kid)
{
	var ele = "<SELECT name='" + ele_name + "' class='form-control' " + " id='" + ele_name + "'>";
	  
  if(beans.length > 0)
    for(var i = 0; i < beans.length; i++)
    	ele = ele + "<OPTION value='" +beans[i].childNodes[0].childNodes[0].nodeValue+ "'" 
    	+ ((beans[i].childNodes[0].childNodes[0].nodeValue == kid) ? ' SELECTED' : '') + ">" +beans[i].childNodes[1].childNodes[0].nodeValue+ "</OPTION>";
 
  ele = ele + "</SELECT>";
  
  return ele;
}

function addnewmembers(){
	var ids="";
	$.each($("#available option:selected"), function(){
		//alert($(this).val() + "-" + $(this).text());
		// add them to the other listbox
		var sadd="<option value='" + $(this).val() + "'>" + $(this).text() + "</option>";
		var sremove="#available option[value=" + $(this).val() + "]";
		if(ids == ""){
			ids=$(this).val()
		}else{
			ids=ids + "," + $(this).val()
		}
		 response = $(this).text().substring($(this).text().indexOf("["));
		 response = response.substring(1);
		response = response.slice(0, -1); 
		thename =  $(this).text().indexOf(" [");
		thename = $(this).text().slice(0, thename);
		$('#assigned')
        .append(sadd);
        //now we remove the selected ones
		$(sremove).remove();
    });
        
    updateVal1 = parseInt($("#rrCount").text()) -1;
    updateVal2 = parseInt($("#aprCount").text()) +1;
   $("#aprCount").text(updateVal2);
   $("#rrCount").text(updateVal1);
   
   
   
    $("#claims-table").DataTable().row.add([
    thename, ids, response, '--','--'
    ]).draw(false); 
    
   
    
	//send ajax request with the ids to add
	addmemberstoapprovedrated(ids);
	
	//now resort the listbox
	  var select = $('#assigned');
	  select.html(select.find('option').sort(function(x, y) {
	    // to change to descending order switch "<" for ">"
	    return $(x).text() > $(y).text() ? 1 : -1;
	}));
}
/************************************************
Calls ajax post to add members to approved rates
*************************************************/
function addmemberstoapprovedrated(sids){
	$.ajax({
        url: 'addApprovedRateMember.html',
        type: 'POST',
        data: {ids:sids},
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "ADDED")
						{
							//show success message and reload the page.							
							//$("#pageContentBody").load('travel_rates.jsp');
							
							$(".details_success_message").html("SUCCESS: Member successfully added to higher rate.").css("display","block").delay(5000).fadeOut();
							
						}else{
							//show error
							$(".details_error_message").html("ERROR: Member cannot be removed at this time. Please try again later or contact support.").css("display","block").delay(5000).fadeOut();
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  //show error
					 $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
					},
				dataType: "text",
				async: false
	
        
    });	
	
}
function removenewmembers(){
	var ids="";
	$.each($("#assigned option:selected"), function(){
		//alert($(this).val() + "-" + $(this).text());
		// add them to the other listbox
		var sadd="<option value='" + $(this).val() + "'>" + $(this).text() + "</option>";
		var sremove="#assigned option[value=" + $(this).val() + "]";
		if(ids == ""){
			ids=$(this).val()
		}else{
			ids=ids + "," + $(this).val()
		}
		
	updateVal1 = parseInt($("#rrCount").text()) +1;
    updateVal2 = parseInt($("#aprCount").text()) -1;
    
    thename =  $(this).text().indexOf(" [");
		thename = $(this).text().slice(0, thename);
    
   $("#aprCount").text(updateVal2);
   $("#rrCount").text(updateVal1);		


		
		var indexes =$("#claims-table").DataTable().rows().indexes().filter( function ( value, index ) {
        return thename === $("#claims-table").DataTable().row(value).data()[0];
      } );
  
    $("#claims-table").DataTable().rows(indexes).remove().draw();
		
		
		
		
		$('#available')
        .append(sadd);
        //now we remove the selected ones
		$(sremove).remove();
    });
	//send ajax request with the ids to add
	removememberstoapprovedrated(ids);
	
	//now resort the listbox
	  var select = $('#assigned');
	  select.html(select.find('option').sort(function(x, y) {
	    // to change to descending order switch "<" for ">"
	    return $(x).text() > $(y).text() ? 1 : -1;
	}));
	select = $('#available');
	  select.html(select.find('option').sort(function(x, y) {
	    // to change to descending order switch "<" for ">"
	    return $(x).text() > $(y).text() ? 1 : -1;
	}));
}
/************************************************
Calls ajax post to add members to approved rates
*************************************************/
function removememberstoapprovedrated(sids){
	$.ajax({
        url: 'removeApprovedRateMember.html',
        type: 'POST',
        data: {ids:sids},
        success: function(xml) {
        		$(xml).find('TRAVELCLAIM').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "REMOVED")
						{
							//show success message
							$(".details_success_message").html("SUCCESS: Member successfully removed from higher rate.").css("display","block").delay(5000).fadeOut();
						 	
						}else{
							//show error
							$(".details_error_message").html("ERROR: Member cannot be removed at this time. Please try again later or contact support.").css("display","block").delay(5000).fadeOut();
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  //show error
					$(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
					},
				dataType: "text",
				async: false
	
        
    });	
	
}
function removememberfromtable(id,but){
	//but.closest("tr").remove();
	
	$("#claims-table").DataTable().closest("tr").remove();
	
	removememberstoapprovedrated(id);
	//now we find the entry in the assigned
	var sremove="#assigned option[value=" + id + "]";
	var sadd="<option value='" + id + "'>" + $(sremove).text() + "</option>";
	$('#available')
    .append(sadd);
	$(sremove).remove();
	//now resort the listbox
	  var select = $('#assigned');
	  select.html(select.find('option').sort(function(x, y) {
	    // to change to descending order switch "<" for ">"
	    return $(x).text() > $(y).text() ? 1 : -1;
	}));
	select = $('#available');
	  select.html(select.find('option').sort(function(x, y) {
	    // to change to descending order switch "<" for ">"
	    return $(x).text() > $(y).text() ? 1 : -1;
	}));
	
}
/*************************************************
Calls ajax post for delete travel claim item
*************************************************/
function deleteCurrentAttachment(fname,itemid,claimid){
		var optext = "CONFIRM";
		loadingData();
		//get values to reset datepickter after ajax
		var cm = $("#cm").val();
		var cy = $("#cy").val();
		var ldm = $("#ldm").val();
		$.ajax({
	        url: 'deleteTravelClaimItemFileAjax.html',
	        type: 'POST',
	        data: {id: itemid,op: optext,clid:claimid,filename:fname},
	        success: function(xml) {
	        		$(xml).find('TRAVELCLAIM').each(function(){
							//now add the items if any
							if($(this).find("STATUS").text() == "SUCCESS")
							{
								$(".details_success_message").html("<b>SUCCESS:</b> Travel Claim Item File Has Been Deleted").css("display","block").delay(5000).fadeOut();			    			
								
															
								var surl="viewTravelClaimDetails.html?id=" + claimid;
				            	$("#pageContentBody").load(surl);
								
							}else{
								$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();							
							}

						});     					
					},
					  error: function(xhr, textStatus, error){
						  $(".details_error_message").html(error).css("display","block").delay(5000).fadeOut();
					  },
					dataType: "text",
					async: false
		
	        
	    });
		refreshJquery(cm,cy,ldm);	
}

function addattach(){	
	$(".addFileTableBlock").css("display","block");	
	var newrow ="<tr id='filerow1'><td width='40%'><div class='custom-file'><input class='form-control-file form-control-sm' type='file' id='filerow' name='filerow'></div></td>";
	newrow = newrow +  "<td width='40%'><input maxlength='30' style='width:100%;' class='form-control-sm' type='text' id='filetext' name='filetext' placeholder='Enter a title for this receipt.'></td>";
	newrow = newrow + "<td width='20%'><button type='button' class='btn btn-sm btn-danger' onclick='removefile(this);'>REMOVE</button> <button type='button' class='btn btn-sm btn-success' onclick='addattach();'>Add Another</button></td></tr>";
	
	$("#addtable tbody").append(newrow);	
}

function removefile(but){
	$(but).closest ('tr').remove ();	
	 if ($('#addtable > tbody > tr').length == 0){
     $('#addtable > thead > tr').css('display','none');
 }	
}

function deletefile(lin,fid){
	$(lin).closest ('tr').remove ();
	 $(".details_success_message").html("File staged to be removed from this claim item. File will be removed once you Save this claim item.").css("display","block").delay(5000).fadeOut();	
	if($("#hidfiledelete").val() == ""){
		$("#hidfiledelete").val(fid);
	}else{
		var test =$("#hidfiledelete").val() + "," + fid;
		$("#hidfiledelete").val(test);
	}
}





function refreshApprovedDataTable() {
$("#pageContentBody").load('travel_rates.jsp');
}
	
