jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});


function goBack() {
    window.history.back()
}

/**
 * 
 */
/************************************************
Display delete goal jquery dialog
*************************************************/
function showdeletegoal(goalid,plpid,sample){
	$("#wasdeleted").val("");
	$("#spantitle").text("DELETE GOAL")
	$("#spangoal").text("Are you sure you want to delete this Goal?");
	$("#myModal").modal();
	$('#myModal').off("hidden.bs.modal");
	$('#myModal').on('hidden.bs.modal', function () {
		if($("#wasdeleted").val() == "DELETED"){
			// do somtething here
			sample.closest('table').remove();
		}
	    
	});
	$('#butdelete').off('click');
	$('#butdelete').on('click', function(event) {
		  event.preventDefault(); // To prevent following the link (optional)
		  deletegoalajax(goalid);
		  $('#myModal').modal('hide');
	});
	
	
}

/************************************************
Display delete task jquery dialog
*************************************************/
function showdeletetask(goalid,plpid,sample){
	$("#wasdeleted").val("");
	$("#spantitle").text("DELETE TASK")
	$("#spangoal").text("Are you sure you want to delete this Task/Stragey?");
	$("#myModal").modal();
	
	$('#myModal').off("hidden.bs.modal");
	$('#myModal').on('hidden.bs.modal', function () {
		if($("#wasdeleted").val() == "DELETED"){
			// do somtething here
			sample.closest('table').remove();
			//$(".task"+taskNum).hide();
		}
	    
	});
	$('#butdelete').off('click');
	$('#butdelete').on('click', function(event) {
		  event.preventDefault(); // To prevent following the link (optional)
		  deletetaskajax(goalid);
		  $('#myModal').modal('hide');
		  
	});
	
}
/************************************************
 * calls ajax function to delete goal and then 
 * update screen after delete
*************************************************/
function deletegoalajax(goalid){
	$.ajax({
        url: 'deleteGrowthPlanGoalAjax.html',
        type: 'POST',
        data: {gid:goalid},
        success: function(xml) {
        		$(xml).find('GOAL').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "SUCCESS")
						{
							
							$('#wasdeleted').val("DELETED");
							$("#divsuccess").text("SUCCESS: Goal has been successfully deleted").show().delay(5000).fadeOut();							
							updatebootstrapmessage();
						}else{
							$("#diverror").text("ERROR: "+$(this).find("MESSAGE").text()).show().delay(5000).fadeOut();
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  	$("#diverror").text(error).show().delay(5000).fadeOut();						
				  },
				dataType: "text",
				async: false
	
        
    });	
	
}
/************************************************
 * calls ajax function to delete task and then 
 * update screen after delete
*************************************************/
function deletetaskajax(goalid){
	$.ajax({
        url: 'deleteGrowthPlanTaskAjax.html',
        type: 'POST',
        data: {gid:goalid},
        success: function(xml) {
        		$(xml).find('GOAL').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "SUCCESS")
						{							
							$('#wasdeleted').val("DELETED");
							$("#divsuccess").text("SUCCESS: Task has been successfully deleted.").show().delay(5000).fadeOut();
							
						}else{
							$("#diverror").text("ERROR: "+$(this).find("MESSAGE").text()).show().delay(5000).fadeOut();					
						
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  	$("#diverror").text("ERROR: "+ error).show().delay(5000).fadeOut();						
				  },
				dataType: "text",
				async: false
	
        
    });	
	
}
/************************************************
 * opens jquery dialog box to update goal
*************************************************/
function showupdategoal(goalid,description,plpid){
	$("#txtgoal").val(description);
	$("#myModalUpdate").modal();
	$("#updateid").val(goalid);
	$("#plpid").val(plpid);
}
/************************************************
 * calls ajax function to update goal description
*************************************************/
function updategoaldes(){
	if($("#txtgoal").val() == ""){
		$("#diverror").show();
		return false;
	}
	updategoalajax();
	
	$('#myModalUpdate').modal('hide');
	var surl="viewGrowthPlanSummary.html?ppgpid=" + $("#plpid").val() + "&pid=" + $("#pid").val();
	window.location.href = surl;
	
}
/************************************************
 * calls ajax function to update goal description
 * and refreshes screen
*************************************************/
function updategoalajax(){
	var goalid=$("#updateid").val();
	var goaldes = $("#txtgoal").val();

	$.ajax({
        url: 'updateGrowthPlanGoalAjax.html',
        type: 'POST',
        data: {gid:goalid,goal:goaldes},
        success: function(xml) {
        		$(xml).find('GOAL').each(function(){
						//now add the items if any
						if($(this).find("MESSAGE").text() == "SUCCESS")
						{
							$("#divsuccess").text("SUCCESS: Goal was successfully updated!").show().delay(5000).fadeOut();
						
						}else{
							$("#diverror").text("ERROR: "+$(this).find("MESSAGE").text()).show().delay(5000).fadeOut();							
						
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  	$("#diverror").text("ERROR: "+error).show().delay(5000).fadeOut();					
				  },
				dataType: "text",
				async: false
	
        
    });	
	
}
/************************************************
 * function to update catagory dropdown and show other
 * dropdowns if needed
*************************************************/
function cat_id_change(){
	var catval= $("#cat_id").val();
	if(catval == "1"){
		$("#divgrade").show();
		$("#divsubject").hide();
		$("#divtopic").hide();
		$("#divstopic").hide();
	}else if(catval == "2" || catval == "3" || catval == "4"){
		$("#divgrade").hide();
		$("#divsubject").hide();
		$("#divtopic").show();
		$("#divstopic").hide();
		
		//now we load the topics
		var cid=$("#cat_id").val();
		$("select[id$=topic_id] > option").remove();
		getselectvalues("TOPICOTHER",cid,0,0,0,0);
	}
}
/************************************************
 * function to update grade dropdown and show other
 * dropdowns if needed
*************************************************/
function grade_id_change(){
	//we to populate the subjects first
	var gid=$("#grade_id").val();
	if(!(gid == "0")){
		//empty dropdown
		//get values for dropdown
		//$('#subject_id').empty();
		$("select[id$=subject_id] > option").remove();
		getselectvalues("SUBJECT",0,gid,0,0,0);
		$("#divsubject").show();
		$("#divtopic").hide();
		$("#divstopic").hide();
	}
}
/************************************************
 * function to update subject dropdown and show other
 * dropdowns if needed
*************************************************/
function subject_id_change(){
	var cid=$("#cat_id").val();
	var gid=$("#grade_id").val();
	var sid=$("#subject_id").val();
	//empty dropdown
	//get values for dropdown
	//$('#topic_id').empty();
	$("select[id$=topic_id] > option").remove();
	getselectvalues("TOPIC",cid,gid,sid,0,0);
	$("#divtopic").show();
	$("#divstopic").hide();
}
/************************************************
 * function to update topic dropdown and show other
 * dropdowns if needed
*************************************************/
function topic_id_change(){
	var cid=$("#cat_id").val();
	var gid=$("#grade_id").val();
	var sid=$("#subject_id").val();
	var tid=$("#topic_id").val();
	//empty dropdown
	//get values for dropdown
	//$('#topic_id').empty();
	$("select[id$=stopic_id] > option").remove();
	//we need to check and see if cat is 1, then we pass all variable
	//if 2,3,4 we pass just cat
	if(cid == "1"){
		getselectvalues("STOPIC",cid,gid,sid,tid,0);
		$("#divstopic").show();
	}else{
		getselectvalues("STOPICOTHER",cid,0,0,tid,0);
		$("#divstopic").show();
	}
	
}
/************************************************
 * function retrieves dropdown values and then calls
 * ajax function to get values for related dropdowns
*************************************************/
function getselectvalues(selecttype,catid,gradeid,subjectid,topicid,domainid){
	var x=0;
	$.ajax({
        url: 'getGrowthPlanSelectValuesAjax.html',
        type: 'POST',
        data: {stype:selecttype,cid:catid,gid:gradeid,sid:subjectid,tid:topicid,did:domainid},
        success: function(xml) {
        		$(xml).find('SOPTION').each(function(){
						//now add the items if any
        				if($(this).find("MESSAGE").text() == "VALID")
						{
							
        					if(selecttype == 'SUBJECT'){
								//clear the values
								var newOption = $('<option value="'+ $(this).find("SID").text() +'">'+ $(this).find("STITLE").text() +'</option>');
								 $('#subject_id').append(newOption);
							}else if (selecttype == 'TOPIC' || selecttype == 'TOPICOTHER'){
								var newOption = $('<option value="'+ $(this).find("SID").text() +'">'+ $(this).find("STITLE").text() +'</option>');
								 $('#topic_id').append(newOption);
							}else if (selecttype == 'STOPIC' || selecttype == 'STOPICOTHER'){
								var newOption = $('<option value="'+ $(this).find("SID").text() +'">'+ $(this).find("STITLE").text() +'</option>');
								 $('#stopic_id').append(newOption);
							}else if(selecttype == "STRENGTH"){
								var newOption = $('<option value="'+ $(this).find("SID").text() +'">'+ $(this).find("STITLE").text() +'</option>');
								 $('#selectstrength').append(newOption);
							}
						}else{
							$("#spanerror").text("Error loading values");
							$("#diverror").show();
						
						}

					});     					
				},
				  error: function(xhr, textStatus, error){
					  	$("#spanerror").text(error);
						$("#diverror").show();
				  },
				dataType: "text",
				async: false
	
        
    });	
	
}
/************************************************
 * function to update strength dropdown and show other
 * dropdowns if needed
*************************************************/
function selectdomain_change(){
	//we to populate the subjects first
	var gid=$("#selectdomain").val();
	if(!(gid == "0")){
		//empty dropdown
		//get values for dropdown
		$("select[id$=selectstrength] > option").remove();
		getselectvalues("STRENGTH",0,0,0,0,gid);
		//$("#divsubject").show();
		//$("#divtopic").hide();
		$("#divstrength").show();
	}
}
/************************************************
 * used to determine if year is less than 2018 and
 * shows old dropdowns.  if greater then new dropdowns
*************************************************/
function changeyears(){
	var selectedyear = $("#year").val();
	var arr = selectedyear.split("-");
	var test = parseInt(arr[1],10);
	if(test > 2017){
		$("#spantitle").text("Domains and Strengths");
		$("#divgrade").hide();
		$("#divsubject").hide();
		$("#divtopic").hide();
		$("#divstopic").hide();
		$("#divstopic").hide();
		$("#divstrength").hide();
		$("#selectdomain").val('0');
		$("#divnew").show();
		$("#divoriginal").hide();
		
	}else{
		$("#divgrade").hide();
		$("#divsubject").hide();
		$("#divtopic").hide();
		$("#divstopic").hide();
		$("#divstopic").hide();
		$("#divstrength").hide();
		$("#divnew").hide();
		$("#divoriginal").show();
		$("#spantitle").text("Categories");
		$("#cat_id").val('0');
	}
}
/************************************************
 * function to check required fields for Search 
*************************************************/
function checkrequired(){
	var regionval = $("#region").val();
	var keywords = $("#keywords").val();
	
	if(regionval== ""){
		$("#spanerror").text("ERROR: Please select Region");
		$("#diverror").show().delay(5000).fadeOut();
		return false;
	}
	if(keywords == ""){
		$("#spanerror").text("ERROR: Please enter Keywords");
		$("#diverror").show().delay(5000).fadeOut();
		return false;
	}
	return true;
}
/************************************************
 * function called after goal is delete to update
 * which bootstrap message is shown
*************************************************/
function updatebootstrapmessage(){
	var test = parseInt($("#totalgoals").val(),10);
	var test2 = --test;
	$("#totalgoals").val(test2);
	if((test2) < 1 ){
		$("#divdanger").show();
		$("#divwarning").hide();
		$("#divcomplete").hide();
	}else if((test2) == 1){
		$("#divdanger").hide();
		$("#divwarning").show();
		$("#divcomplete").hide();
	}else{
		$("#divdanger").hide();
		$("#divwarning").hide();
		$("#divcomplete").show();
	}
	
}

/************************************************
 * function Validate
*************************************************/

function validatePPGP(form)
{ 
  var check = true;
  //Have to get the instance of CKEditor in order to read the actual text area values as reading directly from textarea will not work when replaced.
  
  if ($("#Goal").val()) {	  
  } else {
  if (CKEDITOR.instances.Goal.getData() =="") {
	  $("#msgerr").html( "<b>ERROR:</b> The field 'Goal' is required, you must supply a value to continue." ).css("display","block").delay(5000).fadeOut();	 
	  $("#Goal").focus();
	  check = false;	  
  }}
  
 if (CKEDITOR.instances.txt_task.getData() ==""){
	  $("#msgerr").html( "<b>ERROR:</b> The field 'Strategies' is required, you must supply a value to continue." ).css("display","block").delay(5000).fadeOut();	
	  $("#txt_task").focus();
	  check = false;	  
  }

  else if (CKEDITOR.instances.txt_school_support.getData() ==""){	  
	  $("#msgerr").html( "<b>ERROR:</b> The field 'School Supports' is required, you  must supply a value to continue." ).css("display","block").delay(5000).fadeOut();
	  $("#txt_school_support").focus();
	  check = false;	  
  }

  else if (CKEDITOR.instances.txt_district_support.getData() ==""){
	  $("#msgerr").html( "<b>ERROR:</b> The field 'District Supports' is required, you  must supply a value to continue." ).css("display","block").delay(5000).fadeOut();
	  $("#txt_district_support").focus();
	  check = false;	  
  }
  
  else if (!$("#CompletionDate").val()){
	  $("#msgerr").html( "<b>ERROR:</b> The field 'Timeline' is required, you  must supply a value to continue." ).css("display","block").delay(5000).fadeOut();
	  $("#CompletionDate").focus();
	  check = false;
  }  
  
  else if (CKEDITOR.instances.txt_technology.getData() ==""){ 
	  $("#msgerr").html( "<b>ERROR:</b> The field 'How may techology support the successfully attainment of your goal?' is a required, you  must supply a value to continue." ).css("display","block").delay(5000).fadeOut();
	  $("#txt_technology").focus();
	  check = false;
  }
  
  else if (CKEDITOR.instances.txt_school_support_tech.getData() ==""){ 
	  $("#msgerr").html( "<b>ERROR:</b> The field 'Technology School Support' is required, you  must supply a value to continue." ).css("display","block").delay(5000).fadeOut();
	  $("#txt_school_support_tech").focus();
	  check = false;
  }
 
  else if (CKEDITOR.instances.txt_district_support_tech.getData() ==""){ 
	  $("#msgerr").html( "<b>ERROR:</b> The field 'Technology District Support' is required, you  must supply a value to continue." ).css("display","block").delay(5000).fadeOut();
	  $("#txt_district_support_tech").focus();
	  check = false;
  }
 
  if($('#cat_id').is(':visible')){
	  if($('#cat_id').val() == "0"){
		  $("#msgerr").html( "<b>ERROR:</b> Please select a Category." ).css("display","block").delay(5000).fadeOut();		 
		  $("#cat_id").focus();		  
		  check = false;
	  }
  }
  if($('#grade_id').is(':visible')){
	  if($('#grade_id').val() == "0"){
		  $("#msgerr").html( "<b>ERROR:</b> Please select a Grade." ).css("display","block").delay(5000).fadeOut();
		  $("#grade_id").focus();
		  check = false;
	  }
  }
  if($('#subject_id').is(':visible')){
	  if($('#subject_id').val() == "0"){
		  $("#msgerr").html( "<b>ERROR:</b> Please select a Subject." ).css("display","block").delay(5000).fadeOut();		
		  $("#subject_id").focus();
		  check = false;
	  }
  }
if($('#topic_id').is(':visible')){
	  if($('#topic_id').val() == "0"){
		  $("#msgerr").html( "<b>ERROR:</b> Please select a Topic Area." ).css("display","block").delay(5000).fadeOut();
		  $("#topic_id").focus();
		  check = false;
	  }
  }
if($('#stopic_id').is(':visible')){
	  if($('#stopic_id').val() == "0"){
		  $("#msgerr").html( "<b>ERROR:</b> Please select a Specific Topic Area." ).css("display","block").delay(5000).fadeOut();
		  $("#stopic_id").focus();
		  check = false;
	  }
  }
if($('#selectdomain').is(':visible')){
	  if($('#selectdomain').val() == "0"){
		  $("#msgerr").html( "<b>ERROR:</b> Please select a Domain." ).css("display","block").delay(5000).fadeOut();
		  $("#selectdomain").focus();
		  check = false;
	  }
}  
if($('#selectstrength').is(':visible')){
	  if($('#selectstrength').val() == "0"){
		  $("#msgerr").html( "<b>ERROR:</b> Please select a Strength." ).css("display","block").delay(5000).fadeOut();
		  $("#selectstrength").focus();
		  check = false;
	  }
}  
$("#loadingSpinner").css("display","none");
  return check;
}

var dirty = false;

function submitCheck(form)
{
	alert(dirty);
  return !dirty;
	/*
	var check = true;

  if(form.Goal && form.Goal.value!="")
  {
    check = false;
  }
  else if(form.Task.value!="")
  {
    check = false;
  }
  else if(form.SchoolSupport.value!="")
  {
    check = false;
  }
  else if(form.DistrictSupport.value!="")
  {
    check = false;
  }
  else if(form.CompletionDate.value!="")
  {
    check = false;
  }
  else if((form.SelfEvaluation != null) && (form.SelfEvaluation.value!=""))
  {
    check = false;
  }
  else
  {
    check = true;
  }
  
  if(!check)
  {
    alert( 'Please submit.' );
  }
  
  return check;
  */
}
