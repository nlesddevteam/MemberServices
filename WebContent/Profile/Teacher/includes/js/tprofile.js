

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