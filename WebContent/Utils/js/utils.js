function confirm(title, prompt, action) {
  var w = 200;
  var h = 200;
  var winl = (screen.width-w)/2;
  var wint = (screen.height - h - 25 )/2;

  window.open("/Utils/confirm.jsp?title="+title+"&prompt="+prompt+"&action="+action, MSConfirm, "toolbar=0,location=no,top="+wint+",left="+winl+",directories=0,status=0,menbar=0,scrollbars=0,resizable=0,width="+w+",height="+h);
}