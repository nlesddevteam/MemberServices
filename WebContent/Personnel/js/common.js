function openWindow(id,url,w,h, scroll) 
{
  var winl = (screen.width-w)/2;
  var wint = (screen.height - h - 25 )/2;

  window.open(url,id,"titlebar=0,toolbar=0,location=no,top="+wint+",left="+winl+",directories=0,status=1,menbar=0,scrollbars="+scroll+",resizable=0,width="+w+",height="+h);
}

function toggleRow(rid, state)
{
  var row = document.getElementById(rid);
  row.style.display=state;
}

function validateNotEmpty(fld) 
{
    return /\S+/.test(fld.value);
}

function validateDateFormat(fld)
{
  return /^\d{2}\/\d{4}$/.test(fld.value);
}

function validateSelectionMade(fld)
{
  return ((fld.selectedIndex != -1) && (fld.options[fld.selectedIndex].value != -1));
}

function AddReplExpForm_Validator(theForm)
{

  if (!validateDateFormat(theForm.from_date))
  {
    alert("Invalid value for \"From Date\" field, must match mm/yyyy format.");
    theForm.from_date.focus();
    return (false);
  }
  
  if (!validateDateFormat(theForm.to_date))
  {
    alert("Invalid value for \"To Date\" field, must match mm/yyyy format.");
    theForm.to_date.focus();
    return (false);
  }
  
  if (!validateSelectionMade(theForm.school_id))
  {
    alert("Please select a value for the \"School\" field.");
    theForm.school_id.focus();
    return (false);
  }

  if (!validateNotEmpty(theForm.grds_subs))
  {
    alert("Please enter a value for the \"Grades and/or Subjects Taught\" field.");
    theForm.grds_subs.focus();
    return (false);
  }

  return (true);
}

function onPositionTypeSelected(ele)
{
	if(ele.value == '7') //Other
		toggleRow('recommended_position_other_row', 'inline');
	else
		toggleRow('recommended_position_other_row', 'none');
}

function onSpecialConditionChecked(ele){
	if(ele.checked)
		toggleRow('special_conditions_row', 'inline');
	else
		toggleRow('special_conditions_row', 'none');
}

function onError(){
	//load candidate info
	var ele = document.getElementById('candidate_name');
	onCandidateSelected(ele.value);
	
	ele = document.getElementById('position');
	onPositionTypeSelected(ele);
	
	refreshGSU();
	
	ele = document.getElementById('Special_Conditions');
	onSpecialConditionChecked(ele);
}

function validateEmail(fld) 
{
    return /^[\w.\-]+@[\w\-]+\.[a-zA-Z0-9]+$/.test(fld.value);
}
