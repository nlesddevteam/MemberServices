function validatePPGP(form)
{ 
  var check = true;

  if(form.Goal && form.Goal.value=="")
  {
    alert( "The field 'Goal' is required, you  must supply a value to continue." );
    check = false;
  }
  else if(form.Goal && form.Goal.value.length > 500)
  {
    alert( "The field 'Goal' must not exceed 500 characters." );
    check = false;
  }
  else if(form.Task.value=="")
  {
    alert( "The field 'Strategies' is required, you  must supply a value to continue." );
    check = false;
  }
  else if(form.Task.value.length > 500)
  {
    alert( "The field 'Strategies' must not exceed 500 characters." );
    check = false;
  }
  else if(form.SchoolSupport.value=="")
  {
    alert( "The field 'School Supports' is required, you  must supply a value to continue." );
    check = false;
  }
  else if(form.SchoolSupport.value.length > 500)
  {
    alert( "The field 'School Supports' must not exceed 500 characters." );
    check = false;
  }
  else if(form.DistrictSupport.value=="")
  {
    alert( "The field 'District Supports' is required, you  must supply a value to continue." );
    check = false;
  }
  else if(form.DistrictSupport.value.length > 500)
  {
    alert( "The field 'District Supports' must not exceed 500 characters." );
    check = false;
  }
  else if(form.CompletionDate.value=="")
  {
    alert( "The field 'Timeline' is required, you  must supply a value to continue." );
    check = false;
  }
  else if((form.SelfEvaluation != null)&&(form.SelfEvaluation.value==""))
  {
    alert( "The field 'Self Reflection' is required, you  must supply a value to continue." );
    check = false;
  }
  else if((form.SelfEvaluation != null)&&(form.SelfEvaluation.value.length > 4000))
  {
    alert( "The field 'Self Reflection' must not exceed 4000 characters." );
    check = false;
  }
  else if(form.TechSupport.value=="")
  {
    alert( "The field 'How may techology support the successfully attainment of your goal?' is a required, you  must supply a value to continue." );
    check = false;
  }
  else if(form.TechSupport.value.length > 500)
  {
    alert( "The field 'How may techology support the successfully attainment of your goal?' must not exceed 500 characters." );
    check = false;
  }
  else if(form.TechSchoolSupport.value=="")
  {
    alert( "The field 'Technology School Support' is required, you  must supply a value to continue." );
    check = false;
  }
  else if(form.TechSchoolSupport.value.length > 500)
  {
    alert( "The field 'Technology School Support' must not exceed 500 characters." );
    check = false;
  }
  else if(form.TechDistrictSupport.value=="")
  {
    alert( "The field 'Technology District Support' is required, you  must supply a value to continue." );
    check = false;
  }
  else if(form.TechDistrictSupport.value.length > 500)
  {
    alert( "The field 'Technology District Support' must not exceed 500 characters." );
    check = false;
  }

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