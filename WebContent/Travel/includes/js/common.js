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
    alert("Invalid currency amount."); 
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
    alert("Invalid value."); 
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
    alert("Invalid time format (HH[:MM] AM|PM)"); 
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
    alert('Date is a required field.');
    check = false;
  }
  else if(!validateNotEmpty(frm.item_desc.value))
  {
    alert('Description is a required field.');
    check = false;
  }
  else if(!validateInteger(frm.item_kms))
  {
    check = false;
  }
  else if(!validateDollar(frm.item_meals))
  {
    check = false;
  }
  else if(!validateDollar(frm.item_lodging))
  {
    check = false;
  }
  else if(!validateDollar(frm.item_other))
  {
    check = false;
  }
  else if(validateNotEmpty(frm.item_departure_time) && !validateTime(frm.item_departure_time))
  {
  	check = false;
  }
  else if(validateNotEmpty(frm.item_return_time) && !validateTime(frm.item_return_time))
  {
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