
function validate() {
	 
    
    if( $("#destination").val() == "" ) {
       $(".formERR").css("display","block").html("ERROR: Please fill out the destination for this trip.").delay(5000).fadeOut();
       $("#destination").focus();
       return false;
    	}        
    if( $("#departure_date").val() == "" ) {
        $(".formERR").css("display","block").html("ERROR: Please indicate the departure date for this trip.").delay(5000).fadeOut();
        $("#departure_date").focus();
        return false;
     	}   
    if( $("#return_date").val() == "" ) {
        $(".formERR").css("display","block").html("ERROR: Please indicate the return date for this trip.").delay(5000).fadeOut();
        $("#return_date").focus();
        return false;
     	}  
    
    textbox_data = CKEDITOR.instances.rational.getData();
    if( textbox_data === '' ) {
   	 $(".formERR").css("display","block").html("ERROR: Please fill out the rational/reason for this trip.").delay(5000).fadeOut();
        $("#rational").focus();
        return false; 
    }        
    
    if( $("#days_missed").val() == "" ) {
        $(".formERR").css("display","block").html("ERROR: Please indicate the number of school days that will be missed.").delay(5000).fadeOut();
        $("#days_missed").focus();
        return false;
      	}   
    if( $("#grades").val() == "" ) {
        $(".formERR").css("display","block").html("ERROR: Please indicate the grade(s) that will be going on this trip.").delay(5000).fadeOut();
        $("#grades").focus();
        return false;
      	}   
    if( $("#num_students").val() == "" ) {
        $(".formERR").css("display","block").html("ERROR: Please indicate the number of students going on this trip.").delay(5000).fadeOut();
        $("#num_students").focus();
        return false;
      	}   
    if( $("#total_chaperons").val() == "" ) {
        $(".formERR").css("display","block").html("ERROR: Please indicate the total number of chaperones going on this trip.").delay(5000).fadeOut();
        $("#total_chaperons").focus();
        return false;
      	}   
    if( $("#total_teacher_chaperons").val() == "" ) {
        $(".formERR").css("display","block").html("ERROR: Please indicate the total number of teacher chaperones going on this trip.").delay(5000).fadeOut();
        $("#total_teacher_chaperons").focus();
        return false;
      	}   
    if( $("#teacher_chaperons").val() == "" ) {
        $(".formERR").css("display","block").html("ERROR: Please indicate the name(s) of the teacher chaperones.").delay(5000).fadeOut();
        $("#teacher_chaperons").focus();
        return false;
      	}   
    if( $("#total_nonteacher_chaperons").val() == "" ) {
        $(".formERR").css("display","block").html("ERROR: Please indicate the number of non teacher chaperones that will be going on this trip.").delay(5000).fadeOut();
        $("#total_nonteacher_chaperons").focus();
        return false;
      	}          
    
    if( ($("#other_chaperons").val() == "") && ($("#total_nonteacher_chaperons").val() != "0")) {
        $(".formERR").css("display","block").html("ERROR: Please indicate the name(s) of the non-teacher chaperones.").delay(5000).fadeOut();
        $("#teacher_chaperons").focus();
        return false;
      	}        

    
    if (!$("input[name='chaperons_approved']").is(':checked')) {
        $(".formERR").css("display","block").html("ERROR: Please indicate if the principal has approved all the chaperones going on this trip.").delay(5000).fadeOut();
        $("#chaperons_approved").focus();
        return false;
      	}
    if (!$("input[name='billeting_involved']").is(':checked')) {        
        $(".formERR").css("display","block").html("ERROR: Please indicate if there will be billetting for students and/or chaperones on this trip.").delay(5000).fadeOut();
        $("#billeting_involved").focus();
        return false;
      	}
    if (!$("input[name='school_fundraising']").is(':checked')) {  
        $(".formERR").css("display","block").html("ERROR: Please indicate if there was any school fundraising associated with this trip.").delay(5000).fadeOut();
        $("#school_fundraising").focus();
        return false;
      	}
    
    
    if( $("#emergency_contact").val() == "" ) {
        $(".formERR").css("display","block").html("ERROR: Please indicate an emergency contact number for this trip.").delay(5000).fadeOut();
        $("#emergency_contact").focus();
        return false;
      	}   
    if( $("#itinerary_document").val() == "" ) {
        $(".formERR").css("display","block").html("ERROR: Please upload a valid Itinerary document in PDF format for this trip.").delay(5000).fadeOut();
        $("#itinerary_document").focus();
        return false;
      	}  
    
    return( true );
 }


function validateAdd(sel_obj)
{
  var check = true;

  if(sel_obj.selectedIndex == 0)
  {
	  $("#schoolMsgERR").css("display","block").html("ERROR: Please make a valid school/office selection").delay(5000).fadeOut();
	  $("#schoolSelect").focus();
	  check = false;
  } 
  
  return check;
}

function validateName(frm)
{
  var check = true;

  if(frm.firstname.value == "")
  {
	$("#nameMsgERR").css("display","block").html("ERROR: Please enter your first name.").delay(5000).fadeOut();
	$("#firstName").focus();
    check = false;
  }
  else if(frm.lastname.value == "")
  {
	  $("#nameMsgERR").css("display","block").html("ERROR: Please enter your last name.").delay(5000).fadeOut(); 	  
	  $("#lastName").focus();
    check = false;
  }
  return check;
}