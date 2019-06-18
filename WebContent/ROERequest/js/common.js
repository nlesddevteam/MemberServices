function process_message(id, str)
{
  var msg = document.getElementById(id);
  if(msg)
  {
    msg.innerHTML = "<table width='100%' cellspacing='0' cellpadding='0'><tr><td width='22' valign='middle'><img src='images/hour_glass_ani.gif'><br></td><td width='*' align='left' valign='middle' class='content' style='color:#FF0000;font-weight:bold;padding-left:3px;'>" + str + "</td></tr></table>";
  }
}

function validate_request(frm)
{
  if(!validateNotEmpty(frm.elements['sin'].value))
  {
    alert('SIN is required');
    frm.elements['sin'].focus();
    frm.elements['sin'].select();
    return false;
  }
  else if(!validateValue(frm.elements['sin'].value, /^((\d{9})|(\d{3} \d{3} \d{3})|(\d{3}-\d{3}-\d{3}))$/))
  {
    alert('Invalid SIN. Please check that you have entered it correctly.');
    frm.elements['sin'].focus();
    frm.elements['sin'].select();
    return false;
  }
  else if(!validateNotEmpty(frm.elements['cur_street_addr'].value))
  {
    alert('STREET ADDRESS is required');
    frm.elements['cur_street_addr'].focus();
    frm.elements['cur_street_addr'].select();
    return false;
  }
  else if(!validateNotEmpty(frm.elements['cur_community'].value))
  {
    alert('COMMUNITY is required');
    frm.elements['cur_community'].focus();
    frm.elements['cur_community'].select();
    return false;
  }
  else if(frm.elements['cur_province'].selectedIndex == -1)
  {
    alert('PROVINCE is required');
    return false;
  }
  else if(!validateNotEmpty(frm.elements['cur_postal_code'].value))
  {
    alert('POSTAL CODE is required');
    frm.elements['cur_postal_code'].focus();
    frm.elements['cur_postal_code'].select();
    return false;
  }
  else if(!validateValue(frm.elements['cur_postal_code'].value, /^(\D[0-9]\D\s[0-9]\D[0-9])$/))
  {
    alert('Invalid POSTAL CODE. Please check that you have entered it correctly.');
    frm.elements['cur_postal_code'].focus();
    frm.elements['cur_postal_code'].select();
    return false;
  }
  else if(!validateNotEmpty(frm.elements['home_phone'].value))
  {
    alert('HOME PHONE NUMBER is required');
    frm.elements['home_phone'].focus();
    frm.elements['home_phone'].select();
    return false;
  }
  else if(!validateValue(frm.elements['home_phone'].value, /^((\d{3}\s\d{3}\s\d{4})|(\d{3}-\d{3}-\d{4})|(\d{10}))$/))
  {
    alert('Invalid HOME PHONE NUMBER. Please use the following format xxx xxx xxxx.');
    frm.elements['home_phone'].focus();
    frm.elements['home_phone'].select();
    return false;
  }
  else if(validateNotEmpty(frm.elements['cell_phone'].value) && !validateValue(frm.elements['cell_phone'].value, /^((\d{3}\s\d{3}\s\d{4})|(\d{3}-\d{3}-\d{4})|(\d{10}))$/))
  {
    alert('Invalid CELL PHONE NUMBER. Please use the following format xxx xxx xxxx.');
    frm.elements['cell_phone'].focus();
    frm.elements['cell_phone'].select();
    return false;
  }
  else if(validateNotEmpty(frm.elements['fax'].value) && !validateValue(frm.elements['fax'].value, /^((\d{3}\s\d{3}\s\d{4})|(\d{3}-\d{3}-\d{4})|(\d{10}))$/))
  {
    alert('Invalid FAX NUMBER. Please use the following format xxx xxx xxxx.');
    frm.elements['fax'].focus();
    frm.elements['fax'].select();
    return false;
  }
  else if(frm.elements['gender'].selectedIndex == -1)
  {
    alert('GENDER is required');
    return false;
  }
  
  if(!validateNotEmpty(frm.elements['first_day_worked'].value))
  {
    alert('FIRST DAY WORKED (since last roe) is required');
    return false;
  }
  else if(!validateNotEmpty(frm.elements['last_day_worked'].value))
  {
    alert('LAST DAY WORKED is required');
    return false;
  }
  else
  {
    var arr = frm.elements['first_day_worked'].value.split('/');
    var sd = new Date(arr[2], arr[1], arr[0]);
      
    arr = frm.elements['last_day_worked'].value.split('/');
    var fd = new Date(arr[2], arr[1], arr[0]);
      
    if(fd < sd)
    {
      alert('LAST DAY WORKED should be AFTER FIRST DAY WORKED');
      return false;
    }
  }
  
  if(!validateNotEmpty(frm.elements['week1'].value))
  {
    alert('HRS WORKED DURING WEEK OF MAY 30-JUNE 3 is required.');
    return false;
  }
  else if(!validateValue(frm.elements['week1'].value, /^((\d+)|(\d+.{2})|(\d+.{1}))$/))
  {
    alert('Invalid HOURS. Please check that you have entered it correctly.');
    frm.elements['week1'].focus();
    frm.elements['week1'].select();
    return false;
  }
  else if((+(frm.elements['week1'].value) < 0)||(+(frm.elements['week1'].value) > 35))
  {
    alert('Invalid HOURS. Maximum workable hours is 35. Please check that you have entered it correctly.');
    frm.elements['week1'].focus();
    frm.elements['week1'].select();
    return false;
  }
  
  if(!validateNotEmpty(frm.elements['week2'].value))
  {
    alert('HRS WORKED DURING WEEK OF JUNE 6-JUNE 10 is required.');
    return false;
  }
  else if(!validateValue(frm.elements['week2'].value, /^((\d+)|(\d+.{2})|(\d+.{1}))$/))
  {
    alert('Invalid HOURS. Please check that you have entered it correctly.');
    frm.elements['week2'].focus();
    frm.elements['week2'].select();
    return false;
  }
  else if((+(frm.elements['week2'].value) < 0)||(+(frm.elements['week2'].value) > 35))
  {
    alert('Invalid HOURS. Maximum workable hours is 35. Please check that you have entered it correctly.');
    frm.elements['week2'].focus();
    frm.elements['week2'].select();
    return false;
  }
  
  if(!validateNotEmpty(frm.elements['week3'].value))
  {
    alert('HRS WORKED DURING WEEK OF JUNE 13-JUNE 17 is required.');
    return false;
  }
  else if(!validateValue(frm.elements['week3'].value, /^((\d+)|(\d+.{2})|(\d+.{1}))$/))
  {
    alert('Invalid HOURS. Please check that you have entered it correctly.');
    frm.elements['week3'].focus();
    frm.elements['week3'].select();
    return false;
  }
  else if((+(frm.elements['week3'].value) < 0)||(+(frm.elements['week3'].value) > 35))
  {
    alert('Invalid HOURS. Maximum workable hours is 35. Please check that you have entered it correctly.');
    frm.elements['week3'].focus();
    frm.elements['week3'].select();
    return false;
  }
  
  if(!validateNotEmpty(frm.elements['week4'].value))
  {
    alert('HRS WORKED DURING WEEK OF JUNE 20-JUNE 24 is required.');
    return false;
  }
  else if(!validateValue(frm.elements['week4'].value, /^((\d+)|(\d+.{2})|(\d+.{1}))$/))
  {
    alert('Invalid HOURS. Please check that you have entered it correctly.');
    frm.elements['week4'].focus();
    frm.elements['week4'].select();
    return false;
  }
  else if((+(frm.elements['week4'].value) < 0)||(+(frm.elements['week4'].value) > 35))
  {
    alert('Invalid HOURS. Maximum workable hours is 35. Please check that you have entered it correctly.');
    frm.elements['week4'].focus();
    frm.elements['week4'].select();
    return false;
  }
  
  if(frm.elements['reason_for_record'].selectedIndex == -1)
  {
    alert('REASON FOR ROE REQUEST is required');
    return false;
  }
  
  if(frm.elements['reason_for_record'].options[frm.elements['reason_for_record'].selectedIndex].value == 'REPLACEMENT')
  {
    if(!validateNotEmpty(frm.elements['replacement_start_date'].value))
    {
      alert('REPLACEMENT START DATE is required');
      return false;
    }
    else if(!validateNotEmpty(frm.elements['replacement_finish_date'].value))
    {
      alert('REPLACEMENT END DATE is required');
      return false;
    }
    else
    {
      var arr = frm.elements['replacement_start_date'].value.split('/');
      var sd = new Date(arr[2], arr[1], arr[0]);
      
      arr = frm.elements['replacement_finish_date'].value.split('/');
      var fd = new Date(arr[2], arr[1], arr[0]);
      
      if(fd < sd)
      {
        alert('REPLACEMENT END DATE should be AFTER REPLACEMENT START DATE');
        return false;
      }
    }
  }
  
  if(frm.elements['reason_for_record'].options[frm.elements['reason_for_record'].selectedIndex].value == 'MATERNITY')
  {
    if(!validateNotEmpty(frm.elements['birth_date'].value))
    {
      alert('BABY BIRTH DATE is required');
      return false;
    }
  }
  
  if(!validateNotEmpty(frm.elements['last_record_issued_date'].value))
  {
    alert('DATE LAST RECORD ISSUED is required');
    return false;
  }
  else
  {
    var arr = frm.elements['first_day_worked'].value.split('/');
    var sd = new Date(arr[2], arr[1], arr[0]);
      
    arr = frm.elements['last_record_issued_date'].value.split('/');
    var fd = new Date(arr[2], arr[1], arr[0]);
      
    if(fd > sd)
    {
      alert('DATE LAST RECORD ISSUED should be BEFORE FIRST DAY WORKED');
      return false;
    }
  }
  
  //all clear
  return true;
}

function calendarPopup(obj_id)
{
  var obj = document.getElementById(obj_id);
  var datepicker = new CalendarPopup(obj);
  datepicker.year_scroll = true;
  datepicker.time_comp = false;
        
  datepicker.popup();
}
      
function request_reason_info(sel)
{
  var row = document.getElementById('REPLACEMENT');
  row.style.display = "none";
          
  row = document.getElementById('MATERNITY');
  row.style.display = "none";
  
  if(sel.options[sel.selectedIndex].value.indexOf('REPLACEMENT') >= 0)
    row = document.getElementById('REPLACEMENT');
  else if(sel.options[sel.selectedIndex].value.indexOf('MATERNITY') >= 0)
    row = document.getElementById('MATERNITY');
  else
    row = null;
        
  if(row)
    row.style.display = "inline";
}

function add_unpaid_date(row_id, date_id, hrs_id)
{
  var text_obj = document.getElementById(date_id);
  var hrs_obj = document.getElementById(hrs_id);
  
  if((text_obj.value != "")&&(hrs_obj.value != ""))
  {
    if(!validateValue(hrs_obj.value, /^(\d+)$/))
    {
      alert('Invalid HOURS. Please check that you have entered it correctly.');
      hrs_obj.focus();
      hrs_obj.select();
    }
    else if((+(hrs_obj.value) <= 0)||(+(hrs_obj.value) > 7))
    {
      alert('Invalid HOURS. Maximum workable hours is 7. Please check that you have entered it correctly.');
      hrs_obj.focus();
      hrs_obj.select();
    }
    else if((document.forms[0].elements[row_id].value != "") && (document.forms[0].elements[row_id].value.indexOf(text_obj.value) >= 0))
    {
      alert(text_obj.value + " has already been added.");
    }
    else
    {
      var row = document.getElementById(row_id + '_row');
          
      row.style.display="inline";
            
      var li = document.getElementById('UNPAID_'+text_obj.value);
      if(li)
      {
        li.style.display = "inline";
      }
      else
      {
        var tbl = document.getElementById(row_id + '_tbl');
        var row;
              
        if(tbl.rows)
          row = tbl.insertRow(tbl.rows.length);
        else
          row = tbl.insertRow(0);
              
        row.setAttribute('id', 'UNPAID_'+text_obj.value);
        row.setAttribute('style', 'border-bottom:solid 1px #c4c4c4;');
              
        row.insertCell(0);
        row.cells[0].setAttribute('style', 'padding-left:3px;');
        row.cells[0].innerHTML = "<img src='images/bullet.gif' style='padding-left:5px; padding-right:8px;'>" + text_obj.value + " - " + hrs_obj.value + " HRS";
              
        row.insertCell(1);
        row.cells[1].innerHTML = "<img src='images/btn_delete_01.gif' onmouseover=\"this.src='images/btn_delete_02.gif';\" " +
            "onmouseout=\"this.src='images/btn_delete_01.gif';\" onclick=\"del_unpaid_date('" + text_obj.value +"');\">"
      }
            
      document.forms[0].elements['unpaid_dates'].value = document.forms[0].elements['unpaid_dates'].value + text_obj.value + "-" + hrs_obj.value + "|";
      document.forms[0].elements['unpaid_dates'].value = trimAll(document.forms[0].elements['unpaid_dates'].value);
    }
    text_obj.value = "";
    hrs_obj.value = "0";
  }
  else
  {
    alert('Both DATE and HOURS WORKED are required.');
  }
}

function del_unpaid_date(dt_txt)
{
  var dt_li = document.getElementById('UNPAID_'+dt_txt);
        
  if(dt_li)
  {
    dt_li.style.display="none";
          
    var arr = document.forms[0].elements['unpaid_dates'].value.split(dt_txt);
          
    document.forms[0].elements['unpaid_dates'].value = "";
    
    if(arr.length == 2)
    {
      arr[0] = trimAll(arr[0]);
      
      arr[1] = trimAll(arr[1].substring(arr[1].indexOf('|') + 1, arr[1].length));
    }
    
    for(i=0; i < arr.length; i++)
      document.forms[0].elements['unpaid_dates'].value = document.forms[0].elements['unpaid_dates'].value + arr[i];
          
    document.forms[0].elements['unpaid_dates'].value = trimAll(document.forms[0].elements['unpaid_dates'].value);
          
    if(document.forms[0].elements['unpaid_dates'].value == "")
    {
      var row = document.getElementById('unpaid_dates_row');
      if(row)
        row.style.display = "none";
    }
  }
}
      
      function add_sick_leave_date(row_id, date_id)
      {
        var text_obj = document.getElementById(date_id);
        
        if(text_obj.value != "")
        { 
          if((document.forms[0].elements['sick_dates'].value != "") && (document.forms[0].elements['sick_dates'].value.indexOf(text_obj.value) >= 0))
          {
            alert(text_obj.value + " has already been added.");
          }
          else
          {
            var row = document.getElementById(row_id + '_row');
          
            row.style.display="inline";
            
            var li = document.getElementById('SICK_'+text_obj.value);
            if(li)
            {
              li.style.display = "inline";
            }
            else
            {
              var tbl = document.getElementById(row_id);
              var row;
              
              if(tbl.rows)
                row = tbl.insertRow(tbl.rows.length);
              else
                row = tbl.insertRow(0);
              
              row.setAttribute('id', 'SICK_'+text_obj.value);
              
              row.insertCell(0);
              row.cells[0].setAttribute('style', 'padding-left:3px;');
              row.cells[0].innerHTML = "<img src='images/bullet.gif' style='padding-left:5px; padding-right:8px;'>" + text_obj.value;
              
              row.insertCell(1);
              row.cells[1].innerHTML = "<img src='images/btn_delete_01.gif' onmouseover=\"this.src='images/btn_delete_02.gif';\" " +
                  "onmouseout=\"this.src='images/btn_delete_01.gif';\" onclick=\"del_sick_leave_date('" + text_obj.value +"');\">"
            }
            
            document.forms[0].elements['sick_dates'].value = document.forms[0].elements['sick_dates'].value + " " + text_obj.value;
            document.forms[0].elements['sick_dates'].value = trimAll(document.forms[0].elements['sick_dates'].value);
          }
          text_obj.value = "";
        }
        else
        {
          alert('A DATE is required.');
        }
      }
      
      function del_sick_leave_date(dt_txt)
      {
        var dt_li = document.getElementById('SICK_'+dt_txt);
        
        if(dt_li)
        {
          dt_li.style.display="none";
          
          var arr = document.forms[0].elements['sick_dates'].value.split(dt_txt);
          
          document.forms[0].elements['sick_dates'].value = "";
          
          for(i=0; i < arr.length; i++)
            document.forms[0].elements['sick_dates'].value = document.forms[0].elements['sick_dates'].value + " " + arr[i];
          
          document.forms[0].elements['sick_dates'].value = trimAll(document.forms[0].elements['sick_dates'].value);
          
          if(document.forms[0].elements['sick_dates'].value == "")
          {
            var row = document.getElementById('sick_leave_dates_row');
            if(row)
              row.style.display = "none";
          }
        }
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