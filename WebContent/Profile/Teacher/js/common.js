function View_Edit(cancel)
{
  if(cancel == false)
  {
    document.getElementById('RowView').style.display='none';
    document.getElementById('RowEdit').style.display='inline';
  }
  else
  {
    document.getElementById('RowView').style.display='inline';
    document.getElementById('RowEdit').style.display='none';
  }
}

function validateAdd(sel_obj)
{
  var check = true;

  if(sel_obj.selectedIndex == 0)
  {
    alert ("Please make an appropriate selection.");
    check = false;
  }

  return check;
}

function validateName(frm)
{
  var check = true;

  if(frm.firstname.value == "")
  {
    alert ("Firstname is required.");
    check = false;
  }
  else if(frm.lastname.value == "")
  {
    alert ("Lastname is required.");
    check = false;
  }
  return check;
}