function validatePPGP(form)
{ 
  var check = true;
  
  if(form.Goal && form.Goal.value=="")
  {
	  $("#spanerror").text( "The field 'Goal' is required, you  must supply a value to continue." );
	  $("#diverror").show();
	  check = false;
  }
  else if(form.Goal && form.Goal.value.length > 500)
  {
	  $("#spanerror").text( "The field 'Goal' must not exceed 500 characters." );
	  $("#diverror").show();check = false;
  }
  else if(form.Task.value=="")
  {
	  $("#spanerror").text( "The field 'Strategies' is required, you  must supply a value to continue." );
	  $("#diverror").show();
	  check = false;
  }
  else if(form.Task.value.length > 500)
  {
	  	$("#spanerror").text( "The field 'Strategies' must not exceed 500 characters." );
	  	$("#diverror").show();
	  	check = false;
  }
  else if(form.SchoolSupport.value=="")
  {
	  $("#spanerror").text( "The field 'School Supports' is required, you  must supply a value to continue." );
	  $("#diverror").show();
	  check = false;
  }
  else if(form.SchoolSupport.value.length > 500)
  {
	  $("#spanerror").text( "The field 'School Supports' must not exceed 500 characters." );
	  $("#diverror").show();
	  check = false;
  }
  else if(form.DistrictSupport.value=="")
  {
	  $("#spanerror").text( "The field 'District Supports' is required, you  must supply a value to continue." );
	  $("#diverror").show();
	  check = false;
  }
  else if(form.DistrictSupport.value.length > 500)
  {
	  $("#spanerror").text( "The field 'District Supports' must not exceed 500 characters." );
	  $("#diverror").show();
	  check = false;
  }
  else if(form.CompletionDate.value=="")
  {
	  $("#spanerror").text( "The field 'Timeline' is required, you  must supply a value to continue." );
	  $("#diverror").show();
	  check = false;
  }
  else if((form.SelfEvaluation != null)&&(form.SelfEvaluation.value==""))
  {
	  $("#spanerror").text( "The field 'Self Reflection' is required, you  must supply a value to continue." );
	  $("#diverror").show();
	  check = false;
  }
  else if((form.SelfEvaluation != null)&&(form.SelfEvaluation.value.length > 4000))
  {
	  $("#spanerror").text( "The field 'Self Reflection' must not exceed 4000 characters." );
	  $("#diverror").show();
	  check = false;
  }
  else if(form.TechSupport.value=="")
  {
	  $("#spanerror").text( "The field 'How may techology support the successfully attainment of your goal?' is a required, you  must supply a value to continue." );
	  $("#diverror").show();
	  check = false;
  }
  else if(form.TechSupport.value.length > 500)
  {
	  $("#spanerror").text( "The field 'How may techology support the successfully attainment of your goal?' must not exceed 500 characters." );
	  $("#diverror").show();
	  check = false;
  }
  else if(form.TechSchoolSupport.value=="")
  {
	  $("#spanerror").text( "The field 'Technology School Support' is required, you  must supply a value to continue." );
	  $("#diverror").show();
	  check = false;
  }
  else if(form.TechSchoolSupport.value.length > 500)
  {
	  $("#spanerror").text( "The field 'Technology School Support' must not exceed 500 characters." );
	  $("#diverror").show();
	  check = false;
  }
  else if(form.TechDistrictSupport.value=="")
  {
	  $("#spanerror").text( "The field 'Technology District Support' is required, you  must supply a value to continue." );
	  $("#diverror").show();
	  check = false;
  }
  else if(form.TechDistrictSupport.value.length > 500)
  {
	  $("#spanerror").text( "The field 'Technology District Support' must not exceed 500 characters." );
	  $("#diverror").show();
	  check = false;
  }
  if($('#cat_id').is(':visible')){
	  if($('#cat_id').val() == "0"){
		  $("#spanerror").text( "Please select Category" );
		  $("#diverror").show();
		  check = false;
	  }
  }
  if($('#grade_id').is(':visible')){
	  if($('#grade_id').val() == "0"){
		  $("#spanerror").text( "Please select Grade" );
		  $("#diverror").show();
		  check = false;
	  }
  }
  if($('#subject_id').is(':visible')){
	  if($('#subject_id').val() == "0"){
		  $("#spanerror").text( "Please select Subject" );
		  $("#diverror").show();
		  check = false;
	  }
  }
if($('#topic_id').is(':visible')){
	  if($('#topic_id').val() == "0"){
		  $("#spanerror").text( "Please select Topic Area" );
		  $("#diverror").show();
		  check = false;
	  }
  }
if($('#stopic_id').is(':visible')){
	  if($('#stopic_id').val() == "0"){
		  $("#spanerror").text( "Please select Specific Topic Area" );
		  $("#diverror").show();
		  check = false;
	  }
  }
if($('#selectdomain').is(':visible')){
	  if($('#selectdomain').val() == "0"){
		  $("#spanerror").text( "Please select Domain" );
		  $("#diverror").show();
		  check = false;
	  }
}  
if($('#selectstrength').is(':visible')){
	  if($('#selectstrength').val() == "0"){
		  $("#spanerror").text( "Please select Strength" );
		  $("#diverror").show();
		  check = false;
	  }
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