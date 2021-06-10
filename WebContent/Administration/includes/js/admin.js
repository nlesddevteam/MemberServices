
function validatePermission(form)
{
  var check = true;

  if(form.uid.value=="")
  {
    alert( 'UID is Required' );
    check = false;
  }
  else if(form.description.value=="")
  {
    alert( 'Description is Required' );
    check = false;
  }
  
  return check;
}

function validateRole(form)
{
  var check = true;

  if(form.uid.value=="")
  {
    alert( 'UID is Required' );
    check = false;
  }
  else if(form.description.value=="")
  {
    alert( 'Description is Required' );
    check = false;
  }
  
  return check;
}

function refresh(uid)
{
  var wnd = self.name;
  
  self.opener.parent.menu.location.reload(true);

  if(wnd == 'ModifyPermission')
  {
    self.opener.location.href='viewPermission.html?uid='+uid;
  }
  else if(wnd == 'DeletePermission')
  {
    self.opener.location.href='addPermission.html?passthrough=true';
  }
  else if(wnd == 'ModifyRole')
  {
    self.opener.location.href='viewRole.html?uid='+uid;
  }
  else if(wnd == 'DeleteRole')
  {
    self.opener.location.href='addRole.html?passthrough=true';
  }
}
