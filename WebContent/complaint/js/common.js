function openWindow(id,url,w,h, scroll) {
  var winl = (screen.width-w)/2;
  var wint = (screen.height - h - 25 )/2;

  window.open(url,id,"titlebar=0,toolbar=0,location=no,top="+wint+",left="+winl+",directories=0,status=1,menbar=0,scrollbars="+scroll+",resizable=0,width="+w+",height="+h);
}

function validateNotEmpty(fld) 
{
    return /\S+/.test(fld.value);
}

function validateSelectionMade(fld)
{
  return ((fld.selectedIndex != -1) && (fld.options[fld.selectedIndex].value != -1));
}

function validateRadioButtonSelected(fld)
{
  var selected = false;
  var i;
 
  for(i=0; i < fld.length; i++)
  {
    if(fld[i].checked)
    {
      selected = true;
      break;
    }
  }
  
  return selected;
}

function validatePostalCode(fld)
{
  return /^[a-zA-Z]\d[a-zA-Z]\s\d[a-zA-Z]\d$/.test(fld.value);
}

function validateZipCode(fld)
{
  return /^\d{5}$/.test(fld.value);
}

function validateZipOrPostalCode(fld)
{
  return (validatePostalCode(fld) || validateZipCode(fld));
}

function validateEmail(fld) 
{
    return /^[\w.\-]+@[\w\-]+\.[a-zA-Z0-9]+$/.test(fld.value);
}


function validateAreaCode(fld)
{
  return /^\d{3}$/.test(fld.value);
}

function validatePhoneNumber(fld)
{
  return /^(\d{3})[.-]?(\d{4})$/.test(fld.value);
}


function FrontPage_Form1_Validator(theForm)
{

  if (!validateNotEmpty(theForm.First_Name))
  {
    alert("Please enter a value for the \"First Name\" field.");
    theForm.First_Name.focus();
    return (false);
  }

  if (!validateNotEmpty(theForm.Last_Name))
  {
    alert("Please enter a value for the \"Last Name\" field.");
    theForm.Last_Name.focus();
    return (false);
  }

  if (!validateNotEmpty(theForm.Address_Line_1))
  {
    alert("Please enter a value for the \"Address Line 1\" field.");
    theForm.Address_Line_1.focus();
    return (false);
  }

  if (!validateNotEmpty(theForm.City))
  {
    alert("Please enter a value for the \"City\" field.");
    theForm.City.focus();
    return (false);
  }

  if (!validateSelectionMade(theForm.Province))
  {
    alert("Please select a value for the \"Province\" field.");
    theForm.Province.focus();
    return (false);
  }
  
  if (!validateSelectionMade(theForm.Country))
  {
    alert("Please select a value for the \"Country\" field.");
    theForm.Province.focus();
    return (false);
  }
 
  if(!validateZipOrPostalCode(theForm.Postal_Code))
  {
    alert("The \"Postal Code\"  is empty or has an invalid format. \nPlease enter a value for the \"Postal Code\" field.");
    theForm.Postal_Code.focus();
    theForm.Postal_Code.select();
    return (false);
  }
  
  if(validateNotEmpty(theForm.Email_Address) && !validateEmail(theForm.Email_Address))
  {
    alert("The \"Email Address\"  is empty or has an invalid format. \nPlease enter a value for the \"Email Address\" field.");
    theForm.Email_Address.focus();
    theForm.Email_Address.select();
    return (false);
  }
  
  if (!validateAreaCode(theForm.Contact_Phone_Area_Code))
  {
    alert("The \"Contact Phone # Area Code\" is empty or has an invalid format. \nPlease enter a value for the \"Contact Phone # Area Code\" field.");
    theForm.Contact_Phone_Area_Code.focus();
    return (false);
  }

  if (!validatePhoneNumber(theForm.Contact_Phone))
  {
    alert("The \"Contact Phone #\" is empty or has an invalid format. \nPlease enter a value for the \"Contact Phone #\" field.");
    theForm.Contact_Phone.focus();
    return (false);
  }
  
  if (!validateSelectionMade(theForm.cat_id))
  {
    alert("Please select a value for the \"Complaint Category\" field.");
    theForm.cat_id.focus();
    return (false);
  }
  else
  {
    if(theForm.cat_id.options[theForm.cat_id.selectedIndex].value == 5)
    {
      if (!validateSelectionMade(theForm.school_id))
      {
        alert("Please select a value for the \"School\" field.");
        theForm.school_id.focus();
        return (false);
      }
      
      if (!validateRadioButtonSelected(theForm.school_admin_contacted))
      {
        alert("Please enter a value for the \"School Admin Contatced\" field.");
        return (false);
      }
      else
      {
        if(theForm.school_admin_contacted[0].checked)
        {
          if (!validateNotEmpty(theForm.contact_dates))
          {
            alert("Please enter a value for the \"Contact Dates\" field.");
            theForm.contact_dates.focus();
            return (false);
          }
        }
      }
    }
  }

  if (!validateNotEmpty(theForm.Complaint))
  {
    alert("Please enter a value for the \"Summarize your request for information/complaint and any step\" field.");
    theForm.Complaint.focus();
    return (false);
  }

  return (true);
}

function toggleSchoolRow(theSelect, theValue)
{
  var row = document.getElementById('school-row');
  
  if(theSelect.options[theSelect.selectedIndex].value == theValue)
    row.style.display = 'inline';
  else
    row.style.display = 'none';
}

function toggleAdminContactedRow(btn)
{
  var row = document.getElementById('admin-contacted-row');
  
  if(btn.value == 'true')
    row.style.display = 'inline';
  else
    row.style.display = 'none';
}